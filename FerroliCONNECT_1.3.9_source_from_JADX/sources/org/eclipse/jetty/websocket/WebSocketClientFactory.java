package org.eclipse.jetty.websocket;

import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.net.ssl.SSLEngine;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.p119io.AbstractConnection;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.ConnectedEndPoint;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.SimpleBuffers;
import org.eclipse.jetty.p119io.nio.AsyncConnection;
import org.eclipse.jetty.p119io.nio.SelectChannelEndPoint;
import org.eclipse.jetty.p119io.nio.SelectorManager;
import org.eclipse.jetty.p119io.nio.SslConnection;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.websocket.WebSocketClient;

public class WebSocketClientFactory extends AggregateLifeCycle {
    /* access modifiers changed from: private */
    public static final ByteArrayBuffer __ACCEPT = new ByteArrayBuffer.CaseInsensitive("Sec-WebSocket-Accept");
    /* access modifiers changed from: private */
    public static final Logger __log = Log.getLogger(WebSocketClientFactory.class.getName());
    /* access modifiers changed from: private */
    public WebSocketBuffers _buffers;
    private MaskGen _maskGen;
    private final WebSocketClientSelector _selector;
    private final SslContextFactory _sslContextFactory;
    /* access modifiers changed from: private */
    public final ThreadPool _threadPool;
    private final Queue<WebSocketConnection> connections;

    public WebSocketClientFactory() {
        this((ThreadPool) null);
    }

    public WebSocketClientFactory(ThreadPool threadPool) {
        this(threadPool, new RandomMaskGen());
    }

    public WebSocketClientFactory(ThreadPool threadPool, MaskGen maskGen) {
        this(threadPool, maskGen, 8192);
    }

    public WebSocketClientFactory(ThreadPool threadPool, MaskGen maskGen, int i) {
        this.connections = new ConcurrentLinkedQueue();
        this._sslContextFactory = new SslContextFactory();
        this._threadPool = threadPool == null ? new QueuedThreadPool() : threadPool;
        addBean(this._threadPool);
        this._buffers = new WebSocketBuffers(i);
        addBean(this._buffers);
        this._maskGen = maskGen;
        addBean(this._maskGen);
        this._selector = new WebSocketClientSelector();
        addBean(this._selector);
        addBean(this._sslContextFactory);
    }

    public SslContextFactory getSslContextFactory() {
        return this._sslContextFactory;
    }

    public SelectorManager getSelectorManager() {
        return this._selector;
    }

    public ThreadPool getThreadPool() {
        return this._threadPool;
    }

    public MaskGen getMaskGen() {
        return this._maskGen;
    }

    public void setMaskGen(MaskGen maskGen) {
        if (!isRunning()) {
            removeBean(this._maskGen);
            this._maskGen = maskGen;
            addBean(maskGen);
            return;
        }
        throw new IllegalStateException(getState());
    }

    public void setBufferSize(int i) {
        if (!isRunning()) {
            removeBean(this._buffers);
            this._buffers = new WebSocketBuffers(i);
            addBean(this._buffers);
            return;
        }
        throw new IllegalStateException(getState());
    }

    public int getBufferSize() {
        return this._buffers.getBufferSize();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        closeConnections();
        super.doStop();
    }

    public WebSocketClient newWebSocketClient() {
        return new WebSocketClient(this);
    }

    /* access modifiers changed from: protected */
    public SSLEngine newSslEngine(SocketChannel socketChannel) throws IOException {
        SSLEngine sSLEngine;
        if (socketChannel != null) {
            sSLEngine = this._sslContextFactory.newSslEngine(socketChannel.socket().getInetAddress().getHostAddress(), socketChannel.socket().getPort());
        } else {
            sSLEngine = this._sslContextFactory.newSslEngine();
        }
        sSLEngine.setUseClientMode(true);
        sSLEngine.beginHandshake();
        return sSLEngine;
    }

    /* access modifiers changed from: protected */
    public boolean addConnection(WebSocketConnection webSocketConnection) {
        return isRunning() && this.connections.add(webSocketConnection);
    }

    /* access modifiers changed from: protected */
    public boolean removeConnection(WebSocketConnection webSocketConnection) {
        return this.connections.remove(webSocketConnection);
    }

    /* access modifiers changed from: protected */
    public void closeConnections() {
        for (WebSocketConnection shutdown : this.connections) {
            shutdown.shutdown();
        }
    }

    class WebSocketClientSelector extends SelectorManager {
        /* access modifiers changed from: protected */
        public void endPointOpened(SelectChannelEndPoint selectChannelEndPoint) {
        }

        WebSocketClientSelector() {
        }

        public boolean dispatch(Runnable runnable) {
            return WebSocketClientFactory.this._threadPool.dispatch(runnable);
        }

        /* access modifiers changed from: protected */
        public SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey) throws IOException {
            AsyncEndPoint asyncEndPoint;
            WebSocketClient.WebSocketFuture webSocketFuture = (WebSocketClient.WebSocketFuture) selectionKey.attachment();
            int maxIdleTime = webSocketFuture.getMaxIdleTime();
            if (maxIdleTime < 0) {
                maxIdleTime = (int) getMaxIdleTime();
            }
            SelectChannelEndPoint selectChannelEndPoint = new SelectChannelEndPoint(socketChannel, selectSet, selectionKey, maxIdleTime);
            if ("wss".equals(webSocketFuture.getURI().getScheme())) {
                SslConnection sslConnection = new SslConnection(WebSocketClientFactory.this.newSslEngine(socketChannel), selectChannelEndPoint);
                selectChannelEndPoint.setConnection(sslConnection);
                asyncEndPoint = sslConnection.getSslEndPoint();
            } else {
                asyncEndPoint = selectChannelEndPoint;
            }
            asyncEndPoint.setConnection(selectSet.getManager().newConnection(socketChannel, asyncEndPoint, webSocketFuture));
            return selectChannelEndPoint;
        }

        public AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint, Object obj) {
            return new HandshakeConnection(asyncEndPoint, (WebSocketClient.WebSocketFuture) obj);
        }

        /* access modifiers changed from: protected */
        public void endPointUpgraded(ConnectedEndPoint connectedEndPoint, Connection connection) {
            LOG.debug("upgrade {} -> {}", connection, connectedEndPoint.getConnection());
        }

        /* access modifiers changed from: protected */
        public void endPointClosed(SelectChannelEndPoint selectChannelEndPoint) {
            selectChannelEndPoint.getConnection().onClose();
        }

        /* access modifiers changed from: protected */
        public void connectionFailed(SocketChannel socketChannel, Throwable th, Object obj) {
            if (!(obj instanceof WebSocketClient.WebSocketFuture)) {
                super.connectionFailed(socketChannel, th, obj);
                return;
            }
            WebSocketClientFactory.__log.debug(th);
            ((WebSocketClient.WebSocketFuture) obj).handshakeFailed(th);
        }
    }

    class HandshakeConnection extends AbstractConnection implements AsyncConnection {
        /* access modifiers changed from: private */
        public String _accept;
        /* access modifiers changed from: private */
        public final AsyncEndPoint _endp;
        /* access modifiers changed from: private */
        public String _error;
        private final WebSocketClient.WebSocketFuture _future;
        private ByteArrayBuffer _handshake;
        private final String _key;
        private final HttpParser _parser;

        public boolean isIdle() {
            return false;
        }

        public boolean isSuspended() {
            return false;
        }

        public HandshakeConnection(AsyncEndPoint asyncEndPoint, WebSocketClient.WebSocketFuture webSocketFuture) {
            super(asyncEndPoint, System.currentTimeMillis());
            this._endp = asyncEndPoint;
            this._future = webSocketFuture;
            byte[] bArr = new byte[16];
            new Random().nextBytes(bArr);
            this._key = new String(B64Code.encode(bArr));
            this._parser = new HttpParser(new SimpleBuffers(WebSocketClientFactory.this._buffers.getBuffer(), (Buffer) null), this._endp, new HttpParser.EventHandler(WebSocketClientFactory.this) {
                public void startResponse(Buffer buffer, int i, Buffer buffer2) throws IOException {
                    if (i != 101) {
                        HandshakeConnection handshakeConnection = HandshakeConnection.this;
                        String unused = handshakeConnection._error = "Bad response status " + i + " " + buffer2;
                        HandshakeConnection.this._endp.close();
                    }
                }

                public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException {
                    if (WebSocketClientFactory.__ACCEPT.equals(buffer)) {
                        String unused = HandshakeConnection.this._accept = buffer2.toString();
                    }
                }

                public void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
                    if (HandshakeConnection.this._error == null) {
                        HandshakeConnection handshakeConnection = HandshakeConnection.this;
                        String unused = handshakeConnection._error = "Bad response: " + buffer + " " + buffer2 + " " + buffer3;
                    }
                    HandshakeConnection.this._endp.close();
                }

                public void content(Buffer buffer) throws IOException {
                    if (HandshakeConnection.this._error == null) {
                        HandshakeConnection handshakeConnection = HandshakeConnection.this;
                        String unused = handshakeConnection._error = "Bad response. " + buffer.length() + "B of content?";
                    }
                    HandshakeConnection.this._endp.close();
                }
            });
        }

        private boolean handshake() {
            if (this._handshake == null) {
                String path = this._future.getURI().getPath();
                if (path == null || path.length() == 0) {
                    path = "/";
                }
                if (this._future.getURI().getRawQuery() != null) {
                    path = path + "?" + this._future.getURI().getRawQuery();
                }
                String origin = this._future.getOrigin();
                StringBuilder sb = new StringBuilder(512);
                sb.append("GET ");
                sb.append(path);
                sb.append(" HTTP/1.1\r\n");
                sb.append("Host: ");
                sb.append(this._future.getURI().getHost());
                sb.append(":");
                sb.append(this._future.getURI().getPort());
                sb.append("\r\n");
                sb.append("Upgrade: websocket\r\n");
                sb.append("Connection: Upgrade\r\n");
                sb.append("Sec-WebSocket-Key: ");
                sb.append(this._key);
                sb.append("\r\n");
                if (origin != null) {
                    sb.append("Origin: ");
                    sb.append(origin);
                    sb.append("\r\n");
                }
                sb.append("Sec-WebSocket-Version: ");
                sb.append(13);
                sb.append("\r\n");
                if (this._future.getProtocol() != null) {
                    sb.append("Sec-WebSocket-Protocol: ");
                    sb.append(this._future.getProtocol());
                    sb.append("\r\n");
                }
                Map<String, String> cookies = this._future.getCookies();
                if (cookies != null && cookies.size() > 0) {
                    for (String next : cookies.keySet()) {
                        sb.append("Cookie: ");
                        sb.append(QuotedStringTokenizer.quoteIfNeeded(next, HttpFields.__COOKIE_DELIM));
                        sb.append("=");
                        sb.append(QuotedStringTokenizer.quoteIfNeeded(cookies.get(next), HttpFields.__COOKIE_DELIM));
                        sb.append("\r\n");
                    }
                }
                sb.append("\r\n");
                this._handshake = new ByteArrayBuffer(sb.toString(), false);
            }
            try {
                this._handshake.length();
                if (this._endp.flush(this._handshake) < 0) {
                    throw new IOException("incomplete handshake");
                } else if (this._handshake.length() == 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                this._future.handshakeFailed(e);
            }
        }

        public Connection handle() throws IOException {
            while (this._endp.isOpen() && !this._parser.isComplete()) {
                ByteArrayBuffer byteArrayBuffer = this._handshake;
                if ((byteArrayBuffer == null || byteArrayBuffer.length() > 0) && !handshake()) {
                    return this;
                }
                if (!this._parser.parseAvailable()) {
                    if (this._endp.isInputShutdown()) {
                        this._future.handshakeFailed(new IOException("Incomplete handshake response"));
                    }
                    return this;
                }
            }
            if (this._error == null) {
                if (this._accept == null) {
                    this._error = "No Sec-WebSocket-Accept";
                } else if (!WebSocketConnectionRFC6455.hashKey(this._key).equals(this._accept)) {
                    this._error = "Bad Sec-WebSocket-Accept";
                } else {
                    WebSocketConnection newWebSocketConnection = newWebSocketConnection();
                    Buffer headerBuffer = this._parser.getHeaderBuffer();
                    if (headerBuffer.hasContent()) {
                        newWebSocketConnection.fillBuffersFrom(headerBuffer);
                    }
                    WebSocketClientFactory.this._buffers.returnBuffer(headerBuffer);
                    this._future.onConnection(newWebSocketConnection);
                    return newWebSocketConnection;
                }
            }
            this._endp.close();
            return this;
        }

        private WebSocketConnection newWebSocketConnection() throws IOException {
            WebSocketClientFactory.__log.debug("newWebSocketConnection()", new Object[0]);
            return new WebSocketClientConnection(this._future._client.getFactory(), this._future.getWebSocket(), this._endp, WebSocketClientFactory.this._buffers, System.currentTimeMillis(), this._future.getMaxIdleTime(), this._future.getProtocol(), (List<Extension>) null, 13, this._future.getMaskGen());
        }

        public void onInputShutdown() throws IOException {
            this._endp.close();
        }

        public void onClose() {
            String str = this._error;
            if (str != null) {
                this._future.handshakeFailed(new ProtocolException(str));
            } else {
                this._future.handshakeFailed(new EOFException());
            }
        }
    }

    private static class WebSocketClientConnection extends WebSocketConnectionRFC6455 {
        private final WebSocketClientFactory factory;

        public WebSocketClientConnection(WebSocketClientFactory webSocketClientFactory, WebSocket webSocket, EndPoint endPoint, WebSocketBuffers webSocketBuffers, long j, int i, String str, List<Extension> list, int i2, MaskGen maskGen) throws IOException {
            super(webSocket, endPoint, webSocketBuffers, j, i, str, list, i2, maskGen);
            this.factory = webSocketClientFactory;
        }

        public void onClose() {
            super.onClose();
            this.factory.removeConnection(this);
        }
    }
}
