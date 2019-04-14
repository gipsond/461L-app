package com.example.resyoume;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resyoume.db.CompanyInfo;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class SetRatingAndNoteCompany extends AppCompatActivity {

    EditText ratingUI;
    EditText notesUI;

    private CompanyInfoViewModel companyInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rating_and_note_company);
        ratingUI = (EditText) findViewById(R.id.ratingC);
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
                    ratingUI.setText(String.valueOf(rating));
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
                    int ratingInt = Integer.parseInt(ratingUI.getText().toString());
                    if(ratingInt > 5){
                        ratingInt = 5;
                        ratingUI.setText("5");
                    }
                    if(ratingInt < 0){
                        ratingInt = 0;
                        ratingUI.setText("0");
                    }
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
