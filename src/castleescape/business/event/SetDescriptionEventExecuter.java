/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.framework.Room;

/**
 * An event executer for changing the description of a room.
 *
 * @author Christian Schou
 */
public class SetDescriptionEventExecuter implements EventExecuter {

	/**
	 * Change the description of the room specified by the event to the new
	 * description which is also specified by the event.
	 */
	@Override
	public void execute(Game game, Event event) {
		//Get the room for which the description should be changed
		Room room = game.getRoom(event.getEventParam(Event.ROOM));
		
		//Get the new description for this room
		String roomDescription = event.getEventParam(Event.DESCRIPTION);
		
		//Set the description of the room
		room.setDescription(roomDescription);
	}

}
