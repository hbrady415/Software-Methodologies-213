package com.example.photorun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity{
    final static String PHOTO_NUMBER = "com.example.photorun.PHOTO_NUMBER";
    final static String ALBUM_NUM = "com.example.photorun.ALBUM_NUMBER";
    private int albNum;
    private Photo currentPhoto;
    private int picNum;
    private int tagFlag=-1;
    private Button delTag;
    private ListView tagList;
    private Button nxt;
    private Button add;
    private String typeHolder;
    private Button bck;
    private ArrayAdapter<Tag> tagAdapter;
    final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_display);
        final ImageView imView = (ImageView) findViewById(R.id.imView);
        nxt = findViewById(R.id.next);
        bck = findViewById(R.id.back);
        delTag = findViewById(R.id.delTag);
        add = findViewById(R.id.addTag);
        tagList = findViewById(R.id.tagList);
        final TextView picName = findViewById(R.id.picName);
        Intent intent = getIntent();
        picNum = intent.getIntExtra(AlbumActivity.PHOTO_NUMBER, -1);
        albNum = intent.getIntExtra(AlbumActivity.ALBUM_NUM, -1);
        currentPhoto = MainActivity.al.albums.get(albNum).getPhotos().get(picNum);
        this.setTitle("Photo View");
        tagList = (ListView) findViewById(R.id.tagList);
        tagList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //albumNames.setSelection(0);
        tagAdapter = new ArrayAdapter<Tag>(context, R.layout.album, MainActivity.al.albums.get(albNum).getPhotos().get(picNum).getTags());
        tagList.setAdapter(tagAdapter);
        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listView.setItemChecked(listView.getSelectedItemPosition(), false);
                tagFlag = position;
                tagList.setSelector(android.R.color.holo_blue_light);
                tagList.requestFocusFromTouch();
                tagList.setSelection(position);
                //listView.setFocusable(position);
                tagAdapter.notifyDataSetChanged();

            }
        });
        picName.setText(currentPhoto.getName());
        if(currentPhoto.getImage()!=null) {
            imView.setImageBitmap(currentPhoto.getImage());
        }

        onNextButton();
        onBackButton();
        onAddButton();
        onDelButton();
        //onMoveButton();
    }
    public void onNextButton(){
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = MainActivity.al.albums.get(albNum).getPhotos().size();
                if(MainActivity.al.albums.get(albNum).getPhotos().size()<=picNum+1){
                    AlertDialog.Builder renameDialog = new AlertDialog.Builder(PhotoActivity.this);
                    renameDialog.setTitle("This is the last photo in this album\n");
                    renameDialog.show();
                    return;
                }
                PhotoActivity.super.finish();
                Intent startIntent = new Intent(context, PhotoActivity.class);

                startIntent.putExtra(PHOTO_NUMBER, picNum+1);
                startIntent.putExtra(ALBUM_NUM, albNum);
                startActivity(startIntent);
            }
        });
    }

    public void onBackButton(){
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = MainActivity.al.albums.get(albNum).getPhotos().size();
                if(picNum-1<0){
                    AlertDialog.Builder renameDialog = new AlertDialog.Builder(PhotoActivity.this);
                    renameDialog.setTitle("This is the first photo in this album\n");
                    renameDialog.show();
                    return;
                }
                PhotoActivity.super.finish();
                Intent startIntent = new Intent(context, PhotoActivity.class);

                startIntent.putExtra(PHOTO_NUMBER, picNum-1);
                startIntent.putExtra(ALBUM_NUM, albNum);
                startActivity(startIntent);
            }
        });
    }

    public void onAddButton(){
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder tagDialog = new AlertDialog.Builder(PhotoActivity.this);
                tagDialog.setTitle("Select a tag type from the list and enter its value in the text box\n");

                View mView = getLayoutInflater().inflate(R.layout.tag_spinner, null);
                final Spinner spinner = mView.findViewById(R.id.spinner);
                final EditText valueInput = mView.findViewById(R.id.valueInput);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(PhotoActivity.this, R.layout.simple_spinner_item, getResources().getStringArray(R.array.types));
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                //valueInput.setInputType(InputType.TYPE_CLASS_TEXT);
                tagDialog.setView(valueInput);
                tagDialog.setView(spinner);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        switch(position){
                            case 0:
                                typeHolder = "Location";
                                break;

                            case 1:
                                typeHolder = "Person";
                                break;
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                tagDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            String valueDat = valueInput.getText().toString();
                            if(valueDat.trim().isEmpty()){
                                AlertDialog.Builder alert = new AlertDialog.Builder(PhotoActivity.this);
                                alert.setTitle("You must enter data for the tag value\n");
                                alert.show();
                                return;
                            }
                            //Tag t = new Tag(typeHolder, valueDat);
                        if(typeHolder.equals("Location")){
                            for(Tag t: currentPhoto.getTags()){
                                if(t.getType().equals("Location")){
                                    AlertDialog.Builder alert = new AlertDialog.Builder(PhotoActivity.this);
                                    alert.setTitle("You can't add 2 location tags\n");
                                    alert.show();
                                    return;
                                }
                            }
                        }
                            MainActivity.al.albums.get(albNum).getPhotos().get(picNum).addTag(typeHolder, valueDat);
                            //notify data set change here
                        tagAdapter.notifyDataSetChanged();
                        MainActivity.al.saveToDisk(context);


                            //System.out.println(typeHolder + " - " + valueDat);
                    }
                });
                tagDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                tagDialog.setView(mView);
                AlertDialog dialog=tagDialog.create();
                dialog.show();

                //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                //spinn.setAdapter(adapter);
               // spinn.setOnItemClickListener();
            }
        });
    }
    public void onDelButton(){
        delTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tagFlag==-1){
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(PhotoActivity.this);
                    deleteDialog.setTitle("Please select an item to delete\n");
                    deleteDialog.show();

                    return;
                }
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(PhotoActivity.this);
                deleteDialog.setTitle("Are you sure you want to delete "+ currentPhoto.getTags().get(tagFlag).toString());

                deleteDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        currentPhoto.getTags().remove(tagFlag);
                        tagList.setSelector(android.R.color.white);
                        tagFlag=-1;
                        tagAdapter.notifyDataSetChanged();
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
}
