package castleescape.business.framework;

import castleescape.business.object.InspectableObject;
import castleescape.business.object.Inventory;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class describing a room in the game. Rooms have a description and a
 * {@link HashMap} linking the direction strings
 * {@code "north", "south", "east", "west"} to the exits in these directions, if
 * any. Rooms can also contain inspectable objects and items.
 */
public class Room {

	/**
	 * The name of the room.
	 */
	private final String roomName;

	/**
	 * The string description of the room.
	 */
	private String description;

	/**
	 * {@link Map} for mapping between direction strings and the exits in the
	 * room.
	 */
	private final Map<String, Room> exits;

	/**
	 * Inventory, where the items go.
	 */
	private final Inventory inventory;

	/**
	 * {@link Map} for storing the inspectable objects in this room by their
	 * names.
	 */
	private final Map<String, InspectableObject> roomObjects;

	/**
	 * Constructs a new room with the specified description. The constructed
	 * room will have no exits at this point.
	 *
	 * @param roomName    the name of the room as a string
	 * @param description the description of the room as a string
	 */
	public Room(String roomName, String description) {
		this.roomName = roomName;
		this.description = description;

		//Construct hash map for mapping between direction strings and the exits
		//in the room
		exits = new HashMap<>();

		//Initialize inventory with unlimited capacity
		inventory = new Inventory();

		//Initialize HashMap for storing inspectable objects
		roomObjects = new HashMap<>();
	}

	/**
	 * Add an exit to another room in the specified direction. If there is an
	 * existing exit in this direction, the exit will be overwritten.
	 *
	 * @param direction the direction of the exit, one of
	 *                  {@code "north", "south", "east", "west"}
	 * @param neighbor  the room to go to, when taking this exit
	 */
	public void setExit(String direction, Room neighbor) {
		//Make the string direction map to the specified neighbor room. This
		//defines an exit in this room
		exits.put(direction, neighbor);
	}

	/**
	 * Get the name of this room.
	 *
	 * @return the name of this room
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * Change the description of this room.
	 *
	 * @param description the new description of this room
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the short description of this room. The short description is the
	 * decription chosen in the room's constructor.
	 *
	 * @return the short description of the room
	 */
	private String getShortDescription() {
		return description;
	}

	/**
	 * Get a long description of this room. The long description combines the
	 * name of the room, the description chosen in the room's constructor, the
	 * room's inventory, and the result of {@link #getExitString()} on separate
	 * lines.
	 *
	 * @return the long description of the room
	 */
	public String getLongDescription() {
		StringBuilder descriptionBuilder = new StringBuilder();
		descriptionBuilder.append("<h1>").append(getRoomName()).append("</h1>")
				.append(getShortDescription()).append("</br></br>");

		//Only print inventory if the room contains any items
		if (getInventory().getItemCount() != 0) {
			descriptionBuilder.append("Scattered on the floor you see: ").append(getInventory()).append("</br></br>");
		}

		descriptionBuilder.append(getExitString());

		return descriptionBuilder.toString();
	}

	/**
	 * Get a string describing the exits of this room. The string consists of a
	 * single line with the exits separated by spaces. The string will be
	 * special if the room has no exits.
	 * <p>
	 * Example return value: {@code "Exits: north east"}
	 *
	 * @return a string describing the exits of this room
	 */
	private String getExitString() {
		//If there are no exits, return a special string
		if (exits.isEmpty()) {
			return "There are no exits from this room";
		}

		//Initialize returnString variable
		String returnString = "Exits:";

		//Go through all keys in the exits hash map. These keys are strings
		//defining which directions have valid exits
		Set<String> keys = exits.keySet();
		for (String exit : keys) {
			//For every direction string, concatenate it to the returnString
			//variable along with spaces to separate the individual direction
			//strings
			returnString += " " + exit;
		}

		//Return the resulting string
		return returnString;
	}

	/**
	 * Get the room connected to the exit in the specified direction. If there
	 * is no exit in the specified direction this will return {@code null}.
	 *
	 * @param direction the direction of the exit to use
	 * @return the room connected to the exit in the specified direction
	 */
	public Room getExit(String direction) {
		//Get the room connected to the exit in the direction specified by the
		//parameter. This room, if any, is the value associated with the
		//direction key in the exits hash map
		return exits.get(direction);
	}

	/**
	 * Get a map of all the exits from this room.
	 *
	 * @return the Map of the exits from the room
	 */
	public Map<String, Room> getExits() {
		return exits;
	}

	/**
	 * Get the inventory of this room.
	 *
	 * @return the inventory of this room
	 */
	public Inventory getInventory() {
		// Now all the rooms have an inventory.
		return inventory;
	}

	/**
	 * Add an InspectableObject to this room.
	 *
	 * @param inspectableObject the InspectableObject to add
	 */
	public void addInspectableObject(InspectableObject inspectableObject) {
		roomObjects.put(inspectableObject.getName(), inspectableObject);
	}

	/**
	 * Remove an inspectable object from this room.
	 *
	 * @param inspectableObject the InspectableObject to remove
	 */
	public void removeInspectableObject(InspectableObject inspectableObject) {
		roomObjects.remove(inspectableObject.getName());
	}

	/**
	 * Get the inspectable object with the specified name in this room. This
	 * method will search for the inspectable object in both the room's
	 * inventory and among the static inspectable objects in the room.
	 *
	 * @param name the name of the inspectable object to find
	 * @return the inspectable object with the specified name, or {@code null}
	 *         if no such inspectable object exists
	 */
	public InspectableObject getInspectableObjectByName(String name) {
		//Item inherits from InspectableObject, and thus and Item IS-A
		//InspectableObject, and we are able to store references to Items as if
		//they were InspectableObjects. We know that Item is more specific than
		//an InspectableObject, and thus all operations that can be performed on
		//an InspectableObject can also be performed on an Item.
		//Attempt to find the object in the room's inventory.
		InspectableObject object = inventory.getItemByName(name);

		//If object isn't equal to null, then it points to an object, and we
		//won't have to keep searching.
		if (object != null) {
			return object;
		} else {
			//Attempt to find the object among the inspectable objects
			object = roomObjects.get(name);

			//Return whatever the result is, as we are out of options. If the
			//result is null, that means we could not find an inspectable object
			//with the specified name
			return object;
		}
	}

	/**
	 * Get the inspectable objects in this room as a list. It is safe to alter
	 * this list from outside.
	 *
	 * @return the inspectable objects in this room
	 */
	public List<InspectableObject> getInspectableObjects() {
		return new ArrayList<>(roomObjects.values());
	}

	@Override
	public String toString() {
		return getRoomName();
	}
}
