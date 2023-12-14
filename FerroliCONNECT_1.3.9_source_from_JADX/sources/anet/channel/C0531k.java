package anet.channel;

import anet.channel.util.HttpConstant;
import anetwork.channel.cache.CachePrediction;
import java.util.Map;

/* renamed from: anet.channel.k */
/* compiled from: Taobao */
class C0531k implements CachePrediction {

    /* renamed from: a */
    final /* synthetic */ C0530j f281a;

    C0531k(C0530j jVar) {
        this.f281a = jVar;
    }

    public boolean handleCache(String str, Map<String, String> map) {
        return "weex".equals(map.get(HttpConstant.F_REFER));
    }
}
