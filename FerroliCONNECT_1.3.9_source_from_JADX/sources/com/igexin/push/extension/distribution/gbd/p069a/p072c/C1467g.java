package com.igexin.push.extension.distribution.gbd.p069a.p072c;

import android.net.wifi.ScanResult;
import java.util.Comparator;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.c.g */
class C1467g implements Comparator<ScanResult> {

    /* renamed from: a */
    final /* synthetic */ C1466f f2565a;

    C1467g(C1466f fVar) {
        this.f2565a = fVar;
    }

    /* renamed from: a */
    public int compare(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult2.level - scanResult.level;
    }
}
