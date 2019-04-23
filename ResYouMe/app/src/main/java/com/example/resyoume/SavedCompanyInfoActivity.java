package com.example.resyoume;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.Resume;

import org.json.JSONException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SavedCompanyInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Comparator<CompanyInfo>{

    private CompanyInfoViewModel companyInfoViewModel;
    private RecyclerView recyclerView;
    private String sortCompanySelection = "Name";
    private String style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_database);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recyclerview);
        registerForContextMenu(recyclerView);
        final CompanyInfoListAdapter adapter = new CompanyInfoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
        companyInfoViewModel.getAllCompanyInfo().observe(this, adapter::setCompanyInfo);

        Spinner sortCompanySpinner = (Spinner) findViewById(R.id.style_sortCompanySpinner);
        sortCompanySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortCompanySelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> sortStyle_adapter = ArrayAdapter.createFromResource(this,R.array.sortCompanyStyles, android.R.layout.simple_spinner_item);
        sortStyle_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortCompanySpinner.setAdapter(sortStyle_adapter);

        Button sortButton = findViewById(R.id.sortCompanyInfo);
        sortButton.setOnClickListener(view -> sort());
    }

    @TargetApi(Build.VERSION_CODES.N)
    public void sort() {
        List<CompanyInfo> companies = companyInfoViewModel.getAllCompanyInfo().getValue();
        if (companies != null && companies.size() > 1) {
            companies.sort(this);
        }
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
    }

    public int compare(CompanyInfo c1, CompanyInfo c2){
        if(sortCompanySelection.equals("Name") && (c1.getCompanyName() != null && c2.getCompanyName() != null))
            return c1.getCompanyName().compareTo(c2.getCompanyName());
        else if(sortCompanySelection.equals("Most Recent") && (c1.getTimestamp() != null && c2.getTimestamp() != null))
            return c1.getTimestamp().compareTo(c2.getTimestamp())*-1;
        else
            return 0;
    }

    /**
     * Called on selecting an item from the context menu
     * @see CompanyInfoListAdapter for context menu creation
     * @param item is the context menu item selected
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = ((CompanyInfoListAdapter)recyclerView.getAdapter()).getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.ctx_menu_send_by_nfc: {
                CompanyInfoListAdapter.CompanyInfoViewHolder civh = (CompanyInfoListAdapter.CompanyInfoViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                CompanyInfo companyInfo = civh.getCompanyInfo();
                Intent nfc_intent = new Intent(this, NFCActivity.class);
                try {
                    nfc_intent.putExtra("companyInfoJSON", companyInfo.toJSONObject().toString());
                }
                catch (JSONException e) {}
                startActivity(nfc_intent);
                break;
            }
            case R.id.ctx_menu_display:{
                CompanyInfoListAdapter.CompanyInfoViewHolder civh = (CompanyInfoListAdapter.CompanyInfoViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                CompanyInfo companyInfo = civh.getCompanyInfo();
                Intent display_intent = new Intent(this, DisplayCompanyInfo.class);
                try {
                    display_intent.putExtra("companyInfoJSON", companyInfo.toJSONObject().toString());
                }
                catch (JSONException e) {}
                startActivity(display_intent);
                break;
            }
            case R.id.ctx_edit_NR:{
                CompanyInfoListAdapter.CompanyInfoViewHolder civh = (CompanyInfoListAdapter.CompanyInfoViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                CompanyInfo companyInfo = civh.getCompanyInfo();
                Intent nr_intent = new Intent(this, SetRatingAndNoteCompany.class);
                try {
                    nr_intent.putExtra("companyInfoJSON", companyInfo.toJSONObject().toString());
                }
                catch (JSONException e) {}
                startActivity(nr_intent);
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    // style selector function
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String name = parent.getItemAtPosition(pos).toString();
        this.style = name;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
