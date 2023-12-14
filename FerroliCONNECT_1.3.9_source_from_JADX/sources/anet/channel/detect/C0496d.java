package anet.channel.detect;

import anet.channel.AwcnConfig;
import anet.channel.RequestCb;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.entity.ConnType;
import anet.channel.request.Request;
import anet.channel.session.C0549b;
import anet.channel.statist.HorseRaceStat;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import anet.channel.util.C0611j;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import java.io.IOException;
import java.net.Socket;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.netutil.PingResponse;
import org.android.netutil.PingTask;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;

/* renamed from: anet.channel.detect.d */
/* compiled from: Taobao */
class C0496d {

    /* renamed from: a */
    TreeMap<String, C0583l.C0586c> f197a = new TreeMap<>();

    /* renamed from: b */
    private AtomicInteger f198b = new AtomicInteger(1);

    C0496d() {
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r3 != null) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        m83a(r3.getValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
        anet.channel.util.ALog.m326e("anet.HorseRaceDetector", "start hr task failed", (java.lang.String) null, r1, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo8774a() {
        /*
            r6 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            java.lang.String r3 = "anet.HorseRaceDetector"
            java.lang.String r4 = "network detect thread start"
            anet.channel.util.ALog.m327e(r3, r4, r2, r1)
        L_0x000b:
            java.util.TreeMap<java.lang.String, anet.channel.strategy.l$c> r1 = r6.f197a
            monitor-enter(r1)
            boolean r3 = anet.channel.AwcnConfig.isHorseRaceEnable()     // Catch:{ all -> 0x003a }
            if (r3 != 0) goto L_0x001b
            java.util.TreeMap<java.lang.String, anet.channel.strategy.l$c> r0 = r6.f197a     // Catch:{ all -> 0x003a }
            r0.clear()     // Catch:{ all -> 0x003a }
            monitor-exit(r1)     // Catch:{ all -> 0x003a }
            goto L_0x0024
        L_0x001b:
            java.util.TreeMap<java.lang.String, anet.channel.strategy.l$c> r3 = r6.f197a     // Catch:{ all -> 0x003a }
            java.util.Map$Entry r3 = r3.pollFirstEntry()     // Catch:{ all -> 0x003a }
            monitor-exit(r1)     // Catch:{ all -> 0x003a }
            if (r3 != 0) goto L_0x0025
        L_0x0024:
            return
        L_0x0025:
            java.lang.Object r1 = r3.getValue()     // Catch:{ Exception -> 0x002f }
            anet.channel.strategy.l$c r1 = (anet.channel.strategy.C0583l.C0586c) r1     // Catch:{ Exception -> 0x002f }
            r6.m83a(r1)     // Catch:{ Exception -> 0x002f }
            goto L_0x000b
        L_0x002f:
            r1 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r0]
            java.lang.String r4 = "anet.HorseRaceDetector"
            java.lang.String r5 = "start hr task failed"
            anet.channel.util.ALog.m326e(r4, r5, r2, r1, r3)
            goto L_0x000b
        L_0x003a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x003a }
            goto L_0x003e
        L_0x003d:
            throw r0
        L_0x003e:
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.detect.C0496d.mo8774a():void");
    }

    /* renamed from: b */
    public void mo8775b() {
        StrategyCenter.getInstance().registerListener(new C0497e(this));
        AppLifecycle.registerLifecycleListener(new C0498f(this));
    }

    /* renamed from: a */
    private void m83a(C0583l.C0586c cVar) {
        if (cVar.f536b != null && cVar.f536b.length != 0) {
            String str = cVar.f535a;
            for (C0583l.C0588e eVar : cVar.f536b) {
                String str2 = eVar.f546b.f516b;
                if (str2.equalsIgnoreCase("http") || str2.equalsIgnoreCase("https")) {
                    m85a(str, eVar);
                } else if (str2.equalsIgnoreCase(ConnType.HTTP2) || str2.equalsIgnoreCase(ConnType.SPDY) || str2.equalsIgnoreCase(ConnType.QUIC)) {
                    m86b(str, eVar);
                } else if (str2.equalsIgnoreCase("tcp")) {
                    m87c(str, eVar);
                }
            }
        }
    }

    /* renamed from: a */
    private void m85a(String str, C0583l.C0588e eVar) {
        HttpUrl parse = HttpUrl.parse(eVar.f546b.f516b + HttpConstant.SCHEME_SPLIT + str + eVar.f547c);
        if (parse != null) {
            int i = 0;
            ALog.m328i("anet.HorseRaceDetector", "startShortLinkTask", (String) null, "url", parse);
            Request.Builder sslSocketFactory = new Request.Builder().setUrl(parse).addHeader(HttpHeaders.CONNECTION, HttpHeaderValues.CLOSE).setConnectTimeout(eVar.f546b.f517c).setReadTimeout(eVar.f546b.f518d).setRedirectEnable(false).setSslSocketFactory(new C0611j(str));
            Request build = sslSocketFactory.setSeq("HR" + this.f198b.getAndIncrement()).build();
            build.setDnsOptimize(eVar.f545a, eVar.f546b.f515a);
            long currentTimeMillis = System.currentTimeMillis();
            C0549b.C0550a a = C0549b.m214a(build, (RequestCb) null);
            HorseRaceStat horseRaceStat = new HorseRaceStat(str, eVar);
            horseRaceStat.connTime = System.currentTimeMillis() - currentTimeMillis;
            if (a.f387a <= 0) {
                horseRaceStat.connErrorCode = a.f387a;
            } else {
                horseRaceStat.connRet = 1;
                if (a.f387a == 200) {
                    i = 1;
                }
                horseRaceStat.reqRet = i;
                horseRaceStat.reqErrorCode = a.f387a;
                horseRaceStat.reqTime = horseRaceStat.connTime;
            }
            m84a(eVar.f545a, horseRaceStat);
            AppMonitor.getInstance().commitStat(horseRaceStat);
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x00db */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m86b(java.lang.String r17, anet.channel.strategy.C0583l.C0588e r18) {
        /*
            r16 = this;
            r9 = r16
            r0 = r17
            r10 = r18
            anet.channel.strategy.l$a r1 = r10.f546b
            anet.channel.strategy.ConnProtocol r1 = anet.channel.strategy.ConnProtocol.valueOf(r1)
            anet.channel.entity.ConnType r2 = anet.channel.entity.ConnType.valueOf(r1)
            if (r2 != 0) goto L_0x0013
            return
        L_0x0013:
            r3 = 0
            r4 = 8
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r11 = 0
            java.lang.String r5 = "host"
            r4[r11] = r5
            r5 = 1
            r4[r5] = r0
            r5 = 2
            java.lang.String r6 = "ip"
            r4[r5] = r6
            r5 = 3
            java.lang.String r6 = r10.f545a
            r4[r5] = r6
            r5 = 4
            java.lang.String r6 = "port"
            r4[r5] = r6
            r5 = 5
            anet.channel.strategy.l$a r6 = r10.f546b
            int r6 = r6.f515a
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            r4[r5] = r6
            r5 = 6
            java.lang.String r6 = "protocol"
            r4[r5] = r6
            r5 = 7
            r4[r5] = r1
            java.lang.String r5 = "anet.HorseRaceDetector"
            java.lang.String r6 = "startLongLinkTask"
            anet.channel.util.ALog.m328i(r5, r6, r3, r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "HR"
            r3.append(r4)
            java.util.concurrent.atomic.AtomicInteger r4 = r9.f198b
            int r4 = r4.getAndIncrement()
            r3.append(r4)
            java.lang.String r6 = r3.toString()
            anet.channel.session.TnetSpdySession r12 = new anet.channel.session.TnetSpdySession
            android.content.Context r3 = anet.channel.GlobalAppRuntimeInfo.getContext()
            anet.channel.entity.a r4 = new anet.channel.entity.a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            boolean r2 = r2.isSSL()
            if (r2 == 0) goto L_0x0076
            java.lang.String r2 = "https://"
            goto L_0x0078
        L_0x0076:
            java.lang.String r2 = "http://"
        L_0x0078:
            r5.append(r2)
            r5.append(r0)
            java.lang.String r2 = r5.toString()
            anet.channel.strategy.IConnStrategy r1 = m82a((anet.channel.strategy.ConnProtocol) r1, (anet.channel.strategy.C0583l.C0588e) r10)
            r4.<init>(r2, r6, r1)
            r12.<init>(r3, r4)
            anet.channel.statist.HorseRaceStat r13 = new anet.channel.statist.HorseRaceStat
            r13.<init>(r0, r10)
            long r14 = java.lang.System.currentTimeMillis()
            r0 = 257(0x101, float:3.6E-43)
            anet.channel.detect.h r8 = new anet.channel.detect.h
            r1 = r8
            r2 = r16
            r3 = r13
            r4 = r14
            r7 = r18
            r11 = r8
            r8 = r12
            r1.<init>(r2, r3, r4, r6, r7, r8)
            r12.registerEventcb(r0, r11)
            r12.connect()
            monitor-enter(r13)
            anet.channel.strategy.l$a r0 = r10.f546b     // Catch:{ InterruptedException -> 0x00db }
            int r0 = r0.f517c     // Catch:{ InterruptedException -> 0x00db }
            if (r0 != 0) goto L_0x00b5
            r0 = 10000(0x2710, float:1.4013E-41)
            goto L_0x00b9
        L_0x00b5:
            anet.channel.strategy.l$a r0 = r10.f546b     // Catch:{ InterruptedException -> 0x00db }
            int r0 = r0.f517c     // Catch:{ InterruptedException -> 0x00db }
        L_0x00b9:
            long r0 = (long) r0     // Catch:{ InterruptedException -> 0x00db }
            r13.wait(r0)     // Catch:{ InterruptedException -> 0x00db }
            long r0 = r13.connTime     // Catch:{ InterruptedException -> 0x00db }
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x00cc
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ InterruptedException -> 0x00db }
            long r0 = r0 - r14
            r13.connTime = r0     // Catch:{ InterruptedException -> 0x00db }
        L_0x00cc:
            java.lang.String r0 = r10.f545a     // Catch:{ InterruptedException -> 0x00db }
            r9.m84a((java.lang.String) r0, (anet.channel.statist.HorseRaceStat) r13)     // Catch:{ InterruptedException -> 0x00db }
            anet.channel.appmonitor.IAppMonitor r0 = anet.channel.appmonitor.AppMonitor.getInstance()     // Catch:{ InterruptedException -> 0x00db }
            r0.commitStat(r13)     // Catch:{ InterruptedException -> 0x00db }
            goto L_0x00db
        L_0x00d9:
            r0 = move-exception
            goto L_0x00e1
        L_0x00db:
            monitor-exit(r13)     // Catch:{ all -> 0x00d9 }
            r0 = 0
            r12.close(r0)
            return
        L_0x00e1:
            monitor-exit(r13)     // Catch:{ all -> 0x00d9 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.detect.C0496d.m86b(java.lang.String, anet.channel.strategy.l$e):void");
    }

    /* renamed from: a */
    private static IConnStrategy m82a(ConnProtocol connProtocol, C0583l.C0588e eVar) {
        return new C0502j(eVar, connProtocol);
    }

    /* renamed from: c */
    private void m87c(String str, C0583l.C0588e eVar) {
        String str2 = "HR" + this.f198b.getAndIncrement();
        ALog.m328i("anet.HorseRaceDetector", "startTcpTask", str2, "ip", eVar.f545a, "port", Integer.valueOf(eVar.f546b.f515a));
        HorseRaceStat horseRaceStat = new HorseRaceStat(str, eVar);
        long currentTimeMillis = System.currentTimeMillis();
        try {
            Socket socket = new Socket(eVar.f545a, eVar.f546b.f515a);
            socket.setSoTimeout(eVar.f546b.f517c == 0 ? RestConstants.G_MAX_CONNECTION_TIME_OUT : eVar.f546b.f517c);
            ALog.m328i("anet.HorseRaceDetector", "socket connect success", str2, new Object[0]);
            horseRaceStat.connRet = 1;
            horseRaceStat.connTime = System.currentTimeMillis() - currentTimeMillis;
            socket.close();
        } catch (IOException unused) {
            horseRaceStat.connTime = System.currentTimeMillis() - currentTimeMillis;
            horseRaceStat.connErrorCode = -404;
        }
        AppMonitor.getInstance().commitStat(horseRaceStat);
    }

    /* renamed from: a */
    private void m84a(String str, HorseRaceStat horseRaceStat) {
        if (AwcnConfig.isPing6Enable() && C0594c.m320b(str)) {
            try {
                PingResponse pingResponse = (PingResponse) new PingTask(str, 1000, 3, 0, 0).launch().get();
                if (pingResponse != null) {
                    horseRaceStat.pingSuccessCount = pingResponse.getSuccessCnt();
                    horseRaceStat.pingTimeoutCount = 3 - horseRaceStat.pingSuccessCount;
                    horseRaceStat.localIP = pingResponse.getLocalIPStr();
                }
            } catch (Throwable th) {
                ALog.m326e("anet.HorseRaceDetector", "ping6 task fail.", (String) null, th, new Object[0]);
            }
        }
    }
}
