package com.mvc.kinballwc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.model.MatchPeriod;
import com.mvc.kinballwc.model.MatchPoints;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.adapter.PeriodFragmentAdapter;
import com.mvc.kinballwc.utils.Utils;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.viewpagerindicator.TitlePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MatchActivity extends BaseActivity {

    public static final String TAG = "MatchActivity";
    public static final String MATCH_ID_EXTRA = "matchId";


    private Match mMatch;

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
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
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
        if (isActivityDestroyed) {
            Log.d(TAG, "Activity is destroyed after Parse query");
            return;
        }
        mMatch = match;
        setToolbarTitle(match.getTitle());
        categoryTV.setText(Utils.getTranslatedCategory(this, match.getCategory()));
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat(getString(R.string.hour_format), Locale.getDefault());
        dateTV.setText(dateFormat.format(match.getDate()));
        hourTV.setText(hourFormat.format(match.getDate()));
        Team team1 = match.getTeam1();
        Team team2 = match.getTeam2();
        Team team3 = match.getTeam3();
        if (team1 != null) {
            team1NameTV.setText(team1.getName());
            loadImage(team1.getLogo(), team1LogoIV);
            team1NameTV.setOnClickListener(new OnTeamClick(team1));
            team1LogoIV.setOnClickListener(new OnTeamClick(team1));
        }
        if (team2 != null) {
            team2NameTV.setText(team2.getName());
            loadImage(team2.getLogo(), team2LogoIV);
            team2NameTV.setOnClickListener(new OnTeamClick(team2));
            team2LogoIV.setOnClickListener(new OnTeamClick(team2));
        }
        if (team3 != null) {
            team3NameTV.setText(team3.getName());
            loadImage(team3.getLogo(), team3LogoIV);
            team3NameTV.setOnClickListener(new OnTeamClick(team3));
            team3LogoIV.setOnClickListener(new OnTeamClick(team3));
        }
        MatchPoints matchPoints1 = match.getTeam1Points();
        MatchPoints matchPoints2 = match.getTeam2Points();
        MatchPoints matchPoints3 = match.getTeam3Points();
        if (matchPoints1 != null) {
            team1PeriodPointsTV.setText(String.valueOf(matchPoints1.getWonPeriods()));
            team1MatchPointsTV.setText(String.valueOf(matchPoints1.getMatchPoints()));
            team1SportPointsTV.setText(String.valueOf(matchPoints1.getSportsmanshipPoints()));
            team1TotalPointsTV.setText(String.valueOf(matchPoints1.getTotalPoints()));
        }
        if (matchPoints2 != null) {
            team2PeriodPointsTV.setText(String.valueOf(matchPoints3.getWonPeriods()));
            team2MatchPointsTV.setText(String.valueOf(matchPoints3.getMatchPoints()));
            team2SportPointsTV.setText(String.valueOf(matchPoints3.getSportsmanshipPoints()));
            team2TotalPointsTV.setText(String.valueOf(matchPoints3.getTotalPoints()));
        }
        if (matchPoints3 != null) {
            team3PeriodPointsTV.setText(String.valueOf(matchPoints3.getWonPeriods()));
            team3MatchPointsTV.setText(String.valueOf(matchPoints3.getMatchPoints()));
            team3SportPointsTV.setText(String.valueOf(matchPoints3.getSportsmanshipPoints()));
            team3TotalPointsTV.setText(String.valueOf(matchPoints3.getTotalPoints()));
        }

        List<MatchPeriod> periods = match.getPeriods();
        if (periods == null) {
            periods = new ArrayList<>();
        }
        if (periods.size() == 0) {
            MatchPeriod emptyPeriod = (MatchPeriod) ParseObject.create("MatchPeriod");
            periods.add(emptyPeriod);
            match.setPeriods(periods);
            match.saveInBackground();
        }
        mAdapter.setPeriods(periods);
        mPager.setCurrentItem(periods.size() - 1);
    }

    private void loadImage(String url, ImageView imageView) {
        Glide.with(this)
                .load(url)
//                .placeholder(R.drawable.loading_spinner)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    private void launchTeamActivity(Team team) {
        Intent intent = new Intent(this, TeamActivity.class);
        intent.putExtra(TeamActivity.EXTRA_TEAM_ID, team.getObjectId());
        startActivity(intent);
    }

    class OnTeamClick implements View.OnClickListener {

        private Team mTeam;

        public OnTeamClick(Team mTeam) {
            this.mTeam = mTeam;
        }

        @Override
        public void onClick(View v) {
            launchTeamActivity(mTeam);
        }
    }
}
