/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.framework;

import java.util.ArrayList;
import java.util.List;
import castleescape.business.ViewUtil;
import castleescape.data.DataMediator;

/**
 * Class responsible for all score related operations, including keeping track
 * of the player's score.
 *
 * @author Kasper
 */
public class ScoreManager {

	/**
	 * The name of the level for which these scores apply.
	 */
	private final String levelName;

	/**
	 * The object to use for saving scores to the file system.
	 */
	private final DataMediator dataMediator;

	/**
	 * The player's current score. This will change over the course of the game.
	 */
	private int currentGameScore;

	/**
	 * The highest score that has been encountered.
	 */
	private Score highscore;

	/**
	 * The list of scores. This list is always sorted in descending order.
	 */
	private final List<Score> scores;

	/**
	 * Constructs a new object for managing scores. This will automatically read
	 * the scores associated with the specified level.
	 *
	 * @param dataMediator the object to use for saving scores to the file
	 *                     system
	 * @param levelName    the name of the level for which these scores apply
	 */
	public ScoreManager(DataMediator dataMediator, String levelName) {
		this.dataMediator = dataMediator;
		this.levelName = levelName;

		//Initialize score list
		scores = new ArrayList<>();

		//Read score data from the file system
		dataMediator.readScoreData(levelName);

		//Retrieve unsorted list of scores from the data mediator
		List<Score> unsortedScores = dataMediator.getScores();

		//Add al the scores to the internal score list, sorting it along the way
		for (Score score : unsortedScores) {
			addScore(score);
		}
	}

	/**
	 * Add points to the player's score.
	 *
	 * @param points the points to add, or a negative number to remove points
	 */
	public void addPoints(int points) {
		currentGameScore += points;
	}

	/**
	 * Get the player's score.
	 *
	 * @return the player's score
	 */
	public int getCurrentGameScore() {
		return currentGameScore;
	}

	/**
	 * Record the player's current score by evaluating it as a new highscore and
	 * saving it in the score file. This method should only be called when the
	 * game is over.
	 *
	 * @param name the name of the user who achieved the current score
	 */
	public void recordCurrentGameScore(String name) {
		//Clip the name to three characters
		if (name.length() > 3) {
			name = name.substring(0, 3);
		}

		//Construct new score object
		Score score = new Score(name, currentGameScore);

		//Add the score to the internal score list
		addScore(score);

		//Save the score to the score file
		dataMediator.saveScoreData(levelName, score);
	}

	/**
	 * Add the specified score to the list of all scores. This will sort the
	 * scores in descending order. It will also update the highscore if
	 * necessary.
	 *
	 * @param score the score to add
	 */
	private void addScore(Score score) {
		//If no scores have been recorded yet, just add it
		if (scores.isEmpty()) {
			scores.add(score);
		} else {
			//Otherwise perform insertion sort
			int i;

			//Find the index at which this score should be inserted
			for (i = 0; i < scores.size(); i++) {
				//Add the score before the first score that is smaller than this
				//score
				if (scores.get(i).getPlayerScore() < score.getPlayerScore()) {
					//Insertion position found, break out of the loop
					break;
				}
			}

			//Insert the score at the index that we found in the loop
			scores.add(i, score);
		}

		//If this is the new highscore, save it as that. If no highscore has
		//been set yet, then this score must be the new highscore
		if (highscore == null || score.getPlayerScore() > highscore.getPlayerScore()) {
			highscore = score;
		}
	}

	/**
	 * Write the scores to the user interface. If the scores have not yet been
	 * read from the score file this method will print nothing.
	 *
	 * @param scoreCount the amount of scores to print, or -1 to print all
	 *                   scores
	 */
	public void writeScoreTable(int scoreCount) {
		//If no scores exist, do nothing. Note: if the scores list is not empty,
		//and assuming that the contract of this class has not been violated,
		//then the highscore variable is not null, so no need to test for that
		//explicitly.
		if (scores.isEmpty()) {
			return;
		}

		//Print highscore and table header
		ViewUtil.printBold("Highscore:");
		ViewUtil.newLine();
		ViewUtil.println(highscore);
		ViewUtil.newLine();
		ViewUtil.printBold("Top " + scoreCount + " scores:");
		ViewUtil.newLine();

		//If we are requested to print all scores (scoreCount == -1) then set
		//scoreCount to the size og the scores list. The reasoning behind this
		//functionality is merely to add convenience to the users of this
		//method, as they do not need to worry about the size of the scores
		//list.
		if (scoreCount == -1) {
			scoreCount = scores.size();
		}

		//Print until we have reached the score count or the last element in the
		//scores list.
		for (int i = 0; i < scoreCount && i < scores.size(); i++) {
			//Get the score that we have reached
			Score currentScore = scores.get(i);

			//Print the score along with its rank (the value of i + 1, as we
			//want to start from 1, not 0)
			ViewUtil.print((i + 1) + ". ");
			ViewUtil.println(currentScore);
		}
	}
}
