package com.example.resyoume;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.Resume;

import org.json.JSONException;

public class DisplayActivityCard extends DisplayActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_display_card;
    }

    @Override
    protected void setResume(Resume resume) {
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


        resumeHeader = findViewById(R.id.nameView);
        addressView = findViewById(R.id.addressView);
        addressView2 = findViewById(R.id.addressView2);
        emailView = findViewById(R.id.emailPhoneView);
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
            nfc_intent.putExtra("resumeJSON", resume.toJSONObject().toString());
        }
        catch (JSONException e) {}
        startActivity(nfc_intent);
    }
}
