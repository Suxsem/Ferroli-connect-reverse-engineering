package com.alibaba.sdk.android.ams.common.p011c;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.ams.common.c.b */
class C0675b {

    /* renamed from: a */
    static Map<Class<?>, C0674a<?>> f808a;

    static {
        HashMap hashMap = new HashMap();
        for (C0674a next : C0677d.m494a(C0674a.class, C0676c.class.getClassLoader())) {
            C0674a aVar = (C0674a) hashMap.get(next.mo9536a());
            if (!hashMap.containsKey(next.mo9536a())) {
                hashMap.put(next.mo9536a(), next);
            } else {
                throw new IllegalStateException("Ambiguous providers: " + next.getClass().getCanonicalName() + " versus " + aVar.getClass().getCanonicalName());
            }
        }
        f808a = hashMap;
    }
}
