/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import castleescape.business.framework.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Builder responsible for processing raw room data.
 *
 * @author Alex, Kasper
 */
public class RoomBuilder implements IBuilder {

	/**
	 * The name of the room.
	 */
	private String name;

	/**
	 * The description of the room.
	 */
	private String description;

	/**
	 * The list of inspectable objects in the room, by their names.
	 */
	private final List<String> inspectableObjects;

	/**
	 * The list of items in the room, by their names.
	 */
	private final List<String> items;

	/**
	 * The exits from the room. The key is the direction and the value is the
	 * name of the room that it connects to in that direction.
	 */
	private final Map<String, String> exits;

	/**
	 * The room that has been built. Will be null until {@link #build()} has
	 * been called.
	 */
	protected Room result;

	/**
	 * The name of a data element accepted by this builder.
	 */
	public static final String OBJECT = "object",
			ITEM = "item",
			NORTH = "north",
			SOUTH = "south",
			EAST = "east",
			WEST = "west";

	/**
	 * Constructs a new room builder.
	 */
	public RoomBuilder() {
		inspectableObjects = new ArrayList<>();
		items = new ArrayList<>();
		exits = new HashMap<>();
	}

	@Override
	public void notifyOfElement(String element) {
		//The room builder does not need to do anything here
	}

	@Override
	public void processElement(String element, String content) {
		switch (element) {
			case NAME:
				//Reading the name
				name = content;
				break;
			case DESCRIPTION:
				//Reading the description
				description = content;
				break;
			case OBJECT:
				//Reading an object in the room
				inspectableObjects.add(content);
				break;
			case ITEM:
				//Reading an item in the room
				items.add(content);
				break;
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
				//Reading an exit. The element specifies the direction, while
				//the content specifies the name of the room to connect to
				exits.put(element, content);
				break;
		}
	}

	@Override
	public void build(LevelDataStorage dataStorage) {
		//Construct new room
		result = new Room(name, description);

		//Add inspectable objects
		for (String insp : inspectableObjects) {
			result.addInspectableObject(dataStorage.getInspectableObject(insp));
		}

		//Add items
		for (String item : items) {
			result.getInventory().addItem(dataStorage.getItem(item));
		}
	}
	
	@Override
	public void postBuild(LevelDataStorage dataStorage) {
		//Build the exits of the room.
		//Loop through all exits
		for (Entry<String, String> exit : exits.entrySet()) {
			//For every entry in the exit map the key is the direction and the
			//value is the name of the room that it connects to. We add that
			//exit
			result.setExit(exit.getKey(), dataStorage.getRoom(exit.getValue()));
		}
	}

	@Override
	public Room getResult() {
		return result;
	}
}
