package com.example.resyoume.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class Resume {
    @Embedded
    public Contact contact;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<EducationPhase> educationPhases;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<WorkPhase> workPhases;

    public Resume(Contact contact, List<EducationPhase> educationPhases, List<WorkPhase> workPhases) {
        this.contact = contact;
        this.educationPhases = educationPhases;
        this.workPhases = workPhases;
    }

    public Resume(String resumeJson){
        try {
            JSONArray resume = new JSONArray(resumeJson);
            this.contact = new Contact(resume.getJSONObject(0));
            this.educationPhases = getEducationList(resume.getJSONArray(1));
            this.workPhases = getWorkList(resume.getJSONArray(2));
        }
        catch (Exception e) {
        }
    }

    public JSONArray toJSONArray() throws JSONException {
        JSONArray resume = new JSONArray();
        resume.put(this.contact.toJSONObject());
        resume.put(this.getEducationArray());
        resume.put(this.getWorkArray());
        return resume;
    }

    private List<EducationPhase> getEducationList(JSONArray educationJson) throws JSONException {
        List<EducationPhase> education = new ArrayList<EducationPhase>();
        int x;
        for(x = 0; x < educationJson.length(); x++){
            education.add(new EducationPhase(educationJson.getJSONObject(x)));
        }
        return education;
    }

    private List<WorkPhase> getWorkList(JSONArray workJson) throws JSONException {
        List<WorkPhase> work = new ArrayList<WorkPhase>();
        int x;
        for(x = 0; x < workJson.length(); x++){
            work.add(new WorkPhase(workJson.getJSONObject(x)));
        }
        return work;
    }

    private JSONArray getEducationArray() throws JSONException {
        JSONArray education = new JSONArray();
        int x;
        for(x = 0; x < educationPhases.size(); x++){
            JSONObject item = educationPhases.get(x).toJSONObject();
            education.put(item);
        }
        return education;
    }

    private JSONArray getWorkArray() throws JSONException {
        JSONArray work = new JSONArray();
        int x;
        for(x = 0; x < workPhases.size(); x++){
            JSONObject item = workPhases.get(x).toJSONObject();
            work.put(item);
        }
        return work;
    }
}
