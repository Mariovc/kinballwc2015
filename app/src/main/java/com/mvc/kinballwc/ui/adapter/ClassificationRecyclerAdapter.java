package com.mvc.kinballwc.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Score;

import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 02/08/2015
 * Email: m3ario@gmail.com
 */
public class ClassificationRecyclerAdapter
        extends RecyclerView.Adapter<ClassificationRecyclerAdapter.ViewHolder> {

    private static final String TAG = "ClassificationRecyclerAdapter";

    private Context mContext;
    private List<Score> mScores;

    public ClassificationRecyclerAdapter(Context context, List<Score> scores) {
        this.mContext = context;
        this.mScores = scores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_classification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Score score = mScores.get(position);
        holder.mPostionTV.setText(String.valueOf(position + 1));
        holder.mTeamNameTV.setText(score.getName());
        holder.mMatch1ScoreTV.setText(getConvertedScore(score.getMatch1Score()));
        holder.mMatch2ScoreTV.setText(getConvertedScore(score.getMatch2Score()));
        holder.mMatch3ScoreTV.setText(getConvertedScore(score.getMatch3Score()));
        holder.mTotalScoreTV.setText(getConvertedScore(score.getTotalScore()));
    }

    private String getConvertedScore(int score) {
        String convertedScore;
        if (score > 0) {
            convertedScore = String.valueOf(score);
        } else {
            convertedScore = mContext.getString(R.string.default_score);
        }
        return convertedScore;
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mPostionTV;
        public final TextView mTeamNameTV;
        public final TextView mMatch1ScoreTV;
        public final TextView mMatch2ScoreTV;
        public final TextView mMatch3ScoreTV;
        public final TextView mTotalScoreTV;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPostionTV = (TextView) view.findViewById(R.id.classificationPositionTV);
            mTeamNameTV = (TextView) view.findViewById(R.id.classificationTeamNameTV);
            mMatch1ScoreTV = (TextView) view.findViewById(R.id.classificationMatch1ScoreTV);
            mMatch2ScoreTV = (TextView) view.findViewById(R.id.classificationMatch2ScoreTV);
            mMatch3ScoreTV = (TextView) view.findViewById(R.id.classificationMatch3ScoreTV);
            mTotalScoreTV = (TextView) view.findViewById(R.id.classificationTotalScoreTV);
        }
    }
}
