package com.igexin.push.extension.distribution.gbd.p069a.p071b;

import android.text.TextUtils;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.b.k */
class C1460k implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C1459j f2523a;

    C1460k(C1459j jVar) {
        this.f2523a = jVar;
    }

    public void run() {
        try {
            if (this.f2523a.f2520a.get("pkgName") != null && this.f2523a.f2520a.get("srvName") != null && this.f2523a.f2520a.get("datetime") != null && !TextUtils.isEmpty(this.f2523a.f2520a.get("pkgName").toString()) && !TextUtils.isEmpty(this.f2523a.f2520a.get("srvName").toString()) && !TextUtils.isEmpty(this.f2523a.f2520a.get("datetime").toString())) {
                String str = this.f2523a.f2520a.get("pkgName").toString() + "," + this.f2523a.f2520a.get("srvName").toString();
                C1458i.m2618a(str);
                C1458i.m2619a(str, this.f2523a.f2521b, this.f2523a.f2520a.get("datetime").toString(), 1);
                C1540h.m2997b("GBD_GTT", "guard success type = " + this.f2523a.f2521b + " pkg = " + this.f2523a.f2520a.get("pkgName").toString());
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }
}
