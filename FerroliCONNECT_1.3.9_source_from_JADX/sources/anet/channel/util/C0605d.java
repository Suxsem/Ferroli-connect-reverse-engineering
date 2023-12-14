package anet.channel.util;

import anet.channel.statist.NetTypeStat;
import anet.channel.thread.ThreadPoolExecutorFactory;

/* renamed from: anet.channel.util.d */
/* compiled from: Taobao */
final class C0605d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f584a;

    /* renamed from: b */
    final /* synthetic */ NetTypeStat f585b;

    C0605d(String str, NetTypeStat netTypeStat) {
        this.f584a = str;
        this.f585b = netTypeStat;
    }

    public void run() {
        ThreadPoolExecutorFactory.submitPriorityTask(new C0606e(this), ThreadPoolExecutorFactory.Priority.LOW);
    }
}
