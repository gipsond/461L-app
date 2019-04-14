package com.example.resyoume;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class CompanySearchActivity extends AppCompatActivity {

    EditText CompanyDomainName;
    TextView CompanyInfo_TextView;
    TextView CompanyName_Textview;
    TextView CompanyBio_Textview;
    TextView CompanyLinkedIn_Textview;
    TextView CompanyTwitter_Textview;
    TextView CompanyWebsite_Textview;
    TextView CompanyLocation_Textview;
    ImageView CompanyLogo_ImageView;

    String testLogo = "https://img.fullcontact.com/static/a375e4f9a528272b52f38511a5de88b0_d2f5b2872106a596afe1dcf27ffaf920956e91cdd7c024e2fb707377eb6113a3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);
        CompanyDomainName = (EditText) findViewById(R.id.CompanyDomainName);
        CompanyInfo_TextView = (TextView) findViewById(R.id.CompanyInfo);
        CompanyName_Textview = (TextView) findViewById(R.id.CompanyName);
        CompanyBio_Textview = (TextView) findViewById(R.id.CompanyBio);
        CompanyLinkedIn_Textview = (TextView) findViewById(R.id.CompanyLinkedIn);
        CompanyTwitter_Textview = (TextView) findViewById(R.id.CompanyTwitter);
        CompanyWebsite_Textview = (TextView) findViewById(R.id.CompanyWebSite);
        CompanyLocation_Textview= (TextView) findViewById(R.id.CompanyLocation);
        CompanyLogo_ImageView = (ImageView) findViewById(R.id.CompanyLogo);
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
                    String CompanyName = null, Location = null, Twitter = null, Website = null, Bio = null, Linkedin = null, Logo = null;
                    try {
                        CompanyName = response.getString("name");
                        Location = response.getString("location");
                        Twitter = response.getString("twitter");
                        Website = response.getString("website");
                        Bio = response.getString("bio");
                        Linkedin = response.getString("linkedin");
                        Logo = response.getString("logo");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CompanyInfo_TextView.setText("Company Found!");
                    CompanyName_Textview.setText(CompanyName);
                    CompanyLocation_Textview.setText(Location);
                    CompanyWebsite_Textview.setText(Website);
                    CompanyTwitter_Textview.setText(Twitter);
                    CompanyLinkedIn_Textview.setText(Linkedin);
                    CompanyBio_Textview.setText(Bio);

                    Picasso.with(getApplicationContext()).load(Logo).placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(CompanyLogo_ImageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(), "Image Loaded!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(getApplicationContext(), "Could not load image!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CompanyInfo_TextView.setText("Invalid URL. Please check the spelling and try again.");
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
        catch(JSONException e) {
            e.printStackTrace();
        }
        closeKeyboard();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
