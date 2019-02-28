package com.example.resyoume;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import androidx.appcompat.app.AppCompatActivity;

public class CompanyInfo extends AppCompatActivity {

    EditText CompanyDomainName;
    TextView CompanyInfo_TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        CompanyDomainName = (EditText) findViewById(R.id.CompanyDomainName);
        CompanyInfo_TextView = (TextView) findViewById(R.id.CompanyInfo);
    }

    private class CompanyInfoAsyncTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Toast.makeText(CompanyInfo.this, "Company Information Loaded!", Toast.LENGTH_SHORT).show();
            String CompanyName = null, Location = null, Twitter = null;
            try {
                CompanyName = jsonObject.getString("name");
                Location = jsonObject.getString("location");
                Twitter = jsonObject.getString("twitter");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CompanyInfo_TextView.setText("Company Name: " + CompanyName +
                                        "\nCompany Location: " + Location +
                                        "\nCompany Twitter: " + Twitter);
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            Request request = null;
            Response response = null;
            Content content = null;
            JSONObject companyInfo_JSON = null;
            try {
                request = Request.Post("https://api.fullcontact.com/v3/company.enrich")
                        .addHeader("Authorization","Bearer Kom1m2ezqBt2n2P46bo4dEw0uuZCNAIT")
                        .body(new StringEntity("{" + "\"domain\":\"ni.com\"" + "}"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                assert request != null;
                response = request.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert response != null;
                content = response.returnContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert content != null;
                companyInfo_JSON = new JSONObject(content.asString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return companyInfo_JSON;
        }
    }
    // Creates an AsyncTask that does the company information lookup
    public void onClickInfo(View view) throws NullPointerException {
        EditText companyNameField = (EditText) findViewById(R.id.CompanyDomainName);
        String companyName = companyNameField.getText().toString();
        CompanyInfoAsyncTask task = new CompanyInfoAsyncTask();
        task.execute(companyName);
    }
}
