package com.example.resyoume.db;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "company_info")
public class CompanyInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String companyName;
    private String location;
    private String twitter;
    private String website;
    private String bio;
    private String linkedIn;
    private String additionalInfo;

    public CompanyInfo(int id,
                       String companyName,
                       String location,
                       String twitter,
                       String website,
                       String bio,
                       String linkedIn,
                       String additionalInfo) {
        this.id = id;
        this.companyName = companyName;
        this.location = location;
        this.twitter = twitter;
        this.website = website;
        this.bio = bio;
        this.linkedIn = linkedIn;
        this.additionalInfo = additionalInfo;
    }

    /**
     *
     * @param companyInfoJson
     */
    public CompanyInfo(JSONObject companyInfoJson) {
        if (companyInfoJson == null) {
            return;
        }
        try {
            this.id = 0;
            this.companyName = companyInfoJson.getString("name");
            this.location = companyInfoJson.getString("location");
            this.twitter = companyInfoJson.getString("twitter");
            this.website = companyInfoJson.getString("website");
            this.bio = companyInfoJson.getString("bio");
            this.linkedIn = companyInfoJson.getString("linkedin");
            String additionalInfo = companyInfoJson.getString("additionalInfo");
            this.additionalInfo = additionalInfo != null ? additionalInfo : "";
        } catch (JSONException e) {}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
