package com.example.resyoume.db;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        int contactId = contact.getId();
        updateContact(contact);

        System.out.println(contactId);
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
        setContactIds(contactId, phases);
        updateWorkPhases(getOldPhases(phases));
        insertWorkPhases(getNewPhases(phases));
    }

    private void updateEducationPhases(int contactId, List<EducationPhase> phases) {
        setContactIds(contactId, phases);
        updateEducationPhases(getOldPhases(phases));
        insertEducationPhases(getNewPhases(phases));
    }

    private void setContactIds(int contactId, List<? extends Phase> phases) {
        for (Phase phase : phases) {
            phase.setContactId(contactId);
        }
    }

    private <P extends Phase> List<P> getOldPhases(List<P> phases) {
        return getOldOrNewPhases(true, phases);
    }

    private <P extends Phase> List<P> getNewPhases(List<P> phases) {
        return getOldOrNewPhases(false, phases);
    }

    private <P extends Phase> List<P> getOldOrNewPhases(boolean getOldPhases, List<P> phases) {
        List<P> oldPhases = new ArrayList<>();
        for (P phase : phases) {
            if (phase.hasExistingId() == getOldPhases) {
                oldPhases.add(phase);
            }
        }
        return oldPhases;
    }

    @Insert
    abstract long insertContact(Contact contact);

    @Insert
    abstract void insertWorkPhases(List<WorkPhase> phases);

    @Insert
    abstract void insertEducationPhases(List<EducationPhase> educationPhases);

    @Update
    abstract int updateContact(Contact contact);

    @Update
    abstract void updateWorkPhases(List<WorkPhase> workPhases);

    @Update
    abstract void updateEducationPhases(List<EducationPhase> educationPhases);

}
