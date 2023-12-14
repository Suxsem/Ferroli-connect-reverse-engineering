package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.utils.C0594c;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Taobao */
class StrategyConfig implements Serializable {
    public static final String NO_RESULT = "No_Result";

    /* renamed from: a */
    private SerialLruCache<String, String> f454a = null;

    /* renamed from: b */
    private Map<String, String> f455b = null;

    /* renamed from: c */
    private transient StrategyInfoHolder f456c = null;

    StrategyConfig() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public StrategyConfig mo9007a() {
        StrategyConfig strategyConfig = new StrategyConfig();
        synchronized (this) {
            strategyConfig.f454a = new SerialLruCache<>(this.f454a, 256);
            strategyConfig.f455b = new ConcurrentHashMap(this.f455b);
            strategyConfig.f456c = this.f456c;
        }
        return strategyConfig;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo9009a(StrategyInfoHolder strategyInfoHolder) {
        this.f456c = strategyInfoHolder;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public void mo9012b() {
        if (this.f454a == null) {
            this.f454a = new SerialLruCache<>(256);
        }
        if (this.f455b == null) {
            this.f455b = new ConcurrentHashMap();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo9010a(C0583l.C0587d dVar) {
        if (dVar.f538b != null) {
            synchronized (this) {
                TreeMap treeMap = null;
                for (C0583l.C0585b bVar : dVar.f538b) {
                    if (bVar.f532j) {
                        this.f454a.remove(bVar.f523a);
                    } else if (bVar.f526d != null) {
                        if (treeMap == null) {
                            treeMap = new TreeMap();
                        }
                        treeMap.put(bVar.f523a, bVar.f526d);
                    } else {
                        if ("http".equalsIgnoreCase(bVar.f525c) || "https".equalsIgnoreCase(bVar.f525c)) {
                            this.f454a.put(bVar.f523a, bVar.f525c);
                        } else {
                            this.f454a.put(bVar.f523a, NO_RESULT);
                        }
                        if (!TextUtils.isEmpty(bVar.f527e)) {
                            this.f455b.put(bVar.f523a, bVar.f527e);
                        } else {
                            this.f455b.remove(bVar.f523a);
                        }
                    }
                }
                if (treeMap != null) {
                    for (Map.Entry entry : treeMap.entrySet()) {
                        String str = (String) entry.getValue();
                        if (this.f454a.containsKey(str)) {
                            this.f454a.put(entry.getKey(), this.f454a.get(str));
                        } else {
                            this.f454a.put(entry.getKey(), NO_RESULT);
                        }
                    }
                }
            }
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.StrategyConfig", "", (String) null, "SchemeMap", this.f454a.toString());
                ALog.m325d("awcn.StrategyConfig", "", (String) null, "UnitMap", this.f455b.toString());
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public String mo9008a(String str) {
        String str2;
        if (TextUtils.isEmpty(str) || !C0594c.m321c(str)) {
            return null;
        }
        synchronized (this) {
            str2 = (String) this.f454a.get(str);
            if (str2 == null) {
                this.f454a.put(str, NO_RESULT);
            }
        }
        if (str2 == null) {
            this.f456c.mo9017d().mo9028a(str, false);
            return str2;
        } else if (NO_RESULT.equals(str2)) {
            return null;
        } else {
            return str2;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public String mo9011b(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this) {
            str2 = this.f455b.get(str);
        }
        return str2;
    }
}
