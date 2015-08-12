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
import com.mvc.kinballwc.model.Referee;
import com.mvc.kinballwc.ui.adapter.RefereeRecyclerAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RefereesTabFragment extends Fragment {

    private static final String TAG = "TeamsTabFragment";

    private RefereesFragment mRefereeFragment;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private String mCategoryQuery;


    public static RefereesTabFragment newInstance(RefereesFragment refereesFragment, String categoryQuery) {
        RefereesTabFragment fragment = new RefereesTabFragment();
        fragment.mRefereeFragment = refereesFragment;
        fragment.mCategoryQuery = categoryQuery;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_referees_tab, container, false);
        setupRecyclerView(mRecyclerView);
        return mRecyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new RefereeRecyclerAdapter(mRefereeFragment, new ArrayList<Referee>()));
        mRecyclerView.setHasFixedSize(true);
    }



    @Override
    public void onResume() {
        super.onResume();
        ParseQuery<Referee> query = ParseQuery.getQuery(Referee.class);
        query.whereEqualTo("category", mCategoryQuery);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<Referee>() {
            public void done(List<Referee> itemList, ParseException e) {
                if (e == null) {
                    onRefereesReceived(itemList);
                } else {
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }


    private void onRefereesReceived(List<Referee> itemList) {
        mAdapter = new RefereeRecyclerAdapter(mRefereeFragment, itemList);
        mRecyclerView.swapAdapter(mAdapter, false);
    }

}
