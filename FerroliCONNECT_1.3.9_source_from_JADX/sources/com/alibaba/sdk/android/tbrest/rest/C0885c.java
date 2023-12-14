package com.alibaba.sdk.android.tbrest.rest;

import com.alibaba.sdk.android.tbrest.utils.StringUtils;
import java.util.Arrays;
import java.util.Comparator;

/* renamed from: com.alibaba.sdk.android.tbrest.rest.c */
/* compiled from: RestKeyArraySorter */
public class C0885c {

    /* renamed from: a */
    private static C0885c f1374a;

    /* renamed from: a */
    private final C0887a f1375a = new C0887a();

    /* renamed from: a */
    private final C0888b f1376a = new C0888b();

    private C0885c() {
    }

    /* renamed from: a */
    public static synchronized C0885c m1014a() {
        C0885c cVar;
        synchronized (C0885c.class) {
            if (f1374a == null) {
                f1374a = new C0885c();
            }
            cVar = f1374a;
        }
        return cVar;
    }

    /* renamed from: a */
    public String[] mo10138a(String[] strArr, boolean z) {
        Comparator comparator;
        if (z) {
            comparator = this.f1375a;
        } else {
            comparator = this.f1376a;
        }
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        Arrays.sort(strArr, comparator);
        return strArr;
    }

    /* renamed from: com.alibaba.sdk.android.tbrest.rest.c$b */
    /* compiled from: RestKeyArraySorter */
    private static class C0888b implements Comparator<String> {
        private C0888b() {
        }

        public int compare(String str, String str2) {
            if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2) * -1;
        }
    }

    /* renamed from: com.alibaba.sdk.android.tbrest.rest.c$a */
    /* compiled from: RestKeyArraySorter */
    private static class C0887a implements Comparator<String> {
        private C0887a() {
        }

        public int compare(String str, String str2) {
            if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2);
        }
    }
}
