package com.igexin.p032b.p033a.p035b;

import com.igexin.p032b.p033a.p040d.C1190e;

/* renamed from: com.igexin.b.a.b.e */
public abstract class C1176e extends C1190e {

    /* renamed from: a */
    public String f1606a;

    /* renamed from: b */
    public C1173b f1607b;

    /* renamed from: c */
    public Object f1608c;

    /* renamed from: d */
    public C1175d f1609d;

    public C1176e(int i, String str, C1173b bVar) {
        super(i);
        if (str != null) {
            this.f1606a = m1322a(str);
        }
        this.f1607b = bVar;
    }

    public C1176e(String str, C1173b bVar) {
        this(0, str, bVar);
    }

    /* renamed from: a */
    private String m1322a(String str) {
        return C1177f.m1328a(C1177f.m1333a(str));
    }

    /* renamed from: f */
    public void mo14233f() {
        C1173b bVar = this.f1607b;
        if (bVar != null) {
            bVar.mo14257a(false);
        }
        this.f1607b = null;
        this.f1609d = null;
        this.f1608c = null;
        this.f1606a = null;
        super.mo14233f();
    }
}
