package com.igexin.push.extension.distribution.basic.stub;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.p041a.C1182b;
import com.igexin.push.extension.distribution.basic.p061c.C1412b;
import com.igexin.push.extension.distribution.basic.p066h.C1430a;
import com.igexin.push.p088g.p090b.C1575h;

/* renamed from: com.igexin.push.extension.distribution.basic.stub.b */
class C1447b extends C1575h {

    /* renamed from: a */
    final /* synthetic */ PushExtension f2494a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1447b(PushExtension pushExtension, long j) {
        super(j);
        this.f2494a = pushExtension;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        try {
            C1179b.m1354a(this.f1613l + "|start extensionThread");
            C1412b bVar = new C1412b();
            bVar.mo14940a(new C1430a());
            C1174c.m1310b().mo14314a((C1182b) bVar);
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
