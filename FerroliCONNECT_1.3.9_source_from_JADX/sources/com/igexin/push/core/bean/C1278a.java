package com.igexin.push.core.bean;

import android.os.Build;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.assist.sdk.C1146a;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1356s;
import com.igexin.push.util.C1589n;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.PushBuildConfig;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.common.Constants;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.bean.a */
public class C1278a {

    /* renamed from: a */
    public String f1940a;

    /* renamed from: b */
    public String f1941b;

    /* renamed from: c */
    public String f1942c;

    /* renamed from: d */
    public String f1943d;

    /* renamed from: e */
    public String f1944e;

    /* renamed from: f */
    public String f1945f = "open";

    /* renamed from: g */
    public String f1946g;

    /* renamed from: h */
    public String f1947h;

    /* renamed from: i */
    public String f1948i;

    /* renamed from: j */
    public String f1949j;

    /* renamed from: k */
    public String f1950k;

    /* renamed from: l */
    public String f1951l;

    /* renamed from: m */
    public long f1952m;

    public C1278a() {
        if (C1343f.f2168e != null) {
            this.f1945f += ":" + C1343f.f2168e;
        }
        this.f1944e = PushBuildConfig.sdk_conf_version;
        this.f1941b = C1343f.f2185v;
        this.f1942c = C1343f.f2184u;
        this.f1943d = C1343f.f2186w;
        this.f1947h = "ANDROID";
        this.f1949j = "MDP";
        this.f1946g = C1343f.f2187x;
        this.f1952m = System.currentTimeMillis();
        this.f1950k = C1343f.f2188y;
        if (C1593r.m3267a(C1343f.f2169f)) {
            this.f1940a = Build.MODEL;
            this.f1951l = Build.BRAND;
            this.f1948i = DispatchConstants.ANDROID + Build.VERSION.RELEASE;
        }
        if (C1146a.m1224b(C1343f.f2169f) && C1234k.f1837S) {
            StringBuilder sb = new StringBuilder();
            sb.append("FCM-");
            String str = this.f1951l;
            sb.append(str == null ? "" : str);
            this.f1951l = sb.toString();
        }
    }

    /* renamed from: a */
    public static String m1785a(C1278a aVar) {
        JSONObject jSONObject = new JSONObject();
        String str = aVar.f1940a;
        if (str == null) {
            str = "";
        }
        jSONObject.put(Constants.KEY_MODEL, str);
        String str2 = aVar.f1941b;
        if (str2 == null) {
            str2 = "";
        }
        jSONObject.put("sim", str2);
        String str3 = aVar.f1942c;
        if (str3 == null) {
            str3 = "";
        }
        jSONObject.put(Constants.KEY_IMEI, str3);
        String str4 = aVar.f1943d;
        if (str4 == null) {
            str4 = "";
        }
        jSONObject.put("mac", str4);
        String str5 = aVar.f1944e;
        if (str5 == null) {
            str5 = "";
        }
        jSONObject.put(Constants.SP_KEY_VERSION, str5);
        String str6 = aVar.f1945f;
        if (str6 == null) {
            str6 = "";
        }
        jSONObject.put("channelid", str6);
        jSONObject.put("type", "ANDROID");
        String str7 = aVar.f1949j;
        if (str7 == null) {
            str7 = "";
        }
        jSONObject.put("app", str7);
        StringBuilder sb = new StringBuilder();
        sb.append("ANDROID-");
        String str8 = aVar.f1946g;
        if (str8 == null) {
            str8 = "";
        }
        sb.append(str8);
        jSONObject.put("deviceid", sb.toString());
        String str9 = aVar.f1950k;
        if (str9 == null) {
            str9 = "";
        }
        jSONObject.put("device_token", str9);
        String str10 = aVar.f1951l;
        if (str10 == null) {
            str10 = "";
        }
        jSONObject.put(Constants.KEY_BRAND, str10);
        String str11 = aVar.f1948i;
        if (str11 == null) {
            str11 = "";
        }
        jSONObject.put("system_version", str11);
        jSONObject.put("cell", "");
        jSONObject.put("aid", C1589n.m3260b());
        jSONObject.put("adid", C1589n.m3261c());
        String name = C1356s.m2138a().mo14786c(C1343f.f2169f).getName();
        if (!C1275b.f1913q.equals(name)) {
            jSONObject.put("us", name);
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(PushConsts.CMD_ACTION, "addphoneinfo");
        jSONObject2.put(AgooConstants.MESSAGE_ID, String.valueOf(aVar.f1952m));
        jSONObject2.put("info", jSONObject);
        C1179b.m1354a("addphoneinfo|" + jSONObject2.toString());
        return jSONObject2.toString();
    }
}
