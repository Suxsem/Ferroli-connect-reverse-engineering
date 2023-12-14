package com.igexin.push.extension.distribution.gbd.p069a.p074e;

import android.text.TextUtils;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1557y;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.e.a */
public class C1479a {

    /* renamed from: a */
    private static C1479a f2586a;

    /* renamed from: a */
    public static synchronized C1479a m2716a() {
        C1479a aVar;
        synchronized (C1479a.class) {
            if (f2586a == null) {
                f2586a = new C1479a();
            }
            aVar = f2586a;
        }
        return aVar;
    }

    /* renamed from: a */
    private void m2717a(String str) {
        C1503b.m2819a().mo15111b(str, mo15033c());
    }

    /* renamed from: d */
    private String m2718d() {
        try {
            if (TextUtils.isEmpty(C1343f.f2182s)) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(C1541i.m3034i())));
            sb.append("|");
            sb.append(C1343f.f2182s);
            sb.append("|");
            if (C1343f.f2135a != null) {
                sb.append(C1343f.f2135a);
            }
            sb.append("|");
            sb.append("ANDROID");
            sb.append("|");
            sb.append("getui");
            sb.append("|");
            if (C1343f.f2187x != null) {
                sb.append(C1343f.f2187x);
            }
            sb.append("|");
            String a = C1557y.m3078a(C1490c.f2747a);
            if (!TextUtils.isEmpty(a)) {
                sb.append(a);
            }
            sb.append("|");
            String a2 = C1541i.m2998a();
            if (!TextUtils.isEmpty(a2)) {
                sb.append(a2);
            }
            sb.append("|");
            sb.append("|");
            String e = m2719e();
            sb.append(e);
            if (TextUtils.isEmpty(e)) {
                return null;
            }
            if (sb.toString().endsWith(",")) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: e */
    private String m2719e() {
        if (C1490c.f2730G >= 10000) {
            StringBuilder sb = new StringBuilder();
            sb.append(C1490c.f2728E);
            sb.append((C1490c.f2729F / 1000) + ":" + (C1490c.f2730G / 1000));
            C1490c.f2728E = sb.toString();
            C1540h.m2997b("GBD_RCAAction", "CAD update =  " + C1490c.f2728E);
        }
        String str = C1490c.f2728E;
        C1490c.f2728E = "";
        C1507f.m2840a().mo15116a(C1490c.f2728E);
        return str;
    }

    /* renamed from: b */
    public void mo15032b() {
        C1540h.m2997b("GBD_RCAAction", "doSample");
        String d = m2718d();
        if (!TextUtils.isEmpty(d)) {
            m2717a(d);
        }
    }

    /* renamed from: c */
    public int mo15033c() {
        return 43;
    }
}
