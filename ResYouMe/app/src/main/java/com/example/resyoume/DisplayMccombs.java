package com.example.resyoume;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

public class DisplayMccombs extends DisplayActivity {

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
        final TextView notesView;


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
        notesView = findViewById(R.id.notesView);

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

        notesView.setText("\nMy notes:\n" + contact.getNotes());
    }
}
