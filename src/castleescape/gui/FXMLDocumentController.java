/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.web.WebView;

/**
 * The controller for the main game GUI.
 *
 * @author Kasper
 */
public class FXMLDocumentController implements Initializable {

	/**
	 * The mediator to use for making calls to business code.
	 */
	private BusinessMediator businessMediator;

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
	private WebView console;

	//DropDown Menus
	@FXML
	private void roomAction(ActionEvent event) {

	}

	@FXML
	private void inventoryAction(ActionEvent event) {

	}

	@FXML
	private void peakAction(ActionEvent event) {

	}

	//Buttons actions
	@FXML
	private void useAction(ActionEvent event) {

	}

	@FXML
	private void hintAction(ActionEvent event) {

	}

	@FXML
	private void dropAction(ActionEvent event) {

	}

	@FXML
	private void takeAction(ActionEvent event) {
		
	}

	@FXML
	private void inspectAction(ActionEvent event) {

	}

	@FXML
	private void northAction(ActionEvent event) {
		console.getEngine().loadContent(businessMediator.start());
	}

	@FXML
	private void westAction(ActionEvent event) {

	}

	@FXML
	private void eastAction(ActionEvent event) {

	}

	@FXML
	private void southAction(ActionEvent event) {

	}
	
	public void setBusinessMediator(BusinessMediator bm) {
		this.businessMediator = bm;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

}
