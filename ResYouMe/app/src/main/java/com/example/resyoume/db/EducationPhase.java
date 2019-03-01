package com.example.resyoume.db;

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

}
