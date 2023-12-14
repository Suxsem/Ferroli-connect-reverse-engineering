package com.igexin.push.p046c;

import org.json.JSONObject;

/* renamed from: com.igexin.push.c.e */
public final class C1210e {

    /* renamed from: a */
    public String f1744a;

    /* renamed from: b */
    public long f1745b;

    /* renamed from: a */
    public C1210e mo14379a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return this;
        }
        try {
            this.f1744a = jSONObject.getString("address");
            this.f1745b = jSONObject.getLong("outdateTime");
        } catch (Exception unused) {
        }
        return this;
    }

    /* renamed from: a */
    public JSONObject mo14380a() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("address", this.f1744a);
            jSONObject.put("outdateTime", this.f1745b);
            return jSONObject;
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString() {
        return "ServerAddress{address='" + this.f1744a + '\'' + ", outdateTime=" + this.f1745b + '}';
    }
}
