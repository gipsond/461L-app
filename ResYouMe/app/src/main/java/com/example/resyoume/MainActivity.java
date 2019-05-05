package com.example.resyoume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.RelativeDateTimeFormatter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private View view;

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
    public void onClickUpload(View view) {
        upload_resume(view);
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

    public void parse_resume(byte[] uploadData, String filename){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.rapidparser.com/api/rest/v1/parse";
        JSONObject postParams = new JSONObject();
        try {
            //read uploadData into base64-string
            String fileBase64Encoded = new String(Base64.encode(uploadData, Base64.DEFAULT));

            //prepare json-payload
            postParams.put("data", fileBase64Encoded);
            postParams.put("filename", filename);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // prepare and execute REST-call.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //get person json object from response
                            JSONObject person = response.getJSONObject("person");

                            //Initialize Fields

                            final String FALLBACK = "";
                            String firstname = "";
                            String lastname = "";
                            String title = "";
                            String address = "";
                            String postcode = "";
                            String city = "";
                            String state = "";
                            String country = "";
                            String email = "";
                            String phonenumber = "";
                            String homepage = "";
                            String interests = "";
                            String publications = "";
                            String plaintext = "";

                            List<WorkPhase> workPhases = new ArrayList<>();
                            List<EducationPhase> educationPhases = new ArrayList<>();

                            JSONObject personalinfo = person.optJSONObject("personalinfo"); // returns null if not found
                            if (personalinfo != null) {
                                firstname = personalinfo.optString("firstname", FALLBACK);
                                lastname = personalinfo.optString("lastname", FALLBACK);
                                title = personalinfo.optString("title", FALLBACK);
                                address = personalinfo.optString("address", FALLBACK);
                                postcode = personalinfo.optString("postcode", FALLBACK);
                                city = personalinfo.optString("city", FALLBACK);
                                state = personalinfo.optString("state", FALLBACK);
                                country = personalinfo.optString("country", FALLBACK);
                                email = personalinfo.optString("email", FALLBACK);
                                phonenumber = personalinfo.optString("phonenumber", FALLBACK);
                                homepage = personalinfo.optString("homepage", FALLBACK);
                            }

                            //get languages from person object
                            JSONObject languages = person.optJSONObject("languages");
                            if (languages != null) {
                                //parse languages into fields
                            }

                            //get educationphase from person object
                            JSONArray educationPhaseArray = person.optJSONArray("educationphase");
                            if (educationPhaseArray != null) {
                                for (int i = 0; i < educationPhaseArray.length(); i++) {
                                    JSONObject educationPhaseJSON = educationPhaseArray.optJSONObject(i);
                                    if (educationPhaseJSON != null) {
                                        // parse educationphase into fields
                                        String datefrom = educationPhaseJSON.optString("datefrom");
                                        String dateto = educationPhaseJSON.optString("dateto");
                                        String schoolname = educationPhaseJSON.optString("schoolname");
                                        String eduCountry = educationPhaseJSON.optString("country");
                                        String eduPlaintext = educationPhaseJSON.optString("plaintext");

                                        EducationPhase educationPhase =
                                                new EducationPhase(
                                                        0,
                                                        datefrom,
                                                        dateto,
                                                        schoolname,
                                                        eduCountry,
                                                        eduPlaintext
                                                );

                                        educationPhases.add(educationPhase);
                                    }
                                }
                            }

                            //get workphase from person object
                            JSONArray workPhaseArray = person.optJSONArray("workphase");

                            if (workPhaseArray != null) {
                                for (int i = 0; i < workPhaseArray.length(); i++) {
                                    JSONObject workPhaseJSON = workPhaseArray.optJSONObject(i);
                                    if (workPhaseJSON != null) {
                                        //parse workphase into fields
                                        String datefrom = workPhaseJSON.optString("datefrom");
                                        String dateto = workPhaseJSON.optString("dateto");
                                        String function = workPhaseJSON.optString("function");
                                        String company = workPhaseJSON.optString("company");
                                        String workCountry = workPhaseJSON.optString("country");
                                        String workPlaintext = workPhaseJSON.optString("plaintext");

                                        WorkPhase workPhase =
                                                new WorkPhase(
                                                        0,
                                                        datefrom,
                                                        dateto,
                                                        function,
                                                        company,
                                                        workCountry,
                                                        workPlaintext
                                                );

                                        workPhases.add(workPhase);
                                    }
                                }
                            }

                            JSONObject otherinfo = person.optJSONObject("otherinfo");
                            if (otherinfo != null) {
                                interests = otherinfo.optString("interests", FALLBACK);
                                publications = otherinfo.optString("publications", FALLBACK);
                            }

                            JSONObject auxiliary = person.optJSONObject("auxiliary");
                            if (auxiliary != null) {
                                plaintext = auxiliary.optString("plaintext", FALLBACK);
                            }

                            Contact contact =
                                    new Contact(
                                            0,
                                            new Date(),
                                            firstname,
                                            lastname,
                                            title,
                                            address,
                                            postcode,
                                            city,
                                            state,
                                            country,
                                            email,
                                            phonenumber,
                                            homepage,
                                            interests,
                                            publications,
                                            plaintext,
                                            null,
                                            null
                                    );

                            Resume resume = new Resume(contact, educationPhases, workPhases);

                            Intent edit_intent = new Intent(view.getContext(), ResumeEditActivity.class);
                            try {
                                edit_intent.putExtra("resumeJSON", resume.toJSONObject().toString());
                                edit_intent.putExtra("createNew", true);
                            } catch (JSONException e) {
                            }
                            startActivity(edit_intent);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },

                error -> {
                    String body;
                    //get status code here
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                String apiKey = "c1b6265d-7d2e-49ba-b3dc-4644cfc3912f";
                String authorization = "Bearer " + apiKey;
                // add headers <key,value>
                headers.put("Accept", "application/json");       //Response format = app/json
                headers.put("Content-Type", "application/json; charset=utf-8"); //Content Type = app/json
                headers.put("Authorization", authorization);            //Pass api key
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    private static final int GET_REQUEST_CODE = 42; //Code to ensure picker was used for GET_REQUEST

    private static String[] mimeTypes = {
            "application/msword",  //.doc
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx
            "application/pdf" //pdf
    };

    public void upload_resume(View view){

        this.view = view;

        // ACTION_GET_DOCUMENT is the intent to choose a file via the system's file browser
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Filter to only show results that can be "opened", such as a
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only .doc,.docx, and pdf files using the image MIME data type.
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, GET_REQUEST_CODE);
    }

    private String uri2filename(Uri uri) {

        String ret = "";
        String scheme = uri.getScheme();

        if (scheme.equals("file")) {
            ret = uri.getLastPathSegment();
        }
        else if (scheme.equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                ret = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }
        return ret;
    }

    /**
     * callback function after user selects document
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_GET_DOCUMENT intent was sent with the request code
        // GET_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == GET_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = resultData.getData();
            if (uri != null) {
                try {
                    InputStream in = getContentResolver().openInputStream(uri);
                    if(in != null) {
                        byte[] bytes = IOUtils.toByteArray(in);
                        String filename = uri2filename(uri);
                        parse_resume(bytes, filename);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
