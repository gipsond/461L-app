package com.example.resyoume.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
abstract class ResumeDao {

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
    void insert(Resume resume) {
        Contact contact = resume.contact;
        insertContact(contact);
        insertEducationPhases(contact, resume.educationPhases);
        insertWorkPhases(contact, resume.workPhases);
    }


    /* Helper Methods and Queries */

    private void insertWorkPhases(Contact contact, List<WorkPhase> phases) {
        int contactId = contact.getId();
        for (WorkPhase phase : phases) {
            phase.setContactId(contactId);
        }
        insertWorkPhases(phases);
    }

    private void insertEducationPhases(Contact contact, List<EducationPhase> phases) {
        int contactId = contact.getId();
        for (EducationPhase phase : phases) {
            phase.setContactId(contactId);
        }
        insertEducationPhases(phases);
    }

    @Insert
    abstract void insertContact(Contact contact);

    @Insert
    abstract void insertWorkPhases(List<WorkPhase> phases);

    @Insert
    abstract void insertEducationPhases(List<EducationPhase> educationPhases);

}
