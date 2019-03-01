package com.example.resyoume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.resyoume.db.Resume;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ResumeListAdapter extends RecyclerView.Adapter<ResumeListAdapter.ResumeViewHolder> {

    class ResumeViewHolder extends RecyclerView.ViewHolder {
        private final TextView resumeItemView;

        private ResumeViewHolder(View itemView) {
            super(itemView);
            resumeItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater inflater;
    private List<Resume> resumes; // Cached copy of words

    ResumeListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ResumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ResumeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResumeViewHolder holder, int position) {
        if (resumes != null) {
            Resume current = resumes.get(position);
            holder.resumeItemView.setText(current.contact.getFirstName() + " " + current.contact.getLastName() + "\n");
        } else {
            // Covers the case of data not being ready yet.
            holder.resumeItemView.setText("Resume loading...");
        }
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // resumes has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (resumes != null)
            return resumes.size();
        else return 0;
    }
}