package com.example.resyoume;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JSONUnitTests {

    /* Contact tests */
    @Test
    public void contact_to_json() throws JSONException {
        Contact contact = new Contact(0, null,"Erick", "Shepherd", "", "123 road st", "12345",
                    "Austin", "TX", "US", "erickshepherd@mail.com", "123-456-7890",
                "erick.com", "android design", "", "", null, null);
        JSONObject contactJson = contact.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put("id", 0);
        expected.put("firstName", "Erick");
        expected.put("lastName", "Shepherd");
        expected.put("title", "");
        expected.put("address", "123 road st");
        expected.put("postcode", "12345");
        expected.put("city", "Austin");
        expected.put("state", "TX");
        expected.put("country", "US");
        expected.put("email", "erickshepherd@mail.com");
        expected.put("phoneNumber", "123-456-7890");
        expected.put("homepage", "erick.com");
        expected.put("interests", "android design");
        expected.put("publications", "");
        expected.put("plaintext", "");
        assertEquals("JSONObjects are not equal", expected.toString(), contactJson.toString());
    }

    @Test
    public void json_to_contact() throws JSONException {
        JSONObject contactJson = new JSONObject();
        contactJson.put("id", 0);
        contactJson.put("firstName", "Erick");
        contactJson.put("lastName", "Shepherd");
        contactJson.put("title", "");
        contactJson.put("address", "123 road st");
        contactJson.put("postcode", "12345");
        contactJson.put("city", "Austin");
        contactJson.put("state", "TX");
        contactJson.put("country", "US");
        contactJson.put("email", "erickshepherd@mail.com");
        contactJson.put("phoneNumber", "123-456-7890");
        contactJson.put("homepage", "erick.com");
        contactJson.put("interests", "android design");
        contactJson.put("publications", "");
        contactJson.put("plaintext", "");
        Contact actual = new Contact(contactJson, true);
        Contact expected = new Contact(0, actual.getTimestamp(),"Erick", "Shepherd", "", "123 road st", "12345",
                "Austin", "TX", "US", "erickshepherd@mail.com", "123-456-7890",
                "erick.com", "android design", "", "", null, null);
        assertEquals("Contacts are not equal", expected, actual);
    }

    @Test
    public void empty_json_to_contact(){
        JSONObject contactJson = new JSONObject();
        Contact actual = new Contact(contactJson, true);
        Contact expected = new Contact();
        expected.setTimestamp(actual.getTimestamp());
        assertEquals("Contacts are not equal", expected, actual);
    }

    @Test
    public void null_json_to_contact(){
        Contact actual = new Contact(null, true);
        Contact expected = new Contact();
        assertEquals("Contacts are not equal", expected, actual);
    }

    /* EducationPhase tests */
    @Test
    public void education_to_json() throws JSONException {
        EducationPhase actualEdu = new EducationPhase(0, "8/17/15", "5/25/19", "University of Texas at Austin", "US", "");
        actualEdu.setContactId(0);
        JSONObject actual = actualEdu.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put("id", 0);
        expected.put("contactId", 0);
        expected.put("dateFrom", "8/17/15");
        expected.put("dateTo", "5/25/19");
        expected.put("schoolName", "University of Texas at Austin");
        expected.put("country", "US");
        expected.put("plaintext", "");
        assertEquals("JSON Objects are not equal", expected.toString(), actual.toString());
    }

    @Test
    public void json_to_education() throws JSONException {
        JSONObject actualJson = new JSONObject();
        actualJson.put("id", 0);
        actualJson.put("contactId", 0);
        actualJson.put("dateFrom", "8/17/15");
        actualJson.put("dateTo", "5/25/19");
        actualJson.put("schoolName", "University of Texas at Austin");
        actualJson.put("country", "US");
        actualJson.put("plaintext", "");
        EducationPhase actual = new EducationPhase(actualJson, true);
        EducationPhase expected = new EducationPhase(1, "8/17/15", "5/25/19", "University of Texas at Austin", "US", "");
        expected.setContactId(0);
        assertEquals("EducationPhases are not equal", expected, actual);
    }

    @Test
    public void empty_json_to_education(){
        JSONObject educationJson = new JSONObject();
        EducationPhase actual = new EducationPhase(educationJson, true);
        EducationPhase expected = new EducationPhase();
        assertEquals("EducationPhases are not equal", expected, actual);
    }

    @Test
    public void null_json_to_education(){
        EducationPhase actual = new EducationPhase(null, true);
        EducationPhase expected = new EducationPhase();
        assertEquals("EducationPhases are not equal", expected, actual);
    }

    /* WorkPhase tests */
    @Test
    public void work_to_json() throws JSONException {
        WorkPhase actualWork = new WorkPhase(0, "1/1/19", "4/1/19", "testing", "ResYouMe", "US", "");
        actualWork.setContactId(0);
        JSONObject actual = actualWork.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put("id", 0);
        expected.put("contactId", 0);
        expected.put("dateFrom", "1/1/19");
        expected.put("dateTo", "4/1/19");
        expected.put("function", "testing");
        expected.put("company", "ResYouMe");
        expected.put("country", "US");
        expected.put("plaintext", "");
        assertEquals("JSON Objects are not equal", expected.toString(), actual.toString());
    }

    @Test
    public void json_to_work() throws JSONException {
        JSONObject actualJson = new JSONObject();
        actualJson.put("id", 0);
        actualJson.put("contactId", 0);
        actualJson.put("dateFrom", "1/1/19");
        actualJson.put("dateTo", "4/1/19");
        actualJson.put("function", "testing");
        actualJson.put("company", "ResYouMe");
        actualJson.put("country", "US");
        actualJson.put("plaintext", "");
        WorkPhase actual = new WorkPhase(actualJson, true);
        WorkPhase expected = new WorkPhase(0, "1/1/19", "4/1/19", "testing", "ResYouMe", "US", "");
        expected.setContactId(0);
        assertEquals("WorkPhases are not equal", expected, actual);
    }

    @Test
    public void empty_json_to_work(){
        JSONObject workJson = new JSONObject();
        WorkPhase actual = new WorkPhase(workJson, true);
        WorkPhase expected = new WorkPhase();
        assertEquals("WorkPhases are not equal", expected, actual);
    }

    @Test
    public void null_json_to_work(){
        WorkPhase actual = new WorkPhase(null, true);
        WorkPhase expected = new WorkPhase();
        assertEquals("WorkPhases are not equal", expected, actual);
    }

    /* Resume tests */
    @Test
    public void resume_to_json() throws JSONException {
        // set up the test resume and get its json array
        Contact contact = new Contact(0, null, "Erick", "Shepherd", "", "123 road st", "12345",
                "Austin", "TX", "US", "erickshepherd@mail.com", "123-456-7890",
                "erick.com", "android design", "", "", null, null);
        EducationPhase actualEdu = new EducationPhase(0, "8/17/15", "5/25/19", "University of Texas at Austin", "US", "");
        actualEdu.setContactId(0);
        EducationPhase actualEdu2 = new EducationPhase(0, "8/17/19", "5/25/23", "University of Texas at Austin", "US", "");
        actualEdu2.setContactId(0);
        WorkPhase actualWork = new WorkPhase(0, "1/1/19", "4/1/19", "testing", "ResYouMe", "US", "");
        actualWork.setContactId(0);
        WorkPhase actualWork2 = new WorkPhase(0, "4/1/19", "7/1/19", "verification", "ResYouMe", "US", "");
        actualWork2.setContactId(0);
        List<EducationPhase> actualEduList = new ArrayList<EducationPhase>();
        actualEduList.add(actualEdu);
        actualEduList.add(actualEdu2);
        List<WorkPhase> actualWorkList = new ArrayList<WorkPhase>();
        actualWorkList.add(actualWork);
        actualWorkList.add(actualWork2);
        Resume actualResume = new Resume(contact, actualEduList, actualWorkList);
        JSONObject actual = actualResume.toJSONObject();

        // set up the expected json array
        JSONObject expectedContact = new JSONObject();
        expectedContact.put("id", 0);
        expectedContact.put("firstName", "Erick");
        expectedContact.put("lastName", "Shepherd");
        expectedContact.put("title", "");
        expectedContact.put("address", "123 road st");
        expectedContact.put("postcode", "12345");
        expectedContact.put("city", "Austin");
        expectedContact.put("state", "TX");
        expectedContact.put("country", "US");
        expectedContact.put("email", "erickshepherd@mail.com");
        expectedContact.put("phoneNumber", "123-456-7890");
        expectedContact.put("homepage", "erick.com");
        expectedContact.put("interests", "android design");
        expectedContact.put("publications", "");
        expectedContact.put("plaintext", "");

        JSONObject expectedEdu1 = new JSONObject();
        expectedEdu1.put("id", 0);
        expectedEdu1.put("contactId", 0);
        expectedEdu1.put("dateFrom", "8/17/15");
        expectedEdu1.put("dateTo", "5/25/19");
        expectedEdu1.put("schoolName", "University of Texas at Austin");
        expectedEdu1.put("country", "US");
        expectedEdu1.put("plaintext", "");
        JSONObject expectedEdu2 = new JSONObject();
        expectedEdu2.put("id", 0);
        expectedEdu2.put("contactId", 0);
        expectedEdu2.put("dateFrom", "8/17/19");
        expectedEdu2.put("dateTo", "5/25/23");
        expectedEdu2.put("schoolName", "University of Texas at Austin");
        expectedEdu2.put("country", "US");
        expectedEdu2.put("plaintext", "");
        JSONArray expectedEduList = new JSONArray();
        expectedEduList.put(expectedEdu1);
        expectedEduList.put(expectedEdu2);

        JSONObject expectedWork1 = new JSONObject();
        expectedWork1.put("id", 0);
        expectedWork1.put("contactId", 0);
        expectedWork1.put("dateFrom", "1/1/19");
        expectedWork1.put("dateTo", "4/1/19");
        expectedWork1.put("function", "testing");
        expectedWork1.put("company", "ResYouMe");
        expectedWork1.put("country", "US");
        expectedWork1.put("plaintext", "");
        JSONObject expectedWork2 = new JSONObject();
        expectedWork2.put("id", 0);
        expectedWork2.put("contactId", 0);
        expectedWork2.put("dateFrom", "4/1/19");
        expectedWork2.put("dateTo", "7/1/19");
        expectedWork2.put("function", "verification");
        expectedWork2.put("company", "ResYouMe");
        expectedWork2.put("country", "US");
        expectedWork2.put("plaintext", "");
        JSONArray expectedWorkList = new JSONArray();
        expectedWorkList.put(expectedWork1);
        expectedWorkList.put(expectedWork2);

        JSONObject expected = new JSONObject();
        expected.put("contact", expectedContact);
        expected.put("educationPhases", expectedEduList);
        expected.put("workPhases", expectedWorkList);
        expected.put("type", "resume");

        // compare the two json strings
        assertEquals("JSON Objects are not equal", expected.toString(), actual.toString());
    }

    @Test
    public void json_to_resume() throws JSONException {
        // set up the actual json array
        JSONObject actualJsonContact = new JSONObject();
        actualJsonContact.put("id", 0);
        actualJsonContact.put("firstName", "Erick");
        actualJsonContact.put("lastName", "Shepherd");
        actualJsonContact.put("title", "");
        actualJsonContact.put("address", "123 road st");
        actualJsonContact.put("postcode", "12345");
        actualJsonContact.put("city", "Austin");
        actualJsonContact.put("state", "TX");
        actualJsonContact.put("country", "US");
        actualJsonContact.put("email", "erickshepherd@mail.com");
        actualJsonContact.put("phoneNumber", "123-456-7890");
        actualJsonContact.put("homepage", "erick.com");
        actualJsonContact.put("interests", "android design");
        actualJsonContact.put("publications", "");
        actualJsonContact.put("plaintext", "");

        JSONObject actualJsonEdu1 = new JSONObject();
        actualJsonEdu1.put("id", 0);
        actualJsonEdu1.put("contactId", 0);
        actualJsonEdu1.put("dateFrom", "8/17/15");
        actualJsonEdu1.put("dateTo", "5/25/19");
        actualJsonEdu1.put("schoolName", "University of Texas at Austin");
        actualJsonEdu1.put("country", "US");
        actualJsonEdu1.put("plaintext", "");
        JSONObject actualJsonEdu2 = new JSONObject();
        actualJsonEdu2.put("id", 0);
        actualJsonEdu2.put("contactId", 0);
        actualJsonEdu2.put("dateFrom", "8/17/19");
        actualJsonEdu2.put("dateTo", "5/25/23");
        actualJsonEdu2.put("schoolName", "University of Texas at Austin");
        actualJsonEdu2.put("country", "US");
        actualJsonEdu2.put("plaintext", "");
        JSONArray actualJsonEduList = new JSONArray();
        actualJsonEduList.put(actualJsonEdu1);
        actualJsonEduList.put(actualJsonEdu2);

        JSONObject actualJsonWork1 = new JSONObject();
        actualJsonWork1.put("id", 0);
        actualJsonWork1.put("contactId", 0);
        actualJsonWork1.put("dateFrom", "1/1/19");
        actualJsonWork1.put("dateTo", "4/1/19");
        actualJsonWork1.put("function", "testing");
        actualJsonWork1.put("company", "ResYouMe");
        actualJsonWork1.put("country", "US");
        actualJsonWork1.put("plaintext", "");
        JSONObject actualJsonWork2 = new JSONObject();
        actualJsonWork2.put("id", 0);
        actualJsonWork2.put("contactId", 0);
        actualJsonWork2.put("dateFrom", "4/1/19");
        actualJsonWork2.put("dateTo", "7/1/19");
        actualJsonWork2.put("function", "verification");
        actualJsonWork2.put("company", "ResYouMe");
        actualJsonWork2.put("country", "US");
        actualJsonWork2.put("plaintext", "");
        JSONArray actualJsonWorkList = new JSONArray();
        actualJsonWorkList.put(actualJsonWork1);
        actualJsonWorkList.put(actualJsonWork2);

        JSONObject actualJson = new JSONObject();
        actualJson.put("contact", actualJsonContact);
        actualJson.put("educationPhases", actualJsonEduList);
        actualJson.put("workPhases", actualJsonWorkList);
        Resume actual = new Resume(actualJson, true);

        // set up the test resume and get its json array
        Contact expectedContact = new Contact(0, actual.contact.getTimestamp(),"Erick", "Shepherd", "", "123 road st", "12345",
                "Austin", "TX", "US", "erickshepherd@mail.com", "123-456-7890",
                "erick.com", "android design", "", "", null, null);
        EducationPhase expectedEdu = new EducationPhase(0, "8/17/15", "5/25/19", "University of Texas at Austin", "US", "");
        expectedEdu.setContactId(0);
        EducationPhase expectedEdu2 = new EducationPhase(0, "8/17/19", "5/25/23", "University of Texas at Austin", "US", "");
        expectedEdu2.setContactId(0);
        WorkPhase expectedWork = new WorkPhase(0, "1/1/19", "4/1/19", "testing", "ResYouMe", "US", "");
        expectedWork.setContactId(0);
        WorkPhase expectedWork2 = new WorkPhase(0, "4/1/19", "7/1/19", "verification", "ResYouMe", "US", "");
        expectedWork2.setContactId(0);
        List<EducationPhase> expectedEduList = new ArrayList<EducationPhase>();
        expectedEduList.add(expectedEdu);
        expectedEduList.add(expectedEdu2);
        List<WorkPhase> expectedWorkList = new ArrayList<WorkPhase>();
        expectedWorkList.add(expectedWork);
        expectedWorkList.add(expectedWork2);
        Resume expected = new Resume(expectedContact, expectedEduList, expectedWorkList);

        assertEquals("Resumes are not equal", expected, actual);
    }

    @Test
    public void empty_json_to_resume(){
        JSONObject resumeJson = new JSONObject();
        Resume actual = new Resume(resumeJson, true);
        Resume expected = new Resume();
        assertEquals("Resumes are not equal", expected, actual);
    }

    @Test
    public void null_json_to_resume(){
        Resume actual = new Resume(null, true);
        Resume expected = new Resume();
        assertEquals("Resumes are not equal", expected, actual);
    }
}
