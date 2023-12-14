package anet.channel.util;

import anet.channel.strategy.utils.C0594c;
import java.net.MalformedURLException;
import java.net.URL;

/* compiled from: Taobao */
public class HttpUrl {
    private String host;
    private volatile boolean isSchemeLocked = false;
    private String path;
    private int port;
    private String scheme;
    private String simpleUrl;
    private String url;

    private HttpUrl() {
    }

    public HttpUrl(HttpUrl httpUrl) {
        this.scheme = httpUrl.scheme;
        this.host = httpUrl.host;
        this.path = httpUrl.path;
        this.url = httpUrl.url;
        this.simpleUrl = httpUrl.simpleUrl;
        this.isSchemeLocked = httpUrl.isSchemeLocked;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b3, code lost:
        if (r0.port <= 65535) goto L_0x00b6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0134  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static anet.channel.util.HttpUrl parse(java.lang.String r15) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r15)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.lang.String r15 = r15.trim()
            anet.channel.util.HttpUrl r0 = new anet.channel.util.HttpUrl
            r0.<init>()
            r0.url = r15
            java.lang.String r2 = "//"
            boolean r2 = r15.startsWith(r2)
            java.lang.String r8 = "http"
            java.lang.String r9 = "https"
            r10 = 0
            if (r2 == 0) goto L_0x0024
            r0.scheme = r1
            r2 = 0
            goto L_0x0045
        L_0x0024:
            r3 = 1
            r6 = 0
            r7 = 6
            r4 = 0
            java.lang.String r5 = "https:"
            r2 = r15
            boolean r2 = r2.regionMatches(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x0035
            r0.scheme = r9
            r2 = 6
            goto L_0x0045
        L_0x0035:
            r3 = 1
            r6 = 0
            r7 = 5
            r4 = 0
            java.lang.String r5 = "http:"
            r2 = r15
            boolean r2 = r2.regionMatches(r3, r4, r5, r6, r7)
            if (r2 == 0) goto L_0x014f
            r0.scheme = r8
            r2 = 5
        L_0x0045:
            int r3 = r15.length()
            int r2 = r2 + 2
            r4 = r2
            r5 = 0
        L_0x004d:
            r6 = 58
            r7 = 35
            r11 = 63
            r12 = 47
            if (r4 >= r3) goto L_0x007b
            char r13 = r15.charAt(r4)
            r14 = 91
            if (r13 != r14) goto L_0x0061
            r5 = 1
            goto L_0x0072
        L_0x0061:
            r14 = 93
            if (r13 != r14) goto L_0x0067
            r5 = 0
            goto L_0x0072
        L_0x0067:
            if (r13 == r12) goto L_0x0075
            if (r13 == r11) goto L_0x0075
            if (r13 == r7) goto L_0x0075
            if (r13 != r6) goto L_0x0072
            if (r5 != 0) goto L_0x0072
            goto L_0x0075
        L_0x0072:
            int r4 = r4 + 1
            goto L_0x004d
        L_0x0075:
            java.lang.String r5 = r15.substring(r2, r4)
            r0.host = r5
        L_0x007b:
            if (r4 != r3) goto L_0x0083
            java.lang.String r2 = r15.substring(r2)
            r0.host = r2
        L_0x0083:
            r2 = 0
        L_0x0084:
            if (r4 >= r3) goto L_0x009d
            char r5 = r15.charAt(r4)
            if (r5 != r6) goto L_0x0091
            if (r2 != 0) goto L_0x0091
            int r2 = r4 + 1
            goto L_0x0098
        L_0x0091:
            if (r5 == r12) goto L_0x009b
            if (r5 == r7) goto L_0x009b
            if (r5 != r11) goto L_0x0098
            goto L_0x009b
        L_0x0098:
            int r4 = r4 + 1
            goto L_0x0084
        L_0x009b:
            r5 = r4
            goto L_0x009e
        L_0x009d:
            r5 = r3
        L_0x009e:
            if (r2 == 0) goto L_0x00b6
            java.lang.String r2 = r15.substring(r2, r5)
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x00b5 }
            r0.port = r2     // Catch:{ NumberFormatException -> 0x00b5 }
            int r2 = r0.port     // Catch:{ NumberFormatException -> 0x00b5 }
            if (r2 <= 0) goto L_0x00b5
            int r2 = r0.port     // Catch:{ NumberFormatException -> 0x00b5 }
            r5 = 65535(0xffff, float:9.1834E-41)
            if (r2 <= r5) goto L_0x00b6
        L_0x00b5:
            return r1
        L_0x00b6:
            if (r4 >= r3) goto L_0x00ce
            char r2 = r15.charAt(r4)
            if (r2 != r12) goto L_0x00c2
            if (r10 != 0) goto L_0x00c2
            r10 = r4
            goto L_0x00c7
        L_0x00c2:
            if (r2 == r11) goto L_0x00ca
            if (r2 != r7) goto L_0x00c7
            goto L_0x00ca
        L_0x00c7:
            int r4 = r4 + 1
            goto L_0x00b6
        L_0x00ca:
            if (r10 == 0) goto L_0x00ce
            r2 = r4
            goto L_0x00cf
        L_0x00ce:
            r2 = r3
        L_0x00cf:
            if (r10 == 0) goto L_0x00d8
            java.lang.String r2 = r15.substring(r10, r2)
            r0.path = r2
            goto L_0x00da
        L_0x00d8:
            r0.path = r1
        L_0x00da:
            java.lang.String r2 = r0.scheme
            if (r2 != 0) goto L_0x00fa
            int r2 = r0.port
            r5 = 80
            if (r2 != r5) goto L_0x00e7
            r0.scheme = r8
            goto L_0x00fa
        L_0x00e7:
            r5 = 443(0x1bb, float:6.21E-43)
            if (r2 != r5) goto L_0x00ee
            r0.scheme = r9
            goto L_0x00fa
        L_0x00ee:
            anet.channel.strategy.IStrategyInstance r2 = anet.channel.strategy.StrategyCenter.getInstance()
            java.lang.String r5 = r0.host
            java.lang.String r2 = r2.getSchemeByHost(r5, r1)
            r0.scheme = r2
        L_0x00fa:
            java.lang.String r2 = r0.scheme
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x014f
            java.lang.String r2 = r0.host
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x010b
            goto L_0x014f
        L_0x010b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = r0.scheme
            r1.<init>(r2)
            java.lang.String r2 = "://"
            r1.append(r2)
            java.lang.String r2 = r0.host
            r1.append(r2)
            boolean r2 = r0.containsNonDefaultPort()
            if (r2 == 0) goto L_0x012c
            java.lang.String r2 = ":"
            r1.append(r2)
            int r2 = r0.port
            r1.append(r2)
        L_0x012c:
            java.lang.String r2 = r0.path
            if (r2 == 0) goto L_0x0134
            r1.append(r2)
            goto L_0x013b
        L_0x0134:
            if (r4 == r3) goto L_0x013b
            java.lang.String r2 = "/"
            r1.append(r2)
        L_0x013b:
            java.lang.String r2 = r1.toString()
            r0.simpleUrl = r2
            java.lang.String r15 = r15.substring(r4)
            r1.append(r15)
            java.lang.String r15 = r1.toString()
            r0.url = r15
            return r0
        L_0x014f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.HttpUrl.parse(java.lang.String):anet.channel.util.HttpUrl");
    }

    public String scheme() {
        return this.scheme;
    }

    public String host() {
        return this.host;
    }

    public String path() {
        return this.path;
    }

    public int getPort() {
        return this.port;
    }

    public String urlString() {
        return this.url;
    }

    public String simpleUrlString() {
        return this.simpleUrl;
    }

    public URL toURL() {
        try {
            return new URL(this.url);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    public boolean containsNonDefaultPort() {
        return this.port != 0 && (("http".equals(this.scheme) && this.port != 80) || ("https".equals(this.scheme) && this.port != 443));
    }

    public void downgradeSchemeAndLock() {
        this.isSchemeLocked = true;
        if (!"http".equals(this.scheme)) {
            this.scheme = "http";
            String str = this.scheme;
            String str2 = this.url;
            this.url = StringUtils.concatString(str, ":", str2.substring(str2.indexOf("//")));
        }
    }

    public boolean isSchemeLocked() {
        return this.isSchemeLocked;
    }

    public void lockScheme() {
        this.isSchemeLocked = true;
    }

    public void setScheme(String str) {
        if (!this.isSchemeLocked && !str.equalsIgnoreCase(this.scheme)) {
            this.scheme = str;
            String str2 = this.url;
            this.url = StringUtils.concatString(str, ":", str2.substring(str2.indexOf("//")));
            this.simpleUrl = StringUtils.concatString(str, ":", this.simpleUrl.substring(this.url.indexOf("//")));
        }
    }

    public void replaceIpAndPort(String str, int i) {
        if (str != null) {
            int indexOf = this.url.indexOf("//") + 2;
            while (indexOf < this.url.length() && this.url.charAt(indexOf) != '/') {
                indexOf++;
            }
            boolean b = C0594c.m320b(str);
            StringBuilder sb = new StringBuilder(this.url.length() + str.length());
            sb.append(this.scheme);
            sb.append(HttpConstant.SCHEME_SPLIT);
            if (b) {
                sb.append('[');
            }
            sb.append(str);
            if (b) {
                sb.append(']');
            }
            if (i != 0) {
                sb.append(':');
                sb.append(i);
            } else if (this.port != 0) {
                sb.append(':');
                sb.append(this.port);
            }
            sb.append(this.url.substring(indexOf));
            this.url = sb.toString();
        }
    }

    public String toString() {
        return this.url;
    }
}
