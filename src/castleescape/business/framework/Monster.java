/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.framework;

import java.util.ArrayDeque;
import castleescape.business.Configurations;
import castleescape.business.ViewUtil;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class defining the monster in the game. This class contains instance methods
 * for keeping track of the monster's state (hunting or idle) and whether the
 * player has been caught at any given time.
 *
 * @author DitteKoustrup, Kasper, Alex
 * @see <a href="https://codeshare.io/4jocK">Codeshare</a>
 */
public class Monster {

	/**
	 * The warning message displayed when the monster is hunting the player.
	 */
	private final String WARNING_MESSAGE = "The monster is coming!\nTHE MONSTER IS COMING";

	/**
	 * Whether the monster is currently hunting the player.
	 */
	private boolean hunting;

	/**
	 * Whether the monster is idling, waiting to meet the player.
	 */
	private boolean waitingForPlayer = true;

	/**
	 * The time at which the monster last began hunting the player, in
	 * milliseconds.
	 */
	private long startTime;

	/**
	 * The last time the monster moved to another room during a hunt, in
	 * milliseconds.
	 */
	private long lastMove;

	/**
	 * The amount of time that the player has to escape the monster, in
	 * milliseconds.
	 */
	private int countDown;

	/**
	 * The current location of the monster.
	 */
	private Room currentRoom;

	/**
	 * The path that the monster has to follow to chase the player.
	 */
	private Deque<Room> chasePath;

	/**
	 * Constructs a new monster that starts in the specified room.
	 *
	 * @param location the room that the monster is in initially
	 */
	public Monster(Room location) {
		currentRoom = location;
	}

	/**
	 * Make the monster hunt the player. Calling this method while the player is
	 * already being hunted will have no effect.
	 *
	 * @param playerRoom the room in which the player is
	 */
	public void setHunting(Room playerRoom) {
		//Do nothing if the player is already being hunted
		if (hunting == true) {
			return;
		}

		//Start hunting the player
		//Warn the user
		ViewUtil.println(WARNING_MESSAGE);

		//Stop waiting the first time the player is hunted
		waitingForPlayer = false;
		hunting = true;

		//Calculate chase path
		chasePath = this.getPathToRoom(playerRoom);

		//Set escape time based on distance between monster and player
		startTime = System.currentTimeMillis(); //The amount of milliseconds since midnight, January 1, 1970 UTC
		lastMove = startTime;
		countDown = chasePath.size() * Configurations.getMonsterMoveRoomTime();
	}

	/**
	 * Called whenever the player enters a command. Makes the monster randomly
	 * move between rooms or hunt the player, depending on its state. If the
	 * monster enters the room in which the player is, it will begin hunting the
	 * player.
	 *
	 * @param game the game object
	 */
	public void notifyOfCommand(Game game) {
		//If the monster is hunting the player, warn the player
		if (isHunting()) {
			ViewUtil.print(WARNING_MESSAGE);

			//Move the monster towards the player if enough time has passed.
			//This may need to happen multiple times. Stop moving if the monster
			//has reached the player (chasePath.size() is 1)
			long now = System.currentTimeMillis();
			while (now - lastMove >= Configurations.getMonsterMoveRoomTime() && chasePath.size() > 1) {
				lastMove += Configurations.getMonsterMoveRoomTime();

				//Move to the next room along the path, and remove the current
				//room from the path
				chasePath.pop();
				currentRoom = chasePath.peekFirst();
			}
			return;
		}

		//If the monster is waiting for the player, no need to do any more.
		//Otherwise determine if the monster should move to a random room
		//connected to the current room.
		if (!waitingForPlayer) {

			//Roll a random number to determine if the monster should move to
			//another room
			if (Math.random() < Configurations.getMonsterMoveChance()) {

				//Choose a random room among the exits from the current room. If
				//no exits are present, do nothing
				Room[] exits = currentRoom.getExits().values().toArray(new Room[]{});
				if (exits.length > 0) {
					Room newRoom;

					//The monster cannot enter the safe room. If it tries to,
					//choose another room. This will produce an infinite loop if
					//the safe room is the only way for the monster to go, but
					//in that case the layout of the rooms in the game violate
					//the requirements.
					do {
						newRoom = exits[(int) (Math.random() * exits.length)];
					} while (newRoom.getRoomName().equals(Configurations.getSafeRoomName()));

					currentRoom = newRoom;

					//If the monster entered the player's room, hunt the
					//player
					if (newRoom == game.getCurrentRoom()) {
						setHunting(game.getCurrentRoom());
					}
				}
			}
		}
	}

	/**
	 * Should be called whenever the player changes location. If the player is
	 * hunted this method will add or remove time based on whether the player
	 * moves away from or towards the monster.
	 *
	 * @param destination the room the player moves to
	 */
	public void notifyOfGo(Room destination) {
		//If the player entered the same room as the monster, hunt the player.
		//If the player is already being hunted this will merely print a message
		//to the user interface, as setHunting() has no effect in this case
		if (destination.equals(currentRoom)) {
			ViewUtil.print("You've walked right into the same room as the monster!");
			setHunting(destination);
		}

		//If the player is hunted, test if the player moved towards or away from
		//the monster, or if the move made no difference
		if (isHunting()) {
			//The distance to the monster before the move
			int lastDistance = chasePath.size();

			//The new path to the player
			chasePath = getPathToRoom(destination);

			if (chasePath.size() > lastDistance) {
				//Player ran away from the monster, get more time
				addEscapeTime(Configurations.getMonsterMoveRoomTime());
			} else if (chasePath.size() < lastDistance) {
				//Player ran towards the monster, loose time
				addEscapeTime(-Configurations.getMonsterMoveRoomTime());
			} //Else no difference in distance, no time change
		}
	}

	/**
	 * The room the monster is in.
	 *
	 * @return the room the monster is currently in.
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Make the monster stop hunting the player. Calling this method while the
	 * monster is already idle will have no effect.
	 */
	public void setIdle() {
		this.hunting = false;
	}

	/**
	 * Test whether the monster is currently hunting the player.
	 *
	 * @return {@code true} if the monster is hunting the player, {@code false}
	 *         otherwise
	 */
	public boolean isHunting() {
		return hunting;
	}

	public boolean isWaitingForPlayer() {
		return waitingForPlayer;
	}

	/**
	 * Test whether the monster has caught the player at this time. If the
	 * monster is not hunting the player this will always return false.
	 *
	 * @return {@code true} if the monster has caught the player, {@code false}
	 *         otherwise
	 */
	public boolean isPlayerCaught() {
		//If the monster is not hunting the player then we should always return false
		if (!hunting) {
			return false;
		}

		//Get the current time
		long now = System.currentTimeMillis();

		//Calculate how long the monster has been hunting the player
		long elapsedTime = now - startTime;

		//If the monster has been hunting the player (elapsedTime) for longer
		//than the amount of time the player has to escape (countDown), then the
		//player is caught and we return true
		if (elapsedTime > countDown) {
			return true;
		}

		//The player still has time left to escape, so return false
		return false;
	}

	/**
	 * Add more time for the player to escape the monster.
	 *
	 * @param extraTime the amount of extra time for the player to escape the
	 *                  monster, in milliseconds
	 */
	public void addEscapeTime(int extraTime) {
		countDown += extraTime; //Add more time for the player to escape
	}

	/**
	 * Get the optimal path from the room in which the monster is to the
	 * specified room.
	 *
	 * @param goal the room to find
	 * @return the optimal path to the specified room, or null if no path exists
	 */
	private Deque<Room> getPathToRoom(Room goal) {
		//The path finding algorithm below is a simplified version of the A*
		//algorithm.

		//For each room (key) which room it can most efficiently be reached from
		//(value)	
		Map<Room, Room> optimalRoomConnections = new HashMap<>();

		//List of rooms that have to be evaluated as possible nodes in the path
		List<Room> open = new ArrayList<>();

		//List of rooms that have already been evaluated, and are thus not
		//interesting anymore. There is no point in going to a room that has
		//already been found to be further away from the goal than our current
		//position. This is because every time a room is tested it is currently
		//the room closest to the goal.
		List<Room> closed = new ArrayList<>();

		//The current room is the first to be evaluated
		open.add(getCurrentRoom());

		//While there are rooms to evaluate
		//We will return a path as soon as it is found, thus breaking out of
		//this loop
		while (!open.isEmpty()) {
			//The first room in the open list is always the room that requires
			//the shortest path to reach because it was added the earliest
			Room current = open.get(0);

			//If we reached the specified room, construct and return the optimal
			//path
			if (current == goal) {
				return getOptimalPath(optimalRoomConnections, goal);
			}

			open.remove(0);
			closed.add(current);

			//Evaluate all neighbor rooms
			for (Room neighbor : current.getExits().values()) {
				if (closed.contains(neighbor)) {
					//If the neighbor has already been evaluated, skip it
					continue;
				} else if (!open.contains(neighbor)) {
					//If the neighbor is not already awaiting evaluation,
					//schedule it
					open.add(neighbor);
				}

				//Getting to this neighbor room is best done by coming from the
				//current room
				optimalRoomConnections.put(neighbor, current);
			}
		}

		//We made it through the loop without finding a path, so no path exists
		return null;
	}

	/**
	 * Get the optimal path to the specified room from a map of optimal room
	 * connections.
	 *
	 * @param optimalRoomConnections the optimal room connections
	 * @param goal                   the room to find
	 * @return the optimal path
	 */
	private Deque<Room> getOptimalPath(Map<Room, Room> optimalRoomConnections, Room goal) {
		//List to hold the optimal path
		Deque<Room> optimalPath = new ArrayDeque<>();

		//The last room in the path is the goal
		optimalPath.add(goal);

		Room currentRoom = goal;

		//For every room along the path (starting with the goal ^) get the
		//previous room in the path and add it to the start of the deque
		while (optimalRoomConnections.get(currentRoom) != null) {
			Room neighbor = optimalRoomConnections.get(currentRoom);
			optimalPath.addFirst(neighbor);
			currentRoom = neighbor;
		}

		//Return the optimal path
		return optimalPath;
	}
}
