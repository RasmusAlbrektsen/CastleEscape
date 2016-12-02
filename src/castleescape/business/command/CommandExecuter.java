/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.framework.Game;

/**
 * Interface defining all objects that are able to execute
 * {@link Command commands}.
 */
public interface CommandExecuter {

	/**
	 * Execute the specified command according to the rules defined by this
	 * command executor. The command is assumed to be of the correct type for
	 * this executor.
	 *
	 * @param game    the game instance
	 * @param command the command to execute
	 */
	void execute(Game game, Command command);

}
