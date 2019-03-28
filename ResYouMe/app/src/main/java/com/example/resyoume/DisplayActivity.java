package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

import org.json.JSONArray;
import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public abstract class DisplayActivity extends AppCompatActivity {

    protected SingleResumeViewModel singleResumeViewModel;

    protected Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
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


