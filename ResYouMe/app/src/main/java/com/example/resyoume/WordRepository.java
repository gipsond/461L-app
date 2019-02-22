package com.example.resyoume;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    WordRepository(Application application) {
        WordRoomDatabase database = WordRoomDatabase.getDatabase(application);
        wordDao = database.wordDao();
        allWords = wordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() { return allWords; }

    public void insert(Word word) { new insertAsyncTask(wordDao).execute(word); }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao dao;

        insertAsyncTask(WordDao dao) { this.dao = dao; }

        @Override
        protected Void doInBackground(final Word... params) {
            dao.insert(params[0]);
            return null;
        }
    }
}
