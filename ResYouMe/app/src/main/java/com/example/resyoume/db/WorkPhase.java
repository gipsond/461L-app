package com.example.resyoume.db;

import org.json.JSONException;
import org.json.JSONObject;

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

    public WorkPhase(JSONObject workJson){
        if(workJson == null){
            return;
        }
        try {
            this.id = 0;
            this.contactId = 0;
            this.dateFrom = workJson.getString("dateFrom");
            this.dateTo = workJson.getString("dateTo");
            this.function = workJson.getString("function");
            this.company = workJson.getString("company");
            this.country = workJson.getString("country");
            this.plaintext = workJson.getString("plaintext");
        }
        catch (JSONException e) {}
    }

    public WorkPhase(){

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

    /* JSON Functions */

    public JSONObject toJSONObject() throws JSONException {
        JSONObject work = new JSONObject();
        work.put("id", this.id);
        work.put("contactId", this.contactId);
        work.put("dateFrom", this.dateFrom);
        work.put("dateTo", this.dateTo);
        work.put("function", this.function);
        work.put("company", this.company);
        work.put("country", this.country);
        work.put("plaintext", this.plaintext);
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
        WorkPhase work = (WorkPhase) o;

        if(work.getId() != this.id){return false;}

        if(work.getContactId() != this.contactId){return false;}

        if(work.getDateFrom() == null || this.dateFrom == null){
            if(!(work.getDateFrom() == null && this.dateFrom == null)){
                return false;
            }
        }
        else{
            if(!work.getDateFrom().equals(this.dateFrom)){
                return false;
            }
        }

        if(work.getDateTo() == null || this.dateTo == null){
            if(!(work.getDateTo() == null && this.dateTo == null)){
                return false;
            }
        }
        else{
            if(!work.getDateTo().equals(this.dateTo)){
                return false;
            }
        }

        if(work.getFunction() == null || this.function == null){
            if(!(work.getFunction() == null && this.function == null)){
                return false;
            }
        }
        else{
            if(!work.getFunction().equals(this.function)){
                return false;
            }
        }

        if(work.getCompany() == null || this.company == null){
            if(!(work.getCompany() == null && this.company == null)){
                return false;
            }
        }
        else{
            if(!work.getCompany().equals(this.company)){
                return false;
            }
        }

        if(work.getCountry() == null || this.country == null){
            if(!(work.getCountry() == null && this.country== null)){
                return false;
            }
        }
        else{
            if(!work.getCountry().equals(this.country)){
                return false;
            }
        }

        if(work.getPlaintext() == null || this.plaintext == null){
            if(!(work.getPlaintext() == null && this.plaintext == null)){
                return false;
            }
        }
        else{
            if(!work.getPlaintext().equals(this.plaintext)){
                return false;
            }
        }
        return true;
    }
}
