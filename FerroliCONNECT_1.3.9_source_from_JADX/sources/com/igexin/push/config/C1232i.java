package com.igexin.push.config;

import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.config.i */
public class C1232i {

    /* renamed from: a */
    private static C1232i f1817a;

    private C1232i() {
    }

    /* renamed from: a */
    public static synchronized C1232i m1623a() {
        C1232i iVar;
        synchronized (C1232i.class) {
            if (f1817a == null) {
                f1817a = new C1232i();
            }
            iVar = f1817a;
        }
        return iVar;
    }

    /* renamed from: b */
    public boolean mo14452b() {
        C1235l.m1625a();
        C1235l.m1626a(C1343f.f2169f);
        return true;
    }
}
