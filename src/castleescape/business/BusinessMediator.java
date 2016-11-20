/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business;

import castleescape.business.command.Command;
import castleescape.business.command.CommandWord;
import castleescape.business.framework.Character;
import castleescape.business.framework.Game;
import castleescape.business.object.InspectableObject;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The mediator class for connecting the user interface with the business code.
 * All data exchange is in the form of simple and standard data types to ensure
 * that no part of the presentation layer is dependent on model representations
 * in the business layer. This requires a fair bit of mapping, which is also
 * performed by this class.
 *
 * @author Kasper
 */
public class BusinessMediator {

	/**
	 * The game instance.
	 */
	private Game game;

	/**
	 * Constructs a new mediator for connecting the user interface with the
	 * business code.
	 */
	public BusinessMediator() {
		
	}

	/* Methods for notifying the business layer of the state of execution */
	/**
	 * Notify the game that it should start playing the level with the specified name.
	 * 
	 * @param levelName the name of the level to play
	 */
	public void start(String levelName) {
		game = new Game(levelName);
		game.start();
	}

	/**
	 * Notify the game that it should end.
	 */
	public void end() {
		//TODO: Quit method
	}

	/* Getters for retrieving game data from the business layer */
	/**
	 * Get the exit directions from the current room.
	 *
	 * @return the exit directions from the current room.
	 */
	public List<String> getExitDirections() {
		//The keyset of the exits map specifies the direction strings of the
		//exits. The contents of this KeySet are put in an array list to be
		//returned.
		return new ArrayList(game.getCurrentRoom().getExits().keySet());
	}

	/**
	 * Get the items in the current room.
	 *
	 * @return the items in the current room
	 */
	public List<String> getRoomItems() {
		//Construct array list to store the item names
		List<String> itemList = new ArrayList<>();

		//Loop over all items in the current room's inventory and save their
		//names in the array list constructed above
		for (int i = 0; i < game.getCurrentRoom().getInventory().getItemCount(); i++) {
			itemList.add(game.getCurrentRoom().getInventory().getItemByIndex(i).getName());
		}

		return itemList;
	}

	/**
	 * Get the inspectable objects in the current room.
	 *
	 * @return the inspectable objects in the current room
	 */
	public List<String> getRoomObjects() {
		//Construct array list to store the inspectable object names
		List<String> objectList = new ArrayList<>();

		//Loop over all inspectable objects in the current room and save their
		//names in the array list constructed above
		for (InspectableObject object : game.getCurrentRoom().getInspectableObjects()) {
			objectList.add(object.getName());
		}

		return objectList;
	}

	/**
	 * Get the items in the player's inventory.
	 *
	 * @return the items in the player's inventory
	 */
	public List<String> getPlayerItems() {
		//Construct array list to store the item names
		List<String> itemList = new ArrayList<>();

		//Loop over all items in the player's inventory and save their names in
		//the array list constructed above
		for (int i = 0; i < game.getPlayer().getInventory().getItemCount(); i++) {
			itemList.add(game.getPlayer().getInventory().getItemByIndex(i).getName());
		}

		return itemList;
	}

	/**
	 * Get the player's current score.
	 *
	 * @return the player's current score
	 */
	public int getCurrentScore() {
		return game.getScoreManager().getCurrentGameScore();
	}

	/**
	 * Get the text output that was generated on the last game iteration. This
	 * will clear the text output buffer.
	 *
	 * @return the buffered text output
	 */
	public String getTextOutput() {
		return ViewUtil.getString();
	}

	/**
	 * Request all possible player characters from the game along with their
	 * descriptions as a map, where the key is the character name and the value
	 * is the description of that character.
	 *
	 * @return all possible player characters along with their descriptions
	 */
	public Map<String, String> getCharacterList() {
		//Construct map to store character information
		Map<String, String> characterMap = new HashMap<>();

		//Loop over all possible characters and store their names and
		//descriptions in the map constructed above
		for (Character character : game.getCharacters()) {
			characterMap.put(character.getName(), character.getDescription());
		}

		return characterMap;
	}

	/* Methods for notifying the business layer that an action was performed */
	/**
	 * Notify the business layer that the user attempted to take the specified
	 * item.
	 *
	 * @param toTake the name of the item that the user atempted to take
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyTake(String toTake) {
		//Construct map to store character information
		Map<String, String> params = new HashMap<>();

		//Add parameter describing which item whould be taken
		params.put(Command.ITEM, toTake);

		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.TAKE, params);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the business layer that the user attempted to drop the specified
	 * item.
	 *
	 * @param toDrop the name of the item that the user attempted to drop
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyDrop(String toDrop) {
		//Construct map to store command parameters
		Map<String, String> params = new HashMap<>();

		//Add parameter describing which item whould be dropped
		params.put(Command.ITEM, toDrop);

		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.DROP, params);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the business layer that the user attempted to inspect the
	 * specified inspectable object.
	 *
	 * @param toInspect the name of the inspectable object that the user
	 *                  attempted to inspect
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyInspect(String toInspect) {
		//Construct map to store command parameters
		Map<String, String> params = new HashMap<>();

		//Add parameter describing which inspectable object whould be inspected
		params.put(Command.OBJECT, toInspect);

		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.INSPECT, params);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the business layer that the user attempted to use the specified
	 * item on the specified inspectable object.
	 *
	 * @param useItem the name of the item that the user attempted to use
	 * @param useOn   the name of the inspectable object that the user attempted
	 *                to use the item on
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyUse(String useItem, String useOn) {
		//Construct map to store command parameters
		Map<String, String> params = new HashMap<>();

		//Add parameter describing which item whould be used
		params.put(Command.ITEM, useItem);

		//Add parameter describing which inspectable object the item should be
		//used on
		params.put(Command.OBJECT, useOn);

		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.USE, params);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the business layer that the user attempted to peek in the
	 * specified direction.
	 *
	 * @param direction the direction that the user attempted to peek in
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyPeek(String direction) {
		//Construct map to store command parameters
		Map<String, String> params = new HashMap<>();

		//Add parameter describing which direction to peek in
		params.put(Command.DIRECTION, direction);

		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.PEEK, params);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the business layer that the user attempted to walk in the
	 * specified direction.
	 *
	 * @param direction the direction that the user attempted to walk in
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyGo(String direction) {
		//Construct map to store command parameters
		Map<String, String> params = new HashMap<>();

		//Add parameter describing which direction to go in
		params.put(Command.DIRECTION, direction);

		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.GO, params);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the business layer that the user asked for help.
	 *
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyHelp() {
		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.HELP, null);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the business layer that the user wishes to view the inventory.
	 *
	 * @return true if the game is still running, false otherwise
	 */
	public boolean notifyInventory() {
		//Construct command object and request that the game processes it
		Command command = new Command(CommandWord.INVENTORY, null);
		game.processCommand(command);

		//Return whether the game is still running
		return game.isRunning();
	}

	/**
	 * Notify the game that the user selected a player character.
	 *
	 * @param choice the name of the chosen player character
	 * @throws IllegalArgumentException if no such character exists
	 */
	public void notifyCharacterSelected(String choice) {
		//Loop over all possible player characters until a match with the user
		//choice is found
		for (Character character : game.getCharacters()) {
			if (character.getName().equals(choice)) {

				//Once a match is found, set the player character and return
				game.setPlayer(character);
				return;
			}
		}

		//No match found, so the user somehow entered an illegal choice, so we
		//throw an exception
		throw new IllegalArgumentException("No such player character: " + choice);
	}

	/**
	 * Save the current score under the specified name.
	 *
	 * @param name the name of the player
	 */
	public void saveScore(String name) {
		game.saveScore(name);
	}
}
