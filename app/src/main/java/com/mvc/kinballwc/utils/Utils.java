package com.mvc.kinballwc.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import com.mvc.kinballwc.R;

import java.util.List;

/**
 * Author: Mario Velasco Casquero
 * Date: 02/08/2015
 * Email: m3ario@gmail.com
 */
public class Utils {

    public static final String NATIONS_MAN = "NATIONS MEN";
    public static final String NATIONS_WOMAN = "NATIONS WOMEN";
    public static final String CLUBS_PRO = "CLUBS PRO";
    public static final String CLUBS_AMATEUR = "CLUBS AMATEUR";
    public static final String CATEGORY_WORLD_CUP = "WORLD CUP";
    public static final String CATEGORY_CHAMPIONSHIP = "CHAMPIONSHIP";


    private static final String ROLE_PLAYER = "Player";
    private static final String ROLE_HEAD_COACH = "Head Coach";
    private static final String ROLE_FIRST_ASSISTANT = "First Assistant Coach";
    private static final String ROLE_SECOND_ASSISTANT = "Second Assistant Coach";

    private static final String SHARED_PREFERENCES = "app shared preferences";
    private static final String PREF_IS_FIRST_TIME = "isFirstTime";



    public static String getTranslatedCategory(Context context, String category) {
        String categoryName;
        if (category == null) {
            categoryName = context.getString(R.string.category_all);
        } else if (category.equalsIgnoreCase(NATIONS_MAN)) {
            categoryName = context.getString(R.string.category_nations_man);
        } else if(category.equalsIgnoreCase(NATIONS_WOMAN)) {
            categoryName = context.getString(R.string.category_nations_woman);
        } else if(category.equalsIgnoreCase(CLUBS_PRO)) {
            categoryName = context.getString(R.string.category_clubs_pro);
        } else if(category.equalsIgnoreCase(CLUBS_AMATEUR)) {
            categoryName = context.getString(R.string.category_clubs_amateur);
        } else if(category.equalsIgnoreCase(CLUBS_AMATEUR)) {
            categoryName = context.getString(R.string.category_clubs_amateur);
        } else {
            categoryName = category;
        }
        return categoryName;
    }

    public static String[] getCategories() {
        String[] categories = {NATIONS_MAN, NATIONS_WOMAN, CLUBS_PRO, CLUBS_AMATEUR};
        return categories;
    }


    public static String[] getCategoriesRefees() {
        String[] categories = {CATEGORY_WORLD_CUP, CATEGORY_CHAMPIONSHIP};
        return categories;
    }

    public static String getTranslatedRole(Context context, String role) {
        String roleName;
        if (role.equalsIgnoreCase(ROLE_PLAYER)) {
            roleName = context.getString(R.string.role_player);
        } else if (role.equalsIgnoreCase(ROLE_HEAD_COACH)) {
            roleName = context.getString(R.string.role_coach);
        } else if (role.equalsIgnoreCase(ROLE_FIRST_ASSISTANT)) {
            roleName = context.getString(R.string.role_first_assistant_coach);
        } else if (role.equalsIgnoreCase(ROLE_SECOND_ASSISTANT)) {
            roleName = context.getString(R.string.role_second_assistant_coach);
        } else {
            roleName = role;
        }
        return roleName;
    }

    public static boolean isFistTime(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Boolean isFirstTime = sharedPref.getBoolean(PREF_IS_FIRST_TIME, true);
        return isFirstTime;
    }


    public static void setFistTime(Context context, Boolean isFistTime) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Editor edit = sharedPref.edit();
        edit.putBoolean(PREF_IS_FIRST_TIME, isFistTime);
        edit.commit();
    }


    /**
     * Method checks if the app is in background or not
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}
