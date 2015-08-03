package com.mvc.kinballwc.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.ui.activity.HomeActivity;
import com.mvc.kinballwc.ui.adapter.TabPagerAdapter;

/**
 * Author: Mario Velasco Casquero
 * Date: 27/6/15
 * Email: m3ario@gmail.com
 */
public class MatchesFragment extends Fragment {

    private static final String TAG = "MatchesFragment";

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MatchesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        ((HomeActivity) getActivity()).setupToolbar(view);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        if (viewPager != null && tabLayout != null) {
            setupTabs();
        }
        return view;
    }


    private void setupTabs() {
        String[] daysArray = getResources().getStringArray(R.array.days);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        for (int i = 0; i < daysArray.length; i++) {
            tabPagerAdapter.addFragment(MatchesTabFragment.newInstance(i + 1), daysArray[i]);
        }
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "setting view pager");
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }



}
