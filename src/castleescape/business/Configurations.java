package castleescape.business;


import util.InvalidXMLException;
import util.XMLRoomExitBuilder;

/**
 * Created by Alex on 02/11/2016. Class containing all constants and
 * configurable variables
 */
public class Configurations {

	private static int MONSTER_MOVE_ROOM_TIME;
	private static double MONSTER_MOVE_CHANCE;
	private static String SAFE_ROOM_NAME;
	private static String START_ROOM_NAME;
	private static String MONSTER_START_ROOM_NAME;

	public Configurations(String startRoom, String safeRoom, String monsterStartRoom, double monsterMoveChance, int monsterMoveRoomTime){
		if (startRoom != null) {
			START_ROOM_NAME=startRoom;
		}else {
			//default value to prevent crashes.
			System.out.println("No startroom configuration found, using default value");
			START_ROOM_NAME= XMLRoomExitBuilder.getRooms().keySet().toArray(new String[]{})[0];
		}
		if (safeRoom != null) {
			SAFE_ROOM_NAME=safeRoom;
		}else {
			//default value to prevent crashes.
			System.out.println("No saferoom configuration found, using default value");
			SAFE_ROOM_NAME = XMLRoomExitBuilder.getRooms().keySet().toArray(new String[]{})[0];
		}
		if (monsterStartRoom != null) {
			MONSTER_START_ROOM_NAME=monsterStartRoom;
		}else {
			System.out.println("No monsterstartroom configuration found, using default value");
			MONSTER_START_ROOM_NAME=XMLRoomExitBuilder.getRooms().keySet().toArray(new String[]{})[0];
		}
		if (monsterMoveChance != 0.0) {
			MONSTER_MOVE_CHANCE=monsterMoveChance;
		}else{
			System.out.println("No monstermovechance found, using default value");
			MONSTER_MOVE_CHANCE=0.2;
		}
		if (monsterMoveRoomTime != 0) {
			MONSTER_MOVE_ROOM_TIME=monsterMoveRoomTime;
		}else {
			System.out.println("No monster move time found, using default value");
			MONSTER_MOVE_ROOM_TIME=6000;
		}
	}

	public static int getMonsterMoveRoomTime() {
		if (MONSTER_MOVE_ROOM_TIME == 0) {
			throw new InvalidXMLException("Missing config file");
		}
		return MONSTER_MOVE_ROOM_TIME;
	}

	public static double getMonsterMoveChance() {
		if (MONSTER_MOVE_ROOM_TIME ==0.0) {
			throw new InvalidXMLException("Missing config file");
		}
		return MONSTER_MOVE_CHANCE;
	}

	public static String getSafeRoomName() {
		if (SAFE_ROOM_NAME == null) {
			throw new InvalidXMLException("Missing config file");
		}
		return SAFE_ROOM_NAME;
	}

	public static String getStartRoomName() {
		if (START_ROOM_NAME == null) {
			throw new InvalidXMLException("Missing config file");
		}
		return START_ROOM_NAME;
	}

	public static String getMonsterStartRoomName() {
		if (MONSTER_START_ROOM_NAME == null) {
			throw new InvalidXMLException("Missing config file");
		}
		return MONSTER_START_ROOM_NAME;
	}
}
