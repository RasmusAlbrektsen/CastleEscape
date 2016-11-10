package castleescape.business.command;

/**
 * Class defining instance behavior for command objects. All commands consist of
 * two parts, we call them the command word and the command parameter. A command
 * must have a word, but it does not necessarily have a parameter. The command
 * word is a {@link CommandWord} object, and the command parameter is stored as
 * a string.
 * <p>
 * Example:<br>
 * The command string {@code "go west"} defines the command word {@code "go"},
 * and the command parameter {@code "west}.
 */
public class Command {

	/**
	 * The command word.
	 */
	private final CommandWord commandWord;

	/**
	 * The command parameter.
	 */
	private final String commandParameter;

	/**
	 * Constructs a new command object with the specified word and parameter. If
	 * the command has no parameter, {@code null} should be passed instead.
	 *
	 * @param commandWord  the command word as a {@link CommandWord} object
	 * @param commandParam the command parameter as a string. If the command has
	 *                     no parameter, use {@code null}
	 */
	public Command(CommandWord commandWord, String commandParam) {
		this.commandWord = commandWord;
		this.commandParameter = commandParam;
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
	 * Get the command parameter.
	 *
	 * @return the command parameter
	 */
	public String getCommandParameter() {
		return commandParameter;
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
	 * Test if this command object has a parameter.
	 *
	 * @return {@code true} if it has a parameter, {@code false} otherwise
	 */
	public boolean hasCommandParameter() {
		return (commandParameter != null);
	}
}
