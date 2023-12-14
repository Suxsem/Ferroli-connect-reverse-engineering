package com.alibaba.sdk.android.emas;

import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.alibaba.sdk.android.emas.c */
/* compiled from: CacheManager */
public class C0706c implements Cache<C0712g> {

    /* renamed from: a */
    private final int f893a;

    /* renamed from: a */
    private List<C0712g> f894a;

    /* renamed from: b */
    private final int f895b;

    /* renamed from: c */
    private int f896c = 0;
    private final C0716j mSendManager;

    /* renamed from: a */
    public C0712g get() {
        return null;
    }

    /* renamed from: a */
    public boolean m621a(C0712g gVar) {
        return false;
    }

    public void clear() {
    }

    public C0706c(C0716j jVar, int i, int i2) {
        this.f893a = i;
        this.f895b = i2;
        this.mSendManager = jVar;
    }

    /* renamed from: a */
    public synchronized void remove(C0712g gVar) {
        if (this.f894a == null) {
            this.f894a = new ArrayList();
        }
        this.f894a.add(gVar);
        this.f896c += gVar.length();
        if (this.f894a.size() >= this.f893a || this.f896c >= this.f895b) {
            LogUtil.m1029d("CacheManager satisfy limit. immediately send. size: " + this.f894a.size() + ", current capacity: " + this.f896c);
            m618b();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void flush() {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.List<com.alibaba.sdk.android.emas.g> r0 = r1.f894a     // Catch:{ all -> 0x001a }
            if (r0 == 0) goto L_0x0018
            java.util.List<com.alibaba.sdk.android.emas.g> r0 = r1.f894a     // Catch:{ all -> 0x001a }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x001a }
            if (r0 == 0) goto L_0x000e
            goto L_0x0018
        L_0x000e:
            java.lang.String r0 = "CacheManager flush. immediately send."
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r0)     // Catch:{ all -> 0x001a }
            r1.m618b()     // Catch:{ all -> 0x001a }
            monitor-exit(r1)
            return
        L_0x0018:
            monitor-exit(r1)
            return
        L_0x001a:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.emas.C0706c.flush():void");
    }

    /* renamed from: b */
    private void m618b() {
        this.mSendManager.mo9659a(this.f894a);
        this.f894a = null;
        this.f896c = 0;
    }
}
