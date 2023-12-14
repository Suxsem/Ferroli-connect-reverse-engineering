package org.eclipse.jetty.server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ConnectedEndPoint;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.nio.ChannelEndPoint;
import org.eclipse.jetty.server.BlockingHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class BlockingChannelConnector extends AbstractNIOConnector {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) BlockingChannelConnector.class);
    private transient ServerSocketChannel _acceptChannel;
    /* access modifiers changed from: private */
    public final Set<BlockingChannelEndPoint> _endpoints = new ConcurrentHashSet();

    public Object getConnection() {
        return this._acceptChannel;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        super.doStart();
        getThreadPool().dispatch(new Runnable() {
            public void run() {
                while (BlockingChannelConnector.this.isRunning()) {
                    try {
                        Thread.sleep(400);
                        long currentTimeMillis = System.currentTimeMillis();
                        for (BlockingChannelEndPoint checkIdleTimestamp : BlockingChannelConnector.this._endpoints) {
                            checkIdleTimestamp.checkIdleTimestamp(currentTimeMillis);
                        }
                    } catch (InterruptedException e) {
                        BlockingChannelConnector.LOG.ignore(e);
                    } catch (Exception e2) {
                        BlockingChannelConnector.LOG.warn(e2);
                    }
                }
            }
        });
    }

    public void open() throws IOException {
        this._acceptChannel = ServerSocketChannel.open();
        this._acceptChannel.configureBlocking(true);
        this._acceptChannel.socket().bind(getHost() == null ? new InetSocketAddress(getPort()) : new InetSocketAddress(getHost(), getPort()), getAcceptQueueSize());
    }

    public void close() throws IOException {
        ServerSocketChannel serverSocketChannel = this._acceptChannel;
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
        this._acceptChannel = null;
    }

    public void accept(int i) throws IOException, InterruptedException {
        SocketChannel accept = this._acceptChannel.accept();
        accept.configureBlocking(true);
        configure(accept.socket());
        new BlockingChannelEndPoint(accept).dispatch();
    }

    public void customize(EndPoint endPoint, Request request) throws IOException {
        super.customize(endPoint, request);
        endPoint.setMaxIdleTime(this._maxIdleTime);
        configure(((SocketChannel) endPoint.getTransport()).socket());
    }

    public int getLocalPort() {
        ServerSocketChannel serverSocketChannel = this._acceptChannel;
        if (serverSocketChannel == null || !serverSocketChannel.isOpen()) {
            return -1;
        }
        return this._acceptChannel.socket().getLocalPort();
    }

    private class BlockingChannelEndPoint extends ChannelEndPoint implements Runnable, ConnectedEndPoint {
        private Connection _connection;
        private volatile long _idleTimestamp;
        private int _timeout;

        BlockingChannelEndPoint(ByteChannel byteChannel) throws IOException {
            super(byteChannel, BlockingChannelConnector.this._maxIdleTime);
            this._connection = new BlockingHttpConnection(BlockingChannelConnector.this, this, BlockingChannelConnector.this.getServer());
        }

        public Connection getConnection() {
            return this._connection;
        }

        public void setConnection(Connection connection) {
            this._connection = connection;
        }

        public void checkIdleTimestamp(long j) {
            if (this._idleTimestamp != 0 && this._timeout > 0 && j > this._idleTimestamp + ((long) this._timeout)) {
                idleExpired();
            }
        }

        /* access modifiers changed from: protected */
        public void idleExpired() {
            try {
                super.close();
            } catch (IOException e) {
                BlockingChannelConnector.LOG.ignore(e);
            }
        }

        /* access modifiers changed from: package-private */
        public void dispatch() throws IOException {
            if (!BlockingChannelConnector.this.getThreadPool().dispatch(this)) {
                BlockingChannelConnector.LOG.warn("dispatch failed for  {}", this._connection);
                super.close();
            }
        }

        public int fill(Buffer buffer) throws IOException {
            this._idleTimestamp = System.currentTimeMillis();
            return super.fill(buffer);
        }

        public int flush(Buffer buffer) throws IOException {
            this._idleTimestamp = System.currentTimeMillis();
            return super.flush(buffer);
        }

        public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            this._idleTimestamp = System.currentTimeMillis();
            return super.flush(buffer, buffer2, buffer3);
        }

        /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
            jadx.core.utils.exceptions.JadxOverflowException: 
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        public void run() {
            /*
                r9 = this;
                int r0 = r9.getMaxIdleTime()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                r9._timeout = r0     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                org.eclipse.jetty.io.Connection r1 = r9._connection     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                r0.connectionOpened(r1)     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                java.util.Set r0 = r0._endpoints     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                r0.add(r9)     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
            L_0x0016:
                boolean r0 = r9.isOpen()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                if (r0 == 0) goto L_0x0060
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                r9._idleTimestamp = r0     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                org.eclipse.jetty.io.Connection r0 = r9._connection     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                boolean r0 = r0.isIdle()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                if (r0 == 0) goto L_0x0049
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                org.eclipse.jetty.server.Server r0 = r0.getServer()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                org.eclipse.jetty.util.thread.ThreadPool r0 = r0.getThreadPool()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                boolean r0 = r0.isLowOnThreads()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                if (r0 == 0) goto L_0x0057
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                int r0 = r0.getLowResourcesMaxIdleTime()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                if (r0 < 0) goto L_0x0057
                int r1 = r9._timeout     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                if (r1 == r0) goto L_0x0057
                r9._timeout = r0     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                goto L_0x0057
            L_0x0049:
                int r0 = r9._timeout     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                int r1 = r9.getMaxIdleTime()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                if (r0 == r1) goto L_0x0057
                int r0 = r9.getMaxIdleTime()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                r9._timeout = r0     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
            L_0x0057:
                org.eclipse.jetty.io.Connection r0 = r9._connection     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                org.eclipse.jetty.io.Connection r0 = r0.handle()     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                r9._connection = r0     // Catch:{ EofException -> 0x0178, HttpException -> 0x0115, Throwable -> 0x00b1 }
                goto L_0x0016
            L_0x0060:
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                java.util.Set r0 = r0._endpoints
                r0.remove(r9)
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x01db }
            L_0x0089:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x01db }
                int r3 = r3.read()     // Catch:{ IOException -> 0x01db }
                if (r3 < 0) goto L_0x009f
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x01db }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x0089
            L_0x009f:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                r0.close()     // Catch:{ IOException -> 0x01db }
                goto L_0x01e3
            L_0x00ae:
                r0 = move-exception
                goto L_0x01e4
            L_0x00b1:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG     // Catch:{ all -> 0x00ae }
                java.lang.String r2 = "handle failed"
                r1.warn((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x00ae }
                super.close()     // Catch:{ IOException -> 0x00bf }
                goto L_0x00c7
            L_0x00bf:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG     // Catch:{ all -> 0x00ae }
                r1.ignore(r0)     // Catch:{ all -> 0x00ae }
            L_0x00c7:
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                java.util.Set r0 = r0._endpoints
                r0.remove(r9)
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x01db }
            L_0x00f0:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x01db }
                int r3 = r3.read()     // Catch:{ IOException -> 0x01db }
                if (r3 < 0) goto L_0x0106
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x01db }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x00f0
            L_0x0106:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                r0.close()     // Catch:{ IOException -> 0x01db }
                goto L_0x01e3
            L_0x0115:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG     // Catch:{ all -> 0x00ae }
                java.lang.String r2 = "BAD"
                r1.debug((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x00ae }
                super.close()     // Catch:{ IOException -> 0x0123 }
                goto L_0x012b
            L_0x0123:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG     // Catch:{ all -> 0x00ae }
                r1.ignore(r0)     // Catch:{ all -> 0x00ae }
            L_0x012b:
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                java.util.Set r0 = r0._endpoints
                r0.remove(r9)
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x01db }
            L_0x0154:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x01db }
                int r3 = r3.read()     // Catch:{ IOException -> 0x01db }
                if (r3 < 0) goto L_0x016a
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x01db }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x0154
            L_0x016a:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                r0.close()     // Catch:{ IOException -> 0x01db }
                goto L_0x01e3
            L_0x0178:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG     // Catch:{ all -> 0x00ae }
                java.lang.String r2 = "EOF"
                r1.debug((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x00ae }
                r9.close()     // Catch:{ IOException -> 0x0186 }
                goto L_0x018e
            L_0x0186:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG     // Catch:{ all -> 0x00ae }
                r1.ignore(r0)     // Catch:{ all -> 0x00ae }
            L_0x018e:
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.nio.BlockingChannelConnector r0 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                java.util.Set r0 = r0._endpoints
                r0.remove(r9)
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x01db }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x01db }
            L_0x01b7:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x01db }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x01db }
                int r3 = r3.read()     // Catch:{ IOException -> 0x01db }
                if (r3 < 0) goto L_0x01cd
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x01db }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x01db }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x01b7
            L_0x01cd:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x01db }
                if (r0 != 0) goto L_0x01e3
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x01db }
                r0.close()     // Catch:{ IOException -> 0x01db }
                goto L_0x01e3
            L_0x01db:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG
                r1.ignore(r0)
            L_0x01e3:
                return
            L_0x01e4:
                org.eclipse.jetty.server.nio.BlockingChannelConnector r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                org.eclipse.jetty.io.Connection r2 = r9._connection
                r1.connectionClosed(r2)
                org.eclipse.jetty.server.nio.BlockingChannelConnector r1 = org.eclipse.jetty.server.nio.BlockingChannelConnector.this
                java.util.Set r1 = r1._endpoints
                r1.remove(r9)
                java.net.Socket r1 = r9._socket     // Catch:{ IOException -> 0x0231 }
                boolean r1 = r1.isClosed()     // Catch:{ IOException -> 0x0231 }
                if (r1 != 0) goto L_0x0239
                long r1 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0231 }
                int r3 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0231 }
                java.net.Socket r4 = r9._socket     // Catch:{ IOException -> 0x0231 }
                int r5 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0231 }
                r4.setSoTimeout(r5)     // Catch:{ IOException -> 0x0231 }
            L_0x020d:
                java.net.Socket r4 = r9._socket     // Catch:{ IOException -> 0x0231 }
                java.io.InputStream r4 = r4.getInputStream()     // Catch:{ IOException -> 0x0231 }
                int r4 = r4.read()     // Catch:{ IOException -> 0x0231 }
                if (r4 < 0) goto L_0x0223
                long r4 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0231 }
                long r4 = r4 - r1
                long r6 = (long) r3     // Catch:{ IOException -> 0x0231 }
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 < 0) goto L_0x020d
            L_0x0223:
                java.net.Socket r1 = r9._socket     // Catch:{ IOException -> 0x0231 }
                boolean r1 = r1.isClosed()     // Catch:{ IOException -> 0x0231 }
                if (r1 != 0) goto L_0x0239
                java.net.Socket r1 = r9._socket     // Catch:{ IOException -> 0x0231 }
                r1.close()     // Catch:{ IOException -> 0x0231 }
                goto L_0x0239
            L_0x0231:
                r1 = move-exception
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.server.nio.BlockingChannelConnector.LOG
                r2.ignore(r1)
            L_0x0239:
                goto L_0x023b
            L_0x023a:
                throw r0
            L_0x023b:
                goto L_0x023a
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.nio.BlockingChannelConnector.BlockingChannelEndPoint.run():void");
        }

        public String toString() {
            return String.format("BCEP@%x{l(%s)<->r(%s),open=%b,ishut=%b,oshut=%b}-{%s}", new Object[]{Integer.valueOf(hashCode()), this._socket.getRemoteSocketAddress(), this._socket.getLocalSocketAddress(), Boolean.valueOf(isOpen()), Boolean.valueOf(isInputShutdown()), Boolean.valueOf(isOutputShutdown()), this._connection});
        }
    }
}
