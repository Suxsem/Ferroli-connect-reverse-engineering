package com.alibaba.sdk.android.push.p027g;

import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.alibaba.sdk.android.push.g.f */
public class C0840f {

    /* renamed from: a */
    private ConcurrentHashMap<Integer, C0841a> f1209a = new ConcurrentHashMap<>();

    /* renamed from: com.alibaba.sdk.android.push.g.f$a */
    class C0841a {

        /* renamed from: b */
        private String f1211b;

        /* renamed from: c */
        private String f1212c;

        /* renamed from: d */
        private long f1213d;

        public C0841a(String str, String str2, long j) {
            this.f1211b = str;
            this.f1212c = str2;
            this.f1213d = j;
        }

        /* renamed from: a */
        public String mo9929a() {
            return this.f1212c;
        }

        /* renamed from: b */
        public long mo9930b() {
            return this.f1213d;
        }
    }

    /* renamed from: a */
    private boolean m856a(long j, long j2) {
        return j2 - j >= 5000;
    }

    /* renamed from: a */
    public C0841a mo9927a(int i) {
        C0841a aVar;
        ConcurrentHashMap<Integer, C0841a> concurrentHashMap = this.f1209a;
        if (concurrentHashMap == null || (aVar = concurrentHashMap.get(Integer.valueOf(i))) == null || m856a(aVar.mo9930b(), System.currentTimeMillis())) {
            return null;
        }
        return aVar;
    }

    /* renamed from: a */
    public void mo9928a(int i, String str) {
        C0841a aVar;
        if (this.f1209a != null) {
            long currentTimeMillis = System.currentTimeMillis();
            int i2 = 1;
            if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    i2 = 3;
                    if (i != 3) {
                        i2 = 4;
                        if (i == 4) {
                            aVar = new C0841a(String.valueOf(4), str, currentTimeMillis);
                        } else {
                            return;
                        }
                    } else {
                        aVar = new C0841a(String.valueOf(3), str, currentTimeMillis);
                    }
                } else {
                    aVar = new C0841a(String.valueOf(2), str, currentTimeMillis);
                }
            } else {
                aVar = new C0841a(String.valueOf(1), str, currentTimeMillis);
            }
            this.f1209a.put(Integer.valueOf(i2), aVar);
        }
    }
}
