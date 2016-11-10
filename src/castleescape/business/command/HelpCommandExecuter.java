/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.framework.Game;
import castleescape.business.ViewUtil;

/**
 * A command executer for executing help commands.
 *
 * @author Christian Schou
 */
public class HelpCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//If the command has a parameter, then the help command is of the type:
		//	help <command>
		//So we need to print out help information about a specific parameter.
		//Otherwise it is of the type:
		//	help
		//And we basically just have to print the standard help message.
		if (command.hasCommandParameter()) {
			//In the case of the command having a parameter, we need to
			//determine if the parameter describes a known command word, so we
			//attempt to get a command word from the string representation
			//specified by the user
			String parameter = command.getCommandParameter();
			CommandWord commandWord = CommandWord.getCommandWord(parameter);

			//If no command word was found, tell the user. Otherwise print the
			//description of the command word.
			if (commandWord == null) {
				ViewUtil.println("We cannot find a description for that!");
			} else {
				ViewUtil.println(commandWord.getDescription());
			}
		} else {
			//No parameters, so just print the standard help message.
			ViewUtil.println("Are you lost? Here are some hints for you.");
			ViewUtil.newLine();
			ViewUtil.println("The command words you can use are:");
			ViewUtil.print('\t');

			//Call the showCommands() method on the CommandWord class. This
			//method will print out all valid commands (see documentation)
			CommandWord.showAll();

			ViewUtil.println("To learn more about a specific command use:");
			ViewUtil.println("\thelp <commandword>");
		}
	}
}
