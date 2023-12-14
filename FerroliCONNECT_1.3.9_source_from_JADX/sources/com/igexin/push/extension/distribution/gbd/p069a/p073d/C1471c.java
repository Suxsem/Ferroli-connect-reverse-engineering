package com.igexin.push.extension.distribution.gbd.p069a.p073d;

import android.text.TextUtils;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.d.c */
class C1471c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C1470b f2570a;

    C1471c(C1470b bVar) {
        this.f2570a = bVar;
    }

    public void run() {
        try {
            if (this.f2570a.f2567a.get("pkgName") != null && this.f2570a.f2567a.get("srvName") != null && this.f2570a.f2567a.get("datetime") != null && !TextUtils.isEmpty(this.f2570a.f2567a.get("pkgName").toString()) && !TextUtils.isEmpty(this.f2570a.f2567a.get("srvName").toString()) && !TextUtils.isEmpty(this.f2570a.f2567a.get("datetime").toString())) {
                String str = this.f2570a.f2567a.get("pkgName").toString() + "," + this.f2570a.f2567a.get("srvName").toString();
                C1469a.m2663a(str);
                C1469a.m2664a(str, this.f2570a.f2568b, this.f2570a.f2567a.get("datetime").toString(), 1);
                C1540h.m2997b("GBD_GTT", "guard success type = " + this.f2570a.f2568b + " pkg = " + this.f2570a.f2567a.get("pkgName").toString());
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }
}
