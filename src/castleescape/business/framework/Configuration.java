/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.framework;

/**
 * An immutable object to store the game's configurations.
 * 
 * @author Kasper
 */
public class Configuration {

	/**
	 * The room in which the player starts.
	 */
	private final Room startRoom;

	/**
	 * The safe room.
	 */
	private final Room safeRoom;

	/**
	 * The room in which the monster starts.
	 */
	private final Room monsterStartRoom;

	/**
	 * The chance that the monster will move, in percent.
	 */
	private final double monsterMoveChance;

	/**
	 * The time that it takes the monster to move one room, in milliseconds.
	 */
	private final int monsterMoveTime;

	/**
	 * Constructs a new configuration object.
	 *
	 * @param startRoom         the room in which the player starts
	 * @param safeRoom          the safe room
	 * @param monsterStartRoom  the room in which the monster starts
	 * @param monsterMoveChance the chance that the monster will move, in
	 *                          percent
	 * @param monsterMoveTime   the time that it takes the monster to move one
	 *                          room, in milliseconds
	 */
	public Configuration(Room startRoom, Room safeRoom, Room monsterStartRoom, double monsterMoveChance, int monsterMoveTime) {
		this.startRoom = startRoom;
		this.safeRoom = safeRoom;
		this.monsterStartRoom = monsterStartRoom;
		this.monsterMoveChance = monsterMoveChance;
		this.monsterMoveTime = monsterMoveTime;
	}

	/**
	 * Get the room in which the player starts.
	 *
	 * @return the room in which the player starts
	 */
	public Room getStartRoom() {
		return startRoom;
	}

	/**
	 * Get the safe room
	 *
	 * @return the safe room
	 */
	public Room getSafeRoom() {
		return safeRoom;
	}

	/**
	 * Get the room in which the monster starts
	 *
	 * @return the room in which the monster starts
	 */
	public Room getMonsterStartRoom() {
		return monsterStartRoom;
	}

	/**
	 * Get the chance that the monster will move, in percent
	 *
	 * @return the chance that the monster will move, in percent
	 */
	public double getMonsterMoveChance() {
		return monsterMoveChance;
	}

	/**
	 * Get the time that it takes the monster to move one room, in milliseconds
	 *
	 * @return the time that it takes the monster to move one room, in
	 *         milliseconds
	 */
	public int getMonsterMoveTime() {
		return monsterMoveTime;
	}
}
