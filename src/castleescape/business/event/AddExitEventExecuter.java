package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.framework.Room;
import util.InvalidXMLException;
import castleescape.business.ViewUtil;

/**
 * An event executer for adding an exit to the current room.
 * <p>
 * Created by Alex on 13/10/2016.
 */
public class AddExitEventExecuter implements EventExecuter {

	/**
	 * Adds a room to the current room. The room must already exist, and have a
	 * name.
	 */
	@Override
	public void execute(Game game, Event event) {
		//Get the direction to add an exit in
		String direction = event.getEventParam(Event.DIRECTION);

		//Get the room to connect to
		Room otherRoom = game.getRoom(event.getEventParam(Event.DESTINATION));
		if (otherRoom == null) {
			System.out.println("The room with the name " + Event.DESTINATION + " does not exist");
			throw new InvalidXMLException("An addExit event tried to add a non-existent room");
		}

		//print the description, if one is present
		String description = event.getEventParam(Event.DESCRIPTION);
		if (description != null) {
			ViewUtil.println(description);
		}

		//Add the specified exit to the current room
		game.getCurrentRoom().setExit(direction, otherRoom);
	}
}
