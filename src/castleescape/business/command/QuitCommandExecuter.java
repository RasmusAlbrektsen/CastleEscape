/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.framework.Game;
import castleescape.business.ViewUtil;

/**
 * A command executer for executing quit commands.
 *
 * @author Kasper
 */
public class QuitCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//Quit the game only if the quit command has not parameters
		if (command.hasCommandParameters()) {
			ViewUtil.println("Quit what?");
		} else {
			game.end();
		}
	}
}
