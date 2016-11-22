/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package castleescape.gui;

import castleescape.business.BusinessMediator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the JavaFXML application and the game logic.
 *
 * @author Kasper
 */
public class CastleEscape extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BusinessMediator bm = new BusinessMediator();

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
		//Returns when we call Platform.exit();
		launch(args);
	}
}
