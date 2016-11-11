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
 * Controller class for the game GUI view.
 *
 * @author Kasper
 */
public class GameGuiController implements Initializable {

	/**
	 * The mediator to use for making calls to business code.
	 */
	private BusinessMediator businessMediator;

	/* Buttons */
	@FXML
	private Button northButton;
	@FXML
	private Button southButton;
	@FXML
	private Button eastButton;
	@FXML
	private Button westButton;
	@FXML
	private Button takeButton;
	@FXML
	private Button dropButton;
	@FXML
	private Button useButton;
	@FXML
	private Button inspectButton;
	@FXML
	private Button inventoryButton;
	@FXML
	private Button helpButton;
	@FXML
	private Button peekButton;

	/* Drop downs */
	@FXML
	private MenuButton inventoryDropDown;
	@FXML
	private MenuButton roomContentDropDown;
	@FXML
	private MenuButton roomDropDown;

	/* Web view */
	@FXML
	private WebView console;

	/* Action events */
	@FXML
	private void northButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed north button</p>");
	}

	@FXML
	private void southButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed south button</p>");
	}

	@FXML
	private void eastButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed east button</p>");
	}

	@FXML
	private void westButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed west button</p>");
	}

	@FXML
	private void takeButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed take button</p>");
	}

	@FXML
	private void dropButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed drop button</p>");
	}

	@FXML
	private void useButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed use button</p>");
	}

	@FXML
	private void inspectButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed inspect button</p>");
	}

	@FXML
	private void inventoryButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed inventory button</p>");
	}

	@FXML
	private void helpButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed help button</p>");
	}

	@FXML
	private void peekButtonOnAction(ActionEvent event) {
		writeToConsole("<p>Pressed peak button</p>");
	}

	/**
	 * Set the business mediator to be used by this controller.
	 *
	 * @param bm the business mediator to use
	 */
	public void setBusinessMediator(BusinessMediator bm) {
		this.businessMediator = bm;
	}

	/**
	 * Write the specified string in the console. The string should be formatted
	 * in HTML.
	 *
	 * @param s the string to write to the console
	 */
	private void writeToConsole(String s) {
		console.getEngine().loadContent(s);
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
