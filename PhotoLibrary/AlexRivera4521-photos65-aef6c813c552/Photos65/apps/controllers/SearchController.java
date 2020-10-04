package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Album;
import models.Photo;
import models.PhotoCell;
import models.SerializableImage;
import models.Tag;
import models.User;
import models.UserList;

/**
 * 
 * Search Controller provides help to the user to search pictures by Tag and
 * Date Range and displaying the results in a Table View
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class SearchController {
	@FXML private Button logout;
	@FXML private Button dateSearch;
	@FXML private Button cancel;
	@FXML private Button search1;
	@FXML private Button build;
	@FXML private Button and;
	@FXML private Button or;
	@FXML private TextField type1;
	@FXML private TextField value1;
	@FXML private TextField tag1Type;
	@FXML private TextField tag1Value;
	@FXML private TextField tag2Type;
	@FXML private TextField tag2Value;
	@FXML private TextField startDate;
	@FXML private TextField endDate;
	@FXML private TableView<Photo> table;
	@FXML private TableColumn<Photo, SerializableImage> picCol;
	@FXML private TableColumn<Photo,String> capCol;
	private UserList ulit;
	private int usInd;
	private List<User> users= new ArrayList<User>();
	private User user;
	private ArrayList<Album> albums = new ArrayList<Album>();
	
	/**
	 * 
	 * @param mainStage this will start the Photo Search Page
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
		ulit=UserList.read();
		users=ulit.getUserList();
		user = users.get(usInd);
		albums=user.getAlbums();
		picCol.setCellValueFactory(cellData -> new SimpleObjectProperty<SerializableImage>(cellData.getValue().getSerializableImage()));
		capCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCaption()));
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
		mainStage.setTitle("Search Page");
	}
	
	/**
	 * 
	 * User list initialization. This method keeps track of the current user and
	 * album that the application is being operated on.
	 * 
	 * @param ul User List instance
	 * 
	 * @param u  user index in user list
	 */
	public void initData(UserList ul, int u) {
		this.ulit = ul;
		this.usInd = u;
	}

	/**
	 * 
	 * Method that allows user to perform a photo search using one tag only
	 * 
	 * @param e this is the event handler (Search 1 button) that is triggered upon
	 *          user action and it provides functionality to search by 1 Tag
	 */
	public void search1(ActionEvent e) {	
		if(type1.getText().trim().isEmpty() || value1.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("You must enter both a type and a value");
			alert.showAndWait();
			return;
		}else{
			table.getItems().clear();
			Tag temp = new Tag(type1.getText(), value1.getText());
			boolean noneFoundAlert = false;
			for(Album a: albums) {
				for(Photo p: a.getPhotos()) {
					for(Tag t: p.getTags()) {
						if(t.equals(temp)) {
							table.getItems().add(p);
							noneFoundAlert=true;
						}
					}
				}
			}
			if(noneFoundAlert==false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("No images exist with that tag");
				alert.showAndWait();
				return;
			}
		}
		
	}

	/**
	 * 
	 * @param e this is the event handler (Search on Tag 1 and Tag 2 button) that is
	 *          triggered upon user action and provides functionality to search by 2
	 *          tags using the "and" operator logic
	 */
	public void and(ActionEvent e) {
		boolean seenT1=false;
		boolean seenT2=false;
		if(tag2Type.getText().trim().isEmpty() || tag2Value.getText().trim().isEmpty() || tag1Type.getText().trim().isEmpty() || tag1Value.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("You must enter both a type and a value for tag 1 and tag 2");
			alert.showAndWait();
			return;
		}else{
			table.getItems().clear();
			Tag tag1=new Tag(tag1Type.getText(), tag1Value.getText());
			Tag tag2= new Tag(tag2Type.getText(), tag2Value.getText());
			boolean noneFoundAlert= false;
			for(Album a: albums) {
				for(Photo p: a.getPhotos()) {
					seenT1=false;
					seenT2=false;
					for(Tag t: p.getTags()) {
						if(t.equals(tag1)) {
							seenT1=true;
						}
						if(t.equals(tag2)) {
							seenT2=true;
						}
						if(seenT1 && seenT2) {
							seenT1=false;
							seenT2=false;
							noneFoundAlert=true;
							table.getItems().add(p);
						}
					}
				}
			}
			if(noneFoundAlert==false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("No images exist with that pair of tags");
				alert.showAndWait();
				return;
			}
		}
		
	}

	/**
	 * 
	 * @param e this is the event handler (Search on Tag 1 or Tag 2 button) that is
	 *          triggered upon user action and provides functionality to search by 2
	 *          tags using the "or" operator logic
	 */
	public void or(ActionEvent e) {
		if(tag2Type.getText().trim().isEmpty() || tag2Value.getText().trim().isEmpty() || tag1Type.getText().trim().isEmpty() || tag1Value.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("You must enter both a type and a value for tag 1 and tag 2");
			alert.showAndWait();
			return;
		}else{
			table.getItems().clear();
			Tag tag1=new Tag(tag1Type.getText(), tag1Value.getText());
			Tag tag2= new Tag(tag2Type.getText(), tag2Value.getText());
			boolean noneFoundAlert=false;
			for(Album a: albums) {
				for(Photo p: a.getPhotos()) {
					for(Tag t: p.getTags()) {
						if(t.equals(tag1) || t.equals(tag2)) {
							table.getItems().add(p);
							noneFoundAlert=true;
							break;
						}
					}
				}
			}
			if(noneFoundAlert==false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("No images exist with those tags");
				alert.showAndWait();
				return;
			}
		}
		
	}

	/**
	 * 
	 * This method allows the user to build an album based on the result of the
	 * user's search
	 * 
	 * @param e this is the event handler (Build Album button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 */
	public void build(ActionEvent e) throws IOException {
		if(table.getItems().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("You can not build an empty album on this page");
			alert.showAndWait();
			return;
		}
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
			Album taco = new Album(result.get());
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
			for(Photo p: table.getItems()) {
				taco.addPhoto(p);
			}
			albums.add(taco);
			UserList.write(ulit);
		}
	}

	/**
	 * 
	 * Method that allows user to perform a photo search by using a date range
	 * 
	 * @param e this is the event handler (Search By Date Range button) that is
	 *          triggered upon user action
	 */
	public void dateSearch(ActionEvent e) {
		LocalDate start = convertToLD(startDate.getText());
		LocalDate end = convertToLD(endDate.getText());
		
		if(startDate.getText().trim().isEmpty() || endDate.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please enter both a start date and an end date");
			alert.showAndWait();
			return;
		}else if(start==null || end==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("The dates must be in the form MM-DD-YYYY");
			alert.showAndWait();
			return;
		}else if(end.isBefore(start)){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("You can't end something before it began");
			alert.showAndWait();
			return;
		}else {
			table.getItems().clear();
			boolean noneFoundAlert=false;
			for(Album a: albums) {
				for(Photo p: a.getPhotos()) {
					if((p.getTime().isAfter(start) && p.getTime().isBefore(end)) || p.getTime().isEqual(start) || p.getTime().isEqual(end)) {
						table.getItems().add(p);
						noneFoundAlert=true;
					}
				}
			}
			if(noneFoundAlert==false) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("No pictures were taken between those dates");
				alert.showAndWait();
				return;
			}
		}
		
	}

	/**
	 * 
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
	 * On cancel search, the application will return to album view
	 * 
	 * 
	 * @param e this is the event handler (Cancel Search button) that is triggered
	 *          upon user action
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 */
	public void cancel(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumView.fxml"));
		Parent parent = (Parent) loader.load();
		AlbumController ctrl = loader.getController();
		ctrl.initData(ulit, usInd);
		Scene scene = new Scene(parent);
		Stage nextStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
		ctrl.start(nextStage);
	    nextStage.setScene(scene);
	    nextStage.show();
	}
	
	/**
	 * 
	 * This method converts a String to a date. This is used on search by date
	 * 
	 * @param date the String date input by the user
	 * 
	 * @return null or String converted to date
	 */
	public LocalDate convertToLD(String date) {
		if(date.length()!=10) {
			return null;
		}
		String str = date.substring(6) + "-"+ date.substring(0, 5);
		if(date.charAt(5)!='-' || date.charAt(2)!='-') {
			return null;
		}
		try {
			LocalDate today = LocalDate.parse(str);
			return today;
		}catch(DateTimeParseException e) {
			return null;//basically null means it couldn't make that object, used in dateSearch checks
		}
	}
}
