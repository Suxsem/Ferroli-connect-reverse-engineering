package com.taobao.accs.client;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import com.taobao.accs.ILoginInfo;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.android.agoo.common.AgooConstants;

/* compiled from: Taobao */
public class GlobalClientInfo {
    public static final String AGOO_SERVICE_ID = "agooSend";

    /* renamed from: a */
    public static Context f3223a = null;

    /* renamed from: b */
    public static String f3224b = null;

    /* renamed from: c */
    public static boolean f3225c = false;

    /* renamed from: d */
    private static volatile GlobalClientInfo f3226d;

    /* renamed from: i */
    private static Map<String, String> f3227i = new ConcurrentHashMap();

    /* renamed from: e */
    private ConcurrentHashMap<String, ILoginInfo> f3228e;

    /* renamed from: f */
    private ActivityManager f3229f;

    /* renamed from: g */
    private ConnectivityManager f3230g;

    /* renamed from: h */
    private PackageInfo f3231h;

    /* renamed from: j */
    private Map<String, AccsAbstractDataListener> f3232j = new ConcurrentHashMap();

    static {
        f3227i.put(AGOO_SERVICE_ID, "org.android.agoo.accs.AgooService");
        f3227i.put(AgooConstants.AGOO_SERVICE_AGOOACK, "org.android.agoo.accs.AgooService");
        f3227i.put("agooTokenReport", "org.android.agoo.accs.AgooService");
    }

    public static GlobalClientInfo getInstance(Context context) {
        if (f3226d == null) {
            synchronized (GlobalClientInfo.class) {
                if (f3226d == null) {
                    f3226d = new GlobalClientInfo(context);
                }
            }
        }
        return f3226d;
    }

    public static Context getContext() {
        return f3223a;
    }

    private GlobalClientInfo(Context context) {
        if (context != null) {
            if (f3223a == null) {
                f3223a = context.getApplicationContext();
            }
            ThreadPoolExecutorFactory.execute(new C2022d(this));
            return;
        }
        throw new RuntimeException("Context is null!!");
    }

    public ActivityManager getActivityManager() {
        if (this.f3229f == null) {
            this.f3229f = (ActivityManager) f3223a.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
        }
        return this.f3229f;
    }

    public ConnectivityManager getConnectivityManager() {
        if (this.f3230g == null) {
            this.f3230g = (ConnectivityManager) f3223a.getSystemService("connectivity");
        }
        return this.f3230g;
    }

    public void setLoginInfoImpl(String str, ILoginInfo iLoginInfo) {
        if (this.f3228e == null) {
            this.f3228e = new ConcurrentHashMap<>(1);
        }
        if (iLoginInfo != null) {
            this.f3228e.put(str, iLoginInfo);
        }
    }

    public void clearLoginInfoImpl() {
        this.f3228e = null;
    }

    public String getSid(String str) {
        ILoginInfo iLoginInfo;
        ConcurrentHashMap<String, ILoginInfo> concurrentHashMap = this.f3228e;
        if (concurrentHashMap == null || (iLoginInfo = concurrentHashMap.get(str)) == null) {
            return null;
        }
        return iLoginInfo.getSid();
    }

    public String getUserId(String str) {
        ILoginInfo iLoginInfo;
        ConcurrentHashMap<String, ILoginInfo> concurrentHashMap = this.f3228e;
        if (concurrentHashMap == null || (iLoginInfo = concurrentHashMap.get(str)) == null) {
            return null;
        }
        return iLoginInfo.getUserId();
    }

    public String getNick(String str) {
        ILoginInfo iLoginInfo;
        ConcurrentHashMap<String, ILoginInfo> concurrentHashMap = this.f3228e;
        if (concurrentHashMap == null || (iLoginInfo = concurrentHashMap.get(str)) == null) {
            return null;
        }
        return iLoginInfo.getNick();
    }

    public void registerService(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            f3227i.put(str, str2);
        }
    }

    public void unRegisterService(String str) {
        if (!TextUtils.isEmpty(str)) {
            f3227i.remove(str);
        }
    }

    public String getService(String str) {
        return f3227i.get(str);
    }

    public void registerListener(String str, AccsAbstractDataListener accsAbstractDataListener) {
        if (!TextUtils.isEmpty(str) && accsAbstractDataListener != null) {
            this.f3232j.put(str, accsAbstractDataListener);
        }
    }

    public void unregisterListener(String str) {
        this.f3232j.remove(str);
    }

    public AccsAbstractDataListener getListener(String str) {
        return this.f3232j.get(str);
    }

    public PackageInfo getPackageInfo() {
        try {
            if (this.f3231h == null) {
                this.f3231h = f3223a.getPackageManager().getPackageInfo(f3223a.getPackageName(), 0);
            }
        } catch (Throwable th) {
            ALog.m3726e("GlobalClientInfo", "getPackageInfo", th, new Object[0]);
        }
        return this.f3231h;
    }
}
