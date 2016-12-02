/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.command;

import castleescape.business.event.Event;
import castleescape.business.framework.Game;
import castleescape.business.framework.Room;
import java.util.List;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.Inventory;
import castleescape.business.ViewUtil;

/**
 * A command executer for executing inspect commands.
 */
public class InspectCommandExecuter implements CommandExecuter {

	@Override
	public void execute(Game game, Command command) {
		//Get the name of the inspectable object that the user wishes to inspect
		String objectName = command.getCommandParameter(Command.OBJECT);

		//Get a reference to the room that we are currently in
		Room currentRoom = game.getCurrentRoom();

		//Attempt to find an inspectable object in either the room or the player
		//inventory that has the name specified by the user. If the name is
		//null, then a null object is returned
		InspectableObject currentObject;
		currentObject = currentRoom.getInspectableObjectByName(objectName);

		//If the inspectable object was not in the room (tested above) then look
		//in the player's inventory
		if (currentObject == null) {
			Inventory playerInventory = game.getPlayer().getInventory(); // Players inventory.
			currentObject = playerInventory.getItemByName(objectName); // Object with spoecified name.
		}

		//If we still havent't found an inspectable object with the specified
		//name, then tell the user
		if (currentObject == null) {
			ViewUtil.println("I cannot find that object!");
			return;
		}

		//We found an inspectable object (we know that it is non-null because of
		//the return statement above), so inspect it. This will return a list of
		//events to be executed
		List<Event> inspectEvents = currentObject.inspect();

		//Sort the events
		inspectEvents.sort(new EventComparator());

		//Execute all retrieved events
		for (Event e : inspectEvents) {
			e.execute(game);
		}
	}
}
