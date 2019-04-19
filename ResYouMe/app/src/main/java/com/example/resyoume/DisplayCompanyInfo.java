package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resyoume.db.CompanyInfo;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class DisplayCompanyInfo extends AppCompatActivity {

    private CompanyInfoViewModel companyInfoViewModel;
    private CompanyInfo companyInfo;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_company_info);

        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);

        Intent intent = getIntent();
        String companyInfoString = intent.getStringExtra("companyInfoJSON");
        if (companyInfoString != null) {
            try {
                JSONObject companyInfoJson = new JSONObject(companyInfoString);
                CompanyInfo companyInfo = new CompanyInfo(companyInfoJson, false);
                System.out.println(companyInfoJson);
                System.out.println(companyInfo.toJSONObject());
                setCompanyInfo(companyInfo);
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Error occurred" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* NFC Functions */
    public void shareResume(View view) {
        Intent nfc_intent = new Intent(this, NFCActivity.class);
        try {
            nfc_intent.putExtra("companyInfoJSON", companyInfo.toJSONObject().toString());
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON Error occurred" , Toast.LENGTH_SHORT).show();
        }
        startActivity(nfc_intent);
    }

    private void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;

        final TextView companyName = findViewById(R.id.nameView);
        final TextView companyLocation = findViewById(R.id.locationView);
        final TextView companyTwitter = findViewById(R.id.twitterView);
        final TextView companyWebsite = findViewById(R.id.websiteView);
        final TextView companyBio = findViewById(R.id.bioView);
        final TextView companyLinkedin = findViewById(R.id.linkedinView);
        final TextView companyAdditionalInfo = findViewById(R.id.additionalInfoView);
        final TextView notesView = findViewById(R.id.notesView);

        LinearLayout ll = new LinearLayout(this);
        ScrollView companyView = new ScrollView(this);
        companyView = findViewById(R.id.companyView);

        companyName.setText(companyInfo.getCompanyName());
        companyLocation.setText(companyInfo.getLocation());

        String twitterLink = "<a href=\"" + companyInfo.getTwitter() +"\">Twitter</a>";
        companyTwitter.setText(Html.fromHtml(twitterLink));
        companyTwitter.setMovementMethod(LinkMovementMethod.getInstance());

        String websiteLink = "<a href=\"" + companyInfo.getWebsite() +"\">Website</a>";
        companyWebsite.setText(Html.fromHtml(websiteLink));
        companyWebsite.setMovementMethod(LinkMovementMethod.getInstance());

        companyBio.setText(companyInfo.getBio());

        String linkedInLink = "<a href=\"" + companyInfo.getLinkedIn() +"\">LinkedIn</a>";
        companyLinkedin.setText(Html.fromHtml(linkedInLink));
        companyLinkedin.setMovementMethod(LinkMovementMethod.getInstance());

        companyAdditionalInfo.setText(companyInfo.getAdditionalInfo());

        notesView.setText("\nMy notes:\n" + companyInfo.getNotes());
    }
}
