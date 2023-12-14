package com.igexin.push.p046c;

import java.util.Comparator;
import java.util.Map;

/* renamed from: com.igexin.push.c.n */
class C1219n implements Comparator<Map.Entry<String, C1215j>> {

    /* renamed from: a */
    final /* synthetic */ C1218m f1785a;

    C1219n(C1218m mVar) {
        this.f1785a = mVar;
    }

    /* renamed from: a */
    public int compare(Map.Entry<String, C1215j> entry, Map.Entry<String, C1215j> entry2) {
        return (int) (entry.getValue().mo14402e() - entry2.getValue().mo14402e());
    }
}
