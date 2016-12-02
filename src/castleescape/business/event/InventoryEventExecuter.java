/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.Item;

/**
 * An abstract event executer that performs operations on an inventory. This
 * type of event executers will need to retrieve an item from the event object.
 */
abstract class InventoryEventExecuter implements EventExecuter {

	/**
	 * Get the item that is described in the specified event. The event is
	 * assumed to have a parameter with the name "item".
	 *
	 * @param e    the event to get the item information from
	 * @param game the game instance
	 * @return the item described by the event, or null if no such item exists
	 */
	protected Item getItemFromEvent(Event e, Game game) {
		String itemName = e.getEventParam(Event.ITEM);
		return game.getInspectableObjectRegister().getAsItem(itemName);
	}

	/**
	 * Get the inspectable object that is described in the specified event. The
	 * event is assumed to have a parameter with the name "item".
	 *
	 * @param e    the event to get the item information from
	 * @param game the game instance
	 * @return the inspectable object described by the event, or null if no such
	 *         object exists
	 */
	protected InspectableObject getObjectFromEvent(Event e, Game game) {
		String objectName = e.getEventParam(Event.ITEM);
		return game.getInspectableObjectRegister().getAsInspectableObject(objectName);
	}
}
