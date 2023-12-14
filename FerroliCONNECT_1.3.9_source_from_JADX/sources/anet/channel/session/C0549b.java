package anet.channel.session;

import android.os.Build;
import android.util.Pair;
import anet.channel.RequestCb;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import anet.channel.util.C0603b;
import anet.channel.util.C0608g;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.StringUtils;
import com.taobao.accs.common.Constants;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/* renamed from: anet.channel.session.b */
/* compiled from: Taobao */
public class C0549b {

    /* renamed from: anet.channel.session.b$a */
    /* compiled from: Taobao */
    public static class C0550a {

        /* renamed from: a */
        public int f387a;

        /* renamed from: b */
        public byte[] f388b;

        /* renamed from: c */
        public Map<String, List<String>> f389c;

        /* renamed from: d */
        public int f390d;

        /* renamed from: e */
        public boolean f391e;
    }

    private C0549b() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0303, code lost:
        if (r12 != null) goto L_0x0305;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x030a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x030b, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0314, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0315, code lost:
        r20 = r2;
        r21 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0346, code lost:
        anet.channel.util.ALog.m327e("awcn.HttpConnector", r10, r11.getSeq(), r21, ((javax.net.ssl.HttpsURLConnection) r12).getSSLSocketFactory(), r20, ((javax.net.ssl.HttpsURLConnection) r12).getHostnameVerifier());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0371, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0372, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x037c, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x037d, code lost:
        r20 = r2;
        r21 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x03ae, code lost:
        anet.channel.util.ALog.m327e("awcn.HttpConnector", r10, r11.getSeq(), r21, ((javax.net.ssl.HttpsURLConnection) r12).getSSLSocketFactory(), r20, ((javax.net.ssl.HttpsURLConnection) r12).getHostnameVerifier());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x03d9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x03da, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x03e4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x03e5, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:?, code lost:
        m216a(r11, r9, r1, anet.channel.util.ErrorConstant.ERROR_CONNECT_EXCEPTION, r2);
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "HTTP Connect Exception", r11.getSeq(), r2, new java.lang.Object[0]);
        anet.channel.status.NetworkStatusHelper.printNetworkDetail();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x03fa, code lost:
        if (r12 != null) goto L_0x03fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0401, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0402, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x040c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x040d, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:?, code lost:
        m216a(r11, r9, r1, anet.channel.util.ErrorConstant.ERROR_CONN_TIME_OUT, r2);
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "HTTP Connect Timeout", r11.getSeq(), r2, new java.lang.Object[0]);
        anet.channel.status.NetworkStatusHelper.printNetworkDetail();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0422, code lost:
        if (r12 != null) goto L_0x0424;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0428, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0429, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0432, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0433, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:?, code lost:
        m216a(r11, r9, r1, anet.channel.util.ErrorConstant.ERROR_SOCKET_TIME_OUT, r2);
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "HTTP Socket Timeout", r11.getSeq(), r2, new java.lang.Object[0]);
        anet.channel.status.NetworkStatusHelper.printNetworkDetail();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0448, code lost:
        if (r12 != null) goto L_0x044a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x044e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x044f, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x0458, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0459, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:?, code lost:
        m216a(r11, r9, r1, anet.channel.util.ErrorConstant.ERROR_UNKNOWN_HOST_EXCEPTION, r2);
        anet.channel.util.ALog.m327e("awcn.HttpConnector", "Unknown Host Exception", r11.getSeq(), com.taobao.accs.common.Constants.KEY_HOST, r11.getHost(), r2);
        anet.channel.status.NetworkStatusHelper.printNetworkDetail();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x047b, code lost:
        if (r12 != null) goto L_0x047d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0481, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0482, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x048b, code lost:
        if (r12 != null) goto L_0x048d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0491, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x0492, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x049a, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        anet.channel.util.ALog.m327e("awcn.HttpConnector", "redirect url is invalid!", r11.getSeq(), "redirect url", r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0266, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0269, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0272, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0273, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0276, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0277, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x027c, code lost:
        if (r2.getMessage() != null) goto L_0x027e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x027e, code lost:
        r6 = r2.getMessage();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0288, code lost:
        if (r6.contains("not verified") != false) goto L_0x028a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x028a, code lost:
        anet.channel.strategy.C0566c.C0567a.f482a.mo9042b(r11.getHost());
        m216a(r11, r9, r1, anet.channel.util.ErrorConstant.ERROR_HOST_NOT_VERIFY_ERROR, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0299, code lost:
        m216a(r11, r9, r1, -101, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x029e, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "HTTP Exception", r11.getSeq(), r2, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x02aa, code lost:
        if (r12 != null) goto L_0x02ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x02b1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x02b2, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x02bb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x02bc, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        m216a(r11, r9, r1, -404, r2);
        anet.channel.util.ALog.m327e("awcn.HttpConnector", "IO Exception", r11.getSeq(), com.taobao.accs.common.Constants.KEY_HOST, r11.getHost(), r2);
        anet.channel.status.NetworkStatusHelper.printNetworkDetail();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x02de, code lost:
        if (r12 != null) goto L_0x02e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:?, code lost:
        r12.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x02e5, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x02e6, code lost:
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "http disconnect", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x02f0, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x02f1, code lost:
        r2 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
        m216a(r11, r9, r1, anet.channel.util.ErrorConstant.ERROR_REQUEST_CANCEL, r2);
        anet.channel.util.ALog.m326e("awcn.HttpConnector", "HTTP Request Cancel", r11.getSeq(), r2, new java.lang.Object[0]);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0346 A[Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276, all -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x036c A[SYNTHETIC, Splitter:B:114:0x036c] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x03ae A[Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276, all -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x03d4 A[SYNTHETIC, Splitter:B:127:0x03d4] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x03e4 A[ExcHandler: ConnectException (r0v6 'e' java.net.ConnectException A[CUSTOM_DECLARE]), PHI: r12 
      PHI: (r12v5 java.net.HttpURLConnection) = (r12v1 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection) binds: [B:9:0x0032, B:15:0x0097, B:16:?, B:18:0x00b3, B:33:0x0177] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x040c A[ExcHandler: ConnectTimeoutException (r0v4 'e' org.apache.http.conn.ConnectTimeoutException A[CUSTOM_DECLARE]), PHI: r12 
      PHI: (r12v4 java.net.HttpURLConnection) = (r12v1 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection) binds: [B:9:0x0032, B:15:0x0097, B:16:?, B:18:0x00b3, B:33:0x0177] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0432 A[ExcHandler: SocketTimeoutException (r0v2 'e' java.net.SocketTimeoutException A[CUSTOM_DECLARE]), PHI: r12 
      PHI: (r12v3 java.net.HttpURLConnection) = (r12v1 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection) binds: [B:9:0x0032, B:15:0x0097, B:16:?, B:18:0x00b3, B:33:0x0177] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0458 A[ExcHandler: UnknownHostException (r0v0 'e' java.net.UnknownHostException A[CUSTOM_DECLARE]), PHI: r12 
      PHI: (r12v2 java.net.HttpURLConnection) = (r12v1 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection) binds: [B:9:0x0032, B:15:0x0097, B:16:?, B:18:0x00b3, B:33:0x0177] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0230 A[Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276, all -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x024b A[Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276, all -> 0x0272 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0256 A[SYNTHETIC, Splitter:B:61:0x0256] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0276 A[ExcHandler: Exception (r0v20 'e' java.lang.Exception A[CUSTOM_DECLARE]), PHI: r12 
      PHI: (r12v12 java.net.HttpURLConnection) = (r12v1 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection) binds: [B:9:0x0032, B:15:0x0097, B:16:?, B:18:0x00b3, B:33:0x0177] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x02bb A[ExcHandler: IOException (r0v18 'e' java.io.IOException A[CUSTOM_DECLARE]), PHI: r12 
      PHI: (r12v11 java.net.HttpURLConnection) = (r12v1 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection) binds: [B:9:0x0032, B:15:0x0097, B:16:?, B:18:0x00b3, B:33:0x0177] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x02f0 A[ExcHandler: CancellationException (r0v16 'e' java.util.concurrent.CancellationException A[CUSTOM_DECLARE]), PHI: r12 
      PHI: (r12v10 java.net.HttpURLConnection) = (r12v1 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection), (r12v14 java.net.HttpURLConnection) binds: [B:9:0x0032, B:15:0x0097, B:16:?, B:18:0x00b3, B:33:0x0177] A[DONT_GENERATE, DONT_INLINE], Splitter:B:9:0x0032] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static anet.channel.session.C0549b.C0550a m214a(anet.channel.request.Request r22, anet.channel.RequestCb r23) {
        /*
            r1 = r23
            java.lang.String r2 = "hostnameVerifier"
            java.lang.String r3 = "sslSocketFactory"
            java.lang.String r4 = "SSL Error Info."
            java.lang.String r5 = "host"
            java.lang.String r6 = ""
            java.lang.String r7 = "http disconnect"
            java.lang.String r8 = "awcn.HttpConnector"
            anet.channel.session.b$a r9 = new anet.channel.session.b$a
            r9.<init>()
            r10 = 0
            if (r22 == 0) goto L_0x049b
            java.net.URL r11 = r22.getUrl()
            if (r11 != 0) goto L_0x0020
            goto L_0x049b
        L_0x0020:
            r11 = r22
            r12 = r10
        L_0x0023:
            boolean r13 = anet.channel.status.NetworkStatusHelper.isConnected()
            if (r13 != 0) goto L_0x0030
            r2 = -200(0xffffffffffffff38, float:NaN)
            m216a(r11, r9, r1, r2, r10)
            goto L_0x048a
        L_0x0030:
            r13 = 2
            r15 = 0
            java.net.HttpURLConnection r12 = m215a(r11)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            boolean r16 = anet.channel.util.ALog.isPrintLog(r13)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r16 == 0) goto L_0x0083
            java.lang.String r10 = r11.getSeq()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r18 = "request URL"
            r14[r15] = r18     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.net.URL r18 = r12.getURL()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r18 = r18.toString()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r17 = 1
            r14[r17] = r18     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.util.ALog.m328i(r8, r6, r10, r14)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r10 = r11.getSeq()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r18 = "request Method"
            r14[r15] = r18     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r18 = r12.getRequestMethod()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r17 = 1
            r14[r17] = r18     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.util.ALog.m328i(r8, r6, r10, r14)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r10 = r11.getSeq()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r18 = "request headers"
            r14[r15] = r18     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map r18 = r12.getRequestProperties()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r18 = r18.toString()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r17 = 1
            r14[r17] = r18     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.util.ALog.m328i(r8, r6, r10, r14)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x0083:
            anet.channel.statist.RequestStatistic r10 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r10.sendStart = r13     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r10 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r13 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r13 = r13.sendStart     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r15 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x037c, SSLException -> 0x0314, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r20 = r2
            r21 = r3
            long r2 = r15.start     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r13 = r13 - r2
            r10.processTime = r13     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r12.connect()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            m213a((java.net.HttpURLConnection) r12, (anet.channel.request.Request) r11)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.sendEnd = r13     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r3 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r13 = r3.sendEnd     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r3 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x026f, SSLException -> 0x026c, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r10 = r4
            long r3 = r3.sendStart     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r13 = r13 - r3
            r2.sendDataTime = r13     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r2 = r12.getResponseCode()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r9.f387a = r2     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map r2 = r12.getHeaderFields()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map r2 = anet.channel.util.HttpHelper.cloneMap(r2)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r9.f389c = r2     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r2 = r11.getSeq()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = "response code"
            r13 = 0
            r4[r13] = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r3 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r13 = 1
            r4[r13] = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.util.ALog.m328i(r8, r6, r2, r4)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r2 = r11.getSeq()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = "response headers"
            r13 = 0
            r4[r13] = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r3 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r13 = 1
            r4[r13] = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.util.ALog.m328i(r8, r6, r2, r4)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            boolean r2 = anet.channel.util.HttpHelper.checkRedirect(r11, r2)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r2 == 0) goto L_0x0189
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = "Location"
            java.lang.String r2 = anet.channel.util.HttpHelper.getSingleHeaderFieldByKey(r2, r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r2 == 0) goto L_0x0189
            anet.channel.util.HttpUrl r3 = anet.channel.util.HttpUrl.parse(r2)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r3 == 0) goto L_0x0175
            java.lang.String r4 = "redirect"
            java.lang.String r13 = r11.getSeq()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r14 = 2
            java.lang.Object[] r15 = new java.lang.Object[r14]     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r14 = "to url"
            r19 = 0
            r15[r19] = r14     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r14 = r3.toString()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r17 = 1
            r15[r17] = r14     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.util.ALog.m328i(r8, r4, r13, r15)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.request.Request$Builder r4 = r11.newBuilder()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r13 = "GET"
            anet.channel.request.Request$Builder r4 = r4.setMethod(r13)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r13 = 0
            anet.channel.request.Request$Builder r4 = r4.setBody(r13)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.request.Request$Builder r4 = r4.setUrl((anet.channel.util.HttpUrl) r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r14 = r11.getRedirectTimes()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r15 = 1
            int r14 = r14 + r15
            anet.channel.request.Request$Builder r4 = r4.setRedirectTimes(r14)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.request.Request$Builder r4 = r4.setSslSocketFactory(r13)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.request.Request$Builder r4 = r4.setHostnameVerifier(r13)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.request.Request r11 = r4.build()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r4 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r13 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = r3.simpleUrlString()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r4.recordRedirect(r13, r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r3 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3.locationUrl = r2     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r12 == 0) goto L_0x016d
            r12.disconnect()     // Catch:{ Exception -> 0x0164 }
            goto L_0x016d
        L_0x0164:
            r0 = move-exception
            r2 = r0
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            anet.channel.util.ALog.m326e(r8, r7, r4, r2, r3)
        L_0x016d:
            r4 = r10
            r2 = r20
            r3 = r21
            r10 = 0
            goto L_0x0023
        L_0x0175:
            java.lang.String r3 = "redirect url is invalid!"
            java.lang.String r4 = r11.getSeq()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r13 = 2
            java.lang.Object[] r14 = new java.lang.Object[r13]     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r13 = "redirect url"
            r15 = 0
            r14[r15] = r13     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r13 = 1
            r14[r13] = r2     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.util.ALog.m327e(r8, r3, r4, r14)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x0189:
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r3 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r4 = "Content-Encoding"
            java.lang.String r3 = anet.channel.util.HttpHelper.getSingleHeaderFieldByKey(r3, r4)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.contentEncoding = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r3 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r4 = "Content-Type"
            java.lang.String r3 = anet.channel.util.HttpHelper.getSingleHeaderFieldByKey(r3, r4)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.contentType = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r2 = "HEAD"
            java.lang.String r3 = r11.getMethod()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            boolean r2 = r2.equals(r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r2 != 0) goto L_0x0208
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3 = 304(0x130, float:4.26E-43)
            if (r2 == r3) goto L_0x0208
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3 = 204(0xcc, float:2.86E-43)
            if (r2 == r3) goto L_0x0208
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3 = 100
            if (r2 < r3) goto L_0x01c6
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 >= r3) goto L_0x01c6
            goto L_0x0208
        L_0x01c6:
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r2 = anet.channel.util.HttpHelper.parseContentLength(r2)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r9.f390d = r2     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r3 = r9.f390d     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r3 = (long) r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.contentLength = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r2 = "gzip"
            anet.channel.statist.RequestStatistic r3 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = r3.contentEncoding     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r9.f391e = r2     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            boolean r2 = r9.f391e     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r2 == 0) goto L_0x01f3
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = "Content-Encoding"
            r2.remove(r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = "Content-Length"
            r2.remove(r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x01f3:
            if (r1 == 0) goto L_0x01fc
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r3 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r1.onResponseCode(r2, r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x01fc:
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.rspStart = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            m217a(r12, r11, r9, r1)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            goto L_0x0219
        L_0x0208:
            if (r1 == 0) goto L_0x0211
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r3 = r9.f389c     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r1.onResponseCode(r2, r3)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x0211:
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.rspStart = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x0219:
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r3 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r3 = r3.rspStart     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r13 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r13 = r13.sendEnd     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r3 = r3 - r13
            r2.firstDataTime = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.util.concurrent.atomic.AtomicBoolean r2 = r2.isDone     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            boolean r2 = r2.get()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            if (r2 != 0) goto L_0x0249
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r3 = 1
            r2.ret = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            int r3 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.statusCode = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = "SUCCESS"
            r2.msg = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            anet.channel.statist.RequestStatistic r2 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r2.rspEnd = r3     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x0249:
            if (r1 == 0) goto L_0x0254
            int r2 = r9.f387a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            java.lang.String r3 = "SUCCESS"
            anet.channel.statist.RequestStatistic r4 = r11.f322a     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
            r1.onFinish(r2, r3, r4)     // Catch:{ UnknownHostException -> 0x0458, SocketTimeoutException -> 0x0432, ConnectTimeoutException -> 0x040c, ConnectException -> 0x03e4, SSLHandshakeException -> 0x0269, SSLException -> 0x0266, CancellationException -> 0x02f0, IOException -> 0x02bb, Exception -> 0x0276 }
        L_0x0254:
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x025b }
            goto L_0x048a
        L_0x025b:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x0266:
            r0 = move-exception
            goto L_0x031a
        L_0x0269:
            r0 = move-exception
            goto L_0x0382
        L_0x026c:
            r0 = move-exception
            goto L_0x0319
        L_0x026f:
            r0 = move-exception
            goto L_0x0381
        L_0x0272:
            r0 = move-exception
            r1 = r0
            goto L_0x048b
        L_0x0276:
            r0 = move-exception
            r2 = r0
            java.lang.String r3 = r2.getMessage()     // Catch:{ all -> 0x0272 }
            if (r3 == 0) goto L_0x0282
            java.lang.String r6 = r2.getMessage()     // Catch:{ all -> 0x0272 }
        L_0x0282:
            java.lang.String r3 = "not verified"
            boolean r3 = r6.contains(r3)     // Catch:{ all -> 0x0272 }
            if (r3 == 0) goto L_0x0299
            anet.channel.strategy.c r3 = anet.channel.strategy.C0566c.C0567a.f482a     // Catch:{ all -> 0x0272 }
            java.lang.String r4 = r11.getHost()     // Catch:{ all -> 0x0272 }
            r3.mo9042b(r4)     // Catch:{ all -> 0x0272 }
            r3 = -403(0xfffffffffffffe6d, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            goto L_0x029e
        L_0x0299:
            r3 = -101(0xffffffffffffff9b, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
        L_0x029e:
            java.lang.String r1 = "HTTP Exception"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m326e(r8, r1, r3, r2, r5)     // Catch:{ all -> 0x0272 }
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x02b1 }
            goto L_0x048a
        L_0x02b1:
            r0 = move-exception
            r1 = r0
            java.lang.Object[] r2 = new java.lang.Object[r4]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x02bb:
            r0 = move-exception
            r2 = r0
            r3 = -404(0xfffffffffffffe6c, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "IO Exception"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            r6 = 0
            r4[r6] = r5     // Catch:{ all -> 0x0272 }
            java.lang.String r5 = r11.getHost()     // Catch:{ all -> 0x0272 }
            r6 = 1
            r4[r6] = r5     // Catch:{ all -> 0x0272 }
            r5 = 2
            r4[r5] = r2     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m327e(r8, r1, r3, r4)     // Catch:{ all -> 0x0272 }
            anet.channel.status.NetworkStatusHelper.printNetworkDetail()     // Catch:{ all -> 0x0272 }
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x02e5 }
            goto L_0x048a
        L_0x02e5:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x02f0:
            r0 = move-exception
            r2 = r0
            r3 = -204(0xffffffffffffff34, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "HTTP Request Cancel"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m326e(r8, r1, r3, r2, r5)     // Catch:{ all -> 0x0272 }
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x030a }
            goto L_0x048a
        L_0x030a:
            r0 = move-exception
            r1 = r0
            java.lang.Object[] r2 = new java.lang.Object[r4]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x0314:
            r0 = move-exception
            r20 = r2
            r21 = r3
        L_0x0319:
            r10 = r4
        L_0x031a:
            r2 = r0
            anet.channel.strategy.c r3 = anet.channel.strategy.C0566c.C0567a.f482a     // Catch:{ all -> 0x0272 }
            java.lang.String r4 = r11.getHost()     // Catch:{ all -> 0x0272 }
            r3.mo9042b(r4)     // Catch:{ all -> 0x0272 }
            r3 = -402(0xfffffffffffffe6e, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "connect SSLException"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 3
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            r4 = 0
            r6[r4] = r5     // Catch:{ all -> 0x0272 }
            java.lang.String r4 = r11.getHost()     // Catch:{ all -> 0x0272 }
            r5 = 1
            r6[r5] = r4     // Catch:{ all -> 0x0272 }
            r4 = 2
            r6[r4] = r2     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m327e(r8, r1, r3, r6)     // Catch:{ all -> 0x0272 }
            boolean r1 = r12 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ all -> 0x0272 }
            if (r1 == 0) goto L_0x036a
            r1 = r12
            javax.net.ssl.HttpsURLConnection r1 = (javax.net.ssl.HttpsURLConnection) r1     // Catch:{ all -> 0x0272 }
            javax.net.ssl.SSLSocketFactory r1 = r1.getSSLSocketFactory()     // Catch:{ all -> 0x0272 }
            r2 = r12
            javax.net.ssl.HttpsURLConnection r2 = (javax.net.ssl.HttpsURLConnection) r2     // Catch:{ all -> 0x0272 }
            javax.net.ssl.HostnameVerifier r2 = r2.getHostnameVerifier()     // Catch:{ all -> 0x0272 }
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            r5 = 0
            r4[r5] = r21     // Catch:{ all -> 0x0272 }
            r5 = 1
            r4[r5] = r1     // Catch:{ all -> 0x0272 }
            r1 = 2
            r4[r1] = r20     // Catch:{ all -> 0x0272 }
            r1 = 3
            r4[r1] = r2     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m327e(r8, r10, r3, r4)     // Catch:{ all -> 0x0272 }
        L_0x036a:
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x0371 }
            goto L_0x048a
        L_0x0371:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x037c:
            r0 = move-exception
            r20 = r2
            r21 = r3
        L_0x0381:
            r10 = r4
        L_0x0382:
            r2 = r0
            anet.channel.strategy.c r3 = anet.channel.strategy.C0566c.C0567a.f482a     // Catch:{ all -> 0x0272 }
            java.lang.String r4 = r11.getHost()     // Catch:{ all -> 0x0272 }
            r3.mo9042b(r4)     // Catch:{ all -> 0x0272 }
            r3 = -402(0xfffffffffffffe6e, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "HTTP Connect SSLHandshakeException"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 3
            java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            r4 = 0
            r6[r4] = r5     // Catch:{ all -> 0x0272 }
            java.lang.String r4 = r11.getHost()     // Catch:{ all -> 0x0272 }
            r5 = 1
            r6[r5] = r4     // Catch:{ all -> 0x0272 }
            r4 = 2
            r6[r4] = r2     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m327e(r8, r1, r3, r6)     // Catch:{ all -> 0x0272 }
            boolean r1 = r12 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ all -> 0x0272 }
            if (r1 == 0) goto L_0x03d2
            r1 = r12
            javax.net.ssl.HttpsURLConnection r1 = (javax.net.ssl.HttpsURLConnection) r1     // Catch:{ all -> 0x0272 }
            javax.net.ssl.SSLSocketFactory r1 = r1.getSSLSocketFactory()     // Catch:{ all -> 0x0272 }
            r2 = r12
            javax.net.ssl.HttpsURLConnection r2 = (javax.net.ssl.HttpsURLConnection) r2     // Catch:{ all -> 0x0272 }
            javax.net.ssl.HostnameVerifier r2 = r2.getHostnameVerifier()     // Catch:{ all -> 0x0272 }
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            r5 = 0
            r4[r5] = r21     // Catch:{ all -> 0x0272 }
            r5 = 1
            r4[r5] = r1     // Catch:{ all -> 0x0272 }
            r1 = 2
            r4[r1] = r20     // Catch:{ all -> 0x0272 }
            r1 = 3
            r4[r1] = r2     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m327e(r8, r10, r3, r4)     // Catch:{ all -> 0x0272 }
        L_0x03d2:
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x03d9 }
            goto L_0x048a
        L_0x03d9:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x03e4:
            r0 = move-exception
            r2 = r0
            r3 = -406(0xfffffffffffffe6a, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "HTTP Connect Exception"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m326e(r8, r1, r3, r2, r5)     // Catch:{ all -> 0x0272 }
            anet.channel.status.NetworkStatusHelper.printNetworkDetail()     // Catch:{ all -> 0x0272 }
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x0401 }
            goto L_0x048a
        L_0x0401:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x040c:
            r0 = move-exception
            r2 = r0
            r3 = -400(0xfffffffffffffe70, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "HTTP Connect Timeout"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m326e(r8, r1, r3, r2, r5)     // Catch:{ all -> 0x0272 }
            anet.channel.status.NetworkStatusHelper.printNetworkDetail()     // Catch:{ all -> 0x0272 }
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x0428 }
            goto L_0x048a
        L_0x0428:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x0432:
            r0 = move-exception
            r2 = r0
            r3 = -401(0xfffffffffffffe6f, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "HTTP Socket Timeout"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m326e(r8, r1, r3, r2, r5)     // Catch:{ all -> 0x0272 }
            anet.channel.status.NetworkStatusHelper.printNetworkDetail()     // Catch:{ all -> 0x0272 }
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x044e }
            goto L_0x048a
        L_0x044e:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
            goto L_0x048a
        L_0x0458:
            r0 = move-exception
            r2 = r0
            r3 = -405(0xfffffffffffffe6b, float:NaN)
            m216a(r11, r9, r1, r3, r2)     // Catch:{ all -> 0x0272 }
            java.lang.String r1 = "Unknown Host Exception"
            java.lang.String r3 = r11.getSeq()     // Catch:{ all -> 0x0272 }
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x0272 }
            r6 = 0
            r4[r6] = r5     // Catch:{ all -> 0x0272 }
            java.lang.String r5 = r11.getHost()     // Catch:{ all -> 0x0272 }
            r6 = 1
            r4[r6] = r5     // Catch:{ all -> 0x0272 }
            r5 = 2
            r4[r5] = r2     // Catch:{ all -> 0x0272 }
            anet.channel.util.ALog.m327e(r8, r1, r3, r4)     // Catch:{ all -> 0x0272 }
            anet.channel.status.NetworkStatusHelper.printNetworkDetail()     // Catch:{ all -> 0x0272 }
            if (r12 == 0) goto L_0x048a
            r12.disconnect()     // Catch:{ Exception -> 0x0481 }
            goto L_0x048a
        L_0x0481:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r7, r3, r1, r2)
        L_0x048a:
            return r9
        L_0x048b:
            if (r12 == 0) goto L_0x049a
            r12.disconnect()     // Catch:{ Exception -> 0x0491 }
            goto L_0x049a
        L_0x0491:
            r0 = move-exception
            r2 = r0
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            anet.channel.util.ALog.m326e(r8, r7, r4, r2, r3)
        L_0x049a:
            throw r1
        L_0x049b:
            r4 = r10
            if (r1 == 0) goto L_0x04ac
            r2 = -102(0xffffffffffffff9a, float:NaN)
            java.lang.String r3 = anet.channel.util.ErrorConstant.getErrMsg(r2)
            anet.channel.statist.RequestStatistic r5 = new anet.channel.statist.RequestStatistic
            r5.<init>(r4, r4)
            r1.onFinish(r2, r3, r5)
        L_0x04ac:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.session.C0549b.m214a(anet.channel.request.Request, anet.channel.RequestCb):anet.channel.session.b$a");
    }

    /* renamed from: a */
    private static void m216a(Request request, C0550a aVar, RequestCb requestCb, int i, Throwable th) {
        String errMsg = ErrorConstant.getErrMsg(i);
        ALog.m327e("awcn.HttpConnector", "onException", request.getSeq(), Constants.KEY_ERROR_CODE, Integer.valueOf(i), "errMsg", errMsg, "url", request.getUrlString(), Constants.KEY_HOST, request.getHost());
        if (aVar != null) {
            aVar.f387a = i;
        }
        if (!request.f322a.isDone.get()) {
            request.f322a.statusCode = i;
            request.f322a.msg = errMsg;
            request.f322a.rspEnd = System.currentTimeMillis();
            if (i != -204) {
                AppMonitor.getInstance().commitStat(new ExceptionStatistic(i, errMsg, request.f322a, th));
            }
        }
        if (requestCb != null) {
            requestCb.onFinish(i, errMsg, request.f322a);
        }
    }

    /* renamed from: a */
    private static HttpURLConnection m215a(Request request) throws IOException {
        HttpURLConnection httpURLConnection;
        Pair<String, Integer> wifiProxy = NetworkStatusHelper.getWifiProxy();
        Proxy proxy = wifiProxy != null ? new Proxy(Proxy.Type.HTTP, new InetSocketAddress((String) wifiProxy.first, ((Integer) wifiProxy.second).intValue())) : null;
        C0608g a = C0608g.m360a();
        if (NetworkStatusHelper.getStatus().isMobile() && a != null) {
            proxy = a.mo9108b();
        }
        URL url = request.getUrl();
        if (proxy != null) {
            httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        httpURLConnection.setConnectTimeout(request.getConnectTimeout());
        httpURLConnection.setReadTimeout(request.getReadTimeout());
        httpURLConnection.setRequestMethod(request.getMethod());
        if (request.containsBody()) {
            httpURLConnection.setDoOutput(true);
        }
        Map<String, String> headers = request.getHeaders();
        for (Map.Entry next : headers.entrySet()) {
            httpURLConnection.addRequestProperty((String) next.getKey(), (String) next.getValue());
        }
        String str = headers.get("Host");
        if (str == null) {
            str = request.getHost();
        }
        String concatString = request.getHttpUrl().containsNonDefaultPort() ? StringUtils.concatString(str, ":", String.valueOf(request.getHttpUrl().getPort())) : str;
        httpURLConnection.setRequestProperty("Host", concatString);
        if (NetworkStatusHelper.getApn().equals("cmwap")) {
            httpURLConnection.setRequestProperty(HttpConstant.X_ONLINE_HOST, concatString);
        }
        if (!headers.containsKey("Accept-Encoding")) {
            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
        }
        if (a != null) {
            httpURLConnection.setRequestProperty("Authorization", a.mo9109c());
        }
        if (url.getProtocol().equalsIgnoreCase("https")) {
            m218a(httpURLConnection, request, str);
        }
        httpURLConnection.setInstanceFollowRedirects(false);
        return httpURLConnection;
    }

    /* renamed from: a */
    private static void m218a(HttpURLConnection httpURLConnection, Request request, String str) {
        if (Integer.parseInt(Build.VERSION.SDK) < 8) {
            ALog.m327e("awcn.HttpConnector", "supportHttps", "[supportHttps]Froyo 以下版本不支持https", new Object[0]);
            return;
        }
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
        if (request.getSslSocketFactory() != null) {
            httpsURLConnection.setSSLSocketFactory(request.getSslSocketFactory());
        } else if (C0603b.f577a != null) {
            httpsURLConnection.setSSLSocketFactory(C0603b.f577a);
            if (ALog.isPrintLog(2)) {
                ALog.m328i("awcn.HttpConnector", "HttpSslUtil", request.getSeq(), "SslSocketFactory", C0603b.f577a);
            }
        }
        if (request.getHostnameVerifier() != null) {
            httpsURLConnection.setHostnameVerifier(request.getHostnameVerifier());
        } else if (C0603b.f578b != null) {
            httpsURLConnection.setHostnameVerifier(C0603b.f578b);
            if (ALog.isPrintLog(2)) {
                ALog.m328i("awcn.HttpConnector", "HttpSslUtil", request.getSeq(), "HostnameVerifier", C0603b.f578b);
            }
        } else {
            httpsURLConnection.setHostnameVerifier(new C0551c(str));
        }
    }

    /* renamed from: a */
    private static int m213a(HttpURLConnection httpURLConnection, Request request) {
        int i = 0;
        if (request.containsBody()) {
            OutputStream outputStream = null;
            try {
                OutputStream outputStream2 = httpURLConnection.getOutputStream();
                int postBody = request.postBody(outputStream2);
                if (outputStream2 != null) {
                    try {
                        outputStream2.flush();
                        outputStream2.close();
                    } catch (IOException e) {
                        ALog.m326e("awcn.HttpConnector", "postData", request.getSeq(), e, new Object[0]);
                    }
                }
                i = postBody;
            } catch (Exception e2) {
                ALog.m326e("awcn.HttpConnector", "postData error", request.getSeq(), e2, new Object[0]);
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e3) {
                        ALog.m326e("awcn.HttpConnector", "postData", request.getSeq(), e3, new Object[0]);
                    }
                }
            } catch (Throwable th) {
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e4) {
                        ALog.m326e("awcn.HttpConnector", "postData", request.getSeq(), e4, new Object[0]);
                    }
                }
                throw th;
            }
            long j = (long) i;
            request.f322a.reqBodyInflateSize = j;
            request.f322a.reqBodyDeflateSize = j;
            request.f322a.sendDataSize = j;
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f5 A[SYNTHETIC, Splitter:B:59:0x00f5] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void m217a(java.net.HttpURLConnection r12, anet.channel.request.Request r13, anet.channel.session.C0549b.C0550a r14, anet.channel.RequestCb r15) throws java.io.IOException, java.util.concurrent.CancellationException {
        /*
            java.net.URL r0 = r12.getURL()
            r0.toString()
            r0 = 1
            r1 = 2
            r2 = 0
            r3 = 0
            java.io.InputStream r12 = r12.getInputStream()     // Catch:{ IOException -> 0x0010 }
            goto L_0x003e
        L_0x0010:
            r4 = move-exception
            boolean r4 = r4 instanceof java.io.FileNotFoundException
            java.lang.String r5 = "awcn.HttpConnector"
            if (r4 == 0) goto L_0x002c
            java.lang.String r4 = r13.getSeq()
            java.lang.Object[] r6 = new java.lang.Object[r1]
            java.lang.String r7 = "url"
            r6[r2] = r7
            java.lang.String r7 = r13.getUrlString()
            r6[r0] = r7
            java.lang.String r7 = "File not found"
            anet.channel.util.ALog.m330w(r5, r7, r4, r6)
        L_0x002c:
            java.io.InputStream r12 = r12.getErrorStream()     // Catch:{ Exception -> 0x0031 }
            goto L_0x003e
        L_0x0031:
            r12 = move-exception
            java.lang.String r4 = r13.getSeq()
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.String r7 = "get error stream failed."
            anet.channel.util.ALog.m326e(r5, r7, r4, r12, r6)
            r12 = r3
        L_0x003e:
            if (r12 != 0) goto L_0x0046
            r12 = -404(0xfffffffffffffe6c, float:NaN)
            m216a(r13, r14, r15, r12, r3)
            return
        L_0x0046:
            if (r15 != 0) goto L_0x0060
            int r4 = r14.f390d
            if (r4 > 0) goto L_0x004f
            r1 = 1024(0x400, float:1.435E-42)
            goto L_0x005a
        L_0x004f:
            boolean r4 = r14.f391e
            if (r4 == 0) goto L_0x0058
            int r4 = r14.f390d
            int r1 = r4 * 2
            goto L_0x005a
        L_0x0058:
            int r1 = r14.f390d
        L_0x005a:
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>(r1)
            goto L_0x0061
        L_0x0060:
            r4 = r3
        L_0x0061:
            anet.channel.util.a r1 = new anet.channel.util.a     // Catch:{ all -> 0x00dc }
            r1.<init>(r12)     // Catch:{ all -> 0x00dc }
            boolean r5 = r14.f391e     // Catch:{ all -> 0x00da }
            if (r5 == 0) goto L_0x0071
            java.util.zip.GZIPInputStream r5 = new java.util.zip.GZIPInputStream     // Catch:{ all -> 0x00da }
            r5.<init>(r1)     // Catch:{ all -> 0x00da }
            r12 = r5
            goto L_0x0072
        L_0x0071:
            r12 = r1
        L_0x0072:
            r5 = r3
        L_0x0073:
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00da }
            boolean r6 = r6.isInterrupted()     // Catch:{ all -> 0x00da }
            if (r6 != 0) goto L_0x00d2
            if (r5 != 0) goto L_0x0087
            anet.channel.bytes.a r5 = anet.channel.bytes.C0485a.C0486a.f171a     // Catch:{ all -> 0x00da }
            r6 = 2048(0x800, float:2.87E-42)
            anet.channel.bytes.ByteArray r5 = r5.mo8741a((int) r6)     // Catch:{ all -> 0x00da }
        L_0x0087:
            int r6 = r5.readFrom(r12)     // Catch:{ all -> 0x00da }
            r7 = -1
            if (r6 == r7) goto L_0x00a8
            if (r4 == 0) goto L_0x0094
            r5.writeTo(r4)     // Catch:{ all -> 0x00da }
            goto L_0x0098
        L_0x0094:
            r15.onDataReceive(r5, r2)     // Catch:{ all -> 0x00da }
            r5 = r3
        L_0x0098:
            anet.channel.statist.RequestStatistic r7 = r13.f322a     // Catch:{ all -> 0x00da }
            long r8 = r7.recDataSize     // Catch:{ all -> 0x00da }
            long r10 = (long) r6     // Catch:{ all -> 0x00da }
            long r8 = r8 + r10
            r7.recDataSize = r8     // Catch:{ all -> 0x00da }
            anet.channel.statist.RequestStatistic r6 = r13.f322a     // Catch:{ all -> 0x00da }
            long r7 = r6.rspBodyInflateSize     // Catch:{ all -> 0x00da }
            long r7 = r7 + r10
            r6.rspBodyInflateSize = r7     // Catch:{ all -> 0x00da }
            goto L_0x0073
        L_0x00a8:
            if (r4 == 0) goto L_0x00ae
            r5.recycle()     // Catch:{ all -> 0x00da }
            goto L_0x00b1
        L_0x00ae:
            r15.onDataReceive(r5, r0)     // Catch:{ all -> 0x00da }
        L_0x00b1:
            if (r4 == 0) goto L_0x00b9
            byte[] r15 = r4.toByteArray()     // Catch:{ all -> 0x00da }
            r14.f388b = r15     // Catch:{ all -> 0x00da }
        L_0x00b9:
            anet.channel.statist.RequestStatistic r14 = r13.f322a
            long r2 = java.lang.System.currentTimeMillis()
            anet.channel.statist.RequestStatistic r15 = r13.f322a
            long r4 = r15.rspStart
            long r2 = r2 - r4
            r14.recDataTime = r2
            anet.channel.statist.RequestStatistic r13 = r13.f322a
            long r14 = r1.mo9102a()
            r13.rspBodyDeflateSize = r14
            r12.close()     // Catch:{ IOException -> 0x00d1 }
        L_0x00d1:
            return
        L_0x00d2:
            java.util.concurrent.CancellationException r14 = new java.util.concurrent.CancellationException     // Catch:{ all -> 0x00da }
            java.lang.String r15 = "task cancelled"
            r14.<init>(r15)     // Catch:{ all -> 0x00da }
            throw r14     // Catch:{ all -> 0x00da }
        L_0x00da:
            r14 = move-exception
            goto L_0x00de
        L_0x00dc:
            r14 = move-exception
            r1 = r3
        L_0x00de:
            anet.channel.statist.RequestStatistic r15 = r13.f322a
            long r2 = java.lang.System.currentTimeMillis()
            anet.channel.statist.RequestStatistic r0 = r13.f322a
            long r4 = r0.rspStart
            long r2 = r2 - r4
            r15.recDataTime = r2
            anet.channel.statist.RequestStatistic r13 = r13.f322a
            long r0 = r1.mo9102a()
            r13.rspBodyDeflateSize = r0
            if (r12 == 0) goto L_0x00f8
            r12.close()     // Catch:{ IOException -> 0x00f8 }
        L_0x00f8:
            goto L_0x00fa
        L_0x00f9:
            throw r14
        L_0x00fa:
            goto L_0x00f9
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.session.C0549b.m217a(java.net.HttpURLConnection, anet.channel.request.Request, anet.channel.session.b$a, anet.channel.RequestCb):void");
    }
}
