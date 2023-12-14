package com.igexin.push.extension.distribution.gbd.p069a.p072c;

import java.util.Comparator;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.c.h */
class C1468h implements Comparator<Long> {

    /* renamed from: a */
    final /* synthetic */ C1466f f2566a;

    C1468h(C1466f fVar) {
        this.f2566a = fVar;
    }

    /* renamed from: a */
    public int compare(Long l, Long l2) {
        if (l.longValue() == l2.longValue()) {
            return 0;
        }
        return l.longValue() > l2.longValue() ? 1 : -1;
    }
}
