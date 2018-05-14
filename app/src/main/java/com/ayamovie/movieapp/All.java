package com.ayamovie.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class All extends AppCompatActivity {

    ArrayList<ModelMovie> arrayListMovies;
    CustomAdapter customAdapter;
    SQlite sQlite;
    GridView gridView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        button=findViewById(R.id.deleteall);
        sQlite=new SQlite(All.this);
        gridView=findViewById(R.id.grid2);
        arrayListMovies=sQlite.allMovies();
        customAdapter=new CustomAdapter(All.this,arrayListMovies);
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                sQlite.deleteRow(arrayListMovies.get(i).getId());
                arrayListMovies.remove(i);
                customAdapter.notifyDataSetChanged();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sQlite.deleteAll();
               arrayListMovies.clear();
                customAdapter.notifyDataSetChanged();

            }
        });
    }


}
