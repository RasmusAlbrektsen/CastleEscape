/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import castleescape.business.event.Event;
import castleescape.business.object.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Builder responsible for processing raw item data.
 */
public class ItemBuilder extends InspectableObjectBuilder {

	/**
	 * The name of a data element accepted by this builder.
	 */
	public static final String TRIGGER = "trigger";

	/**
	 * If reading a use event, this will be the name of the inspectable object
	 * that can trigger the use event currently being read. Otherwise this will
	 * be null.
	 */
	private String trigger;

	/**
	 * The use events of the item. The key is the name of the trigger, and the
	 * value is the list of events associated with that trigger.
	 */
	private final Map<String, List<Event>> useEvents;

	/**
	 * Constructs a new item builder.
	 */
	public ItemBuilder() {
		super();
		useEvents = new HashMap<>();
	}

	/**
	 * Called when this builder is notified of a new event. This will reset the
	 * event word, event parameter map and event trigger.
	 */
	@Override
	protected void readNewEvent() {
		//Perform the logic in the super method as well
		super.readNewEvent();

		//Reset the event trigger in case the next event to be read is not a use
		//event
		trigger = null;
	}

	@Override
	protected void readEventParameter(String name, String value) {
		//In the case that the event parameter is the trigger element, we want
		//to handle it in a special way. Otherwise just call the super method.
		if (TRIGGER.equals(name)) {
			//In the case of the trigger element we are reading a use event, and
			//the name of its trigger is the parameter value
			trigger = value;
		} else {
			super.readEventParameter(name, value);
		}
	}

	@Override
	protected void buildEvent() {
		//If we have read a use event we want to handle that in a special way.
		//Otherwise just call the super method.
		if (trigger == null) {
			super.buildEvent();
		} else {
			//Use events are saved in the use events map. If a list of use
			//events already exists for this trigger, we add to that list.
			//Otherwise we make a new list.
			List<Event> useEventsForTrigger = useEvents.get(trigger);
			Event event = new Event(currentEventWord, currentEventParameters);

			if (useEventsForTrigger == null) {
				//Make new list
				useEventsForTrigger = new ArrayList<>();
				useEventsForTrigger.add(event);
				useEvents.put(trigger, useEventsForTrigger);
			} else {
				//Add to existing list
				useEventsForTrigger.add(event);
			}
		}
	}

	@Override
	public void build(LevelDataStorage dataStorage) {
		//Construct new item
		result = new Item(name, description);

		//Add inspect events
		for (Event event : inspectEvents) {
			result.addInspectEvent(event);
		}

		//Add use events
		for (Entry<String, List<Event>> entry : useEvents.entrySet()) {
			((Item) result).addObjectInteraction(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Item getResult() {
		//We know that it is safe to cast result to item, unless the super class
		//has given result a value outside of the build() method, which should
		//not happen
		return (Item) result;
	}
}
