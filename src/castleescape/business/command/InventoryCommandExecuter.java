/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.framework.Game;
import castleescape.business.object.Inventory;
import castleescape.business.ViewUtil;

/**
 * A command executer for executing inventory commands.
 *
 * @author Christian Schou
 */
public class InventoryCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//If the command has a parameter then the user used the command
		//incorrectly
		if (command.hasCommandParameter()) {
			ViewUtil.println("I don't understand what you mean");
		} else {
			//Get a reference to the player character's inventory
			Inventory playerInventory = game.getPlayer().getInventory();

			//If the inventory is empty, say so
			if (playerInventory.getItemCount() == 0) {
				ViewUtil.println("My inventory is empty");
			} else {
				//Otherwise print out the inventory, since Inventory overrides
				//the toString() method
				ViewUtil.println(playerInventory);
			}
		}
	}

}
