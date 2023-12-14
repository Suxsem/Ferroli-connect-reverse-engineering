package com.igexin.push.extension.distribution.gbd.p084h.p085a;

import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p084h.C1532b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;

/* renamed from: com.igexin.push.extension.distribution.gbd.h.a.a */
public class C1520a extends C1532b {

    /* renamed from: c */
    private static C1520a f2921c;

    private C1520a() {
        this.f2935b = C1488a.f2672ac * 1000;
        this.f2934a = C1490c.f2769w;
    }

    /* renamed from: d */
    public static C1520a m2921d() {
        if (f2921c == null) {
            f2921c = new C1520a();
        }
        return f2921c;
    }

    /* renamed from: a */
    public void mo15163a() {
        try {
            C1540h.m2997b("GBD_ALT", "dotask ...");
            if (C1490c.f2749c != null) {
                Message message = new Message();
                message.what = 10;
                C1490c.f2749c.sendMessage(message);
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: a */
    public void mo15164a(long j) {
        this.f2934a = j;
        C1507f.m2840a().mo15119b(j);
    }

    /* renamed from: b */
    public boolean mo15165b() {
        return super.mo15165b() && C1541i.m3038k(C1490c.f2747a);
    }

    /* renamed from: c */
    public boolean mo15166c() {
        return C1488a.f2674ae;
    }
}
