package castleescape.business.event;

/**
 * Created by Alex on 13/10/2016. Holds all event types. String representations
 * are as it would look like in XML files.
 */
public enum EventWord {
	ADD_ROOM_ITEM("addRoomItem"),
	ADD_PLAYER_ITEM("addPlayerItem"),
	REMOVE_ROOM_ITEM("removeRoomItem"),
	REMOVE_PLAYER_ITEM("removePlayerItem"),
	MAKE_NOISE("makeNoise"),
	SET_DESCRIPTION("setDescription"),
	ADD_EXIT("addExit"),
	QUIT("quit");

	/**
	 * The representation of an event word as a string.
	 */
	private final String name;

	/**
	 * Private constructor for new event words.
	 *
	 * @param name the string representation of this event word
	 */
	EventWord(String name) {
		this.name = name;
	}

	/**
	 * Get the {@link EventWord} object associated with the specified event
	 * string. If the string is not recognized as an event or is null, this
	 * method will throw an {@link IllegalArgumentException}.
	 *
	 * @param eventName the string representation of an event
	 * @return the {@link EventWord} equivalent to the specified event string,
	 *         or {@link IllegalArgumentException} if the event string was not
	 *         recognized
	 */
	public static EventWord getEventWord(String eventName) {
		//Go through all event words
		for (EventWord eventWord : EventWord.values()) {

			//If the eventWord string equals the string representation of the
			//current event word, then return it, because we found a match.
			//Notice that if the eventWord string is "?", then EventWord.UNKNOWN
			//will be returned here, but that is fine, as "?" is not a valid
			//event
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
