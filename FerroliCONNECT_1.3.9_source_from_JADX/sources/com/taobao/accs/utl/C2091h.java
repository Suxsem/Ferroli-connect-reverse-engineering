package com.taobao.accs.utl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/* renamed from: com.taobao.accs.utl.h */
/* compiled from: Taobao */
public class C2091h extends ByteArrayInputStream {
    public C2091h(byte[] bArr) {
        super(bArr);
    }

    /* renamed from: a */
    public int mo18599a() {
        return read() & 255;
    }

    /* renamed from: b */
    public int mo18601b() {
        return (mo18599a() << 8) | mo18599a();
    }

    /* renamed from: a */
    public String mo18600a(int i) throws IOException {
        byte[] bArr = new byte[i];
        int read = read(bArr);
        if (read == i) {
            return new String(bArr, "utf-8");
        }
        throw new IOException("read len not match. ask for " + i + " but read for " + read);
    }

    /* renamed from: c */
    public byte[] mo18602c() throws IOException {
        byte[] bArr = new byte[available()];
        read(bArr);
        return bArr;
    }
}
