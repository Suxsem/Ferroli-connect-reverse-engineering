package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import kotlin.UByte;
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

public class WebSocketConnectionD06 extends AbstractConnection implements WebSocketConnection {
    static final int CLOSE_BADDATA = 1003;
    static final int CLOSE_LARGE = 1004;
    static final int CLOSE_NORMAL = 1000;
    static final int CLOSE_PROTOCOL = 1002;
    static final int CLOSE_SHUTDOWN = 1001;
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) WebSocketConnectionD06.class);
    private static final byte[] MAGIC;
    static final byte OP_BINARY = 5;
    static final byte OP_CLOSE = 1;
    static final byte OP_CONTINUATION = 0;
    static final byte OP_PING = 2;
    static final byte OP_PONG = 3;
    static final byte OP_TEXT = 4;
    /* access modifiers changed from: private */
    public volatile boolean _closedIn;
    /* access modifiers changed from: private */
    public volatile boolean _closedOut;
    /* access modifiers changed from: private */
    public final WebSocket.FrameConnection _connection = new FrameConnectionD06();
    private final WebSocketParser.FrameHandler _frameHandler = new FrameHandlerD06();
    /* access modifiers changed from: private */
    public final WebSocketGenerator _generator;
    /* access modifiers changed from: private */
    public int _maxBinaryMessageSize = -1;
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
    private final WebSocketParser _parser;
    /* access modifiers changed from: private */
    public final String _protocol;
    private final WebSocket _webSocket;

    static boolean isControlFrame(int i) {
        return i == 1 || i == 2 || i == 3;
    }

    static boolean isLastFrame(int i) {
        return (i & 8) != 0;
    }

    public boolean isSuspended() {
        return false;
    }

    public void onInputShutdown() throws IOException {
    }

    static {
        try {
            MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11".getBytes(StringUtil.__ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public WebSocketConnectionD06(WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str) throws IOException {
        super(endPoint, j);
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
        this._generator = new WebSocketGeneratorD06(webSocketBuffers, this._endp, (MaskGen) null);
        this._parser = new WebSocketParserD06(webSocketBuffers, endPoint, this._frameHandler, true);
        this._protocol = str;
        this._maxTextMessageSize = webSocketBuffers.getBufferSize();
        this._maxBinaryMessageSize = -1;
    }

    public WebSocket.Connection getConnection() {
        return this._connection;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x001d A[Catch:{ IOException -> 0x002a, all -> 0x0028 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0022 A[EDGE_INSN: B:56:0x0022->B:14:0x0022 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.p119io.Connection handle() throws java.io.IOException {
        /*
            r6 = this;
            r0 = 1
            r1 = 1
        L_0x0002:
            r2 = 0
            r3 = 1002(0x3ea, float:1.404E-42)
            if (r1 == 0) goto L_0x006b
            org.eclipse.jetty.websocket.WebSocketGenerator r1 = r6._generator     // Catch:{ IOException -> 0x002a }
            int r1 = r1.flush()     // Catch:{ IOException -> 0x002a }
            org.eclipse.jetty.websocket.WebSocketParser r4 = r6._parser     // Catch:{ IOException -> 0x002a }
            int r4 = r4.parseNext()     // Catch:{ IOException -> 0x002a }
            if (r1 > 0) goto L_0x001a
            if (r4 <= 0) goto L_0x0018
            goto L_0x001a
        L_0x0018:
            r5 = 0
            goto L_0x001b
        L_0x001a:
            r5 = 1
        L_0x001b:
            if (r4 < 0) goto L_0x0022
            if (r1 >= 0) goto L_0x0020
            goto L_0x0022
        L_0x0020:
            r1 = r5
            goto L_0x0002
        L_0x0022:
            org.eclipse.jetty.io.EndPoint r0 = r6._endp     // Catch:{ IOException -> 0x002a }
            r0.close()     // Catch:{ IOException -> 0x002a }
            goto L_0x006b
        L_0x0028:
            r0 = move-exception
            goto L_0x0038
        L_0x002a:
            r0 = move-exception
            org.eclipse.jetty.io.EndPoint r1 = r6._endp     // Catch:{ IOException -> 0x0031 }
            r1.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0037
        L_0x0031:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x0028 }
            r4.ignore(r1)     // Catch:{ all -> 0x0028 }
        L_0x0037:
            throw r0     // Catch:{ all -> 0x0028 }
        L_0x0038:
            org.eclipse.jetty.io.EndPoint r1 = r6._endp
            boolean r1 = r1.isOpen()
            if (r1 == 0) goto L_0x006a
            boolean r1 = r6._closedIn
            if (r1 == 0) goto L_0x0057
            boolean r1 = r6._closedOut
            if (r1 == 0) goto L_0x0057
            org.eclipse.jetty.websocket.WebSocketGenerator r1 = r6._generator
            boolean r1 = r1.isBufferEmpty()
            if (r1 != 0) goto L_0x0051
            goto L_0x0057
        L_0x0051:
            org.eclipse.jetty.io.EndPoint r1 = r6._endp
            r1.close()
            goto L_0x006a
        L_0x0057:
            org.eclipse.jetty.io.EndPoint r1 = r6._endp
            boolean r1 = r1.isInputShutdown()
            if (r1 == 0) goto L_0x0067
            boolean r1 = r6._closedIn
            if (r1 != 0) goto L_0x0067
            r6.closeIn(r3, r2)
            goto L_0x006a
        L_0x0067:
            r6.checkWriteable()
        L_0x006a:
            throw r0
        L_0x006b:
            org.eclipse.jetty.io.EndPoint r0 = r6._endp
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x009c
            boolean r0 = r6._closedIn
            if (r0 == 0) goto L_0x0089
            boolean r0 = r6._closedOut
            if (r0 == 0) goto L_0x0089
            org.eclipse.jetty.websocket.WebSocketGenerator r0 = r6._generator
            boolean r0 = r0.isBufferEmpty()
            if (r0 == 0) goto L_0x0089
            org.eclipse.jetty.io.EndPoint r0 = r6._endp
            r0.close()
            goto L_0x009c
        L_0x0089:
            org.eclipse.jetty.io.EndPoint r0 = r6._endp
            boolean r0 = r0.isInputShutdown()
            if (r0 == 0) goto L_0x0099
            boolean r0 = r6._closedIn
            if (r0 != 0) goto L_0x0099
            r6.closeIn(r3, r2)
            goto L_0x009c
        L_0x0099:
            r6.checkWriteable()
        L_0x009c:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketConnectionD06.handle():org.eclipse.jetty.io.Connection");
    }

    public boolean isIdle() {
        return this._parser.isBufferEmpty() && this._generator.isBufferEmpty();
    }

    public void onIdleExpired(long j) {
        closeOut(1000, "Idle");
    }

    public void onClose() {
        this._webSocket.onClose(1000, "");
    }

    public synchronized void closeIn(int i, String str) {
        LOG.debug("ClosedIn {} {}", this, str);
        try {
            if (this._closedOut) {
                this._endp.close();
            } else {
                closeOut(i, str);
            }
        } catch (IOException e) {
            try {
                LOG.ignore(e);
            } catch (Throwable th) {
                this._closedIn = true;
                throw th;
            }
        }
        this._closedIn = true;
    }

    public synchronized void closeOut(int i, String str) {
        LOG.debug("ClosedOut {} {}", this, str);
        try {
            if (!this._closedIn) {
                if (!this._closedOut) {
                    if (i <= 0) {
                        i = 1000;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("xx");
                    if (str == null) {
                        str = "";
                    }
                    sb.append(str);
                    byte[] bytes = sb.toString().getBytes(StringUtil.__ISO_8859_1);
                    bytes[0] = (byte) (i / 256);
                    bytes[1] = (byte) (i % 256);
                    this._generator.addFrame((byte) 8, (byte) 1, bytes, 0, bytes.length);
                    this._generator.flush();
                    this._closedOut = true;
                }
            }
            this._endp.close();
            this._generator.flush();
        } catch (IOException e) {
            try {
                LOG.ignore(e);
            } catch (Throwable th) {
                this._closedOut = true;
                throw th;
            }
        }
        this._closedOut = true;
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
        if (!this._generator.isBufferEmpty() && (this._endp instanceof AsyncEndPoint)) {
            ((AsyncEndPoint) this._endp).scheduleWrite();
        }
    }

    public List<Extension> getExtensions() {
        return Collections.emptyList();
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

    private class FrameConnectionD06 implements WebSocket.FrameConnection {
        volatile boolean _disconnecting;
        int _maxBinaryMessage;
        int _maxTextMessage;

        public byte binaryOpcode() {
            return 5;
        }

        public byte continuationOpcode() {
            return 0;
        }

        public byte finMask() {
            return 8;
        }

        public boolean isAllowFrameFragmentation() {
            return false;
        }

        public boolean isBinary(byte b) {
            return b == 5;
        }

        public boolean isClose(byte b) {
            return b == 1;
        }

        public boolean isContinuation(byte b) {
            return b == 0;
        }

        public boolean isPing(byte b) {
            return b == 2;
        }

        public boolean isPong(byte b) {
            return b == 3;
        }

        public boolean isText(byte b) {
            return b == 4;
        }

        public void setAllowFrameFragmentation(boolean z) {
        }

        public byte textOpcode() {
            return 4;
        }

        private FrameConnectionD06() {
            this._maxTextMessage = WebSocketConnectionD06.this._maxTextMessageSize;
            this._maxBinaryMessage = WebSocketConnectionD06.this._maxBinaryMessageSize;
        }

        public synchronized void sendMessage(String str) throws IOException {
            if (!WebSocketConnectionD06.this._closedOut) {
                byte[] bytes = str.getBytes("UTF-8");
                WebSocketConnectionD06.this._generator.addFrame((byte) 8, (byte) 4, bytes, 0, bytes.length);
                WebSocketConnectionD06.this._generator.flush();
                WebSocketConnectionD06.this.checkWriteable();
            } else {
                throw new IOException("closing");
            }
        }

        public synchronized void sendMessage(byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionD06.this._closedOut) {
                WebSocketConnectionD06.this._generator.addFrame((byte) 8, (byte) 5, bArr, i, i2);
                WebSocketConnectionD06.this._generator.flush();
                WebSocketConnectionD06.this.checkWriteable();
            } else {
                throw new IOException("closing");
            }
        }

        public void sendFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionD06.this._closedOut) {
                WebSocketConnectionD06.this._generator.addFrame(b, b2, bArr, i, i2);
                WebSocketConnectionD06.this._generator.flush();
                WebSocketConnectionD06.this.checkWriteable();
                return;
            }
            throw new IOException("closing");
        }

        public void sendControl(byte b, byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionD06.this._closedOut) {
                WebSocketConnectionD06.this._generator.addFrame((byte) 8, b, bArr, i, i2);
                WebSocketConnectionD06.this._generator.flush();
                WebSocketConnectionD06.this.checkWriteable();
                return;
            }
            throw new IOException("closing");
        }

        public boolean isMessageComplete(byte b) {
            return WebSocketConnectionD06.isLastFrame(b);
        }

        public boolean isOpen() {
            return WebSocketConnectionD06.this._endp != null && WebSocketConnectionD06.this._endp.isOpen();
        }

        public void close(int i, String str) {
            if (!this._disconnecting) {
                this._disconnecting = true;
                WebSocketConnectionD06.this.closeOut(i, str);
            }
        }

        public void setMaxIdleTime(int i) {
            try {
                WebSocketConnectionD06.this._endp.setMaxIdleTime(i);
            } catch (IOException e) {
                WebSocketConnectionD06.LOG.warn(e);
            }
        }

        public void setMaxTextMessageSize(int i) {
            this._maxTextMessage = i;
        }

        public void setMaxBinaryMessageSize(int i) {
            this._maxBinaryMessage = i;
        }

        public int getMaxTextMessageSize() {
            return this._maxTextMessage;
        }

        public int getMaxIdleTime() {
            return WebSocketConnectionD06.this._endp.getMaxIdleTime();
        }

        public int getMaxBinaryMessageSize() {
            return this._maxBinaryMessage;
        }

        public String getProtocol() {
            return WebSocketConnectionD06.this._protocol;
        }

        public boolean isControl(byte b) {
            return WebSocketConnectionD06.isControlFrame(b);
        }

        public void disconnect() {
            close();
        }

        public void close() {
            close(1000, (String) null);
        }

        public String toString() {
            return getClass().getSimpleName() + "@" + WebSocketConnectionD06.this._endp.getLocalAddr() + ":" + WebSocketConnectionD06.this._endp.getLocalPort() + "<->" + WebSocketConnectionD06.this._endp.getRemoteAddr() + ":" + WebSocketConnectionD06.this._endp.getRemotePort();
        }
    }

    private class FrameHandlerD06 implements WebSocketParser.FrameHandler {
        private ByteArrayBuffer _aggregate;
        private byte _opcode;
        private final Utf8StringBuilder _utf8;

        private FrameHandlerD06() {
            this._utf8 = new Utf8StringBuilder();
            this._opcode = -1;
        }

        public void onFrame(byte b, byte b2, Buffer buffer) {
            boolean isLastFrame = WebSocketConnectionD06.isLastFrame(b);
            synchronized (WebSocketConnectionD06.this) {
                if (!WebSocketConnectionD06.this._closedIn) {
                    try {
                        byte[] array = buffer.array();
                        if (WebSocketConnectionD06.this._onFrame != null) {
                            if (WebSocketConnectionD06.this._onFrame.onFrame(b, b2, array, buffer.getIndex(), buffer.length())) {
                                return;
                            }
                        }
                        if (WebSocketConnectionD06.this._onControl == null || !WebSocketConnectionD06.isControlFrame(b2) || !WebSocketConnectionD06.this._onControl.onControl(b2, array, buffer.getIndex(), buffer.length())) {
                            int i = -1;
                            if (b2 != 0) {
                                if (b2 == 1) {
                                    String str = null;
                                    if (buffer.length() >= 2) {
                                        i = (buffer.array()[buffer.getIndex()] * UByte.MAX_VALUE) + buffer.array()[buffer.getIndex() + 1];
                                        if (buffer.length() > 2) {
                                            str = new String(buffer.array(), buffer.getIndex() + 2, buffer.length() - 2, "UTF-8");
                                        }
                                    }
                                    WebSocketConnectionD06.this.closeIn(i, str);
                                } else if (b2 == 2) {
                                    WebSocketConnectionD06.LOG.debug("PING {}", this);
                                    if (!WebSocketConnectionD06.this._closedOut) {
                                        WebSocketConnectionD06.this._connection.sendControl((byte) 3, buffer.array(), buffer.getIndex(), buffer.length());
                                    }
                                } else if (b2 == 3) {
                                    WebSocketConnectionD06.LOG.debug("PONG {}", this);
                                } else if (b2 != 4) {
                                    if (WebSocketConnectionD06.this._onBinaryMessage != null) {
                                        if (isLastFrame) {
                                            WebSocketConnectionD06.this._onBinaryMessage.onMessage(array, buffer.getIndex(), buffer.length());
                                        } else if (WebSocketConnectionD06.this._connection.getMaxBinaryMessageSize() >= 0) {
                                            if (buffer.length() > WebSocketConnectionD06.this._connection.getMaxBinaryMessageSize()) {
                                                WebSocket.FrameConnection access$2000 = WebSocketConnectionD06.this._connection;
                                                access$2000.close(1004, "Message size > " + WebSocketConnectionD06.this._connection.getMaxBinaryMessageSize());
                                                if (this._aggregate != null) {
                                                    this._aggregate.clear();
                                                }
                                                this._opcode = -1;
                                            } else {
                                                this._opcode = b2;
                                                if (this._aggregate == null) {
                                                    this._aggregate = new ByteArrayBuffer(WebSocketConnectionD06.this._connection.getMaxBinaryMessageSize());
                                                }
                                                this._aggregate.put(buffer);
                                            }
                                        }
                                    }
                                } else if (WebSocketConnectionD06.this._onTextMessage != null) {
                                    if (isLastFrame) {
                                        WebSocketConnectionD06.this._onTextMessage.onMessage(buffer.toString("UTF-8"));
                                    } else if (WebSocketConnectionD06.this._connection.getMaxTextMessageSize() >= 0) {
                                        if (this._utf8.append(buffer.array(), buffer.getIndex(), buffer.length(), WebSocketConnectionD06.this._connection.getMaxTextMessageSize())) {
                                            this._opcode = 4;
                                        } else {
                                            this._utf8.reset();
                                            this._opcode = -1;
                                            WebSocket.FrameConnection access$20002 = WebSocketConnectionD06.this._connection;
                                            access$20002.close(1004, "Text message size > " + WebSocketConnectionD06.this._connection.getMaxTextMessageSize() + " chars");
                                        }
                                    }
                                }
                            } else if (this._opcode != 4 || WebSocketConnectionD06.this._connection.getMaxTextMessageSize() < 0) {
                                if (this._opcode >= 0 && WebSocketConnectionD06.this._connection.getMaxBinaryMessageSize() >= 0) {
                                    if (this._aggregate.space() < this._aggregate.length()) {
                                        WebSocket.FrameConnection access$20003 = WebSocketConnectionD06.this._connection;
                                        access$20003.close(1004, "Message size > " + WebSocketConnectionD06.this._connection.getMaxBinaryMessageSize());
                                        this._aggregate.clear();
                                        this._opcode = -1;
                                    } else {
                                        this._aggregate.put(buffer);
                                        if (isLastFrame && WebSocketConnectionD06.this._onBinaryMessage != null) {
                                            WebSocketConnectionD06.this._onBinaryMessage.onMessage(this._aggregate.array(), this._aggregate.getIndex(), this._aggregate.length());
                                            this._opcode = -1;
                                            this._aggregate.clear();
                                        }
                                    }
                                }
                            } else if (!this._utf8.append(buffer.array(), buffer.getIndex(), buffer.length(), WebSocketConnectionD06.this._connection.getMaxTextMessageSize())) {
                                WebSocket.FrameConnection access$20004 = WebSocketConnectionD06.this._connection;
                                access$20004.close(1004, "Text message size > " + WebSocketConnectionD06.this._connection.getMaxTextMessageSize() + " chars");
                                this._utf8.reset();
                                this._opcode = -1;
                            } else if (isLastFrame && WebSocketConnectionD06.this._onTextMessage != null) {
                                this._opcode = -1;
                                String utf8StringBuilder = this._utf8.toString();
                                this._utf8.reset();
                                WebSocketConnectionD06.this._onTextMessage.onMessage(utf8StringBuilder);
                            }
                        }
                    } catch (Throwable th) {
                        WebSocketConnectionD06.LOG.warn(th);
                    }
                }
            }
        }

        public void close(int i, String str) {
            WebSocketConnectionD06.this._connection.close(i, str);
        }

        public String toString() {
            return WebSocketConnectionD06.this.toString() + "FH";
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
}
