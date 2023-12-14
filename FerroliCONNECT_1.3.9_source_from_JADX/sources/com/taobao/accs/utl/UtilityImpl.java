package com.taobao.accs.utl;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import anet.channel.util.HMacUtil;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;

/* compiled from: Taobao */
public class UtilityImpl {
    public static final String NET_TYPE_2G = "2g";
    public static final String NET_TYPE_3G = "3g";
    public static final String NET_TYPE_4G = "4g";
    public static final String NET_TYPE_UNKNOWN = "unknown";
    public static final String NET_TYPE_WIFI = "wifi";
    public static final int TNET_FILE_NUM = 5;
    public static final int TNET_FILE_SIZE = 5242880;

    /* renamed from: a */
    private static final byte[] f3549a = new byte[0];

    /* renamed from: a */
    public static byte[] m3746a() {
        return null;
    }

    /* renamed from: b */
    public static String m3749b() {
        return "null";
    }

    /* renamed from: a */
    public static String m3737a(Context context) {
        String string = context.getSharedPreferences(Constants.SP_FILE_NAME, 4).getString(Constants.KEY_PROXY_HOST, (String) null);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        String d = m3756d();
        if (TextUtils.isEmpty(d)) {
            return null;
        }
        return d;
    }

    /* renamed from: b */
    public static int m3748b(Context context) {
        int i = context.getSharedPreferences(Constants.SP_FILE_NAME, 4).getInt(Constants.KEY_PROXY_PORT, -1);
        if (i > 0) {
            return i;
        }
        if (m3737a(context) == null) {
            return -1;
        }
        try {
            return m3759e();
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    /* renamed from: c */
    public static boolean m3754c(Context context) {
        int i;
        String str;
        synchronized (f3549a) {
            PackageInfo packageInfo = GlobalClientInfo.getInstance(context).getPackageInfo();
            int i2 = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getInt(Constants.KEY_APP_VERSION_CODE, -1);
            String string = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getString(Constants.KEY_APP_VERSION_NAME, "");
            if (packageInfo != null) {
                i = packageInfo.versionCode;
                str = packageInfo.versionName;
            } else {
                str = null;
                i = 0;
            }
            if (i2 == i) {
                if (string.equals(str)) {
                    return false;
                }
            }
            m3772n(context);
            ALog.m3728i("UtilityImpl", "appVersionChanged", "oldV", Integer.valueOf(i2), "nowV", Integer.valueOf(i), "oldN", string, "nowN", str);
            return true;
        }
    }

    /* renamed from: n */
    private static void m3772n(Context context) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putInt(Constants.KEY_APP_VERSION_CODE, GlobalClientInfo.getInstance(context).getPackageInfo().versionCode);
            edit.putString(Constants.KEY_APP_VERSION_NAME, GlobalClientInfo.getInstance(context).getPackageInfo().versionName);
            edit.apply();
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "saveAppVersion", th, new Object[0]);
        }
    }

    /* renamed from: d */
    public static boolean m3758d(Context context) {
        String agooCustomServiceName = AdapterGlobalClientInfo.getAgooCustomServiceName(context);
        if (TextUtils.isEmpty(agooCustomServiceName)) {
            return false;
        }
        ComponentName componentName = new ComponentName(context, agooCustomServiceName);
        PackageManager packageManager = context.getPackageManager();
        try {
            if (componentName.getPackageName().equals("!")) {
                ALog.m3727e("UtilityImpl", "getAgooServiceEnabled,exception,comptName.getPackageName()=" + componentName.getPackageName(), new Object[0]);
                return false;
            } else if (packageManager.getServiceInfo(componentName, 128).enabled) {
                return true;
            } else {
                return false;
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public static void m3742a(Context context, String str) {
        ComponentName componentName = new ComponentName(context, str);
        ALog.m3725d("UtilityImpl", "enableComponent", "comptName", componentName);
        try {
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        } catch (Throwable th) {
            ALog.m3730w("UtilityImpl", "enableComponent", th, new Object[0]);
        }
    }

    /* renamed from: b */
    public static boolean m3751b(Context context, String str) {
        ComponentName componentName = new ComponentName(context, str);
        PackageManager packageManager = context.getPackageManager();
        try {
            ALog.m3725d("UtilityImpl", "disableComponent,comptName=" + componentName.toString(), new Object[0]);
            if (packageManager.getComponentEnabledSetting(componentName) != 2) {
                packageManager.setComponentEnabledSetting(componentName, 2, 1);
                return true;
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static void enableService(Context context) {
        m3742a(context, AdapterUtilityImpl.channelService);
    }

    public static void disableService(Context context) {
        try {
            m3751b(context, AdapterUtilityImpl.channelService);
        } catch (Throwable unused) {
        }
    }

    /* renamed from: e */
    public static String m3760e(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "unknown";
            }
            if (activeNetworkInfo.getType() == 1) {
                return NET_TYPE_WIFI;
            }
            String subtypeName = activeNetworkInfo.getSubtypeName();
            if (!TextUtils.isEmpty(subtypeName)) {
                return subtypeName.replaceAll(" ", "");
            }
            return "";
        } catch (Throwable th) {
            th.printStackTrace();
            return "unknown";
        }
    }

    /* renamed from: f */
    public static String m3763f(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "unknown";
            }
            if (activeNetworkInfo.getType() == 1) {
                return NET_TYPE_WIFI;
            }
            switch (activeNetworkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return NET_TYPE_2G;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return NET_TYPE_3G;
                case 13:
                    return NET_TYPE_4G;
                default:
                    String subtypeName = activeNetworkInfo.getSubtypeName();
                    if (TextUtils.isEmpty(subtypeName)) {
                        return "unknown";
                    }
                    if (subtypeName.equalsIgnoreCase("td-scdma") || subtypeName.equalsIgnoreCase("td_scdma") || subtypeName.equalsIgnoreCase("tds_hsdpa")) {
                        return NET_TYPE_3G;
                    }
                    return "unknown";
            }
            ALog.m3726e("UtilityImpl", "getNetworkTypeExt", th, new Object[0]);
            return null;
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "getNetworkTypeExt", th, new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    public static String m3736a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Long.valueOf(j));
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "formatDay", th, new Object[0]);
            return "";
        }
    }

    /* renamed from: a */
    public static String m3738a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            ALog.m3727e("UtilityImpl", "getAppsign appkey null", new Object[0]);
            return null;
        }
        try {
            if (!TextUtils.isEmpty(str2)) {
                byte[] bytes = str2.getBytes();
                return HMacUtil.hmacSha1Hex(bytes, (str + str3).getBytes());
            }
            ALog.m3727e("UtilityImpl", "getAppsign secret null", new Object[0]);
            return null;
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "getAppsign", th, new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    public static int m3733a(Context context, String str, String str2, byte[] bArr) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || context == null || bArr == null) {
        }
        return -1;
    }

    /* renamed from: a */
    public static byte[] m3747a(Context context, String str, String str2) {
        if (context != null && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            return null;
        }
        ALog.m3728i("UtilityImpl", "get sslticket input null", new Object[0]);
        return null;
    }

    /* renamed from: g */
    public static boolean m3765g(Context context) {
        if (context == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = GlobalClientInfo.getInstance(context).getConnectivityManager().getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnected();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static String getDeviceId(Context context) {
        return AdapterUtilityImpl.getDeviceId(context);
    }

    /* renamed from: c */
    public static boolean m3755c(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            ALog.m3727e("UtilityImpl", "package not exist", "pkg", str);
            return false;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: boolean} */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        r3 = r3;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean utdidChanged(java.lang.String r3, android.content.Context r4) {
        /*
            r0 = 0
            byte[] r1 = f3549a     // Catch:{ Throwable -> 0x0025 }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x0025 }
            java.lang.String r2 = getDeviceId(r4)     // Catch:{ all -> 0x001d }
            android.content.SharedPreferences r3 = r4.getSharedPreferences(r3, r0)     // Catch:{ all -> 0x001d }
            java.lang.String r4 = "utdid"
            java.lang.String r3 = r3.getString(r4, r2)     // Catch:{ all -> 0x001d }
            boolean r3 = r3.equals(r2)     // Catch:{ all -> 0x001d }
            if (r3 != 0) goto L_0x001a
            r3 = 1
            goto L_0x001b
        L_0x001a:
            r3 = 0
        L_0x001b:
            monitor-exit(r1)     // Catch:{ all -> 0x0023 }
            goto L_0x0030
        L_0x001d:
            r4 = move-exception
            r3 = 0
        L_0x001f:
            monitor-exit(r1)     // Catch:{ all -> 0x0023 }
            throw r4     // Catch:{ Throwable -> 0x0021 }
        L_0x0021:
            r4 = move-exception
            goto L_0x0027
        L_0x0023:
            r4 = move-exception
            goto L_0x001f
        L_0x0025:
            r4 = move-exception
            r3 = 0
        L_0x0027:
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r1 = "UtilityImpl"
            java.lang.String r2 = "saveUtdid"
            com.taobao.accs.utl.ALog.m3726e(r1, r2, r4, r0)
        L_0x0030:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.UtilityImpl.utdidChanged(java.lang.String, android.content.Context):boolean");
    }

    public static void saveUtdid(String str, Context context) {
        JSONArray jSONArray;
        try {
            synchronized (f3549a) {
                String deviceId = getDeviceId(context);
                SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                String string = sharedPreferences.getString(Constants.SP_KEY_UTDID_LIST, (String) null);
                if (string == null || !string.contains(deviceId)) {
                    if (string == null) {
                        jSONArray = new JSONArray();
                    } else {
                        jSONArray = new JSONArray(string);
                    }
                    jSONArray.put(deviceId);
                    edit.putString(Constants.SP_KEY_UTDID_LIST, jSONArray.toString());
                }
                edit.putString("utdid", deviceId);
                edit.apply();
            }
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "saveUtdid", th, new Object[0]);
        }
    }

    public static List<String> getUtdids(String str, Context context) {
        ArrayList arrayList;
        try {
            synchronized (f3549a) {
                String string = context.getSharedPreferences(str, 0).getString(Constants.SP_KEY_UTDID_LIST, (String) null);
                arrayList = new ArrayList();
                if (string != null) {
                    JSONArray jSONArray = new JSONArray(string);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add(jSONArray.getString(i));
                    }
                }
                arrayList.add(getDeviceId(context));
            }
            return arrayList;
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "getUtdidList", th, new Object[0]);
            return null;
        }
    }

    public static void hitUtdid(String str, Context context, String str2) {
        try {
            synchronized (f3549a) {
                SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(str2);
                edit.putString(Constants.SP_KEY_UTDID_LIST, jSONArray.toString());
                edit.apply();
            }
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "hitUtdid", th, new Object[0]);
        }
    }

    public static String getUtdid(String str, Context context) {
        String string;
        try {
            synchronized (f3549a) {
                string = context.getSharedPreferences(str, 0).getString("utdid", getDeviceId(context));
            }
            return string;
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "getUtdid", th, new Object[0]);
            return "";
        }
    }

    /* renamed from: a */
    public static void m3743a(Context context, String str, long j) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_CHANNEL_FILE_NAME, 0).edit();
            edit.putLong(str, j);
            edit.apply();
            ALog.m3725d("UtilityImpl", "setServiceTime:" + j, new Object[0]);
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "setServiceTime:", th, new Object[0]);
        }
    }

    /* renamed from: h */
    public static long m3766h(Context context) {
        long j;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_CHANNEL_FILE_NAME, 0);
            long j2 = sharedPreferences.getLong(Constants.SP_KEY_SERVICE_START, 0);
            j = j2 > 0 ? sharedPreferences.getLong(Constants.SP_KEY_SERVICE_END, 0) - j2 : 0;
            try {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putLong(Constants.SP_KEY_SERVICE_START, 0);
                edit.putLong(Constants.SP_KEY_SERVICE_END, 0);
                edit.apply();
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            j = 0;
            ALog.m3726e("UtilityImpl", "getServiceAliveTime:", th, new Object[0]);
            return j;
        }
        return j;
    }

    /* renamed from: i */
    public static String m3767i(Context context) {
        try {
            return GlobalClientInfo.getInstance(context).getPackageInfo().versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isMainProcess(Context context) {
        return AdapterUtilityImpl.isMainProcess(context);
    }

    /* renamed from: a */
    public static int m3734a(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return str.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* renamed from: a */
    public static String m3739a(Throwable th) {
        return AdapterUtilityImpl.getStackMsg(th);
    }

    /* renamed from: j */
    public static String m3768j(Context context) {
        try {
            return context.getSharedPreferences(Constants.SP_COOKIE_FILE_NAME, 4).getString(Constants.SP_KEY_COOKIE_SEC, (String) null);
        } catch (Exception e) {
            ALog.m3726e("UtilityImpl", "reStoreCookie fail", e, new Object[0]);
            return null;
        }
    }

    /* renamed from: c */
    public static long m3753c() {
        return AdapterUtilityImpl.getUsableSpace();
    }

    /* renamed from: d */
    public static String m3756d() {
        if (Build.VERSION.SDK_INT < 11) {
            return Proxy.getDefaultHost();
        }
        return System.getProperty("http.proxyHost");
    }

    /* renamed from: e */
    public static int m3759e() {
        if (Build.VERSION.SDK_INT < 11) {
            return Proxy.getDefaultPort();
        }
        try {
            return Integer.parseInt(System.getProperty("http.proxyPort"));
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    /* renamed from: f */
    public static String m3762f() {
        String str = m3756d() + ":" + m3759e();
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.m3725d("UtilityImpl", "getProxy:" + str, new Object[0]);
        }
        return str;
    }

    /* renamed from: k */
    public static String m3769k(Context context) {
        return AdapterUtilityImpl.isNotificationEnabled(context);
    }

    /* renamed from: d */
    public static String m3757d(Context context, String str) {
        try {
            File externalFilesDir = context.getExternalFilesDir("emastnetlogs");
            if (externalFilesDir == null || !externalFilesDir.exists() || !externalFilesDir.canWrite()) {
                externalFilesDir = context.getDir("emaslogs", 0);
            }
            ALog.m3725d("UtilityImpl", "getTnetLogFilePath :" + externalFilesDir, new Object[0]);
            return externalFilesDir + "/" + str.toLowerCase();
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "getTnetLogFilePath", th, new Object[0]);
            return null;
        }
    }

    /* renamed from: a */
    public static String m3735a(int i) {
        try {
            return String.valueOf(i);
        } catch (Exception e) {
            ALog.m3726e("UtilityImpl", "int2String", e, new Object[0]);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004c, code lost:
        return;
     */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m3761e(android.content.Context r3, java.lang.String r4) {
        /*
            byte[] r0 = f3549a     // Catch:{ Throwable -> 0x0050 }
            monitor-enter(r0)     // Catch:{ Throwable -> 0x0050 }
            java.lang.String r1 = "EMAS_ACCS_SDK"
            r2 = 0
            android.content.SharedPreferences r3 = r3.getSharedPreferences(r1, r2)     // Catch:{ all -> 0x004d }
            java.lang.String r1 = "appkey"
            java.lang.String r2 = ""
            java.lang.String r1 = r3.getString(r1, r2)     // Catch:{ all -> 0x004d }
            boolean r2 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x004d }
            if (r2 != 0) goto L_0x004b
            boolean r2 = r1.equals(r4)     // Catch:{ all -> 0x004d }
            if (r2 != 0) goto L_0x004b
            boolean r2 = r1.contains(r4)     // Catch:{ all -> 0x004d }
            if (r2 == 0) goto L_0x0025
            goto L_0x004b
        L_0x0025:
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x004d }
            if (r2 == 0) goto L_0x002c
            goto L_0x003d
        L_0x002c:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x004d }
            r2.<init>(r1)     // Catch:{ all -> 0x004d }
            java.lang.String r1 = "|"
            r2.append(r1)     // Catch:{ all -> 0x004d }
            r2.append(r4)     // Catch:{ all -> 0x004d }
            java.lang.String r4 = r2.toString()     // Catch:{ all -> 0x004d }
        L_0x003d:
            android.content.SharedPreferences$Editor r3 = r3.edit()     // Catch:{ all -> 0x004d }
            java.lang.String r1 = "appkey"
            r3.putString(r1, r4)     // Catch:{ all -> 0x004d }
            r3.apply()     // Catch:{ all -> 0x004d }
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            goto L_0x0054
        L_0x004b:
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x004d:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            throw r3     // Catch:{ Throwable -> 0x0050 }
        L_0x0050:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0054:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.utl.UtilityImpl.m3761e(android.content.Context, java.lang.String):void");
    }

    public static void clearSharePreferences(Context context) {
        try {
            synchronized (f3549a) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_FILE_NAME, 0);
                String string = sharedPreferences.getString("appkey", (String) null);
                String string2 = sharedPreferences.getString("app_sercet", (String) null);
                String string3 = sharedPreferences.getString(Constants.KEY_PROXY_HOST, (String) null);
                int i = sharedPreferences.getInt(Constants.KEY_PROXY_PORT, -1);
                int i2 = sharedPreferences.getInt(Constants.SP_KEY_VERSION, -1);
                SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.clear();
                if (!TextUtils.isEmpty(string)) {
                    edit.putString("appkey", string);
                }
                if (!TextUtils.isEmpty(string2)) {
                    edit.putString("app_sercet", string2);
                }
                if (!TextUtils.isEmpty(string3)) {
                    edit.putString(Constants.KEY_PROXY_HOST, string3);
                }
                if (i > 0) {
                    edit.putInt(Constants.KEY_PROXY_PORT, i);
                }
                if (i2 > 0) {
                    edit.putInt(Constants.SP_KEY_VERSION, i2);
                }
                edit.apply();
            }
        } catch (Throwable th) {
            ALog.m3726e("UtilityImpl", "clearSharePreferences", th, new Object[0]);
        }
    }

    /* renamed from: g */
    public static String m3764g() {
        Class[] clsArr = {String.class};
        Object[] objArr = {"ro.build.version.emui"};
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str = (String) cls.getDeclaredMethod("get", clsArr).invoke(cls, objArr);
            ALog.m3725d("UtilityImpl", "getEmuiVersion", "result", str);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            return "";
        } catch (Exception e) {
            ALog.m3726e("UtilityImpl", "getEmuiVersion", e, new Object[0]);
            return "";
        }
    }

    /* renamed from: a */
    public static final Map<String, String> m3741a(Map<String, List<String>> map) {
        HashMap hashMap = new HashMap();
        try {
            for (Map.Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                if (!TextUtils.isEmpty(str)) {
                    String a = m3740a((List<String>) (List) next.getValue());
                    if (!TextUtils.isEmpty(a)) {
                        if (!str.startsWith(":")) {
                            str = str.toLowerCase(Locale.US);
                        }
                        hashMap.put(str, a);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return hashMap;
    }

    /* renamed from: a */
    public static final String m3740a(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            stringBuffer.append(list.get(i));
            if (i < size - 1) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    /* renamed from: b */
    public static String m3750b(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    /* renamed from: l */
    public static boolean m3770l(Context context) {
        try {
            return context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getBoolean(Constants.SP_KEY_ENABLE_CHANNEL_PROCESS, true);
        } catch (Throwable unused) {
            return false;
        }
    }

    /* renamed from: a */
    public static void m3745a(Context context, boolean z) {
        try {
            SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putBoolean(Constants.SP_KEY_ENABLE_CHANNEL_PROCESS, z);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b */
    public static boolean m3752b(Context context, boolean z) {
        if (m3770l(context)) {
            return false;
        }
        ALog.m3725d("UtilityImpl", "channel process is disabled, kill it", new Object[0]);
        if (!z) {
            return true;
        }
        Process.killProcess(Process.myPid());
        return true;
    }

    public static void saveChannelInitClass(Context context, String str) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_FILE_NAME, 0);
            String string = sharedPreferences.getString(Constants.SP_KEY_CHANNEL_INIT, (String) null);
            ArrayList arrayList = new ArrayList();
            if (string != null) {
                JSONArray jSONArray = new JSONArray(string);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
            }
            if (!arrayList.contains(str)) {
                arrayList.add(str);
                JSONArray jSONArray2 = new JSONArray();
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    jSONArray2.put(arrayList.get(i2));
                }
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(Constants.SP_KEY_CHANNEL_INIT, jSONArray2.toString());
                edit.commit();
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public static void m3744a(Context context, List<String> list) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_FILE_NAME, 0);
            String string = sharedPreferences.getString(Constants.SP_KEY_CHANNEL_INIT, (String) null);
            ArrayList arrayList = new ArrayList();
            if (string != null) {
                JSONArray jSONArray = new JSONArray(string);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
            }
            if (arrayList.removeAll(list)) {
                JSONArray jSONArray2 = new JSONArray();
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    jSONArray2.put(arrayList.get(i2));
                }
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(Constants.SP_KEY_CHANNEL_INIT, jSONArray2.toString());
                edit.commit();
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: m */
    public static List<String> m3771m(Context context) {
        try {
            String string = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getString(Constants.SP_KEY_CHANNEL_INIT, (String) null);
            if (string != null) {
                JSONArray jSONArray = new JSONArray(string);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                return arrayList;
            }
        } catch (Throwable unused) {
        }
        return null;
    }
}
