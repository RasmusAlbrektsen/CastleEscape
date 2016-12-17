/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;
import castleescape.shared.GameListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

/**
 * Controller class for the game GUI view. This class is also responsible for
 * getting initial user input required to start the game, such as level and
 * character selection.
 */
public class GameGuiController implements Initializable, GameListener {

	/**
	 * Images for rendering the map part of the GUI.
	 */
	private Image compassImg,
			roomImg,
			horizontalDoorImg,
			verticalDoorImg;

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
	@FXML
	private Button highscoreButton;
	@FXML
	private Button quitButton;

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

	/* Labels */
	@FXML
	private Label scoreLabel;

	/**
	 * Called when the north button is pressed.
	 */
	@FXML
	private void onNorthButtonAction() {
		businessMediator.notifyGo("north");
	}

	/**
	 * Called when the south button is pressed.
	 */
	@FXML
	private void onSouthButtonAction() {
		businessMediator.notifyGo("south");
	}

	/**
	 * Called when the east button is pressed.
	 */
	@FXML
	private void onEastButtonAction() {
		businessMediator.notifyGo("east");
	}

	/**
	 * Called when the west button is pressed.
	 */
	@FXML
	private void onWestButtonAction() {
		businessMediator.notifyGo("west");
	}

	/**
	 * Called when the take button is pressed.
	 */
	@FXML
	private void onTakeButtonAction() {
		businessMediator.notifyTake(roomContentDropDown.getValue());
	}

	/**
	 * Called when the drop button is pressed.
	 */
	@FXML
	private void onDropButtonAction() {
		businessMediator.notifyDrop(inventoryDropDown.getValue());
	}

	/**
	 * Called when the use button is pressed.
	 */
	@FXML
	private void onUseButtonAction() {
		businessMediator.notifyUse(
				inventoryDropDown.getValue(),
				roomContentDropDown.getValue());
	}

	/**
	 * Called when the inspect button is pressed.
	 */
	@FXML
	private void onInspectButtonAction() {
		String inventorySelection = inventoryDropDown.getValue();
		String roomSelection = roomContentDropDown.getValue();

		if (inventorySelection != null) {
			businessMediator.notifyInspect(inventorySelection);
		} else {
			businessMediator.notifyInspect(roomSelection);
		}
	}

	/**
	 * Called when the inventory button is pressed.
	 */
	@FXML
	private void onInventoryButtonAction() {
		businessMediator.notifyInventory();
	}

	/**
	 * Called when the help button is pressed.
	 */
	@FXML
	private void onHelpButtonAction() {
		businessMediator.notifyHelp();
	}

	/**
	 * Called when the peek button is pressed.
	 */
	@FXML
	private void onPeekButtonAction() {
		businessMediator.notifyPeek(roomDropDown.getValue());
	}

	/**
	 * Called when the highscore button is pressed.
	 */
	@FXML
	private void onHighscoreButtonAction() {
		businessMediator.notifyHighscores();
	}
	
	/**
	 * Called when the quit button is pressed.
	 */
	@FXML
	private void onQuitButtonAction() {
		businessMediator.end();
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
	 * Start a new game. This method can be called at any time to start a new
	 * game.
	 */
	public void startGame() {
		//Initialize the new game
		attemptGameInitialization();

		//Subscribe to game events
		businessMediator.setGameListener(this);

		//Start the game
		businessMediator.start();
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
	 * Synchronize the display of the room exits, player and room inventories,
	 * and score with the actual data in the game.
	 */
	private void updateGameDataDisplay() {
		//Update player inventory display
		//Get the list of items in the player's inventory
		List<String> playerItems = businessMediator.getPlayerItems();
		playerItems.add(0, null); //We add a null element to allow deselection

		//Remember the current selection in the choice box, as this will be
		//reset when we repopulate it, even if the selected item persists
		String item = inventoryDropDown.getValue();

		//Repopulate the choice box. This requires our list to be wrapped in an
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
		roomContent.add(0, null);
		item = roomContentDropDown.getValue();
		roomContentDropDown.setItems(FXCollections.observableArrayList(roomContent));

		if (roomContent.contains(item)) {
			roomContentDropDown.setValue(item);
		}

		//Update exits
		List<String> exitDirections = businessMediator.getExitDirections();
		exitDirections.add(0, null);
		item = roomDropDown.getValue();
		roomDropDown.setItems(FXCollections.observableArrayList(exitDirections));

		if (exitDirections.contains(item)) {
			roomDropDown.setValue(item);
		}

		//Update score label
		scoreLabel.setText(String.valueOf(businessMediator.getCurrentScore()));

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
		renderImageCentered(g, roomImg, cx, cy);

		//Get the exits from the current room. A connection to all neighbor
		//rooms should be drawn
		List<String> exitDirections = businessMediator.getExitDirections();

		//For every neighbor, figure out in which direction it is located
		//relative to the current room. For instance, a room to the north is
		//offset along the y axis in the negative direction (thus dx = 0 and
		//dy = -1), and a room to the east is offset along the x axis in the
		//positive direction (thus dx = 1 and dy = 0).
		for (String direction : exitDirections) {
			int dx = 0;
			int dy = 0;

			//The key is the direction in which the neighbor is located
			switch (direction) {
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

			//Render the neighbor room just besides the current room, offset in
			//the direction specified in the switch statement above
			renderImageCentered(g, roomImg,
					cx + roomImg.getWidth() * dx,
					cy + roomImg.getHeight() * dy);

			//Render connector doors between the rooms. If the offset happened
			//on the x axis (dx != 0) then render the horizontal room connector,
			//otherwise assume the offset was vertical and render the vertical
			//connector. The connector should be rendered right between the two
			//rooms, thus its center is half a width / height from the current
			//center (cx, cy)
			if (dx != 0) {
				renderImageCentered(g, horizontalDoorImg,
						cx + roomImg.getWidth() * dx / 2,
						cy);
			} else {
				renderImageCentered(g, verticalDoorImg,
						cx,
						cy + roomImg.getHeight() * dy / 2);
			}
		}

		//We do not want the anything we rendered to exceed the bounds of the
		//compass image, so we clear all pixels that are further than r pixels
		//away from the center of the canvas, where r is the radius of the
		//compass minus a few pixels to account for the edge of the compass to
		//be anti-aliased
		int halfWidth = (int) cx;
		int halfHeight = (int) cy;

		for (int y = -halfHeight; y < halfHeight; y++) {
			for (int x = -halfWidth; x < halfWidth; x++) {
				if (x * x + y * y >= (halfWidth - 14) * (halfHeight - 14)) {
					g.getPixelWriter().setArgb(x + halfWidth, y + halfHeight, 0);
				}
			}
		}

		//Draw the compass image
		renderImageCentered(g, compassImg, cx, cy);
	}

	/**
	 * Draw the specified image in the specified graphics context centered on
	 * the specified coordinate (cx, cy).
	 *
	 * @param g   the graphics context to draw to
	 * @param img the image to draw
	 * @param cx  the x coordinate of the center
	 * @param cy  the y coordinate of the center
	 */
	private void renderImageCentered(GraphicsContext g, Image img, double cx, double cy) {
		g.drawImage(img, cx - img.getWidth() / 2, cy - img.getHeight() / 2);
	}

	/**
	 * Request the user to enter a player name and save the player's score.
	 */
	private void getNameAndSaveScore() {
		//Create a text input dialog
		TextInputDialog nameDialog = new TextInputDialog("FOO");
		nameDialog.setTitle("Name");
		nameDialog.setHeaderText("Enter player name");
		nameDialog.setContentText("Please enter your name");

		//Get the result of opening the dialog
		Optional<String> result = nameDialog.showAndWait();

		//If the player entered a name, save his score, otherwise discard it
		if (result.isPresent()) {
			this.businessMediator.saveScore(result.get());
		}
	}

	/**
	 * Ask the user which level he/she wants to play and return the name of it.
	 * If the return value is null, that means the user did not want to play any
	 * level and the game should quit.
	 *
	 * @return the name of the level that the user wishes to play, or null if
	 *         the user did not choose a level
	 */
	private String getLevelNameFromUser() {
		//Get the available levels
		String[] levelNames = businessMediator.getLevels();

		//Create choice dialog for the user to select the level he/she wants to
		//play
		ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(levelNames[0], levelNames);
		choiceDialog.setHeaderText("Wich level would you like to play?");
		choiceDialog.setTitle("Level selection");

		//Get the result of opening the dialog
		Optional<String> result = choiceDialog.showAndWait();

		//Return result. We use a ternary operator because result.get() will
		//throw an exception if no result was present. The ternary operator
		//reads:
		//if (result.isPresent()) return result.get(); else return null;
		return (result.isPresent() ? result.get() : null);
	}

	/**
	 * Ask the user which character he/she wants to play as and return the name
	 * of it. If the return value is null, that means the user did not want to
	 * play any character and the game should quit.
	 *
	 * @return the name of the character that the user wishes to play, or null
	 *         if the user did not choose a character
	 */
	private String getCharacterNameFromUser() {
		//Get the available characters and their descriptions
		Map<String, String> characters = businessMediator.getCharacterList();

		//Create array of character names from the map above
		String[] characterNames = new ArrayList<>(characters.keySet()).toArray(new String[0]);

		//Create choice dialog for the user to select the charatcer he/she wants
		//to play
		ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(characterNames[0], characterNames);
		choiceDialog.setHeaderText("Wich character would you like to play as?");
		choiceDialog.setTitle("Character selection");

		//We also want to display the character's description, so we create a
		//new label control to display the character's description. To begin
		//with, this is the description of the 0th character
		Label descriptionLabel = new Label(characters.get(characterNames[0]));

		//The 'expendable content' of the choice dialog is set to our label
		choiceDialog.getDialogPane().setExpandableContent(descriptionLabel);

		//We add a listener to the current selection in the choice dialog
		choiceDialog.selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//When we receive an event that the user has made a new
				//selection we update the label's text to the character's
				//description from the character map
				descriptionLabel.setText(characters.get(newValue));
				
				//Resize the dialog box
				choiceDialog.getDialogPane().getScene().getWindow().sizeToScene();
			}
		});

		//Get the result of opening the dialog
		Optional<String> result = choiceDialog.showAndWait();

		//Return result. We use a ternary operator because result.get() will
		//throw an exception if no result was present. The ternary operator
		//reads:
		//if (result.isPresent()) return result.get(); else return null;
		return (result.isPresent() ? result.get() : null);
	}

	/**
	 * Get the level and character that the user wants to play and construct a
	 * new game from this information. If the user dismisses these dialogs, the
	 * game will quit before it even has a chance to start.
	 */
	private void attemptGameInitialization() {
		//Get level name
		String levelName = getLevelNameFromUser();

		//If level name is non-null, initialize a new game with this level
		if (levelName != null) {
			businessMediator.initialize(levelName);
		} else {
			//Else, close the application
			Platform.exit();
			System.exit(0);
		}

		//Get character name
		String characterName = getCharacterNameFromUser();

		//If character name is non-null, set the character for the new game
		if (characterName != null) {
			businessMediator.notifyCharacterSelected(characterName);
		} else {
			//Else, close the application
			Platform.exit();
			System.exit(0);
		}
	}

	/* Event received from the business layer */
	@Override
	public void onGameStart(String output) {
		//Write the text that was generated on game start
		writeToConsole(output);

		//Update data display, to show the game's initial state
		updateGameDataDisplay();
	}

	@Override
	public void onGameExit() {
		//If the game is no longer running, get the players score
		this.getNameAndSaveScore();

		//Try to start a new game, or quit if the user wishes to
		startGame();
	}

	@Override
	public void onGameIteration(String output) {
		//Write the text that was generated on this iteration to the console
		writeToConsole(output);

		//Update data display, as something might have changed now
		updateGameDataDisplay();
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Read image resources from the classpath
		compassImg = new Image(getClass().getResourceAsStream("/res/compassBig.png"));
		roomImg = new Image(getClass().getResourceAsStream("/res/room.png"));
		horizontalDoorImg = new Image(getClass().getResourceAsStream("/res/roomDoorHorizontal.png"));
		verticalDoorImg = new Image(getClass().getResourceAsStream("/res/roomDoorVertical.png"));
	}
}
