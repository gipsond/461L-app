package com.example.resyoume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.ResumeRepository;
import com.example.resyoume.db.WorkPhase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeParseActivity extends AppCompatActivity {

    private static final String filename = "cv.pdf";

    private ResumeViewModel resumeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_parse);
        resumeViewModel = ViewModelProviders.of(this).get(ResumeViewModel.class);
    }

    public void parse_resume(View view){
        final TextView textView = (TextView) findViewById(R.id.parse_text);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.rapidparser.com/api/rest/v1/parse";
        JSONObject postParams = new JSONObject();
        InputStream fileInStream = null;
        try {
            //read file into base64-string
            AssetManager manager = getAssets();
            fileInStream = manager.open(filename);
            byte bytes[] = new byte[fileInStream.available()];
            fileInStream.read(bytes);
            String fileBase64Encoded = new String(Base64.encode(bytes, Base64.DEFAULT));
            fileInStream.close();

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
                            String gender = "";
                            String birthday = "";
                            String nationality = "";
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
                                gender = personalinfo.optString("gender", FALLBACK);
                                birthday = personalinfo.optString("birthday", FALLBACK);
                                nationality = personalinfo.optString("nationality", FALLBACK);
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

                                        /* Demo value changes output from Array to Value, uncomment when switch to production api key
                                        if (!educationphase.isNull("skill")) {
                                            JSONArray skill_edu = educationphase.getJSONArray("skill");
                                            String skill_edu_arr[] = new String[skill_edu.length()];
                                            for (int j = 0; j < skill_edu.length(); j++) {
                                                skill_edu_arr[j] = skill_edu.getString(j);
                                            }
                                            skills_edu.add(skill_edu_arr);
                                        }
                                        */

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

                                        /* Demo value changes output from Array to Value, uncomment when switch to production api key
                                        if (!workphase.isNull("skill")) {
                                            JSONArray skill_work = workphase.getJSONArray("skill");
                                            String skill_work_arr[] = new String[skill_work.length()];
                                            for (int j = 0; j < skill_work.length(); j++) {
                                                skill_work_arr[j] = skill_work.getString(j);
                                            }
                                            skills_work.add(skill_work_arr);
                                        }
                                        */

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
                                    plaintext
                                );

                            Resume resume = new Resume(contact, educationPhases, workPhases);
                            resumeViewModel.insert(resume);

                            String out = firstname + " " + lastname + ":\n" +
                                    phonenumber + "\n" +
                                    email + "\n\n";
                            textView.setText(out);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },

                error -> {
                    textView.setText("That didn't work!");
                    String body;
                    //get status code here
                    final String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    try {
                        body = new String(error.networkResponse.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // exception
                    }
                    textView.setText("Whoops");
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                String apiKey = "dd62a4d2-88f8-4e2e-bbe1-5c27940cf199";
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
}
