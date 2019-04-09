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
        singleResume = resumeDao.getOldestResume();
        allCompanyInfo = companyInfoDao.getAllCompanyInfo();
    }

    public LiveData<List<Resume>> getAllResumes() { return allResumes; }

    public LiveData<Resume> getSingleResume() { return singleResume; }

    public LiveData<List<CompanyInfo>> getAllCompanyInfo() { return allCompanyInfo; }

    public void insert(Resume resume) {
        new ResumeQueryAsyncTask(resumeDao, Query.INSERT).execute(resume);
    }

    public void insert(CompanyInfo companyInfo) {
        new CompanyInfoInsertAsyncTask(companyInfoDao).execute(companyInfo);
    }

    public void update(Resume resume) {
        System.out.println("Repository updating resume");
        new ResumeQueryAsyncTask(resumeDao, Query.UPDATE).execute(resume);
    }

    private enum Query {
        INSERT,
        UPDATE
    }

    private static class ResumeQueryAsyncTask extends AsyncTask<Resume, Void, Void> {
        private ResumeDao dao;
        private Query query;

        ResumeQueryAsyncTask(ResumeDao dao, Query query) {
            this.dao = dao;
            this.query = query;
        }

        @Override
        protected Void doInBackground(final Resume... params) {
            switch (query) {
                case INSERT: dao.insert(params[0]); break;
                case UPDATE: dao.update(params[0]); break;
                default:
                    throw new IllegalStateException("ResumeQueryAsyncTask has invalid Query: " + query);
            }
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
