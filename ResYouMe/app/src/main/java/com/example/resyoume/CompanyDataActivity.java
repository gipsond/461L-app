package com.example.resyoume;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
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
    String domainName;


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
        String additionalInfo = textInputAdditionalInfo.getEditText().getText().toString();
        domainName = url;
        getCompanyInfo(new VolleyCallback(){
            @Override
            public void onSuccess(JSONObject result){
                CompanyInfo = result;
                // result contains the JSONObject received from the company API
                // From here send CompanyInfo + additionalInfo to database?
            }
        });
        Toast.makeText(this, "Data Saved" , Toast.LENGTH_SHORT).show();
    }
}
