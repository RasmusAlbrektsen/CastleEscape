/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.framework.Character;
import castleescape.business.framework.Game;
import castleescape.business.framework.Room;
import castleescape.business.object.Inventory;
import castleescape.business.object.Item;
import castleescape.business.ViewUtil;

/**
 * A command executer for executing drop commands.
 *
 * @author Kasper
 */
public class DropCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//Get the player character and the current room
		Character player = game.getPlayer();
		Room room = game.getCurrentRoom();

		//Attempt to get the item with the specified name in the player's
		//inventory. If no such item exists, then the value of item will be null
		String itemName = command.getCommandParameter();
		Item item = game.getPlayer().getInventory().getItemByName(itemName);

		//Get the inventory of the player and the current room
		Inventory playerInventory = player.getInventory();
		Inventory roomInventory = room.getInventory();

		//Attempt to move the specified item from the player inventory to the
		//room inventory. If the item is null, then this method will do nothing
		//except to return a boolean, as specified by
		//Inventory.move(Item, Inventory). If the item was actually moved, then
		//this method will return true, otherwise it will return false.
		boolean success = playerInventory.moveItem(item, roomInventory);

		//Tell the user about the result
		if (success) {
			ViewUtil.println("Dropped " + itemName + " on the floor.");
		} else {
			ViewUtil.println("I don't have that item!");
		}
	}
}
