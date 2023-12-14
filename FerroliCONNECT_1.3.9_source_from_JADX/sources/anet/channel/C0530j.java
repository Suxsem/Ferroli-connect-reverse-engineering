package anet.channel;

import anet.channel.p005b.C0481a;
import anetwork.channel.cache.CacheManager;

/* renamed from: anet.channel.j */
/* compiled from: Taobao */
final class C0530j implements Runnable {
    C0530j() {
    }

    public void run() {
        try {
            C0481a aVar = new C0481a();
            aVar.mo8724a();
            CacheManager.addCache(aVar, new C0531k(this), 1);
        } catch (Exception unused) {
        }
    }
}
