package com.igexin.p032b.p033a.p035b.p036a.p037a;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

/* renamed from: com.igexin.b.a.b.a.a.p */
public class C1171p {

    /* renamed from: a */
    private BufferedOutputStream f1585a;

    public C1171p(OutputStream outputStream) {
        this.f1585a = new BufferedOutputStream(outputStream);
    }

    /* renamed from: a */
    public void mo14254a(byte[] bArr) {
        this.f1585a.write(bArr, 0, bArr.length);
        this.f1585a.flush();
    }
}
