/*
 * SongLib.java by:
 * Hunter Brady & Alex Rivera
 * hb306 & aer136
 */
package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/application/songList.fxml"));
		AnchorPane root = (AnchorPane)loader.load();


		SongListController listController = loader.getController();
		listController.start(primaryStage);
		primaryStage.setResizable(false);
		Scene scene = new Scene(root, 300, 329);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	

/*@Override
public void stop(){
    System.out.println("Stage is closing");
    // Save file
}*/
}