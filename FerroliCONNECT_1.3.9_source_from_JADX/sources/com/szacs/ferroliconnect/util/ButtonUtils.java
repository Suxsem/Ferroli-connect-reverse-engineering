package com.szacs.ferroliconnect.util;

import android.util.Log;

public class ButtonUtils {
    private static long DIFF = 1000;
    private static int lastButtonId = -1;
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    public static boolean isFastDoubleClick(int i) {
        return isFastDoubleClick(i, DIFF);
    }

    public static boolean isFastDoubleClick(int i, long j) {
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = lastClickTime;
        long j3 = currentTimeMillis - j2;
        if (lastButtonId != i || j2 <= 0 || j3 >= j) {
            lastClickTime = currentTimeMillis;
            lastButtonId = i;
            return false;
        }
        Log.v("isFastDoubleClick", "短时间内按钮多次触发");
        return true;
    }
}
