/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	private ChoiceBox<String> inventoryDropDown;
	@FXML
	private ChoiceBox<String> roomContentDropDown;
	@FXML
	private ChoiceBox<String> roomDropDown;

	/* Web view */
	@FXML
	private WebView console;

	/* Action events */
	@FXML
	private void northButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyGo("north");
		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void southButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyGo("south");
		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void eastButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyGo("east");
		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void westButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyGo("west");
		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void takeButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyTake(roomContentDropDown.getSelectionModel().getSelectedItem());
		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void dropButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyDrop(inventoryDropDown.getSelectionModel().getSelectedItem());
		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void useButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyUse(
				inventoryDropDown.getSelectionModel().getSelectedItem(),
				roomContentDropDown.getSelectionModel().getSelectedItem());

		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void inspectButtonOnAction(ActionEvent event) {
		String inventorySelection = inventoryDropDown.getSelectionModel().getSelectedItem();
		String roomSelection = roomContentDropDown.getSelectionModel().getSelectedItem();
		String msg;

		if (inventorySelection != null) {
			msg = businessMediator.notifyInspect(inventorySelection);
		} else {
			msg = businessMediator.notifyInspect(roomSelection);
		}

		writeToConsole(msg);

		updateGameDataDisplay();
	}

	@FXML
	private void inventoryButtonOnAction(ActionEvent event) {
		writeToConsole(businessMediator.notifyInventory());

		updateGameDataDisplay();
	}

	@FXML
	private void helpButtonOnAction(ActionEvent event) {
		writeToConsole(businessMediator.notifyHelp());

		updateGameDataDisplay();
	}

	@FXML
	private void peekButtonOnAction(ActionEvent event) {
		String msg = businessMediator.notifyPeek(roomDropDown.getSelectionModel().getSelectedItem());
		writeToConsole(msg);
		
		updateGameDataDisplay();
	}

	/**
	 * Set the business mediator to be used by this controller.
	 *
	 * @param bm the business mediator to use
	 */
	public void setBusinessMediator(BusinessMediator bm) {
		this.businessMediator = bm;

		String msg = businessMediator.start();
		writeToConsole(msg);
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
	 * Synchronize the display of the room exits and player and room inventories
	 * with the actual data in the game.
	 */
	private void updateGameDataDisplay() {
		//Update player inventory display
		List<String> playerItems = businessMediator.getPlayerItems();
		inventoryDropDown.getItems().setAll(playerItems);

		//Update room inventory and content display
		List<String> roomItems = businessMediator.getRoomItems();
		roomContentDropDown.getItems().setAll(roomItems);

		List<String> roomObjects = businessMediator.getRoomObjects();
		roomContentDropDown.getItems().addAll(roomObjects);
		
		//Update exits
		Map<String, String> exits = businessMediator.getCurrentExits();
		roomDropDown.getItems().setAll(exits.keySet());
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
