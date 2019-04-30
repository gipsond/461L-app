package com.example.resyoume;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.lifecycle.ViewModelProviders;

public class SetRatingAndNote extends RatingAndNoteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rating_and_note);
        ratingUI = (RatingBar) findViewById(R.id.ratingBar);
        notesUI = (EditText) findViewById(R.id.notes);
        setupUI();
        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
    }

    public void saveRNToResumeDB(View view){
        saveRNToDB(view);
    }
}
