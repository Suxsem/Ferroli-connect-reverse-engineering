package com.igexin.p030a.p031a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.igexin.a.a.i */
public class C1126i extends C1122e {

    /* renamed from: j */
    private final C1127j f1513j;

    public C1126i(boolean z, C1127j jVar) {
        this.f1498a = z;
        this.f1513j = jVar;
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(z ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.f1499b = jVar.mo14162d(allocate, 16);
        this.f1500c = jVar.mo14158b(allocate, 32);
        this.f1501d = jVar.mo14158b(allocate, 40);
        this.f1502e = jVar.mo14162d(allocate, 54);
        this.f1503f = jVar.mo14162d(allocate, 56);
        this.f1504g = jVar.mo14162d(allocate, 58);
        this.f1505h = jVar.mo14162d(allocate, 60);
        this.f1506i = jVar.mo14162d(allocate, 62);
    }

    /* renamed from: a */
    public C1121d mo14152a(long j, int i) {
        return new C1119b(this.f1513j, this, j, i);
    }

    /* renamed from: a */
    public C1123f mo14153a(long j) {
        return new C1129l(this.f1513j, this, j);
    }

    /* renamed from: a */
    public C1124g mo14154a(int i) {
        return new C1131n(this.f1513j, this, i);
    }
}
