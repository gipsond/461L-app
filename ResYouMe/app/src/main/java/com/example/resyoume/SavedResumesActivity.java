package com.example.resyoume;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;

import com.example.resyoume.db.Resume;
import com.example.resyoume.db.Settings;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;

public class SavedResumesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Comparator<Resume>{

    private ResumeViewModel resumeViewModel;
    private SettingsViewModel settingsViewModel;
    private RecyclerView recyclerView;
    private ResumeListAdapter adapter;
    private String style;
    private String sortSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.recyclerView = findViewById(R.id.recyclerview);
        registerForContextMenu(recyclerView);
        adapter = new ResumeListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resumeViewModel = ViewModelProviders.of(this).get(ResumeViewModel.class);
        resumeViewModel.getAllResumes().observe(this, adapter::setResumes);

        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        Spinner spinner = (Spinner) findViewById(R.id.style_spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> style_adapter = ArrayAdapter.createFromResource(this, R.array.styles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        style_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(style_adapter);

        Spinner sortSpinner = (Spinner) findViewById(R.id.style_sortSpinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortSelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> sortStyle_adapter = ArrayAdapter.createFromResource(this,R.array.sortStyles, android.R.layout.simple_spinner_item);
        sortStyle_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortStyle_adapter);

        // When Settings are retrieved, set the spinner to them and sort by that.
        settingsViewModel.getSettings().observe(this, settings -> {
            if (settings != null) {
                String resumeSort = settings.getResumeSort();
                if (resumeSort != null) {
                    int spinnerPosition = sortStyle_adapter.getPosition(resumeSort);
                    sortSpinner.setSelection(spinnerPosition);
                    sortArrayNoUpdate();
                }
            }
        });


        Button sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(view -> sortArray());
    }

    public void sortArray() {
        sortArrayNoUpdate();

        Settings settings = settingsViewModel.getSettings().getValue();
        settings.setResumeSort(sortSelection);
        settingsViewModel.update(settings);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void sortArrayNoUpdate() {
        List<Resume> resumes = resumeViewModel.getAllResumes().getValue();
        if (resumes != null) {
            resumes.sort(this);
        }
    }

    public int compare(Resume r1, Resume r2) {
        if(sortSelection.equals("Last Name")){
            return r1.contact.getLastName().compareTo(r2.contact.getLastName());
        }
        else if(sortSelection.equals("Stars")){
            if(r1.contact.getRating() != null && r2.contact.getRating() != null)
                return (r1.contact.getRating() - r2.contact.getRating())*-1;
            else
                return 0;
        }
        else{
            return r1.contact.getTimestamp().compareTo(r2.contact.getTimestamp())*-1;
        }
    }

    /**
     * Called on selecting an item from the context menu
     * @see ResumeListAdapter for context menu creation
     * @param item is the context menu item selected
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = ((ResumeListAdapter)recyclerView.getAdapter()).getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.ctx_menu_send_by_nfc: {
                ResumeListAdapter.ResumeViewHolder rvh = (ResumeListAdapter.ResumeViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                Resume resume = rvh.getResume();
                Intent nfc_intent = new Intent(this, NFCActivity.class);
                try {
                    nfc_intent.putExtra("resumeJSON", resume.toJSONObject().toString());
                }
                catch (JSONException e) {}
                startActivity(nfc_intent);
                break;
            }
            case R.id.ctx_menu_display:{
                ResumeListAdapter.ResumeViewHolder rvh = (ResumeListAdapter.ResumeViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                Resume resume = rvh.getResume();
                Intent display_intent = new Intent(this, DisplayBasic.class);
                switch(style){
                    case "Basic":{
                        display_intent = new Intent(this, DisplayBasic.class);
                        break;
                    }
                    case "Card":{
                        display_intent = new Intent(this, DisplayActivityCard.class);
                        break;
                    }
                    case "McCombs":{
                        display_intent = new Intent(this, DisplayMccombs.class);
                        break;
                    }
                    case "CLA":{
                        display_intent = new Intent(this, DisplayCLA.class);
                        break;
                    }
                    case "ECAC":{
                        display_intent = new Intent(this, DisplayECAC.class);
                        break;
                    }
                    case "Functional":{
                        display_intent = new Intent(this, DisplayFunctional.class);
                        break;
                    }
                }
                try {
                    display_intent.putExtra("resumeJSON", resume.toJSONObject().toString());
                }
                catch (JSONException e) {}
                startActivity(display_intent);
                break;
            }
            case R.id.ctx_edit_NR:{
                ResumeListAdapter.ResumeViewHolder rvh = (ResumeListAdapter.ResumeViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                Resume resume = rvh.getResume();
                Intent nr_intent = new Intent(this, SetRatingAndNote.class);
                try {
                    nr_intent.putExtra("resumeJSON", resume.toJSONObject().toString());
                }
                catch (JSONException e) {}
                startActivity(nr_intent);
                break;
            }
            case R.id.ctx_edit_resume:{
                ResumeListAdapter.ResumeViewHolder rvh = (ResumeListAdapter.ResumeViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                Resume resume = rvh.getResume();
                Intent edit_intent = new Intent(this, ResumeEditActivity.class);
                try {
                    edit_intent.putExtra("resumeJSON", resume.toJSONObject().toString());
                }
                catch (JSONException e) {}
                startActivity(edit_intent);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search by full name...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
