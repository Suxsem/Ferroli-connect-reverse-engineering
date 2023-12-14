package com.taobao.accs.messenger;

import android.content.Intent;
import android.os.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* renamed from: com.taobao.accs.messenger.e */
/* compiled from: Taobao */
public class C2045e {

    /* renamed from: a */
    private static final String f3363a = (C2045e.class.getName() + ".TRY_COUNT");

    /* renamed from: b */
    private ScheduledExecutorService f3364b = Executors.newSingleThreadScheduledExecutor();

    /* renamed from: c */
    private C2041a f3365c;

    public C2045e(C2041a aVar) {
        this.f3365c = aVar;
    }

    /* renamed from: a */
    public void mo18464a(String str, Intent intent) {
        m3585b(str, intent);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m3585b(String str, Intent intent) {
        C2044d a = this.f3365c.mo18452a(str);
        if (a == null) {
            this.f3365c.mo18453a(str, intent);
            m3586c(str, intent);
            return;
        }
        try {
            a.mo18458a(intent);
        } catch (RemoteException unused) {
            this.f3365c.mo18455b(str, a);
            this.f3365c.mo18453a(str, intent);
            m3586c(str, intent);
        }
    }

    /* renamed from: c */
    private void m3586c(String str, Intent intent) {
        int intExtra = intent.getIntExtra(f3363a, 0);
        if (intExtra <= 10) {
            intent.putExtra(f3363a, intExtra + 1);
            this.f3364b.schedule(new C2046f(this, str, intent), 1000, TimeUnit.MILLISECONDS);
        }
    }
}
