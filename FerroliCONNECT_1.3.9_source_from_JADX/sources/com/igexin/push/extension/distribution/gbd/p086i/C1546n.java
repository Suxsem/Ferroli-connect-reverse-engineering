package com.igexin.push.extension.distribution.gbd.p086i;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.n */
final class C1546n implements FilenameFilter {

    /* renamed from: a */
    final /* synthetic */ String f2958a;

    C1546n(String str) {
        this.f2958a = str;
    }

    public boolean accept(File file, String str) {
        return str.contains(this.f2958a) && str.endsWith(".db");
    }
}
