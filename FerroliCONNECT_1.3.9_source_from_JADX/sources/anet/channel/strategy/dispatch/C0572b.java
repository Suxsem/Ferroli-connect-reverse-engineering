package anet.channel.strategy.dispatch;

import android.util.Base64InputStream;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.flow.FlowStat;
import anet.channel.flow.NetworkAnalysis;
import anet.channel.statist.AmdcStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.utils.C0594c;
import anet.channel.util.ALog;
import anet.channel.util.C0604c;
import anet.channel.util.HttpConstant;
import com.taobao.accs.common.Constants;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;

/* renamed from: anet.channel.strategy.dispatch.b */
/* compiled from: Taobao */
class C0572b {

    /* renamed from: a */
    static AtomicInteger f496a = new AtomicInteger(0);

    /* renamed from: b */
    static HostnameVerifier f497b = new C0573c();

    /* renamed from: c */
    static Random f498c = new Random();

    C0572b() {
    }

    /* renamed from: a */
    static List<IConnStrategy> m290a(String str) {
        List<IConnStrategy> list = Collections.EMPTY_LIST;
        if (!NetworkStatusHelper.isProxy()) {
            list = StrategyCenter.getInstance().getConnStrategyListByHost(DispatchConstants.getAmdcServerDomain());
            ListIterator<IConnStrategy> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                if (!listIterator.next().getProtocol().protocol.equalsIgnoreCase(str)) {
                    listIterator.remove();
                }
            }
        }
        return list;
    }

    /* renamed from: a */
    public static void m293a(Map map) {
        String str;
        IConnStrategy iConnStrategy;
        String str2;
        String str3;
        if (map != null) {
            if (AmdcRuntimeInfo.isForceHttps()) {
                str = "https";
            } else {
                str = StrategyCenter.getInstance().getSchemeByHost(DispatchConstants.getAmdcServerDomain(), "http");
            }
            List<IConnStrategy> a = m290a(str);
            int i = 0;
            while (i < 3) {
                HashMap hashMap = new HashMap(map);
                if (i != 2) {
                    iConnStrategy = !a.isEmpty() ? a.remove(0) : null;
                    if (iConnStrategy != null) {
                        str2 = m289a(str, iConnStrategy.getIp(), iConnStrategy.getPort(), (Map<String, String>) hashMap, i);
                    } else {
                        str2 = m289a(str, (String) null, 0, (Map<String, String>) hashMap, i);
                    }
                } else {
                    String[] amdcServerFixIp = DispatchConstants.getAmdcServerFixIp();
                    if (amdcServerFixIp == null || amdcServerFixIp.length <= 0) {
                        str3 = m289a(str, (String) null, 0, (Map<String, String>) hashMap, i);
                    } else {
                        str3 = m289a(str, amdcServerFixIp[f498c.nextInt(amdcServerFixIp.length)], 0, (Map<String, String>) hashMap, i);
                    }
                    String str4 = str3;
                    iConnStrategy = null;
                    str2 = str4;
                }
                int a2 = m287a(str2, (Map) hashMap, i);
                if (iConnStrategy != null) {
                    ConnEvent connEvent = new ConnEvent();
                    connEvent.isSuccess = a2 == 0;
                    StrategyCenter.getInstance().notifyConnEvent(DispatchConstants.getAmdcServerDomain(), iConnStrategy, connEvent);
                }
                if (a2 != 0 && a2 != 2) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    /* renamed from: a */
    private static String m289a(String str, String str2, int i, Map<String, String> map, int i2) {
        StringBuilder sb = new StringBuilder(64);
        if (!AmdcRuntimeInfo.isForceHttps() && i2 == 2 && "https".equalsIgnoreCase(str) && f498c.nextBoolean()) {
            str = "http";
        }
        sb.append(str);
        sb.append(HttpConstant.SCHEME_SPLIT);
        if (str2 != null) {
            if (C0604c.m347a() && C0594c.m319a(str2)) {
                try {
                    str2 = C0604c.m345a(str2);
                } catch (Exception unused) {
                }
            }
            if (C0594c.m320b(str2)) {
                sb.append('[');
                sb.append(str2);
                sb.append(']');
            } else {
                sb.append(str2);
            }
            if (i == 0) {
                i = "https".equalsIgnoreCase(str) ? Constants.PORT : 80;
            }
            sb.append(":");
            sb.append(i);
        } else {
            sb.append(DispatchConstants.getAmdcServerDomain());
        }
        sb.append(DispatchConstants.serverPath);
        TreeMap treeMap = new TreeMap();
        treeMap.put("appkey", map.remove("appkey"));
        treeMap.put("v", map.remove("v"));
        treeMap.put("platform", map.remove("platform"));
        sb.append('?');
        sb.append(C0594c.m318a(treeMap, "utf-8"));
        return sb.toString();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:133|134|(2:136|137)|140) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x009c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:?, code lost:
        anet.channel.strategy.dispatch.HttpDispatcher.getInstance().mo9044a(new anet.channel.strategy.dispatch.DispatchEvent(0, (java.lang.Object) null));
        anet.channel.util.ALog.m327e(r8, "resolve amdc anser failed", r5, new java.lang.Object[0]);
        m292a("-1004", "resolve answer failed", r10, r2, 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x027a, code lost:
        if (r15 != null) goto L_0x027c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:?, code lost:
        r15.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0280, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0281, code lost:
        anet.channel.util.ALog.m326e(r8, "http disconnect failed", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x009d, code lost:
        r1 = r0;
        r8 = "awcn.DispatchCore";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0289, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x028a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x028c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x028d, code lost:
        r10 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0290, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0298, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0299, code lost:
        r8 = "awcn.DispatchCore";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x029d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x029e, code lost:
        r8 = "awcn.DispatchCore";
        r10 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x02a1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x02a2, code lost:
        r8 = "awcn.DispatchCore";
        r1 = r0;
        r15 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x02b4, code lost:
        r1 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:?, code lost:
        r13.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x02cb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x02cc, code lost:
        anet.channel.util.ALog.m326e(r8, "http disconnect failed", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:?, code lost:
        r15.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x02dd, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x02de, code lost:
        anet.channel.util.ALog.m326e(r8, "http disconnect failed", (java.lang.String) null, r0, new java.lang.Object[0]);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:133:0x025e */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0290 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:81:0x01aa] */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0298 A[ExcHandler: Throwable (th java.lang.Throwable), Splitter:B:6:0x005e] */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x02a1 A[ExcHandler: all (r0v9 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:1:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x02b4 A[Catch:{ all -> 0x02d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x02c7 A[SYNTHETIC, Splitter:B:166:0x02c7] */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x02d9 A[SYNTHETIC, Splitter:B:174:0x02d9] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int m287a(java.lang.String r19, java.util.Map r20, int r21) {
        /*
            r0 = r19
            r1 = r20
            r2 = r21
            java.lang.String r3 = "gzip"
            java.lang.String r4 = "http disconnect failed"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "AMDC"
            r5.append(r6)
            java.util.concurrent.atomic.AtomicInteger r6 = f496a
            int r6 = r6.incrementAndGet()
            java.lang.String r6 = java.lang.String.valueOf(r6)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = 4
            java.lang.Object[] r7 = new java.lang.Object[r6]
            r8 = 0
            java.lang.String r9 = "url"
            r7[r8] = r9
            r9 = 1
            r7[r9] = r0
            r10 = 2
            java.lang.String r11 = "\nhost"
            r7[r10] = r11
            java.lang.String r11 = "domain"
            java.lang.Object r11 = r1.get(r11)
            java.lang.String r11 = r11.toString()
            r12 = 3
            r7[r12] = r11
            java.lang.String r11 = "awcn.DispatchCore"
            java.lang.String r13 = "send amdc request"
            anet.channel.util.ALog.m328i(r11, r13, r5, r7)
            java.lang.String r7 = "Env"
            java.lang.Object r7 = r1.remove(r7)
            anet.channel.entity.ENV r7 = (anet.channel.entity.ENV) r7
            java.net.URL r14 = new java.net.URL     // Catch:{ Throwable -> 0x02a6, all -> 0x02a1 }
            r14.<init>(r0)     // Catch:{ Throwable -> 0x02a6, all -> 0x02a1 }
            java.net.URLConnection r15 = r14.openConnection()     // Catch:{ Throwable -> 0x029d, all -> 0x02a1 }
            java.net.HttpURLConnection r15 = (java.net.HttpURLConnection) r15     // Catch:{ Throwable -> 0x029d, all -> 0x02a1 }
            r12 = 20000(0x4e20, float:2.8026E-41)
            r15.setConnectTimeout(r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            r15.setReadTimeout(r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r12 = "POST"
            r15.setRequestMethod(r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            r15.setDoOutput(r9)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            r15.setDoInput(r9)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r12 = "Connection"
            java.lang.String r6 = "close"
            r15.addRequestProperty(r12, r6)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r6 = "Accept-Encoding"
            r15.addRequestProperty(r6, r3)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r6 = "Host"
            java.lang.String r12 = anet.channel.strategy.dispatch.DispatchConstants.getAmdcServerDomain()     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            r15.addRequestProperty(r6, r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            r15.setInstanceFollowRedirects(r8)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r6 = r14.getProtocol()     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r12 = "https"
            boolean r6 = r6.equals(r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            if (r6 == 0) goto L_0x00a1
            r6 = r15
            javax.net.ssl.HttpsURLConnection r6 = (javax.net.ssl.HttpsURLConnection) r6     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            javax.net.ssl.HostnameVerifier r12 = f497b     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r6.setHostnameVerifier(r12)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            goto L_0x00a1
        L_0x009c:
            r0 = move-exception
            r1 = r0
            r8 = r11
            goto L_0x02d7
        L_0x00a1:
            boolean r6 = anet.channel.util.ALog.isPrintLog(r9)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            if (r6 == 0) goto L_0x00bc
            java.lang.String r6 = "amdc request."
            java.lang.Object[] r12 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r16 = "headers"
            r12[r8] = r16     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.util.Map r16 = r15.getRequestProperties()     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r16 = r16.toString()     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r12[r9] = r16     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            anet.channel.util.ALog.m325d(r11, r6, r5, r12)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
        L_0x00bc:
            java.io.OutputStream r6 = r15.getOutputStream()     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r12 = "utf-8"
            java.lang.String r1 = anet.channel.strategy.utils.C0594c.m318a(r1, r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            byte[] r1 = r1.getBytes()     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            r6.write(r1)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            int r6 = r15.getResponseCode()     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            boolean r12 = anet.channel.util.ALog.isPrintLog(r9)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            if (r12 == 0) goto L_0x00f7
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r12.<init>()     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r13 = "amdc response. code: "
            r12.append(r13)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r12.append(r6)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r12 = r12.toString()     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.Object[] r13 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r17 = "\nheaders"
            r13[r8] = r17     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.util.Map r17 = r15.getHeaderFields()     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r13[r9] = r17     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            anet.channel.util.ALog.m325d(r11, r12, r5, r13)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
        L_0x00f7:
            r12 = 200(0xc8, float:2.8E-43)
            if (r6 == r12) goto L_0x011d
            r0 = 302(0x12e, float:4.23E-43)
            if (r6 == r0) goto L_0x0105
            r0 = 307(0x133, float:4.3E-43)
            if (r6 != r0) goto L_0x0104
            goto L_0x0105
        L_0x0104:
            r10 = 1
        L_0x0105:
            java.lang.String r0 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r1 = "response code not 200"
            m292a((java.lang.String) r0, (java.lang.String) r1, (java.net.URL) r14, (int) r2, (int) r10)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            if (r15 == 0) goto L_0x011c
            r15.disconnect()     // Catch:{ Exception -> 0x0114 }
            goto L_0x011c
        L_0x0114:
            r0 = move-exception
            r1 = r0
            java.lang.Object[] r0 = new java.lang.Object[r8]
            r2 = 0
            anet.channel.util.ALog.m326e(r11, r4, r2, r1, r0)
        L_0x011c:
            return r10
        L_0x011d:
            java.lang.String r6 = "x-am-code"
            java.lang.String r6 = r15.getHeaderField(r6)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r12 = "1000"
            boolean r12 = r12.equals(r6)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            if (r12 != 0) goto L_0x0160
            java.lang.String r0 = "1007"
            boolean r0 = r0.equals(r6)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            if (r0 != 0) goto L_0x013d
            java.lang.String r0 = "1008"
            boolean r0 = r0.equals(r6)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            if (r0 == 0) goto L_0x013c
            goto L_0x013d
        L_0x013c:
            r10 = 1
        L_0x013d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r0.<init>()     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r1 = "return code: "
            r0.append(r1)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r0.append(r6)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            m292a((java.lang.String) r6, (java.lang.String) r0, (java.net.URL) r14, (int) r2, (int) r10)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            if (r15 == 0) goto L_0x015f
            r15.disconnect()     // Catch:{ Exception -> 0x0157 }
            goto L_0x015f
        L_0x0157:
            r0 = move-exception
            r1 = r0
            java.lang.Object[] r0 = new java.lang.Object[r8]
            r2 = 0
            anet.channel.util.ALog.m326e(r11, r4, r2, r1, r0)
        L_0x015f:
            return r10
        L_0x0160:
            java.lang.String r12 = "x-am-sign"
            java.lang.String r12 = r15.getHeaderField(r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            boolean r13 = android.text.TextUtils.isEmpty(r12)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            if (r13 == 0) goto L_0x0182
            java.lang.String r0 = "-1001"
            java.lang.String r1 = "response sign is empty"
            m292a((java.lang.String) r0, (java.lang.String) r1, (java.net.URL) r14, (int) r2, (int) r9)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            if (r15 == 0) goto L_0x0181
            r15.disconnect()     // Catch:{ Exception -> 0x0179 }
            goto L_0x0181
        L_0x0179:
            r0 = move-exception
            r1 = r0
            java.lang.Object[] r0 = new java.lang.Object[r8]
            r2 = 0
            anet.channel.util.ALog.m326e(r11, r4, r2, r1, r0)
        L_0x0181:
            return r9
        L_0x0182:
            java.io.InputStream r13 = r15.getInputStream()     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r8 = r15.getContentEncoding()     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            boolean r3 = r3.equalsIgnoreCase(r8)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            java.lang.String r3 = m288a(r13, r3)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            boolean r8 = anet.channel.util.ALog.isPrintLog(r9)     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            if (r8 == 0) goto L_0x01a7
            java.lang.String r8 = "amdc response body"
            java.lang.Object[] r13 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            java.lang.String r18 = "\nbody"
            r17 = 0
            r13[r17] = r18     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            r13[r9] = r3     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
            anet.channel.util.ALog.m325d(r11, r8, r5, r13)     // Catch:{ Throwable -> 0x0298, all -> 0x009c }
        L_0x01a7:
            int r1 = r1.length     // Catch:{ Throwable -> 0x0298, all -> 0x0294 }
            r8 = r11
            long r10 = (long) r1
            int r1 = r15.getContentLength()     // Catch:{ Throwable -> 0x0292, all -> 0x0290 }
            r18 = r14
            long r13 = (long) r1
            m291a((java.lang.String) r0, (long) r10, (long) r13)     // Catch:{ Throwable -> 0x028c, all -> 0x0290 }
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x028c, all -> 0x0290 }
            if (r0 == 0) goto L_0x01d3
            java.lang.String r0 = "-1002"
            java.lang.String r1 = "read answer error"
            r10 = r18
            m292a((java.lang.String) r0, (java.lang.String) r1, (java.net.URL) r10, (int) r2, (int) r9)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            if (r15 == 0) goto L_0x01d2
            r15.disconnect()     // Catch:{ Exception -> 0x01c9 }
            goto L_0x01d2
        L_0x01c9:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r0 = new java.lang.Object[r2]
            r2 = 0
            anet.channel.util.ALog.m326e(r8, r4, r2, r1, r0)
        L_0x01d2:
            return r9
        L_0x01d3:
            r10 = r18
            anet.channel.strategy.dispatch.IAmdcSign r0 = anet.channel.strategy.dispatch.AmdcRuntimeInfo.getSign()     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            if (r0 == 0) goto L_0x01e0
            java.lang.String r13 = r0.sign(r3)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            goto L_0x01e1
        L_0x01e0:
            r13 = 0
        L_0x01e1:
            boolean r0 = r13.equalsIgnoreCase(r12)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            if (r0 != 0) goto L_0x0215
            java.lang.String r0 = "check ret sign failed"
            r1 = 4
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            java.lang.String r3 = "retSign"
            r6 = 0
            r1[r6] = r3     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            r1[r9] = r12     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            java.lang.String r3 = "checkSign"
            r6 = 2
            r1[r6] = r3     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            r3 = 3
            r1[r3] = r13     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            anet.channel.util.ALog.m327e(r8, r0, r5, r1)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            java.lang.String r0 = "-1003"
            java.lang.String r1 = "check sign failed"
            m292a((java.lang.String) r0, (java.lang.String) r1, (java.net.URL) r10, (int) r2, (int) r9)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            if (r15 == 0) goto L_0x0214
            r15.disconnect()     // Catch:{ Exception -> 0x020b }
            goto L_0x0214
        L_0x020b:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r0 = new java.lang.Object[r2]
            r2 = 0
            anet.channel.util.ALog.m326e(r8, r4, r2, r1, r0)
        L_0x0214:
            return r9
        L_0x0215:
            org.json.JSONTokener r0 = new org.json.JSONTokener     // Catch:{ JSONException -> 0x025e }
            r0.<init>(r3)     // Catch:{ JSONException -> 0x025e }
            java.lang.Object r0 = r0.nextValue()     // Catch:{ JSONException -> 0x025e }
            org.json.JSONObject r0 = (org.json.JSONObject) r0     // Catch:{ JSONException -> 0x025e }
            anet.channel.entity.ENV r1 = anet.channel.GlobalAppRuntimeInfo.getEnv()     // Catch:{ JSONException -> 0x025e }
            if (r1 == r7) goto L_0x023d
            java.lang.String r0 = "env change, do not notify result"
            r1 = 0
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ JSONException -> 0x025e }
            anet.channel.util.ALog.m330w(r8, r0, r5, r3)     // Catch:{ JSONException -> 0x025e }
            if (r15 == 0) goto L_0x023c
            r15.disconnect()     // Catch:{ Exception -> 0x0234 }
            goto L_0x023c
        L_0x0234:
            r0 = move-exception
            r2 = r0
            java.lang.Object[] r0 = new java.lang.Object[r1]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r4, r3, r2, r0)
        L_0x023c:
            return r1
        L_0x023d:
            anet.channel.strategy.dispatch.HttpDispatcher r1 = anet.channel.strategy.dispatch.HttpDispatcher.getInstance()     // Catch:{ JSONException -> 0x025e }
            anet.channel.strategy.dispatch.DispatchEvent r3 = new anet.channel.strategy.dispatch.DispatchEvent     // Catch:{ JSONException -> 0x025e }
            r3.<init>(r9, r0)     // Catch:{ JSONException -> 0x025e }
            r1.mo9044a(r3)     // Catch:{ JSONException -> 0x025e }
            java.lang.String r0 = "request success"
            r1 = 0
            m292a((java.lang.String) r6, (java.lang.String) r0, (java.net.URL) r10, (int) r2, (int) r1)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            if (r15 == 0) goto L_0x025d
            r15.disconnect()     // Catch:{ Exception -> 0x0255 }
            goto L_0x025d
        L_0x0255:
            r0 = move-exception
            r2 = r0
            java.lang.Object[] r0 = new java.lang.Object[r1]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r4, r3, r2, r0)
        L_0x025d:
            return r1
        L_0x025e:
            anet.channel.strategy.dispatch.HttpDispatcher r0 = anet.channel.strategy.dispatch.HttpDispatcher.getInstance()     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            anet.channel.strategy.dispatch.DispatchEvent r1 = new anet.channel.strategy.dispatch.DispatchEvent     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            r3 = 0
            r6 = 0
            r1.<init>(r6, r3)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            r0.mo9044a(r1)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            java.lang.String r0 = "resolve amdc anser failed"
            java.lang.Object[] r1 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            anet.channel.util.ALog.m327e(r8, r0, r5, r1)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            java.lang.String r0 = "-1004"
            java.lang.String r1 = "resolve answer failed"
            m292a((java.lang.String) r0, (java.lang.String) r1, (java.net.URL) r10, (int) r2, (int) r9)     // Catch:{ Throwable -> 0x028a, all -> 0x0290 }
            if (r15 == 0) goto L_0x0289
            r15.disconnect()     // Catch:{ Exception -> 0x0280 }
            goto L_0x0289
        L_0x0280:
            r0 = move-exception
            r1 = r0
            r2 = 0
            java.lang.Object[] r0 = new java.lang.Object[r2]
            r2 = 0
            anet.channel.util.ALog.m326e(r8, r4, r2, r1, r0)
        L_0x0289:
            return r9
        L_0x028a:
            r0 = move-exception
            goto L_0x029b
        L_0x028c:
            r0 = move-exception
            r10 = r18
            goto L_0x029b
        L_0x0290:
            r0 = move-exception
            goto L_0x0296
        L_0x0292:
            r0 = move-exception
            goto L_0x029a
        L_0x0294:
            r0 = move-exception
            r8 = r11
        L_0x0296:
            r1 = r0
            goto L_0x02d7
        L_0x0298:
            r0 = move-exception
            r8 = r11
        L_0x029a:
            r10 = r14
        L_0x029b:
            r13 = r15
            goto L_0x02aa
        L_0x029d:
            r0 = move-exception
            r8 = r11
            r10 = r14
            goto L_0x02a9
        L_0x02a1:
            r0 = move-exception
            r8 = r11
            r1 = r0
            r15 = 0
            goto L_0x02d7
        L_0x02a6:
            r0 = move-exception
            r8 = r11
            r10 = 0
        L_0x02a9:
            r13 = 0
        L_0x02aa:
            java.lang.String r1 = r0.getMessage()     // Catch:{ all -> 0x02d4 }
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x02d4 }
            if (r3 == 0) goto L_0x02b8
            java.lang.String r1 = r0.toString()     // Catch:{ all -> 0x02d4 }
        L_0x02b8:
            java.lang.String r3 = "-1000"
            m292a((java.lang.String) r3, (java.lang.String) r1, (java.net.URL) r10, (int) r2, (int) r9)     // Catch:{ all -> 0x02d4 }
            java.lang.String r1 = "amdc request fail"
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x02d4 }
            anet.channel.util.ALog.m326e(r8, r1, r5, r0, r3)     // Catch:{ all -> 0x02d4 }
            if (r13 == 0) goto L_0x02d3
            r13.disconnect()     // Catch:{ Exception -> 0x02cb }
            goto L_0x02d3
        L_0x02cb:
            r0 = move-exception
            r1 = r0
            java.lang.Object[] r0 = new java.lang.Object[r2]
            r2 = 0
            anet.channel.util.ALog.m326e(r8, r4, r2, r1, r0)
        L_0x02d3:
            return r9
        L_0x02d4:
            r0 = move-exception
            r1 = r0
            r15 = r13
        L_0x02d7:
            if (r15 == 0) goto L_0x02e6
            r15.disconnect()     // Catch:{ Exception -> 0x02dd }
            goto L_0x02e6
        L_0x02dd:
            r0 = move-exception
            r2 = r0
            r3 = 0
            java.lang.Object[] r0 = new java.lang.Object[r3]
            r3 = 0
            anet.channel.util.ALog.m326e(r8, r4, r3, r2, r0)
        L_0x02e6:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.dispatch.C0572b.m287a(java.lang.String, java.util.Map, int):int");
    }

    /* renamed from: a */
    static String m288a(InputStream inputStream, boolean z) {
        FilterInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        if (z) {
            try {
                bufferedInputStream = new GZIPInputStream(bufferedInputStream);
            } catch (IOException e) {
                e = e;
                try {
                    ALog.m326e("awcn.DispatchCore", "", (String) null, e, new Object[0]);
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused2) {
                    }
                    throw th;
                }
            }
        }
        FilterInputStream base64InputStream = new Base64InputStream(bufferedInputStream, 0);
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = base64InputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            String str = new String(byteArrayOutputStream.toByteArray(), "utf-8");
            try {
                base64InputStream.close();
            } catch (IOException unused3) {
            }
            return str;
        } catch (IOException e2) {
            e = e2;
            bufferedInputStream = base64InputStream;
            ALog.m326e("awcn.DispatchCore", "", (String) null, e, new Object[0]);
            bufferedInputStream.close();
            return null;
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream = base64InputStream;
            bufferedInputStream.close();
            throw th;
        }
    }

    /* renamed from: a */
    static void m292a(String str, String str2, URL url, int i, int i2) {
        if ((i2 != 1 || i == 2) && GlobalAppRuntimeInfo.isTargetProcess()) {
            try {
                AmdcStatistic amdcStatistic = new AmdcStatistic();
                amdcStatistic.errorCode = str;
                amdcStatistic.errorMsg = str2;
                if (url != null) {
                    amdcStatistic.host = url.getHost();
                    amdcStatistic.url = url.toString();
                }
                amdcStatistic.retryTimes = i;
                AppMonitor.getInstance().commitStat(amdcStatistic);
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: a */
    static void m291a(String str, long j, long j2) {
        try {
            FlowStat flowStat = new FlowStat();
            flowStat.refer = "amdc";
            flowStat.protocoltype = "http";
            flowStat.req_identifier = str;
            flowStat.upstream = j;
            flowStat.downstream = j2;
            NetworkAnalysis.getInstance().commitFlow(flowStat);
        } catch (Exception e) {
            ALog.m326e("awcn.DispatchCore", "commit flow info failed!", (String) null, e, new Object[0]);
        }
    }
}
