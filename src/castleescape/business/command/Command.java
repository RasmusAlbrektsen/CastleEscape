package castleescape.business.command;

import castleescape.business.framework.Game;
import java.util.HashMap;
import java.util.Map;

/**
 * Class defining instance behavior for command objects. All commands consist of
 * a command word and multiple possible command parameters, some of which can be
 * null depending on the use of the command.
 */
public class Command {

	/**
	 * String representation of a command parameter.
	 */
	public static final String DIRECTION = "direction",
			ITEM = "item",
			OBJECT = "object";

	/**
	 * The command word.
	 */
	private final CommandWord commandWord;

	/**
	 * Map containing the command parameters.
	 */
	private final Map<String, String> commandParams;

	/**
	 * Constructs a new command object with the specified word and parameters.
	 * If the command has no parameters, {@code null} should be passed instead.
	 *
	 * @param commandWord   the command word as a {@link CommandWord} object
	 * @param commandParams Map containing the parameters associated with this
	 *                      command, or {@code null} if it has no parameters
	 */
	public Command(CommandWord commandWord, Map<String, String> commandParams) {
		this.commandWord = commandWord;

		//If the parameter map is null, create an empty map instead
		this.commandParams = (commandParams == null ? new HashMap<>() : commandParams);
	}

	/**
	 * Get the command word as a {@link CommandWord} object.
	 *
	 * @return the command word
	 */
	public CommandWord getCommandWord() {
		return commandWord;
	}

	/**
	 * Get the command parameter with the specified name.
	 *
	 * @param name the name of the command parameter
	 * @return the command parameter with the specified name
	 */
	public String getCommandParameter(String name) {
		return commandParams.get(name);
	}

	/**
	 * Test if this command object describes an unknown command.
	 *
	 * @return {@code true} if it defines an unknown command, {@code false}
	 *         otherwise
	 */
	public boolean isUnknown() {
		return (commandWord == CommandWord.UNKNOWN);
	}

	/**
	 * Test if this command object has one or more parameters.
	 *
	 * @return {@code true} if it has one or more parameters, {@code false}
	 *         otherwise
	 */
	public boolean hasCommandParameters() {
		return !commandParams.isEmpty();
	}

	/**
	 * Execute this command.
	 *
	 * @param game the game object
	 */
	public void execute(Game game) {
		//Get the command executer to execute this specific command and call its
		//execute() method
		game.getCommandExecuter(commandWord).execute(game, this);
	}
}
