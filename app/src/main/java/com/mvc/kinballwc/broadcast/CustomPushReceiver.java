package com.mvc.kinballwc.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mvc.kinballwc.ui.activity.HomeActivity;
import com.mvc.kinballwc.utils.NotificationUtils;
import com.mvc.kinballwc.utils.PushUtils;
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
                PushUtils.parsePushJson(context, json);
            }


        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }



}
