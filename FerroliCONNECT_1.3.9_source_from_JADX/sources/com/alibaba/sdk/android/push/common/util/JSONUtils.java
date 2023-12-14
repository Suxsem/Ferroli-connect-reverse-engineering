package com.alibaba.sdk.android.push.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class JSONUtils {
    public static boolean getBoolean(JSONObject jSONObject, String str, boolean z) {
        try {
            return jSONObject.getBoolean(str);
        } catch (Throwable unused) {
            return z;
        }
    }

    public static int getInt(JSONObject jSONObject, String str, int i) {
        try {
            return jSONObject.getInt(str);
        } catch (Throwable unused) {
            return i;
        }
    }

    public static JSONObject getJSONObject(JSONObject jSONObject, String str, JSONObject jSONObject2) {
        try {
            return jSONObject.getJSONObject(str);
        } catch (Throwable unused) {
            return jSONObject2;
        }
    }

    public static String getString(JSONObject jSONObject, String str, String str2) {
        try {
            return jSONObject.getString(str);
        } catch (Throwable unused) {
            return str2;
        }
    }

    public static Map<String, String> toMap(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            hashMap.put(next, jSONObject.get(next) == null ? null : jSONObject.get(next).toString());
        }
        return hashMap;
    }
}
