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
        String httpReq = "https://api.github.com/repos/gipsond/461L-app/stats/contributors";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, httpReq, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        TextView github_stats = (TextView) findViewById(R.id.github_stats);
                        TextView erick_stats = (TextView) findViewById(R.id.erick_stats);
                        TextView david_stats = (TextView) findViewById(R.id.david_stats);
                        TextView james_stats = (TextView) findViewById(R.id.james_stats);
                        TextView jonathan_stats = (TextView) findViewById(R.id.jonathan_stats);
                        TextView errol_stats = (TextView) findViewById(R.id.errol_stats);
                        TextView mircea_stats = (TextView) findViewById(R.id.mircea_stats);

                        int total_commits = 0;

                        int x;
                        for(x = 0; x < response.length(); x++){

                            try{
                                JSONObject contributor = response.getJSONObject(x);
                                JSONObject author = contributor.getJSONObject("author");
                                String name = author.getString("login");
                                String commits = contributor.getString("total");
                                String stat_string = "Github Commits: " + commits;
                                total_commits += Integer.parseInt(commits);

                                if(name.equals("erickjshepherd")){
                                    erick_stats.setText(stat_string);
                                }
                                else if(name.equals("gipsond")){
                                    david_stats.setText(stat_string);
                                }
                                else if(name.equals("jmounsif")){
                                    jonathan_stats.setText(stat_string);
                                }
                                else if(name.equals("errolwilliams")){
                                    errol_stats.setText(stat_string);
                                }
                            }
                            catch(Exception e){
                                github_stats.setText("error");
                            }
                        }
                        String totalCommitString = String.valueOf(total_commits);
                        String totalStatString = "Total Github Commits: " + totalCommitString;
                        github_stats.setText(totalStatString);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}
