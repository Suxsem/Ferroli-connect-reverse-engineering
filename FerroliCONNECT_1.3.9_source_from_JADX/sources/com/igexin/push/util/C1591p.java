package com.igexin.push.util;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.igexin.push.util.p */
final class C1591p implements ServiceConnection {

    /* renamed from: a */
    boolean f3037a;

    /* renamed from: b */
    private final LinkedBlockingQueue<IBinder> f3038b;

    private C1591p() {
        this.f3037a = false;
        this.f3038b = new LinkedBlockingQueue<>(1);
    }

    /* renamed from: a */
    public IBinder mo15219a() {
        if (!this.f3037a) {
            this.f3037a = true;
            return this.f3038b.take();
        }
        throw new IllegalStateException();
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.f3038b.put(iBinder);
        } catch (Exception unused) {
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }
}
