package com.example.jihon.cst2335_final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chenxiao on 2018-03-30.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "Movie.db";
    private static int VERSION_NUM = 2;
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_ACTOR= "Actors";
    public static final String KEY_LENGTH = "Length";
    public static final String KEY_DES = "Description";
    public static final String KEY_RATING = "Rating";
    public static final String KEY_GENRE = "Genre";
    public static final String KEY_URL = "URL";
    public static final String TABLE_NAME = "myTable";

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + "( " + KEY_ID + " integer primary key autoincrement, "
            + KEY_TITLE  + " text ,"
            + KEY_ACTOR + " text ,"
            + KEY_LENGTH + " text ,"
            + KEY_DES + " text ,"
            + KEY_RATING + " INTEGER ,"
            + KEY_GENRE + " text ,"
            + KEY_URL + " text );";

    public MovieDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME,null,VERSION_NUM);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
