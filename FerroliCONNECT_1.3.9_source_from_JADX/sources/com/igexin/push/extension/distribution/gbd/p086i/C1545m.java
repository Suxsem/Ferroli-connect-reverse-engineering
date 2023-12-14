package com.igexin.push.extension.distribution.gbd.p086i;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.m */
final class C1545m implements FilenameFilter {
    C1545m() {
    }

    public boolean accept(File file, String str) {
        return str.endsWith(".bin");
    }
}
