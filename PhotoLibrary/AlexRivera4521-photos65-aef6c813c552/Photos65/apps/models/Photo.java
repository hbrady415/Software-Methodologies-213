package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * 
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class Photo implements Serializable{
	
	private String caption;
	private ArrayList<Tag> tags;
	private LocalDate date;
	private SerializableImage i;
	
	/**
	 *Photo object to store caption, Image, edit date and a list of tags to be used in an album in UserList
	 */
	public Photo() {
		caption = "";		
		tags = new ArrayList<Tag>();
		date = LocalDate.now();
		i = new SerializableImage();
	}

	/**
	 * 
	 * Returns the caption in the specified photo
	 * 
	 * @return a string that contains the captions
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * 
	 * @param cap sets the caption of the specified photo
	 */
	public void setCaption(String cap) {
		caption=cap;
		date = LocalDate.now();
	}
	

	/**
	 * 
	 * @param im The image
	 */
	public Photo(Image im) {
		this();
		i.setImage(im);
	}

	/**
	 * 
	 * @return the image created by the photo
	 */
	public Image getImage() {
		return i.getImage();
	}

	/**
	 * 
	 * @return a serializable image
	 */
	public SerializableImage getSerializableImage() {
		return i;
	}

	/**
	 * 
	 * @param type  String that represents the type of Tag
	 * 
	 * @param value String that represents the value of the Tag
	 */
	public void addTag(String type, String value) {
		tags.add(new Tag(type, value));
		date = LocalDate.now();
	}

	/**
	 * 
	 * @param type  String Type for the tag to be deleted
	 * 
	 * @param value String value of the tag to be deleted
	 */
	public void deleteTag(String type, String value) {
		for(Tag t: tags) {
			if(t.getType().contentEquals(type) && t.getValue().contentEquals(value)) {
				tags.remove(t);
			}
		}
		date = LocalDate.now();
	}

	/**
	 * 
	 * @return an array list of tags
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}

	/**
	 * 
	 * @param t sets the array list of tags
	 */
	public void setTags(ArrayList<Tag> t) {
		tags=t;
	}

	/**
	 * 
	 * @return a date
	 */
	public LocalDate getTime() {
		return date;
	}

	/**
	 * 
	 * @return edited date
	 */
	public String getTimeString() {
		String editDate = date.toString().substring(5);
		editDate += "-";
		editDate += date.toString().substring(0, 4);
		return editDate;
	}

	/**
	 * 
	 * @param l sets date
	 */
	public void setDate(LocalDate l) {
		date = l;
	}
}
