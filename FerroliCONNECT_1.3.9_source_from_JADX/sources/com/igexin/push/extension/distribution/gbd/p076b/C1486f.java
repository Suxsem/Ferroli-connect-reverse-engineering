package com.igexin.push.extension.distribution.gbd.p076b;

import android.text.TextUtils;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.b.f */
public class C1486f {

    /* renamed from: a */
    private int f2607a;

    /* renamed from: b */
    private int f2608b;

    /* renamed from: c */
    private String f2609c;

    /* renamed from: d */
    private long f2610d;

    /* renamed from: e */
    private int f2611e;

    /* renamed from: a */
    public int mo15067a() {
        return this.f2607a;
    }

    /* renamed from: a */
    public void mo15068a(int i) {
        this.f2607a = i;
    }

    /* renamed from: a */
    public void mo15069a(long j) {
        this.f2610d = j;
    }

    /* renamed from: a */
    public void mo15070a(String str) {
        this.f2609c = str;
    }

    /* renamed from: b */
    public int mo15071b() {
        return this.f2608b;
    }

    /* renamed from: b */
    public void mo15072b(int i) {
        this.f2608b = i;
    }

    /* renamed from: c */
    public String mo15073c() {
        return this.f2609c;
    }

    /* renamed from: c */
    public void mo15074c(int i) {
        this.f2611e = i;
    }

    /* renamed from: d */
    public int mo15075d() {
        return this.f2611e;
    }

    public boolean equals(Object obj) {
        try {
            if (!(obj instanceof C1486f)) {
                return false;
            }
            C1486f fVar = (C1486f) obj;
            return !TextUtils.isEmpty(fVar.mo15073c()) && !TextUtils.isEmpty(mo15073c()) && fVar.mo15073c().equals(mo15073c());
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return false;
        }
    }

    public int hashCode() {
        int i = ((this.f2607a * 31) + this.f2608b) * 31;
        String str = this.f2609c;
        int hashCode = str != null ? str.hashCode() : 0;
        long j = this.f2610d;
        return ((i + hashCode) * 31) + ((int) (j ^ (j >>> 32)));
    }
}
