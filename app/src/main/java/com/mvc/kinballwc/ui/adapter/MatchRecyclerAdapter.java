package com.mvc.kinballwc.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;

import java.util.ArrayList;


public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {

    private ArrayList<Match> matches;

    public MatchRecyclerAdapter(ArrayList<Match> matches) {
        this.matches = matches;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int i) {

        View rowView = LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.item_match2, parentViewGroup, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final Match match = matches.get(position);
        viewHolder.titleTextView.setText(match.getTitle());

        viewHolder.itemView.setTag(match);
    }


    @Override
    public int getItemCount() {

        return matches.size();
    }

    public void removeData(int position) {

        matches.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int positionToAdd) {

        matches.add(positionToAdd, new Match());
        notifyItemInserted(positionToAdd);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(
                    R.id.matchTitleTextView);
        }
    }

}
