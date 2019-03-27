package com.example.resyoume.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class, WorkPhase.class, EducationPhase.class, CompanyInfo.class}, version = 1, exportSchema = false)
public abstract class ResumeRoomDatabase extends RoomDatabase {

    /* Singleton Interface */

    public abstract ResumeDao resumeDao();
    public abstract CompanyInfoDao companyInfoDao();

    private static volatile ResumeRoomDatabase INSTANCE;

    static ResumeRoomDatabase getDatabase(final Context context) {
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
            Contact contact =
                new Contact(
                    0,
                    "John", "Doe",
                    "Mr.",
                    "1600 Amphitheatre Parkway", "94043", "Mountain View", "CA", "United States",
                    "john.doe@example.com", "987 555 1234", "linkedin.com/john-example-doe",
                    "Being average, watching grass grow, watching paint dry",
                    "101 Ways to Blend In",
                    "This user was manually input and thus has no accompanying plaintext."
                );

            List<WorkPhase> workPhases = new ArrayList<>();
            workPhases.add(
                new WorkPhase(
                    0,
                    "2018-01", "2018-07",
                    "Groundskeeper",
                    "Google",
                    "United States",
                    "This phase was manually input and thus has no accompanying plaintext."
                )
            );

            workPhases.add(
                new WorkPhase(
                    0,
                    "2017-10", "2017-12",
                    "Painter",
                    "Microsoft",
                    "United States",
                    "This phase (2) was manually input and thus has no accompanying plaintext."
                )
            );

            List<EducationPhase> educationPhases = new ArrayList<>();
            educationPhases.add(
                new EducationPhase(
                    0,
                    "2013-08", "2017-06",
                    "University of South Dakota",
                    "United States",
                    "This phase was manually input and thus has no accompanying plaintext."
                )
            );

            resumeDao.insert(new Resume(contact, educationPhases, workPhases));

            return null;
        }
    }

}
