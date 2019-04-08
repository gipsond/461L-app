package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.resyoume.db.Resume;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class SetRatingAndNote extends AppCompatActivity {

    EditText ratingUI;
    EditText notesUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rating_and_note);
        ratingUI = (EditText) findViewById(R.id.rating);
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
                    int rating = dataJson.getInt("rating");
                    String notes = dataJson.getString("notes");
                    ratingUI.setText(String.valueOf(rating));
                    notesUI.setText(notes);
                } else {
                }
            } catch (JSONException e) {
            }
        }
    }

    public void saveRNToDB(View view){
        Intent intent = getIntent();
        String data = intent.getStringExtra("resumeJSON");
        if(data != null) {
            try {
                JSONObject dataJson = new JSONObject(data);
                String type = dataJson.getString("type");
                if (type.equals("resume")) {
                    Resume resume = new Resume(dataJson);
                    int ratingInt = Integer.parseInt(ratingUI.getText().toString());
                    if(ratingInt > 5){
                        ratingInt = 5;
                        ratingUI.setText("5");
                    }
                    if(ratingInt < 0){
                        ratingInt = 0;
                        ratingUI.setText("0");
                    }
                    resume.rating = ratingInt;
                    resume.notes = notesUI.getText().toString();
                } else {
                }
            } catch (JSONException e) {
            }
        }
    }
}
