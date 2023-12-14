package anetwork.channel.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.http.NetworkSdkSetting;
import com.taobao.accs.common.Constants;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Taobao */
public class CookieManager {
    public static final String TAG = "anet.CookieManager";

    /* renamed from: a */
    private static volatile boolean f669a = false;

    /* renamed from: b */
    private static android.webkit.CookieManager f670b = null;

    /* renamed from: c */
    private static boolean f671c = true;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public static C0626a f672d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public static SharedPreferences f673e;

    public static synchronized void setup(Context context) {
        synchronized (CookieManager.class) {
            if (NetworkConfigCenter.isCookieEnable()) {
                if (!f669a) {
                    try {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (Build.VERSION.SDK_INT < 21) {
                            CookieSyncManager.createInstance(context);
                        }
                        f670b = android.webkit.CookieManager.getInstance();
                        f670b.setAcceptCookie(true);
                        if (Build.VERSION.SDK_INT < 21) {
                            f670b.removeExpiredCookie();
                        }
                        f673e = PreferenceManager.getDefaultSharedPreferences(context);
                        m396e();
                        ALog.m327e(TAG, "CookieManager setup.", (String) null, "cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                    } catch (Throwable th) {
                        f671c = false;
                        ALog.m326e(TAG, "Cookie Manager setup failed!!!", (String) null, th, new Object[0]);
                    }
                    f669a = true;
                }
            }
        }
    }

    /* renamed from: d */
    private static boolean m395d() {
        if (!f669a && NetworkSdkSetting.getContext() != null) {
            setup(NetworkSdkSetting.getContext());
        }
        return f669a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void setCookie(java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.Class<anetwork.channel.cookie.CookieManager> r0 = anetwork.channel.cookie.CookieManager.class
            monitor-enter(r0)
            boolean r1 = anetwork.channel.config.NetworkConfigCenter.isCookieEnable()     // Catch:{ all -> 0x004f }
            if (r1 != 0) goto L_0x000b
            monitor-exit(r0)
            return
        L_0x000b:
            boolean r1 = m395d()     // Catch:{ all -> 0x004f }
            if (r1 == 0) goto L_0x004d
            boolean r1 = f671c     // Catch:{ all -> 0x004f }
            if (r1 != 0) goto L_0x0016
            goto L_0x004d
        L_0x0016:
            android.webkit.CookieManager r1 = f670b     // Catch:{ Throwable -> 0x002f }
            r1.setCookie(r8, r9)     // Catch:{ Throwable -> 0x002f }
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x002f }
            r2 = 21
            if (r1 >= r2) goto L_0x0029
            android.webkit.CookieSyncManager r1 = android.webkit.CookieSyncManager.getInstance()     // Catch:{ Throwable -> 0x002f }
            r1.sync()     // Catch:{ Throwable -> 0x002f }
            goto L_0x004b
        L_0x0029:
            android.webkit.CookieManager r1 = f670b     // Catch:{ Throwable -> 0x002f }
            r1.flush()     // Catch:{ Throwable -> 0x002f }
            goto L_0x004b
        L_0x002f:
            r1 = move-exception
            java.lang.String r2 = "anet.CookieManager"
            java.lang.String r3 = "set cookie failed."
            r4 = 0
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x004f }
            r6 = 0
            java.lang.String r7 = "url"
            r5[r6] = r7     // Catch:{ all -> 0x004f }
            r6 = 1
            r5[r6] = r8     // Catch:{ all -> 0x004f }
            r8 = 2
            java.lang.String r6 = "cookies"
            r5[r8] = r6     // Catch:{ all -> 0x004f }
            r8 = 3
            r5[r8] = r9     // Catch:{ all -> 0x004f }
            anet.channel.util.ALog.m326e(r2, r3, r4, r1, r5)     // Catch:{ all -> 0x004f }
        L_0x004b:
            monitor-exit(r0)
            return
        L_0x004d:
            monitor-exit(r0)
            return
        L_0x004f:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.cookie.CookieManager.setCookie(java.lang.String, java.lang.String):void");
    }

    public static void setCookie(String str, Map<String, List<String>> map) {
        if (NetworkConfigCenter.isCookieEnable() && str != null && map != null) {
            try {
                for (Map.Entry next : map.entrySet()) {
                    String str2 = (String) next.getKey();
                    if (str2 != null && (str2.equalsIgnoreCase("Set-Cookie") || str2.equalsIgnoreCase("Set-Cookie2"))) {
                        for (String str3 : (List) next.getValue()) {
                            setCookie(str, str3);
                            m391a(str3);
                        }
                    }
                }
            } catch (Exception e) {
                ALog.m326e(TAG, "set cookie failed", (String) null, e, "url", str, "\nheaders", map);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003e, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String getCookie(java.lang.String r6) {
        /*
            java.lang.Class<anetwork.channel.cookie.CookieManager> r0 = anetwork.channel.cookie.CookieManager.class
            monitor-enter(r0)
            boolean r1 = anetwork.channel.config.NetworkConfigCenter.isCookieEnable()     // Catch:{ all -> 0x003f }
            r2 = 0
            if (r1 != 0) goto L_0x000c
            monitor-exit(r0)
            return r2
        L_0x000c:
            boolean r1 = m395d()     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x003d
            boolean r1 = f671c     // Catch:{ all -> 0x003f }
            if (r1 != 0) goto L_0x0017
            goto L_0x003d
        L_0x0017:
            android.webkit.CookieManager r1 = f670b     // Catch:{ Throwable -> 0x001e }
            java.lang.String r2 = r1.getCookie(r6)     // Catch:{ Throwable -> 0x001e }
            goto L_0x0038
        L_0x001e:
            r1 = move-exception
            java.lang.String r3 = "anet.CookieManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x003f }
            r4.<init>()     // Catch:{ all -> 0x003f }
            java.lang.String r5 = "get cookie failed. url="
            r4.append(r5)     // Catch:{ all -> 0x003f }
            r4.append(r6)     // Catch:{ all -> 0x003f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x003f }
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x003f }
            anet.channel.util.ALog.m326e(r3, r4, r2, r1, r5)     // Catch:{ all -> 0x003f }
        L_0x0038:
            m392a(r6, r2)     // Catch:{ all -> 0x003f }
            monitor-exit(r0)
            return r2
        L_0x003d:
            monitor-exit(r0)
            return r2
        L_0x003f:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.cookie.CookieManager.getCookie(java.lang.String):java.lang.String");
    }

    /* renamed from: e */
    private static void m396e() {
        ThreadPoolExecutorFactory.submitCookieMonitor(new C0627a());
    }

    /* renamed from: a */
    private static void m391a(String str) {
        ThreadPoolExecutorFactory.submitCookieMonitor(new C0628b(str));
    }

    /* renamed from: a */
    private static void m392a(String str, String str2) {
        ThreadPoolExecutorFactory.submitCookieMonitor(new C0629c(str, str2));
    }

    public static void setTargetMonitorCookieName(String str) {
        SharedPreferences sharedPreferences;
        if (str != null && (sharedPreferences = f673e) != null) {
            sharedPreferences.edit().putString("networksdk_target_cookie_name", str).apply();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public static String m397f() {
        SharedPreferences sharedPreferences = f673e;
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString("networksdk_target_cookie_name", (String) null);
    }

    /* renamed from: anetwork.channel.cookie.CookieManager$a */
    /* compiled from: Taobao */
    private static class C0626a {

        /* renamed from: a */
        String f674a;

        /* renamed from: b */
        String f675b;

        /* renamed from: c */
        String f676c;

        /* renamed from: d */
        String f677d;

        /* renamed from: e */
        long f678e;

        C0626a(String str) {
            this.f674a = str;
            String string = CookieManager.f673e.getString("networksdk_cookie_monitor", (String) null);
            if (!TextUtils.isEmpty(string)) {
                try {
                    JSONObject jSONObject = new JSONObject(string);
                    if (!TextUtils.isEmpty(this.f674a) && this.f674a.equals(jSONObject.getString("cookieName"))) {
                        this.f678e = jSONObject.getLong(AgooConstants.MESSAGE_TIME);
                        if (System.currentTimeMillis() - this.f678e < Constants.CLIENT_FLUSH_INTERVAL) {
                            this.f675b = jSONObject.getString("cookieText");
                            this.f676c = jSONObject.getString("setCookie");
                            this.f677d = jSONObject.getString(DispatchConstants.DOMAIN);
                            return;
                        }
                        this.f678e = 0;
                        CookieManager.f673e.edit().remove("networksdk_cookie_monitor").apply();
                    }
                } catch (JSONException e) {
                    ALog.m326e(CookieManager.TAG, "cookie json parse error.", (String) null, e, new Object[0]);
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public void mo9302a() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("cookieName", this.f674a);
                jSONObject.put("cookieText", this.f675b);
                jSONObject.put("setCookie", this.f676c);
                this.f678e = System.currentTimeMillis();
                jSONObject.put(AgooConstants.MESSAGE_TIME, this.f678e);
                jSONObject.put(DispatchConstants.DOMAIN, this.f677d);
                CookieManager.f673e.edit().putString("networksdk_cookie_monitor", jSONObject.toString()).apply();
            } catch (Exception e) {
                ALog.m326e(CookieManager.TAG, "cookie json save error.", (String) null, e, new Object[0]);
            }
        }
    }
}
