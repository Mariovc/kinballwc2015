package com.mvc.kinballwc.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mvc.kinballwc.application.App;
import com.mvc.kinballwc.broadcast.PeriodBroadcastReceiver;
import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: Mario Velasco Casquero
 * Date: 15/08/2015
 * Email: m3ario@gmail.com
 */
public class PushUtils {

    private static final String TAG = "PushUtils";

    private static final String ACTION_CONFIG = "config";
    private static final String ACTION_UPDATE_PERIOD = "updateP";
    private static final String ACTION_ADD_PERIOD = "addP";
    private static final String ACTION_REMOVE_PERIOD = "removeP";
    private static final String ACTION_REFRESH = "refresh";

    private static final String FIELD_PERIOD_ID = "periodId";
    private static final String FIELD_TEAM_POSITION = "teamPos";
    private static final String FIELD_SCORE = "score";
    private static final String FIELD_UPDATE = "update";
    private static final String FIELD_ACTION = "action";
    private static final String FIELD_CONFIG = "config";
    private static final String FIELD_USE_GCM = "useGCM";
    private static final String FIELD_REFRESH_TIME = "time";


    public static void sendConfigPush(boolean useGCM, int time) {
        JSONObject configJson = new JSONObject();
        JSONObject mainJson = new JSONObject();
        try {
            configJson.put(FIELD_USE_GCM, useGCM);
            configJson.put(FIELD_REFRESH_TIME, time);

            mainJson.put(FIELD_CONFIG, configJson);
            mainJson.put(FIELD_ACTION, ACTION_CONFIG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ParsePush push = new ParsePush();
        push.setData(mainJson);
        push.sendInBackground();
    }

    public static void sendRefreshPush(String channelPush) {
        JSONObject json = new JSONObject();
        try {
            json.put(FIELD_ACTION, ACTION_REFRESH);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendPeriodPush(channelPush, json);
    }

    public static void sendUpdatePush(String channelPush, String periodId, int teamPos, int score) {
        JSONObject periodUpdateJson = new JSONObject();
        JSONObject mainJson = new JSONObject();
        try {
            periodUpdateJson.put(FIELD_PERIOD_ID, periodId);
            periodUpdateJson.put(FIELD_TEAM_POSITION, teamPos);
            periodUpdateJson.put(FIELD_SCORE, score);

            mainJson.put(FIELD_UPDATE, periodUpdateJson);
            mainJson.put(FIELD_ACTION, ACTION_UPDATE_PERIOD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendPeriodPush(channelPush, mainJson);
    }


    public static void sendAddPush(String channelPush, String periodId) {
        JSONObject newPeriodJson = new JSONObject();
        JSONObject mainJson = new JSONObject();
        try {
            newPeriodJson.put(FIELD_PERIOD_ID, periodId);

            mainJson.put(FIELD_UPDATE, newPeriodJson);
            mainJson.put(FIELD_ACTION, ACTION_ADD_PERIOD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendPeriodPush(channelPush, mainJson);
    }


    public static void sendRemovePush(String channelPush, String periodId) {
        JSONObject newPeriodJson = new JSONObject();
        JSONObject mainJson = new JSONObject();
        try {
            newPeriodJson.put(FIELD_PERIOD_ID, periodId);

            mainJson.put(FIELD_UPDATE, newPeriodJson);
            mainJson.put(FIELD_ACTION, ACTION_REMOVE_PERIOD);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendPeriodPush(channelPush, mainJson);
    }

    private static void sendPeriodPush(String channelPush, JSONObject json) {
        if (App.useGCM) {
            ParsePush push = new ParsePush();
            push.setChannel(channelPush);
            push.setData(json);
            push.sendInBackground();
        }
    }


    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    public static void parsePushJson(Context context, JSONObject json) {
        try {
            String action = json.getString(FIELD_ACTION);
            if (action.equals((ACTION_CONFIG))) {
                parseConfigPush(json);
            } else if (App.useGCM) {
                if (action.equals(ACTION_UPDATE_PERIOD)) {
                    parseUpdatePush(context, json);
                } else if (action.equals(ACTION_ADD_PERIOD)) {
                    parseAddPush(context, json);
                } else if (action.equals(ACTION_REMOVE_PERIOD)) {
                    parseRemovePush(context, json);
                } else if (action.equals(ACTION_REFRESH)) {
                    parseRefreshPush(context, json);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    private static void parseConfigPush(JSONObject json) throws JSONException {
        JSONObject configJson = json.getJSONObject(FIELD_CONFIG);
        boolean useGCM = configJson.optBoolean(FIELD_USE_GCM, App.useGCM);
        int time = configJson.optInt(FIELD_REFRESH_TIME);

        App.setRefreshConfig(useGCM, time);
    }

    private static void parseRefreshPush(Context context, JSONObject json) throws JSONException {
        Intent intent = new Intent(PeriodBroadcastReceiver.PERIOD_INTENT_ACTION);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_ACTION, PeriodBroadcastReceiver.FIELD_REFRESH);
        context.sendBroadcast(intent);
    }

    private static void parseUpdatePush(Context context, JSONObject json) throws JSONException {
        JSONObject updateJson = json.getJSONObject(FIELD_UPDATE);
        String periodId = updateJson.getString(FIELD_PERIOD_ID);
        int teamPos = updateJson.getInt(FIELD_TEAM_POSITION);
        int score = updateJson.getInt(FIELD_SCORE);

        Intent intent = new Intent(PeriodBroadcastReceiver.PERIOD_INTENT_ACTION);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_ACTION, PeriodBroadcastReceiver.FIELD_UPDATE);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_PERIOD_ID, periodId);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_TEAM_POSITION, teamPos);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_SCORE, score);
        context.sendBroadcast(intent);
    }

    private static void parseAddPush(Context context, JSONObject json) throws JSONException {
        JSONObject updateJson = json.getJSONObject(FIELD_UPDATE);
        String periodId = updateJson.getString(FIELD_PERIOD_ID);

        Intent intent = new Intent(PeriodBroadcastReceiver.PERIOD_INTENT_ACTION);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_ACTION, PeriodBroadcastReceiver.FIELD_ADD);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_PERIOD_ID, periodId);
        context.sendBroadcast(intent);
    }

    private static void parseRemovePush(Context context, JSONObject json) throws JSONException {
        JSONObject updateJson = json.getJSONObject(FIELD_UPDATE);
        String periodId = updateJson.getString(FIELD_PERIOD_ID);

        Intent intent = new Intent(PeriodBroadcastReceiver.PERIOD_INTENT_ACTION);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_ACTION, PeriodBroadcastReceiver.FIELD_REMOVE);
        intent.putExtra(PeriodBroadcastReceiver.FIELD_PERIOD_ID, periodId);
        context.sendBroadcast(intent);
    }
}
