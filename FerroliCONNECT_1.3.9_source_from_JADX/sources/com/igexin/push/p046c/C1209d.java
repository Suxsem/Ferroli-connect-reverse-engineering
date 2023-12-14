package com.igexin.push.p046c;

/* renamed from: com.igexin.push.c.d */
public enum C1209d {
    NORMAL(0),
    BACKUP(1),
    TRY_NORMAL(2);
    

    /* renamed from: d */
    private int f1743d;

    private C1209d(int i) {
        this.f1743d = -1;
        this.f1743d = i;
    }

    /* renamed from: a */
    public static C1209d m1495a(int i) {
        for (C1209d dVar : m1496a()) {
            if (dVar.mo14378b() == i) {
                return dVar;
            }
        }
        return null;
    }

    /* renamed from: a */
    public static C1209d[] m1496a() {
        return (C1209d[]) f1742e.clone();
    }

    /* renamed from: b */
    public int mo14378b() {
        return this.f1743d;
    }
}
