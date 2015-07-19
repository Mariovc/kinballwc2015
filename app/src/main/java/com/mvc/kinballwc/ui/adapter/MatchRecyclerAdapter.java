package com.mvc.kinballwc.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.ui.activity.MatchActivity;

import java.util.List;

/**
 * Created by Mario on 08/07/2015.
 */
public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {
    private List<Match> mMatchList;


    public MatchRecyclerAdapter(List<Match> matchList) {
        mMatchList = matchList;
    }

    @Override
    public MatchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match2, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match match = mMatchList.get(position);
//        holder.mTeam1NameTV.setText(match.getTeam1().getName());
//        holder.mTeam2NameTV.setText(match.getTeam2().getName());
//        holder.mTeam3NameTV.setText(match.getTeam3().getName());
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }










    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTeam1NameTV;
        public TextView mTeam2NameTV;
        public TextView mTeam3NameTV;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mTeam1NameTV = (TextView) v.findViewById(R.id.matchTeam1NameTextView);
            mTeam2NameTV = (TextView) v.findViewById(R.id.matchTeam2NameTextView);
            mTeam3NameTV = (TextView) v.findViewById(R.id.matchTeam3NameTextView);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(view.getContext(),"hola", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(view.getContext(), MatchActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
