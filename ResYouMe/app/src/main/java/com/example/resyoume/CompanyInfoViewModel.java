package com.example.resyoume;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.ResumeRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    public void insert(JSONObject companyInfoJson) throws JSONException {
        if (companyInfoJson.has("type")
                && companyInfoJson.getString("type").equals("companyInfo")
                && companyInfoJson.has("name")) {

            CompanyInfo companyInfo = new CompanyInfo(companyInfoJson, true);
            companyInfo.setRating(null);
            companyInfo.setNotes(null);
            insert(companyInfo);
        }
    }

    public void update(CompanyInfo companyInfo) { repository.update(companyInfo); }

}
