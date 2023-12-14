package com.taobao.accs.utl;

import java.io.ByteArrayOutputStream;

/* renamed from: com.taobao.accs.utl.g */
/* compiled from: Taobao */
public class C2090g extends ByteArrayOutputStream {
    public C2090g(int i) {
        super(i);
    }

    public C2090g() {
    }

    /* renamed from: a */
    public C2090g mo18597a(byte b) {
        write(b);
        return this;
    }

    /* renamed from: a */
    public C2090g mo18598a(short s) {
        write(s >> 8);
        write(s);
        return this;
    }
}
