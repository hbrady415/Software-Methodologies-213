package com.example.photorun;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private String name;
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public Album(String name){
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhotos(ArrayList<Photo>photoz) {
        this.photos = photoz;
    }
    public void removePhoto(Photo photo){
        photos.remove(photo);
    }
    public ArrayList<Photo> getPhotos(){
        return photos;
    }
    public String toString(){
        return name;
    }
}
