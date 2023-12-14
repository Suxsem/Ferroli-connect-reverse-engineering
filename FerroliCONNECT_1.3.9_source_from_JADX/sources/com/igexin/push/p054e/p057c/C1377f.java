package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;

/* renamed from: com.igexin.push.e.c.f */
public class C1377f extends C1376e {

    /* renamed from: a */
    String f2281a;

    /* renamed from: b */
    String f2282b;

    /* renamed from: c */
    String f2283c;

    /* renamed from: d */
    String f2284d;

    public C1377f() {
        this.f2278i = 6;
        this.f2279j = 20;
        this.f2281a = "";
        this.f2282b = "";
        this.f2283c = "";
        this.f2284d = "";
    }

    public C1377f(String str, String str2, String str3, String str4) {
        this.f2278i = 6;
        this.f2279j = 20;
        this.f2281a = str == null ? "" : str;
        this.f2282b = str2 == null ? "" : str2;
        this.f2283c = str3 == null ? "" : str3;
        this.f2284d = str4 == null ? "" : str4;
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        try {
            int a = C1177f.m1326a(bArr, 0);
            this.f2281a = new String(bArr, 1, a, "utf-8");
            int i = a + 1;
            int a2 = C1177f.m1326a(bArr, i);
            int i2 = i + 1;
            this.f2282b = new String(bArr, i2, a2, "utf-8");
            int i3 = i2 + a2;
            int a3 = C1177f.m1326a(bArr, i3);
            int i4 = i3 + 1;
            this.f2283c = new String(bArr, i4, a3, "utf-8");
            int i5 = i4 + a3;
            this.f2284d = new String(bArr, i5 + 1, C1177f.m1326a(bArr, i5), "utf-8");
        } catch (Exception unused) {
        }
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        byte[] bytes = this.f2282b.getBytes();
        byte[] bytes2 = this.f2281a.getBytes();
        byte[] bytes3 = this.f2283c.getBytes();
        byte[] bytes4 = this.f2284d.getBytes();
        byte[] bArr = new byte[(bytes.length + bytes2.length + bytes3.length + bytes4.length + 4)];
        C1177f.m1338c(bytes.length, bArr, 0);
        System.arraycopy(bytes, 0, bArr, 1, bytes.length);
        int length = 1 + bytes.length;
        int i = length + 1;
        C1177f.m1338c(bytes2.length, bArr, length);
        System.arraycopy(bytes2, 0, bArr, i, bytes2.length);
        int length2 = i + bytes2.length;
        int i2 = length2 + 1;
        C1177f.m1338c(bytes3.length, bArr, length2);
        System.arraycopy(bytes3, 0, bArr, i2, bytes3.length);
        int length3 = i2 + bytes3.length;
        C1177f.m1338c(bytes4.length, bArr, length3);
        System.arraycopy(bytes4, 0, bArr, length3 + 1, bytes4.length);
        int length4 = bytes4.length;
        return bArr;
    }
}
