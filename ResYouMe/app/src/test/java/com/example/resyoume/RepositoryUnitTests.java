package com.example.resyoume;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.CompanyInfoDao;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.ResumeDao;
import com.example.resyoume.db.ResumeRepository;
import com.example.resyoume.db.ResumeRoomDatabase;
import com.example.resyoume.db.TestEntityFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RepositoryUnitTests {

    @Mock private ResumeRoomDatabase mockDB;

    @Mock private ResumeDao mockResumeDao;

    @Mock private CompanyInfoDao mockCompanyInfoDao;

    @Mock private ResumeRepository repoUnderTest;

    @Mock private LiveData<List<Resume>> mockLiveAllResumes, differentMockLiveAllResumes;
    @Mock private LiveData<Resume> mockLiveResume, differentMockLiveResume;
    @Mock private LiveData<List<CompanyInfo>> mockLiveCompanyInfo, differentMockLiveCompanyInfo;

    @Before
    public void setUp() {
        when(mockDB.resumeDao()).thenReturn(mockResumeDao);
        when(mockDB.companyInfoDao()).thenReturn(mockCompanyInfoDao);

        when(mockResumeDao.getAllResumes()).thenReturn(mockLiveAllResumes);
        when(mockResumeDao.getOldestResume()).thenReturn(mockLiveResume);
        when(mockCompanyInfoDao.getAllCompanyInfo()).thenReturn(mockLiveCompanyInfo);

        repoUnderTest = new ResumeRepository(mockDB);
    }

    @Test
    public void insertResume_shouldInsertEmptyResumeWithDao() {
        Resume testResume = new Resume();

        repoUnderTest.insert(testResume);

        verify(mockResumeDao).insert(testResume);
    }

    @Test
    public void insertResume_shouldInsertFullResumeWithDao() {
        Resume testResume = TestEntityFactory.createTestResume();

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

    @Test
    public void insertCompanyInfo_shouldInsertCompanyInfoWithDao2() {
        CompanyInfo testCompanyInfo = TestEntityFactory.createTestCompanyInfo();

        repoUnderTest.insert(testCompanyInfo);

        verify(mockCompanyInfoDao).insert(testCompanyInfo);
    }

    @Test
    public void getAllResumes_shouldReturnCachedResumes() {
        assertEquals(mockLiveAllResumes, repoUnderTest.getAllResumes());

        // Change data returned from DAO
        when(mockResumeDao.getAllResumes()).thenReturn(differentMockLiveAllResumes);

        // Repo's data should stay the same
        assertEquals(mockLiveAllResumes, repoUnderTest.getAllResumes());
        assertNotEquals(differentMockLiveAllResumes, repoUnderTest.getAllResumes());
    }

    @Test
    public void getSingleResume_shouldReturnCachedResume() {
        assertEquals(mockLiveResume, repoUnderTest.getSingleResume());

        // Change data returned from DAO
        when(mockResumeDao.getOldestResume()).thenReturn(differentMockLiveResume);

        // Repo's data should stay the same
        assertEquals(mockLiveResume, repoUnderTest.getSingleResume());
        assertNotEquals(differentMockLiveResume, repoUnderTest.getSingleResume());
    }

    @Test
    public void getAllCompanyInfo_shouldReturnCachedCompanyInfo() {
        assertEquals(mockLiveCompanyInfo, repoUnderTest.getAllCompanyInfo());

        // Change data returned from DAO
        when(mockCompanyInfoDao.getAllCompanyInfo()).thenReturn(differentMockLiveCompanyInfo);

        // Repo's data should stay the same
        assertEquals(mockLiveCompanyInfo, repoUnderTest.getAllCompanyInfo());
        assertNotEquals(differentMockLiveCompanyInfo, repoUnderTest.getAllCompanyInfo());
    }

}
