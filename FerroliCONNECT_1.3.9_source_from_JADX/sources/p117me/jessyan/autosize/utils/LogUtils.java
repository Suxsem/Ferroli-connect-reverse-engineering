package p117me.jessyan.autosize.utils;

import android.util.Log;

/* renamed from: me.jessyan.autosize.utils.LogUtils */
public class LogUtils {
    private static final String TAG = "AndroidAutoSize";
    private static boolean debug;

    private LogUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean z) {
        debug = z;
    }

    /* renamed from: d */
    public static void m3884d(String str) {
        if (debug) {
            Log.d(TAG, str);
        }
    }

    /* renamed from: w */
    public static void m3886w(String str) {
        if (debug) {
            Log.w(TAG, str);
        }
    }

    /* renamed from: e */
    public static void m3885e(String str) {
        if (debug) {
            Log.e(TAG, str);
        }
    }
}
