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
import com.mvc.kinballwc.utils.Utils;

/**
 * Created by mario on 27/6/15.
 */
public class TeamsFragment extends Fragment {

    private static final String TAG = "TeamsFragment";

    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        ((HomeActivity) getActivity()).setupToolbar(view);

        viewPager = (ViewPager) view.findViewById(R.id.teams_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.teams_tabs);
        if (viewPager != null && tabLayout != null) {
            setupTabs();
        }
        return view;
    }


    private void setupTabs() {
        String[] categoryQueries = Utils.getCategories();
        String[] categoryNames = getResources().getStringArray(R.array.categories);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        for (int i = 0; i < categoryNames.length; i++) {
            tabPagerAdapter.addFragment(new TeamsTabFragment(), categoryNames[i]);
//            tabPagerAdapter.addFragment(MatchesTabFragment.newInstance(i + 1), categoryNames[i]);
        }
        viewPager.setAdapter(tabPagerAdapter);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "setting view pager");
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

}
