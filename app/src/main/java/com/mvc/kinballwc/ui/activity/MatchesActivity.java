package com.mvc.kinballwc.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.ui.adapter.TabPagerAdapter;
import com.mvc.kinballwc.ui.fragment.MatchesTabFragment;

/**
 * Author: Mario Velasco Casquero
 * Date: 11/08/2015
 * Email: m3ario@gmail.com
 */
public class MatchesActivity extends BaseActivity{

    private static final String TAG = "MatchesActivity";

    public static final String EXTRA_CATEGORY = "filter_category";
    public static final String EXTRA_TEAM = "filter_team";


    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String mCategoryFilter;
    private String mClubFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_matches);

        mCategoryFilter = getIntent().getStringExtra(EXTRA_CATEGORY);
        mClubFilter = getIntent().getStringExtra(EXTRA_TEAM);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (viewPager != null && tabLayout != null) {
            setupTabs();
        }
    }



    private void setupTabs() {
        String[] daysArray = getResources().getStringArray(R.array.days);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < daysArray.length; i++) {
            MatchesTabFragment matchesTabFragment = MatchesTabFragment.newInstance(i + 1);
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_CATEGORY, mCategoryFilter);
            bundle.putString(EXTRA_TEAM, mClubFilter);
            matchesTabFragment.setArguments(bundle);
            tabPagerAdapter.addFragment(matchesTabFragment, daysArray[i]);
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
