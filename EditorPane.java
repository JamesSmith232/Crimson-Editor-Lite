/**
	Class EditorPane is a subclass of BorderPane
		-contains a scrollable, editable text area
		 in the center
**/

import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

import java.io.*;

public class EditorPane extends BorderPane {
	protected File currentFile; 
	protected TextArea outputTA;
	
	public EditorPane() {
		
		if (this.currentFile.exists()) {
			this.currentFile = currentFile; 
		}
		else {
			this.currentFile = null; 
		}
		
		this.outputTA = new TextArea();
		this.outputTA.setText("WORKS");
		getChildren().add(this.outputTA);
	}	
	
	protected TextArea getEP() {
		return this.outputTA;
	}
	
	protected void addLine(String line) {
		this.outputTA.appendText(line);
	}
	
	protected File getCurrentFile() {
		return this.currentFile;
	}
	
	protected void setCurrentFile(File newFile) {
		this.currentFile = newFile;			
	}
	
	protected void saveCurrentFile() {
		PrintWriter outputWriter = null; 
				
		try { 
			this.currentFile = new File("F:/College/Java/Java II/Assignments/Assignment03/currentfile.txt");
			outputWriter = new PrintWriter(this.currentFile);
			outputWriter.println("\n" + this.outputTA.getText());
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("\nCould not open the file for writing.");	
		}		
		finally {
			if (outputWriter != null) {
				outputWriter.close();
			}
		}	
	}
	
	protected void openFile(File newFile) {
		PrintWriter outputWriter = null; 
		this.currentFile = newFile; 
		
		try { 
			this.currentFile = new File("F:/College/Java/Java II/Assignments/Assignment03/currentfile.txt");
			outputWriter = new PrintWriter(this.currentFile);
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("\nCould not open the file for writing.");	
		}
	}
	
	protected void openCurrentFile(File newFile) {
		PrintWriter outputWriter = null;
		
		try { 
			this.currentFile = new File("F:/College/Java/Java II/Assignments/Assignment03/currentfile.txt");
			outputWriter = new PrintWriter(this.currentFile);
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("\nCould not open the file for writing.");	
		}
	}	
	
}
