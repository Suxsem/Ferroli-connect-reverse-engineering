package anet.channel.strategy.dispatch;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.utils.C0592a;
import anet.channel.util.ALog;
import com.contrarywind.timer.MessageHandler;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/* renamed from: anet.channel.strategy.dispatch.a */
/* compiled from: Taobao */
class C0570a {
    public static final String TAG = "awcn.AmdcThreadPoolExecutor";

    /* renamed from: b */
    private static Random f492b = new Random();
    /* access modifiers changed from: private */

    /* renamed from: a */
    public Map<String, Object> f493a;

    C0570a() {
    }

    /* renamed from: a */
    public void mo9054a(Map<String, Object> map) {
        try {
            map.put("Env", GlobalAppRuntimeInfo.getEnv());
            synchronized (this) {
                if (this.f493a == null) {
                    this.f493a = map;
                    int nextInt = f492b.nextInt(3000) + MessageHandler.WHAT_SMOOTH_SCROLL;
                    ALog.m328i(TAG, "merge amdc request", (String) null, "delay", Integer.valueOf(nextInt));
                    C0592a.m315a(new C0571a(), (long) nextInt);
                } else {
                    Set set = (Set) this.f493a.get(DispatchConstants.HOSTS);
                    Set set2 = (Set) map.get(DispatchConstants.HOSTS);
                    if (map.get("Env") != this.f493a.get("Env")) {
                        this.f493a = map;
                    } else if (set.size() + set2.size() <= 40) {
                        set2.addAll(set);
                        this.f493a = map;
                    } else {
                        C0592a.m314a(new C0571a(map));
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: anet.channel.strategy.dispatch.a$a */
    /* compiled from: Taobao */
    private class C0571a implements Runnable {

        /* renamed from: b */
        private Map<String, Object> f495b;

        C0571a(Map<String, Object> map) {
            this.f495b = map;
        }

        C0571a() {
        }

        public void run() {
            Map<String, Object> a;
            try {
                Map<String, Object> map = this.f495b;
                if (map == null) {
                    synchronized (C0570a.class) {
                        a = C0570a.this.f493a;
                        Map unused = C0570a.this.f493a = null;
                    }
                    map = a;
                }
                if (NetworkStatusHelper.isConnected()) {
                    if (GlobalAppRuntimeInfo.getEnv() != map.get("Env")) {
                        ALog.m330w(C0570a.TAG, "task's env changed", (String) null, new Object[0]);
                    } else {
                        C0572b.m293a(C0574d.m296a(map));
                    }
                }
            } catch (Exception e) {
                ALog.m326e(C0570a.TAG, "exec amdc task failed.", (String) null, e, new Object[0]);
            }
        }
    }
}
