package com.igexin.push.core.p047a;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1353p;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p054e.p057c.C1382k;
import com.igexin.push.p054e.p057c.C1388q;
import com.igexin.push.p087f.C1560a;

/* renamed from: com.igexin.push.core.a.s */
public class C1270s extends C1239a {

    /* renamed from: a */
    private static final String f1893a = (C1233j.f1818a + "_RegisterResultAction");

    /* renamed from: a */
    public boolean mo14464a(C1190e eVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo14465a(Object obj) {
        if (obj instanceof C1388q) {
            C1388q qVar = (C1388q) obj;
            C1343f.f2112D = 0;
            C1179b.m1354a("register resp |" + qVar.f2334a + "|" + C1343f.f2181r);
            C1179b.m1354a("register resp cid = " + qVar.f2336c + " device id = " + qVar.f2337d);
            if (qVar.f2334a != C1343f.f2181r) {
                C1343f.f2176m = false;
                C1179b.m1354a(f1893a + " change session : from [" + C1343f.f2181r + "] to [" + qVar.f2334a + "]");
                C1179b.m1354a(f1893a + " change cid : from [" + C1343f.f2182s + "] to [" + qVar.f2336c + "]");
                if (TextUtils.isEmpty(qVar.f2336c) || TextUtils.isEmpty(qVar.f2337d)) {
                    C1312h.m1937a().mo14673b(qVar.f2334a);
                } else {
                    C1312h.m1937a().mo14668a(qVar.f2336c, qVar.f2337d, qVar.f2334a);
                }
                C1343f.f2114F = 0;
            }
            C1179b.m1354a("loginReqAfterRegister|new session:" + C1343f.f2181r + ", cid :" + C1343f.f2182s + ", devId :" + C1343f.f2187x);
            C1382k d = C1353p.m2098a().mo14751d();
            C1560a g = C1340e.m2032a().mo14710g();
            StringBuilder sb = new StringBuilder();
            sb.append("S-");
            sb.append(d.f2308a);
            g.mo15187a(sb.toString(), d, true);
        }
        return true;
    }
}
