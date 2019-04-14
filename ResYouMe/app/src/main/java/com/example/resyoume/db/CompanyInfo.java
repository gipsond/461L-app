package com.example.resyoume.db;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "company_info")
public class CompanyInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private Date timestamp;

    private String companyName;
    private String location;
    private String twitter;
    private String website;
    private String bio;
    private String linkedIn;
    private String additionalInfo;

    public Integer rating;
    public String notes;

    public CompanyInfo(int id,
                       Date timestamp,
                       String companyName,
                       String location,
                       String twitter,
                       String website,
                       String bio,
                       String linkedIn,
                       String additionalInfo,
                       Integer rating,
                       String notes) {
        this.id = id;
        this.timestamp = timestamp;
        this.companyName = companyName;
        this.location = location;
        this.twitter = twitter;
        this.website = website;
        this.bio = bio;
        this.linkedIn = linkedIn;
        this.additionalInfo = additionalInfo;
        this.rating = rating;
        this.notes = notes;
    }

    /**
     * Creates a CompanyInfo object from OUR JSON format (not FullContact's)
     * @param companyInfoJson
     */
    public CompanyInfo(JSONObject companyInfoJson, boolean assignNewId) {
        if (companyInfoJson == null) {
            return;
        }
        try {
            this.id = assignNewId ? 0 : companyInfoJson.getInt("id");
            this.timestamp = new Date();
            this.companyName = companyInfoJson.getString("name");
            this.location = companyInfoJson.getString("location");
            this.twitter = companyInfoJson.getString("twitter");
            this.website = companyInfoJson.getString("website");
            this.bio = companyInfoJson.getString("bio");
            this.linkedIn = companyInfoJson.getString("linkedin");
            this.rating = companyInfoJson.getInt("rating");
            this.notes = companyInfoJson.getString("notes");
            String additionalInfo = companyInfoJson.getString("additionalInfo");
            this.additionalInfo = additionalInfo != null ? additionalInfo : "";
        } catch (JSONException e) {}
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject companyInfoJson = new JSONObject();
        companyInfoJson.put("type", "companyInfo");
        companyInfoJson.put("name", this.companyName);
        companyInfoJson.put("location", this.location);
        companyInfoJson.put("twitter", this.twitter);
        companyInfoJson.put("website", this.website);
        companyInfoJson.put("bio", this.bio);
        companyInfoJson.put("linkedin", this.linkedIn);
        companyInfoJson.put("rating", this.rating);
        companyInfoJson.put("notes", this.notes);
        companyInfoJson.put("additionalInfo", this.additionalInfo);
        return companyInfoJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() { return timestamp; }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
