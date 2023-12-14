package com.igexin.push.extension.distribution.gbd.p081f;

import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.f.b */
public class C1514b {
    /* renamed from: a */
    public static String m2891a() {
        String a = m2892a("getBiUploadServiceUrl");
        return a == null ? C1343f.m2075a() : a;
    }

    /* renamed from: a */
    private static String m2892a(String str) {
        try {
            Class<?> cls = Class.forName("com.igexin.push.config.SDKUrlConfig");
            return (String) cls.getMethod(str, new Class[0]).invoke(cls, new Object[0]);
        } catch (Exception unused) {
            C1540h.m2997b("GBDGBDUrl", "GBD_GBDUrl-> get method :" + str + "() error, use default");
            return null;
        }
    }

    /* renamed from: b */
    public static String m2893b() {
        String a = m2892a("getConfigServiceUrl");
        return a == null ? C1343f.m2075a() : a;
    }
}
