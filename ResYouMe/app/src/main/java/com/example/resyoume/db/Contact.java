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

    public Contact(JSONObject contactJson, boolean assignNewId){
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
        Contact contact = (Contact) o;

        if(contact.getTimestamp() == null || this.timestamp == null){
            if(!(contact.getTimestamp() == null && this.timestamp == null)){
                return false;
            }
        }
        else{
            if(!contact.getTimestamp().equals(this.timestamp)){
                return false;
            }
        }

        if(contact.getFirstName() == null || this.firstName == null){
            if(!(contact.getFirstName() == null && this.firstName == null)){
                return false;
            }
        }
        else{
            if(!contact.getFirstName().equals(this.firstName)){
                return false;
            }
        }

        if(contact.getLastName() == null || this.lastName == null){
            if(!(contact.getLastName() == null && this.lastName == null)){
                return false;
            }
        }
        else{
            if(!contact.getLastName().equals(this.lastName)){
                return false;
            }
        }

        if(contact.getTitle() == null || this.title == null){
            if(!(contact.getTitle() == null && this.title == null)){
                return false;
            }
        }
        else{
            if(!contact.getTitle().equals(this.title)){
                return false;
            }
        }

        if(contact.getAddress() == null || this.address == null){
            if(!(contact.getAddress() == null && this.address == null)){
                return false;
            }
        }
        else{
            if(!contact.getAddress().equals(this.address)){
                return false;
            }
        }

        if(contact.getPostcode() == null || this.postcode == null){
            if(!(contact.getPostcode() == null && this.postcode == null)){
                return false;
            }
        }
        else{
            if(!contact.getPostcode().equals(this.postcode)){
                return false;
            }
        }

        if(contact.getCity() == null || this.city == null){
            if(!(contact.getCity() == null && this.city == null)){
                return false;
            }
        }
        else{
            if(!contact.getCity().equals(this.city)){
                return false;
            }
        }

        if(contact.getState() == null || this.state == null){
            if(!(contact.getState() == null && this.state == null)){
                return false;
            }
        }
        else{
            if(!contact.getState().equals(this.state)){
                return false;
            }
        }

        if(contact.getCountry() == null || this.country == null){
            if(!(contact.getCountry() == null && this.country == null)){
                return false;
            }
        }
        else{
            if(!contact.getCountry().equals(this.country)){
                return false;
            }
        }

        if(contact.getEmail() == null || this.email == null){
            if(!(contact.getEmail() == null && this.email == null)){
                return false;
            }
        }
        else{
            if(!contact.getEmail().equals(this.email)){
                return false;
            }
        }

        if(contact.getPhoneNumber() == null || this.phoneNumber == null){
            if(!(contact.getPhoneNumber() == null && this.phoneNumber == null)){
                return false;
            }
        }
        else{
            if(!contact.getPhoneNumber().equals(this.phoneNumber)){
                return false;
            }
        }

        if(contact.getHomepage() == null || this.homepage == null){
            if(!(contact.getHomepage() == null && this.homepage == null)){
                return false;
            }
        }
        else{
            if(!contact.getHomepage().equals(this.homepage)){
                return false;
            }
        }

        if(contact.getInterests() == null || this.interests == null){
            if(!(contact.getInterests() == null && this.interests == null)){
                return false;
            }
        }
        else{
            if(!contact.getInterests().equals(this.interests)){
                return false;
            }
        }

        if(contact.getPublications() == null || this.publications == null){
            if(!(contact.getPublications() == null && this.publications == null)){
                return false;
            }
        }
        else{
            if(!contact.getPublications().equals(this.publications)){
                return false;
            }
        }

        if(contact.getPlaintext() == null || this.plaintext == null){
            if(!(contact.getPlaintext() == null && this.plaintext == null)){
                return false;
            }
        }
        else{
            if(!contact.getPlaintext().equals(this.plaintext)){
                return false;
            }
        }

        if(contact.getRating() == null || this.rating == null){
            if(!(contact.getRating() == null && this.rating == null)){
                return false;
            }
        }
        else{
            if(!contact.getRating().equals(this.rating)){
                return false;
            }
        }

        if(contact.getNotes() == null || this.notes == null){
            if(!(contact.getNotes() == null && this.notes == null)){
                return false;
            }
        }
        else{
            if(!contact.getNotes().equals(this.notes)){
                return false;
            }
        }
        return true;
    }
}
