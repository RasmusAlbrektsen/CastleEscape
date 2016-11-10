/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.event;

import castleescape.business.object.InspectableObject;
import castleescape.business.object.InspectableObjectRegister;
import castleescape.business.object.Item;

/**
 * An abstract event executer that performs operations on an inventory. This
 * type of event executers will need to retrieve an item from the event object.
 * 
 * @author Kasper
 */
abstract class InventoryEventExecuter implements EventExecuter {
	
	/**
	 * Get the item that is described in the specified event. The event is
	 * assumed to have a parameter with the name "Item".
	 * 
	 * @param e the event to get the item information from
	 * @return the item described by the event, or null if no such item exists
	 */
	Item getItemFromEvent(Event e) {
		String itemName = e.getEventParam(Event.ITEM);
		return InspectableObjectRegister.getAsItem(itemName);
	}

	InspectableObject getObjectFromEvent(Event e) {
		return InspectableObjectRegister.getAsInspectableObject(e.getEventParam(Event.ITEM));
	}
}
