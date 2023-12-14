package com.alibaba.sdk.android.ams.common.p011c;

/* renamed from: com.alibaba.sdk.android.ams.common.c.c */
public final class C0676c {
    /* renamed from: a */
    public static <T> T m493a(Class<T> cls) {
        C0674a aVar = C0675b.f808a.get(cls);
        if (aVar != null) {
            return cls.cast(aVar.mo9537b());
        }
        throw new IllegalArgumentException("No factory was registered for " + cls.getCanonicalName());
    }
}
