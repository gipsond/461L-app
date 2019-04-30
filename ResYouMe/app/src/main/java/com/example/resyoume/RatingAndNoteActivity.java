package com.example.resyoume;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.Resume;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class RatingAndNoteActivity extends AppCompatActivity {

    RatingBar ratingUI;
    EditText notesUI;

    protected SingleResumeViewModel singleResumeViewModel;
    protected CompanyInfoViewModel companyInfoViewModel;

    protected void setupUI() {
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
                    System.out.println(dataJson);
                    int rating = dataJson.getInt("rating");
                    String notes = dataJson.getString("notes");
                    System.out.println(rating + " " + notes);
                    ratingUI.setRating(rating);
                    notesUI.setText(notes);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveRNToDB(View view){
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
                    Resume resume = new Resume(dataJson, false);
                    int ratingInt = (int) ratingUI.getRating();
                    resume.contact.setRating(ratingInt);
                    resume.contact.setNotes(notesUI.getText().toString());
                    if (singleResumeViewModel == null) {
                        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
                    }
                    singleResumeViewModel.update(resume);
                } else {
                    CompanyInfo companyInfo = new CompanyInfo(dataJson, false);
                    int ratingInt = (int) ratingUI.getRating();
                    companyInfo.setRating(ratingInt);
                    companyInfo.setNotes(notesUI.getText().toString());
                    if (companyInfoViewModel == null) {
                        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
                    }
                    companyInfoViewModel.update(companyInfo);
                }

                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "Rating and notes have been saved.", duration);
                toast.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
