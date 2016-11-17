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
		if (command.hasCommandParameters()) {
			//If the player has typed a direction, which is not in the current
			//room's hashmap, or if the direction is null
			if (game.getCurrentRoom().getExit(command.getCommandParameter(Command.DIRECTION)) == null) {
				ViewUtil.println("There is no door to peek through.");
				return;
			}
			
			//If the monsters room matches the room the player is peeking into
			if (game.getMonster().getCurrentRoom() == game.getCurrentRoom().getExit(command.getCommandParameter(Command.DIRECTION))) {
				if (!game.getMonster().isWaitingForPlayer()){
					ViewUtil.println("The monster is in there!");
				}else {
					ViewUtil.println("Peeking into the room reveals nothing other than an old statue in the corner.");
				}
			} else {
				ViewUtil.println("The room appears empty.");
			}
		} else {
			ViewUtil.println("Peek where?");
		}
	}
}
