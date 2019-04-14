package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayCLA extends DisplayActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String resumeString = intent.getStringExtra("resumeJSON");
        if (resumeString != null) {
            try {
                JSONObject resumeJson = new JSONObject(resumeString);
                Resume resume = new Resume(resumeJson, true);
                setResume(resume);
            } catch (JSONException e) {
            }
        }
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_display_cla;
    }

    @Override
    protected void setResume(Resume resume){
        this.resume = resume;

        final TextView resumeHeader;
        final TextView addressPhoneEmailView;
        final TextView educationViewLeft1;
        final TextView educationViewRight1;
        final TextView educationViewLeft2;
        final TextView educationViewRight2;
        final TextView experienceViewLeft1;
        final TextView experienceViewRight1;
        final TextView experienceView1;
        final TextView experienceViewLeft2;
        final TextView experienceViewRight2;
        final TextView experienceView2;
        final TextView experienceViewLeft3;
        final TextView experienceViewRight3;
        final TextView experienceView3;
        final TextView leadershipViewLeft1;
        final TextView leadershipViewRight1;
        final TextView leadershipView1;
        final TextView leadershipViewLeft2;
        final TextView leadershipViewRight2;
        final TextView leadershipView2;
        final TextView leadershipViewLeft3;
        final TextView leadershipViewRight3;
        final TextView leadershipView3;
        final TextView skillsView;
        final TextView additionalInfoView;
        final TextView notesView;


        resumeHeader = findViewById(R.id.nameView);
        addressPhoneEmailView = findViewById(R.id.addressPhoneEmailView);
        educationViewLeft1 = findViewById(R.id.educationViewLeft1);
        educationViewRight1 = findViewById(R.id.educationViewRight1);
        educationViewLeft2 = findViewById(R.id.educationViewLeft2);
        educationViewRight2 = findViewById(R.id.educationViewRight2);
        experienceViewLeft1 = findViewById(R.id.experienceViewLeft1);
        experienceViewRight1 = findViewById(R.id.experienceViewRight1);
        experienceView1 = findViewById(R.id.experienceView1);
        experienceViewLeft2 = findViewById(R.id.experienceViewLeft2);
        experienceViewRight2 = findViewById(R.id.experienceViewRight2);
        experienceView2 = findViewById(R.id.experienceView2);
        experienceViewLeft3 = findViewById(R.id.experienceViewLeft3);
        experienceViewRight3 = findViewById(R.id.experienceViewRight3);
        experienceView3 = findViewById(R.id.experienceView3);
        leadershipViewLeft1 = findViewById(R.id.leadershipViewLeft1);
        leadershipViewRight1 = findViewById(R.id.leadershipViewRight1);
        leadershipView1 = findViewById(R.id.leadershipView1);
        leadershipViewLeft2 = findViewById(R.id.leadershipViewLeft2);
        leadershipViewRight2 = findViewById(R.id.leadershipViewRight2);
        leadershipView2 = findViewById(R.id.leadershipView2);
        leadershipViewLeft3 = findViewById(R.id.leadershipViewLeft3);
        leadershipViewRight3 = findViewById(R.id.leadershipViewRight3);
        leadershipView3 = findViewById(R.id.leadershipView3);
        skillsView = findViewById(R.id.skillsView);
        additionalInfoView = findViewById(R.id.additionalInfoView);
        notesView = findViewById(R.id.notesView);

        Contact contact = resume.contact;
        resumeHeader.setText(contact.getFirstName() + " " + contact.getLastName());
        addressPhoneEmailView.setText(contact.getAddress() + "," +
                contact.getCity() + "," +
                contact.getState() + " " +
                contact.getPostcode() + " \u2022 " +
                contact.getPhoneNumber() + " \u2022 " +
                contact.getEmail());

        if(resume.educationPhases.size() > 0) {
            setEducationPhase(resume.educationPhases.get(0), educationViewLeft1, educationViewRight1);
        }
        if(resume.educationPhases.size() > 1) {
            setEducationPhase(resume.educationPhases.get(1), educationViewLeft2, educationViewRight2);
        }

        if(resume.workPhases.size() > 0) {
            setExperiencePhase(resume.workPhases.get(0), experienceViewLeft1, experienceViewRight1, experienceView1);
        }
        if(resume.workPhases.size() > 1) {
            setExperiencePhase(resume.workPhases.get(1), experienceViewLeft2, experienceViewRight2, experienceView2);
        }
        if(resume.workPhases.size() > 2) {
            setExperiencePhase(resume.workPhases.get(2), experienceViewLeft3, experienceViewRight3, experienceView3);
        }


        leadershipView1.setText(contact.getPublications());

        additionalInfoView.setText(contact.getInterests());

        notesView.setText("\nMy notes:\n" + contact.getNotes());
    }

    private void setEducationPhase(EducationPhase edu, TextView left, TextView right){
        left.setText(edu.getSchoolName());
        String separator = "-";
        if(edu.getDateTo().equals("") || edu.getDateTo().equals("")){
            separator = "";
        }
        right.setText(edu.getDateFrom() + separator + edu.getDateTo());
    }

    private void setExperiencePhase(WorkPhase work, TextView left, TextView right, TextView center){
        left.setText(work.getFunction() + "-" + work.getCompany() + "," + work.getCountry());
        String separator = "-";
        if(work.getDateTo().equals("") || work.getDateFrom().equals("")){
            separator = "";
        }
        right.setText(work.getDateFrom() + separator + work.getDateTo());
        center.setText(work.getPlaintext());
    }

}
