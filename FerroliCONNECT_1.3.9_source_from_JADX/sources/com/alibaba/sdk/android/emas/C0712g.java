package com.alibaba.sdk.android.emas;

import android.text.TextUtils;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.alibaba.sdk.android.emas.g */
/* compiled from: EmasSingleLog */
public class C0712g {

    /* renamed from: h */
    String f909h;

    /* renamed from: i */
    String f910i;
    long timestamp;

    public C0712g(String str, String str2, long j) {
        this.f910i = str;
        this.f909h = str2;
        this.timestamp = j;
    }

    public int length() {
        return this.f909h.getBytes(Charset.forName("UTF-8")).length;
    }

    /* renamed from: a */
    public JSONObject mo9645a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("eventId", this.f910i);
            jSONObject.put("rawLog", this.f909h);
            jSONObject.put("timestamp", this.timestamp);
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static C0712g m634a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        String optString = jSONObject.optString("eventId");
        String optString2 = jSONObject.optString("rawLog");
        long optLong = jSONObject.optLong("timestamp");
        if (TextUtils.isEmpty(optString) || TextUtils.isEmpty(optString2) || optLong == 0) {
            return null;
        }
        return new C0712g(optString, optString2, optLong);
    }
}
