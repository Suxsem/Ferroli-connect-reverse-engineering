package com.igexin.push.extension.distribution.basic.stub;

import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.extension.distribution.basic.p064f.C1422a;
import com.igexin.push.extension.distribution.basic.p064f.C1426e;
import com.igexin.push.p088g.p090b.C1575h;

/* renamed from: com.igexin.push.extension.distribution.basic.stub.a */
class C1446a extends C1575h {

    /* renamed from: a */
    final /* synthetic */ PushExtension f2493a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1446a(PushExtension pushExtension, long j) {
        super(j);
        this.f2493a = pushExtension;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        try {
            C1179b.m1354a(this.f1613l + "|current time - lastGetSdkConfigTime > 24h, request config");
            C1174c.m1310b().mo14317a(new C1422a(new C1426e()), false, true);
        } catch (Exception unused) {
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
