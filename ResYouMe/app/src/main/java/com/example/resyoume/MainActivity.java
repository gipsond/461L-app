package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // start the NFC activity
    public void onClickNFC(View view){
        Intent nfc_intent = new Intent(this, NFCActivity.class);
        startActivity(nfc_intent);
    }

    // start the Database activity
    public void onClickDatabase(View view) {
        Intent db_intent = new Intent(this, SavedResumesActivity.class);
        startActivity(db_intent);
    }

    public void onClickSavedCompanyInfo(View view) {
        Intent savedCompanyInfoIntent = new Intent(this, SavedCompanyInfoActivity.class);
        startActivity(savedCompanyInfoIntent);
    }

    // start the Database activity
    public void onClickDisplay(View view) {
        Intent display_intent = new Intent(this, DisplayBasic.class);
        startActivity(display_intent);
    }

    // start the Parse activity
    public void onClickParse(View view) {
        Intent parse_intent = new Intent(this, ResumeParseActivity.class);
        startActivity(parse_intent);
    }

    public void onClickCompany(View view){
        Intent company_intent = new Intent(this, CompanySearchActivity.class);
        startActivity(company_intent);
    }

    public void onClickAbout(View view){
        Intent about_intent = new Intent(this, AboutActivity.class);
        startActivity(about_intent);
    }

    public void onClickCompanyInput(View view) {
        Intent companyData_intent = new Intent(this, CompanyDataActivity.class);
        startActivity(companyData_intent);
    }
}
