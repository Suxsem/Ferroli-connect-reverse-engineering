package anetwork.channel.entity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: anetwork.channel.entity.a */
/* compiled from: Taobao */
public class C0634a {

    /* renamed from: a */
    private static final ExecutorService[] f711a = new ExecutorService[2];
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static AtomicInteger f712b = new AtomicInteger(0);

    static {
        for (int i = 0; i < 2; i++) {
            f711a[i] = Executors.newSingleThreadExecutor(new C0635b());
        }
    }

    /* renamed from: a */
    public static void m414a(int i, Runnable runnable) {
        f711a[Math.abs(i % 2)].submit(runnable);
    }
}
