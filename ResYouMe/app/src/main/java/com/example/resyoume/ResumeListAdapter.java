package com.example.resyoume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.EducationPhase;
import com.example.resyoume.db.Resume;
import com.example.resyoume.db.WorkPhase;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ResumeListAdapter extends RecyclerView.Adapter<ResumeListAdapter.ResumeViewHolder> {

    class ResumeViewHolder extends RecyclerView.ViewHolder {
        private final TextView resumeHeader;
        private final TextView addressView;
        private final TextView addressView2;
        private final TextView emailView;
        private final TextView phoneView;
        private final TextView websiteView;
        private final TextView interestsView;
        private final TextView publicationsView;
        private final TextView educationPhasesView;
        private final TextView workPhasesView;

        private ResumeViewHolder(View itemView) {
            super(itemView);
            resumeHeader = itemView.findViewById(R.id.resumeHeader);
            addressView = itemView.findViewById(R.id.addressView);
            addressView2 = itemView.findViewById(R.id.addressView2);
            emailView = itemView.findViewById(R.id.emailView);
            phoneView = itemView.findViewById(R.id.phoneView);
            websiteView = itemView.findViewById(R.id.websiteView);
            interestsView = itemView.findViewById(R.id.interestsView);
            publicationsView = itemView.findViewById(R.id.publicationsView);
            educationPhasesView = itemView.findViewById(R.id.educationPhasesView);
            workPhasesView = itemView.findViewById(R.id.workPhasesView);
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
            Contact contact = current.contact;
            holder.resumeHeader.setText(contact.getTitle() + " " + contact.getFirstName() + " " + contact.getLastName());
            holder.addressView.setText(contact.getAddress());
            holder.addressView2.setText(contact.getCity() + ", " + contact.getState() + " " + contact.getPostcode());
            holder.emailView.setText(contact.getEmail());
            holder.phoneView.setText(contact.getPhoneNumber());
            holder.websiteView.setText(contact.getHomepage());
            holder.interestsView.setText(contact.getInterests());
            holder.publicationsView.setText(contact.getPublications());

            StringBuilder eduPhasesStr = new StringBuilder();
            for (EducationPhase educationPhase : current.educationPhases) {
                eduPhasesStr.append(educationPhase.getPlaintext());
            }
            holder.educationPhasesView.setText(eduPhasesStr.toString());

            StringBuilder workPhasesStr = new StringBuilder();
            for (WorkPhase workPhase : current.workPhases) {
                workPhasesStr.append(workPhase.getPlaintext());
            }
            holder.workPhasesView.setText(workPhasesStr.toString());

        } else {
            // Covers the case of data not being ready yet.
            holder.resumeHeader.setText("Loading...");
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