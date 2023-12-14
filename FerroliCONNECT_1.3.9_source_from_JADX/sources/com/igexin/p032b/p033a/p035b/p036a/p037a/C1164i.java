package com.igexin.p032b.p033a.p035b.p036a.p037a;

import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.p038a.C1155c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.util.C1589n;

/* renamed from: com.igexin.b.a.b.a.a.i */
class C1164i implements C1155c {

    /* renamed from: a */
    final /* synthetic */ C1161f f1572a;

    C1164i(C1161f fVar) {
        this.f1572a = fVar;
    }

    /* renamed from: a */
    public void mo14226a(C1168m mVar) {
        if (!C1589n.m3262d()) {
            this.f1572a.m1255b(mVar);
        }
    }

    /* renamed from: a */
    public void mo14224a(C1176e eVar) {
        this.f1572a.f1567n.sendEmptyMessage(C1172q.INTERRUPT_SUCCESS.ordinal());
    }

    /* renamed from: a */
    public void mo14227a(Exception exc) {
        C1179b.m1354a("GS-M|w ex = " + exc.toString());
        this.f1572a.m1258i();
    }
}
