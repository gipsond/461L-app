package com.example.resyoume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resyoume.R;
import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResumeEditActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    private SingleResumeViewModel singleResumeViewModel;
    private Resume newResume;

    private int resumeId;
    private List<Integer> workPhaseDBIDs;
    private List<Integer> educationPhaseDBIDs;

    private int titleId;
    private int firstNameId;
    private int lastNameId;
    private int emailId;
    private int phoneId;
    private int addressId;
    private int homepageId;
    private int cityId;
    private int stateId;
    private int postalId;
    private int countryId;
    private int interestsId;
    private int publicationsId;

    private ArrayList<ArrayList<Integer>> workPhaseIds;
    private ArrayList<ArrayList<Integer>> educationPhaseIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_edit);
        linearLayout = findViewById(R.id.edit_linear_layout);
        educationPhaseIds = new ArrayList<>();
        workPhaseIds = new ArrayList<>();
        workPhaseDBIDs = new ArrayList<>();
        educationPhaseDBIDs = new ArrayList<>();
        Intent intent = getIntent();
        String resumeString = intent.getStringExtra("resumeJSON");
        if (resumeString != null) {
            try {
                JSONObject resumeJson = new JSONObject(resumeString);
                newResume = new Resume(resumeJson, false);
                System.out.println(newResume.contact.getFirstName());
                createEditableFields(newResume);
                this.resumeId = newResume.contact.getId();
                createSaveButton();
            } catch (JSONException e) {
            }
        }
    }

    private void createSaveButton(){
        Button btn = new Button(this);
        btn.setText("Save Resume");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResume();
                finish();
            }
        });
        linearLayout.addView(btn);
    }

    private void createEditableFields(Resume resume){
        editContactInfo(resume.contact);
        editEducationPhases(resume.educationPhases);
        editWorkPhases(resume.workPhases);
    }

    private void editContactInfo(Contact contact){
        addLabel("Contact Information");
        titleId = addLabelAndEdit("Title", contact.getTitle());
        firstNameId = addLabelAndEdit("First Name", contact.getFirstName());
        lastNameId = addLabelAndEdit("Last Name", contact.getLastName());
        emailId = addLabelAndEdit("Email Address", contact.getEmail());
        phoneId = addLabelAndEdit("Phone Number", contact.getPhoneNumber());
        addressId = addLabelAndEdit("Address", contact.getAddress());
        homepageId = addLabelAndEdit("Homepage", contact.getHomepage());
        cityId = addLabelAndEdit("City", contact.getCity());
        stateId = addLabelAndEdit("State", contact.getState());
        postalId = addLabelAndEdit("Postal Code", contact.getPostcode());
        countryId = addLabelAndEdit("Country", contact.getCountry());
        interestsId = addLabelAndEdit("Interests", contact.getInterests());
        publicationsId = addLabelAndEdit("Publications", contact.getPublications());

    }

    private void editWorkPhases(List<WorkPhase> workPhases){
        if(workPhases.size() > 0) {
            addLabel("Work Information");
            for (int i = 0; i < workPhases.size(); i++) {
                addLabel("Job " + Integer.toString(i+1));
                WorkPhase workPhase = workPhases.get(i);
                workPhaseDBIDs.add(workPhases.get(i).getId());
                workPhaseIds.add(new ArrayList<Integer>());
                workPhaseIds.get(i).add(addLabelAndEdit("From", workPhase.getDateFrom()));
                workPhaseIds.get(i).add(addLabelAndEdit("To", workPhase.getDateTo()));
                workPhaseIds.get(i).add(addLabelAndEdit("Company", workPhase.getCompany()));
                workPhaseIds.get(i).add(addLabelAndEdit("Role", workPhase.getFunction()));
                workPhaseIds.get(i).add(addLabelAndEdit("Country", workPhase.getCountry()));
                workPhaseIds.get(i).add(addLabelAndEdit("Details", workPhase.getPlaintext())); //Todo: update to include position
            }
        }

        //Todo: Add options to add more Workphases

    }

    private void editEducationPhases(List<EducationPhase> educationPhases){
        if(educationPhases.size() > 0) {
            addLabel("Education Information");
            for (int i = 0; i < educationPhases.size(); i++) {
                addLabel("School " + Integer.toString(i+1));
                EducationPhase educationPhase = educationPhases.get(i);
                educationPhaseDBIDs.add(educationPhases.get(i).getId());
                educationPhaseIds.add(new ArrayList<Integer>());
                educationPhaseIds.get(i).add(addLabelAndEdit("From", educationPhase.getDateFrom()));
                educationPhaseIds.get(i).add(addLabelAndEdit("To", educationPhase.getDateTo()));
                educationPhaseIds.get(i).add(addLabelAndEdit("School Name", educationPhase.getSchoolName()));
                educationPhaseIds.get(i).add(addLabelAndEdit("Country", educationPhase.getCountry()));
                educationPhaseIds.get(i).add(addLabelAndEdit("Graduation", educationPhase.getPlaintext())); //Todo: update to include graduation info
            }
        }

        //Todo: Add options to add more EducationPhases
    }

    private int addLabelAndEdit(String label, String content){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        TextView labelView = new TextView(this);
        labelView.setText(label);
        EditText editText = new EditText(this);
        int id = editText.generateViewId();
        editText.setId(id);
        editText.setText(content);
        layout.addView(labelView);
        layout.addView(editText);
        linearLayout.addView(layout);
        return id;
    }

    private void addLabel(String label){
        TextView labelView = new TextView(this);
        labelView.setText(label);
        linearLayout.addView(labelView);
    }

    private void updateResumeFields(){
        EditText titleView = findViewById(titleId);
        EditText firstNameView = findViewById(firstNameId);
        EditText lastNameView = findViewById(lastNameId);
        EditText emailView = findViewById(emailId);
        EditText phoneView = findViewById(phoneId);
        EditText addressView = findViewById(addressId);
        EditText homepageView = findViewById(homepageId);
        EditText cityView = findViewById(cityId);
        EditText stateView = findViewById(stateId);
        EditText postalView = findViewById(postalId);
        EditText countryView = findViewById(countryId);
        EditText interestsView = findViewById(interestsId);
        EditText publicationsView = findViewById(publicationsId);

        String title = titleView.getText().toString();
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String email = emailView.getText().toString();
        String phoneNumber = phoneView.getText().toString();
        String address = addressView.getText().toString();
        String homepage = homepageView.getText().toString();
        String city = cityView.getText().toString();
        String state = stateView.getText().toString();
        String postalCode = postalView.getText().toString();
        String country = countryView.getText().toString();
        String interests = interestsView.getText().toString();
        String publications = publicationsView.getText().toString();

        Contact contact =
                new Contact(
                        resumeId,
                        new Date(),
                        firstName,
                        lastName,
                        title,
                        address,
                        postalCode,
                        city,
                        state,
                        country,
                        email,
                        phoneNumber,
                        homepage,
                        interests,
                        publications,
                        "",     //TODO: update to match original plaintext
                        null,
                        null
                );

        List<EducationPhase> educationPhases = new ArrayList<>();

        for(int i = 0; i < educationPhaseIds.size(); i++){
            EditText fromView = findViewById(educationPhaseIds.get(i).get(0));
            EditText toView = findViewById(educationPhaseIds.get(i).get(1));
            EditText schoolView = findViewById(educationPhaseIds.get(i).get(2));
            EditText edu_countryView = findViewById(educationPhaseIds.get(i).get(3));
            EditText graduationView = findViewById(educationPhaseIds.get(i).get(4));

            String from = fromView.getText().toString();
            String to = toView.getText().toString();
            String school = schoolView.getText().toString();
            String eduCountry = edu_countryView.getText().toString();
            String graduation = graduationView.getText().toString();

            EducationPhase educationPhase =
                    new EducationPhase(
                            educationPhaseDBIDs.get(i),
                            from,
                            to,
                            school,
                            eduCountry,
                            ""  //TODO: update to match original plaintext
                    );

            educationPhases.add(educationPhase);

        }

        List<WorkPhase> workPhases = new ArrayList<>();

        for(int i = 0; i < workPhaseIds.size(); i++){
            EditText fromView = findViewById(workPhaseIds.get(i).get(0));
            EditText toView = findViewById(workPhaseIds.get(i).get(1));
            EditText companyView = findViewById(workPhaseIds.get(i).get(2));
            EditText roleView = findViewById(workPhaseIds.get(i).get(3));
            EditText work_countryView = findViewById(workPhaseIds.get(i).get(4));
            EditText detailsView = findViewById(workPhaseIds.get(i).get(5));

            String from = fromView.getText().toString();
            String to = toView.getText().toString();
            String company = companyView.getText().toString();
            String role = roleView.getText().toString();
            String workCountry = work_countryView.getText().toString();
            String details = detailsView.getText().toString();

            WorkPhase workPhase =
                    new WorkPhase(
                            workPhaseDBIDs.get(i),
                            from,
                            to,
                            role,
                            company,
                            workCountry,
                            ""  //TODO: update to match original plaintext
                    );

            workPhases.add(workPhase);

        }

        newResume.contact = contact;
        for (WorkPhase phase : workPhases) {
            try {
                System.out.println(phase.toJSONObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        newResume.educationPhases = educationPhases;
        newResume.workPhases = workPhases;
    }

    private void saveResume(){

        updateResumeFields();

        if (singleResumeViewModel == null) {
            singleResumeViewModel = ViewModelProviders.of(this).get(SingleResumeViewModel.class);
        }
        singleResumeViewModel.update(newResume);

        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Resume changes have been saved.", duration);
        toast.show();

    }
}
