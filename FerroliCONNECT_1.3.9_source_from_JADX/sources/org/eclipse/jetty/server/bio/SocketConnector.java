package org.eclipse.jetty.server.bio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ConnectedEndPoint;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.bio.SocketEndPoint;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.BlockingHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class SocketConnector extends AbstractConnector {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) SocketConnector.class);
    protected final Set<EndPoint> _connections = new HashSet();
    protected volatile int _localPort = -1;
    protected ServerSocket _serverSocket;

    public Object getConnection() {
        return this._serverSocket;
    }

    public void open() throws IOException {
        ServerSocket serverSocket = this._serverSocket;
        if (serverSocket == null || serverSocket.isClosed()) {
            this._serverSocket = newServerSocket(getHost(), getPort(), getAcceptQueueSize());
        }
        this._serverSocket.setReuseAddress(getReuseAddress());
        this._localPort = this._serverSocket.getLocalPort();
        if (this._localPort <= 0) {
            throw new IllegalStateException("port not allocated for " + this);
        }
    }

    /* access modifiers changed from: protected */
    public ServerSocket newServerSocket(String str, int i, int i2) throws IOException {
        return str == null ? new ServerSocket(i, i2) : new ServerSocket(i, i2, InetAddress.getByName(str));
    }

    public void close() throws IOException {
        ServerSocket serverSocket = this._serverSocket;
        if (serverSocket != null) {
            serverSocket.close();
        }
        this._serverSocket = null;
        this._localPort = -2;
    }

    public void accept(int i) throws IOException, InterruptedException {
        Socket accept = this._serverSocket.accept();
        configure(accept);
        new ConnectorEndPoint(accept).dispatch();
    }

    /* access modifiers changed from: protected */
    public Connection newConnection(EndPoint endPoint) {
        return new BlockingHttpConnection(this, endPoint, getServer());
    }

    public void customize(EndPoint endPoint, Request request) throws IOException {
        ((ConnectorEndPoint) endPoint).setMaxIdleTime(isLowResources() ? this._lowResourceMaxIdleTime : this._maxIdleTime);
        super.customize(endPoint, request);
    }

    public int getLocalPort() {
        return this._localPort;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        this._connections.clear();
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        HashSet<EndPoint> hashSet = new HashSet<>();
        synchronized (this._connections) {
            hashSet.addAll(this._connections);
        }
        for (EndPoint endPoint : hashSet) {
            ((ConnectorEndPoint) endPoint).close();
        }
    }

    public void dump(Appendable appendable, String str) throws IOException {
        super.dump(appendable, str);
        HashSet hashSet = new HashSet();
        synchronized (this._connections) {
            hashSet.addAll(this._connections);
        }
        AggregateLifeCycle.dump(appendable, str, hashSet);
    }

    protected class ConnectorEndPoint extends SocketEndPoint implements Runnable, ConnectedEndPoint {
        volatile Connection _connection;
        protected final Socket _socket;

        public ConnectorEndPoint(Socket socket) throws IOException {
            super(socket, SocketConnector.this._maxIdleTime);
            this._connection = SocketConnector.this.newConnection(this);
            this._socket = socket;
        }

        public Connection getConnection() {
            return this._connection;
        }

        public void setConnection(Connection connection) {
            if (!(this._connection == connection || this._connection == null)) {
                SocketConnector.this.connectionUpgraded(this._connection, connection);
            }
            this._connection = connection;
        }

        public void dispatch() throws IOException {
            if (SocketConnector.this.getThreadPool() == null || !SocketConnector.this.getThreadPool().dispatch(this)) {
                SocketConnector.LOG.warn("dispatch failed for {}", this._connection);
                close();
            }
        }

        public int fill(Buffer buffer) throws IOException {
            int fill = super.fill(buffer);
            if (fill < 0) {
                if (!isInputShutdown()) {
                    shutdownInput();
                }
                if (isOutputShutdown()) {
                    close();
                }
            }
            return fill;
        }

        public void close() throws IOException {
            if (this._connection instanceof AbstractHttpConnection) {
                ((AbstractHttpConnection) this._connection).getRequest().getAsyncContinuation().cancel();
            }
            super.close();
        }

        /* JADX WARNING: Removed duplicated region for block: B:116:0x01b8 A[Catch:{ IOException -> 0x0246 }, LOOP:8: B:116:0x01b8->B:119:0x01cc, LOOP_START] */
        /* JADX WARNING: Removed duplicated region for block: B:144:0x0222 A[Catch:{ IOException -> 0x0246 }, LOOP:10: B:144:0x0222->B:147:0x0236, LOOP_START] */
        /* JADX WARNING: Removed duplicated region for block: B:166:0x027f A[Catch:{ IOException -> 0x02a3 }, LOOP:12: B:166:0x027f->B:169:0x0293, LOOP_START] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0071 A[Catch:{ IOException -> 0x0246 }, LOOP:1: B:26:0x0071->B:29:0x0085, LOOP_START] */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x00e2 A[Catch:{ IOException -> 0x0246 }, LOOP:4: B:60:0x00e2->B:63:0x00f6, LOOP_START] */
        /* JADX WARNING: Removed duplicated region for block: B:88:0x014d A[Catch:{ IOException -> 0x0246 }, LOOP:6: B:88:0x014d->B:91:0x0161, LOOP_START] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r9 = this;
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                org.eclipse.jetty.io.Connection r1 = r9._connection     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                r0.connectionOpened(r1)     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r0 = r0._connections     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                monitor-enter(r0)     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ all -> 0x0099 }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r1 = r1._connections     // Catch:{ all -> 0x0099 }
                r1.add(r9)     // Catch:{ all -> 0x0099 }
                monitor-exit(r0)     // Catch:{ all -> 0x0099 }
            L_0x0014:
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                boolean r0 = r0.isStarted()     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                if (r0 == 0) goto L_0x0044
                boolean r0 = r9.isClosed()     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                if (r0 != 0) goto L_0x0044
                org.eclipse.jetty.io.Connection r0 = r9._connection     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                boolean r0 = r0.isIdle()     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                if (r0 == 0) goto L_0x003b
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                boolean r0 = r0.isLowResources()     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                if (r0 == 0) goto L_0x003b
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                int r0 = r0.getLowResourcesMaxIdleTime()     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                r9.setMaxIdleTime(r0)     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
            L_0x003b:
                org.eclipse.jetty.io.Connection r0 = r9._connection     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                org.eclipse.jetty.io.Connection r0 = r0.handle()     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                r9._connection = r0     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
                goto L_0x0014
            L_0x0044:
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                java.util.Set<org.eclipse.jetty.io.EndPoint> r0 = r0._connections
                monitor-enter(r0)
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ all -> 0x0096 }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r1 = r1._connections     // Catch:{ all -> 0x0096 }
                r1.remove(r9)     // Catch:{ all -> 0x0096 }
                monitor-exit(r0)     // Catch:{ all -> 0x0096 }
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x0246 }
            L_0x0071:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x0246 }
                int r3 = r3.read()     // Catch:{ IOException -> 0x0246 }
                if (r3 < 0) goto L_0x0087
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x0246 }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x0071
            L_0x0087:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                r0.close()     // Catch:{ IOException -> 0x0246 }
                goto L_0x024e
            L_0x0096:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0096 }
                throw r1
            L_0x0099:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0099 }
                throw r1     // Catch:{ EofException -> 0x01df, SocketException -> 0x0175, HttpException -> 0x010a, Exception -> 0x009f }
            L_0x009c:
                r0 = move-exception
                goto L_0x0252
            L_0x009f:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                java.lang.String r2 = "handle failed?"
                r1.warn((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x009c }
                r9.close()     // Catch:{ IOException -> 0x00ad }
                goto L_0x00b5
            L_0x00ad:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                r1.ignore(r0)     // Catch:{ all -> 0x009c }
            L_0x00b5:
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                java.util.Set<org.eclipse.jetty.io.EndPoint> r0 = r0._connections
                monitor-enter(r0)
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ all -> 0x0107 }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r1 = r1._connections     // Catch:{ all -> 0x0107 }
                r1.remove(r9)     // Catch:{ all -> 0x0107 }
                monitor-exit(r0)     // Catch:{ all -> 0x0107 }
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x0246 }
            L_0x00e2:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x0246 }
                int r3 = r3.read()     // Catch:{ IOException -> 0x0246 }
                if (r3 < 0) goto L_0x00f8
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x0246 }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x00e2
            L_0x00f8:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                r0.close()     // Catch:{ IOException -> 0x0246 }
                goto L_0x024e
            L_0x0107:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0107 }
                throw r1
            L_0x010a:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                java.lang.String r2 = "BAD"
                r1.debug((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x009c }
                r9.close()     // Catch:{ IOException -> 0x0118 }
                goto L_0x0120
            L_0x0118:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                r1.ignore(r0)     // Catch:{ all -> 0x009c }
            L_0x0120:
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                java.util.Set<org.eclipse.jetty.io.EndPoint> r0 = r0._connections
                monitor-enter(r0)
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ all -> 0x0172 }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r1 = r1._connections     // Catch:{ all -> 0x0172 }
                r1.remove(r9)     // Catch:{ all -> 0x0172 }
                monitor-exit(r0)     // Catch:{ all -> 0x0172 }
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x0246 }
            L_0x014d:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x0246 }
                int r3 = r3.read()     // Catch:{ IOException -> 0x0246 }
                if (r3 < 0) goto L_0x0163
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x0246 }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x014d
            L_0x0163:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                r0.close()     // Catch:{ IOException -> 0x0246 }
                goto L_0x024e
            L_0x0172:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0172 }
                throw r1
            L_0x0175:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                java.lang.String r2 = "EOF"
                r1.debug((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x009c }
                r9.close()     // Catch:{ IOException -> 0x0183 }
                goto L_0x018b
            L_0x0183:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                r1.ignore(r0)     // Catch:{ all -> 0x009c }
            L_0x018b:
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                java.util.Set<org.eclipse.jetty.io.EndPoint> r0 = r0._connections
                monitor-enter(r0)
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ all -> 0x01dc }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r1 = r1._connections     // Catch:{ all -> 0x01dc }
                r1.remove(r9)     // Catch:{ all -> 0x01dc }
                monitor-exit(r0)     // Catch:{ all -> 0x01dc }
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x0246 }
            L_0x01b8:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x0246 }
                int r3 = r3.read()     // Catch:{ IOException -> 0x0246 }
                if (r3 < 0) goto L_0x01ce
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x0246 }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x01b8
            L_0x01ce:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                r0.close()     // Catch:{ IOException -> 0x0246 }
                goto L_0x024e
            L_0x01dc:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x01dc }
                throw r1
            L_0x01df:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                java.lang.String r2 = "EOF"
                r1.debug((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x009c }
                r9.close()     // Catch:{ IOException -> 0x01ed }
                goto L_0x01f5
            L_0x01ed:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG     // Catch:{ all -> 0x009c }
                r1.ignore(r0)     // Catch:{ all -> 0x009c }
            L_0x01f5:
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                org.eclipse.jetty.io.Connection r1 = r9._connection
                r0.connectionClosed(r1)
                org.eclipse.jetty.server.bio.SocketConnector r0 = org.eclipse.jetty.server.bio.SocketConnector.this
                java.util.Set<org.eclipse.jetty.io.EndPoint> r0 = r0._connections
                monitor-enter(r0)
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ all -> 0x024f }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r1 = r1._connections     // Catch:{ all -> 0x024f }
                r1.remove(r9)     // Catch:{ all -> 0x024f }
                monitor-exit(r0)     // Catch:{ all -> 0x024f }
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                long r0 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                int r2 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                int r4 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x0246 }
                r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x0246 }
            L_0x0222:
                java.net.Socket r3 = r9._socket     // Catch:{ IOException -> 0x0246 }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ IOException -> 0x0246 }
                int r3 = r3.read()     // Catch:{ IOException -> 0x0246 }
                if (r3 < 0) goto L_0x0238
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0246 }
                long r3 = r3 - r0
                long r5 = (long) r2     // Catch:{ IOException -> 0x0246 }
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x0222
            L_0x0238:
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                boolean r0 = r0.isClosed()     // Catch:{ IOException -> 0x0246 }
                if (r0 != 0) goto L_0x024e
                java.net.Socket r0 = r9._socket     // Catch:{ IOException -> 0x0246 }
                r0.close()     // Catch:{ IOException -> 0x0246 }
                goto L_0x024e
            L_0x0246:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.server.bio.SocketConnector.LOG
                r1.ignore(r0)
            L_0x024e:
                return
            L_0x024f:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x024f }
                throw r1
            L_0x0252:
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this
                org.eclipse.jetty.io.Connection r2 = r9._connection
                r1.connectionClosed(r2)
                org.eclipse.jetty.server.bio.SocketConnector r1 = org.eclipse.jetty.server.bio.SocketConnector.this
                java.util.Set<org.eclipse.jetty.io.EndPoint> r1 = r1._connections
                monitor-enter(r1)
                org.eclipse.jetty.server.bio.SocketConnector r2 = org.eclipse.jetty.server.bio.SocketConnector.this     // Catch:{ all -> 0x02ac }
                java.util.Set<org.eclipse.jetty.io.EndPoint> r2 = r2._connections     // Catch:{ all -> 0x02ac }
                r2.remove(r9)     // Catch:{ all -> 0x02ac }
                monitor-exit(r1)     // Catch:{ all -> 0x02ac }
                java.net.Socket r1 = r9._socket     // Catch:{ IOException -> 0x02a3 }
                boolean r1 = r1.isClosed()     // Catch:{ IOException -> 0x02a3 }
                if (r1 != 0) goto L_0x02ab
                long r1 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x02a3 }
                int r3 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x02a3 }
                java.net.Socket r4 = r9._socket     // Catch:{ IOException -> 0x02a3 }
                int r5 = r9.getMaxIdleTime()     // Catch:{ IOException -> 0x02a3 }
                r4.setSoTimeout(r5)     // Catch:{ IOException -> 0x02a3 }
            L_0x027f:
                java.net.Socket r4 = r9._socket     // Catch:{ IOException -> 0x02a3 }
                java.io.InputStream r4 = r4.getInputStream()     // Catch:{ IOException -> 0x02a3 }
                int r4 = r4.read()     // Catch:{ IOException -> 0x02a3 }
                if (r4 < 0) goto L_0x0295
                long r4 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x02a3 }
                long r4 = r4 - r1
                long r6 = (long) r3     // Catch:{ IOException -> 0x02a3 }
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 < 0) goto L_0x027f
            L_0x0295:
                java.net.Socket r1 = r9._socket     // Catch:{ IOException -> 0x02a3 }
                boolean r1 = r1.isClosed()     // Catch:{ IOException -> 0x02a3 }
                if (r1 != 0) goto L_0x02ab
                java.net.Socket r1 = r9._socket     // Catch:{ IOException -> 0x02a3 }
                r1.close()     // Catch:{ IOException -> 0x02a3 }
                goto L_0x02ab
            L_0x02a3:
                r1 = move-exception
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.server.bio.SocketConnector.LOG
                r2.ignore(r1)
            L_0x02ab:
                throw r0
            L_0x02ac:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x02ac }
                goto L_0x02b0
            L_0x02af:
                throw r0
            L_0x02b0:
                goto L_0x02af
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.bio.SocketConnector.ConnectorEndPoint.run():void");
        }
    }
}
