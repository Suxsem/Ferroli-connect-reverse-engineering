package anet.channel.strategy.dispatch;

import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.util.ALog;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Taobao */
public class HttpDispatcher {

    /* renamed from: a */
    private CopyOnWriteArraySet<IDispatchEventListener> f485a;

    /* renamed from: b */
    private C0570a f486b;

    /* renamed from: c */
    private volatile boolean f487c;

    /* renamed from: d */
    private Set<String> f488d;

    /* renamed from: e */
    private Set<String> f489e;

    /* renamed from: f */
    private AtomicBoolean f490f;

    /* compiled from: Taobao */
    public interface IDispatchEventListener {
        void onEvent(DispatchEvent dispatchEvent);
    }

    /* renamed from: anet.channel.strategy.dispatch.HttpDispatcher$a */
    /* compiled from: Taobao */
    private static class C0569a {

        /* renamed from: a */
        static HttpDispatcher f491a = new HttpDispatcher();

        private C0569a() {
        }
    }

    public static HttpDispatcher getInstance() {
        return C0569a.f491a;
    }

    private HttpDispatcher() {
        this.f485a = new CopyOnWriteArraySet<>();
        this.f486b = new C0570a();
        this.f487c = true;
        this.f488d = Collections.newSetFromMap(new ConcurrentHashMap());
        this.f489e = new TreeSet();
        this.f490f = new AtomicBoolean();
        m282a();
    }

    public void sendAmdcRequest(Set<String> set, int i) {
        if (!this.f487c || set == null || set.isEmpty()) {
            ALog.m327e("awcn.HttpDispatcher", "invalid parameter", (String) null, new Object[0]);
            return;
        }
        if (ALog.isPrintLog(2)) {
            ALog.m328i("awcn.HttpDispatcher", "sendAmdcRequest", (String) null, DispatchConstants.HOSTS, set.toString());
        }
        HashMap hashMap = new HashMap();
        hashMap.put(DispatchConstants.HOSTS, set);
        hashMap.put(DispatchConstants.CONFIG_VERSION, String.valueOf(i));
        this.f486b.mo9054a((Map<String, Object>) hashMap);
    }

    public void addListener(IDispatchEventListener iDispatchEventListener) {
        this.f485a.add(iDispatchEventListener);
    }

    public void removeListener(IDispatchEventListener iDispatchEventListener) {
        this.f485a.remove(iDispatchEventListener);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo9044a(DispatchEvent dispatchEvent) {
        Iterator<IDispatchEventListener> it = this.f485a.iterator();
        while (it.hasNext()) {
            try {
                it.next().onEvent(dispatchEvent);
            } catch (Exception unused) {
            }
        }
    }

    public void setEnable(boolean z) {
        this.f487c = z;
    }

    public synchronized void addHosts(List<String> list) {
        if (list != null) {
            this.f489e.addAll(list);
            this.f488d.clear();
        }
    }

    public static void setInitHosts(List<String> list) {
        if (list != null) {
            DispatchConstants.initHostArray = (String[]) list.toArray(new String[0]);
        }
    }

    public synchronized Set<String> getInitHosts() {
        m282a();
        return new HashSet(this.f489e);
    }

    /* renamed from: a */
    private void m282a() {
        if (!this.f490f.get() && GlobalAppRuntimeInfo.getContext() != null && this.f490f.compareAndSet(false, true)) {
            this.f489e.add(DispatchConstants.getAmdcServerDomain());
            if (GlobalAppRuntimeInfo.isTargetProcess()) {
                this.f489e.addAll(Arrays.asList(DispatchConstants.initHostArray));
            }
        }
    }

    public boolean isInitHostsChanged(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean contains = this.f488d.contains(str);
        if (!contains) {
            this.f488d.add(str);
        }
        return !contains;
    }

    public void switchENV() {
        this.f488d.clear();
        this.f489e.clear();
        this.f490f.set(false);
    }
}
