package com.example.resyoume.db;

import org.json.JSONException;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class ResumeDao {

    /* Main Queries */

    @Transaction
    @Query("SELECT * FROM contacts ORDER BY timestamp ASC")
    public abstract LiveData<List<Resume>> getAllResumes();

    @Transaction
    @Query("SELECT * FROM contacts ORDER BY timestamp ASC LIMIT 1")
    public abstract LiveData<Resume> getOldestResume();

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

    @Transaction
    public void update(Resume resume) {
        System.out.println("DAO updating resume:");
        System.out.println(resume);
        try {
            System.out.println(resume.toJSONObject());
        } catch (JSONException e) {}
        Contact contact = resume.contact;
        int contactId = updateContact(contact);
        updateEducationPhases(contactId, resume.educationPhases);
        updateWorkPhases(contactId, resume.workPhases);
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

    private void updateWorkPhases(int contactId, List<WorkPhase> phases) {
        for (WorkPhase phase : phases) {
            phase.setContactId(contactId);
        }
        updateWorkPhases(phases);
    }

    private void updateEducationPhases(int contactId, List<EducationPhase> phases) {
        for (EducationPhase phase : phases) {
            phase.setContactId(contactId);
        }
        updateEducationPhases(phases);
    }



    @Insert
    abstract long insertContact(Contact contact);

    @Insert
    abstract void insertWorkPhases(List<WorkPhase> phases);

    @Insert
    abstract void insertEducationPhases(List<EducationPhase> educationPhases);

    @Update()
    abstract int updateContact(Contact contact);

    @Update
    abstract void updateWorkPhases(List<WorkPhase> workPhases);

    @Update
    abstract void updateEducationPhases(List<EducationPhase> educationPhases);

}
