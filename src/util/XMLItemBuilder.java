package util;

import castleescape.business.event.Event;
import castleescape.business.event.EventWord;
import castleescape.business.object.InspectableObjectRegister;
import castleescape.business.object.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alex on 26/10/2016.
 * Class for building items. Instantiated by {@link XMLContentBuilder}
 * Calling build, builds the item and instantiates it.
 */
class XMLItemBuilder implements IBuilder {
	private final String name;
	private String description;

	private EventWord eventType;
	private String eventTrigger;
	private HashMap<String, String> eventParameters = new HashMap<>();
	private final HashMap<String, ArrayList<Event>> useEvents = new HashMap<>();
	private final ArrayList<Event> inspectEvents = new ArrayList<>();

	XMLItemBuilder(String name) {
		this.name = name;
	}

	/**
	 * Sets the description of the item.
	 *
	 * @param description the description of the item.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Adds an event parameter to an un-built event.
	 * @param name the name of the parameter.
	 * @param parameter the value og the parameter.
	 */
	void addEventParameter(String name, String parameter) {
		eventParameters.put(name, parameter);
	}

	/**
	 * Adds a {@link EventWord} type to the current un-built event.
	 * @param type the {@link EventWord} type of event.
	 */
	void addEventType(EventWord type) {
		eventType = type;
	}

	/**
	 * Adds a trigger object to the current un-built event.
	 * @param trigger the name of the object to act as a trigger.
	 */
	void addEventTrigger(String trigger) {
		eventTrigger = trigger;
	}

	/**
	 * Builds the current un-build event and readies the builder for a new one.
	 */
	void buildEvent() {
		if (eventTrigger != null) {
			useEvents.putIfAbsent(eventTrigger, new ArrayList<>());
			useEvents.get(eventTrigger).add(new Event(eventType, eventParameters));
			eventTrigger = null;
		} else {
			inspectEvents.add(new Event(eventType, eventParameters));
		}
		eventType = null;
		eventParameters = new HashMap<>();
	}

	/**
	 * Builds the item and instantiates the events.
	 */
	public void build() {
		Item builtItem = new Item(name, description);
		for (Event inspectEvent : inspectEvents) {
			builtItem.addInspectEvent(inspectEvent);
		}
		for (String triggerName : useEvents.keySet()) {
			builtItem.addObjectInteraction(triggerName, useEvents.get(triggerName));
		}
		InspectableObjectRegister.registerInspectableObject(builtItem);
	}
}