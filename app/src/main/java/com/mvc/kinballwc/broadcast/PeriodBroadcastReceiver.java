package com.mvc.kinballwc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.mvc.kinballwc.application.App;
import com.mvc.kinballwc.ui.activity.MatchActivity;
import com.mvc.kinballwc.ui.fragment.PeriodFragment;

import java.util.Random;

/**
 * Author: Mario Velasco Casquero
 * Date: 15/08/2015
 * Email: m3ario@gmail.com
 */
public class PeriodBroadcastReceiver extends BroadcastReceiver {

    public static final String PERIOD_INTENT_ACTION = "com.mvc.kinballwc.PERIOD_UPDATE";

    public static final String FIELD_PERIOD_ID = "periodId";
    public static final String FIELD_TEAM_POSITION = "teamPos";
    public static final String FIELD_SCORE = "score";
    public static final String FIELD_UPDATE = "update";
    public static final String FIELD_ADD = "add";
    public static final String FIELD_REMOVE = "remove";
    public static final String FIELD_REFRESH = "refresh";
    public static final String FIELD_ACTION = "action";

    private MatchActivity mMatchActivity;

    public PeriodBroadcastReceiver(MatchActivity matchActivity) {
        this.mMatchActivity = matchActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra(FIELD_ACTION);
        String periodId = intent.getStringExtra(FIELD_PERIOD_ID);
        if (action.equals(FIELD_UPDATE)) {
            int teamPos = intent.getIntExtra(FIELD_TEAM_POSITION, 0);
            int score = intent.getIntExtra(FIELD_SCORE, 0);
            mMatchActivity.onUpdatePeriod(periodId, teamPos, score);
        } else if (action.equals(FIELD_ADD)) {
            mMatchActivity.onAddPeriod(periodId);
        } else if (action.equals(FIELD_REMOVE)) {
            mMatchActivity.onRemovePeriod(periodId);
        } else if (action.equals(FIELD_REFRESH)) {
            mMatchActivity.onRefresh();
        }
    }
}
