package com.example.resyoume.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ResumeRepository {
    private ResumeDao resumeDao;
    private CompanyInfoDao companyInfoDao;
    private SettingsDao settingsDao;

    private LiveData<List<Resume>> allResumes;
    private LiveData<Resume> singleResume;

    private LiveData<List<CompanyInfo>> allCompanyInfo;

    private LiveData<Settings> settings;

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
        settingsDao = db.settingsDao();
        allResumes = resumeDao.getAllResumes();
        singleResume = resumeDao.getOldestResume();
        allCompanyInfo = companyInfoDao.getAllCompanyInfo();
        settings = settingsDao.getSettings();
    }

    public LiveData<List<Resume>> getAllResumes() { return allResumes; }

    public LiveData<Resume> getSingleResume() { return singleResume; }

    public LiveData<List<CompanyInfo>> getAllCompanyInfo() { return allCompanyInfo; }

    public LiveData<Settings> getSettings() { return settings; }

    public void insert(Resume resume) {
        new ResumeQueryAsyncTask(resumeDao, Query.INSERT).execute(resume);
    }

    public void insert(CompanyInfo companyInfo) {
        new CompanyInfoQueryAsyncTask(companyInfoDao, Query.INSERT).execute(companyInfo);
    }

    public void update(Resume resume) {
        System.out.println("Repository updating resume");
        new ResumeQueryAsyncTask(resumeDao, Query.UPDATE).execute(resume);
    }

    public void update(CompanyInfo companyInfo) {
        new CompanyInfoQueryAsyncTask(companyInfoDao, Query.UPDATE).execute(companyInfo);
    }

    public void update(Settings settings) {
        new SettingsUpdateAsyncTask(settingsDao).execute(settings);
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

    private static class CompanyInfoQueryAsyncTask extends AsyncTask<CompanyInfo, Void, Void> {
        private CompanyInfoDao dao;
        private Query query;

        CompanyInfoQueryAsyncTask(CompanyInfoDao dao, Query query) {
            this.dao = dao;
            this.query = query;
        }

        @Override
        protected Void doInBackground(final CompanyInfo... params) {
            switch (query) {
                case INSERT: dao.insert(params[0]); break;
                case UPDATE: dao.update(params[0]); break;
                default:
                    throw new IllegalStateException("CompanyInfoQueryAsyncTask has invalid Query: " + query);
            }
            return null;
        }
    }

    private static class SettingsUpdateAsyncTask extends AsyncTask<Settings, Void, Void> {
        private SettingsDao dao;

        SettingsUpdateAsyncTask(SettingsDao dao) { this.dao = dao; }

        @Override
        protected Void doInBackground(final Settings... params) {
            dao.update(params[0]);
            return null;
        }
    }
}
