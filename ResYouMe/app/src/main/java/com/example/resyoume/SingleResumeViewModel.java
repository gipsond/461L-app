package com.example.resyoume;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.resyoume.db.Resume;
import com.example.resyoume.db.ResumeRepository;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void insert(JSONObject resumeJson) throws JSONException {
        if (resumeJson.has("type")
                && resumeJson.getString("type").equals("resume")
                && resumeJson.has("contact")
                && resumeJson.has("educationPhases")
                && resumeJson.has("workPhases")) {

            Resume resume = new Resume(resumeJson, true);
            resume.contact.setRating(null);
            resume.contact.setNotes(null);
            insert(resume);
        }
    }

    public void update(Resume resume) { repository.update(resume); }

}
