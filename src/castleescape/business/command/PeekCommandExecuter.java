package castleescape.business.command;

import castleescape.business.framework.Game;
import castleescape.business.ViewUtil;

/**
 * Created by Alex on 01/11/2016.
 * Command for peeking into a room, revealing whether a monster is in there.
 */
public class PeekCommandExecuter implements CommandExecuter {
	@Override
	public void execute(Game game, Command command) {
		if (command.hasCommandParameter()) {
			//If the monster or the monster's room is uninitialised
			if (game.getMonster() == null || game.getMonster().getCurrentRoom() == null) {
				ViewUtil.println("The room appears empty");
				return;
			}
			//If the player has typed a direction, which is not in the current rooms hashmap
			if (game.getCurrentRoom().getExit(command.getCommandParameter()) == null) {
				ViewUtil.println("There is no door to peek through");
				return;
			}
			//If the monsters room matches the room the player is peeking into
			if (game.getMonster().getCurrentRoom().equals(game.getCurrentRoom().getExit(command.getCommandParameter()))) {
				ViewUtil.println("The monster is in there!");
			} else {
				ViewUtil.println("The room appears empty");
			}
		} else {
			ViewUtil.println("peek in which direction?");
		}
	}
}
