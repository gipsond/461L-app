package com.example.resyoume;

import android.app.Application;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.ResumeRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CompanyInfoViewModel extends AndroidViewModel {
    private ResumeRepository repository;
    private LiveData<List<CompanyInfo>> allCompanyInfo;

    public CompanyInfoViewModel(Application application) {
        super(application);
        repository = new ResumeRepository(application);
        allCompanyInfo = repository.getAllCompanyInfo();
    }

    public LiveData<List<CompanyInfo>> getAllCompanyInfo() { return allCompanyInfo; }

    public void insert(CompanyInfo companyInfo) { repository.insert(companyInfo); }

    public void update(CompanyInfo companyInfo) { repository.update(companyInfo);}
}
