package org.eclipse.jetty.websocket;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import org.eclipse.jetty.p119io.AbstractConnection;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.nio.IndirectNIOBuffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketParser;

public class WebSocketConnectionD00 extends AbstractConnection implements WebSocketConnection, WebSocket.FrameConnection {
    public static final byte LENGTH_FRAME = Byte.MIN_VALUE;
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) WebSocketConnectionD00.class);
    public static final byte SENTINEL_FRAME = 0;
    private final WebSocketGenerator _generator;
    private ByteArrayBuffer _hixieBytes;
    private String _key1;
    private String _key2;
    private final WebSocketParser _parser;
    private final String _protocol;
    private final WebSocket _websocket;

    public byte binaryOpcode() {
        return Byte.MIN_VALUE;
    }

    public byte continuationOpcode() {
        return 0;
    }

    public byte finMask() {
        return 0;
    }

    public WebSocket.Connection getConnection() {
        return this;
    }

    public int getMaxBinaryMessageSize() {
        return -1;
    }

    public int getMaxTextMessageSize() {
        return -1;
    }

    public boolean isAllowFrameFragmentation() {
        return false;
    }

    public boolean isBinary(byte b) {
        return (b & Byte.MIN_VALUE) != 0;
    }

    public boolean isClose(byte b) {
        return false;
    }

    public boolean isContinuation(byte b) {
        return false;
    }

    public boolean isControl(byte b) {
        return false;
    }

    public boolean isMessageComplete(byte b) {
        return true;
    }

    public boolean isMore(byte b) {
        return (b & 8) != 0;
    }

    public boolean isPing(byte b) {
        return false;
    }

    public boolean isPong(byte b) {
        return false;
    }

    public boolean isSuspended() {
        return false;
    }

    public boolean isText(byte b) {
        return (b & Byte.MIN_VALUE) == 0;
    }

    public void onInputShutdown() throws IOException {
    }

    public void sendControl(byte b, byte[] bArr, int i, int i2) throws IOException {
    }

    public void setAllowFrameFragmentation(boolean z) {
    }

    public void setMaxBinaryMessageSize(int i) {
    }

    public void setMaxTextMessageSize(int i) {
    }

    public byte textOpcode() {
        return 0;
    }

    public WebSocketConnectionD00(WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str) throws IOException {
        super(endPoint, j);
        this._endp.setMaxIdleTime(i);
        this._websocket = webSocket;
        this._protocol = str;
        this._generator = new WebSocketGeneratorD00(webSocketBuffers, this._endp);
        this._parser = new WebSocketParserD00(webSocketBuffers, endPoint, new FrameHandlerD00(this._websocket));
    }

    public void setHixieKeys(String str, String str2) {
        this._key1 = str;
        this._key2 = str2;
        this._hixieBytes = new IndirectNIOBuffer(16);
    }

    /* JADX WARNING: Removed duplicated region for block: B:96:0x00d2 A[LOOP:2: B:47:0x00d2->B:96:0x00d2, LOOP_END, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x00d1 A[LOOP:1: B:46:0x00d1->B:97:0x00d1, LOOP_END, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.p119io.Connection handle() throws java.io.IOException {
        /*
            r5 = this;
            org.eclipse.jetty.io.ByteArrayBuffer r0 = r5._hixieBytes     // Catch:{ IOException -> 0x0125 }
            if (r0 == 0) goto L_0x00d0
            org.eclipse.jetty.websocket.WebSocketParser r0 = r5._parser     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.Buffer r0 = r0.getBuffer()     // Catch:{ IOException -> 0x0125 }
            r1 = 8
            if (r0 == 0) goto L_0x003a
            int r2 = r0.length()     // Catch:{ IOException -> 0x0125 }
            if (r2 <= 0) goto L_0x003a
            int r2 = r0.length()     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.ByteArrayBuffer r3 = r5._hixieBytes     // Catch:{ IOException -> 0x0125 }
            int r3 = r3.length()     // Catch:{ IOException -> 0x0125 }
            int r3 = 8 - r3
            if (r2 <= r3) goto L_0x002a
            org.eclipse.jetty.io.ByteArrayBuffer r2 = r5._hixieBytes     // Catch:{ IOException -> 0x0125 }
            int r2 = r2.length()     // Catch:{ IOException -> 0x0125 }
            int r2 = 8 - r2
        L_0x002a:
            org.eclipse.jetty.io.ByteArrayBuffer r3 = r5._hixieBytes     // Catch:{ IOException -> 0x0125 }
            int r4 = r0.getIndex()     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.Buffer r4 = r0.peek(r4, r2)     // Catch:{ IOException -> 0x0125 }
            r3.put((org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ IOException -> 0x0125 }
            r0.skip(r2)     // Catch:{ IOException -> 0x0125 }
        L_0x003a:
            org.eclipse.jetty.io.EndPoint r0 = r5._endp     // Catch:{ IOException -> 0x0125 }
            boolean r0 = r0.isOpen()     // Catch:{ IOException -> 0x0125 }
            if (r0 == 0) goto L_0x0099
            org.eclipse.jetty.io.ByteArrayBuffer r0 = r5._hixieBytes     // Catch:{ IOException -> 0x0125 }
            int r0 = r0.length()     // Catch:{ IOException -> 0x0125 }
            if (r0 != r1) goto L_0x005d
            r5.doTheHixieHixieShake()     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.EndPoint r0 = r5._endp     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.ByteArrayBuffer r1 = r5._hixieBytes     // Catch:{ IOException -> 0x0125 }
            r0.flush(r1)     // Catch:{ IOException -> 0x0125 }
            r0 = 0
            r5._hixieBytes = r0     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.EndPoint r0 = r5._endp     // Catch:{ IOException -> 0x0125 }
            r0.flush()     // Catch:{ IOException -> 0x0125 }
            goto L_0x0099
        L_0x005d:
            org.eclipse.jetty.io.EndPoint r0 = r5._endp     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.ByteArrayBuffer r2 = r5._hixieBytes     // Catch:{ IOException -> 0x0125 }
            int r0 = r0.fill(r2)     // Catch:{ IOException -> 0x0125 }
            if (r0 >= 0) goto L_0x0072
            org.eclipse.jetty.io.EndPoint r0 = r5._endp     // Catch:{ IOException -> 0x0125 }
            r0.flush()     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.EndPoint r0 = r5._endp     // Catch:{ IOException -> 0x0125 }
            r0.close()     // Catch:{ IOException -> 0x0125 }
            goto L_0x0099
        L_0x0072:
            if (r0 != 0) goto L_0x003a
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x0098
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            boolean r0 = r0.isInputShutdown()
            if (r0 == 0) goto L_0x0092
            org.eclipse.jetty.websocket.WebSocketGenerator r0 = r5._generator
            boolean r0 = r0.isBufferEmpty()
            if (r0 == 0) goto L_0x0092
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            r0.close()
            goto L_0x0095
        L_0x0092:
            r5.checkWriteable()
        L_0x0095:
            r5.checkWriteable()
        L_0x0098:
            return r5
        L_0x0099:
            org.eclipse.jetty.websocket.WebSocket r0 = r5._websocket     // Catch:{ IOException -> 0x0125 }
            boolean r0 = r0 instanceof org.eclipse.jetty.websocket.WebSocket.OnFrame     // Catch:{ IOException -> 0x0125 }
            if (r0 == 0) goto L_0x00a6
            org.eclipse.jetty.websocket.WebSocket r0 = r5._websocket     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.websocket.WebSocket$OnFrame r0 = (org.eclipse.jetty.websocket.WebSocket.OnFrame) r0     // Catch:{ IOException -> 0x0125 }
            r0.onHandshake(r5)     // Catch:{ IOException -> 0x0125 }
        L_0x00a6:
            org.eclipse.jetty.websocket.WebSocket r0 = r5._websocket     // Catch:{ IOException -> 0x0125 }
            r0.onOpen(r5)     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x00cf
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            boolean r0 = r0.isInputShutdown()
            if (r0 == 0) goto L_0x00c9
            org.eclipse.jetty.websocket.WebSocketGenerator r0 = r5._generator
            boolean r0 = r0.isBufferEmpty()
            if (r0 == 0) goto L_0x00c9
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            r0.close()
            goto L_0x00cc
        L_0x00c9:
            r5.checkWriteable()
        L_0x00cc:
            r5.checkWriteable()
        L_0x00cf:
            return r5
        L_0x00d0:
            r0 = 1
        L_0x00d1:
            r1 = 1
        L_0x00d2:
            if (r1 == 0) goto L_0x00fe
            org.eclipse.jetty.websocket.WebSocketGenerator r1 = r5._generator     // Catch:{ IOException -> 0x0125 }
            int r1 = r1.flush()     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.websocket.WebSocketParser r2 = r5._parser     // Catch:{ IOException -> 0x0125 }
            int r2 = r2.parseNext()     // Catch:{ IOException -> 0x0125 }
            if (r1 > 0) goto L_0x00e7
            if (r2 <= 0) goto L_0x00e5
            goto L_0x00e7
        L_0x00e5:
            r1 = 0
            goto L_0x00e8
        L_0x00e7:
            r1 = 1
        L_0x00e8:
            org.eclipse.jetty.io.EndPoint r2 = r5._endp     // Catch:{ IOException -> 0x0125 }
            r2.flush()     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.EndPoint r2 = r5._endp     // Catch:{ IOException -> 0x0125 }
            boolean r2 = r2 instanceof org.eclipse.jetty.p119io.AsyncEndPoint     // Catch:{ IOException -> 0x0125 }
            if (r2 == 0) goto L_0x00d2
            org.eclipse.jetty.io.EndPoint r2 = r5._endp     // Catch:{ IOException -> 0x0125 }
            org.eclipse.jetty.io.AsyncEndPoint r2 = (org.eclipse.jetty.p119io.AsyncEndPoint) r2     // Catch:{ IOException -> 0x0125 }
            boolean r2 = r2.hasProgressed()     // Catch:{ IOException -> 0x0125 }
            if (r2 == 0) goto L_0x00d2
            goto L_0x00d1
        L_0x00fe:
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x0122
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            boolean r0 = r0.isInputShutdown()
            if (r0 == 0) goto L_0x011c
            org.eclipse.jetty.websocket.WebSocketGenerator r0 = r5._generator
            boolean r0 = r0.isBufferEmpty()
            if (r0 == 0) goto L_0x011c
            org.eclipse.jetty.io.EndPoint r0 = r5._endp
            r0.close()
            goto L_0x011f
        L_0x011c:
            r5.checkWriteable()
        L_0x011f:
            r5.checkWriteable()
        L_0x0122:
            return r5
        L_0x0123:
            r0 = move-exception
            goto L_0x0140
        L_0x0125:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r1 = LOG     // Catch:{ all -> 0x0123 }
            r1.debug(r0)     // Catch:{ all -> 0x0123 }
            org.eclipse.jetty.io.EndPoint r1 = r5._endp     // Catch:{ IOException -> 0x0139 }
            boolean r1 = r1.isOpen()     // Catch:{ IOException -> 0x0139 }
            if (r1 == 0) goto L_0x013f
            org.eclipse.jetty.io.EndPoint r1 = r5._endp     // Catch:{ IOException -> 0x0139 }
            r1.close()     // Catch:{ IOException -> 0x0139 }
            goto L_0x013f
        L_0x0139:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ all -> 0x0123 }
            r2.ignore(r1)     // Catch:{ all -> 0x0123 }
        L_0x013f:
            throw r0     // Catch:{ all -> 0x0123 }
        L_0x0140:
            org.eclipse.jetty.io.EndPoint r1 = r5._endp
            boolean r1 = r1.isOpen()
            if (r1 == 0) goto L_0x0164
            org.eclipse.jetty.io.EndPoint r1 = r5._endp
            boolean r1 = r1.isInputShutdown()
            if (r1 == 0) goto L_0x015e
            org.eclipse.jetty.websocket.WebSocketGenerator r1 = r5._generator
            boolean r1 = r1.isBufferEmpty()
            if (r1 == 0) goto L_0x015e
            org.eclipse.jetty.io.EndPoint r1 = r5._endp
            r1.close()
            goto L_0x0161
        L_0x015e:
            r5.checkWriteable()
        L_0x0161:
            r5.checkWriteable()
        L_0x0164:
            goto L_0x0166
        L_0x0165:
            throw r0
        L_0x0166:
            goto L_0x0165
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketConnectionD00.handle():org.eclipse.jetty.io.Connection");
    }

    private void doTheHixieHixieShake() {
        byte[] doTheHixieHixieShake = doTheHixieHixieShake(hixieCrypt(this._key1), hixieCrypt(this._key2), this._hixieBytes.asArray());
        this._hixieBytes.clear();
        this._hixieBytes.put(doTheHixieHixieShake);
    }

    public boolean isOpen() {
        return this._endp != null && this._endp.isOpen();
    }

    public boolean isIdle() {
        return this._parser.isBufferEmpty() && this._generator.isBufferEmpty();
    }

    public void onClose() {
        this._websocket.onClose(1000, "");
    }

    public void sendMessage(String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        this._generator.addFrame((byte) 0, (byte) 0, bytes, 0, bytes.length);
        this._generator.flush();
        checkWriteable();
    }

    public void sendMessage(byte[] bArr, int i, int i2) throws IOException {
        this._generator.addFrame((byte) 0, Byte.MIN_VALUE, bArr, i, i2);
        this._generator.flush();
        checkWriteable();
    }

    public void sendFrame(byte b, byte b2, byte[] bArr, int i, int i2) throws IOException {
        this._generator.addFrame((byte) 0, b2, bArr, i, i2);
        this._generator.flush();
        checkWriteable();
    }

    public void close(int i, String str) {
        throw new UnsupportedOperationException();
    }

    public void disconnect() {
        close();
    }

    public void close() {
        try {
            this._generator.flush();
            this._endp.close();
        } catch (IOException e) {
            LOG.ignore(e);
        }
    }

    public void shutdown() {
        close();
    }

    public void fillBuffersFrom(Buffer buffer) {
        this._parser.fill(buffer);
    }

    private void checkWriteable() {
        if (!this._generator.isBufferEmpty() && (this._endp instanceof AsyncEndPoint)) {
            ((AsyncEndPoint) this._endp).scheduleWrite();
        }
    }

    static long hixieCrypt(String str) {
        long j = 0;
        int i = 0;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                j = (j * 10) + ((long) (c - '0'));
            } else if (c == ' ') {
                i++;
            }
        }
        return j / ((long) i);
    }

    public static byte[] doTheHixieHixieShake(long j, long j2, byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] bArr2 = new byte[16];
            bArr2[0] = (byte) ((int) ((j >> 24) & 255));
            bArr2[1] = (byte) ((int) ((j >> 16) & 255));
            bArr2[2] = (byte) ((int) ((j >> 8) & 255));
            bArr2[3] = (byte) ((int) (j & 255));
            bArr2[4] = (byte) ((int) ((j2 >> 24) & 255));
            bArr2[5] = (byte) ((int) ((j2 >> 16) & 255));
            bArr2[6] = (byte) ((int) ((j2 >> 8) & 255));
            bArr2[7] = (byte) ((int) (j2 & 255));
            System.arraycopy(bArr, 0, bArr2, 8, 8);
            instance.update(bArr2);
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public void setMaxIdleTime(int i) {
        try {
            this._endp.setMaxIdleTime(i);
        } catch (IOException e) {
            LOG.warn(e);
        }
    }

    public int getMaxIdleTime() {
        return this._endp.getMaxIdleTime();
    }

    public String getProtocol() {
        return this._protocol;
    }

    /* access modifiers changed from: protected */
    public void onFrameHandshake() {
        WebSocket webSocket = this._websocket;
        if (webSocket instanceof WebSocket.OnFrame) {
            ((WebSocket.OnFrame) webSocket).onHandshake(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onWebsocketOpen() {
        this._websocket.onOpen(this);
    }

    static class FrameHandlerD00 implements WebSocketParser.FrameHandler {
        final WebSocket _websocket;

        public void close(int i, String str) {
        }

        FrameHandlerD00(WebSocket webSocket) {
            this._websocket = webSocket;
        }

        public void onFrame(byte b, byte b2, Buffer buffer) {
            try {
                byte[] array = buffer.array();
                if (b2 == 0) {
                    if (this._websocket instanceof WebSocket.OnTextMessage) {
                        ((WebSocket.OnTextMessage) this._websocket).onMessage(buffer.toString("UTF-8"));
                    }
                } else if (this._websocket instanceof WebSocket.OnBinaryMessage) {
                    ((WebSocket.OnBinaryMessage) this._websocket).onMessage(array, buffer.getIndex(), buffer.length());
                }
            } catch (Throwable th) {
                WebSocketConnectionD00.LOG.warn(th);
            }
        }
    }

    public List<Extension> getExtensions() {
        return Collections.emptyList();
    }
}
