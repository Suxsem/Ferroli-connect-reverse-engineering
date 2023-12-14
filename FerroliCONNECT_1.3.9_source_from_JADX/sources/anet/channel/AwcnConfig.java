package anet.channel;

import android.text.TextUtils;
import anet.channel.heartbeat.IHeartbeat;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.StrategyTemplate;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.taobao.accs.common.Constants;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Taobao */
public class AwcnConfig {
    public static final String HTTP3_ENABLE = "HTTP3_ENABLE";
    public static final String NEXT_LAUNCH_FORBID = "NEXT_LAUNCH_FORBID";

    /* renamed from: a */
    private static volatile boolean f41a = false;

    /* renamed from: b */
    private static volatile boolean f42b = true;

    /* renamed from: c */
    private static volatile boolean f43c = true;

    /* renamed from: d */
    private static volatile boolean f44d = true;

    /* renamed from: e */
    private static volatile boolean f45e = false;

    /* renamed from: f */
    private static volatile boolean f46f = true;

    /* renamed from: g */
    private static volatile long f47g = 43200000;

    /* renamed from: h */
    private static volatile boolean f48h = true;

    /* renamed from: i */
    private static volatile boolean f49i = true;

    /* renamed from: j */
    private static boolean f50j = true;

    /* renamed from: k */
    private static boolean f51k = false;

    /* renamed from: l */
    private static volatile boolean f52l = false;

    /* renamed from: m */
    private static volatile boolean f53m = true;

    /* renamed from: n */
    private static volatile boolean f54n = false;

    /* renamed from: o */
    private static volatile int f55o = 10000;

    /* renamed from: p */
    private static volatile boolean f56p = false;

    /* renamed from: q */
    private static volatile boolean f57q = true;

    /* renamed from: r */
    private static volatile int f58r = -1;

    /* renamed from: s */
    private static volatile boolean f59s = true;

    /* renamed from: t */
    private static volatile boolean f60t = true;

    /* renamed from: u */
    private static volatile boolean f61u = false;

    /* renamed from: v */
    private static volatile boolean f62v = true;

    /* renamed from: w */
    private static volatile CopyOnWriteArrayList<String> f63w = null;

    /* renamed from: x */
    private static volatile boolean f64x = true;

    /* renamed from: y */
    private static volatile boolean f65y = true;

    public static boolean isAccsSessionCreateForbiddenInBg() {
        return f41a;
    }

    public static void setAccsSessionCreateForbiddenInBg(boolean z) {
        f41a = z;
    }

    public static void setHttpsSniEnable(boolean z) {
        f42b = z;
    }

    public static boolean isHttpsSniEnable() {
        return f42b;
    }

    public static boolean isHorseRaceEnable() {
        return f43c;
    }

    public static void setHorseRaceEnable(boolean z) {
        f43c = z;
    }

    public static boolean isTnetHeaderCacheEnable() {
        return f44d;
    }

    public static void setTnetHeaderCacheEnable(boolean z) {
        f44d = z;
    }

    public static void setQuicEnable(boolean z) {
        f45e = z;
    }

    public static boolean isQuicEnable() {
        return f45e;
    }

    public static void setIdleSessionCloseEnable(boolean z) {
        f46f = z;
    }

    public static boolean isIdleSessionCloseEnable() {
        return f46f;
    }

    public static void registerPresetSessions(String str) {
        if (GlobalAppRuntimeInfo.isTargetProcess() && !TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                int i = 0;
                while (i < length) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String string = jSONObject.getString(Constants.KEY_HOST);
                    if (C0594c.m321c(string)) {
                        StrategyTemplate.getInstance().registerConnProtocol(string, ConnProtocol.valueOf(jSONObject.getString("protocol"), jSONObject.getString("rtt"), jSONObject.getString("publicKey")));
                        if (jSONObject.getBoolean("isKeepAlive")) {
                            SessionCenter.getInstance().registerSessionInfo(SessionInfo.create(string, true, false, (IAuth) null, (IHeartbeat) null, (DataFrameCb) null));
                        }
                        i++;
                    } else {
                        return;
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    public static boolean isIpv6Enable() {
        return f48h;
    }

    public static void setIpv6Enable(boolean z) {
        f48h = z;
    }

    public static boolean isIpv6BlackListEnable() {
        return f49i;
    }

    public static void setIpv6BlackListEnable(boolean z) {
        f49i = z;
    }

    public static long getIpv6BlackListTtl() {
        return f47g;
    }

    public static void setIpv6BlackListTtl(long j) {
        f47g = j;
    }

    public static boolean isAppLifeCycleListenerEnable() {
        return f50j;
    }

    public static void setAppLifeCycleListenerEnable(boolean z) {
        f50j = z;
    }

    public static boolean isAsyncLoadStrategyEnable() {
        return f51k;
    }

    public static void setAsyncLoadStrategyEnable(boolean z) {
        f51k = z;
    }

    public static boolean isTbNextLaunch() {
        return f52l;
    }

    public static void setTbNextLaunch(boolean z) {
        f52l = z;
    }

    public static boolean isPing6Enable() {
        return f53m;
    }

    public static void setPing6Enable(boolean z) {
        f53m = z;
    }

    public static boolean isNetworkDetectEnable() {
        return f54n;
    }

    public static void setNetworkDetectEnable(boolean z) {
        f54n = z;
    }

    public static int getAccsReconnectionDelayPeriod() {
        return f55o;
    }

    public static void setAccsReconnectionDelayPeriod(int i) {
        if (i < 0) {
            i = 0;
        }
        if (i > 10000) {
            i = RestConstants.G_MAX_CONNECTION_TIME_OUT;
        }
        f55o = i;
    }

    public static void setHttp3Enable(boolean z) {
        f56p = z;
        ALog.m327e("awcn.AwcnConfig", "[setHttp3Enable]", (String) null, "enable", Boolean.valueOf(z));
    }

    public static boolean isHttp3Enable() {
        return f56p;
    }

    public static void setHttp3OrangeEnable(boolean z) {
        f57q = z;
    }

    public static boolean isHttp3OrangeEnable() {
        return f57q;
    }

    public static void setXquicCongControl(int i) {
        if (i >= 0) {
            f58r = i;
        }
    }

    public static int getXquicCongControl() {
        return f58r;
    }

    public static void setIpStackDetectByUdpConnect(boolean z) {
        f59s = z;
    }

    public static boolean isIpStackDetectByUdpConnect() {
        return f59s;
    }

    public static void setCookieHeaderRedundantFix(boolean z) {
        f60t = z;
    }

    public static boolean isCookieHeaderRedundantFix() {
        return f60t;
    }

    public static void setSendConnectInfoByBroadcast(boolean z) {
        f61u = z;
    }

    public static boolean isSendConnectInfoByBroadcast() {
        return f61u;
    }

    public static void setSendConnectInfoByService(boolean z) {
        f62v = z;
    }

    public static boolean isSendConnectInfoByService() {
        return f62v;
    }

    public static void setHttpDnsNotifyWhiteList(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                JSONArray jSONArray = new JSONArray(str);
                for (int i = 0; i < jSONArray.length(); i++) {
                    String string = jSONArray.getString(i);
                    if (!TextUtils.isEmpty(string)) {
                        copyOnWriteArrayList.add(string);
                    }
                }
                f63w = copyOnWriteArrayList;
            } catch (Exception e) {
                ALog.m326e("awcn.AwcnConfig", "[setHttpDnsNotifyWhiteList] error", (String) null, e, new Object[0]);
            }
        }
    }

    public static boolean isAllowHttpDnsNotify(String str) {
        if (f63w == null || TextUtils.isEmpty(str)) {
            return false;
        }
        return f63w.contains(str);
    }

    public static boolean isWifiInfoEnable() {
        return f64x;
    }

    public static void setWifiInfoEnable(boolean z) {
        f64x = z;
    }

    public static boolean isCarrierInfoEnable() {
        return f65y;
    }

    public static void setCarrierInfoEnable(boolean z) {
        f65y = z;
    }
}
