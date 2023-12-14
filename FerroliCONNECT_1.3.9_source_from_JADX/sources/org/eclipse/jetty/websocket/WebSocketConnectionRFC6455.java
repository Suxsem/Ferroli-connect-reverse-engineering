package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import org.eclipse.jetty.p119io.AbstractConnection;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.Utf8StringBuilder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketParser;

public class WebSocketConnectionRFC6455 extends AbstractConnection implements WebSocketConnection {
    static final int CLOSE_BAD_DATA = 1003;
    static final int CLOSE_BAD_PAYLOAD = 1007;
    static final int CLOSE_FAILED_TLS_HANDSHAKE = 1015;
    static final int CLOSE_MESSAGE_TOO_LARGE = 1009;
    static final int CLOSE_NORMAL = 1000;
    static final int CLOSE_NO_CLOSE = 1006;
    static final int CLOSE_NO_CODE = 1005;
    static final int CLOSE_POLICY_VIOLATION = 1008;
    static final int CLOSE_PROTOCOL = 1002;
    static final int CLOSE_REQUIRED_EXTENSION = 1010;
    static final int CLOSE_SERVER_ERROR = 1011;
    static final int CLOSE_SHUTDOWN = 1001;
    static final int CLOSE_UNDEFINED = 1004;
    static final int FLAG_FIN = 8;
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) WebSocketConnectionRFC6455.class);
    private static final byte[] MAGIC;
    static final byte OP_BINARY = 2;
    static final byte OP_CLOSE = 8;
    static final byte OP_CONTINUATION = 0;
    static final byte OP_CONTROL = 8;
    static final byte OP_EXT_CTRL = 11;
    static final byte OP_EXT_DATA = 3;
    static final byte OP_PING = 9;
    static final byte OP_PONG = 10;
    static final byte OP_TEXT = 1;
    static final int VERSION = 13;
    /* access modifiers changed from: private */
    public volatile int _closeCode;
    /* access modifiers changed from: private */
    public volatile String _closeMessage;
    /* access modifiers changed from: private */
    public volatile boolean _closedIn;
    /* access modifiers changed from: private */
    public volatile boolean _closedOut;
    /* access modifiers changed from: private */
    public final WebSocket.FrameConnection _connection;
    private final ClassLoader _context;
    private final int _draft;
    private final List<Extension> _extensions;
    private final WebSocketGeneratorRFC6455 _generator;
    /* access modifiers changed from: private */
    public int _maxBinaryMessageSize;
    /* access modifiers changed from: private */
    public int _maxTextMessageSize;
    /* access modifiers changed from: private */
    public final WebSocket.OnBinaryMessage _onBinaryMessage;
    /* access modifiers changed from: private */
    public final WebSocket.OnControl _onControl;
    /* access modifiers changed from: private */
    public final WebSocket.OnFrame _onFrame;
    /* access modifiers changed from: private */
    public final WebSocket.OnTextMessage _onTextMessage;
    /* access modifiers changed from: private */
    public final WebSocketGenerator _outbound;
    /* access modifiers changed from: private */
    public final WebSocketParserRFC6455 _parser;
    /* access modifiers changed from: private */
    public final String _protocol;
    private final WebSocket _webSocket;

    static boolean isControlFrame(byte b) {
        return (b & 8) != 0;
    }

    static boolean isLastFrame(byte b) {
        return (b & 8) != 0;
    }

    public boolean isSuspended() {
        return false;
    }

    static {
        try {
            MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11".getBytes(StringUtil.__ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public WebSocketConnectionRFC6455(WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str, List<Extension> list, int i2) throws IOException {
        this(webSocket, endPoint, webSocketBuffers, j, i, str, list, i2, (MaskGen) null);
    }

    public WebSocketConnectionRFC6455(WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str, List<Extension> list, int i2, MaskGen maskGen) throws IOException {
        super(endPoint, j);
        this._maxTextMessageSize = -1;
        this._maxBinaryMessageSize = -1;
        this._connection = new WSFrameConnection();
        this._context = Thread.currentThread().getContextClassLoader();
        this._draft = i2;
        this._endp.setMaxIdleTime(i);
        this._webSocket = webSocket;
        WebSocket webSocket2 = this._webSocket;
        this._onFrame = webSocket2 instanceof WebSocket.OnFrame ? (WebSocket.OnFrame) webSocket2 : null;
        WebSocket webSocket3 = this._webSocket;
        this._onTextMessage = webSocket3 instanceof WebSocket.OnTextMessage ? (WebSocket.OnTextMessage) webSocket3 : null;
        WebSocket webSocket4 = this._webSocket;
        this._onBinaryMessage = webSocket4 instanceof WebSocket.OnBinaryMessage ? (WebSocket.OnBinaryMessage) webSocket4 : null;
        WebSocket webSocket5 = this._webSocket;
        this._onControl = webSocket5 instanceof WebSocket.OnControl ? (WebSocket.OnControl) webSocket5 : null;
        this._generator = new WebSocketGeneratorRFC6455(webSocketBuffers, this._endp, maskGen);
        this._extensions = list;
        WebSocketParser.FrameHandler wSFrameHandler = new WSFrameHandler();
        List<Extension> list2 = this._extensions;
        boolean z = false;
        if (list2 != null) {
            int i3 = 0;
            for (Extension bind : list2) {
                bind.bind(this._connection, i3 == list.size() - 1 ? wSFrameHandler : list.get(i3 + 1), i3 == 0 ? this._generator : list.get(i3 - 1));
                i3++;
            }
        }
        List<Extension> list3 = this._extensions;
        this._outbound = (list3 == null || list3.size() == 0) ? this._generator : list.get(list.size() - 1);
        List<Extension> list4 = this._extensions;
        if (!(list4 == null || list4.size() == 0)) {
            wSFrameHandler = list.get(0);
        }
        this._parser = new WebSocketParserRFC6455(webSocketBuffers, endPoint, wSFrameHandler, maskGen == null ? true : z);
        this._protocol = str;
    }

    public WebSocket.Connection getConnection() {
        return this._connection;
    }

    public List<Extension> getExtensions() {
        List<Extension> list = this._extensions;
        return list == null ? Collections.emptyList() : list;
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x000f A[LOOP:1: B:2:0x000f->B:58:0x000f, LOOP_END, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x000e A[LOOP:0: B:1:0x000e->B:59:0x000e, LOOP_END, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.p119io.Connection handle() throws java.io.IOException {
        /*
            r7 = this;
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r1 = r0.getContextClassLoader()
            java.lang.ClassLoader r2 = r7._context
            r0.setContextClassLoader(r2)
            r2 = 1
        L_0x000e:
            r3 = 1
        L_0x000f:
            r4 = 0
            r5 = 1006(0x3ee, float:1.41E-42)
            if (r3 == 0) goto L_0x0096
            org.eclipse.jetty.websocket.WebSocketGeneratorRFC6455 r3 = r7._generator     // Catch:{ IOException -> 0x0040 }
            int r3 = r3.flushBuffer()     // Catch:{ IOException -> 0x0040 }
            org.eclipse.jetty.websocket.WebSocketParserRFC6455 r6 = r7._parser     // Catch:{ IOException -> 0x0040 }
            int r6 = r6.parseNext()     // Catch:{ IOException -> 0x0040 }
            if (r3 > 0) goto L_0x0027
            if (r6 <= 0) goto L_0x0025
            goto L_0x0027
        L_0x0025:
            r3 = 0
            goto L_0x0028
        L_0x0027:
            r3 = 1
        L_0x0028:
            org.eclipse.jetty.io.EndPoint r6 = r7._endp     // Catch:{ IOException -> 0x0040 }
            r6.flush()     // Catch:{ IOException -> 0x0040 }
            org.eclipse.jetty.io.EndPoint r6 = r7._endp     // Catch:{ IOException -> 0x0040 }
            boolean r6 = r6 instanceof org.eclipse.jetty.p119io.AsyncEndPoint     // Catch:{ IOException -> 0x0040 }
            if (r6 == 0) goto L_0x000f
            org.eclipse.jetty.io.EndPoint r6 = r7._endp     // Catch:{ IOException -> 0x0040 }
            org.eclipse.jetty.io.AsyncEndPoint r6 = (org.eclipse.jetty.p119io.AsyncEndPoint) r6     // Catch:{ IOException -> 0x0040 }
            boolean r4 = r6.hasProgressed()     // Catch:{ IOException -> 0x0040 }
            if (r4 == 0) goto L_0x000f
            goto L_0x000e
        L_0x003e:
            r2 = move-exception
            goto L_0x0056
        L_0x0040:
            r2 = move-exception
            org.eclipse.jetty.io.EndPoint r3 = r7._endp     // Catch:{ IOException -> 0x004f }
            boolean r3 = r3.isOpen()     // Catch:{ IOException -> 0x004f }
            if (r3 == 0) goto L_0x0055
            org.eclipse.jetty.io.EndPoint r3 = r7._endp     // Catch:{ IOException -> 0x004f }
            r3.close()     // Catch:{ IOException -> 0x004f }
            goto L_0x0055
        L_0x004f:
            r3 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x003e }
            r6.ignore(r3)     // Catch:{ all -> 0x003e }
        L_0x0055:
            throw r2     // Catch:{ all -> 0x003e }
        L_0x0056:
            r0.setContextClassLoader(r1)
            org.eclipse.jetty.websocket.WebSocketParserRFC6455 r0 = r7._parser
            r0.returnBuffer()
            org.eclipse.jetty.websocket.WebSocketGeneratorRFC6455 r0 = r7._generator
            r0.returnBuffer()
            org.eclipse.jetty.io.EndPoint r0 = r7._endp
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x0095
            boolean r0 = r7._closedIn
            if (r0 == 0) goto L_0x0082
            boolean r0 = r7._closedOut
            if (r0 == 0) goto L_0x0082
            org.eclipse.jetty.websocket.WebSocketGenerator r0 = r7._outbound
            boolean r0 = r0.isBufferEmpty()
            if (r0 != 0) goto L_0x007c
            goto L_0x0082
        L_0x007c:
            org.eclipse.jetty.io.EndPoint r0 = r7._endp
            r0.close()
            goto L_0x0095
        L_0x0082:
            org.eclipse.jetty.io.EndPoint r0 = r7._endp
            boolean r0 = r0.isInputShutdown()
            if (r0 == 0) goto L_0x0092
            boolean r0 = r7._closedIn
            if (r0 != 0) goto L_0x0092
            r7.closeIn(r5, r4)
            goto L_0x0095
        L_0x0092:
            r7.checkWriteable()
        L_0x0095:
            throw r2
        L_0x0096:
            r0.setContextClassLoader(r1)
            org.eclipse.jetty.websocket.WebSocketParserRFC6455 r0 = r7._parser
            r0.returnBuffer()
            org.eclipse.jetty.websocket.WebSocketGeneratorRFC6455 r0 = r7._generator
            r0.returnBuffer()
            org.eclipse.jetty.io.EndPoint r0 = r7._endp
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x00d4
            boolean r0 = r7._closedIn
            if (r0 == 0) goto L_0x00c1
            boolean r0 = r7._closedOut
            if (r0 == 0) goto L_0x00c1
            org.eclipse.jetty.websocket.WebSocketGenerator r0 = r7._outbound
            boolean r0 = r0.isBufferEmpty()
            if (r0 == 0) goto L_0x00c1
            org.eclipse.jetty.io.EndPoint r0 = r7._endp
            r0.close()
            goto L_0x00d4
        L_0x00c1:
            org.eclipse.jetty.io.EndPoint r0 = r7._endp
            boolean r0 = r0.isInputShutdown()
            if (r0 == 0) goto L_0x00d1
            boolean r0 = r7._closedIn
            if (r0 != 0) goto L_0x00d1
            r7.closeIn(r5, r4)
            goto L_0x00d4
        L_0x00d1:
            r7.checkWriteable()
        L_0x00d4:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.handle():org.eclipse.jetty.io.Connection");
    }

    public void onInputShutdown() throws IOException {
        if (!this._closedIn) {
            this._endp.close();
        }
    }

    public boolean isIdle() {
        return this._parser.isBufferEmpty() && this._outbound.isBufferEmpty();
    }

    public void onIdleExpired(long j) {
        closeOut(1000, "Idle for " + j + "ms > " + this._endp.getMaxIdleTime() + "ms");
    }

    public void onClose() {
        boolean z;
        synchronized (this) {
            z = this._closeCode == 0;
            if (z) {
                this._closeCode = 1006;
            }
        }
        if (z) {
            this._webSocket.onClose(1006, "closed");
        }
    }

    public void closeIn(int i, String str) {
        boolean z;
        boolean z2 = false;
        LOG.debug("ClosedIn {} {} {}", this, Integer.valueOf(i), str);
        synchronized (this) {
            z = this._closedOut;
            this._closedIn = true;
            if (this._closeCode == 0) {
                z2 = true;
            }
            if (z2) {
                this._closeCode = i;
                this._closeMessage = str;
            }
        }
        if (!z) {
            try {
                closeOut(i, str);
            } catch (Throwable th) {
                if (z2) {
                    this._webSocket.onClose(i, str);
                }
                throw th;
            }
        }
        if (z2) {
            this._webSocket.onClose(i, str);
        }
    }

    public void closeOut(int i, String str) {
        boolean z;
        boolean z2;
        LOG.debug("ClosedOut {} {} {}", this, Integer.valueOf(i), str);
        synchronized (this) {
            z = this._closedOut;
            this._closedOut = true;
            z2 = this._closeCode == 0;
            if (z2) {
                this._closeCode = i;
                this._closeMessage = str;
            }
        }
        if (z2) {
            try {
                this._webSocket.onClose(i, str);
            } catch (Throwable th) {
                if (!z) {
                    if (i < 0 || i == CLOSE_NO_CODE || i == 1006 || i == 1015) {
                        i = -1;
                    } else if (i == 0) {
                        i = 1000;
                    }
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("xx");
                        if (str == null) {
                            str = "";
                        }
                        sb.append(str);
                        byte[] bytes = sb.toString().getBytes(StringUtil.__ISO_8859_1);
                        bytes[0] = (byte) (i / 256);
                        bytes[1] = (byte) (i % 256);
                        this._outbound.addFrame((byte) 8, (byte) 8, bytes, 0, i > 0 ? bytes.length : 0);
                        this._outbound.flush();
                    } catch (IOException e) {
                        LOG.ignore(e);
                    }
                }
                throw th;
            }
        }
        if (!z) {
            if (i < 0 || i == CLOSE_NO_CODE || i == 1006 || i == 1015) {
                i = -1;
            } else if (i == 0) {
                i = 1000;
            }
            try {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("xx");
                if (str == null) {
                    str = "";
                }
                sb2.append(str);
                byte[] bytes2 = sb2.toString().getBytes(StringUtil.__ISO_8859_1);
                bytes2[0] = (byte) (i / 256);
                bytes2[1] = (byte) (i % 256);
                this._outbound.addFrame((byte) 8, (byte) 8, bytes2, 0, i > 0 ? bytes2.length : 0);
                this._outbound.flush();
            } catch (IOException e2) {
                LOG.ignore(e2);
            }
        }
    }

    public void shutdown() {
        WebSocket.FrameConnection frameConnection = this._connection;
        if (frameConnection != null) {
            frameConnection.close(1001, (String) null);
        }
    }

    public void fillBuffersFrom(Buffer buffer) {
        this._parser.fill(buffer);
    }

    /* access modifiers changed from: private */
    public void checkWriteable() {
        if (!this._outbound.isBufferEmpty() && (this._endp instanceof AsyncEndPoint)) {
            ((AsyncEndPoint) this._endp).scheduleWrite();
        }
    }

    /* access modifiers changed from: protected */
    public void onFrameHandshake() {
        WebSocket.OnFrame onFrame = this._onFrame;
        if (onFrame != null) {
            onFrame.onHandshake(this._connection);
        }
    }

    /* access modifiers changed from: protected */
    public void onWebSocketOpen() {
        this._webSocket.onOpen(this._connection);
    }

    private class WSFrameConnection implements WebSocket.FrameConnection {
        private volatile boolean _disconnecting;

        public byte binaryOpcode() {
            return 2;
        }

        public byte continuationOpcode() {
            return 0;
        }

        public byte finMask() {
            return 8;
        }

        public boolean isBinary(byte b) {
            return b == 2;
        }

        public boolean isClose(byte b) {
            return b == 8;
        }

        public boolean isContinuation(byte b) {
            return b == 0;
        }

        public boolean isPing(byte b) {
            return b == 9;
        }

        public boolean isPong(byte b) {
            return b == 10;
        }

        public boolean isText(byte b) {
            return b == 1;
        }

        public byte textOpcode() {
            return 1;
        }

        private WSFrameConnection() {
        }

        public void sendMessage(String str) throws IOException {
            if (!WebSocketConnectionRFC6455.this._closedOut) {
                byte[] bytes = str.getBytes("UTF-8");
                WebSocketConnectionRFC6455.this._outbound.addFrame((byte) 8, (byte) 1, bytes, 0, bytes.length);
                WebSocketConnectionRFC6455.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionRFC6455.this._closeCode + ":" + WebSocketConnectionRFC6455.this._closeMessage);
        }

        public void sendMessage(byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionRFC6455.this._closedOut) {
                WebSocketConnectionRFC6455.this._outbound.addFrame((byte) 8, (byte) 2, bArr, i, i2);
                WebSocketConnectionRFC6455.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionRFC6455.this._closeCode + ":" + WebSocketConnectionRFC6455.this._closeMessage);
        }

        public void sendFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionRFC6455.this._closedOut) {
                WebSocketConnectionRFC6455.this._outbound.addFrame(b, b2, bArr, i, i2);
                WebSocketConnectionRFC6455.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionRFC6455.this._closeCode + ":" + WebSocketConnectionRFC6455.this._closeMessage);
        }

        public void sendControl(byte b, byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionRFC6455.this._closedOut) {
                WebSocketConnectionRFC6455.this._outbound.addFrame((byte) 8, b, bArr, i, i2);
                WebSocketConnectionRFC6455.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionRFC6455.this._closeCode + ":" + WebSocketConnectionRFC6455.this._closeMessage);
        }

        public boolean isMessageComplete(byte b) {
            return WebSocketConnectionRFC6455.isLastFrame(b);
        }

        public boolean isOpen() {
            return WebSocketConnectionRFC6455.this._endp != null && WebSocketConnectionRFC6455.this._endp.isOpen();
        }

        public void close(int i, String str) {
            if (!this._disconnecting) {
                this._disconnecting = true;
                WebSocketConnectionRFC6455.this.closeOut(i, str);
            }
        }

        public void setMaxIdleTime(int i) {
            try {
                WebSocketConnectionRFC6455.this._endp.setMaxIdleTime(i);
            } catch (IOException e) {
                WebSocketConnectionRFC6455.LOG.warn(e);
            }
        }

        public void setMaxTextMessageSize(int i) {
            int unused = WebSocketConnectionRFC6455.this._maxTextMessageSize = i;
        }

        public void setMaxBinaryMessageSize(int i) {
            int unused = WebSocketConnectionRFC6455.this._maxBinaryMessageSize = i;
        }

        public int getMaxIdleTime() {
            return WebSocketConnectionRFC6455.this._endp.getMaxIdleTime();
        }

        public int getMaxTextMessageSize() {
            return WebSocketConnectionRFC6455.this._maxTextMessageSize;
        }

        public int getMaxBinaryMessageSize() {
            return WebSocketConnectionRFC6455.this._maxBinaryMessageSize;
        }

        public String getProtocol() {
            return WebSocketConnectionRFC6455.this._protocol;
        }

        public boolean isControl(byte b) {
            return WebSocketConnectionRFC6455.isControlFrame(b);
        }

        public void disconnect() {
            close(1000, (String) null);
        }

        public void close() {
            close(1000, (String) null);
        }

        public void setAllowFrameFragmentation(boolean z) {
            WebSocketConnectionRFC6455.this._parser.setFakeFragments(z);
        }

        public boolean isAllowFrameFragmentation() {
            return WebSocketConnectionRFC6455.this._parser.isFakeFragments();
        }

        public String toString() {
            return String.format("%s@%x l(%s:%d)<->r(%s:%d)", new Object[]{getClass().getSimpleName(), Integer.valueOf(hashCode()), WebSocketConnectionRFC6455.this._endp.getLocalAddr(), Integer.valueOf(WebSocketConnectionRFC6455.this._endp.getLocalPort()), WebSocketConnectionRFC6455.this._endp.getRemoteAddr(), Integer.valueOf(WebSocketConnectionRFC6455.this._endp.getRemotePort())});
        }
    }

    private class WSFrameHandler implements WebSocketParser.FrameHandler {
        private static final int MAX_CONTROL_FRAME_PAYLOAD = 125;
        private ByteArrayBuffer _aggregate;
        private byte _opcode;
        private final Utf8StringBuilder _utf8;

        private WSFrameHandler() {
            this._utf8 = new Utf8StringBuilder(512);
            this._opcode = -1;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:100:0x0276, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:102:0x027d, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2300(r1.this$0) == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:104:0x0289, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).getMaxTextMessageSize() > 0) goto L_0x02b8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:105:0x028b, code lost:
            if (r9 == false) goto L_0x029e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:0x028d, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2300(r1.this$0).onMessage(r8.toString("UTF-8"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:0x029e, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().warn("Frame discarded. Text aggregation disabled for {}", org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2600(r1.this$0));
            errorClose(1008, "Text frame aggregation disabled");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:109:0x02d4, code lost:
            if (r1._utf8.append(r21.array(), r21.getIndex(), r21.length(), org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).getMaxTextMessageSize()) == false) goto L_0x02f2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
            r15 = r21.array();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:0x02d6, code lost:
            if (r9 == false) goto L_0x02ee;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:0x02d8, code lost:
            r0 = r1._utf8.toString();
            r1._utf8.reset();
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2300(r1.this$0).onMessage(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:0x02ee, code lost:
            r1._opcode = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:113:0x02f2, code lost:
            textMessageTooLarge();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:0x02f9, code lost:
            if (r1._opcode != -1) goto L_0x0301;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x02fb, code lost:
            errorClose(r10, "Bad Continuation");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x0300, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x0307, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2300(r1.this$0) == null) goto L_0x0347;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0025, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.isControlFrame(r20) == false) goto L_0x0050;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x030b, code lost:
            if (r1._opcode != 1) goto L_0x0347;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x0329, code lost:
            if (r1._utf8.append(r21.array(), r21.getIndex(), r21.length(), org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).getMaxTextMessageSize()) == false) goto L_0x0344;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x032b, code lost:
            if (r9 == false) goto L_0x0347;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:125:0x032d, code lost:
            r1._opcode = -1;
            r0 = r1._utf8.toString();
            r1._utf8.reset();
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2300(r1.this$0).onMessage(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:0x0344, code lost:
            textMessageTooLarge();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:128:0x0349, code lost:
            if (r1._opcode < 0) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:0x0355, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).getMaxBinaryMessageSize() < 0) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:132:0x0359, code lost:
            if (r1._aggregate == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x0369, code lost:
            if (checkBinaryMessageSize(r1._aggregate.length(), r21.length()) == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x036b, code lost:
            r1._aggregate.put(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x0370, code lost:
            if (r9 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x0378, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2500(r1.this$0) == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
            if (r21.length() <= MAX_CONTROL_FRAME_PAYLOAD) goto L_0x0050;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:?, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2500(r1.this$0).onMessage(r1._aggregate.array(), r1._aggregate.getIndex(), r1._aggregate.length());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:?, code lost:
            r1._opcode = -1;
            r1._aggregate.clear();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:143:0x039d, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:144:0x039e, code lost:
            r1._opcode = -1;
            r1._aggregate.clear();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:145:0x03a5, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:146:0x03a6, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:147:0x03a7, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().warn("{} for {}", r0, org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2900(r1.this$0), r0);
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().debug(r0);
            errorClose(1011, "Internal Server Error: " + r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:148:0x03dd, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x03de, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().warn("NOTUTF8 - {} for {}", r0, org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2800(r1.this$0), r0);
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().debug(r0);
            errorClose(1007, "Invalid UTF-8");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x002f, code lost:
            errorClose(1002, "Control frame too large: " + r21.length() + " > " + MAX_CONTROL_FRAME_PAYLOAD);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:158:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:159:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x004f, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:163:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:164:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:165:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:166:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:167:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:168:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:169:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:171:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:172:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:173:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:174:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:175:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:176:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:177:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:178:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:179:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0052, code lost:
            if ((r19 & 7) == 0) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:180:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:181:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0054, code lost:
            errorClose(1002, "RSV bits set 0x" + java.lang.Integer.toHexString(r19));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x006c, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0075, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$300(r1.this$0) == 0) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x007d, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$300(r1.this$0) == 1000) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0081, code lost:
            if (r0 == 8) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0083, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x008a, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2100(r1.this$0) == null) goto L_0x00ae;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x008c, code lost:
            r11 = 1000;
            r10 = 1002;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ab, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2100(r1.this$0).onFrame(r19, r20, r15, r21.getIndex(), r21.length()) == false) goto L_0x00b2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ad, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ae, code lost:
            r10 = 1002;
            r11 = 1000;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b8, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2200(r1.this$0) == null) goto L_0x00d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00be, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.isControlFrame(r20) == false) goto L_0x00d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00d2, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2200(r1.this$0).onControl(r0, r15, r21.getIndex(), r21.length()) == false) goto L_0x00d5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00d4, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00d6, code lost:
            if (r0 == 0) goto L_0x02f7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00da, code lost:
            if (r0 == 1) goto L_0x025a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00dc, code lost:
            if (r0 == 2) goto L_0x01d2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00de, code lost:
            switch(r0) {
                case 8: goto L_0x0138;
                case 9: goto L_0x010a;
                case 10: goto L_0x00fb;
                default: goto L_0x00e1;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e1, code lost:
            errorClose(r10, "Bad opcode 0x" + java.lang.Integer.toHexString(r20));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x00fb, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().debug("PONG {}", r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x010a, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().debug("PING {}", r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x011d, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$200(r1.this$0) != false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x011f, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).sendControl((byte) 10, r21.array(), r21.getIndex(), r21.length());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0138, code lost:
            r0 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x013f, code lost:
            if (r21.length() < 2) goto L_0x01bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0141, code lost:
            r2 = ((r21.array()[r21.getIndex()] & kotlin.UByte.MAX_VALUE) * 256) + (r21.array()[r21.getIndex() + 1] & kotlin.UByte.MAX_VALUE);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x015d, code lost:
            if (r2 < r11) goto L_0x01a8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x0161, code lost:
            if (r2 == 1004) goto L_0x01a8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x0165, code lost:
            if (r2 == 1006) goto L_0x01a8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x0167, code lost:
            if (r2 == org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.CLOSE_NO_CODE) goto L_0x01a8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x016b, code lost:
            if (r2 <= 1011) goto L_0x0171;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x016f, code lost:
            if (r2 <= 2999) goto L_0x01a8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x0173, code lost:
            if (r2 < 5000) goto L_0x0176;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:0x017a, code lost:
            if (r21.length() <= 2) goto L_0x01cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x019a, code lost:
            if (r1._utf8.append(r21.array(), r21.getIndex() + 2, r21.length() - 2, org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).getMaxTextMessageSize()) == false) goto L_0x01cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x019c, code lost:
            r0 = r1._utf8.toString();
            r1._utf8.reset();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x01a8, code lost:
            errorClose(r10, "Invalid close code " + r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:0x01bc, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:0x01c1, code lost:
            if (r21.length() != 1) goto L_0x01c9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:0x01c3, code lost:
            errorClose(r10, "Invalid payload length of 1");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x01c8, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x01c9, code lost:
            r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.CLOSE_NO_CODE;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x01cb, code lost:
            r1.this$0.closeIn(r2, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x01d4, code lost:
            if (r1._opcode == -1) goto L_0x01ef;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x01d6, code lost:
            errorClose(r10, "Expected Continuation" + java.lang.Integer.toHexString(r20));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:83:0x01ee, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:85:0x01f5, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2500(r1.this$0) == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ff, code lost:
            if (checkBinaryMessageSize(0, r21.length()) == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:88:0x0201, code lost:
            if (r9 == false) goto L_0x0216;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x0203, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2500(r1.this$0).onMessage(r15, r21.getIndex(), r21.length());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:91:0x0220, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).getMaxBinaryMessageSize() < 0) goto L_0x0240;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:92:0x0222, code lost:
            r1._opcode = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:93:0x0226, code lost:
            if (r1._aggregate != null) goto L_0x0239;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x0228, code lost:
            r1._aggregate = new org.eclipse.jetty.p119io.ByteArrayBuffer(org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2400(r1.this$0).getMaxBinaryMessageSize());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:0x0239, code lost:
            r1._aggregate.put(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:96:0x0240, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$1000().warn("Frame discarded. Binary aggregation disabed for {}", org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.access$2700(r1.this$0));
            errorClose(1008, "Binary frame aggregation disabled");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:0x025c, code lost:
            if (r1._opcode == -1) goto L_0x0277;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x025e, code lost:
            errorClose(r10, "Expected Continuation" + java.lang.Integer.toHexString(r20));
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFrame(byte r19, byte r20, org.eclipse.jetty.p119io.Buffer r21) {
            /*
                r18 = this;
                r1 = r18
                r0 = r20
                r8 = r21
                boolean r9 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.isLastFrame(r19)
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this
                monitor-enter(r2)
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r3 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ all -> 0x0405 }
                boolean r3 = r3._closedIn     // Catch:{ all -> 0x0405 }
                if (r3 == 0) goto L_0x0017
                monitor-exit(r2)     // Catch:{ all -> 0x0405 }
                return
            L_0x0017:
                monitor-exit(r2)     // Catch:{ all -> 0x0405 }
                r12 = 2
                r13 = 0
                r14 = 1
                byte[] r15 = r21.array()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.isControlFrame(r20)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r7 = 1002(0x3ea, float:1.404E-42)
                if (r2 == 0) goto L_0x0050
                int r2 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3 = 125(0x7d, float:1.75E-43)
                if (r2 <= r3) goto L_0x0050
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.<init>()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = "Control frame too large: "
                r0.append(r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r2 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.append(r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = " > "
                r0.append(r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.append(r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r1.errorClose(r7, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                return
            L_0x0050:
                r2 = r19 & 7
                if (r2 == 0) goto L_0x006d
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.<init>()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = "RSV bits set 0x"
                r0.append(r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = java.lang.Integer.toHexString(r19)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.append(r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r1.errorClose(r7, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                return
            L_0x006d:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r2 = r2._closeCode     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r6 = 1000(0x3e8, float:1.401E-42)
                if (r2 == 0) goto L_0x0084
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r2 = r2._closeCode     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == r6) goto L_0x0084
                r2 = 8
                if (r0 == r2) goto L_0x0084
                return
            L_0x0084:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnFrame r2 = r2._onFrame     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == 0) goto L_0x00ae
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnFrame r2 = r2._onFrame     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r16 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r17 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3 = r19
                r4 = r20
                r5 = r15
                r11 = 1000(0x3e8, float:1.401E-42)
                r6 = r16
                r10 = 1002(0x3ea, float:1.404E-42)
                r7 = r17
                boolean r2 = r2.onFrame(r3, r4, r5, r6, r7)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == 0) goto L_0x00b2
                return
            L_0x00ae:
                r10 = 1002(0x3ea, float:1.404E-42)
                r11 = 1000(0x3e8, float:1.401E-42)
            L_0x00b2:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnControl r2 = r2._onControl     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == 0) goto L_0x00d5
                boolean r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.isControlFrame(r20)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == 0) goto L_0x00d5
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnControl r2 = r2._onControl     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r3 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r4 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r2 = r2.onControl(r0, r15, r3, r4)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == 0) goto L_0x00d5
                return
            L_0x00d5:
                r2 = -1
                if (r0 == 0) goto L_0x02f7
                r3 = 1008(0x3f0, float:1.413E-42)
                if (r0 == r14) goto L_0x025a
                if (r0 == r12) goto L_0x01d2
                switch(r0) {
                    case 8: goto L_0x0138;
                    case 9: goto L_0x010a;
                    case 10: goto L_0x00fb;
                    default: goto L_0x00e1;
                }     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
            L_0x00e1:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.<init>()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r3 = "Bad opcode 0x"
                r2.append(r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = java.lang.Integer.toHexString(r20)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.append(r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r2.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r1.errorClose(r10, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x00fb:
                org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = "PONG {}"
                java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3[r13] = r1     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.debug((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x010a:
                org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = "PING {}"
                java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3[r13] = r1     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.debug((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r0 = r0._closedOut     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 != 0) goto L_0x0404
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r0 = r0._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2 = 10
                byte[] r3 = r21.array()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r4 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r5 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.sendControl(r2, r3, r4, r5)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x0138:
                r0 = 0
                int r2 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3 = 1005(0x3ed, float:1.408E-42)
                if (r2 < r12) goto L_0x01bd
                byte[] r2 = r21.array()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r4 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                byte r2 = r2[r4]     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2 = r2 & 255(0xff, float:3.57E-43)
                int r2 = r2 * 256
                byte[] r4 = r21.array()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r5 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r5 = r5 + r14
                byte r4 = r4[r5]     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r4 = r4 & 255(0xff, float:3.57E-43)
                int r2 = r2 + r4
                if (r2 < r11) goto L_0x01a8
                r4 = 1004(0x3ec, float:1.407E-42)
                if (r2 == r4) goto L_0x01a8
                r4 = 1006(0x3ee, float:1.41E-42)
                if (r2 == r4) goto L_0x01a8
                if (r2 == r3) goto L_0x01a8
                r3 = 1011(0x3f3, float:1.417E-42)
                if (r2 <= r3) goto L_0x0171
                r3 = 2999(0xbb7, float:4.202E-42)
                if (r2 <= r3) goto L_0x01a8
            L_0x0171:
                r3 = 5000(0x1388, float:7.006E-42)
                if (r2 < r3) goto L_0x0176
                goto L_0x01a8
            L_0x0176:
                int r3 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r3 <= r12) goto L_0x01cb
                org.eclipse.jetty.util.Utf8StringBuilder r3 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                byte[] r4 = r21.array()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r5 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r5 = r5 + r12
                int r6 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r6 = r6 - r12
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r7 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r7 = r7._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r7 = r7.getMaxTextMessageSize()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r3 = r3.append(r4, r5, r6, r7)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r3 == 0) goto L_0x01cb
                org.eclipse.jetty.util.Utf8StringBuilder r0 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.util.Utf8StringBuilder r3 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3.reset()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x01cb
            L_0x01a8:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.<init>()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r3 = "Invalid close code "
                r0.append(r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.append(r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r1.errorClose(r10, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                return
            L_0x01bd:
                int r2 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 != r14) goto L_0x01c9
                java.lang.String r0 = "Invalid payload length of 1"
                r1.errorClose(r10, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                return
            L_0x01c9:
                r2 = 1005(0x3ed, float:1.408E-42)
            L_0x01cb:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r3 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3.closeIn(r2, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x01d2:
                byte r4 = r1._opcode     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r4 == r2) goto L_0x01ef
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.<init>()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r3 = "Expected Continuation"
                r2.append(r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = java.lang.Integer.toHexString(r20)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.append(r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r2.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r1.errorClose(r10, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                return
            L_0x01ef:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r2 = r2._onBinaryMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == 0) goto L_0x0404
                int r2 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r2 = r1.checkBinaryMessageSize(r13, r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 == 0) goto L_0x0404
                if (r9 == 0) goto L_0x0216
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r0 = r0._onBinaryMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r2 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r3 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.onMessage(r15, r2, r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x0216:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r2 = r2._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r2 = r2.getMaxBinaryMessageSize()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r2 < 0) goto L_0x0240
                r1._opcode = r0     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r1._aggregate     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 != 0) goto L_0x0239
                org.eclipse.jetty.io.ByteArrayBuffer r0 = new org.eclipse.jetty.io.ByteArrayBuffer     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r2 = r2._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r2 = r2.getMaxBinaryMessageSize()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.<init>((int) r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r1._aggregate = r0     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
            L_0x0239:
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r1._aggregate     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.put((org.eclipse.jetty.p119io.Buffer) r8)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x0240:
                org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = "Frame discarded. Binary aggregation disabed for {}"
                java.lang.Object[] r4 = new java.lang.Object[r14]     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r5 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.io.EndPoint r5 = r5._endp     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r4[r13] = r5     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.warn((java.lang.String) r2, (java.lang.Object[]) r4)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = "Binary frame aggregation disabled"
                r1.errorClose(r3, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x025a:
                byte r4 = r1._opcode     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r4 == r2) goto L_0x0277
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.<init>()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r3 = "Expected Continuation"
                r2.append(r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = java.lang.Integer.toHexString(r20)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.append(r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r2.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r1.errorClose(r10, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                return
            L_0x0277:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r0 = r0._onTextMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 == 0) goto L_0x0404
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r0 = r0._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r0 = r0.getMaxTextMessageSize()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 > 0) goto L_0x02b8
                if (r9 == 0) goto L_0x029e
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r0 = r0._onTextMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = "UTF-8"
                java.lang.String r2 = r8.toString((java.lang.String) r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.onMessage(r2)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x029e:
                org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r2 = "Frame discarded. Text aggregation disabled for {}"
                java.lang.Object[] r4 = new java.lang.Object[r14]     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r5 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.io.EndPoint r5 = r5._endp     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r4[r13] = r5     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.warn((java.lang.String) r2, (java.lang.Object[]) r4)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = "Text frame aggregation disabled"
                r1.errorClose(r3, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x02b8:
                org.eclipse.jetty.util.Utf8StringBuilder r0 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                byte[] r2 = r21.array()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r3 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r4 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r5 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r5 = r5._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r5 = r5.getMaxTextMessageSize()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r0 = r0.append(r2, r3, r4, r5)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 == 0) goto L_0x02f2
                if (r9 == 0) goto L_0x02ee
                org.eclipse.jetty.util.Utf8StringBuilder r0 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.util.Utf8StringBuilder r2 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.reset()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r2 = r2._onTextMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.onMessage(r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x02ee:
                r1._opcode = r14     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x02f2:
                r18.textMessageTooLarge()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x02f7:
                byte r0 = r1._opcode     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 != r2) goto L_0x0301
                java.lang.String r0 = "Bad Continuation"
                r1.errorClose(r10, r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                return
            L_0x0301:
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r0 = r0._onTextMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 == 0) goto L_0x0347
                byte r0 = r1._opcode     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 != r14) goto L_0x0347
                org.eclipse.jetty.util.Utf8StringBuilder r0 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                byte[] r3 = r21.array()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r4 = r21.getIndex()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r5 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r6 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r6 = r6._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r6 = r6.getMaxTextMessageSize()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r0 = r0.append(r3, r4, r5, r6)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 == 0) goto L_0x0344
                if (r9 == 0) goto L_0x0347
                r1._opcode = r2     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.util.Utf8StringBuilder r0 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                java.lang.String r0 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.util.Utf8StringBuilder r3 = r1._utf8     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3.reset()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r3 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r3 = r3._onTextMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r3.onMessage(r0)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0347
            L_0x0344:
                r18.textMessageTooLarge()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
            L_0x0347:
                byte r0 = r1._opcode     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 < 0) goto L_0x0404
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r0 = r0._connection     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r0 = r0.getMaxBinaryMessageSize()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 < 0) goto L_0x0404
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r1._aggregate     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 == 0) goto L_0x0404
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r1._aggregate     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r0 = r0.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                int r3 = r21.length()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                boolean r0 = r1.checkBinaryMessageSize(r0, r3)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 == 0) goto L_0x0404
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r1._aggregate     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.put((org.eclipse.jetty.p119io.Buffer) r8)     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r9 == 0) goto L_0x0404
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r0 = r0._onBinaryMessage     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                if (r0 == 0) goto L_0x0404
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r0 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this     // Catch:{ all -> 0x039d }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r0 = r0._onBinaryMessage     // Catch:{ all -> 0x039d }
                org.eclipse.jetty.io.ByteArrayBuffer r3 = r1._aggregate     // Catch:{ all -> 0x039d }
                byte[] r3 = r3.array()     // Catch:{ all -> 0x039d }
                org.eclipse.jetty.io.ByteArrayBuffer r4 = r1._aggregate     // Catch:{ all -> 0x039d }
                int r4 = r4.getIndex()     // Catch:{ all -> 0x039d }
                org.eclipse.jetty.io.ByteArrayBuffer r5 = r1._aggregate     // Catch:{ all -> 0x039d }
                int r5 = r5.length()     // Catch:{ all -> 0x039d }
                r0.onMessage(r3, r4, r5)     // Catch:{ all -> 0x039d }
                r1._opcode = r2     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r1._aggregate     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r0.clear()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                goto L_0x0404
            L_0x039d:
                r0 = move-exception
                r1._opcode = r2     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                org.eclipse.jetty.io.ByteArrayBuffer r2 = r1._aggregate     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                r2.clear()     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
                throw r0     // Catch:{ NotUtf8Exception -> 0x03dd, Throwable -> 0x03a6 }
            L_0x03a6:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG
                r3 = 3
                java.lang.Object[] r3 = new java.lang.Object[r3]
                r3[r13] = r0
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r4 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this
                org.eclipse.jetty.io.EndPoint r4 = r4._endp
                r3[r14] = r4
                r3[r12] = r0
                java.lang.String r4 = "{} for {}"
                r2.warn((java.lang.String) r4, (java.lang.Object[]) r3)
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG
                r2.debug(r0)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "Internal Server Error: "
                r2.append(r3)
                r2.append(r0)
                java.lang.String r0 = r2.toString()
                r2 = 1011(0x3f3, float:1.417E-42)
                r1.errorClose(r2, r0)
                goto L_0x0404
            L_0x03dd:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG
                r3 = 3
                java.lang.Object[] r3 = new java.lang.Object[r3]
                r3[r13] = r0
                org.eclipse.jetty.websocket.WebSocketConnectionRFC6455 r4 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.this
                org.eclipse.jetty.io.EndPoint r4 = r4._endp
                r3[r14] = r4
                r3[r12] = r0
                java.lang.String r4 = "NOTUTF8 - {} for {}"
                r2.warn((java.lang.String) r4, (java.lang.Object[]) r3)
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.LOG
                r2.debug(r0)
                r0 = 1007(0x3ef, float:1.411E-42)
                java.lang.String r2 = "Invalid UTF-8"
                r1.errorClose(r0, r2)
            L_0x0404:
                return
            L_0x0405:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0405 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.WSFrameHandler.onFrame(byte, byte, org.eclipse.jetty.io.Buffer):void");
        }

        private void errorClose(int i, String str) {
            WebSocketConnectionRFC6455.this._connection.close(i, str);
            try {
                WebSocketConnectionRFC6455.this._endp.close();
            } catch (IOException e) {
                WebSocketConnectionRFC6455.LOG.warn(e.toString(), new Object[0]);
                WebSocketConnectionRFC6455.LOG.debug(e);
            }
        }

        private boolean checkBinaryMessageSize(int i, int i2) {
            int maxBinaryMessageSize = WebSocketConnectionRFC6455.this._connection.getMaxBinaryMessageSize();
            if (maxBinaryMessageSize <= 0 || i + i2 <= maxBinaryMessageSize) {
                return true;
            }
            WebSocketConnectionRFC6455.LOG.warn("Binary message too large > {}B for {}", Integer.valueOf(WebSocketConnectionRFC6455.this._connection.getMaxBinaryMessageSize()), WebSocketConnectionRFC6455.this._endp);
            WebSocket.FrameConnection access$2400 = WebSocketConnectionRFC6455.this._connection;
            access$2400.close(1009, "Message size > " + WebSocketConnectionRFC6455.this._connection.getMaxBinaryMessageSize());
            this._opcode = -1;
            ByteArrayBuffer byteArrayBuffer = this._aggregate;
            if (byteArrayBuffer != null) {
                byteArrayBuffer.clear();
            }
            return false;
        }

        private void textMessageTooLarge() {
            WebSocketConnectionRFC6455.LOG.warn("Text message too large > {} chars for {}", Integer.valueOf(WebSocketConnectionRFC6455.this._connection.getMaxTextMessageSize()), WebSocketConnectionRFC6455.this._endp);
            WebSocket.FrameConnection access$2400 = WebSocketConnectionRFC6455.this._connection;
            access$2400.close(1009, "Text message size > " + WebSocketConnectionRFC6455.this._connection.getMaxTextMessageSize() + " chars");
            this._opcode = -1;
            this._utf8.reset();
        }

        public void close(int i, String str) {
            if (i != 1000) {
                Logger access$1000 = WebSocketConnectionRFC6455.LOG;
                access$1000.warn("Close: " + i + " " + str, new Object[0]);
            }
            WebSocketConnectionRFC6455.this._connection.close(i, str);
        }

        public String toString() {
            return WebSocketConnectionRFC6455.this.toString() + "FH";
        }
    }

    public static String hashKey(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            instance.update(str.getBytes("UTF-8"));
            instance.update(MAGIC);
            return new String(B64Code.encode(instance.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return String.format("%s p=%s g=%s", new Object[]{getClass().getSimpleName(), this._parser, this._generator});
    }
}
