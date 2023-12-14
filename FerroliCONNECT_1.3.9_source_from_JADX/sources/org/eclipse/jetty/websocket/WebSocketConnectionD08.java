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

public class WebSocketConnectionD08 extends AbstractConnection implements WebSocketConnection {
    static final int CLOSE_BADDATA = 1003;
    static final int CLOSE_NOCLOSE = 1006;
    static final int CLOSE_NOCODE = 1005;
    static final int CLOSE_NORMAL = 1000;
    static final int CLOSE_NOTUTF8 = 1007;
    static final int CLOSE_PROTOCOL = 1002;
    static final int CLOSE_SHUTDOWN = 1001;
    static final int FLAG_FIN = 8;
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) WebSocketConnectionD08.class);
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
    static final int VERSION = 8;
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
    private final WebSocketGeneratorD08 _generator;
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
    public final WebSocketParserD08 _parser;
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

    public WebSocketConnectionD08(WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str, List<Extension> list, int i2) throws IOException {
        this(webSocket, endPoint, webSocketBuffers, j, i, str, list, i2, (MaskGen) null);
    }

    public WebSocketConnectionD08(WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str, List<Extension> list, int i2, MaskGen maskGen) throws IOException {
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
        this._generator = new WebSocketGeneratorD08(webSocketBuffers, this._endp, maskGen);
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
        this._parser = new WebSocketParserD08(webSocketBuffers, endPoint, wSFrameHandler, maskGen == null ? true : z);
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
            org.eclipse.jetty.websocket.WebSocketGeneratorD08 r3 = r7._generator     // Catch:{ IOException -> 0x0040 }
            int r3 = r3.flushBuffer()     // Catch:{ IOException -> 0x0040 }
            org.eclipse.jetty.websocket.WebSocketParserD08 r6 = r7._parser     // Catch:{ IOException -> 0x0040 }
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
            org.eclipse.jetty.websocket.WebSocketParserD08 r0 = r7._parser
            r0.returnBuffer()
            org.eclipse.jetty.websocket.WebSocketGeneratorD08 r0 = r7._generator
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
            org.eclipse.jetty.websocket.WebSocketParserD08 r0 = r7._parser
            r0.returnBuffer()
            org.eclipse.jetty.websocket.WebSocketGeneratorD08 r0 = r7._generator
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
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketConnectionD08.handle():org.eclipse.jetty.io.Connection");
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
                    if (i <= 0) {
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
                        this._outbound.addFrame((byte) 8, (byte) 8, bytes, 0, bytes.length);
                    } catch (IOException e) {
                        LOG.ignore(e);
                        throw th;
                    }
                }
                this._outbound.flush();
                throw th;
            }
        }
        if (!z) {
            if (i <= 0) {
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
                this._outbound.addFrame((byte) 8, (byte) 8, bytes2, 0, bytes2.length);
            } catch (IOException e2) {
                LOG.ignore(e2);
                return;
            }
        }
        this._outbound.flush();
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
            if (!WebSocketConnectionD08.this._closedOut) {
                byte[] bytes = str.getBytes("UTF-8");
                WebSocketConnectionD08.this._outbound.addFrame((byte) 8, (byte) 1, bytes, 0, bytes.length);
                WebSocketConnectionD08.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionD08.this._closeCode + ":" + WebSocketConnectionD08.this._closeMessage);
        }

        public void sendMessage(byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionD08.this._closedOut) {
                WebSocketConnectionD08.this._outbound.addFrame((byte) 8, (byte) 2, bArr, i, i2);
                WebSocketConnectionD08.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionD08.this._closeCode + ":" + WebSocketConnectionD08.this._closeMessage);
        }

        public void sendFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionD08.this._closedOut) {
                WebSocketConnectionD08.this._outbound.addFrame(b, b2, bArr, i, i2);
                WebSocketConnectionD08.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionD08.this._closeCode + ":" + WebSocketConnectionD08.this._closeMessage);
        }

        public void sendControl(byte b, byte[] bArr, int i, int i2) throws IOException {
            if (!WebSocketConnectionD08.this._closedOut) {
                WebSocketConnectionD08.this._outbound.addFrame((byte) 8, b, bArr, i, i2);
                WebSocketConnectionD08.this.checkWriteable();
                return;
            }
            throw new IOException("closedOut " + WebSocketConnectionD08.this._closeCode + ":" + WebSocketConnectionD08.this._closeMessage);
        }

        public boolean isMessageComplete(byte b) {
            return WebSocketConnectionD08.isLastFrame(b);
        }

        public boolean isOpen() {
            return WebSocketConnectionD08.this._endp != null && WebSocketConnectionD08.this._endp.isOpen();
        }

        public void close(int i, String str) {
            if (!this._disconnecting) {
                this._disconnecting = true;
                WebSocketConnectionD08.this.closeOut(i, str);
            }
        }

        public void setMaxIdleTime(int i) {
            try {
                WebSocketConnectionD08.this._endp.setMaxIdleTime(i);
            } catch (IOException e) {
                WebSocketConnectionD08.LOG.warn(e);
            }
        }

        public void setMaxTextMessageSize(int i) {
            int unused = WebSocketConnectionD08.this._maxTextMessageSize = i;
        }

        public void setMaxBinaryMessageSize(int i) {
            int unused = WebSocketConnectionD08.this._maxBinaryMessageSize = i;
        }

        public int getMaxIdleTime() {
            return WebSocketConnectionD08.this._endp.getMaxIdleTime();
        }

        public int getMaxTextMessageSize() {
            return WebSocketConnectionD08.this._maxTextMessageSize;
        }

        public int getMaxBinaryMessageSize() {
            return WebSocketConnectionD08.this._maxBinaryMessageSize;
        }

        public String getProtocol() {
            return WebSocketConnectionD08.this._protocol;
        }

        public boolean isControl(byte b) {
            return WebSocketConnectionD08.isControlFrame(b);
        }

        public void disconnect() {
            close(1000, (String) null);
        }

        public void close() {
            close(1000, (String) null);
        }

        public void setAllowFrameFragmentation(boolean z) {
            WebSocketConnectionD08.this._parser.setFakeFragments(z);
        }

        public boolean isAllowFrameFragmentation() {
            return WebSocketConnectionD08.this._parser.isFakeFragments();
        }

        public String toString() {
            return String.format("%s[D08]@%x l(%s:%d)<->r(%s:%d)", new Object[]{getClass().getSimpleName(), Integer.valueOf(hashCode()), WebSocketConnectionD08.this._endp.getLocalAddr(), Integer.valueOf(WebSocketConnectionD08.this._endp.getLocalPort()), WebSocketConnectionD08.this._endp.getRemoteAddr(), Integer.valueOf(WebSocketConnectionD08.this._endp.getRemotePort())});
        }
    }

    private class WSFrameHandler implements WebSocketParser.FrameHandler {
        private ByteArrayBuffer _aggregate;
        private byte _opcode;
        private final Utf8StringBuilder _utf8;

        private WSFrameHandler() {
            this._utf8 = new Utf8StringBuilder();
            this._opcode = -1;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:100:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:101:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:103:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:104:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:105:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:109:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
            r10 = r14.array();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:113:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2100(r11.this$0) == null) goto L_0x0039;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2100(r11.this$0).onFrame(r12, r13, r10, r14.getIndex(), r14.length()) == false) goto L_0x0039;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0038, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2200(r11.this$0) == null) goto L_0x005c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0045, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.isControlFrame(r13) == false) goto L_0x005c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0059, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2200(r11.this$0).onControl(r13, r10, r14.getIndex(), r14.length()) == false) goto L_0x005c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x005b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
            if (r13 == 0) goto L_0x01dc;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0060, code lost:
            if (r13 == 1) goto L_0x0156;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0062, code lost:
            switch(r13) {
                case 8: goto L_0x0113;
                case 9: goto L_0x00e5;
                case 10: goto L_0x00d6;
                default: goto L_0x0065;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x006b, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2500(r11.this$0) == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0075, code lost:
            if (checkBinaryMessageSize(0, r14.length()) == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0077, code lost:
            if (r0 == false) goto L_0x008c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0079, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2500(r11.this$0).onMessage(r10, r14.getIndex(), r14.length());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0096, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).getMaxBinaryMessageSize() < 0) goto L_0x00b6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x0098, code lost:
            r11._opcode = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x009c, code lost:
            if (r11._aggregate != null) goto L_0x00af;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x009e, code lost:
            r11._aggregate = new org.eclipse.jetty.p119io.ByteArrayBuffer(org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).getMaxBinaryMessageSize());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00af, code lost:
            r11._aggregate.put(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b6, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$1000().warn("Frame discarded. Binary aggregation disabed for {}", org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2700(r11.this$0));
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).close(1003, "Binary frame aggregation disabled");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00d6, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$1000().debug("PONG {}", r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e5, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$1000().debug("PING {}", r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f8, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$200(r11.this$0) != false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00fa, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).sendControl((byte) 10, r14.array(), r14.getIndex(), r14.length());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0113, code lost:
            r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.CLOSE_NOCODE;
            r13 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x011a, code lost:
            if (r14.length() < 2) goto L_0x014f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x011c, code lost:
            r12 = (r14.array()[r14.getIndex()] * 256) + r14.array()[r14.getIndex() + 1];
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0138, code lost:
            if (r14.length() <= 2) goto L_0x014f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x013a, code lost:
            r13 = new java.lang.String(r14.array(), r14.getIndex() + 2, r14.length() - 2, "UTF-8");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x014f, code lost:
            r11.this$0.closeIn(r12, r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x015c, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2300(r11.this$0) == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0168, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).getMaxTextMessageSize() > 0) goto L_0x019d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x016a, code lost:
            if (r0 == false) goto L_0x017d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x016c, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2300(r11.this$0).onMessage(r14.toString("UTF-8"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x017d, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$1000().warn("Frame discarded. Text aggregation disabled for {}", org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2600(r11.this$0));
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).close(1003, "Text frame aggregation disabled");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x01b9, code lost:
            if (r11._utf8.append(r14.array(), r14.getIndex(), r14.length(), org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).getMaxTextMessageSize()) == false) goto L_0x01d7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x01bb, code lost:
            if (r0 == false) goto L_0x01d3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x01bd, code lost:
            r12 = r11._utf8.toString();
            r11._utf8.reset();
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2300(r11.this$0).onMessage(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x01d3, code lost:
            r11._opcode = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x01d7, code lost:
            textMessageTooLarge();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x01e3, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2300(r11.this$0) == null) goto L_0x0223;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x01e7, code lost:
            if (r11._opcode != 1) goto L_0x0223;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x0205, code lost:
            if (r11._utf8.append(r14.array(), r14.getIndex(), r14.length(), org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).getMaxTextMessageSize()) == false) goto L_0x0220;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:0x0207, code lost:
            if (r0 == false) goto L_0x0223;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:0x0209, code lost:
            r11._opcode = -1;
            r12 = r11._utf8.toString();
            r11._utf8.reset();
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2300(r11.this$0).onMessage(r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x0220, code lost:
            textMessageTooLarge();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x0225, code lost:
            if (r11._opcode < 0) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x0231, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2400(r11.this$0).getMaxBinaryMessageSize() < 0) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:0x0241, code lost:
            if (checkBinaryMessageSize(r11._aggregate.length(), r14.length()) == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x0243, code lost:
            r11._aggregate.put(r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x0248, code lost:
            if (r0 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:0x0250, code lost:
            if (org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2500(r11.this$0) == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2500(r11.this$0).onMessage(r11._aggregate.array(), r11._aggregate.getIndex(), r11._aggregate.length());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
            r11._opcode = -1;
            r11._aggregate.clear();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:85:0x0275, code lost:
            r12 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:86:0x0276, code lost:
            r11._opcode = -1;
            r11._aggregate.clear();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:87:0x027d, code lost:
            throw r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:88:0x027e, code lost:
            r12 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x027f, code lost:
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$1000().warn("{} for {}", r12, org.eclipse.jetty.websocket.WebSocketConnectionD08.access$2800(r11.this$0), r12);
            org.eclipse.jetty.websocket.WebSocketConnectionD08.access$1000().debug(r12);
            errorClose(android.support.p000v4.view.PointerIconCompat.TYPE_COPY, "Internal Server Error: " + r12);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:97:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFrame(byte r12, byte r13, org.eclipse.jetty.p119io.Buffer r14) {
            /*
                r11 = this;
                boolean r0 = org.eclipse.jetty.websocket.WebSocketConnectionD08.isLastFrame(r12)
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r1 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this
                monitor-enter(r1)
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r2 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ all -> 0x02b5 }
                boolean r2 = r2._closedIn     // Catch:{ all -> 0x02b5 }
                if (r2 == 0) goto L_0x0011
                monitor-exit(r1)     // Catch:{ all -> 0x02b5 }
                return
            L_0x0011:
                monitor-exit(r1)     // Catch:{ all -> 0x02b5 }
                r1 = 2
                r2 = 0
                r3 = 1
                byte[] r10 = r14.array()     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r4 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnFrame r4 = r4._onFrame     // Catch:{ Throwable -> 0x027e }
                if (r4 == 0) goto L_0x0039
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r4 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnFrame r4 = r4._onFrame     // Catch:{ Throwable -> 0x027e }
                int r8 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r9 = r14.length()     // Catch:{ Throwable -> 0x027e }
                r5 = r12
                r6 = r13
                r7 = r10
                boolean r12 = r4.onFrame(r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x0039
                return
            L_0x0039:
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnControl r12 = r12._onControl     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x005c
                boolean r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.isControlFrame(r13)     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x005c
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnControl r12 = r12._onControl     // Catch:{ Throwable -> 0x027e }
                int r4 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r5 = r14.length()     // Catch:{ Throwable -> 0x027e }
                boolean r12 = r12.onControl(r13, r10, r4, r5)     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x005c
                return
            L_0x005c:
                if (r13 == 0) goto L_0x01dc
                r12 = 1003(0x3eb, float:1.406E-42)
                if (r13 == r3) goto L_0x0156
                switch(r13) {
                    case 8: goto L_0x0113;
                    case 9: goto L_0x00e5;
                    case 10: goto L_0x00d6;
                    default: goto L_0x0065;
                }     // Catch:{ Throwable -> 0x027e }
            L_0x0065:
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r4 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r4 = r4._onBinaryMessage     // Catch:{ Throwable -> 0x027e }
                if (r4 == 0) goto L_0x02b4
                int r4 = r14.length()     // Catch:{ Throwable -> 0x027e }
                boolean r4 = r11.checkBinaryMessageSize(r2, r4)     // Catch:{ Throwable -> 0x027e }
                if (r4 == 0) goto L_0x02b4
                if (r0 == 0) goto L_0x008c
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r12 = r12._onBinaryMessage     // Catch:{ Throwable -> 0x027e }
                int r13 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r14 = r14.length()     // Catch:{ Throwable -> 0x027e }
                r12.onMessage(r10, r13, r14)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x008c:
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r0 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r0 = r0._connection     // Catch:{ Throwable -> 0x027e }
                int r0 = r0.getMaxBinaryMessageSize()     // Catch:{ Throwable -> 0x027e }
                if (r0 < 0) goto L_0x00b6
                r11._opcode = r13     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.io.ByteArrayBuffer r12 = r11._aggregate     // Catch:{ Throwable -> 0x027e }
                if (r12 != 0) goto L_0x00af
                org.eclipse.jetty.io.ByteArrayBuffer r12 = new org.eclipse.jetty.io.ByteArrayBuffer     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r13 = r13._connection     // Catch:{ Throwable -> 0x027e }
                int r13 = r13.getMaxBinaryMessageSize()     // Catch:{ Throwable -> 0x027e }
                r12.<init>((int) r13)     // Catch:{ Throwable -> 0x027e }
                r11._aggregate = r12     // Catch:{ Throwable -> 0x027e }
            L_0x00af:
                org.eclipse.jetty.io.ByteArrayBuffer r12 = r11._aggregate     // Catch:{ Throwable -> 0x027e }
                r12.put((org.eclipse.jetty.p119io.Buffer) r14)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x00b6:
                org.eclipse.jetty.util.log.Logger r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.LOG     // Catch:{ Throwable -> 0x027e }
                java.lang.String r14 = "Frame discarded. Binary aggregation disabed for {}"
                java.lang.Object[] r0 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r4 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.io.EndPoint r4 = r4._endp     // Catch:{ Throwable -> 0x027e }
                r0[r2] = r4     // Catch:{ Throwable -> 0x027e }
                r13.warn((java.lang.String) r14, (java.lang.Object[]) r0)     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r13 = r13._connection     // Catch:{ Throwable -> 0x027e }
                java.lang.String r14 = "Binary frame aggregation disabled"
                r13.close(r12, r14)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x00d6:
                org.eclipse.jetty.util.log.Logger r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.LOG     // Catch:{ Throwable -> 0x027e }
                java.lang.String r13 = "PONG {}"
                java.lang.Object[] r14 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x027e }
                r14[r2] = r11     // Catch:{ Throwable -> 0x027e }
                r12.debug((java.lang.String) r13, (java.lang.Object[]) r14)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x00e5:
                org.eclipse.jetty.util.log.Logger r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.LOG     // Catch:{ Throwable -> 0x027e }
                java.lang.String r13 = "PING {}"
                java.lang.Object[] r0 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x027e }
                r0[r2] = r11     // Catch:{ Throwable -> 0x027e }
                r12.debug((java.lang.String) r13, (java.lang.Object[]) r0)     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                boolean r12 = r12._closedOut     // Catch:{ Throwable -> 0x027e }
                if (r12 != 0) goto L_0x02b4
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r12 = r12._connection     // Catch:{ Throwable -> 0x027e }
                r13 = 10
                byte[] r0 = r14.array()     // Catch:{ Throwable -> 0x027e }
                int r4 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r14 = r14.length()     // Catch:{ Throwable -> 0x027e }
                r12.sendControl(r13, r0, r4, r14)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x0113:
                r12 = 1005(0x3ed, float:1.408E-42)
                r13 = 0
                int r0 = r14.length()     // Catch:{ Throwable -> 0x027e }
                if (r0 < r1) goto L_0x014f
                byte[] r12 = r14.array()     // Catch:{ Throwable -> 0x027e }
                int r0 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                byte r12 = r12[r0]     // Catch:{ Throwable -> 0x027e }
                int r12 = r12 * 256
                byte[] r0 = r14.array()     // Catch:{ Throwable -> 0x027e }
                int r4 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r4 = r4 + r3
                byte r0 = r0[r4]     // Catch:{ Throwable -> 0x027e }
                int r12 = r12 + r0
                int r0 = r14.length()     // Catch:{ Throwable -> 0x027e }
                if (r0 <= r1) goto L_0x014f
                java.lang.String r13 = new java.lang.String     // Catch:{ Throwable -> 0x027e }
                byte[] r0 = r14.array()     // Catch:{ Throwable -> 0x027e }
                int r4 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r4 = r4 + r1
                int r14 = r14.length()     // Catch:{ Throwable -> 0x027e }
                int r14 = r14 - r1
                java.lang.String r5 = "UTF-8"
                r13.<init>(r0, r4, r14, r5)     // Catch:{ Throwable -> 0x027e }
            L_0x014f:
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r14 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                r14.closeIn(r12, r13)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x0156:
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r13 = r13._onTextMessage     // Catch:{ Throwable -> 0x027e }
                if (r13 == 0) goto L_0x02b4
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r13 = r13._connection     // Catch:{ Throwable -> 0x027e }
                int r13 = r13.getMaxTextMessageSize()     // Catch:{ Throwable -> 0x027e }
                if (r13 > 0) goto L_0x019d
                if (r0 == 0) goto L_0x017d
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r12 = r12._onTextMessage     // Catch:{ Throwable -> 0x027e }
                java.lang.String r13 = "UTF-8"
                java.lang.String r13 = r14.toString((java.lang.String) r13)     // Catch:{ Throwable -> 0x027e }
                r12.onMessage(r13)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x017d:
                org.eclipse.jetty.util.log.Logger r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.LOG     // Catch:{ Throwable -> 0x027e }
                java.lang.String r14 = "Frame discarded. Text aggregation disabled for {}"
                java.lang.Object[] r0 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r4 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.io.EndPoint r4 = r4._endp     // Catch:{ Throwable -> 0x027e }
                r0[r2] = r4     // Catch:{ Throwable -> 0x027e }
                r13.warn((java.lang.String) r14, (java.lang.Object[]) r0)     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r13 = r13._connection     // Catch:{ Throwable -> 0x027e }
                java.lang.String r14 = "Text frame aggregation disabled"
                r13.close(r12, r14)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x019d:
                org.eclipse.jetty.util.Utf8StringBuilder r12 = r11._utf8     // Catch:{ Throwable -> 0x027e }
                byte[] r13 = r14.array()     // Catch:{ Throwable -> 0x027e }
                int r4 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r14 = r14.length()     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r5 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r5 = r5._connection     // Catch:{ Throwable -> 0x027e }
                int r5 = r5.getMaxTextMessageSize()     // Catch:{ Throwable -> 0x027e }
                boolean r12 = r12.append(r13, r4, r14, r5)     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x01d7
                if (r0 == 0) goto L_0x01d3
                org.eclipse.jetty.util.Utf8StringBuilder r12 = r11._utf8     // Catch:{ Throwable -> 0x027e }
                java.lang.String r12 = r12.toString()     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.util.Utf8StringBuilder r13 = r11._utf8     // Catch:{ Throwable -> 0x027e }
                r13.reset()     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r13 = r13._onTextMessage     // Catch:{ Throwable -> 0x027e }
                r13.onMessage(r12)     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x01d3:
                r11._opcode = r3     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x01d7:
                r11.textMessageTooLarge()     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x01dc:
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r12 = r12._onTextMessage     // Catch:{ Throwable -> 0x027e }
                r13 = -1
                if (r12 == 0) goto L_0x0223
                byte r12 = r11._opcode     // Catch:{ Throwable -> 0x027e }
                if (r12 != r3) goto L_0x0223
                org.eclipse.jetty.util.Utf8StringBuilder r12 = r11._utf8     // Catch:{ Throwable -> 0x027e }
                byte[] r4 = r14.array()     // Catch:{ Throwable -> 0x027e }
                int r5 = r14.getIndex()     // Catch:{ Throwable -> 0x027e }
                int r6 = r14.length()     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r7 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r7 = r7._connection     // Catch:{ Throwable -> 0x027e }
                int r7 = r7.getMaxTextMessageSize()     // Catch:{ Throwable -> 0x027e }
                boolean r12 = r12.append(r4, r5, r6, r7)     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x0220
                if (r0 == 0) goto L_0x0223
                r11._opcode = r13     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.util.Utf8StringBuilder r12 = r11._utf8     // Catch:{ Throwable -> 0x027e }
                java.lang.String r12 = r12.toString()     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.util.Utf8StringBuilder r4 = r11._utf8     // Catch:{ Throwable -> 0x027e }
                r4.reset()     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r4 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnTextMessage r4 = r4._onTextMessage     // Catch:{ Throwable -> 0x027e }
                r4.onMessage(r12)     // Catch:{ Throwable -> 0x027e }
                goto L_0x0223
            L_0x0220:
                r11.textMessageTooLarge()     // Catch:{ Throwable -> 0x027e }
            L_0x0223:
                byte r12 = r11._opcode     // Catch:{ Throwable -> 0x027e }
                if (r12 < 0) goto L_0x02b4
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$FrameConnection r12 = r12._connection     // Catch:{ Throwable -> 0x027e }
                int r12 = r12.getMaxBinaryMessageSize()     // Catch:{ Throwable -> 0x027e }
                if (r12 < 0) goto L_0x02b4
                org.eclipse.jetty.io.ByteArrayBuffer r12 = r11._aggregate     // Catch:{ Throwable -> 0x027e }
                int r12 = r12.length()     // Catch:{ Throwable -> 0x027e }
                int r4 = r14.length()     // Catch:{ Throwable -> 0x027e }
                boolean r12 = r11.checkBinaryMessageSize(r12, r4)     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x02b4
                org.eclipse.jetty.io.ByteArrayBuffer r12 = r11._aggregate     // Catch:{ Throwable -> 0x027e }
                r12.put((org.eclipse.jetty.p119io.Buffer) r14)     // Catch:{ Throwable -> 0x027e }
                if (r0 == 0) goto L_0x02b4
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r12 = r12._onBinaryMessage     // Catch:{ Throwable -> 0x027e }
                if (r12 == 0) goto L_0x02b4
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r12 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this     // Catch:{ all -> 0x0275 }
                org.eclipse.jetty.websocket.WebSocket$OnBinaryMessage r12 = r12._onBinaryMessage     // Catch:{ all -> 0x0275 }
                org.eclipse.jetty.io.ByteArrayBuffer r14 = r11._aggregate     // Catch:{ all -> 0x0275 }
                byte[] r14 = r14.array()     // Catch:{ all -> 0x0275 }
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r11._aggregate     // Catch:{ all -> 0x0275 }
                int r0 = r0.getIndex()     // Catch:{ all -> 0x0275 }
                org.eclipse.jetty.io.ByteArrayBuffer r4 = r11._aggregate     // Catch:{ all -> 0x0275 }
                int r4 = r4.length()     // Catch:{ all -> 0x0275 }
                r12.onMessage(r14, r0, r4)     // Catch:{ all -> 0x0275 }
                r11._opcode = r13     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.io.ByteArrayBuffer r12 = r11._aggregate     // Catch:{ Throwable -> 0x027e }
                r12.clear()     // Catch:{ Throwable -> 0x027e }
                goto L_0x02b4
            L_0x0275:
                r12 = move-exception
                r11._opcode = r13     // Catch:{ Throwable -> 0x027e }
                org.eclipse.jetty.io.ByteArrayBuffer r13 = r11._aggregate     // Catch:{ Throwable -> 0x027e }
                r13.clear()     // Catch:{ Throwable -> 0x027e }
                throw r12     // Catch:{ Throwable -> 0x027e }
            L_0x027e:
                r12 = move-exception
                org.eclipse.jetty.util.log.Logger r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.LOG
                r14 = 3
                java.lang.Object[] r14 = new java.lang.Object[r14]
                r14[r2] = r12
                org.eclipse.jetty.websocket.WebSocketConnectionD08 r0 = org.eclipse.jetty.websocket.WebSocketConnectionD08.this
                org.eclipse.jetty.io.EndPoint r0 = r0._endp
                r14[r3] = r0
                r14[r1] = r12
                java.lang.String r0 = "{} for {}"
                r13.warn((java.lang.String) r0, (java.lang.Object[]) r14)
                org.eclipse.jetty.util.log.Logger r13 = org.eclipse.jetty.websocket.WebSocketConnectionD08.LOG
                r13.debug(r12)
                r13 = 1011(0x3f3, float:1.417E-42)
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                java.lang.String r0 = "Internal Server Error: "
                r14.append(r0)
                r14.append(r12)
                java.lang.String r12 = r14.toString()
                r11.errorClose(r13, r12)
            L_0x02b4:
                return
            L_0x02b5:
                r12 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x02b5 }
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketConnectionD08.WSFrameHandler.onFrame(byte, byte, org.eclipse.jetty.io.Buffer):void");
        }

        private void errorClose(int i, String str) {
            WebSocketConnectionD08.this._connection.close(i, str);
            try {
                WebSocketConnectionD08.this._endp.close();
            } catch (IOException e) {
                WebSocketConnectionD08.LOG.warn(e.toString(), new Object[0]);
                WebSocketConnectionD08.LOG.debug(e);
            }
        }

        private boolean checkBinaryMessageSize(int i, int i2) {
            int maxBinaryMessageSize = WebSocketConnectionD08.this._connection.getMaxBinaryMessageSize();
            if (maxBinaryMessageSize <= 0 || i + i2 <= maxBinaryMessageSize) {
                return true;
            }
            WebSocketConnectionD08.LOG.warn("Binary message too large > {}B for {}", Integer.valueOf(WebSocketConnectionD08.this._connection.getMaxBinaryMessageSize()), WebSocketConnectionD08.this._endp);
            WebSocket.FrameConnection access$2400 = WebSocketConnectionD08.this._connection;
            access$2400.close(1003, "Message size > " + WebSocketConnectionD08.this._connection.getMaxBinaryMessageSize());
            this._opcode = -1;
            ByteArrayBuffer byteArrayBuffer = this._aggregate;
            if (byteArrayBuffer != null) {
                byteArrayBuffer.clear();
            }
            return false;
        }

        private void textMessageTooLarge() {
            WebSocketConnectionD08.LOG.warn("Text message too large > {} chars for {}", Integer.valueOf(WebSocketConnectionD08.this._connection.getMaxTextMessageSize()), WebSocketConnectionD08.this._endp);
            WebSocket.FrameConnection access$2400 = WebSocketConnectionD08.this._connection;
            access$2400.close(1003, "Text message size > " + WebSocketConnectionD08.this._connection.getMaxTextMessageSize() + " chars");
            this._opcode = -1;
            this._utf8.reset();
        }

        public void close(int i, String str) {
            if (i != 1000) {
                Logger access$1000 = WebSocketConnectionD08.LOG;
                access$1000.warn("Close: " + i + " " + str, new Object[0]);
            }
            WebSocketConnectionD08.this._connection.close(i, str);
        }

        public String toString() {
            return WebSocketConnectionD08.this.toString() + "FH";
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
        return String.format("WS/D%d p=%s g=%s", new Object[]{Integer.valueOf(this._draft), this._parser, this._generator});
    }
}
