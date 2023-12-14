package com.p092ta.p093a;

import android.content.Context;

/* renamed from: com.ta.a.a */
public class C1964a {

    /* renamed from: a */
    private static final C1964a f3147a = new C1964a();

    /* renamed from: a */
    private long f3148a = 0;
    private Context mContext = null;

    private C1964a() {
    }

    /* renamed from: a */
    public static C1964a mo18112a() {
        return f3147a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo18114a(android.content.Context r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            android.content.Context r0 = r1.mContext     // Catch:{ all -> 0x001a }
            if (r0 != 0) goto L_0x0018
            if (r2 != 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            android.content.Context r0 = r2.getApplicationContext()     // Catch:{ all -> 0x001a }
            if (r0 == 0) goto L_0x0016
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x001a }
            r1.mContext = r2     // Catch:{ all -> 0x001a }
            goto L_0x0018
        L_0x0016:
            r1.mContext = r2     // Catch:{ all -> 0x001a }
        L_0x0018:
            monitor-exit(r1)
            return
        L_0x001a:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p092ta.p093a.C1964a.mo18114a(android.content.Context):void");
    }

    public Context getContext() {
        return this.mContext;
    }

    /* renamed from: a */
    public void mo18113a(long j) {
        this.f3148a = j - System.currentTimeMillis();
    }

    /* renamed from: a */
    public long m3330a() {
        return System.currentTimeMillis() + this.f3148a;
    }

    /* renamed from: a */
    public String m3331a() {
        return "" + mo18112a();
    }
}
