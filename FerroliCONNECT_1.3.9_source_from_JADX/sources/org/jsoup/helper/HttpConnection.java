package org.jsoup.helper;

import anet.channel.util.HttpConstant;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import kotlin.text.Typography;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.TokenQueue;

public class HttpConnection implements Connection {
    public static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
    private static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final int HTTP_TEMP_REDIR = 307;
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String USER_AGENT = "User-Agent";
    private Connection.Request req = new Request();
    private Connection.Response res = new Response();

    public static Connection connect(String str) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(str);
        return httpConnection;
    }

    public static Connection connect(URL url) {
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.url(url);
        return httpConnection;
    }

    private static String encodeUrl(String str) {
        try {
            return encodeUrl(new URL(str)).toExternalForm();
        } catch (Exception unused) {
            return str;
        }
    }

    /* access modifiers changed from: private */
    public static URL encodeUrl(URL url) {
        try {
            return new URL(new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()).toASCIIString());
        } catch (Exception unused) {
            return url;
        }
    }

    /* access modifiers changed from: private */
    public static String encodeMimeName(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\"", "%22");
    }

    private HttpConnection() {
    }

    public Connection url(URL url) {
        this.req.url(url);
        return this;
    }

    public Connection url(String str) {
        Validate.notEmpty(str, "Must supply a valid URL");
        try {
            this.req.url(new URL(encodeUrl(str)));
            return this;
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + str, e);
        }
    }

    public Connection proxy(Proxy proxy) {
        this.req.proxy(proxy);
        return this;
    }

    public Connection proxy(String str, int i) {
        this.req.proxy(str, i);
        return this;
    }

    public Connection userAgent(String str) {
        Validate.notNull(str, "User agent must not be null");
        this.req.header("User-Agent", str);
        return this;
    }

    public Connection timeout(int i) {
        this.req.timeout(i);
        return this;
    }

    public Connection maxBodySize(int i) {
        this.req.maxBodySize(i);
        return this;
    }

    public Connection followRedirects(boolean z) {
        this.req.followRedirects(z);
        return this;
    }

    public Connection referrer(String str) {
        Validate.notNull(str, "Referrer must not be null");
        this.req.header(HttpHeaders.REFERER, str);
        return this;
    }

    public Connection method(Connection.Method method) {
        this.req.method(method);
        return this;
    }

    public Connection ignoreHttpErrors(boolean z) {
        this.req.ignoreHttpErrors(z);
        return this;
    }

    public Connection ignoreContentType(boolean z) {
        this.req.ignoreContentType(z);
        return this;
    }

    public Connection validateTLSCertificates(boolean z) {
        this.req.validateTLSCertificates(z);
        return this;
    }

    public Connection data(String str, String str2) {
        this.req.data(KeyVal.create(str, str2));
        return this;
    }

    public Connection data(String str, String str2, InputStream inputStream) {
        this.req.data(KeyVal.create(str, str2, inputStream));
        return this;
    }

    public Connection data(Map<String, String> map) {
        Validate.notNull(map, "Data map must not be null");
        for (Map.Entry next : map.entrySet()) {
            this.req.data(KeyVal.create((String) next.getKey(), (String) next.getValue()));
        }
        return this;
    }

    public Connection data(String... strArr) {
        Validate.notNull(strArr, "Data key value pairs must not be null");
        Validate.isTrue(strArr.length % 2 == 0, "Must supply an even number of key value pairs");
        for (int i = 0; i < strArr.length; i += 2) {
            String str = strArr[i];
            String str2 = strArr[i + 1];
            Validate.notEmpty(str, "Data key must not be empty");
            Validate.notNull(str2, "Data value must not be null");
            this.req.data(KeyVal.create(str, str2));
        }
        return this;
    }

    public Connection data(Collection<Connection.KeyVal> collection) {
        Validate.notNull(collection, "Data collection must not be null");
        for (Connection.KeyVal data : collection) {
            this.req.data(data);
        }
        return this;
    }

    public Connection.KeyVal data(String str) {
        Validate.notEmpty(str, "Data key must not be empty");
        for (Connection.KeyVal next : request().data()) {
            if (next.key().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public Connection requestBody(String str) {
        this.req.requestBody(str);
        return this;
    }

    public Connection header(String str, String str2) {
        this.req.header(str, str2);
        return this;
    }

    public Connection headers(Map<String, String> map) {
        Validate.notNull(map, "Header map must not be null");
        for (Map.Entry next : map.entrySet()) {
            this.req.header((String) next.getKey(), (String) next.getValue());
        }
        return this;
    }

    public Connection cookie(String str, String str2) {
        this.req.cookie(str, str2);
        return this;
    }

    public Connection cookies(Map<String, String> map) {
        Validate.notNull(map, "Cookie map must not be null");
        for (Map.Entry next : map.entrySet()) {
            this.req.cookie((String) next.getKey(), (String) next.getValue());
        }
        return this;
    }

    public Connection parser(Parser parser) {
        this.req.parser(parser);
        return this;
    }

    public Document get() throws IOException {
        this.req.method(Connection.Method.GET);
        execute();
        return this.res.parse();
    }

    public Document post() throws IOException {
        this.req.method(Connection.Method.POST);
        execute();
        return this.res.parse();
    }

    public Connection.Response execute() throws IOException {
        this.res = Response.execute(this.req);
        return this.res;
    }

    public Connection.Request request() {
        return this.req;
    }

    public Connection request(Connection.Request request) {
        this.req = request;
        return this;
    }

    public Connection.Response response() {
        return this.res;
    }

    public Connection response(Connection.Response response) {
        this.res = response;
        return this;
    }

    public Connection postDataCharset(String str) {
        this.req.postDataCharset(str);
        return this;
    }

    private static abstract class Base<T extends Connection.Base> implements Connection.Base<T> {
        Map<String, String> cookies;
        Map<String, String> headers;
        Connection.Method method;
        URL url;

        private Base() {
            this.headers = new LinkedHashMap();
            this.cookies = new LinkedHashMap();
        }

        public URL url() {
            return this.url;
        }

        public T url(URL url2) {
            Validate.notNull(url2, "URL must not be null");
            this.url = url2;
            return this;
        }

        public Connection.Method method() {
            return this.method;
        }

        public T method(Connection.Method method2) {
            Validate.notNull(method2, "Method must not be null");
            this.method = method2;
            return this;
        }

        public String header(String str) {
            Validate.notNull(str, "Header name must not be null");
            String headerCaseInsensitive = getHeaderCaseInsensitive(str);
            return headerCaseInsensitive != null ? fixHeaderEncoding(headerCaseInsensitive) : headerCaseInsensitive;
        }

        private static String fixHeaderEncoding(String str) {
            try {
                byte[] bytes = str.getBytes(StringUtil.__ISO_8859_1);
                if (!looksLikeUtf8(bytes)) {
                    return str;
                }
                return new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException unused) {
                return str;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
            if ((((r8[1] & kotlin.UByte.MAX_VALUE) == 187) & ((r8[2] & kotlin.UByte.MAX_VALUE) == 191)) != false) goto L_0x002a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static boolean looksLikeUtf8(byte[] r8) {
            /*
                int r0 = r8.length
                r1 = 3
                r2 = 0
                r3 = 1
                if (r0 < r1) goto L_0x0029
                byte r0 = r8[r2]
                r0 = r0 & 255(0xff, float:3.57E-43)
                r4 = 239(0xef, float:3.35E-43)
                if (r0 != r4) goto L_0x0029
                byte r0 = r8[r3]
                r0 = r0 & 255(0xff, float:3.57E-43)
                r4 = 187(0xbb, float:2.62E-43)
                if (r0 != r4) goto L_0x0018
                r0 = 1
                goto L_0x0019
            L_0x0018:
                r0 = 0
            L_0x0019:
                r4 = 2
                byte r4 = r8[r4]
                r4 = r4 & 255(0xff, float:3.57E-43)
                r5 = 191(0xbf, float:2.68E-43)
                if (r4 != r5) goto L_0x0024
                r4 = 1
                goto L_0x0025
            L_0x0024:
                r4 = 0
            L_0x0025:
                r0 = r0 & r4
                if (r0 == 0) goto L_0x0029
                goto L_0x002a
            L_0x0029:
                r1 = 0
            L_0x002a:
                int r0 = r8.length
            L_0x002b:
                if (r1 >= r0) goto L_0x005d
                byte r4 = r8[r1]
                r5 = r4 & 128(0x80, float:1.794E-43)
                if (r5 != 0) goto L_0x0034
                goto L_0x005a
            L_0x0034:
                r5 = r4 & 224(0xe0, float:3.14E-43)
                r6 = 192(0xc0, float:2.69E-43)
                if (r5 != r6) goto L_0x003d
                int r4 = r1 + 1
                goto L_0x004e
            L_0x003d:
                r5 = r4 & 240(0xf0, float:3.36E-43)
                r7 = 224(0xe0, float:3.14E-43)
                if (r5 != r7) goto L_0x0046
                int r4 = r1 + 2
                goto L_0x004e
            L_0x0046:
                r4 = r4 & 248(0xf8, float:3.48E-43)
                r5 = 240(0xf0, float:3.36E-43)
                if (r4 != r5) goto L_0x005c
                int r4 = r1 + 3
            L_0x004e:
                if (r1 >= r4) goto L_0x005a
                int r1 = r1 + 1
                byte r5 = r8[r1]
                r5 = r5 & r6
                r7 = 128(0x80, float:1.794E-43)
                if (r5 == r7) goto L_0x004e
                return r2
            L_0x005a:
                int r1 = r1 + r3
                goto L_0x002b
            L_0x005c:
                return r2
            L_0x005d:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.HttpConnection.Base.looksLikeUtf8(byte[]):boolean");
        }

        public T header(String str, String str2) {
            Validate.notEmpty(str, "Header name must not be empty");
            Validate.notNull(str2, "Header value must not be null");
            removeHeader(str);
            this.headers.put(str, str2);
            return this;
        }

        public boolean hasHeader(String str) {
            Validate.notEmpty(str, "Header name must not be empty");
            return getHeaderCaseInsensitive(str) != null;
        }

        public boolean hasHeaderWithValue(String str, String str2) {
            return hasHeader(str) && header(str).equalsIgnoreCase(str2);
        }

        public T removeHeader(String str) {
            Validate.notEmpty(str, "Header name must not be empty");
            Map.Entry<String, String> scanHeaders = scanHeaders(str);
            if (scanHeaders != null) {
                this.headers.remove(scanHeaders.getKey());
            }
            return this;
        }

        public Map<String, String> headers() {
            return this.headers;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:4:0x001d, code lost:
            r3 = scanHeaders(r3);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private java.lang.String getHeaderCaseInsensitive(java.lang.String r3) {
            /*
                r2 = this;
                java.lang.String r0 = "Header name must not be null"
                org.jsoup.helper.Validate.notNull(r3, r0)
                java.util.Map<java.lang.String, java.lang.String> r0 = r2.headers
                java.lang.Object r0 = r0.get(r3)
                java.lang.String r0 = (java.lang.String) r0
                if (r0 != 0) goto L_0x001b
                java.util.Map<java.lang.String, java.lang.String> r0 = r2.headers
                java.lang.String r1 = r3.toLowerCase()
                java.lang.Object r0 = r0.get(r1)
                java.lang.String r0 = (java.lang.String) r0
            L_0x001b:
                if (r0 != 0) goto L_0x002a
                java.util.Map$Entry r3 = r2.scanHeaders(r3)
                if (r3 == 0) goto L_0x002a
                java.lang.Object r3 = r3.getValue()
                r0 = r3
                java.lang.String r0 = (java.lang.String) r0
            L_0x002a:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.HttpConnection.Base.getHeaderCaseInsensitive(java.lang.String):java.lang.String");
        }

        private Map.Entry<String, String> scanHeaders(String str) {
            String lowerCase = str.toLowerCase();
            for (Map.Entry<String, String> next : this.headers.entrySet()) {
                if (next.getKey().toLowerCase().equals(lowerCase)) {
                    return next;
                }
            }
            return null;
        }

        public String cookie(String str) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            return this.cookies.get(str);
        }

        public T cookie(String str, String str2) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            Validate.notNull(str2, "Cookie value must not be null");
            this.cookies.put(str, str2);
            return this;
        }

        public boolean hasCookie(String str) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            return this.cookies.containsKey(str);
        }

        public T removeCookie(String str) {
            Validate.notEmpty(str, "Cookie name must not be empty");
            this.cookies.remove(str);
            return this;
        }

        public Map<String, String> cookies() {
            return this.cookies;
        }
    }

    public static class Request extends Base<Connection.Request> implements Connection.Request {
        private String body;
        private Collection<Connection.KeyVal> data;
        private boolean followRedirects;
        private boolean ignoreContentType;
        private boolean ignoreHttpErrors;
        private int maxBodySizeBytes;
        private Parser parser;
        /* access modifiers changed from: private */
        public boolean parserDefined;
        private String postDataCharset;
        private Proxy proxy;
        private int timeoutMilliseconds;
        private boolean validateTSLCertificates;

        public /* bridge */ /* synthetic */ String cookie(String str) {
            return super.cookie(str);
        }

        public /* bridge */ /* synthetic */ Map cookies() {
            return super.cookies();
        }

        public /* bridge */ /* synthetic */ boolean hasCookie(String str) {
            return super.hasCookie(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeader(String str) {
            return super.hasHeader(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeaderWithValue(String str, String str2) {
            return super.hasHeaderWithValue(str, str2);
        }

        public /* bridge */ /* synthetic */ String header(String str) {
            return super.header(str);
        }

        public /* bridge */ /* synthetic */ Map headers() {
            return super.headers();
        }

        public /* bridge */ /* synthetic */ Connection.Method method() {
            return super.method();
        }

        public /* bridge */ /* synthetic */ URL url() {
            return super.url();
        }

        private Request() {
            super();
            this.body = null;
            this.ignoreHttpErrors = false;
            this.ignoreContentType = false;
            this.parserDefined = false;
            this.validateTSLCertificates = true;
            this.postDataCharset = "UTF-8";
            this.timeoutMilliseconds = RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT;
            this.maxBodySizeBytes = 1048576;
            this.followRedirects = true;
            this.data = new ArrayList();
            this.method = Connection.Method.GET;
            this.headers.put("Accept-Encoding", "gzip");
            this.headers.put("User-Agent", HttpConnection.DEFAULT_UA);
            this.parser = Parser.htmlParser();
        }

        public Proxy proxy() {
            return this.proxy;
        }

        public Request proxy(Proxy proxy2) {
            this.proxy = proxy2;
            return this;
        }

        public Request proxy(String str, int i) {
            this.proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(str, i));
            return this;
        }

        public int timeout() {
            return this.timeoutMilliseconds;
        }

        public Request timeout(int i) {
            Validate.isTrue(i >= 0, "Timeout milliseconds must be 0 (infinite) or greater");
            this.timeoutMilliseconds = i;
            return this;
        }

        public int maxBodySize() {
            return this.maxBodySizeBytes;
        }

        public Connection.Request maxBodySize(int i) {
            Validate.isTrue(i >= 0, "maxSize must be 0 (unlimited) or larger");
            this.maxBodySizeBytes = i;
            return this;
        }

        public boolean followRedirects() {
            return this.followRedirects;
        }

        public Connection.Request followRedirects(boolean z) {
            this.followRedirects = z;
            return this;
        }

        public boolean ignoreHttpErrors() {
            return this.ignoreHttpErrors;
        }

        public boolean validateTLSCertificates() {
            return this.validateTSLCertificates;
        }

        public void validateTLSCertificates(boolean z) {
            this.validateTSLCertificates = z;
        }

        public Connection.Request ignoreHttpErrors(boolean z) {
            this.ignoreHttpErrors = z;
            return this;
        }

        public boolean ignoreContentType() {
            return this.ignoreContentType;
        }

        public Connection.Request ignoreContentType(boolean z) {
            this.ignoreContentType = z;
            return this;
        }

        public Request data(Connection.KeyVal keyVal) {
            Validate.notNull(keyVal, "Key val must not be null");
            this.data.add(keyVal);
            return this;
        }

        public Collection<Connection.KeyVal> data() {
            return this.data;
        }

        public Connection.Request requestBody(String str) {
            this.body = str;
            return this;
        }

        public String requestBody() {
            return this.body;
        }

        public Request parser(Parser parser2) {
            this.parser = parser2;
            this.parserDefined = true;
            return this;
        }

        public Parser parser() {
            return this.parser;
        }

        public Connection.Request postDataCharset(String str) {
            Validate.notNull(str, "Charset must not be null");
            if (Charset.isSupported(str)) {
                this.postDataCharset = str;
                return this;
            }
            throw new IllegalCharsetNameException(str);
        }

        public String postDataCharset() {
            return this.postDataCharset;
        }
    }

    public static class Response extends Base<Connection.Response> implements Connection.Response {
        private static final String LOCATION = "Location";
        private static final int MAX_REDIRECTS = 20;
        private static SSLSocketFactory sslSocketFactory;
        private static final Pattern xmlContentTypeRxp = Pattern.compile("(application|text)/\\w*\\+?xml.*");
        private ByteBuffer byteData;
        private String charset;
        private String contentType;
        private boolean executed = false;
        private int numRedirects = 0;
        private Connection.Request req;
        private int statusCode;
        private String statusMessage;

        public /* bridge */ /* synthetic */ String cookie(String str) {
            return super.cookie(str);
        }

        public /* bridge */ /* synthetic */ Map cookies() {
            return super.cookies();
        }

        public /* bridge */ /* synthetic */ boolean hasCookie(String str) {
            return super.hasCookie(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeader(String str) {
            return super.hasHeader(str);
        }

        public /* bridge */ /* synthetic */ boolean hasHeaderWithValue(String str, String str2) {
            return super.hasHeaderWithValue(str, str2);
        }

        public /* bridge */ /* synthetic */ String header(String str) {
            return super.header(str);
        }

        public /* bridge */ /* synthetic */ Map headers() {
            return super.headers();
        }

        public /* bridge */ /* synthetic */ Connection.Method method() {
            return super.method();
        }

        public /* bridge */ /* synthetic */ URL url() {
            return super.url();
        }

        Response() {
            super();
        }

        private Response(Response response) throws IOException {
            super();
            if (response != null) {
                this.numRedirects = response.numRedirects + 1;
                if (this.numRedirects >= 20) {
                    throw new IOException(String.format("Too many redirects occurred trying to load URL %s", new Object[]{response.url()}));
                }
            }
        }

        static Response execute(Connection.Request request) throws IOException {
            return execute(request, (Response) null);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.io.InputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.String} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.util.zip.GZIPInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: java.lang.String} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: java.lang.String} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x007c A[Catch:{ all -> 0x01ac, all -> 0x01cf }] */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x009d A[Catch:{ all -> 0x01ac, all -> 0x01cf }] */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x0105  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static org.jsoup.helper.HttpConnection.Response execute(org.jsoup.Connection.Request r6, org.jsoup.helper.HttpConnection.Response r7) throws java.io.IOException {
            /*
                java.lang.String r0 = "Location"
                java.lang.String r1 = "Request must not be null"
                org.jsoup.helper.Validate.notNull(r6, r1)
                java.net.URL r1 = r6.url()
                java.lang.String r1 = r1.getProtocol()
                java.lang.String r2 = "http"
                boolean r2 = r1.equals(r2)
                if (r2 != 0) goto L_0x0028
                java.lang.String r2 = "https"
                boolean r1 = r1.equals(r2)
                if (r1 == 0) goto L_0x0020
                goto L_0x0028
            L_0x0020:
                java.net.MalformedURLException r6 = new java.net.MalformedURLException
                java.lang.String r7 = "Only http & https protocols supported"
                r6.<init>(r7)
                throw r6
            L_0x0028:
                org.jsoup.Connection$Method r1 = r6.method()
                boolean r1 = r1.hasBody()
                java.lang.String r2 = r6.requestBody()
                r3 = 1
                if (r2 == 0) goto L_0x0039
                r2 = 1
                goto L_0x003a
            L_0x0039:
                r2 = 0
            L_0x003a:
                if (r1 != 0) goto L_0x0054
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Cannot set a request body for HTTP method "
                r4.append(r5)
                org.jsoup.Connection$Method r5 = r6.method()
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                org.jsoup.helper.Validate.isFalse(r2, r4)
            L_0x0054:
                java.util.Collection r4 = r6.data()
                int r4 = r4.size()
                r5 = 0
                if (r4 <= 0) goto L_0x0067
                if (r1 == 0) goto L_0x0063
                if (r2 == 0) goto L_0x0067
            L_0x0063:
                serialiseRequestUrl(r6)
                goto L_0x006e
            L_0x0067:
                if (r1 == 0) goto L_0x006e
                java.lang.String r1 = setOutputContentType(r6)
                goto L_0x006f
            L_0x006e:
                r1 = r5
            L_0x006f:
                java.net.HttpURLConnection r2 = createConnection(r6)
                r2.connect()     // Catch:{ all -> 0x01cf }
                boolean r4 = r2.getDoOutput()     // Catch:{ all -> 0x01cf }
                if (r4 == 0) goto L_0x0083
                java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ all -> 0x01cf }
                writePost(r6, r4, r1)     // Catch:{ all -> 0x01cf }
            L_0x0083:
                int r1 = r2.getResponseCode()     // Catch:{ all -> 0x01cf }
                org.jsoup.helper.HttpConnection$Response r4 = new org.jsoup.helper.HttpConnection$Response     // Catch:{ all -> 0x01cf }
                r4.<init>(r7)     // Catch:{ all -> 0x01cf }
                r4.setupFromConnection(r2, r7)     // Catch:{ all -> 0x01cf }
                r4.req = r6     // Catch:{ all -> 0x01cf }
                boolean r7 = r4.hasHeader(r0)     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x0105
                boolean r7 = r6.followRedirects()     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x0105
                r7 = 307(0x133, float:4.3E-43)
                if (r1 == r7) goto L_0x00ad
                org.jsoup.Connection$Method r7 = org.jsoup.Connection.Method.GET     // Catch:{ all -> 0x01cf }
                r6.method(r7)     // Catch:{ all -> 0x01cf }
                java.util.Collection r7 = r6.data()     // Catch:{ all -> 0x01cf }
                r7.clear()     // Catch:{ all -> 0x01cf }
            L_0x00ad:
                java.lang.String r7 = r4.header(r0)     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x00c8
                java.lang.String r0 = "http:/"
                boolean r0 = r7.startsWith(r0)     // Catch:{ all -> 0x01cf }
                if (r0 == 0) goto L_0x00c8
                r0 = 6
                char r1 = r7.charAt(r0)     // Catch:{ all -> 0x01cf }
                r3 = 47
                if (r1 == r3) goto L_0x00c8
                java.lang.String r7 = r7.substring(r0)     // Catch:{ all -> 0x01cf }
            L_0x00c8:
                java.net.URL r0 = r6.url()     // Catch:{ all -> 0x01cf }
                java.net.URL r7 = org.jsoup.helper.StringUtil.resolve((java.net.URL) r0, (java.lang.String) r7)     // Catch:{ all -> 0x01cf }
                java.net.URL r7 = org.jsoup.helper.HttpConnection.encodeUrl((java.net.URL) r7)     // Catch:{ all -> 0x01cf }
                r6.url(r7)     // Catch:{ all -> 0x01cf }
                java.util.Map r7 = r4.cookies     // Catch:{ all -> 0x01cf }
                java.util.Set r7 = r7.entrySet()     // Catch:{ all -> 0x01cf }
                java.util.Iterator r7 = r7.iterator()     // Catch:{ all -> 0x01cf }
            L_0x00e1:
                boolean r0 = r7.hasNext()     // Catch:{ all -> 0x01cf }
                if (r0 == 0) goto L_0x00fd
                java.lang.Object r0 = r7.next()     // Catch:{ all -> 0x01cf }
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x01cf }
                java.lang.Object r1 = r0.getKey()     // Catch:{ all -> 0x01cf }
                java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x01cf }
                java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x01cf }
                java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x01cf }
                r6.cookie(r1, r0)     // Catch:{ all -> 0x01cf }
                goto L_0x00e1
            L_0x00fd:
                org.jsoup.helper.HttpConnection$Response r6 = execute(r6, r4)     // Catch:{ all -> 0x01cf }
                r2.disconnect()
                return r6
            L_0x0105:
                r7 = 200(0xc8, float:2.8E-43)
                if (r1 < r7) goto L_0x010d
                r7 = 400(0x190, float:5.6E-43)
                if (r1 < r7) goto L_0x0113
            L_0x010d:
                boolean r7 = r6.ignoreHttpErrors()     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x01bf
            L_0x0113:
                java.lang.String r7 = r4.contentType()     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x0144
                boolean r0 = r6.ignoreContentType()     // Catch:{ all -> 0x01cf }
                if (r0 != 0) goto L_0x0144
                java.lang.String r0 = "text/"
                boolean r0 = r7.startsWith(r0)     // Catch:{ all -> 0x01cf }
                if (r0 != 0) goto L_0x0144
                java.util.regex.Pattern r0 = xmlContentTypeRxp     // Catch:{ all -> 0x01cf }
                java.util.regex.Matcher r0 = r0.matcher(r7)     // Catch:{ all -> 0x01cf }
                boolean r0 = r0.matches()     // Catch:{ all -> 0x01cf }
                if (r0 == 0) goto L_0x0134
                goto L_0x0144
            L_0x0134:
                org.jsoup.UnsupportedMimeTypeException r0 = new org.jsoup.UnsupportedMimeTypeException     // Catch:{ all -> 0x01cf }
                java.lang.String r1 = "Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml"
                java.net.URL r6 = r6.url()     // Catch:{ all -> 0x01cf }
                java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x01cf }
                r0.<init>(r1, r7, r6)     // Catch:{ all -> 0x01cf }
                throw r0     // Catch:{ all -> 0x01cf }
            L_0x0144:
                if (r7 == 0) goto L_0x0166
                java.util.regex.Pattern r0 = xmlContentTypeRxp     // Catch:{ all -> 0x01cf }
                java.util.regex.Matcher r7 = r0.matcher(r7)     // Catch:{ all -> 0x01cf }
                boolean r7 = r7.matches()     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x0166
                boolean r7 = r6 instanceof org.jsoup.helper.HttpConnection.Request     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x0166
                r7 = r6
                org.jsoup.helper.HttpConnection$Request r7 = (org.jsoup.helper.HttpConnection.Request) r7     // Catch:{ all -> 0x01cf }
                boolean r7 = r7.parserDefined     // Catch:{ all -> 0x01cf }
                if (r7 != 0) goto L_0x0166
                org.jsoup.parser.Parser r7 = org.jsoup.parser.Parser.xmlParser()     // Catch:{ all -> 0x01cf }
                r6.parser(r7)     // Catch:{ all -> 0x01cf }
            L_0x0166:
                java.lang.String r7 = r4.contentType     // Catch:{ all -> 0x01cf }
                java.lang.String r7 = org.jsoup.helper.DataUtil.getCharsetFromContentType(r7)     // Catch:{ all -> 0x01cf }
                r4.charset = r7     // Catch:{ all -> 0x01cf }
                int r7 = r2.getContentLength()     // Catch:{ all -> 0x01cf }
                if (r7 == 0) goto L_0x01b3
                org.jsoup.Connection$Method r7 = r6.method()     // Catch:{ all -> 0x01cf }
                org.jsoup.Connection$Method r0 = org.jsoup.Connection.Method.HEAD     // Catch:{ all -> 0x01cf }
                if (r7 == r0) goto L_0x01b3
                java.io.InputStream r7 = r2.getErrorStream()     // Catch:{ all -> 0x01ac }
                if (r7 == 0) goto L_0x0187
                java.io.InputStream r7 = r2.getErrorStream()     // Catch:{ all -> 0x01ac }
                goto L_0x018b
            L_0x0187:
                java.io.InputStream r7 = r2.getInputStream()     // Catch:{ all -> 0x01ac }
            L_0x018b:
                r5 = r7
                java.lang.String r7 = "Content-Encoding"
                java.lang.String r0 = "gzip"
                boolean r7 = r4.hasHeaderWithValue(r7, r0)     // Catch:{ all -> 0x01ac }
                if (r7 == 0) goto L_0x019c
                java.util.zip.GZIPInputStream r7 = new java.util.zip.GZIPInputStream     // Catch:{ all -> 0x01ac }
                r7.<init>(r5)     // Catch:{ all -> 0x01ac }
                r5 = r7
            L_0x019c:
                int r6 = r6.maxBodySize()     // Catch:{ all -> 0x01ac }
                java.nio.ByteBuffer r6 = org.jsoup.helper.DataUtil.readToByteBuffer(r5, r6)     // Catch:{ all -> 0x01ac }
                r4.byteData = r6     // Catch:{ all -> 0x01ac }
                if (r5 == 0) goto L_0x01b9
                r5.close()     // Catch:{ all -> 0x01cf }
                goto L_0x01b9
            L_0x01ac:
                r6 = move-exception
                if (r5 == 0) goto L_0x01b2
                r5.close()     // Catch:{ all -> 0x01cf }
            L_0x01b2:
                throw r6     // Catch:{ all -> 0x01cf }
            L_0x01b3:
                java.nio.ByteBuffer r6 = org.jsoup.helper.DataUtil.emptyByteBuffer()     // Catch:{ all -> 0x01cf }
                r4.byteData = r6     // Catch:{ all -> 0x01cf }
            L_0x01b9:
                r2.disconnect()
                r4.executed = r3
                return r4
            L_0x01bf:
                org.jsoup.HttpStatusException r7 = new org.jsoup.HttpStatusException     // Catch:{ all -> 0x01cf }
                java.lang.String r0 = "HTTP error fetching URL"
                java.net.URL r6 = r6.url()     // Catch:{ all -> 0x01cf }
                java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x01cf }
                r7.<init>(r0, r1, r6)     // Catch:{ all -> 0x01cf }
                throw r7     // Catch:{ all -> 0x01cf }
            L_0x01cf:
                r6 = move-exception
                r2.disconnect()
                goto L_0x01d5
            L_0x01d4:
                throw r6
            L_0x01d5:
                goto L_0x01d4
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.HttpConnection.Response.execute(org.jsoup.Connection$Request, org.jsoup.helper.HttpConnection$Response):org.jsoup.helper.HttpConnection$Response");
        }

        public int statusCode() {
            return this.statusCode;
        }

        public String statusMessage() {
            return this.statusMessage;
        }

        public String charset() {
            return this.charset;
        }

        public Response charset(String str) {
            this.charset = str;
            return this;
        }

        public String contentType() {
            return this.contentType;
        }

        public Document parse() throws IOException {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
            Document parseByteData = DataUtil.parseByteData(this.byteData, this.charset, this.url.toExternalForm(), this.req.parser());
            this.byteData.rewind();
            this.charset = parseByteData.outputSettings().charset().name();
            return parseByteData;
        }

        public String body() {
            String str;
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            String str2 = this.charset;
            if (str2 == null) {
                str = Charset.forName("UTF-8").decode(this.byteData).toString();
            } else {
                str = Charset.forName(str2).decode(this.byteData).toString();
            }
            this.byteData.rewind();
            return str;
        }

        public byte[] bodyAsBytes() {
            Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
            return this.byteData.array();
        }

        private static HttpURLConnection createConnection(Connection.Request request) throws IOException {
            URLConnection uRLConnection;
            if (request.proxy() == null) {
                uRLConnection = request.url().openConnection();
            } else {
                uRLConnection = request.url().openConnection(request.proxy());
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnection;
            httpURLConnection.setRequestMethod(request.method().name());
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setConnectTimeout(request.timeout());
            httpURLConnection.setReadTimeout(request.timeout());
            if ((httpURLConnection instanceof HttpsURLConnection) && !request.validateTLSCertificates()) {
                initUnSecureTSL();
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
                httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
                httpsURLConnection.setHostnameVerifier(getInsecureVerifier());
            }
            if (request.method().hasBody()) {
                httpURLConnection.setDoOutput(true);
            }
            if (request.cookies().size() > 0) {
                httpURLConnection.addRequestProperty("Cookie", getRequestCookieString(request));
            }
            for (Map.Entry next : request.headers().entrySet()) {
                httpURLConnection.addRequestProperty((String) next.getKey(), (String) next.getValue());
            }
            return httpURLConnection;
        }

        private static HostnameVerifier getInsecureVerifier() {
            return new HostnameVerifier() {
                public boolean verify(String str, SSLSession sSLSession) {
                    return true;
                }
            };
        }

        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0028 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static synchronized void initUnSecureTSL() throws java.io.IOException {
            /*
                java.lang.Class<org.jsoup.helper.HttpConnection$Response> r0 = org.jsoup.helper.HttpConnection.Response.class
                monitor-enter(r0)
                javax.net.ssl.SSLSocketFactory r1 = sslSocketFactory     // Catch:{ all -> 0x003a }
                if (r1 != 0) goto L_0x0038
                r1 = 1
                javax.net.ssl.TrustManager[] r1 = new javax.net.ssl.TrustManager[r1]     // Catch:{ all -> 0x003a }
                r2 = 0
                org.jsoup.helper.HttpConnection$Response$2 r3 = new org.jsoup.helper.HttpConnection$Response$2     // Catch:{ all -> 0x003a }
                r3.<init>()     // Catch:{ all -> 0x003a }
                r1[r2] = r3     // Catch:{ all -> 0x003a }
                java.lang.String r2 = "SSL"
                javax.net.ssl.SSLContext r2 = javax.net.ssl.SSLContext.getInstance(r2)     // Catch:{ NoSuchAlgorithmException -> 0x0030, KeyManagementException -> 0x0028 }
                r3 = 0
                java.security.SecureRandom r4 = new java.security.SecureRandom     // Catch:{ NoSuchAlgorithmException -> 0x0030, KeyManagementException -> 0x0028 }
                r4.<init>()     // Catch:{ NoSuchAlgorithmException -> 0x0030, KeyManagementException -> 0x0028 }
                r2.init(r3, r1, r4)     // Catch:{ NoSuchAlgorithmException -> 0x0030, KeyManagementException -> 0x0028 }
                javax.net.ssl.SSLSocketFactory r1 = r2.getSocketFactory()     // Catch:{ NoSuchAlgorithmException -> 0x0030, KeyManagementException -> 0x0028 }
                sslSocketFactory = r1     // Catch:{ NoSuchAlgorithmException -> 0x0030, KeyManagementException -> 0x0028 }
                goto L_0x0038
            L_0x0028:
                java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x003a }
                java.lang.String r2 = "Can't create unsecure trust manager"
                r1.<init>(r2)     // Catch:{ all -> 0x003a }
                throw r1     // Catch:{ all -> 0x003a }
            L_0x0030:
                java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x003a }
                java.lang.String r2 = "Can't create unsecure trust manager"
                r1.<init>(r2)     // Catch:{ all -> 0x003a }
                throw r1     // Catch:{ all -> 0x003a }
            L_0x0038:
                monitor-exit(r0)
                return
            L_0x003a:
                r1 = move-exception
                monitor-exit(r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.HttpConnection.Response.initUnSecureTSL():void");
        }

        private void setupFromConnection(HttpURLConnection httpURLConnection, Connection.Response response) throws IOException {
            this.method = Connection.Method.valueOf(httpURLConnection.getRequestMethod());
            this.url = httpURLConnection.getURL();
            this.statusCode = httpURLConnection.getResponseCode();
            this.statusMessage = httpURLConnection.getResponseMessage();
            this.contentType = httpURLConnection.getContentType();
            processResponseHeaders(createHeaderMap(httpURLConnection));
            if (response != null) {
                for (Map.Entry next : response.cookies().entrySet()) {
                    if (!hasCookie((String) next.getKey())) {
                        cookie((String) next.getKey(), (String) next.getValue());
                    }
                }
            }
        }

        private static LinkedHashMap<String, List<String>> createHeaderMap(HttpURLConnection httpURLConnection) {
            LinkedHashMap<String, List<String>> linkedHashMap = new LinkedHashMap<>();
            int i = 0;
            while (true) {
                String headerFieldKey = httpURLConnection.getHeaderFieldKey(i);
                String headerField = httpURLConnection.getHeaderField(i);
                if (headerFieldKey == null && headerField == null) {
                    return linkedHashMap;
                }
                i++;
                if (!(headerFieldKey == null || headerField == null)) {
                    if (linkedHashMap.containsKey(headerFieldKey)) {
                        linkedHashMap.get(headerFieldKey).add(headerField);
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(headerField);
                        linkedHashMap.put(headerFieldKey, arrayList);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void processResponseHeaders(Map<String, List<String>> map) {
            for (Map.Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                if (str != null) {
                    List<String> list = (List) next.getValue();
                    if (str.equalsIgnoreCase("Set-Cookie")) {
                        for (String str2 : list) {
                            if (str2 != null) {
                                TokenQueue tokenQueue = new TokenQueue(str2);
                                String trim = tokenQueue.chompTo("=").trim();
                                String trim2 = tokenQueue.consumeTo(";").trim();
                                if (trim.length() > 0) {
                                    cookie(trim, trim2);
                                }
                            }
                        }
                    } else {
                        if (list.size() == 1) {
                            header(str, (String) list.get(0));
                        } else if (list.size() > 1) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < list.size(); i++) {
                                String str3 = (String) list.get(i);
                                if (i != 0) {
                                    sb.append(", ");
                                }
                                sb.append(str3);
                            }
                            header(str, sb.toString());
                        }
                    }
                }
            }
        }

        private static String setOutputContentType(Connection.Request request) {
            if (!request.hasHeader("Content-Type")) {
                if (HttpConnection.needsMultipart(request)) {
                    String mimeBoundary = DataUtil.mimeBoundary();
                    request.header("Content-Type", "multipart/form-data; boundary=" + mimeBoundary);
                    return mimeBoundary;
                }
                request.header("Content-Type", "application/x-www-form-urlencoded; charset=" + request.postDataCharset());
            }
            return null;
        }

        private static void writePost(Connection.Request request, OutputStream outputStream, String str) throws IOException {
            Collection<Connection.KeyVal> data = request.data();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, request.postDataCharset()));
            if (str != null) {
                for (Connection.KeyVal next : data) {
                    bufferedWriter.write("--");
                    bufferedWriter.write(str);
                    bufferedWriter.write("\r\n");
                    bufferedWriter.write("Content-Disposition: form-data; name=\"");
                    bufferedWriter.write(HttpConnection.encodeMimeName(next.key()));
                    bufferedWriter.write("\"");
                    if (next.hasInputStream()) {
                        bufferedWriter.write("; filename=\"");
                        bufferedWriter.write(HttpConnection.encodeMimeName(next.value()));
                        bufferedWriter.write("\"\r\nContent-Type: application/octet-stream\r\n\r\n");
                        bufferedWriter.flush();
                        DataUtil.crossStreams(next.inputStream(), outputStream);
                        outputStream.flush();
                    } else {
                        bufferedWriter.write("\r\n\r\n");
                        bufferedWriter.write(next.value());
                    }
                    bufferedWriter.write("\r\n");
                }
                bufferedWriter.write("--");
                bufferedWriter.write(str);
                bufferedWriter.write("--");
            } else if (request.requestBody() != null) {
                bufferedWriter.write(request.requestBody());
            } else {
                boolean z = true;
                for (Connection.KeyVal next2 : data) {
                    if (!z) {
                        bufferedWriter.append(Typography.amp);
                    } else {
                        z = false;
                    }
                    bufferedWriter.write(URLEncoder.encode(next2.key(), request.postDataCharset()));
                    bufferedWriter.write(61);
                    bufferedWriter.write(URLEncoder.encode(next2.value(), request.postDataCharset()));
                }
            }
            bufferedWriter.close();
        }

        private static String getRequestCookieString(Connection.Request request) {
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            for (Map.Entry next : request.cookies().entrySet()) {
                if (!z) {
                    sb.append("; ");
                } else {
                    z = false;
                }
                sb.append((String) next.getKey());
                sb.append('=');
                sb.append((String) next.getValue());
            }
            return sb.toString();
        }

        private static void serialiseRequestUrl(Connection.Request request) throws IOException {
            boolean z;
            URL url = request.url();
            StringBuilder sb = new StringBuilder();
            sb.append(url.getProtocol());
            sb.append(HttpConstant.SCHEME_SPLIT);
            sb.append(url.getAuthority());
            sb.append(url.getPath());
            sb.append("?");
            if (url.getQuery() != null) {
                sb.append(url.getQuery());
                z = false;
            } else {
                z = true;
            }
            for (Connection.KeyVal next : request.data()) {
                Validate.isFalse(next.hasInputStream(), "InputStream data not supported in URL query string.");
                if (!z) {
                    sb.append(Typography.amp);
                } else {
                    z = false;
                }
                sb.append(URLEncoder.encode(next.key(), "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(next.value(), "UTF-8"));
            }
            request.url(new URL(sb.toString()));
            request.data().clear();
        }
    }

    /* access modifiers changed from: private */
    public static boolean needsMultipart(Connection.Request request) {
        for (Connection.KeyVal hasInputStream : request.data()) {
            if (hasInputStream.hasInputStream()) {
                return true;
            }
        }
        return false;
    }

    public static class KeyVal implements Connection.KeyVal {
        private String key;
        private InputStream stream;
        private String value;

        public static KeyVal create(String str, String str2) {
            return new KeyVal().key(str).value(str2);
        }

        public static KeyVal create(String str, String str2, InputStream inputStream) {
            return new KeyVal().key(str).value(str2).inputStream(inputStream);
        }

        private KeyVal() {
        }

        public KeyVal key(String str) {
            Validate.notEmpty(str, "Data key must not be empty");
            this.key = str;
            return this;
        }

        public String key() {
            return this.key;
        }

        public KeyVal value(String str) {
            Validate.notNull(str, "Data value must not be null");
            this.value = str;
            return this;
        }

        public String value() {
            return this.value;
        }

        public KeyVal inputStream(InputStream inputStream) {
            Validate.notNull(this.value, "Data input stream must not be null");
            this.stream = inputStream;
            return this;
        }

        public InputStream inputStream() {
            return this.stream;
        }

        public boolean hasInputStream() {
            return this.stream != null;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}
