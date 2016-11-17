package castleescape.business.framework;

import castleescape.business.event.EventExecuter;
import castleescape.business.event.AddExitEventExecuter;
import castleescape.business.event.EventWord;
import castleescape.business.event.AddRoomItemEventExecuter;
import castleescape.business.event.RemovePlayerItemEventExecuter;
import castleescape.business.event.SetDescriptionEventExecuter;
import castleescape.business.event.MakeNoiseEventExecuter;
import castleescape.business.event.RemoveRoomItemEventExecuter;
import castleescape.business.event.AddPlayerItemEventExecuter;
import castleescape.business.command.Command;
import castleescape.business.command.QuitCommandExecuter;
import castleescape.business.command.InventoryCommandExecuter;
import castleescape.business.command.TakeCommandExecuter;
import castleescape.business.command.HelpCommandExecuter;
import castleescape.business.command.CommandWord;
import castleescape.business.command.GoCommandExecuter;
import castleescape.business.command.UseCommandExecuter;
import castleescape.business.command.DropCommandExecuter;
import castleescape.business.command.PeekCommandExecuter;
import castleescape.business.command.InspectCommandExecuter;
import castleescape.business.command.CommandExecuter;
import castleescape.business.Configurations;
import castleescape.business.ViewUtil;
import castleescape.business.object.InspectableObjectRegister;
import util.XMLRoomExitBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class defining instance behavior for setting up and running a game. This
 * includes creating the game {@link Room rooms}, processing commands, changing
 * rooms, initiating and running the game loop and quitting the game. It also
 * defines instance methods for priting a welcome message and help information.
 *
 * @see <a href="https://codeshare.io/vRDTN">Codeshare</a>
 */
public class Game {

	/**
	 * Map of command executers. The keys are CommandWord objects and the values
	 * are the CommandExecuters associated with these CommandWord objects.
	 */
	private final HashMap<CommandWord, CommandExecuter> commandExecuters;

	/**
	 * Map of event executers. The keys are EventWord objects and the values are
	 * the Event Executers associated with these EventWord objects.
	 */
	private final HashMap<EventWord, EventExecuter> eventExecuters;

	/**
	 * Map of rooms in the game. The keys are room names and the values are the
	 * rooms with these names.
	 */
	private final HashMap<String, Room> roomMap;

	/**
	 * The room that we are currently in.
	 */
	private Room currentRoom;

	/**
	 * The monster in the game.
	 */
	private final Monster monster;

	/**
	 * The player character in the game.
	 */
	private Character player;

	/**
	 * The score manager in the game.
	 */
	private ScoreManager scoreManager;

	/**
	 * Boolean keeping track of whether the game is running.
	 */
	private boolean running;

	/**
	 * Constructs a new game object. This constructor will call the method
	 * {@link XMLRoomExitBuilder#getRooms()} to initialize all rooms in the game
	 * and constructs a {@link Parser} for reading user input.
	 * <p>
	 * To start the game, call the {@link #play()} method after the game object
	 * has been successfully constructed.
	 */
	public Game() {
		//Initialize rooms HashMap
		roomMap = XMLRoomExitBuilder.getRooms();
		currentRoom = roomMap.get(Configurations.getStartRoomName());

		//Create a player character
		//Add command executers and associate them with command words
		commandExecuters = new HashMap<>();
		commandExecuters.put(CommandWord.HELP, new HelpCommandExecuter());
		commandExecuters.put(CommandWord.GO, new GoCommandExecuter());
		commandExecuters.put(CommandWord.TAKE, new TakeCommandExecuter());
		commandExecuters.put(CommandWord.DROP, new DropCommandExecuter());
		commandExecuters.put(CommandWord.INSPECT, new InspectCommandExecuter());
		commandExecuters.put(CommandWord.INVENTORY, new InventoryCommandExecuter());
		commandExecuters.put(CommandWord.USE, new UseCommandExecuter());
		commandExecuters.put(CommandWord.QUIT, new QuitCommandExecuter());
		commandExecuters.put(CommandWord.PEEK, new PeekCommandExecuter());

		//Add event executers and associate them with event words
		eventExecuters = new HashMap<>();
		eventExecuters.put(EventWord.ADD_EXIT, new AddExitEventExecuter());
		eventExecuters.put(EventWord.ADD_PLAYER_ITEM, new AddPlayerItemEventExecuter());
		eventExecuters.put(EventWord.ADD_ROOM_ITEM, new AddRoomItemEventExecuter());
		eventExecuters.put(EventWord.MAKE_NOISE, new MakeNoiseEventExecuter());
		eventExecuters.put(EventWord.SET_DESCRIPTION, new SetDescriptionEventExecuter());
		eventExecuters.put(EventWord.REMOVE_PLAYER_ITEM, new RemovePlayerItemEventExecuter());
		eventExecuters.put(EventWord.REMOVE_ROOM_ITEM, new RemoveRoomItemEventExecuter());

		//Initialize remaining variables
		monster = new Monster(roomMap.get(Configurations.getMonsterStartRoomName()));
		scoreManager = new ScoreManager();
	}

	/**
	 * Get the monster in the game.
	 *
	 * @return the monster
	 */
	public Monster getMonster() {
		return monster;
	}

	public Character[] getCharacters() {
		return new Character[]{
			new Character(0.2, 2, "Norman", "Norman who is a normal ninja, that makes less noise but can't carry that much"),
			new Character(0.8, 6, "Bob", "Bob is a bodybuilder making him capable of carrying a lot if items but he also makes a lot of noise"),
			new Character(0.7, 3, "Obi", "Obi the obvious is a man that makes a lot of noise, but is capable to carry a medium amount of stuff"),
			new Character(0.4, 4, "Tim", "Tim is pretty generic, he does not make that much noise and can carry a reasonable number of items"),
			new Character(0, 999, "", "")};
	}

	/**
	 * Get the player character in the game.
	 *
	 * @return the player
	 */
	public Character getPlayer() {
		return player;
	}

	public void setPlayer(Character player) {
		this.player = player;
	}

	/**
	 * Get the score manager in the game. The score manager is responsible for
	 * keeping track of the player's current score.
	 *
	 * @return the score manager
	 */
	public ScoreManager getScoreManager() {
		return scoreManager;
	}

	/**
	 * Get the command executer associated with the specified command word.
	 *
	 * @param commandWord the command word that the requested command executer
	 *                    is associated with
	 * @return the command executer associated with the specified command word
	 */
	public CommandExecuter getCommandExecuter(CommandWord commandWord) {
		return commandExecuters.get(commandWord);
	}

	/**
	 * Get the event executer associated with the specified event word.
	 *
	 * @param eventWord the event word that the requested event executer is
	 *                  associated with
	 * @return the event executer associated with the specified event word
	 */
	public EventExecuter getEventExecuter(EventWord eventWord) {
		return eventExecuters.get(eventWord);
	}

	/**
	 * Start playing the game. This method will print the welcome message along
	 * with the description of the first room given by
	 * {@link Room#getLongDescription()}.
	 */
	public void start() {
		//Set the game as running
		running = true;

		//Print out game details
		ViewUtil.newLine();
		ViewUtil.println("Welcome to Castle Escape!");
		ViewUtil.println("Castle Escape is a game where the objective is to escape a castle. Wow.");

		//Printing out CommandWord.HELP will replace it with the return value of
		//its toString() method, which is the string representation of the
		//command word
		ViewUtil.println("Press 'Help' if you need help.");
		ViewUtil.newLine();

		//Print the long description of the current room, that is the starting
		//room
		ViewUtil.println(currentRoom.getLongDescription());
	}

	/**
	 * Process the specified {@link Command} to carry out the action associated
	 * with it. Thisa method will also check whether the player has been caught,
	 * as such an action can only happen when the player executes a command.
	 *
	 * @param command the command to process
	 */
	public void processCommand(Command command) {
		//If the player is caught by the monster, game over
		if (monster.isPlayerCaught()) {
			ViewUtil.println("The monster caught you and shredded you to pieces!");
			ViewUtil.println("\t\tGAME OVER");

			//Game over, so we quit
			end();
			return;
		}

		//The player is still alive, so we can process the command
		//Notify the monster that a command has been entered.
		monster.notifyOfCommand(this);

		//Get the command executer associated with the specified CommandWord
		//object
		CommandExecuter executer = commandExecuters.get(command.getCommandWord());

		//If no such command executer was found that means the command word is
		//unknown
		if (executer == null) {
			ViewUtil.println("I don't know what you mean.");
			return;
		}

		//At this point executer is able to execute the specified command
		executer.execute(this, command);
	}

	/**
	 * Notify the game that it should end.
	 */
	public void end() {
		running = false;
	}

	/**
	 * Test whether the game is currently running. If not, that means it either
	 * has not been {@link #start() started yet} or the player has finished
	 * playing.
	 *
	 * @return true if the game is running, false otherwise
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Save the player's score using the specified player name.
	 * 
	 * @param name the name of the player
	 */
	public void saveScore(String name) {
		scoreManager.recordCurrentGameScore(name);
		scoreManager.reset();
	}

	/**
	 * Add a room to the game.
	 *
	 * @param room the room to add
	 */
	public void addRoom(Room room) {
		roomMap.put(room.getRoomName(), room);
	}

	/**
	 * Get the room that the player is currently in.
	 *
	 * @return the room that the player is currently in
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Set the room that the character is in.
	 *
	 * @param room the room to move the character to
	 */
	public void setRoom(Room room) {
		currentRoom = room;
	}

	/**
	 * Get the room with the specified name.
	 *
	 * @param name the name of the room
	 * @return the room with the specified name, or null if no such room exists
	 */
	public Room getRoom(String name) {
		return roomMap.get(name);
	}
}
