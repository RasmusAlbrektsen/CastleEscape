package castleescape.business.command;

import castleescape.business.framework.Game;
import castleescape.business.ViewUtil;

/**
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
				if (!game.getMonster().isWaitingForPlayer()) {
					//If the player has seen the monster before, tell the player
					//that the monster is in the room
					ViewUtil.println("The monster is in there!");
				} else {
					//If the player has not seen the monster before, tell the
					//player that he sees a statue instead
					ViewUtil.println("Peeking into the room reveals nothing other than an old statue in the corner.");
				}
			} else {
				//If the monster is not in the room, tell the player
				ViewUtil.println("The room appears empty.");
			}
		} else {
			//If no firection was specified, tell the player
			ViewUtil.println("Peek where?");
		}
	}
}
