package com.example.resyoume;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.lifecycle.ViewModelProviders;

public class SetRatingAndNoteCompany extends RatingAndNoteActivity{

    private CompanyInfoViewModel companyInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rating_and_note_company);
        ratingUI = (RatingBar) findViewById(R.id.ratingBarC);
        notesUI = (EditText) findViewById(R.id.notesC);
        setupUI();
        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
    }

    public void saveRNToCompanyDB(View view){
        saveRNToDB(view);
    }
}
