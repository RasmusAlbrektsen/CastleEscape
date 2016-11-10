package castleescape.business.event;

import castleescape.business.framework.Game;
import java.util.Map;

/**
 * Created by Alex on 13/10/2016. Contains all data for a given event.
 * Parameters is stored as a key-value pair to account for inconsistencies in
 * the parameter order in the XML format.
 */
public class Event {

	/**
	 * String representation of an event parameter.
	 */
	public static final String DIRECTION = "direction",
			DESTINATION = "destination",
			ITEM = "item",
			WEIGHT = "weight",
			ROOM = "room",
			DESCRIPTION = "description",
			OBJECT = "object";

	/**
	 * The event word.
	 */
	private final EventWord eventWord;

	/**
	 * HashMap containing the event parameters.
	 */
	private final Map<String, String> eventParams;

	/**
	 * Constructs a new event with the specified type and the specified
	 * parameters.
	 *
	 * @param type   the type of this event
	 * @param params the event parameters
	 */
	public Event(EventWord type, Map<String, String> params) {
		eventWord = type;
		eventParams = params;
	}

	/**
	 * Returns the type of event
	 *
	 * @return the name of the event to be executed.
	 */
	public EventWord getEventWord() {
		return eventWord;
	}

	/**
	 * Returns a parameter associated with the given name.
	 *
	 * @param name the name of the parameter to return.
	 * @return the Value of the parameter.
	 */
	public String getEventParam(String name) {
		return eventParams.get(name);
	}

	/**
	 * Execute this event. This method will also award the player with points.
	 *
	 * @param game the game object
	 */
	public void execute(Game game) {
		//Get the event executer to execute this specific event and call its
		//execute() method
		game.getEventExecuter(eventWord).execute(game, this);
		
		//Add points for every event executed
		game.getScoreManager().addPoints(1);
	}
}
