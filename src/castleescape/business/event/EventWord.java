package castleescape.business.event;

/**
 * Enum holding all event types. String representations are as it would look
 * like in XML files.
 */
public enum EventWord {
	ADD_ROOM_ITEM("addRoomItem", 80),
	ADD_PLAYER_ITEM("addPlayerItem", 80),
	REMOVE_ROOM_ITEM("removeRoomItem", 100),
	REMOVE_PLAYER_ITEM("removePlayerItem", 100),
	MAKE_NOISE("makeNoise", 20),
	SET_DESCRIPTION("setDescription", 50),
	SET_OBJECT_DESCRIPTION("setObjectDescription", 50),
	ADD_EXIT("addExit", 30),
	TELEPORT("teleport", 10),
	QUIT("quit", 0);

	/**
	 * The representation of an event word as a string.
	 */
	private final String name;

	/**
	 * The importance of this event type. The more important an event type is,
	 * the earlier it will be executed among a series of events
	 */
	private final int weight;

	/**
	 * Private constructor for new event words.
	 *
	 * @param name   the string representation of this event word
	 * @param weight the importance of this event type. The more important an
	 *               event type is, the earlier it will be executed among a
	 *               series of events
	 */
	EventWord(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	/**
	 * Get the weight of this event word.
	 * 
	 * @return the weight of this event word
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Get the {@link EventWord} object associated with the specified event
	 * string. If the string is not recognized as an event or is null, this
	 * method will throw an {@link IllegalArgumentException}.
	 *
	 * @param eventName the string representation of an event
	 * @return the {@link EventWord} equivalent to the specified event string,
	 * @throws IllegalArgumentException if the event string was not recognized
	 */
	public static EventWord getEventWord(String eventName) {
		//Go through all event words
		for (EventWord eventWord : EventWord.values()) {

			//If the eventWord string equals the string representation of the
			//current event word, then return it, because we found a match
			if (eventWord.toString().equalsIgnoreCase(eventName)) {
				return eventWord;
			}
		}

		throw new IllegalArgumentException("No event has the given type: " + eventName);
	}

	/**
	 * Get the string representation of this event word object. This will return
	 * the string representation specified in the object's constructor.
	 *
	 * @return the string representation of this event word object
	 */
	@Override
	public String toString() {
		return name;
	}
}
