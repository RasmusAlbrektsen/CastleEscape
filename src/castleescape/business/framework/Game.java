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
import castleescape.business.ViewUtil;
import castleescape.business.event.QuitEventExecuter;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.InspectableObjectRegister;
import castleescape.data.DataMediator;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

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
	 * The data mediator used to communicate with the data layer.
	 */
	private final DataMediator dataMediator;
	
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
	 * List of possible player characters.
	 */
	private final List<Character> possibleCharacters;

	/**
	 * The player character in the game.
	 */
	private Character player;

	/**
	 * The score manager in the game.
	 */
	private final ScoreManager scoreManager;

	/**
	 * Boolean keeping track of whether the game is running.
	 */
	private boolean running;

	/**
	 * Constructs a new game object to play the specified level.
	 * <p>
	 * To start the game, call the {@link #play()} method after the game object
	 * has been successfully constructed.
	 * 
	 * @param levelName the name of the level to play
	 */
	public Game(String levelName) {
		//Construct data mediator for performing operations on files
		dataMediator = new DataMediator();

		//Load the level with the specified name
		//TODO: Make better
		dataMediator.readLevel("xml/" + levelName);
		
		//Initialize inspectable objects and items
		List<InspectableObject> inspectableObjects = dataMediator.getInspectableObjects();
		inspectableObjects.addAll(dataMediator.getItems());
		
		for (InspectableObject o: inspectableObjects) {
			InspectableObjectRegister.registerInspectableObject(o);
		}
		
		//Initialize rooms
		roomMap = new HashMap<>();
		
		for (Room r: dataMediator.getRooms()) {
			roomMap.put(r.getRoomName(), r);
		}
		
		//Initialize configurations and set start room
		Configuration configuration = dataMediator.getConfiguration();
		currentRoom = configuration.getStartRoom();
		
		//Initialize monster
		monster = new Monster(configuration.getMonsterStartRoom(),
				configuration.getSafeRoom(),
				configuration.getMonsterMoveChance(),
				configuration.getMonsterMoveTime());

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
		eventExecuters.put(EventWord.QUIT, new QuitEventExecuter());

		//Add possible player characters
		possibleCharacters = new ArrayList<>();
		possibleCharacters.add(new Character("Norman", "Norman who is a normal ninja, that makes less noise but can't carry that much.", 0.2, 2));
		possibleCharacters.add(new Character("Bob", "Bob is a bodybuilder making him capable of carrying a lot if items but he also makes a lot of noise.", 0.8, 6));
		possibleCharacters.add(new Character("Obi", "Obi the obvious is a man that makes a lot of noise, but is capable to carry a medium amount of stuff.", 0.7, 3));
		possibleCharacters.add(new Character("Tim", "Tim is pretty generic, he does not make that much noise and can carry a reasonable number of items.", 0.4, 4));
		possibleCharacters.add(new Character("", "Debug character.", 0, 999));

		//Initialize remaining variables
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

	/**
	 * Get all possible player characters.
	 *
	 * @return all possible player characters
	 */
	public List<Character> getCharacters() {
		return possibleCharacters;
	}

	/**
	 * Set the player character to use in the game.
	 *
	 * @param player the player character to use in the game
	 */
	public void setPlayer(Character player) {
		this.player = player;
	}

	/**
	 * Get the player character in the game.
	 *
	 * @return the player character
	 */
	public Character getPlayer() {
		return player;
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
			ViewUtil.println("GAME OVER");

			//Game over, so we quit
			end();
			return;
		}

		//The player is still alive, so we can process the command
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

		//Notify the monster that a command has been entered.
		monster.notifyOfCommand(this);
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
