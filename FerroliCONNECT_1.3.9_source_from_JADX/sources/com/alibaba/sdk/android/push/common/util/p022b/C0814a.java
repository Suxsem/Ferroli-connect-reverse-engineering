package com.alibaba.sdk.android.push.common.util.p022b;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.push.common.util.b.a */
public class C0814a {

    /* renamed from: com.alibaba.sdk.android.push.common.util.b.a$a */
    public enum C0815a {
        UNKNOWN(1),
        WIFI(2),
        G2(3),
        G3(4),
        G4(5);
        

        /* renamed from: f */
        private static Map<Integer, C0815a> f1157f;

        /* renamed from: g */
        private int f1159g;

        static {
            int i;
            f1157f = new HashMap();
            for (C0815a aVar : values()) {
                f1157f.put(Integer.valueOf(aVar.f1159g), aVar);
            }
        }

        private C0815a(int i) {
            this.f1159g = i;
        }
    }
}
