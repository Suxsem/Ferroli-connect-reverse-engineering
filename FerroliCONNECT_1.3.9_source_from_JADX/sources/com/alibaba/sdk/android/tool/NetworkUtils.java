package com.alibaba.sdk.android.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import com.taobao.accs.utl.UtilityImpl;

public class NetworkUtils {
    private static boolean checkHasPermission(Context context, String str) {
        Class<?> cls;
        try {
            cls = Class.forName("android.support.v4.content.ContextCompat");
        } catch (Exception unused) {
            cls = null;
        }
        if (cls == null) {
            try {
                cls = Class.forName("androidx.core.content.ContextCompat");
            } catch (Exception unused2) {
            }
        }
        if (cls == null) {
            return true;
        }
        try {
            return ((Integer) cls.getMethod("androidx.core.content.ContextCompat", new Class[]{Context.class, String.class}).invoke((Object) null, new Object[]{context, str})).intValue() == 0;
        } catch (Exception unused3) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0039 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0040 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0043 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getMobileNetworkType(android.content.Context r2, android.telephony.TelephonyManager r3, android.net.ConnectivityManager r4) {
        /*
            r0 = 30
            if (r3 == 0) goto L_0x0020
            int r1 = android.os.Build.VERSION.SDK_INT
            if (r1 < r0) goto L_0x001b
            java.lang.String r1 = "android.permission.READ_PHONE_STATE"
            boolean r2 = checkHasPermission(r2, r1)
            if (r2 != 0) goto L_0x0016
            boolean r2 = r3.hasCarrierPrivileges()
            if (r2 == 0) goto L_0x001b
        L_0x0016:
            int r2 = r3.getDataNetworkType()
            goto L_0x0021
        L_0x001b:
            int r2 = r3.getNetworkType()     // Catch:{ Exception -> 0x0020 }
            goto L_0x0021
        L_0x0020:
            r2 = 0
        L_0x0021:
            java.lang.String r3 = "NULL"
            if (r2 != 0) goto L_0x0036
            int r1 = android.os.Build.VERSION.SDK_INT
            if (r1 < r0) goto L_0x002a
            return r3
        L_0x002a:
            if (r4 == 0) goto L_0x0036
            android.net.NetworkInfo r4 = r4.getActiveNetworkInfo()
            if (r4 == 0) goto L_0x0036
            int r2 = r4.getSubtype()
        L_0x0036:
            switch(r2) {
                case 1: goto L_0x0043;
                case 2: goto L_0x0043;
                case 3: goto L_0x0040;
                case 4: goto L_0x0043;
                case 5: goto L_0x0040;
                case 6: goto L_0x0040;
                case 7: goto L_0x0043;
                case 8: goto L_0x0040;
                case 9: goto L_0x0040;
                case 10: goto L_0x0040;
                case 11: goto L_0x0043;
                case 12: goto L_0x0040;
                case 13: goto L_0x003d;
                case 14: goto L_0x0040;
                case 15: goto L_0x0040;
                case 16: goto L_0x0039;
                case 17: goto L_0x0039;
                case 18: goto L_0x003d;
                case 19: goto L_0x003d;
                case 20: goto L_0x003a;
                default: goto L_0x0039;
            }
        L_0x0039:
            return r3
        L_0x003a:
            java.lang.String r2 = "5G"
            return r2
        L_0x003d:
            java.lang.String r2 = "4G"
            return r2
        L_0x0040:
            java.lang.String r2 = "3G"
            return r2
        L_0x0043:
            java.lang.String r2 = "2G"
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tool.NetworkUtils.getMobileNetworkType(android.content.Context, android.telephony.TelephonyManager, android.net.ConnectivityManager):java.lang.String");
    }

    public static String getWifiAddress(Context context) {
        WifiInfo connectionInfo;
        if (context == null || (connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService(UtilityImpl.NET_TYPE_WIFI)).getConnectionInfo()) == null) {
            return "00:00:00:00:00:00";
        }
        String macAddress = connectionInfo.getMacAddress();
        return TextUtils.isEmpty(macAddress) ? "00:00:00:00:00:00" : macAddress;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context == null) {
            return false;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isWiFiNetwork(ConnectivityManager connectivityManager) {
        NetworkCapabilities networkCapabilities;
        if (Build.VERSION.SDK_INT >= 23) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
                return false;
            }
            return networkCapabilities.hasTransport(1);
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
