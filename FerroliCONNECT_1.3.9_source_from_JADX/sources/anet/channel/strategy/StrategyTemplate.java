package anet.channel.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Taobao */
public class StrategyTemplate {
    Map<String, ConnProtocol> templateMap = new ConcurrentHashMap();

    public static StrategyTemplate getInstance() {
        return C0563a.f474a;
    }

    /* renamed from: anet.channel.strategy.StrategyTemplate$a */
    /* compiled from: Taobao */
    static class C0563a {

        /* renamed from: a */
        static StrategyTemplate f474a = new StrategyTemplate();

        C0563a() {
        }
    }

    public void registerConnProtocol(String str, ConnProtocol connProtocol) {
        if (connProtocol != null) {
            this.templateMap.put(str, connProtocol);
            try {
                IStrategyInstance instance = StrategyCenter.getInstance();
                if (instance instanceof C0578g) {
                    ((C0578g) instance).f504b.f459c.mo9036a(str, connProtocol);
                }
            } catch (Exception unused) {
            }
        }
    }

    public ConnProtocol getConnProtocol(String str) {
        return this.templateMap.get(str);
    }
}
