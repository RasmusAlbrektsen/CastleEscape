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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The mediator class for connecting the user interface with the business code.
 *
 * @author Kasper
 */
public class BusinessMediator {

	/**
	 * The game instance.
	 */
	private final Game game;

	/**
	 * Constructs a new mediator for connecting the user interface with the
	 * business code.
	 */
	public BusinessMediator() {
		this.game = new Game();

	}

	/* Methods for notifying the business layer of the state of execution */
	/**
	 * Notify the game that it should start over.
	 *
	 * @return the message to print as a result
	 */
	public String start() {
		game.start();

		return ViewUtil.getString();
	}

	/**
	 * Notify the game that it should quit.
	 *
	 * @return the message to print as a result
	 */
	public String quit() {
		//TODO
		return "TODO: Quit method";
	}

	/* Getters for retrieving game data from the business layer */
	/**
	 * Get the exits from the current room.
	 *
	 * @return the exits from the current room.
	 */
	public Map<String, String> getCurrentExits() {
		return game.getCurrentRoom().getExitView();
	}

	/**
	 * Get the items in the current room.
	 *
	 * @return the items in the current room.
	 */
	public List<String> getRoomItems() {
		return game.getCurrentRoom().getInventory().getContentView();
	}

	/**
	 * Get the inspectable objects in the current room.
	 *
	 * @return the inspectable objects in the current room
	 */
	public List<String> getRoomObjects() {
		return game.getCurrentRoom().getInspectableObjectView();
	}

	/**
	 * Get the items in the player's inventory.
	 *
	 * @return the items in the player's inventory
	 */
	public List<String> getPlayerItems() {
		return game.getPlayer().getInventory().getContentView();
	}

	/* Methods for notifying the business layer that an action was performed */
	/**
	 * Notify the business layer that the user attempted to take the specified
	 * item.
	 *
	 * @param toTake the name of the item that the user atempted to take
	 * @return the message to print as a result
	 */
	public String notifyTake(String toTake) {
		Map<String, String> params = new HashMap<>();

		params.put(Command.ITEM, toTake);

		Command command = new Command(CommandWord.TAKE, params);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the business layer that the user attempted to drop the specified
	 * item.
	 *
	 * @param toDrop the name of the item that the user attempted to drop
	 * @return the message to print as a result
	 */
	public String notifyDrop(String toDrop) {
		Map<String, String> params = new HashMap<>();

		params.put(Command.ITEM, toDrop);

		Command command = new Command(CommandWord.DROP, params);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the business layer that the user attempted to inspect the
	 * specified inspectable object.
	 *
	 * @param toInspect the name of the inspectable object that the user
	 *                  attempted to inspect
	 * @return the message to print as a result
	 */
	public String notifyInspect(String toInspect) {
		Map<String, String> params = new HashMap<>();

		params.put(Command.OBJECT, toInspect);

		Command command = new Command(CommandWord.INSPECT, params);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the business layer that the user attempted to use the specified
	 * item on the specified inspectable object.
	 *
	 * @param useItem the name of the item that the user attempted to use
	 * @param useOn   the name of the inspectable object that the user attempted
	 *                to use the item on
	 * @return the message to print as a result
	 */
	public String notifyUse(String useItem, String useOn) {
		Map<String, String> params = new HashMap<>();

		params.put(Command.ITEM, useItem);
		params.put(Command.OBJECT, useOn);

		Command command = new Command(CommandWord.USE, params);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the business layer that the user attempted to peek in the
	 * specified direction.
	 *
	 * @param direction the direction that the user attempted to peek in
	 * @return the message to print as a result
	 */
	public String notifyPeek(String direction) {
		Map<String, String> params = new HashMap<>();

		params.put(Command.DIRECTION, direction);

		Command command = new Command(CommandWord.PEEK, params);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the business layer that the user attempted to walk in the
	 * specified direction.
	 *
	 * @param direction the direction that the user attempted to walk in
	 * @return the message to print as a result
	 */
	public String notifyGo(String direction) {
		Map<String, String> params = new HashMap<>();

		params.put(Command.DIRECTION, direction);

		Command command = new Command(CommandWord.GO, params);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the business layer that the user asked for help.
	 *
	 * @return the help message to display
	 */
	public String notifyHelp() {
		Command command = new Command(CommandWord.HELP, null);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the business layer that the user wishes to view the inventory.
	 *
	 * @return the help message to display
	 */
	public String notifyInventory() {
		Command command = new Command(CommandWord.INVENTORY, null);
		game.processCommand(command);

		return ViewUtil.getString();
	}

	/**
	 * Notify the game that the user selected a player character.
	 *
	 * @param choice the chosen player character
	 */
	public void notifyCharacterSelected(Character choice) {
		game.setPlayer(choice);
	}

	/**
	 * Request an arry of possible player characters from the game.
	 *
	 * @return an arry of possible player characters from the game
	 */
	public Character[] getCharacterList() {
		return game.getCharacters();
	}
}
