package com.igexin.push.extension.distribution.basic.p061c;

import java.io.File;
import java.io.FileFilter;

/* renamed from: com.igexin.push.extension.distribution.basic.c.d */
class C1414d implements FileFilter {

    /* renamed from: a */
    long f2419a = System.currentTimeMillis();

    /* renamed from: b */
    long f2420b = 604800000;

    /* renamed from: c */
    final /* synthetic */ C1413c f2421c;

    C1414d(C1413c cVar) {
        this.f2421c = cVar;
    }

    public boolean accept(File file) {
        return this.f2419a - file.lastModified() >= this.f2420b;
    }
}
