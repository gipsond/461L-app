package com.example.resyoume;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResumeParseActivity extends AppCompatActivity {

    private static final String filename = "cv.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_parse);
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
                            JSONObject person = new JSONObject(response.getString("person"));

                            //Initialize Fields
                            String firstname = "";
                            String lastname = "";
                            String gender = "";
                            String birthday = "";
                            String nationality = "";
                            String address = "";
                            String email = "";
                            String phonenumber = "";
                            ArrayList<String> date_froms_edu = new ArrayList<>();
                            ArrayList<String> date_tos_edu = new ArrayList<>();
                            ArrayList<String> schoolnames = new ArrayList<>();
                            ArrayList<String> countries_edu = new ArrayList<>();
                            ArrayList<String[]> skills_edu = new ArrayList<>();
                            ArrayList<String> plaintexts_edu = new ArrayList<>();
                            ArrayList<String> date_froms_work = new ArrayList<>();
                            ArrayList<String> date_tos_work = new ArrayList<>();
                            ArrayList<String> functions = new ArrayList<>();
                            ArrayList<String> companies = new ArrayList<>();
                            ArrayList<String> countries_work = new ArrayList<>();
                            ArrayList<String[]> skills_work = new ArrayList<>();
                            ArrayList<String> plaintexts_work = new ArrayList<>();


                            if(!person.isNull("personalinfo")) { //returns true if string not found or maps to null
                                //get personalinfo from person object
                                JSONObject personalinfo = new JSONObject(person.getString("personalinfo"));

                                //parse personalinfo into fields
                                if(!personalinfo.isNull("firstname")) {
                                    firstname = personalinfo.getString("firstname");
                                }
                                if(!personalinfo.isNull("lastname")) {
                                    lastname = personalinfo.getString("lastname");
                                }
                                if(!personalinfo.isNull("gender")) {
                                    gender = personalinfo.getString("gender");
                                }
                                if(!personalinfo.isNull("birthday")) {
                                    birthday = personalinfo.getString("birthday");
                                }
                                if(!personalinfo.isNull("nationality")) {
                                    nationality = personalinfo.getString("nationality");
                                }
                                if(!personalinfo.isNull("address")) {
                                    address = personalinfo.getString("address");
                                }
                                if(!personalinfo.isNull("email")) {
                                    email = personalinfo.getString("email");
                                }
                                if(!personalinfo.isNull("phonenumber")) {
                                    phonenumber = personalinfo.getString("phonenumber");
                                }
                            }

                            if(!person.isNull("languages")) { //returns true if string not found or maps to null
                                //get languages from person object
                                JSONObject languages = new JSONObject(person.getString("languages"));

                                //parse languages into fields
                            }

                            if(!person.isNull("educationphase")) { //returns true if string not found or maps to null
                                //get educationphase from person object
                                JSONArray educationphases = new JSONArray(person.getString("educationphase"));

                                for (int i = 0; i < educationphases.length(); i++) {
                                    JSONObject educationphase = new JSONObject(educationphases.get(i).toString());

                                    //parse educationphase into fields
                                    if (!educationphase.isNull("datefrom")) {
                                        String datefrom_edu = educationphase.getString("datefrom");
                                        date_froms_edu.add(datefrom_edu);
                                    }
                                    if (!educationphase.isNull("dateto")) {
                                        String dateto_edu = educationphase.getString("dateto");
                                        date_tos_edu.add(dateto_edu);
                                    }
                                    if (!educationphase.isNull("schoolname")) {
                                        String schoolname = educationphase.getString("schoolname");
                                        schoolnames.add(schoolname);
                                    }
                                    if (!educationphase.isNull("country")) {
                                        String country_edu = educationphase.getString("country");
                                        countries_edu.add(country_edu);
                                    }
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
                                    if (!educationphase.isNull("plaintext")) {
                                        String plaintext_edu = educationphase.getString("plaintext");
                                        plaintexts_edu.add(plaintext_edu);
                                    }
                                }
                            }
                            if(!person.isNull("workphase")) { //returns true if string not found or maps to null
                                //get workphase from person object
                                JSONArray workphases = new JSONArray(person.getString("workphase"));

                                for (int i = 0; i < workphases.length(); i++) {
                                    JSONObject workphase = new JSONObject(workphases.get(i).toString());

                                    //parse workphase into fields
                                    if (!workphase.isNull("datefrom")) {
                                        String datefrom_work = workphase.getString("datefrom");
                                        date_froms_work.add(datefrom_work);
                                    }
                                    if (!workphase.isNull("dateto")) {
                                        String dateto_work = workphase.getString("dateto");
                                        date_tos_work.add(dateto_work);
                                    }
                                    if (!workphase.isNull("function")) {
                                        String function = workphase.getString("function");
                                        functions.add(function);
                                    }
                                    if (!workphase.isNull("company")) {
                                        String company = workphase.getString("company");
                                        companies.add(company);
                                    }
                                    if (!workphase.isNull("country")) {
                                        String country_work = workphase.getString("country");
                                        countries_work.add(country_work);
                                    }
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
                                    if (!workphase.isNull("plaintext")) {
                                        String plaintext_work = workphase.getString("plaintext");
                                        plaintexts_work.add(plaintext_work);
                                    }
                                }
                            }

                            if(!person.isNull("otherinfo")) { //returns true if string not found or maps to null
                                //get otherinfo from person object
                                JSONObject otherinfo = new JSONObject(person.getString("otherinfo"));

                                //parse otherinfo into fields
                                if(!otherinfo.isNull("interests")) {
                                    String interests = otherinfo.getString("interests");
                                }
                                if(!otherinfo.isNull("publications")) {
                                    String publications = otherinfo.getString("publications");
                                }
                            }

                            if(!person.isNull("auxiliary")) { //returns true if string not found or maps to null
                                //get auxiliary from person object
                                JSONObject auxiliary = new JSONObject(person.getString("auxiliary"));

                                //parse auxiliary into fields
                                if(!auxiliary.isNull("plaintext")) {
                                    String plaintext_aux = auxiliary.getString("plaintext");
                                }
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
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
            }
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
