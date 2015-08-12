package com.mvc.kinballwc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.ui.activity.MatchesActivity;
import com.mvc.kinballwc.ui.adapter.MatchRecyclerAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 13/07/2015
 * Email: m3ario@gmail.com
 */
public class MatchesTabFragment extends Fragment {

    private static final String TAG = "MatchesTabFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int tabNumber;
    private String mCategoryFilter;
    private String mTeamFilter;

    public static MatchesTabFragment newInstance(int tabNumber) {
        MatchesTabFragment fragment = new MatchesTabFragment();
        fragment.tabNumber = tabNumber;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        mCategoryFilter = bundle.getString(MatchesActivity.EXTRA_CATEGORY, null);
        mTeamFilter = bundle.getString(MatchesActivity.EXTRA_TEAM, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_matches_tab, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new MatchRecyclerAdapter(new ArrayList<Match>());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mTeamFilter)) {
            makeTeamQuery(mTeamFilter);
        } else {
            makeSimpleQuery();
        }
    }

    private void makeSimpleQuery() {
        Log.d(TAG, "ParseQuery get matches, tab: " + tabNumber);
        String[] filterDates = MatchesActivity.dates;
        ParseQuery<Match> query = ParseQuery.getQuery(Match.class);
        Date filterDate1, filterDate2;
        try {
            filterDate1 = MatchesActivity.format.parse(filterDates[tabNumber]);
            query.whereGreaterThanOrEqualTo("date", filterDate1);
            filterDate2 = MatchesActivity.format.parse(filterDates[tabNumber+1]);
            query.whereLessThan("date", filterDate2);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(mCategoryFilter)){
            query.whereEqualTo("category", mCategoryFilter);
        }
        query.include("team1");
        query.include("team2");
        query.include("team3");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Match>() {
            public void done(List<Match> itemList, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "ParseQuery ok, tab: " + tabNumber + " matches: itemList: " + itemList.size());
                    onMatchesReceived(itemList);
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }



    private void makeTeamQuery(String mTeamFilter) {
        ParseQuery<Match> team1Query = getTeamQuery("team1.name", mTeamFilter);
        ParseQuery<Match> team2Query = getTeamQuery("team2.name", mTeamFilter);
        ParseQuery<Match> team3Query = getTeamQuery("team3.name", mTeamFilter);
        List<ParseQuery<Match>> queries = new ArrayList<>();
        queries.add(team1Query);
        queries.add(team2Query);
        queries.add(team3Query);
        ParseQuery<Match> mainQuery = ParseQuery.or(queries);
        mainQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        mainQuery.findInBackground(new FindCallback<Match>() {
            public void done(List<Match> itemList, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "ParseQuery ok, tab: " + tabNumber + " matches: itemList: " + itemList.size());
                    onMatchesReceived(itemList);
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    private ParseQuery<Match> getTeamQuery(String field, String teamId) {
        ParseQuery<Match> query = ParseQuery.getQuery(Match.class);
        // TODO make query with date
        query.whereEqualTo(field, teamId);
        query.include("team1");
        query.include("team2");
        query.include("team3");
        return query;
    }


    private void onMatchesReceived(List<Match> itemList) {
        Collections.sort(itemList, new Match.MatchComparator());
        mAdapter = new MatchRecyclerAdapter(itemList);
        mRecyclerView.swapAdapter(mAdapter, false);
    }


}
