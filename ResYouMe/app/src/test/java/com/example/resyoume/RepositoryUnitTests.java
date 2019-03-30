package com.example.resyoume;

import android.app.Application;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.CompanyInfoDao;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.ResumeDao;
import com.example.resyoume.db.ResumeRepository;
import com.example.resyoume.db.ResumeRoomDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.Date;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryUnitTests {

    private final static long INSERT_TIMEOUT = 3000;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private Application mockApp;
    private ResumeRoomDatabase mockDB;
    private ResumeDao mockResumeDao;
    private CompanyInfoDao mockCompanyInfoDao;

    private ResumeRepository repoUnderTest;

    @Before
    public void setUp() {
        mockApp            = mock(Application.class);
        mockDB             = mock(ResumeRoomDatabase.class);
        mockResumeDao      = mock(ResumeDao.class);
        mockCompanyInfoDao = mock(CompanyInfoDao.class);

        when(mockDB.resumeDao()).thenReturn(mockResumeDao);
        when(mockDB.companyInfoDao()).thenReturn(mockCompanyInfoDao);

        repoUnderTest = new ResumeRepository(mockDB);
    }

    @Test
    public void insertResume_shouldInsertResumeWithDao() {
        Resume testResume = new Resume();

        repoUnderTest.insert(testResume);

        verify(mockResumeDao).insert(testResume);
    }

    @Test
    public void insertCompanyInfo_shouldInsertCompanyInfoWithDao() {
        Date mockDate = mock(Date.class);
        CompanyInfo testCompanyInfo = new CompanyInfo(
                0,
                mockDate,
                "testCompany",
                "testLocation",
                "testTwitter",
                "testWebsite",
                "testBio",
                "testLinkedIn",
                "testAdditionalInfo"
        );

        repoUnderTest.insert(testCompanyInfo);

        verify(mockCompanyInfoDao).insert(testCompanyInfo);
    }

}
