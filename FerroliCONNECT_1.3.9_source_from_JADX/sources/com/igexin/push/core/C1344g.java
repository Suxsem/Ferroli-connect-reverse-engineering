package com.igexin.push.core;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.igexin.push.core.g */
final class C1344g implements FilenameFilter {
    C1344g() {
    }

    public boolean accept(File file, String str) {
        return str.startsWith("tdata");
    }
}
