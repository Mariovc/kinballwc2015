package com.mvc.kinballwc.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.ui.activity.MatchActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Author: Mario Velasco Casquero
 * Date: 08/07/2015
 * Email: m3ario@gmail.com
 */
public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {
    private List<Match> mMatchList;


    public MatchRecyclerAdapter(List<Match> matchList) {
        mMatchList = matchList;
    }

    @Override
    public MatchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, new ViewHolder.MatchViewHolderClicks() {
            @Override
            public void onClick(View view, int position) {
                String matchId = mMatchList.get(position).getObjectId();
                Intent intent = new Intent(view.getContext(), MatchActivity.class);
                intent.putExtra(MatchActivity.MATCH_ID_EXTRA, matchId);
                view.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.mTitleTV.getContext();
        Match match = mMatchList.get(position);
        holder.mTitleTV.setText(match.getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.date_format),
                Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat(context.getString(R.string.hour_format), Locale.getDefault());
        holder.mDateTV.setText(dateFormat.format(match.getDate()));
        holder.mHourTV.setText(hourFormat.format(match.getDate()));
        holder.mTeam1NameTV.setText(match.getTeam1().getName());
        holder.mTeam2NameTV.setText(match.getTeam2().getName());
        holder.mTeam3NameTV.setText(match.getTeam3().getName());
        loadImage(holder.mTeam1LogoIV, match.getTeam1().getLogo());
        loadImage(holder.mTeam2LogoIV, match.getTeam2().getLogo());
        loadImage(holder.mTeam3LogoIV, match.getTeam3().getLogo());
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }

    private void loadImage(final ImageView imageView, final String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.placeholder)
//                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }








    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MatchViewHolderClicks mListener;
        public TextView mTitleTV;
        public TextView mLiveLabelTV;
        public TextView mDateTV;
        public TextView mHourTV;
        public TextView mTeam1NameTV;
        public TextView mTeam2NameTV;
        public TextView mTeam3NameTV;
        public ImageView mTeam1LogoIV;
        public ImageView mTeam2LogoIV;
        public ImageView mTeam3LogoIV;

        public ViewHolder(View v, MatchViewHolderClicks onClickListener) {
            super(v);
            mListener = onClickListener;
            mTitleTV = (TextView) v.findViewById(R.id.matchTitleTV);
            mLiveLabelTV = (TextView) v.findViewById(R.id.matchLiveTV);
            mDateTV = (TextView) v.findViewById(R.id.matchDateTV);
            mHourTV = (TextView) v.findViewById(R.id.matchHourTV);
            mTeam1NameTV = (TextView) v.findViewById(R.id.matchTeam1NameTextView);
            mTeam2NameTV = (TextView) v.findViewById(R.id.matchTeam2NameTextView);
            mTeam3NameTV = (TextView) v.findViewById(R.id.matchTeam3NameTextView);
            mTeam1LogoIV = (ImageView) v.findViewById(R.id.matchTeam1LogoIV);
            mTeam2LogoIV = (ImageView) v.findViewById(R.id.matchTeam2LogoIV);
            mTeam3LogoIV = (ImageView) v.findViewById(R.id.matchTeam3LogoIV);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getPosition()); // TODO deprecated
        }

        public interface MatchViewHolderClicks {
            void onClick(View view, int position);
        }
    }
}
