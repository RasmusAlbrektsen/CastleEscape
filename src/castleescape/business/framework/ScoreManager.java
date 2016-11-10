/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import castleescape.business.ViewUtil;

/**
 * Class responsible for all score related operations, including keeping track
 * of the player's score.
 *
 * @author Kasper
 */
public class ScoreManager {

	private static final String HIGHSCORE_FILE = "HighScores.txt";
	private static final String SCORE_ELEMENT_SEPARATOR = ":";

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
	private List<Score> scores;

	/**
	 * Constructs a new object for managing scores. This automatically reads the
	 * score file.
	 */
	public ScoreManager() {
		scores = new ArrayList<>();
		readFromFile();
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
	 * <p>
	 * Calling this method will not reset the player's score. To do so, call
	 * {@link #reset()}.
	 */
	public void recordCurrentGameScore() {
		//Get the name of the player	
		ViewUtil.print("Enter player name (clipped to 3 characters): ");
		Scanner in = new Scanner(System.in);
		String playerName = in.nextLine();

		//Clip the name to three characters
		if (playerName.length() > 3) {
			playerName = playerName.substring(0, 3);
		}

		//Construct new score object
		Score score = new Score(playerName, currentGameScore);

		//Add the score to the internal score list
		addScore(score);

		//Save the score to the score file
		saveScore(score);
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
	 * Write the specified score to the score file.
	 *
	 * @param score the score to save in the score file
	 */
	private void saveScore(Score score) {
		//Use try-with-resources to create and use a filewriter. This will
		//automatically close the filewriter if something goes wrong and is the
		//preferred way to handle IO operations.
		try (FileWriter writer = new FileWriter(HIGHSCORE_FILE, true)) {

			//Write the score to a new line in the score file
			writer.write(score.getPlayerName() + SCORE_ELEMENT_SEPARATOR + score.getPlayerScore() + "\n");

			//Ensure that all data has actually been written out. Some IO
			//implementations use buffering to enhance performance, but closing
			//the stream before any buffered input has been written out will
			//cause the buffered data to be lost.
			writer.flush();

			//Close the filewriter
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the score file, if it exists, and store the contents in this score
	 * manager. This method will also sort the scores.
	 */
	private void readFromFile() {
		//Use try-with-resources to create and use a scanner on a file. This
		//will automatically close the scanner if something goes wrong and is
		//the preferred way to handle IO operations.
		try (Scanner scanner = new Scanner(new File(HIGHSCORE_FILE))) {

			//Loop through all lines in the file
			while (scanner.hasNextLine()) {
				//Get the name and score on the line as separate strings. The
				//file is assumed to be well formatted.
				String[] lineContent = scanner.nextLine().split(SCORE_ELEMENT_SEPARATOR);

				//Get the name and score from the current line
				String name = lineContent[0];
				int points = Integer.parseInt(lineContent[1]);
				Score score = new Score(name, points);

				//Add the score to the internal list. This automatically sorts
				//and tracks the highscore
				addScore(score);
			}

			//Close the scanner for goo measure
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
		ViewUtil.println("Highscore:\n\t" + highscore);
		ViewUtil.newLine();
		ViewUtil.println(" Name | Score");
		ViewUtil.println("------|-------");

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

			//Print the score so that it looks good
			//Make player name take up 5 characters by padding with spaces to
			//the left
			ViewUtil.print(String.format("%5s", currentScore.getPlayerName()));
			ViewUtil.print(" | ");
			ViewUtil.println(currentScore.getPlayerScore());
		}
	}

	/**
	 * Reset this score manager so that it can track a new player's score.
	 */
	public void reset() {
		currentGameScore = 0;
	}

	/**
	 * Private class for storing player names and scores together in a
	 * convenient way.
	 */
	private class Score {

		//Instance variables are final, as they should never be changed
		private final String playerName;
		private final int playerScore;

		/**
		 * Constructs a new score object with the specified player name and
		 * score.
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
			return playerName + ": " + playerScore;
		}
	}
}
