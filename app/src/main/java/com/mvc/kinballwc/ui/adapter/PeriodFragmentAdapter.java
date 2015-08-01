package com.mvc.kinballwc.ui.adapter;

/**
 * Created by Mario on 31/07/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mvc.kinballwc.R;
import com.viewpagerindicator.IconPagerAdapter;

public class PeriodFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "Period 1", "Period 2", "Period 3", "Period 4", };
    protected static final int[] ICONS = new int[] {
            R.drawable.abc_ab_share_pack_mtrl_alpha,
            R.drawable.abc_ab_share_pack_mtrl_alpha,
            R.drawable.abc_ab_share_pack_mtrl_alpha,
            R.drawable.abc_ab_share_pack_mtrl_alpha
    };

    private int mCount = CONTENT.length;

    public PeriodFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PeriodFragmentAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
        return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}
