package com.igexin.push.p053d;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1340e;
import com.igexin.push.p088g.p090b.C1575h;
import com.igexin.push.util.C1576a;
import com.igexin.sdk.GActivity;
import java.util.Random;

/* renamed from: com.igexin.push.d.a */
public class C1360a implements C1363d {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public String f2227a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public C1363d f2228b;

    public C1360a(String str) {
        this.f2227a = str;
    }

    /* renamed from: a */
    public void mo14806a() {
        if (TextUtils.isEmpty(this.f2227a) || !C1234k.f1822D || !C1576a.m3209a(this.f2227a, GActivity.TAG)) {
            C1363d dVar = this.f2228b;
            if (dVar != null) {
                dVar.mo14806a();
                return;
            }
            return;
        }
        try {
            C1340e.m2032a().mo14703a((C1575h) new C1361b(this, (long) ((new Random().nextInt(6) + 1) * 1000)));
        } catch (Throwable th) {
            C1179b.m1354a("ActivityGuardTask|startActivity pkgName = " + this.f2227a + ", exception : " + th.toString());
        }
    }

    /* renamed from: a */
    public void mo14807a(C1363d dVar) {
        this.f2228b = dVar;
    }
}
