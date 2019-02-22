package com.example.resyoume;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao dao;

        PopulateDbAsync(WordRoomDatabase db) {
            dao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            dao.deleteAll();
            Word word = new Word("Hello");
            dao.insert(word);
            word = new Word("World");
            dao.insert(word);
            return null;
        }
    }

    public abstract WordDao wordDao();

    private static volatile WordRoomDatabase INSTANCE;

    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        // TODO: implement if necessary
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        // TODO: implement if necessary
        return null;
    }

    @Override
    public void clearAllTables() {
        // TODO: implement if necessary
    }
}
