package com.example.resyoume;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/*
    Notes from the tutorial:
    ------------------------
    WARNING: Never pass context into ViewModel instances.
    Do not store Activity, Fragment, or View instances or their Context in the ViewModel.
    For example, an Activity can be destroyed and created many times during the lifecycle of a ViewModel
    as the device is rotated. If you store a reference to the Activity in the ViewModel,
    you end up with references that point to the destroyed Activity. This is a memory leak.
    If you need the application context, use AndroidViewModel.

    IMPORTANT: ViewModel is not a replacement for the onSaveInstanceState() method,
    because the ViewModel does not survive a process shutdown.
 */

public class WordViewModel extends AndroidViewModel {
    private WordRepository repository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(Application application) {
        super(application);
        repository = new WordRepository(application);
        allWords = repository.getAllWords();
    }

    LiveData<List<Word>> getAllWords() { return allWords; }

    public void insert(Word word) { repository.insert(word); }
}
