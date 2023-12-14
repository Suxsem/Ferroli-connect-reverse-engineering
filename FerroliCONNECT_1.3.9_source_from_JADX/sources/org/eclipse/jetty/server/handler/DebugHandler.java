package org.eclipse.jetty.server.handler;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.RolloverFileOutputStream;

public class DebugHandler extends HandlerWrapper {
    private DateCache _date = new DateCache("HH:mm:ss", Locale.US);
    private OutputStream _out;
    private PrintStream _print;

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x027d, code lost:
        if (r11 > 9) goto L_0x027f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x027f, code lost:
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0282, code lost:
        r5 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0284, code lost:
        r3.append(r5);
        r3.append(r11);
        r3.append(":");
        r3.append(r6);
        r3.append(" ");
        r3.append(r10.getStatus());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x029a, code lost:
        if (r7 != null) goto L_0x029d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x029d, code lost:
        r4 = "/" + r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x02ae, code lost:
        r3.append(r4);
        r3.append(" ");
        r3.append(r10.getContentType());
        r3.append(" ");
        r3.append(r10.getContentCount());
        r2.println(r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01cb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01cc, code lost:
        r4 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01cf, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01d0, code lost:
        r4 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01d3, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01d4, code lost:
        r4 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01d7, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01d8, code lost:
        r4 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01db, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01dc, code lost:
        r4 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0236, code lost:
        r3.setAttribute("org.eclipse.jetty.thread.name", r6);
        r2 = r1._print;
        r3 = new java.lang.StringBuilder();
        r3.append(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0245, code lost:
        if (r11 <= 99) goto L_0x0247;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0249, code lost:
        if (r11 > 9) goto L_0x024b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x024b, code lost:
        r4 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x024e, code lost:
        r4 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0251, code lost:
        r4 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0253, code lost:
        r3.append(r4);
        r3.append(r11);
        r3.append(":");
        r3.append(r6);
        r3.append(" SUSPEND");
        r2.println(r3.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x026a, code lost:
        r2 = r1._print;
        r3 = new java.lang.StringBuilder();
        r3.append(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0276, code lost:
        if (r11 > 99) goto L_0x0278;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0278, code lost:
        r5 = r19;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:19:0x0093, B:28:0x00c4] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01cf A[ExcHandler: Error (e java.lang.Error), PHI: r19 r20 
      PHI: (r19v9 java.lang.String) = (r19v11 java.lang.String), (r19v12 java.lang.String), (r19v12 java.lang.String) binds: [B:28:0x00c4, B:19:0x0093, B:20:?] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r20v3 java.lang.String) = (r20v5 java.lang.String), (r20v6 java.lang.String), (r20v6 java.lang.String) binds: [B:28:0x00c4, B:19:0x0093, B:20:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01d3 A[ExcHandler: RuntimeException (e java.lang.RuntimeException), PHI: r20 
      PHI: (r20v2 java.lang.String) = (r20v5 java.lang.String), (r20v6 java.lang.String), (r20v6 java.lang.String) binds: [B:28:0x00c4, B:19:0x0093, B:20:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01d7 A[ExcHandler: ServletException (e javax.servlet.ServletException), PHI: r20 
      PHI: (r20v1 java.lang.String) = (r20v5 java.lang.String), (r20v6 java.lang.String), (r20v6 java.lang.String) binds: [B:28:0x00c4, B:19:0x0093, B:20:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01db A[ExcHandler: IOException (e java.io.IOException), PHI: r20 
      PHI: (r20v0 java.lang.String) = (r20v5 java.lang.String), (r20v6 java.lang.String), (r20v6 java.lang.String) binds: [B:28:0x00c4, B:19:0x0093, B:20:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0236  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x026a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle(java.lang.String r22, org.eclipse.jetty.server.Request r23, javax.servlet.http.HttpServletRequest r24, javax.servlet.http.HttpServletResponse r25) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r21 = this;
            r1 = r21
            r2 = r23
            r3 = r24
            java.lang.String r4 = ""
            java.lang.String r5 = " SUSPEND"
            java.lang.String r6 = ".0"
            java.lang.String r7 = ".00"
            java.lang.String r8 = "."
            java.lang.String r9 = " "
            org.eclipse.jetty.server.Response r10 = r23.getResponse()
            java.lang.Thread r11 = java.lang.Thread.currentThread()
            java.lang.String r12 = r11.getName()
            java.lang.String r13 = "org.eclipse.jetty.thread.name"
            java.lang.Object r0 = r3.getAttribute(r13)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r14 = ":"
            if (r0 != 0) goto L_0x005f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r12)
            r0.append(r14)
            java.lang.String r15 = r23.getScheme()
            r0.append(r15)
            java.lang.String r15 = "://"
            r0.append(r15)
            java.lang.String r15 = r23.getLocalAddr()
            r0.append(r15)
            r0.append(r14)
            int r15 = r23.getLocalPort()
            r0.append(r15)
            org.eclipse.jetty.http.HttpURI r15 = r23.getUri()
            r0.append(r15)
            java.lang.String r0 = r0.toString()
            r15 = 0
            goto L_0x0060
        L_0x005f:
            r15 = 1
        L_0x0060:
            r16 = r6
            r6 = r0
            r17 = 0
            r18 = r7
            org.eclipse.jetty.util.DateCache r0 = r1._date     // Catch:{ IOException -> 0x0213, ServletException -> 0x01f5, RuntimeException -> 0x01ed, Error -> 0x01e5, all -> 0x01df }
            java.lang.String r0 = r0.now()     // Catch:{ IOException -> 0x0213, ServletException -> 0x01f5, RuntimeException -> 0x01ed, Error -> 0x01e5, all -> 0x01df }
            org.eclipse.jetty.util.DateCache r7 = r1._date     // Catch:{ IOException -> 0x0213, ServletException -> 0x01f5, RuntimeException -> 0x01ed, Error -> 0x01e5, all -> 0x01df }
            int r7 = r7.lastMs()     // Catch:{ IOException -> 0x0213, ServletException -> 0x01f5, RuntimeException -> 0x01ed, Error -> 0x01e5, all -> 0x01df }
            if (r15 == 0) goto L_0x00c0
            java.io.PrintStream r15 = r1._print     // Catch:{ IOException -> 0x0213, ServletException -> 0x01f5, RuntimeException -> 0x01ed, Error -> 0x01e5, all -> 0x01df }
            r19 = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00bd, ServletException -> 0x00ba, RuntimeException -> 0x00b7, Error -> 0x00b4 }
            r8.<init>()     // Catch:{ IOException -> 0x00bd, ServletException -> 0x00ba, RuntimeException -> 0x00b7, Error -> 0x00b4 }
            r8.append(r0)     // Catch:{ IOException -> 0x00bd, ServletException -> 0x00ba, RuntimeException -> 0x00b7, Error -> 0x00b4 }
            r20 = r4
            r4 = 99
            if (r7 <= r4) goto L_0x008a
            r0 = r19
            goto L_0x0093
        L_0x008a:
            r4 = 9
            if (r7 <= r4) goto L_0x0091
            r0 = r16
            goto L_0x0093
        L_0x0091:
            r0 = r18
        L_0x0093:
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x00ad }
            r8.append(r7)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x00ad }
            r8.append(r14)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x00ad }
            r8.append(r6)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x00ad }
            java.lang.String r0 = " RETRY"
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x00ad }
            java.lang.String r0 = r8.toString()     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x00ad }
            r15.println(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x00ad }
            goto L_0x011f
        L_0x00ad:
            r0 = move-exception
            r7 = r17
            r4 = r20
            goto L_0x021d
        L_0x00b4:
            r0 = move-exception
            goto L_0x01e8
        L_0x00b7:
            r0 = move-exception
            goto L_0x01f0
        L_0x00ba:
            r0 = move-exception
            goto L_0x01f8
        L_0x00bd:
            r0 = move-exception
            goto L_0x0216
        L_0x00c0:
            r20 = r4
            r19 = r8
            java.io.PrintStream r4 = r1._print     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.<init>()     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r15 = 99
            if (r7 <= r15) goto L_0x00d5
            r0 = r19
            goto L_0x00de
        L_0x00d5:
            r15 = 9
            if (r7 <= r15) goto L_0x00dc
            r0 = r16
            goto L_0x00de
        L_0x00dc:
            r0 = r18
        L_0x00de:
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r7)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r14)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r6)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r9)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            java.lang.String r0 = r23.getRemoteAddr()     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r9)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            java.lang.String r0 = r24.getMethod()     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r9)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            java.lang.String r0 = "Cookie"
            java.lang.String r0 = r2.getHeader(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            java.lang.String r0 = "; "
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            java.lang.String r0 = "User-Agent"
            java.lang.String r0 = r2.getHeader(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r8.append(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            java.lang.String r0 = r8.toString()     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r4.println(r0)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
        L_0x011f:
            r11.setName(r6)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            org.eclipse.jetty.server.Handler r0 = r21.getHandler()     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r4 = r22
            r7 = r25
            r0.handle(r4, r2, r3, r7)     // Catch:{ IOException -> 0x01db, ServletException -> 0x01d7, RuntimeException -> 0x01d3, Error -> 0x01cf, all -> 0x01cb }
            r11.setName(r12)
            org.eclipse.jetty.util.DateCache r0 = r1._date
            java.lang.String r0 = r0.now()
            org.eclipse.jetty.util.DateCache r4 = r1._date
            int r4 = r4.lastMs()
            org.eclipse.jetty.server.AsyncContinuation r2 = r23.getAsyncContinuation()
            boolean r2 = r2.isSuspended()
            if (r2 == 0) goto L_0x017a
            r3.setAttribute(r13, r6)
            java.io.PrintStream r2 = r1._print
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            r7 = 99
            if (r4 <= r7) goto L_0x015a
            r0 = r19
            goto L_0x0163
        L_0x015a:
            r7 = 9
            if (r4 <= r7) goto L_0x0161
            r0 = r16
            goto L_0x0163
        L_0x0161:
            r0 = r18
        L_0x0163:
            r3.append(r0)
            r3.append(r4)
            r3.append(r14)
            r3.append(r6)
            r3.append(r5)
            java.lang.String r0 = r3.toString()
            r2.println(r0)
            goto L_0x01ca
        L_0x017a:
            java.io.PrintStream r2 = r1._print
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            r5 = 99
            if (r4 <= r5) goto L_0x018b
            r0 = r19
            goto L_0x0194
        L_0x018b:
            r5 = 9
            if (r4 <= r5) goto L_0x0192
            r0 = r16
            goto L_0x0194
        L_0x0192:
            r0 = r18
        L_0x0194:
            r3.append(r0)
            r3.append(r4)
            r3.append(r14)
            r3.append(r6)
            r3.append(r9)
            int r0 = r10.getStatus()
            r3.append(r0)
            r4 = r20
            r3.append(r4)
            r3.append(r9)
            java.lang.String r0 = r10.getContentType()
            r3.append(r0)
            r3.append(r9)
            long r4 = r10.getContentCount()
            r3.append(r4)
            java.lang.String r0 = r3.toString()
            r2.println(r0)
        L_0x01ca:
            return
        L_0x01cb:
            r0 = move-exception
            r4 = r20
            goto L_0x01e2
        L_0x01cf:
            r0 = move-exception
            r4 = r20
            goto L_0x01e8
        L_0x01d3:
            r0 = move-exception
            r4 = r20
            goto L_0x01f0
        L_0x01d7:
            r0 = move-exception
            r4 = r20
            goto L_0x01f8
        L_0x01db:
            r0 = move-exception
            r4 = r20
            goto L_0x0216
        L_0x01df:
            r0 = move-exception
            r19 = r8
        L_0x01e2:
            r7 = r17
            goto L_0x021d
        L_0x01e5:
            r0 = move-exception
            r19 = r8
        L_0x01e8:
            java.lang.String r17 = r0.toString()     // Catch:{ all -> 0x021b }
            throw r0     // Catch:{ all -> 0x021b }
        L_0x01ed:
            r0 = move-exception
            r19 = r8
        L_0x01f0:
            java.lang.String r17 = r0.toString()     // Catch:{ all -> 0x021b }
            throw r0     // Catch:{ all -> 0x021b }
        L_0x01f5:
            r0 = move-exception
            r19 = r8
        L_0x01f8:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x021b }
            r7.<init>()     // Catch:{ all -> 0x021b }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x021b }
            r7.append(r8)     // Catch:{ all -> 0x021b }
            r7.append(r14)     // Catch:{ all -> 0x021b }
            java.lang.Throwable r8 = r0.getCause()     // Catch:{ all -> 0x021b }
            r7.append(r8)     // Catch:{ all -> 0x021b }
            java.lang.String r17 = r7.toString()     // Catch:{ all -> 0x021b }
            throw r0     // Catch:{ all -> 0x021b }
        L_0x0213:
            r0 = move-exception
            r19 = r8
        L_0x0216:
            java.lang.String r17 = r0.toString()     // Catch:{ all -> 0x021b }
            throw r0     // Catch:{ all -> 0x021b }
        L_0x021b:
            r0 = move-exception
            goto L_0x01e2
        L_0x021d:
            r11.setName(r12)
            org.eclipse.jetty.util.DateCache r8 = r1._date
            java.lang.String r8 = r8.now()
            org.eclipse.jetty.util.DateCache r11 = r1._date
            int r11 = r11.lastMs()
            org.eclipse.jetty.server.AsyncContinuation r2 = r23.getAsyncContinuation()
            boolean r2 = r2.isSuspended()
            if (r2 == 0) goto L_0x026a
            r3.setAttribute(r13, r6)
            java.io.PrintStream r2 = r1._print
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r8)
            r4 = 99
            if (r11 > r4) goto L_0x0251
            r4 = 9
            if (r11 <= r4) goto L_0x024e
            r4 = r16
            goto L_0x0253
        L_0x024e:
            r4 = r18
            goto L_0x0253
        L_0x0251:
            r4 = r19
        L_0x0253:
            r3.append(r4)
            r3.append(r11)
            r3.append(r14)
            r3.append(r6)
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            r2.println(r3)
            goto L_0x02cc
        L_0x026a:
            java.io.PrintStream r2 = r1._print
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r8)
            r5 = 99
            if (r11 <= r5) goto L_0x027b
            r5 = r19
            goto L_0x0284
        L_0x027b:
            r5 = 9
            if (r11 <= r5) goto L_0x0282
            r5 = r16
            goto L_0x0284
        L_0x0282:
            r5 = r18
        L_0x0284:
            r3.append(r5)
            r3.append(r11)
            r3.append(r14)
            r3.append(r6)
            r3.append(r9)
            int r5 = r10.getStatus()
            r3.append(r5)
            if (r7 != 0) goto L_0x029d
            goto L_0x02ae
        L_0x029d:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "/"
            r4.append(r5)
            r4.append(r7)
            java.lang.String r4 = r4.toString()
        L_0x02ae:
            r3.append(r4)
            r3.append(r9)
            java.lang.String r4 = r10.getContentType()
            r3.append(r4)
            r3.append(r9)
            long r4 = r10.getContentCount()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.println(r3)
        L_0x02cc:
            goto L_0x02ce
        L_0x02cd:
            throw r0
        L_0x02ce:
            goto L_0x02cd
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.DebugHandler.handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        if (this._out == null) {
            this._out = new RolloverFileOutputStream("./logs/yyyy_mm_dd.debug.log", true);
        }
        this._print = new PrintStream(this._out);
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        this._print.close();
    }

    public OutputStream getOutputStream() {
        return this._out;
    }

    public void setOutputStream(OutputStream outputStream) {
        this._out = outputStream;
    }
}
