package anet.channel.strategy;

/* renamed from: anet.channel.strategy.e */
/* compiled from: Taobao */
class C0576e implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f499a;

    /* renamed from: b */
    final /* synthetic */ StrategyInfoHolder f500b;

    C0576e(StrategyInfoHolder strategyInfoHolder, String str) {
        this.f500b = strategyInfoHolder;
        this.f499a = str;
    }

    public void run() {
        this.f500b.mo9014a(this.f499a, true);
    }
}
