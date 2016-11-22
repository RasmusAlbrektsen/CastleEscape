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
import java.util.List;

/**
 * The mediator class for connecting the business code with the data layer.
 * Unfortunately, the data layer relies directly on the models defined in the
 * business layer (Room, InspectableObject, Item, Event, EventWord), although
 * the effort required to remove this coupling would increase the complexity of
 * both the business and the data layer far above what is worthwhile for an
 * application this simple.
 *
 * @author Kasper
 */
public class DataMediator {

	/**
	 * The object responsible for reading level data.
	 */
	private final LevelDataReader levelDataReader;

	/**
	 * Constructs a new data mediator.
	 */
	public DataMediator() {
		levelDataReader = new LevelDataReader();
	}

	/**
	 * Get the names of the playable levels.
	 *
	 * @return the names of the playable levels
	 */
	public String[] getLevels() {
		//Pass call to level data reader
		return levelDataReader.getLevels();
	}

	/**
	 * Read all level data in the folder at the specified path. The level data
	 * can then be retrieved using the methods
	 * {@link #getInspectableObjects()}, {@link #getItems()} and
	 * {@link #getRooms()}.
	 *
	 * @param levelFolderPath the path of the level folder as a string
	 */
	public void readLevelData(String levelFolderPath) {
		//Pass call to level data reader
		levelDataReader.readLevel(levelFolderPath);
	}

	/**
	 * Get the inspectable objects that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the inspectable objects that were read
	 */
	public List<InspectableObject> getInspectableObjects() {
		//Pass call to level data reader
		return levelDataReader.getInspectableObjects();
	}

	/**
	 * Get the items that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the items that were read
	 */
	public List<Item> getItems() {
		//Pass call to level data reader
		return levelDataReader.getItems();
	}

	/**
	 * Get the rooms that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the rooms that were read
	 */
	public List<Room> getRooms() {
		//Pass call to level data reader
		return levelDataReader.getRooms();
	}

	/**
	 * Get the configurations that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the configurations that were read
	 */
	public Configuration getConfiguration() {
		//Pass call to level data reader
		return levelDataReader.getConfiguration();
	}
}
