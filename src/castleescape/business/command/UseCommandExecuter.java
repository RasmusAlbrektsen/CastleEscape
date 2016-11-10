/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.event.Event;
import castleescape.business.framework.Game;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.Item;
import castleescape.business.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A command executer for executing use commands.
 *
 * @author DitteKoustrup
 */
public class UseCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//Example of use: use item on object
		//Item is in the player's inventory and object is either in the player's
		//inventory or in the current room

		//Split between the names of item and object, and put them in an array
		String[] splitItem = command.getCommandParameter().split(" on ", 2);

		//If less than two objects were specified by the user, then no operation
		//can be performed
		if (splitItem.length < 2) {
			ViewUtil.println("I don't understand what you mean!");
			return;
		}

		//Get item from the player's inventory
		Item item = game.getPlayer().getInventory().getItemByName(splitItem[0]);

		InspectableObject object;

		//Attempt to get object from the room
		object = game.getCurrentRoom().getInspectableObjectByName(splitItem[1]);

		//If no object was found attempt to get it from the player's inventory
		if (object == null) {
			object = game.getPlayer().getInventory().getItemByName(splitItem[1]);
		}

		//If either does not exist, then tell the player
		if (item == null || object == null) {
			ViewUtil.println("I cannot find all those objects!");
			return;
		}

		//Create array list to store al use events
		List<Event> useEvents = new ArrayList<>();

		//Get the events created by using item on object
		useEvents.addAll(item.useWithObject(object.getName()));

		//if object is also of type Item, then maybe it defines additional
		//events, so we attempt to add those
		if (object instanceof Item) {
			//Cast object to Item so we can access its useWithObject() method
			Item objectAsItem = (Item) object;

			//Get the events created by using object on item
			useEvents.addAll(objectAsItem.useWithObject(item.getName()));
		}

		//If we can't use the item and object together, tell the player
		if (useEvents.isEmpty()) {
			ViewUtil.println("I can't seem to see how i should use these objects with each other.");
			return;
		}

		//Execute all retrieved events
		for (Event e : useEvents) {
			e.execute(game);
		}
	}
}
