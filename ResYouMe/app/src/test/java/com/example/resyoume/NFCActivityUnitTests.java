package com.example.resyoume;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.TextView;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.TestEntityFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class NFCActivityUnitTests {

    @Mock SingleResumeViewModel mockResumeViewModel;
    @Mock CompanyInfoViewModel mockCompanyInfoViewModel;

    private NFCActivity spyActivityUnderTest;

    @Before
    public void setUp() {
        NFCActivity realActivityUnderTest = new NFCActivity();
        spyActivityUnderTest = spy(realActivityUnderTest); // Partial mock
        when(spyActivityUnderTest.getResumeViewModel()).thenReturn(mockResumeViewModel);
        when(spyActivityUnderTest.getCompanyInfoViewModel()).thenReturn(mockCompanyInfoViewModel);
    }

    @Test
    public void saveResponseToDB_shouldInsertNewResume_whenResponseIsResume() {
        try {
            JSONObject testResumeJSON = TestEntityFactory.createTestResume().toJSONObject();
            NFCDataSingleton.getInstance().setResponse(testResumeJSON.toString());

            spyActivityUnderTest.saveResponseToDB(null);
            verify(mockResumeViewModel).insert(any(JSONObject.class));

        } catch (JSONException e) {
            fail("Encountered JSON Exception");
        }
    }

    @Test
    public void saveResponseToDB_shouldInsertNewCompanyInfo_whenResponseIsCompanyInfo() {
        try {
            JSONObject testCompanyInfoJSON = TestEntityFactory.createTestCompanyInfo().toJSONObject();
            NFCDataSingleton.getInstance().setResponse(testCompanyInfoJSON.toString());

            spyActivityUnderTest.saveResponseToDB(null);
            verify(mockCompanyInfoViewModel).insert(any(JSONObject.class));
        } catch (JSONException e) {
            fail("Encountered JSON Exception");
        }
    }

}
