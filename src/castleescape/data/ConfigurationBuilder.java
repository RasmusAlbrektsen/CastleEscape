/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import castleescape.business.framework.Configuration;

/**
 *
 * @author Kasper
 */
public class ConfigurationBuilder implements IBuilder {

	/**
	 * The name of a data element accepted by this builder.
	 */
	public static final String START_ROOM = "startroom",
			SAFE_ROOM = "saferoom",
			MONSTER_START_ROOM = "monsterstartroom",
			MONSTER_MOVE_CHANCE = "monstermovechance",
			MONSTER_MOVE_TIME = "monstermovetime";

	/**
	 * The room in which the player starts.
	 */
	private String startRoom;

	/**
	 * The safe room.
	 */
	private String safeRoom;

	/**
	 * The room in which the monster starts.
	 */
	private String monsterStartRoom;

	/**
	 * The chance that the monster will move, in percent.
	 */
	private double monsterMoveChance;

	/**
	 * The time that it takes the monster to move one room, in milliseconds.
	 */
	private int monsterMoveTime;

	/**
	 * The configuration object that has been built. Will be null until
	 * {@link #build()} has been called.
	 */
	private Configuration result;

	@Override
	public void notifyOfElement(String element) {
		//Nothing to do here
	}

	@Override
	public void processElement(String element, String content) {
		//Determine what action should happen when reading the specified element
		switch (element) {
			case START_ROOM:
				startRoom = content;
				break;
			case SAFE_ROOM:
				safeRoom = content;
				break;
			case MONSTER_START_ROOM:
				monsterStartRoom = content;
				break;
			case MONSTER_MOVE_CHANCE:
				monsterMoveChance = Double.parseDouble(content);
				break;
			case MONSTER_MOVE_TIME:
				monsterMoveTime = Integer.parseInt(content);
				break;
		}
	}

	@Override
	public void build(LevelDataStorage dataStorage) {
		//Construct new configuration object
		result = new Configuration(dataStorage.getRoom(startRoom),
				dataStorage.getRoom(safeRoom),
				dataStorage.getRoom(monsterStartRoom),
				monsterMoveChance,
				monsterMoveTime);
	}

	@Override
	public void postBuild(LevelDataStorage dataStorage) {
		//Nothing to do here
	}

	@Override
	public Configuration getResult() {
		return result;
	}
}
