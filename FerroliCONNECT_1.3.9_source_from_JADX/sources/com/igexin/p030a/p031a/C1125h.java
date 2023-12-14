package com.igexin.p030a.p031a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.igexin.a.a.h */
public class C1125h extends C1122e {

    /* renamed from: j */
    private final C1127j f1512j;

    public C1125h(boolean z, C1127j jVar) {
        this.f1498a = z;
        this.f1512j = jVar;
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(z ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.f1499b = jVar.mo14162d(allocate, 16);
        this.f1500c = jVar.mo14160c(allocate, 28);
        this.f1501d = jVar.mo14160c(allocate, 32);
        this.f1502e = jVar.mo14162d(allocate, 42);
        this.f1503f = jVar.mo14162d(allocate, 44);
        this.f1504g = jVar.mo14162d(allocate, 46);
        this.f1505h = jVar.mo14162d(allocate, 48);
        this.f1506i = jVar.mo14162d(allocate, 50);
    }

    /* renamed from: a */
    public C1121d mo14152a(long j, int i) {
        return new C1118a(this.f1512j, this, j, i);
    }

    /* renamed from: a */
    public C1123f mo14153a(long j) {
        return new C1128k(this.f1512j, this, j);
    }

    /* renamed from: a */
    public C1124g mo14154a(int i) {
        return new C1130m(this.f1512j, this, i);
    }
}
