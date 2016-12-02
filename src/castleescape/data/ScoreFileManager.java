/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.data;

import castleescape.business.framework.Score;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class responsible for reading score data into the game and writing it back to
 * the file system.
 */
public class ScoreFileManager {

	/**
	 * The suffix for all score files. The prefix will be the name of the level,
	 * so for a level with the name "tutorial", the score file would be
	 * tutorialHighScores.txt
	 */
	private static final String HIGHSCORE_FILE = "HighScores.txt";

	/**
	 * The string that separates names and scores in the score files.
	 */
	private static final String SCORE_ELEMENT_SEPARATOR = ":";

	/**
	 * Map for storing all scores that have been read. The key is the player's
	 * name and the value is the score of that player.
	 */
	private final List<Score> scores;

	/**
	 * Constructs a new score file manager.
	 */
	public ScoreFileManager() {
		scores = new ArrayList<>();
	}

	/**
	 * Read the player scores for the level with the specified name, if they
	 * exist.
	 *
	 * @param levelName the name of the level for which to read scores
	 */
	public void readScores(String levelName) {
		//Reset data for new run
		scores.clear();

		//Create a file object from the path at which the file should be
		File scoreFile = new File(levelName + HIGHSCORE_FILE);

		//If the score file does not exist, return now, as there is nothing to
		//read
		if (!scoreFile.exists()) {
			return;
		}

		//Use try-with-resources to create and use a scanner on a file. This
		//will automatically close the scanner if something goes wrong and is
		//the preferred way to handle IO operations.
		try (Scanner scanner = new Scanner(scoreFile)) {

			//Loop through all lines in the file
			while (scanner.hasNextLine()) {
				//Get the name and score on the line as separate strings. The
				//file is assumed to be well formatted.
				String[] lineContent = scanner.nextLine().split(SCORE_ELEMENT_SEPARATOR);

				//Get the name and score from the current line
				String name = lineContent[0];
				int points = Integer.parseInt(lineContent[1]);
				Score score = new Score(name, points);

				//Add the score to the list
				scores.add(score);
			}

			//Close the scanner for good measure
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save the specified score to the score file associated with the specified
	 * level name. If no such file exists, it will be created when calling this
	 * method.
	 *
	 * @param levelName the name of the level for which to save the score
	 * @param score     the score to save
	 */
	public void saveScore(String levelName, Score score) {
		//Use try-with-resources to create and use a filewriter. This will
		//automatically close the filewriter if something goes wrong and is the
		//preferred way to handle IO operations.
		try (FileWriter writer = new FileWriter(levelName + HIGHSCORE_FILE, true)) {

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
	 * Get the scores that were read during the last call to
	 * {@link #readScores(String)}.
	 *
	 * @return the scores that were read last
	 */
	public List<Score> getScores() {
		return scores;
	}
}
