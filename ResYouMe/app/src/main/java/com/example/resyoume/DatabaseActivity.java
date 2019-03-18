package com.example.resyoume;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.resyoume.db.Resume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DatabaseActivity extends AppCompatActivity {

    private ResumeViewModel resumeViewModel;
    private RecyclerView recyclerView;

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
                // TODO: Create Intent to send resume as String to NFC Activity (should be easy as pie)
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

}
