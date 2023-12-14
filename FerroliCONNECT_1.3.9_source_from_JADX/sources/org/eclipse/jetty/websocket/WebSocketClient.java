package org.eclipse.jetty.websocket;

import android.support.p000v4.view.PointerIconCompat;
import com.taobao.accs.common.Constants;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.jvm.internal.LongCompanionObject;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.WebSocket;

public class WebSocketClient {
    /* access modifiers changed from: private */
    public static final Logger __log = Log.getLogger(WebSocketClient.class.getName());
    private SocketAddress _bindAddress;
    private final Map<String, String> _cookies;
    private final List<String> _extensions;
    private final WebSocketClientFactory _factory;
    private MaskGen _maskGen;
    private int _maxBinaryMessageSize;
    private int _maxIdleTime;
    private int _maxTextMessageSize;
    private String _origin;
    private String _protocol;

    @Deprecated
    public WebSocketClient() throws Exception {
        this._cookies = new ConcurrentHashMap();
        this._extensions = new CopyOnWriteArrayList();
        this._maxIdleTime = -1;
        this._maxTextMessageSize = 16384;
        this._maxBinaryMessageSize = -1;
        this._factory = new WebSocketClientFactory();
        this._factory.start();
        this._maskGen = this._factory.getMaskGen();
    }

    public WebSocketClient(WebSocketClientFactory webSocketClientFactory) {
        this._cookies = new ConcurrentHashMap();
        this._extensions = new CopyOnWriteArrayList();
        this._maxIdleTime = -1;
        this._maxTextMessageSize = 16384;
        this._maxBinaryMessageSize = -1;
        this._factory = webSocketClientFactory;
        this._maskGen = this._factory.getMaskGen();
    }

    public WebSocketClientFactory getFactory() {
        return this._factory;
    }

    public SocketAddress getBindAddress() {
        return this._bindAddress;
    }

    public void setBindAddress(SocketAddress socketAddress) {
        this._bindAddress = socketAddress;
    }

    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    public void setMaxIdleTime(int i) {
        this._maxIdleTime = i;
    }

    public String getProtocol() {
        return this._protocol;
    }

    public void setProtocol(String str) {
        this._protocol = str;
    }

    public String getOrigin() {
        return this._origin;
    }

    public void setOrigin(String str) {
        this._origin = str;
    }

    public Map<String, String> getCookies() {
        return this._cookies;
    }

    public List<String> getExtensions() {
        return this._extensions;
    }

    public MaskGen getMaskGen() {
        return this._maskGen;
    }

    public void setMaskGen(MaskGen maskGen) {
        this._maskGen = maskGen;
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

    public WebSocket.Connection open(URI uri, WebSocket webSocket, long j, TimeUnit timeUnit) throws IOException, InterruptedException, TimeoutException {
        try {
            return open(uri, webSocket).get(j, timeUnit);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else {
                throw new RuntimeException(cause);
            }
        }
    }

    public Future<WebSocket.Connection> open(URI uri, WebSocket webSocket) throws IOException {
        if (this._factory.isStarted()) {
            InetSocketAddress socketAddress = toSocketAddress(uri);
            try {
                SocketChannel open = SocketChannel.open();
                if (this._bindAddress != null) {
                    open.socket().bind(this._bindAddress);
                }
                open.socket().setTcpNoDelay(true);
                WebSocketFuture webSocketFuture = new WebSocketFuture(webSocket, uri, this, open);
                open.configureBlocking(false);
                open.connect(socketAddress);
                this._factory.getSelectorManager().register(open, webSocketFuture);
                return webSocketFuture;
            } catch (RuntimeException e) {
                C2439IO.close((Closeable) null);
                throw e;
            } catch (IOException e2) {
                C2439IO.close((Closeable) null);
                throw e2;
            }
        } else {
            throw new IllegalStateException("Factory !started");
        }
    }

    public static InetSocketAddress toSocketAddress(URI uri) {
        String scheme = uri.getScheme();
        if ("ws".equalsIgnoreCase(scheme) || "wss".equalsIgnoreCase(scheme)) {
            int port = uri.getPort();
            if (port != 0) {
                if (port < 0) {
                    port = "ws".equals(scheme) ? 80 : Constants.PORT;
                }
                return new InetSocketAddress(uri.getHost(), port);
            }
            throw new IllegalArgumentException("Bad WebSocket port: " + port);
        }
        throw new IllegalArgumentException("Bad WebSocket scheme: " + scheme);
    }

    static class WebSocketFuture implements Future<WebSocket.Connection> {
        ByteChannel _channel;
        final WebSocketClient _client;
        WebSocketConnection _connection;
        final CountDownLatch _done;
        Throwable _exception;
        final URI _uri;
        final WebSocket _websocket;

        private WebSocketFuture(WebSocket webSocket, URI uri, WebSocketClient webSocketClient, ByteChannel byteChannel) {
            this._done = new CountDownLatch(1);
            this._websocket = webSocket;
            this._uri = uri;
            this._client = webSocketClient;
            this._channel = byteChannel;
        }

        public void onConnection(WebSocketConnection webSocketConnection) {
            WebSocketConnection webSocketConnection2;
            try {
                this._client.getFactory().addConnection(webSocketConnection);
                webSocketConnection.getConnection().setMaxTextMessageSize(this._client.getMaxTextMessageSize());
                webSocketConnection.getConnection().setMaxBinaryMessageSize(this._client.getMaxBinaryMessageSize());
                synchronized (this) {
                    if (this._channel != null) {
                        this._connection = webSocketConnection;
                    }
                    webSocketConnection2 = this._connection;
                }
                if (webSocketConnection2 != null) {
                    if (this._websocket instanceof WebSocket.OnFrame) {
                        ((WebSocket.OnFrame) this._websocket).onHandshake((WebSocket.FrameConnection) webSocketConnection2.getConnection());
                    }
                    this._websocket.onOpen(webSocketConnection2.getConnection());
                }
                this._done.countDown();
            } catch (Throwable th) {
                this._done.countDown();
                throw th;
            }
        }

        public void handshakeFailed(Throwable th) {
            ByteChannel byteChannel;
            try {
                synchronized (this) {
                    if (this._channel != null) {
                        byteChannel = this._channel;
                        this._channel = null;
                        this._exception = th;
                    } else {
                        byteChannel = null;
                    }
                }
                if (byteChannel != null) {
                    if (th instanceof ProtocolException) {
                        closeChannel(byteChannel, 1002, th.getMessage());
                    } else {
                        closeChannel(byteChannel, PointerIconCompat.TYPE_CELL, th.getMessage());
                    }
                }
                this._done.countDown();
            } catch (Throwable th2) {
                this._done.countDown();
                throw th2;
            }
        }

        public Map<String, String> getCookies() {
            return this._client.getCookies();
        }

        public String getProtocol() {
            return this._client.getProtocol();
        }

        public WebSocket getWebSocket() {
            return this._websocket;
        }

        public URI getURI() {
            return this._uri;
        }

        public int getMaxIdleTime() {
            return this._client.getMaxIdleTime();
        }

        public String getOrigin() {
            return this._client.getOrigin();
        }

        public MaskGen getMaskGen() {
            return this._client.getMaskGen();
        }

        public String toString() {
            return "[" + this._uri + "," + this._websocket + "]@" + hashCode();
        }

        public boolean cancel(boolean z) {
            ByteChannel byteChannel;
            boolean z2;
            try {
                synchronized (this) {
                    if (this._connection == null && this._exception == null && this._channel != null) {
                        byteChannel = this._channel;
                        this._channel = null;
                    } else {
                        byteChannel = null;
                    }
                }
                if (byteChannel != null) {
                    closeChannel(byteChannel, PointerIconCompat.TYPE_CELL, "cancelled");
                    z2 = true;
                } else {
                    z2 = false;
                }
                this._done.countDown();
                return z2;
            } catch (Throwable th) {
                this._done.countDown();
                throw th;
            }
        }

        public boolean isCancelled() {
            boolean z;
            synchronized (this) {
                z = this._channel == null && this._connection == null;
            }
            return z;
        }

        public boolean isDone() {
            boolean z;
            synchronized (this) {
                z = this._connection != null && this._exception == null;
            }
            return z;
        }

        public WebSocket.Connection get() throws InterruptedException, ExecutionException {
            try {
                return get((long) LongCompanionObject.MAX_VALUE, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                throw new IllegalStateException("The universe has ended", e);
            }
        }

        public WebSocket.Connection get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            Throwable th;
            WebSocket.Connection connection;
            ByteChannel byteChannel;
            this._done.await(j, timeUnit);
            synchronized (this) {
                th = this._exception;
                connection = null;
                if (this._connection == null) {
                    th = this._exception;
                    byteChannel = this._channel;
                    this._channel = null;
                } else {
                    connection = this._connection.getConnection();
                    byteChannel = null;
                }
            }
            if (byteChannel != null) {
                closeChannel(byteChannel, PointerIconCompat.TYPE_CELL, "timeout");
            }
            if (th != null) {
                throw new ExecutionException(th);
            } else if (connection != null) {
                return connection;
            } else {
                throw new TimeoutException();
            }
        }

        private void closeChannel(ByteChannel byteChannel, int i, String str) {
            try {
                this._websocket.onClose(i, str);
            } catch (Exception e) {
                WebSocketClient.__log.warn(e);
            }
            try {
                byteChannel.close();
            } catch (IOException e2) {
                WebSocketClient.__log.debug(e2);
            }
        }
    }
}
