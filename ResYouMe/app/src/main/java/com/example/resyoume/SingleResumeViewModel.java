package com.example.resyoume;

import android.app.Application;

import com.example.resyoume.db.Resume;
import com.example.resyoume.db.ResumeRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SingleResumeViewModel extends AndroidViewModel {
    private ResumeRepository repository;
    private LiveData<Resume> resume;

    public SingleResumeViewModel(Application application) {
        super(application);
        repository = new ResumeRepository(application);
        resume = repository.getSingleResume();
    }

    public LiveData<Resume> getResume() { return resume; }

    public void insert(Resume resume) { repository.insert(resume); }

}
