package com.example.resyoume;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.resyoume.db.CompanyInfo;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SetRatingAndNoteCompany extends AppCompatActivity {

    RatingBar ratingUI;
    EditText notesUI;

    private CompanyInfoViewModel companyInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rating_and_note_company);
        ratingUI = (RatingBar) findViewById(R.id.ratingBarC);
        notesUI = (EditText) findViewById(R.id.notesC);
        Intent intent = getIntent();
        String data = intent.getStringExtra("companyInfoJSON");
        if(data != null) {
            try {
                JSONObject dataJson = new JSONObject(data);
                String type = dataJson.getString("type");
                if (type.equals("companyInfo")) {
                    System.out.println(dataJson);
                    int rating = dataJson.getInt("rating");
                    String notes = dataJson.getString("notes");
                    System.out.println(rating + " " + notes);
                    ratingUI.setRating(rating);
                    notesUI.setText(notes);
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
    }

    public void saveRNCToDB(View view){
        Intent intent = getIntent();
        String data = intent.getStringExtra("companyInfoJSON");
        if(data != null) {
            try {
                JSONObject dataJson = new JSONObject(data);
                String type = dataJson.getString("type");
                if (type.equals("companyInfo")) {
                    CompanyInfo companyInfo = new CompanyInfo(dataJson, false);
                    int ratingInt = (int) ratingUI.getRating();
                    companyInfo.setRating(ratingInt);
                    companyInfo.setNotes(notesUI.getText().toString());
                    if (companyInfoViewModel == null) {
                        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
                    }
                    companyInfoViewModel.update(companyInfo);

                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Rating and notes have been saved.", duration);
                    toast.show();
                } else {
                }
            } catch (JSONException e) {
            }
        }
    }
}
