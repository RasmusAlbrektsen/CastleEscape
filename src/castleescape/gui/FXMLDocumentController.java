/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.web.WebView;

/**
 *
 * @author Kasper
 */
public class FXMLDocumentController implements Initializable {
	
	// DropDown Menus
	@FXML
	private MenuButton roomButton;

	@FXML
	private MenuButton inventoryButton;
	
	@FXML
	private MenuButton peakButton;
	
	//Buttons
	@FXML
	private Button useButton;

	@FXML
	private Button hintButton;	
	
	@FXML
	private Button dropButton;
		
	@FXML
	private Button takeButton;
			
	@FXML
	private Button inspectButton;
				
	@FXML
	private Button northButton;
		
	@FXML
	private Button westButton;
	
	@FXML
	private Button eastButton;
	
	@FXML
	private Button southButton;
	
	//WebView
	@FXML
	private WebView webViewField;
	
	// DropDown Menus
	@FXML
	private void roomAction(ActionEvent event){
		
	}
	
	@FXML
	private void inventoryAction(ActionEvent event){
		
	}
	
	@FXML
	private void peakAction(ActionEvent event){
		
	}
	
	//Buttons
	
	@FXML
	private void useAction(ActionEvent event){
		
	}
	
	@FXML
	private void hintAction(ActionEvent event){
		
	}
	
	@FXML
	private void dropAction(ActionEvent event){
		
	}
	
	@FXML
	private void takeAction(ActionEvent event){
			webViewField.getEngine().load("http://www.google.dk/");
	}
	
	@FXML
	private void inspectAction(ActionEvent event){
		
	}
	
	@FXML
	private void northAction(ActionEvent event){
		
	}
	
	@FXML
	private void westAction(ActionEvent event){
		
	}
	
	@FXML
	private void eastAction(ActionEvent event){
		
	}
	
	
	@FXML
	private void southAction(ActionEvent event){
		
	}
	
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}	
	
}
