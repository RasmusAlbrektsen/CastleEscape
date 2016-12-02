/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.framework;

import castleescape.business.object.Inventory;

/**
 * Class defining a playable character in the game. This class contains all the
 * characteristics that make a character unique.
 */
public class Character {

	/**
	 * The character's name.
	 */
	private final String name;

	/**
	 * The description of the character.
	 */
	private final String description;

	/**
	 * How likely the character is to make noise when performing actions like
	 * inspecting or using items. A value of 0.0 means that the character will
	 * never make noise while a value of 1.0 means that the character will make
	 * as much noise as possible.
	 */
	private final double clumsiness;

	/**
	 * The character's inventory.
	 */
	private final Inventory inventory;

	/**
	 * Constructs a new character.
	 *
	 * @param name          the name of the character
	 * @param description   the description of the character
	 * @param clumsiness    how likely the character is to make noise
	 * @param carryCapacity how many items the character can carry
	 */
	public Character(String name, String description, double clumsiness, int carryCapacity) {
		this.name = name;
		this.description = description;
		this.clumsiness = clumsiness;
		this.inventory = new Inventory(carryCapacity);
	}

	/**
	 * Get the name of this character.
	 *
	 * @return the name of this character
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the description of this character.
	 *
	 * @return the description of this character
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the inventory of this character.
	 *
	 * @return the inventory of this character
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Get the clumsiness of this character. Clumsiness defines how likely the
	 * character is to make noise when performing actions like inspecting or
	 * using items. A value of 0.0 means that the character will never make
	 * noise while a value of 1.0 means that the character will always make
	 * noise.
	 *
	 * @return the clumsiness of this character
	 */
	public double getClumsiness() {
		return clumsiness;
	}

	@Override
	public String toString() {
		return name;
	}
}
