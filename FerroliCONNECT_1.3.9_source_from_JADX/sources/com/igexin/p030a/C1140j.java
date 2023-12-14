package com.igexin.p030a;

import android.os.Build;

/* renamed from: com.igexin.a.j */
final class C1140j implements C1135e {
    C1140j() {
    }

    /* renamed from: a */
    public void mo14164a(String str) {
        System.loadLibrary(str);
    }

    /* renamed from: a */
    public String[] mo14165a() {
        if (Build.VERSION.SDK_INT >= 21 && Build.SUPPORTED_ABIS.length > 0) {
            return Build.SUPPORTED_ABIS;
        }
        return new String[]{Build.CPU_ABI};
    }

    /* renamed from: b */
    public void mo14166b(String str) {
        System.load(str);
    }

    /* renamed from: c */
    public String mo14167c(String str) {
        return (!str.startsWith("lib") || !str.endsWith(".so")) ? System.mapLibraryName(str) : str;
    }

    /* renamed from: d */
    public String mo14168d(String str) {
        return str.substring(3, str.length() - 3);
    }
}
