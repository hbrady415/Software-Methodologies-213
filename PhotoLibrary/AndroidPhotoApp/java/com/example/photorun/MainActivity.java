package com.example.photorun;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //final static String PHOTO_NUMBER = "com.example.photorun.PHOTO_NUMBER";
    final static String ALBUM_NUMBER = "com.example.photorun.ALBUM_NUMBER";
    final Context context = this;
    private int flag = -1;
    public static AlbumList al;
    private ListView listView;
    private Button create;
    private Button rename;
    private Button open;
    private Button search;
    private Button delete;
    private ArrayAdapter<Album> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        al = AlbumList.loadFromDisk(context);
        if (al == null) {
            al = new AlbumList();
        }

        if (al.albums == null) {
            al.albums = new ArrayList<Album>();
        }


        this.setTitle("User Albums");
       /*al=new AlbumList();
        al.saveToDisk(this);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.myList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //albumNames.setSelection(0);
        adapter = new ArrayAdapter<Album>(context, R.layout.album, al.albums);
        listView.setAdapter(adapter);
        create=findViewById(R.id.create);
        delete=findViewById(R.id.delete);
        search = findViewById(R.id.perSearch);
        open = findViewById(R.id.open);
        rename=findViewById(R.id.rename);
        //adapter = new ArrayAdapter<Album>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listView.setItemChecked(listView.getSelectedItemPosition(), false);
                flag = position;
                listView.setSelector(android.R.color.holo_blue_light);
                listView.requestFocusFromTouch();
                listView.setSelection(position);
                //listView.setFocusable(position);
                adapter.notifyDataSetChanged();

            }
        });
        onCreateClick();
        onDeleteClick();
        onRenameClick();
        onOpenClick();
        onSearchClick();

    }
    public void onCreateClick(){
        flag=-1;
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder createDialog = new AlertDialog.Builder(MainActivity.this);
                createDialog.setTitle("Enter Album Name");

                final EditText createInput = new EditText(MainActivity.this);

                createInput.setInputType(InputType.TYPE_CLASS_TEXT);
                createDialog.setView(createInput);

                createDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result=createInput.getText().toString();
                        if(result.trim().isEmpty()){
                            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                            deleteDialog.setTitle("You must enter a name\n");
                            deleteDialog.show();
                            return;
                        }
                        for(Album a: al.albums){
                            if(result.toString().contentEquals(a.toString())){
                                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                                deleteDialog.setTitle("An album with this name exists\n");
                                deleteDialog.show();
                                return;
                            }
                        }

                        al.albums.add(new Album(result));
                        //arrayList.add(new Album(result));
                        adapter.notifyDataSetChanged();

                        al.saveToDisk(context);
                        //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });
                createDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                createDialog.show();
            }
        });
    }
    public void onDeleteClick(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==-1){
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                    deleteDialog.setTitle("Please select an item to delete\n");
                    deleteDialog.show();

                    return;
                }
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                deleteDialog.setTitle("Are you sure you want to delete "+ al.albums.get(flag).toString());

                deleteDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        al.albums.remove(flag);
                        listView.setSelector(android.R.color.white);
                        flag=-1;
                        adapter.notifyDataSetChanged();
                        al.saveToDisk(context);
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
    public void onRenameClick(){
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==-1){
                    AlertDialog.Builder renameDialog = new AlertDialog.Builder(MainActivity.this);
                    renameDialog.setTitle("Please select an item to rename\n");
                    renameDialog.show();
                    return;
                }
                AlertDialog.Builder renameDialog = new AlertDialog.Builder(MainActivity.this);
                renameDialog.setTitle("Enter "+ al.albums.get(flag).toString() + "'s new name");
                final EditText renameInput = new EditText(MainActivity.this);
                renameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                renameDialog.setView(renameInput);
                renameDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result=renameInput.getText().toString();
                        if(result.trim().isEmpty()){
                            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                            deleteDialog.setTitle("You must enter a name\n");
                            deleteDialog.show();
                            return;
                        }
                        for(Album a: al.albums){
                            if(result.toString().contentEquals(a.toString())){
                                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                                deleteDialog.setTitle("An album with this name exists\n");
                                deleteDialog.show();
                                return;
                            }
                        }
                        al.albums.get(flag).setName(result);
                        listView.setSelector(android.R.color.white);
                        flag=-1;
                        adapter.notifyDataSetChanged();
                        al.saveToDisk(context);
                        //Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                });
                renameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                renameDialog.show();
            }
        });
    }
    public void onOpenClick(){
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==-1){
                    AlertDialog.Builder renameDialog = new AlertDialog.Builder(MainActivity.this);
                    renameDialog.setTitle("Please select an item to open\n");
                    renameDialog.show();
                    return;
                }
                Intent startIntent = new Intent(context, AlbumActivity.class);

                startIntent.putExtra(ALBUM_NUMBER, flag);
                startActivity(startIntent);
                //Intent startIntent = new Intent(context, AlbumActivity.class);
                //startActivity(startIntent);
            }
        });
    }
    public void onSearchClick(){
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(context, SearchActivity.class);
                startActivity(startIntent);
                //Intent startIntent = new Intent(context, AlbumActivity.class);
                //startActivity(startIntent);
            }
        });
    }

}
