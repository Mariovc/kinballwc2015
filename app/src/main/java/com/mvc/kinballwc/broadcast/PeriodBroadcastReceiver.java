package com.mvc.kinballwc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

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

    private MatchActivity mMatchActivity;

    public PeriodBroadcastReceiver(MatchActivity matchActivity) {
        this.mMatchActivity = matchActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        String periodId = intent.getStringExtra("periodId");
        int teamPos = intent.getIntExtra("teamPos", 0);
        int score = intent.getIntExtra("score", 0);
        mMatchActivity.onUpdatePeriod(periodId, teamPos, score);
    }
}
