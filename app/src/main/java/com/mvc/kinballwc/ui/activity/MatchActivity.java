package com.mvc.kinballwc.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.model.MatchPeriod;
import com.mvc.kinballwc.ui.adapter.PeriodFragmentAdapter;
import com.mvc.kinballwc.utils.Utils;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MatchActivity extends BaseActivity {

    public static final String MATCH_ID_EXTRA = "matchId";

    private ViewPager mPager;
    private PeriodFragmentAdapter mAdapter;
    private TitlePageIndicator mIndicator;

    private TextView categoryTV;
    private TextView dateTV;
    private TextView hourTV;
    private TextView team1NameTV;
    private TextView team2NameTV;
    private TextView team3NameTV;
    private ImageView team1LogoIV;
    private ImageView team2LogoIV;
    private ImageView team3LogoIV;
    private TextView team1PeriodPointsTV;
    private TextView team2PeriodPointsTV;
    private TextView team3PeriodPointsTV;
    private TextView team1MatchPointsTV;
    private TextView team2MatchPointsTV;
    private TextView team3MatchPointsTV;
    private TextView team1SportPointsTV;
    private TextView team2SportPointsTV;
    private TextView team3SportPointsTV;
    private TextView team1TotalPointsTV;
    private TextView team2TotalPointsTV;
    private TextView team3TotalPointsTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        categoryTV = (TextView) findViewById(R.id.matchCategoryTV);
        dateTV = (TextView) findViewById(R.id.matchDateTV);
        hourTV = (TextView) findViewById(R.id.matchHourTV);
        team1NameTV = (TextView) findViewById(R.id.matchTeam1NameTV);
        team2NameTV = (TextView) findViewById(R.id.matchTeam2NameTV);
        team3NameTV = (TextView) findViewById(R.id.matchTeam3NameTV);
        team1LogoIV = (ImageView) findViewById(R.id.matchTeam1LogoIV);
        team2LogoIV = (ImageView) findViewById(R.id.matchTeam2LogoIV);
        team3LogoIV = (ImageView) findViewById(R.id.matchTeam3LogoIV);
        team1PeriodPointsTV = (TextView) findViewById(R.id.matchTeam1PeriodPointsTV);
        team2PeriodPointsTV = (TextView) findViewById(R.id.matchTeam2PeriodPointsTV);
        team3PeriodPointsTV = (TextView) findViewById(R.id.matchTeam3PeriodPointsTV);
        team1MatchPointsTV = (TextView) findViewById(R.id.matchTeam1MatchPointsTV);
        team2MatchPointsTV = (TextView) findViewById(R.id.matchTeam2MatchPointsTV);
        team3MatchPointsTV = (TextView) findViewById(R.id.matchTeam3MatchPointsTV);
        team1SportPointsTV = (TextView) findViewById(R.id.matchTeam1SportPointsTV);
        team2SportPointsTV = (TextView) findViewById(R.id.matchTeam2SportPointsTV);
        team3SportPointsTV = (TextView) findViewById(R.id.matchTeam3SportPointsTV);
        team1TotalPointsTV = (TextView) findViewById(R.id.matchTeam1TotalPointsTV);
        team2TotalPointsTV = (TextView) findViewById(R.id.matchTeam2TotalPointsTV);
        team3TotalPointsTV = (TextView) findViewById(R.id.matchTeam3TotalPointsTV);
        setPeriodViewPager();


        String matchId = getIntent().getStringExtra(MATCH_ID_EXTRA);
        if (matchId != null) {
            getMatch(matchId);
        }

    }

    private void setPeriodViewPager() {
        mAdapter = new PeriodFragmentAdapter(getSupportFragmentManager(),
                this, new ArrayList<MatchPeriod>());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
        mIndicator = indicator;
    }


    private void getMatch(String matchId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
        query.include("team1");
        query.include("team2");
        query.include("team3");
        query.include("team1Points");
        query.include("team2Points");
        query.include("team3Points");
        query.include("periods");
        query.getInBackground(matchId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Match match = (Match) object;
                    onMatchReceived(match);
                } else {
                    // TODO something went wrong
                }
            }
        });
    }

    private void onMatchReceived(Match match) {
        setToolbarTitle(match.getTitle());
        categoryTV.setText(Utils.getCategoryName(this, match.getCategory()));
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat(getString(R.string.hour_format), Locale.getDefault());
        dateTV.setText(dateFormat.format(match.getDate()));
        hourTV.setText(hourFormat.format(match.getDate()));
        team1NameTV.setText(match.getTeam1().getName());
        team2NameTV.setText(match.getTeam2().getName());
        team3NameTV.setText(match.getTeam3().getName());
        loadImage(match.getTeam1().getLogo(), team1LogoIV);
        loadImage(match.getTeam2().getLogo(), team2LogoIV);
        loadImage(match.getTeam3().getLogo(), team3LogoIV);
        team1PeriodPointsTV.setText(String.valueOf(match.getTeam1Points().getWonPeriods()));
        team2PeriodPointsTV.setText(String.valueOf(match.getTeam2Points().getWonPeriods()));
        team3PeriodPointsTV.setText(String.valueOf(match.getTeam3Points().getWonPeriods()));
        team1MatchPointsTV.setText(String.valueOf(match.getTeam1Points().getMatchPoints()));
        team2MatchPointsTV.setText(String.valueOf(match.getTeam2Points().getMatchPoints()));
        team3MatchPointsTV.setText(String.valueOf(match.getTeam3Points().getMatchPoints()));
        team1SportPointsTV.setText(String.valueOf(match.getTeam1Points().getSportsmanshipPoints()));
        team2SportPointsTV.setText(String.valueOf(match.getTeam2Points().getSportsmanshipPoints()));
        team3SportPointsTV.setText(String.valueOf(match.getTeam3Points().getSportsmanshipPoints()));
        team1TotalPointsTV.setText(String.valueOf(match.getTeam1Points().getTotalPoints()));
        team2TotalPointsTV.setText(String.valueOf(match.getTeam2Points().getTotalPoints()));
        team3TotalPointsTV.setText(String.valueOf(match.getTeam3Points().getTotalPoints()));
//        ParseObject period1 = ParseObject.create("MatchPeriod");
//        period1.put("team1Score", 11);
//        period1.put("team2Score", 9);
//        period1.put("team3Score", 7);
//        ParseObject period2 = ParseObject.create("MatchPeriod");
//        period2.put("team1Score", 4);
//        period2.put("team2Score", 4);
//        period2.put("team3Score", 4);
//        ArrayList<ParseObject> periods = new ArrayList<>();
//        periods.add(period1);
//        periods.add(period2);
//        match.put("periods", periods);
//        match.saveInBackground();
        List<MatchPeriod> periods = match.getPeriods();
        if (periods.size() == 0) {
            MatchPeriod emptyPeriod = (MatchPeriod) ParseObject.create("MatchPeriod");
            periods.add(emptyPeriod);
        }
        mAdapter.setPeriods(periods);
        mPager.setCurrentItem(periods.size() - 1);
    }

    private void loadImage(String url, ImageView imageView) {
        Glide.with(this)
                .load(url)
//                .placeholder(R.drawable.loading_spinner)
                .into(imageView);
    }


}
