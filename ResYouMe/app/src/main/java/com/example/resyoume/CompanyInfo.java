package com.example.resyoume;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public void onClickInfo(View view) throws NullPointerException {
        EditText companyNameField = (EditText) findViewById(R.id.CompanyDomainName);
        String companyName = companyNameField.getText().toString();

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://api.fullcontact.com/v3/company.enrich";

            // set up the POST request body
            JSONObject body = new JSONObject();
            body.put("domain", CompanyDomainName.getText().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String CompanyName = null, Location = null, Twitter = null, Website = null, Bio = null, Linkedin = null;
                    try {
                        CompanyName = response.getString("name");
                        Location = response.getString("location");
                        Twitter = response.getString("twitter");
                        Website = response.getString("website");
                        Bio = response.getString("bio");
                        Linkedin = response.getString("linkedin");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CompanyInfo_TextView.setText("Company Name: " + CompanyName +
                            "\nCompany Location: " + Location +
                            "\nCompany Bio: " + Bio +
                            "\nCompany Website: " + Website +
                            "\nCompany Linkedin: " + Linkedin +
                            "\nCompany Twitter: " + Twitter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onBackPressed();
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
        }
        catch(JSONException e){}
    }
}
