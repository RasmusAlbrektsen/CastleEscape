/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.framework.Game;
import castleescape.business.framework.Room;
import castleescape.business.ViewUtil;

/**
 * A command executer for executing go commands.
 */
public class GoCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//Get the direction to go in
		String direction = command.getCommandParameter(Command.DIRECTION);

		//If the specified "go" command does not have any direction specified
		//then we can not tell where to go, and we will just return and skip the
		//rest of the method
		if (direction == null) {
			ViewUtil.println("Go where?");
			return;
		}

		//At this point we know that the direction is non-null. We ask the
		//current room for the exit in the direction specified by the direction
		//variable. If an exit in the specified direction exists, it returns the
		//room at the exit. Otherwise it will return null
		Room nextRoom = game.getCurrentRoom().getExit(direction);

		//If the return value above was null, that means the direction specified
		//by the user did not describe a valid exit
		if (nextRoom == null) {
			ViewUtil.println("There is no door in that direction!");
		} else {
			//We set a new value for the current room in the game object and
			//print out its long description
			game.setRoom(nextRoom);
			ViewUtil.println(nextRoom.getLongDescription());

			//We also have to notify the monster that the player moved
			game.getMonster().notifyOfGo(nextRoom);
		}
	}
}
