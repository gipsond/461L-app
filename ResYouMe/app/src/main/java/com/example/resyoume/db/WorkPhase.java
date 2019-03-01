package com.example.resyoume.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "work_phases")
public class WorkPhase {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int contactId;

    private String dateFrom;
    private String dateTo;
    private String function;
    private String company;
    private String country;
    private String plaintext;


    /* Constructors */

    public WorkPhase(int id,
                     String dateFrom, String dateTo,
                     String function,
                     String company,
                     String country,
                     String plaintext) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.function = function;
        this.company = company;
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

    public String getFunction() {
        return function;
    }

    public String getCompany() {
        return company;
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

    public void setFunction(String function) {
        this.function = function;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

}
