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

    public static String getCategoryName(Context context, String category) {
        String categoryName = "";
        if (category.equalsIgnoreCase(NATIONS_MAN)) {
            categoryName = context.getString(R.string.category_nations_man);
        } else if(category.equalsIgnoreCase(NATIONS_WOMAN)) {
            categoryName = context.getString(R.string.category_nations_woman);
        } else if(category.equalsIgnoreCase(CLUBS_PRO)) {
            categoryName = context.getString(R.string.category_clubs_pro);
        } else if(category.equalsIgnoreCase(CLUBS_AMATEUR)) {
            categoryName = context.getString(R.string.category_clubs_amateur);
        }
        return categoryName;
    }
}
