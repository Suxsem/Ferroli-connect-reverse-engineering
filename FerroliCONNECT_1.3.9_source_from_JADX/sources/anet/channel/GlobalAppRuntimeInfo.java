package anet.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.entity.ENV;
import anet.channel.fulltrace.C0521a;
import anet.channel.fulltrace.C0523b;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import anet.channel.util.Utils;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: Taobao */
public class GlobalAppRuntimeInfo {

    /* renamed from: a */
    private static Context f76a;

    /* renamed from: b */
    private static ENV f77b = ENV.ONLINE;

    /* renamed from: c */
    private static String f78c = "";

    /* renamed from: d */
    private static String f79d = "";

    /* renamed from: e */
    private static String f80e;

    /* renamed from: f */
    private static String f81f;

    /* renamed from: g */
    private static String f82g;

    /* renamed from: h */
    private static volatile boolean f83h = true;

    /* renamed from: i */
    private static SharedPreferences f84i = null;

    /* renamed from: j */
    private static volatile CopyOnWriteArrayList<String> f85j = null;

    /* renamed from: k */
    private static volatile long f86k;

    /* renamed from: l */
    private static String f87l;

    public static void setContext(Context context) {
        f76a = context;
        if (context != null) {
            if (TextUtils.isEmpty(f79d)) {
                f79d = Utils.getProcessName(context, Process.myPid());
            }
            if (TextUtils.isEmpty(f78c)) {
                f78c = Utils.getMainProcessName(context);
            }
            if (f84i == null) {
                f84i = PreferenceManager.getDefaultSharedPreferences(context);
                f81f = f84i.getString("UserId", (String) null);
            }
            ALog.m327e("awcn.GlobalAppRuntimeInfo", "", (String) null, "CurrentProcess", f79d, "TargetProcess", f78c);
        }
    }

    public static Context getContext() {
        return f76a;
    }

    public static void setTargetProcess(String str) {
        f78c = str;
    }

    public static boolean isTargetProcess() {
        if (TextUtils.isEmpty(f78c) || TextUtils.isEmpty(f79d)) {
            return true;
        }
        return f78c.equalsIgnoreCase(f79d);
    }

    public static boolean isTargetProcess(String str) {
        if (TextUtils.isEmpty(f78c) || TextUtils.isEmpty(str)) {
            return true;
        }
        return f78c.equalsIgnoreCase(str);
    }

    public static String getCurrentProcess() {
        return f79d;
    }

    public static void setCurrentProcess(String str) {
        f79d = str;
    }

    public static void setEnv(ENV env) {
        f77b = env;
    }

    public static ENV getEnv() {
        return f77b;
    }

    public static void setTtid(String str) {
        f80e = str;
        try {
            if (!TextUtils.isEmpty(str)) {
                int indexOf = str.indexOf("@");
                String str2 = null;
                String substring = indexOf != -1 ? str.substring(0, indexOf) : null;
                String substring2 = str.substring(indexOf + 1);
                int lastIndexOf = substring2.lastIndexOf("_");
                if (lastIndexOf != -1) {
                    String substring3 = substring2.substring(0, lastIndexOf);
                    str2 = substring2.substring(lastIndexOf + 1);
                    substring2 = substring3;
                }
                f87l = str2;
                AmdcRuntimeInfo.setAppInfo(substring2, str2, substring);
            }
        } catch (Exception unused) {
        }
    }

    public static String getTtid() {
        return f80e;
    }

    public static void setUserId(String str) {
        String str2 = f81f;
        if (str2 == null || !str2.equals(str)) {
            f81f = str;
            StrategyCenter.getInstance().forceRefreshStrategy(DispatchConstants.getAmdcServerDomain());
            SharedPreferences sharedPreferences = f84i;
            if (sharedPreferences != null) {
                sharedPreferences.edit().putString("UserId", str).apply();
            }
        }
    }

    public static String getUserId() {
        return f81f;
    }

    public static void setUtdid(String str) {
        String str2 = f82g;
        if (str2 == null || !str2.equals(str)) {
            f82g = str;
        }
    }

    public static String getUtdid() {
        Context context;
        if (f82g == null && (context = f76a) != null) {
            f82g = Utils.getDeviceId(context);
        }
        return f82g;
    }

    public static void setBackground(boolean z) {
        f83h = z;
    }

    public static boolean isAppBackground() {
        if (f76a == null) {
            return true;
        }
        return f83h;
    }

    public static void addBucketInfo(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && str.length() <= 32 && str2.length() <= 32) {
            synchronized (GlobalAppRuntimeInfo.class) {
                if (f85j == null) {
                    f85j = new CopyOnWriteArrayList<>();
                }
                f85j.add(str);
                f85j.add(str2);
            }
        }
    }

    public static CopyOnWriteArrayList<String> getBucketInfo() {
        return f85j;
    }

    @Deprecated
    public static void setInitTime(long j) {
        f86k = j;
    }

    @Deprecated
    public static long getInitTime() {
        return f86k;
    }

    @Deprecated
    public static int getStartType() {
        C0523b sceneInfo = C0521a.m128a().getSceneInfo();
        if (sceneInfo != null) {
            return sceneInfo.f258a;
        }
        return -1;
    }
}
