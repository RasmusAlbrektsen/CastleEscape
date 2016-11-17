/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.object;

import castleescape.business.event.Event;
import java.util.ArrayList;
import java.util.List;
import castleescape.business.ViewUtil;

/**
 * Class describing an object that can be placed in a room and inspected.
 *
 * @author Christian, Kasper, Sebastian
 * @see <a href="https://codeshare.io/JVaDp">Codeshare</a>
 */
public class InspectableObject {

	/**
	 * The name of this inspectable object. Must be unique, as it is used to
	 * refer to this inspectable object.
	 */
	private final String name;

	/**
	 * The description of this inspectable object.
	 */
	private String description;

	private List<Event> inspectEvents;

	/**
	 * Constructs a new inspectable object, giving it a name and a description.
	 *
	 * @param name        the name of the object
	 * @param description the description of the object
	 */
	public InspectableObject(String name, String description) {
		this.name = name;
		this.description = description;

		//Initialize array of use events
		this.inspectEvents = new ArrayList<>();
	}

	/**
	 * Get the name of this inspectable object.
	 *
	 * @return the name of this inspectable object
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the description of this inspectable object.
	 *
	 * @param description the description of this inspectable object
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the description of this inspectable object.
	 *
	 * @return the description of this inspectable object
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Test if this inspectable object has the specified name. The name of an
	 * inspectable object is used as a unique identifier. This is primarily a
	 * convenience method.
	 *
	 * @param name the name to test against
	 * @return {@code true} if this inspectable object has the specified name,
	 *         {@code false} otherwise
	 */
	public boolean hasName(String name) {
		//Test if the specified name equals the name of this inspectable object,
		//and return the result
		return this.name.equals(name);
	}

	/**
	 * Add an event to be executed when inspecting this inspectable object.
	 *
	 * @param event the event to be executed
	 */
	public void addInspectEvent(Event event) {
		this.inspectEvents.add(event);
	}

	/**
	 * Inspect this object. Inspecting an object means to print out its
	 * description. This method will also return a list of event that should be
	 * executed when inspecting this object.
	 *
	 * @return a list of event that should be executed when inspecting this
	 *         object
	 */
	public List<Event> inspect() {
		ViewUtil.println(description);
		return inspectEvents;
	}

	/**
	 * Get a string representation of this inspectable object which is simply
	 * its name.
	 *
	 * @return a string representation of this inspectable object
	 */
	@Override
	public String toString() {
		return name;
	}

}
