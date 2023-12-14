package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.entity.ConnType;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.strategy.utils.C0592a;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: anet.channel.strategy.a */
/* compiled from: Taobao */
class C0564a {

    /* renamed from: a */
    final ConcurrentHashMap<String, List<IPConnStrategy>> f475a = new ConcurrentHashMap<>();

    /* renamed from: b */
    final HashMap<String, Object> f476b = new HashMap<>();

    C0564a() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public List mo9035a(String str) {
        Object obj;
        if (TextUtils.isEmpty(str) || !C0594c.m321c(str) || DispatchConstants.getAmdcServerDomain().equalsIgnoreCase(str)) {
            return Collections.EMPTY_LIST;
        }
        if (ALog.isPrintLog(1)) {
            ALog.m325d("awcn.LocalDnsStrategyTable", "try resolve ip with local dns", (String) null, Constants.KEY_HOST, str);
        }
        List list = Collections.EMPTY_LIST;
        if (!this.f475a.containsKey(str)) {
            synchronized (this.f476b) {
                if (!this.f476b.containsKey(str)) {
                    obj = new Object();
                    this.f476b.put(str, obj);
                    m274a(str, obj);
                } else {
                    obj = this.f476b.get(str);
                }
            }
            if (obj != null) {
                try {
                    synchronized (obj) {
                        obj.wait(500);
                    }
                } catch (InterruptedException unused) {
                }
            }
        }
        List list2 = this.f475a.get(str);
        if (!(list2 == null || list2 == Collections.EMPTY_LIST)) {
            list = new ArrayList(list2);
        }
        ALog.m328i("awcn.LocalDnsStrategyTable", "get local strategy", (String) null, "strategyList", list2);
        return list;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo9036a(String str, ConnProtocol connProtocol) {
        List<IPConnStrategy> list = this.f475a.get(str);
        if (list != null && !list.isEmpty()) {
            for (IPConnStrategy protocol : list) {
                if (protocol.getProtocol().equals(connProtocol)) {
                    return;
                }
            }
            list.add(IPConnStrategy.m243a(((IPConnStrategy) list.get(0)).getIp(), !mo9038a(connProtocol) ? 80 : Constants.PORT, connProtocol, 0, 0, 1, 45000));
            ALog.m328i("awcn.LocalDnsStrategyTable", "setProtocolForHost", (String) null, "strategyList", list);
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo9037a(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        List list;
        if (!connEvent.isSuccess && !TextUtils.isEmpty(str) && !connEvent.isAccs && (list = this.f475a.get(str)) != null && list != Collections.EMPTY_LIST) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (it.next() == iConnStrategy) {
                    it.remove();
                }
            }
            if (list.isEmpty()) {
                this.f475a.put(str, Collections.EMPTY_LIST);
            }
        }
    }

    /* renamed from: a */
    private void m274a(String str, Object obj) {
        C0592a.m314a(new C0565b(this, str, obj));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public boolean mo9038a(ConnProtocol connProtocol) {
        return connProtocol.protocol.equalsIgnoreCase("https") || connProtocol.protocol.equalsIgnoreCase(ConnType.H2S) || !TextUtils.isEmpty(connProtocol.publicKey);
    }
}
