/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.ViewUtil;
import castleescape.business.framework.Game;

/**
 * A command executer for executing highscore commands.
 * 
 * @author Kasper
 */
public class HighscoresCommandExecuter implements CommandExecuter {
	
	@Override
	public void execute(Game game, Command command) {
		//Print out highscores only if the command has no parameters
		if (command.hasCommandParameters()) {
			ViewUtil.println("Highscores of what?");
		} else {
			//Write out the top ten scores at most
			game.getScoreManager().writeScoreTable(10);
		}
	}
}
