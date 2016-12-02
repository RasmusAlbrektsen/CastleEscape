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
 * Rvent executer for adding an item to the player's inventory.
 */
public class AddPlayerItemEventExecuter extends InventoryEventExecuter {

	/**
	 * Add an item specified by the event to the player's inventory.
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

		//If the item exists, add it, otherwise do nothing
		if (item != null) {
			boolean success = game.getPlayer().getInventory().addItem(item);

			//If the item could not be added, that means the player's inventory
			//is full, so we drop the item on the ground instead
			if (!success) {
				ViewUtil.println("Your inventory is full, so you drop the " + item + " on the floor.");
				game.getCurrentRoom().getInventory().addItem(item);
			}
		}
	}

}
