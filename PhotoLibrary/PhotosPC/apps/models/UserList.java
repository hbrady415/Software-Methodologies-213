package models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class UserList implements Serializable {
	
	private static final long serialVersionUID = 1L;		
	public static final String storeDir = "ser";
	public static final String storeFile = "users.ser";
	
	private List<User> users;
	
	/**
	 * 
	 * Contructor for creation of UserList object used to allow persistent storage of all users 
	 * and their respective albums the photos in those albums 
	 */
	public UserList() {
		users = new ArrayList<User>();
	}
	  
	  
	  /** 
	   * 
	   * Returns the full list of Users for the application
	   * 
	   * @return the overall list of users for the application
	   */
	  public List<User> getUserList()
	  {
		  return users;
	  }
	  
	  /**
	   * 
	   * Adds indicated user to the overall list of users
	   * 
	   * @param u - the user to add to the overall list of users 	    
	   */  
	  public void addUserToList(User u)
	  {
		  users.add(u);
	  }
	  
	   /**
	   * Remove indicated user from the overall list of users
	   * 
	   * @param u - the user to remove from the overall list of users 	    
	   */
	  public void removeUserFromList(User u)
	  {
		  users.remove(u);
	  }
	  
	  /**
	   * Checks if a specific username and password combination is in the given list
	   * 
	   * @param un - the username to check for in the from the overall list of users 
	   * @param p - the password to check for in the from the overall list of users	
	   * @return boolean - return true if username and password combination is in the list, false if it isnt    
	   */
	  public boolean isUserInList(String un, String p)
	  {
		  for(User u : users)
		  {
			  if (u.getUserName().equals(un) && u.getPass().equals(p))
				  return true;
		  }
		  return false;
	  }
	  
	  /**
	   * Checks if a specific username already exists
	   * 
	   * @param un - the username to check for in the from the overall list of users 
	   * @return boolean - return true if username is in the list, false if it isnt    
	   */
	  public boolean userExists(String un)
	  {
		  for(User u : users)
		  {
			  if (u.getUserName().equals(un))
				  return true;
		  }
		  return false;
	  }
	  


	  /**
	 * 
	 * @param username String for the user name
	 * 
	 * @return the index of the user name or -1 if the user name is not in the list
	 */
	  public int getIndexByUsername(String username) {
		  for (int i =0; i<users.size(); i++)
		  {
			  if (users.get(i).getUserName().equals(username))
				  return i;
		  }
		  return -1;
	  }
	  
	  /**
	   * toString
	   * Will display the usernames of all users
	   * 
	   * @return String - all usernames
	   */
	  @Override
	public String toString() {
		  if (users == null)
			  return "no users";
		  String output = "";
		  for(User u : users)
		  {
			  output += u.getUserName() + " ";
		  }
		  return output;
	  }
	   
	  /**
	   * Read the users.ser file and return the UserList object containing the list of all users and all their data.
	   * @return	return the UserList model of all users
	   * @throws IOException		Exception for serialization
	   * @throws ClassNotFoundException		Exception for serialization
	   */
	   public static UserList read() throws IOException, ClassNotFoundException {
		   ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./ser/users.ser"));
		   UserList ulist = (UserList) ois.readObject();
		   ois.close();
		   return ulist;
	   }
	   
	   /**
	    * write this UserList object into users.ser, overwriting anything in the file.
	    * @param ulist	The UserList model to write with
	    * @throws IOException		Exception for serialization
	    */
	   public static void write (UserList ulist) throws IOException {
		   ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./ser/users.ser"));
		   oos.writeObject(ulist);
		   oos.close();
	   }
}