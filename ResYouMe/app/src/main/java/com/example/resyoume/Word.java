package com.example.resyoume;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey //can use (autoGenerate = true) with private int id to make automatically
    @NonNull
    // @ColumnInfo(name = "word") // only need this if name different than variable name
    private String word;

    public Word(@NonNull String word) { this.word = word; }

    public String getWord() { return word; }
}
