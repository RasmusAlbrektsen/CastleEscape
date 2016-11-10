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
		//Print out a help message only if the command has no parameters
		if (command.hasCommandParameters()) {
			ViewUtil.println("Help what?");
		} else {
			//No parameters, so just print the standard help message.
			ViewUtil.println("Are you lost? Here are some hints for you.");
			ViewUtil.newLine();
			ViewUtil.println("Use the directional buttons on the right to move between rooms.");
			ViewUtil.println("Use the drop down boxes to select an item in your inventory and an object in the room to interact with.");
			ViewUtil.println("Press the buttons below the drop down boxes to perform the interaction.");
		}
	}
}
