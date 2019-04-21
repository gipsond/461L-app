package com.example.resyoume.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class Settings {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String resumeSort;


    public Settings(int id, String resumeSort) {
        this.id = id;
        this.resumeSort = resumeSort;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResumeSort() {
        return resumeSort;
    }

    public void setResumeSort(String resumeSort) {
        this.resumeSort = resumeSort;
    }

}
