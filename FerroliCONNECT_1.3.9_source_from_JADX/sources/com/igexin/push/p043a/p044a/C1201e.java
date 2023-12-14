package com.igexin.push.p043a.p044a;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.p051d.C1338g;
import com.igexin.push.p088g.C1567b;
import com.igexin.push.p088g.p089a.C1564c;
import com.igexin.push.util.C1581f;

/* renamed from: com.igexin.push.a.a.e */
class C1201e extends C1567b {

    /* renamed from: a */
    final /* synthetic */ C1200d f1707a;

    C1201e(C1200d dVar) {
        this.f1707a = dVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14352a() {
        try {
            String h = C1581f.m3244h();
            if (!TextUtils.isEmpty(h)) {
                C1174c.m1310b().mo14317a(new C1564c(new C1338g(SDKUrlConfig.getBiUploadServiceUrl(), h.getBytes(), 10, false)), false, true);
            }
        } catch (Throwable th) {
            C1179b.m1354a("UploadBITask|" + th.toString());
        }
    }
}
