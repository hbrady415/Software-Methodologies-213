package com.example.photorun;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AlbumActivity extends AppCompatActivity {
    final static String PHOTO_NUMBER = "com.example.photorun.PHOTO_NUMBER";
    final static String ALBUM_NUM = "com.example.photorun.ALBUM_NUMBER";
    private Button add;
    private Button delete;
    private Button display;
    private GridView thumbList;
    private ThumbAdapter picAdapter;
    private int albNum;
    final Context context=this;
    private Button move;
    int flag=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity);

        thumbList= findViewById(R.id.thumbList);
        Intent intent = getIntent();
        albNum = intent.getIntExtra(MainActivity.ALBUM_NUMBER, -1);
        System.out.println(albNum);

        picAdapter = new ThumbAdapter(this, MainActivity.al.albums.get(albNum).getPhotos());
        thumbList.setAdapter(picAdapter);
        add=findViewById(R.id.add);
        move=findViewById(R.id.move);
        display=findViewById(R.id.display);
        delete=findViewById(R.id.delete);
        thumbList.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flag = position;
                thumbList.setSelector(android.R.color.holo_blue_light);
                thumbList.requestFocusFromTouch();
                thumbList.setSelection(position);
                //listView.setFocusable(position);
                picAdapter.notifyDataSetChanged();
            }
        });
        this.setTitle("Photos in " + MainActivity.al.albums.get(albNum).toString());
        onAddClick();
        onDeleteClick();
        onMoveButton();
        onDisplayClick();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

            ImageView iv = new ImageView(this);

            iv.setImageURI(selectedImage);

            BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
            Bitmap selectedImageGal = drawable.getBitmap();

            Photo photoToAdd = new Photo(selectedImageGal);

            File f = new File(Objects.requireNonNull(selectedImage.getPath()));
            String pathID = f.getAbsolutePath();
            String filename = pathToFileName(selectedImage);
            photoToAdd.setFileName(filename);


            MainActivity.al.albums.get(albNum).getPhotos().add(photoToAdd);
            MainActivity.al.saveToDisk(context);

            thumbList.setAdapter(picAdapter);
            //Log.i("Image Path", selectedImage.getPath());
        }
    }
    private String pathToFileName(Uri selectedImage){
        String filename = "not found";
        String[] column = {MediaStore.MediaColumns.DISPLAY_NAME};
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(selectedImage, column, null, null, null);

        if(cursor != null) {
            try {
                if (cursor.moveToFirst()){
                    filename = cursor.getString(0);
                }
            } catch (Exception e){
                System.out.println("Broken");
            }
        }
        return filename;
    }

    public void onAddClick(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

                thumbList.setAdapter(picAdapter);
            }
        });
    }
    public void onDeleteClick(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==-1){
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(AlbumActivity.this);
                    deleteDialog.setTitle("Please select an item to delete\n");
                    deleteDialog.show();

                    return;
                }
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(AlbumActivity.this);
                deleteDialog.setTitle("Are you sure you want to delete ");

                deleteDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        MainActivity.al.albums.get(albNum).getPhotos().remove(flag);
                        thumbList.setSelector(android.R.color.white);
                        flag=-1;
                        picAdapter.notifyDataSetChanged();
                        MainActivity.al.saveToDisk(context);
                        //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                deleteDialog.show();
            }

        });
    }
    public void onDisplayClick(){
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==-1){
                    AlertDialog.Builder renameDialog = new AlertDialog.Builder(AlbumActivity.this);
                    renameDialog.setTitle("Please select an item to rename\n");
                    renameDialog.show();
                    return;
                }
                Intent startIntent = new Intent(context, PhotoActivity.class);

                startIntent.putExtra(PHOTO_NUMBER, flag);
                startIntent.putExtra(ALBUM_NUM, albNum);
                startActivity(startIntent);
                //Intent startIntent = new Intent(context, AlbumActivity.class);
                //startActivity(startIntent);
            }
        });
    }
    public void onMoveButton(){
        move.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder moveDialog = new AlertDialog.Builder(AlbumActivity.this);
                moveDialog.setTitle("Enter the album you want to put this photo into\n");
                final EditText moveInput = new EditText(AlbumActivity.this);
                moveInput.setInputType(InputType.TYPE_CLASS_TEXT);
                moveDialog.setView(moveInput);
                moveDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check =0;
                        String result=moveInput.getText().toString();
                        for(Album a: MainActivity.al.albums){
                            if(result.toString().contentEquals(a.toString())){
                                check=1;
                                a.getPhotos().add(MainActivity.al.albums.get(albNum).getPhotos().get(flag));
                                MainActivity.al.albums.get(albNum).getPhotos().remove(flag);
                                MainActivity.al.saveToDisk(context);
                                picAdapter.notifyDataSetChanged();
                            }
                        }
                        if(check==0) {
                            AlertDialog.Builder miniDialog = new AlertDialog.Builder(AlbumActivity.this);
                            miniDialog.setTitle("No album with this name exists\n");
                            miniDialog.show();
                        }

                        //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });
                moveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                moveDialog.show();
            }
        });
    }
}
