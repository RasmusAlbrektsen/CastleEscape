package util;

import castleescape.business.event.Event;
import castleescape.business.event.EventWord;
import castleescape.business.object.InspectableObject;
import castleescape.business.object.InspectableObjectRegister;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for building inspectableObjects. Instantiated by {@link XMLContentBuilder}
 * Calling build, builds the inspectableObject and instantiates it.
 * Created by Alex on 26/10/2016.
 */
class XMLInspectableObjectBulder implements IBuilder {
	private final String name;
	private String description;
	private EventWord eventType;
	private HashMap<String, String> eventParameters = new HashMap<>();
	private final ArrayList<Event> events = new ArrayList<>();

	XMLInspectableObjectBulder(String name) {
		this.name = name;
	}

	/**
	 * Adds an event parameter to an inspectEvent.
	 *
	 * @param name      the name of the parameter.
	 * @param parameter the data of the parameter.
	 */
	void addEventParameter(String name, String parameter) {
		eventParameters.put(name, parameter);
	}

	/**
	 * Add a type for an event.
	 *
	 * @param type the {@link EventWord} associated with this event.
	 */
	void addEventType(EventWord type) {
		eventType = type;
	}

	/**
	 * Builds an event, and readies the builder for a new event.
	 */
	void buildEvent() {
		events.add(new Event(eventType, eventParameters));
		eventType = null;
		eventParameters = new HashMap<>();
	}

	/**
	 * Setter for the description of the object.
	 *
	 * @param description the description shown when inspecting the object
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Builds the object and registers the events.
	 */
	public void build() {
		InspectableObject builtObject = new InspectableObject(name, description);
		for (Event event : events) {
			builtObject.addInspectEvent(event);
		}
		InspectableObjectRegister.registerInspectableObject(builtObject);
	}
}
