/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import castleescape.business.event.Event;
import castleescape.business.event.EventWord;
import castleescape.business.object.InspectableObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder responsible for processing raw inspectable object data.
 */
public class InspectableObjectBuilder implements IBuilder {

	/**
	 * The name of a data element accepted by this builder.
	 */
	public static final String EVENTS = "events",
			EVENT = "event",
			TYPE = "type";

	/**
	 * The name of the inspectable object.
	 */
	protected String name;

	/**
	 * The description of the inspectable object.
	 */
	protected String description;

	/**
	 * Whether this builder is currently reading a specific event.
	 */
	private boolean readingEvent;

	/**
	 * The event word of the event that is currently being constructed.
	 */
	protected EventWord currentEventWord;

	/**
	 * The event paramaters for the event that is currently being constructed.
	 */
	protected Map<String, String> currentEventParameters;

	/**
	 * The list of inspect events of the inspectable object.
	 */
	protected final List<Event> inspectEvents;

	/**
	 * The inspectable object that has been built. Will be null until
	 * {@link #build()} has been called.
	 */
	protected InspectableObject result;

	/**
	 * Constructs a new inspectable object builder.
	 */
	public InspectableObjectBuilder() {
		inspectEvents = new ArrayList<>();
	}

	@Override
	public void notifyOfElement(String element) {
		//Determine what action should happen when reading the specified element
		switch (element) {
			case EVENT:
				//We are about to read a new event so we reset the current event
				//data and remember that we are reading an event
				readNewEvent();
				readingEvent = true;
				break;
		}

		//For all other elements we do not need to do anything special for now
	}

	@Override
	public void processElement(String element, String content) {
		//Determine what action should happen when reading the specified element
		if (readingEvent) {

			//If we are reading an event we must process the elements in a
			//special way
			switch (element) {
				case TYPE:
					//The content associated with type defines the event word
					readEventWord(content);
					break;
				case EVENT:
					//An event element signals that we are done reading an event
					//so we build the event we have made so far and remember
					//that we are done reading the event
					buildEvent();
					readingEvent = false;
					break;
				default:
					//All other data is regarded as event parameters
					readEventParameter(element, content);
					break;
			}

			//Nothing more to do, so we return
			return;
		}

		//We are not reading events
		switch (element) {
			case NAME:
				//Reading the name
				name = content;
				break;
			case DESCRIPTION:
				//Reading the description
				description = content;
				break;
		}
	}

	/**
	 * Called when this builder is notified of a new event. This will reset the
	 * event word and event parameter map.
	 */
	protected void readNewEvent() {
		//Reset the event word
		currentEventWord = null;

		//Reset the map for holding all event parameters
		currentEventParameters = new HashMap<>();
	}

	/**
	 * Called when this builder is notified of the type of the event currently
	 * being read.
	 *
	 * @param eventName the name of the event that is being read
	 */
	private void readEventWord(String eventName) {
		currentEventWord = EventWord.getEventWord(eventName);
	}

	/**
	 * Called when this builder is notified of a new event parameter.
	 *
	 * @param name  the name of the parameter
	 * @param value the value of the parameter
	 */
	protected void readEventParameter(String name, String value) {
		//Add the new parameter to the parameter map
		currentEventParameters.put(name, value);
	}

	/**
	 * Called when this builder is done receiving data about an event and the
	 * event should be built.
	 */
	protected void buildEvent() {
		//Construct new event and save it
		inspectEvents.add(new Event(currentEventWord, currentEventParameters));
	}

	@Override
	public void build(LevelDataStorage dataStorage) {
		//Construct new inspectable object
		result = new InspectableObject(name, description);

		//Add inspect events
		for (Event event : inspectEvents) {
			result.addInspectEvent(event);
		}
	}

	@Override
	public void postBuild(LevelDataStorage dataStorage) {
		//Nothing to do here
	}

	@Override
	public InspectableObject getResult() {
		return result;
	}
}
