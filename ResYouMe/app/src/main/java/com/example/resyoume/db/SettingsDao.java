package com.example.resyoume.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class SettingsDao {

    @Query("SELECT * FROM settings LIMIT 1")
    public abstract LiveData<Settings> getSettings();

    @Insert
    public abstract void insert(Settings settings);

    @Update
    public abstract void update(Settings settings);

}
