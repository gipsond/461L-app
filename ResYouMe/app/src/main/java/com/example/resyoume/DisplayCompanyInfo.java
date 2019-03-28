package com.example.resyoume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.resyoume.db.CompanyInfo;

import org.json.JSONException;
import org.json.JSONObject;

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
                CompanyInfo companyInfo = new CompanyInfo(companyInfoJson);
                setCompanyInfo(companyInfo);
            } catch (JSONException e) {
                // TODO: don't ignore silently
            }
        }
    }

    /* NFC Functions */
    public void shareResume(View view) {
        Intent nfc_intent = new Intent(this, NFCActivity.class);
        try {
            nfc_intent.putExtra("companyInfoJSON", companyInfo.toJSONObject().toString());
        } catch (JSONException e) {
        }
        startActivity(nfc_intent);
    }

    private void setCompanyInfo(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;

        // TODO: implement setting of TextViews to fields
    }
}
