package com.igexin.p030a.p031a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.igexin.a.a.k */
public class C1128k extends C1123f {
    public C1128k(C1127j jVar, C1122e eVar, long j) {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(eVar.f1498a ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = eVar.f1500c + (j * ((long) eVar.f1502e));
        this.f1507a = jVar.mo14160c(allocate, j2);
        this.f1508b = jVar.mo14160c(allocate, 4 + j2);
        this.f1509c = jVar.mo14160c(allocate, 8 + j2);
        this.f1510d = jVar.mo14160c(allocate, j2 + 20);
    }
}
