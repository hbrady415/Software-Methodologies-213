package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Album;
import models.Photo;
import models.PhotoCell;
import models.SerializableImage;
import models.User;
import models.UserList;

/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class ThumbController {
	private UserList ulist;
	private int usInd;
	private int albInt;
	private Album album;
	private User user;
	private ArrayList<Album> albums = new ArrayList<Album>();
	private List<User> users= new ArrayList<User>();
	private ObservableList<Photo> obsList;
	private ArrayList<Photo> photos;
	@FXML private Button logout;
	@FXML private Button back;
	@FXML private Button addPic;
	@FXML private Button delPic;
	@FXML private Text title;
	@FXML private TableView<Photo> table;
	@FXML private TableColumn<Photo, SerializableImage> picCol;
	@FXML private TableColumn<Photo,String> capCol;

	/**
	 * 
	 * @param mainStage this will start the Album View (Thumbnail) Page
	 * 
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 * 
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException {
		mainStage.setTitle("Album Overview");
		ulist = UserList.read();
		users = ulist.getUserList();
		user = users.get(usInd);
		albums = user.getAlbums();
		album = albums.get(albInt);
		title.setText("Photos in " + album.toString() + " album:");
		photos = album.getPhotos();
		obsList = FXCollections.observableArrayList(photos);
		table.getItems().setAll(obsList);
		picCol.setCellValueFactory(cellData -> new SimpleObjectProperty<SerializableImage>(cellData.getValue().getSerializableImage()));
		capCol.setCellValueFactory(new PropertyValueFactory<Photo, String>("caption"));
		capCol.setCellFactory(new Callback<TableColumn<Photo, String>, TableCell<Photo, String>>() {
	        @Override
	        public TableCell<Photo, String> call(
	                TableColumn<Photo, String> param) {
	            TableCell<Photo, String> cell = new TableCell<>();
	            Text text = new Text();
	            cell.setGraphic(text);
	            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
	            text.wrappingWidthProperty().bind(cell.widthProperty());
	            text.textProperty().bind(cell.itemProperty());
	            return cell ;
	        }

	    });
		
		picCol.setCellFactory(new Callback<TableColumn<Photo, SerializableImage>, TableCell<Photo, SerializableImage>>(){
			@Override
			public TableCell<Photo, SerializableImage> call(TableColumn<Photo, SerializableImage> param) {
					 return new PhotoCell();
			}
		});
	}

	/**
	 * 
	 * This method will support the user to add a photo into an album
	 * 
	 * @param e this is the event handler (Delete Photo button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O
	 * 
	 *  @throws ClassNotFound produced when you are unable to read the UserList
	 */
	public void delPic(ActionEvent e) throws IOException, ClassNotFoundException {
		if (photos.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("This user doesn't have any photos in this album");
			alert.showAndWait();
		}else if(table.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please select a photo to delete");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			Photo taco= table.getSelectionModel().getSelectedItem();
			String s = "Are you sure you want to delete this photo?";
			alert.setContentText(s);
			Optional<ButtonType> result = alert.showAndWait();
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				table.getItems().remove(table.getSelectionModel().getSelectedIndex());
				photos.remove(taco);
				UserList.write(ulist);
				ulist=UserList.read();
				users = ulist.getUserList();
				user = users.get(usInd);
				albums = user.getAlbums();
				album = albums.get(albInt);
				photos=album.getPhotos();
				
			}
			
		}
	}

	/**
	 * User list initialization. This method keeps track of the current user, user
	 * index and album that the application is being operated on.
	 * 
	 * @param ul  User List Instance
	 * @param i   User index in User List
	 * @param alb album index in albums
	 */
	public void initData(UserList ul, int i, int alb) {
		ulist = ul;
		usInd = i;
		albInt = alb;
	}
	
	/**
	 * 
	 * @param e this is the event handler (View Photo button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */
	public void view(ActionEvent e) throws IOException, ClassNotFoundException {
		if(table.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please select an item to view");
			alert.showAndWait();
		}
		else if (photos.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("List is empty");
			alert.showAndWait();
		}
		else{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
		
			Parent parent = (Parent) loader.load();
			        
			PhotoController ctrl = loader.getController();
			ctrl.initData(ulist, usInd, albInt, table.getSelectionModel().getSelectedIndex());
			Scene scene = new Scene(parent);
						
			Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
		               
			ctrl.start(nextStage);
		             
		    nextStage.setScene(scene);
		    nextStage.show();
		}
	}
	
	/**
	 * 
	 * This method will support the user to add a photo into an album
	 * 
	 * @param e this is the event handler (Add Photo button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 * @throws ClassNotFoundException produced when there is no picture in the given destination
	 * 
	 */
	public void addPic(ActionEvent e) throws IOException, ClassNotFoundException {
		Photo ph = null;
		boolean exists = false;
		FileChooser imageFile = new FileChooser();
		imageFile.setTitle("Select a photo");

		/*
		 * These are all types of image file extensions that the user might try to add
		 */

		FileChooser.ExtensionFilter imageFileExtensions = new FileChooser.ExtensionFilter("*.jpg", "*.JPG", "*.jpeg",
				"*.JPEG", "*.png", "*.PNG", "*.gif", "*.GIF");

		imageFile.getExtensionFilters().addAll(imageFileExtensions);
		Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		File imageSelected = imageFile.showOpenDialog(nextStage);

		// If the file is not null we will start reading the image
		if (imageSelected == null) {
			return;
		}
		Image image = new Image(imageSelected.toURI().toString());
		SerializableImage temp = new SerializableImage();
		for (Photo photo : album.getPhotos()) {
			if (temp.equals(photo.getSerializableImage())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("The photo already exists in the album. Please try adding a different photo");
				alert.showAndWait();
				return;
			}
		}
		for (Album album : user.getAlbums()) {
			for (Photo photo : album.getPhotos()) {
				if (temp.equals(photo.getSerializableImage())) {
					ph = photo;
					exists = true;
					break;
				}
			}
			if (exists) {
				break;
			}
		}
		ph = new Photo(image);
		photos.add(ph);
		table.getItems().add(ph);
		UserList.write(ulist);
		ulist=UserList.read();
		users = ulist.getUserList();
		user = users.get(usInd);
		albums = user.getAlbums();
		album = albums.get(albInt);
		photos=album.getPhotos();
	}

	/**
	 * 
	 * @param e this is the event handler (Back to Album button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */
	public void back(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumView.fxml"));
		Parent parent = (Parent) loader.load();
		AlbumController ctrl = loader.getController();
		ctrl.initData(ulist, usInd);
		Scene scene = new Scene(parent);
		Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		ctrl.start(nextStage);
	    nextStage.setScene(scene);
	    nextStage.show();
	}

	/**
	 * 
	 * Method to logout. It is set to take the user back to the Login page
	 * 
	 * @param e this is the event handler (Logout button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
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
}