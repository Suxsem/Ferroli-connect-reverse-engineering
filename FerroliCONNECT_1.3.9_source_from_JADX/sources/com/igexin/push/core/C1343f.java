package com.igexin.push.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.os.Environment;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.config.C1237n;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.p054e.p057c.C1374c;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.push.util.C1576a;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.p091a.C1600c;
import com.igexin.sdk.p091a.C1601d;
import com.taobao.accs.common.Constants;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.igexin.push.core.f */
public class C1343f {

    /* renamed from: A */
    public static String f2109A = null;

    /* renamed from: B */
    public static String f2110B = null;

    /* renamed from: C */
    public static String f2111C = "";

    /* renamed from: D */
    public static long f2112D = -1;

    /* renamed from: E */
    public static long f2113E = -1;

    /* renamed from: F */
    public static long f2114F = 0;

    /* renamed from: G */
    public static long f2115G = 0;

    /* renamed from: H */
    public static long f2116H = 0;

    /* renamed from: I */
    public static long f2117I = 0;

    /* renamed from: J */
    public static long f2118J = 0;

    /* renamed from: K */
    public static long f2119K = 0;

    /* renamed from: L */
    public static String f2120L = null;

    /* renamed from: M */
    public static boolean f2121M = C1237n.f1867a.equals(MqttServiceConstants.TRACE_DEBUG);

    /* renamed from: N */
    public static long f2122N = 0;

    /* renamed from: O */
    public static long f2123O = 0;

    /* renamed from: P */
    public static String f2124P = null;

    /* renamed from: Q */
    public static long f2125Q = 0;

    /* renamed from: R */
    public static int f2126R = 0;

    /* renamed from: S */
    public static String f2127S = null;

    /* renamed from: T */
    public static String f2128T = null;

    /* renamed from: U */
    public static String f2129U = null;

    /* renamed from: V */
    public static String f2130V = null;

    /* renamed from: W */
    public static String f2131W = null;

    /* renamed from: X */
    public static String f2132X = null;

    /* renamed from: Y */
    public static String f2133Y = null;

    /* renamed from: Z */
    public static byte[] f2134Z = null;

    /* renamed from: a */
    public static String f2135a = "";

    /* renamed from: aA */
    public static int f2136aA = 0;

    /* renamed from: aB */
    public static byte[] f2137aB = null;

    /* renamed from: aC */
    private static Map<String, Integer> f2138aC = null;

    /* renamed from: aa */
    public static boolean f2139aa = false;

    /* renamed from: ab */
    public static boolean f2140ab = false;

    /* renamed from: ac */
    public static boolean f2141ac = false;

    /* renamed from: ad */
    public static Map<String, PushTaskBean> f2142ad = null;

    /* renamed from: ae */
    public static Map<String, Integer> f2143ae = null;

    /* renamed from: af */
    public static Set<String> f2144af = null;

    /* renamed from: ag */
    public static HashMap<String, Timer> f2145ag = null;

    /* renamed from: ah */
    public static HashMap<String, C1374c> f2146ah = null;

    /* renamed from: ai */
    public static HashMap<String, Long> f2147ai = null;

    /* renamed from: aj */
    public static int f2148aj = 0;

    /* renamed from: ak */
    public static Map<String, String> f2149ak = null;

    /* renamed from: al */
    public static int f2150al = 0;

    /* renamed from: am */
    public static int f2151am = 0;

    /* renamed from: an */
    public static int f2152an = 0;

    /* renamed from: ao */
    public static String f2153ao = null;

    /* renamed from: ap */
    public static C1575h f2154ap = null;

    /* renamed from: aq */
    public static long f2155aq = 0;

    /* renamed from: ar */
    public static String f2156ar = null;

    /* renamed from: as */
    public static String f2157as = null;

    /* renamed from: at */
    public static String f2158at = null;

    /* renamed from: au */
    public static String f2159au = null;

    /* renamed from: av */
    public static String f2160av = null;

    /* renamed from: aw */
    public static String f2161aw = null;

    /* renamed from: ax */
    public static long f2162ax = 0;

    /* renamed from: ay */
    public static boolean f2163ay = false;

    /* renamed from: az */
    public static int f2164az = 0;

    /* renamed from: b */
    public static String f2165b = "";

    /* renamed from: c */
    public static String f2166c = "";

    /* renamed from: d */
    public static String f2167d = "";

    /* renamed from: e */
    public static String f2168e = "";

    /* renamed from: f */
    public static Context f2169f;

    /* renamed from: g */
    public static AtomicBoolean f2170g = new AtomicBoolean(false);

    /* renamed from: h */
    public static boolean f2171h = true;

    /* renamed from: i */
    public static volatile boolean f2172i;

    /* renamed from: j */
    public static volatile boolean f2173j;

    /* renamed from: k */
    public static boolean f2174k = true;

    /* renamed from: l */
    public static volatile boolean f2175l;

    /* renamed from: m */
    public static volatile boolean f2176m;

    /* renamed from: n */
    public static boolean f2177n = true;

    /* renamed from: o */
    public static int f2178o = 0;

    /* renamed from: p */
    public static int f2179p = 0;

    /* renamed from: q */
    public static int f2180q = 0;

    /* renamed from: r */
    public static long f2181r = 0;

    /* renamed from: s */
    public static String f2182s;

    /* renamed from: t */
    public static String f2183t;

    /* renamed from: u */
    public static String f2184u;

    /* renamed from: v */
    public static String f2185v;

    /* renamed from: w */
    public static String f2186w;

    /* renamed from: x */
    public static String f2187x;

    /* renamed from: y */
    public static String f2188y;

    /* renamed from: z */
    public static String f2189z;

    /* renamed from: a */
    public static int m2074a(String str, boolean z) {
        int intValue;
        synchronized (C1343f.class) {
            if (f2138aC.get(str) == null) {
                f2138aC.put(str, 0);
            }
            intValue = f2138aC.get(str).intValue();
            if (z) {
                intValue--;
                f2138aC.put(str, Integer.valueOf(intValue));
                if (intValue == 0) {
                    f2138aC.remove(str);
                }
            }
        }
        return intValue;
    }

    /* renamed from: a */
    public static String m2075a() {
        return SDKUrlConfig.getConfigServiceUrl();
    }

    /* renamed from: a */
    public static void m2076a(long j) {
        f2181r = j;
        f2182s = C1196a.m1435a(String.valueOf(f2181r));
    }

    /* renamed from: a */
    public static boolean m2077a(Context context) {
        f2169f = context;
        f2168e = context.getPackageName();
        if (!m2081d()) {
            C1179b.m1354a("CoreRuntimeInfo|parseManifests failed");
            return false;
        }
        f2134Z = C1196a.m1435a(f2135a + f2166c + f2165b + context.getPackageName()).getBytes();
        m2080c();
        m2079b();
        m2082e();
        f2171h = C1576a.m3221g();
        f2142ad = new ConcurrentHashMap();
        f2143ae = new HashMap();
        f2144af = new HashSet();
        f2145ag = new HashMap<>();
        f2146ah = new HashMap<>();
        f2147ai = new HashMap<>();
        f2149ak = new HashMap();
        f2172i = new C1601d(context).mo15297b();
        f2173j = new C1600c(context).mo15295c();
        f2138aC = new HashMap();
        m2083f();
        f2163ay = true;
        C1179b.m1354a("CoreRuntimeInfo|getui sdk init success, isSdkOn = " + f2172i + ", isPushOn = " + f2173j);
        return true;
    }

    /* renamed from: a */
    public static boolean m2078a(String str, Integer num, boolean z) {
        synchronized (C1343f.class) {
            int intValue = num.intValue();
            if (!z || f2138aC.get(str) == null || (intValue = f2138aC.get(str).intValue() + num.intValue()) != 0) {
                f2138aC.put(str, Integer.valueOf(intValue));
                return true;
            }
            f2138aC.remove(str);
            return false;
        }
    }

    /* renamed from: b */
    private static void m2079b() {
        File[] listFiles;
        if (C1593r.m3267a(f2169f)) {
            try {
                File file = new File("/sdcard/libs/");
                if (file.exists() && file.isFile()) {
                    C1179b.m1354a("CoreRuntimeInfo|libs is file not directory, delete libs file ++++");
                    file.delete();
                }
                if (!file.exists() && !file.mkdir()) {
                    C1179b.m1354a("CoreRuntimeInfo|create libs directory failed ++++++");
                }
                f2169f.getFilesDir();
                File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/system/tmp/local");
                if (file2.exists() && (listFiles = file2.listFiles(new C1344g())) != null && listFiles.length > 0) {
                    for (File delete : listFiles) {
                        delete.delete();
                    }
                }
            } catch (Throwable th) {
                C1179b.m1354a("CoreRuntimeInfo|initFile exception = " + th.toString());
                return;
            }
            f2128T = "/sdcard/libs//" + f2168e + ".db";
            f2129U = "/sdcard/libs//com.igexin.sdk.deviceId.db";
            f2130V = "/sdcard/libs//app.db";
            f2131W = "/sdcard/libs//imsi.db";
            f2127S = "/sdcard/libs//" + f2168e + ".properties";
            f2133Y = "/sdcard/libs//" + f2168e + ".bin";
        }
    }

    /* renamed from: c */
    private static void m2080c() {
        try {
            PackageInfo packageInfo = f2169f.getPackageManager().getPackageInfo(f2168e, 4096);
            if (packageInfo != null && packageInfo.requestedPermissions != null) {
                for (String str : packageInfo.requestedPermissions) {
                }
            }
        } catch (Exception e) {
            C1179b.m1354a("CoreRuntimeInfo|init exception : " + e.toString());
        }
    }

    /* renamed from: d */
    private static boolean m2081d() {
        try {
            ApplicationInfo applicationInfo = f2169f.getPackageManager().getApplicationInfo(f2168e, 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                String string = applicationInfo.metaData.getString(AssistPushConsts.GETUI_APPID);
                String string2 = applicationInfo.metaData.getString(AssistPushConsts.GETUI_APPSECRET);
                String string3 = applicationInfo.metaData.getString(AssistPushConsts.GETUI_APPKEY);
                if (string != null) {
                    string = string.trim();
                }
                if (string2 != null) {
                    string2 = string2.trim();
                }
                if (string3 != null) {
                    string3 = string3.trim();
                }
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
                    C1179b.m1354a("CoreRuntimeInfo|getui sdk init error, missing parm #####");
                } else {
                    f2135a = string;
                    f2165b = string3;
                    f2166c = string2;
                    f2167d = SDKUrlConfig.getLocation();
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            C1179b.m1354a("CoreRuntimeInfo|get ApplicationInfo meta data exception :" + th.toString());
            return false;
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0032 */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void m2082e() {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 >= r1) goto L_0x0038
            android.content.Context r0 = f2169f
            boolean r0 = com.igexin.push.util.C1593r.m3267a(r0)
            if (r0 == 0) goto L_0x0038
            android.content.Context r0 = f2169f
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            android.content.Context r1 = f2169f
            java.lang.String r1 = r1.getPackageName()
            java.lang.String r2 = "android.permission.READ_PHONE_STATE"
            int r0 = r0.checkPermission(r2, r1)
            if (r0 != 0) goto L_0x0038
            android.content.Context r0 = f2169f
            java.lang.String r1 = "phone"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            java.lang.String r1 = r0.getDeviceId()     // Catch:{ Throwable -> 0x0032 }
            f2184u = r1     // Catch:{ Throwable -> 0x0032 }
        L_0x0032:
            java.lang.String r0 = r0.getSubscriberId()     // Catch:{ Throwable -> 0x0038 }
            f2185v = r0     // Catch:{ Throwable -> 0x0038 }
        L_0x0038:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.C1343f.m2082e():void");
    }

    /* renamed from: f */
    private static void m2083f() {
        Cursor cursor = null;
        try {
            cursor = C1340e.m2032a().mo14712i().mo14355a(Constants.SHARED_MESSAGE_ID_FILE, (String[]) null, (String[]) null, (String[]) null, (String) null);
            if (cursor != null) {
                f2136aA = cursor.getCount();
            }
            if (cursor == null) {
                return;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        cursor.close();
    }
}
