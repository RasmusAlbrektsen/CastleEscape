/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.framework;

/**
 * Class for storing player names and scores together in a convenient way.
 */
public class Score {

	/**
	 * The name of the player that got this score.
	 */
	private final String playerName;

	/**
	 * The amount of points.
	 */
	private final int playerScore;

	/**
	 * Constructs a new score object with the specified player name and score.
	 *
	 * @param playerName the name of the player
	 * @param score      the player's score
	 */
	public Score(String playerName, int score) {
		this.playerName = playerName;
		this.playerScore = score;
	}

	/**
	 * Get the name of the player that made this score.
	 *
	 * @return the name of the player that made this score
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Get the score of this score object.
	 *
	 * @return the score of this score object
	 */
	public int getPlayerScore() {
		return playerScore;
	}

	@Override
	public String toString() {
		return playerName + ", " + playerScore + " points";
	}
}
