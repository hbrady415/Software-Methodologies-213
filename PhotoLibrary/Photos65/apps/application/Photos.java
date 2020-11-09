/*
 * PhotosApp.java by:
 * Hunter Brady & Alex Rivera
 * hb306 & aer136
 */
package application;

import java.io.IOException;

import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class Photos extends Application {

	/**
	 * 
	 * @param primaryStage the stage in which the application is started
	 * 
	 */

	@Override
	public void start(Stage primaryStage) 
	throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		LoginController listController = loader.getController();
		listController.start(primaryStage);
		primaryStage.setResizable(false);
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	/**
	 * 
	 * @param args takes main method arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}