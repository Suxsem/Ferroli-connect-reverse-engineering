package com.igexin.push.p043a.p044a;

import android.os.Message;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1340e;
import com.igexin.push.p088g.p090b.C1571d;

/* renamed from: com.igexin.push.a.a.a */
public class C1197a implements C1571d {

    /* renamed from: a */
    private long f1697a = 0;

    /* renamed from: a */
    public void mo14348a() {
        Message obtain = Message.obtain();
        obtain.what = C1275b.f1906j;
        C1340e.m2032a().mo14702a(obtain);
    }

    /* renamed from: a */
    public void mo14349a(long j) {
        this.f1697a = j;
    }

    /* renamed from: b */
    public boolean mo14350b() {
        return System.currentTimeMillis() - this.f1697a > 360000;
    }
}
