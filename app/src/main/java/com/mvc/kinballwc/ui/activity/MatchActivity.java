package com.mvc.kinballwc.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.ui.adapter.PeriodFragmentAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.viewpagerindicator.TitlePageIndicator;

public class MatchActivity extends BaseActivity {

    public static final String MATCH_ID_EXTRA = "matchId";

    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private TitlePageIndicator mIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match2);

        setPeriodViewPager();

        String matchId = getIntent().getStringExtra(MATCH_ID_EXTRA);
        if (matchId != null) {
            getMatch(matchId);
        }

    }

    private void setPeriodViewPager() {
        mAdapter = new PeriodFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
        mIndicator = indicator;
    }


    private void getMatch(String matchId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
        query.getInBackground(matchId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Match match = (Match) object;
                    onMatchReceived(match);
                } else {
                    // something went wrong
                }
            }
        });
    }

    private void onMatchReceived(Match match) {
        setToolbarTitle(match.getTitle());

    }


}
