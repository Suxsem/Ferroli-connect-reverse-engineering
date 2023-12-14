package anet.channel.session;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.Config;
import anet.channel.DataFrameCb;
import anet.channel.IAuth;
import anet.channel.RequestCb;
import anet.channel.Session;
import anet.channel.SessionInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.bytes.C0485a;
import anet.channel.entity.C0517a;
import anet.channel.entity.C0518b;
import anet.channel.heartbeat.C0528c;
import anet.channel.heartbeat.HeartbeatManager;
import anet.channel.heartbeat.IHeartbeat;
import anet.channel.request.Request;
import anet.channel.security.ISecurity;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.SessionMonitor;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpHelper;
import com.taobao.accs.common.Constants;
import java.util.List;
import java.util.Map;
import kotlin.UByte;
import org.android.agoo.common.AgooConstants;
import org.android.spdy.SessionCb;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;

/* compiled from: Taobao */
public class TnetSpdySession extends Session implements SessionCb {

    /* renamed from: A */
    protected long f369A = 0;

    /* renamed from: B */
    protected int f370B = -1;

    /* renamed from: C */
    protected DataFrameCb f371C = null;

    /* renamed from: D */
    protected IHeartbeat f372D = null;

    /* renamed from: E */
    protected IAuth f373E = null;

    /* renamed from: F */
    protected String f374F = null;

    /* renamed from: G */
    protected ISecurity f375G = null;
    /* access modifiers changed from: private */

    /* renamed from: H */
    public int f376H = 0;
    /* access modifiers changed from: private */

    /* renamed from: I */
    public boolean f377I = false;

    /* renamed from: w */
    protected SpdyAgent f378w;

    /* renamed from: x */
    protected SpdySession f379x;

    /* renamed from: y */
    protected volatile boolean f380y = false;

    /* renamed from: z */
    protected long f381z;

    public void bioPingRecvCallback(SpdySession spdySession, int i) {
    }

    /* renamed from: e */
    static /* synthetic */ int m207e(TnetSpdySession tnetSpdySession) {
        int i = tnetSpdySession.f376H + 1;
        tnetSpdySession.f376H = i;
        return i;
    }

    public TnetSpdySession(Context context, C0517a aVar) {
        super(context, aVar);
    }

    public void initConfig(Config config) {
        if (config != null) {
            this.f374F = config.getAppkey();
            this.f375G = config.getSecurity();
        }
    }

    public void initSessionInfo(SessionInfo sessionInfo) {
        if (sessionInfo != null) {
            this.f371C = sessionInfo.dataFrameCb;
            this.f373E = sessionInfo.auth;
            if (sessionInfo.isKeepAlive) {
                this.f105q.isKL = 1;
                this.f108t = true;
                this.f372D = sessionInfo.heartbeat;
                this.f377I = sessionInfo.isAccs;
                if (this.f372D == null) {
                    if (!sessionInfo.isAccs || AwcnConfig.isAccsSessionCreateForbiddenInBg()) {
                        this.f372D = HeartbeatManager.getDefaultHeartbeat();
                    } else {
                        this.f372D = HeartbeatManager.getDefaultBackgroundAccsHeartbeat();
                    }
                }
            }
        }
        if (AwcnConfig.isIdleSessionCloseEnable() && this.f372D == null) {
            this.f372D = new C0528c();
        }
    }

    public void setTnetPublicKey(int i) {
        this.f370B = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0113 A[SYNTHETIC, Splitter:B:36:0x0113] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0125 A[Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x016f A[Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01a9 A[Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public anet.channel.request.Cancelable request(anet.channel.request.Request r25, anet.channel.RequestCb r26) {
        /*
            r24 = this;
            r1 = r24
            r0 = r25
            r2 = r26
            java.lang.String r3 = "Host"
            java.lang.String r4 = "awcn.TnetSpdySession"
            anet.channel.request.c r5 = anet.channel.request.C0542c.NULL
            if (r0 == 0) goto L_0x0011
            anet.channel.statist.RequestStatistic r6 = r0.f322a
            goto L_0x0019
        L_0x0011:
            anet.channel.statist.RequestStatistic r6 = new anet.channel.statist.RequestStatistic
            java.lang.String r7 = r1.f92d
            r8 = 0
            r6.<init>(r7, r8)
        L_0x0019:
            anet.channel.entity.ConnType r7 = r1.f98j
            r6.setConnType(r7)
            long r7 = r6.start
            r9 = 0
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 != 0) goto L_0x002e
            long r7 = java.lang.System.currentTimeMillis()
            r6.reqStart = r7
            r6.start = r7
        L_0x002e:
            java.lang.String r7 = r1.f94f
            int r8 = r1.f95g
            r6.setIPAndPort(r7, r8)
            anet.channel.strategy.IConnStrategy r7 = r1.f99k
            int r7 = r7.getIpSource()
            r6.ipRefer = r7
            anet.channel.strategy.IConnStrategy r7 = r1.f99k
            int r7 = r7.getIpType()
            r6.ipType = r7
            java.lang.String r7 = r1.f100l
            r6.unit = r7
            if (r0 == 0) goto L_0x0200
            if (r2 != 0) goto L_0x004f
            goto L_0x0200
        L_0x004f:
            r7 = 0
            r8 = 2
            org.android.spdy.SpdySession r9 = r1.f379x     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r9 == 0) goto L_0x01b5
            int r9 = r1.f102n     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r9 == 0) goto L_0x005e
            int r9 = r1.f102n     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10 = 4
            if (r9 != r10) goto L_0x01b5
        L_0x005e:
            boolean r9 = r1.f101m     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r9 == 0) goto L_0x0069
            java.lang.String r9 = r1.f93e     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            int r10 = r1.f95g     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r0.setDnsOptimize(r9, r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
        L_0x0069:
            anet.channel.entity.ConnType r9 = r1.f98j     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            boolean r9 = r9.isSSL()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r0.setUrlScheme(r9)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.net.URL r11 = r25.getUrl()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            boolean r9 = anet.channel.util.ALog.isPrintLog(r8)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r15 = ""
            r21 = 1
            if (r9 == 0) goto L_0x00b9
            java.lang.String r9 = r25.getSeq()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = "request URL"
            r10[r7] = r12     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = r11.toString()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10[r21] = r12     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.util.ALog.m328i(r4, r15, r9, r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r9 = r25.getSeq()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = "request Method"
            r10[r7] = r12     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = r25.getMethod()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10[r21] = r12     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.util.ALog.m328i(r4, r15, r9, r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r9 = r25.getSeq()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = "request headers"
            r10[r7] = r12     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.util.Map r12 = r25.getHeaders()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10[r21] = r12     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.util.ALog.m328i(r4, r15, r9, r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
        L_0x00b9:
            java.lang.String r9 = r1.f96h     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            boolean r9 = android.text.TextUtils.isEmpty(r9)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r9 != 0) goto L_0x00ed
            int r9 = r1.f97i     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r9 > 0) goto L_0x00c6
            goto L_0x00ed
        L_0x00c6:
            org.android.spdy.SpdyRequest r9 = new org.android.spdy.SpdyRequest     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = r11.getHost()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            int r13 = r11.getPort()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r14 = r1.f96h     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            int r10 = r1.f97i     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r16 = r25.getMethod()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            org.android.spdy.RequestPriority r17 = org.android.spdy.RequestPriority.DEFAULT_PRIORITY     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r18 = -1
            int r19 = r25.getConnectTimeout()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r20 = 0
            r22 = r10
            r10 = r9
            r23 = r15
            r15 = r22
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            goto L_0x0100
        L_0x00ed:
            r23 = r15
            org.android.spdy.SpdyRequest r9 = new org.android.spdy.SpdyRequest     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = r25.getMethod()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            org.android.spdy.RequestPriority r13 = org.android.spdy.RequestPriority.DEFAULT_PRIORITY     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r14 = -1
            int r15 = r25.getConnectTimeout()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10 = r9
            r10.<init>((java.net.URL) r11, (java.lang.String) r12, (org.android.spdy.RequestPriority) r13, (int) r14, (int) r15)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
        L_0x0100:
            int r10 = r25.getReadTimeout()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r9.setRequestRdTimeoutMs(r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.util.Map r10 = r25.getHeaders()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            boolean r11 = r10.containsKey(r3)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r12 = ":host"
            if (r11 != 0) goto L_0x0125
            r9.addHeaders(r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            boolean r3 = r1.f101m     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r3 == 0) goto L_0x011d
            java.lang.String r3 = r1.f93e     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            goto L_0x0121
        L_0x011d:
            java.lang.String r3 = r25.getHost()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
        L_0x0121:
            r9.addHeader(r12, r3)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            goto L_0x0140
        L_0x0125:
            java.util.HashMap r10 = new java.util.HashMap     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.util.Map r11 = r25.getHeaders()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10.<init>(r11)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.Object r3 = r10.remove(r3)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            boolean r11 = r1.f101m     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r11 == 0) goto L_0x013a
            java.lang.String r3 = r1.f93e     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
        L_0x013a:
            r10.put(r12, r3)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r9.addHeaders(r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
        L_0x0140:
            byte[] r3 = r25.getBodyBytes()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            org.android.spdy.SpdyDataProvider r10 = new org.android.spdy.SpdyDataProvider     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10.<init>((byte[]) r3)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.statist.RequestStatistic r3 = r0.f322a     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r3.sendStart = r11     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.statist.RequestStatistic r3 = r0.f322a     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.statist.RequestStatistic r11 = r0.f322a     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            long r11 = r11.sendStart     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.statist.RequestStatistic r13 = r0.f322a     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            long r13 = r13.start     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            long r11 = r11 - r13
            r3.processTime = r11     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            org.android.spdy.SpdySession r3 = r1.f379x     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.session.TnetSpdySession$a r11 = new anet.channel.session.TnetSpdySession$a     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r11.<init>(r0, r2)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            int r3 = r3.submitRequest(r9, r10, r1, r11)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            boolean r9 = anet.channel.util.ALog.isPrintLog(r21)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            if (r9 == 0) goto L_0x0184
            java.lang.String r9 = r25.getSeq()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r11 = "streamId"
            r10[r7] = r11     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r3)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r10[r21] = r11     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r11 = r23
            anet.channel.util.ALog.m325d(r4, r11, r9, r10)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
        L_0x0184:
            anet.channel.request.c r9 = new anet.channel.request.c     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            org.android.spdy.SpdySession r10 = r1.f379x     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            java.lang.String r0 = r25.getSeq()     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r9.<init>(r10, r3, r0)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.statist.SessionStatistic r0 = r1.f105q     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            long r10 = r0.requestCount     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            r12 = 1
            long r10 = r10 + r12
            r0.requestCount = r10     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            anet.channel.statist.SessionStatistic r0 = r1.f105q     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            long r10 = r0.stdRCount     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            long r10 = r10 + r12
            r0.stdRCount = r10     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            r1.f381z = r10     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            anet.channel.heartbeat.IHeartbeat r0 = r1.f372D     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            if (r0 == 0) goto L_0x01ae
            anet.channel.heartbeat.IHeartbeat r0 = r1.f372D     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
            r0.reSchedule()     // Catch:{ SpdyErrorException -> 0x01b2, Exception -> 0x01b0 }
        L_0x01ae:
            r5 = r9
            goto L_0x01ff
        L_0x01b0:
            r5 = r9
            goto L_0x01c1
        L_0x01b2:
            r0 = move-exception
            r5 = r9
            goto L_0x01cc
        L_0x01b5:
            r3 = -301(0xfffffffffffffed3, float:NaN)
            java.lang.String r9 = anet.channel.util.ErrorConstant.getErrMsg(r3)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            anet.channel.statist.RequestStatistic r0 = r0.f322a     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            r2.onFinish(r3, r9, r0)     // Catch:{ SpdyErrorException -> 0x01cb, Exception -> 0x01c1 }
            goto L_0x01ff
        L_0x01c1:
            r0 = -101(0xffffffffffffff9b, float:NaN)
            java.lang.String r3 = anet.channel.util.ErrorConstant.getErrMsg(r0)
            r2.onFinish(r0, r3, r6)
            goto L_0x01ff
        L_0x01cb:
            r0 = move-exception
        L_0x01cc:
            int r3 = r0.SpdyErrorGetCode()
            r9 = -1104(0xfffffffffffffbb0, float:NaN)
            if (r3 == r9) goto L_0x01dc
            int r3 = r0.SpdyErrorGetCode()
            r9 = -1103(0xfffffffffffffbb1, float:NaN)
            if (r3 != r9) goto L_0x01ee
        L_0x01dc:
            java.lang.String r3 = r1.f104p
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.String r9 = "Send request on closed session!!!"
            anet.channel.util.ALog.m327e(r4, r9, r3, r7)
            r3 = 6
            anet.channel.entity.b r4 = new anet.channel.entity.b
            r4.<init>(r8)
            r1.notifyStatus(r3, r4)
        L_0x01ee:
            int r0 = r0.SpdyErrorGetCode()
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r3 = -300(0xfffffffffffffed4, float:NaN)
            java.lang.String r0 = anet.channel.util.ErrorConstant.formatMsg(r3, r0)
            r2.onFinish(r3, r0, r6)
        L_0x01ff:
            return r5
        L_0x0200:
            if (r2 == 0) goto L_0x020b
            r0 = -102(0xffffffffffffff9a, float:NaN)
            java.lang.String r3 = anet.channel.util.ErrorConstant.getErrMsg(r0)
            r2.onFinish(r0, r3, r6)
        L_0x020b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.session.TnetSpdySession.request(anet.channel.request.Request, anet.channel.RequestCb):anet.channel.request.Cancelable");
    }

    public void sendCustomFrame(int i, byte[] bArr, int i2) {
        try {
            if (this.f371C != null) {
                ALog.m327e("awcn.TnetSpdySession", "sendCustomFrame", this.f104p, Constants.KEY_DATA_ID, Integer.valueOf(i), "type", Integer.valueOf(i2));
                if (this.f102n != 4 || this.f379x == null) {
                    ALog.m327e("awcn.TnetSpdySession", "sendCustomFrame", this.f104p, "sendCustomFrame con invalid mStatus:" + this.f102n);
                    m195a(i, ErrorConstant.ERROR_SESSION_INVALID, true, "session invalid");
                } else if (bArr == null || bArr.length <= 16384) {
                    this.f379x.sendCustomControlFrame(i, i2, 0, bArr == null ? 0 : bArr.length, bArr);
                    this.f105q.requestCount++;
                    this.f105q.cfRCount++;
                    this.f381z = System.currentTimeMillis();
                    if (this.f372D != null) {
                        this.f372D.reSchedule();
                    }
                } else {
                    m195a(i, ErrorConstant.ERROR_DATA_TOO_LARGE, false, (String) null);
                }
            }
        } catch (SpdyErrorException e) {
            ALog.m326e("awcn.TnetSpdySession", "sendCustomFrame error", this.f104p, e, new Object[0]);
            m195a(i, ErrorConstant.ERROR_TNET_EXCEPTION, true, "SpdyErrorException: " + e.toString());
        } catch (Exception e2) {
            ALog.m326e("awcn.TnetSpdySession", "sendCustomFrame error", this.f104p, e2, new Object[0]);
            m195a(i, -101, true, e2.toString());
        }
    }

    /* renamed from: a */
    private void m195a(int i, int i2, boolean z, String str) {
        DataFrameCb dataFrameCb = this.f371C;
        if (dataFrameCb != null) {
            dataFrameCb.onException(i, i2, z, str);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(17:6|7|8|(1:10)|11|(2:15|16)|17|18|(2:22|(1:24)(6:25|(1:27)(3:28|(1:30)(1:31)|32)|38|(1:42)|43|(2:45|46)(4:47|(1:49)(1:50)|51|58)))|33|(1:35)(1:36)|37|38|40|42|43|(0)(0)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0034 */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00e8 A[Catch:{ Throwable -> 0x0179 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00e9 A[Catch:{ Throwable -> 0x0179 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x013a A[Catch:{ Throwable -> 0x0179 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x014f A[Catch:{ Throwable -> 0x0179 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect() {
        /*
            r16 = this;
            r10 = r16
            java.lang.String r11 = "awcn.TnetSpdySession"
            int r0 = r10.f102n
            r12 = 1
            if (r0 == r12) goto L_0x0186
            int r0 = r10.f102n
            if (r0 == 0) goto L_0x0186
            int r0 = r10.f102n
            r1 = 4
            if (r0 != r1) goto L_0x0014
            goto L_0x0186
        L_0x0014:
            r13 = 0
            r14 = 2
            r15 = 0
            org.android.spdy.SpdyAgent r0 = r10.f378w     // Catch:{ Throwable -> 0x0179 }
            if (r0 != 0) goto L_0x001e
            r16.m203c()     // Catch:{ Throwable -> 0x0179 }
        L_0x001e:
            boolean r0 = anet.channel.util.C0604c.m347a()     // Catch:{ Throwable -> 0x0179 }
            if (r0 == 0) goto L_0x0034
            java.lang.String r0 = r10.f93e     // Catch:{ Throwable -> 0x0179 }
            boolean r0 = anet.channel.strategy.utils.C0594c.m319a((java.lang.String) r0)     // Catch:{ Throwable -> 0x0179 }
            if (r0 == 0) goto L_0x0034
            java.lang.String r0 = r10.f93e     // Catch:{ Exception -> 0x0034 }
            java.lang.String r0 = anet.channel.util.C0604c.m345a((java.lang.String) r0)     // Catch:{ Exception -> 0x0034 }
            r10.f94f = r0     // Catch:{ Exception -> 0x0034 }
        L_0x0034:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r7 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r0 = "connect"
            java.lang.String r2 = r10.f104p     // Catch:{ Throwable -> 0x0179 }
            r3 = 14
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = "host"
            r3[r15] = r4     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = r10.f91c     // Catch:{ Throwable -> 0x0179 }
            r3[r12] = r4     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = "ip"
            r3[r14] = r4     // Catch:{ Throwable -> 0x0179 }
            r4 = 3
            java.lang.String r5 = r10.f94f     // Catch:{ Throwable -> 0x0179 }
            r3[r4] = r5     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = "port"
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 5
            int r4 = r10.f95g     // Catch:{ Throwable -> 0x0179 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0179 }
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 6
            java.lang.String r4 = "sessionId"
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 7
            r3[r1] = r7     // Catch:{ Throwable -> 0x0179 }
            r1 = 8
            java.lang.String r4 = "SpdyProtocol,"
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 9
            anet.channel.entity.ConnType r4 = r10.f98j     // Catch:{ Throwable -> 0x0179 }
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 10
            java.lang.String r4 = "proxyIp,"
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 11
            java.lang.String r4 = r10.f96h     // Catch:{ Throwable -> 0x0179 }
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 12
            java.lang.String r4 = "proxyPort,"
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            r1 = 13
            int r4 = r10.f97i     // Catch:{ Throwable -> 0x0179 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x0179 }
            r3[r1] = r4     // Catch:{ Throwable -> 0x0179 }
            anet.channel.util.ALog.m327e(r11, r0, r2, r3)     // Catch:{ Throwable -> 0x0179 }
            org.android.spdy.SessionInfo r0 = new org.android.spdy.SessionInfo     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r2 = r10.f94f     // Catch:{ Throwable -> 0x0179 }
            int r3 = r10.f95g     // Catch:{ Throwable -> 0x0179 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0179 }
            r1.<init>()     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = r10.f91c     // Catch:{ Throwable -> 0x0179 }
            r1.append(r4)     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = "_"
            r1.append(r4)     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = r10.f374F     // Catch:{ Throwable -> 0x0179 }
            r1.append(r4)     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r4 = r1.toString()     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r5 = r10.f96h     // Catch:{ Throwable -> 0x0179 }
            int r6 = r10.f97i     // Catch:{ Throwable -> 0x0179 }
            anet.channel.entity.ConnType r1 = r10.f98j     // Catch:{ Throwable -> 0x0179 }
            int r9 = r1.getTnetConType()     // Catch:{ Throwable -> 0x0179 }
            r1 = r0
            r8 = r16
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0179 }
            int r1 = r10.f106r     // Catch:{ Throwable -> 0x0179 }
            float r1 = (float) r1     // Catch:{ Throwable -> 0x0179 }
            float r2 = anet.channel.util.Utils.getNetworkTimeFactor()     // Catch:{ Throwable -> 0x0179 }
            float r1 = r1 * r2
            int r1 = (int) r1     // Catch:{ Throwable -> 0x0179 }
            r0.setConnectionTimeoutMs(r1)     // Catch:{ Throwable -> 0x0179 }
            anet.channel.entity.ConnType r1 = r10.f98j     // Catch:{ Throwable -> 0x0179 }
            boolean r1 = r1.isPublicKeyAuto()     // Catch:{ Throwable -> 0x0179 }
            if (r1 != 0) goto L_0x010d
            anet.channel.entity.ConnType r1 = r10.f98j     // Catch:{ Throwable -> 0x0179 }
            boolean r1 = r1.isH2S()     // Catch:{ Throwable -> 0x0179 }
            if (r1 != 0) goto L_0x010d
            anet.channel.entity.ConnType r1 = r10.f98j     // Catch:{ Throwable -> 0x0179 }
            boolean r1 = r1.isHTTP3()     // Catch:{ Throwable -> 0x0179 }
            if (r1 == 0) goto L_0x00e9
            goto L_0x010d
        L_0x00e9:
            int r1 = r10.f370B     // Catch:{ Throwable -> 0x0179 }
            if (r1 < 0) goto L_0x00f3
            int r1 = r10.f370B     // Catch:{ Throwable -> 0x0179 }
            r0.setPubKeySeqNum(r1)     // Catch:{ Throwable -> 0x0179 }
            goto L_0x0119
        L_0x00f3:
            anet.channel.entity.ConnType r1 = r10.f98j     // Catch:{ Throwable -> 0x0179 }
            anet.channel.security.ISecurity r2 = r10.f375G     // Catch:{ Throwable -> 0x0179 }
            if (r2 == 0) goto L_0x0100
            anet.channel.security.ISecurity r2 = r10.f375G     // Catch:{ Throwable -> 0x0179 }
            boolean r2 = r2.isSecOff()     // Catch:{ Throwable -> 0x0179 }
            goto L_0x0101
        L_0x0100:
            r2 = 1
        L_0x0101:
            int r1 = r1.getTnetPublicKey(r2)     // Catch:{ Throwable -> 0x0179 }
            r10.f370B = r1     // Catch:{ Throwable -> 0x0179 }
            int r1 = r10.f370B     // Catch:{ Throwable -> 0x0179 }
            r0.setPubKeySeqNum(r1)     // Catch:{ Throwable -> 0x0179 }
            goto L_0x0119
        L_0x010d:
            boolean r1 = r10.f101m     // Catch:{ Throwable -> 0x0179 }
            if (r1 == 0) goto L_0x0114
            java.lang.String r1 = r10.f93e     // Catch:{ Throwable -> 0x0179 }
            goto L_0x0116
        L_0x0114:
            java.lang.String r1 = r10.f92d     // Catch:{ Throwable -> 0x0179 }
        L_0x0116:
            r0.setCertHost(r1)     // Catch:{ Throwable -> 0x0179 }
        L_0x0119:
            anet.channel.entity.ConnType r1 = r10.f98j     // Catch:{ Throwable -> 0x0179 }
            boolean r1 = r1.isHTTP3()     // Catch:{ Throwable -> 0x0179 }
            if (r1 == 0) goto L_0x012a
            int r1 = anet.channel.AwcnConfig.getXquicCongControl()     // Catch:{ Throwable -> 0x0179 }
            if (r1 < 0) goto L_0x012a
            r0.setXquicCongControl(r1)     // Catch:{ Throwable -> 0x0179 }
        L_0x012a:
            org.android.spdy.SpdyAgent r1 = r10.f378w     // Catch:{ Throwable -> 0x0179 }
            org.android.spdy.SpdySession r0 = r1.createSession(r0)     // Catch:{ Throwable -> 0x0179 }
            r10.f379x = r0     // Catch:{ Throwable -> 0x0179 }
            org.android.spdy.SpdySession r0 = r10.f379x     // Catch:{ Throwable -> 0x0179 }
            int r0 = r0.getRefCount()     // Catch:{ Throwable -> 0x0179 }
            if (r0 <= r12) goto L_0x014f
            java.lang.String r0 = "get session ref count > 1!!!"
            java.lang.String r1 = r10.f104p     // Catch:{ Throwable -> 0x0179 }
            java.lang.Object[] r2 = new java.lang.Object[r15]     // Catch:{ Throwable -> 0x0179 }
            anet.channel.util.ALog.m327e(r11, r0, r1, r2)     // Catch:{ Throwable -> 0x0179 }
            anet.channel.entity.b r0 = new anet.channel.entity.b     // Catch:{ Throwable -> 0x0179 }
            r0.<init>(r12)     // Catch:{ Throwable -> 0x0179 }
            r10.notifyStatus(r15, r0)     // Catch:{ Throwable -> 0x0179 }
            r16.mo8912b()     // Catch:{ Throwable -> 0x0179 }
            return
        L_0x014f:
            r10.notifyStatus(r12, r13)     // Catch:{ Throwable -> 0x0179 }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0179 }
            r10.f381z = r0     // Catch:{ Throwable -> 0x0179 }
            anet.channel.statist.SessionStatistic r0 = r10.f105q     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r1 = r10.f96h     // Catch:{ Throwable -> 0x0179 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0179 }
            if (r1 != 0) goto L_0x0163
            goto L_0x0164
        L_0x0163:
            r12 = 0
        L_0x0164:
            r0.isProxy = r12     // Catch:{ Throwable -> 0x0179 }
            anet.channel.statist.SessionStatistic r0 = r10.f105q     // Catch:{ Throwable -> 0x0179 }
            java.lang.String r1 = "false"
            r0.isTunnel = r1     // Catch:{ Throwable -> 0x0179 }
            anet.channel.statist.SessionStatistic r0 = r10.f105q     // Catch:{ Throwable -> 0x0179 }
            boolean r1 = anet.channel.GlobalAppRuntimeInfo.isAppBackground()     // Catch:{ Throwable -> 0x0179 }
            r0.isBackground = r1     // Catch:{ Throwable -> 0x0179 }
            r0 = 0
            r10.f369A = r0     // Catch:{ Throwable -> 0x0179 }
            goto L_0x0186
        L_0x0179:
            r0 = move-exception
            r10.notifyStatus(r14, r13)
            java.lang.String r1 = r10.f104p
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.String r3 = "connect exception "
            anet.channel.util.ALog.m326e(r11, r3, r1, r0, r2)
        L_0x0186:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.session.TnetSpdySession.connect():void");
    }

    public void close() {
        ALog.m327e("awcn.TnetSpdySession", "force close!", this.f104p, "session", this);
        notifyStatus(7, (C0518b) null);
        try {
            if (this.f372D != null) {
                this.f372D.stop();
                this.f372D = null;
            }
            if (this.f379x != null) {
                this.f379x.closeSession();
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void onDisconnect() {
        this.f380y = false;
    }

    /* access modifiers changed from: protected */
    public Runnable getRecvTimeOutRunnable() {
        return new C0556h(this);
    }

    public void ping(boolean z) {
        ping(z, this.f107s);
    }

    public void ping(boolean z, int i) {
        if (ALog.isPrintLog(1)) {
            ALog.m325d("awcn.TnetSpdySession", "ping", this.f104p, Constants.KEY_HOST, this.f91c, "thread", Thread.currentThread().getName());
        }
        if (z) {
            try {
                if (this.f379x == null) {
                    if (this.f105q != null) {
                        this.f105q.closeReason = "session null";
                    }
                    ALog.m327e("awcn.TnetSpdySession", this.f91c + " session null", this.f104p, new Object[0]);
                    close();
                } else if (this.f102n == 0 || this.f102n == 4) {
                    handleCallbacks(64, (C0518b) null);
                    if (!this.f380y) {
                        this.f380y = true;
                        this.f105q.ppkgCount++;
                        this.f379x.submitPing();
                        if (ALog.isPrintLog(1)) {
                            ALog.m325d("awcn.TnetSpdySession", this.f91c + " submit ping ms:" + (System.currentTimeMillis() - this.f381z) + " force:" + z, this.f104p, new Object[0]);
                        }
                        setPingTimeout(i);
                        this.f381z = System.currentTimeMillis();
                        if (this.f372D != null) {
                            this.f372D.reSchedule();
                        }
                    }
                }
            } catch (SpdyErrorException e) {
                if (e.SpdyErrorGetCode() == -1104 || e.SpdyErrorGetCode() == -1103) {
                    ALog.m327e("awcn.TnetSpdySession", "Send request on closed session!!!", this.f104p, new Object[0]);
                    notifyStatus(6, new C0518b(2));
                }
                ALog.m326e("awcn.TnetSpdySession", "ping", this.f104p, e, new Object[0]);
            } catch (Exception e2) {
                ALog.m326e("awcn.TnetSpdySession", "ping", this.f104p, e2, new Object[0]);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo8912b() {
        IAuth iAuth = this.f373E;
        if (iAuth != null) {
            iAuth.auth(this, new C0557i(this));
            return;
        }
        notifyStatus(4, (C0518b) null);
        this.f105q.ret = 1;
        IHeartbeat iHeartbeat = this.f372D;
        if (iHeartbeat != null) {
            iHeartbeat.start(this);
        }
    }

    public boolean isAvailable() {
        return this.f102n == 4;
    }

    /* renamed from: c */
    private void m203c() {
        SpdyAgent.enableDebug = false;
        this.f378w = SpdyAgent.getInstance(this.f89a, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        ISecurity iSecurity = this.f375G;
        if (iSecurity != null && !iSecurity.isSecOff()) {
            this.f378w.setAccsSslCallback(new C0558j(this));
        }
        if (!AwcnConfig.isTnetHeaderCacheEnable()) {
            try {
                this.f378w.getClass().getDeclaredMethod("disableHeaderCache", new Class[0]).invoke(this.f378w, new Object[0]);
                ALog.m328i("awcn.TnetSpdySession", "tnet disableHeaderCache", (String) null, new Object[0]);
            } catch (Exception e) {
                ALog.m326e("awcn.TnetSpdySession", "tnet disableHeaderCache", (String) null, e, new Object[0]);
            }
        }
    }

    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        this.f105q.connectionTime = (long) superviseConnectInfo.connectTime;
        this.f105q.sslTime = (long) superviseConnectInfo.handshakeTime;
        this.f105q.sslCalTime = (long) superviseConnectInfo.doHandshakeTime;
        this.f105q.netType = NetworkStatusHelper.getNetworkSubType();
        this.f369A = System.currentTimeMillis();
        notifyStatus(0, new C0518b(1));
        mo8912b();
        ALog.m327e("awcn.TnetSpdySession", "spdySessionConnectCB connect", this.f104p, "connectTime", Integer.valueOf(superviseConnectInfo.connectTime), "sslTime", Integer.valueOf(superviseConnectInfo.handshakeTime));
        if (this.f98j.isHTTP3()) {
            this.f105q.scid = superviseConnectInfo.scid;
            this.f105q.dcid = superviseConnectInfo.dcid;
            this.f105q.congControlKind = superviseConnectInfo.congControlKind;
            ALog.m327e("awcn.TnetSpdySession", "[HTTP3 spdySessionConnectCB]", this.f104p, "connectInfo", spdySession.getConnectInfoOnConnected());
        }
    }

    public void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        if (ALog.isPrintLog(2)) {
            ALog.m328i("awcn.TnetSpdySession", "ping receive", this.f104p, "Host", this.f91c, AgooConstants.MESSAGE_ID, Long.valueOf(j));
        }
        if (j >= 0) {
            this.f380y = false;
            this.f376H = 0;
            IHeartbeat iHeartbeat = this.f372D;
            if (iHeartbeat != null) {
                iHeartbeat.reSchedule();
            }
            handleCallbacks(128, (C0518b) null);
        }
    }

    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        ALog.m327e("awcn.TnetSpdySession", "[spdyCustomControlFrameRecvCallback]", this.f104p, "len", Integer.valueOf(i4), "frameCb", this.f371C);
        if (ALog.isPrintLog(1) && i4 < 512) {
            String str = "";
            for (int i5 = 0; i5 < bArr.length; i5++) {
                str = str + Integer.toHexString(bArr[i5] & UByte.MAX_VALUE) + " ";
            }
            ALog.m327e("awcn.TnetSpdySession", (String) null, this.f104p, "str", str);
        }
        DataFrameCb dataFrameCb = this.f371C;
        if (dataFrameCb != null) {
            dataFrameCb.onDataReceive(this, bArr, i, i2);
        } else {
            ALog.m327e("awcn.TnetSpdySession", "AccsFrameCb is null", this.f104p, new Object[0]);
            AppMonitor.getInstance().commitStat(new ExceptionStatistic(-105, (String) null, "rt"));
        }
        this.f105q.inceptCount++;
        IHeartbeat iHeartbeat = this.f372D;
        if (iHeartbeat != null) {
            iHeartbeat.reSchedule();
        }
    }

    public void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                ALog.m326e("awcn.TnetSpdySession", "[spdySessionFailedError]session clean up failed!", (String) null, e, new Object[0]);
            }
        }
        notifyStatus(2, new C0518b(256, i, "tnet connect fail"));
        ALog.m327e("awcn.TnetSpdySession", (String) null, this.f104p, " errorId:", Integer.valueOf(i));
        this.f105q.errorCode = (long) i;
        this.f105q.ret = 0;
        this.f105q.netType = NetworkStatusHelper.getNetworkSubType();
        AppMonitor.getInstance().commitStat(this.f105q);
        if (C0594c.m320b(this.f105q.f410ip)) {
            AppMonitor.getInstance().commitStat(new SessionMonitor(this.f105q));
        }
        AppMonitor.getInstance().commitAlarm(this.f105q.getAlarmObject());
    }

    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        ALog.m327e("awcn.TnetSpdySession", "spdySessionCloseCallback", this.f104p, " errorCode:", Integer.valueOf(i));
        IHeartbeat iHeartbeat = this.f372D;
        if (iHeartbeat != null) {
            iHeartbeat.stop();
            this.f372D = null;
        }
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                ALog.m326e("awcn.TnetSpdySession", "session clean up failed!", (String) null, e, new Object[0]);
            }
        }
        if (i == -3516) {
            ConnEvent connEvent = new ConnEvent();
            connEvent.isSuccess = false;
            StrategyCenter.getInstance().notifyConnEvent(this.f92d, this.f99k, connEvent);
        }
        notifyStatus(6, new C0518b(2));
        if (superviseConnectInfo != null) {
            this.f105q.requestCount = (long) superviseConnectInfo.reused_counter;
            this.f105q.liveTime = (long) superviseConnectInfo.keepalive_period_second;
            try {
                if (this.f98j.isHTTP3()) {
                    if (spdySession != null) {
                        ALog.m327e("awcn.TnetSpdySession", "[HTTP3 spdySessionCloseCallback]", this.f104p, "connectInfo", spdySession.getConnectInfoOnDisConnected());
                    }
                    this.f105q.xqc0RttStatus = superviseConnectInfo.xqc0RttStatus;
                    this.f105q.retransmissionRate = superviseConnectInfo.retransmissionRate;
                    this.f105q.lossRate = superviseConnectInfo.lossRate;
                    this.f105q.tlpCount = superviseConnectInfo.tlpCount;
                    this.f105q.rtoCount = superviseConnectInfo.rtoCount;
                    this.f105q.srtt = superviseConnectInfo.srtt;
                }
            } catch (Exception unused) {
            }
        }
        if (this.f105q.errorCode == 0) {
            this.f105q.errorCode = (long) i;
        }
        this.f105q.lastPingInterval = (int) (System.currentTimeMillis() - this.f381z);
        AppMonitor.getInstance().commitStat(this.f105q);
        if (C0594c.m320b(this.f105q.f410ip)) {
            AppMonitor.getInstance().commitStat(new SessionMonitor(this.f105q));
        }
        AppMonitor.getInstance().commitAlarm(this.f105q.getAlarmObject());
    }

    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        ALog.m327e("awcn.TnetSpdySession", "spdyCustomControlFrameFailCallback", this.f104p, Constants.KEY_DATA_ID, Integer.valueOf(i));
        m195a(i, i2, true, "tnet error");
    }

    public byte[] getSSLMeta(SpdySession spdySession) {
        String domain = spdySession.getDomain();
        if (TextUtils.isEmpty(domain)) {
            ALog.m328i("awcn.TnetSpdySession", "get sslticket host is null", (String) null, new Object[0]);
            return null;
        }
        try {
            if (this.f375G == null) {
                return null;
            }
            ISecurity iSecurity = this.f375G;
            Context context = this.f89a;
            return iSecurity.getBytes(context, "accs_ssl_key2_" + domain);
        } catch (Throwable th) {
            ALog.m326e("awcn.TnetSpdySession", "getSSLMeta", (String) null, th, new Object[0]);
            return null;
        }
    }

    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        String domain = spdySession.getDomain();
        if (TextUtils.isEmpty(domain)) {
            return -1;
        }
        int i = 0;
        try {
            if (this.f375G == null) {
                return -1;
            }
            ISecurity iSecurity = this.f375G;
            Context context = this.f89a;
            if (!iSecurity.saveBytes(context, "accs_ssl_key2_" + domain, bArr)) {
                i = -1;
            }
            return i;
        } catch (Throwable th) {
            ALog.m326e("awcn.TnetSpdySession", "putSSLMeta", (String) null, th, new Object[0]);
            return -1;
        }
    }

    /* renamed from: anet.channel.session.TnetSpdySession$a */
    /* compiled from: Taobao */
    private class C0547a extends C0548a {

        /* renamed from: b */
        private Request f383b;

        /* renamed from: c */
        private RequestCb f384c;

        /* renamed from: d */
        private int f385d = 0;

        /* renamed from: e */
        private long f386e = 0;

        public C0547a(Request request, RequestCb requestCb) {
            this.f383b = request;
            this.f384c = requestCb;
        }

        public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, Object obj) {
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.TnetSpdySession", "spdyDataChunkRecvCB", this.f383b.getSeq(), "len", Integer.valueOf(spdyByteArray.getDataLength()), "fin", Boolean.valueOf(z));
            }
            this.f386e += (long) spdyByteArray.getDataLength();
            this.f383b.f322a.recDataSize += (long) spdyByteArray.getDataLength();
            if (TnetSpdySession.this.f372D != null) {
                TnetSpdySession.this.f372D.reSchedule();
            }
            if (this.f384c != null) {
                ByteArray a = C0485a.C0486a.f171a.mo8742a(spdyByteArray.getByteArray(), spdyByteArray.getDataLength());
                spdyByteArray.recycle();
                this.f384c.onDataReceive(a, z);
            }
            TnetSpdySession.this.handleCallbacks(32, (C0518b) null);
        }

        public void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData) {
            String str;
            if (ALog.isPrintLog(1)) {
                ALog.m325d("awcn.TnetSpdySession", "spdyStreamCloseCallback", this.f383b.getSeq(), "streamId", Long.valueOf(j), Constants.KEY_ERROR_CODE, Integer.valueOf(i));
            }
            if (i != 0) {
                this.f385d = ErrorConstant.ERROR_TNET_REQUEST_FAIL;
                str = ErrorConstant.formatMsg(ErrorConstant.ERROR_TNET_REQUEST_FAIL, String.valueOf(i));
                if (i != -2005) {
                    AppMonitor.getInstance().commitStat(new ExceptionStatistic(ErrorConstant.ERROR_TNET_EXCEPTION, str, this.f383b.f322a, (Throwable) null));
                }
                ALog.m327e("awcn.TnetSpdySession", "spdyStreamCloseCallback error", this.f383b.getSeq(), "session", TnetSpdySession.this.f104p, "status code", Integer.valueOf(i), "URL", this.f383b.getHttpUrl().simpleUrlString());
            } else {
                str = HttpConstant.SUCCESS;
            }
            this.f383b.f322a.tnetErrorCode = i;
            m212a(superviseData, this.f385d, str);
            RequestCb requestCb = this.f384c;
            if (requestCb != null) {
                requestCb.onFinish(this.f385d, str, this.f383b.f322a);
            }
            if (i == -2004) {
                if (!TnetSpdySession.this.f380y) {
                    TnetSpdySession.this.ping(true);
                }
                if (TnetSpdySession.m207e(TnetSpdySession.this) >= 2) {
                    ConnEvent connEvent = new ConnEvent();
                    connEvent.isSuccess = false;
                    connEvent.isAccs = TnetSpdySession.this.f377I;
                    StrategyCenter.getInstance().notifyConnEvent(TnetSpdySession.this.f92d, TnetSpdySession.this.f99k, connEvent);
                    TnetSpdySession.this.close(true);
                }
            }
        }

        /* renamed from: a */
        private void m212a(SuperviseData superviseData, int i, String str) {
            try {
                this.f383b.f322a.rspEnd = System.currentTimeMillis();
                if (!this.f383b.f322a.isDone.get()) {
                    if (i > 0) {
                        this.f383b.f322a.ret = 1;
                    }
                    this.f383b.f322a.statusCode = i;
                    this.f383b.f322a.msg = str;
                    if (superviseData != null) {
                        this.f383b.f322a.rspEnd = superviseData.responseEnd;
                        this.f383b.f322a.sendBeforeTime = superviseData.sendStart - superviseData.requestStart;
                        this.f383b.f322a.sendDataTime = superviseData.sendEnd - this.f383b.f322a.sendStart;
                        this.f383b.f322a.firstDataTime = superviseData.responseStart - superviseData.sendEnd;
                        this.f383b.f322a.recDataTime = superviseData.responseEnd - superviseData.responseStart;
                        this.f383b.f322a.sendDataSize = (long) (superviseData.bodySize + superviseData.compressSize);
                        this.f383b.f322a.recDataSize = this.f386e + ((long) superviseData.recvUncompressSize);
                        this.f383b.f322a.reqHeadInflateSize = (long) superviseData.uncompressSize;
                        this.f383b.f322a.reqHeadDeflateSize = (long) superviseData.compressSize;
                        this.f383b.f322a.reqBodyInflateSize = (long) superviseData.bodySize;
                        this.f383b.f322a.reqBodyDeflateSize = (long) superviseData.bodySize;
                        this.f383b.f322a.rspHeadDeflateSize = (long) superviseData.recvCompressSize;
                        this.f383b.f322a.rspHeadInflateSize = (long) superviseData.recvUncompressSize;
                        this.f383b.f322a.rspBodyDeflateSize = (long) superviseData.recvBodySize;
                        this.f383b.f322a.rspBodyInflateSize = this.f386e;
                        if (this.f383b.f322a.contentLength == 0) {
                            this.f383b.f322a.contentLength = (long) superviseData.originContentLength;
                        }
                        TnetSpdySession.this.f105q.recvSizeCount += (long) (superviseData.recvBodySize + superviseData.recvCompressSize);
                        TnetSpdySession.this.f105q.sendSizeCount += (long) (superviseData.bodySize + superviseData.compressSize);
                    }
                }
            } catch (Exception unused) {
            }
        }

        public void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj) {
            this.f383b.f322a.firstDataTime = System.currentTimeMillis() - this.f383b.f322a.sendStart;
            this.f385d = HttpHelper.parseStatusCode(map);
            int unused = TnetSpdySession.this.f376H = 0;
            ALog.m328i("awcn.TnetSpdySession", "", this.f383b.getSeq(), "statusCode", Integer.valueOf(this.f385d));
            ALog.m328i("awcn.TnetSpdySession", "", this.f383b.getSeq(), "response headers", map);
            RequestCb requestCb = this.f384c;
            if (requestCb != null) {
                requestCb.onResponseCode(this.f385d, HttpHelper.cloneMap(map));
            }
            TnetSpdySession.this.handleCallbacks(16, (C0518b) null);
            this.f383b.f322a.contentEncoding = HttpHelper.getSingleHeaderFieldByKey(map, "Content-Encoding");
            this.f383b.f322a.contentType = HttpHelper.getSingleHeaderFieldByKey(map, "Content-Type");
            this.f383b.f322a.contentLength = (long) HttpHelper.parseContentLength(map);
            this.f383b.f322a.serverRT = HttpHelper.parseServerRT(map);
            TnetSpdySession.this.handleResponseCode(this.f383b, this.f385d);
            TnetSpdySession.this.handleResponseHeaders(this.f383b, map);
            if (TnetSpdySession.this.f372D != null) {
                TnetSpdySession.this.f372D.reSchedule();
            }
        }
    }
}
