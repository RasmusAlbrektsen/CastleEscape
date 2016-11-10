package util;

import castleescape.business.framework.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex on 26/10/2016.
 * Stores a list of instantiated rooms and exits. Calling build adds the exits to each room.
 */
public class XMLRoomExitBuilder {
	private static boolean isRoomsBuilt = false;
	private static final ArrayList<Room> rooms = new ArrayList<>();
	private static final HashMap<String, HashMap<String, String>> roomsExits = new HashMap<>();

	/**
	 * Adds an exit to a room.
	 *
	 * @param roomName    The name of the room to add an exit to.
	 * @param direction   The direction of the exit.
	 * @param destination The name of the room the exit leads to.
	 */
	static void addExit(String roomName, String direction, String destination) {
		if (roomsExits.containsKey(roomName)) {
			roomsExits.get(roomName).put(direction, destination);
		} else {
			roomsExits.put(roomName, new HashMap<>());
			roomsExits.get(roomName).put(direction, destination);
		}
	}

	/**
	 * Builds exits on all the rooms.
	 */
	public static void build() {
		for (Room room : rooms) {                                                           //For each room:
			HashMap<String, String> roomExits = roomsExits.get(room.getRoomName());           //Store the rooms' exits
			if (roomExits == null) continue;
			for (String direction : roomExits.keySet()) {                                   //For each exit direction:
				for (Room neighborCandidate : rooms) {                                      //for each room:
					if (neighborCandidate.getRoomName().equals(roomExits.get(direction))) {  //if the name of the room matches the name of the room in the direction:
						room.setExit(direction, neighborCandidate);                          //add the NeighborCandidate to the rooms' exit
					}
				}
			}
		}
		isRoomsBuilt = true;
	}

	/**
	 * Adds a room to the list of rooms.
	 * @param room the room to add to the list.
	 */
	static void addRoom(Room room) {
		System.out.println("room added!");
		rooms.add(room);
	}

	/**
	 * Getter for the rooms, once the exits has been built. Calling this prematurely returns null.
	 * @return The instantiated rooms with exits built.
	 */
	public static HashMap<String, Room> getRooms() {
		if (isRoomsBuilt) {
			HashMap<String, Room> returnValue = new HashMap<>();
			for (Room room : rooms) {
				returnValue.put(room.getRoomName(), room);
			}
			return returnValue;
		}
		return null;
	}
}