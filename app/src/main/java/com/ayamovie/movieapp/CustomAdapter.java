package com.ayamovie.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by egypt2 on 4/6/2018.
 */

public class CustomAdapter extends ArrayAdapter<ModelMovie> {

    Context context;


    public CustomAdapter(@NonNull Context context, @NonNull ArrayList<ModelMovie> objects) {
        super(context, 0, objects);
    this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.layout_row,parent,false);
        ModelMovie modelMovie=getItem(position);
        ImageView img = convertView.findViewById(R.id.img);
       TextView textView=convertView.findViewById(R.id.text);
        textView.setText(modelMovie.getTitle());
//        img.setImageResource(modelMovie.getPoster();
        Picasso.with(context).load( "http://image.tmdb.org/t/p/w185/" + modelMovie.getPoster()).into(img);
        Log.i("imagePoster" ,"http://image.tmdb.org/t/p/w185/" + modelMovie.getPoster() );
        return convertView;
    }
}
