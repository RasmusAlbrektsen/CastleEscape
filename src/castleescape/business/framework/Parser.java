package castleescape.business.framework;

import castleescape.business.command.Command;
import castleescape.business.command.CommandWord;
import castleescape.business.ViewUtil;

import java.util.Scanner;

/**
 * Class defining instance behavior responsible for getting user input in the
 * form of command strings and turning them into {@link Command} objects.
 */
class Parser {

	/**
	 * The {@link Scanner} responsible for reading user input.
	 */
	private final Scanner reader;

	/**
	 * Constructs a new parser object. This creates a member instance of
	 * {@link CommandWords} for keeping track of available commands and for
	 * turning command strings into {@link CommandWord} objects. It also creates
	 * a scanner object for reading user input.
	 */
	public Parser() {
		reader = new Scanner(System.in);
	}

	/**
	 * Read the next line of user input using the rules defined by
	 * {@link Scanner#nextLine()}. The input string will be broken up into a
	 * first and second word. All subsequent words are discarded. Lastly, the
	 * input string will be turned into a {@link Command} object with the first
	 * word being the command word and the second word being the command
	 * parameter.
	 * <p>
	 * The method will pause until the user enters a line of input.
	 *
	 * @return The next user input in the form of a {@link Command} object
	 */
	public Command getCommand() {
		//Declare string to hold the user input
		String inputLine;

		//Initialize two strings to hold the first input word from the user and
		//the rest of the input line respectively. Both are assigned values of
		//null, as they are not necessarily assigned any values in the code
		//below, but they must be initialized before we can use them to create a
		//command object
		String word = null;
		String param = null;

		//Indicate to the user that we are accepting input
		ViewUtil.print("> ");

		//Read all user input when the user presses enter
		inputLine = reader.nextLine();

		//Make the user input lower case, which is our naming convention
		inputLine = inputLine.toLowerCase();

		//Construct a scanner to go through each word in the input string
		Scanner tokenizer = new Scanner(inputLine);

		//Test if the scanner can read a word in the input string. If not, then
		//the input string is empty
		if (tokenizer.hasNext()) {
			//Read the first word in the input string and store it in the word
			//variable. This will also make the scanner move on to the next word
			//in the input string
			word = tokenizer.next();

			//Test if the scanner can read a second word in the input string. If
			//not, then the input string does not specify any command parameters
			if (tokenizer.hasNext()) {
				//Read the rest of the input and store it in the param variable
				param = tokenizer.nextLine();

				//tokenizer.nextLine() will include the space between the
				//command word and the first command parameter, so we remove
				//this space
				param = param.substring(1);
			}
		}

		//Construct command object from the two strings word1 and word2. It is
		//legal for both to be null - these cases are handled by the command
		//constructor and the method CommandWords.getCommandWord(String)
		return new Command(CommandWord.getCommandWord(word), param);
	}

	//TODO: What to do about this?
	/**
	 * Print out all commands known by this parser as defined by
	 * {@link CommandWords#showAll()}.
	 */
	public void showCommands() {
		//Pass on method call to CommandWords.showAll() (see documentation for
		//details)
		CommandWord.showAll();
	}
}
