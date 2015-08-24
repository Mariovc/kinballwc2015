package com.mvc.kinballwc.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.ui.adapter.TabPagerAdapter;
import com.mvc.kinballwc.ui.fragment.MatchesTabFragment;
import com.mvc.kinballwc.utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author: Mario Velasco Casquero
 * Date: 11/08/2015
 * Email: m3ario@gmail.com
 */
public class MatchesActivity extends BaseActivity{

    private static final String TAG = "MatchesActivity";

    public static final String EXTRA_CATEGORY = "filter_category";
    public static final String EXTRA_TEAM = "filter_team";

    public static final String[] dates = new String[]{"18/08/2015", "19/08/2015",
            "20/08/2015", "21/08/2015", "22/08/2015", "23/08/2015"};
    public static final DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

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
        setToolbarTitle(Utils.getTranslatedCategory(this, mCategoryFilter));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (viewPager != null && tabLayout != null) {
            setupTabs();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupTabs() {
        String[] daysArray = getResources().getStringArray(R.array.days);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < daysArray.length; i++) {
            MatchesTabFragment matchesTabFragment = MatchesTabFragment.newInstance(i);
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
        selectTodayTab();
    }

    private void selectTodayTab() {
        int numTabs = getResources().getStringArray(R.array.days).length;
        int todayTabPosition = numTabs - 1;
        Date today = new Date();
        for (int i = 0; i < numTabs; i++) {
            String dateString = dates[i+1];
            try {
                Date filterDate1 = format.parse(dateString);
                if (today.before(filterDate1)) {
                    todayTabPosition = i;
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        viewPager.setCurrentItem(todayTabPosition, true);
    }

}
