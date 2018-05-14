package com.ayamovie.movieapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/**
 * Created by egypt2 on 4/13/2018.
 */

public class SQlite extends SQLiteOpenHelper {
private static final String DATABASE_NAME="Movie posters";
    private static final int DATABASE_VERSION=3;

    public SQlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dp) {
        String sql="create table MoViePoster (id integer primary key AUTOINCREMENT ,movieName text ,poster text)";
        dp.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase dp, int oldVersion, int newVersion) {
           String sql ="drop table MoViePoster";
           dp.execSQL(sql);
           onCreate(dp);
    }

    public void addMovie(String poster,String name){
        SQLiteDatabase dp=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("poster",poster);
        contentValues.put("movieName",name);
        dp.insert("MoViePoster",null,contentValues);

    }
    public void deleteRow(int row) {
        SQLiteDatabase dp=getWritableDatabase();
        dp.delete("MoViePoster", "id =?" ,new String[]{String.valueOf(row)});
    }


    public void  deleteAll(){
        SQLiteDatabase dp=getWritableDatabase();

        String sql="delete from MoViePoster";
        dp.execSQL(sql);

    }

    public ArrayList<ModelMovie> allMovies(){
         ArrayList<ModelMovie> arrayList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from MoViePoster",null);
         if (cursor.moveToFirst()) {
             do {
                 ModelMovie modelMovie=new ModelMovie();
                 modelMovie.setTitle(cursor.getString(1));
                 modelMovie.setPoster(cursor.getString(2));
                 modelMovie.setId(cursor.getInt(0));
                arrayList.add(modelMovie);
             }
             while (cursor.moveToNext());
         }

         return arrayList;
    }


}
