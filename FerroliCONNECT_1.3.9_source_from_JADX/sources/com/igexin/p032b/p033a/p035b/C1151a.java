package com.igexin.p032b.p033a.p035b;

import java.io.OutputStream;

/* renamed from: com.igexin.b.a.b.a */
public class C1151a extends OutputStream {

    /* renamed from: a */
    private OutputStream f1538a = null;

    /* renamed from: b */
    private int f1539b = 0;

    /* renamed from: c */
    private int f1540c = 0;

    /* renamed from: d */
    private int f1541d = 0;

    /* renamed from: e */
    private int f1542e = 0;

    public C1151a(OutputStream outputStream, int i) {
        this.f1538a = outputStream;
        this.f1542e = i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14218a() {
        if (this.f1540c > 0) {
            int i = this.f1542e;
            if (i > 0 && this.f1541d == i) {
                this.f1538a.write("\r\n".getBytes());
                this.f1541d = 0;
            }
            char charAt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((this.f1539b << 8) >>> 26);
            char charAt2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((this.f1539b << 14) >>> 26);
            char c = '=';
            char charAt3 = this.f1540c < 2 ? '=' : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((this.f1539b << 20) >>> 26);
            if (this.f1540c >= 3) {
                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt((this.f1539b << 26) >>> 26);
            }
            this.f1538a.write(charAt);
            this.f1538a.write(charAt2);
            this.f1538a.write(charAt3);
            this.f1538a.write(c);
            this.f1541d += 4;
            this.f1540c = 0;
            this.f1539b = 0;
        }
    }

    public void close() {
        mo14218a();
        this.f1538a.close();
    }

    public void write(int i) {
        int i2 = this.f1540c;
        this.f1539b = ((i & 255) << (16 - (i2 * 8))) | this.f1539b;
        this.f1540c = i2 + 1;
        if (this.f1540c == 3) {
            mo14218a();
        }
    }
}
