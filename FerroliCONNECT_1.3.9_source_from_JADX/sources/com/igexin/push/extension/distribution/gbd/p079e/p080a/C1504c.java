package com.igexin.push.extension.distribution.gbd.p079e.p080a;

import com.igexin.push.extension.distribution.gbd.p076b.C1486f;
import java.util.Comparator;

/* renamed from: com.igexin.push.extension.distribution.gbd.e.a.c */
class C1504c implements Comparator<C1486f> {

    /* renamed from: a */
    final /* synthetic */ C1503b f2896a;

    C1504c(C1503b bVar) {
        this.f2896a = bVar;
    }

    /* renamed from: a */
    public int compare(C1486f fVar, C1486f fVar2) {
        if (fVar.mo15075d() == fVar2.mo15075d()) {
            return 0;
        }
        return fVar.mo15075d() > fVar2.mo15075d() ? -1 : 1;
    }
}
