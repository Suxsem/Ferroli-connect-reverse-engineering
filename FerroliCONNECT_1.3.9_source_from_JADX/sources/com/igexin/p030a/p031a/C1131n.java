package com.igexin.p030a.p031a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.igexin.a.a.n */
public class C1131n extends C1124g {
    public C1131n(C1127j jVar, C1122e eVar, int i) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(eVar.f1498a ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.f1511a = jVar.mo14160c(allocate, eVar.f1501d + ((long) (i * eVar.f1504g)) + 44);
    }
}
