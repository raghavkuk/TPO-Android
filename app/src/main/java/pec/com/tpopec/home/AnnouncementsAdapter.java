package pec.com.tpopec.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pec.com.tpopec.R;
import pec.com.tpopec.model.Announcement;

/**
 * Created by Raghav on 26-05-2016.
 */
public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.AnnouncementViewHolder> {

    private ArrayList<Announcement> announcementArrayList;

    public AnnouncementsAdapter(ArrayList<Announcement> announcements) {
        this.announcementArrayList = announcements;
    }

    @Override
    public AnnouncementsAdapter.AnnouncementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_announcement, parent, false);

        return new AnnouncementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnnouncementViewHolder holder, int position) {
        Announcement announcement = announcementArrayList.get(position);
        holder.companyName.setText(announcement.getCompany_name());
        holder.msg.setText(announcement.getMsg());
        holder.date.setText(announcement.getDate());
    }


    @Override
    public int getItemCount() {
        return announcementArrayList.size();
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder{
        public TextView companyName, msg, date;

        public AnnouncementViewHolder(View view) {
            super(view);
            companyName = (TextView) view.findViewById(R.id.name_announcement_company);
            msg = (TextView) view.findViewById(R.id.announcement_msg);
            date = (TextView) view.findViewById(R.id.date_announcement);
        }

    }
}
