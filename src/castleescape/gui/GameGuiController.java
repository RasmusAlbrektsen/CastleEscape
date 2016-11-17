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
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;

/**
 * Controller class for the game GUI view.
 *
 * @author Kasper
 */
public class GameGuiController implements Initializable {

	/**
	 * Constants for rendering rooms in the map world map.
	 */
	private static final double MAP_ROOM_WIDTH = 84,
			MAP_ROOM_HEIGHT = 84,
			MAP_ROOM_SPACING = 16;

	/**
	 * The compass image.
	 */
	private Image compassImg;

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

	/* Canvas */
	@FXML
	private Canvas compass;

	/* Action events */
	@FXML
	private void commandButtonOnAction(ActionEvent event) {
		Object source = event.getSource();
		boolean running = true;
		
		if (source == northButton) {
			running = northButtonPressed();
		} else if (source == southButton) {
			running = southButtonPressed();
		} else if (source == eastButton) {
			running = eastButtonPressed();
		} else if (source == westButton) {
			running = westButtonPressed();
		} else if (source == takeButton) {
			running = takeButtonPressed();
		} else if (source == dropButton) {
			running = dropButtonPressed();
		} else if (source == useButton) {
			running = useButtonPressed();
		} else if (source == inspectButton) {
			running = inspectButtonPressed();
		} else if (source == inventoryButton) {
			running = inventoryButtonPressed();
		} else if (source == helpButton) {
			running = helpButtonPressed();
		} else if (source == peekButton) {
			running = peekButtonPressed();
		}
		
		writeToConsole(businessMediator.getTextOutput());
		
		updateGameDataDisplay();
		
		if (! running){
			this.getNameAndSaveScore();
		}
	}
	
	private boolean northButtonPressed() {
		return businessMediator.notifyGo("north");
	}

	private boolean southButtonPressed() {
		return businessMediator.notifyGo("south");
	}
	
	private boolean eastButtonPressed() {
		return businessMediator.notifyGo("east");
	}

	private boolean westButtonPressed() {
		return businessMediator.notifyGo("west");
	}

	private boolean takeButtonPressed() {
		return businessMediator.notifyTake(roomContentDropDown.getSelectionModel().getSelectedItem());
	}
	
	private boolean dropButtonPressed() {
		return businessMediator.notifyDrop(inventoryDropDown.getSelectionModel().getSelectedItem());
	}

	private boolean useButtonPressed() {
		return businessMediator.notifyUse(
				inventoryDropDown.getSelectionModel().getSelectedItem(),
				roomContentDropDown.getSelectionModel().getSelectedItem());
	}
	
	private boolean inspectButtonPressed() {
		String inventorySelection = inventoryDropDown.getSelectionModel().getSelectedItem();
		String roomSelection = roomContentDropDown.getSelectionModel().getSelectedItem();

		if (inventorySelection != null) {
			return businessMediator.notifyInspect(inventorySelection);
		} else {
			return businessMediator.notifyInspect(roomSelection);
		}
	}

	private boolean inventoryButtonPressed() {
		return businessMediator.notifyInventory();
	}

	private boolean helpButtonPressed() {
		return businessMediator.notifyHelp();
	}

	private boolean peekButtonPressed() {
		return businessMediator.notifyPeek(roomDropDown.getSelectionModel().getSelectedItem());
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

		//Render compass and map
		renderCompass();
	}

	/**
	 * Render the compass and the view of the nearby rooms on the user
	 * interface.
	 */
	private void renderCompass() {
		//Get the graphics context from the compass canvas. The graphics context
		//will allow us to draw shapes and images on the part of the user
		//interface covered by the compass canvas.
		GraphicsContext g = compass.getGraphicsContext2D();

		//Clear previous renderings, otherwise rooms rendered previously will
		//carry over
		g.clearRect(0, 0, compass.getWidth(), compass.getHeight());

		//Calculate the center of the canvas, which is where we want to render
		//the current room
		double cx = compass.getWidth() / 2;
		double cy = compass.getHeight() / 2;

		//Render the current room, centered on (cx, cy)
		renderSquareCentered(g, cx, cy, MAP_ROOM_WIDTH, MAP_ROOM_HEIGHT);

		//Get the exits from the current room. A connection to all neighbor
		//rooms should be drawn
		Map<String, String> exits = businessMediator.getCurrentExits();

		//For every neighbor, figure out in which direction it is located
		//relative to the current room. For instance, a room to the north is
		//offset along the y axis in the negative direction (thus dx = 0 and
		//dy = -1), and a room to the east is offset along the x axis in the
		//positive direction (thus dx = 1 and dy = 0).
		for (Entry<String, String> neighbor : exits.entrySet()) {
			int dx = 0;
			int dy = 0;

			//The key is the direction in which the neighbor is located
			switch (neighbor.getKey()) {
				case "north":
					dy = -1;
					break;
				case "south":
					dy = 1;
					break;
				case "east":
					dx = 1;
					break;
				case "west":
					dx = -1;
					break;
			}

			//Render the neighbor room offset from the current room with a
			//spacing in between
			renderSquareCentered(g,
					cx + (MAP_ROOM_WIDTH + MAP_ROOM_SPACING) * dx,
					cy + (MAP_ROOM_HEIGHT + MAP_ROOM_SPACING) * dy,
					MAP_ROOM_WIDTH, MAP_ROOM_HEIGHT);

			//Render a connector between the current room and the neighbor,
			//symbolizing a door
			renderSquareCentered(g,
					cx + (MAP_ROOM_WIDTH + MAP_ROOM_SPACING) / 2 * dx,
					cy + (MAP_ROOM_HEIGHT + MAP_ROOM_SPACING) / 2 * dy,
					MAP_ROOM_SPACING, MAP_ROOM_SPACING);
		}

		int halfWidth = (int) compass.getWidth() / 2;
		int halfHeight = (int) compass.getHeight() / 2;
		
		for (int y = - halfHeight; y < halfHeight; y++) {
			for (int x = - halfWidth; x < halfWidth; x++) {
				if (x * x + y * y >= (halfWidth - 2) * (halfHeight - 2)) {
					g.getPixelWriter().setArgb(x + halfWidth, y + halfHeight, 0);
				}
			}
		}
		
		//Draw the compass image
		g.drawImage(compassImg, 0, 0);
	}

	/**
	 * Draw a filled rectangle with the current fill color in the specified
	 * graphics context centered on the specified coordinate (cx, cy) and with
	 * the specified dimensions.
	 *
	 * @param g  the graphics context to draw to
	 * @param cx the x coordinate of the center
	 * @param cy the y coordinate of the center
	 * @param w  the width of the rectangle
	 * @param h  the height of the rectangle
	 */
	private void renderSquareCentered(GraphicsContext g, double cx, double cy, double w, double h) {
		g.fillRect(cx - w / 2, cy - h / 2, w, h);
	}
	
	/**
	 * Reuest the user to enter a player name and save the player's score.
	 */
	private void getNameAndSaveScore() {
		//Cerate a text input dialog
		TextInputDialog nameDialog = new TextInputDialog ("FOO");
		nameDialog.setTitle("Name");
		nameDialog.setHeaderText("Enter player name");
		nameDialog.setContentText("Please enter your name");
		
		//Get the result of opening the dialog
		Optional<String> result = nameDialog.showAndWait();
		
		//If the player entered a name, save his score, otherwise discard it
		if (result.isPresent()){
			this.businessMediator.saveScore(result.get());
		}
	}
			
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Read compass image
		compassImg = new Image(getClass().getResourceAsStream("compass.png"));
	}

}
