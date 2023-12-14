package com.igexin.push.extension.distribution.basic.p068j;

import java.io.File;
import java.io.FileFilter;

/* renamed from: com.igexin.push.extension.distribution.basic.j.l */
final class C1444l implements FileFilter {
    C1444l() {
    }

    public boolean accept(File file) {
        try {
            return file.isDirectory() && Integer.parseInt(file.getName()) >= 2000;
        } catch (Exception unused) {
            return false;
        }
    }
}
