package com.mvc.kinballwc.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mvc.kinballwc.model.Match;
import com.mvc.kinballwc.model.MatchPeriod;
import com.mvc.kinballwc.model.MatchPoints;
import com.mvc.kinballwc.model.Player;
import com.mvc.kinballwc.model.Referee;
import com.mvc.kinballwc.model.Role;
import com.mvc.kinballwc.model.Team;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Author: Mario Velasco Casquero
 * Date: 23/6/15
 * Email: m3ario@gmail.com
 */
public class App extends Application{


    public static final String PARSE_APPLICATION_ID = "FCHZ57kyg8w90onvgWwSlooVix0rXhabgii5ogm9";
    public static final String PARSE_CLIENT_KEY = "A1Tf4HM97nufDKb0A9IEWlyllwRJgcMV1TwDO0LK";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        // Initialize the singletons so their instances
        // are bound to the application process.
        initParse();
        subscribePushNotifications();
    }

    protected void initParse() {

        // Register your parse models here
        ParseObject.registerSubclass(Team.class);
        ParseObject.registerSubclass(Player.class);
        ParseObject.registerSubclass(Match.class);
        ParseObject.registerSubclass(MatchPoints.class);
        ParseObject.registerSubclass(MatchPeriod.class);
        ParseObject.registerSubclass(Role.class);
        ParseObject.registerSubclass(Referee.class);

        // Enable Crash Reporting
        ParseCrashReporting.enable(this);

        // Initialize the instance of Parse
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    private void subscribePushNotifications() {
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

    public static Context getAppContext() {
        return App.context;
    }
}
