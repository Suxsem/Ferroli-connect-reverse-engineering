package anetwork.channel.unified;

import anet.channel.thread.ThreadPoolExecutorFactory;

/* renamed from: anetwork.channel.unified.f */
/* compiled from: Taobao */
class C0648f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C0646e f770a;

    C0648f(C0646e eVar) {
        this.f770a = eVar;
    }

    public void run() {
        ThreadPoolExecutorFactory.submitPriorityTask(this.f770a, ThreadPoolExecutorFactory.Priority.HIGH);
    }
}
