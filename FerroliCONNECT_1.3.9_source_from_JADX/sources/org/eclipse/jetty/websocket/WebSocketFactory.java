package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class WebSocketFactory extends AbstractLifeCycle {
    private static final Logger LOG = Log.getLogger((Class<?>) WebSocketFactory.class);
    private final Acceptor _acceptor;
    private WebSocketBuffers _buffers;
    private final Map<String, Class<? extends Extension>> _extensionClasses;
    private int _maxBinaryMessageSize;
    private int _maxIdleTime;
    private int _maxTextMessageSize;
    private int _minVersion;
    private final Queue<WebSocketServletConnection> connections;

    public interface Acceptor {
        boolean checkOrigin(HttpServletRequest httpServletRequest, String str);

        WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String str);
    }

    public WebSocketFactory(Acceptor acceptor) {
        this(acceptor, 65536, 13);
    }

    public WebSocketFactory(Acceptor acceptor, int i) {
        this(acceptor, i, 13);
    }

    public WebSocketFactory(Acceptor acceptor, int i, int i2) {
        this.connections = new ConcurrentLinkedQueue();
        this._extensionClasses = new HashMap();
        this._extensionClasses.put("identity", IdentityExtension.class);
        this._extensionClasses.put("fragment", FragmentExtension.class);
        this._extensionClasses.put("x-deflate-frame", DeflateFrameExtension.class);
        this._maxIdleTime = 300000;
        this._maxTextMessageSize = 16384;
        this._maxBinaryMessageSize = -1;
        this._buffers = new WebSocketBuffers(i);
        this._acceptor = acceptor;
        this._minVersion = 13;
    }

    public int getMinVersion() {
        return this._minVersion;
    }

    public void setMinVersion(int i) {
        this._minVersion = i;
    }

    public Map<String, Class<? extends Extension>> getExtensionClassesMap() {
        return this._extensionClasses;
    }

    public long getMaxIdleTime() {
        return (long) this._maxIdleTime;
    }

    public void setMaxIdleTime(int i) {
        this._maxIdleTime = i;
    }

    public int getBufferSize() {
        return this._buffers.getBufferSize();
    }

    public void setBufferSize(int i) {
        if (i != getBufferSize()) {
            this._buffers = new WebSocketBuffers(i);
        }
    }

    public int getMaxTextMessageSize() {
        return this._maxTextMessageSize;
    }

    public void setMaxTextMessageSize(int i) {
        this._maxTextMessageSize = i;
    }

    public int getMaxBinaryMessageSize() {
        return this._maxBinaryMessageSize;
    }

    public void setMaxBinaryMessageSize(int i) {
        this._maxBinaryMessageSize = i;
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        closeConnections();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object[]} */
    /* JADX WARNING: type inference failed for: r9v1, types: [org.eclipse.jetty.websocket.WebSocketServletConnection, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r17v1 */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r0v19, types: [org.eclipse.jetty.websocket.WebSocketServletConnectionRFC6455] */
    /* JADX WARNING: type inference failed for: r0v20, types: [org.eclipse.jetty.websocket.WebSocketServletConnectionD00] */
    /* JADX WARNING: type inference failed for: r0v21, types: [org.eclipse.jetty.websocket.WebSocketServletConnectionD06] */
    /* JADX WARNING: type inference failed for: r0v22, types: [org.eclipse.jetty.websocket.WebSocketServletConnectionD08] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void upgrade(javax.servlet.http.HttpServletRequest r20, javax.servlet.http.HttpServletResponse r21, org.eclipse.jetty.websocket.WebSocket r22, java.lang.String r23) throws java.io.IOException {
        /*
            r19 = this;
            r11 = r19
            r12 = r20
            r13 = r21
            r14 = r23
            java.lang.String r0 = "Upgrade"
            java.lang.String r0 = r12.getHeader(r0)
            java.lang.String r1 = "websocket"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x01f4
            java.lang.String r0 = r20.getProtocol()
            java.lang.String r1 = "HTTP/1.1"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x01ec
            java.lang.String r0 = "Sec-WebSocket-Version"
            int r1 = r12.getIntHeader(r0)
            if (r1 >= 0) goto L_0x0030
            java.lang.String r1 = "Sec-WebSocket-Draft"
            int r1 = r12.getIntHeader(r1)
        L_0x0030:
            org.eclipse.jetty.server.AbstractHttpConnection r15 = org.eclipse.jetty.server.AbstractHttpConnection.getCurrentConnection()
            boolean r2 = r15 instanceof org.eclipse.jetty.server.BlockingHttpConnection
            if (r2 != 0) goto L_0x01e4
            org.eclipse.jetty.io.EndPoint r2 = r15.getEndPoint()
            r3 = r2
            org.eclipse.jetty.io.ConnectedEndPoint r3 = (org.eclipse.jetty.p119io.ConnectedEndPoint) r3
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.String r4 = "Sec-WebSocket-Extensions"
            java.util.Enumeration r4 = r12.getHeaders(r4)
        L_0x004a:
            boolean r5 = r4.hasMoreElements()
            if (r5 == 0) goto L_0x006b
            org.eclipse.jetty.util.QuotedStringTokenizer r5 = new org.eclipse.jetty.util.QuotedStringTokenizer
            java.lang.Object r6 = r4.nextElement()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r7 = ","
            r5.<init>(r6, r7)
        L_0x005d:
            boolean r6 = r5.hasMoreTokens()
            if (r6 == 0) goto L_0x004a
            java.lang.String r6 = r5.nextToken()
            r2.add(r6)
            goto L_0x005d
        L_0x006b:
            int r4 = r11._minVersion
            if (r1 >= r4) goto L_0x0076
            r4 = 2147483647(0x7fffffff, float:NaN)
            r10 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0077
        L_0x0076:
            r10 = r1
        L_0x0077:
            r4 = 13
            r9 = 0
            r8 = 3
            r5 = 5
            if (r10 == r4) goto L_0x016b
            switch(r10) {
                case -1: goto L_0x0151;
                case 0: goto L_0x0151;
                case 1: goto L_0x0137;
                case 2: goto L_0x0137;
                case 3: goto L_0x0137;
                case 4: goto L_0x0137;
                case 5: goto L_0x0137;
                case 6: goto L_0x0137;
                case 7: goto L_0x0114;
                case 8: goto L_0x0114;
                default: goto L_0x0081;
            }
        L_0x0081:
            java.lang.String r2 = "13"
            int r3 = r11._minVersion
            r4 = 8
            if (r3 > r4) goto L_0x009a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = ", 8"
            r3.append(r2)
            java.lang.String r2 = r3.toString()
        L_0x009a:
            int r3 = r11._minVersion
            r4 = 6
            if (r3 > r4) goto L_0x00b0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = ", 6"
            r3.append(r2)
            java.lang.String r2 = r3.toString()
        L_0x00b0:
            int r3 = r11._minVersion
            if (r3 > 0) goto L_0x00c5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = ", 0"
            r3.append(r2)
            java.lang.String r2 = r3.toString()
        L_0x00c5:
            r13.setHeader(r0, r2)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "Unsupported websocket client version specification "
            r0.append(r3)
            java.lang.String r3 = "]"
            if (r1 < 0) goto L_0x00e2
            java.lang.String r4 = "["
            r0.append(r4)
            r0.append(r1)
            r0.append(r3)
            goto L_0x00e7
        L_0x00e2:
            java.lang.String r1 = "<Unspecified, likely a pre-draft version of websocket>"
            r0.append(r1)
        L_0x00e7:
            java.lang.String r1 = ", configured minVersion ["
            r0.append(r1)
            int r1 = r11._minVersion
            r0.append(r1)
            r0.append(r3)
            java.lang.String r1 = ", reported supported versions ["
            r0.append(r1)
            r0.append(r2)
            r0.append(r3)
            org.eclipse.jetty.util.log.Logger r1 = LOG
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r2 = new java.lang.Object[r9]
            r1.warn((java.lang.String) r0, (java.lang.Object[]) r2)
            org.eclipse.jetty.http.HttpException r0 = new org.eclipse.jetty.http.HttpException
            r1 = 400(0x190, float:5.6E-43)
            java.lang.String r2 = "Unsupported websocket version specification"
            r0.<init>(r1, r2)
            throw r0
        L_0x0114:
            java.util.List r16 = r11.initExtensions(r2, r5, r5, r8)
            org.eclipse.jetty.websocket.WebSocketServletConnectionD08 r17 = new org.eclipse.jetty.websocket.WebSocketServletConnectionD08
            org.eclipse.jetty.websocket.WebSocketBuffers r4 = r11._buffers
            long r5 = r15.getTimeStamp()
            int r7 = r11._maxIdleTime
            r0 = r17
            r1 = r19
            r2 = r22
            r12 = 3
            r8 = r23
            r18 = 0
            r9 = r16
            r16 = r10
            r0.<init>(r1, r2, r3, r4, r5, r7, r8, r9, r10)
        L_0x0134:
            r9 = r17
            goto L_0x018a
        L_0x0137:
            r16 = r10
            r12 = 3
            r18 = 0
            org.eclipse.jetty.websocket.WebSocketServletConnectionD06 r9 = new org.eclipse.jetty.websocket.WebSocketServletConnectionD06
            org.eclipse.jetty.websocket.WebSocketBuffers r4 = r11._buffers
            long r5 = r15.getTimeStamp()
            int r7 = r11._maxIdleTime
            r0 = r9
            r1 = r19
            r2 = r22
            r8 = r23
            r0.<init>(r1, r2, r3, r4, r5, r7, r8)
            goto L_0x018a
        L_0x0151:
            r16 = r10
            r12 = 3
            r18 = 0
            org.eclipse.jetty.websocket.WebSocketServletConnectionD00 r9 = new org.eclipse.jetty.websocket.WebSocketServletConnectionD00
            org.eclipse.jetty.websocket.WebSocketBuffers r4 = r11._buffers
            long r5 = r15.getTimeStamp()
            int r7 = r11._maxIdleTime
            r0 = r9
            r1 = r19
            r2 = r22
            r8 = r23
            r0.<init>(r1, r2, r3, r4, r5, r7, r8)
            goto L_0x018a
        L_0x016b:
            r16 = r10
            r12 = 3
            r18 = 0
            java.util.List r9 = r11.initExtensions(r2, r5, r5, r12)
            org.eclipse.jetty.websocket.WebSocketServletConnectionRFC6455 r17 = new org.eclipse.jetty.websocket.WebSocketServletConnectionRFC6455
            org.eclipse.jetty.websocket.WebSocketBuffers r4 = r11._buffers
            long r5 = r15.getTimeStamp()
            int r7 = r11._maxIdleTime
            r0 = r17
            r1 = r19
            r2 = r22
            r8 = r23
            r0.<init>(r1, r2, r3, r4, r5, r7, r8, r9, r10)
            goto L_0x0134
        L_0x018a:
            r11.addConnection(r9)
            org.eclipse.jetty.websocket.WebSocket$Connection r0 = r9.getConnection()
            int r1 = r11._maxBinaryMessageSize
            r0.setMaxBinaryMessageSize(r1)
            org.eclipse.jetty.websocket.WebSocket$Connection r0 = r9.getConnection()
            int r1 = r11._maxTextMessageSize
            r0.setMaxTextMessageSize(r1)
            r0 = r20
            r1 = 3
            r9.handshake(r0, r13, r14)
            r21.flushBuffer()
            org.eclipse.jetty.http.Parser r2 = r15.getParser()
            org.eclipse.jetty.http.HttpParser r2 = (org.eclipse.jetty.http.HttpParser) r2
            org.eclipse.jetty.io.Buffer r2 = r2.getHeaderBuffer()
            r9.fillBuffersFrom(r2)
            org.eclipse.jetty.http.Parser r2 = r15.getParser()
            org.eclipse.jetty.http.HttpParser r2 = (org.eclipse.jetty.http.HttpParser) r2
            org.eclipse.jetty.io.Buffer r2 = r2.getBodyBuffer()
            r9.fillBuffersFrom(r2)
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r3 = 4
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r4 = r20.getRequestURI()
            r3[r18] = r4
            r4 = 1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r16)
            r3[r4] = r5
            r4 = 2
            r3[r4] = r14
            r3[r1] = r9
            java.lang.String r1 = "Websocket upgrade {} {} {} {}"
            r2.debug((java.lang.String) r1, (java.lang.Object[]) r3)
            java.lang.String r1 = "org.eclipse.jetty.io.Connection"
            r0.setAttribute(r1, r9)
            return
        L_0x01e4:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Websockets not supported on blocking connectors"
            r0.<init>(r1)
            throw r0
        L_0x01ec:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "!HTTP/1.1"
            r0.<init>(r1)
            throw r0
        L_0x01f4:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "!Upgrade:websocket"
            r0.<init>(r1)
            goto L_0x01fd
        L_0x01fc:
            throw r0
        L_0x01fd:
            goto L_0x01fc
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketFactory.upgrade(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.eclipse.jetty.websocket.WebSocket, java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public String[] parseProtocols(String str) {
        if (str == null) {
            return new String[]{null};
        }
        String trim = str.trim();
        if (trim == null || trim.length() == 0) {
            return new String[]{null};
        }
        String[] split = trim.split("\\s*,\\s*");
        String[] strArr = new String[(split.length + 1)];
        System.arraycopy(split, 0, strArr, 0, split.length);
        return strArr;
    }

    public boolean acceptWebSocket(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        if (!"websocket".equalsIgnoreCase(httpServletRequest.getHeader("Upgrade"))) {
            return false;
        }
        String header = httpServletRequest.getHeader("Origin");
        if (header == null) {
            header = httpServletRequest.getHeader("Sec-WebSocket-Origin");
        }
        if (!this._acceptor.checkOrigin(httpServletRequest, header)) {
            httpServletResponse.sendError(403);
            return false;
        }
        Enumeration<String> headers = httpServletRequest.getHeaders("Sec-WebSocket-Protocol");
        String str = null;
        WebSocket webSocket = null;
        while (str == null && headers != null && headers.hasMoreElements()) {
            String[] parseProtocols = parseProtocols(headers.nextElement());
            int length = parseProtocols.length;
            WebSocket webSocket2 = webSocket;
            int i = 0;
            while (true) {
                if (i >= length) {
                    webSocket = webSocket2;
                    break;
                }
                String str2 = parseProtocols[i];
                WebSocket doWebSocketConnect = this._acceptor.doWebSocketConnect(httpServletRequest, str2);
                if (doWebSocketConnect != null) {
                    str = str2;
                    webSocket = doWebSocketConnect;
                    break;
                }
                i++;
                webSocket2 = doWebSocketConnect;
            }
        }
        if (webSocket == null && (webSocket = this._acceptor.doWebSocketConnect(httpServletRequest, (String) null)) == null) {
            httpServletResponse.sendError(503);
            return false;
        }
        upgrade(httpServletRequest, httpServletResponse, webSocket, str);
        return true;
    }

    public List<Extension> initExtensions(List<String> list, int i, int i2, int i3) {
        ArrayList arrayList = new ArrayList();
        for (String quotedStringTokenizer : list) {
            QuotedStringTokenizer quotedStringTokenizer2 = new QuotedStringTokenizer(quotedStringTokenizer, ";");
            String trim = quotedStringTokenizer2.nextToken().trim();
            HashMap hashMap = new HashMap();
            while (quotedStringTokenizer2.hasMoreTokens()) {
                QuotedStringTokenizer quotedStringTokenizer3 = new QuotedStringTokenizer(quotedStringTokenizer2.nextToken().trim(), "=");
                hashMap.put(quotedStringTokenizer3.nextToken().trim(), quotedStringTokenizer3.hasMoreTokens() ? quotedStringTokenizer3.nextToken().trim() : null);
            }
            Extension newExtension = newExtension(trim);
            if (newExtension != null && newExtension.init(hashMap)) {
                LOG.debug("add {} {}", trim, hashMap);
                arrayList.add(newExtension);
            }
        }
        LOG.debug("extensions={}", arrayList);
        return arrayList;
    }

    private Extension newExtension(String str) {
        try {
            Class cls = this._extensionClasses.get(str);
            if (cls != null) {
                return (Extension) cls.newInstance();
            }
            return null;
        } catch (Exception e) {
            LOG.warn(e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean addConnection(WebSocketServletConnection webSocketServletConnection) {
        return isRunning() && this.connections.add(webSocketServletConnection);
    }

    /* access modifiers changed from: protected */
    public boolean removeConnection(WebSocketServletConnection webSocketServletConnection) {
        return this.connections.remove(webSocketServletConnection);
    }

    /* access modifiers changed from: protected */
    public void closeConnections() {
        for (WebSocketServletConnection shutdown : this.connections) {
            shutdown.shutdown();
        }
    }
}
