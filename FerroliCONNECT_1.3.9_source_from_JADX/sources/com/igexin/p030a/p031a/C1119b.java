package com.igexin.p030a.p031a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.igexin.a.a.b */
public class C1119b extends C1121d {
    public C1119b(C1127j jVar, C1122e eVar, long j, int i) {
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(eVar.f1498a ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = j + ((long) (i * 16));
        this.f1496a = jVar.mo14158b(allocate, j2);
        this.f1497b = jVar.mo14158b(allocate, j2 + 8);
    }
}
