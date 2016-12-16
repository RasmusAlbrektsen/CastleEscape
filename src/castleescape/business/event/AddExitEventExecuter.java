package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.framework.Room;
import castleescape.business.ViewUtil;

/**
 * Event executer for adding an exit to the current room.
 */
public class AddExitEventExecuter implements EventExecuter {

	/**
	 * Adds a room to the current rooms exits. The room must already exist, and
	 * have a name.
	 */
	@Override
	public void execute(Game game, Event event) {
		//Get the direction to add an exit in
		String direction = event.getEventParam(Event.DIRECTION);

		//Get the room to connect to
		Room otherRoom = game.getRoom(event.getEventParam(Event.DESTINATION));

		//If the room does not exist, we cannot add it
		if (otherRoom == null) {
			//Use System.out.println() for debugging purposes
			System.out.println("The room with the name " + event.getEventParam(Event.DESTINATION) + " does not exist");
		}

		//Print the description, if one is present
		String description = event.getEventParam(Event.DESCRIPTION);
		if (!description.isEmpty()) {
			ViewUtil.println(description);
		}

		//Add the specified exit to the current room
		game.getCurrentRoom().setExit(direction, otherRoom);
	}
}
