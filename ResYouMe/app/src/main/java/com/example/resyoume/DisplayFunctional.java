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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class DisplayFunctional extends DisplayActivity {
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
        return R.layout.activity_display_functional;
    }

    @Override
    protected void setResume(Resume resume){
        this.resume = resume;

        final TextView resumeHeader;
        final TextView phoneEmailView;
        final TextView addressViewCurrent;
        final TextView addressViewPermanent;
        final TextView educationView1;
        final TextView educationView2;
        final TextView experienceView1;
        final TextView experienceView2;
        final TextView experienceView3;
        final TextView additionalExperienceView;
        final TextView skillsView;
        final TextView additionalInfoView;


        LinearLayout ll = new LinearLayout(this);
        ScrollView resumeView = new ScrollView(this);

        resumeView = findViewById(R.id.resumeview);


        resumeHeader = findViewById(R.id.resumeHeader);
        phoneEmailView = findViewById(R.id.phoneEmailView);
        addressViewCurrent = findViewById(R.id.addressViewCurrent);
        addressViewPermanent = findViewById(R.id.addressViewPermanent);
        educationView1 = findViewById(R.id.educationView1);
        educationView2 = findViewById(R.id.educationView2);
        experienceView1 = findViewById(R.id.experienceView1);
        experienceView2 = findViewById(R.id.experienceView2);
        experienceView3 = findViewById(R.id.experienceView3);
        additionalExperienceView = findViewById(R.id.additionalExperienceView);
        skillsView = findViewById(R.id.skillsView);
        additionalInfoView = findViewById(R.id.additionalInfoView);

        Contact contact = resume.contact;
        resumeHeader.setText(contact.getFirstName() + " " + contact.getLastName());
        phoneEmailView.setText(contact.getEmail() + "\n" + contact.getPhoneNumber());
        addressViewCurrent.setText("Current\n" + contact.getAddress() + "\n" + contact.getCity() + ", " +
                contact.getState() + " " + contact.getPostcode());
        addressViewPermanent.setText("Permanent\n" + contact.getAddress() + "\n" + contact.getCity() + ", " +
                contact.getState() + " " + contact.getPostcode());

        if(resume.educationPhases.size() > 0) {
            setEducationPhase(resume.educationPhases.get(0), educationView1);
        }
        if(resume.educationPhases.size() > 1) {
            setEducationPhase(resume.educationPhases.get(0), educationView2);
        }

        if(resume.workPhases.size() > 0) {
            setExperiencePhase(resume.workPhases.get(0), experienceView1);
        }
        if(resume.workPhases.size() > 1) {
            setExperiencePhase(resume.workPhases.get(1), experienceView2);
        }
        if(resume.workPhases.size() > 2) {
            setExperiencePhase(resume.workPhases.get(2), experienceView3);
        }

        additionalExperienceView.setText(contact.getPublications());

        additionalInfoView.setText(contact.getInterests());
    }

    private void setEducationPhase(EducationPhase edu, TextView view){
        view.setText(edu.getSchoolName() + ", " + edu.getCountry() + "\nGPA: " + edu.getId());
    }

    private void setExperiencePhase(WorkPhase work, TextView view){
        StringBuilder workStr = new StringBuilder();
        workStr.append(work.getCompany() + ", " + work.getCountry() +
                "\n" + work.getFunction() + ", ");
        if((work.getDateFrom().equals("") && !(work.getDateTo().equals("")))){
            workStr.append(work.getDateTo());
        }
        else if(!(work.getDateFrom().equals("")) && work.getDateTo().equals("")){
            workStr.append(work.getDateFrom());
        }
        else if (work.getDateFrom().equals("") && work.getDateTo().equals("")){
            workStr.append("Present");
        }
        else{
            workStr.append(work.getDateFrom() + "-" + work.getDateTo());
        }
        workStr.append("\n" + work.getPlaintext());
        view.setText(workStr.toString());
    }
}
