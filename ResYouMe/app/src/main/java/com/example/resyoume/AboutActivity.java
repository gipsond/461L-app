package com.example.resyoume;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // set up github link
        TextView github_link = (TextView) findViewById(R.id.github_link);
        String link = "<a href=\"https://github.com/gipsond/461L-app\">Github Repo</a>";
        github_link.setText(Html.fromHtml(link));
        github_link.setMovementMethod(LinkMovementMethod.getInstance());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String httpReq = "https://api.github.com/repos/gipsond/461L-app/stats/commit_activity";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, httpReq, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        TextView github_stats = (TextView) findViewById(R.id.github_stats);
                        int total = 0;
                        int x;
                        for(x = 0; x < response.length(); x++){

                            try{
                                JSONObject week = response.getJSONObject(x);
                                String commits = week.getString("total");
                                total += Integer.parseInt(commits);
                            }
                            catch(Exception e){
                                github_stats.setText("error");
                            }
                        }
                        String totalString = String.valueOf(total);
                        github_stats.setText("Total Commits: " + totalString);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}
