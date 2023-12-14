package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.strategy.C0566c;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.dispatch.DispatchEvent;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.utils.C0592a;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import anet.channel.util.StringUtils;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.igexin.sdk.GTIntentService;
import com.taobao.accs.common.Constants;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONObject;

/* renamed from: anet.channel.strategy.g */
/* compiled from: Taobao */
class C0578g implements IStrategyInstance, HttpDispatcher.IDispatchEventListener {

    /* renamed from: a */
    boolean f503a = false;

    /* renamed from: b */
    StrategyInfoHolder f504b = null;

    /* renamed from: c */
    long f505c = 0;

    /* renamed from: d */
    CopyOnWriteArraySet<IStrategyListener> f506d = new CopyOnWriteArraySet<>();

    /* renamed from: e */
    private IStrategyFilter f507e = new C0579h(this);

    C0578g() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0040, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void initialize(android.content.Context r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.f503a     // Catch:{ all -> 0x0041 }
            if (r0 != 0) goto L_0x003f
            if (r6 != 0) goto L_0x0008
            goto L_0x003f
        L_0x0008:
            r0 = 0
            r1 = 0
            java.lang.String r2 = "awcn.StrategyCenter"
            java.lang.String r3 = "StrategyCenter initialize started."
            java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0033 }
            anet.channel.util.ALog.m328i(r2, r3, r1, r4)     // Catch:{ Exception -> 0x0033 }
            anet.channel.strategy.dispatch.AmdcRuntimeInfo.setContext(r6)     // Catch:{ Exception -> 0x0033 }
            anet.channel.strategy.C0589m.m306a((android.content.Context) r6)     // Catch:{ Exception -> 0x0033 }
            anet.channel.strategy.dispatch.HttpDispatcher r6 = anet.channel.strategy.dispatch.HttpDispatcher.getInstance()     // Catch:{ Exception -> 0x0033 }
            r6.addListener(r5)     // Catch:{ Exception -> 0x0033 }
            anet.channel.strategy.StrategyInfoHolder r6 = anet.channel.strategy.StrategyInfoHolder.m251a()     // Catch:{ Exception -> 0x0033 }
            r5.f504b = r6     // Catch:{ Exception -> 0x0033 }
            r6 = 1
            r5.f503a = r6     // Catch:{ Exception -> 0x0033 }
            java.lang.String r6 = "awcn.StrategyCenter"
            java.lang.String r2 = "StrategyCenter initialize finished."
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0033 }
            anet.channel.util.ALog.m328i(r6, r2, r1, r3)     // Catch:{ Exception -> 0x0033 }
            goto L_0x003d
        L_0x0033:
            r6 = move-exception
            java.lang.String r2 = "awcn.StrategyCenter"
            java.lang.String r3 = "StrategyCenter initialize failed."
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0041 }
            anet.channel.util.ALog.m326e(r2, r3, r1, r6, r0)     // Catch:{ all -> 0x0041 }
        L_0x003d:
            monitor-exit(r5)
            return
        L_0x003f:
            monitor-exit(r5)
            return
        L_0x0041:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.C0578g.initialize(android.content.Context):void");
    }

    public synchronized void switchEnv() {
        C0589m.m305a();
        HttpDispatcher.getInstance().switchENV();
        if (this.f504b != null) {
            this.f504b.mo9015b();
            this.f504b = StrategyInfoHolder.m251a();
        }
    }

    @Deprecated
    public String getSchemeByHost(String str) {
        return getSchemeByHost(str, (String) null);
    }

    public String getSchemeByHost(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (m298a()) {
            return str2;
        }
        String a = this.f504b.f458b.mo9008a(str);
        if (a != null || TextUtils.isEmpty(str2)) {
            str2 = a;
        }
        if (str2 == null && (str2 = C0566c.C0567a.f482a.mo9040a(str)) == null) {
            str2 = "http";
        }
        ALog.m325d("awcn.StrategyCenter", "getSchemeByHost", (String) null, Constants.KEY_HOST, str, "scheme", str2);
        return str2;
    }

    public String getCNameByHost(String str) {
        if (m298a() || TextUtils.isEmpty(str)) {
            return null;
        }
        return this.f504b.mo9017d().getCnameByHost(str);
    }

    public String getFormalizeUrl(String str) {
        HttpUrl parse = HttpUrl.parse(str);
        if (parse == null) {
            ALog.m327e("awcn.StrategyCenter", "url is invalid.", (String) null, "URL", str);
            return null;
        }
        String urlString = parse.urlString();
        try {
            String schemeByHost = getSchemeByHost(parse.host(), parse.scheme());
            if (!schemeByHost.equalsIgnoreCase(parse.scheme())) {
                urlString = StringUtils.concatString(schemeByHost, ":", str.substring(str.indexOf("//")));
            }
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.StrategyCenter", "", (String) null, "raw", StringUtils.simplifyString(str, 128), "ret", StringUtils.simplifyString(urlString, 128));
            }
        } catch (Exception e) {
            ALog.m326e("awcn.StrategyCenter", "getFormalizeUrl failed", (String) null, e, "raw", str);
        }
        return urlString;
    }

    public List<IConnStrategy> getConnStrategyListByHost(String str) {
        return getConnStrategyListByHost(str, this.f507e);
    }

    public List<IConnStrategy> getConnStrategyListByHost(String str, IStrategyFilter iStrategyFilter) {
        if (TextUtils.isEmpty(str) || m298a()) {
            return Collections.EMPTY_LIST;
        }
        String cnameByHost = this.f504b.mo9017d().getCnameByHost(str);
        if (!TextUtils.isEmpty(cnameByHost)) {
            str = cnameByHost;
        }
        List<IConnStrategy> queryByHost = this.f504b.mo9017d().queryByHost(str);
        if (queryByHost.isEmpty()) {
            queryByHost = this.f504b.f459c.mo9035a(str);
        }
        if (queryByHost.isEmpty() || iStrategyFilter == null) {
            ALog.m325d("getConnStrategyListByHost", (String) null, Constants.KEY_HOST, str, "result", queryByHost);
            return queryByHost;
        }
        boolean z = !AwcnConfig.isIpv6Enable() || (AwcnConfig.isIpv6BlackListEnable() && this.f504b.mo9017d().mo9029a(str, AwcnConfig.getIpv6BlackListTtl()));
        ListIterator<IConnStrategy> listIterator = queryByHost.listIterator();
        while (listIterator.hasNext()) {
            IConnStrategy next = listIterator.next();
            if (!iStrategyFilter.accept(next)) {
                listIterator.remove();
            } else if (z && C0594c.m320b(next.getIp())) {
                listIterator.remove();
            }
        }
        if (ALog.isPrintLog(1)) {
            ALog.m325d("getConnStrategyListByHost", (String) null, Constants.KEY_HOST, str, "result", queryByHost);
        }
        return queryByHost;
    }

    public void forceRefreshStrategy(String str) {
        if (!m298a() && !TextUtils.isEmpty(str)) {
            ALog.m328i("awcn.StrategyCenter", "force refresh strategy", (String) null, Constants.KEY_HOST, str);
            this.f504b.mo9017d().mo9028a(str, true);
        }
    }

    public void registerListener(IStrategyListener iStrategyListener) {
        ALog.m327e("awcn.StrategyCenter", "registerListener", (String) null, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, this.f506d);
        if (iStrategyListener != null) {
            this.f506d.add(iStrategyListener);
        }
    }

    public void unregisterListener(IStrategyListener iStrategyListener) {
        ALog.m327e("awcn.StrategyCenter", "unregisterListener", (String) null, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, this.f506d);
        this.f506d.remove(iStrategyListener);
    }

    public String getUnitByHost(String str) {
        if (m298a()) {
            return null;
        }
        return this.f504b.f458b.mo9011b(str);
    }

    public String getClientIp() {
        if (m298a()) {
            return "";
        }
        return this.f504b.mo9017d().f469b;
    }

    public void notifyConnEvent(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if (!m298a() && iConnStrategy != null && (iConnStrategy instanceof IPConnStrategy)) {
            IPConnStrategy iPConnStrategy = (IPConnStrategy) iConnStrategy;
            if (iPConnStrategy.f443b == 1) {
                this.f504b.f459c.mo9037a(str, iConnStrategy, connEvent);
            } else if (iPConnStrategy.f443b == 0) {
                this.f504b.mo9017d().mo9027a(str, iConnStrategy, connEvent);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public boolean m298a() {
        if (this.f504b != null) {
            return false;
        }
        ALog.m330w("StrategyCenter not initialized", (String) null, "isInitialized", Boolean.valueOf(this.f503a));
        return true;
    }

    public void onEvent(DispatchEvent dispatchEvent) {
        if (dispatchEvent.eventType == 1 && this.f504b != null) {
            ALog.m325d("awcn.StrategyCenter", "receive amdc event", (String) null, new Object[0]);
            C0583l.C0587d a = C0583l.m302a((JSONObject) dispatchEvent.extraObject);
            if (a != null) {
                this.f504b.mo9013a(a);
                saveData();
                Iterator<IStrategyListener> it = this.f506d.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().onStrategyUpdated(a);
                    } catch (Exception e) {
                        ALog.m326e("awcn.StrategyCenter", "onStrategyUpdated failed", (String) null, e, new Object[0]);
                    }
                }
            }
        }
    }

    public synchronized void saveData() {
        ALog.m328i("awcn.StrategyCenter", "saveData", (String) null, new Object[0]);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f505c > GTIntentService.WAIT_TIME) {
            this.f505c = currentTimeMillis;
            C0592a.m315a(new C0580i(this), 500);
        }
    }
}
