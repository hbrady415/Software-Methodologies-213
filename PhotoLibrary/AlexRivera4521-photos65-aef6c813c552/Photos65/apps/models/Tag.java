package models;

import java.io.Serializable;

/**
 * 
 * 
 * @author Hunter Brady
 * @author Alex Rivera
 *
 */
public class Tag implements Serializable{
	
	private String type, value;
	
	/**
	 * 
	 * @param t string that represents the type of tag
	 * @param v string that represents the tag value
	 */
	public Tag(String t, String v) {
		this.type = t;
		this.value = v;
	}
	
	/**
	 * 
	 * @return tag type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param t sets the type of the tag
	 */
	public void setType(String t) {
		this.type = t;
	}

	/**
	 * 
	 * @return the tag value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * 
	 * @param v the tag value to be set
	 */
	public void setValue(String v) {
		this.value = v;
	}

	@Override
	public String toString() {
		return type + ": " + value;
	}

	/**
	 * 
	 * Method to check if two tags are the same
	 * 
	 * @param t a tag instance
	 * 
	 * @return true if a tag is equal to a new tag false otherwise
	 */
	public boolean equals(Tag t) {
		if(value.contentEquals(t.getValue()) && type.contentEquals(t.getType())) {
			return true;
		}
		return false;
	}

}