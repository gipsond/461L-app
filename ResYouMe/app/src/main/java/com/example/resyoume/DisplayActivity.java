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

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class DisplayActivity extends AppCompatActivity {

    private SingleResumeViewModel singleResumeViewModel;

    private Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
        singleResumeViewModel.getResume().observe(this, this::setResume);
    }

    private void setResume(Resume resume) {
        this.resume = resume;

        final TextView resumeHeader;
        final TextView addressView;
        final TextView addressView2;
        final TextView emailView;
        final TextView phoneView;
        final TextView websiteView;
        final TextView interestsView;
        final TextView publicationsView;
        final TextView educationPhasesView;
        final TextView workPhasesView;


        LinearLayout ll = new LinearLayout(this);
        ScrollView resumeView = new ScrollView(this);

        resumeView = findViewById(R.id.resumeview);


        resumeHeader = findViewById(R.id.resumeHeader);
        addressView = findViewById(R.id.addressView);
        addressView2 = findViewById(R.id.addressView2);
        emailView = findViewById(R.id.emailView);
        phoneView = findViewById(R.id.phoneView);
        websiteView = findViewById(R.id.websiteView);
        interestsView = findViewById(R.id.interestsView);
        publicationsView = findViewById(R.id.publicationsView);
        educationPhasesView = findViewById(R.id.educationPhasesView);
        workPhasesView = findViewById(R.id.workPhasesView);

        Contact contact = resume.contact;
        resumeHeader.setText(contact.getTitle() + " " + contact.getFirstName() + " " + contact.getLastName());
        addressView.setText(contact.getAddress());
        addressView2.setText(contact.getCity() + ", " + contact.getState() + " " + contact.getPostcode());
        emailView.setText(contact.getEmail());
        phoneView.setText(contact.getPhoneNumber());
        websiteView.setText(contact.getHomepage());
        interestsView.setText("Interests: " + contact.getInterests());
        publicationsView.setText("Publications: " + contact.getPublications());

        StringBuilder eduPhasesStr = new StringBuilder("Education:\n");
        for (EducationPhase educationPhase : resume.educationPhases) {
            eduPhasesStr.append(educationPhase.getPlaintext());
            eduPhasesStr.append('\n');
        }
        educationPhasesView.setText(eduPhasesStr.toString());

        StringBuilder workPhasesStr = new StringBuilder("Work Experience:\n");
        for (WorkPhase workPhase : resume.workPhases) {
            workPhasesStr.append(workPhase.getPlaintext());
            workPhasesStr.append('\n');
        }
        workPhasesView.setText(workPhasesStr.toString());
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
