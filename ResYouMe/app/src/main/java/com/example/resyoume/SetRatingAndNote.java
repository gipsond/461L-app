package com.example.resyoume;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.resyoume.db.Resume;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SetRatingAndNote extends AppCompatActivity {

    RatingBar ratingUI;
    EditText notesUI;

    private SingleResumeViewModel singleResumeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rating_and_note);
        ratingUI = (RatingBar) findViewById(R.id.ratingBar);
        notesUI = (EditText) findViewById(R.id.notes);
        Intent intent = getIntent();
        String data = intent.getStringExtra("resumeJSON");
        if(data == null){
            data = intent.getStringExtra("companyInfoJSON");
        }
        if(data != null) {
            try {
                JSONObject dataJson = new JSONObject(data);
                String type = dataJson.getString("type");
                if (type.equals("resume")) {
                    System.out.println(dataJson);
                    JSONObject contactJson = dataJson.getJSONObject("contact");
                    int rating = contactJson.getInt("rating");
                    String notes = contactJson.getString("notes");
                    System.out.println(rating + " " + notes);
                    ratingUI.setRating(rating);
                    notesUI.setText(notes);
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
    }

    public void saveRNToDB(View view){
        Intent intent = getIntent();
        String data = intent.getStringExtra("resumeJSON");
        if(data != null) {
            try {
                JSONObject dataJson = new JSONObject(data);
                String type = dataJson.getString("type");
                if (type.equals("resume")) {
                    Resume resume = new Resume(dataJson, false);
                    int ratingInt = (int) ratingUI.getRating();
                    resume.contact.setRating(ratingInt);
                    resume.contact.setNotes(notesUI.getText().toString());
                    if (singleResumeViewModel == null) {
                        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
                    }
                    singleResumeViewModel.update(resume);

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
