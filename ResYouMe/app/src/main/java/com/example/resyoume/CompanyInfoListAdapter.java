package com.example.resyoume;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.resyoume.db.CompanyInfo;

import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CompanyInfoListAdapter extends RecyclerView.Adapter<CompanyInfoListAdapter.CompanyInfoViewHolder> {

    class CompanyInfoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView companyNameView;
        private final TextView timestampView;
        private final TextView ratingView;

        private CompanyInfo companyInfo;

        private CompanyInfoViewHolder(View itemView) {
            super(itemView);
            companyNameView = itemView.findViewById(R.id.nameView);
            timestampView = itemView.findViewById(R.id.timestampView);
            ratingView = itemView.findViewById(R.id.ratingView);

            itemView.setOnCreateContextMenuListener(this);
        }

        public CompanyInfo getCompanyInfo() {
            return companyInfo;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Share or View Company Info for " + companyInfo.getCompanyName() + "?");
            menu.add(Menu.NONE, R.id.ctx_menu_send_by_nfc, Menu.NONE, R.string.send_by_nfc);
            menu.add(Menu.NONE, R.id.ctx_menu_display, Menu.NONE, R.string.display_resume);
            menu.add(Menu.NONE, R.id.ctx_edit_NR, Menu.NONE, R.string.edit_NR);
        }
    }

    private final LayoutInflater inflater;
    private List<CompanyInfo> companyInfos; // Cached copy of words
    private int position;

    CompanyInfoListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CompanyInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CompanyInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompanyInfoViewHolder holder, int position) {
        if (companyInfos != null) {
            CompanyInfo current = companyInfos.get(position);
            holder.companyInfo = current;
            holder.companyNameView.setText(holder.companyInfo.getCompanyName());

            Date timestamp = current.getTimestamp();
            String timestampString = OurDateFormatter.formatDate(timestamp);
            holder.timestampView.setText(timestampString);

            if(current.getRating() != null){
                holder.ratingView.setText(String.valueOf(current.getRating()));
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.companyNameView.setText("Loading...");
        }

        holder.itemView.setOnLongClickListener(view -> {
            setPosition(holder.getLayoutPosition());
            return false;
        });
    }

    @Override
    public void onViewRecycled(CompanyInfoViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public void setCompanyInfo(List<CompanyInfo> companyInfos) {
        this.companyInfos = companyInfos;
        notifyDataSetChanged();
    }

    public CompanyInfo getCompanyInfo(int position) {
        return companyInfos.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // getItemCount() is called many times, and when it is first called,
    // companyInfos has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (companyInfos != null)
            return companyInfos.size();
        else return 0;
    }

}
