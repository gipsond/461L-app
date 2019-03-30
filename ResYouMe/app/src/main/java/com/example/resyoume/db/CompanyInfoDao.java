package com.example.resyoume.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class CompanyInfoDao {

    @Transaction
    @Query("SELECT * FROM company_info")
    public abstract LiveData<List<CompanyInfo>> getAllCompanyInfo();

    @Insert
    public abstract long insert(CompanyInfo companyInfo);

}
