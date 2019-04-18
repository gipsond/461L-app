package com.example.resyoume.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class CompanyInfoDao {

    @Transaction
    @Query("SELECT * FROM company_info ORDER BY timestamp DESC")
    public abstract LiveData<List<CompanyInfo>> getAllCompanyInfo();

    @Insert
    public abstract long insert(CompanyInfo companyInfo);

    @Update
    public abstract int update(CompanyInfo companyInfo);

}
