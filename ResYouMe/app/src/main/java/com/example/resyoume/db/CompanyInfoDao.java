package com.example.resyoume.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
abstract class CompanyInfoDao {

    @Transaction
    @Query("SELECT * FROM company_info")
    abstract LiveData<List<CompanyInfo>> getAllCompanyInfo();

    @Insert
    abstract long insert(CompanyInfo companyInfo);

}
