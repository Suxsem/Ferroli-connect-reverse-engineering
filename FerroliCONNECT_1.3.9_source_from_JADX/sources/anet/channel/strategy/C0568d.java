package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.statist.StrategyStatObject;
import anet.channel.util.ALog;
import java.io.File;

/* renamed from: anet.channel.strategy.d */
/* compiled from: Taobao */
class C0568d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f483a;

    /* renamed from: b */
    final /* synthetic */ StrategyInfoHolder f484b;

    C0568d(StrategyInfoHolder strategyInfoHolder, String str) {
        this.f484b = strategyInfoHolder;
        this.f483a = str;
    }

    public void run() {
        try {
            ALog.m328i("awcn.StrategyInfoHolder", "start loading strategy files", (String) null, new Object[0]);
            long currentTimeMillis = System.currentTimeMillis();
            if (AwcnConfig.isAsyncLoadStrategyEnable()) {
                ALog.m328i("awcn.StrategyInfoHolder", "load strategy async", (String) null, new Object[0]);
                if (!TextUtils.isEmpty(this.f483a)) {
                    this.f484b.mo9014a(this.f483a, true);
                }
                StrategyConfig strategyConfig = (StrategyConfig) C0589m.m304a("StrategyConfig", (StrategyStatObject) null);
                if (strategyConfig != null) {
                    strategyConfig.mo9012b();
                    strategyConfig.mo9009a(this.f484b);
                    synchronized (this.f484b) {
                        this.f484b.f458b = strategyConfig;
                    }
                }
            }
            File[] b = C0589m.m309b();
            if (b != null) {
                int i = 0;
                for (int i2 = 0; i2 < b.length && i < 2; i2++) {
                    File file = b[i2];
                    if (!file.isDirectory()) {
                        String name = file.getName();
                        if (!name.equals(this.f483a) && !name.startsWith("StrategyConfig")) {
                            this.f484b.mo9014a(name, false);
                            i++;
                        }
                    }
                }
                ALog.m328i("awcn.StrategyInfoHolder", "end loading strategy files", (String) null, "total cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
            }
        } catch (Exception unused) {
        }
    }
}
