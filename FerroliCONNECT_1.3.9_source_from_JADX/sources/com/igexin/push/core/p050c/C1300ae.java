package com.igexin.push.core.p050c;

import com.igexin.push.core.C1343f;
import com.igexin.push.p045b.C1205d;
import com.igexin.push.util.C1581f;
import com.igexin.push.util.EncryptUtils;

/* renamed from: com.igexin.push.core.c.ae */
class C1300ae extends C1205d {

    /* renamed from: a */
    final /* synthetic */ C1312h f2023a;

    C1300ae(C1312h hVar) {
        this.f2023a = hVar;
    }

    /* renamed from: a */
    public void mo14362a() {
        C1312h.m1937a().m1939a(this.f1716d, 1, EncryptUtils.getBytesEncrypted(String.valueOf(C1343f.f2181r).getBytes()));
        C1312h.m1937a().m1939a(this.f1716d, 20, this.f2023a.m1953h(C1343f.f2182s));
        C1581f.m3230a();
    }
}
