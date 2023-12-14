package com.taobao.accs.net;

import anet.channel.strategy.dispatch.DispatchEvent;
import anet.channel.strategy.dispatch.HttpDispatcher;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import java.util.concurrent.TimeUnit;

/* renamed from: com.taobao.accs.net.h */
/* compiled from: Taobao */
class C2055h implements HttpDispatcher.IDispatchEventListener {

    /* renamed from: a */
    final /* synthetic */ C2054g f3406a;

    C2055h(C2054g gVar) {
        this.f3406a = gVar;
    }

    public void onEvent(DispatchEvent dispatchEvent) {
        ThreadPoolExecutorFactory.schedule(new C2056i(this), 2000, TimeUnit.MILLISECONDS);
    }
}
