package com.example.resyoume;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_parse);
    }

    private static void addStringToListIfExists(JSONObject object, String key, List<String> list) {
        String value = object.optString(key);
        if (value != null) {
            list.add(value);
        }
    }

    public void parse_resume(View view){
        final TextView textView = (TextView) findViewById(R.id.parse_text);
        // ...

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
                            String gender = "";
                            String birthday = "";
                            String nationality = "";
                            String address = "";
                            String email = "";
                            String phonenumber = "";
                            List<String> date_froms_edu = new ArrayList<>();
                            List<String> date_tos_edu = new ArrayList<>();
                            List<String> schoolnames = new ArrayList<>();
                            List<String> countries_edu = new ArrayList<>();
                            List<String[]> skills_edu = new ArrayList<>();
                            List<String> plaintexts_edu = new ArrayList<>();
                            List<String> date_froms_work = new ArrayList<>();
                            List<String> date_tos_work = new ArrayList<>();
                            List<String> functions = new ArrayList<>();
                            List<String> companies = new ArrayList<>();
                            List<String> countries_work = new ArrayList<>();
                            List<String[]> skills_work = new ArrayList<>();
                            List<String> plaintexts_work = new ArrayList<>();


                            JSONObject personalinfo = person.optJSONObject("personalinfo"); // returns null if not found
                            if (personalinfo != null) {
                                firstname = personalinfo.optString("firstname", FALLBACK);
                                lastname = personalinfo.optString("lastname", FALLBACK);
                                gender = personalinfo.optString("gender", FALLBACK);
                                birthday = personalinfo.optString("birthday", FALLBACK);
                                nationality = personalinfo.optString("nationality", FALLBACK);
                                address = personalinfo.optString("address", FALLBACK);
                                email = personalinfo.optString("email", FALLBACK);
                                phonenumber = personalinfo.optString("phonenumber", FALLBACK);
                            }

                            //get languages from person object
                            JSONObject languages = person.optJSONObject("languages");
                            if (languages != null) {
                                //parse languages into fields
                            }

                            //get educationphase from person object
                            JSONArray educationphases = person.optJSONArray("educationphase");
                            if (educationphases != null) {
                                for (int i = 0; i < educationphases.length(); i++) {
                                    JSONObject educationphase = educationphases.optJSONObject(i);
                                    if (educationphase != null) {
                                        // parse educationphase into fields
                                        addStringToListIfExists(educationphase,"datefrom", date_froms_edu);
                                        addStringToListIfExists(educationphase,"dateto", date_tos_edu);
                                        addStringToListIfExists(educationphase, "schoolname", schoolnames);
                                        addStringToListIfExists(educationphase,"country", countries_edu);

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

                                        addStringToListIfExists(educationphase, "plaintext", plaintexts_edu);
                                    }
                                }
                            }

                            //get workphase from person object
                            JSONArray workphases = person.optJSONArray("workphase");

                            if (workphases != null) {
                                for (int i = 0; i < workphases.length(); i++) {
                                    JSONObject workphase = workphases.optJSONObject(i);
                                    if (workphase != null) {
                                        //parse workphase into fields
                                        addStringToListIfExists(workphase, "datefrom", date_froms_work);
                                        addStringToListIfExists(workphase, "dateto", date_tos_work);
                                        addStringToListIfExists(workphase, "function", functions);
                                        addStringToListIfExists(workphase, "company", companies);
                                        addStringToListIfExists(workphase, "country", countries_work);

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

                                        addStringToListIfExists(workphase, "plaintext", plaintexts_work);

                                    }
                                }
                            }

                            JSONObject otherinfo = person.optJSONObject("otherinfo");
                            if (otherinfo != null) {
                                String interests = otherinfo.optString("interests", FALLBACK);
                                String publications = otherinfo.optString("publications", FALLBACK);
                            }

                            JSONObject auxiliary = person.optJSONObject("auxiliary");
                            if (auxiliary != null) {
                                String plaintext_aux = auxiliary.getString("plaintext");
                            }

                            String out = firstname + " " + lastname + ":\n" +
                                    phonenumber + "\n" +
                                    email + "\n\n" +
                                    "Education: " + schoolnames.get(0) + "\n\n" +
                                    "Experience: " + functions.get(0) + " at " + companies.get(0)+ "\n\n";
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
