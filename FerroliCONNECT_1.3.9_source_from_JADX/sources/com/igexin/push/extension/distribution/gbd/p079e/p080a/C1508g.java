package com.igexin.push.extension.distribution.gbd.p079e.p080a;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.igexin.push.extension.distribution.gbd.p077c.C1489b;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p086i.C1535c;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1551s;
import org.json.JSONObject;

/* renamed from: com.igexin.push.extension.distribution.gbd.e.a.g */
public class C1508g {

    /* renamed from: a */
    public static C1508g f2900a;

    /* renamed from: b */
    private SharedPreferences f2901b = C1490c.f2747a.getSharedPreferences("gx_sp", 0);

    private C1508g() {
    }

    /* renamed from: a */
    public static C1508g m2864a() {
        if (f2900a == null) {
            f2900a = new C1508g();
        }
        return f2900a;
    }

    /* renamed from: a */
    public void mo15135a(byte[] bArr) {
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr));
            C1540h.m2997b("GBD_SPM", "parseServiceConfig : " + jSONObject);
            if (jSONObject.has("result") && jSONObject.has("services")) {
                String string = jSONObject.getString("services");
                if (!TextUtils.isEmpty(string)) {
                    SharedPreferences.Editor edit = this.f2901b.edit();
                    edit.putString("sp_guard_services", C1535c.m2985a(C1551s.m3050b(string.getBytes("UTF-8"), C1489b.f2721a), 0, 102400));
                    edit.apply();
                }
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: b */
    public String mo15136b() {
        try {
            String string = this.f2901b.getString("sp_guard_services", "");
            if (TextUtils.isEmpty(string)) {
                return string;
            }
            String str = new String(C1551s.m3048a(C1535c.m2988a(string.toCharArray(), 0), C1489b.f2721a), "utf-8");
            C1540h.m2997b("GBD_SPM", "readServiceConfig : " + str);
            return str;
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return "";
        }
    }
}
