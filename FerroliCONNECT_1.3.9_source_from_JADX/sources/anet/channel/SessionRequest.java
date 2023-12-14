package anet.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.entity.C0517a;
import anet.channel.entity.C0519c;
import anet.channel.entity.ConnType;
import anet.channel.entity.EventType;
import anet.channel.session.C0552d;
import anet.channel.session.TnetSpdySession;
import anet.channel.statist.AlarmObject;
import anet.channel.statist.SessionConnStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.utils.C0594c;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.C0604c;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Taobao */
class SessionRequest {

    /* renamed from: a */
    SessionCenter f127a;

    /* renamed from: b */
    C0507e f128b;

    /* renamed from: c */
    SessionInfo f129c;

    /* renamed from: d */
    volatile boolean f130d = false;

    /* renamed from: e */
    volatile Session f131e;

    /* renamed from: f */
    volatile boolean f132f = false;

    /* renamed from: g */
    HashMap<SessionGetCallback, C0474c> f133g = new HashMap<>();

    /* renamed from: h */
    SessionConnStat f134h = null;

    /* renamed from: i */
    private String f135i;

    /* renamed from: j */
    private String f136j;

    /* renamed from: k */
    private volatile Future f137k;

    /* renamed from: l */
    private Object f138l = new Object();

    /* compiled from: Taobao */
    private interface IConnCb {
        void onDisConnect(Session session, long j, int i);

        void onFailed(Session session, long j, int i, int i2);

        void onSuccess(Session session, long j);
    }

    SessionRequest(String str, SessionCenter sessionCenter) {
        this.f135i = str;
        String str2 = this.f135i;
        this.f136j = str2.substring(str2.indexOf(HttpConstant.SCHEME_SPLIT) + 3);
        this.f127a = sessionCenter;
        this.f129c = sessionCenter.f122g.mo8748b(this.f136j);
        this.f128b = sessionCenter.f120e;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String mo8695a() {
        return this.f135i;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo8702a(boolean z) {
        this.f130d = z;
        if (!z) {
            if (this.f137k != null) {
                this.f137k.cancel(true);
                this.f137k = null;
            }
            this.f131e = null;
        }
    }

    /* renamed from: anet.channel.SessionRequest$b */
    /* compiled from: Taobao */
    private class C0473b implements Runnable {

        /* renamed from: a */
        String f144a = null;

        C0473b(String str) {
            this.f144a = str;
        }

        public void run() {
            if (SessionRequest.this.f130d) {
                ALog.m327e("awcn.SessionRequest", "Connecting timeout!!! reset status!", this.f144a, new Object[0]);
                SessionRequest.this.f134h.ret = 2;
                SessionRequest.this.f134h.totalTime = System.currentTimeMillis() - SessionRequest.this.f134h.start;
                if (SessionRequest.this.f131e != null) {
                    SessionRequest.this.f131e.f109u = false;
                    SessionRequest.this.f131e.close();
                    SessionRequest.this.f134h.syncValueFromSession(SessionRequest.this.f131e);
                }
                AppMonitor.getInstance().commitStat(SessionRequest.this.f134h);
                SessionRequest.this.mo8702a(false);
            }
        }
    }

    /* renamed from: anet.channel.SessionRequest$c */
    /* compiled from: Taobao */
    protected class C0474c implements Runnable {

        /* renamed from: a */
        SessionGetCallback f146a = null;

        /* renamed from: b */
        AtomicBoolean f147b = new AtomicBoolean(false);

        protected C0474c(SessionGetCallback sessionGetCallback) {
            this.f146a = sessionGetCallback;
        }

        public void run() {
            if (this.f147b.compareAndSet(false, true)) {
                ALog.m327e("awcn.SessionRequest", "get session timeout", (String) null, new Object[0]);
                synchronized (SessionRequest.this.f133g) {
                    SessionRequest.this.f133g.remove(this.f146a);
                }
                this.f146a.onSessionGetFail();
            }
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* renamed from: a */
    protected synchronized void mo8697a(android.content.Context r10, int r11, java.lang.String r12, anet.channel.SessionGetCallback r13, long r14) {
        /*
            r9 = this;
            monitor-enter(r9)
            anet.channel.e r0 = r9.f128b     // Catch:{ all -> 0x0132 }
            anet.channel.Session r0 = r0.mo8789a((anet.channel.SessionRequest) r9, (int) r11)     // Catch:{ all -> 0x0132 }
            r1 = 0
            if (r0 == 0) goto L_0x001a
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r11 = "Available Session exist!!!"
            java.lang.Object[] r14 = new java.lang.Object[r1]     // Catch:{ all -> 0x0132 }
            anet.channel.util.ALog.m325d(r10, r11, r12, r14)     // Catch:{ all -> 0x0132 }
            if (r13 == 0) goto L_0x0018
            r13.onSessionGetSuccess(r0)     // Catch:{ all -> 0x0132 }
        L_0x0018:
            monitor-exit(r9)
            return
        L_0x001a:
            boolean r0 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x0132 }
            if (r0 == 0) goto L_0x0025
            r12 = 0
            java.lang.String r12 = anet.channel.util.C0610i.m364a(r12)     // Catch:{ all -> 0x0132 }
        L_0x0025:
            java.lang.String r0 = "awcn.SessionRequest"
            java.lang.String r2 = "SessionRequest start"
            r3 = 4
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x0132 }
            java.lang.String r5 = "host"
            r4[r1] = r5     // Catch:{ all -> 0x0132 }
            java.lang.String r5 = r9.f135i     // Catch:{ all -> 0x0132 }
            r6 = 1
            r4[r6] = r5     // Catch:{ all -> 0x0132 }
            java.lang.String r5 = "type"
            r7 = 2
            r4[r7] = r5     // Catch:{ all -> 0x0132 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x0132 }
            r8 = 3
            r4[r8] = r5     // Catch:{ all -> 0x0132 }
            anet.channel.util.ALog.m325d(r0, r2, r12, r4)     // Catch:{ all -> 0x0132 }
            boolean r0 = r9.f130d     // Catch:{ all -> 0x0132 }
            if (r0 == 0) goto L_0x007f
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r0 = "session connecting"
            java.lang.Object[] r2 = new java.lang.Object[r7]     // Catch:{ all -> 0x0132 }
            java.lang.String r3 = "host"
            r2[r1] = r3     // Catch:{ all -> 0x0132 }
            java.lang.String r1 = r9.mo8695a()     // Catch:{ all -> 0x0132 }
            r2[r6] = r1     // Catch:{ all -> 0x0132 }
            anet.channel.util.ALog.m325d(r10, r0, r12, r2)     // Catch:{ all -> 0x0132 }
            if (r13 == 0) goto L_0x007d
            int r10 = r9.mo8703b()     // Catch:{ all -> 0x0132 }
            if (r10 != r11) goto L_0x007a
            anet.channel.SessionRequest$c r10 = new anet.channel.SessionRequest$c     // Catch:{ all -> 0x0132 }
            r10.<init>(r13)     // Catch:{ all -> 0x0132 }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r11 = r9.f133g     // Catch:{ all -> 0x0132 }
            monitor-enter(r11)     // Catch:{ all -> 0x0132 }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r12 = r9.f133g     // Catch:{ all -> 0x0077 }
            r12.put(r13, r10)     // Catch:{ all -> 0x0077 }
            monitor-exit(r11)     // Catch:{ all -> 0x0077 }
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0132 }
            anet.channel.thread.ThreadPoolExecutorFactory.submitScheduledTask(r10, r14, r11)     // Catch:{ all -> 0x0132 }
            goto L_0x007d
        L_0x0077:
            r10 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0077 }
            throw r10     // Catch:{ all -> 0x0132 }
        L_0x007a:
            r13.onSessionGetFail()     // Catch:{ all -> 0x0132 }
        L_0x007d:
            monitor-exit(r9)
            return
        L_0x007f:
            r9.mo8702a((boolean) r6)     // Catch:{ all -> 0x0132 }
            anet.channel.SessionRequest$b r0 = new anet.channel.SessionRequest$b     // Catch:{ all -> 0x0132 }
            r0.<init>(r12)     // Catch:{ all -> 0x0132 }
            r4 = 45
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x0132 }
            java.util.concurrent.Future r0 = anet.channel.thread.ThreadPoolExecutorFactory.submitScheduledTask(r0, r4, r2)     // Catch:{ all -> 0x0132 }
            r9.f137k = r0     // Catch:{ all -> 0x0132 }
            anet.channel.statist.SessionConnStat r0 = new anet.channel.statist.SessionConnStat     // Catch:{ all -> 0x0132 }
            r0.<init>()     // Catch:{ all -> 0x0132 }
            r9.f134h = r0     // Catch:{ all -> 0x0132 }
            anet.channel.statist.SessionConnStat r0 = r9.f134h     // Catch:{ all -> 0x0132 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0132 }
            r0.start = r4     // Catch:{ all -> 0x0132 }
            boolean r0 = anet.channel.status.NetworkStatusHelper.isConnected()     // Catch:{ all -> 0x0132 }
            if (r0 != 0) goto L_0x00ce
            boolean r10 = anet.channel.util.ALog.isPrintLog(r6)     // Catch:{ all -> 0x0132 }
            if (r10 == 0) goto L_0x00c3
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r11 = "network is not available, can't create session"
            java.lang.Object[] r13 = new java.lang.Object[r7]     // Catch:{ all -> 0x0132 }
            java.lang.String r14 = "isConnected"
            r13[r1] = r14     // Catch:{ all -> 0x0132 }
            boolean r14 = anet.channel.status.NetworkStatusHelper.isConnected()     // Catch:{ all -> 0x0132 }
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r14)     // Catch:{ all -> 0x0132 }
            r13[r6] = r14     // Catch:{ all -> 0x0132 }
            anet.channel.util.ALog.m325d(r10, r11, r12, r13)     // Catch:{ all -> 0x0132 }
        L_0x00c3:
            r9.mo8706c()     // Catch:{ all -> 0x0132 }
            java.lang.RuntimeException r10 = new java.lang.RuntimeException     // Catch:{ all -> 0x0132 }
            java.lang.String r11 = "no network"
            r10.<init>(r11)     // Catch:{ all -> 0x0132 }
            throw r10     // Catch:{ all -> 0x0132 }
        L_0x00ce:
            java.util.List r0 = r9.m34a((int) r11, (java.lang.String) r12)     // Catch:{ all -> 0x0132 }
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x0132 }
            if (r2 != 0) goto L_0x010c
            java.util.List r11 = r9.m35a((java.util.List<anet.channel.strategy.IConnStrategy>) r0, (java.lang.String) r12)     // Catch:{ all -> 0x0132 }
            java.lang.Object r12 = r11.remove(r1)     // Catch:{ Throwable -> 0x0107 }
            anet.channel.entity.a r12 = (anet.channel.entity.C0517a) r12     // Catch:{ Throwable -> 0x0107 }
            anet.channel.SessionRequest$a r0 = new anet.channel.SessionRequest$a     // Catch:{ Throwable -> 0x0107 }
            r0.<init>(r10, r11, r12)     // Catch:{ Throwable -> 0x0107 }
            java.lang.String r11 = r12.mo8821h()     // Catch:{ Throwable -> 0x0107 }
            r9.m36a(r10, r12, r0, r11)     // Catch:{ Throwable -> 0x0107 }
            if (r13 == 0) goto L_0x010a
            anet.channel.SessionRequest$c r10 = new anet.channel.SessionRequest$c     // Catch:{ Throwable -> 0x0107 }
            r10.<init>(r13)     // Catch:{ Throwable -> 0x0107 }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r11 = r9.f133g     // Catch:{ Throwable -> 0x0107 }
            monitor-enter(r11)     // Catch:{ Throwable -> 0x0107 }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r12 = r9.f133g     // Catch:{ all -> 0x0104 }
            r12.put(r13, r10)     // Catch:{ all -> 0x0104 }
            monitor-exit(r11)     // Catch:{ all -> 0x0104 }
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x0107 }
            anet.channel.thread.ThreadPoolExecutorFactory.submitScheduledTask(r10, r14, r11)     // Catch:{ Throwable -> 0x0107 }
            goto L_0x010a
        L_0x0104:
            r10 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0104 }
            throw r10     // Catch:{ Throwable -> 0x0107 }
        L_0x0107:
            r9.mo8706c()     // Catch:{ all -> 0x0132 }
        L_0x010a:
            monitor-exit(r9)
            return
        L_0x010c:
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r13 = "no avalible strategy, can't create session"
            java.lang.Object[] r14 = new java.lang.Object[r3]     // Catch:{ all -> 0x0132 }
            java.lang.String r15 = "host"
            r14[r1] = r15     // Catch:{ all -> 0x0132 }
            java.lang.String r15 = r9.f135i     // Catch:{ all -> 0x0132 }
            r14[r6] = r15     // Catch:{ all -> 0x0132 }
            java.lang.String r15 = "type"
            r14[r7] = r15     // Catch:{ all -> 0x0132 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x0132 }
            r14[r8] = r11     // Catch:{ all -> 0x0132 }
            anet.channel.util.ALog.m328i(r10, r13, r12, r14)     // Catch:{ all -> 0x0132 }
            r9.mo8706c()     // Catch:{ all -> 0x0132 }
            anet.channel.NoAvailStrategyException r10 = new anet.channel.NoAvailStrategyException     // Catch:{ all -> 0x0132 }
            java.lang.String r11 = "no avalible strategy"
            r10.<init>(r11)     // Catch:{ all -> 0x0132 }
            throw r10     // Catch:{ all -> 0x0132 }
        L_0x0132:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.SessionRequest.mo8697a(android.content.Context, int, java.lang.String, anet.channel.SessionGetCallback, long):void");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* renamed from: b */
    protected synchronized void mo8704b(android.content.Context r10, int r11, java.lang.String r12, anet.channel.SessionGetCallback r13, long r14) {
        /*
            r9 = this;
            monitor-enter(r9)
            anet.channel.e r0 = r9.f128b     // Catch:{ all -> 0x012c }
            anet.channel.Session r0 = r0.mo8789a((anet.channel.SessionRequest) r9, (int) r11)     // Catch:{ all -> 0x012c }
            r1 = 0
            if (r0 == 0) goto L_0x0018
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r11 = "Available Session exist!!!"
            java.lang.Object[] r14 = new java.lang.Object[r1]     // Catch:{ all -> 0x012c }
            anet.channel.util.ALog.m325d(r10, r11, r12, r14)     // Catch:{ all -> 0x012c }
            r13.onSessionGetSuccess(r0)     // Catch:{ all -> 0x012c }
            monitor-exit(r9)
            return
        L_0x0018:
            boolean r0 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x012c }
            if (r0 == 0) goto L_0x0023
            r12 = 0
            java.lang.String r12 = anet.channel.util.C0610i.m364a(r12)     // Catch:{ all -> 0x012c }
        L_0x0023:
            java.lang.String r0 = "awcn.SessionRequest"
            java.lang.String r2 = "SessionRequest start"
            r3 = 4
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x012c }
            java.lang.String r5 = "host"
            r4[r1] = r5     // Catch:{ all -> 0x012c }
            java.lang.String r5 = r9.f135i     // Catch:{ all -> 0x012c }
            r6 = 1
            r4[r6] = r5     // Catch:{ all -> 0x012c }
            java.lang.String r5 = "type"
            r7 = 2
            r4[r7] = r5     // Catch:{ all -> 0x012c }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x012c }
            r8 = 3
            r4[r8] = r5     // Catch:{ all -> 0x012c }
            anet.channel.util.ALog.m325d(r0, r2, r12, r4)     // Catch:{ all -> 0x012c }
            boolean r0 = r9.f130d     // Catch:{ all -> 0x012c }
            if (r0 == 0) goto L_0x007b
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r0 = "session connecting"
            java.lang.Object[] r2 = new java.lang.Object[r7]     // Catch:{ all -> 0x012c }
            java.lang.String r3 = "host"
            r2[r1] = r3     // Catch:{ all -> 0x012c }
            java.lang.String r1 = r9.mo8695a()     // Catch:{ all -> 0x012c }
            r2[r6] = r1     // Catch:{ all -> 0x012c }
            anet.channel.util.ALog.m325d(r10, r0, r12, r2)     // Catch:{ all -> 0x012c }
            int r10 = r9.mo8703b()     // Catch:{ all -> 0x012c }
            if (r10 != r11) goto L_0x0076
            anet.channel.SessionRequest$c r10 = new anet.channel.SessionRequest$c     // Catch:{ all -> 0x012c }
            r10.<init>(r13)     // Catch:{ all -> 0x012c }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r11 = r9.f133g     // Catch:{ all -> 0x012c }
            monitor-enter(r11)     // Catch:{ all -> 0x012c }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r12 = r9.f133g     // Catch:{ all -> 0x0073 }
            r12.put(r13, r10)     // Catch:{ all -> 0x0073 }
            monitor-exit(r11)     // Catch:{ all -> 0x0073 }
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x012c }
            anet.channel.thread.ThreadPoolExecutorFactory.submitScheduledTask(r10, r14, r11)     // Catch:{ all -> 0x012c }
            goto L_0x0079
        L_0x0073:
            r10 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0073 }
            throw r10     // Catch:{ all -> 0x012c }
        L_0x0076:
            r13.onSessionGetFail()     // Catch:{ all -> 0x012c }
        L_0x0079:
            monitor-exit(r9)
            return
        L_0x007b:
            r9.mo8702a((boolean) r6)     // Catch:{ all -> 0x012c }
            anet.channel.SessionRequest$b r0 = new anet.channel.SessionRequest$b     // Catch:{ all -> 0x012c }
            r0.<init>(r12)     // Catch:{ all -> 0x012c }
            r4 = 45
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x012c }
            java.util.concurrent.Future r0 = anet.channel.thread.ThreadPoolExecutorFactory.submitScheduledTask(r0, r4, r2)     // Catch:{ all -> 0x012c }
            r9.f137k = r0     // Catch:{ all -> 0x012c }
            anet.channel.statist.SessionConnStat r0 = new anet.channel.statist.SessionConnStat     // Catch:{ all -> 0x012c }
            r0.<init>()     // Catch:{ all -> 0x012c }
            r9.f134h = r0     // Catch:{ all -> 0x012c }
            anet.channel.statist.SessionConnStat r0 = r9.f134h     // Catch:{ all -> 0x012c }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x012c }
            r0.start = r4     // Catch:{ all -> 0x012c }
            boolean r0 = anet.channel.status.NetworkStatusHelper.isConnected()     // Catch:{ all -> 0x012c }
            if (r0 != 0) goto L_0x00ca
            boolean r10 = anet.channel.util.ALog.isPrintLog(r6)     // Catch:{ all -> 0x012c }
            if (r10 == 0) goto L_0x00bf
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r11 = "network is not available, can't create session"
            java.lang.Object[] r13 = new java.lang.Object[r7]     // Catch:{ all -> 0x012c }
            java.lang.String r14 = "isConnected"
            r13[r1] = r14     // Catch:{ all -> 0x012c }
            boolean r14 = anet.channel.status.NetworkStatusHelper.isConnected()     // Catch:{ all -> 0x012c }
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r14)     // Catch:{ all -> 0x012c }
            r13[r6] = r14     // Catch:{ all -> 0x012c }
            anet.channel.util.ALog.m325d(r10, r11, r12, r13)     // Catch:{ all -> 0x012c }
        L_0x00bf:
            r9.mo8706c()     // Catch:{ all -> 0x012c }
            java.lang.RuntimeException r10 = new java.lang.RuntimeException     // Catch:{ all -> 0x012c }
            java.lang.String r11 = "no network"
            r10.<init>(r11)     // Catch:{ all -> 0x012c }
            throw r10     // Catch:{ all -> 0x012c }
        L_0x00ca:
            java.util.List r0 = r9.m34a((int) r11, (java.lang.String) r12)     // Catch:{ all -> 0x012c }
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x012c }
            if (r2 != 0) goto L_0x0106
            java.util.List r11 = r9.m35a((java.util.List<anet.channel.strategy.IConnStrategy>) r0, (java.lang.String) r12)     // Catch:{ all -> 0x012c }
            java.lang.Object r12 = r11.remove(r1)     // Catch:{ Throwable -> 0x0101 }
            anet.channel.entity.a r12 = (anet.channel.entity.C0517a) r12     // Catch:{ Throwable -> 0x0101 }
            anet.channel.SessionRequest$a r0 = new anet.channel.SessionRequest$a     // Catch:{ Throwable -> 0x0101 }
            r0.<init>(r10, r11, r12)     // Catch:{ Throwable -> 0x0101 }
            java.lang.String r11 = r12.mo8821h()     // Catch:{ Throwable -> 0x0101 }
            r9.m36a(r10, r12, r0, r11)     // Catch:{ Throwable -> 0x0101 }
            anet.channel.SessionRequest$c r10 = new anet.channel.SessionRequest$c     // Catch:{ Throwable -> 0x0101 }
            r10.<init>(r13)     // Catch:{ Throwable -> 0x0101 }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r11 = r9.f133g     // Catch:{ Throwable -> 0x0101 }
            monitor-enter(r11)     // Catch:{ Throwable -> 0x0101 }
            java.util.HashMap<anet.channel.SessionGetCallback, anet.channel.SessionRequest$c> r12 = r9.f133g     // Catch:{ all -> 0x00fe }
            r12.put(r13, r10)     // Catch:{ all -> 0x00fe }
            monitor-exit(r11)     // Catch:{ all -> 0x00fe }
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x0101 }
            anet.channel.thread.ThreadPoolExecutorFactory.submitScheduledTask(r10, r14, r11)     // Catch:{ Throwable -> 0x0101 }
            goto L_0x0104
        L_0x00fe:
            r10 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x00fe }
            throw r10     // Catch:{ Throwable -> 0x0101 }
        L_0x0101:
            r9.mo8706c()     // Catch:{ all -> 0x012c }
        L_0x0104:
            monitor-exit(r9)
            return
        L_0x0106:
            java.lang.String r10 = "awcn.SessionRequest"
            java.lang.String r13 = "no avalible strategy, can't create session"
            java.lang.Object[] r14 = new java.lang.Object[r3]     // Catch:{ all -> 0x012c }
            java.lang.String r15 = "host"
            r14[r1] = r15     // Catch:{ all -> 0x012c }
            java.lang.String r15 = r9.f135i     // Catch:{ all -> 0x012c }
            r14[r6] = r15     // Catch:{ all -> 0x012c }
            java.lang.String r15 = "type"
            r14[r7] = r15     // Catch:{ all -> 0x012c }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x012c }
            r14[r8] = r11     // Catch:{ all -> 0x012c }
            anet.channel.util.ALog.m328i(r10, r13, r12, r14)     // Catch:{ all -> 0x012c }
            r9.mo8706c()     // Catch:{ all -> 0x012c }
            anet.channel.NoAvailStrategyException r10 = new anet.channel.NoAvailStrategyException     // Catch:{ all -> 0x012c }
            java.lang.String r11 = "no avalible strategy"
            r10.<init>(r11)     // Catch:{ all -> 0x012c }
            throw r10     // Catch:{ all -> 0x012c }
        L_0x012c:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.SessionRequest.mo8704b(android.content.Context, int, java.lang.String, anet.channel.SessionGetCallback, long):void");
    }

    /* renamed from: anet.channel.SessionRequest$a */
    /* compiled from: Taobao */
    class C0472a implements IConnCb {

        /* renamed from: a */
        boolean f139a = false;
        /* access modifiers changed from: private */

        /* renamed from: c */
        public Context f141c;

        /* renamed from: d */
        private List<C0517a> f142d;

        /* renamed from: e */
        private C0517a f143e;

        C0472a(Context context, List<C0517a> list, C0517a aVar) {
            this.f141c = context;
            this.f142d = list;
            this.f143e = aVar;
        }

        public void onFailed(Session session, long j, int i, int i2) {
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.SessionRequest", "Connect failed", this.f143e.mo8821h(), "session", session, Constants.KEY_HOST, SessionRequest.this.mo8695a(), "isHandleFinish", Boolean.valueOf(this.f139a));
            }
            if (SessionRequest.this.f132f) {
                SessionRequest.this.f132f = false;
            } else if (!this.f139a) {
                this.f139a = true;
                SessionRequest.this.f128b.mo8793b(SessionRequest.this, session);
                if (!session.f109u || !NetworkStatusHelper.isConnected() || this.f142d.isEmpty()) {
                    SessionRequest.this.mo8706c();
                    SessionRequest.this.mo8699a(session, i, i2);
                    synchronized (SessionRequest.this.f133g) {
                        for (Map.Entry next : SessionRequest.this.f133g.entrySet()) {
                            C0474c cVar = (C0474c) next.getValue();
                            if (cVar.f147b.compareAndSet(false, true)) {
                                ThreadPoolExecutorFactory.removeScheduleTask(cVar);
                                ((SessionGetCallback) next.getKey()).onSessionGetFail();
                            }
                        }
                        SessionRequest.this.f133g.clear();
                    }
                    return;
                }
                if (ALog.isPrintLog(1)) {
                    ALog.m325d("awcn.SessionRequest", "use next connInfo to create session", this.f143e.mo8821h(), Constants.KEY_HOST, SessionRequest.this.mo8695a());
                }
                if (this.f143e.f242b == this.f143e.f243c && (i2 == -2003 || i2 == -2410)) {
                    ListIterator<C0517a> listIterator = this.f142d.listIterator();
                    while (listIterator.hasNext()) {
                        if (session.getIp().equals(listIterator.next().f241a.getIp())) {
                            listIterator.remove();
                        }
                    }
                }
                if (C0594c.m320b(session.getIp())) {
                    ListIterator<C0517a> listIterator2 = this.f142d.listIterator();
                    while (listIterator2.hasNext()) {
                        if (C0594c.m320b(listIterator2.next().f241a.getIp())) {
                            listIterator2.remove();
                        }
                    }
                }
                if (this.f142d.isEmpty()) {
                    SessionRequest.this.mo8706c();
                    SessionRequest.this.mo8699a(session, i, i2);
                    synchronized (SessionRequest.this.f133g) {
                        for (Map.Entry next2 : SessionRequest.this.f133g.entrySet()) {
                            C0474c cVar2 = (C0474c) next2.getValue();
                            if (cVar2.f147b.compareAndSet(false, true)) {
                                ThreadPoolExecutorFactory.removeScheduleTask(cVar2);
                                ((SessionGetCallback) next2.getKey()).onSessionGetFail();
                            }
                        }
                        SessionRequest.this.f133g.clear();
                    }
                    return;
                }
                C0517a remove = this.f142d.remove(0);
                SessionRequest sessionRequest = SessionRequest.this;
                Context context = this.f141c;
                sessionRequest.m36a(context, remove, new C0472a(context, this.f142d, remove), remove.mo8821h());
            }
        }

        public void onSuccess(Session session, long j) {
            ALog.m325d("awcn.SessionRequest", "Connect Success", this.f143e.mo8821h(), "session", session, Constants.KEY_HOST, SessionRequest.this.mo8695a());
            try {
                if (SessionRequest.this.f132f) {
                    SessionRequest.this.f132f = false;
                    session.close(false);
                    SessionRequest.this.mo8706c();
                    return;
                }
                SessionRequest.this.f128b.mo8792a(SessionRequest.this, session);
                SessionRequest.this.mo8698a(session);
                synchronized (SessionRequest.this.f133g) {
                    for (Map.Entry next : SessionRequest.this.f133g.entrySet()) {
                        C0474c cVar = (C0474c) next.getValue();
                        if (cVar.f147b.compareAndSet(false, true)) {
                            ThreadPoolExecutorFactory.removeScheduleTask(cVar);
                            ((SessionGetCallback) next.getKey()).onSessionGetSuccess(session);
                        }
                    }
                    SessionRequest.this.f133g.clear();
                }
                SessionRequest.this.mo8706c();
            } catch (Exception e) {
                try {
                    ALog.m326e("awcn.SessionRequest", "[onSuccess]:", this.f143e.mo8821h(), e, new Object[0]);
                } finally {
                    SessionRequest.this.mo8706c();
                }
            }
        }

        public void onDisConnect(Session session, long j, int i) {
            boolean isAppBackground = GlobalAppRuntimeInfo.isAppBackground();
            ALog.m325d("awcn.SessionRequest", "Connect Disconnect", this.f143e.mo8821h(), "session", session, Constants.KEY_HOST, SessionRequest.this.mo8695a(), "appIsBg", Boolean.valueOf(isAppBackground), "isHandleFinish", Boolean.valueOf(this.f139a));
            SessionRequest.this.f128b.mo8793b(SessionRequest.this, session);
            if (!this.f139a) {
                this.f139a = true;
                if (session.f108t) {
                    if (isAppBackground && (SessionRequest.this.f129c == null || !SessionRequest.this.f129c.isAccs || AwcnConfig.isAccsSessionCreateForbiddenInBg())) {
                        ALog.m327e("awcn.SessionRequest", "[onDisConnect]app background, don't Recreate", this.f143e.mo8821h(), "session", session);
                    } else if (!NetworkStatusHelper.isConnected()) {
                        ALog.m327e("awcn.SessionRequest", "[onDisConnect]no network, don't Recreate", this.f143e.mo8821h(), "session", session);
                    } else {
                        try {
                            ALog.m325d("awcn.SessionRequest", "session disconnected, try to recreate session", this.f143e.mo8821h(), new Object[0]);
                            int i2 = RestConstants.G_MAX_CONNECTION_TIME_OUT;
                            if (SessionRequest.this.f129c != null && SessionRequest.this.f129c.isAccs) {
                                i2 = AwcnConfig.getAccsReconnectionDelayPeriod();
                            }
                            C0529i iVar = new C0529i(this, session);
                            double random = Math.random();
                            double d = (double) i2;
                            Double.isNaN(d);
                            ThreadPoolExecutorFactory.submitScheduledTask(iVar, (long) (random * d), TimeUnit.MILLISECONDS);
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo8698a(Session session) {
        AlarmObject alarmObject = new AlarmObject();
        alarmObject.module = "networkPrefer";
        alarmObject.modulePoint = "policy";
        alarmObject.arg = this.f135i;
        alarmObject.isSuccess = true;
        AppMonitor.getInstance().commitAlarm(alarmObject);
        this.f134h.syncValueFromSession(session);
        SessionConnStat sessionConnStat = this.f134h;
        sessionConnStat.ret = 1;
        sessionConnStat.totalTime = System.currentTimeMillis() - this.f134h.start;
        AppMonitor.getInstance().commitStat(this.f134h);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo8699a(Session session, int i, int i2) {
        if (256 == i && i2 != -2613 && i2 != -2601) {
            AlarmObject alarmObject = new AlarmObject();
            alarmObject.module = "networkPrefer";
            alarmObject.modulePoint = "policy";
            alarmObject.arg = this.f135i;
            alarmObject.errorCode = String.valueOf(i2);
            alarmObject.isSuccess = false;
            AppMonitor.getInstance().commitAlarm(alarmObject);
            SessionConnStat sessionConnStat = this.f134h;
            sessionConnStat.ret = 0;
            sessionConnStat.appendErrorTrace(i2);
            this.f134h.errorCode = String.valueOf(i2);
            this.f134h.totalTime = System.currentTimeMillis() - this.f134h.start;
            this.f134h.syncValueFromSession(session);
            AppMonitor.getInstance().commitStat(this.f134h);
        }
    }

    /* renamed from: a */
    private List<IConnStrategy> m34a(int i, String str) {
        List<IConnStrategy> list = Collections.EMPTY_LIST;
        try {
            HttpUrl parse = HttpUrl.parse(mo8695a());
            if (parse == null) {
                return Collections.EMPTY_LIST;
            }
            list = StrategyCenter.getInstance().getConnStrategyListByHost(parse.host());
            if (!list.isEmpty()) {
                boolean equalsIgnoreCase = "https".equalsIgnoreCase(parse.scheme());
                boolean b = C0604c.m350b();
                ListIterator<IConnStrategy> listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    IConnStrategy next = listIterator.next();
                    ConnType valueOf = ConnType.valueOf(next.getProtocol());
                    if (valueOf != null) {
                        if (valueOf.isSSL() == equalsIgnoreCase) {
                            if (i == C0519c.f251c || valueOf.getType() == i) {
                                if (b && C0594c.m320b(next.getIp())) {
                                    listIterator.remove();
                                }
                            }
                        }
                        listIterator.remove();
                    }
                }
            }
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.SessionRequest", "[getAvailStrategy]", str, "strategies", list);
            }
            return list;
        } catch (Throwable th) {
            ALog.m326e("awcn.SessionRequest", "", str, th, new Object[0]);
        }
    }

    /* renamed from: a */
    private List<C0517a> m35a(List<IConnStrategy> list, String str) {
        if (list.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (i < list.size()) {
            IConnStrategy iConnStrategy = list.get(i);
            int retryTimes = iConnStrategy.getRetryTimes();
            int i3 = i2;
            for (int i4 = 0; i4 <= retryTimes; i4++) {
                i3++;
                String a = mo8695a();
                C0517a aVar = new C0517a(a, str + "_" + i3, iConnStrategy);
                aVar.f242b = i4;
                aVar.f243c = retryTimes;
                arrayList.add(aVar);
            }
            i++;
            i2 = i3;
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m36a(Context context, C0517a aVar, IConnCb iConnCb, String str) {
        ConnType c = aVar.mo8816c();
        if (context == null || c.isHttpType()) {
            this.f131e = new C0552d(context, aVar);
        } else {
            TnetSpdySession tnetSpdySession = new TnetSpdySession(context, aVar);
            tnetSpdySession.initConfig(this.f127a.f119d);
            tnetSpdySession.initSessionInfo(this.f129c);
            tnetSpdySession.setTnetPublicKey(this.f127a.f122g.mo8749c(this.f136j));
            this.f131e = tnetSpdySession;
        }
        ALog.m328i("awcn.SessionRequest", "create connection...", str, "Host", mo8695a(), "Type", aVar.mo8816c(), "IP", aVar.mo8814a(), "Port", Integer.valueOf(aVar.mo8815b()), "heartbeat", Integer.valueOf(aVar.mo8820g()), "session", this.f131e);
        m37a(this.f131e, iConnCb, System.currentTimeMillis());
        this.f131e.connect();
        this.f134h.retryTimes++;
        this.f134h.startConnect = System.currentTimeMillis();
        if (this.f134h.retryTimes == 0) {
            this.f134h.putExtra("firstIp", aVar.mo8814a());
        }
    }

    /* renamed from: a */
    private void m37a(Session session, IConnCb iConnCb, long j) {
        if (iConnCb != null) {
            session.registerEventcb(EventType.ALL, new C0520f(this, iConnCb, j));
            session.registerEventcb(1792, new C0524g(this, session));
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo8705b(boolean z) {
        ALog.m325d("awcn.SessionRequest", "closeSessions", this.f127a.f118c, Constants.KEY_HOST, this.f135i, "autoCreate", Boolean.valueOf(z));
        if (!z && this.f131e != null) {
            this.f131e.f109u = false;
            this.f131e.close(false);
        }
        List<Session> a = this.f128b.mo8791a(this);
        if (a != null) {
            for (Session next : a) {
                if (next != null) {
                    next.close(z);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo8701a(String str) {
        ALog.m325d("awcn.SessionRequest", "reCreateSession", str, Constants.KEY_HOST, this.f135i);
        mo8705b(true);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo8696a(long j) throws InterruptedException, TimeoutException {
        ALog.m325d("awcn.SessionRequest", "[await]", (String) null, "timeoutMs", Long.valueOf(j));
        if (j > 0) {
            synchronized (this.f138l) {
                long currentTimeMillis = System.currentTimeMillis() + j;
                while (true) {
                    if (!this.f130d) {
                        break;
                    }
                    long currentTimeMillis2 = System.currentTimeMillis();
                    if (currentTimeMillis2 >= currentTimeMillis) {
                        break;
                    }
                    this.f138l.wait(currentTimeMillis - currentTimeMillis2);
                }
                if (this.f130d) {
                    throw new TimeoutException();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public int mo8703b() {
        Session session = this.f131e;
        if (session != null) {
            return session.f98j.getType();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public void mo8706c() {
        mo8702a(false);
        synchronized (this.f138l) {
            this.f138l.notifyAll();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo8700a(Session session, int i, String str) {
        if (AwcnConfig.isSendConnectInfoByService()) {
            m39b(session, i, str);
        }
        m40c(session, i, str);
    }

    /* renamed from: b */
    private void m39b(Session session, int i, String str) {
        SessionInfo sessionInfo;
        Context context = GlobalAppRuntimeInfo.getContext();
        if (context != null && (sessionInfo = this.f129c) != null && sessionInfo.isAccs) {
            ALog.m327e("awcn.SessionRequest", "sendConnectInfoToAccsByService", (String) null, new Object[0]);
            try {
                Intent intent = new Intent(Constants.ACTION_RECEIVE);
                intent.setPackage(context.getPackageName());
                intent.setClassName(context, "com.taobao.accs.data.MsgDistributeService");
                intent.putExtra("command", 103);
                intent.putExtra(Constants.KEY_HOST, session.getHost());
                intent.putExtra(Constants.KEY_CENTER_HOST, true);
                boolean isAvailable = session.isAvailable();
                if (!isAvailable) {
                    intent.putExtra(Constants.KEY_ERROR_CODE, i);
                    intent.putExtra(Constants.KEY_ERROR_DETAIL, str);
                }
                intent.putExtra(Constants.KEY_CONNECT_AVAILABLE, isAvailable);
                intent.putExtra(Constants.KEY_TYPE_INAPP, true);
                if (Build.VERSION.SDK_INT >= 26) {
                    context.bindService(intent, new C0525h(this, intent, context), 1);
                } else {
                    context.startService(intent);
                }
            } catch (Throwable th) {
                ALog.m326e("awcn.SessionRequest", "sendConnectInfoToAccsByService", (String) null, th, new Object[0]);
            }
        }
    }

    /* renamed from: c */
    private void m40c(Session session, int i, String str) {
        SessionInfo sessionInfo = this.f129c;
        if (sessionInfo != null && sessionInfo.isAccs) {
            ALog.m327e("awcn.SessionRequest", "sendConnectInfoToAccsByCallBack", (String) null, new Object[0]);
            Intent intent = new Intent("com.taobao.ACCS_CONNECT_INFO");
            intent.putExtra("command", 103);
            intent.putExtra(Constants.KEY_HOST, session.getHost());
            intent.putExtra(Constants.KEY_CENTER_HOST, true);
            boolean isAvailable = session.isAvailable();
            if (!isAvailable) {
                intent.putExtra(Constants.KEY_ERROR_CODE, i);
                intent.putExtra(Constants.KEY_ERROR_DETAIL, str);
            }
            intent.putExtra(Constants.KEY_CONNECT_AVAILABLE, isAvailable);
            intent.putExtra(Constants.KEY_TYPE_INAPP, true);
            this.f127a.f123h.notifyListener(intent);
        }
    }
}
