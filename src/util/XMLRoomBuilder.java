package util;

import castleescape.business.framework.Room;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.InspectableObjectRegister;
import castleescape.business.object.Item;

import java.util.ArrayList;

/**
 * Created by Alex on 26/10/2016.
 * Builder for rooms. Instantiated by {@link XMLContentBuilder}
 * Calling build, builds the room and adds it to {@link XMLRoomExitBuilder} which will add the room exits.
 */
class XMLRoomBuilder implements IBuilder {
	private final String name;
	private String description;
	private final ArrayList<InspectableObject> inspectableObjects = new ArrayList<>();
	private final ArrayList<Item> items = new ArrayList<>();

	XMLRoomBuilder(String name) {
		this.name = name;
	}

	/**
	 * Sets the description of the room, which is shown upon entry.
	 *
	 * @param description the description of the room.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Adds an exit to the room in the given direction.
	 * @param direction the direction of the room.
	 * @param destination the name of the room to add.
	 */
	void addExit(String direction, String destination) {
		XMLRoomExitBuilder.addExit(name, direction, destination);
	}

	/**
	 * Adds an item to the rooms inventory.
	 * @param name the name of the item to add.
	 */
	void addItem(String name) {
		Item itemToAdd = InspectableObjectRegister.getAsItem(name);
		if (itemToAdd == null) {
			System.out.println("An item with the name \"" + name + "\" does not exist.");
			throw new InvalidXMLException("Item in room does not exist");
		}
		items.add(itemToAdd);
	}

	/**
	 * Add an inspectable object to the room.
	 * @param name The name of the object.
	 */
	void addInspectableObject(String name) {
		inspectableObjects.add(InspectableObjectRegister.getAsInspectableObject(name));
	}

	/**
	 * Builds the room, and adds it to {@link XMLRoomExitBuilder}
	 */
	public void build() {
		Room builtRoom = new Room(name, description);
		for (Item item : items) {
			builtRoom.getInventory().addItem(item);
		}
		for (InspectableObject inspectableObject : inspectableObjects) {
			builtRoom.addInspectableObject(inspectableObject);
		}
		XMLRoomExitBuilder.addRoom(builtRoom);
	}
}
