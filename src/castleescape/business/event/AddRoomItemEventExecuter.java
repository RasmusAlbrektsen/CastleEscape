package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.Item;
import castleescape.business.ViewUtil;

/**
 * Event executer for adding an item to the current room's inventory.
 */
public class AddRoomItemEventExecuter extends InventoryEventExecuter {

	/**
	 * Add an item specified by the event to the current room's inventory.
	 */
	@Override
	public void execute(Game game, Event event) {
		//Get the item specified by the event by using the method in the
		//superclass InventoryEventExecuter
		InspectableObject object = getObjectFromEvent(event, game);

		//Print the description, if one is present
		String description = event.getEventParam(Event.DESCRIPTION);
		if (description != null) {
			ViewUtil.println(description);
		}

		//If the item exists, add it, otherwise do nothing
		if (object != null) {
			if (object instanceof Item) {
				game.getCurrentRoom().getInventory().addItem((Item) object);
			} else {
				game.getCurrentRoom().addInspectableObject(object);
			}
		}
	}
}
