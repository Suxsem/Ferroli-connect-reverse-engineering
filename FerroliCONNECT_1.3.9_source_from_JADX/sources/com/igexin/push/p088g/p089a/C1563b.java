package com.igexin.push.p088g.p089a;

import com.igexin.p032b.p033a.p040d.C1180a;
import java.io.InputStream;

/* renamed from: com.igexin.push.g.a.b */
public abstract class C1563b extends C1180a {

    /* renamed from: b */
    String f2981b;

    /* renamed from: c */
    byte[] f2982c;

    /* renamed from: d */
    InputStream f2983d;

    /* renamed from: e */
    long f2984e;

    /* renamed from: f */
    public boolean f2985f;

    public C1563b(String str) {
        this.f2981b = str;
    }

    /* renamed from: a */
    public void mo14695a(Exception exc) {
    }

    /* renamed from: a */
    public void mo14696a(byte[] bArr) {
        this.f2985f = false;
        if (bArr != null && bArr.length >= 7 && bArr[5] == 111 && bArr[6] == 107) {
            this.f2985f = true;
        }
    }

    /* renamed from: b */
    public void mo15202b(byte[] bArr) {
        this.f2982c = bArr;
    }

    /* renamed from: c */
    public String mo15203c() {
        return this.f2981b;
    }

    /* renamed from: d */
    public byte[] mo15204d() {
        return this.f2982c;
    }

    /* renamed from: e */
    public InputStream mo15205e() {
        return this.f2983d;
    }

    /* renamed from: f */
    public long mo15206f() {
        return this.f2984e;
    }
}
