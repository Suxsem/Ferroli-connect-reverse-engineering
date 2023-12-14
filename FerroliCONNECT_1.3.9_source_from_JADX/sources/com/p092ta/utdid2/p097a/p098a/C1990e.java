package com.p092ta.utdid2.p097a.p098a;

import com.p092ta.p093a.p096c.C1982f;

/* renamed from: com.ta.utdid2.a.a.e */
public class C1990e {
    public static String get(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{str, str2});
        } catch (Exception e) {
            C1982f.m3370b("", e, new Object[0]);
            return str2;
        }
    }
}
