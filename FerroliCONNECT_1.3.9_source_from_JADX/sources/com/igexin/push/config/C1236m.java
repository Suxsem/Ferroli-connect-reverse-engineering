package com.igexin.push.config;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.p046c.C1214i;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.config.m */
public class C1236m {
    /* renamed from: a */
    public static void m1628a(String str, boolean z) {
        JSONObject jSONObject;
        String[] a;
        String[] a2;
        String[] a3;
        String[] a4;
        C1179b.m1354a("IDCConfigParse parse idc config data : " + str);
        try {
            jSONObject = new JSONObject(str);
        } catch (Exception unused) {
            jSONObject = null;
        }
        if (jSONObject != null) {
            if (jSONObject.has("N")) {
                try {
                    SDKUrlConfig.setLocation(jSONObject.getString("N"));
                } catch (JSONException unused2) {
                }
            }
            if (jSONObject.has("X1") && (a4 = m1629a(jSONObject, "X1")) != null && a4.length > 0) {
                SDKUrlConfig.setXfrAddressIps(a4);
                if (z) {
                    C1179b.m1354a("Detect_IDCConfigParse parse idc success, , reset and redetect");
                    C1214i.m1500a().mo14387g();
                }
            }
            if (jSONObject.has("X2") && (a3 = m1629a(jSONObject, "X2")) != null && a3.length > 0) {
                SDKUrlConfig.XFR_ADDRESS_BAK = a3;
            }
            if (jSONObject.has("B") && (a2 = m1629a(jSONObject, "B")) != null && a2.length > 0) {
                SDKUrlConfig.BI_ADDRESS_IPS = a2;
            }
            if (jSONObject.has("C") && (a = m1629a(jSONObject, "C")) != null && a.length > 0) {
                SDKUrlConfig.CONFIG_ADDRESS_IPS = a;
            }
        }
    }

    /* renamed from: a */
    private static String[] m1629a(JSONObject jSONObject, String str) {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray(str);
            int length = jSONArray.length();
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                if (!str.equals("X1")) {
                    if (!str.equals("X2")) {
                        strArr[i] = "https://" + jSONArray.getString(i);
                    }
                }
                strArr[i] = "socket://" + jSONArray.getString(i);
            }
            return strArr;
        } catch (Exception unused) {
            return null;
        }
    }
}
