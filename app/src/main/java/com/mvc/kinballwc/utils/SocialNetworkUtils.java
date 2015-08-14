package com.mvc.kinballwc.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Author: Mario Velasco Casquero
 * Date: 10/08/2015
 * Email: m3ario@gmail.com
 */
public class SocialNetworkUtils {
    private static final String TWITTER_USERID = "3001303594";
    private static final String FACEBOOK_USERID = "1539527069664722";

    public static void launchTwitter(Context context) {
        Intent intent;
        try {
            // get the Twitter app if possible
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + TWITTER_USERID));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/user?user_id=" + TWITTER_USERID));
        }
        context.startActivity(intent);
    }

    public static void launchFacebook(Context context) {
        Intent intent;
        try {
            // get the Facebook app if possible
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + FACEBOOK_USERID));
        } catch (Exception e) {
            // no Facebook app, launch browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + FACEBOOK_USERID));
        }
        context.startActivity(intent);
    }
}
