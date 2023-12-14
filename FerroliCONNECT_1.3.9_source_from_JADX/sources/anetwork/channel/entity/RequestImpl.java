package anetwork.channel.entity;

import android.text.TextUtils;
import anet.channel.request.BodyEntry;
import anet.channel.util.ALog;
import anetwork.channel.Header;
import anetwork.channel.IBodyHandler;
import anetwork.channel.Param;
import anetwork.channel.Request;
import anetwork.channel.util.RequestConstant;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public class RequestImpl implements Request {
    @Deprecated

    /* renamed from: a */
    private URI f696a;
    @Deprecated

    /* renamed from: b */
    private URL f697b;

    /* renamed from: c */
    private String f698c;

    /* renamed from: d */
    private boolean f699d = true;

    /* renamed from: e */
    private List<Header> f700e;

    /* renamed from: f */
    private String f701f = "GET";

    /* renamed from: g */
    private List<Param> f702g;

    /* renamed from: h */
    private int f703h = 2;

    /* renamed from: i */
    private String f704i = "utf-8";

    /* renamed from: j */
    private BodyEntry f705j = null;

    /* renamed from: k */
    private int f706k;

    /* renamed from: l */
    private int f707l;

    /* renamed from: m */
    private String f708m;

    /* renamed from: n */
    private String f709n;

    /* renamed from: o */
    private Map<String, String> f710o;

    @Deprecated
    public IBodyHandler getBodyHandler() {
        return null;
    }

    public RequestImpl() {
    }

    @Deprecated
    public RequestImpl(URI uri) {
        this.f696a = uri;
        this.f698c = uri.toString();
    }

    @Deprecated
    public RequestImpl(URL url) {
        this.f697b = url;
        this.f698c = url.toString();
    }

    public RequestImpl(String str) {
        this.f698c = str;
    }

    @Deprecated
    public URI getURI() {
        URI uri = this.f696a;
        if (uri != null) {
            return uri;
        }
        String str = this.f698c;
        if (str != null) {
            try {
                this.f696a = new URI(str);
            } catch (Exception e) {
                ALog.m326e("anet.RequestImpl", "uri error", this.f709n, e, new Object[0]);
            }
        }
        return this.f696a;
    }

    @Deprecated
    public void setUri(URI uri) {
        this.f696a = uri;
    }

    @Deprecated
    public URL getURL() {
        URL url = this.f697b;
        if (url != null) {
            return url;
        }
        String str = this.f698c;
        if (str != null) {
            try {
                this.f697b = new URL(str);
            } catch (Exception e) {
                ALog.m326e("anet.RequestImpl", "url error", this.f709n, e, new Object[0]);
            }
        }
        return this.f697b;
    }

    @Deprecated
    public void setUrL(URL url) {
        this.f697b = url;
        this.f698c = url.toString();
    }

    public String getUrlString() {
        return this.f698c;
    }

    public boolean getFollowRedirects() {
        return this.f699d;
    }

    public void setFollowRedirects(boolean z) {
        this.f699d = z;
    }

    public List<Header> getHeaders() {
        return this.f700e;
    }

    public void setHeaders(List<Header> list) {
        this.f700e = list;
    }

    public void addHeader(String str, String str2) {
        if (str != null && str2 != null) {
            if (this.f700e == null) {
                this.f700e = new ArrayList();
            }
            this.f700e.add(new BasicHeader(str, str2));
        }
    }

    public void removeHeader(Header header) {
        List<Header> list = this.f700e;
        if (list != null) {
            list.remove(header);
        }
    }

    public void setHeader(Header header) {
        if (header != null) {
            if (this.f700e == null) {
                this.f700e = new ArrayList();
            }
            int i = 0;
            int size = this.f700e.size();
            while (true) {
                if (i >= size) {
                    break;
                }
                if (header.getName().equalsIgnoreCase(this.f700e.get(i).getName())) {
                    this.f700e.set(i, header);
                    break;
                }
                i++;
            }
            if (i < this.f700e.size()) {
                this.f700e.add(header);
            }
        }
    }

    public Header[] getHeaders(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (this.f700e == null) {
            return null;
        }
        for (int i = 0; i < this.f700e.size(); i++) {
            if (!(this.f700e.get(i) == null || this.f700e.get(i).getName() == null || !this.f700e.get(i).getName().equalsIgnoreCase(str))) {
                arrayList.add(this.f700e.get(i));
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        Header[] headerArr = new Header[arrayList.size()];
        arrayList.toArray(headerArr);
        return headerArr;
    }

    public String getMethod() {
        return this.f701f;
    }

    public void setMethod(String str) {
        this.f701f = str;
    }

    public int getRetryTime() {
        return this.f703h;
    }

    public void setRetryTime(int i) {
        this.f703h = i;
    }

    public String getCharset() {
        return this.f704i;
    }

    public void setCharset(String str) {
        this.f704i = str;
    }

    public List<Param> getParams() {
        return this.f702g;
    }

    public void setParams(List<Param> list) {
        this.f702g = list;
    }

    public BodyEntry getBodyEntry() {
        return this.f705j;
    }

    public void setBodyEntry(BodyEntry bodyEntry) {
        this.f705j = bodyEntry;
    }

    public void setBodyHandler(IBodyHandler iBodyHandler) {
        this.f705j = new BodyHandlerEntry(iBodyHandler);
    }

    public int getConnectTimeout() {
        return this.f706k;
    }

    public int getReadTimeout() {
        return this.f707l;
    }

    public void setConnectTimeout(int i) {
        this.f706k = i;
    }

    public void setReadTimeout(int i) {
        this.f707l = i;
    }

    @Deprecated
    public void setBizId(int i) {
        this.f708m = String.valueOf(i);
    }

    public void setBizId(String str) {
        this.f708m = str;
    }

    public String getBizId() {
        return this.f708m;
    }

    public void setSeqNo(String str) {
        this.f709n = str;
    }

    public String getSeqNo() {
        return this.f709n;
    }

    @Deprecated
    public boolean isCookieEnabled() {
        return !RequestConstant.FALSE.equals(getExtProperty(RequestConstant.ENABLE_COOKIE));
    }

    @Deprecated
    public void setCookieEnabled(boolean z) {
        setExtProperty(RequestConstant.ENABLE_COOKIE, z ? RequestConstant.TRUE : RequestConstant.FALSE);
    }

    public void setExtProperty(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (this.f710o == null) {
                this.f710o = new HashMap();
            }
            this.f710o.put(str, str2);
        }
    }

    public String getExtProperty(String str) {
        Map<String, String> map = this.f710o;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public Map<String, String> getExtProperties() {
        return this.f710o;
    }
}
