package com.example.resyoume.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class, WorkPhase.class, EducationPhase.class, CompanyInfo.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ResumeRoomDatabase extends RoomDatabase {

    /* Singleton Interface */

    public abstract ResumeDao resumeDao();
    public abstract CompanyInfoDao companyInfoDao();

    private static volatile ResumeRoomDatabase INSTANCE;

    public static ResumeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ResumeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ResumeRoomDatabase.class, "resume_database")
                            .addCallback(initializeCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /* Initialization Callback */

    private static RoomDatabase.Callback initializeCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new ResumeRoomDatabase.InitializeDbAsync(INSTANCE).execute();
                }
            };

    private static class InitializeDbAsync extends AsyncTask<Void, Void, Void> {

        private final ResumeDao resumeDao;
        private final CompanyInfoDao companyInfoDao;

        InitializeDbAsync(ResumeRoomDatabase db) {
            resumeDao = db.resumeDao();
            companyInfoDao = db.companyInfoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            resumeDao.insert(TestEntityFactory.createTestResume());
            resumeDao.insert(TestEntityFactory.createTestResumeLongName());
            companyInfoDao.insert(TestEntityFactory.createTestCompanyInfo());

            return null;
        }
    }

}
