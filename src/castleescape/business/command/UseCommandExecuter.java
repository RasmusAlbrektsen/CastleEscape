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
		//Get item and object names
		String itemName = command.getCommandParameter(Command.ITEM);
		String objectName = command.getCommandParameter(Command.OBJECT);
		
		//If either was not specified, tell the user
		if (itemName == null || objectName == null) {
			ViewUtil.println("I need two actual objects to use with one another!");
			return;
		}
		
		//Get item from the player's inventory
		Item item = game.getPlayer().getInventory().getItemByName(itemName);

		InspectableObject object;

		//Attempt to get object from the room
		object = game.getCurrentRoom().getInspectableObjectByName(objectName);

		//If no object was found attempt to get it from the player's inventory
		if (object == null) {
			object = game.getPlayer().getInventory().getItemByName(objectName);
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

		//If object is also of type Item, then maybe it defines additional
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
