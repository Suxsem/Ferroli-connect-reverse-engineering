package anetwork.channel.entity;

import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.aidl.ParcelableNetworkListener;

/* renamed from: anetwork.channel.entity.f */
/* compiled from: Taobao */
class C0639f implements Runnable {

    /* renamed from: a */
    final /* synthetic */ DefaultFinishEvent f727a;

    /* renamed from: b */
    final /* synthetic */ ParcelableNetworkListener f728b;

    /* renamed from: c */
    final /* synthetic */ C0636c f729c;

    C0639f(C0636c cVar, DefaultFinishEvent defaultFinishEvent, ParcelableNetworkListener parcelableNetworkListener) {
        this.f729c = cVar;
        this.f727a = defaultFinishEvent;
        this.f728b = parcelableNetworkListener;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x0159 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r10 = this;
            java.lang.String r0 = "anet.Repeater"
            anetwork.channel.aidl.DefaultFinishEvent r1 = r10.f727a
            r2 = 0
            if (r1 == 0) goto L_0x000a
            r1.setContext(r2)
        L_0x000a:
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.aidl.DefaultFinishEvent r1 = r10.f727a     // Catch:{ Throwable -> 0x0173 }
            anet.channel.statist.RequestStatistic r1 = r1.f603rs     // Catch:{ Throwable -> 0x0173 }
            if (r1 == 0) goto L_0x002e
            r1.rspCbStart = r3     // Catch:{ Throwable -> 0x0173 }
            long r5 = r1.rspEnd     // Catch:{ Throwable -> 0x0173 }
            long r5 = r3 - r5
            r1.lastProcessTime = r5     // Catch:{ Throwable -> 0x0173 }
            long r5 = r1.retryCostTime     // Catch:{ Throwable -> 0x0173 }
            long r7 = r1.start     // Catch:{ Throwable -> 0x0173 }
            long r7 = r3 - r7
            long r5 = r5 + r7
            r1.oneWayTime = r5     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.aidl.DefaultFinishEvent r5 = r10.f727a     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.statist.StatisticData r5 = r5.getStatisticData()     // Catch:{ Throwable -> 0x0173 }
            r5.filledBy(r1)     // Catch:{ Throwable -> 0x0173 }
        L_0x002e:
            anetwork.channel.aidl.ParcelableNetworkListener r5 = r10.f728b     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.aidl.DefaultFinishEvent r6 = r10.f727a     // Catch:{ Throwable -> 0x0173 }
            r5.onFinished(r6)     // Catch:{ Throwable -> 0x0173 }
            if (r1 == 0) goto L_0x0049
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0173 }
            r1.rspCbEnd = r5     // Catch:{ Throwable -> 0x0173 }
            long r5 = r5 - r3
            r1.callbackTime = r5     // Catch:{ Throwable -> 0x0173 }
            anet.channel.fulltrace.IFullTraceAnalysis r3 = anet.channel.fulltrace.C0521a.m128a()     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r4 = r1.traceId     // Catch:{ Throwable -> 0x0173 }
            r3.commitRequest(r4, r1)     // Catch:{ Throwable -> 0x0173 }
        L_0x0049:
            anetwork.channel.entity.c r3 = r10.f729c     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.aidl.adapter.ParcelableInputStreamImpl r3 = r3.f715c     // Catch:{ Throwable -> 0x0173 }
            if (r3 == 0) goto L_0x005a
            anetwork.channel.entity.c r3 = r10.f729c     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.aidl.adapter.ParcelableInputStreamImpl r3 = r3.f715c     // Catch:{ Throwable -> 0x0173 }
            r3.writeEnd()     // Catch:{ Throwable -> 0x0173 }
        L_0x005a:
            if (r1 == 0) goto L_0x0173
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0173 }
            r3.<init>()     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r4 = "[traceId:"
            r3.append(r4)     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r4 = r1.traceId     // Catch:{ Throwable -> 0x0173 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r4 = "]"
            r3.append(r4)     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r4 = "end, "
            r3.append(r4)     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r4 = r1.toString()     // Catch:{ Throwable -> 0x0173 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.entity.c r4 = r10.f729c     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r4 = r4.f714b     // Catch:{ Throwable -> 0x0173 }
            r5 = 0
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0173 }
            anet.channel.util.ALog.m327e(r0, r3, r4, r6)     // Catch:{ Throwable -> 0x0173 }
            java.util.concurrent.CopyOnWriteArrayList r3 = anet.channel.GlobalAppRuntimeInfo.getBucketInfo()     // Catch:{ Throwable -> 0x0173 }
            r4 = 1
            if (r3 == 0) goto L_0x00ae
            int r6 = r3.size()     // Catch:{ Throwable -> 0x0173 }
            r7 = 0
        L_0x0098:
            int r8 = r6 + -1
            if (r7 >= r8) goto L_0x00ae
            java.lang.Object r8 = r3.get(r7)     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Throwable -> 0x0173 }
            int r9 = r7 + 1
            java.lang.Object r9 = r3.get(r9)     // Catch:{ Throwable -> 0x0173 }
            r1.putExtra(r8, r9)     // Catch:{ Throwable -> 0x0173 }
            int r7 = r7 + 2
            goto L_0x0098
        L_0x00ae:
            boolean r3 = anet.channel.GlobalAppRuntimeInfo.isAppBackground()     // Catch:{ Throwable -> 0x0173 }
            if (r3 == 0) goto L_0x00c1
            java.lang.String r3 = "restrictBg"
            int r6 = anet.channel.status.NetworkStatusHelper.getRestrictBackgroundStatus()     // Catch:{ Throwable -> 0x0173 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x0173 }
            r1.putExtra(r3, r6)     // Catch:{ Throwable -> 0x0173 }
        L_0x00c1:
            anet.channel.fulltrace.IFullTraceAnalysis r3 = anet.channel.fulltrace.C0521a.m128a()     // Catch:{ Throwable -> 0x0173 }
            anet.channel.fulltrace.b r3 = r3.getSceneInfo()     // Catch:{ Throwable -> 0x0173 }
            if (r3 == 0) goto L_0x0104
            java.lang.String r6 = r3.toString()     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.entity.c r7 = r10.f729c     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r7 = r7.f714b     // Catch:{ Throwable -> 0x0173 }
            java.lang.Object[] r8 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0173 }
            anet.channel.util.ALog.m328i(r0, r6, r7, r8)     // Catch:{ Throwable -> 0x0173 }
            long r6 = r1.start     // Catch:{ Throwable -> 0x0173 }
            long r8 = r3.f260c     // Catch:{ Throwable -> 0x0173 }
            long r6 = r6 - r8
            r1.sinceInitTime = r6     // Catch:{ Throwable -> 0x0173 }
            int r0 = r3.f258a     // Catch:{ Throwable -> 0x0173 }
            r1.startType = r0     // Catch:{ Throwable -> 0x0173 }
            int r0 = r3.f258a     // Catch:{ Throwable -> 0x0173 }
            if (r0 == r4) goto L_0x00f0
            long r6 = r3.f260c     // Catch:{ Throwable -> 0x0173 }
            long r8 = r3.f261d     // Catch:{ Throwable -> 0x0173 }
            long r6 = r6 - r8
            r1.sinceLastLaunchTime = r6     // Catch:{ Throwable -> 0x0173 }
        L_0x00f0:
            int r0 = r3.f262e     // Catch:{ Throwable -> 0x0173 }
            r1.deviceLevel = r0     // Catch:{ Throwable -> 0x0173 }
            boolean r0 = r3.f259b     // Catch:{ Throwable -> 0x0173 }
            if (r0 == 0) goto L_0x00f9
            goto L_0x00fa
        L_0x00f9:
            r4 = 0
        L_0x00fa:
            r1.isFromExternal = r4     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r0 = r3.f263f     // Catch:{ Throwable -> 0x0173 }
            r1.speedBucket = r0     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r0 = r3.f264g     // Catch:{ Throwable -> 0x0173 }
            r1.abTestBucket = r0     // Catch:{ Throwable -> 0x0173 }
        L_0x0104:
            long r3 = r1.reqServiceTransmissionEnd     // Catch:{ Throwable -> 0x0173 }
            long r5 = r1.netReqStart     // Catch:{ Throwable -> 0x0173 }
            long r3 = r3 - r5
            r1.serializeTransferTime = r3     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.entity.c r0 = r10.f729c     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.entity.g r0 = r0.f717e     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r3 = "RequestUserInfo"
            java.lang.String r0 = r0.mo9329a((java.lang.String) r3)     // Catch:{ Throwable -> 0x0173 }
            r1.userInfo = r0     // Catch:{ Throwable -> 0x0173 }
            anet.channel.appmonitor.IAppMonitor r0 = anet.channel.appmonitor.AppMonitor.getInstance()     // Catch:{ Throwable -> 0x0173 }
            r0.commitStat(r1)     // Catch:{ Throwable -> 0x0173 }
            boolean r0 = anetwork.channel.config.NetworkConfigCenter.isRequestInMonitorList(r1)     // Catch:{ Throwable -> 0x0173 }
            if (r0 == 0) goto L_0x0132
            anet.channel.statist.RequestMonitor r0 = new anet.channel.statist.RequestMonitor     // Catch:{ Throwable -> 0x0173 }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x0173 }
            anet.channel.appmonitor.IAppMonitor r3 = anet.channel.appmonitor.AppMonitor.getInstance()     // Catch:{ Throwable -> 0x0173 }
            r3.commitStat(r0)     // Catch:{ Throwable -> 0x0173 }
        L_0x0132:
            java.lang.String r0 = r1.f408ip     // Catch:{ Exception -> 0x0159 }
            org.json.JSONObject r3 = r1.extra     // Catch:{ Exception -> 0x0159 }
            if (r3 != 0) goto L_0x0139
            goto L_0x0141
        L_0x0139:
            org.json.JSONObject r2 = r1.extra     // Catch:{ Exception -> 0x0159 }
            java.lang.String r3 = "firstIp"
            java.lang.String r2 = r2.optString(r3)     // Catch:{ Exception -> 0x0159 }
        L_0x0141:
            boolean r0 = anet.channel.strategy.utils.C0594c.m320b(r0)     // Catch:{ Exception -> 0x0159 }
            if (r0 != 0) goto L_0x014d
            boolean r0 = anet.channel.strategy.utils.C0594c.m320b(r2)     // Catch:{ Exception -> 0x0159 }
            if (r0 == 0) goto L_0x0159
        L_0x014d:
            anet.channel.statist.RequestMonitor r0 = new anet.channel.statist.RequestMonitor     // Catch:{ Exception -> 0x0159 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0159 }
            anet.channel.appmonitor.IAppMonitor r2 = anet.channel.appmonitor.AppMonitor.getInstance()     // Catch:{ Exception -> 0x0159 }
            r2.commitStat(r0)     // Catch:{ Exception -> 0x0159 }
        L_0x0159:
            anetwork.channel.stat.INetworkStat r0 = anetwork.channel.stat.NetworkStat.getNetworkStat()     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.entity.c r2 = r10.f729c     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.entity.g r2 = r2.f717e     // Catch:{ Throwable -> 0x0173 }
            java.lang.String r2 = r2.mo9337g()     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.aidl.DefaultFinishEvent r3 = r10.f727a     // Catch:{ Throwable -> 0x0173 }
            anetwork.channel.statist.StatisticData r3 = r3.getStatisticData()     // Catch:{ Throwable -> 0x0173 }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x0173 }
            anet.channel.detect.C0506n.m95a(r1)     // Catch:{ Throwable -> 0x0173 }
        L_0x0173:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.entity.C0639f.run():void");
    }
}
