package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.Resume;

import org.json.JSONArray;
import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class DisplayActivityCard extends AppCompatActivity {

    private SingleResumeViewModel singleResumeViewModel;

    private Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_card);
        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
        //singleResumeViewModel.getResume().observe(this, this::setResume);
        Intent intent = getIntent();
        String resumeString = intent.getStringExtra("resumeJSON");
        if(resumeString != null){
            try {
                JSONArray resumeJson = new JSONArray(resumeString);
                Resume resume = new Resume(resumeJson);
                setResume(resume);
            }
            catch (JSONException e) {}
        }
    }

    private void setResume(Resume resume) {
        this.resume = resume;

        final TextView resumeHeader;
        final TextView addressView;
        final TextView addressView2;
        final TextView emailView;
        final TextView phoneView;
        final TextView websiteView;


        LinearLayout ll = new LinearLayout(this);
        ScrollView resumeView = new ScrollView(this);

        resumeView = findViewById(R.id.resumeview);


        resumeHeader = findViewById(R.id.resumeHeader);
        addressView = findViewById(R.id.addressView);
        addressView2 = findViewById(R.id.addressView2);
        emailView = findViewById(R.id.emailView);
        phoneView = findViewById(R.id.phoneView);
        websiteView = findViewById(R.id.websiteView);

        Contact contact = resume.contact;
        resumeHeader.setText(contact.getTitle() + " " + contact.getFirstName() + " " + contact.getLastName());
        addressView.setText(contact.getAddress());
        addressView2.setText(contact.getCity() + ", " + contact.getState() + " " + contact.getPostcode());
        emailView.setText(contact.getEmail());
        phoneView.setText(contact.getPhoneNumber());
        websiteView.setText(contact.getHomepage());
    }

    /* NFC Functions */
    public void shareResume(View view){
        Intent nfc_intent = new Intent(this, NFCActivity.class);
        try {
            nfc_intent.putExtra("resumeJSON", resume.toJSONArray().toString());
        }
        catch (JSONException e) {}
        startActivity(nfc_intent);
    }
}
