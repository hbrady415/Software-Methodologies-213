package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;
import models.UserList;

/**
 * 
 * This class puts the application into an administration subsystem and manages
 * the user list. That includes user names, password, etc. In this page the user
 * will be able to create more users and delete users (administrator only). User
 * names should be unique, thus there is logic in place to check user names upon
 * creation.
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */

public class AdminController {
	@FXML private Button add;
	@FXML private Button delete;
	@FXML private Button logout;
	@FXML private TableView<User> table;
	@FXML private TableColumn<User, String> userCol;
	@FXML private TableColumn<User,String> passCol;
	private ObservableList<User> obsList;
	private List<User> users = new ArrayList<User>();
	private UserList ulist;
	
	/**
	 * 
	 * @param mainStage the stage (view) in which the application will display
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 */

	public void start(Stage mainStage) throws ClassNotFoundException, IOException {  
		mainStage.setTitle("Admin Page");
		ulist= UserList.read();
		users= ulist.getUserList();
		obsList = FXCollections.observableArrayList(users);
		table.setItems(obsList);
		userCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPass()));
		passCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserName()));
	}
	
	
	/**
	 * 
	 * Method to delete an user from the list of users. Only the application
	 * administrator should have access to this method. If the users list is empty,
	 * the user will be prompted with a warning confirming that the operation cannot
	 * be performed.
	 * 
	 * @param e this is the event handler (delete button) that is triggered upon
	 *          user action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 */

	public void delete(ActionEvent e) throws IOException {
		if(table.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Please select an item to delete");
			alert.showAndWait();
		}else if (obsList.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("List is empty");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm");
			User taco= table.getSelectionModel().getSelectedItem();
			String s = "Are you sure you want to delete the user:\n          " + taco.getUserName();
			alert.setContentText(s);
			Optional<ButtonType> result = alert.showAndWait();
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				obsList.remove(table.getSelectionModel().getSelectedIndex());
				users.remove(table.getSelectionModel().getSelectedIndex()+1);
				UserList.write(ulist);
			}
		}
	}
	
	/**
	 * 
	 * Method to create a user. It receives two text fields. One for user name, and
	 * the other one for the password.
	 * 
	 * 
	 * @param e this is the event handler (add button) that is triggered upon user
	 *          action
	 * 
	 * @throws IOException produced by failed or interrupted I/O operations
	 */
	public void add(ActionEvent e) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Create User");
		dialog.setHeaderText("Please enter the following:");
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		TextField username = new TextField();
		username.setPromptText("Username");
		TextField password = new TextField();
		password.setPromptText("Password");
		gridPane.add(new Label("Username"), 0, 0);
		gridPane.add(username, 0, 1);
		gridPane.add(new Label("Password"), 1, 0);
		gridPane.add(password, 1, 1);

		dialog.getDialogPane().setContent(gridPane);
	    Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			User u = new User(username.getText(), password.getText());
			if(u.getUserName().length()>30 || u.getPass().length()>30) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText(
						"Both a username and passowrd have a maximum size of 30 characters");
				alert.showAndWait();
				return;
			}
			if(checkUser(obsList, username)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText(
						"The user " + username.getText()
								+ " already exists");
				alert.showAndWait();
				return;
			}
			if(username.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText(
						"Username and password are required fields");
				alert.showAndWait();
				return;
			}
			users.add(u);
			obsList.add(u);
			UserList.write(ulist);
		}
	}
	
	/**
	 * 
	 * Method to logout. It is set to take the user back to the Login page
	 * 
	 * @param e this is the event handler (logout button) that is triggered upon
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
		Stage newStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	           
		ctrl.start(newStage);
	    newStage.setScene(scene);
	    newStage.show();
	}
	
	/**
	 * 
	 * checkUser method will run a check to determine if the user being created is
	 * equal to an existing user in the users lists
	 * 
	 * @param obsList an observable list of users
	 * 
	 * @param user    a userName in a String
	 * 
	 * @return boolean returns true if user exists in the observable list and false
	 *         otherwise
	 */
	public boolean checkUser(ObservableList<User> obsList, TextField user) {

		for (User u : obsList) {
			if (u.getUserName().equals(user.getText().toLowerCase())) {
	            return true;
	        }
	    }
	    return false;
	}
}
