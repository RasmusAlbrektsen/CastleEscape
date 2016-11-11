/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;
import castleescape.business.framework.Game;
import java.io.File;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.XMLFileLocater;

/**
 * Entry point for the JavaFXML application and the game logic.
 * 
 * @author Kasper
 */
public class CastleEscape extends Application {

	private BusinessMediator initGame() {
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

		return new BusinessMediator(game);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BusinessMediator bm = initGame();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameGuiView.fxml"));

		Parent root = (Parent) loader.load();
		GameGuiController controller = loader.getController();
		controller.setBusinessMediator(bm);

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
