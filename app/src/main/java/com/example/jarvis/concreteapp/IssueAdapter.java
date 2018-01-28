package com.example.jarvis.concreteapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jarvis.concreteapp.model.Issue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jarvis on 26-01-2018.
 */

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder>{

    private List<Issue> issueList;

    public IssueAdapter(List<Issue> issueList) {
        this.issueList = issueList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        long milliseconds=Long.parseLong(issueList.get(position).getDate());
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH);
        holder.issue_date.setText(formatter.format(date));
        holder.issue_status.setText(issueList.get(position).getStatus());
        holder.issue_title.setText("Title:"+issueList.get(position).getTitle());
        holder.issue_type.setText("Issue Type:"+issueList.get(position).getType());
        holder.issue_desc.setText(issueList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView issue_title,issue_desc,issue_type,issue_status,issue_date;


        public ViewHolder(View itemView) {
            super(itemView);
            issue_title = (TextView) itemView.findViewById(R.id.issue_title);
            issue_type=(TextView)itemView.findViewById(R.id.issuetype);
            issue_desc=(TextView)itemView.findViewById(R.id.issuedescription);
            issue_status=(TextView)itemView.findViewById(R.id.Issuestatus);
            issue_date=(TextView)itemView.findViewById(R.id.issuedate);

        }
    }
}
