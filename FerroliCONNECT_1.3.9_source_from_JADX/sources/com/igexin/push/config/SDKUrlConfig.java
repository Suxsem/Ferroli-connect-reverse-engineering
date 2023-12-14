package com.igexin.push.config;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import java.util.ArrayList;

public class SDKUrlConfig {
    public static String[] BI_ADDRESS_IPS = {"https://sdk-open-phone.getui.com/api.php"};
    public static String[] CONFIG_ADDRESS_IPS = {"https://c-hzgt2.getui.com/api.php"};
    public static String[] XFR_ADDRESS_BAK = {"socket://43.231.145.10:5224"};

    /* renamed from: a */
    private static final Object f1801a = new Object();

    /* renamed from: b */
    private static String[] f1802b;

    /* renamed from: c */
    private static String f1803c = "HZ";

    /* renamed from: d */
    private static String[] f1804d = {"socket://sdk.open.talk.igexin.com:5224", "socket://sdk.open.talk.getui.net:5224", "socket://sdk.open.talk.gepush.com:5224"};

    /* renamed from: e */
    private static volatile String f1805e;

    public static String getBiUploadServiceUrl() {
        return BI_ADDRESS_IPS[0] + "?format=json&t=1";
    }

    public static String getCmAddress() {
        return f1805e == null ? f1804d[0] : f1805e;
    }

    public static String getConfigServiceUrl() {
        return CONFIG_ADDRESS_IPS[0] + "?format=json&t=1";
    }

    public static String[] getIdcConfigUrl() {
        return f1802b;
    }

    public static String getLocation() {
        return f1803c;
    }

    public static String[] getXfrAddress() {
        String[] strArr;
        synchronized (f1801a) {
            strArr = f1804d;
        }
        return strArr;
    }

    public static boolean realXfrListIsOnly() {
        String[] xfrAddress = getXfrAddress();
        ArrayList arrayList = new ArrayList();
        for (String str : xfrAddress) {
            if (!arrayList.contains(str)) {
                arrayList.add(str);
            }
        }
        return arrayList.size() == 1;
    }

    public static void setCmAddress(String str) {
        C1179b.m1354a("set cm address : " + str);
        f1805e = str;
    }

    public static void setIdcConfigUrl(String[] strArr) {
        f1802b = strArr;
    }

    public static void setLocation(String str) {
        if (!TextUtils.isEmpty(str)) {
            C1343f.f2167d = str;
            f1803c = str;
        }
    }

    public static void setXfrAddressIps(String[] strArr) {
        synchronized (f1801a) {
            f1804d = strArr;
        }
    }
}
