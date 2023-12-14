package anet.channel.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.AwcnConfig;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import anet.channel.util.C0604c;
import com.igexin.sdk.PushConsts;
import com.p107tb.appyunsdk.Constant;
import com.taobao.accs.utl.UtilityImpl;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* renamed from: anet.channel.status.b */
/* compiled from: Taobao */
class C0560b {

    /* renamed from: a */
    static volatile Context f417a = null;

    /* renamed from: b */
    static volatile boolean f418b = false;

    /* renamed from: c */
    static volatile NetworkStatusHelper.NetworkStatus f419c = NetworkStatusHelper.NetworkStatus.NONE;

    /* renamed from: d */
    static volatile String f420d = "unknown";

    /* renamed from: e */
    static volatile String f421e = "";

    /* renamed from: f */
    static volatile String f422f = "";

    /* renamed from: g */
    static volatile String f423g = "";

    /* renamed from: h */
    static volatile String f424h = "unknown";

    /* renamed from: i */
    static volatile String f425i = "";

    /* renamed from: j */
    static volatile Pair<String, Integer> f426j = null;

    /* renamed from: k */
    static volatile boolean f427k = false;

    /* renamed from: l */
    static volatile List<InetAddress> f428l = Collections.EMPTY_LIST;

    /* renamed from: m */
    private static String[] f429m = {"net.dns1", "net.dns2", "net.dns3", "net.dns4"};

    /* renamed from: n */
    private static volatile boolean f430n = false;

    /* renamed from: o */
    private static volatile boolean f431o = false;

    /* renamed from: p */
    private static ConnectivityManager f432p = null;

    /* renamed from: q */
    private static TelephonyManager f433q = null;

    /* renamed from: r */
    private static WifiManager f434r = null;

    /* renamed from: s */
    private static SubscriptionManager f435s = null;

    /* renamed from: t */
    private static Method f436t;

    /* renamed from: u */
    private static BroadcastReceiver f437u = new NetworkStatusMonitor$2();

    C0560b() {
    }

    /* renamed from: a */
    static void m226a() {
        if (!f430n && f417a != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(PushConsts.ACTION_BROADCAST_NETWORK_CHANGE);
            try {
                f417a.registerReceiver(f437u, intentFilter);
            } catch (Exception unused) {
                ALog.m327e("awcn.NetworkStatusMonitor", "registerReceiver failed", (String) null, new Object[0]);
            }
            m231d();
            f430n = true;
        }
    }

    /* renamed from: b */
    static void m228b() {
        if (f417a != null) {
            f417a.unregisterReceiver(f437u);
        }
    }

    /* renamed from: c */
    static void m230c() {
        if (Build.VERSION.SDK_INT >= 24 && !f431o) {
            NetworkInfo e = m232e();
            f418b = e != null && e.isConnected();
            f432p.registerDefaultNetworkCallback(new C0561c());
            f431o = true;
        }
    }

    /* renamed from: d */
    static void m231d() {
        NetworkInfo networkInfo;
        boolean z;
        WifiInfo i;
        ALog.m325d("awcn.NetworkStatusMonitor", "[checkNetworkStatus]", (String) null, new Object[0]);
        NetworkStatusHelper.NetworkStatus networkStatus = f419c;
        String str = f421e;
        String str2 = f422f;
        try {
            networkInfo = m232e();
            z = false;
        } catch (Exception e) {
            try {
                ALog.m326e("awcn.NetworkStatusMonitor", "getNetworkInfo exception", (String) null, e, new Object[0]);
                m227a(NetworkStatusHelper.NetworkStatus.NONE, "unknown");
                networkInfo = null;
                z = true;
            } catch (Exception e2) {
                ALog.m326e("awcn.NetworkStatusMonitor", "checkNetworkStatus", (String) null, e2, new Object[0]);
                return;
            }
        }
        if (!z) {
            if (networkInfo != null) {
                if (networkInfo.isConnected()) {
                    ALog.m328i("awcn.NetworkStatusMonitor", "checkNetworkStatus", (String) null, "info.isConnected", Boolean.valueOf(networkInfo.isConnected()), "info.isAvailable", Boolean.valueOf(networkInfo.isAvailable()), "info.getType", Integer.valueOf(networkInfo.getType()));
                    if (networkInfo.getType() == 0) {
                        String subtypeName = networkInfo.getSubtypeName();
                        String str3 = "";
                        if (!TextUtils.isEmpty(subtypeName)) {
                            str3 = subtypeName.replace(" ", str3);
                        }
                        m227a(m224a(networkInfo.getSubtype(), str3), str3);
                        f421e = m225a(networkInfo.getExtraInfo());
                        m235h();
                    } else if (networkInfo.getType() == 1) {
                        m227a(NetworkStatusHelper.NetworkStatus.WIFI, UtilityImpl.NET_TYPE_WIFI);
                        if (AwcnConfig.isWifiInfoEnable() && (i = m236i()) != null && m229b("android.permission.ACCESS_FINE_LOCATION")) {
                            f423g = i.getBSSID();
                            f422f = i.getSSID();
                        }
                        f424h = UtilityImpl.NET_TYPE_WIFI;
                        f425i = UtilityImpl.NET_TYPE_WIFI;
                        f426j = m237j();
                    } else {
                        m227a(NetworkStatusHelper.NetworkStatus.NONE, "unknown");
                    }
                    f427k = networkInfo.isRoaming();
                    C0604c.m353e();
                }
            }
            m227a(NetworkStatusHelper.NetworkStatus.NO, "no network");
            ALog.m328i("awcn.NetworkStatusMonitor", "checkNetworkStatus", (String) null, "no network");
        }
        if (f419c != networkStatus || !f421e.equalsIgnoreCase(str) || !f422f.equalsIgnoreCase(str2)) {
            if (ALog.isPrintLog(2)) {
                NetworkStatusHelper.printNetworkDetail();
            }
            NetworkStatusHelper.notifyStatusChanged(f419c);
        }
    }

    /* renamed from: a */
    private static void m227a(NetworkStatusHelper.NetworkStatus networkStatus, String str) {
        f419c = networkStatus;
        f420d = str;
        f421e = "";
        f422f = "";
        f423g = "";
        f426j = null;
        f424h = "";
        f425i = "";
    }

    /* renamed from: a */
    private static NetworkStatusHelper.NetworkStatus m224a(int i, String str) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetworkStatusHelper.NetworkStatus.G2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return NetworkStatusHelper.NetworkStatus.G3;
            case 13:
            case 18:
            case 19:
                return NetworkStatusHelper.NetworkStatus.G4;
            case 20:
                return NetworkStatusHelper.NetworkStatus.G5;
            default:
                if (str.equalsIgnoreCase("TD-SCDMA") || str.equalsIgnoreCase("WCDMA") || str.equalsIgnoreCase("CDMA2000")) {
                    return NetworkStatusHelper.NetworkStatus.G3;
                }
                return NetworkStatusHelper.NetworkStatus.NONE;
        }
    }

    /* renamed from: a */
    private static String m225a(String str) {
        if (!TextUtils.isEmpty(str)) {
            String lowerCase = str.toLowerCase(Locale.US);
            if (lowerCase.contains("cmwap")) {
                return "cmwap";
            }
            if (lowerCase.contains("uniwap")) {
                return "uniwap";
            }
            if (lowerCase.contains("3gwap")) {
                return "3gwap";
            }
            if (lowerCase.contains("ctwap")) {
                return "ctwap";
            }
            if (lowerCase.contains("cmnet")) {
                return "cmnet";
            }
            if (lowerCase.contains("uninet")) {
                return "uninet";
            }
            if (lowerCase.contains("3gnet")) {
                return "3gnet";
            }
            if (lowerCase.contains("ctnet")) {
                return "ctnet";
            }
        }
        return "unknown";
    }

    /* renamed from: h */
    private static void m235h() {
        try {
            if (AwcnConfig.isCarrierInfoEnable() && m229b("android.permission.READ_PHONE_STATE")) {
                if (f433q == null) {
                    f433q = (TelephonyManager) f417a.getSystemService(Constant.PHONE);
                }
                f425i = f433q.getSimOperator();
                if (Build.VERSION.SDK_INT >= 22) {
                    if (f435s == null) {
                        f435s = SubscriptionManager.from(f417a);
                        f436t = f435s.getClass().getDeclaredMethod("getDefaultDataSubscriptionInfo", new Class[0]);
                    }
                    if (f436t != null) {
                        f424h = ((SubscriptionInfo) f436t.invoke(f435s, new Object[0])).getCarrierName().toString();
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: e */
    static NetworkInfo m232e() {
        if (f432p == null) {
            f432p = (ConnectivityManager) f417a.getSystemService("connectivity");
        }
        return f432p.getActiveNetworkInfo();
    }

    /* renamed from: i */
    private static WifiInfo m236i() {
        try {
            if (f434r == null) {
                f434r = (WifiManager) f417a.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            }
            return f434r.getConnectionInfo();
        } catch (Throwable th) {
            ALog.m326e("awcn.NetworkStatusMonitor", "getWifiInfo", (String) null, th, new Object[0]);
            return null;
        }
    }

    /* renamed from: j */
    private static Pair<String, Integer> m237j() {
        try {
            String property = System.getProperty("http.proxyHost");
            if (!TextUtils.isEmpty(property)) {
                return Pair.create(property, Integer.valueOf(Integer.parseInt(System.getProperty("http.proxyPort"))));
            }
            return null;
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    /* renamed from: f */
    static String m233f() {
        try {
            Method method = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class});
            String[] strArr = f429m;
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str = (String) method.invoke((Object) null, new Object[]{strArr[i]});
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    /* renamed from: g */
    static int m234g() {
        if (f432p == null || Build.VERSION.SDK_INT < 24) {
            return -1;
        }
        return f432p.getRestrictBackgroundStatus();
    }

    /* renamed from: b */
    private static boolean m229b(String str) {
        if (Build.VERSION.SDK_INT < 23 || f417a.checkSelfPermission(str) != 0) {
            return false;
        }
        return true;
    }
}
