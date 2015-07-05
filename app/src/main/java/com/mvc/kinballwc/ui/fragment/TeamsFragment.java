package com.mvc.kinballwc.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.activity.HomeActivity;
import com.mvc.kinballwc.ui.adapter.TeamAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by mario on 27/6/15.
 */
public class TeamsFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    private Adapter adapter;
    private ListView listView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TeamsFragment newInstance(int sectionNumber) {
        TeamsFragment fragment = new TeamsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TeamsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teams, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Define the class we would like to query
        ParseQuery<Team> query = ParseQuery.getQuery(Team.class);
        // Define our query conditions
        //query.whereEqualTo("owner", ParseUser.getCurrentUser());
        // Execute the find asynchronously
        query.include("player");
        query.findInBackground(new FindCallback<Team>() {
            public void done(List<Team> itemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    onTeamsReceived(itemList);
                    String firstItemId = itemList.get(0).getName();
                    Toast.makeText(getActivity(), firstItemId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }


    private void onTeamsReceived(List<Team> itemList) {
        TeamAdapter mAdapter = new TeamAdapter(getActivity(), itemList);
        listView.setAdapter(mAdapter);
    }

}
