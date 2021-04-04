/**
	Class EditorGUI is a code editor that would be a modified,
	"light" cousin of Crimson Editor. The user can: 
		-choose to create new classes
		-add buttons, labels, textfields, etc., by clicking buttons
		-could not get the tabs and EditorPane to work
**/

import java.util.Scanner;
import java.lang.ProcessBuilder;

import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.SplitPane;

import javafx.scene.control.Label; 
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup; 

import javafx.event.EventHandler; 
import javafx.event.ActionEvent;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCombination;

import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.geometry.Side;

import java.io.PrintWriter;
import javafx.stage.FileChooser; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import javafx.scene.layout.StackPane;

public class EditorGUI extends Application {
	
	private final int BUTTON = 0; 
	private final int NEW = 1;
	private final int CLASS_IMPORT = 2;
	private final int PACKAGE_IMPORT = 3; 
	private final int ADD_PACKAGE = 4;
	
	private final int TOOLBOX_ITEMS = 7;
	private String controlString;
	private int tabCount = 0; 	
	
	private BorderPane mainBP;
	private BorderPane leftBP;
	private BorderPane rightBP;
	private SplitPane mainSP;
	private TextArea outputTA;
	private Label toolboxLabel; 
	
	private TextArea[] textAreaTabs = new TextArea[10];
	private Button[] toolboxBtns = new Button[TOOLBOX_ITEMS];
	private Button buttonBtn;
	private Button labelBtn;
	private Button textfieldBtn;
	private Button borderPaneBtn;
	private Button splitPaneBtn;
	private Button stageBtn;
	private Button sceneBtn;
	
	private VBox sizeVB;
	private VBox toolboxVB; 
	private HBox toolboxHB;
	
	private MenuBar mainMB;
    private Menu fileMenu;
    private Menu classMenu;
    private Menu packageMenu;
    private Menu toolsMenu;
	private Menu helpMenu;
	private MenuItem openMI;
    private MenuItem saveMI;
    private MenuItem exitMI;
	private MenuItem newMI;
    private MenuItem cImportMI;
    private MenuItem pImportMI;
	private MenuItem addMI;
	private MenuItem MS_DOSMI;
	private MenuItem helpMI;
	
	private TabPane newCodeTabs;
	private Tab nextTab; 
	private TextArea nextTabTA;
	private EditorPane nextTabEP;
	
	//Second Stage variables
	private GridPane nameGP; 
	private VBox nameVB;
	private Label nameLabel;
	private TextField nameTF;
	private Button nameAddBtn;
	private Scene secondScene; 
	private Stage secondStage;
	
	//Help Stage variables
	private BorderPane helpBP;
	private TextArea helpTA;
	private Button helpOKBtn; 
	private VBox helpVB;
	private Scene helpScene; 
	private Stage helpStage; 
	
	private FileChooser fileChooser;    
	private Scene scene; 
	private MenuHandler listener;
	private ButtonHandler toolboxHandler;
		
	public void start(Stage primaryStage) {
		newCodeTabs = new TabPane(); 
		newCodeTabs.setSide(Side.TOP);
		newCodeTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		newCodeTabs.setRotateGraphic(true);
		
		setUpMenus();
		setUpButtons();
		
		toolboxLabel = new Label("TOOLBOX"); 
		toolboxLabel.setWrapText(true);
		toolboxLabel.setStyle("-fx-font-weight: bold"); 
		toolboxHB = new HBox(10);
		toolboxHB.setAlignment(Pos.CENTER); 
		toolboxHB.getChildren().add(toolboxLabel);
		
		outputTA = new TextArea();
		outputTA.setText("Create a New Class to get started!"); 
		
	 	mainBP = new BorderPane();
	 	mainBP.setTop(newCodeTabs);
	 	mainBP.setCenter(outputTA);
	 	
	 	leftBP = new BorderPane();
	 	leftBP.setTop(mainMB);
	 	leftBP.setCenter(mainBP);
	 	
	 	rightBP = new BorderPane();
	 	rightBP.setTop(toolboxHB);
	 	rightBP.setCenter(toolboxVB);
		
		mainSP = new SplitPane(); 
		mainSP.setDividerPosition(0, 0.75);
		mainSP.setOrientation(Orientation.HORIZONTAL);
		mainSP.getItems().add(leftBP);
		mainSP.getItems().add(rightBP);
		
		listener = new MenuHandler();
     	openMI.setOnAction(listener);	
		saveMI.setOnAction(listener);
        exitMI.setOnAction(listener);
		newMI.setOnAction(listener);
      	cImportMI.setOnAction(listener);
      	pImportMI.setOnAction(listener);
      	addMI.setOnAction(listener);
      	MS_DOSMI.setOnAction(listener);
      	helpMI.setOnAction(listener);
		
		scene = new Scene(mainSP, 700, 700);
		
		primaryStage.setTitle("Crimson Editor LITE");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	} //end of start()
	
	/******************* MENUS SOURCE CODE *******************/
	private void setUpMenus() {
		//setting up a menu:
	 	mainMB = new MenuBar();
		fileMenu = new Menu("_File");
		classMenu = new Menu("_Class");
		packageMenu = new Menu("_Package");
		toolsMenu = new Menu("_Tools");
		helpMenu = new Menu("_Help");
		
		//initialize menu items
		openMI = new MenuItem("_Open");
		saveMI = new MenuItem("_Save");
		exitMI = new MenuItem("_Exit");
		newMI = new MenuItem("_New");
		cImportMI = new MenuItem("_Import");
		pImportMI = new MenuItem("_Import");
		addMI = new MenuItem("_Add");
		MS_DOSMI = new MenuItem("_MS-DOS Shell");
		helpMI = new MenuItem("_Help");
	
		//add menu items to menu	 
		fileMenu.getItems().add(openMI);
		fileMenu.getItems().add(saveMI);
		fileMenu.getItems().add(exitMI);
		classMenu.getItems().add(newMI);
		classMenu.getItems().add(cImportMI);
		packageMenu.getItems().add(pImportMI);
		packageMenu.getItems().add(addMI);
		toolsMenu.getItems().add(MS_DOSMI);
		helpMenu.getItems().add(helpMI);
		 
		//adding all menus to the menu bar
	 	mainMB.getMenus().add(fileMenu);
	 	mainMB.getMenus().add(classMenu);
	 	mainMB.getMenus().add(packageMenu);
	 	mainMB.getMenus().add(toolsMenu);
	 	mainMB.getMenus().add(helpMenu);
	}
	
	private class MenuHandler implements EventHandler<ActionEvent> {


// 		private MenuItem helpMI;
		public void handle(ActionEvent e) {
			
			if (e.getSource() == openMI) {
				openFile();
			}
			else if (e.getSource() == saveMI) {
				saveFile();
			}	
 			else if (e.getSource() == exitMI) {
		       	exitFile();
		       	System.exit(0);			
 	        }
 	        else if (e.getSource() == newMI) {
		       	secondStage(NEW);
		       	createTab();	
 	        }
 	        else if (e.getSource() == cImportMI) {
		       	secondStage(CLASS_IMPORT);
		       	controlString = nameTF.getText();
				outputTA.appendText("\nimport java." + controlString + ";");
 	        }
 	        else if (e.getSource() == pImportMI) {
		       	secondStage(PACKAGE_IMPORT);
		       	controlString = nameTF.getText();
				outputTA.appendText("\nimport " + controlString + ".*;");
 	        }
 	        else if (e.getSource() == addMI) {
		       	secondStage(ADD_PACKAGE);
		       	controlString = nameTF.getText();
				outputTA.appendText("\npackage " + controlString + ";");
 	        }
 	        else if (e.getSource() == MS_DOSMI) {
		       	msdosShell();
 	        }
 	        else if (e.getSource() == helpMI) {
		       	helpStage();
 	        }
				
		}
	} //end of class MenuHandler
	
	//Connected to the open menu item, opens a file chooser
	//and prints whatever is in that file to the text area
	private void openFile() {
		File inputFile;
		Scanner inputReader; 
		
		inputFile = null; 
		inputReader = null; 
		
		fileChooser = new FileChooser();
	    fileChooser.setTitle("Open File");
		
		try {	
			inputFile = fileChooser.showOpenDialog(new Stage());
			outputTA.setText("");	
			inputReader = new Scanner(inputFile);
			while (inputReader.hasNextLine()) {
				outputTA.appendText("\n" + inputReader.nextLine());
			}
		}
		catch (FileNotFoundException fnfe) {
			outputTA.setText("");	
			outputTA.appendText("\nNo items currently in the library. Add some if you like!");
		}		
		finally {
			if (inputReader != null) {
				inputReader.close();
			}
		}		
	}
	
	//Connected to the save menu item, opens a file chooser
	//and saves whatever is in the textarea to the file chosen
	private void saveFile() {
		File outputFile;
		PrintWriter outputWriter; 
		String textAreaString = outputTA.getText();
		
		outputFile = null; 
		outputWriter = null; 
		
		fileChooser = new FileChooser();
	    fileChooser.setTitle("Save File");
		
		try { 
			outputFile = fileChooser.showSaveDialog(new Stage());
			outputWriter = new PrintWriter(outputFile);
			outputWriter.println("\n" + outputTA.getText());
		}
		catch (FileNotFoundException fnfe) {
			outputTA.setText("");	
			outputTA.setText("\nCould not open the file for writing.");	
		}		
		finally {
			if (outputWriter != null) {
				outputWriter.close();
			}
		}		
	}
	
	private void exitFile() {
		File outputFile;
		PrintWriter outputWriter; 
		
		outputFile = null; 
		outputWriter = null; 
		
		try { 
			//Might need to update the file path for the output and input files
			outputFile = new File("D:/College/Java/Java II/Assignments/Assignment03/Saved Programs(exitMI)/program.txt");
			outputWriter = new PrintWriter(outputFile);
			outputWriter.println("\n" + outputTA.getText());
		}
		catch (FileNotFoundException fnfe) {
			outputTA.setText("");	
			outputTA.setText("\nCould not open the file for writing.");	
		}		
		finally {
			if (outputWriter != null) {
				outputWriter.close();
			}
		}		
	}
	
	private void createTab() {
		tabCount++;
		nextTab = new Tab("Tab: " + tabCount);  
		
		textAreaTabs[tabCount] = new TextArea();
        nextTab.setContent(textAreaTabs[tabCount]);
		
		newCodeTabs.getTabs().add(nextTab);
	}
	
	private void msdosShell() {
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "start");
		
		try {
			pb.start();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}	
	
	/******************* BUTTONS SOURCE CODE *******************/
	private void setUpButtons() {
		buttonBtn = new Button("Button");
		labelBtn = new Button("Label");
		textfieldBtn = new Button("TextField");
		borderPaneBtn = new Button("BorderPane");
		splitPaneBtn = new Button("SplitPane");
		sceneBtn = new Button("Scene");
		stageBtn = new Button("Stage");
		
		customButtons();
		
		toolboxVB = new VBox(10);
		toolboxVB.setAlignment(Pos.CENTER);
		for (int i = 0; i < toolboxBtns.length; i++) {
			toolboxVB.getChildren().add(toolboxBtns[i]);
		}
		
		toolboxHandler = new ButtonHandler(); 
		for (int i = 0; i < toolboxBtns.length; i++) {
			toolboxBtns[i].setOnAction(toolboxHandler);
		}
	}
	
	private void customButtons() {
		toolboxBtns[0] = buttonBtn;	
		toolboxBtns[1] = labelBtn;	
		toolboxBtns[2] = textfieldBtn;	
		toolboxBtns[3] = borderPaneBtn;	
		toolboxBtns[4] = splitPaneBtn;	
		toolboxBtns[5] = sceneBtn;	
		toolboxBtns[6] = stageBtn;
		
		for (int i = 0; i < toolboxBtns.length; i++) {
			toolboxBtns[i].setMaxWidth(100);
			toolboxBtns[i].setStyle("-fx-background-color: linear-gradient(to top, darkgray, snow);" +
									"-fx-border-color: darkgray");
		}
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent ae) {
			
			if (ae.getSource() == buttonBtn) {
				secondStage(BUTTON);
				controlString = nameTF.getText();
				outputTA.appendText("\nButton " + controlString + ";");
				outputTA.appendText("\n" + controlString + 
									" = new Button(\"" + controlString + "\");");	
			} 
			else if (ae.getSource() == labelBtn) {
				secondStage(BUTTON);
				controlString = nameTF.getText();
				outputTA.appendText("\nLabel " + controlString + ";");
				outputTA.appendText("\n" + controlString + 
									" = new Label( _insert text_ );");	
			}
			else if (ae.getSource() == textfieldBtn) {
				secondStage(BUTTON);
				controlString = nameTF.getText();
				outputTA.appendText("\nTextField " + controlString + ";");
				outputTA.appendText("\n" + controlString + 
									" = new TextField();");	
			}
			else if (ae.getSource() == borderPaneBtn) {
				secondStage(BUTTON);
				controlString = nameTF.getText();
				outputTA.appendText("\nBorderPane " + controlString + ";");
				outputTA.appendText("\n" + controlString + 
									" = new BorderPane();");	
			} 
			else if (ae.getSource() == splitPaneBtn) {
				secondStage(BUTTON);
				controlString = nameTF.getText();
				outputTA.appendText("\nSplitPane " + controlString + ";");
				outputTA.appendText("\n" + controlString + 
									" = new SplitPane();");	
			}   
			else if (ae.getSource() == sceneBtn) {
				secondStage(BUTTON);
				controlString = nameTF.getText();
				outputTA.appendText("\nScene " + controlString + ";");
				outputTA.appendText("\n" + controlString + 
									" = new Scene( _insert pane_, _size_, _size_);");	
			} 
			else if (ae.getSource() == stageBtn) {
				secondStage(BUTTON);
				controlString = nameTF.getText();
				outputTA.appendText("\nStage " + controlString + ";");
				outputTA.appendText("\n" + controlString + 
									" = new Stage( _insert scene_);");	
			} 		
		}
	} //end of class ButtonHandler
	
	// Second stage for the Toolbox buttons 
	// Opens the controlStage and gets the user's control variable name
	// or class name
	private void secondStage(int choice) {
		
		if (choice == BUTTON) {
			nameLabel = new Label("Control Name: ");
		}
		else if (choice == NEW) {
			nameLabel = new Label("Class Name: ");
		}
		else if (choice == CLASS_IMPORT) {
			nameLabel = new Label("Class Import Name (ex. util.Scanner): ");
		}
		else if (choice == PACKAGE_IMPORT) {
			nameLabel = new Label("Import Name for entire package (ex. java.io): ");
		}
		else if (choice == ADD_PACKAGE) {
			nameLabel = new Label("Name for package statement: ");
		}
		nameTF = new TextField();
		
		nameGP = new GridPane();
		nameGP.setHgap(10); 
		nameGP.setVgap(10); 
		nameGP.setAlignment(Pos.CENTER);
		nameGP.add(nameLabel, 0, 0);
		nameGP.add(nameTF, 1, 0);
		
		nameAddBtn = new Button("OK");
		nameAddBtn.setOnAction(ae -> { secondStage.close(); });
		
		nameVB = new VBox(10);
		nameVB.setAlignment(Pos.CENTER);
		nameVB.getChildren().add(nameGP);
		nameVB.getChildren().add(nameAddBtn);

		secondScene = new Scene(nameVB, 400, 100);
		
		secondStage = new Stage();
		secondStage.setTitle("Name Description");
		secondStage.setScene(secondScene);
		secondStage.showAndWait();
	} //end of controlStage()
	
	private void helpStage() {
		
		helpTA = new TextArea();
		helpTA.setEditable(false);
		helpTA.setText("This is Crimson Editor LITE! " + 
						"This will do everything CE does but worse!" + 
						"\nOn the left, you have a menu which allows: " + 
						"\n\tLoad, Save, & Exit \n\tCreate a new Tab " + 
						"\n\tImport statements and packages " + 
						"\n\tAnd Test your code! \nOn the right, you " + 
						"have an assortment of buttons that will make " + 
						"your life easier! \nEnjoy Crimson Editor LITE!");
		
		helpOKBtn = new Button("OK");
		helpOKBtn.setOnAction(ae -> { helpStage.close(); });
		
		helpVB = new VBox(10);
		helpVB.setAlignment(Pos.CENTER);
		helpVB.getChildren().add(helpTA);
		helpVB.getChildren().add(helpOKBtn);

		helpScene = new Scene(helpVB, 500, 400);
		
		helpStage = new Stage();
		helpStage.setTitle("Help");
		helpStage.setScene(helpScene);
		helpStage.showAndWait();
	} //end of controlStage()
	
} //end of EditorGUI()





//exitButton.setOnAction(ae -> { System.exit(0); });
