package com.mvc.kinballwc.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.activity.TeamActivity;

import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 02/08/2015
 * Email: m3ario@gmail.com
 */
public class TeamsRecyclerAdapter
        extends RecyclerView.Adapter<TeamsRecyclerAdapter.ViewHolder> {

    private List<Team> mTeams;

    public TeamsRecyclerAdapter(List<Team> teams) {
        this.mTeams = teams;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.avatar);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Team team = mTeams.get(position);
        holder.mTextView.setText(team.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TeamActivity.class);
                    intent.putExtra(TeamActivity.EXTRA_TEAM_ID, team.getObjectId());
                    context.startActivity(intent);
            }
        });

        Glide.with(holder.mImageView.getContext())
                .load(team.getLogo())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }
}
