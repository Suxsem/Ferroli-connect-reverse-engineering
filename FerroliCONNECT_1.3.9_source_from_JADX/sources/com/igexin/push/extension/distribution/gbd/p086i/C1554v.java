package com.igexin.push.extension.distribution.gbd.p086i;

import java.io.File;
import java.io.FileFilter;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.v */
final class C1554v implements FileFilter {

    /* renamed from: a */
    final /* synthetic */ int f2965a;

    C1554v(int i) {
        this.f2965a = i;
    }

    public boolean accept(File file) {
        try {
            return file.isDirectory() && Integer.parseInt(file.getName()) >= this.f2965a;
        } catch (Exception unused) {
            return false;
        }
    }
}
