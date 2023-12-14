package com.alibaba.sdk.android.push.p018b;

import com.alibaba.sdk.android.push.PushControlService;
import com.alibaba.sdk.android.push.p027g.C0827a;

/* renamed from: com.alibaba.sdk.android.push.b.e */
public class C0796e implements PushControlService {

    /* renamed from: com.alibaba.sdk.android.push.b.e$a */
    private static class C0798a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C0796e f1062a = new C0796e();
    }

    private C0796e() {
    }

    /* renamed from: a */
    public static C0796e m756a() {
        return C0798a.f1062a;
    }

    public void disconnect() {
        C0827a.m825a().mo9911f();
    }

    public boolean isConnected() {
        return C0827a.m825a().mo9908c();
    }

    public void reconnect() {
        C0827a.m825a().mo9909d();
    }

    public void reset() {
        C0827a.m825a().mo9910e();
    }

    public void setConnectionChangeListener(PushControlService.ConnectionChangeListener connectionChangeListener) {
        C0827a.m825a().mo9905a(connectionChangeListener);
    }
}
