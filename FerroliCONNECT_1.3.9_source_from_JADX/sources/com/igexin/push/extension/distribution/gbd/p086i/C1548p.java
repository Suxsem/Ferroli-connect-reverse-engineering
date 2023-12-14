package com.igexin.push.extension.distribution.gbd.p086i;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.p */
final class C1548p implements FilenameFilter {

    /* renamed from: a */
    final /* synthetic */ String f2959a;

    C1548p(String str) {
        this.f2959a = str;
    }

    public boolean accept(File file, String str) {
        return str.contains(this.f2959a) && str.endsWith(".db");
    }
}
