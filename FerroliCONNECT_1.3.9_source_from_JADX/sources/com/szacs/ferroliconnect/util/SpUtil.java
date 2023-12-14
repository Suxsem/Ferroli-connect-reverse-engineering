package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    public static final String LANGUAGE = "language";
    private static final String SP_NAME = "poemTripSpref";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences hmSpref;
    private static SpUtil spUtil;

    private SpUtil(Context context) {
        hmSpref = context.getSharedPreferences(SP_NAME, 0);
        editor = hmSpref.edit();
    }

    public static SpUtil getInstance(Context context) {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil(context);
                }
            }
        }
        return spUtil;
    }

    public void putString(String str, String str2) {
        editor.putString(str, str2);
        editor.commit();
    }

    public String getString(String str) {
        return hmSpref.getString(str, "");
    }
}
