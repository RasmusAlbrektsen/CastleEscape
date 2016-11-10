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
 * A command executer for executing take commands.
 *
 * @author Kasper
 */
public class TakeCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//Get the player character and the current room
		Character player = game.getPlayer();
		Room room = game.getCurrentRoom();

		//Attempt to get the item with the specified name in the room's
		//inventory. If no such item exists, then the value of item will be null
		String itemName = command.getCommandParameter(Command.ITEM);
		Item item = room.getInventory().getItemByName(itemName);
		
		//If the item does not exist in the room, tell the user
		if (item == null) {
			ViewUtil.println("There is no such item to take!");
			return;
		}

		//Get the inventory of the room and the player
		Inventory playerInventory = player.getInventory();
		Inventory roomInventory = room.getInventory();

		//Attempt to move the specified item from the room inventory to the
		//player inventory. If the item was actually moved, then this method
		//will return true, otherwise it will return false.
		boolean success = roomInventory.moveItem(item, playerInventory);

		//Tell the user about the result
		if (success) {
			ViewUtil.println("Picked up " + itemName + ".");
		} else {
			ViewUtil.println("I cannot carry any more!");
		}
	}
}
