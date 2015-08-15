package com.mvc.kinballwc.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mvc.kinballwc.ui.activity.HomeActivity;
import com.mvc.kinballwc.utils.NotificationUtils;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Author: Mario Velasco Casquero
 * Date: 15/08/2015
 * Email: m3ario@gmail.com
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {
    private final String TAG = CustomPushReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;

    private Intent parseIntent;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.d(TAG, "Push received: " + json);

            parseIntent = intent;

            String plainText = json.optString("alert");
            if (!TextUtils.isEmpty(plainText)) {
                super.onPushReceive(context, intent);
            } else {
                parsePushJson(context, json);
            }


        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json) {
        try {

            String periodId = json.getString("periodId");
            int teamPos = json.getInt("teamPos");
            int score = json.getInt("score");

//            Toast.makeText(context, "received push", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PeriodBroadcastReceiver.PERIOD_INTENT_ACTION);
            intent.putExtra("periodId", periodId);
            intent.putExtra("teamPos", teamPos);
            intent.putExtra("score", score);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }


}
