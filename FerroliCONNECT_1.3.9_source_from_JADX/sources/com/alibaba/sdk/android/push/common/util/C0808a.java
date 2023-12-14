package com.alibaba.sdk.android.push.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.alibaba.sdk.android.push.common.util.p022b.C0814a;
import com.p107tb.appyunsdk.Constant;

/* renamed from: com.alibaba.sdk.android.push.common.util.a */
public class C0808a {
    /* renamed from: a */
    public static boolean m777a(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    /* renamed from: b */
    public static C0814a.C0815a m778b(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 1) {
                return C0814a.C0815a.WIFI;
            }
            switch (((TelephonyManager) context.getSystemService(Constant.PHONE)).getNetworkType()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return C0814a.C0815a.G2;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return C0814a.C0815a.G3;
                case 13:
                    return C0814a.C0815a.G4;
                default:
                    return C0814a.C0815a.UNKNOWN;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return C0814a.C0815a.UNKNOWN;
        }
    }
}
