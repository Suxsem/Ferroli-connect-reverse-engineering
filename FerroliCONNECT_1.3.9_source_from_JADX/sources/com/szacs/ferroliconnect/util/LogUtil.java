package com.szacs.ferroliconnect.util;

import android.util.Log;

public class LogUtil {
    public static final boolean DEBUG = true;
    public static final String TAG = "yxck";

    /* renamed from: d */
    public static void m3314d(String str) {
        Log.d(TAG, str);
    }

    /* renamed from: d */
    public static void m3315d(String str, String str2) {
        Log.d(str, str2);
    }

    /* renamed from: d */
    public static void m3316d(String str, Throwable th) {
        Log.d(TAG, str, th);
    }

    /* renamed from: v */
    public static void m3323v(String str) {
        Log.v(TAG, str);
    }

    /* renamed from: v */
    public static void m3324v(String str, String str2) {
        Log.v(str, str2);
    }

    /* renamed from: v */
    public static void m3325v(String str, Throwable th) {
        Log.v(TAG, str);
    }

    /* renamed from: w */
    public static void m3326w(String str) {
        Log.w(TAG, str);
    }

    /* renamed from: w */
    public static void m3327w(String str, String str2) {
        Log.w(str, str2);
    }

    /* renamed from: w */
    public static void m3328w(String str, Throwable th) {
        Log.w(TAG, str, th);
    }

    /* renamed from: i */
    public static void m3320i(String str) {
        Log.i(TAG, str);
    }

    /* renamed from: i */
    public static void m3321i(String str, String str2) {
        Log.i(str, str2);
    }

    /* renamed from: i */
    public static void m3322i(String str, Throwable th) {
        Log.i(TAG, str, th);
    }

    /* renamed from: e */
    public static void m3317e(String str) {
        Log.e(TAG, str);
    }

    /* renamed from: e */
    public static void m3318e(String str, String str2) {
        Log.e(str, str2);
    }

    /* renamed from: e */
    public static void m3319e(String str, Throwable th) {
        Log.e(TAG, str, th);
    }
}
