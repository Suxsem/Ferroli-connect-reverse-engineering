package com.igexin.push.extension.distribution.basic.p064f;

import com.igexin.p032b.p033a.p040d.C1180a;

/* renamed from: com.igexin.push.extension.distribution.basic.f.f */
public abstract class C1427f extends C1180a {

    /* renamed from: a */
    String f2470a;

    /* renamed from: b */
    byte[] f2471b;

    /* renamed from: c */
    protected boolean f2472c;

    /* renamed from: d */
    protected boolean f2473d;

    /* renamed from: e */
    protected boolean f2474e;

    /* renamed from: f */
    public boolean f2475f;

    public C1427f(String str) {
        this.f2470a = str;
    }

    /* renamed from: a */
    public void mo14974a(Exception exc) {
    }

    /* renamed from: a */
    public void mo14975a(byte[] bArr) {
        this.f2475f = false;
        if (bArr != null && bArr.length >= 7 && bArr[5] == 111 && bArr[6] == 107) {
            this.f2475f = true;
        }
    }

    /* renamed from: b */
    public void mo14977b(byte[] bArr) {
        this.f2471b = bArr;
    }

    /* renamed from: c */
    public String mo14978c() {
        return this.f2470a;
    }

    /* renamed from: d */
    public byte[] mo14979d() {
        return this.f2471b;
    }

    /* renamed from: e */
    public boolean mo14980e() {
        return this.f2472c;
    }

    /* renamed from: f */
    public boolean mo14981f() {
        return this.f2473d;
    }
}
