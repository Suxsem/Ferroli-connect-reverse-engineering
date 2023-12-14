package com.alibaba.sdk.android.tbrest.utils;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "RestApi";

    /* renamed from: d */
    public static void m1029d(String str) {
        Log.d(TAG, str);
    }

    /* renamed from: w */
    public static void m1033w(String str, Throwable th) {
        Log.w(TAG, str, th);
    }

    /* renamed from: i */
    public static void m1032i(String str) {
        Log.i(TAG, str);
    }

    /* renamed from: e */
    public static void m1030e(String str) {
        Log.e(TAG, str);
    }

    /* renamed from: e */
    public static void m1031e(String str, Throwable th) {
        Log.e(TAG, str, th);
    }
}
