package com.mvc.kinballwc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mvc.kinballwc.R;
import com.mvc.kinballwc.application.App;
import com.mvc.kinballwc.broadcast.PeriodBroadcastReceiver;
import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.model.MatchPeriod;
import com.mvc.kinballwc.model.MatchPoints;
import com.mvc.kinballwc.model.Team;
import com.mvc.kinballwc.ui.adapter.PeriodFragmentAdapter;
import com.mvc.kinballwc.ui.fragment.PeriodFragment;
import com.mvc.kinballwc.utils.PushUtils;
import com.mvc.kinballwc.utils.Utils;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.viewpagerindicator.TitlePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MatchActivity extends BaseActivity {

    public static final String TAG = "MatchActivity";
    public static final String MATCH_ID_EXTRA = "matchId";


    private Match mMatch;
    private String mMatchId;

    private PeriodBroadcastReceiver broadcastReceiver;
    private boolean subscribed = false;
    private boolean isFirstLoad = true;
    private int secretTimesClicked = 0;
    private int pageSelected;
    private Timer timer;
    private Animation animation;

    private ViewPager mPager;
    private PeriodFragmentAdapter mAdapter;
    private TitlePageIndicator mIndicator;

    private TextView titleTV;
    private TextView courtTV;
    private TextView dateTV;
    private TextView hourTV;
    private TextView team1NameTV;
    private TextView team2NameTV;
    private TextView team3NameTV;
    private ImageView team1LogoIV;
    private ImageView team2LogoIV;
    private ImageView team3LogoIV;
    private EditText team1PeriodPointsTV;
    private EditText team2PeriodPointsTV;
    private EditText team3PeriodPointsTV;
    private EditText team1MatchPointsTV;
    private EditText team2MatchPointsTV;
    private EditText team3MatchPointsTV;
    private EditText team1SportPointsTV;
    private EditText team2SportPointsTV;
    private EditText team3SportPointsTV;
    private EditText team1TotalPointsTV;
    private EditText team2TotalPointsTV;
    private EditText team3TotalPointsTV;
    private TextView liveTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        titleTV = (TextView) findViewById(R.id.matchTitleTV);
        courtTV = (TextView) findViewById(R.id.matchCourtTV);
        dateTV = (TextView) findViewById(R.id.matchDateTV);
        hourTV = (TextView) findViewById(R.id.matchHourTV);
        team1NameTV = (TextView) findViewById(R.id.matchTeam1NameTV);
        team2NameTV = (TextView) findViewById(R.id.matchTeam2NameTV);
        team3NameTV = (TextView) findViewById(R.id.matchTeam3NameTV);
        team1LogoIV = (ImageView) findViewById(R.id.matchTeam1LogoIV);
        team2LogoIV = (ImageView) findViewById(R.id.matchTeam2LogoIV);
        team3LogoIV = (ImageView) findViewById(R.id.matchTeam3LogoIV);
        team1PeriodPointsTV = (EditText) findViewById(R.id.matchTeam1PeriodPointsTV);
        team2PeriodPointsTV = (EditText) findViewById(R.id.matchTeam2PeriodPointsTV);
        team3PeriodPointsTV = (EditText) findViewById(R.id.matchTeam3PeriodPointsTV);
        team1MatchPointsTV = (EditText) findViewById(R.id.matchTeam1MatchPointsTV);
        team2MatchPointsTV = (EditText) findViewById(R.id.matchTeam2MatchPointsTV);
        team3MatchPointsTV = (EditText) findViewById(R.id.matchTeam3MatchPointsTV);
        team1SportPointsTV = (EditText) findViewById(R.id.matchTeam1SportPointsTV);
        team2SportPointsTV = (EditText) findViewById(R.id.matchTeam2SportPointsTV);
        team3SportPointsTV = (EditText) findViewById(R.id.matchTeam3SportPointsTV);
        team1TotalPointsTV = (EditText) findViewById(R.id.matchTeam1TotalPointsTV);
        team2TotalPointsTV = (EditText) findViewById(R.id.matchTeam2TotalPointsTV);
        team3TotalPointsTV = (EditText) findViewById(R.id.matchTeam3TotalPointsTV);
        titleTV.setOnClickListener(onSecretClicked);
        setPeriodViewPager();

        liveTV = (TextView) findViewById(R.id.matchLiveTV);
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_fade_out);

        mMatchId = getIntent().getStringExtra(MATCH_ID_EXTRA);
    }

    private void setButtons() {
        Button upScore1Button = (Button) findViewById(R.id.upScore1Button);
        Button upScore2Button = (Button) findViewById(R.id.upScore2Button);
        Button upScore3Button = (Button) findViewById(R.id.upScore3Button);
        Button downScore1Button = (Button) findViewById(R.id.downScore1Button);
        Button downScore2Button = (Button) findViewById(R.id.downScore2Button);
        Button downScore3Button = (Button) findViewById(R.id.downScore3Button);
        View upButtonsLayout = findViewById(R.id.upButtonsLayout);
        View downButtonsLayout = findViewById(R.id.downButtonsLayout);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button removeButton = (Button) findViewById(R.id.removeButton);
        Button asyncButton = (Button) findViewById(R.id.asyncButton);
        Button instantButton = (Button) findViewById(R.id.instantButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        View view = findViewById(R.id.teamsRelativeLayout);
        if (App.allowEdit) {
            upButtonsLayout.setVisibility(View.VISIBLE);
            downButtonsLayout.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
            removeButton.setVisibility(View.VISIBLE);
            asyncButton.setVisibility(View.VISIBLE);
            instantButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);

            upScore1Button.setOnClickListener(new OnButtonClick(1, true));
            upScore2Button.setOnClickListener(new OnButtonClick(2, true));
            upScore3Button.setOnClickListener(new OnButtonClick(3, true));
            downScore1Button.setOnClickListener(new OnButtonClick(1, false));
            downScore2Button.setOnClickListener(new OnButtonClick(2, false));
            downScore3Button.setOnClickListener(new OnButtonClick(3, false));

            addButton.setOnClickListener(onAddClick);
            removeButton.setOnClickListener(onRemoveClick);
            asyncButton.setOnClickListener(onModeAsyncClick);
            instantButton.setOnClickListener(onModeInstantClick);
            saveButton.setOnClickListener(onSaveClick);
            view.setOnClickListener(onLiveClick);
            view.setVisibility(View.VISIBLE);

            enableEditText(team1PeriodPointsTV);
            enableEditText(team2PeriodPointsTV);
            enableEditText(team3PeriodPointsTV);
            enableEditText(team1MatchPointsTV);
            enableEditText(team2MatchPointsTV);
            enableEditText(team3MatchPointsTV);
            enableEditText(team1SportPointsTV);
            enableEditText(team2SportPointsTV);
            enableEditText(team3SportPointsTV);
            enableEditText(team1TotalPointsTV);
            enableEditText(team2TotalPointsTV);
            enableEditText(team3TotalPointsTV);

        } else {
            upButtonsLayout.setVisibility(View.GONE);
            downButtonsLayout.setVisibility(View.GONE);
            addButton.setVisibility(View.GONE);
            removeButton.setVisibility(View.GONE);
            asyncButton.setVisibility(View.GONE);
            instantButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            view.setVisibility(View.GONE);

            disableEditText(team1PeriodPointsTV);
            disableEditText(team2PeriodPointsTV);
            disableEditText(team3PeriodPointsTV);
            disableEditText(team1MatchPointsTV);
            disableEditText(team2MatchPointsTV);
            disableEditText(team3MatchPointsTV);
            disableEditText(team1SportPointsTV);
            disableEditText(team2SportPointsTV);
            disableEditText(team3SportPointsTV);
            disableEditText(team1TotalPointsTV);
            disableEditText(team2TotalPointsTV);
            disableEditText(team3TotalPointsTV);
        }
    }
    private void disableEditText(EditText editText){
        editText.setEnabled(false);
        editText.setInputType(InputType.TYPE_NULL);
    }
    private void enableEditText(EditText editText) {
        editText.setEnabled(true);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
    private void hideKeyBoard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setPeriodViewPager() {
        mAdapter = new PeriodFragmentAdapter(getSupportFragmentManager(),
                this, new ArrayList<MatchPeriod>(), null);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TitlePageIndicator indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFooterIndicatorStyle(TitlePageIndicator.IndicatorStyle.Triangle);
        mIndicator = indicator;

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void getMatch(String matchId) {
        if (mMatchId == null) {
            return;
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
        query.include("team1");
        query.include("team2");
        query.include("team3");
        query.include("team1Points");
        query.include("team2Points");
        query.include("team3Points");
        query.include("periods");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
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
        setToolbarTitle(Utils.getTranslatedCategory(this, mMatch.getCategory()));
        titleTV.setText(mMatch.getTitle());
        courtTV.setText(mMatch.getCourtString());
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat(getString(R.string.hour_format), Locale.getDefault());
        if (mMatch.getDateToShow() == null) {
            dateTV.setText(dateFormat.format(mMatch.getDate()));
            hourTV.setText(hourFormat.format(mMatch.getDate()));
        } else {
            dateTV.setText(dateFormat.format(mMatch.getDateToShow()));
            hourTV.setText(hourFormat.format(mMatch.getDateToShow()));
        }
        Team team1 = mMatch.getTeam1();
        Team team2 = mMatch.getTeam2();
        Team team3 = mMatch.getTeam3();
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
        MatchPoints matchPoints1 = mMatch.getTeam1Points();
        MatchPoints matchPoints2 = mMatch.getTeam2Points();
        MatchPoints matchPoints3 = mMatch.getTeam3Points();
        if (matchPoints1 != null) {
            team1PeriodPointsTV.setText(String.valueOf(matchPoints1.getWonPeriods()));
            team1MatchPointsTV.setText(String.valueOf(matchPoints1.getMatchPoints()));
            team1SportPointsTV.setText(String.valueOf(matchPoints1.getSportsmanshipPoints()));
            team1TotalPointsTV.setText(String.valueOf(matchPoints1.getTotalPoints()));
        }
        if (matchPoints2 != null) {
            team2PeriodPointsTV.setText(String.valueOf(matchPoints2.getWonPeriods()));
            team2MatchPointsTV.setText(String.valueOf(matchPoints2.getMatchPoints()));
            team2SportPointsTV.setText(String.valueOf(matchPoints2.getSportsmanshipPoints()));
            team2TotalPointsTV.setText(String.valueOf(matchPoints2.getTotalPoints()));
        }
        if (matchPoints3 != null) {
            team3PeriodPointsTV.setText(String.valueOf(matchPoints3.getWonPeriods()));
            team3MatchPointsTV.setText(String.valueOf(matchPoints3.getMatchPoints()));
            team3SportPointsTV.setText(String.valueOf(matchPoints3.getSportsmanshipPoints()));
            team3TotalPointsTV.setText(String.valueOf(matchPoints3.getTotalPoints()));
        }

        List<MatchPeriod> periods = mMatch.getPeriods();
        if (periods == null) {
            periods = new ArrayList<>();
        }
        if (periods.size() == 0) {
            MatchPeriod emptyPeriod = (MatchPeriod) ParseObject.create("MatchPeriod");
            periods.add(emptyPeriod);
            mMatch.saveInBackground();
        }
        updatePeriods(periods, mMatch.getChannelForPush());
        if (isFirstLoad) {
            isFirstLoad = false;
            mPager.setCurrentItem(periods.size() - 1);
        } else {
            mPager.setCurrentItem(pageSelected);
        }
        if (mMatch.isLive()) {
            liveTV.startAnimation(animation);
        } else {
            liveTV.clearAnimation();
        }

        registerReceiver();
        subscribeToPush();
    }

    private void updatePeriods(List<MatchPeriod> periods, String channelPush) {
        mMatch.setPeriods(periods);
        mAdapter = new PeriodFragmentAdapter(getSupportFragmentManager(),
                this, periods, channelPush);
        mPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtons();
        hideKeyBoard();
        getMatch(mMatchId);
        setTimer();
        registerReceiver();
        subscribeToPush();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        stopTimer();
        unregisterReceiver();
        unsubscribeToPush();
    }

    private void stopTimer() {
        timer.cancel();
        timer = null;
    }

    private void setTimer() {
        timer = new Timer();
        timer.schedule(new RefreshTask(), App.refreshTime * 1000);
    }

    public class RefreshTask extends TimerTask {
        @Override
        public void run() {
            Log.d(TAG, "Refreshing match task, useGCM: "+ App.useGCM + " time: " + App.refreshTime);
            if (!App.useGCM) {
                getMatch(mMatchId);
            }
            timer.schedule(new RefreshTask(), App.refreshTime * 1000);
        }
    }

    private void registerReceiver() {
        if (broadcastReceiver == null && mMatch != null) {
            broadcastReceiver = new PeriodBroadcastReceiver(this);
            registerReceiver(broadcastReceiver, new IntentFilter(PeriodBroadcastReceiver.PERIOD_INTENT_ACTION));
        }
    }

    private void unregisterReceiver() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void subscribeToPush() {
        if (!subscribed && mMatch != null) {
            ParsePush.subscribeInBackground(mMatch.getChannelForPush(), new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully subscribed to the channel: " + mMatch.getObjectId());
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push: " + mMatch.getObjectId(), e);
                    }
                }
            });
            subscribed = true;
        }
    }

    private void unsubscribeToPush() {
        if (subscribed) {
            ParsePush.unsubscribeInBackground(mMatch.getChannelForPush(), new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully subscribed to the channel: " + mMatch.getObjectId());
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push: " + mMatch.getObjectId(), e);
                    }
                }
            });
            subscribed = false;
        }
    }

    public void onUpdatePeriod(String periodId, int teamPos, int score) {
        int periodToUpdate = -1;
        int numPeriods = mMatch.getPeriods().size();
        for (int i = 0; i < numPeriods; i++) {
            MatchPeriod period = mMatch.getPeriods().get(i);
            if (period.getObjectId().equals(periodId)) {
                periodToUpdate = i;
                break;
            }
        }
        if (periodToUpdate > -1) {
            PeriodFragment periodFragment = (PeriodFragment) mAdapter.getActiveFragment(mPager, periodToUpdate);
            periodFragment.setScore(teamPos, score);
        }
    }

    public void onAddPeriod(String periodId) {
        boolean periodExists = false;
        final MatchPeriod emptyPeriod = (MatchPeriod) ParseObject.create("MatchPeriod");
        emptyPeriod.setObjectId(periodId);
        List<MatchPeriod> periods = mMatch.getPeriods();
        for (MatchPeriod period : periods) {
            if (period.getObjectId().equals(periodId)) {
                periodExists = true;
                break;
            }
        }
        if (!periodExists) {
            periods.add(emptyPeriod);
            updatePeriods(periods, mMatch.getChannelForPush());
            mPager.setCurrentItem(periods.size() - 1);
        }
    }

    public void onRemovePeriod(String periodId) {
        boolean periodExists = false;
        MatchPeriod periodToRemove = null;
        List<MatchPeriod> periods = mMatch.getPeriods();
        for (MatchPeriod period : periods) {
            if (period.getObjectId().equals(periodId)) {
                periodExists = true;
                periodToRemove = period;
                break;
            }
        }
        if (periodExists) {
            periods.remove(periodToRemove);
            updatePeriods(periods, mMatch.getChannelForPush());
            mPager.setCurrentItem(periods.size() - 1);
        }
    }

    public void onRefresh() {
        getMatch(mMatchId);
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

    class OnTeamClick implements OnClickListener {

        private Team mTeam;

        public OnTeamClick(Team mTeam) {
            this.mTeam = mTeam;
        }

        @Override
        public void onClick(View v) {
            launchTeamActivity(mTeam);
        }
    }

    class OnButtonClick implements OnClickListener {

        private int teamPos;
        private boolean increment;

        public OnButtonClick(int teamPos, boolean increment) {
            this.teamPos = teamPos;
            this.increment = increment;
        }

        @Override
        public void onClick(View v) {
            if (mMatch != null && mPager != null) {
                if (!mMatch.isLive()) {
                    mMatch.setState(Match.LIVE);
                    liveTV.setVisibility(View.VISIBLE);
                    liveTV.startAnimation(animation);
                }
                int currentPeriod = mPager.getCurrentItem();
                PeriodFragment periodFragment =
                        (PeriodFragment) mAdapter.getActiveFragment(mPager, currentPeriod);
                if (increment) {
                    periodFragment.incrementScore(teamPos);
                } else {
                    periodFragment.decrementScore(teamPos);
                }
            }
        }
    }

    private OnClickListener onAddClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMatch != null) {
                final MatchPeriod emptyPeriod = (MatchPeriod) ParseObject.create("MatchPeriod");
                List<MatchPeriod> periods = mMatch.getPeriods();
                periods.add(emptyPeriod);
                mMatch.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d(TAG, "period added and saved: " + emptyPeriod.getObjectId());
                            PushUtils.sendAddPush(mMatch.getChannelForPush(), emptyPeriod.getObjectId());
                        }
                    }
                });
                updatePeriods(periods, mMatch.getChannelForPush());
                mPager.setCurrentItem(periods.size() - 1);
            }
        }
    };

    private OnClickListener onRemoveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMatch != null && mPager != null) {
                boolean periodExists = false;
                MatchPeriod periodToRemove = null;
                int currentPeriod = mPager.getCurrentItem();
                PeriodFragment periodFragment =
                        (PeriodFragment) mAdapter.getActiveFragment(mPager, currentPeriod);
                String periodId = periodFragment.getPeriod().getObjectId();
                if(periodId == null) {
                    Toast.makeText(MatchActivity.this, "Error. Try again later", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<MatchPeriod> periods = mMatch.getPeriods();
                for (MatchPeriod period : periods) {
                    if (period.getObjectId().equals(periodId)) {
                        periodExists = true;
                        periodToRemove = period;
                        break;
                    }
                }
                if (periodExists) {
                    periods.remove(periodToRemove);
                    updatePeriods(periods, mMatch.getChannelForPush());
                    mPager.setCurrentItem(periods.size() - 1);
                    mMatch.saveInBackground();
                    PushUtils.sendRemovePush(mMatch.getChannelForPush(), periodToRemove.getObjectId());
                }
            }
        }
    };

    private OnClickListener onModeAsyncClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PushUtils.sendConfigPush(false, 10);
            Toast.makeText(MatchActivity.this, "Async mode active", Toast.LENGTH_SHORT).show();
        }
    };

    private OnClickListener onModeInstantClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PushUtils.sendConfigPush(true, 10);
            Toast.makeText(MatchActivity.this, "Instant mode active", Toast.LENGTH_SHORT).show();
        }
    };

    private OnClickListener onSecretClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {
            secretTimesClicked++;
            if (secretTimesClicked > 10) {
                secretTimesClicked = 0;
                Intent intent = new Intent(MatchActivity.this, SecretActivity.class);
                startActivity(intent);
            }
        }
    };

    private OnClickListener onLiveClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMatch.isLive()) {
                mMatch.setState(Match.FINISHED);
                liveTV.setVisibility(View.INVISIBLE);
                liveTV.clearAnimation();
            } else {
                mMatch.setState(Match.LIVE);
                liveTV.setVisibility(View.VISIBLE);
                liveTV.startAnimation(animation);
            }
        }
    };

    private OnClickListener onSaveClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            saveData();
        }
    };

    private void saveData() {
        if (mMatch != null && App.allowEdit) {
            MatchPoints matchPoints1 = mMatch.getTeam1Points();
            if (matchPoints1 == null) {
                matchPoints1 = new MatchPoints();
            }
            matchPoints1.setWonPeriods(parseStringToInt(team1PeriodPointsTV.getText().toString()));
            matchPoints1.setMatchPoints(parseStringToInt(team1MatchPointsTV.getText().toString()));
            matchPoints1.setSportsmanshipPoints(parseStringToInt(team1SportPointsTV.getText().toString()));
            matchPoints1.setTotalPoints(parseStringToInt(team1TotalPointsTV.getText().toString()));
            MatchPoints matchPoints2 = mMatch.getTeam2Points();
            if (matchPoints2 == null) {
                matchPoints2 = new MatchPoints();
            }
            matchPoints2.setWonPeriods(parseStringToInt(team2PeriodPointsTV.getText().toString()));
            matchPoints2.setMatchPoints(parseStringToInt(team2MatchPointsTV.getText().toString()));
            matchPoints2.setSportsmanshipPoints(parseStringToInt(team2SportPointsTV.getText().toString()));
            matchPoints2.setTotalPoints(parseStringToInt(team2TotalPointsTV.getText().toString()));
            MatchPoints matchPoints3 = mMatch.getTeam3Points();
            if (matchPoints3 == null) {
                matchPoints3 = new MatchPoints();
            }
            matchPoints3.setWonPeriods(parseStringToInt(team3PeriodPointsTV.getText().toString()));
            matchPoints3.setMatchPoints(parseStringToInt(team3MatchPointsTV.getText().toString()));
            matchPoints3.setSportsmanshipPoints(parseStringToInt(team3SportPointsTV.getText().toString()));
            matchPoints3.setTotalPoints(parseStringToInt(team3TotalPointsTV.getText().toString()));
            mMatch.setTeam1Points(matchPoints1);
            mMatch.setTeam2Points(matchPoints2);
            mMatch.setTeam3Points(matchPoints3);
            mMatch.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        PushUtils.sendRefreshPush(mMatch.getChannelForPush());
                    }
                }
            });
            Toast.makeText(MatchActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            hideKeyBoard();
        }
    }

    private int parseStringToInt(String stringInt){
        int num = 0;
        try {
            num = Integer.parseInt(stringInt);
        } catch (NumberFormatException e) {
            Log.d("", "error parsing points");
        }
        return num;
    }
}
