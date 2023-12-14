package com.igexin.push.extension.distribution.basic.p064f;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;

/* renamed from: com.igexin.push.extension.distribution.basic.f.c */
public class C1424c {
    /* renamed from: a */
    public static String m2469a() {
        String a = m2470a("getConfigServiceUrl");
        return a == null ? C1343f.m2075a() : a;
    }

    /* renamed from: a */
    private static String m2470a(String str) {
        try {
            Class<?> cls = Class.forName("com.igexin.push.config.SDKUrlConfig");
            return (String) cls.getMethod(str, new Class[0]).invoke(cls, new Object[0]);
        } catch (Exception unused) {
            C1179b.m1354a("Basic_BasicUrl-> get method :" + str + "() error, use default");
            return null;
        }
    }
}
