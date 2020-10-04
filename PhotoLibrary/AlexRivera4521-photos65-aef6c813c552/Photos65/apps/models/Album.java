package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */

public class Album implements Serializable{
	private String name;
	private ArrayList<Photo> photos;
	private Photo oldestPhoto;
	private Photo newestPhoto;
	
	/**
	 * 
	 * This is the constructor for the Album class
	 * 
	 * @param n represents the name of the album
	 */
	public Album(String n) {
		this.name = n;
		this.photos = new ArrayList<Photo>();
	}

	/*
	 * method for setting an oldest and newest photo on call to prevent nullPointer on setting dateRange text
	 * return of null to confirm no photos in the list
	 */
	public void oldAndNew() {
		if(photos.isEmpty()) {
			oldestPhoto=null;
			newestPhoto=null;
			return;
		}
		oldestPhoto=photos.get(0);
		newestPhoto=photos.get(0);;
		for(int i=1; i<photos.size(); i++) {
			if(photos.get(i).getTime().isAfter(newestPhoto.getTime())) {
				newestPhoto= photos.get(i);
			}
			if(photos.get(i).getTime().isBefore(oldestPhoto.getTime())) {
				oldestPhoto= photos.get(i);
			}
		}
		
	}

	/**
	 * 
	 * To set the name of the album
	 * 
	 * @param str represents the name of the album to be set
	 */
	public void setName(String str) {
		name = str;
	}

	/**
	 * 
	 * To add a photo to the album
	 * 
	 * @param pic an instance of photo
	 */
	public void addPhoto(Photo pic) {
		photos.add(pic);
	}

	/**
	 * 
	 * This method will provide all the photos of an specific album
	 * 
	 * @return an Array list of photos
	 */
	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	/**
	 * 
	 * @return oldest photo in the album
	 */
	public Photo getOldestPhoto() {
		return oldestPhoto;
	}

	/**
	 * 
	 * @return newest photo in the album
	 */
	public Photo getNewestPhoto() {
		return newestPhoto;
	}

	/**
	 * 
	 * To delete a photo from an album
	 * 
	 * @param pic a photo instance needs to be passed
	 * 
	 */
	public void deletePhoto(Photo pic) {
		photos.remove(pic);
	}

	@Override
	public String toString() {
		return name;
	}

}
