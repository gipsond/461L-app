package com.example.resyoume;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.WorkPhase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSONUnitTests {

    @Test
    public void contact_to_json() throws JSONException {
        Contact contact = new Contact(1, "Erick", "Shepherd", "", "123 road st", "12345",
                    "Austin", "TX", "US", "erickshepherd@mail.com", "123-456-7890",
                "erick.com", "android design", "", "");
        JSONObject contactJson = contact.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put("id", 1);
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
        contactJson.put("id", 1);
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
        Contact actual = new Contact(contactJson);
        Contact expected = new Contact(1, "Erick", "Shepherd", "", "123 road st", "12345",
                "Austin", "TX", "US", "erickshepherd@mail.com", "123-456-7890",
                "erick.com", "android design", "", "");
        assertEquals("Contacts are not equal", expected, actual);
    }

    @Test
    public void education_to_json() throws JSONException {
        EducationPhase actualEdu = new EducationPhase(1, "8/17/15", "5/25/19", "University of Texas at Austin", "US", "");
        actualEdu.setContactId(2);
        JSONObject actual = actualEdu.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put("id", 1);
        expected.put("contactId", 2);
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
        actualJson.put("id", 1);
        actualJson.put("contactId", 2);
        actualJson.put("dateFrom", "8/17/15");
        actualJson.put("dateTo", "5/25/19");
        actualJson.put("schoolName", "University of Texas at Austin");
        actualJson.put("country", "US");
        actualJson.put("plaintext", "");
        EducationPhase actual = new EducationPhase(actualJson);
        EducationPhase expected = new EducationPhase(1, "8/17/15", "5/25/19", "University of Texas at Austin", "US", "");
        expected.setContactId(2);
        assertEquals("EducationPhases are not equal", expected, actual);
    }

    @Test
    public void work_to_json() throws JSONException {
        WorkPhase actualWork = new WorkPhase(1, "1/1/19", "4/1/19", "testing", "ResYouMe", "US", "");
        actualWork.setContactId(2);
        JSONObject actual = actualWork.toJSONObject();
        JSONObject expected = new JSONObject();
        expected.put("id", 1);
        expected.put("contactId", 2);
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
        actualJson.put("id", 1);
        actualJson.put("contactId", 2);
        actualJson.put("dateFrom", "1/1/19");
        actualJson.put("dateTo", "4/1/19");
        actualJson.put("function", "testing");
        actualJson.put("company", "ResYouMe");
        actualJson.put("country", "US");
        actualJson.put("plaintext", "");
        WorkPhase actual = new WorkPhase(actualJson);
        WorkPhase expected = new WorkPhase(1, "1/1/19", "4/1/19", "testing", "ResYouMe", "US", "");
        expected.setContactId(2);
        assertEquals("WorkPhases are not equal", expected, actual);
    }
}
