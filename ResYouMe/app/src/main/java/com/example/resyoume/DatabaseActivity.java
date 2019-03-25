package com.example.resyoume;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.resyoume.db.Resume;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DatabaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ResumeViewModel resumeViewModel;
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
        final ResumeListAdapter adapter = new ResumeListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resumeViewModel = ViewModelProviders.of(this).get(ResumeViewModel.class);
        resumeViewModel.getAllResumes().observe(this, adapter::setResumes);

        Spinner spinner = (Spinner) findViewById(R.id.style_spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> style_adapter = ArrayAdapter.createFromResource(this, R.array.styles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        style_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(style_adapter);
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
                    nfc_intent.putExtra("resumeJSON", resume.toJSONArray().toString());
                }
                catch (JSONException e) {}
                startActivity(nfc_intent);
                break;
            }
            case R.id.ctx_menu_display:{
                ResumeListAdapter.ResumeViewHolder rvh = (ResumeListAdapter.ResumeViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
                Resume resume = rvh.getResume();
                Intent display_intent = new Intent(this, DisplayActivity.class);
                switch(style){
                    case "Style 1":{
                        display_intent = new Intent(this, DisplayActivity.class);
                        break;
                    }
                    case "Style 2":{
                        display_intent = new Intent(this, DisplayActivityCard.class);
                        break;
                    }
                    case "Style 3":{
                        display_intent = new Intent(this, DisplayActivity.class);
                        break;
                    }
                    case "Style 4":{
                        display_intent = new Intent(this, DisplayActivity.class);
                        break;
                    }
                    case "Style 5":{
                        display_intent = new Intent(this, DisplayActivity.class);
                        break;
                    }
                    case "Style 6":{
                        display_intent = new Intent(this, DisplayActivity.class);
                        break;
                    }
                }
                try {
                    display_intent.putExtra("resumeJSON", resume.toJSONArray().toString());
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
