/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;
import castleescape.business.framework.Character;
import castleescape.business.framework.Game;
import java.io.File;
import java.sql.Time;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
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

		ChoiceDialog<File> choiceDialog= new ChoiceDialog<>(fileList[0],fileList);
		choiceDialog.setHeaderText("Wich game would you like to play?");
		choiceDialog.setTitle("Game selection");
		choiceDialog.showAndWait();

		XMLFileLocater locater = new XMLFileLocater(choiceDialog.getResult().toPath());

		BusinessMediator bm = new BusinessMediator();

		ChoiceDialog<Character> characterChoiceDialog = new ChoiceDialog<Character>(bm.getCharacterList()[0],bm.getCharacterList());
		characterChoiceDialog.setHeaderText("Which character would you like to play?");
		characterChoiceDialog.setTitle("Character selection");
		characterChoiceDialog.showAndWait();
		bm.notifyCharacterSelected(characterChoiceDialog.getResult());
		//construct game instance
		return bm;
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

	public static void exit(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ignored) {}
		Platform.exit();
		System.exit(0);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
