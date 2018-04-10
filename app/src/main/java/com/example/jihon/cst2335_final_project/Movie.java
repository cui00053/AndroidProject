package com.example.jihon.cst2335_final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;


public class Movie extends AppCompatActivity {

    protected final static String ACTIVITY_NAME = "Movie";
    ListView movieListView;
    Button addMovieButton;
    Button viewStatisticsButton;
    public static final int ADD_MOVIE_REQUEST = 1;
    public static final int VIEW_MOVIE_REQUEST = 2;
    ArrayList<String> movieList = new ArrayList<>();
    MovieDatabaseHelper movieDatabaseHelper;
    SQLiteDatabase movieDB;
    ContentValues contentValues;
    Boolean frameExist;
    Cursor cursor;
    MovieAdapter movieAdapter;
    MovieFragment movieFragment;
    FragmentTransaction fragmentTransaction;
    private final static String version = "VERSION 1.0";
    private final static String author = "Chenxiao Cui";
    private  Intent intent;
    private RelativeLayout movieLayout;
    private  String aboutMessageMovie;
    private String helpMessageMovie;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movieLayout = (RelativeLayout)findViewById(R.id.movieLayout) ;
        helpMessageMovie = getString(R.string.helpMessageMovie);
        aboutMessageMovie = getString(R.string.aboutMessageMovie);
        movieAdapter = new MovieAdapter(this);
        movieDatabaseHelper = new MovieDatabaseHelper(Movie.this);
        contentValues = new ContentValues();
        movieDB = movieDatabaseHelper.getWritableDatabase();

        movieListView = (ListView) findViewById(R.id.movieListView);
        movieListView.setAdapter(movieAdapter);
        addMovieButton = (Button) findViewById(R.id.addMovieButton);
        viewStatisticsButton = (Button) findViewById(R.id.vieStatisticsButton);

        Toolbar movieToolbar = (Toolbar) findViewById(R.id.movie_toolbar);
        setSupportActionBar(movieToolbar);
        getSupportActionBar().setTitle("Movie Information");

        showMovie();
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Movie.this, AddMovie.class);
                startActivityForResult(intent, ADD_MOVIE_REQUEST);
            }
        });

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                frameExist = findViewById(R.id.movieFrameLayout) != null;
                cursor = movieDB.rawQuery("SELECT * FROM "+ MovieDatabaseHelper.TABLE_NAME,null );
                if (cursor != null)
                    cursor.moveToFirst();

                cursor.moveToPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt(MovieDatabaseHelper.KEY_ID, cursor.getInt(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID)));
                if (!cursor.getString(1).isEmpty()) {
                    bundle.putString(MovieDatabaseHelper.KEY_TITLE, cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE)));
                } else {
                    bundle.putString(MovieDatabaseHelper.KEY_TITLE, "");
                }
                if (!cursor.getString(2).isEmpty()) {
                    bundle.putString(MovieDatabaseHelper.KEY_ACTOR, cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ACTOR)));
                } else {
                    bundle.putString(MovieDatabaseHelper.KEY_ACTOR, "");
                }
                if (!cursor.getString(3).isEmpty()) {
                    bundle.putString(MovieDatabaseHelper.KEY_LENGTH, cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_LENGTH)));
                } else {
                    bundle.putString(MovieDatabaseHelper.KEY_LENGTH, "");
                }
                if (!cursor.getString(4).isEmpty()) {
                    bundle.putString(MovieDatabaseHelper.KEY_DES, cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_DES)));
                } else {
                    bundle.putString(MovieDatabaseHelper.KEY_DES, "");
                }
                if (!cursor.getString(5).isEmpty()) {
                    bundle.putString(MovieDatabaseHelper.KEY_RATING, cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_RATING)));
                } else {
                    bundle.putString(MovieDatabaseHelper.KEY_RATING, "");
                }
                if (!cursor.getString(6).isEmpty()) {
                    bundle.putString(MovieDatabaseHelper.KEY_GENRE, cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_GENRE)));
                } else {
                    bundle.putString(MovieDatabaseHelper.KEY_GENRE, "");
                }
                if (!cursor.getString(7).isEmpty()) {
                    bundle.putString(MovieDatabaseHelper.KEY_URL, cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_URL)));
                } else {
                    bundle.putString(MovieDatabaseHelper.KEY_URL, "");
                }

                // Running on a tablet, use a Bundle to pass message and database id
                if(frameExist){
                    movieFragment = new MovieFragment(Movie.this);
                    movieFragment.setArguments(bundle);
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.movieFrameLayout,movieFragment);
                    fragmentTransaction.commit();
                }else{
                    Intent intent = new Intent(Movie.this,MovieDetails.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,VIEW_MOVIE_REQUEST);
                }
            }
        });

    }

    private Dialog createHelpDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help);
        builder.setMessage(helpMessageMovie);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                // do nothing
            }
        });
        // Create the AlertDialog
        return builder.create();
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu_movie, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        switch(mi.getItemId()){
            case R.id.about : Snackbar.make(movieLayout, aboutMessageMovie, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                break;
            case R.id.help : createHelpDialog().show();
                break;
            case R.id.activity_one :
                intent = new Intent(Movie.this, OCTranspo.class);
                startActivity(intent);
                break;
            case R.id.activity_three :
                intent = new Intent(Movie.this, PatientIntake.class);
                startActivity(intent);
                break;
            case R.id.activity_four :
                intent = new Intent(Movie.this, QuizCreator.class);
                startActivity(intent);
                break;

        }

        return true;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data ){
        if(resultCode == Activity.RESULT_OK){
            if (requestCode == ADD_MOVIE_REQUEST){
                Bundle bundle = data.getExtras();
                Bundle newMovie = bundle.getBundle("newMovie");
                addMovie(newMovie);
            }
        }else if(resultCode == MovieFragment.deleteMovieResult){
            int itemID = data.getIntExtra(MovieDatabaseHelper.KEY_ID,-1);
            deleteItem(itemID);
        }
    }

    protected void addMovie(Bundle bundle){
        if(bundle!=null){
            String itemTitle = bundle.getString(MovieDatabaseHelper.KEY_TITLE);
            String itemActor = bundle.getString(MovieDatabaseHelper.KEY_ACTOR);
            String itemLength = bundle.getString(MovieDatabaseHelper.KEY_LENGTH);
            String itemDescription = bundle.getString(MovieDatabaseHelper.KEY_DES);
            int itemRating = bundle.getInt(MovieDatabaseHelper.KEY_RATING);
            String itemGenre = bundle.getString(MovieDatabaseHelper.KEY_GENRE);
            String itemURL = bundle.getString(MovieDatabaseHelper.KEY_URL);

            contentValues.put(MovieDatabaseHelper.KEY_TITLE,itemTitle);
            contentValues.put(MovieDatabaseHelper.KEY_ACTOR,itemActor);
            contentValues.put(MovieDatabaseHelper.KEY_LENGTH,itemLength);
            contentValues.put(MovieDatabaseHelper.KEY_DES,itemDescription);
            contentValues.put(MovieDatabaseHelper.KEY_RATING,itemRating);
            contentValues.put(MovieDatabaseHelper.KEY_GENRE,itemGenre);
            contentValues.put(MovieDatabaseHelper.KEY_URL,itemURL);

            movieDB.insert(MovieDatabaseHelper.TABLE_NAME,null,contentValues);
            showMovie();
            movieAdapter.notifyDataSetChanged();

        }
    }

    protected void showMovie(){
        movieList.clear();
        cursor = movieDB.query(MovieDatabaseHelper.TABLE_NAME,new String[]{ MovieDatabaseHelper.KEY_ID, MovieDatabaseHelper.KEY_TITLE,MovieDatabaseHelper.KEY_ACTOR,
        MovieDatabaseHelper.KEY_LENGTH,MovieDatabaseHelper.KEY_DES,MovieDatabaseHelper.KEY_RATING,MovieDatabaseHelper.KEY_GENRE,MovieDatabaseHelper.KEY_URL},null,null,null,null,null);
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String title = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE));
                movieList.add(title);
                cursor.moveToNext();
            }
        }
        movieAdapter.notifyDataSetChanged();
    }

    public void deleteItem(int id){
        movieDB.delete(MovieDatabaseHelper.TABLE_NAME,MovieDatabaseHelper.KEY_ID + "="+id, null);
        showMovie();
    }


    private class MovieAdapter extends ArrayAdapter<String>{
        public MovieAdapter(Context ctx){super(ctx,0);}
        public int getCount(){
            return movieList.size();
        }

        public String getItem(int position){
            return movieList.get(position);
        }

        public View getView(int position,View convertView,ViewGroup parent){
            LayoutInflater inflater = Movie.this.getLayoutInflater();
            View movieView = inflater.inflate(R.layout.movie_list,null);
            TextView movie = (TextView)movieView.findViewById(R.id.movie_text
            );
            movie.setText(   getItem(position)  ); // get the string at position
            //position++;
            return movieView;
        }

        public long getId(int position){
            return position;
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(movieDatabaseHelper.KEY_ID));
        }


    }
}
