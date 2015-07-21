package com.mvc.kinballwc.ui.fragment;

import android.app.Activity;
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
import com.mvc.kinballwc.ui.adapter.MatchRecyclerAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Mario on 13/07/2015.
 */
public class MatchesTabFragment extends Fragment{

    private static final String ARG_TAB_NUMBER = "tab_number";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int tabNumber;

    public static MatchesTabFragment newInstance(int tabNumber) {
        MatchesTabFragment fragment = new MatchesTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NUMBER, tabNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MatchesTabFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tabNumber = getArguments().getInt(ARG_TAB_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches_tab, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(null);

//        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        // Define the class we would like to query
        ParseQuery<Match> query = ParseQuery.getQuery(Match.class);
        // Define our query conditions
        //query.whereEqualTo("owner", ParseUser.getCurrentUser());
        // Execute the find asynchronously
        // TODO make query with date
        query.include("team1");
        query.include("team2");
        query.include("team3");
        query.include("team1Points");
        query.include("team2Points");
        query.include("team3Points");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findInBackground(new FindCallback<Match>() {
            public void done(List<Match> itemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    onMatchesReceived(itemList);
                    //String firstItemId = itemList.get(0).getName();
                    //Toast.makeText(getActivity(), firstItemId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }


    private void onMatchesReceived(List<Match> itemList) {
        mAdapter = new MatchRecyclerAdapter(itemList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
