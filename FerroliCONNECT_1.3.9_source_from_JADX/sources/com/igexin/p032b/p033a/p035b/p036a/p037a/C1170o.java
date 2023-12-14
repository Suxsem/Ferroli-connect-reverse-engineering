package com.igexin.p032b.p033a.p035b.p036a.p037a;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.igexin.b.a.b.a.a.o */
public class C1170o {

    /* renamed from: a */
    BufferedInputStream f1584a;

    public C1170o(InputStream inputStream) {
        this.f1584a = new BufferedInputStream(inputStream);
    }

    /* renamed from: a */
    public int mo14253a(byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            i2 = this.f1584a.read(bArr, i, length - i);
            if (i2 > 0) {
                i += i2;
            } else {
                throw new IOException("read = -1, end of stream !");
            }
        }
        return i2;
    }
}
