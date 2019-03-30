package com.example.resyoume.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ResumeRepository {
    private ResumeDao resumeDao;
    private CompanyInfoDao companyInfoDao;

    private LiveData<List<Resume>> allResumes;
    private LiveData<Resume> singleResume;

    private LiveData<List<CompanyInfo>> allCompanyInfo;

    public ResumeRepository(Application application) {
        this(ResumeRoomDatabase.getDatabase(application));
    }

    /**
     * For testing purposes. Use ResumeRepository(Application).
     * @param db from ResumeRoomDatabase.getDatabase(Application) unless mocking
     */
    public ResumeRepository(ResumeRoomDatabase db) {
        resumeDao = db.resumeDao();
        companyInfoDao = db.companyInfoDao();
        allResumes = resumeDao.getAllResumes();
        singleResume = resumeDao.getResume();
        allCompanyInfo = companyInfoDao.getAllCompanyInfo();
    }

    public LiveData<List<Resume>> getAllResumes() { return allResumes; }

    public LiveData<Resume> getSingleResume() { return singleResume; }

    public LiveData<List<CompanyInfo>> getAllCompanyInfo() { return allCompanyInfo; }

    public void insert(Resume resume) {
        new ResumeInsertAsyncTask(resumeDao).execute(resume);
    }

    public void insert(CompanyInfo companyInfo) {
        new CompanyInfoInsertAsyncTask(companyInfoDao).execute(companyInfo);
    }

    private static class ResumeInsertAsyncTask extends AsyncTask<Resume, Void, Void> {
        private ResumeDao dao;

        ResumeInsertAsyncTask(ResumeDao dao) { this.dao = dao; }

        @Override
        protected Void doInBackground(final Resume... params) {
            dao.insert(params[0]);
            return null;
        }
    }

    private static class CompanyInfoInsertAsyncTask extends AsyncTask<CompanyInfo, Void, Void> {
        private CompanyInfoDao dao;

        CompanyInfoInsertAsyncTask(CompanyInfoDao dao) { this.dao = dao; }

        @Override
        protected Void doInBackground(final CompanyInfo... params) {
            dao.insert(params[0]);
            return null;
        }
    }
}
