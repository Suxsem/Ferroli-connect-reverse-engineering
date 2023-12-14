package com.igexin.p030a.p031a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.igexin.a.a.l */
public class C1129l extends C1123f {
    public C1129l(C1127j jVar, C1122e eVar, long j) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(eVar.f1498a ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = eVar.f1500c + (j * ((long) eVar.f1502e));
        this.f1507a = jVar.mo14160c(allocate, j2);
        this.f1508b = jVar.mo14158b(allocate, 8 + j2);
        this.f1509c = jVar.mo14158b(allocate, 16 + j2);
        this.f1510d = jVar.mo14158b(allocate, j2 + 40);
    }
}
