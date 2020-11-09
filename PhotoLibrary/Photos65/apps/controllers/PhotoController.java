package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Album;
import models.Photo;
import models.Tag;
import models.User;
import models.UserList;

/**
 * 
 * In this page the user will be able to display any desired photo, add or
 * remove tags, details, set a caption and move photos from one album to another
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class PhotoController{

	@FXML private Button logout;
	@FXML private Button back;
	@FXML private Button setCap;
	@FXML private Button move;
	@FXML private Button nextPic;
	@FXML private Button backPic;
	@FXML private Text albName;
	@FXML private Text albN;
	@FXML private Text dateText;
	@FXML private TextField newAlb;
	@FXML private TextArea capArea;
	@FXML private ImageView imV;
	@FXML private Button addTag;
	@FXML private Button deleteTag;
	@FXML private TextField value;
	@FXML private TextField type;
	@FXML private TableView<Tag> tagTable; // Tag Table
	@FXML private TableColumn<Tag, String> Type; // Column for Tag Type
	@FXML private TableColumn<Tag, String> Value; // Column for Tag Value
	private ObservableList<Tag> obsL;
	private int userInd;
	private int picInd;
	private int albInt;
	private UserList ulist;
	private ArrayList<Tag> tags;
	private List<User> users= new ArrayList<User>();
	private ArrayList<Album> albums = new ArrayList<Album>();
	private User user;
	private Album album;
	private Photo arr;
	private ArrayList<Photo> photos = new ArrayList<Photo>();

	/**
	 * 
	 * @param mainStage this will start the Photo View Page
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
			ulist=UserList.read();
			mainStage.setTitle("Photo View");
			users = ulist.getUserList();
			user = users.get(userInd);
			albums = user.getAlbums();
			album = albums.get(albInt);
			albName.setText("Album: " + album.toString());
			albN.setText(album.toString());
			photos = album.getPhotos();
			arr = photos.get(picInd);
			capArea.setWrapText(true);
			capArea.setText(arr.getCaption());
			dateText.setText(arr.getTimeString());
			imV.setImage(arr.getSerializableImage().getImage());
			tags=arr.getTags();
			obsL = FXCollections.observableArrayList(arr.getTags()); // If you're having issues
			tagTable.setItems(obsL);
			Type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
			Value.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
		}

	/**
	 * 
	 * User list initialization. This method keeps track of the current user, user
	 * index, photo index and album that the application is being operated on.
	 * 
	 * 
	 * @param ul a User List instance
	 * @param u  user list index
	 * @param a  album index
	 * @param i  picture index
	 */
	public void initData(UserList ul, int u, int a, int i) {
		ulist = ul;
		userInd = u;
		albInt = a;
		picInd=i;
	}
	
	/**
	 * 
	 * This method allows a user to move a photo from one album to another. The
	 * photo will be removed from the source album
	 * 
	 * @param e this is the event handler (Move Photo button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */
	public void move(ActionEvent e) throws IOException, ClassNotFoundException {
		Album temp = new Album (newAlb.getText());
		if(album.toString().contentEquals(temp.toString())) {
			//Alert for same album
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText(
					"This photo is already in "+ temp.toString());
			alert.showAndWait();
			return;
		}
		for(Album a: albums) {
			if(a.toString().contentEquals(temp.toString())) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm");
				String s = "Are you sure you want move this photo from " + album.toString() + " to " + a.toString();
				alert.setContentText(s);
				Optional<ButtonType> result = alert.showAndWait();
				 
				if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
					a.addPhoto(arr);
					album.deletePhoto(arr);
					UserList.write(ulist);
					if(picInd-1<0) {
						if(photos.isEmpty()) {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ThumbView.fxml"));
							Parent parent = (Parent) loader.load();  
							ThumbController ctrl = loader.getController();
							ctrl.initData(ulist, userInd, albInt);
							Scene scene = new Scene(parent);		
							Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	        
							ctrl.start(nextStage);     
						    nextStage.setScene(scene);
						    nextStage.show();
						    return;
						}else {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
							Parent parent = (Parent) loader.load();
							PhotoController ctrl = loader.getController();
							ctrl.initData(ulist, userInd, albInt, picInd);
							Scene scene = new Scene(parent);	
							Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
							ctrl.start(nextStage);    
						    nextStage.setScene(scene);
						    nextStage.show();
						    return;
						}
					}else {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
						Parent parent = (Parent) loader.load();
						PhotoController ctrl = loader.getController();
						ctrl.initData(ulist, userInd, albInt, picInd-1);
						Scene scene = new Scene(parent);		
						Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
						ctrl.start(nextStage);
					    nextStage.setScene(scene);
					    nextStage.show();
					    return;
					}
				}else {
					return;
				}
			}
		}
		//Alert for not finding that album
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ERROR");
		alert.setHeaderText(null);
		alert.setContentText("The album " + temp.toString() +" does not exist");
		alert.showAndWait();
		
	}

	/**
	 * 
	 * This method helps the user to add a tag to a desired photo upon action.
	 * Duplicate tags in the same photo will not be allowed
	 * 
	 * @param e this is the event handler (Add Tag Photo button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void addTag(ActionEvent e) throws IOException, ClassNotFoundException {
		if (type.getText().isEmpty() || value.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Type and Value are required fields. Please try again");
			alert.showAndWait();
		}
		else if (findTag(obsL, type, value)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Tag of Type:" + type.getText() + " " + "and Value: " + value.getText() + " "
					+ "is already on this list");
			alert.showAndWait();
			return;
		}else {
			if(type.getText().toLowerCase().contentEquals("location")) {
				for(Tag t: tags) {
					if(t.getType().toLowerCase().contentEquals("location")) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("ERROR");
						alert.setHeaderText(null);
						alert.setContentText("A photo can only have one Tag with the type location");
						alert.showAndWait();
						return;
					}
				}
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			//alert.setWidth(arg0);
			Text s = new Text("   Are you sure you want to add a tag \n   of Type : " + type.getText()
					+ " and Value: "
					+ value.getText() + " to the \n" + "   List?   ");
			s.setWrappingWidth(200);
			alert.getDialogPane().setContent(s);
			Optional<ButtonType> result = alert.showAndWait();
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				Tag temp = new Tag(type.getText(), value.getText());
				tags.add(temp);
				tagTable.getItems().add(temp);
				UserList.write(ulist);	
			}
		}
	}

	/**
	 * 
	 * This method will help the user to delete a desired tag upon action.
	 * 
	 * @param e this is the event handler (Delete Tag Photo button) that is
	 *          triggered upon user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 * 
	 */
	public void deleteTag(ActionEvent e) throws IOException {
		if(tagTable.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please select a tag to delete");
			alert.showAndWait();
			return;
		}else if (obsL.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("There are no tags to delete");
			alert.showAndWait();
			return;
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			Tag tag = tagTable.getSelectionModel().getSelectedItem();
			String s = "Are you sure you want to delete this tag?";
			//s.setWrappingWidth(200);
			alert.setContentText(s);
			Optional<ButtonType> result = alert.showAndWait();
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				//obsL.remove(tagTable.getSelectionModel().getSelectedIndex()); //
				tags.remove(tag);
				tagTable.getItems().remove(tag);
				UserList.write(ulist);
			}
		}
	}

	/**
	 * 
	 * Helper method. Upon user's request, it will take user back to ThumbView
	 * 
	 * @param e this is the event handler (Back button) that is triggered upon user
	 *          action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */

	public void back(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ThumbView.fxml"));
		Parent parent = (Parent) loader.load();
		ThumbController ctrl = loader.getController();
		ctrl.initData(ulist, userInd, albInt);
		Scene scene = new Scene(parent);
		Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		ctrl.start(nextStage);
	    nextStage.setScene(scene);
	    nextStage.show();
	}

	/**
	 * 
	 * Helper method to set Caption in a photo upon user action
	 * 
	 * @param e this is the event handler (Set Caption button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 * 
	 */
	public void setCap(ActionEvent e) throws IOException {
		if(capArea.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText(
					"Can not set a caption to nothing");
			alert.showAndWait();
			return;
		}if(capArea.getText().length()>152) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText(
					"A caption can only be 152 characters long");
			alert.showAndWait();
			return;
		}
		arr.setCaption(capArea.getText());
		UserList.write(ulist);
	}

	/**
	 * 
	 * User will be able to get back to a previous photo using the backPic method
	 * 
	 * @param e this is the event handler (Previous photo button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */

	public void backPic(ActionEvent e) throws IOException, ClassNotFoundException {
		if(picInd-1<0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("This is the first picture in the album");
			alert.showAndWait();
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
			Parent parent = (Parent) loader.load();
			PhotoController ctrl = loader.getController();
			ctrl.initData(ulist, userInd, albInt, picInd-1);
			Scene scene = new Scene(parent);
			Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
			ctrl.start(nextStage);         
		    nextStage.setScene(scene);
		    nextStage.show();
		}
	}

	/**
	 * 
	 * User will be able to go to the next photo using the nextPic method
	 * 
	 * @param e this is the event handler (Next Photo button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */

	public void nextPic(ActionEvent e) throws IOException, ClassNotFoundException {
		if(picInd+1>album.getPhotos().size()-1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("This is the last picture in the album");
			alert.showAndWait();
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoView.fxml"));
			Parent parent = (Parent) loader.load();
			PhotoController ctrl = loader.getController();
			ctrl.initData(ulist, userInd, albInt, picInd+1);
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

	/**
	 * 
	 * This helper method is implemented by add tag in order to determine if a new
	 * tag is equal to an existing tag in the tags list
	 * 
	 * @param obsList Observabke list of Tags
	 * 
	 * @param type    Type of Tag (Person or Location)
	 * 
	 * @param value   Value of Tag (Susan or Mall)
	 * 
	 * @return True if the tag is equal to an existing tag or false if otherwise
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean findTag(ObservableList<Tag> obsList, TextField type, TextField value) {
		for (Tag findTag : obsList) {
			if (findTag.getType().equals(type.getText().toLowerCase())
					&& findTag.getValue().equals(value.getText().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
