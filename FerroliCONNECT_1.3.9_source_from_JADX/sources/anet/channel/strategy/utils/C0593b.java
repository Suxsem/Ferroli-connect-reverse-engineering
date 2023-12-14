package anet.channel.strategy.utils;

import anet.channel.util.ALog;
import com.p107tb.appyunsdk.Constant;
import java.util.concurrent.ThreadFactory;

/* renamed from: anet.channel.strategy.utils.b */
/* compiled from: Taobao */
final class C0593b implements ThreadFactory {
    C0593b() {
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "AMDC" + C0592a.f552a.incrementAndGet());
        ALog.m328i(C0570a.TAG, "thread created!", (String) null, Constant.NAME, thread.getName());
        thread.setPriority(5);
        return thread;
    }
}
