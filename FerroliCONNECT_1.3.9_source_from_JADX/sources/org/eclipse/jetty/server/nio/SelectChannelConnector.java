package org.eclipse.jetty.server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.ConnectedEndPoint;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.nio.AsyncConnection;
import org.eclipse.jetty.p119io.nio.SelectChannelEndPoint;
import org.eclipse.jetty.p119io.nio.SelectorManager;
import org.eclipse.jetty.server.AsyncHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.thread.ThreadPool;

public class SelectChannelConnector extends AbstractNIOConnector {
    protected ServerSocketChannel _acceptChannel;
    private int _localPort = -1;
    private int _lowResourcesConnections;
    private int _lowResourcesMaxIdleTime;
    private final SelectorManager _manager = new ConnectorSelectorManager();

    public SelectChannelConnector() {
        this._manager.setMaxIdleTime((long) getMaxIdleTime());
        addBean(this._manager, true);
        setAcceptors(Math.max(1, (Runtime.getRuntime().availableProcessors() + 3) / 4));
    }

    public void setThreadPool(ThreadPool threadPool) {
        super.setThreadPool(threadPool);
        removeBean(this._manager);
        addBean(this._manager, true);
    }

    public void accept(int i) throws IOException {
        ServerSocketChannel serverSocketChannel;
        synchronized (this) {
            serverSocketChannel = this._acceptChannel;
        }
        if (serverSocketChannel != null && serverSocketChannel.isOpen() && this._manager.isStarted()) {
            SocketChannel accept = serverSocketChannel.accept();
            accept.configureBlocking(false);
            configure(accept.socket());
            this._manager.register(accept);
        }
    }

    public void close() throws IOException {
        synchronized (this) {
            if (this._acceptChannel != null) {
                removeBean(this._acceptChannel);
                if (this._acceptChannel.isOpen()) {
                    this._acceptChannel.close();
                }
            }
            this._acceptChannel = null;
            this._localPort = -2;
        }
    }

    public void customize(EndPoint endPoint, Request request) throws IOException {
        request.setTimeStamp(System.currentTimeMillis());
        endPoint.setMaxIdleTime(this._maxIdleTime);
        super.customize(endPoint, request);
    }

    public void persist(EndPoint endPoint) throws IOException {
        ((AsyncEndPoint) endPoint).setCheckForIdle(true);
        super.persist(endPoint);
    }

    public SelectorManager getSelectorManager() {
        return this._manager;
    }

    public synchronized Object getConnection() {
        return this._acceptChannel;
    }

    public int getLocalPort() {
        int i;
        synchronized (this) {
            i = this._localPort;
        }
        return i;
    }

    public void open() throws IOException {
        synchronized (this) {
            if (this._acceptChannel == null) {
                this._acceptChannel = ServerSocketChannel.open();
                this._acceptChannel.configureBlocking(true);
                this._acceptChannel.socket().setReuseAddress(getReuseAddress());
                this._acceptChannel.socket().bind(getHost() == null ? new InetSocketAddress(getPort()) : new InetSocketAddress(getHost(), getPort()), getAcceptQueueSize());
                this._localPort = this._acceptChannel.socket().getLocalPort();
                if (this._localPort > 0) {
                    addBean(this._acceptChannel);
                } else {
                    throw new IOException("Server channel not bound");
                }
            }
        }
    }

    public void setMaxIdleTime(int i) {
        this._manager.setMaxIdleTime((long) i);
        super.setMaxIdleTime(i);
    }

    public int getLowResourcesConnections() {
        return this._lowResourcesConnections;
    }

    public void setLowResourcesConnections(int i) {
        this._lowResourcesConnections = i;
    }

    public int getLowResourcesMaxIdleTime() {
        return this._lowResourcesMaxIdleTime;
    }

    public void setLowResourcesMaxIdleTime(int i) {
        this._lowResourcesMaxIdleTime = i;
        super.setLowResourcesMaxIdleTime(i);
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        this._manager.setSelectSets(getAcceptors());
        this._manager.setMaxIdleTime((long) getMaxIdleTime());
        this._manager.setLowResourcesConnections((long) getLowResourcesConnections());
        this._manager.setLowResourcesMaxIdleTime((long) getLowResourcesMaxIdleTime());
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey) throws IOException {
        SelectChannelEndPoint selectChannelEndPoint = new SelectChannelEndPoint(socketChannel, selectSet, selectionKey, this._maxIdleTime);
        selectChannelEndPoint.setConnection(selectSet.getManager().newConnection(socketChannel, selectChannelEndPoint, selectionKey.attachment()));
        return selectChannelEndPoint;
    }

    /* access modifiers changed from: protected */
    public void endPointClosed(SelectChannelEndPoint selectChannelEndPoint) {
        connectionClosed(selectChannelEndPoint.getConnection());
    }

    /* access modifiers changed from: protected */
    public AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint) {
        return new AsyncHttpConnection(this, asyncEndPoint, getServer());
    }

    private final class ConnectorSelectorManager extends SelectorManager {
        private ConnectorSelectorManager() {
        }

        public boolean dispatch(Runnable runnable) {
            ThreadPool threadPool = SelectChannelConnector.this.getThreadPool();
            if (threadPool == null) {
                threadPool = SelectChannelConnector.this.getServer().getThreadPool();
            }
            return threadPool.dispatch(runnable);
        }

        /* access modifiers changed from: protected */
        public void endPointClosed(SelectChannelEndPoint selectChannelEndPoint) {
            SelectChannelConnector.this.endPointClosed(selectChannelEndPoint);
        }

        /* access modifiers changed from: protected */
        public void endPointOpened(SelectChannelEndPoint selectChannelEndPoint) {
            SelectChannelConnector.this.connectionOpened(selectChannelEndPoint.getConnection());
        }

        /* access modifiers changed from: protected */
        public void endPointUpgraded(ConnectedEndPoint connectedEndPoint, Connection connection) {
            SelectChannelConnector.this.connectionUpgraded(connection, connectedEndPoint.getConnection());
        }

        public AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint, Object obj) {
            return SelectChannelConnector.this.newConnection(socketChannel, asyncEndPoint);
        }

        /* access modifiers changed from: protected */
        public SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey) throws IOException {
            return SelectChannelConnector.this.newEndPoint(socketChannel, selectSet, selectionKey);
        }
    }
}
