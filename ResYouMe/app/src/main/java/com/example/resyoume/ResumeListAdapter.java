package com.example.resyoume;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.resyoume.db.Contact;
import com.example.resyoume.db.Resume;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ResumeListAdapter extends RecyclerView.Adapter<ResumeListAdapter.ResumeViewHolder> implements Filterable {

    class ResumeViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView resumeHeader;
        private final TextView timestampView;
        private final TextView ratingView;

        private Resume resume;

        private ResumeViewHolder(View itemView) {
            super(itemView);
            resumeHeader = itemView.findViewById(R.id.nameView);
            timestampView = itemView.findViewById(R.id.timestampView);
            ratingView = itemView.findViewById(R.id.ratingView);

            itemView.setOnCreateContextMenuListener(this);
        }

        public Resume getResume() {
            return resume;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Share Resume for " + resume.contact.getFirstName() + " " + resume.contact.getLastName() + "?");
            menu.add(Menu.NONE, R.id.ctx_menu_send_by_nfc, Menu.NONE, R.string.send_by_nfc);
            menu.add(Menu.NONE, R.id.ctx_menu_display, Menu.NONE, R.string.display_resume);
            menu.add(Menu.NONE, R.id.ctx_edit_resume, Menu.NONE, R.string.edit_resume);
            menu.add(Menu.NONE, R.id.ctx_edit_NR, Menu.NONE, R.string.edit_NR);
        }
    }

    private final LayoutInflater inflater;
    private List<Resume> resumes;
    private List<Resume> resumesFull;
    private int position;

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
            holder.resume = current;
            holder.resumeHeader.setText(contact.getTitle() + " " + contact.getFirstName() + " " + contact.getLastName());

            Date timestamp = current.contact.getTimestamp();
            String timestampString = OurDateFormatter.formatDate(timestamp);
            holder.timestampView.setText(timestampString);

            if(current.contact.getRating() != null){
                holder.ratingView.setText(String.valueOf(current.contact.getRating()));
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.resumeHeader.setText("Loading...");
        }

        holder.itemView.setOnLongClickListener(view -> {
            setPosition(holder.getLayoutPosition());
            return false;
        });
    }

    @Override
    public void onViewRecycled(ResumeViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public void setResumes(List<Resume> resumes) {
        this.resumesFull = new ArrayList<>(resumes);
        this.resumes = resumes;
        getFilter().filter(searchFilter.getLastFilter());
        notifyDataSetChanged();
    }

    public Resume getResume(int position) {
        return resumes.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // getItemCount() is called many times, and when it is first called,
    // resumes has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (resumes != null)
            return resumes.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private CachingFilter searchFilter = new CachingFilter();


    private class CachingFilter extends Filter {

        private CharSequence lastFilter = "";

        public CharSequence getLastFilter() {
            return lastFilter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Resume> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(resumesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Resume resume : resumesFull)
                    if (resume.getContact().getFullName().toLowerCase().contains(filterPattern))
                        filteredList.add(resume);

            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            lastFilter = constraint;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            resumes.clear();
            resumes.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}