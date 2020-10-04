package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Album;
import models.Photo;
import models.User;
import models.UserList;

/**
 * 
 * This page will allow user to complete functions such: create, open, edit,
 * delete an albums.
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */

public class AlbumController{

	@FXML private Button logout;
	@FXML private Button create;
	@FXML private Button delAlbum;
	@FXML private Text title;
	@FXML private Text albumName;
	@FXML private Text numPhotos;
	@FXML private Text dateRange;
	@FXML private ListView<Album> listView;
	
	private ObservableList <Album> obsList; 
	private List<User> users= new ArrayList<User>();
	private ArrayList<Album> albums = new ArrayList<Album>();
	private UserList ulist;
	private int userInd=-1;
	private User user;
	
	/**
	 * 
	 * @param mainStage the stage (Album View) in which the application will display
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException {  
		mainStage.setTitle("Album View");
		ulist = UserList.read();
		users=ulist.getUserList();
		user = users.get(userInd);
		title.setText(user.getUserName() + "'s Albums:");
		albums = user.getAlbums();
		obsList = FXCollections.observableArrayList(albums);
		listView.setItems(obsList);
		obsList.setAll(albums);
		if(user.getUserName().equals("stock")) {
			Album stock = new Album("stock");
			Image image = new Image("/data/stock1.jpg");
			Image image2 = new Image("/data/stock2.jpg");
			Image image3 = new Image("/data/stock3.jpg");
			Image image4 = new Image("/data/stock4.jpg");
			Image image5 = new Image("/data/stock5.jpg");
			Image image6 = new Image("/data/stock6.jpg");
			Image image7 = new Image("/data/stock7.jpg");
			Image image8 = new Image("/data/stock8.jpg");
			Image image9 = new Image("/data/stock9.jpg");
			Image image1 = new Image("/data/stock10.jpg");
			stock.addPhoto(new Photo(image));
			stock.addPhoto(new Photo(image2));
			stock.addPhoto(new Photo(image3));
			stock.addPhoto(new Photo(image4));
			stock.addPhoto(new Photo(image5));
			stock.addPhoto(new Photo(image6));
			stock.addPhoto(new Photo(image7));
			stock.addPhoto(new Photo(image8));
			stock.addPhoto(new Photo(image9));
			stock.addPhoto(new Photo(image1));
			if(!checkAlbum(albums, "stock")) {
				obsList.add(stock);
				albums.add(stock);
				UserList.write(ulist);
			}
		}
			listView.getSelectionModel().selectedIndexProperty()
		        .addListener((obs, oldVal, newVal) -> 
		               showItemInputDialog(mainStage));
			
		}
	
	/**
	 * 
	 * User list initialization. This method keeps track of the current user and
	 * album that the application is being operated on.
	 * 
	 * @param ul user list parameter
	 * 
	 * @param i  index in which the user is positoned in the list
	 */
	public void initData(UserList ul, int i) {

		this.ulist = ul;
		this.userInd = i;
	}

	/**
	 * 
	 * Triggered by mouse click, just sets the data in the album details field to be the details of the album you selected
	 * 
	 * @param mainStage the stage (view) in which the application will display
	 */
	private void showItemInputDialog(Stage mainStage) {                
		Album item = listView.getSelectionModel().getSelectedItem();
		if(item==null) {
			//Album t = obsList.get(listView.getSelectionModel().getSelectedIndex());
	    	  albumName.setText("");
	    	  numPhotos.setText("");
	    	  dateRange.setText("");
	    }else {
	    		
	    	  item.oldAndNew();
	    	  albumName.setText(item.toString());
		      numPhotos.setText(Integer.toString(item.getPhotos().size()));
		      if(item.getNewestPhoto()==null) {
		    	  numPhotos.setText("0");
		    	  dateRange.setText("");
		      }else {
		    	  dateRange.setText(item.getOldestPhoto().getTimeString() + " to " + item.getNewestPhoto().getTimeString());
		      }
		}  
	}

	/**
	 * This method implements logic to delete Albums. The album to be deleted must
	 * be selected prior to the delete album action. If the album list is empty at
	 * the moment the action is taken, the user will be prompted a warning.
	 * 
	 * @param e this is the event handler (delete Album button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 * 
	 */
	public void delAlbum(ActionEvent e) throws IOException {
		if (albums.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("This user doesn't have any albums");
			alert.showAndWait();
		}else if(listView.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please select an album to delete");
			alert.showAndWait();
		}else {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			Album taco= listView.getSelectionModel().getSelectedItem();
			String s = "Are you sure you want to delete the Album:\n          " + taco.toString();
			alert.setContentText(s);
			 
			Optional<ButtonType> result = alert.showAndWait();
			 
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				obsList.remove(listView.getSelectionModel().getSelectedIndex());
				albums.remove(taco);
				UserList.write(ulist);
			}
		}
	}

	/**
	 * 
	 * Create album method allows the user to create a new album in the application.
	 * Users will not be allowed to have duplicate albums but the same album name
	 * can be used across different users.
	 * 
	 * @param e this is the event handler (Create Album button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 * @throws ClassNotFoundException produced by inability to read()
	 */
	public void create(ActionEvent e) throws IOException, ClassNotFoundException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Create Album");
		dialog.setHeaderText("Enter album's name:");
	    Optional<String> result = dialog.showAndWait();  
		if (result.isPresent()) {
			if(result.get().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("An album's name is a required field");
				alert.showAndWait();
				return;
			}else if(result.get().length()>20){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("An album's name can only be 20 characters long");
				alert.showAndWait();
				return;
			}
			Album taco = new Album(result.get().toString());
			for(Album al: albums) {
				if(taco.toString().contentEquals(al.toString())) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("ERROR");
					alert.setHeaderText(null);
					alert.setContentText("You already have an album named " + taco.toString());
					alert.showAndWait();
					return;
				}
			}
			obsList.add(taco);
			albums.add(taco);
			UserList.write(ulist);
			
		}
	}

	/**
	 * 
	 * Rename album method allow the user to rename albums. If the user renames an
	 * album with the same name as an existing album, the user will be prompted with
	 * a warning
	 * 
	 * @param e this is the event handler (Rename Album button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException IOException produced by failed or interrupted I/O
	 *                     operations
	 */
	public void rename(ActionEvent e) throws IOException {
		if (albums.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("This user doesn't have any albums to rename");
			alert.showAndWait();
			return;
		}else if(listView.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please select an album to rename");
			alert.showAndWait();
		}else {
			TextInputDialog dialog = new TextInputDialog(albums.get(listView.getSelectionModel().getSelectedIndex()).toString());
			dialog.setTitle("Rename Album");
			dialog.setHeaderText("Enter album's name:");
		    Optional<String> result = dialog.showAndWait();
		    
			if (result.isPresent()) {
				if(result.get().trim().isEmpty()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("ERROR");
					alert.setHeaderText(null);
					alert.setContentText("An album's name is a required field");
					alert.showAndWait();
					return;
				}else if(result.get().length()>19){
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("ERROR");
					alert.setHeaderText(null);
					alert.setContentText("An album's name can only be 20 characters long");
					alert.showAndWait();
					return;
				}
				for(Album al: albums) {
					if(al.toString().contentEquals(result.get())) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("ERROR");
						alert.setHeaderText(null);
						alert.setContentText("You already have an album named " + al.toString());
						alert.showAndWait();
						return;
					}
					
				}
				Album taco = albums.get(listView.getSelectionModel().getSelectedIndex());
				taco.setName(result.get());
				albumName.setText(taco.toString());
				obsList.set(listView.getSelectionModel().getSelectedIndex(), taco);
				UserList.write(ulist);
			}
		}
	}

	/**
	 * 
	 * On action triggered by the Open button, this method will take the user to the
	 * Thumbnail view
	 * 
	 * @param e this is the event handler (Open Album button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException            IOException produced by failed or interrupted
	 *                                I/O operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */
	public void open(ActionEvent e) throws IOException, ClassNotFoundException{
		if(listView.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please create or select an album to view it");
			alert.showAndWait();
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ThumbView.fxml"));
			Parent parent = (Parent) loader.load();
			ThumbController ctrl = loader.getController();
			ctrl.initData(ulist, userInd, listView.getSelectionModel().getSelectedIndex());
			Scene scene = new Scene(parent);			
			Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
			ctrl.start(nextStage);
		    nextStage.setScene(scene);
		    nextStage.show();
		}
	}

	/**
	 * 
	 * Method to logout. It is set to take the user back to the Login page
	 * 
	 * @param e this is the event handler (Logout button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException            IOException produced by failed or interrupted
	 *                                I/O operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 * 
	 */
	public void logout(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		Parent parent = (Parent) loader.load();
		LoginController ctrl = loader.getController();
		Scene scene = new Scene(parent);
		Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
		ctrl.start(nextStage);
	    nextStage.setScene(scene);
	    nextStage.show();
	}

	/**
	 * 
	 * Search method will be triggered via the Search button and it will guide the
	 * user to the Search View Page in which the user will be able to perform a
	 * variety of searches
	 * 
	 * @param e e this is the event handler (Search button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException            IOException produced by failed or interrupted
	 *                                I/O operations
	 * 
	 * 
	 * @throws ClassNotFoundException ClassNotFoundException produced when no
	 *                                definition for the class with the specified
	 *                                name could be found.
	 */
	public void search(ActionEvent e) throws IOException, ClassNotFoundException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Search.fxml"));
		Parent parent = (Parent) loader.load();
		SearchController ctrl = loader.getController();
		ctrl.initData(ulist, userInd);
		Scene scene = new Scene(parent);
		Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		ctrl.start(nextStage);
	    nextStage.setScene(scene);
	    nextStage.show();
	}

	/**
	 * 
	 * This method checks for duplicate album names. If the name of a new album
	 * matches the name of an existing album, the return value will be true and
	 * false if otherwise
	 * 
	 * @param alb an array list of albums
	 * @param s   name of the album passed to a String
	 * @return true if new album equals existing album and false if otherwise
	 */
	public boolean checkAlbum(ArrayList<Album> alb, String s) {
		for (Album a : alb) {
			if (a.toString().equals(s)) {
	            return true;
	        }
	    }
	    return false;
	}
	
}
