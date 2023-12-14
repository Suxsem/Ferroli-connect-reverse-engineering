package com.alibaba.sdk.android.emas;

import java.util.List;

/* renamed from: com.alibaba.sdk.android.emas.f */
/* compiled from: EmasLog */
public class C0711f {

    /* renamed from: b */
    private final List<C0712g> f906b;

    /* renamed from: c */
    private final C0707d f907c;

    /* renamed from: c */
    private final String f908c;

    public C0711f(List<C0712g> list) {
        this(list, C0707d.MEM_CACHE, (String) null);
    }

    public C0711f(List<C0712g> list, C0707d dVar, String str) {
        this.f906b = list;
        this.f907c = dVar;
        this.f908c = str;
    }

    /* renamed from: a */
    public C0707d mo9643a() {
        return this.f907c;
    }

    /* renamed from: a */
    public List<C0712g> m633a() {
        return this.f906b;
    }

    public String getLocation() {
        if (this.f907c == C0707d.DISK_CACHE) {
            return this.f908c;
        }
        return null;
    }
}
