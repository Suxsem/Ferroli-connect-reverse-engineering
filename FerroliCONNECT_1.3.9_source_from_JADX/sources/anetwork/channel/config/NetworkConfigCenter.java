package anetwork.channel.config;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.statist.RequestStatistic;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.utils.C0594c;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import anetwork.channel.cache.CacheManager;
import anetwork.channel.http.NetworkSdkSetting;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.jetty.util.security.Constraint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Taobao */
public class NetworkConfigCenter {
    public static final String SERVICE_OPTIMIZE = "SERVICE_OPTIMIZE";
    public static final String SESSION_ASYNC_OPTIMIZE = "SESSION_ASYNC_OPTIMIZE";

    /* renamed from: a */
    private static volatile boolean f645a = true;

    /* renamed from: b */
    private static volatile boolean f646b = true;

    /* renamed from: c */
    private static volatile boolean f647c = true;

    /* renamed from: d */
    private static volatile int f648d = 5;

    /* renamed from: e */
    private static volatile boolean f649e = true;

    /* renamed from: f */
    private static volatile boolean f650f = true;

    /* renamed from: g */
    private static volatile boolean f651g = false;

    /* renamed from: h */
    private static volatile long f652h = 0;

    /* renamed from: i */
    private static volatile boolean f653i = false;

    /* renamed from: j */
    private static volatile ConcurrentHashMap<String, List<String>> f654j;

    /* renamed from: k */
    private static volatile CopyOnWriteArrayList<String> f655k;

    /* renamed from: l */
    private static final List<String> f656l = new ArrayList();

    /* renamed from: m */
    private static volatile int f657m = RestConstants.G_MAX_CONNECTION_TIME_OUT;

    /* renamed from: n */
    private static volatile boolean f658n = true;

    /* renamed from: o */
    private static volatile boolean f659o = false;

    /* renamed from: p */
    private static volatile int f660p = 60000;

    /* renamed from: q */
    private static volatile CopyOnWriteArrayList<String> f661q = null;

    /* renamed from: r */
    private static volatile ConcurrentHashMap<String, List<String>> f662r = null;

    /* renamed from: s */
    private static volatile boolean f663s = true;

    /* renamed from: t */
    private static volatile boolean f664t = false;

    /* renamed from: u */
    private static volatile boolean f665u = false;

    /* renamed from: v */
    private static volatile boolean f666v = true;

    /* renamed from: w */
    private static volatile boolean f667w = true;

    /* renamed from: x */
    private static volatile IRemoteConfig f668x;

    @Deprecated
    public static void setHttpsValidationEnabled(boolean z) {
    }

    public static void init() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext());
        f652h = defaultSharedPreferences.getLong("Cache.Flag", 0);
        f665u = defaultSharedPreferences.getBoolean("CHANNEL_LOCAL_INSTANCE_ENABLE", false);
        f666v = defaultSharedPreferences.getBoolean("ALLOW_SPDY_WHEN_BIND_SERVICE_FAILED", true);
    }

    public static void setSSLEnabled(boolean z) {
        ALog.m328i("anet.NetworkConfigCenter", "[setSSLEnabled]", (String) null, "enable", Boolean.valueOf(z));
        f645a = z;
    }

    public static boolean isSSLEnabled() {
        return f645a;
    }

    public static void setSpdyEnabled(boolean z) {
        ALog.m328i("anet.NetworkConfigCenter", "[setSpdyEnabled]", (String) null, "enable", Boolean.valueOf(z));
        f646b = z;
    }

    public static boolean isSpdyEnabled() {
        return f646b;
    }

    public static void setServiceBindWaitTime(int i) {
        f648d = i;
    }

    public static int getServiceBindWaitTime() {
        return f648d;
    }

    public static void setRemoteNetworkServiceEnable(boolean z) {
        f647c = z;
    }

    public static boolean isRemoteNetworkServiceEnable() {
        return f647c;
    }

    public static void setRemoteConfig(IRemoteConfig iRemoteConfig) {
        if (f668x != null) {
            f668x.unRegister();
        }
        if (iRemoteConfig != null) {
            iRemoteConfig.register();
        }
        f668x = iRemoteConfig;
    }

    public static boolean isHttpSessionEnable() {
        return f649e;
    }

    public static void setHttpSessionEnable(boolean z) {
        f649e = z;
    }

    public static boolean isAllowHttpIpRetry() {
        return f649e && f651g;
    }

    public static void setAllowHttpIpRetry(boolean z) {
        f651g = z;
    }

    public static boolean isHttpCacheEnable() {
        return f650f;
    }

    public static void setHttpCacheEnable(boolean z) {
        f650f = z;
    }

    public static void setCacheFlag(long j) {
        if (j != f652h) {
            ALog.m328i("anet.NetworkConfigCenter", "set cache flag", (String) null, "old", Long.valueOf(f652h), "new", Long.valueOf(j));
            f652h = j;
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).edit();
            edit.putLong("Cache.Flag", f652h);
            edit.apply();
            CacheManager.clearAllCache();
        }
    }

    public static void updateWhiteListMap(String str) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.NetworkConfigCenter", "updateWhiteUrlList", (String) null, "White List", str);
        }
        if (TextUtils.isEmpty(str)) {
            f654j = null;
            return;
        }
        ConcurrentHashMap<String, List<String>> concurrentHashMap = new ConcurrentHashMap<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                Object obj = jSONObject.get(next);
                try {
                    if (Constraint.ANY_ROLE.equals(obj)) {
                        concurrentHashMap.put(next, f656l);
                    } else if (obj instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) obj;
                        int length = jSONArray.length();
                        ArrayList arrayList = new ArrayList(length);
                        for (int i = 0; i < length; i++) {
                            Object obj2 = jSONArray.get(i);
                            if (obj2 instanceof String) {
                                arrayList.add((String) obj2);
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            concurrentHashMap.put(next, arrayList);
                        }
                    }
                } catch (JSONException unused) {
                }
            }
        } catch (JSONException e) {
            ALog.m326e("anet.NetworkConfigCenter", "parse jsonObject failed", (String) null, e, new Object[0]);
        }
        f654j = concurrentHashMap;
    }

    public static void updateBizWhiteList(String str) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.NetworkConfigCenter", "updateRequestWhiteList", (String) null, "White List", str);
        }
        if (TextUtils.isEmpty(str)) {
            f655k = null;
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            for (int i = 0; i < length; i++) {
                String string = jSONArray.getString(i);
                if (!string.isEmpty()) {
                    copyOnWriteArrayList.add(string);
                }
            }
            f655k = copyOnWriteArrayList;
        } catch (JSONException e) {
            ALog.m326e("anet.NetworkConfigCenter", "parse bizId failed", (String) null, e, new Object[0]);
        }
    }

    public static boolean isUrlInWhiteList(HttpUrl httpUrl) {
        ConcurrentHashMap<String, List<String>> concurrentHashMap;
        List<String> list;
        if (httpUrl == null || (concurrentHashMap = f654j) == null || (list = concurrentHashMap.get(httpUrl.host())) == null) {
            return false;
        }
        if (list == f656l) {
            return true;
        }
        for (String startsWith : list) {
            if (httpUrl.path().startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBizInWhiteList(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        CopyOnWriteArrayList<String> copyOnWriteArrayList = f655k;
        if (f655k == null) {
            return false;
        }
        Iterator<String> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            if (str.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static int getRequestStatisticSampleRate() {
        return f657m;
    }

    public static void setRequestStatisticSampleRate(int i) {
        f657m = i;
    }

    public static boolean isBgRequestForbidden() {
        return f653i;
    }

    public static void setBgRequestForbidden(boolean z) {
        f653i = z;
    }

    public static void setAmdcPresetHosts(String str) {
        if (GlobalAppRuntimeInfo.isTargetProcess()) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                ArrayList arrayList = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    String string = jSONArray.getString(i);
                    if (C0594c.m321c(string)) {
                        arrayList.add(string);
                    }
                }
                HttpDispatcher.getInstance().addHosts(arrayList);
            } catch (JSONException e) {
                ALog.m326e("anet.NetworkConfigCenter", "parse hosts failed", (String) null, e, new Object[0]);
            }
        }
    }

    public static boolean isResponseBufferEnable() {
        return f658n;
    }

    public static void setResponseBufferEnable(boolean z) {
        f658n = z;
    }

    public static boolean isGetSessionAsyncEnable() {
        return f659o;
    }

    public static void setGetSessionAsyncEnable(boolean z) {
        f659o = z;
    }

    public static int getBgForbidRequestThreshold() {
        return f660p;
    }

    public static void setBgForbidRequestThreshold(int i) {
        f660p = i;
    }

    public static void setMonitorRequestList(String str) {
        if (TextUtils.isEmpty(str)) {
            f661q = null;
        }
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray(Constants.KEY_HOST);
            int length = jSONArray.length();
            CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            for (int i = 0; i < length; i++) {
                String string = jSONArray.getString(i);
                if (C0594c.m321c(string)) {
                    copyOnWriteArrayList.add(string);
                }
            }
            f661q = copyOnWriteArrayList;
        } catch (JSONException e) {
            ALog.m326e("anet.NetworkConfigCenter", "parse hosts failed", (String) null, e, new Object[0]);
        }
    }

    public static boolean isRequestInMonitorList(RequestStatistic requestStatistic) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList;
        if (requestStatistic == null || (copyOnWriteArrayList = f661q) == null || TextUtils.isEmpty(requestStatistic.host)) {
            return false;
        }
        Iterator<String> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            if (requestStatistic.host.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static void setDegradeRequestList(String str) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("anet.NetworkConfigCenter", "setDegradeRequestList", (String) null, "Degrade List", str);
        }
        if (TextUtils.isEmpty(str)) {
            f662r = null;
            return;
        }
        ConcurrentHashMap<String, List<String>> concurrentHashMap = new ConcurrentHashMap<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                Object obj = jSONObject.get(next);
                try {
                    if (Constraint.ANY_ROLE.equals(obj)) {
                        concurrentHashMap.put(next, f656l);
                    } else if (obj instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) obj;
                        int length = jSONArray.length();
                        ArrayList arrayList = new ArrayList(length);
                        for (int i = 0; i < length; i++) {
                            Object obj2 = jSONArray.get(i);
                            if (obj2 instanceof String) {
                                arrayList.add((String) obj2);
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            concurrentHashMap.put(next, arrayList);
                        }
                    }
                } catch (JSONException unused) {
                }
            }
        } catch (JSONException e) {
            ALog.m326e("anet.NetworkConfigCenter", "parse jsonObject failed", (String) null, e, new Object[0]);
        }
        f662r = concurrentHashMap;
    }

    public static boolean isUrlInDegradeList(HttpUrl httpUrl) {
        ConcurrentHashMap<String, List<String>> concurrentHashMap;
        List<String> list;
        if (httpUrl == null || (concurrentHashMap = f662r) == null || (list = concurrentHashMap.get(httpUrl.host())) == null) {
            return false;
        }
        if (list == f656l) {
            return true;
        }
        for (String startsWith : list) {
            if (httpUrl.path().startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    public static void enableNetworkSdkOptimizeTest(boolean z) {
        if (z) {
            setGetSessionAsyncEnable(true);
            ThreadPoolExecutorFactory.setNormalExecutorPoolSize(16);
            AwcnConfig.registerPresetSessions("[{\"host\":\"trade-acs.m.taobao.com\", \"protocol\":\"http2\", \"rtt\":\"0rtt\", \"publicKey\": \"acs\", \"isKeepAlive\":true}]");
            return;
        }
        setGetSessionAsyncEnable(false);
        ThreadPoolExecutorFactory.setNormalExecutorPoolSize(6);
    }

    public static boolean isRequestDelayRetryForNoNetwork() {
        return f663s;
    }

    public static void setRequestDelayRetryForNoNetwork(boolean z) {
        f663s = z;
    }

    public static boolean isBindServiceOptimize() {
        return f664t;
    }

    public static void setBindServiceOptimize(boolean z) {
        f664t = z;
    }

    public static void setChannelLocalInstanceEnable(boolean z) {
        f665u = z;
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).edit();
        edit.putBoolean("CHANNEL_LOCAL_INSTANCE_ENABLE", f665u);
        edit.apply();
    }

    public static boolean isChannelLocalInstanceEnable() {
        return f665u;
    }

    public static void setAllowSpdyWhenBindServiceFailed(boolean z) {
        f666v = z;
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).edit();
        edit.putBoolean("ALLOW_SPDY_WHEN_BIND_SERVICE_FAILED", f666v);
        edit.apply();
    }

    public static boolean isAllowSpdyWhenBindServiceFailed() {
        return f666v;
    }

    public static void setCookieEnable(boolean z) {
        f667w = z;
    }

    public static boolean isCookieEnable() {
        return f667w;
    }
}
