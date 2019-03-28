package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayMccombs extends DisplayActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String resumeString = intent.getStringExtra("resumeJSON");
        if (resumeString != null) {
            try {
                JSONObject resumeJson = new JSONObject(resumeString);
                Resume resume = new Resume(resumeJson);
                setResume(resume);
            } catch (JSONException e) {
            }
        }
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_display_mccombs;
    }

    @Override
    protected void setResume(Resume resume){
        this.resume = resume;

        final TextView resumeHeader;
        final TextView emailPhoneView;
        final TextView addressAndPhonehoneView;
        final TextView educationView;
        final TextView experienceView;
        final TextView projectsView;
        final TextView leadershipView;
        final TextView honorsView;
        final TextView additionalInfoView;


        LinearLayout ll = new LinearLayout(this);
        ScrollView resumeView = new ScrollView(this);

        resumeView = findViewById(R.id.resumeview);


        resumeHeader = findViewById(R.id.nameView);
        emailPhoneView = findViewById(R.id.emailPhoneView);
        addressAndPhonehoneView = findViewById(R.id.addressAndPhoneView);
        educationView = findViewById(R.id.educationView);
        experienceView = findViewById(R.id.experienceView);
        projectsView = findViewById(R.id.projectsView);
        leadershipView = findViewById(R.id.leadershipView);
        honorsView = findViewById(R.id.honorsView);
        additionalInfoView = findViewById(R.id.additionalInfoView);

        Contact contact = resume.contact;
        resumeHeader.setText(contact.getTitle() + " " + contact.getFirstName() + " " + contact.getLastName());
        emailPhoneView.setText(contact.getEmail());
        addressAndPhonehoneView.setText(contact.getAddress() + " \u2022 " +
                contact.getCity() + ", " +
                contact.getState() + " " +
                contact.getPostcode() + " \u2022 " +
                contact.getPhoneNumber());
        StringBuilder eduPhasesStr = new StringBuilder();
        for (EducationPhase educationPhase : resume.educationPhases) {
            eduPhasesStr.append(educationPhase.getPlaintext());
            eduPhasesStr.append('\n');
        }

        educationView.setText(eduPhasesStr.toString());

        StringBuilder workPhasesStr = new StringBuilder();
        for (WorkPhase workPhase : resume.workPhases) {
            workPhasesStr.append(workPhase.getPlaintext());
            workPhasesStr.append('\n');
        }
        experienceView.setText(workPhasesStr.toString());

        projectsView.setText(contact.getPublications());

        additionalInfoView.setText(contact.getInterests());
    }
}
