package com.example.resyoume;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.resyoume.db.ResumeRepository;
import com.example.resyoume.db.Settings;

public class SettingsViewModel extends AndroidViewModel {

    private ResumeRepository repository;
    private LiveData<Settings> settings;

    public SettingsViewModel(Application application) {
        super(application);
        repository = new ResumeRepository(application);
        settings = repository.getSettings();
    }

    public LiveData<Settings> getSettings() { return settings; }

    public void update(Settings settings) { repository.update(settings); }

}
