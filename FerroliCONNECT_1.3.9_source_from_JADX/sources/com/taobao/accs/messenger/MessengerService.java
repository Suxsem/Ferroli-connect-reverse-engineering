package com.taobao.accs.messenger;

import android.app.Service;
import android.os.Messenger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: Taobao */
public abstract class MessengerService extends Service {
    public static final String INTENT = "intent";
    /* access modifiers changed from: private */

    /* renamed from: a */
    public ExecutorService f3350a = Executors.newSingleThreadExecutor();

    /* renamed from: b */
    private Messenger f3351b = new Messenger(new C2042b(this));

    /* renamed from: a */
    static /* synthetic */ void m3574a() {
    }
}
