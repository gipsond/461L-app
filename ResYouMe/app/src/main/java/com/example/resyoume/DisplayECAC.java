package com.example.resyoume;

import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

public class DisplayECAC extends DisplayActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_display_ecac;
    }

    @Override
    protected void setResume(Resume resume){
        this.resume = resume;

        final TextView resumeHeader;
        final TextView addressPhoneEmailView;
        final TextView educationView1;
        final TextView educationView2;
        final TextView experienceView1;
        final TextView experienceView2;
        final TextView experienceView3;
        final TextView academicExperienceView;
        final TextView skillsView;
        final TextView additionalInfoView;
        final TextView notesView;


        resumeHeader = findViewById(R.id.nameView);
        addressPhoneEmailView = findViewById(R.id.addressPhoneEmailView);
        educationView1 = findViewById(R.id.educationView1);
        educationView2 = findViewById(R.id.educationView2);
        experienceView1 = findViewById(R.id.experienceView1);
        experienceView2 = findViewById(R.id.experienceView2);
        experienceView3 = findViewById(R.id.experienceView3);
        academicExperienceView = findViewById(R.id.academicExperienceView);
        skillsView = findViewById(R.id.skillsView);
        additionalInfoView = findViewById(R.id.additionalInfoView);
        notesView = findViewById(R.id.notesView);

        Contact contact = resume.contact;
        resumeHeader.setText(contact.getFirstName() + " " + contact.getLastName());
        addressPhoneEmailView.setText(contact.getPhoneNumber() + " | " +
                contact.getEmail() + ",");


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

        academicExperienceView.setText(contact.getPublications());

        additionalInfoView.setText(contact.getInterests());

        notesView.setText("\nMy notes:\n" + contact.getNotes());
    }

    private void setEducationPhase(EducationPhase edu, TextView view){
        view.setText(edu.getDateFrom() + "\n" + edu.getSchoolName() + "\nGPA: " + edu.getId());
    }

    private void setExperiencePhase(WorkPhase work, TextView view){
        StringBuilder workStr = new StringBuilder();
        workStr.append(work.getFunction() + ", " + work.getCompany() + ", ");
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
        view.setText(workStr.toString());
    }

}
