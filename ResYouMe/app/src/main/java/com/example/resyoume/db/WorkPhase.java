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

    public WorkPhase(JSONObject workJson, boolean assignNewId) {
        if(workJson == null){
            return;
        }
        try {
            this.id = assignNewId ? 0 : workJson.getInt("id");
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkPhase work = (WorkPhase) o;

        return Utils.equalOrBothNull(this.getDateFrom(),  work.getDateFrom() )
            && Utils.equalOrBothNull(this.getDateTo(),    work.getDateTo()   )
            && Utils.equalOrBothNull(this.getFunction(),  work.getFunction() )
            && Utils.equalOrBothNull(this.getCompany(),   work.getCompany()  )
            && Utils.equalOrBothNull(this.getCountry(),   work.getCountry()  )
            && Utils.equalOrBothNull(this.getPlaintext(), work.getPlaintext());
    }
}
