/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.business.framework;

import util.XMLFileLocater;

import java.io.File;
import java.util.Scanner;

/**
 * Class containing the main method that starts the game.
 *
 * @author Christian Schou
 */
class GameStarter {

	public static void main(String[] args) {
		//Folder containing the xml files
		File xmlFolder = new File("xml");
		//List of files in the xml folder
		File[] fileList = xmlFolder.listFiles();
		//Prompt for a selection between games, list the names of them
		System.out.println("Which game would you like to play?:");
		for (int i = 0; i < fileList.length; i++) {
			System.out.println(i + 1 + ": " + fileList[i].getName());
		}

		Scanner input = new Scanner(System.in);
		//Parse the selected folder with the XMLFileLocater
		XMLFileLocater locater = new XMLFileLocater(fileList[input.nextInt() - 1].toPath());
		//construct game instance
		Game game = new Game();
		//Run the game. The play() method will return when the game quits
		game.play();
	}
}
