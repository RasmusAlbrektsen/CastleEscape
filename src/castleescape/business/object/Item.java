/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.object;

import castleescape.business.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class describing a type og inspectable object that can be kept in an
 * {@link Inventory} and used with other inspectable objects to generate game
 * events.
 *
 * @author Christian, Kasper, Sebastian
 * @see <a href="https://codeshare.io/pvTlZ">Codeshare</a>
 */
public class Item extends InspectableObject {

	/**
	 * A HashMap that keeps track of which inspectable objects this item can be
	 * used with, and the resulting events.
	 */
	private final HashMap<String, List<Event>> useMap;

	/**
	 * Constructs a new item with the specified name and description.
	 *
	 * @param name        the name of the item
	 * @param description the description of the item
	 */
	public Item(String name, String description) {
		//Super point to the class that we inherit from, in this case
		//InspectableObject
		super(name, description);

		//Initialize useMap
		useMap = new HashMap<>();
	}

	/**
	 * Define a use between this item and some other inspectable object.
	 *
	 * @param otherName   the name of the inspectable object that this item can
	 *                    be used with
	 * @param eventResult the event that should be executed as a result of this
	 *                    use, or null if the use was not defined
	 */
	public void addObjectInteraction(String otherName, Event eventResult) {
		//Get existing list of events, if any
		List<Event> existingEvents = useMap.get(otherName);

		if (existingEvents == null) {
			//If no events have been defined so far, create a new ArrayList to store
			//the events and put the list in the useMap
			existingEvents = new ArrayList<>();
			existingEvents.add(eventResult);
			useMap.put(otherName, existingEvents);
		} else {
			//A list exists, so just add this event to it
			existingEvents.add(eventResult);
		}
	}

	/**
	 * Define a use between this item and some other inspectable object.
	 *
	 * @param otherName the name of the inspectable object that this item can
	 *                  be used with
	 * @param eventList the list of events that should be executed as a result
	 *                  of this use.
	 */
	public void addObjectInteraction(String otherName, List<Event> eventList) {
		//Get existing list of events, if any
		List<Event> existingEvents = useMap.get(otherName);

		if (existingEvents == null) {
			//If no events have been defined so far, add the list
			useMap.put(otherName, eventList);
		} else {
			//A list exists, add all entries to it.
			existingEvents.addAll(eventList);
		}
	}

	/**
	 * Test whether this item can be used with another inspectable object to
	 * generate game events. If not, then using the objects with each other will
	 * have no effect.
	 *
	 * @param otherName the name of the inspectable object to test against this
	 *                  item
	 * @return {@code true} if this item can be used with the other object,
	 *         {@code false} otherwise
	 */
	public boolean canUseWithObject(String otherName) {
		return useMap.containsKey(otherName);
	}

	/**
	 * Use this item with another inspectable object. This method will return a
	 * list of event objects describing what should happen when these items are
	 * used with one another. If the items cannot be used with one another this
	 * method will return an empty list.
	 *
	 * @param otherName the name of the inspectable object to use with this item
	 * @return the events to execute as a result of this interaction, if any
	 */
	public List<Event> useWithObject(String otherName) {
		//Get the list of events to be executed, or null if the interaction is
		//not defined
		List<Event> eventList = useMap.get(otherName);

		//if the interaction is not defined, return an empty list. Otherwise
		//return the list above
		if (eventList == null) {
			return new ArrayList<>();
		}

		return eventList;
	}

}
