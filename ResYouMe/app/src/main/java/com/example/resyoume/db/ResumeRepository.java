package com.example.resyoume.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ResumeRepository {
    private ResumeDao resumeDao;
    private LiveData<List<Resume>> allResumes;
    private LiveData<Resume> singleResume;

    public ResumeRepository(Application application) {
        ResumeRoomDatabase db = ResumeRoomDatabase.getDatabase(application);
        resumeDao = db.resumeDao();
        allResumes = resumeDao.getAllResumes();
        singleResume = resumeDao.getResume();
    }

    public LiveData<List<Resume>> getAllResumes() { return allResumes; }

    public LiveData<Resume> getSingleResume() { return singleResume; }

    public void insert(Resume resume) {
        new insertAsyncTask(resumeDao).execute(resume);
    }

    private static class insertAsyncTask extends AsyncTask<Resume, Void, Void> {
        private ResumeDao dao;

        insertAsyncTask(ResumeDao dao) { this.dao = dao; }

        @Override
        protected Void doInBackground(final Resume... params) {
            dao.insert(params[0]);
            return null;
        }
    }
}
