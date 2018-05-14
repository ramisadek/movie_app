package com.ayamovie.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
GridView gridView;
ArrayList<ModelMovie>arrayListMovies;
CustomAdapter customAdapter;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
SQlite sQlite;
Button favorite;
//ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView=findViewById(R.id.grid);
        arrayListMovies=new ArrayList<>();
        sQlite=new SQlite(MainActivity.this);
          favorite=findViewById(R.id.all_favoriate);
//        progressBar=findViewById(R.id.progress);
//        progressBar.setProgress(0);
//        progressBar.setMax(100);
        new FetchMovies().execute();

        sharedPreferences=getSharedPreferences("pref_file",MODE_PRIVATE);
        editor=sharedPreferences.edit();
//        ModelMovie modelView1=new ModelMovie();
//        modelView1.setPoster(R.drawable.tang);
//        modelView1.setTitle("tangled");
//        arrayListMovies.add(modelView1);
//
//        ModelMovie modelView2=new ModelMovie();
//       modelView2.setPoster(R.drawable.shre);
//        modelView2.setTitle("shrek");
//        arrayListMovies.add(modelView2);
//
//        ModelMovie modelView3=new ModelMovie();
//        modelView3.setPoster(R.drawable.spirited_away);
//        modelView3.setTitle("Spritied Away");
//        arrayListMovies.add(modelView2);
//
//        ModelMovie modelView4=new ModelMovie();
//        modelView4.setPoster(R.drawable.brav);
//        modelView4.setTitle("brave");
//        arrayListMovies.add(modelView4);

/*        ModelMovie modelView5=new ModelMovie();
        modelView5.setPoster(R.drawable.nemo);
        modelView5.setTitle("Finding Nemo ");
        arrayListMovies.add(modelView5);
//*/
//        ModelMovie modelView6=new ModelMovie();
//        modelView6.setPoster(R.drawable.zoo);
//        modelView6.setTitle("zootopia");
//        arrayListMovies.add(modelView6);

        customAdapter=new CustomAdapter(MainActivity.this,arrayListMovies);
        gridView.setAdapter(customAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String email= sharedPreferences.getString("emailKey","no email");
                Toast.makeText(MainActivity.this,email,Toast.LENGTH_LONG).show();



            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ModelMovie modelMovie=arrayListMovies.get(i);
                sQlite.addMovie(modelMovie.getPoster(),modelMovie.getTitle());
                return false;
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,All.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                editor.clear();
                editor.commit();
                editor.clear();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
public class FetchMovies extends AsyncTask<Void,Integer,Integer>{

//first step
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(MainActivity.this,"Async Task Start",Toast.LENGTH_LONG).show();
    }
    //2nd
    @Override
    protected Integer doInBackground(Void... voids) {
//        try {
//            for(int i=0;i<100;i++) {
//                Thread.sleep(100);
//                publishProgress(i);
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Integer result=0;
        final String Base_URL="http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=18147b0826078c6d5e462bf97f3e032d";

        try {
            URL url=new URL(Base_URL);
            HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);



            StringBuffer stringBuffer =new StringBuffer();
            String line;
            while((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line+"\n" );

            }

            String allStringFromApi =stringBuffer.toString();
            getJasonMovieToString(allStringFromApi);


        } catch (IOException e) {
            e.printStackTrace();
        }

        result=1;
        return result;
    }
    //with 2nd
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //progressBar.setProgress(values[0]);
    }
    //third
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
//        progressBar.setVisibility(View.GONE);
  if(result==0){
      Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();
  }
  else{
      customAdapter.notifyDataSetChanged();
  }

    }
}

    private void getJasonMovieToString(String allStringFromApi) {
        try {
            JSONObject jsonObject=new JSONObject(allStringFromApi);
          JSONArray allMovies= jsonObject.getJSONArray("results");
            ModelMovie modelMovie;
          for(int i=0;i<allMovies.length();i++){

               modelMovie=new ModelMovie();

           JSONObject movie=   allMovies.getJSONObject(i);
           modelMovie.setTitle(movie.getString("original_title"));
           modelMovie.setPoster(movie.getString("poster_path"));
            arrayListMovies.add(modelMovie);
          }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
