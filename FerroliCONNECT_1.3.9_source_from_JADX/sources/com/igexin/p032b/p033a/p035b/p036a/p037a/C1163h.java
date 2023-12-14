package com.igexin.p032b.p033a.p035b.p036a.p037a;

import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.p038a.C1154b;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.p054e.C1368b;

/* renamed from: com.igexin.b.a.b.a.a.h */
class C1163h implements C1154b {

    /* renamed from: a */
    final /* synthetic */ C1161f f1571a;

    C1163h(C1161f fVar) {
        this.f1571a = fVar;
    }

    /* renamed from: a */
    public void mo14224a(C1176e eVar) {
        this.f1571a.f1567n.sendEmptyMessage(C1172q.INTERRUPT_SUCCESS.ordinal());
    }

    /* renamed from: a */
    public void mo14225a(Exception exc) {
        C1179b.m1354a("GS-M|r ex = " + exc.toString());
        if (exc.getMessage() != null && exc.getMessage().equals("end of stream")) {
            C1368b.m2191a().mo14820d();
        }
        this.f1571a.m1258i();
    }
}
