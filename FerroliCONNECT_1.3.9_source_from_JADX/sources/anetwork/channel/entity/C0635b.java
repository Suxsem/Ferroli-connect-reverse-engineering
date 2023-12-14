package anetwork.channel.entity;

import java.util.concurrent.ThreadFactory;

/* renamed from: anetwork.channel.entity.b */
/* compiled from: Taobao */
final class C0635b implements ThreadFactory {
    C0635b() {
    }

    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, String.format("RepeaterThread:%d", new Object[]{Integer.valueOf(C0634a.f712b.getAndIncrement())}));
    }
}
