package com.igexin.push.extension.distribution.basic.p068j;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/* renamed from: com.igexin.push.extension.distribution.basic.j.d */
public class C1436d {

    /* renamed from: a */
    private final Properties f2489a = new Properties();

    private C1436d() {
        this.f2489a.load(new FileInputStream(new File("/system/build.prop")));
    }

    /* renamed from: a */
    public static C1436d m2514a() {
        return new C1436d();
    }

    /* renamed from: a */
    public String mo14987a(String str, String str2) {
        return this.f2489a.getProperty(str, str2);
    }
}
