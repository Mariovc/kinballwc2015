package com.mvc.kinballwc.ui.adapter;

/**
 * Author: Mario Velasco Casquero
 * Date: 31/07/2015
 * Email: m3ario@gmail.com
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.MatchPeriod;
import com.mvc.kinballwc.ui.fragment.PeriodFragment;

import java.util.List;

public class PeriodFragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<MatchPeriod> periods;

    public PeriodFragmentAdapter(FragmentManager fm, Context context, List<MatchPeriod> periods) {
        super(fm);
        this.context = context;
        this.periods = periods;
    }

    @Override
    public Fragment getItem(int position) {
        return PeriodFragment.newInstance(periods.get(position));
    }

    @Override
    public int getCount() {
        return periods.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.period_title, position+1);
    }

    public void setPeriods(List<MatchPeriod> periods) {
        this.periods = periods;
        notifyDataSetChanged();
    }
}
