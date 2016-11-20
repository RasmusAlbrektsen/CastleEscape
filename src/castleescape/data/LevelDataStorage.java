/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import castleescape.business.framework.Configuration;
import castleescape.business.framework.Room;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for storing all the data read when loading a level.
 * Instances of this class can be passed to builders if they require knowledge
 * of other data during building.
 *
 * @author Kasper
 */
public class LevelDataStorage {

	private final List<InspectableObject> inspectableObjects;
	private final List<Item> items;
	private final List<Room> rooms;
	private Configuration config;

	/**
	 * Constructs a new data storage for levels in the game.
	 */
	public LevelDataStorage() {
		inspectableObjects = new ArrayList<>();
		items = new ArrayList<>();
		rooms = new ArrayList<>();
	}

	/**
	 * Add an inspectable object to this data storage.
	 *
	 * @param o the inspectable object to add
	 */
	public void addInspectableObject(InspectableObject o) {
		inspectableObjects.add(o);
	}

	/**
	 * Get the inspectable object with the specified name.
	 *
	 * @param name the name of the inspectable object
	 * @return the inspectable object with the specified name, or null if no
	 *         such inspectable object exists
	 */
	public InspectableObject getInspectableObject(String name) {
		//Loop through all inspectable objects and return the first occurrence
		for (InspectableObject o : inspectableObjects) {
			if (o.getName().equals(name)) {
				return o;
			}
		}

		//No match, return null
		return null;
	}

	/**
	 * Get the list of inspectable objects from this data storage.
	 *
	 * @return the list of inspectable objects
	 */
	public List<InspectableObject> getInspectableObjects() {
		return inspectableObjects;
	}

	/**
	 * Add an item to this data storage.
	 *
	 * @param i the item to add
	 */
	public void addItem(Item i) {
		items.add(i);
	}

	/**
	 * Get the item with the specified name.
	 *
	 * @param name the name of the item
	 * @return the item with the specified name, or null if no such item exists
	 */
	public Item getItem(String name) {
		//Loop through all items and return the first occurrence
		for (Item i : items) {
			if (i.getName().equals(name)) {
				return i;
			}
		}

		//No match, return null
		return null;
	}

	/**
	 * Get the list of items from this data storage.
	 *
	 * @return the list of items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Add a room to this data storage.
	 *
	 * @param r the room to add
	 */
	public void addRoom(Room r) {
		rooms.add(r);
	}

	/**
	 * Get the room with the specified name.
	 *
	 * @param name the name of the room
	 * @return the room with the specified name, or null if no such room exists
	 */
	public Room getRoom(String name) {
		//Loop through all rooms and return the first occurrence
		for (Room r : rooms) {
			if (r.getRoomName().equals(name)) {
				return r;
			}
		}

		//No match, return null
		return null;
	}

	/**
	 * Get the list of rooms from this data storage.
	 *
	 * @return the list of rooms
	 */
	public List<Room> getRooms() {
		return rooms;
	}
	
	/**
	 * Set the configuration file.
	 * 
	 * @param config the configuration file
	 */
	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	/**
	 * Get the configuration file
	 * 
	 * @return the configuration file
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * Reset this data storage so that it can be used anew.
	 */
	public void reset() {
		//Clear all lists
		inspectableObjects.clear();
		items.clear();
		rooms.clear();
		config = null;
	}
}
