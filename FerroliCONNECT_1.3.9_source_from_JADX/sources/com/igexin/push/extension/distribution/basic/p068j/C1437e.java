package com.igexin.push.extension.distribution.basic.p068j;

/* renamed from: com.igexin.push.extension.distribution.basic.j.e */
public class C1437e {

    /* renamed from: a */
    private static C1436d f2490a;

    /* renamed from: a */
    public static boolean m2516a() {
        try {
            String a = m2517b().mo14987a("ro.build.user", (String) null);
            return a != null && a.trim().equals("flyme");
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: b */
    private static C1436d m2517b() {
        try {
            if (f2490a == null) {
                f2490a = C1436d.m2514a();
            }
        } catch (Exception unused) {
        }
        return f2490a;
    }
}
