package com.mvc.kinballwc.ui.adapter;

/**
 * Author: Mario Velasco Casquero
 * Date: 31/07/2015
 * Email: m3ario@gmail.com
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.MatchPeriod;
import com.mvc.kinballwc.ui.fragment.PeriodFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeriodFragmentAdapter extends FragmentStatePagerAdapter {

    private long baseId = 0;
    private final FragmentManager fragmentManager;
    private Context context;
    private List<MatchPeriod> periods;
    private String channelPush;
    private SparseArray<PeriodFragment> fragments  = new SparseArray<>();

    public PeriodFragmentAdapter(FragmentManager fm, Context context, List<MatchPeriod> periods, String channelPush) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
        this.periods = periods;
        this.channelPush = channelPush;
    }

    @Override
    public Fragment getItem(int position) {
        PeriodFragment periodFragment = PeriodFragment.newInstance(periods.get(position), channelPush);
        fragments.put(position, periodFragment);
        return periodFragment;
    }

    @Override
    public int getCount() {
        return periods.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.period_title, position + 1);
    }

    public void setPeriods(List<MatchPeriod> periods) {
        this.periods = periods;
        notifyDataSetChanged();
    }
    public Fragment getActiveFragment(ViewPager container, int position) {
//        String name = makeFragmentName(container.getId(), position);
//        return  fragmentManager.findFragmentByTag(name);
        return fragments.get(position);
    }

//    private static String makeFragmentName(int viewId, int index) {
//        return "android:switcher:" + viewId + ":" + index;
//    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
//
//    //this is called when notifyDataSetChanged() is called
//    @Override
//    public int getItemPosition(Object object) {
//        // refresh all fragments when data set changed
//        return PagerAdapter.POSITION_NONE;
//    }
//
//
//    @Override
//    public long getItemId(int position) {
//        // give an ID different from position when position has been changed
//        return baseId + position;
//    }
//
//    /**
//     * Notify that the position of a fragment has been changed.
//     * Create a new ID for each position to force recreation of the fragment
//     * @param n number of items which have been changed
//     */
//    public void notifyChanges() {
//        // shift the ID returned by getItemId outside the range of all previous fragments
//        baseId += getCount() + 1;
//        notifyDataSetChanged();
//    }
}
