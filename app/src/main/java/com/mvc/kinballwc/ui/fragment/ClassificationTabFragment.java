package com.mvc.kinballwc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.model.MatchPoints;
import com.mvc.kinballwc.model.Score;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.activity.BaseActivity;
import com.mvc.kinballwc.ui.activity.HomeActivity;
import com.mvc.kinballwc.ui.adapter.ClassificationRecyclerAdapter;
import com.mvc.kinballwc.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 13/07/2015
 * Email: m3ario@gmail.com
 */
public class ClassificationTabFragment extends Fragment {

    private static final String TAG = "ClassifTabFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String mCategoryQuery;


    public static ClassificationTabFragment newInstance(String categoryQuery) {
        ClassificationTabFragment fragment = new ClassificationTabFragment();
        fragment.mCategoryQuery = categoryQuery;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_classification_tab, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new ClassificationRecyclerAdapter(getActivity(), new ArrayList<Score>());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        ParseQuery<Match> query = ParseQuery.getQuery(Match.class);
        query.whereEqualTo("category", mCategoryQuery);
        query.include("team1");
        query.include("team2");
        query.include("team3");
        query.include("team1Points");
        query.include("team2Points");
        query.include("team3Points");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Match>() {
            public void done(List<Match> itemList, ParseException e) {
                if (e == null) {
                    onMatchesReceived(itemList);
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }


    private void onMatchesReceived(List<Match> matchList) {
        if (getActivity() == null || ((HomeActivity) getActivity()).isActivityDestroyed) {
            Log.d(TAG, "Activity is destroyed after Parse query");
            return;
        }
        HashMap<String, Score> scoresMap = new HashMap<>();
        for (Match match : matchList) {
            fillScore(scoresMap, match.getTeam1(), match.getTeam1Points());
            fillScore(scoresMap, match.getTeam2(), match.getTeam2Points());
            fillScore(scoresMap, match.getTeam3(), match.getTeam3Points());
        }
        List<Score> scoreList = new ArrayList<>(scoresMap.values());
        Collections.sort(scoreList, new Score.ScoreComparator());
        mAdapter = new ClassificationRecyclerAdapter(getActivity(), scoreList);
        mRecyclerView.swapAdapter(mAdapter, false);
    }

    private void fillScore(HashMap<String,Score> scoresMap, Team team, MatchPoints matchPoints) {
        if (team == null) {
            return;
        }
        Score score;
        String teamId = team.getObjectId();
        if (scoresMap.containsKey(teamId)) {
            score = scoresMap.get(teamId);
        } else {
            score = new Score();
            score.setName(team.getName());
        }
        if (matchPoints != null) {
            if (score.getMatch3Score() == 0) {
                if (matchPoints.getMatchPoints() == MatchPoints.MATCH_POINTS_FIRST) {
                    score.setFirstPositionCount(score.getFirstPositionCount() + 1);
                } else if (matchPoints.getMatchPoints() == MatchPoints.MATCH_POINTS_SECOND) {
                    score.setSecondPositionCount(score.getSecondPositionCount() + 1);
                }
                score.setWonPeriodsCount(score.getWonPeriodsCount() + matchPoints.getWonPeriods());
                score.setTotalScore(score.getTotalScore() + matchPoints.getTotalPoints());
            }
            if (score.getMatch1Score() == 0) {
                score.setMatch1Score(matchPoints.getTotalPoints());
            } else if (score.getMatch2Score() == 0) {
                score.setMatch2Score(matchPoints.getTotalPoints());
            } else if (score.getMatch3Score() == 0) {
                score.setMatch3Score(matchPoints.getTotalPoints());
            }
        }
        scoresMap.put(teamId, score);
    }


}
