package com.example.resyoume.db;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "education_phases")
public class EducationPhase {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int contactId;

    private String dateFrom;
    private String dateTo;
    private String schoolName;
    private String country;
    private String plaintext;


    /* Constructors */

    public EducationPhase(int id,
                          String dateFrom, String dateTo,
                          String schoolName,
                          String country,
                          String plaintext) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.schoolName = schoolName;
        this.country = country;
        this.plaintext = plaintext;
    }

    public EducationPhase(JSONObject educationJson){
        if(educationJson == null){
            return;
        }
        try {
            this.id = 0;
            this.contactId = 0;
            this.dateFrom = educationJson.getString("dateFrom");
            this.dateTo = educationJson.getString("dateTo");
            this.schoolName = educationJson.getString("schoolName");
            this.country = educationJson.getString("country");
            this.plaintext = educationJson.getString("plaintext");
        }
        catch (JSONException e) {}
    }

    public EducationPhase(){

    }

    /* Getters */

    public int getId() {
        return id;
    }

    public int getContactId() {
        return contactId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getCountry() {
        return country;
    }

    public String getPlaintext() {
        return plaintext;
    }


    /* Setters */

    public void setId(int id) {
        this.id = id;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    /* JSON Functions */

    public JSONObject toJSONObject() throws JSONException {
        JSONObject education = new JSONObject();
        education.put("id", this.id);
        education.put("contactId", this.contactId);
        education.put("dateFrom", this.dateFrom);
        education.put("dateTo", this.dateTo);
        education.put("schoolName", this.schoolName);
        education.put("country", this.country);
        education.put("plaintext", this.plaintext);
        return education;
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
        EducationPhase education = (EducationPhase) o;

        if(education.getId() != this.id){return false;}

        if(education.getContactId() != this.contactId){return false;}

        if(education.getDateFrom() == null || this.dateFrom == null){
            if(!(education.getDateFrom() == null && this.dateFrom == null)){
                return false;
            }
        }
        else{
            if(!education.getDateFrom().equals(this.dateFrom)){
                return false;
            }
        }

        if(education.getDateTo() == null || this.dateTo == null){
            if(!(education.getDateTo() == null && this.dateTo == null)){
                return false;
            }
        }
        else{
            if(!education.getDateTo().equals(this.dateTo)){
                return false;
            }
        }

        if(education.getSchoolName() == null || this.schoolName == null){
            if(!(education.getSchoolName() == null && this.schoolName == null)){
                return false;
            }
        }
        else{
            if(!education.getSchoolName().equals(this.schoolName)){
                return false;
            }
        }

        if(education.getCountry() == null || this.country == null){
            if(!(education.getCountry() == null && this.country== null)){
                return false;
            }
        }
        else{
            if(!education.getCountry().equals(this.country)){
                return false;
            }
        }

        if(education.getPlaintext() == null || this.plaintext == null){
            if(!(education.getPlaintext() == null && this.plaintext == null)){
                return false;
            }
        }
        else{
            if(!education.getPlaintext().equals(this.plaintext)){
                return false;
            }
        }
        return true;
    }
}
