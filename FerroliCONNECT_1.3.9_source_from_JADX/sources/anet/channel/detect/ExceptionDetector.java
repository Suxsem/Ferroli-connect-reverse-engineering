package anet.channel.detect;

import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Pair;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import com.taobao.accs.common.Constants;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;
import org.android.netutil.NetUtils;
import org.android.netutil.PingEntry;
import org.android.netutil.PingResponse;
import org.android.netutil.PingTask;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.eclipse.jetty.util.security.Constraint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Taobao */
class ExceptionDetector {

    /* renamed from: a */
    long f179a;

    /* renamed from: b */
    String f180b;

    /* renamed from: c */
    String f181c;

    /* renamed from: d */
    String f182d;

    /* renamed from: e */
    LimitedQueue<Pair<String, Integer>> f183e = new LimitedQueue<>(10);

    ExceptionDetector() {
    }

    /* renamed from: a */
    public void mo8767a() {
        NetworkStatusHelper.addStatusChangeListener(new C0493a(this));
    }

    /* renamed from: a */
    public void mo8768a(RequestStatistic requestStatistic) {
        if (!AwcnConfig.isNetworkDetectEnable()) {
            ALog.m328i("anet.ExceptionDetector", "network detect closed.", (String) null, new Object[0]);
        } else {
            ThreadPoolExecutorFactory.submitDetectTask(new C0495c(this, requestStatistic));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public void mo8769b() throws JSONException {
        ALog.m327e("anet.ExceptionDetector", "network detect start.", (String) null, new Object[0]);
        SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        NetworkStatusHelper.NetworkStatus status = NetworkStatusHelper.getStatus();
        jSONObject2.put(NotificationCompat.CATEGORY_STATUS, status.getType());
        jSONObject2.put("subType", NetworkStatusHelper.getNetworkSubType());
        if (status != NetworkStatusHelper.NetworkStatus.NO) {
            if (status.isMobile()) {
                jSONObject2.put("apn", NetworkStatusHelper.getApn());
                jSONObject2.put(DispatchConstants.CARRIER, NetworkStatusHelper.getCarrier());
            } else {
                jSONObject2.put(DispatchConstants.BSSID, NetworkStatusHelper.getWifiBSSID());
                jSONObject2.put("ssid", NetworkStatusHelper.getWifiSSID());
            }
            jSONObject2.put("proxy", NetworkStatusHelper.getProxyType());
        }
        jSONObject.put("NetworkInfo", jSONObject2);
        String defaultGateway = status.isWifi() ? NetUtils.getDefaultGateway("114.114.114.114") : NetUtils.getPreferNextHop("114.114.114.114", 2);
        Future launch = !TextUtils.isEmpty(defaultGateway) ? new PingTask(defaultGateway, 1000, 3, 0, 0).launch() : null;
        C0492a a = m74a("guide-acs.m.taobao.com", this.f180b);
        C0492a a2 = m74a("gw.alicdn.com", this.f182d);
        C0492a a3 = m74a("msgacs.m.taobao.com", this.f181c);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("nextHop", defaultGateway);
        jSONObject3.put("ping", m77a((Future<PingResponse>) launch));
        jSONObject.put("LocalDetect", jSONObject3);
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(m76a(a));
        jSONArray.put(m76a(a2));
        jSONArray.put(m76a(a3));
        jSONObject.put("InternetDetect", jSONArray);
        JSONObject jSONObject4 = new JSONObject();
        Iterator it = this.f183e.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            jSONObject4.put((String) pair.first, pair.second);
        }
        jSONObject.put("BizDetect", jSONObject4);
        this.f183e.clear();
        ALog.m327e("anet.ExceptionDetector", "network detect result: " + jSONObject.toString(), (String) null, new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public boolean mo8770c() {
        boolean z = false;
        if (this.f183e.size() != 10) {
            return false;
        }
        if (NetworkStatusHelper.getStatus() == NetworkStatusHelper.NetworkStatus.NO) {
            ALog.m327e("anet.ExceptionDetector", "no network", (String) null, new Object[0]);
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < this.f179a) {
            return false;
        }
        Iterator it = this.f183e.iterator();
        int i = 0;
        while (it.hasNext()) {
            int intValue = ((Integer) ((Pair) it.next()).second).intValue();
            if (intValue == -202 || intValue == -400 || intValue == -401 || intValue == -405 || intValue == -406) {
                i++;
            }
        }
        if (i * 2 > 10) {
            z = true;
        }
        if (z) {
            this.f179a = currentTimeMillis + 1800000;
        }
        return z;
    }

    /* renamed from: a */
    private ArrayList<String> m75a(String str, int i) {
        PingResponse pingResponse;
        ArrayList<String> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        int i2 = 0;
        while (i2 < i) {
            i2++;
            try {
                pingResponse = (PingResponse) new PingTask(str, 0, 1, 0, i2).launch().get();
            } catch (Exception unused) {
                pingResponse = null;
            }
            StringBuilder sb = new StringBuilder();
            if (pingResponse != null) {
                String lastHopIPStr = pingResponse.getLastHopIPStr();
                double d = pingResponse.getResults()[0].rtt;
                int errcode = pingResponse.getErrcode();
                if (TextUtils.isEmpty(lastHopIPStr)) {
                    lastHopIPStr = Constraint.ANY_ROLE;
                }
                sb.append("hop=");
                sb.append(lastHopIPStr);
                sb.append(",rtt=");
                sb.append(d);
                sb.append(",errCode=");
                sb.append(errcode);
            }
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    /* renamed from: a */
    private C0492a m74a(String str, String str2) {
        C0492a aVar = new C0492a(this, (C0493a) null);
        aVar.f186a = str;
        try {
            aVar.f187b = InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException unused) {
        }
        if (!TextUtils.isEmpty(str2)) {
            aVar.f188c = str2;
        } else {
            List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(str);
            if (connStrategyListByHost != null && !connStrategyListByHost.isEmpty()) {
                aVar.f188c = connStrategyListByHost.get(0).getIp();
            }
        }
        String str3 = !TextUtils.isEmpty(aVar.f188c) ? aVar.f188c : aVar.f187b;
        if (!TextUtils.isEmpty(str3)) {
            String str4 = str3;
            aVar.f189d = new PingTask(str4, 1000, 3, 0, 0).launch();
            aVar.f190e = new PingTask(str4, 1000, 3, 1172, 0).launch();
            aVar.f191f = new PingTask(str4, 1000, 3, 1432, 0).launch();
        }
        return aVar;
    }

    /* renamed from: a */
    private JSONObject m76a(C0492a aVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!(aVar == null || aVar.f189d == null)) {
            jSONObject.put(Constants.KEY_HOST, aVar.f186a);
            jSONObject.put("currentIp", aVar.f188c);
            jSONObject.put("localIp", aVar.f187b);
            jSONObject.put("ping", m77a(aVar.f189d));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("1200", m77a(aVar.f190e));
            jSONObject2.put("1460", m77a(aVar.f191f));
            jSONObject.put("MTU", jSONObject2);
            if ("guide-acs.m.taobao.com".equals(aVar.f186a)) {
                ArrayList<String> a = m75a(!TextUtils.isEmpty(aVar.f188c) ? aVar.f188c : aVar.f187b, 5);
                JSONObject jSONObject3 = new JSONObject();
                int i = 0;
                while (i < a.size()) {
                    int i2 = i + 1;
                    jSONObject3.put(String.valueOf(i2), a.get(i));
                    i = i2;
                }
                jSONObject.put("traceRoute", jSONObject3);
            }
        }
        return jSONObject;
    }

    /* renamed from: a */
    private JSONObject m77a(Future<PingResponse> future) throws JSONException {
        PingResponse pingResponse;
        JSONObject jSONObject = new JSONObject();
        if (future == null) {
            return jSONObject;
        }
        try {
            pingResponse = future.get();
        } catch (Exception unused) {
            pingResponse = null;
        }
        if (pingResponse == null) {
            return jSONObject;
        }
        jSONObject.put("errCode", pingResponse.getErrcode());
        JSONArray jSONArray = new JSONArray();
        for (PingEntry pingEntry : pingResponse.getResults()) {
            jSONArray.put("seq=" + pingEntry.seq + ",hop=" + pingEntry.hop + ",rtt=" + pingEntry.rtt);
        }
        jSONObject.put("response", jSONArray);
        return jSONObject;
    }

    /* compiled from: Taobao */
    private class LimitedQueue<E> extends LinkedList<E> {

        /* renamed from: b */
        private int f185b;

        public LimitedQueue(int i) {
            this.f185b = i;
        }

        public boolean add(E e) {
            boolean add = super.add(e);
            while (add && size() > this.f185b) {
                super.remove();
            }
            return add;
        }
    }

    /* renamed from: anet.channel.detect.ExceptionDetector$a */
    /* compiled from: Taobao */
    private class C0492a {

        /* renamed from: a */
        String f186a;

        /* renamed from: b */
        String f187b;

        /* renamed from: c */
        String f188c;

        /* renamed from: d */
        Future<PingResponse> f189d;

        /* renamed from: e */
        Future<PingResponse> f190e;

        /* renamed from: f */
        Future<PingResponse> f191f;

        private C0492a() {
        }

        /* synthetic */ C0492a(ExceptionDetector exceptionDetector, C0493a aVar) {
            this();
        }
    }
}
