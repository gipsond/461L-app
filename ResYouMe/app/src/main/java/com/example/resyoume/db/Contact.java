package com.example.resyoume.db;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private Date timestamp;

    private String firstName;
    private String lastName;
//    private String gender; // Unnecessary and legally discouraged for recruiters anyway
    private String title;
//    private String birthday; // Unnecessary
//    private String birthplace; // Unnecessary
//    private String nationality; // Unnecessary?
    private String address;
    private String postcode;
    private String city;
    private String state;
    private String country;
    private String email;
    private String phoneNumber;
    private String homepage;

    // From 'otherinfo'
    private String interests;
    private String publications;

    // Full plaintext of resume from 'auxiliary'
    private String plaintext;

    // TODO: rename Contact or refactor it to better show its purpose. It holds the data that isn't held in related arrays.
    // Rating and notes on a resume
    public Integer rating;
    public String notes;


    /* Constructors */

    public Contact(int id,
                   Date timestamp,
                   String firstName, String lastName,
                   String title,
                   String address, String postcode, String city, String state, String country,
                   String email, String phoneNumber, String homepage,
                   String interests, String publications,
                   String plaintext,
                   Integer rating,
                   String notes) {
        this.id = id;
        this.timestamp = timestamp;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.homepage = homepage;
        this.interests = interests;
        this.publications = publications;
        this.plaintext = plaintext;
        this.rating = rating;
        this.notes = notes;
    }

    public Contact(JSONObject contactJson, boolean assignNewId) {
        if(contactJson == null){
            return;
        }
        try {
            this.id = assignNewId ? 0 : contactJson.getInt("id");
            this.timestamp = new Date();
            this.firstName = contactJson.getString("firstName");
            this.lastName = contactJson.getString("lastName");
            this.title = contactJson.getString("title");
            this.address = contactJson.getString("address");
            this.postcode = contactJson.getString("postcode");
            this.city = contactJson.getString("city");
            this.state = contactJson.getString("state");
            this.country = contactJson.getString("country");
            this.email = contactJson.getString("email");
            this.phoneNumber = contactJson.getString("phoneNumber");
            this.homepage = contactJson.getString("homepage");
            this.interests = contactJson.getString("interests");
            this.publications = contactJson.getString("publications");
            this.plaintext = contactJson.getString("plaintext");

            int possibleRating = contactJson.optInt("rating", -1);
            this.rating = (possibleRating == -1) ? null : possibleRating;

            String possibleNotes = contactJson.optString("notes");
            this.notes = (possibleNotes == "") ? null : possibleNotes;
        }
        catch (JSONException e) {}
    }

    public Contact() {

    }



    /* Getters */

    public int getId() {
        return id;
    }

    public Date getTimestamp() { return timestamp; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() { return firstName + " " + lastName; }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getInterests() {
        return interests;
    }

    public String getPublications() {
        return publications;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public Integer getRating() {
        return rating;
    }

    public String getNotes() {
        return notes;
    }



    /* Setters */

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public void setPublications(String publications) {
        this.publications = publications;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }



    /* JSON functions */

    public JSONObject toJSONObject() throws JSONException {
        JSONObject contact = new JSONObject();
        contact.put("id",this.id);
        contact.put("firstName", this.firstName);
        contact.put("lastName", this.lastName);
        contact.put("title", this.title);
        contact.put("address", this.address);
        contact.put("postcode", this.postcode);
        contact.put("city", this.city);
        contact.put("state", this.state);
        contact.put("country", this.country);
        contact.put("email", this.email);
        contact.put("phoneNumber", this.phoneNumber);
        contact.put("homepage", this.homepage);
        contact.put("interests", this.interests);
        contact.put("publications", this.publications);
        contact.put("plaintext", this.plaintext);
        contact.put("rating", this.rating);
        contact.put("notes", this.notes);
        return contact;
    }

    // doesnt handle null fields
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
        Contact other = (Contact) o;

        return Utils.equalOrBothNull(other.getTimestamp(),    this.getTimestamp()   )
            && Utils.equalOrBothNull(other.getFirstName(),    this.getFirstName()   )
            && Utils.equalOrBothNull(other.getLastName(),     this.getLastName()    )
            && Utils.equalOrBothNull(other.getTitle(),        this.getTitle()       )
            && Utils.equalOrBothNull(other.getAddress(),      this.getAddress()     )
            && Utils.equalOrBothNull(other.getPostcode(),     this.getPostcode()    )
            && Utils.equalOrBothNull(other.getCity(),         this.getCity()        )
            && Utils.equalOrBothNull(other.getState(),        this.getState()       )
            && Utils.equalOrBothNull(other.getCountry(),      this.getCountry()     )
            && Utils.equalOrBothNull(other.getEmail(),        this.getEmail()       )
            && Utils.equalOrBothNull(other.getPhoneNumber(),  this.getPhoneNumber() )
            && Utils.equalOrBothNull(other.getHomepage(),     this.getHomepage()    )
            && Utils.equalOrBothNull(other.getInterests(),    this.getInterests()   )
            && Utils.equalOrBothNull(other.getPublications(), this.getPublications())
            && Utils.equalOrBothNull(other.getPlaintext(),    this.getPlaintext()   )
            && Utils.equalOrBothNull(other.getRating(),       this.getRating()      )
            && Utils.equalOrBothNull(other.getNotes(),        this.getNotes()       );

    }


}
