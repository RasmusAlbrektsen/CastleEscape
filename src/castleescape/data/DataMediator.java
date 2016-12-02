/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import castleescape.business.framework.Configuration;
import castleescape.business.framework.Room;
import castleescape.business.framework.Score;
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
 */
public class DataMediator {

	/**
	 * The object responsible for reading level data.
	 */
	private final LevelDataReader levelDataReader;
	private final ScoreFileManager scoreFileManager;

	/**
	 * Constructs a new data mediator.
	 */
	public DataMediator() {
		levelDataReader = new LevelDataReader();
		scoreFileManager = new ScoreFileManager();
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
	 * Read all data of the level with the specified name. The level data can
	 * then be retrieved using the methods
	 * {@link #getInspectableObjects()}, {@link #getItems()} and
	 * {@link #getRooms()}.
	 *
	 * @param levelName the name of the level to read
	 */
	public void readLevelData(String levelName) {
		//Pass call to level data reader
		levelDataReader.readLevel(levelName);

		//Pass call to score file manager
		scoreFileManager.readScores(levelName);
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

	/**
	 * Read the score data for the specified level. This data can be retrieved
	 * by calling {@link #getScores()}.
	 *
	 * @param levelName the name of the level to read
	 */
	public void readScoreData(String levelName) {
		scoreFileManager.readScores(levelName);
	}
	
	/**
	 * Save the specified score to the score file associated with the specified
	 * level name. If no such file exists, it will be created when calling this
	 * method.
	 *
	 * @param levelName the name of the level for which to save the score
	 * @param score     the score to save
	 */
	public void saveScoreData(String levelName, Score score) {
		scoreFileManager.saveScore(levelName, score);
	}

	/**
	 * Get the scores that were read during the last call to
	 * {@link #readScoreData(String)}.
	 *
	 * @return the scores that were read last
	 */
	public List<Score> getScores() {
		return scoreFileManager.getScores();
	}
}
