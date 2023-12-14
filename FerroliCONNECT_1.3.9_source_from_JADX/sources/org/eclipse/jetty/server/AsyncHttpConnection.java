package org.eclipse.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.nio.AsyncConnection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class AsyncHttpConnection extends AbstractHttpConnection implements AsyncConnection {
    private static final Logger LOG = Log.getLogger((Class<?>) AsyncHttpConnection.class);
    private static final int NO_PROGRESS_CLOSE = Integer.getInteger("org.mortbay.jetty.NO_PROGRESS_CLOSE", 200).intValue();
    private static final int NO_PROGRESS_INFO = Integer.getInteger("org.mortbay.jetty.NO_PROGRESS_INFO", 100).intValue();
    private final AsyncEndPoint _asyncEndp;
    private boolean _readInterested = true;
    private int _total_no_progress;

    public AsyncHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(connector, endPoint, server);
        this._asyncEndp = (AsyncEndPoint) endPoint;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v14, resolved type: org.eclipse.jetty.util.log.Logger} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v17, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v18, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v19, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v20, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v21, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v22, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v23, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v24, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v26, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v28, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v29, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v30, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v31, resolved type: boolean} */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01d6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01d7, code lost:
        r15 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01ec, code lost:
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01f1, code lost:
        if (r12 != false) goto L_0x01f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01fb, code lost:
        if (r1._response.getStatus() == 101) goto L_0x01fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01fd, code lost:
        r6 = (org.eclipse.jetty.p119io.Connection) r1._request.getAttribute("org.eclipse.jetty.io.Connection");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0205, code lost:
        reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0218, code lost:
        LOG.warn("Safety net oshut!!!  IF YOU SEE THIS, PLEASE RAISE BUGZILLA", new java.lang.Object[0]);
        r1._endp.shutdownOutput();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0225, code lost:
        r1._readInterested = false;
        LOG.debug("Disabled read interest while writing response {}", r1._endp);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x024c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01ea A[ADDED_TO_REGION, Catch:{ all -> 0x01d6, all -> 0x024c }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01f1 A[Catch:{ all -> 0x01d6, all -> 0x024c }] */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x0305  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0316  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x0319  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x0394  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a1 A[Catch:{ all -> 0x00ff }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0113 A[Catch:{ all -> 0x01d9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0172 A[Catch:{ all -> 0x01d6, all -> 0x024c }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01c5 A[Catch:{ all -> 0x01d6, all -> 0x024c }] */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01d1 A[Catch:{ all -> 0x01d6, all -> 0x024c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.p119io.Connection handle() throws java.io.IOException {
        /*
            r17 = this;
            r1 = r17
            java.lang.String r2 = "EndPoint making no progress: "
            java.lang.String r3 = "Closing EndPoint making no progress: "
            java.lang.String r4 = "Safety net oshut!!!  IF YOU SEE THIS, PLEASE RAISE BUGZILLA"
            java.lang.String r5 = "suspended {}"
            java.lang.String r6 = "org.eclipse.jetty.io.Connection"
            java.lang.String r7 = "Disabled read interest while writing response {}"
            java.lang.String r8 = " "
            r10 = 1
            r11 = 0
            setCurrentConnection(r17)     // Catch:{ all -> 0x02f3 }
            org.eclipse.jetty.io.AsyncEndPoint r0 = r1._asyncEndp     // Catch:{ all -> 0x02f3 }
            r0.setCheckForIdle(r11)     // Catch:{ all -> 0x02f3 }
            r12 = r1
            r0 = 1
            r13 = 0
        L_0x001d:
            if (r0 == 0) goto L_0x0250
            if (r12 != r1) goto L_0x0250
            r14 = 101(0x65, float:1.42E-43)
            org.eclipse.jetty.server.Request r0 = r1._request     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            org.eclipse.jetty.server.AsyncContinuation r0 = r0._async     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            boolean r0 = r0.isAsync()     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            if (r0 == 0) goto L_0x003b
            org.eclipse.jetty.server.Request r0 = r1._request     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            org.eclipse.jetty.server.AsyncContinuation r0 = r0._async     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            boolean r0 = r0.isDispatchable()     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            if (r0 == 0) goto L_0x004d
            r17.handleRequest()     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            goto L_0x004d
        L_0x003b:
            org.eclipse.jetty.http.Parser r0 = r1._parser     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            boolean r0 = r0.isComplete()     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            if (r0 != 0) goto L_0x004d
            org.eclipse.jetty.http.Parser r0 = r1._parser     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            boolean r0 = r0.parseAvailable()     // Catch:{ HttpException -> 0x0109, all -> 0x0105 }
            if (r0 == 0) goto L_0x004d
            r15 = 1
            goto L_0x004e
        L_0x004d:
            r15 = 0
        L_0x004e:
            org.eclipse.jetty.http.Generator r0 = r1._generator     // Catch:{ HttpException -> 0x0103 }
            boolean r0 = r0.isCommitted()     // Catch:{ HttpException -> 0x0103 }
            if (r0 == 0) goto L_0x007b
            org.eclipse.jetty.http.Generator r0 = r1._generator     // Catch:{ HttpException -> 0x0103 }
            boolean r0 = r0.isComplete()     // Catch:{ HttpException -> 0x0103 }
            if (r0 != 0) goto L_0x007b
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ HttpException -> 0x0103 }
            boolean r0 = r0.isOutputShutdown()     // Catch:{ HttpException -> 0x0103 }
            if (r0 != 0) goto L_0x007b
            org.eclipse.jetty.server.Request r0 = r1._request     // Catch:{ HttpException -> 0x0103 }
            org.eclipse.jetty.server.AsyncContinuation r0 = r0.getAsyncContinuation()     // Catch:{ HttpException -> 0x0103 }
            boolean r0 = r0.isAsyncStarted()     // Catch:{ HttpException -> 0x0103 }
            if (r0 != 0) goto L_0x007b
            org.eclipse.jetty.http.Generator r0 = r1._generator     // Catch:{ HttpException -> 0x0103 }
            int r0 = r0.flushBuffer()     // Catch:{ HttpException -> 0x0103 }
            if (r0 <= 0) goto L_0x007b
            r15 = 1
        L_0x007b:
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ HttpException -> 0x0103 }
            r0.flush()     // Catch:{ HttpException -> 0x0103 }
            org.eclipse.jetty.io.AsyncEndPoint r0 = r1._asyncEndp     // Catch:{ HttpException -> 0x0103 }
            boolean r0 = r0.hasProgressed()     // Catch:{ HttpException -> 0x0103 }
            if (r0 == 0) goto L_0x0089
            r15 = 1
        L_0x0089:
            r13 = r13 | r15
            org.eclipse.jetty.http.Parser r0 = r1._parser     // Catch:{ all -> 0x00ff }
            boolean r0 = r0.isComplete()     // Catch:{ all -> 0x00ff }
            org.eclipse.jetty.http.Generator r9 = r1._generator     // Catch:{ all -> 0x00ff }
            boolean r9 = r9.isComplete()     // Catch:{ all -> 0x00ff }
            if (r0 == 0) goto L_0x009d
            if (r9 == 0) goto L_0x009d
            r16 = 1
            goto L_0x009f
        L_0x009d:
            r16 = 0
        L_0x009f:
            if (r0 == 0) goto L_0x00e4
            if (r9 == 0) goto L_0x00d7
            org.eclipse.jetty.server.Response r0 = r1._response     // Catch:{ all -> 0x00ff }
            int r0 = r0.getStatus()     // Catch:{ all -> 0x00ff }
            if (r0 != r14) goto L_0x00b6
            org.eclipse.jetty.server.Request r0 = r1._request     // Catch:{ all -> 0x00ff }
            java.lang.Object r0 = r0.getAttribute(r6)     // Catch:{ all -> 0x00ff }
            org.eclipse.jetty.io.Connection r0 = (org.eclipse.jetty.p119io.Connection) r0     // Catch:{ all -> 0x00ff }
            if (r0 == 0) goto L_0x00b6
            r12 = r0
        L_0x00b6:
            r17.reset()     // Catch:{ all -> 0x00ff }
            org.eclipse.jetty.http.Generator r0 = r1._generator     // Catch:{ all -> 0x00ff }
            boolean r0 = r0.isPersistent()     // Catch:{ all -> 0x00ff }
            if (r0 != 0) goto L_0x00d5
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ all -> 0x00ff }
            boolean r0 = r0.isOutputShutdown()     // Catch:{ all -> 0x00ff }
            if (r0 != 0) goto L_0x00d5
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x00ff }
            java.lang.Object[] r9 = new java.lang.Object[r11]     // Catch:{ all -> 0x00ff }
            r0.warn((java.lang.String) r4, (java.lang.Object[]) r9)     // Catch:{ all -> 0x00ff }
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ all -> 0x00ff }
            r0.shutdownOutput()     // Catch:{ all -> 0x00ff }
        L_0x00d5:
            r15 = 1
            goto L_0x00e4
        L_0x00d7:
            r1._readInterested = r11     // Catch:{ all -> 0x00ff }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x00ff }
            java.lang.Object[] r9 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ff }
            org.eclipse.jetty.io.EndPoint r14 = r1._endp     // Catch:{ all -> 0x00ff }
            r9[r11] = r14     // Catch:{ all -> 0x00ff }
            r0.debug((java.lang.String) r7, (java.lang.Object[]) r9)     // Catch:{ all -> 0x00ff }
        L_0x00e4:
            if (r16 != 0) goto L_0x00fc
            org.eclipse.jetty.server.Request r0 = r1._request     // Catch:{ all -> 0x00ff }
            org.eclipse.jetty.server.AsyncContinuation r0 = r0.getAsyncContinuation()     // Catch:{ all -> 0x00ff }
            boolean r0 = r0.isAsyncStarted()     // Catch:{ all -> 0x00ff }
            if (r0 == 0) goto L_0x00fc
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x00ff }
            java.lang.Object[] r9 = new java.lang.Object[r10]     // Catch:{ all -> 0x00ff }
            r9[r11] = r1     // Catch:{ all -> 0x00ff }
            r0.debug((java.lang.String) r5, (java.lang.Object[]) r9)     // Catch:{ all -> 0x00ff }
            r15 = 0
        L_0x00fc:
            r0 = r15
            goto L_0x01d3
        L_0x00ff:
            r0 = move-exception
            r9 = r13
            goto L_0x024d
        L_0x0103:
            r0 = move-exception
            goto L_0x010b
        L_0x0105:
            r0 = move-exception
            r15 = 0
            goto L_0x01da
        L_0x0109:
            r0 = move-exception
            r15 = 0
        L_0x010b:
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x01d9 }
            boolean r9 = r9.isDebugEnabled()     // Catch:{ all -> 0x01d9 }
            if (r9 == 0) goto L_0x014c
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x01d9 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d9 }
            r14.<init>()     // Catch:{ all -> 0x01d9 }
            java.lang.String r10 = "uri="
            r14.append(r10)     // Catch:{ all -> 0x01d9 }
            org.eclipse.jetty.http.HttpURI r10 = r1._uri     // Catch:{ all -> 0x01d9 }
            r14.append(r10)     // Catch:{ all -> 0x01d9 }
            java.lang.String r10 = r14.toString()     // Catch:{ all -> 0x01d9 }
            java.lang.Object[] r14 = new java.lang.Object[r11]     // Catch:{ all -> 0x01d9 }
            r9.debug((java.lang.String) r10, (java.lang.Object[]) r14)     // Catch:{ all -> 0x01d9 }
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x01d9 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d9 }
            r10.<init>()     // Catch:{ all -> 0x01d9 }
            java.lang.String r14 = "fields="
            r10.append(r14)     // Catch:{ all -> 0x01d9 }
            org.eclipse.jetty.http.HttpFields r14 = r1._requestFields     // Catch:{ all -> 0x01d9 }
            r10.append(r14)     // Catch:{ all -> 0x01d9 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x01d9 }
            java.lang.Object[] r14 = new java.lang.Object[r11]     // Catch:{ all -> 0x01d9 }
            r9.debug((java.lang.String) r10, (java.lang.Object[]) r14)     // Catch:{ all -> 0x01d9 }
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x01d9 }
            r9.debug(r0)     // Catch:{ all -> 0x01d9 }
        L_0x014c:
            org.eclipse.jetty.http.Generator r9 = r1._generator     // Catch:{ all -> 0x01d6 }
            int r10 = r0.getStatus()     // Catch:{ all -> 0x01d6 }
            java.lang.String r0 = r0.getReason()     // Catch:{ all -> 0x01d6 }
            r14 = 0
            r15 = 1
            r9.sendError(r10, r0, r14, r15)     // Catch:{ all -> 0x01d6 }
            r9 = r13 | 1
            org.eclipse.jetty.http.Parser r0 = r1._parser     // Catch:{ all -> 0x024c }
            boolean r0 = r0.isComplete()     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.http.Generator r10 = r1._generator     // Catch:{ all -> 0x024c }
            boolean r10 = r10.isComplete()     // Catch:{ all -> 0x024c }
            if (r0 == 0) goto L_0x016f
            if (r10 == 0) goto L_0x016f
            r13 = 1
            goto L_0x0170
        L_0x016f:
            r13 = 0
        L_0x0170:
            if (r0 == 0) goto L_0x01b7
            if (r10 == 0) goto L_0x01a9
            org.eclipse.jetty.server.Response r0 = r1._response     // Catch:{ all -> 0x024c }
            int r0 = r0.getStatus()     // Catch:{ all -> 0x024c }
            r10 = 101(0x65, float:1.42E-43)
            if (r0 != r10) goto L_0x0189
            org.eclipse.jetty.server.Request r0 = r1._request     // Catch:{ all -> 0x024c }
            java.lang.Object r0 = r0.getAttribute(r6)     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.io.Connection r0 = (org.eclipse.jetty.p119io.Connection) r0     // Catch:{ all -> 0x024c }
            if (r0 == 0) goto L_0x0189
            r12 = r0
        L_0x0189:
            r17.reset()     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.http.Generator r0 = r1._generator     // Catch:{ all -> 0x024c }
            boolean r0 = r0.isPersistent()     // Catch:{ all -> 0x024c }
            if (r0 != 0) goto L_0x01b7
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ all -> 0x024c }
            boolean r0 = r0.isOutputShutdown()     // Catch:{ all -> 0x024c }
            if (r0 != 0) goto L_0x01b7
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x024c }
            java.lang.Object[] r10 = new java.lang.Object[r11]     // Catch:{ all -> 0x024c }
            r0.warn((java.lang.String) r4, (java.lang.Object[]) r10)     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ all -> 0x024c }
            r0.shutdownOutput()     // Catch:{ all -> 0x024c }
            goto L_0x01b7
        L_0x01a9:
            r1._readInterested = r11     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x024c }
            r10 = 1
            java.lang.Object[] r14 = new java.lang.Object[r10]     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.io.EndPoint r10 = r1._endp     // Catch:{ all -> 0x024c }
            r14[r11] = r10     // Catch:{ all -> 0x024c }
            r0.debug((java.lang.String) r7, (java.lang.Object[]) r14)     // Catch:{ all -> 0x024c }
        L_0x01b7:
            if (r13 != 0) goto L_0x01d1
            org.eclipse.jetty.server.Request r0 = r1._request     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.server.AsyncContinuation r0 = r0.getAsyncContinuation()     // Catch:{ all -> 0x024c }
            boolean r0 = r0.isAsyncStarted()     // Catch:{ all -> 0x024c }
            if (r0 == 0) goto L_0x01d1
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x024c }
            r10 = 1
            java.lang.Object[] r13 = new java.lang.Object[r10]     // Catch:{ all -> 0x024c }
            r13[r11] = r1     // Catch:{ all -> 0x024c }
            r0.debug((java.lang.String) r5, (java.lang.Object[]) r13)     // Catch:{ all -> 0x024c }
            r0 = 0
            goto L_0x01d2
        L_0x01d1:
            r0 = 1
        L_0x01d2:
            r13 = r9
        L_0x01d3:
            r10 = 1
            goto L_0x001d
        L_0x01d6:
            r0 = move-exception
            r15 = 1
            goto L_0x01da
        L_0x01d9:
            r0 = move-exception
        L_0x01da:
            r9 = r13 | r15
            org.eclipse.jetty.http.Parser r10 = r1._parser     // Catch:{ all -> 0x024c }
            boolean r10 = r10.isComplete()     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.http.Generator r12 = r1._generator     // Catch:{ all -> 0x024c }
            boolean r12 = r12.isComplete()     // Catch:{ all -> 0x024c }
            if (r10 == 0) goto L_0x01ee
            if (r12 == 0) goto L_0x01ee
            r13 = 1
            goto L_0x01ef
        L_0x01ee:
            r13 = 0
        L_0x01ef:
            if (r10 == 0) goto L_0x0233
            if (r12 == 0) goto L_0x0225
            org.eclipse.jetty.server.Response r7 = r1._response     // Catch:{ all -> 0x024c }
            int r7 = r7.getStatus()     // Catch:{ all -> 0x024c }
            r10 = 101(0x65, float:1.42E-43)
            if (r7 != r10) goto L_0x0205
            org.eclipse.jetty.server.Request r7 = r1._request     // Catch:{ all -> 0x024c }
            java.lang.Object r6 = r7.getAttribute(r6)     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.io.Connection r6 = (org.eclipse.jetty.p119io.Connection) r6     // Catch:{ all -> 0x024c }
        L_0x0205:
            r17.reset()     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.http.Generator r6 = r1._generator     // Catch:{ all -> 0x024c }
            boolean r6 = r6.isPersistent()     // Catch:{ all -> 0x024c }
            if (r6 != 0) goto L_0x0233
            org.eclipse.jetty.io.EndPoint r6 = r1._endp     // Catch:{ all -> 0x024c }
            boolean r6 = r6.isOutputShutdown()     // Catch:{ all -> 0x024c }
            if (r6 != 0) goto L_0x0233
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x024c }
            java.lang.Object[] r7 = new java.lang.Object[r11]     // Catch:{ all -> 0x024c }
            r6.warn((java.lang.String) r4, (java.lang.Object[]) r7)     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.io.EndPoint r4 = r1._endp     // Catch:{ all -> 0x024c }
            r4.shutdownOutput()     // Catch:{ all -> 0x024c }
            goto L_0x0233
        L_0x0225:
            r1._readInterested = r11     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x024c }
            r6 = 1
            java.lang.Object[] r10 = new java.lang.Object[r6]     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.io.EndPoint r6 = r1._endp     // Catch:{ all -> 0x024c }
            r10[r11] = r6     // Catch:{ all -> 0x024c }
            r4.debug((java.lang.String) r7, (java.lang.Object[]) r10)     // Catch:{ all -> 0x024c }
        L_0x0233:
            if (r13 != 0) goto L_0x024b
            org.eclipse.jetty.server.Request r4 = r1._request     // Catch:{ all -> 0x024c }
            org.eclipse.jetty.server.AsyncContinuation r4 = r4.getAsyncContinuation()     // Catch:{ all -> 0x024c }
            boolean r4 = r4.isAsyncStarted()     // Catch:{ all -> 0x024c }
            if (r4 == 0) goto L_0x024b
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x024c }
            r6 = 1
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ all -> 0x024c }
            r7[r11] = r1     // Catch:{ all -> 0x024c }
            r4.debug((java.lang.String) r5, (java.lang.Object[]) r7)     // Catch:{ all -> 0x024c }
        L_0x024b:
            throw r0     // Catch:{ all -> 0x024c }
        L_0x024c:
            r0 = move-exception
        L_0x024d:
            r4 = 0
            goto L_0x02f6
        L_0x0250:
            r4 = 0
            setCurrentConnection(r4)
            org.eclipse.jetty.server.Request r0 = r1._request
            org.eclipse.jetty.server.AsyncContinuation r0 = r0.getAsyncContinuation()
            boolean r0 = r0.isAsyncStarted()
            if (r0 != 0) goto L_0x0271
            org.eclipse.jetty.http.Parser r0 = r1._parser
            r0.returnBuffers()
            org.eclipse.jetty.http.Generator r0 = r1._generator
            r0.returnBuffers()
            org.eclipse.jetty.io.AsyncEndPoint r0 = r1._asyncEndp
            r4 = 1
            r0.setCheckForIdle(r4)
            goto L_0x0272
        L_0x0271:
            r4 = 1
        L_0x0272:
            if (r13 == 0) goto L_0x0278
            r1._total_no_progress = r11
            goto L_0x02f2
        L_0x0278:
            int r0 = r1._total_no_progress
            int r0 = r0 + r4
            r1._total_no_progress = r0
            int r0 = NO_PROGRESS_INFO
            if (r0 <= 0) goto L_0x02b3
            int r4 = r1._total_no_progress
            int r0 = r4 % r0
            if (r0 != 0) goto L_0x02b3
            int r0 = NO_PROGRESS_CLOSE
            if (r0 <= 0) goto L_0x028d
            if (r4 >= r0) goto L_0x02b3
        L_0x028d:
            org.eclipse.jetty.util.log.Logger r0 = LOG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r2)
            int r2 = r1._total_no_progress
            r4.append(r2)
            r4.append(r8)
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            r4.append(r2)
            r4.append(r8)
            r4.append(r1)
            java.lang.String r2 = r4.toString()
            java.lang.Object[] r4 = new java.lang.Object[r11]
            r0.info((java.lang.String) r2, (java.lang.Object[]) r4)
        L_0x02b3:
            int r0 = NO_PROGRESS_CLOSE
            if (r0 <= 0) goto L_0x02f2
            int r2 = r1._total_no_progress
            if (r2 != r0) goto L_0x02f2
            org.eclipse.jetty.util.log.Logger r0 = LOG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            int r3 = r1._total_no_progress
            r2.append(r3)
            r2.append(r8)
            org.eclipse.jetty.io.EndPoint r3 = r1._endp
            r2.append(r3)
            r2.append(r8)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r11]
            r0.warn((java.lang.String) r2, (java.lang.Object[]) r3)
            org.eclipse.jetty.io.EndPoint r0 = r1._endp
            boolean r0 = r0 instanceof org.eclipse.jetty.p119io.nio.SelectChannelEndPoint
            if (r0 == 0) goto L_0x02f2
            org.eclipse.jetty.io.EndPoint r0 = r1._endp
            org.eclipse.jetty.io.nio.SelectChannelEndPoint r0 = (org.eclipse.jetty.p119io.nio.SelectChannelEndPoint) r0
            java.nio.channels.ByteChannel r0 = r0.getChannel()
            r0.close()
        L_0x02f2:
            return r12
        L_0x02f3:
            r0 = move-exception
            r4 = 0
            r9 = 0
        L_0x02f6:
            setCurrentConnection(r4)
            org.eclipse.jetty.server.Request r4 = r1._request
            org.eclipse.jetty.server.AsyncContinuation r4 = r4.getAsyncContinuation()
            boolean r4 = r4.isAsyncStarted()
            if (r4 != 0) goto L_0x0316
            org.eclipse.jetty.http.Parser r4 = r1._parser
            r4.returnBuffers()
            org.eclipse.jetty.http.Generator r4 = r1._generator
            r4.returnBuffers()
            org.eclipse.jetty.io.AsyncEndPoint r4 = r1._asyncEndp
            r5 = 1
            r4.setCheckForIdle(r5)
            goto L_0x0317
        L_0x0316:
            r5 = 1
        L_0x0317:
            if (r9 != 0) goto L_0x0394
            int r4 = r1._total_no_progress
            int r4 = r4 + r5
            r1._total_no_progress = r4
            int r4 = NO_PROGRESS_INFO
            if (r4 <= 0) goto L_0x0354
            int r5 = r1._total_no_progress
            int r4 = r5 % r4
            if (r4 != 0) goto L_0x0354
            int r4 = NO_PROGRESS_CLOSE
            if (r4 <= 0) goto L_0x032e
            if (r5 >= r4) goto L_0x0354
        L_0x032e:
            org.eclipse.jetty.util.log.Logger r4 = LOG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r2)
            int r2 = r1._total_no_progress
            r5.append(r2)
            r5.append(r8)
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            r5.append(r2)
            r5.append(r8)
            r5.append(r1)
            java.lang.String r2 = r5.toString()
            java.lang.Object[] r5 = new java.lang.Object[r11]
            r4.info((java.lang.String) r2, (java.lang.Object[]) r5)
        L_0x0354:
            int r2 = NO_PROGRESS_CLOSE
            if (r2 <= 0) goto L_0x0396
            int r4 = r1._total_no_progress
            if (r4 != r2) goto L_0x0396
            org.eclipse.jetty.util.log.Logger r2 = LOG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            int r3 = r1._total_no_progress
            r4.append(r3)
            r4.append(r8)
            org.eclipse.jetty.io.EndPoint r3 = r1._endp
            r4.append(r3)
            r4.append(r8)
            r4.append(r1)
            java.lang.String r3 = r4.toString()
            java.lang.Object[] r4 = new java.lang.Object[r11]
            r2.warn((java.lang.String) r3, (java.lang.Object[]) r4)
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            boolean r2 = r2 instanceof org.eclipse.jetty.p119io.nio.SelectChannelEndPoint
            if (r2 == 0) goto L_0x0396
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            org.eclipse.jetty.io.nio.SelectChannelEndPoint r2 = (org.eclipse.jetty.p119io.nio.SelectChannelEndPoint) r2
            java.nio.channels.ByteChannel r2 = r2.getChannel()
            r2.close()
            goto L_0x0396
        L_0x0394:
            r1._total_no_progress = r11
        L_0x0396:
            goto L_0x0398
        L_0x0397:
            throw r0
        L_0x0398:
            goto L_0x0397
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncHttpConnection.handle():org.eclipse.jetty.io.Connection");
    }

    public void onInputShutdown() throws IOException {
        if (this._generator.isIdle() && !this._request.getAsyncContinuation().isSuspended()) {
            this._endp.close();
        }
        if (this._parser.isIdle()) {
            this._parser.setPersistent(false);
        }
    }

    public void reset() {
        this._readInterested = true;
        LOG.debug("Enabled read interest {}", this._endp);
        super.reset();
    }

    public boolean isSuspended() {
        return !this._readInterested || super.isSuspended();
    }
}
