package anetwork.channel.entity;

import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import anet.channel.util.Utils;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.util.C0658a;
import anetwork.channel.util.RequestConstant;
import java.util.HashMap;
import java.util.Map;

/* renamed from: anetwork.channel.entity.g */
/* compiled from: Taobao */
public class C0640g {

    /* renamed from: a */
    public int f730a = 0;

    /* renamed from: b */
    public RequestStatistic f731b;

    /* renamed from: c */
    public final int f732c;

    /* renamed from: d */
    public final int f733d;

    /* renamed from: e */
    public final String f734e;

    /* renamed from: f */
    public final int f735f;

    /* renamed from: g */
    private ParcelableRequest f736g;

    /* renamed from: h */
    private Request f737h = null;

    /* renamed from: i */
    private int f738i = 0;

    /* renamed from: j */
    private int f739j = 0;

    /* renamed from: k */
    private final boolean f740k;

    public C0640g(ParcelableRequest parcelableRequest, int i, boolean z) {
        if (parcelableRequest != null) {
            this.f736g = parcelableRequest;
            this.f735f = i;
            this.f740k = z;
            this.f734e = C0658a.m464a(parcelableRequest.seqNo, this.f735f == 0 ? "HTTP" : "DGRD");
            this.f732c = parcelableRequest.connectTimeout <= 0 ? (int) (Utils.getNetworkTimeFactor() * 12000.0f) : parcelableRequest.connectTimeout;
            this.f733d = parcelableRequest.readTimeout <= 0 ? (int) (Utils.getNetworkTimeFactor() * 12000.0f) : parcelableRequest.readTimeout;
            this.f739j = (parcelableRequest.retryTime < 0 || parcelableRequest.retryTime > 3) ? 2 : parcelableRequest.retryTime;
            HttpUrl l = m424l();
            this.f731b = new RequestStatistic(l.host(), String.valueOf(parcelableRequest.bizId));
            this.f731b.url = l.simpleUrlString();
            this.f737h = m422b(l);
            return;
        }
        throw new IllegalArgumentException("request is null");
    }

    /* renamed from: a */
    public Request mo9328a() {
        return this.f737h;
    }

    /* renamed from: a */
    public void mo9330a(Request request) {
        this.f737h = request;
    }

    /* renamed from: l */
    private HttpUrl m424l() {
        HttpUrl parse = HttpUrl.parse(this.f736g.url);
        if (parse != null) {
            if (!NetworkConfigCenter.isSSLEnabled()) {
                ALog.m328i("anet.RequestConfig", "request ssl disabled.", this.f734e, new Object[0]);
                parse.downgradeSchemeAndLock();
            } else if (RequestConstant.FALSE.equalsIgnoreCase(this.f736g.getExtProperty(RequestConstant.ENABLE_SCHEME_REPLACE))) {
                parse.lockScheme();
            }
            return parse;
        }
        throw new IllegalArgumentException("url is invalid. url=" + this.f736g.url);
    }

    /* renamed from: b */
    private Request m422b(HttpUrl httpUrl) {
        Request.Builder requestStatistic = new Request.Builder().setUrl(httpUrl).setMethod(this.f736g.method).setBody(this.f736g.bodyEntry).setReadTimeout(this.f733d).setConnectTimeout(this.f732c).setRedirectEnable(this.f736g.allowRedirect).setRedirectTimes(this.f738i).setBizId(this.f736g.bizId).setSeq(this.f734e).setRequestStatistic(this.f731b);
        requestStatistic.setParams(this.f736g.params);
        if (this.f736g.charset != null) {
            requestStatistic.setCharset(this.f736g.charset);
        }
        requestStatistic.setHeaders(m423c(httpUrl));
        return requestStatistic.build();
    }

    /* renamed from: b */
    public int mo9332b() {
        return this.f733d * (this.f739j + 1);
    }

    /* renamed from: c */
    public boolean mo9333c() {
        return this.f740k;
    }

    /* renamed from: a */
    public String mo9329a(String str) {
        return this.f736g.getExtProperty(str);
    }

    /* renamed from: d */
    public boolean mo9334d() {
        return this.f730a < this.f739j;
    }

    /* renamed from: e */
    public boolean mo9335e() {
        return NetworkConfigCenter.isHttpSessionEnable() && !RequestConstant.FALSE.equalsIgnoreCase(this.f736g.getExtProperty(RequestConstant.ENABLE_HTTP_DNS)) && (NetworkConfigCenter.isAllowHttpIpRetry() || this.f730a == 0);
    }

    /* renamed from: f */
    public HttpUrl mo9336f() {
        return this.f737h.getHttpUrl();
    }

    /* renamed from: g */
    public String mo9337g() {
        return this.f737h.getUrlString();
    }

    /* renamed from: h */
    public Map<String, String> mo9338h() {
        return this.f737h.getHeaders();
    }

    /* renamed from: c */
    private Map<String, String> m423c(HttpUrl httpUrl) {
        String host = httpUrl.host();
        boolean z = !C0594c.m319a(host);
        if (host.length() > 2 && host.charAt(0) == '[' && host.charAt(host.length() - 1) == ']' && C0594c.m320b(host.substring(1, host.length() - 1))) {
            z = false;
        }
        HashMap hashMap = new HashMap();
        if (this.f736g.headers != null) {
            for (Map.Entry next : this.f736g.headers.entrySet()) {
                String str = (String) next.getKey();
                if (!"Host".equalsIgnoreCase(str) && !":host".equalsIgnoreCase(str)) {
                    boolean equalsIgnoreCase = RequestConstant.TRUE.equalsIgnoreCase(this.f736g.getExtProperty(RequestConstant.KEEP_CUSTOM_COOKIE));
                    if (!"Cookie".equalsIgnoreCase(str) || equalsIgnoreCase) {
                        hashMap.put(str, next.getValue());
                    }
                } else if (!z) {
                    hashMap.put("Host", next.getValue());
                }
            }
        }
        return hashMap;
    }

    /* renamed from: i */
    public boolean mo9339i() {
        return !RequestConstant.FALSE.equalsIgnoreCase(this.f736g.getExtProperty(RequestConstant.ENABLE_COOKIE));
    }

    /* renamed from: j */
    public boolean mo9340j() {
        return RequestConstant.TRUE.equals(this.f736g.getExtProperty(RequestConstant.CHECK_CONTENT_LENGTH));
    }

    /* renamed from: k */
    public void mo9341k() {
        this.f730a++;
        this.f731b.retryTimes = this.f730a;
    }

    /* renamed from: a */
    public void mo9331a(HttpUrl httpUrl) {
        ALog.m328i("anet.RequestConfig", "redirect", this.f734e, "to url", httpUrl.toString());
        this.f738i++;
        this.f731b.url = httpUrl.simpleUrlString();
        this.f737h = m422b(httpUrl);
    }
}
