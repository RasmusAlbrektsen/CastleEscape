/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.framework.Room;

/**
 * Event executer for teleporting the player.
 *
 * @author Kasper
 */
public class TeleportEventExecuter implements EventExecuter {

	@Override
	public void execute(Game game, Event event) {
		//Get the name of the room that we should teleport to
		String teleport = event.getEventParam(Event.DESTINATION);

		//Get the room object with the name we acquired above
		Room teleportRoom = game.getRoom(teleport);

		if (teleportRoom == null) {
			//If no room was found, print an error message
			System.out.println("The room " + teleport + " does not exist!");
		} else {
			//Otherwise, move the player to the room specified by the event
			game.setRoom(teleportRoom);

			//We also have to notify the monster that the player moved
			game.getMonster().notifyOfGo(teleportRoom);
		}
	}
}
