package anet.channel.strategy;

import anet.channel.strategy.C0583l;
import anet.channel.strategy.utils.C0594c;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* compiled from: Taobao */
class StrategyList implements Serializable {

    /* renamed from: a */
    private List<IPConnStrategy> f463a = new ArrayList();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public Map<Integer, ConnHistoryItem> f464b = new SerialLruCache(40);

    /* renamed from: c */
    private boolean f465c = false;

    /* renamed from: d */
    private transient Comparator<IPConnStrategy> f466d = null;

    /* compiled from: Taobao */
    private interface Predicate<T> {
        boolean apply(T t);
    }

    public StrategyList() {
    }

    StrategyList(List<IPConnStrategy> list) {
        this.f463a = list;
    }

    public void checkInit() {
        if (this.f463a == null) {
            this.f463a = new ArrayList();
        }
        if (this.f464b == null) {
            this.f464b = new SerialLruCache(40);
        }
        Iterator<Map.Entry<Integer, ConnHistoryItem>> it = this.f464b.entrySet().iterator();
        while (it.hasNext()) {
            if (((ConnHistoryItem) it.next().getValue()).mo8973d()) {
                it.remove();
            }
        }
        for (IPConnStrategy next : this.f463a) {
            if (!this.f464b.containsKey(Integer.valueOf(next.getUniqueId()))) {
                this.f464b.put(Integer.valueOf(next.getUniqueId()), new ConnHistoryItem());
            }
        }
        Collections.sort(this.f463a, m262a());
    }

    public String toString() {
        return new ArrayList(this.f463a).toString();
    }

    public List<IConnStrategy> getStrategyList() {
        if (this.f463a.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        LinkedList linkedList = null;
        for (IPConnStrategy next : this.f463a) {
            ConnHistoryItem connHistoryItem = this.f464b.get(Integer.valueOf(next.getUniqueId()));
            if (connHistoryItem == null || !connHistoryItem.mo8972c()) {
                if (linkedList == null) {
                    linkedList = new LinkedList();
                }
                linkedList.add(next);
            } else {
                ALog.m328i("awcn.StrategyList", "strategy ban!", (String) null, "strategy", next);
            }
        }
        return linkedList == null ? Collections.EMPTY_LIST : linkedList;
    }

    public void update(C0583l.C0585b bVar) {
        for (IPConnStrategy iPConnStrategy : this.f463a) {
            iPConnStrategy.f444c = true;
        }
        for (int i = 0; i < bVar.f530h.length; i++) {
            for (String a : bVar.f528f) {
                m264a(a, 1, bVar.f530h[i]);
            }
            if (bVar.f529g != null) {
                this.f465c = true;
                for (String a2 : bVar.f529g) {
                    m264a(a2, 0, bVar.f530h[i]);
                }
            } else {
                this.f465c = false;
            }
        }
        if (bVar.f531i != null) {
            for (C0583l.C0588e eVar : bVar.f531i) {
                m264a(eVar.f545a, C0594c.m321c(eVar.f545a) ? -1 : 1, eVar.f546b);
            }
        }
        ListIterator<IPConnStrategy> listIterator = this.f463a.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().f444c) {
                listIterator.remove();
            }
        }
        Collections.sort(this.f463a, m262a());
    }

    /* renamed from: a */
    private void m264a(String str, int i, C0583l.C0584a aVar) {
        int a = m261a(this.f463a, new C0581j(this, aVar, str, ConnProtocol.valueOf(aVar)));
        if (a != -1) {
            IPConnStrategy iPConnStrategy = this.f463a.get(a);
            iPConnStrategy.cto = aVar.f517c;
            iPConnStrategy.rto = aVar.f518d;
            iPConnStrategy.heartbeat = aVar.f520f;
            iPConnStrategy.f442a = i;
            iPConnStrategy.f443b = 0;
            iPConnStrategy.f444c = false;
            return;
        }
        IPConnStrategy a2 = IPConnStrategy.m244a(str, aVar);
        if (a2 != null) {
            a2.f442a = i;
            a2.f443b = 0;
            if (!this.f464b.containsKey(Integer.valueOf(a2.getUniqueId()))) {
                this.f464b.put(Integer.valueOf(a2.getUniqueId()), new ConnHistoryItem());
            }
            this.f463a.add(a2);
        }
    }

    public boolean shouldRefresh() {
        boolean z = true;
        boolean z2 = true;
        for (IPConnStrategy next : this.f463a) {
            if (!this.f464b.get(Integer.valueOf(next.getUniqueId())).mo8971b()) {
                if (next.f442a == 0) {
                    z = false;
                }
                z2 = false;
            }
        }
        if ((!this.f465c || !z) && !z2) {
            return false;
        }
        return true;
    }

    public void notifyConnEvent(IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if ((iConnStrategy instanceof IPConnStrategy) && this.f463a.indexOf(iConnStrategy) != -1) {
            this.f464b.get(Integer.valueOf(((IPConnStrategy) iConnStrategy).getUniqueId())).mo8970a(connEvent.isSuccess);
            Collections.sort(this.f463a, this.f466d);
        }
    }

    /* renamed from: a */
    private Comparator m262a() {
        if (this.f466d == null) {
            this.f466d = new C0582k(this);
        }
        return this.f466d;
    }

    /* renamed from: a */
    private static <T> int m261a(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null) {
            return -1;
        }
        int i = 0;
        Iterator<T> it = collection.iterator();
        while (it.hasNext() && !predicate.apply(it.next())) {
            i++;
        }
        if (i == collection.size()) {
            return -1;
        }
        return i;
    }
}
