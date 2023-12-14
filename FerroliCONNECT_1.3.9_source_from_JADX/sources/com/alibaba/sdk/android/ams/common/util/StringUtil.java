package com.alibaba.sdk.android.ams.common.util;

public class StringUtil {
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
