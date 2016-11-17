/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
		//Get the source of the button action
		Object source = event.getSource();
		
		//By now, we assume that the game is still running.
		boolean running = true;

		//Carry out the operations associated with the pressed button. These
		//methods return booleans indicating whether the game has ended, which
		//we use to update the value of running
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

		//Write the text that was generated on this iteration to the console
		writeToConsole(businessMediator.getTextOutput());

		//Update data display, as something might have changed now
		updateGameDataDisplay();

		//If the game is no longer running, get the players score and quit
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
		List<String> playerItems = businessMediator.getPlayerItems();
		System.out.println(Arrays.toString(playerItems.toArray()));
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

		//Start the game using the business mediator and print the result to the
		//GUI console
		String msg = businessMediator.start();
		writeToConsole(msg);
		
		//Initialize the display of all game data now that the game has been
		//properly initialized
		updateGameDataDisplay();
	}

	/**
	 * Write the specified string in the console. The string should be formatted
	 * in HTML.
	 *
	 * @param s the string to write to the console
	 */
	private void writeToConsole(String s) {
		//Convert string s to an html string with a stylesheet defined in the
		//header, so that styling can take place elsewhere
		String contentString
				= "<html>"
				+ "<head>"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ getClass().getResource("consoleview.css")
				+ "\"/>"
				+ "</head>"
				+ "<body>"
				+ "<div class=\"webview\">"
				+ s
				+ "</div>"
				+ "</body>"
				+ "</html>";

		//Load the html string into the web view
		console.getEngine().loadContent(contentString);
	}

	/**
	 * Synchronize the display of the room exits and player and room inventories
	 * with the actual data in the game.
	 */
	private void updateGameDataDisplay() {
		//Update player inventory display
		//Get the list of items in the player's inventory
		List<String> playerItems = businessMediator.getPlayerItems();
		
		//Remember the current selection in the choice box, as this will be
		//reset when we repopulate it, even if the selected item persists
		String item = inventoryDropDown.getValue();
		
		//Repopulate the choice box. This requires out list to be wrapped in an
		//observable array list
		inventoryDropDown.setItems(FXCollections.observableArrayList(playerItems));
		
		//If the previously selected item persisted, select it again
		if (playerItems.contains(item)) {
			inventoryDropDown.setValue(item);
		}
		
		//Now do the same exact thing for room content and room exits
		//Update room inventory and content display
		List<String> roomContent = businessMediator.getRoomItems();
		roomContent.addAll(businessMediator.getRoomObjects());
		item = roomContentDropDown.getValue();
		roomContentDropDown.setItems(FXCollections.observableArrayList(roomContent));
		
		if (roomContent.contains(item)) {
			roomContentDropDown.setValue(item);
		}

		//Update exits
		Set<String> exitDirections = businessMediator.getCurrentExits().keySet();
		item = roomDropDown.getValue();
		roomDropDown.setItems(FXCollections.observableArrayList(exitDirections));
		
		if (exitDirections.contains(item)) {
			roomDropDown.setValue(item);
		}
		
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
		
		//Render a translucent background behind the compass - to make it look
		//cool
		g.setFill(new Color(0, 0, 0, 0.5));
		g.fillRect(0, 0, compass.getWidth(), compass.getHeight());

		//Calculate the center of the canvas, which is where we want to render
		//the current room
		double cx = compass.getWidth() / 2;
		double cy = compass.getHeight() / 2;

		//Render the current room, centered on (cx, cy)
		g.setFill(Color.BLACK);
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

		//We do not want the anything we rendered to exceed the bounds of the
		//compass image, so we clear all pixels that are further than r pixels
		//away from the center of the canvas, where r is the radius of the
		//compass minus a few pixels to account for the edge of the compass to
		//be anti-aliased
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
	 * Request the user to enter a player name and save the player's score.
	 * After this the application will quit.
	 */
	private void getNameAndSaveScore() {
		//Create a text input dialog
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
		
		//Quit the game after the player's score has been set. A static call is
		//fine, as System.exit() and Platform.exit() are static calls as well
		CastleEscape.quit();
	}
			
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Read compass image
		compassImg = new Image(getClass().getResourceAsStream("compass02.png"));
	}

}
