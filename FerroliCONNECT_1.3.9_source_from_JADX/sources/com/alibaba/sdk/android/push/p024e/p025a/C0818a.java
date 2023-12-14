package com.alibaba.sdk.android.push.p024e.p025a;

import android.content.Context;
import android.content.SharedPreferences;
import com.alibaba.sdk.android.ams.common.p009a.C0669a;
import com.alibaba.sdk.android.ams.common.p010b.C0672b;
import com.alibaba.sdk.android.ams.common.util.C0682b;
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.alibaba.sdk.android.push.common.util.AppInfoUtil;
import com.alibaba.sdk.android.push.common.util.p021a.C0812d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.push.e.a.a */
public class C0818a implements C0672b {

    /* renamed from: a */
    private SharedPreferences f1167a = C0669a.m471f();

    /* renamed from: b */
    private String f1168b = null;

    /* renamed from: c */
    private String f1169c = null;

    /* renamed from: d */
    private String f1170d = null;

    /* renamed from: a */
    private static int m799a(Context context, String str) {
        return context.getResources().getIdentifier(str, "string", context.getPackageName());
    }

    /* renamed from: b */
    private static String m800b(Context context, String str) {
        try {
            return context.getResources().getString(m799a(context, str));
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: f */
    private String m801f() {
        return "mps_deviceId_" + mo9524a();
    }

    /* renamed from: a */
    public String mo9524a() {
        if (!StringUtil.isEmpty(this.f1168b)) {
            return this.f1168b;
        }
        this.f1168b = C0669a.m466a("com.alibaba.app.appkey");
        if (!StringUtil.isEmpty(this.f1168b)) {
            return this.f1168b;
        }
        this.f1168b = m800b(C0669a.m467b(), "ams_appKey");
        return this.f1168b;
    }

    /* renamed from: a */
    public String mo9525a(Map<String, String> map, String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<>(map.keySet());
        arrayList.add("appSecret");
        Collections.sort(arrayList);
        for (String str3 : arrayList) {
            if (!str3.equals(C0812d.f1148u)) {
                if ("appSecret".equals(str3)) {
                    sb.append(str3);
                    str2 = mo9532d();
                } else {
                    sb.append(str3);
                    str2 = map.get(str3);
                }
                sb.append(str2);
            }
        }
        if (map.containsKey(C0812d.f1148u)) {
            int parseInt = Integer.parseInt(map.get(C0812d.f1148u));
            map.remove(C0812d.f1148u);
            if (parseInt > C0812d.m786b()) {
                return C0682b.m518a().mo9556a(sb.toString());
            }
        }
        return C0682b.m518a().mo9557b(sb.toString());
    }

    /* renamed from: a */
    public void mo9526a(String str) {
        this.f1170d = str;
        this.f1167a.edit().putString(m801f(), str).putLong("mps_device_store_time", System.currentTimeMillis()).commit();
    }

    /* renamed from: a */
    public void mo9527a(String str, String str2) {
        this.f1167a.edit().putString(str, str2).commit();
    }

    /* renamed from: b */
    public String mo9528b() {
        String str = this.f1170d;
        if (str != null) {
            return str;
        }
        String string = this.f1167a.getString(m801f(), "");
        if (System.currentTimeMillis() - this.f1167a.getLong("mps_device_store_time", 0) > 604800000) {
            return "";
        }
        this.f1170d = string;
        return string;
    }

    /* renamed from: b */
    public void mo9529b(String str) {
        this.f1167a.edit().putString("mps_utdid", str).commit();
    }

    /* renamed from: c */
    public String mo9530c() {
        return this.f1167a.getString("mps_utdid", "");
    }

    /* renamed from: c */
    public String mo9531c(String str) {
        return this.f1167a.getString(str, "");
    }

    /* renamed from: d */
    public String mo9532d() {
        if (!StringUtil.isEmpty(this.f1169c)) {
            return this.f1169c;
        }
        this.f1169c = C0669a.m466a("com.alibaba.app.appsecret");
        if (!StringUtil.isEmpty(this.f1169c)) {
            return this.f1169c;
        }
        this.f1169c = m800b(C0669a.m467b(), "ams_appSecret");
        return this.f1169c;
    }

    /* renamed from: d */
    public void mo9533d(String str) {
        this.f1168b = str;
    }

    /* renamed from: e */
    public String mo9534e() {
        return AppInfoUtil.getAppVersionName(C0669a.m467b());
    }

    /* renamed from: e */
    public void mo9535e(String str) {
        this.f1169c = str;
    }
}
