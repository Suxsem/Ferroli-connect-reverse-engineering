package com.alibaba.sdk.android.push.p026f;

import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.alibaba.sdk.android.push.f.b */
public class C0822b {

    /* renamed from: c */
    public static final AmsLogger f1173c = AmsLogger.getLogger("MPS:SyncTool");

    /* renamed from: a */
    Lock f1174a = new ReentrantLock();

    /* renamed from: b */
    Condition f1175b = this.f1174a.newCondition();

    /* renamed from: a */
    public void mo9902a() {
        this.f1174a.lock();
        try {
            this.f1175b.signal();
        } finally {
            this.f1174a.unlock();
        }
    }

    /* renamed from: a */
    public void mo9903a(int i) {
        this.f1174a.lock();
        try {
            this.f1175b.await((long) i, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            f1173c.mo9542e("await error:", e);
        } catch (Throwable th) {
            this.f1174a.unlock();
            throw th;
        }
        this.f1174a.unlock();
    }
}
