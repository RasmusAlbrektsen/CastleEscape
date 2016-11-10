package castleescape.business.command;

import castleescape.business.ViewUtil;

/**
 * An enum defining all command words known in this framework.
 */
public enum CommandWord {
	GO("go", "go <direction>\nDirection is either 'north', 'south', 'east' or 'west'"),
	QUIT("quit", "quit\nQuit takes no parameters"),
	HELP("help", "Seriously?"),
	HINT("hint", "hint\nHint takes no parameters"),
	INSPECT("inspect", "inspect <object or item>\nThe object or item must be in either the player inventory or the current room"),
	DROP("drop", "drop <item>\nThe item must be in the player inventory"),
	TAKE("take", "take <item>\nThe item must be in the current room"),
	USE("use", "use <item> on <object or item>\nThe first item must be in the player inventory, while the second object or item must be in either the player inventory or the current room"),
	INVENTORY("inventory", "inventory\nInventory takes no parameters"),
	PEEK("peek", "peek <direction>"),
	UNKNOWN("?", "Unknown command");
	
	/**
	 * The representation of a command word as a string.
	 */
	private final String commandString;

	/**
	 * The description of how to use this command.
	 */
	private final String commandDescription;

	/**
	 * Private constructor for new command words. All new command word objects
	 * must specify a string representation.
	 *
	 * @param commandString      the string representation of this command word
	 * @param commandDescription a description of how to use this command
	 */
	CommandWord(String commandString, String commandDescription) {
		this.commandString = commandString;
		this.commandDescription = commandDescription;
	}

	/**
	 * Get a description of how to use this command word.
	 *
	 * @return a description of how to use this command word
	 */
	public String getDescription() {
		return commandDescription;
	}

	/**
	 * Get the string representation of this command word object. This will
	 * return the string representation specified in the object's constructor.
	 *
	 * @return the string representation of this command word object
	 */
	@Override
	public String toString() {
		return commandString;
	}

	/**
	 * Get the {@link CommandWord} object associated with the specified command
	 * string. If the string is not recognized as a command or is null, this
	 * method will return {@link CommandWord#UNKNOWN}.
	 *
	 * @param commandWord the string representation of a command
	 * @return the {@link CommandWord} equivalent to the specified command
	 *         string, or {@link CommandWord#UNKNOWN} if the command string was
	 *         not recognized
	 */
	public static CommandWord getCommandWord(String commandWord) {
		//Go through all command words
		for (CommandWord word : values()) {

			//If the commandWord string equals the string representation of the
			//current command word, then return it, because we found a match.
			//Notice that if the commandWord string is "?", then
			//CommandWord.UNKNOWN will be returned here, but that is fine, as
			//"?" is not a valid command
			if (word.toString().equals(commandWord)) {
				return word;
			}
		}

		//No match, just return UNKNOWN
		return CommandWord.UNKNOWN;
	}

	/**
	 * Print out all available commands separated by commas and spaces on a
	 * single line. A newline is appended before this method returns.
	 */
	public static void showAll() {
		//The prefix is an empty string to begin with
		String prefix = "";

		//Loop through all command words
		for (CommandWord word : values()) {
			//If the current command word is UNKNOWN, don't print it
			if (word == CommandWord.UNKNOWN) {
				continue;
			}

			//Print the command words on a single line separated by the prefix.
			//To begin with, the prefix is an empty string. On every other
			//iteration the prefix will be ", ". This will construct an output
			//such as "go, exit, help".
			ViewUtil.print(prefix + word.toString());

			//Change the prefix
			prefix = ", ";
		}

		//Add a newline, since this is not done above
		ViewUtil.newLine();
	}
}
