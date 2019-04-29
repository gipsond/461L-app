package com.example.resyoume;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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

    private LinearLayout baseLayout;

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

    private LinearLayout educationPhaseLayout;
    private LinearLayout workPhaseLayout;
    private ArrayList<ArrayList<Integer>> workPhaseIds;
    private ArrayList<ArrayList<Integer>> educationPhaseIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_edit);
        baseLayout = findViewById(R.id.edit_linear_layout);
        educationPhaseIds = new ArrayList<>();
        workPhaseIds = new ArrayList<>();
        educationPhaseLayout = new LinearLayout(this);
        educationPhaseLayout.setOrientation(LinearLayout.VERTICAL);
        workPhaseLayout = new LinearLayout(this);
        workPhaseLayout.setOrientation(LinearLayout.VERTICAL);
        workPhaseDBIDs = new ArrayList<>();
        educationPhaseDBIDs = new ArrayList<>();
        Intent intent = getIntent();
        String resumeString = intent.getStringExtra("resumeJSON");
        if (resumeString != null) {
            try {
                JSONObject resumeJson = new JSONObject(resumeString);
                newResume = new Resume(resumeJson, false);
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
        baseLayout.addView(btn);
    }

    private void createAddEducationPhaseButton(){
        Button btn = new Button(this);
        btn.setText("Add EducationPhase");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEducationPhase();
            }
        });
        baseLayout.addView(btn);
    }

    private void createAddWorkPhaseButton(){
        Button btn = new Button(this);
        btn.setText("Add WorkPhase");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewWorkPhase();
            }
        });
        baseLayout.addView(btn);
    }

    private void createEditableFields(Resume resume){
        editContactInfo(resume.contact);
        editEducationPhases(resume.educationPhases);
        baseLayout.addView(educationPhaseLayout);
        createAddEducationPhaseButton();
        editWorkPhases(resume.workPhases);
        baseLayout.addView(workPhaseLayout);
        createAddWorkPhaseButton();
    }

    private void editContactInfo(Contact contact){
        addLabel("Contact Information", baseLayout);
        titleId = addLabelAndEdit("Title", contact.getTitle(), baseLayout);
        firstNameId = addLabelAndEdit("First Name", contact.getFirstName(), baseLayout);
        lastNameId = addLabelAndEdit("Last Name", contact.getLastName(), baseLayout);
        emailId = addLabelAndEdit("Email Address", contact.getEmail(), baseLayout);
        phoneId = addLabelAndEdit("Phone Number", contact.getPhoneNumber(), baseLayout);
        addressId = addLabelAndEdit("Address", contact.getAddress(), baseLayout);
        homepageId = addLabelAndEdit("Homepage", contact.getHomepage(), baseLayout);
        cityId = addLabelAndEdit("City", contact.getCity(), baseLayout);
        stateId = addLabelAndEdit("State", contact.getState(), baseLayout);
        postalId = addLabelAndEdit("Postal Code", contact.getPostcode(), baseLayout);
        countryId = addLabelAndEdit("Country", contact.getCountry(), baseLayout);
        interestsId = addLabelAndEdit("Interests", contact.getInterests(), baseLayout);
        publicationsId = addLabelAndEdit("Publications", contact.getPublications(), baseLayout);

    }

    private void addNewWorkPhase(){
        WorkPhase workPhase = new WorkPhase();
        workPhaseIds.add(new ArrayList<Integer>());
        int index = workPhaseIds.size()-1;
        LinearLayout workLayout = new LinearLayout(this);
        workLayout.setOrientation(LinearLayout.VERTICAL);
        workPhaseDBIDs.add(0);
        workPhaseIds.get(index).add(addLabelAndEdit("From", workPhase.getDateFrom(), workLayout));
        workPhaseIds.get(index).add(addLabelAndEdit("To", workPhase.getDateTo(), workLayout));
        workPhaseIds.get(index).add(addLabelAndEdit("Company", workPhase.getCompany(), workLayout));
        workPhaseIds.get(index).add(addLabelAndEdit("Role", workPhase.getFunction(), workLayout));
        workPhaseIds.get(index).add(addLabelAndEdit("Country", workPhase.getCountry(), workLayout));
        workPhaseIds.get(index).add(addLabelAndEdit("Details", workPhase.getPlaintext(), workLayout)); //Todo: update to include position
        Button btn = new Button(this);
        btn.setText("Remove WorkPhase");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup layout = (ViewGroup) workLayout.getParent();
                if(layout != null) //for safety only  as you are doing onClick
                    workPhaseIds.remove(layout.indexOfChild(workLayout));
                    layout.removeView(workLayout);
            }
        });
        //workLayout.addView(btn);  //No support for removal
        workPhaseLayout.addView(workLayout);
    }

    private void editWorkPhases(List<WorkPhase> workPhases){
        if(workPhases.size() > 0) {
            addLabel("Work Information", baseLayout);
            for (int i = 0; i < workPhases.size(); i++) {
                WorkPhase workPhase = workPhases.get(i);
                workPhaseIds.add(new ArrayList<Integer>());
                LinearLayout workLayout = new LinearLayout(this);
                workLayout.setOrientation(LinearLayout.VERTICAL);
                workPhaseDBIDs.add(workPhases.get(i).getId());
                workPhaseIds.get(i).add(addLabelAndEdit("From", workPhase.getDateFrom(), workLayout));
                workPhaseIds.get(i).add(addLabelAndEdit("To", workPhase.getDateTo(), workLayout));
                workPhaseIds.get(i).add(addLabelAndEdit("Company", workPhase.getCompany(), workLayout));
                workPhaseIds.get(i).add(addLabelAndEdit("Role", workPhase.getFunction(), workLayout));
                workPhaseIds.get(i).add(addLabelAndEdit("Country", workPhase.getCountry(), workLayout));
                workPhaseIds.get(i).add(addLabelAndEdit("Details", workPhase.getPlaintext(), workLayout)); //Todo: update to include position
                Button btn = new Button(this);
                btn.setText("Remove WorkPhase");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup layout = (ViewGroup) workLayout.getParent();
                        if(layout != null) //for safety only  as you are doing onClick
                            workPhaseIds.remove(layout.indexOfChild(workLayout));
                            layout.removeView(workLayout);
                    }
                });
                //workLayout.addView(btn);  //No support for removal
                workPhaseLayout.addView(workLayout);
            }
        }

    }

    private void addNewEducationPhase(){
        EducationPhase educationPhase = new EducationPhase();
        newResume.educationPhases.add(educationPhase);
        educationPhaseIds.add(new ArrayList<Integer>());
        int index = educationPhaseIds.size()-1;
        LinearLayout educationLayout = new LinearLayout(this);
        educationLayout.setOrientation(LinearLayout.VERTICAL);
        educationPhaseDBIDs.add(0);
        educationPhaseDBIDs.add(educationPhase.getId());
        educationPhaseIds.get(index).add(addLabelAndEdit("From", educationPhase.getDateFrom(), educationLayout));
        educationPhaseIds.get(index).add(addLabelAndEdit("To", educationPhase.getDateTo(), educationLayout));
        educationPhaseIds.get(index).add(addLabelAndEdit("School Name", educationPhase.getSchoolName(), educationLayout));
        educationPhaseIds.get(index).add(addLabelAndEdit("Country", educationPhase.getCountry(), educationLayout));
        educationPhaseIds.get(index).add(addLabelAndEdit("Graduation", educationPhase.getPlaintext(), educationLayout)); //Todo: update to include graduation info
        Button btn = new Button(this);
        btn.setText("Remove EducationPhase");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup layout = (ViewGroup) educationLayout.getParent();
                if(layout != null) //for safety only  as you are doing onClick
                    educationPhaseIds.remove(layout.indexOfChild(educationLayout));
                    layout.removeView(educationLayout);
            }
        });
        //educationLayout.addView(btn); //No support for removal
        educationPhaseLayout.addView(educationLayout);
    }

    private void editEducationPhases(List<EducationPhase> educationPhases){
        if(educationPhases.size() > 0) {
            addLabel("Education Information", baseLayout);
            for (int i = 0; i < educationPhases.size(); i++) {
                EducationPhase educationPhase = educationPhases.get(i);
                LinearLayout educationLayout = new LinearLayout(this);
                educationLayout.setOrientation(LinearLayout.VERTICAL);
                educationPhaseDBIDs.add(educationPhases.get(i).getId());
                educationPhaseIds.add(new ArrayList<Integer>());
                educationPhaseIds.get(i).add(addLabelAndEdit("From", educationPhase.getDateFrom(), educationLayout));
                educationPhaseIds.get(i).add(addLabelAndEdit("To", educationPhase.getDateTo(), educationLayout));
                educationPhaseIds.get(i).add(addLabelAndEdit("School Name", educationPhase.getSchoolName(), educationLayout));
                educationPhaseIds.get(i).add(addLabelAndEdit("Country", educationPhase.getCountry(), educationLayout));
                educationPhaseIds.get(i).add(addLabelAndEdit("Graduation", educationPhase.getPlaintext(), educationLayout)); //Todo: update to include graduation info
                Button btn = new Button(this);
                btn.setText("Remove EducationPhase");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup layout = (ViewGroup) educationLayout.getParent();
                        if(layout != null) //for safety only  as you are doing onClick
                            educationPhaseIds.remove(layout.indexOfChild(educationLayout));
                            layout.removeView(educationLayout);
                    }
                });
                //educationLayout.addView(btn);     //No support for removal
                educationPhaseLayout.addView(educationLayout);
            }
        }
    }

    private int addLabelAndEdit(String label, String content, LinearLayout baseLayout){
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
        baseLayout.addView(layout);
        return id;
    }

    private void addLabel(String label, LinearLayout layout){
        TextView labelView = new TextView(this);
        labelView.setText(label);
        labelView.setAllCaps(true);
        labelView.setTypeface(null, Typeface.BOLD);
        layout.addView(labelView);
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
                            graduation
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
                            details
                    );

            workPhases.add(workPhase);

        }

        newResume.contact = contact;
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
