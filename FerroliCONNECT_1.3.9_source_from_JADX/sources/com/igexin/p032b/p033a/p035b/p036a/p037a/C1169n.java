package com.igexin.p032b.p033a.p035b.p036a.p037a;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1175d;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.p038a.C1155c;
import com.igexin.p032b.p033a.p039c.C1179b;
import org.android.spdy.TnetStatusCode;

/* renamed from: com.igexin.b.a.b.a.a.n */
public final class C1169n extends C1152a {

    /* renamed from: L */
    private C1155c f1580L;

    /* renamed from: M */
    private C1175d f1581M;

    /* renamed from: i */
    public C1173b f1582i;

    /* renamed from: j */
    C1171p f1583j;

    public C1169n(C1171p pVar, C1173b bVar, C1175d dVar) {
        super(TnetStatusCode.EASY_REASON_SPDYINIT_ERROR, (String) null, bVar);
        this.f1582i = bVar;
        this.f1581M = dVar;
        this.f1583j = pVar;
    }

    /* renamed from: a */
    public void mo14251a(C1155c cVar) {
        this.f1580L = cVar;
    }

    /* renamed from: b */
    public final int mo14231b() {
        return TnetStatusCode.EASY_REASON_SPDYINIT_ERROR;
    }

    /* renamed from: b_ */
    public void mo14232b_() {
        super.mo14232b_();
        Thread currentThread = Thread.currentThread();
        C1179b.m1354a("GS-W|" + currentThread + " running");
        C1161f a = C1161f.m1252a();
        while (this.f1546h && !currentThread.isInterrupted() && !this.f1543e) {
            try {
                a.f1555a.lock();
                if (a.f1557c.isEmpty() && this.f1546h) {
                    a.f1556b.await();
                }
                C1168m poll = a.f1557c.poll();
                if (poll != null && this.f1546h) {
                    poll.f1609d = this.f1581M;
                    if (!(this.f1581M == null || this.f1583j == null || !this.f1546h)) {
                        this.f1544f = C1157b.NORMAL;
                        this.f1583j.mo14254a((byte[]) this.f1582i.mo14259d((C1176e) null, this.f1581M, poll.f1608c));
                        C1179b.m1354a("GS-W|" + poll.toString() + " --> " + poll.f1608c.getClass().getName() + "-- send success");
                        if (this.f1580L != null && this.f1546h) {
                            this.f1580L.mo14226a(poll);
                        }
                    }
                }
            } catch (Throwable th) {
                try {
                    a.f1555a.unlock();
                } catch (Exception unused) {
                }
                throw th;
            }
            try {
                a.f1555a.unlock();
            } catch (Exception unused2) {
            }
        }
        this.f1543e = true;
        C1179b.m1354a("GS-W|finish ~~~~~~");
    }

    /* renamed from: f */
    public void mo14233f() {
        super.mo14233f();
        C1179b.m1354a("GS-W|wt dispose");
        if (this.f1580L != null) {
            if (this.f1544f != C1157b.EXCEPTION) {
                this.f1580L.mo14224a(this);
            } else if (!TextUtils.isEmpty(this.f1545g)) {
                this.f1580L.mo14227a(new Exception(this.f1545g));
            }
        }
        this.f1580L = null;
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0019 */
    /* renamed from: j */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14252j() {
        /*
            r2 = this;
            r0 = 0
            r2.f1546h = r0
            com.igexin.b.a.b.a.a.b r0 = com.igexin.p032b.p033a.p035b.p036a.p037a.C1157b.INTERRUPT
            r2.f1544f = r0
            com.igexin.b.a.b.a.a.f r0 = com.igexin.p032b.p033a.p035b.p036a.p037a.C1161f.m1252a()
            boolean r1 = r2.f1543e     // Catch:{ Exception -> 0x0019, all -> 0x001f }
            if (r1 != 0) goto L_0x0019
            java.util.concurrent.locks.Lock r1 = r0.f1555a     // Catch:{ Exception -> 0x0019, all -> 0x001f }
            r1.lock()     // Catch:{ Exception -> 0x0019, all -> 0x001f }
            java.util.concurrent.locks.Condition r1 = r0.f1556b     // Catch:{ Exception -> 0x0019, all -> 0x001f }
            r1.signalAll()     // Catch:{ Exception -> 0x0019, all -> 0x001f }
        L_0x0019:
            java.util.concurrent.locks.Lock r0 = r0.f1555a     // Catch:{ Exception -> 0x0026 }
            r0.unlock()     // Catch:{ Exception -> 0x0026 }
            goto L_0x0026
        L_0x001f:
            r1 = move-exception
            java.util.concurrent.locks.Lock r0 = r0.f1555a     // Catch:{ Exception -> 0x0025 }
            r0.unlock()     // Catch:{ Exception -> 0x0025 }
        L_0x0025:
            throw r1
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p035b.p036a.p037a.C1169n.mo14252j():void");
    }
}
