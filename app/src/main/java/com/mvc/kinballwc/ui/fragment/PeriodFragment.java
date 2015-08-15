package com.mvc.kinballwc.ui.fragment;

/**
 * Author: Mario Velasco Casquero
 * Date: 31/07/2015
 * Email: m3ario@gmail.com
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.MatchPeriod;
import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

public final class PeriodFragment extends Fragment {

    private MatchPeriod period;
    private String channelPush;

    private TextSwitcher team1Score;
    private TextSwitcher team2Score;
    private TextSwitcher team3Score;
    private int score1;
    private int score2;
    private int score3;

    public static PeriodFragment newInstance(MatchPeriod period, String channelPush) {
        PeriodFragment fragment = new PeriodFragment();
        fragment.period = period;
        fragment.channelPush = channelPush;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_period, container, false);

        team1Score = (TextSwitcher) view.findViewById(R.id.team1Score);
        team2Score = (TextSwitcher) view.findViewById(R.id.team2Score);
        team3Score = (TextSwitcher) view.findViewById(R.id.team3Score);

        setupSwitcher(team1Score);
        setupSwitcher(team2Score);
        setupSwitcher(team3Score);

        setScore(1, period.getTeam1Score());
        setScore(2, period.getTeam2Score());
        setScore(3, period.getTeam3Score());

        return view;
    }

    private void setupSwitcher(TextSwitcher switcher) {
        switcher.setFactory(switcherFactory);
        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        switcher.setInAnimation(in);
    }

    public void incrementScore(int teamPos) {
        int newScore = 0;
        switch (teamPos) {
            case 1:
                newScore = score1 +1;
                period.setTeam1Score(newScore);
                break;
            case 2:
                newScore = score2 +1;
                period.setTeam2Score(newScore);
                break;
            case 3:
                newScore = score3 +1;
                period.setTeam3Score(newScore);
                break;
        }
        setScore(teamPos, newScore);
        period.saveInBackground();
        sendPush(period.getObjectId(), teamPos, newScore);
    }

    public void decrementScore(int teamPos) {
        int newScore = 0;
        switch (teamPos) {
            case 1:
                newScore = score1 -1;
                period.setTeam1Score(newScore);
                break;
            case 2:
                newScore = score2 -1;
                period.setTeam2Score(newScore);
                break;
            case 3:
                newScore = score3 -1;
                period.setTeam3Score(newScore);
                break;
        }
        setScore(teamPos, newScore);
        period.saveInBackground();
        sendPush(period.getObjectId(), teamPos, newScore);
    }



    public void setScore(int teamPos, int score) {
        switch (teamPos) {
            case 1:
                score1 = score;
                setText(team1Score, score);
                break;
            case 2:
                score2 = score;
                setText(team2Score, score);
                break;
            case 3:
                score3 = score;
                setText(team3Score, score);
                break;
        }
    }

    private void setText(final TextSwitcher switcher, final int score) {
        String text = String.format("%02d", score);
        switcher.setText(text);
    }

    private void sendPush(String periodId, int teamPos, int score) {
        ParsePush push = new ParsePush();
        JSONObject periodJson = new JSONObject();
        try {
            periodJson.put("periodId", periodId);
            periodJson.put("teamPos", teamPos);
            periodJson.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        push.setChannel(channelPush);
        push.setData(periodJson);
        push.sendInBackground();
    }


    private ViewFactory switcherFactory = new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            TextView t = new TextView(getActivity());
            t.setTextSize(36);
            t.setGravity(Gravity.CENTER);
            t.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            return t;
        }
    };
}
