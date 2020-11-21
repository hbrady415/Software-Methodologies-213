package com.example.photorun;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ListView searchList;
    private Button locSearch;
    private Button perSearch;
    private Button andSearch;
    private Button orSearch;
    private EditText locText;
    private EditText perText;
    private ThumbAdapter picAdapter;
    private ArrayList<Photo> arr=new ArrayList<Photo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchList=findViewById(R.id.searchList);
        locSearch=findViewById(R.id.locSearch);
        perSearch=findViewById(R.id.perSearch);
        orSearch=findViewById(R.id.orSearch);
        andSearch=findViewById(R.id.andSearch);
        locText=findViewById(R.id.locText);
        perText=findViewById(R.id.perText);

        picAdapter = new ThumbAdapter(this, arr);
        searchList.setAdapter(picAdapter);
        onLocSearch();
        onPerSearch();
        onAndSearch();
        onOrSearch();
    }


    public void onLocSearch(){
        locSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.clear();
                picAdapter.notifyDataSetChanged();
                if(locText.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(SearchActivity.this);
                    deleteDialog.setTitle("You must enter a tag value\n");
                    deleteDialog.show();
                    return;
                }
                String value= locText.getText().toString();
                Tag temp = new Tag("Location", value);
                for(int a = 0; a<MainActivity.al.albums.size(); a++) {
                    for(int p = 0; p<MainActivity.al.albums.get(a).getPhotos().size(); p++) {
                        for(int t = 0; t<MainActivity.al.albums.get(a).getPhotos().get(p).getTags().size(); t++) {
                            if(MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).equals(temp) || MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).toString().startsWith("Location - "+ value)) {
                                arr.add(MainActivity.al.albums.get(a).getPhotos().get(p));
                                picAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

            }
        });
    }
    public void onPerSearch(){
        perSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.clear();
                picAdapter.notifyDataSetChanged();
                if(perText.getText().toString().trim().isEmpty()){
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(SearchActivity.this);
                    deleteDialog.setTitle("You must enter a tag value\n");
                    deleteDialog.show();
                    return;
                }
                String value= perText.getText().toString();
                Tag temp = new Tag("Person", value);
                for(int a = 0; a<MainActivity.al.albums.size(); a++) {
                    for(int p = 0; p<MainActivity.al.albums.get(a).getPhotos().size(); p++) {

                        for(int t = 0; t<MainActivity.al.albums.get(a).getPhotos().get(p).getTags().size(); t++) {
                            if(MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).equals(temp) || MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).toString().startsWith("Person - "+ value)) {
                                arr.add(MainActivity.al.albums.get(a).getPhotos().get(p));
                                picAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }

            }
        });
    }
    public void onAndSearch(){
        andSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.clear();
                picAdapter.notifyDataSetChanged();
                if(locText.getText().toString().trim().isEmpty() || perText.getText().toString().isEmpty()){
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(SearchActivity.this);
                    deleteDialog.setTitle("You must enter a tag value\n");
                    deleteDialog.show();
                    return;
                }
                String locVal= locText.getText().toString();
                Tag locTemp = new Tag("Location", locVal);
                String perVal = perText.getText().toString();
                Tag perTemp = new Tag("Person", perVal);
                for(int a = 0; a<MainActivity.al.albums.size(); a++) {
                    for(int p = 0; p<MainActivity.al.albums.get(a).getPhotos().size(); p++) {
                        boolean locPass=false;
                        boolean perPass=false;
                        for(int t = 0; t<MainActivity.al.albums.get(a).getPhotos().get(p).getTags().size(); t++) {
                            if(MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).equals(perTemp) || MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).toString().startsWith(perTemp.toString())) {
                                perPass=true;
                            }

                            if(MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).equals(locTemp) || MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).toString().startsWith(locTemp.toString())) {
                                locPass=true;
                            }
                            if(locPass && perPass){
                                arr.add(MainActivity.al.albums.get(a).getPhotos().get(p));
                                picAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

            }
        });
    }
    public void onOrSearch(){
        orSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.clear();
                picAdapter.notifyDataSetChanged();
                if(locText.getText().toString().trim().isEmpty() || perText.getText().toString().isEmpty()){
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(SearchActivity.this);
                    deleteDialog.setTitle("You must enter a tag value\n");
                    deleteDialog.show();
                    return;
                }
                String locVal= locText.getText().toString();
                Tag locTemp = new Tag("Location", locVal);
                String perVal = perText.getText().toString();
                Tag perTemp = new Tag("Person", perVal);
                for(int a = 0; a<MainActivity.al.albums.size(); a++) {
                    for(int p = 0; p<MainActivity.al.albums.get(a).getPhotos().size(); p++) {

                        for(int t = 0; t<MainActivity.al.albums.get(a).getPhotos().get(p).getTags().size(); t++) {
                            if(MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).equals(perTemp) || MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).toString().startsWith(perTemp.toString())) {
                                arr.add(MainActivity.al.albums.get(a).getPhotos().get(p));
                                picAdapter.notifyDataSetChanged();
                                break;
                            }

                            if(MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).equals(locTemp) || MainActivity.al.albums.get(a).getPhotos().get(p).getTags().get(t).toString().startsWith(locTemp.toString())) {
                                arr.add(MainActivity.al.albums.get(a).getPhotos().get(p));
                                picAdapter.notifyDataSetChanged();
                                break;
                            }

                        }
                    }
                }

            }
        });
    }
}
