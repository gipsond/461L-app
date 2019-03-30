package com.example.resyoume.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class ResumeDao {

    /* Main Queries */

    @Transaction
    @Query("SELECT * FROM contacts")
    abstract LiveData<List<Resume>> getAllResumes();

    @Transaction
    @Query("SELECT * FROM contacts LIMIT 1")
    abstract LiveData<Resume> getResume();

//    Example Queries from WordDao
//
//    @Query("DELETE FROM word_table")
//    void deleteAll();
//
//    @Query("SELECT * from word_table ORDER BY word ASC")
//    LiveData<List<Word>> getAllWords();

    @Transaction
    public void insert(Resume resume) {
        Contact contact = resume.contact;
        int contactId = (int)insertContact(contact);
        insertEducationPhases(contactId, resume.educationPhases);
        insertWorkPhases(contactId, resume.workPhases);
    }


    /* Helper Methods and Queries */

    private void insertWorkPhases(int contactId, List<WorkPhase> phases) {
        for (WorkPhase phase : phases) {
            phase.setContactId(contactId);
        }
        insertWorkPhases(phases);
    }

    private void insertEducationPhases(int contactId, List<EducationPhase> phases) {
        for (EducationPhase phase : phases) {
            phase.setContactId(contactId);
        }
        insertEducationPhases(phases);
    }

    @Insert
    abstract long insertContact(Contact contact);

    @Insert
    abstract void insertWorkPhases(List<WorkPhase> phases);

    @Insert
    abstract void insertEducationPhases(List<EducationPhase> educationPhases);

}
