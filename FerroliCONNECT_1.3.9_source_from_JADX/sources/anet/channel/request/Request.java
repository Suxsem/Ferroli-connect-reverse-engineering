package anet.channel.request;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.statist.RequestStatistic;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.text.Typography;

/* compiled from: Taobao */
public class Request {
    public static final String DEFAULT_CHARSET = "UTF-8";

    /* renamed from: a */
    public final RequestStatistic f322a;

    /* renamed from: b */
    private HttpUrl f323b;

    /* renamed from: c */
    private HttpUrl f324c;

    /* renamed from: d */
    private HttpUrl f325d;

    /* renamed from: e */
    private URL f326e;

    /* renamed from: f */
    private String f327f;

    /* renamed from: g */
    private Map<String, String> f328g;

    /* renamed from: h */
    private Map<String, String> f329h;

    /* renamed from: i */
    private String f330i;

    /* renamed from: j */
    private BodyEntry f331j;

    /* renamed from: k */
    private boolean f332k;

    /* renamed from: l */
    private String f333l;

    /* renamed from: m */
    private String f334m;

    /* renamed from: n */
    private int f335n;

    /* renamed from: o */
    private int f336o;

    /* renamed from: p */
    private int f337p;

    /* renamed from: q */
    private HostnameVerifier f338q;

    /* renamed from: r */
    private SSLSocketFactory f339r;

    /* renamed from: s */
    private boolean f340s;

    /* compiled from: Taobao */
    public static final class Method {
        public static final String DELETE = "DELETE";
        public static final String GET = "GET";
        public static final String HEAD = "HEAD";
        public static final String OPTION = "OPTIONS";
        public static final String POST = "POST";
        public static final String PUT = "PUT";

        /* renamed from: a */
        static boolean m189a(String str) {
            return str.equals("POST") || str.equals("PUT");
        }

        /* renamed from: b */
        static boolean m190b(String str) {
            return m189a(str) || str.equals("DELETE") || str.equals("OPTIONS");
        }
    }

    private Request(Builder builder) {
        this.f327f = "GET";
        this.f332k = true;
        this.f335n = 0;
        this.f336o = RestConstants.G_MAX_CONNECTION_TIME_OUT;
        this.f337p = RestConstants.G_MAX_CONNECTION_TIME_OUT;
        this.f327f = builder.f343c;
        this.f328g = builder.f344d;
        this.f329h = builder.f345e;
        this.f331j = builder.f347g;
        this.f330i = builder.f346f;
        this.f332k = builder.f348h;
        this.f335n = builder.f349i;
        this.f338q = builder.f350j;
        this.f339r = builder.f351k;
        this.f333l = builder.f352l;
        this.f334m = builder.f353m;
        this.f336o = builder.f354n;
        this.f337p = builder.f355o;
        this.f323b = builder.f341a;
        this.f324c = builder.f342b;
        if (this.f324c == null) {
            m154b();
        }
        this.f322a = builder.f356p != null ? builder.f356p : new RequestStatistic(getHost(), this.f333l);
        this.f340s = builder.f357q;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        String unused = builder.f343c = this.f327f;
        Map unused2 = builder.f344d = m153a();
        Map unused3 = builder.f345e = this.f329h;
        BodyEntry unused4 = builder.f347g = this.f331j;
        String unused5 = builder.f346f = this.f330i;
        boolean unused6 = builder.f348h = this.f332k;
        int unused7 = builder.f349i = this.f335n;
        HostnameVerifier unused8 = builder.f350j = this.f338q;
        SSLSocketFactory unused9 = builder.f351k = this.f339r;
        HttpUrl unused10 = builder.f341a = this.f323b;
        HttpUrl unused11 = builder.f342b = this.f324c;
        String unused12 = builder.f352l = this.f333l;
        String unused13 = builder.f353m = this.f334m;
        int unused14 = builder.f354n = this.f336o;
        int unused15 = builder.f355o = this.f337p;
        RequestStatistic unused16 = builder.f356p = this.f322a;
        boolean unused17 = builder.f357q = this.f340s;
        return builder;
    }

    /* renamed from: a */
    private Map<String, String> m153a() {
        if (AwcnConfig.isCookieHeaderRedundantFix()) {
            return new HashMap(this.f328g);
        }
        return this.f328g;
    }

    public HttpUrl getHttpUrl() {
        return this.f324c;
    }

    public String getUrlString() {
        return this.f324c.urlString();
    }

    public URL getUrl() {
        if (this.f326e == null) {
            HttpUrl httpUrl = this.f325d;
            if (httpUrl == null) {
                httpUrl = this.f324c;
            }
            this.f326e = httpUrl.toURL();
        }
        return this.f326e;
    }

    public void setDnsOptimize(String str, int i) {
        if (str != null) {
            if (this.f325d == null) {
                this.f325d = new HttpUrl(this.f324c);
            }
            this.f325d.replaceIpAndPort(str, i);
        } else {
            this.f325d = null;
        }
        this.f326e = null;
        this.f322a.setIPAndPort(str, i);
    }

    public void setUrlScheme(boolean z) {
        if (this.f325d == null) {
            this.f325d = new HttpUrl(this.f324c);
        }
        this.f325d.setScheme(z ? "https" : "http");
        this.f326e = null;
    }

    public int getRedirectTimes() {
        return this.f335n;
    }

    public String getHost() {
        return this.f324c.host();
    }

    public String getMethod() {
        return this.f327f;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.f328g);
    }

    public String getContentEncoding() {
        String str = this.f330i;
        return str != null ? str : "UTF-8";
    }

    public boolean isRedirectEnable() {
        return this.f332k;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.f338q;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.f339r;
    }

    public int postBody(OutputStream outputStream) throws IOException {
        BodyEntry bodyEntry = this.f331j;
        if (bodyEntry != null) {
            return bodyEntry.writeTo(outputStream);
        }
        return 0;
    }

    public byte[] getBodyBytes() {
        if (this.f331j == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);
        try {
            postBody(byteArrayOutputStream);
        } catch (IOException unused) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public boolean containsBody() {
        return this.f331j != null;
    }

    public String getBizId() {
        return this.f333l;
    }

    public String getSeq() {
        return this.f334m;
    }

    public int getReadTimeout() {
        return this.f337p;
    }

    public int getConnectTimeout() {
        return this.f336o;
    }

    public boolean isAllowRequestInBg() {
        return this.f340s;
    }

    /* renamed from: b */
    private void m154b() {
        String a = C0594c.m318a(this.f329h, getContentEncoding());
        if (!TextUtils.isEmpty(a)) {
            if (!Method.m189a(this.f327f) || this.f331j != null) {
                String urlString = this.f323b.urlString();
                StringBuilder sb = new StringBuilder(urlString);
                if (sb.indexOf("?") == -1) {
                    sb.append('?');
                } else if (urlString.charAt(urlString.length() - 1) != '&') {
                    sb.append(Typography.amp);
                }
                sb.append(a);
                HttpUrl parse = HttpUrl.parse(sb.toString());
                if (parse != null) {
                    this.f324c = parse;
                }
            } else {
                try {
                    this.f331j = new ByteArrayEntry(a.getBytes(getContentEncoding()));
                    Map<String, String> map = this.f328g;
                    map.put("Content-Type", "application/x-www-form-urlencoded; charset=" + getContentEncoding());
                } catch (UnsupportedEncodingException unused) {
                }
            }
        }
        if (this.f324c == null) {
            this.f324c = this.f323b;
        }
    }

    /* compiled from: Taobao */
    public static class Builder {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public HttpUrl f341a;
        /* access modifiers changed from: private */

        /* renamed from: b */
        public HttpUrl f342b;
        /* access modifiers changed from: private */

        /* renamed from: c */
        public String f343c = "GET";
        /* access modifiers changed from: private */

        /* renamed from: d */
        public Map<String, String> f344d = new HashMap();
        /* access modifiers changed from: private */

        /* renamed from: e */
        public Map<String, String> f345e;
        /* access modifiers changed from: private */

        /* renamed from: f */
        public String f346f;
        /* access modifiers changed from: private */

        /* renamed from: g */
        public BodyEntry f347g;
        /* access modifiers changed from: private */

        /* renamed from: h */
        public boolean f348h = true;
        /* access modifiers changed from: private */

        /* renamed from: i */
        public int f349i = 0;
        /* access modifiers changed from: private */

        /* renamed from: j */
        public HostnameVerifier f350j;
        /* access modifiers changed from: private */

        /* renamed from: k */
        public SSLSocketFactory f351k;
        /* access modifiers changed from: private */

        /* renamed from: l */
        public String f352l;
        /* access modifiers changed from: private */

        /* renamed from: m */
        public String f353m;
        /* access modifiers changed from: private */

        /* renamed from: n */
        public int f354n = RestConstants.G_MAX_CONNECTION_TIME_OUT;
        /* access modifiers changed from: private */

        /* renamed from: o */
        public int f355o = RestConstants.G_MAX_CONNECTION_TIME_OUT;
        /* access modifiers changed from: private */

        /* renamed from: p */
        public RequestStatistic f356p = null;
        /* access modifiers changed from: private */

        /* renamed from: q */
        public boolean f357q;

        public Builder setUrl(HttpUrl httpUrl) {
            this.f341a = httpUrl;
            this.f342b = null;
            return this;
        }

        public Builder setUrl(String str) {
            this.f341a = HttpUrl.parse(str);
            this.f342b = null;
            if (this.f341a != null) {
                return this;
            }
            throw new IllegalArgumentException("toURL is invalid! toURL = " + str);
        }

        public Builder setMethod(String str) {
            if (!TextUtils.isEmpty(str)) {
                if ("GET".equalsIgnoreCase(str)) {
                    this.f343c = "GET";
                } else if ("POST".equalsIgnoreCase(str)) {
                    this.f343c = "POST";
                } else if ("OPTIONS".equalsIgnoreCase(str)) {
                    this.f343c = "OPTIONS";
                } else if ("HEAD".equalsIgnoreCase(str)) {
                    this.f343c = "HEAD";
                } else if ("PUT".equalsIgnoreCase(str)) {
                    this.f343c = "PUT";
                } else if ("DELETE".equalsIgnoreCase(str)) {
                    this.f343c = "DELETE";
                } else {
                    this.f343c = "GET";
                }
                return this;
            }
            throw new IllegalArgumentException("method is null or empty");
        }

        public Builder setHeaders(Map<String, String> map) {
            this.f344d.clear();
            if (map != null) {
                this.f344d.putAll(map);
            }
            return this;
        }

        public Builder addHeader(String str, String str2) {
            this.f344d.put(str, str2);
            return this;
        }

        public Builder setParams(Map<String, String> map) {
            this.f345e = map;
            this.f342b = null;
            return this;
        }

        public Builder addParam(String str, String str2) {
            if (this.f345e == null) {
                this.f345e = new HashMap();
            }
            this.f345e.put(str, str2);
            this.f342b = null;
            return this;
        }

        public Builder setCharset(String str) {
            this.f346f = str;
            this.f342b = null;
            return this;
        }

        public Builder setBody(BodyEntry bodyEntry) {
            this.f347g = bodyEntry;
            return this;
        }

        public Builder setRedirectEnable(boolean z) {
            this.f348h = z;
            return this;
        }

        public Builder setRedirectTimes(int i) {
            this.f349i = i;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.f350j = hostnameVerifier;
            return this;
        }

        public Builder setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.f351k = sSLSocketFactory;
            return this;
        }

        public Builder setBizId(String str) {
            this.f352l = str;
            return this;
        }

        public Builder setSeq(String str) {
            this.f353m = str;
            return this;
        }

        public Builder setReadTimeout(int i) {
            if (i > 0) {
                this.f355o = i;
            }
            return this;
        }

        public Builder setConnectTimeout(int i) {
            if (i > 0) {
                this.f354n = i;
            }
            return this;
        }

        public Builder setRequestStatistic(RequestStatistic requestStatistic) {
            this.f356p = requestStatistic;
            return this;
        }

        public Builder setAllowRequestInBg(boolean z) {
            this.f357q = z;
            return this;
        }

        public Request build() {
            if (this.f347g == null && this.f345e == null && Method.m189a(this.f343c)) {
                ALog.m327e("awcn.Request", "method " + this.f343c + " must have a request body", (String) null, new Object[0]);
            }
            if (this.f347g != null && !Method.m190b(this.f343c)) {
                ALog.m327e("awcn.Request", "method " + this.f343c + " should not have a request body", (String) null, new Object[0]);
                this.f347g = null;
            }
            BodyEntry bodyEntry = this.f347g;
            if (!(bodyEntry == null || bodyEntry.getContentType() == null)) {
                addHeader("Content-Type", this.f347g.getContentType());
            }
            return new Request(this);
        }
    }
}
