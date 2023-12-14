package com.taobao.accs.p102b;

import com.taobao.accs.utl.ALog;

/* renamed from: com.taobao.accs.b.a */
/* compiled from: Taobao */
public class C2010a {

    /* renamed from: a */
    private static C2010a f3220a;

    /* renamed from: b */
    private ClassLoader f3221b = null;

    /* renamed from: c */
    private boolean f3222c = false;

    /* renamed from: a */
    public static synchronized C2010a m3433a() {
        C2010a aVar;
        synchronized (C2010a.class) {
            if (f3220a == null) {
                f3220a = new C2010a();
            }
            aVar = f3220a;
        }
        return aVar;
    }

    /* renamed from: b */
    public synchronized ClassLoader mo18324b() {
        if (this.f3221b == null) {
            ALog.m3725d("ACCSClassLoader", "getClassLoader", new Object[0]);
            this.f3221b = C2010a.class.getClassLoader();
        }
        return this.f3221b;
    }
}
