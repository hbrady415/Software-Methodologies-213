package com.example.photorun;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.content.Context;
import java.util.ArrayList;
import android.view.*;



public class ThumbAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList data;

    public ThumbAdapter(Context context, ArrayList data){
        super(context, R.layout.thumbnail_listitem, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){

        if (currentView == null){
            LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = (View) i.inflate(R.layout.thumbnail_listitem, parent, false);
        }

        ImageView iv = (ImageView) currentView.findViewById(R.id.imageView);
        TextView tv = (TextView) currentView.findViewById(R.id.caption);

        Photo photo = (Photo) data.get(position);
        iv.setImageBitmap(photo.getImage());
        tv.setText(photo.getName());

        return currentView;

    }
}