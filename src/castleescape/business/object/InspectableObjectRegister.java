/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.object;

import java.util.HashMap;

/**
 * Instances of this class are responsible for keeping references to all
 * {@link InspectableObject inspectable objects}, including {@link Item items}
 * that have been read into the game during startup.
 */
public class InspectableObjectRegister {

	/**
	 * Internal HashMap for storing inspectable objects. The keys in the HashMap
	 * are the names of the inspectable objects which are thus guaranteed to be
	 * unique.
	 */
	private final HashMap<String, InspectableObject> objectStore;

	/**
	 * Constructs a new register for inspectable objects and items.
	 */
	public InspectableObjectRegister() {
		objectStore = new HashMap<>();
	}

	/**
	 * Register the specified inspectable object. This method should also be
	 * used to register items.
	 *
	 * @param object the inspectable object to register
	 */
	public void registerInspectableObject(InspectableObject object) {
		objectStore.put(object.getName(), object);
	}

	/**
	 * Get the inspectable object with the specified name.
	 *
	 * @param name the name of the inspectable object
	 * @return the inspectable object with the specified name, or null if no
	 *         such inspectable object exists
	 */
	public InspectableObject getAsInspectableObject(String name) {
		return objectStore.get(name);
	}

	/**
	 * Get the item with the specified name.
	 *
	 * @param name the name of the item
	 * @return the item with the specified name, or null if no such item exists
	 */
	public Item getAsItem(String name) {
		//Get the object with the specified name as an inspectable object
		InspectableObject obj = getAsInspectableObject(name);

		//If an object was retrieved and is of type Item, cast and return it.
		//The keyword instanceof test whether the object to its left is an
		//instance created from the class (we can view it as a blueprint) on the
		//right.
		if (obj instanceof Item) {
			return (Item) obj;
		}

		//Otherwise return null, because there was no Item with the specified
		//name
		return null;
	}
}
