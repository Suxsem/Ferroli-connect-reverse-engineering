package com.igexin.push.extension.distribution.basic.p062d;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import com.igexin.push.extension.distribution.basic.p068j.C1434b;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.basic.d.a */
public class C1417a {

    /* renamed from: a */
    public static C1417a f2442a;

    /* renamed from: b */
    private SharedPreferences f2443b = C1416f.f2423a.getSharedPreferences("gx_sp", 0);

    private C1417a() {
    }

    /* renamed from: a */
    public static C1417a m2423a() {
        if (f2442a == null) {
            f2442a = new C1417a();
        }
        return f2442a;
    }

    /* renamed from: c */
    public static String m2424c() {
        return C1416f.f2423a.getSharedPreferences("getui_sp", 0).getString("us", "");
    }

    /* renamed from: d */
    public static String m2425d() {
        return C1416f.f2423a.getSharedPreferences("getui_sp", 0).getString("uis", "");
    }

    /* renamed from: a */
    public String mo14949a(String str) {
        try {
            String b = mo14950b();
            if (TextUtils.isEmpty(b)) {
                return null;
            }
            String string = new JSONObject(b).getString(str);
            if (TextUtils.isEmpty(string)) {
                return null;
            }
            String[] split = string.split("\\|");
            if (split.length > 0) {
                return split[0];
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: b */
    public String mo14950b() {
        try {
            return new String(C1150a.m1231a(C1434b.m2501a(this.f2443b.getString("sp_guard_services", "").toCharArray(), 0), "dj1om0z0za9kwzxrphkqxsu9oc21tez1"), "utf-8");
        } catch (Throwable unused) {
            return "";
        }
    }
}
