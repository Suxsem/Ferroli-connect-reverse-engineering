package com.igexin.push.extension.distribution.gbd.p086i;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.k */
class C1543k implements ServiceConnection {

    /* renamed from: a */
    private boolean f2955a = false;

    /* renamed from: b */
    private final LinkedBlockingQueue<IBinder> f2956b = new LinkedBlockingQueue<>(1);

    C1543k() {
    }

    /* renamed from: a */
    public IBinder mo15173a() {
        if (!this.f2955a) {
            this.f2955a = true;
            return this.f2956b.take();
        }
        throw new IllegalStateException();
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.f2956b.put(iBinder);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }
}
