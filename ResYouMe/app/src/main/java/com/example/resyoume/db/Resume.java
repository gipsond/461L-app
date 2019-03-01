package com.example.resyoume.db;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class Resume {
    @Embedded
    public Contact contact;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<EducationPhase> educationPhases;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<WorkPhase> workPhases;

    public Resume(Contact contact, List<EducationPhase> educationPhases, List<WorkPhase> workPhases) {
        this.contact = contact;
        this.educationPhases = educationPhases;
        this.workPhases = workPhases;
    }

}
