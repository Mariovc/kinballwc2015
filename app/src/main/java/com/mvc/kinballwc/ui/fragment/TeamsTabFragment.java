/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mvc.kinballwc.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.adapter.TeamsRecyclerAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamsTabFragment extends Fragment {

    private static final String TAG = "TeamsTabFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String mCategoryQuery;


    public static TeamsTabFragment newInstance(String categoryQuery) {
        TeamsTabFragment fragment = new TeamsTabFragment();
        fragment.mCategoryQuery = categoryQuery;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_teams_tab, container, false);
        setupRecyclerView(mRecyclerView);
        return mRecyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new TeamsRecyclerAdapter(getActivity(), new ArrayList<Team>()));
        mRecyclerView.setHasFixedSize(true);
    }



    @Override
    public void onResume() {
        super.onResume();
        ParseQuery<Team> query = ParseQuery.getQuery(Team.class);
        query.whereEqualTo("category", mCategoryQuery);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Team>() {
            public void done(List<Team> itemList, ParseException e) {
                if (e == null) {
                    onTeamsReceived(itemList);
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }


    private void onTeamsReceived(List<Team> itemList) {
        Collections.sort(itemList, new Team.NameComparator());
        mAdapter = new TeamsRecyclerAdapter(getActivity(), itemList);
        mRecyclerView.swapAdapter(mAdapter, false);
    }

}
