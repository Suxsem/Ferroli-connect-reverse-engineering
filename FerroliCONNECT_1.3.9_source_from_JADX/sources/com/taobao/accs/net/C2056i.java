package com.taobao.accs.net;

import anet.channel.strategy.StrategyCenter;

/* renamed from: com.taobao.accs.net.i */
/* compiled from: Taobao */
class C2056i implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C2055h f3407a;

    C2056i(C2055h hVar) {
        this.f3407a = hVar;
    }

    public void run() {
        StrategyCenter.getInstance().saveData();
    }
}
