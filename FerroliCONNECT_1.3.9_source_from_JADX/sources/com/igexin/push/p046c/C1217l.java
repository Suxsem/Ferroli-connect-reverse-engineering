package com.igexin.push.p046c;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.c.l */
public class C1217l extends C1218m implements C1220o {

    /* renamed from: e */
    private static final String f1773e = "com.igexin.push.c.l";

    /* renamed from: f */
    private static C1217l f1774f;

    private C1217l() {
        super(C1343f.f2156ar, C1343f.f2158at);
        this.f1779d.mo14365a(false);
    }

    /* renamed from: a */
    public static synchronized C1217l m1532a() {
        C1217l lVar;
        synchronized (C1217l.class) {
            if (f1774f == null) {
                f1774f = new C1217l();
            }
            lVar = f1774f;
        }
        return lVar;
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
                C1179b.m1354a(f1773e + "|detect" + mo14415c(jVar) + "failed --------");
                if (mo14428p()) {
                    C1179b.m1354a(f1773e + "|pool is not empty, detect task " + mo14415c(jVar) + "stop");
                    a.mo14434b(false);
                    return;
                }
                a.mo14435c(false);
            }
        }
    }

    /* renamed from: a */
    public void mo14410a(C1215j jVar) {
    }

    /* renamed from: b */
    public C1213h mo14411b() {
        return C1213h.MOBILE;
    }

    /* renamed from: c */
    public C1220o mo14412c() {
        return this;
    }
}
