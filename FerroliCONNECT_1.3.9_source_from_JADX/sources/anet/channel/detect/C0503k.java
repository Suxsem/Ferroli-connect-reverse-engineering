package anet.channel.detect;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.MtuDetectStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import org.android.netutil.PingEntry;
import org.android.netutil.PingResponse;
import org.android.netutil.PingTask;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* renamed from: anet.channel.detect.k */
/* compiled from: Taobao */
class C0503k {

    /* renamed from: a */
    private static HashMap<String, Long> f211a = new HashMap<>();

    C0503k() {
    }

    /* renamed from: a */
    public void mo8787a() {
        NetworkStatusHelper.addStatusChangeListener(new C0504l(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m92a(String str) {
        PingResponse pingResponse;
        String str2 = str;
        if (!AwcnConfig.isNetworkDetectEnable()) {
            ALog.m328i("anet.MTUDetector", "network detect closed.", (String) null, new Object[0]);
            return;
        }
        ALog.m328i("anet.MTUDetector", "mtuDetectTask start", (String) null, new Object[0]);
        SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        if (!TextUtils.isEmpty(str)) {
            long currentTimeMillis = System.currentTimeMillis();
            Long l = f211a.get(str2);
            if (l == null || currentTimeMillis >= l.longValue()) {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(GlobalAppRuntimeInfo.getContext());
                long j = defaultSharedPreferences.getLong("sp_mtu_" + str2, 0);
                if (currentTimeMillis < j) {
                    f211a.put(str2, Long.valueOf(j));
                    ALog.m328i("anet.MTUDetector", "mtuDetectTask in period of validity", (String) null, new Object[0]);
                    return;
                }
                List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost("guide-acs.m.taobao.com");
                String ip = (connStrategyListByHost == null || connStrategyListByHost.isEmpty()) ? null : connStrategyListByHost.get(0).getIp();
                if (!TextUtils.isEmpty(ip)) {
                    String str3 = ip;
                    Future launch = new PingTask(str3, 1000, 3, 0, 0).launch();
                    Future launch2 = new PingTask(str3, 1000, 3, 1172, 0).launch();
                    Future launch3 = new PingTask(str3, 1000, 3, 1272, 0).launch();
                    Future launch4 = new PingTask(str3, 1000, 3, 1372, 0).launch();
                    Future launch5 = new PingTask(str3, 1000, 3, 1432, 0).launch();
                    try {
                        pingResponse = (PingResponse) launch.get();
                    } catch (Exception unused) {
                        pingResponse = null;
                    }
                    if (pingResponse != null) {
                        if (pingResponse.getSuccessCnt() < 2) {
                            ALog.m327e("anet.MTUDetector", "MTU detect preTask error", (String) null, "errCode", Integer.valueOf(pingResponse.getErrcode()), "successCount", Integer.valueOf(pingResponse.getSuccessCnt()));
                            return;
                        }
                        m90a(1200, (Future<PingResponse>) launch2);
                        m90a(1300, (Future<PingResponse>) launch3);
                        m90a(1400, (Future<PingResponse>) launch4);
                        m90a(1460, (Future<PingResponse>) launch5);
                        long j2 = currentTimeMillis + 432000000;
                        f211a.put(str2, Long.valueOf(j2));
                        defaultSharedPreferences.edit().putLong("sp_mtu_" + str2, j2).apply();
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private void m90a(int i, Future<PingResponse> future) {
        PingResponse pingResponse;
        try {
            pingResponse = future.get();
        } catch (Exception unused) {
            pingResponse = null;
        }
        if (pingResponse != null) {
            int successCnt = pingResponse.getSuccessCnt();
            int i2 = 3 - successCnt;
            StringBuilder sb = new StringBuilder();
            PingEntry[] results = pingResponse.getResults();
            int length = results.length;
            for (int i3 = 0; i3 < length; i3++) {
                sb.append(results[i3].rtt);
                if (i3 != length - 1) {
                    sb.append(",");
                }
            }
            ALog.m328i("anet.MTUDetector", "MTU detect result", (String) null, "mtu", Integer.valueOf(i), "successCount", Integer.valueOf(successCnt), "timeoutCount", Integer.valueOf(i2));
            MtuDetectStat mtuDetectStat = new MtuDetectStat();
            mtuDetectStat.mtu = i;
            mtuDetectStat.pingSuccessCount = successCnt;
            mtuDetectStat.pingTimeoutCount = i2;
            mtuDetectStat.rtt = sb.toString();
            mtuDetectStat.errCode = pingResponse.getErrcode();
            AppMonitor.getInstance().commitStat(mtuDetectStat);
        }
    }
}
