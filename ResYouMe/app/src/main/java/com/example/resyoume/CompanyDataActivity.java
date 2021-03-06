package com.example.resyoume;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.resyoume.db.CompanyInfo;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class CompanyDataActivity extends AppCompatActivity {

    private TextInputLayout textInputURL;
    private TextInputLayout textInputAdditionalInfo;
    JSONObject CompanyInfo;
    String domainName;
    private CompanyInfoViewModel companyInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_data);
        textInputURL = findViewById(R.id.text_input_URL);
        textInputAdditionalInfo = findViewById(R.id.text_input_additionalInfo);
    }

    // See https://stackoverflow.com/questions/28120029/how-can-i-return-value-from-function-onresponse-of-volley
    // for details on callback code
    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }

    public void getCompanyInfo(final VolleyCallback callback) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://api.fullcontact.com/v3/company.enrich";
            // set up the POST request body
            JSONObject body = new JSONObject();
            body.put("domain", domainName);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    callback.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    // set up the POST request headers with security key
                    //headers.put("Authorization", "Bearer Kom1m2ezqBt2n2P46bo4dEw0uuZCNAIT");
                    headers.put("Authorization", "Bearer fHhLhckJoW2FAwm67Iu2MRJSR42roXdV");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void saveButton(View v) {
        domainName = textInputURL.getEditText().getText().toString();
        String additionalInfo = textInputAdditionalInfo.getEditText().getText().toString();
        getCompanyInfo(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result){
                String CompanyName = null, Location = null, Twitter = null, Website = null, Bio = null, Linkedin = null;
                try {
                    CompanyName = result.getString("name");
                    Location = result.getString("location");
                    Twitter = result.getString("twitter");
                    Website = result.getString("website");
                    Bio = result.getString("bio");
                    Linkedin = result.getString("linkedin");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "JSON Error" , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                // Make Company object
                
                CompanyInfo company = new CompanyInfo(0, new Date(), CompanyName, Location, Twitter, Website, Bio, Linkedin, additionalInfo, null, null);
                if (companyInfoViewModel == null) {
                    companyInfoViewModel = ViewModelProviders.of(CompanyDataActivity.this).get(CompanyInfoViewModel.class);
                }
                companyInfoViewModel.insert(company);

                Toast.makeText(getApplicationContext(), "Data Saved" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
