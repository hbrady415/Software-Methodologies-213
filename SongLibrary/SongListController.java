/*
 * SongListController.java by:
 * Hunter Brady & Alex Rivera
 * hb306 & aer136
 */

package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SongListController {
	
	@FXML
	ListView<Song> listView;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML TextField songName;
	@FXML TextField songArtist;
	@FXML TextField songAlbum;
	@FXML TextField songYear;
	
	int max=0;
	private ObservableList<Song> obsList;

	public void start(Stage mainStage) {  
		
		mainStage.setTitle("Song Library");
		obsList = FXCollections.observableArrayList();
		listView.setItems(obsList);

		Scanner sc;
		try {
			sc = new Scanner(new File("./apps/application/songList.txt"));
			// String line = reader.readLine();
			while (sc.hasNext()) {
			// System.out.println(sc.nextLine());
			Song s = new Song(sc.nextLine(), sc.nextLine());
			s.album = sc.nextLine();
			s.year = sc.nextLine();
			// read next line
			obsList.add(s);
			max++;
			}
			sc.close();
		} catch (IOException e) {
			//System.out.print("file not found\n");
			//e.printStackTrace();
		}
		Collections.sort(obsList);
			
		/*
		 * for (int i = 0; i < max; i++) { for (int j = i + 1; j < max; j++) { if
		 * (obsList.get(i).toString().toLowerCase().compareTo(obsList.get(j).toString().
		 * toLowerCase()) > 0) { Song temp = obsList.get(i); obsList.set(i,
		 * obsList.get(j)); obsList.set(j, temp);
		 * 
		 * } } }
		 */
		
	    listView.getSelectionModel().select(0);
		
	    listView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
           (obs, oldVal, newVal) -> 
               showItemInputDialog(mainStage));
	}
	

	// Delete method
	public void delete(ActionEvent e) {
		Button b = (Button)e.getSource();
		if (b == delete) {
			if (obsList.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("List is empty");
				alert.showAndWait();
			}else {
				int temp = listView.getSelectionModel().getSelectedIndex();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm");
				Song taco= listView.getSelectionModel().getSelectedItem();
				String s = "Are you sure you want to delete the song:\n          " + taco.songName + " by  " + taco.songArtist +  "from the library?";
				alert.setContentText(s);
				 
				Optional<ButtonType> result = alert.showAndWait();
				 
				if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
					obsList.remove(listView.getSelectionModel().getSelectedIndex());
				//listView.getSelectionModel().clearSelection();
				if (listView.getSelectionModel().getSelectedIndex() != temp && max >0) {
					listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex()+1);
				}
				rewrite(obsList);
				max--;
				}
			}
		}
	}


	// Edit Method

	public void edit(ActionEvent e) {
		Button b = (Button)e.getSource();
	    Song song = new Song(songName.getText(), songArtist.getText());
	    song.setAlbum(songAlbum.getText());
	    song.setYear(songYear.getText());
	

		// Alert user if edited 'required' fields are empty

		if (b == edit) {
			if (songName.getText().isEmpty() || songArtist.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("Name of song and artist are required fields. Please try again");
				alert.showAndWait();
				
			}else if (obsList.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("List is empty");
				alert.showAndWait();
			}
			
			else if (findSong(obsList, songName, songArtist)) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText(
						"The song " + songName.getText() + " " + "by artist " + songArtist.getText() + " "
								+ "is already on the list");
				alert.showAndWait();
				return;
			}

			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm");
				Song taco= listView.getSelectionModel().getSelectedItem();
				String s = "Are you sure you want to change the song:\n          " + taco.toString() + " to " + song.toString()+ "?";
				alert.setContentText(s);
				 
				Optional<ButtonType> result = alert.showAndWait();
				 
				if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
					obsList.set(listView.getSelectionModel().getSelectedIndex(), song);
					
				
				
				Collections.sort(obsList);
				int m =0;
				for(int j=0; j<max; j++) {
					if (song.compareTo(obsList.get(j))==0) {
                    	m = j;
						}
				}
				rewrite(obsList);
			
				listView.getSelectionModel().select(m);
				}
			}

		}

	}



	public void add(ActionEvent e) {
		
		Button b = (Button)e.getSource();
	    Song song = new Song(songName.getText(), songArtist.getText());
	    song.setAlbum(songAlbum.getText());
	    song.setYear(songYear.getText());
	
		if (b == add) {

		if (songName.getText().isEmpty() || songArtist.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Name of song and artist are required fields. Please try again");
			alert.showAndWait();
		}

			else if (findSong(obsList, songName, songArtist)) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText(
					"The song " + songName.getText() + " " + "by artist " + songArtist.getText() + " "
							+ "is already on the list");
			alert.showAndWait();
			return;
		}

		else {
				
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			String s = "Are you sure you want to add the song:\n          " + songName.getText() + " by " + songArtist.getText() + " to the library?";
			alert.setContentText(s);
			 
			Optional<ButtonType> result = alert.showAndWait();
			 
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			 
				obsList.add(song);
				int m =0;
				max++;
				Collections.sort(obsList);
			
				for(int j=0; j<max; j++) {
					if (song.compareTo(obsList.get(j))==0) {
                    	m = j;
						}
				}
				rewrite(obsList);
			
				listView.getSelectionModel().select(m);
			}
		}
		}
	}
	private void rewrite(ObservableList<Song> ObsList) {
		
		try {
			File file = new File("./apps/application/songList.txt");
			FileWriter writer = new FileWriter(file, false);
			for(Song s: obsList) {
				writer.write(s.songName + "\n");
				writer.write(s.songArtist + "\n");
				writer.write(s.album + "\n");
				writer.write(s.year + "\n");
			}
			writer.close();
			}catch(IOException e){
				//System.out.println("An error occured");
				e.printStackTrace();
			}
		}
	
	private void showItemInputDialog(Stage mainStage) {                
		Song item = listView.getSelectionModel().getSelectedItem();
	      //int index = listView.getSelectionModel().getSelectedIndex();
	      
		
		if(item==null) {
	    	  songName.setText("");
	    	  songArtist.setText("");
	    	  songAlbum.setText("");
	    	  songYear.setText("");
	      
	      }else {
	    	  songName.setText(item.songName);
		      songArtist.setText(item.songArtist);
		      songAlbum.setText(item.album);
		      songYear.setText(item.year);
	      }
	      
	   }
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean findSong(ObservableList<Song> obsList, TextField name, TextField artist) {
		for (Song findSong : obsList) {
			if (findSong.getSongName().equals(name.getText().toLowerCase())
					&& findSong.getSongArtist().equals(artist.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
