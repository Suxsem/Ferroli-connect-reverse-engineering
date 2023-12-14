package com.igexin.p032b.p033a.p035b;

/* renamed from: com.igexin.b.a.b.b */
public abstract class C1173b {

    /* renamed from: c */
    protected String f1594c;

    /* renamed from: d */
    protected C1173b f1595d;

    /* renamed from: e */
    protected C1173b f1596e;

    /* renamed from: f */
    protected boolean f1597f;

    public C1173b(String str, boolean z) {
        this.f1594c = str;
        this.f1597f = z;
    }

    /* renamed from: a */
    public abstract Object mo14255a(C1176e eVar, C1175d dVar, Object obj);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo14256a(C1173b bVar) {
        if (bVar != null) {
            C1173b bVar2 = bVar.f1595d;
            bVar.f1595d = this;
            this.f1596e = bVar;
            this.f1595d = bVar2;
        }
    }

    /* renamed from: a */
    public void mo14257a(boolean z) {
        if (!this.f1597f || z) {
            while (true) {
                C1173b bVar = this.f1595d;
                if (bVar != null) {
                    C1173b bVar2 = bVar.f1595d;
                    bVar.f1595d = null;
                    this.f1595d = bVar2;
                } else {
                    return;
                }
            }
        }
    }

    /* renamed from: c */
    public abstract Object mo14258c(C1176e eVar, C1175d dVar, Object obj);

    /* renamed from: d */
    public final Object mo14259d(C1176e eVar, C1175d dVar, Object obj) {
        if (obj != null) {
            C1173b bVar = this.f1595d;
            if (bVar != null) {
                obj = bVar.mo14259d(eVar, dVar, obj);
            }
            return mo14255a(eVar, dVar, obj);
        }
        throw new NullPointerException("Nothing to encode!");
    }
}
