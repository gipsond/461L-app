package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.resyoume.db.Resume;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class DisplayActivity extends AppCompatActivity {

    protected SingleResumeViewModel singleResumeViewModel;

    protected Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);

        Intent intent = getIntent();
        String resumeString = intent.getStringExtra("resumeJSON");
        if(resumeString != null){
            try {
                JSONObject resumeJson = new JSONObject(resumeString);
                Resume resume = new Resume(resumeJson, true);
                setResume(resume);
            }
            catch (JSONException e) {}
        }
    }

    protected abstract int getLayoutResourceId();

    protected abstract void setResume(Resume resume);


    /* NFC Functions */
    public void shareResume(View view) {
        Intent nfc_intent = new Intent(this, NFCActivity.class);
        try {
            nfc_intent.putExtra("resumeJSON", resume.toJSONObject().toString());
        } catch (JSONException e) {
        }
        startActivity(nfc_intent);
    }
}


