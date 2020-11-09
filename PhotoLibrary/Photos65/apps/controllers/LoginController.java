package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.User;
import models.UserList;

/**
 * 
 * This class handles user and password implementation for Login
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class LoginController {
	@FXML private Button login;
	@FXML private Button create;
	@FXML private TextField user;
	@FXML private TextField pass;
	private List<User> users= new ArrayList<User>();
	private UserList ulist;
	
	/**
	 * 
	 * @param mainStage mainStage the stage (Login page view) in which the
	 *                  application will display
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 * 
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException { 
		//UserList.write(new UserList());    //This line exists in case data in serializable object gets error write blank list and repopulate
		ulist = UserList.read();
		users=ulist.getUserList();
		mainStage.setTitle("Login Page");
		User admin = new User("admin", "admin");
		User stock = new User("stock", "stock");
		boolean seeStock=false;
		boolean seeAd=false;
		for(User u: users) {
			if(u.getUserName().contentEquals("admin")) {
				seeAd=true;
			}
			if(u.getUserName().contentEquals("stock")) {
				seeStock=true;
			}
		}
		if(seeAd==false) {
			users.add(admin);
			UserList.write(ulist);
		}
		if(seeStock==false) {
			users.add(stock);
			UserList.write(ulist);
		}
		
		seeStock=false;
		seeAd=false;
		ulist = UserList.read();
		users=ulist.getUserList();
		mainStage.setTitle("Login Page");
	}

	/**
	 * 
	 * This method will allow the user to login in by entering an user name and
	 * password. If the user credentials are equal to the administrator credentials,
	 * such user will be directed to the administrator page
	 * 
	 * @param e this is the event handler (Login button) that is triggered upon user
	 *          action
	 * 
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 */
	public void login(ActionEvent e) throws ClassNotFoundException, IOException {
		if(checkLogin(users, user, pass)) {
			if(user.getText().equals("admin") && pass.getText().equals("admin")){	 
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/adminPage.fxml"));
				Parent parent = (Parent) loader.load();
				AdminController ctrl1 = loader.getController();
				Scene scene = new Scene(parent);
				Stage newStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
				ctrl1.start(newStage);
			    newStage.setScene(scene);
			    newStage.show();  
			}else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumView.fxml"));
				Parent parent = (Parent) loader.load();
				AlbumController ctrl1 = loader.getController();
				ctrl1.initData(ulist, ulist.getIndexByUsername(user.getText()));
				Scene scene = new Scene(parent);
							
				Stage newStage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
			               
				ctrl1.start(newStage);
			             
			    newStage.setScene(scene);
			    newStage.show();  
			}
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR");
			alert.setHeaderText(null);
			alert.setContentText("Invalid login, try again or create new user.");
			alert.showAndWait();
		}
		
		
	}
	
	/**
	 * 
	 * This method will guide the user through the process of creating a user name.
	 * User names are unique. In case the user violates this requirement, a warning
	 * will pop
	 * 
	 * @param e this is the event handler (Create User button) that is triggered
	 *          upon user action
	 * 
	 * 
	 * @throws ClassNotFoundException produced when no definition for the class with
	 *                                the specified name could be found.
	 * 
	 * @throws IOException            produced by failed or interrupted I/O
	 *                                operations
	 * 
	 */
	public void create(ActionEvent e) throws IOException, ClassNotFoundException {
		
		
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
	    	if(u.getUserName().length()>20 || u.getPass().length()>20) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText(
						"Both a username and passowrd have a maximum size of 30 characters");
				alert.showAndWait();
				return;
			}
	    	if(ulist.userExists(username.getText().trim())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("The user " + username.getText() + " already exists");
				alert.showAndWait();
				return;
			}
			if(username.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ERROR");
				alert.setHeaderText(null);
				alert.setContentText("Username and password are required fields");
				alert.showAndWait();
				return;
			}
			users.add(u);
			UserList.write(ulist);
			ulist = UserList.read();
			users=ulist.getUserList();
	    }
	}
	
	/**
	 * 
	 * This is a helper method to check if the login credential belong to any user
	 * in the list
	 * 
	 * 
	 * @param users   List of users
	 * 
	 * @param user    user name request input by the user
	 * 
	 * @param pass    password request input by the user
	 * 
	 * @return true if user matches a user in the list false otherwise
	 * 
	 */
	public boolean checkLogin(List<User> users, TextField user, TextField pass) {
		for (User u : users) {
			if (u.getUserName().equals(user.getText())
					&& u.getPass().equals(pass.getText())) {
                return true;
            }
        }
        return false;
    }


}
