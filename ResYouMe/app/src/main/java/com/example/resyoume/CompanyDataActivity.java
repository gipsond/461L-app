package com.example.resyoume;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompanyDataActivity extends AppCompatActivity {

    private TextInputLayout textInputURL;
    private TextInputLayout textInputAdditionalInfo;
    JSONObject CompanyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_data);
        textInputURL = findViewById(R.id.text_input_URL);
        textInputAdditionalInfo = findViewById(R.id.text_input_additionalInfo);
        CompanyInfo = new JSONObject();
    }
    private void getCompanyInfo(String domainName) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://api.fullcontact.com/v3/company.enrich";
            // set up the POST request body
            JSONObject body = new JSONObject();
            body.put("domain", domainName);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // Save to CompanyInfo
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //onBackPressed();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    // set up the POST request headers with security key
                    headers.put("Authorization", "Bearer Kom1m2ezqBt2n2P46bo4dEw0uuZCNAIT");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveButton(View v) {
        String url = textInputURL.getEditText().getText().toString();
        getCompanyInfo(url);
        Toast.makeText(this, "Data Saved" , Toast.LENGTH_SHORT).show();
        // CompanyInfo stores the JSON received from the CompanyAPI
        // Add to the database/NFC here?
    }
}
