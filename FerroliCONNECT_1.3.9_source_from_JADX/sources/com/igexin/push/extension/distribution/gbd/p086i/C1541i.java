package com.igexin.push.extension.distribution.gbd.p086i;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p076b.C1487g;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.p107tb.appyunsdk.Constant;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.taobao.accs.utl.UtilityImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.android.agoo.common.AgooConstants;
import p110io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.i */
public class C1541i {

    /* renamed from: a */
    private static String f2954a = "eth0";

    /* JADX WARNING: Removed duplicated region for block: B:31:0x004f A[SYNTHETIC, Splitter:B:31:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0059 A[SYNTHETIC, Splitter:B:36:0x0059] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0065 A[SYNTHETIC, Splitter:B:43:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x006f A[SYNTHETIC, Splitter:B:48:0x006f] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m2998a() {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            java.lang.String r3 = "cat /sys/class/net/wlan0/address"
            java.lang.Process r2 = r2.exec(r3)     // Catch:{ Throwable -> 0x0047, all -> 0x0044 }
            java.io.InputStream r3 = r2.getInputStream()     // Catch:{ Throwable -> 0x0042 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0042 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x0042 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0042 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0042 }
            java.lang.String r1 = r3.readLine()     // Catch:{ Throwable -> 0x003d, all -> 0x003a }
            boolean r4 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x003d, all -> 0x003a }
            if (r4 == 0) goto L_0x0026
            goto L_0x0027
        L_0x0026:
            r0 = r1
        L_0x0027:
            r3.close()     // Catch:{ IOException -> 0x002b }
            goto L_0x002f
        L_0x002b:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x002f:
            if (r2 == 0) goto L_0x0039
            r2.destroy()     // Catch:{ Throwable -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x0039:
            return r0
        L_0x003a:
            r0 = move-exception
            r1 = r3
            goto L_0x0063
        L_0x003d:
            r1 = move-exception
            r5 = r3
            r3 = r1
            r1 = r5
            goto L_0x004a
        L_0x0042:
            r3 = move-exception
            goto L_0x004a
        L_0x0044:
            r0 = move-exception
            r2 = r1
            goto L_0x0063
        L_0x0047:
            r2 = move-exception
            r3 = r2
            r2 = r1
        L_0x004a:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r3)     // Catch:{ all -> 0x0062 }
            if (r1 == 0) goto L_0x0057
            r1.close()     // Catch:{ IOException -> 0x0053 }
            goto L_0x0057
        L_0x0053:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x0057:
            if (r2 == 0) goto L_0x0061
            r2.destroy()     // Catch:{ Throwable -> 0x005d }
            goto L_0x0061
        L_0x005d:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x0061:
            return r0
        L_0x0062:
            r0 = move-exception
        L_0x0063:
            if (r1 == 0) goto L_0x006d
            r1.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x006d
        L_0x0069:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x006d:
            if (r2 == 0) goto L_0x0077
            r2.destroy()     // Catch:{ Throwable -> 0x0073 }
            goto L_0x0077
        L_0x0073:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x0077:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1541i.m2998a():java.lang.String");
    }

    /* renamed from: a */
    public static String m2999a(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    /* renamed from: a */
    public static String m3000a(Context context, int i) {
        File[] listFiles;
        StringBuilder sb = new StringBuilder();
        try {
            for (PackageInfo next : context.getPackageManager().getInstalledPackages(i)) {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(next.packageName, 128);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        Bundle bundle = applicationInfo.metaData;
                        Object obj = "";
                        String str = (String) (bundle.get(AssistPushConsts.GETUI_APPID) == null ? obj : bundle.get(AssistPushConsts.GETUI_APPID));
                        String str2 = (String) (bundle.get(AssistPushConsts.GETUI_APPKEY) == null ? obj : bundle.get(AssistPushConsts.GETUI_APPKEY));
                        if (bundle.get(AssistPushConsts.GETUI_APPSECRET) != null) {
                            obj = bundle.get(AssistPushConsts.GETUI_APPSECRET);
                        }
                        String str3 = (String) obj;
                        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                            sb.append(next.packageName);
                            sb.append("|");
                        }
                    }
                }
            }
            File file = new File("/sdcard/libs/");
            if (file.exists() && (listFiles = file.listFiles(new C1542j())) != null) {
                for (File listFiles2 : listFiles) {
                    File[] listFiles3 = listFiles2.listFiles(new C1545m());
                    if (listFiles3 != null) {
                        for (File name : listFiles3) {
                            String name2 = name.getName();
                            if (!TextUtils.isEmpty(name2) && name2.contains(".bin")) {
                                sb.append(name2.substring(0, name2.lastIndexOf(".")));
                                sb.append("|");
                            }
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(sb.toString()) && sb.toString().endsWith("|")) {
                sb = sb.deleteCharAt(sb.length() - 1);
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static String m3001a(PackageInfo packageInfo) {
        int i = packageInfo.applicationInfo.uid;
        String str = TrafficStats.getUidTxBytes(i) + "";
        String str2 = TrafficStats.getUidRxBytes(i) + "";
        if (str.equals("-1")) {
            str = m3002a("cat  /proc/uid_stat/" + i + "/tcp_rcv");
        }
        if (str2.equals("-1")) {
            str2 = m3002a("cat  /proc/uid_stat/" + i + "/tcp_snd");
        }
        return str + DispatchConstants.SIGN_SPLIT_SYMBOL + str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x004d A[SYNTHETIC, Splitter:B:31:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0057 A[SYNTHETIC, Splitter:B:36:0x0057] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0063 A[SYNTHETIC, Splitter:B:43:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x006d A[SYNTHETIC, Splitter:B:48:0x006d] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3002a(java.lang.String r5) {
        /*
            java.lang.String r0 = "0"
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0045, all -> 0x0042 }
            java.lang.Process r5 = r2.exec(r5)     // Catch:{ Throwable -> 0x0045, all -> 0x0042 }
            java.io.InputStream r2 = r5.getInputStream()     // Catch:{ Throwable -> 0x0040 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0040 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0040 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0040 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0040 }
            java.lang.String r1 = r2.readLine()     // Catch:{ Throwable -> 0x003b, all -> 0x0038 }
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x003b, all -> 0x0038 }
            if (r3 == 0) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            r0 = r1
        L_0x0025:
            r2.close()     // Catch:{ IOException -> 0x0029 }
            goto L_0x002d
        L_0x0029:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x002d:
            if (r5 == 0) goto L_0x0037
            r5.destroy()     // Catch:{ Throwable -> 0x0033 }
            goto L_0x0037
        L_0x0033:
            r5 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)
        L_0x0037:
            return r0
        L_0x0038:
            r0 = move-exception
            r1 = r2
            goto L_0x0061
        L_0x003b:
            r1 = move-exception
            r4 = r2
            r2 = r1
            r1 = r4
            goto L_0x0048
        L_0x0040:
            r2 = move-exception
            goto L_0x0048
        L_0x0042:
            r0 = move-exception
            r5 = r1
            goto L_0x0061
        L_0x0045:
            r5 = move-exception
            r2 = r5
            r5 = r1
        L_0x0048:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r2)     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x0055
            r1.close()     // Catch:{ IOException -> 0x0051 }
            goto L_0x0055
        L_0x0051:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x0055:
            if (r5 == 0) goto L_0x005f
            r5.destroy()     // Catch:{ Throwable -> 0x005b }
            goto L_0x005f
        L_0x005b:
            r5 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)
        L_0x005f:
            return r0
        L_0x0060:
            r0 = move-exception
        L_0x0061:
            if (r1 == 0) goto L_0x006b
            r1.close()     // Catch:{ IOException -> 0x0067 }
            goto L_0x006b
        L_0x0067:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x006b:
            if (r5 == 0) goto L_0x0075
            r5.destroy()     // Catch:{ Throwable -> 0x0071 }
            goto L_0x0075
        L_0x0071:
            r5 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)
        L_0x0075:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3002a(java.lang.String):java.lang.String");
    }

    /* renamed from: a */
    public static String m3003a(String str, Context context) {
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(str, 128).metaData;
            if (bundle == null) {
                return "";
            }
            for (String str2 : bundle.keySet()) {
                if ("com.sdk.plus.appid".equals(str2)) {
                    return bundle.get(str2).toString();
                }
            }
            return "";
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }

    /* renamed from: a */
    public static String m3004a(String str, String str2) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop" + " " + str).getInputStream()));
            String str3 = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return str3;
                }
                str3 = str3 + readLine;
            }
        } catch (Exception unused) {
            return str2;
        }
    }

    /* renamed from: a */
    public static boolean m3005a(Context context) {
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ((ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME)).getMemoryInfo(memoryInfo);
            if ((memoryInfo.availMem / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID < ((long) C1488a.f2624H)) {
                return false;
            }
            return (((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) + Runtime.getRuntime().freeMemory()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID > ((long) C1488a.f2625I);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m3006a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: a */
    public static boolean m3007a(Intent intent, Context context) {
        if (!(intent == null || context == null)) {
            try {
                List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
                return queryIntentServices != null && queryIntentServices.size() > 0;
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
        return false;
    }

    /* renamed from: a */
    public static boolean m3008a(String str, String str2, List<ActivityManager.RunningServiceInfo> list) {
        if (list != null) {
            try {
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).service.getClassName().equals(str) && list.get(i).service.getPackageName().equals(str2)) {
                            return true;
                        }
                    }
                    return false;
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
        return false;
    }

    /* renamed from: b */
    public static C1487g m3009b(Context context) {
        C1487g gVar = new C1487g();
        if (!C1490c.f2752f) {
            return gVar;
        }
        try {
            if (m3037k()) {
                C1540h.m2997b("GBD_Utils", "SLMA getMac old.");
                WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                gVar.mo15080b(connectionInfo.getMacAddress());
                gVar.mo15081c(connectionInfo.getSSID());
                gVar.mo15079a(connectionInfo.getBSSID());
                DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                gVar.mo15082d(m2999a(dhcpInfo.gateway));
                gVar.mo15083e(m2999a(dhcpInfo.ipAddress));
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                C1540h.m2997b("GBD_Utils", "SLMA getMac new.");
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    if ("wlan0".equalsIgnoreCase(nextElement.getName())) {
                        byte[] bArr = new byte[0];
                        byte[] hardwareAddress = nextElement.getHardwareAddress();
                        if (hardwareAddress != null) {
                            if (hardwareAddress.length != 0) {
                                StringBuilder sb = new StringBuilder();
                                int length = hardwareAddress.length;
                                for (int i = 0; i < length; i++) {
                                    sb.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                                }
                                if (sb.length() > 0) {
                                    sb.deleteCharAt(sb.length() - 1);
                                }
                                gVar.mo15080b(sb.toString());
                            }
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            C1540h.m2996a(th2);
        }
        return gVar;
    }

    /* renamed from: b */
    public static String m3010b() {
        try {
            String str = Build.BRAND;
            return (TextUtils.isEmpty(str) || str.equals("unknown")) ? Build.MANUFACTURER : str;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x001c A[Catch:{ Throwable -> 0x003b }] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3011b(java.lang.String r2, android.content.Context r3) {
        /*
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ Throwable -> 0x003b }
            r0 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r2 = r3.getApplicationInfo(r2, r0)     // Catch:{ Throwable -> 0x003b }
            android.os.Bundle r2 = r2.metaData     // Catch:{ Throwable -> 0x003b }
            if (r2 == 0) goto L_0x003f
            java.util.Set r3 = r2.keySet()     // Catch:{ Throwable -> 0x003b }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x003b }
        L_0x0016:
            boolean r0 = r3.hasNext()     // Catch:{ Throwable -> 0x003b }
            if (r0 == 0) goto L_0x003f
            java.lang.Object r0 = r3.next()     // Catch:{ Throwable -> 0x003b }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x003b }
            java.lang.String r1 = "PUSH_APPID"
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x003b }
            if (r1 != 0) goto L_0x0032
            java.lang.String r1 = "appid"
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x003b }
            if (r1 == 0) goto L_0x0016
        L_0x0032:
            java.lang.Object r2 = r2.get(r0)     // Catch:{ Throwable -> 0x003b }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x003b }
            return r2
        L_0x003b:
            r2 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r2)
        L_0x003f:
            java.lang.String r2 = ""
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3011b(java.lang.String, android.content.Context):java.lang.String");
    }

    /* renamed from: b */
    public static String m3012b(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        try {
            byte[] b = m3014b(str2 + "/" + str + ".db");
            String[] strArr = null;
            if (b == null) {
                C1540h.m2997b("GBDUtils", str + ".db not exist");
                return null;
            }
            ArrayList arrayList = new ArrayList();
            if (m3006a(C1343f.f2169f, "android.permission.READ_PHONE_STATE")) {
                String deviceId = ((TelephonyManager) C1343f.f2169f.getSystemService(Constant.PHONE)).getDeviceId();
                if (!TextUtils.isEmpty(deviceId)) {
                    String a = C1558z.m3084a(deviceId);
                    if (!TextUtils.isEmpty(a) && !a.equals(C1343f.f2110B)) {
                        arrayList.add(a);
                    }
                }
            }
            arrayList.add(C1343f.f2110B);
            arrayList.add(C1558z.m3084a(""));
            arrayList.add(C1558z.m3084a("000000000000000"));
            arrayList.add(C1558z.m3084a("cantgetimei"));
            String a2 = C1557y.m3077a(1, C1490c.f2747a);
            if (!TextUtils.isEmpty(a2)) {
                arrayList.add(C1558z.m3084a(a2));
            }
            Iterator it = arrayList.iterator();
            String str3 = null;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String str4 = new String(C1551s.m3048a(b, (String) it.next()));
                if (Pattern.matches("[\\.:0-9a-zA-Z\\|]+", str4)) {
                    strArr = str4.split("\\|");
                    str3 = str4;
                    break;
                }
                str3 = str4;
            }
            return (strArr == null || strArr.length != 5) ? "" : str3;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }

    /* renamed from: b */
    public static boolean m3013b(Intent intent, Context context) {
        if (!(intent == null || context == null)) {
            try {
                List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
                return queryBroadcastReceivers != null && queryBroadcastReceivers.size() > 0;
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0050 A[SYNTHETIC, Splitter:B:33:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x005a A[SYNTHETIC, Splitter:B:38:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0061 A[SYNTHETIC, Splitter:B:43:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x006b A[SYNTHETIC, Splitter:B:48:0x006b] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m3014b(java.lang.String r5) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 != 0) goto L_0x000d
            return r1
        L_0x000d:
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0048, all -> 0x0044 }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0048, all -> 0x0044 }
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0041, all -> 0x003e }
            r5.<init>()     // Catch:{ Exception -> 0x0041, all -> 0x003e }
        L_0x001b:
            int r3 = r2.read(r0)     // Catch:{ Exception -> 0x003c }
            r4 = -1
            if (r3 == r4) goto L_0x0027
            r4 = 0
            r5.write(r0, r4, r3)     // Catch:{ Exception -> 0x003c }
            goto L_0x001b
        L_0x0027:
            byte[] r1 = r5.toByteArray()     // Catch:{ Exception -> 0x003c }
            r2.close()     // Catch:{ Exception -> 0x002f }
            goto L_0x0033
        L_0x002f:
            r0 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)
        L_0x0033:
            r5.close()     // Catch:{ Exception -> 0x0037 }
            goto L_0x005d
        L_0x0037:
            r5 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)
            goto L_0x005d
        L_0x003c:
            r0 = move-exception
            goto L_0x004b
        L_0x003e:
            r0 = move-exception
            r5 = r1
            goto L_0x005f
        L_0x0041:
            r0 = move-exception
            r5 = r1
            goto L_0x004b
        L_0x0044:
            r0 = move-exception
            r5 = r1
            r2 = r5
            goto L_0x005f
        L_0x0048:
            r0 = move-exception
            r5 = r1
            r2 = r5
        L_0x004b:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)     // Catch:{ all -> 0x005e }
            if (r2 == 0) goto L_0x0058
            r2.close()     // Catch:{ Exception -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r0 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r0)
        L_0x0058:
            if (r5 == 0) goto L_0x005d
            r5.close()     // Catch:{ Exception -> 0x0037 }
        L_0x005d:
            return r1
        L_0x005e:
            r0 = move-exception
        L_0x005f:
            if (r2 == 0) goto L_0x0069
            r2.close()     // Catch:{ Exception -> 0x0065 }
            goto L_0x0069
        L_0x0065:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x0069:
            if (r5 == 0) goto L_0x0073
            r5.close()     // Catch:{ Exception -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r5 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r5)
        L_0x0073:
            goto L_0x0075
        L_0x0074:
            throw r0
        L_0x0075:
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3014b(java.lang.String):byte[]");
    }

    /* renamed from: c */
    public static String m3015c() {
        if (C1490c.f2731H != null) {
            return C1490c.f2731H;
        }
        try {
            String e = m3024e();
            if (TextUtils.isEmpty(e)) {
                e = m3020d();
            }
            if (!TextUtils.isEmpty(e)) {
                C1490c.f2731H = e;
            } else {
                C1490c.f2731H = "unknown";
            }
            return C1490c.f2731H;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "unknown";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x00c6 A[SYNTHETIC, Splitter:B:61:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00d0 A[SYNTHETIC, Splitter:B:67:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00d9 A[SYNTHETIC, Splitter:B:72:0x00d9] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x00e3 A[SYNTHETIC, Splitter:B:78:0x00e3] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3016c(java.lang.String r8) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r2 = "/sdcard/libs/"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x00ec }
            boolean r2 = r1.exists()     // Catch:{ Throwable -> 0x00ec }
            if (r2 != 0) goto L_0x000f
            return r0
        L_0x000f:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x00ec }
            r2.<init>()     // Catch:{ Throwable -> 0x00ec }
            com.igexin.push.extension.distribution.gbd.i.n r3 = new com.igexin.push.extension.distribution.gbd.i.n     // Catch:{ Throwable -> 0x00ec }
            r3.<init>(r8)     // Catch:{ Throwable -> 0x00ec }
            java.io.File[] r3 = r1.listFiles(r3)     // Catch:{ Throwable -> 0x00ec }
            java.util.Collections.addAll(r2, r3)     // Catch:{ Throwable -> 0x00ec }
            boolean r3 = r2.isEmpty()     // Catch:{ Throwable -> 0x00ec }
            r4 = 0
            if (r3 == 0) goto L_0x0045
            com.igexin.push.extension.distribution.gbd.i.o r3 = new com.igexin.push.extension.distribution.gbd.i.o     // Catch:{ Throwable -> 0x00ec }
            r3.<init>()     // Catch:{ Throwable -> 0x00ec }
            java.io.File[] r1 = r1.listFiles(r3)     // Catch:{ Throwable -> 0x00ec }
            int r3 = r1.length     // Catch:{ Throwable -> 0x00ec }
            r5 = 0
        L_0x0032:
            if (r5 >= r3) goto L_0x0045
            r6 = r1[r5]     // Catch:{ Throwable -> 0x00ec }
            com.igexin.push.extension.distribution.gbd.i.p r7 = new com.igexin.push.extension.distribution.gbd.i.p     // Catch:{ Throwable -> 0x00ec }
            r7.<init>(r8)     // Catch:{ Throwable -> 0x00ec }
            java.io.File[] r6 = r6.listFiles(r7)     // Catch:{ Throwable -> 0x00ec }
            java.util.Collections.addAll(r2, r6)     // Catch:{ Throwable -> 0x00ec }
            int r5 = r5 + 1
            goto L_0x0032
        L_0x0045:
            boolean r8 = r2.isEmpty()     // Catch:{ Throwable -> 0x00ec }
            if (r8 != 0) goto L_0x00f0
            r8 = 1024(0x400, float:1.435E-42)
            byte[] r8 = new byte[r8]     // Catch:{ Throwable -> 0x00ec }
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00be, all -> 0x00ba }
            java.lang.Object r2 = r2.get(r4)     // Catch:{ Throwable -> 0x00be, all -> 0x00ba }
            java.io.File r2 = (java.io.File) r2     // Catch:{ Throwable -> 0x00be, all -> 0x00ba }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x00be, all -> 0x00ba }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x00b7, all -> 0x00b4 }
            r2.<init>()     // Catch:{ Throwable -> 0x00b7, all -> 0x00b4 }
        L_0x005f:
            int r3 = r1.read(r8)     // Catch:{ Throwable -> 0x00b2 }
            r5 = -1
            if (r3 == r5) goto L_0x006a
            r2.write(r8, r4, r3)     // Catch:{ Throwable -> 0x00b2 }
            goto L_0x005f
        L_0x006a:
            byte[] r8 = r2.toByteArray()     // Catch:{ Throwable -> 0x00b2 }
            java.lang.String r3 = com.igexin.push.core.C1343f.f2184u     // Catch:{ Throwable -> 0x00b2 }
            if (r3 != 0) goto L_0x0075
            java.lang.String r3 = "cantgetimei"
            goto L_0x0077
        L_0x0075:
            java.lang.String r3 = com.igexin.push.core.C1343f.f2184u     // Catch:{ Throwable -> 0x00b2 }
        L_0x0077:
            java.lang.String r3 = com.igexin.push.extension.distribution.gbd.p086i.C1558z.m3084a((java.lang.String) r3)     // Catch:{ Throwable -> 0x00b2 }
            java.lang.String r4 = new java.lang.String     // Catch:{ Throwable -> 0x00b2 }
            byte[] r8 = com.igexin.p032b.p033a.p034a.C1150a.m1231a((byte[]) r8, (java.lang.String) r3)     // Catch:{ Throwable -> 0x00b2 }
            r4.<init>(r8)     // Catch:{ Throwable -> 0x00b2 }
            java.lang.String r8 = "\\|"
            java.lang.String[] r8 = r4.split(r8)     // Catch:{ Throwable -> 0x00b2 }
            int r3 = r8.length     // Catch:{ Throwable -> 0x00b2 }
            r4 = 1
            if (r3 <= r4) goto L_0x00a1
            r8 = r8[r4]     // Catch:{ Throwable -> 0x00b2 }
            r1.close()     // Catch:{ Exception -> 0x0094 }
            goto L_0x0098
        L_0x0094:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)     // Catch:{ Throwable -> 0x00ec }
        L_0x0098:
            r2.close()     // Catch:{ Exception -> 0x009c }
            goto L_0x00a0
        L_0x009c:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)     // Catch:{ Throwable -> 0x00ec }
        L_0x00a0:
            return r8
        L_0x00a1:
            r1.close()     // Catch:{ Exception -> 0x00a5 }
            goto L_0x00a9
        L_0x00a5:
            r8 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r8)     // Catch:{ Throwable -> 0x00ec }
        L_0x00a9:
            r2.close()     // Catch:{ Exception -> 0x00ad }
            goto L_0x00f0
        L_0x00ad:
            r8 = move-exception
        L_0x00ae:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r8)     // Catch:{ Throwable -> 0x00ec }
            goto L_0x00f0
        L_0x00b2:
            r8 = move-exception
            goto L_0x00c1
        L_0x00b4:
            r8 = move-exception
            r2 = r0
            goto L_0x00d7
        L_0x00b7:
            r8 = move-exception
            r2 = r0
            goto L_0x00c1
        L_0x00ba:
            r8 = move-exception
            r1 = r0
            r2 = r1
            goto L_0x00d7
        L_0x00be:
            r8 = move-exception
            r1 = r0
            r2 = r1
        L_0x00c1:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r8)     // Catch:{ all -> 0x00d6 }
            if (r1 == 0) goto L_0x00ce
            r1.close()     // Catch:{ Exception -> 0x00ca }
            goto L_0x00ce
        L_0x00ca:
            r8 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r8)     // Catch:{ Throwable -> 0x00ec }
        L_0x00ce:
            if (r2 == 0) goto L_0x00f0
            r2.close()     // Catch:{ Exception -> 0x00d4 }
            goto L_0x00f0
        L_0x00d4:
            r8 = move-exception
            goto L_0x00ae
        L_0x00d6:
            r8 = move-exception
        L_0x00d7:
            if (r1 == 0) goto L_0x00e1
            r1.close()     // Catch:{ Exception -> 0x00dd }
            goto L_0x00e1
        L_0x00dd:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)     // Catch:{ Throwable -> 0x00ec }
        L_0x00e1:
            if (r2 == 0) goto L_0x00eb
            r2.close()     // Catch:{ Exception -> 0x00e7 }
            goto L_0x00eb
        L_0x00e7:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)     // Catch:{ Throwable -> 0x00ec }
        L_0x00eb:
            throw r8     // Catch:{ Throwable -> 0x00ec }
        L_0x00ec:
            r8 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r8)
        L_0x00f0:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3016c(java.lang.String):java.lang.String");
    }

    /* renamed from: c */
    public static boolean m3017c(Context context) {
        try {
            return ((PowerManager) context.getSystemService("power")).isScreenOn();
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: c */
    public static boolean m3018c(Intent intent, Context context) {
        if (!(intent == null || context == null)) {
            try {
                List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
                return queryIntentActivities != null && queryIntentActivities.size() > 0;
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
        return false;
    }

    /* renamed from: c */
    public static boolean m3019c(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: d */
    public static String m3020d() {
        try {
            String b = m3010b();
            if (TextUtils.isEmpty(b)) {
                return "";
            }
            String lowerCase = b.toLowerCase();
            HashMap hashMap = new HashMap();
            hashMap.put(AgooConstants.MESSAGE_SYSTEM_SOURCE_HUAWEI, "ro.build.version.emui");
            hashMap.put("honor", "ro.build.version.emui");
            hashMap.put(AgooConstants.MESSAGE_SYSTEM_SOURCE_XIAOMI, "ro.build.version.incremental");
            hashMap.put("samsang", "ro.build.version.incremental");
            hashMap.put(AgooConstants.MESSAGE_SYSTEM_SOURCE_VIVO, "ro.vivo.os.version");
            hashMap.put(AgooConstants.MESSAGE_SYSTEM_SOURCE_OPPO, "ro.build.version.opporom");
            hashMap.put(AgooConstants.MESSAGE_SYSTEM_SOURCE_MEIZU, "ro.build.display.id");
            hashMap.put("lenovo", "ro.build.version.incremental");
            hashMap.put("smartisan", "ro.modversion");
            hashMap.put("htc", "ro.build.sense.version");
            hashMap.put("oneplus", "ro.rom.version");
            hashMap.put("yunos", "ro.cta.yunos.version");
            hashMap.put("360", "ro.build.uiversion");
            hashMap.put("nubia", "ro.build.rom.internal.id");
            if (hashMap.containsKey(lowerCase)) {
                return m3004a((String) hashMap.get(lowerCase), "");
            }
            return "";
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: d */
    public static String m3021d(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(C1488a.f2671ab)) {
            for (String str2 : C1488a.f2671ab.split(",")) {
                try {
                    ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
                    if (applicationInfo != null) {
                        if (applicationInfo.metaData != null) {
                            String str3 = (String) applicationInfo.metaData.get(str2);
                            if (!TextUtils.isEmpty(str3)) {
                                sb.append(str2);
                                sb.append("=");
                                sb.append(str3);
                                sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
                            }
                        }
                    }
                } catch (Throwable th) {
                    C1540h.m2996a(th);
                }
            }
            if (sb.length() > 0 && sb.toString().endsWith(DispatchConstants.SIGN_SPLIT_SYMBOL)) {
                sb = sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    /* renamed from: d */
    public static boolean m3022d(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: d */
    public static boolean m3023d(String str) {
        boolean z = false;
        try {
            PackageInfo packageInfo = C1490c.f2747a.getPackageManager().getPackageInfo(str, 0);
            if ((packageInfo.applicationInfo.flags & 1) == 0 || (packageInfo.applicationInfo.flags & 128) != 0) {
                z = true;
            }
            return !z;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:107:0x0182 A[SYNTHETIC, Splitter:B:107:0x0182] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0189 A[SYNTHETIC, Splitter:B:112:0x0189] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0167  */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3024e() {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0179, all -> 0x0176 }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0179, all -> 0x0176 }
            java.io.File r4 = android.os.Environment.getRootDirectory()     // Catch:{ Throwable -> 0x0179, all -> 0x0176 }
            java.lang.String r5 = "build.prop"
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x0179, all -> 0x0176 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0179, all -> 0x0176 }
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Throwable -> 0x0174 }
            r1.<init>()     // Catch:{ Throwable -> 0x0174 }
            r1.load(r2)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r3 = "ro.product.brand"
            java.lang.String r3 = r1.getProperty(r3, r0)     // Catch:{ Throwable -> 0x0174 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 != 0) goto L_0x002f
            java.lang.String r4 = "unknown"
            boolean r4 = r3.equals(r4)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x0035
        L_0x002f:
            java.lang.String r3 = "ro.product.manufacturer"
            java.lang.String r3 = r1.getProperty(r3, r0)     // Catch:{ Throwable -> 0x0174 }
        L_0x0035:
            java.lang.String r4 = "samsung"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            java.lang.String r5 = "Meizu"
            if (r4 != 0) goto L_0x013a
            boolean r4 = r5.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 != 0) goto L_0x013a
            java.lang.String r4 = "LENOVO"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 != 0) goto L_0x013a
            java.lang.String r4 = "YuLong"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x0057
            goto L_0x013a
        L_0x0057:
            java.lang.String r4 = "HUAWEI"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x00a7
            java.lang.String r4 = "ro.build.version.emui"
            java.lang.String r0 = r1.getProperty(r4, r0)     // Catch:{ Throwable -> 0x0174 }
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x0161
            java.lang.String r4 = "ro.build.hw_emui_api_level"
            java.lang.String r0 = r1.getProperty(r4)     // Catch:{ Throwable -> 0x0174 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0174 }
            if (r1 != 0) goto L_0x0161
            java.lang.String r1 = "9"
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0174 }
            if (r1 == 0) goto L_0x0083
            java.lang.String r0 = "EmotionUI_3.0"
            goto L_0x0161
        L_0x0083:
            java.lang.String r1 = "10"
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0174 }
            if (r1 == 0) goto L_0x008f
            java.lang.String r0 = "EmotionUI_4.1"
            goto L_0x0161
        L_0x008f:
            java.lang.String r1 = "11"
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0174 }
            if (r1 == 0) goto L_0x009b
            java.lang.String r0 = "EmotionUI_5.0"
            goto L_0x0161
        L_0x009b:
            java.lang.String r1 = "12"
            boolean r1 = r0.equals(r1)     // Catch:{ Throwable -> 0x0174 }
            if (r1 == 0) goto L_0x0161
            java.lang.String r0 = "EmotionUI_5.1"
            goto L_0x0161
        L_0x00a7:
            java.lang.String r4 = "OPPO"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x00b7
            java.lang.String r4 = "ro.build.version.opporom"
        L_0x00b1:
            java.lang.String r0 = r1.getProperty(r4, r0)     // Catch:{ Throwable -> 0x0174 }
            goto L_0x0161
        L_0x00b7:
            java.lang.String r4 = "vivo"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x00df
            java.lang.String r4 = "ro.vivo.os.build.display.id"
            java.lang.String r4 = r1.getProperty(r4, r0)     // Catch:{ Throwable -> 0x0174 }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0171 }
            if (r5 == 0) goto L_0x00d1
            java.lang.String r5 = "ro.vivo.rom.version"
            java.lang.String r4 = r1.getProperty(r5, r0)     // Catch:{ Throwable -> 0x0171 }
        L_0x00d1:
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0171 }
            if (r5 == 0) goto L_0x0160
            java.lang.String r5 = "ro.vivo.os.version"
        L_0x00d9:
            java.lang.String r0 = r1.getProperty(r5, r0)     // Catch:{ Throwable -> 0x0171 }
            goto L_0x0161
        L_0x00df:
            java.lang.String r4 = "Xiaomi"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x00ea
            java.lang.String r4 = "ro.miui.ui.version.name"
            goto L_0x00b1
        L_0x00ea:
            java.lang.String r4 = "smartisan"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x00f5
            java.lang.String r4 = "ro.smartisan.version"
            goto L_0x00b1
        L_0x00f5:
            java.lang.String r4 = "LeMobile"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 != 0) goto L_0x0137
            java.lang.String r4 = "Letv"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x0106
            goto L_0x0137
        L_0x0106:
            java.lang.String r4 = "nubia"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x0121
            java.lang.String r4 = "ro.build.rom.internal.id"
            java.lang.String r0 = r1.getProperty(r4)     // Catch:{ Throwable -> 0x0174 }
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x0161
            java.lang.String r4 = "ro.build.rom.id"
        L_0x011c:
            java.lang.String r0 = r1.getProperty(r4)     // Catch:{ Throwable -> 0x0174 }
            goto L_0x0161
        L_0x0121:
            java.lang.String r4 = "ZTE"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x012c
            java.lang.String r4 = "ro.build.sw_internal_version"
            goto L_0x011c
        L_0x012c:
            java.lang.String r4 = "QiKU"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0174 }
            if (r4 == 0) goto L_0x0161
            java.lang.String r4 = "ro.build.uiversion"
            goto L_0x011c
        L_0x0137:
            java.lang.String r4 = "ro.letv.release.version"
            goto L_0x011c
        L_0x013a:
            java.lang.String r4 = "ro.build.version.incremental"
            java.lang.String r4 = r1.getProperty(r4, r0)     // Catch:{ Throwable -> 0x0174 }
            boolean r5 = r5.equalsIgnoreCase(r3)     // Catch:{ Throwable -> 0x0171 }
            if (r5 == 0) goto L_0x0160
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0171 }
            if (r5 != 0) goto L_0x015c
            java.lang.String r5 = "[0-9]*"
            java.util.regex.Pattern r5 = java.util.regex.Pattern.compile(r5)     // Catch:{ Throwable -> 0x0171 }
            java.util.regex.Matcher r5 = r5.matcher(r4)     // Catch:{ Throwable -> 0x0171 }
            boolean r5 = r5.matches()     // Catch:{ Throwable -> 0x0171 }
            if (r5 == 0) goto L_0x0160
        L_0x015c:
            java.lang.String r5 = "ro.build.display.id"
            goto L_0x00d9
        L_0x0160:
            r0 = r4
        L_0x0161:
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0174 }
            if (r1 == 0) goto L_0x0168
            r0 = r3
        L_0x0168:
            r2.close()     // Catch:{ Exception -> 0x016c }
            goto L_0x0185
        L_0x016c:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
            goto L_0x0185
        L_0x0171:
            r1 = move-exception
            r0 = r4
            goto L_0x017d
        L_0x0174:
            r1 = move-exception
            goto L_0x017d
        L_0x0176:
            r0 = move-exception
            r2 = r1
            goto L_0x0187
        L_0x0179:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
        L_0x017d:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)     // Catch:{ all -> 0x0186 }
            if (r2 == 0) goto L_0x0185
            r2.close()     // Catch:{ Exception -> 0x016c }
        L_0x0185:
            return r0
        L_0x0186:
            r0 = move-exception
        L_0x0187:
            if (r2 == 0) goto L_0x0191
            r2.close()     // Catch:{ Exception -> 0x018d }
            goto L_0x0191
        L_0x018d:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)
        L_0x0191:
            goto L_0x0193
        L_0x0192:
            throw r0
        L_0x0193:
            goto L_0x0192
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p086i.C1541i.m3024e():java.lang.String");
    }

    /* renamed from: e */
    public static String m3025e(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            C1540h.m2996a(e);
            return null;
        }
    }

    /* renamed from: e */
    public static String m3026e(String str) {
        String[] strArr;
        try {
            byte[] b = m3014b("/sdcard/libs/" + str + ".db");
            if (b == null) {
                C1540h.m2997b("GBDUtils", str + ".db not exist");
                return null;
            }
            ArrayList arrayList = new ArrayList();
            String deviceId = ((TelephonyManager) C1490c.f2747a.getSystemService(Constant.PHONE)).getDeviceId();
            if (!TextUtils.isEmpty(deviceId)) {
                String a = C1558z.m3084a(deviceId);
                if (!TextUtils.isEmpty(a) && !a.equals(C1343f.f2110B)) {
                    arrayList.add(a);
                }
            }
            arrayList.add(C1343f.f2110B);
            arrayList.add(C1558z.m3084a(""));
            arrayList.add(C1558z.m3084a("000000000000000"));
            arrayList.add(C1558z.m3084a("cantgetimei"));
            String a2 = C1557y.m3077a(1, C1490c.f2747a);
            if (!TextUtils.isEmpty(a2)) {
                arrayList.add(C1558z.m3084a(a2));
            }
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    strArr = null;
                    break;
                }
                String str2 = new String(C1551s.m3048a(b, (String) it.next()));
                if (Pattern.matches("[\\.:0-9a-zA-Z\\|]+", str2)) {
                    strArr = str2.split("\\|");
                    break;
                }
            }
            if (strArr == null || strArr.length <= 3) {
                return null;
            }
            String str3 = strArr[3];
            if (str3 != null) {
                try {
                    if (str3.equals("null")) {
                        return null;
                    }
                } catch (Throwable unused) {
                }
            }
            return str3;
        } catch (Throwable unused2) {
            return null;
        }
    }

    /* renamed from: f */
    public static String m3027f(Context context) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            try {
                context.getPackageManager().getPackageInfo("com.android.vending", 0);
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                C1543k kVar = new C1543k();
                if (context.bindService(intent, kVar, 1)) {
                    try {
                        String a = new C1544l(kVar.mo15173a()).mo15176a();
                        context.unbindService(kVar);
                        return a;
                    } catch (Exception e) {
                        throw e;
                    } catch (Throwable th) {
                        context.unbindService(kVar);
                        throw th;
                    }
                } else {
                    throw new IOException("Google Play connection failed");
                }
            } catch (Exception e2) {
                throw e2;
            }
        } else {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
    }

    /* renamed from: f */
    public static boolean m3028f() {
        int intValue = Integer.valueOf(C1343f.f2182s.substring(28), 16).intValue();
        int intValue2 = Integer.valueOf(C1488a.f2652aI, 16).intValue();
        for (int i = 0; i < 16; i++) {
            int i2 = 1 << i;
            if ((intValue2 & i2) != 0 && (i2 & intValue) == 0) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: g */
    public static boolean m3029g() {
        try {
            String a = C1557y.m3078a(C1490c.f2747a);
            if (TextUtils.isEmpty(a)) {
                C1540h.m2997b("GBD_Utils", "mei empty.");
                return false;
            }
            int intValue = Integer.valueOf(a.substring(a.length() - 4), 16).intValue();
            int intValue2 = Integer.valueOf(C1488a.f2651aH, 16).intValue();
            C1540h.m2997b("checkMaskForType43", " 43 dynamic= " + C1488a.f2651aH);
            C1540h.m2997b("checkMaskForType43", "imei= " + intValue + "  43mask= " + intValue2);
            int i = 0;
            while (i < 16) {
                int i2 = 1 << i;
                if ((intValue2 & i2) == 0 || (i2 & intValue) != 0) {
                    i++;
                } else {
                    C1540h.m2997b("GBD_Utils", "mei not match.");
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: g */
    public static boolean m3030g(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            return wifiManager != null && wifiManager.isWifiEnabled();
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: h */
    public static boolean m3031h() {
        if (!C1490c.f2741R) {
            C1490c.f2742S = m3029g();
            C1490c.f2741R = true;
        }
        return C1488a.f2679aj && C1490c.f2742S;
    }

    /* renamed from: h */
    public static boolean m3032h(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "auto_time", 1) == 1;
    }

    /* renamed from: i */
    public static int m3033i(Context context) {
        if (Build.VERSION.SDK_INT < 19) {
            return -1;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        String packageName = context.getApplicationContext().getPackageName();
        int i = applicationInfo.uid;
        try {
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            return ((Integer) cls.getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(i), packageName})).intValue() == 0 ? 1 : 0;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return 0;
        }
    }

    /* renamed from: i */
    public static long m3034i() {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            return !m3032h(C1490c.f2747a) ? currentTimeMillis + C1490c.f2732I : currentTimeMillis;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return currentTimeMillis;
        }
    }

    /* renamed from: j */
    public static String m3035j() {
        Object invoke;
        try {
            if (Build.VERSION.SDK_INT < 26 || !m3006a(C1490c.f2747a, "android.permission.READ_PHONE_STATE")) {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                invoke = cls.getMethod("get", new Class[]{String.class}).invoke(cls, new Object[]{"ro.serialno"});
            } else {
                Class<?> cls2 = Class.forName("android.os.Build");
                invoke = cls2.getMethod("getSerial", new Class[0]).invoke(cls2, new Object[0]);
            }
            return (String) invoke;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* renamed from: j */
    public static boolean m3036j(Context context) {
        try {
            boolean z = Settings.Secure.getInt(context.getContentResolver(), "adb_enabled", 0) > 0;
            Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (registerReceiver != null) {
                if (registerReceiver.getExtras() != null) {
                    return z && registerReceiver.getExtras().getInt("plugged") == 2;
                }
            }
            return true;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return true;
        }
    }

    /* renamed from: k */
    public static boolean m3037k() {
        try {
            C1540h.m2997b("GBD_Utils", "SLMA watchoutForMac " + C1488a.f2705k);
            if (TextUtils.isEmpty(C1488a.f2705k)) {
                return true;
            }
            if (SchedulerSupport.NONE.equals(C1488a.f2705k)) {
                return false;
            }
            for (String str : C1488a.f2705k.split(",")) {
                if (m3019c(str, C1490c.f2747a)) {
                    C1540h.m2995a("27mac-", "check = " + str + " exist");
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: k */
    public static boolean m3038k(Context context) {
        try {
            if (!C1488a.f2644aA) {
                return true;
            }
            if (m3039l()) {
                return false;
            }
            String displayName = TimeZone.getDefault().getDisplayName(false, 0);
            if (!TextUtils.isEmpty(displayName)) {
                if (displayName.equals("GMT+08:00") || displayName.contains("+08")) {
                    String language = Locale.getDefault().getLanguage();
                    if (!TextUtils.isEmpty(language)) {
                        if (language.equals(LanguageUtil.LOGOGRAM_CHINESE)) {
                            String country = Locale.getDefault().getCountry();
                            if (!TextUtils.isEmpty(country)) {
                                if (country.equals("CN")) {
                                    if (!m3036j(context) && m3005a(context) && !SchedulerSupport.NONE.equals(C1488a.f2697c)) {
                                        for (String c : C1488a.f2697c.split(",")) {
                                            if (m3019c(c, context)) {
                                                return false;
                                            }
                                        }
                                        if (!SchedulerSupport.NONE.equals(C1488a.f2698d)) {
                                            String[] split = C1488a.f2698d.split(",");
                                            for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(4)) {
                                                ServiceInfo[] serviceInfoArr = packageInfo.services;
                                                if (serviceInfoArr != null && serviceInfoArr.length > 0) {
                                                    for (ServiceInfo serviceInfo : serviceInfoArr) {
                                                        for (String equals : split) {
                                                            if (equals.equals(serviceInfo.name)) {
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                    continue;
                                                }
                                            }
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    /* renamed from: l */
    public static boolean m3039l() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return false;
            }
            Iterator<T> it = Collections.list(networkInterfaces).iterator();
            while (it.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface) it.next();
                if (networkInterface.isUp()) {
                    if (networkInterface.getInterfaceAddresses().size() != 0) {
                        if ("tun0".equals(networkInterface.getName()) || "ppp0".equals(networkInterface.getName())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }
}
