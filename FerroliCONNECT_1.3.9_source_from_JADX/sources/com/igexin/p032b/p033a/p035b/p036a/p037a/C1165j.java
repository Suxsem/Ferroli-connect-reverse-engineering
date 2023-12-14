package com.igexin.p032b.p033a.p035b.p036a.p037a;

import java.util.Comparator;

/* renamed from: com.igexin.b.a.b.a.a.j */
class C1165j implements Comparator<C1168m> {

    /* renamed from: a */
    final /* synthetic */ C1161f f1573a;

    C1165j(C1161f fVar) {
        this.f1573a = fVar;
    }

    /* renamed from: a */
    public int compare(C1168m mVar, C1168m mVar2) {
        if (mVar == null) {
            return 1;
        }
        if (mVar2 == null) {
            return -1;
        }
        if (((long) mVar.f1659y) + mVar.f1657w > ((long) mVar2.f1659y) + mVar2.f1657w) {
            return 1;
        }
        return ((long) mVar.f1659y) + mVar.f1657w < ((long) mVar2.f1659y) + mVar2.f1657w ? -1 : 0;
    }
}
