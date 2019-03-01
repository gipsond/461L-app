package com.example.resyoume.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

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


    /* Constructors */

    public Contact(int id,
                   String firstName, String lastName,
                   String title,
                   String address, String postcode, String city, String state, String country,
                   String email, String phoneNumber, String homepage,
                   String interests, String publications,
                   String plaintext) {
        this.id = id;
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
    }


    /* Getters */

    public int getId() {
        return id;
    }

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


    /* Setters */

    public void setId(int id) {
        this.id = id;
    }

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
}
