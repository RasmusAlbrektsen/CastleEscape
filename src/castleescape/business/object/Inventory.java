/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Class describing an object that can store items.
 *
 * @author Christian, Kasper, Sebastian
 * @see <a href="https://codeshare.io/pqEfD">Codeshare</a>
 */
public class Inventory {

	/**
	 * Used to specify that an inventory is not limited in capacity.
	 */
	private static final int CAPACITY_UNLIMITED = -1;

	/**
	 * Arraylist for storing the contents of this inventory. An arraylist was
	 * chosen over an array because it is dynamic, which is a requirement for
	 * some inventories. The arraylist is final because it is never changed.
	 */
	private final List<Item> content;

	/**
	 * The capacity of this inventory, if it is limited. If not, this variable
	 * will store {@link #CAPACITY_UNLIMITED}. This variable is final because
	 * the inventory class has no setter for it.
	 */
	private final int capacity;

	/**
	 * Constructs a new inventory with unlimited capacity.
	 */
	public Inventory() {
		//Call the constructor of this class that takes an int parameter
		this(CAPACITY_UNLIMITED);
	}

	/**
	 * Constructs a new inventory with the specified capacity. If the inventory
	 * should not be limited in size the parameter {@link #CAPACITY_UNLIMITED}
	 * should be passed instead.
	 *
	 * @param capacity the capacity of the inventory, or
	 *                 {@link #CAPACITY_UNLIMITED} if the inventory should not
	 *                 be limited in size
	 */
	public Inventory(int capacity) {
		this.content = new ArrayList<>();
		this.capacity = capacity;
	}

	/**
	 * Get the capacity of this inventory.
	 *
	 * @return the capacity of this inventory, or {@link #CAPACITY_UNLIMITED} if
	 *         the capacity is not limited
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Get the amount of items inside this inventory.
	 *
	 * @return the amount if items inside this inventory
	 */
	public int getItemCount() {
		return content.size();
	}

	/**
	 * Add an item to this inventory. If the item could not be added this method
	 * does nothing. This method will return a boolean based on whether adding
	 * the item was a success.
	 *
	 * @param item the item to add
	 * @return {@code true} if the item could be added, {@code false} otherwise
	 * @throws IllegalArgumentException if the item is null
	 */
	public boolean addItem(Item item) {
		//We do not permit null elements in an inventory
		if (item == null) {
			throw new IllegalArgumentException("Inventories do not permit null items!");
		}

		//If the capacity is unlimited, simply add the item (the second test
		//below will be skipped as an or gate only requires one input to be true
		//to have an output of true). If not, we must perform the second test to
		//see if the current amount of items in this inventory is smaller than the
		//limited capacity, and if it is, add the item.
		if (capacity == CAPACITY_UNLIMITED || content.size() < capacity) {

			//We do not want duplicate items, so we make sure that the item is
			//not already present in this inventory
			if (!content.contains(item)) {
				content.add(item);
				return true;
			}
		}

		//The inventory had a limited capacity and was full, so the item could
		//not be added, or the item was already present in this inventory
		return false;
	}

	/**
	 * Remove an item from this inventory. This method will return a boolean
	 * based on whether removing the item was a success.
	 *
	 * @param item the item to remove
	 * @return {@code true} if the item was removed, {@code false} if the item
	 *         was not present in this inventory
	 */
	public boolean removeItem(Item item) {
		return content.remove(item);
	}

	/**
	 * Move the specified item from this inventory to another inventory. If
	 * either the item does not exist in this inventory or the other inventory
	 * does not permit adding the item this method will do nothing. This method
	 * returns a boolean based on whether the move operation was a success.
	 *
	 * @param item  the item to move from this inventory
	 * @param other the inventory to move the item to
	 * @return {@code true} if the item was successfully moved, {@code false}
	 *         otherwise
	 */
	public boolean moveItem(Item item, Inventory other) {
		//We can only move the item from this inventory to another if it is
		//present in this inventory
		if (content.contains(item)) {

			//Attempt to add the item to the other inventory
			if (other.addItem(item)) {

				//Adding the item was possible and has now been performed, so we
				//can safely remove the item from this inventory knowing that it
				//resides elsewhere
				removeItem(item);
				return true;
			}
		}

		//The move operation failed either because the item was not present in
		//this inventory or because it could not be added to the other
		//inventory. In this case both inventories are left untouched
		return false;
	}

	/**
	 * Swap the specified items inside this inventory. If either item is not
	 * present inside this inventory the method will do nothing. This method
	 * returns a boolean based on whether the swap operation was a success.
	 *
	 * @param item1 the first item to swap
	 * @param item2 the second item to swap
	 * @return {@code true} if the items were successfully swapped,
	 *         {@code false} otherwise
	 */
	public boolean swapItems(Item item1, Item item2) {
		//We can only swap items if both are present in this inventory
		if (content.contains(item1) && content.contains(item2)) {

			//Retrieve the locations of the items to be swapped
			int item1Location = content.indexOf(item1);
			int item2Location = content.indexOf(item2);

			//Swap the items
			content.set(item1Location, item2);
			content.set(item2Location, item1);

			return true;
		}

		//Either items was not present in this inventory, so the inventory is
		//untouched
		return false;
	}

	/**
	 * Get the item located at the specified index in this inventory.
	 *
	 * @param i the index of the item to retrieve
	 * @return the item at the specified index
	 * @throws IndexOutOfBoundsException if the specified index is outside the
	 *                                   range of this inventory
	 */
	public Item getItemByIndex(int i) {
		//Throw an exception if the index is invalid. We do not rely on the
		//exception thrown by content.get(i) because exceptions should thrown as
		//early as possible
		if (i < 0 || i >= content.size()) {
			throw new IndexOutOfBoundsException("Index " + i + " was not in this inventory!");
		}

		//Return the item at the index i
		return content.get(i);
	}

	/**
	 * Get the item with the specified name in this inventory. If no such item
	 * exists this method will return null.
	 *
	 * @param name the name of the item to retrieve
	 * @return the item with the specified name, or null if no such item exists
	 *         in this inventory
	 */
	public Item getItemByName(String name) {
		//We use linear search since the inventory is not sorted in any way
		for (Item item : content) {

			//If the item has the specified name, return it
			if (item.hasName(name)) {
				return item;
			}
		}

		//No item with the specified name was found, return null instead
		return null;
	}

	/**
	 * Test whether this inventory contains the specified item.
	 *
	 * @param item the item to test for
	 * @return {@code true} if this inventory contains the specified item,
	 *         {@code false} otherwise
	 */
	public boolean containsItem(Item item) {
		return content.contains(item);
	}

	/**
	 * Get a string representation of this inventory of the form:
	 * <br>
	 * "Item1, Item2, Item3, Item4"
	 *
	 * @return a string representation of this inventory
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String prefix = "";

		for (Item item : content) {
			builder.append(prefix);
			prefix = ", ";
			builder.append(item);
		}

		return builder.toString();
	}
}
