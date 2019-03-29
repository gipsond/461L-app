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

    public Resume(JSONObject resume) {
        if(resume == null){
            return;
        }
        try {
            // TODO: handle malformed JSON gracefully (currently crashes due to NullPointerException)
            JSONObject contact = resume.getJSONObject("contact");
            JSONArray education = resume.getJSONArray("educationPhases");
            JSONArray work = resume.getJSONArray("workPhases");
            this.contact = new Contact(contact);
            this.educationPhases = getEducationList(education);
            this.workPhases = getWorkList(work);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Resume(){

    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject resume = new JSONObject();
        resume.put("contact", this.contact.toJSONObject());
        resume.put("educationPhases", this.getEducationArray());
        resume.put("workPhases", this.getWorkArray());
        resume.put("type", "resume");
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

    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null){
            return false;
        }
        if(getClass() != o.getClass()){
            return false;
        }
        Resume resume = (Resume) o;

        if(resume.contact == null || this.contact == null){
            if(!(resume.contact == null && this.contact == null)){
                return false;
            }
        }
        else{
            if(!resume.contact.equals(this.contact)){
                return false;
            }
        }

        if(resume.educationPhases == null || this.educationPhases == null){
            if(!(resume.educationPhases == null && this.educationPhases == null)){
                return false;
            }
        }
        else{
            if(resume.educationPhases.size() == this.educationPhases.size()){
                int x;
                for(x = 0; x < this.educationPhases.size(); x++){
                    if(!resume.educationPhases.get(x).equals(this.educationPhases.get(x))){
                        return false;
                    }
                }
            }
            else{
                return false;
            }
        }

        if(resume.workPhases == null || this.workPhases == null){
            if(!(resume.workPhases == null && this.workPhases == null)){
                return false;
            }
        }
        else{
            if(resume.workPhases.size() == this.workPhases.size()){
                int x;
                for(x = 0; x < this.workPhases.size(); x++){
                    if(!resume.workPhases.get(x).equals(this.workPhases.get(x))){
                        return false;
                    }
                }
            }
            else{
                return false;
            }
        }

        return true;
    }
}
