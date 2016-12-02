/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.event;

import castleescape.business.framework.Game;
import castleescape.business.object.Item;
import castleescape.business.ViewUtil;

/**
 * An event executer for removing an item from the player's inventory.
 */
public class RemovePlayerItemEventExecuter extends InventoryEventExecuter {

	/**
	 * Remove an item specified by the event from the player's inventory.
	 */
	@Override
	public void execute(Game game, Event event) {
		//Get the item specified by the event by using the method in the
		//superclass InventoryEventExecuter
		Item item = getItemFromEvent(event, game);

		//Print the description, if one is present
		String description = event.getEventParam(Event.DESCRIPTION);
		if (description != null) {
			ViewUtil.println(description);
		}

		//If the item exists, remove it, otherwise do nothing
		if (item != null) {
			game.getPlayer().getInventory().removeItem(item);
		}
	}
}
