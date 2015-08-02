package com.mvc.kinballwc.ui.fragment;

/**
 * Created by Mario on 31/07/2015.
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

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.MatchPeriod;

public final class PeriodFragment extends Fragment {

    private MatchPeriod period;

    public static PeriodFragment newInstance(MatchPeriod period) {
        PeriodFragment fragment = new PeriodFragment();
        fragment.period = period;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_period, container, false);

        TextSwitcher team1Score = (TextSwitcher) view.findViewById(R.id.team1Score);
        TextSwitcher team2Score = (TextSwitcher) view.findViewById(R.id.team2Score);
        TextSwitcher team3Score = (TextSwitcher) view.findViewById(R.id.team3Score);

        setupSwitcher(team1Score);
        setupSwitcher(team2Score);
        setupSwitcher(team3Score);

        setText(team1Score, period.getTeam1Score());
        setText(team2Score, period.getTeam2Score());
        setText(team3Score, period.getTeam3Score());

        return view;
    }

    private void setupSwitcher(TextSwitcher switcher) {
        switcher.setFactory(switcherFactory);
        Animation in = AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_out);
        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);
    }

    private void setText(TextSwitcher switcher, int score) {
        String text = String.format("%02d", score);
        switcher.setText(text);
    }


    private ViewSwitcher.ViewFactory switcherFactory = new ViewSwitcher.ViewFactory() {
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
