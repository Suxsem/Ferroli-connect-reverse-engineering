package com.igexin.p030a;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.igexin.a.i */
class C1139i implements FilenameFilter {

    /* renamed from: a */
    final /* synthetic */ String f1522a;

    /* renamed from: b */
    final /* synthetic */ C1138h f1523b;

    C1139i(C1138h hVar, String str) {
        this.f1523b = hVar;
        this.f1522a = str;
    }

    public boolean accept(File file, String str) {
        return str.startsWith(this.f1522a);
    }
}
