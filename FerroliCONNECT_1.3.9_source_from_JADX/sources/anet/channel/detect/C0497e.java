package anet.channel.detect;

import anet.channel.AwcnConfig;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.IStrategyListener;
import anet.channel.util.ALog;

/* renamed from: anet.channel.detect.e */
/* compiled from: Taobao */
class C0497e implements IStrategyListener {

    /* renamed from: a */
    final /* synthetic */ C0496d f199a;

    C0497e(C0496d dVar) {
        this.f199a = dVar;
    }

    public void onStrategyUpdated(C0583l.C0587d dVar) {
        ALog.m328i("anet.HorseRaceDetector", "onStrategyUpdated", (String) null, new Object[0]);
        if (AwcnConfig.isHorseRaceEnable() && dVar.f539c != null && dVar.f539c.length != 0) {
            synchronized (this.f199a.f197a) {
                for (C0583l.C0586c cVar : dVar.f539c) {
                    this.f199a.f197a.put(cVar.f535a, cVar);
                }
            }
        }
    }
}
