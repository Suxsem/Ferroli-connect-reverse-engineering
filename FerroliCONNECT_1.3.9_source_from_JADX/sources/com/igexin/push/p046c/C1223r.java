package com.igexin.push.p046c;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.c.r */
public class C1223r extends C1218m implements C1220o {

    /* renamed from: e */
    private static final String f1799e = "com.igexin.push.c.r";

    /* renamed from: f */
    private static C1223r f1800f;

    private C1223r() {
        super(C1343f.f2157as, C1343f.f2159au);
        this.f1779d.mo14365a(true);
    }

    /* renamed from: a */
    public static synchronized C1223r m1596a() {
        C1223r rVar;
        synchronized (C1223r.class) {
            if (f1800f == null) {
                f1800f = new C1223r();
            }
            rVar = f1800f;
        }
        return rVar;
    }

    /* renamed from: a */
    public void mo14409a(C1212g gVar, C1215j jVar) {
        C1221p a;
        if (jVar != null && !TextUtils.isEmpty(jVar.mo14389a()) && (a = mo14413a(jVar.mo14389a())) != null) {
            mo14414b(jVar);
            if (gVar == C1212g.f1748a) {
                a.mo14434b(false);
                a.mo14439j();
                mo14422j();
                mo14425m();
            } else if (gVar == C1212g.EXCEPTION || gVar == C1212g.FAILED) {
                mo14426n();
                C1179b.m1354a(f1799e + "|detect" + mo14415c(jVar) + "failed --------------");
                if (mo14428p()) {
                    a.mo14434b(false);
                } else {
                    a.mo14435c(false);
                }
            }
        }
    }

    /* renamed from: a */
    public void mo14410a(C1215j jVar) {
    }

    /* renamed from: b */
    public C1213h mo14411b() {
        return C1213h.WIFI;
    }

    /* renamed from: c */
    public C1220o mo14412c() {
        return this;
    }
}
