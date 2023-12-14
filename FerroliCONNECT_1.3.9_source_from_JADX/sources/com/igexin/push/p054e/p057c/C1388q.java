package com.igexin.push.p054e.p057c;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p035b.C1177f;
import kotlin.UByte;

/* renamed from: com.igexin.push.e.c.q */
public class C1388q extends C1376e {

    /* renamed from: a */
    public long f2334a;

    /* renamed from: b */
    public String f2335b = "";

    /* renamed from: c */
    public String f2336c = "";

    /* renamed from: d */
    public String f2337d = "";

    public C1388q() {
        this.f2278i = 9;
    }

    /* renamed from: a */
    private String m2252a(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, i2, "UTF-8");
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        this.f2334a = C1177f.m1343e(bArr, 0);
        int i = 8;
        if (bArr.length > 8) {
            byte b = bArr[8] & UByte.MAX_VALUE;
            if (b > 0) {
                this.f2335b = m2252a(bArr, 9, b);
                i = b + 9;
            } else {
                i = 9;
            }
        }
        if (bArr.length > i) {
            int i2 = i + 1;
            byte b2 = bArr[i] & UByte.MAX_VALUE;
            if (b2 > 0) {
                this.f2336c = m2252a(bArr, i2, b2);
                i = b2 + i2;
            } else {
                i = i2;
            }
        }
        if (bArr.length > i) {
            int i3 = i + 1;
            byte b3 = bArr[i] & UByte.MAX_VALUE;
            if (b3 > 0) {
                this.f2337d = m2252a(bArr, i3, b3);
            }
        }
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        if (TextUtils.isEmpty(this.f2336c) || TextUtils.isEmpty(this.f2337d)) {
            byte[] bytes = this.f2335b.getBytes();
            byte[] bArr = new byte[(bytes.length + 8 + 1)];
            C1177f.m1325a(this.f2334a, bArr, 0);
            C1177f.m1338c(bytes.length, bArr, 8);
            System.arraycopy(bytes, 0, bArr, 9, bytes.length);
            return bArr;
        }
        byte[] bytes2 = this.f2335b.getBytes();
        byte[] bytes3 = this.f2336c.getBytes();
        byte[] bytes4 = this.f2337d.getBytes();
        byte[] bArr2 = new byte[(bytes2.length + 8 + bytes3.length + bytes4.length + 3)];
        C1177f.m1325a(this.f2334a, bArr2, 0);
        C1177f.m1338c(bytes2.length, bArr2, 8);
        System.arraycopy(bytes2, 0, bArr2, 9, bytes2.length);
        int length = 9 + bytes2.length;
        int i = length + 1;
        C1177f.m1338c(bytes3.length, bArr2, length);
        System.arraycopy(bytes3, 0, bArr2, i, bytes3.length);
        int length2 = i + bytes3.length;
        C1177f.m1338c(bytes4.length, bArr2, length2);
        System.arraycopy(bytes4, 0, bArr2, length2 + 1, bytes4.length);
        return bArr2;
    }
}
