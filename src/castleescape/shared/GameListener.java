/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.shared;

/**
 * A game listener is an object that can receive events from the game.
 */
public interface GameListener {

	/**
	 * Called right after the game has started playing a new level.
	 *
	 * @param output the textual output that the game generated on startup
	 */
	void onGameStart(String output);

	/**
	 * Called right after the game has ended.
	 */
	void onGameExit();

	/**
	 * Called every time the game has made an iteration (getting user input,
	 * executing it and generating some output).
	 *
	 * @param output the textual output that the game generated on this
	 *               iteration
	 */
	void onGameIteration(String output);
}
