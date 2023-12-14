package com.igexin.push.core.p047a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.push.core.C1353p;
import com.igexin.push.p054e.p057c.C1381j;

/* renamed from: com.igexin.push.core.a.j */
public class C1261j extends C1239a {

    /* renamed from: a */
    private static final String f1887a = "com.igexin.push.core.a.j";

    /* renamed from: a */
    public boolean mo14464a(C1190e eVar) {
        return false;
    }

    /* renamed from: a */
    public boolean mo14465a(Object obj) {
        if (obj instanceof C1381j) {
            C1381j jVar = (C1381j) obj;
            boolean z = jVar.f2306a == 0;
            C1179b.m1354a(f1887a + "|KeyNego result = " + jVar.f2306a);
            if (z) {
                C1179b.m1354a(f1887a + "|KeyNego success and login");
                C1353p.m2098a().mo14750c();
            }
        }
        return true;
    }
}
