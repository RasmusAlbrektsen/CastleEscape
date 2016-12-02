/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the JavaFXML application and the game logic.
 */
public class CastleEscape extends Application {

	/**
	 * This method is automatically called by the JavaFX framework. It loads the
	 * .fxml document describing our user interface and sets it up to receive
	 * user input.
	 *
	 * @param stage the primary stage for this application
	 * @throws IOException if an exception occurs while reading the .fxml
	 *                     document
	 */
	@Override
	public void start(Stage stage) throws IOException {
		//Construct a new business mediator for communicating with the game
		//logic
		BusinessMediator bm = new BusinessMediator();

		//Load the .fxml document describing our user interface
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameGuiView.fxml"));
		Parent root = (Parent) loader.load();

		//Notify the controller og the user interface about the business
		//mediator, and call the method that is responsible for starting the
		//game. This method is located in the user interface, as the user must
		//enter some information before the game can begin
		GameGuiController controller = loader.getController();
		controller.setBusinessMediator(bm);
		controller.startGame();

		//Show the user interface
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		//Returns when we call Platform.exit();
		launch(args);
	}
}
