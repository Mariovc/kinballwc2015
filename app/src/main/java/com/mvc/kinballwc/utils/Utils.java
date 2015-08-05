package com.mvc.kinballwc.utils;

import android.content.Context;

import com.mvc.kinballwc.R;

/**
 * Author: Mario Velasco Casquero
 * Date: 02/08/2015
 * Email: m3ario@gmail.com
 */
public class Utils {

    private static final String NATIONS_MAN = "NATIONS MAN";
    private static final String NATIONS_WOMAN = "NATIONS WOMAN";
    private static final String CLUBS_PRO = "CLUBS PRO";
    private static final String CLUBS_AMATEUR = "CLUBS AMATEUR";


    private static final String ROLE_PLAYER = "Player";
    private static final String ROLE_COACH = "Coach";
    private static final String ROLE_FIRST_ASSISTANT = "First Assistant Coach";
    private static final String ROLE_SECOND_ASSISTANT = "Second Assistant Coach";
    private static final String ROLE_MEDICAL_ASSISTANT = "Medical Assistant";

    public static String getCategoryName(Context context, String category) {
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

    public static String getRoleName(Context context, String role) {
        String roleName;
        if (role.equalsIgnoreCase(ROLE_PLAYER)) {
            roleName = context.getString(R.string.role_player);
        } else if (role.equalsIgnoreCase(ROLE_COACH)) {
            roleName = context.getString(R.string.role_coach);
        } else if (role.equalsIgnoreCase(ROLE_FIRST_ASSISTANT)) {
            roleName = context.getString(R.string.role_first_assistant_coach);
        } else if (role.equalsIgnoreCase(ROLE_SECOND_ASSISTANT)) {
            roleName = context.getString(R.string.role_second_assistant_coach);
        } else if (role.equalsIgnoreCase(ROLE_MEDICAL_ASSISTANT)) {
            roleName = context.getString(R.string.role_medical_assistant);
        } else {
            roleName = role;
        }
        return roleName;
    }
}
