package com.example.jihon.cst2335_final_project;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddMovie extends Activity {

    Movie movie = new Movie();
    Button addButton;
    Button cancelButton;
    Bundle bundle;
    ContentValues contentValues;

    EditText title;
    EditText actor;
    EditText length;
    EditText description;
    EditText rating;
    EditText genre;
    EditText url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        bundle = new Bundle();
        addButton = (Button) findViewById(R.id.movie_add_add_button);
        cancelButton = (Button) findViewById(R.id.movie_add_cancel_button);
        title = (EditText) findViewById(R.id.movie_add_title_value);
        actor = (EditText) findViewById(R.id.movie_add_actor_value);
        length = (EditText) findViewById(R.id.movie_add_length_value);
        description = (EditText) findViewById(R.id.movie_add_description_value);
        rating = (EditText) findViewById(R.id.movie_add_rating_value);
        genre = (EditText) findViewById(R.id.movie_add_genre_value);
        url = (EditText) findViewById(R.id.movie_add_url_value);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemTitle = title.getText().toString();
                String itemActor = actor.getText().toString();
                String itemlength = length.getText().toString();
                String itemDescription = description.getText().toString();
                int itemRating = Integer.valueOf(rating.getText().toString());
                String itemGenre = genre.getText().toString();
                String itemURL = url.getText().toString();

                bundle.putString(MovieDatabaseHelper.KEY_TITLE,itemTitle);
                bundle.putString(MovieDatabaseHelper.KEY_ACTOR,itemActor);
                bundle.putString(MovieDatabaseHelper.KEY_LENGTH,itemlength);
                bundle.putString(MovieDatabaseHelper.KEY_DES,itemDescription);
                bundle.putInt(MovieDatabaseHelper.KEY_RATING,itemRating);
                bundle.putString(MovieDatabaseHelper.KEY_GENRE,itemGenre);
                bundle.putString(MovieDatabaseHelper.KEY_URL,itemURL);

                Intent intent = new Intent();
                intent.putExtra("newMovie",bundle);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
