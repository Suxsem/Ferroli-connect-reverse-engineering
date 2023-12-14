package anet.channel.session;

import android.content.Context;
import anet.channel.AwcnConfig;
import anet.channel.Session;
import anet.channel.entity.C0517a;
import anet.channel.entity.C0518b;
import anet.channel.entity.ConnType;
import anet.channel.request.Request;
import anet.channel.strategy.utils.C0594c;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.C0604c;
import anet.channel.util.C0611j;
import anet.channel.util.Utils;
import com.taobao.accs.common.Constants;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: anet.channel.session.d */
/* compiled from: Taobao */
public class C0552d extends Session {

    /* renamed from: w */
    private SSLSocketFactory f393w;

    /* access modifiers changed from: protected */
    public Runnable getRecvTimeOutRunnable() {
        return null;
    }

    public C0552d(Context context, C0517a aVar) {
        super(context, aVar);
        if (this.f99k == null) {
            this.f98j = (this.f91c == null || !this.f91c.startsWith("https")) ? ConnType.HTTP : ConnType.HTTPS;
        } else if (AwcnConfig.isHttpsSniEnable() && this.f98j.equals(ConnType.HTTPS)) {
            this.f393w = new C0611j(this.f92d);
        }
    }

    public boolean isAvailable() {
        return this.f102n == 4;
    }

    public void connect() {
        try {
            if (this.f99k == null || this.f99k.getIpSource() != 1) {
                Request.Builder redirectEnable = new Request.Builder().setUrl(this.f91c).setSeq(this.f104p).setConnectTimeout((int) (((float) this.f106r) * Utils.getNetworkTimeFactor())).setReadTimeout((int) (((float) this.f107s) * Utils.getNetworkTimeFactor())).setRedirectEnable(false);
                if (this.f393w != null) {
                    redirectEnable.setSslSocketFactory(this.f393w);
                }
                if (this.f101m) {
                    redirectEnable.addHeader("Host", this.f93e);
                }
                if (C0604c.m347a() && C0594c.m319a(this.f93e)) {
                    try {
                        this.f94f = C0604c.m345a(this.f93e);
                    } catch (Exception unused) {
                    }
                }
                ALog.m328i("awcn.HttpSession", "HttpSession connect", (String) null, Constants.KEY_HOST, this.f91c, "ip", this.f94f, "port", Integer.valueOf(this.f95g));
                Request build = redirectEnable.build();
                build.setDnsOptimize(this.f94f, this.f95g);
                ThreadPoolExecutorFactory.submitPriorityTask(new C0553e(this, build), ThreadPoolExecutorFactory.Priority.LOW);
                return;
            }
            notifyStatus(4, new C0518b(1));
        } catch (Throwable th) {
            ALog.m326e("awcn.HttpSession", "HTTP connect fail.", (String) null, th, new Object[0]);
        }
    }

    public void close() {
        notifyStatus(6, (C0518b) null);
    }

    public void close(boolean z) {
        this.f108t = false;
        close();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:9|10|(1:14)|15|(2:(1:18)|19)|(1:21)|22|(2:24|(2:28|29))|30|31|(1:33)(1:34)|35|36|44) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x0073 */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0087 A[Catch:{ Throwable -> 0x00bd }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0099 A[Catch:{ Throwable -> 0x00bd }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public anet.channel.request.Cancelable request(anet.channel.request.Request r9, anet.channel.RequestCb r10) {
        /*
            r8 = this;
            anet.channel.request.b r0 = anet.channel.request.C0541b.NULL
            r1 = 0
            if (r9 == 0) goto L_0x0008
            anet.channel.statist.RequestStatistic r2 = r9.f322a
            goto L_0x000f
        L_0x0008:
            anet.channel.statist.RequestStatistic r2 = new anet.channel.statist.RequestStatistic
            java.lang.String r3 = r8.f92d
            r2.<init>(r3, r1)
        L_0x000f:
            anet.channel.entity.ConnType r3 = r8.f98j
            r2.setConnType(r3)
            long r3 = r2.start
            r5 = 0
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x0024
            long r3 = java.lang.System.currentTimeMillis()
            r2.reqStart = r3
            r2.start = r3
        L_0x0024:
            if (r9 == 0) goto L_0x00ce
            if (r10 != 0) goto L_0x002a
            goto L_0x00ce
        L_0x002a:
            javax.net.ssl.SSLSocketFactory r3 = r9.getSslSocketFactory()     // Catch:{ Throwable -> 0x00bd }
            if (r3 != 0) goto L_0x003e
            javax.net.ssl.SSLSocketFactory r3 = r8.f393w     // Catch:{ Throwable -> 0x00bd }
            if (r3 == 0) goto L_0x003e
            anet.channel.request.Request$Builder r1 = r9.newBuilder()     // Catch:{ Throwable -> 0x00bd }
            javax.net.ssl.SSLSocketFactory r3 = r8.f393w     // Catch:{ Throwable -> 0x00bd }
            anet.channel.request.Request$Builder r1 = r1.setSslSocketFactory(r3)     // Catch:{ Throwable -> 0x00bd }
        L_0x003e:
            boolean r3 = r8.f101m     // Catch:{ Throwable -> 0x00bd }
            if (r3 == 0) goto L_0x004f
            if (r1 != 0) goto L_0x0048
            anet.channel.request.Request$Builder r1 = r9.newBuilder()     // Catch:{ Throwable -> 0x00bd }
        L_0x0048:
            java.lang.String r3 = "Host"
            java.lang.String r4 = r8.f93e     // Catch:{ Throwable -> 0x00bd }
            r1.addHeader(r3, r4)     // Catch:{ Throwable -> 0x00bd }
        L_0x004f:
            if (r1 == 0) goto L_0x0055
            anet.channel.request.Request r9 = r1.build()     // Catch:{ Throwable -> 0x00bd }
        L_0x0055:
            java.lang.String r1 = r8.f94f     // Catch:{ Throwable -> 0x00bd }
            if (r1 != 0) goto L_0x0073
            anet.channel.util.HttpUrl r1 = r9.getHttpUrl()     // Catch:{ Throwable -> 0x00bd }
            java.lang.String r1 = r1.host()     // Catch:{ Throwable -> 0x00bd }
            boolean r3 = anet.channel.util.C0604c.m347a()     // Catch:{ Throwable -> 0x00bd }
            if (r3 == 0) goto L_0x0073
            boolean r3 = anet.channel.strategy.utils.C0594c.m319a((java.lang.String) r1)     // Catch:{ Throwable -> 0x00bd }
            if (r3 == 0) goto L_0x0073
            java.lang.String r1 = anet.channel.util.C0604c.m345a((java.lang.String) r1)     // Catch:{ Exception -> 0x0073 }
            r8.f94f = r1     // Catch:{ Exception -> 0x0073 }
        L_0x0073:
            java.lang.String r1 = r8.f94f     // Catch:{ Throwable -> 0x00bd }
            int r3 = r8.f95g     // Catch:{ Throwable -> 0x00bd }
            r9.setDnsOptimize(r1, r3)     // Catch:{ Throwable -> 0x00bd }
            anet.channel.entity.ConnType r1 = r8.f98j     // Catch:{ Throwable -> 0x00bd }
            boolean r1 = r1.isSSL()     // Catch:{ Throwable -> 0x00bd }
            r9.setUrlScheme(r1)     // Catch:{ Throwable -> 0x00bd }
            anet.channel.strategy.IConnStrategy r1 = r8.f99k     // Catch:{ Throwable -> 0x00bd }
            if (r1 == 0) goto L_0x0099
            anet.channel.statist.RequestStatistic r1 = r9.f322a     // Catch:{ Throwable -> 0x00bd }
            anet.channel.strategy.IConnStrategy r3 = r8.f99k     // Catch:{ Throwable -> 0x00bd }
            int r3 = r3.getIpSource()     // Catch:{ Throwable -> 0x00bd }
            anet.channel.strategy.IConnStrategy r4 = r8.f99k     // Catch:{ Throwable -> 0x00bd }
            int r4 = r4.getIpType()     // Catch:{ Throwable -> 0x00bd }
            r1.setIpInfo(r3, r4)     // Catch:{ Throwable -> 0x00bd }
            goto L_0x009f
        L_0x0099:
            anet.channel.statist.RequestStatistic r1 = r9.f322a     // Catch:{ Throwable -> 0x00bd }
            r3 = 1
            r1.setIpInfo(r3, r3)     // Catch:{ Throwable -> 0x00bd }
        L_0x009f:
            anet.channel.statist.RequestStatistic r1 = r9.f322a     // Catch:{ Throwable -> 0x00bd }
            java.lang.String r3 = r8.f100l     // Catch:{ Throwable -> 0x00bd }
            r1.unit = r3     // Catch:{ Throwable -> 0x00bd }
            anet.channel.request.b r1 = new anet.channel.request.b     // Catch:{ Throwable -> 0x00bd }
            anet.channel.session.f r3 = new anet.channel.session.f     // Catch:{ Throwable -> 0x00bd }
            r3.<init>(r8, r9, r10, r2)     // Catch:{ Throwable -> 0x00bd }
            int r4 = anet.channel.util.C0609h.m363a(r9)     // Catch:{ Throwable -> 0x00bd }
            java.util.concurrent.Future r3 = anet.channel.thread.ThreadPoolExecutorFactory.submitPriorityTask(r3, r4)     // Catch:{ Throwable -> 0x00bd }
            java.lang.String r9 = r9.getSeq()     // Catch:{ Throwable -> 0x00bd }
            r1.<init>(r3, r9)     // Catch:{ Throwable -> 0x00bd }
            r0 = r1
            goto L_0x00cd
        L_0x00bd:
            r9 = move-exception
            if (r10 == 0) goto L_0x00cd
            java.lang.String r9 = r9.toString()
            r1 = -101(0xffffffffffffff9b, float:NaN)
            java.lang.String r9 = anet.channel.util.ErrorConstant.formatMsg(r1, r9)
            r10.onFinish(r1, r9, r2)
        L_0x00cd:
            return r0
        L_0x00ce:
            if (r10 == 0) goto L_0x00d9
            r9 = -102(0xffffffffffffff9a, float:NaN)
            java.lang.String r1 = anet.channel.util.ErrorConstant.getErrMsg(r9)
            r10.onFinish(r9, r1, r2)
        L_0x00d9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.session.C0552d.request(anet.channel.request.Request, anet.channel.RequestCb):anet.channel.request.Cancelable");
    }
}
