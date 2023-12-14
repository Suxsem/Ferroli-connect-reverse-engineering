package com.igexin.push.core.p049b;

import com.igexin.push.core.bean.C1292o;
import java.util.Comparator;

/* renamed from: com.igexin.push.core.b.b */
class C1277b implements Comparator<C1292o> {

    /* renamed from: a */
    final /* synthetic */ C1276a f1918a;

    C1277b(C1276a aVar) {
        this.f1918a = aVar;
    }

    /* renamed from: a */
    public int compare(C1292o oVar, C1292o oVar2) {
        if (!oVar.mo14634d().equals(oVar2.mo14634d())) {
            return oVar.mo14634d().compareTo(oVar2.mo14634d());
        }
        return 0;
    }
}
