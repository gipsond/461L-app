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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class NFCActivityUnitTests {

    @Mock TextView mockResponseView;
    @Mock SingleResumeViewModel mockResumeViewModel;
    @Mock CompanyInfoViewModel mockCompanyInfoViewModel;

    private NFCActivity spyActivityUnderTest;

    @Before
    public void setUp() {
        NFCActivity realActivityUnderTest = new NFCActivity();
        spyActivityUnderTest = spy(realActivityUnderTest); // Partial mock
        when(spyActivityUnderTest.getResponse()).thenReturn(mockResponseView);
        when(spyActivityUnderTest.getResumeViewModel()).thenReturn(mockResumeViewModel);
        when(spyActivityUnderTest.getCompanyInfoViewModel()).thenReturn(mockCompanyInfoViewModel);
    }

    @Test
    public void saveResponseToDB_shouldInsertNewResume_whenResponseIsResume() {
        String testResumeJSONString = null;
        try {
            testResumeJSONString = TestEntityFactory.createTestResume().toJSONObject().toString();
        } catch (JSONException e) {}
        when(mockResponseView.getText()).thenReturn(testResumeJSONString);

        spyActivityUnderTest.saveResponseToDB(null);
        verify(mockResumeViewModel).insert(any(Resume.class));
    }

    @Test
    public void saveResponseToDB_shouldInsertNewCompanyInfo_whenResponseIsCompanyInfo() {
        String testCompanyInfoJSONString = null;
        try {
            testCompanyInfoJSONString = TestEntityFactory.createTestCompanyInfo().toJSONObject().toString();
        } catch (JSONException e) {}
        when(mockResponseView.getText()).thenReturn(testCompanyInfoJSONString);

        spyActivityUnderTest.saveResponseToDB(null);
        verify(mockCompanyInfoViewModel).insert(any(CompanyInfo.class));
    }

}
