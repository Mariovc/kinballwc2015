package com.mvc.kinballwc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mvc.kinballwc.R;

/**
 * Author: Mario Velasco Casquero
 * Date: 02/08/2015
 * Email: m3ario@gmail.com
 */
public class Utils {

    private static final String NATIONS_MAN = "NATIONS MEN";
    private static final String NATIONS_WOMAN = "NATIONS WOMEN";
    private static final String CLUBS_PRO = "CLUBS PRO";
    private static final String CLUBS_AMATEUR = "CLUBS AMATEUR";


    private static final String ROLE_PLAYER = "Player";
    private static final String ROLE_HEAD_COACH = "Head Coach";
    private static final String ROLE_FIRST_ASSISTANT = "First Assistant Coach";
    private static final String ROLE_SECOND_ASSISTANT = "Second Assistant Coach";

    private static final String SHARED_PREFERENCES = "app shared preferences";
    private static final String PREF_IS_FIRST_TIME = "isFirstTime";


    public static String getTranslatedCategory(Context context, String category) {
        String categoryName;
        if (category.equalsIgnoreCase(NATIONS_MAN)) {
            categoryName = context.getString(R.string.category_nations_man);
        } else if(category.equalsIgnoreCase(NATIONS_WOMAN)) {
            categoryName = context.getString(R.string.category_nations_woman);
        } else if(category.equalsIgnoreCase(CLUBS_PRO)) {
            categoryName = context.getString(R.string.category_clubs_pro);
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
}
