package anet.channel.monitor;

import anet.channel.util.ALog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: anet.channel.monitor.a */
/* compiled from: Taobao */
public class C0532a {

    /* renamed from: a */
    private static volatile C0532a f284a;

    /* renamed from: b */
    private Map<INetworkQualityChangeListener, C0538f> f285b = new ConcurrentHashMap();

    /* renamed from: c */
    private C0538f f286c = new C0538f();

    private C0532a() {
    }

    /* renamed from: a */
    public static C0532a m133a() {
        if (f284a == null) {
            synchronized (C0532a.class) {
                if (f284a == null) {
                    f284a = new C0532a();
                }
            }
        }
        return f284a;
    }

    /* renamed from: a */
    public void mo8841a(INetworkQualityChangeListener iNetworkQualityChangeListener, C0538f fVar) {
        if (iNetworkQualityChangeListener == null) {
            ALog.m327e("BandWidthListenerHelp", "listener is null", (String) null, new Object[0]);
        } else if (fVar == null) {
            this.f286c.f319b = System.currentTimeMillis();
            this.f285b.put(iNetworkQualityChangeListener, this.f286c);
        } else {
            fVar.f319b = System.currentTimeMillis();
            this.f285b.put(iNetworkQualityChangeListener, fVar);
        }
    }

    /* renamed from: a */
    public void mo8840a(INetworkQualityChangeListener iNetworkQualityChangeListener) {
        this.f285b.remove(iNetworkQualityChangeListener);
    }

    /* renamed from: a */
    public void mo8839a(double d) {
        boolean a;
        for (Map.Entry next : this.f285b.entrySet()) {
            INetworkQualityChangeListener iNetworkQualityChangeListener = (INetworkQualityChangeListener) next.getKey();
            C0538f fVar = (C0538f) next.getValue();
            if (!(iNetworkQualityChangeListener == null || fVar == null || fVar.mo8852b() || fVar.f318a == (a = fVar.mo8851a(d)))) {
                fVar.f318a = a;
                iNetworkQualityChangeListener.onNetworkQualityChanged(a ? NetworkSpeed.Slow : NetworkSpeed.Fast);
            }
        }
    }
}
