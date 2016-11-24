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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * Class responsible for reading a directory that contains level-specific data.
 * This logic has been moved to a separate class to reduce the complexity of the
 * mediator, whose only job should be to handle communication between the data
 * and the business layer, and to perform any necessary mapping.
 *
 * @author Alex, Kasper
 */
public class LevelDataReader {

	/**
	 * The name of the root directory containing all level directories.
	 */
	private static final String ROOT_DIRECTORY = "xml/";
	
	/**
	 * A subdirectory in a level directory.
	 */
	private static final String INSPECTABLE_OBJECT_DIRECTORY = "/InspectableObjects",
			ITEM_DIRECTORY = "/Items",
			ROOM_DIRECTORY = "/Rooms";

	/**
	 * The name of the config file.
	 */
	private static final String CONFIG_FILE = "/config.xml";

	/**
	 * List of all room builders retrieved from the XMLHandler. We store the
	 * room builders because they cannot be built until after all rooms have
	 * been read.
	 */
	private final List<RoomBuilder> roomBuilders;

	/**
	 * Object used to store all level data that has been read.
	 */
	private final LevelDataStorage levelDataStorage;

	/**
	 * Constructs a new level data reader.
	 */
	public LevelDataReader() {
		roomBuilders = new ArrayList<>();
		levelDataStorage = new LevelDataStorage();
	}

	/**
	 * Get the names of the playable levels.
	 *
	 * @return the names of the playable levels
	 */
	public String[] getLevels() {
		//Get all directories containing level specific data. These are all
		//located in the root directory
		File[] levelDirs = new File(ROOT_DIRECTORY).listFiles();
		
		//Create array of strings to hold the names of all the level directories
		String[] levelNames = new String[levelDirs.length];
		
		//For every level diretory, save its name in the array of level names
		for (int i = 0; i < levelDirs.length; i++) {
			levelNames[i] = levelDirs[i].getName();
		}
		
		//Return the result
		return levelNames;
	}

	/**
	 * Read all level data in the level folder with the specified name. The
	 * level data can then be retrieved using the methods
	 * {@link #getInspectableObjects()}, {@link #getItems()} and
	 * {@link #getRooms()}.
	 *
	 * @param name the name of the level to read
	 */
	public void readLevel(String name) {
		//Reset data for new run
		roomBuilders.clear();
		levelDataStorage.reset();

		try {
			//Attempt on acquiring a SAXParser to read the level xml files
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

			//Construct the handler for our SAXParser
			XMLHandler handler = new XMLHandler();

			//Read level files
			String levelDirectoryPath = ROOT_DIRECTORY + name;
			readInspectableObjects(saxParser, handler, levelDirectoryPath + INSPECTABLE_OBJECT_DIRECTORY);
			readItems(saxParser, handler, levelDirectoryPath + ITEM_DIRECTORY);
			readRooms(saxParser, handler, levelDirectoryPath + ROOM_DIRECTORY);

			//Finish building the rooms
			buildRoomExits();

			//Read config
			readConfig(saxParser, handler, levelDirectoryPath + CONFIG_FILE);
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			//Something went wrong and we cannot recover, so print the stack
			//trace
			ex.printStackTrace();
		}
	}

	/**
	 * Read all inspectable object files at the specified path.
	 *
	 * @param parser  the SAXParser to use for reading the xml files
	 * @param handler the handler to process the parsed data
	 * @param path    the path of the directory to read from
	 * @throws IOException
	 * @throws SAXException
	 */
	private void readInspectableObjects(SAXParser parser, XMLHandler handler, String path) throws IOException, SAXException {
		//Read all InspectableObject files
		//Construct a file from the location of the inspectable object
		//directory and use it to get all its subfiles
		File[] inspectableObjectFiles = new File(path).listFiles();
		for (File file : inspectableObjectFiles) {
			//Parse the file
			parser.parse(file, handler);

			//Get the resulting builder. We know that it should be an
			//InspectableObjectBuilder
			InspectableObjectBuilder builder = (InspectableObjectBuilder) handler.getBuilderResult();

			//Build the data and get the resulting inspectable object
			builder.build(levelDataStorage);
			levelDataStorage.addInspectableObject(builder.getResult());
		}
	}

	/**
	 * Read all item files at the specified path.
	 *
	 * @param parser  the SAXParser to use for reading the xml files
	 * @param handler the handler to process the parsed data
	 * @param path    the path of the directory to read from
	 * @throws IOException
	 * @throws SAXException
	 */
	private void readItems(SAXParser parser, XMLHandler handler, String path) throws IOException, SAXException {
		//Read all Item files
		//Construct a file from the location of the item directory and use it to
		//get all its subfiles
		File[] itemFiles = new File(path).listFiles();
		for (File file : itemFiles) {
			//Parse the file
			parser.parse(file, handler);

			//Get the resulting builder. We know that it should be an
			//ItemBuilder
			ItemBuilder builder = (ItemBuilder) handler.getBuilderResult();

			//Build the data and get the resulting item object
			builder.build(levelDataStorage);
			levelDataStorage.addItem(builder.getResult());
		}
	}

	/**
	 * Read all room files at the specified path.
	 *
	 * @param parser  the SAXParser to use for reading the xml files
	 * @param handler the handler to process the parsed data
	 * @param path    the path of the directory to read from
	 * @throws IOException
	 * @throws SAXException
	 */
	private void readRooms(SAXParser parser, XMLHandler handler, String path) throws IOException, SAXException {
		//Read all room files
		//Construct a file from the location of the room directory and use it to
		//get all its subfiles
		File[] roomFiles = new File(path).listFiles();
		for (File file : roomFiles) {
			//Parse the file
			parser.parse(file, handler);

			//Get the resulting builder. We know that it should be a RoomBuilder
			RoomBuilder builder = (RoomBuilder) handler.getBuilderResult();

			//Build the data and get the resulting room object
			builder.build(levelDataStorage);
			levelDataStorage.addRoom(builder.getResult());

			//Store the room builder, as we need to build the room's exits later
			roomBuilders.add(builder);
		}
	}

	/**
	 * Build all the room exits. Room exits need to be built after all other
	 * data has been read since they store references to other rooms.
	 */
	public void buildRoomExits() {
		//Loop through all room builders
		for (RoomBuilder builder : roomBuilders) {
			//Build the room's exits
			builder.postBuild(levelDataStorage);
		}
	}

	/**
	 * Read the configuration file at the specified path.
	 *
	 * @param parser  the SAXParser to use for reading the xml files
	 * @param handler the handler to process the parsed data
	 * @param path    the path of the directory to read from
	 * @throws IOException
	 * @throws SAXException
	 */
	public void readConfig(SAXParser parser, XMLHandler handler, String path) throws IOException, SAXException {
		//Create file from path and ensure that it exists
		File configFile = new File(path);
		if (!configFile.exists()) {
			//If not, throw an exception
			throw new IllegalArgumentException("Config file missing!");
		}

		//Parse the file
		parser.parse(configFile, handler);

		//Get the resulting builder. We know that it should be a
		//ConfigurationBuilder
		ConfigurationBuilder builder = (ConfigurationBuilder) handler.getBuilderResult();

		//Build the data and get the resulting configuration object
		builder.build(levelDataStorage);
		levelDataStorage.setConfig(builder.getResult());
	}

	/**
	 * Get the inspectable objects that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the inspectable objects that were read
	 */
	public List<InspectableObject> getInspectableObjects() {
		return levelDataStorage.getInspectableObjects();
	}

	/**
	 * Get the items that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the items that were read
	 */
	public List<Item> getItems() {
		return levelDataStorage.getItems();
	}

	/**
	 * Get the rooms that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the rooms that were read
	 */
	public List<Room> getRooms() {
		return levelDataStorage.getRooms();
	}

	/**
	 * Get the configurations that were read during the last call to
	 * {@link #readLevel(String)}.
	 *
	 * @return the configurations that were read
	 */
	public Configuration getConfiguration() {
		return levelDataStorage.getConfig();
	}
}
