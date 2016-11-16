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
 *
 * @author DitteKoustrup, Kasper
 * @see <a href="https://codeshare.io/bLKnE">Codeshare</a>
 */
public class Character {

	/**
	 * How likely the character is to make noise when performing actions like
	 * inspecting or using items. A value of 0.0 means that the character will
	 * never make noise while a value of 1.0 means that the character will
	 * always make noise.
	 */
	private final double clumsiness;

	/**
	 * The character's inventory.
	 */
	private final Inventory inventory;

	private String name;

	private String description;
	/**
	 * Constructs a new character with the specified clumsiness and carry
	 * capacity.
	 *
	 * @param clumsiness    how likely the character is to make noise
	 * @param carryCapacity how many items the character can carry
	 */
	public Character(double clumsiness, int carryCapacity,String name,String description) {
		this.clumsiness = clumsiness;
		inventory = new Inventory(carryCapacity);
		this.name=name;
		this.description=description;
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

	public String toString(){
		return name;
	}
}
