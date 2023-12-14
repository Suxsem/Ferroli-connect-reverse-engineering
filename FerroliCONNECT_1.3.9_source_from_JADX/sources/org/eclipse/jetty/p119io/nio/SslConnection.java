package org.eclipse.jetty.p119io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import kotlin.jvm.internal.LongCompanionObject;
import org.eclipse.jetty.p119io.AbstractConnection;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* renamed from: org.eclipse.jetty.io.nio.SslConnection */
public class SslConnection extends AbstractConnection implements AsyncConnection {
    private static final NIOBuffer __ZERO_BUFFER = new IndirectNIOBuffer(0);
    private static final ThreadLocal<SslBuffers> __buffers = new ThreadLocal<>();
    /* access modifiers changed from: private */
    public AsyncEndPoint _aEndp;
    private int _allocations;
    private boolean _allowRenegotiate;
    private SslBuffers _buffers;
    /* access modifiers changed from: private */
    public AsyncConnection _connection;
    /* access modifiers changed from: private */
    public final SSLEngine _engine;
    private boolean _handshook;
    /* access modifiers changed from: private */
    public NIOBuffer _inbound;
    /* access modifiers changed from: private */
    public boolean _ishut;
    /* access modifiers changed from: private */
    public final Logger _logger;
    /* access modifiers changed from: private */
    public boolean _oshut;
    /* access modifiers changed from: private */
    public NIOBuffer _outbound;
    /* access modifiers changed from: private */
    public final AtomicBoolean _progressed;
    /* access modifiers changed from: private */
    public final SSLSession _session;
    private final SslEndPoint _sslEndPoint;
    /* access modifiers changed from: private */
    public NIOBuffer _unwrapBuf;

    public boolean isIdle() {
        return false;
    }

    public boolean isSuspended() {
        return false;
    }

    public void onInputShutdown() throws IOException {
    }

    /* renamed from: org.eclipse.jetty.io.nio.SslConnection$SslBuffers */
    private static class SslBuffers {
        final NIOBuffer _in;
        final NIOBuffer _out;
        final NIOBuffer _unwrap;

        SslBuffers(int i, int i2) {
            this._in = new IndirectNIOBuffer(i);
            this._out = new IndirectNIOBuffer(i);
            this._unwrap = new IndirectNIOBuffer(i2);
        }
    }

    public SslConnection(SSLEngine sSLEngine, EndPoint endPoint) {
        this(sSLEngine, endPoint, System.currentTimeMillis());
    }

    public SslConnection(SSLEngine sSLEngine, EndPoint endPoint, long j) {
        super(endPoint, j);
        this._logger = Log.getLogger("org.eclipse.jetty.io.nio.ssl");
        this._allowRenegotiate = true;
        this._progressed = new AtomicBoolean();
        this._engine = sSLEngine;
        this._session = this._engine.getSession();
        this._aEndp = (AsyncEndPoint) endPoint;
        this._sslEndPoint = newSslEndPoint();
    }

    /* access modifiers changed from: protected */
    public SslEndPoint newSslEndPoint() {
        return new SslEndPoint();
    }

    public boolean isAllowRenegotiate() {
        return this._allowRenegotiate;
    }

    public void setAllowRenegotiate(boolean z) {
        this._allowRenegotiate = z;
    }

    private void allocateBuffers() {
        synchronized (this) {
            int i = this._allocations;
            this._allocations = i + 1;
            if (i == 0 && this._buffers == null) {
                this._buffers = __buffers.get();
                if (this._buffers == null) {
                    this._buffers = new SslBuffers(this._session.getPacketBufferSize() * 2, this._session.getApplicationBufferSize() * 2);
                }
                this._inbound = this._buffers._in;
                this._outbound = this._buffers._out;
                this._unwrapBuf = this._buffers._unwrap;
                __buffers.set((Object) null);
            }
        }
    }

    private void releaseBuffers() {
        synchronized (this) {
            int i = this._allocations - 1;
            this._allocations = i;
            if (i == 0 && this._buffers != null && this._inbound.length() == 0 && this._outbound.length() == 0 && this._unwrapBuf.length() == 0) {
                this._inbound = null;
                this._outbound = null;
                this._unwrapBuf = null;
                __buffers.set(this._buffers);
                this._buffers = null;
            }
        }
    }

    public Connection handle() throws IOException {
        try {
            allocateBuffers();
            boolean z = true;
            while (z) {
                z = this._engine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING ? process((Buffer) null, (Buffer) null) : false;
                AsyncConnection asyncConnection = (AsyncConnection) this._connection.handle();
                if (!(asyncConnection == this._connection || asyncConnection == null)) {
                    this._connection = asyncConnection;
                    z = true;
                }
                this._logger.debug("{} handle {} progress={}", this._session, this, Boolean.valueOf(z));
            }
            releaseBuffers();
            if (!this._ishut && this._sslEndPoint.isInputShutdown() && this._sslEndPoint.isOpen()) {
                this._ishut = true;
                try {
                    this._connection.onInputShutdown();
                } catch (Throwable th) {
                    this._logger.warn("onInputShutdown failed", th);
                    try {
                        this._sslEndPoint.close();
                    } catch (IOException e) {
                        this._logger.ignore(e);
                    }
                }
            }
            return this;
        } catch (Throwable th2) {
            this._logger.warn("onInputShutdown failed", th2);
            try {
                this._sslEndPoint.close();
            } catch (IOException e2) {
                this._logger.ignore(e2);
            }
        }
        throw th;
    }

    public void onClose() {
        Connection connection = this._sslEndPoint.getConnection();
        if (connection != null && connection != this) {
            connection.onClose();
        }
    }

    public void onIdleExpired(long j) {
        try {
            this._logger.debug("onIdleExpired {}ms on {}", Long.valueOf(j), this);
            if (this._endp.isOutputShutdown()) {
                this._sslEndPoint.close();
            } else {
                this._sslEndPoint.shutdownOutput();
            }
        } catch (IOException e) {
            this._logger.warn(e);
            super.onIdleExpired(j);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0218, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x012a, code lost:
        if (wrap(r2) != false) goto L_0x012c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0222  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x01a2 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x008e A[Catch:{ IOException -> 0x01a8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00e8 A[Catch:{ all -> 0x0219 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:18:0x0043=Splitter:B:18:0x0043, B:137:0x020d=Splitter:B:137:0x020d, B:146:0x021d=Splitter:B:146:0x021d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean process(org.eclipse.jetty.p119io.Buffer r17, org.eclipse.jetty.p119io.Buffer r18) throws java.io.IOException {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            r2 = r18
            monitor-enter(r16)
            r3 = 0
            r4 = 1
            r16.allocateBuffers()     // Catch:{ all -> 0x021b }
            if (r0 != 0) goto L_0x0016
            org.eclipse.jetty.io.nio.NIOBuffer r0 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            r0.compact()     // Catch:{ all -> 0x021b }
            org.eclipse.jetty.io.nio.NIOBuffer r0 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            goto L_0x0064
        L_0x0016:
            int r5 = r17.capacity()     // Catch:{ all -> 0x021b }
            javax.net.ssl.SSLSession r6 = r1._session     // Catch:{ all -> 0x021b }
            int r6 = r6.getApplicationBufferSize()     // Catch:{ all -> 0x021b }
            if (r5 >= r6) goto L_0x0048
            r5 = 0
            boolean r2 = r1.process(r5, r2)     // Catch:{ all -> 0x021b }
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            if (r5 == 0) goto L_0x0043
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            boolean r5 = r5.hasContent()     // Catch:{ all -> 0x021b }
            if (r5 == 0) goto L_0x0043
            org.eclipse.jetty.io.nio.NIOBuffer r2 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            int r0 = r0.put((org.eclipse.jetty.p119io.Buffer) r5)     // Catch:{ all -> 0x021b }
            r2.skip(r0)     // Catch:{ all -> 0x021b }
            r16.releaseBuffers()     // Catch:{ all -> 0x0228 }
            monitor-exit(r16)
            return r4
        L_0x0043:
            r16.releaseBuffers()     // Catch:{ all -> 0x0228 }
            monitor-exit(r16)
            return r2
        L_0x0048:
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            if (r5 == 0) goto L_0x0064
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            boolean r5 = r5.hasContent()     // Catch:{ all -> 0x021b }
            if (r5 == 0) goto L_0x0064
            org.eclipse.jetty.io.nio.NIOBuffer r2 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._unwrapBuf     // Catch:{ all -> 0x021b }
            int r0 = r0.put((org.eclipse.jetty.p119io.Buffer) r5)     // Catch:{ all -> 0x021b }
            r2.skip(r0)     // Catch:{ all -> 0x021b }
            r16.releaseBuffers()     // Catch:{ all -> 0x0228 }
            monitor-exit(r16)
            return r4
        L_0x0064:
            if (r2 != 0) goto L_0x0068
            org.eclipse.jetty.io.nio.NIOBuffer r2 = __ZERO_BUFFER     // Catch:{ all -> 0x021b }
        L_0x0068:
            r5 = 1
            r6 = 0
        L_0x006a:
            if (r5 == 0) goto L_0x01f4
            r7 = 7
            r9 = 4
            r10 = 3
            r11 = 2
            org.eclipse.jetty.io.nio.NIOBuffer r12 = r1._inbound     // Catch:{ IOException -> 0x01ad, all -> 0x01aa }
            int r12 = r12.space()     // Catch:{ IOException -> 0x01ad, all -> 0x01aa }
            if (r12 <= 0) goto L_0x0084
            org.eclipse.jetty.io.EndPoint r12 = r1._endp     // Catch:{ IOException -> 0x01ad, all -> 0x01aa }
            org.eclipse.jetty.io.nio.NIOBuffer r13 = r1._inbound     // Catch:{ IOException -> 0x01ad, all -> 0x01aa }
            int r12 = r12.fill(r13)     // Catch:{ IOException -> 0x01ad, all -> 0x01aa }
            if (r12 <= 0) goto L_0x0085
            r13 = 1
            goto L_0x0086
        L_0x0084:
            r12 = 0
        L_0x0085:
            r13 = 0
        L_0x0086:
            org.eclipse.jetty.io.nio.NIOBuffer r14 = r1._outbound     // Catch:{ IOException -> 0x01a8 }
            boolean r14 = r14.hasContent()     // Catch:{ IOException -> 0x01a8 }
            if (r14 == 0) goto L_0x009a
            org.eclipse.jetty.io.EndPoint r14 = r1._endp     // Catch:{ IOException -> 0x01a8 }
            org.eclipse.jetty.io.nio.NIOBuffer r15 = r1._outbound     // Catch:{ IOException -> 0x01a8 }
            int r14 = r14.flush(r15)     // Catch:{ IOException -> 0x01a8 }
            if (r14 <= 0) goto L_0x009b
            r13 = 1
            goto L_0x009b
        L_0x009a:
            r14 = 0
        L_0x009b:
            org.eclipse.jetty.util.log.Logger r15 = r1._logger     // Catch:{ all -> 0x0219 }
            java.lang.String r5 = "{} {} {} filled={}/{} flushed={}/{}"
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLSession r8 = r1._session     // Catch:{ all -> 0x0219 }
            r7[r3] = r8     // Catch:{ all -> 0x0219 }
            r7[r4] = r1     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLEngine r8 = r1._engine     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r8 = r8.getHandshakeStatus()     // Catch:{ all -> 0x0219 }
            r7[r11] = r8     // Catch:{ all -> 0x0219 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x0219 }
            r7[r10] = r8     // Catch:{ all -> 0x0219 }
            org.eclipse.jetty.io.nio.NIOBuffer r8 = r1._inbound     // Catch:{ all -> 0x0219 }
            int r8 = r8.length()     // Catch:{ all -> 0x0219 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0219 }
            r7[r9] = r8     // Catch:{ all -> 0x0219 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x0219 }
            r14 = 5
            r7[r14] = r8     // Catch:{ all -> 0x0219 }
            org.eclipse.jetty.io.nio.NIOBuffer r8 = r1._outbound     // Catch:{ all -> 0x0219 }
            int r8 = r8.length()     // Catch:{ all -> 0x0219 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0219 }
            r14 = 6
            r7[r14] = r8     // Catch:{ all -> 0x0219 }
            r15.debug((java.lang.String) r5, (java.lang.Object[]) r7)     // Catch:{ all -> 0x0219 }
            int[] r5 = org.eclipse.jetty.p119io.nio.SslConnection.C23931.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLEngine r7 = r1._engine     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r7 = r7.getHandshakeStatus()     // Catch:{ all -> 0x0219 }
            int r7 = r7.ordinal()     // Catch:{ all -> 0x0219 }
            r5 = r5[r7]     // Catch:{ all -> 0x0219 }
            if (r5 == r4) goto L_0x01a2
            if (r5 == r11) goto L_0x013d
            if (r5 == r10) goto L_0x0130
            if (r5 == r9) goto L_0x0118
            r7 = 5
            if (r5 == r7) goto L_0x00f2
            goto L_0x012e
        L_0x00f2:
            boolean r5 = r1._handshook     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x0100
            boolean r5 = r1._allowRenegotiate     // Catch:{ all -> 0x0219 }
            if (r5 != 0) goto L_0x0100
            org.eclipse.jetty.io.EndPoint r5 = r1._endp     // Catch:{ all -> 0x0219 }
            r5.close()     // Catch:{ all -> 0x0219 }
            goto L_0x012e
        L_0x0100:
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._inbound     // Catch:{ all -> 0x0219 }
            boolean r5 = r5.hasContent()     // Catch:{ all -> 0x0219 }
            if (r5 != 0) goto L_0x0111
            r5 = -1
            if (r12 != r5) goto L_0x0111
            org.eclipse.jetty.io.EndPoint r5 = r1._endp     // Catch:{ all -> 0x0219 }
            r5.shutdownInput()     // Catch:{ all -> 0x0219 }
            goto L_0x012e
        L_0x0111:
            boolean r5 = r1.unwrap(r0)     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x012e
            goto L_0x012c
        L_0x0118:
            boolean r5 = r1._handshook     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x0126
            boolean r5 = r1._allowRenegotiate     // Catch:{ all -> 0x0219 }
            if (r5 != 0) goto L_0x0126
            org.eclipse.jetty.io.EndPoint r5 = r1._endp     // Catch:{ all -> 0x0219 }
            r5.close()     // Catch:{ all -> 0x0219 }
            goto L_0x012e
        L_0x0126:
            boolean r5 = r1.wrap(r2)     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x012e
        L_0x012c:
            r5 = 1
            goto L_0x0167
        L_0x012e:
            r5 = r13
            goto L_0x0167
        L_0x0130:
            javax.net.ssl.SSLEngine r5 = r1._engine     // Catch:{ all -> 0x0219 }
            java.lang.Runnable r5 = r5.getDelegatedTask()     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x012e
            r5.run()     // Catch:{ all -> 0x0219 }
            r13 = 1
            goto L_0x0130
        L_0x013d:
            int r5 = r0.space()     // Catch:{ all -> 0x0219 }
            if (r5 <= 0) goto L_0x0152
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._inbound     // Catch:{ all -> 0x0219 }
            boolean r5 = r5.hasContent()     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x0152
            boolean r5 = r1.unwrap(r0)     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x0152
            r13 = 1
        L_0x0152:
            boolean r5 = r2.hasContent()     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x012e
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r1._outbound     // Catch:{ all -> 0x0219 }
            int r5 = r5.space()     // Catch:{ all -> 0x0219 }
            if (r5 <= 0) goto L_0x012e
            boolean r5 = r1.wrap(r2)     // Catch:{ all -> 0x0219 }
            if (r5 == 0) goto L_0x012e
            goto L_0x012c
        L_0x0167:
            org.eclipse.jetty.io.EndPoint r7 = r1._endp     // Catch:{ all -> 0x0219 }
            boolean r7 = r7.isOpen()     // Catch:{ all -> 0x0219 }
            if (r7 == 0) goto L_0x0182
            org.eclipse.jetty.io.EndPoint r7 = r1._endp     // Catch:{ all -> 0x0219 }
            boolean r7 = r7.isInputShutdown()     // Catch:{ all -> 0x0219 }
            if (r7 == 0) goto L_0x0182
            org.eclipse.jetty.io.nio.NIOBuffer r7 = r1._inbound     // Catch:{ all -> 0x0219 }
            boolean r7 = r7.hasContent()     // Catch:{ all -> 0x0219 }
            if (r7 != 0) goto L_0x0182
            r16.closeInbound()     // Catch:{ all -> 0x0219 }
        L_0x0182:
            org.eclipse.jetty.io.EndPoint r7 = r1._endp     // Catch:{ all -> 0x0219 }
            boolean r7 = r7.isOpen()     // Catch:{ all -> 0x0219 }
            if (r7 == 0) goto L_0x019f
            javax.net.ssl.SSLEngine r7 = r1._engine     // Catch:{ all -> 0x0219 }
            boolean r7 = r7.isOutboundDone()     // Catch:{ all -> 0x0219 }
            if (r7 == 0) goto L_0x019f
            org.eclipse.jetty.io.nio.NIOBuffer r7 = r1._outbound     // Catch:{ all -> 0x0219 }
            boolean r7 = r7.hasContent()     // Catch:{ all -> 0x0219 }
            if (r7 != 0) goto L_0x019f
            org.eclipse.jetty.io.EndPoint r7 = r1._endp     // Catch:{ all -> 0x0219 }
            r7.shutdownOutput()     // Catch:{ all -> 0x0219 }
        L_0x019f:
            r6 = r6 | r5
            goto L_0x006a
        L_0x01a2:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0219 }
            r0.<init>()     // Catch:{ all -> 0x0219 }
            throw r0     // Catch:{ all -> 0x0219 }
        L_0x01a8:
            r0 = move-exception
            goto L_0x01af
        L_0x01aa:
            r0 = move-exception
            r12 = 0
            goto L_0x01b6
        L_0x01ad:
            r0 = move-exception
            r12 = 0
        L_0x01af:
            org.eclipse.jetty.io.EndPoint r2 = r1._endp     // Catch:{ all -> 0x01b5 }
            r2.close()     // Catch:{ all -> 0x01b5 }
            throw r0     // Catch:{ all -> 0x01b5 }
        L_0x01b5:
            r0 = move-exception
        L_0x01b6:
            org.eclipse.jetty.util.log.Logger r2 = r1._logger     // Catch:{ all -> 0x0219 }
            java.lang.String r5 = "{} {} {} filled={}/{} flushed={}/{}"
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLSession r8 = r1._session     // Catch:{ all -> 0x0219 }
            r7[r3] = r8     // Catch:{ all -> 0x0219 }
            r7[r4] = r1     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLEngine r8 = r1._engine     // Catch:{ all -> 0x0219 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r8 = r8.getHandshakeStatus()     // Catch:{ all -> 0x0219 }
            r7[r11] = r8     // Catch:{ all -> 0x0219 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r12)     // Catch:{ all -> 0x0219 }
            r7[r10] = r8     // Catch:{ all -> 0x0219 }
            org.eclipse.jetty.io.nio.NIOBuffer r8 = r1._inbound     // Catch:{ all -> 0x0219 }
            int r8 = r8.length()     // Catch:{ all -> 0x0219 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0219 }
            r7[r9] = r8     // Catch:{ all -> 0x0219 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0219 }
            r8 = 5
            r7[r8] = r3     // Catch:{ all -> 0x0219 }
            org.eclipse.jetty.io.nio.NIOBuffer r3 = r1._outbound     // Catch:{ all -> 0x0219 }
            int r3 = r3.length()     // Catch:{ all -> 0x0219 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0219 }
            r8 = 6
            r7[r8] = r3     // Catch:{ all -> 0x0219 }
            r2.debug((java.lang.String) r5, (java.lang.Object[]) r7)     // Catch:{ all -> 0x0219 }
            throw r0     // Catch:{ all -> 0x0219 }
        L_0x01f4:
            org.eclipse.jetty.io.nio.NIOBuffer r2 = r1._unwrapBuf     // Catch:{ all -> 0x0219 }
            if (r0 != r2) goto L_0x020d
            org.eclipse.jetty.io.nio.NIOBuffer r0 = r1._unwrapBuf     // Catch:{ all -> 0x0219 }
            boolean r0 = r0.hasContent()     // Catch:{ all -> 0x0219 }
            if (r0 == 0) goto L_0x020d
            org.eclipse.jetty.io.nio.AsyncConnection r0 = r1._connection     // Catch:{ all -> 0x0219 }
            boolean r0 = r0.isSuspended()     // Catch:{ all -> 0x0219 }
            if (r0 != 0) goto L_0x020d
            org.eclipse.jetty.io.AsyncEndPoint r0 = r1._aEndp     // Catch:{ all -> 0x0219 }
            r0.dispatch()     // Catch:{ all -> 0x0219 }
        L_0x020d:
            r16.releaseBuffers()     // Catch:{ all -> 0x0228 }
            if (r6 == 0) goto L_0x0217
            java.util.concurrent.atomic.AtomicBoolean r0 = r1._progressed     // Catch:{ all -> 0x0228 }
            r0.set(r4)     // Catch:{ all -> 0x0228 }
        L_0x0217:
            monitor-exit(r16)
            return r6
        L_0x0219:
            r0 = move-exception
            goto L_0x021d
        L_0x021b:
            r0 = move-exception
            r6 = 0
        L_0x021d:
            r16.releaseBuffers()     // Catch:{ all -> 0x0228 }
            if (r6 == 0) goto L_0x0227
            java.util.concurrent.atomic.AtomicBoolean r2 = r1._progressed     // Catch:{ all -> 0x0228 }
            r2.set(r4)     // Catch:{ all -> 0x0228 }
        L_0x0227:
            throw r0     // Catch:{ all -> 0x0228 }
        L_0x0228:
            r0 = move-exception
            monitor-exit(r16)
            goto L_0x022c
        L_0x022b:
            throw r0
        L_0x022c:
            goto L_0x022b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SslConnection.process(org.eclipse.jetty.io.Buffer, org.eclipse.jetty.io.Buffer):boolean");
    }

    private void closeInbound() {
        try {
            this._engine.closeInbound();
        } catch (SSLException e) {
            this._logger.debug(e);
        }
    }

    private synchronized boolean wrap(Buffer buffer) throws IOException {
        boolean z;
        SSLEngineResult wrap;
        ByteBuffer extractByteBuffer = extractByteBuffer(buffer);
        synchronized (extractByteBuffer) {
            this._outbound.compact();
            ByteBuffer byteBuffer = this._outbound.getByteBuffer();
            synchronized (byteBuffer) {
                z = false;
                try {
                    extractByteBuffer.position(buffer.getIndex());
                    extractByteBuffer.limit(buffer.putIndex());
                    byteBuffer.position(this._outbound.putIndex());
                    byteBuffer.limit(byteBuffer.capacity());
                    wrap = this._engine.wrap(extractByteBuffer, byteBuffer);
                    if (this._logger.isDebugEnabled()) {
                        this._logger.debug("{} wrap {} {} consumed={} produced={}", this._session, wrap.getStatus(), wrap.getHandshakeStatus(), Integer.valueOf(wrap.bytesConsumed()), Integer.valueOf(wrap.bytesProduced()));
                    }
                    buffer.skip(wrap.bytesConsumed());
                    this._outbound.setPutIndex(this._outbound.putIndex() + wrap.bytesProduced());
                    byteBuffer.position(0);
                    byteBuffer.limit(byteBuffer.capacity());
                    extractByteBuffer.position(0);
                    extractByteBuffer.limit(extractByteBuffer.capacity());
                } catch (SSLException e) {
                    this._logger.debug(String.valueOf(this._endp), (Throwable) e);
                    this._endp.close();
                    throw e;
                } catch (Throwable th) {
                    byteBuffer.position(0);
                    byteBuffer.limit(byteBuffer.capacity());
                    extractByteBuffer.position(0);
                    extractByteBuffer.limit(extractByteBuffer.capacity());
                    throw th;
                }
            }
        }
        int i = C23931.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[wrap.getStatus().ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        this._logger.debug("wrap CLOSE {} {}", this, wrap);
                        if (wrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                            this._endp.close();
                        }
                    } else {
                        this._logger.debug("{} wrap default {}", this._session, wrap);
                        throw new IOException(wrap.toString());
                    }
                } else if (wrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED) {
                    this._handshook = true;
                }
            }
            if (wrap.bytesConsumed() > 0 || wrap.bytesProduced() > 0) {
                z = true;
            }
        } else {
            throw new IllegalStateException();
        }
        return z;
    }

    /* renamed from: org.eclipse.jetty.io.nio.SslConnection$1 */
    static /* synthetic */ class C23931 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = new int[SSLEngineResult.HandshakeStatus.values().length];
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status = new int[SSLEngineResult.Status.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Can't wrap try/catch for region: R(23:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0048 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0066 */
        static {
            /*
                javax.net.ssl.SSLEngineResult$Status[] r0 = javax.net.ssl.SSLEngineResult.Status.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status = r0
                r0 = 1
                int[] r1 = $SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ NoSuchFieldError -> 0x0014 }
                javax.net.ssl.SSLEngineResult$Status r2 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ NoSuchFieldError -> 0x001f }
                javax.net.ssl.SSLEngineResult$Status r3 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = $SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ NoSuchFieldError -> 0x002a }
                javax.net.ssl.SSLEngineResult$Status r4 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                r3 = 4
                int[] r4 = $SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ NoSuchFieldError -> 0x0035 }
                javax.net.ssl.SSLEngineResult$Status r5 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                javax.net.ssl.SSLEngineResult$HandshakeStatus[] r4 = javax.net.ssl.SSLEngineResult.HandshakeStatus.values()
                int r4 = r4.length
                int[] r4 = new int[r4]
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = r4
                int[] r4 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x0048 }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r5 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ NoSuchFieldError -> 0x0048 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0048 }
                r4[r5] = r0     // Catch:{ NoSuchFieldError -> 0x0048 }
            L_0x0048:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x0052 }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r4 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ NoSuchFieldError -> 0x0052 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0052 }
                r0[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0052 }
            L_0x0052:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x005c }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_TASK     // Catch:{ NoSuchFieldError -> 0x005c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005c }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005c }
            L_0x005c:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x0066 }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ NoSuchFieldError -> 0x0066 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0066 }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x0066 }
            L_0x0066:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x0071 }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ NoSuchFieldError -> 0x0071 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0071 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0071 }
            L_0x0071:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SslConnection.C23931.<clinit>():void");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0140, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean unwrap(org.eclipse.jetty.p119io.Buffer r13) throws java.io.IOException {
        /*
            r12 = this;
            monitor-enter(r12)
            org.eclipse.jetty.io.nio.NIOBuffer r0 = r12._inbound     // Catch:{ all -> 0x0170 }
            boolean r0 = r0.hasContent()     // Catch:{ all -> 0x0170 }
            r1 = 0
            if (r0 != 0) goto L_0x000c
            monitor-exit(r12)
            return r1
        L_0x000c:
            java.nio.ByteBuffer r0 = r12.extractByteBuffer(r13)     // Catch:{ all -> 0x0170 }
            monitor-enter(r0)     // Catch:{ all -> 0x0170 }
            org.eclipse.jetty.io.nio.NIOBuffer r2 = r12._inbound     // Catch:{ all -> 0x016d }
            java.nio.ByteBuffer r2 = r2.getByteBuffer()     // Catch:{ all -> 0x016d }
            monitor-enter(r2)     // Catch:{ all -> 0x016d }
            int r3 = r13.putIndex()     // Catch:{ SSLException -> 0x0143 }
            r0.position(r3)     // Catch:{ SSLException -> 0x0143 }
            int r3 = r13.capacity()     // Catch:{ SSLException -> 0x0143 }
            r0.limit(r3)     // Catch:{ SSLException -> 0x0143 }
            org.eclipse.jetty.io.nio.NIOBuffer r3 = r12._inbound     // Catch:{ SSLException -> 0x0143 }
            int r3 = r3.getIndex()     // Catch:{ SSLException -> 0x0143 }
            r2.position(r3)     // Catch:{ SSLException -> 0x0143 }
            org.eclipse.jetty.io.nio.NIOBuffer r3 = r12._inbound     // Catch:{ SSLException -> 0x0143 }
            int r3 = r3.putIndex()     // Catch:{ SSLException -> 0x0143 }
            r2.limit(r3)     // Catch:{ SSLException -> 0x0143 }
            javax.net.ssl.SSLEngine r3 = r12._engine     // Catch:{ SSLException -> 0x0143 }
            javax.net.ssl.SSLEngineResult r3 = r3.unwrap(r2, r0)     // Catch:{ SSLException -> 0x0143 }
            org.eclipse.jetty.util.log.Logger r4 = r12._logger     // Catch:{ SSLException -> 0x0143 }
            boolean r4 = r4.isDebugEnabled()     // Catch:{ SSLException -> 0x0143 }
            r5 = 4
            r6 = 3
            r7 = 2
            r8 = 1
            if (r4 == 0) goto L_0x0078
            org.eclipse.jetty.util.log.Logger r4 = r12._logger     // Catch:{ SSLException -> 0x0143 }
            java.lang.String r9 = "{} unwrap {} {} consumed={} produced={}"
            r10 = 5
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ SSLException -> 0x0143 }
            javax.net.ssl.SSLSession r11 = r12._session     // Catch:{ SSLException -> 0x0143 }
            r10[r1] = r11     // Catch:{ SSLException -> 0x0143 }
            javax.net.ssl.SSLEngineResult$Status r11 = r3.getStatus()     // Catch:{ SSLException -> 0x0143 }
            r10[r8] = r11     // Catch:{ SSLException -> 0x0143 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = r3.getHandshakeStatus()     // Catch:{ SSLException -> 0x0143 }
            r10[r7] = r11     // Catch:{ SSLException -> 0x0143 }
            int r11 = r3.bytesConsumed()     // Catch:{ SSLException -> 0x0143 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ SSLException -> 0x0143 }
            r10[r6] = r11     // Catch:{ SSLException -> 0x0143 }
            int r11 = r3.bytesProduced()     // Catch:{ SSLException -> 0x0143 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ SSLException -> 0x0143 }
            r10[r5] = r11     // Catch:{ SSLException -> 0x0143 }
            r4.debug((java.lang.String) r9, (java.lang.Object[]) r10)     // Catch:{ SSLException -> 0x0143 }
        L_0x0078:
            org.eclipse.jetty.io.nio.NIOBuffer r4 = r12._inbound     // Catch:{ SSLException -> 0x0143 }
            int r9 = r3.bytesConsumed()     // Catch:{ SSLException -> 0x0143 }
            r4.skip(r9)     // Catch:{ SSLException -> 0x0143 }
            org.eclipse.jetty.io.nio.NIOBuffer r4 = r12._inbound     // Catch:{ SSLException -> 0x0143 }
            r4.compact()     // Catch:{ SSLException -> 0x0143 }
            int r4 = r13.putIndex()     // Catch:{ SSLException -> 0x0143 }
            int r9 = r3.bytesProduced()     // Catch:{ SSLException -> 0x0143 }
            int r4 = r4 + r9
            r13.setPutIndex(r4)     // Catch:{ SSLException -> 0x0143 }
            r2.position(r1)     // Catch:{ all -> 0x016a }
            int r4 = r2.capacity()     // Catch:{ all -> 0x016a }
            r2.limit(r4)     // Catch:{ all -> 0x016a }
            r0.position(r1)     // Catch:{ all -> 0x016a }
            int r4 = r0.capacity()     // Catch:{ all -> 0x016a }
            r0.limit(r4)     // Catch:{ all -> 0x016a }
            monitor-exit(r2)     // Catch:{ all -> 0x016a }
            monitor-exit(r0)     // Catch:{ all -> 0x016d }
            int[] r0 = org.eclipse.jetty.p119io.nio.SslConnection.C23931.$SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ all -> 0x0170 }
            javax.net.ssl.SSLEngineResult$Status r2 = r3.getStatus()     // Catch:{ all -> 0x0170 }
            int r2 = r2.ordinal()     // Catch:{ all -> 0x0170 }
            r0 = r0[r2]     // Catch:{ all -> 0x0170 }
            if (r0 == r8) goto L_0x0125
            if (r0 == r7) goto L_0x00fb
            if (r0 == r6) goto L_0x00f0
            if (r0 != r5) goto L_0x00d7
            org.eclipse.jetty.util.log.Logger r13 = r12._logger     // Catch:{ all -> 0x0170 }
            java.lang.String r0 = "unwrap CLOSE {} {}"
            java.lang.Object[] r2 = new java.lang.Object[r7]     // Catch:{ all -> 0x0170 }
            r2[r1] = r12     // Catch:{ all -> 0x0170 }
            r2[r8] = r3     // Catch:{ all -> 0x0170 }
            r13.debug((java.lang.String) r0, (java.lang.Object[]) r2)     // Catch:{ all -> 0x0170 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r13 = r3.getHandshakeStatus()     // Catch:{ all -> 0x0170 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0170 }
            if (r13 != r0) goto L_0x0132
            org.eclipse.jetty.io.EndPoint r13 = r12._endp     // Catch:{ all -> 0x0170 }
            r13.close()     // Catch:{ all -> 0x0170 }
            goto L_0x0132
        L_0x00d7:
            org.eclipse.jetty.util.log.Logger r13 = r12._logger     // Catch:{ all -> 0x0170 }
            java.lang.String r0 = "{} wrap default {}"
            java.lang.Object[] r2 = new java.lang.Object[r7]     // Catch:{ all -> 0x0170 }
            javax.net.ssl.SSLSession r4 = r12._session     // Catch:{ all -> 0x0170 }
            r2[r1] = r4     // Catch:{ all -> 0x0170 }
            r2[r8] = r3     // Catch:{ all -> 0x0170 }
            r13.debug((java.lang.String) r0, (java.lang.Object[]) r2)     // Catch:{ all -> 0x0170 }
            java.io.IOException r13 = new java.io.IOException     // Catch:{ all -> 0x0170 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x0170 }
            r13.<init>(r0)     // Catch:{ all -> 0x0170 }
            throw r13     // Catch:{ all -> 0x0170 }
        L_0x00f0:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r13 = r3.getHandshakeStatus()     // Catch:{ all -> 0x0170 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0170 }
            if (r13 != r0) goto L_0x0132
            r12._handshook = r8     // Catch:{ all -> 0x0170 }
            goto L_0x0132
        L_0x00fb:
            org.eclipse.jetty.util.log.Logger r0 = r12._logger     // Catch:{ all -> 0x0170 }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ all -> 0x0170 }
            if (r0 == 0) goto L_0x0132
            org.eclipse.jetty.util.log.Logger r0 = r12._logger     // Catch:{ all -> 0x0170 }
            java.lang.String r2 = "{} unwrap {} {}->{}"
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ all -> 0x0170 }
            javax.net.ssl.SSLSession r5 = r12._session     // Catch:{ all -> 0x0170 }
            r4[r1] = r5     // Catch:{ all -> 0x0170 }
            javax.net.ssl.SSLEngineResult$Status r5 = r3.getStatus()     // Catch:{ all -> 0x0170 }
            r4[r8] = r5     // Catch:{ all -> 0x0170 }
            org.eclipse.jetty.io.nio.NIOBuffer r5 = r12._inbound     // Catch:{ all -> 0x0170 }
            java.lang.String r5 = r5.toDetailString()     // Catch:{ all -> 0x0170 }
            r4[r7] = r5     // Catch:{ all -> 0x0170 }
            java.lang.String r13 = r13.toDetailString()     // Catch:{ all -> 0x0170 }
            r4[r6] = r13     // Catch:{ all -> 0x0170 }
            r0.debug((java.lang.String) r2, (java.lang.Object[]) r4)     // Catch:{ all -> 0x0170 }
            goto L_0x0132
        L_0x0125:
            org.eclipse.jetty.io.EndPoint r13 = r12._endp     // Catch:{ all -> 0x0170 }
            boolean r13 = r13.isInputShutdown()     // Catch:{ all -> 0x0170 }
            if (r13 == 0) goto L_0x0132
            org.eclipse.jetty.io.nio.NIOBuffer r13 = r12._inbound     // Catch:{ all -> 0x0170 }
            r13.clear()     // Catch:{ all -> 0x0170 }
        L_0x0132:
            int r13 = r3.bytesConsumed()     // Catch:{ all -> 0x0170 }
            if (r13 > 0) goto L_0x013e
            int r13 = r3.bytesProduced()     // Catch:{ all -> 0x0170 }
            if (r13 <= 0) goto L_0x013f
        L_0x013e:
            r1 = 1
        L_0x013f:
            monitor-exit(r12)
            return r1
        L_0x0141:
            r13 = move-exception
            goto L_0x0155
        L_0x0143:
            r13 = move-exception
            org.eclipse.jetty.util.log.Logger r3 = r12._logger     // Catch:{ all -> 0x0141 }
            org.eclipse.jetty.io.EndPoint r4 = r12._endp     // Catch:{ all -> 0x0141 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0141 }
            r3.debug((java.lang.String) r4, (java.lang.Throwable) r13)     // Catch:{ all -> 0x0141 }
            org.eclipse.jetty.io.EndPoint r3 = r12._endp     // Catch:{ all -> 0x0141 }
            r3.close()     // Catch:{ all -> 0x0141 }
            throw r13     // Catch:{ all -> 0x0141 }
        L_0x0155:
            r2.position(r1)     // Catch:{ all -> 0x016a }
            int r3 = r2.capacity()     // Catch:{ all -> 0x016a }
            r2.limit(r3)     // Catch:{ all -> 0x016a }
            r0.position(r1)     // Catch:{ all -> 0x016a }
            int r1 = r0.capacity()     // Catch:{ all -> 0x016a }
            r0.limit(r1)     // Catch:{ all -> 0x016a }
            throw r13     // Catch:{ all -> 0x016a }
        L_0x016a:
            r13 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x016a }
            throw r13     // Catch:{ all -> 0x016d }
        L_0x016d:
            r13 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x016d }
            throw r13     // Catch:{ all -> 0x0170 }
        L_0x0170:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SslConnection.unwrap(org.eclipse.jetty.io.Buffer):boolean");
    }

    private ByteBuffer extractByteBuffer(Buffer buffer) {
        if (buffer.buffer() instanceof NIOBuffer) {
            return ((NIOBuffer) buffer.buffer()).getByteBuffer();
        }
        return ByteBuffer.wrap(buffer.array());
    }

    public AsyncEndPoint getSslEndPoint() {
        return this._sslEndPoint;
    }

    public String toString() {
        return String.format("%s %s", new Object[]{super.toString(), this._sslEndPoint});
    }

    /* renamed from: org.eclipse.jetty.io.nio.SslConnection$SslEndPoint */
    public class SslEndPoint implements AsyncEndPoint {
        public boolean isBlocking() {
            return false;
        }

        public SslEndPoint() {
        }

        public SSLEngine getSslEngine() {
            return SslConnection.this._engine;
        }

        public AsyncEndPoint getEndpoint() {
            return SslConnection.this._aEndp;
        }

        public void shutdownOutput() throws IOException {
            synchronized (SslConnection.this) {
                SslConnection.this._logger.debug("{} ssl endp.oshut {}", SslConnection.this._session, this);
                SslConnection.this._engine.closeOutbound();
                boolean unused = SslConnection.this._oshut = true;
            }
            flush();
        }

        public boolean isOutputShutdown() {
            boolean z;
            synchronized (SslConnection.this) {
                if (!SslConnection.this._oshut && isOpen()) {
                    if (!SslConnection.this._engine.isOutboundDone()) {
                        z = false;
                    }
                }
                z = true;
            }
            return z;
        }

        public void shutdownInput() throws IOException {
            SslConnection.this._logger.debug("{} ssl endp.ishut!", SslConnection.this._session);
        }

        public boolean isInputShutdown() {
            boolean z;
            synchronized (SslConnection.this) {
                z = SslConnection.this._endp.isInputShutdown() && (SslConnection.this._unwrapBuf == null || !SslConnection.this._unwrapBuf.hasContent()) && (SslConnection.this._inbound == null || !SslConnection.this._inbound.hasContent());
            }
            return z;
        }

        public void close() throws IOException {
            SslConnection.this._logger.debug("{} ssl endp.close", SslConnection.this._session);
            SslConnection.this._endp.close();
        }

        public int fill(Buffer buffer) throws IOException {
            int length = buffer.length();
            boolean unused = SslConnection.this.process(buffer, (Buffer) null);
            int length2 = buffer.length() - length;
            if (length2 != 0 || !isInputShutdown()) {
                return length2;
            }
            return -1;
        }

        public int flush(Buffer buffer) throws IOException {
            int length = buffer.length();
            boolean unused = SslConnection.this.process((Buffer) null, buffer);
            return length - buffer.length();
        }

        public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            if (buffer != null && buffer.hasContent()) {
                return flush(buffer);
            }
            if (buffer2 != null && buffer2.hasContent()) {
                return flush(buffer2);
            }
            if (buffer3 == null || !buffer3.hasContent()) {
                return 0;
            }
            return flush(buffer3);
        }

        public boolean blockReadable(long j) throws IOException {
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = j > 0 ? j + currentTimeMillis : LongCompanionObject.MAX_VALUE;
            while (currentTimeMillis < j2 && !SslConnection.this.process((Buffer) null, (Buffer) null)) {
                SslConnection.this._endp.blockReadable(j2 - currentTimeMillis);
                currentTimeMillis = System.currentTimeMillis();
            }
            return currentTimeMillis < j2;
        }

        public boolean blockWritable(long j) throws IOException {
            return SslConnection.this._endp.blockWritable(j);
        }

        public boolean isOpen() {
            return SslConnection.this._endp.isOpen();
        }

        public Object getTransport() {
            return SslConnection.this._endp;
        }

        public void flush() throws IOException {
            boolean unused = SslConnection.this.process((Buffer) null, (Buffer) null);
        }

        public void dispatch() {
            SslConnection.this._aEndp.dispatch();
        }

        public void asyncDispatch() {
            SslConnection.this._aEndp.asyncDispatch();
        }

        public void scheduleWrite() {
            SslConnection.this._aEndp.scheduleWrite();
        }

        public void onIdleExpired(long j) {
            SslConnection.this._aEndp.onIdleExpired(j);
        }

        public void setCheckForIdle(boolean z) {
            SslConnection.this._aEndp.setCheckForIdle(z);
        }

        public boolean isCheckForIdle() {
            return SslConnection.this._aEndp.isCheckForIdle();
        }

        public void scheduleTimeout(Timeout.Task task, long j) {
            SslConnection.this._aEndp.scheduleTimeout(task, j);
        }

        public void cancelTimeout(Timeout.Task task) {
            SslConnection.this._aEndp.cancelTimeout(task);
        }

        public boolean isWritable() {
            return SslConnection.this._aEndp.isWritable();
        }

        public boolean hasProgressed() {
            return SslConnection.this._progressed.getAndSet(false);
        }

        public String getLocalAddr() {
            return SslConnection.this._aEndp.getLocalAddr();
        }

        public String getLocalHost() {
            return SslConnection.this._aEndp.getLocalHost();
        }

        public int getLocalPort() {
            return SslConnection.this._aEndp.getLocalPort();
        }

        public String getRemoteAddr() {
            return SslConnection.this._aEndp.getRemoteAddr();
        }

        public String getRemoteHost() {
            return SslConnection.this._aEndp.getRemoteHost();
        }

        public int getRemotePort() {
            return SslConnection.this._aEndp.getRemotePort();
        }

        public int getMaxIdleTime() {
            return SslConnection.this._aEndp.getMaxIdleTime();
        }

        public void setMaxIdleTime(int i) throws IOException {
            SslConnection.this._aEndp.setMaxIdleTime(i);
        }

        public Connection getConnection() {
            return SslConnection.this._connection;
        }

        public void setConnection(Connection connection) {
            AsyncConnection unused = SslConnection.this._connection = (AsyncConnection) connection;
        }

        public String toString() {
            int i;
            int i2;
            NIOBuffer access$700 = SslConnection.this._inbound;
            NIOBuffer access$1600 = SslConnection.this._outbound;
            NIOBuffer access$600 = SslConnection.this._unwrapBuf;
            int i3 = -1;
            if (access$700 == null) {
                i = -1;
            } else {
                i = access$700.length();
            }
            if (access$1600 == null) {
                i2 = -1;
            } else {
                i2 = access$1600.length();
            }
            if (access$600 != null) {
                i3 = access$600.length();
            }
            return String.format("SSL %s i/o/u=%d/%d/%d ishut=%b oshut=%b {%s}", new Object[]{SslConnection.this._engine.getHandshakeStatus(), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Boolean.valueOf(SslConnection.this._ishut), Boolean.valueOf(SslConnection.this._oshut), SslConnection.this._connection});
        }
    }
}
