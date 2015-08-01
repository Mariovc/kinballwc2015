package com.mvc.kinballwc.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.activity.HomeActivity;
import com.mvc.kinballwc.ui.adapter.TeamAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by mario on 27/6/15.
 */
public class TeamsFragment extends Fragment {


    private ListView listView;


    public TeamsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teams, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);

        ((HomeActivity) getActivity()).setupToolbar(rootView);
        return rootView;
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
        if (listView != null) {
            TeamAdapter mAdapter = new TeamAdapter(getActivity(), itemList);
            listView.setAdapter(mAdapter);
        }
    }

}
