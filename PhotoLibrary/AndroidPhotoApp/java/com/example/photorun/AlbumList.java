package com.example.photorun;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class AlbumList implements Serializable {

    public static final long serialVersionUID = 0L;
    public static final String fileName = "albums.ser";
    public ArrayList<Album> albums;
    //public static Album searchResults = new Album("results");

    /*public ArrayList<Album>getAList(){
        return albums;
    }*/

    public static AlbumList loadFromDisk(Context context){
        AlbumList al = null;
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            al = (AlbumList) ois.readObject();

            if (al.albums == null) {
                al.albums = new ArrayList<Album>();
            }
            fis.close();
            ois.close();
        } catch (Exception e) {
            return null;
        }
        return al;
    }

    public void saveToDisk(Context context){
        ObjectOutputStream oos;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

