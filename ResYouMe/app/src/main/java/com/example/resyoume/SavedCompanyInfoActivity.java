package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.resyoume.db.CompanyInfo;
import com.example.resyoume.db.CompanyInfo;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedCompanyInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CompanyInfoViewModel companyInfoViewModel;
    private RecyclerView recyclerView;
    private String style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recyclerview);
        registerForContextMenu(recyclerView);
        final CompanyInfoListAdapter adapter = new CompanyInfoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        companyInfoViewModel = ViewModelProviders.of(this).get(CompanyInfoViewModel.class);
        companyInfoViewModel.getAllCompanyInfo().observe(this, adapter::setCompanyInfo);
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
