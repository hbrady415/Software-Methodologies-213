package com.example.photorun;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {
    private ArrayList<Tag> tags;
    private String fileName="";
    transient Bitmap image;

    public Photo(Bitmap bitmap){
        image = bitmap;
    }
    public Bitmap getImage(){
        return image;
    }

    public String getName(){
        return fileName;
    }
    public ArrayList<Tag> getTags(){
        if(tags==null){
            tags=new ArrayList<Tag>();
            return tags;
        }
        return tags;
    }
    public void addTag(String type, String value){
        Tag t = new Tag(type, value);
            tags.add(t);
    }
    public void setFileName(String name){
        this.fileName=name;
    }
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int b;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        while((b = ois.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        image = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        if(image != null){
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
            byte bitmapBytes[] = byteStream.toByteArray();
            oos.write(bitmapBytes, 0, bitmapBytes.length);
        }
    }


}

