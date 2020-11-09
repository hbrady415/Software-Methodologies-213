package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class User implements Serializable{
	private String username;
	private String password;
	private ArrayList<Album> albums;

	/**
	 * 
	 * User class constructor
	 * 
	 * @param username string for the user name
	 * @param password string for the password
	 */
	public User(String username, String password) {
		
		this.username = username;
		this.password = password;
		this.albums = new ArrayList<Album>();
	}

	/**
	 * 
	 * @return array list of albums for the user
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * 
	 * @param alb sets the array list if albums that belong to the user
	 */
	public void setAlbums(ArrayList<Album> alb) {
		this.albums=alb;
	}
	
	/**
	 * 
	 * @param a add an album for the respective user account
	 */
	public void addAlbum(Album a) {
		this.albums.add(a);
	}

	/**
	 * 
	 * @param album remove an album from the respective user account
	 */
	public void removeAlbum(Album album)
	{
		this.albums.remove(album);
	}

	/**
	 * 
	 * @return user name
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * 
	 * @return password
	 */
	public String getPass() {
		return password;
	}
}
