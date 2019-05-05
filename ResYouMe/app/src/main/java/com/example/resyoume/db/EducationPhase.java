package com.example.resyoume.db;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "education_phases")
public class EducationPhase implements Phase {
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

    public EducationPhase(JSONObject educationJson, boolean assignNewId){
        if(educationJson == null){
            return;
        }
        try {
            this.id = assignNewId ? 0 : educationJson.getInt("id");
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

    @Override
    public boolean hasExistingId() {
        return getId() != 0;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EducationPhase education = (EducationPhase) o;

        return Objects.equals(this.getDateFrom(), education.getDateFrom())
            && Objects.equals(this.getDateTo(), education.getDateTo())
            && Objects.equals(this.getSchoolName(), education.getSchoolName())
            && Objects.equals(this.getCountry(), education.getCountry())
            && Objects.equals(this.getPlaintext(), education.getPlaintext());
    }
}
