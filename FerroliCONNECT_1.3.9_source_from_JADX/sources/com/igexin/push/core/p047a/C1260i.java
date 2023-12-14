package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.config.C1233j;
import com.igexin.push.core.C1347j;
import com.igexin.push.core.C1349l;
import com.igexin.push.p054e.C1368b;
import com.igexin.push.p054e.p057c.C1379h;

/* renamed from: com.igexin.push.core.a.i */
public class C1260i extends C1239a {

    /* renamed from: a */
    private static final String f1886a = C1233j.f1818a;

    /* renamed from: a */
    public boolean mo14464a(C1190e eVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo14465a(Object obj) {
        if (!(obj instanceof C1379h)) {
            return true;
        }
        C1368b.m2191a().mo14821e();
        C1179b.m1354a("heartbeatRsp");
        C1347j.m2085a().mo14744a(C1349l.HEARTBEAT_OK);
        return true;
    }
}
