package org.eclipse.jetty.server.ssl;

import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.RuntimeIOException;
import org.eclipse.jetty.p119io.bio.SocketEndPoint;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class SslSocketConnector extends SocketConnector implements SslConnector {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) SslSocketConnector.class);
    private int _handshakeTimeout;
    /* access modifiers changed from: private */
    public final SslContextFactory _sslContextFactory;

    public SslSocketConnector() {
        this(new SslContextFactory(SslContextFactory.DEFAULT_KEYSTORE_PATH));
        setSoLingerTime(RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT);
    }

    public SslSocketConnector(SslContextFactory sslContextFactory) {
        this._handshakeTimeout = 0;
        this._sslContextFactory = sslContextFactory;
    }

    public boolean isAllowRenegotiate() {
        return this._sslContextFactory.isAllowRenegotiate();
    }

    public void setAllowRenegotiate(boolean z) {
        this._sslContextFactory.setAllowRenegotiate(z);
    }

    public void accept(int i) throws IOException, InterruptedException {
        Socket accept = this._serverSocket.accept();
        configure(accept);
        new SslConnectorEndPoint(accept).dispatch();
    }

    /* access modifiers changed from: protected */
    public void configure(Socket socket) throws IOException {
        super.configure(socket);
    }

    public void customize(EndPoint endPoint, Request request) throws IOException {
        super.customize(endPoint, request);
        request.setScheme("https");
        SslCertificates.customize(((SSLSocket) ((SocketEndPoint) endPoint).getTransport()).getSession(), endPoint, request);
    }

    @Deprecated
    public String[] getExcludeCipherSuites() {
        return this._sslContextFactory.getExcludeCipherSuites();
    }

    @Deprecated
    public String[] getIncludeCipherSuites() {
        return this._sslContextFactory.getIncludeCipherSuites();
    }

    @Deprecated
    public String getKeystore() {
        return this._sslContextFactory.getKeyStorePath();
    }

    @Deprecated
    public String getKeystoreType() {
        return this._sslContextFactory.getKeyStoreType();
    }

    @Deprecated
    public boolean getNeedClientAuth() {
        return this._sslContextFactory.getNeedClientAuth();
    }

    @Deprecated
    public String getProtocol() {
        return this._sslContextFactory.getProtocol();
    }

    @Deprecated
    public String getProvider() {
        return this._sslContextFactory.getProvider();
    }

    @Deprecated
    public String getSecureRandomAlgorithm() {
        return this._sslContextFactory.getSecureRandomAlgorithm();
    }

    @Deprecated
    public String getSslKeyManagerFactoryAlgorithm() {
        return this._sslContextFactory.getSslKeyManagerFactoryAlgorithm();
    }

    @Deprecated
    public String getSslTrustManagerFactoryAlgorithm() {
        return this._sslContextFactory.getTrustManagerFactoryAlgorithm();
    }

    @Deprecated
    public String getTruststore() {
        return this._sslContextFactory.getTrustStore();
    }

    public SslContextFactory getSslContextFactory() {
        return this._sslContextFactory;
    }

    @Deprecated
    public String getTruststoreType() {
        return this._sslContextFactory.getTrustStoreType();
    }

    @Deprecated
    public boolean getWantClientAuth() {
        return this._sslContextFactory.getWantClientAuth();
    }

    public boolean isConfidential(Request request) {
        int confidentialPort = getConfidentialPort();
        return confidentialPort == 0 || confidentialPort == request.getServerPort();
    }

    public boolean isIntegral(Request request) {
        int integralPort = getIntegralPort();
        return integralPort == 0 || integralPort == request.getServerPort();
    }

    public void open() throws IOException {
        this._sslContextFactory.checkKeyStore();
        try {
            this._sslContextFactory.start();
            super.open();
        } catch (Exception e) {
            throw new RuntimeIOException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        this._sslContextFactory.checkKeyStore();
        this._sslContextFactory.start();
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        this._sslContextFactory.stop();
        super.doStop();
    }

    /* access modifiers changed from: protected */
    public ServerSocket newServerSocket(String str, int i, int i2) throws IOException {
        return this._sslContextFactory.newSslServerSocket(str, i, i2);
    }

    @Deprecated
    public void setExcludeCipherSuites(String[] strArr) {
        this._sslContextFactory.setExcludeCipherSuites(strArr);
    }

    @Deprecated
    public void setIncludeCipherSuites(String[] strArr) {
        this._sslContextFactory.setIncludeCipherSuites(strArr);
    }

    @Deprecated
    public void setKeyPassword(String str) {
        this._sslContextFactory.setKeyManagerPassword(str);
    }

    @Deprecated
    public void setKeystore(String str) {
        this._sslContextFactory.setKeyStorePath(str);
    }

    @Deprecated
    public void setKeystoreType(String str) {
        this._sslContextFactory.setKeyStoreType(str);
    }

    @Deprecated
    public void setNeedClientAuth(boolean z) {
        this._sslContextFactory.setNeedClientAuth(z);
    }

    @Deprecated
    public void setPassword(String str) {
        this._sslContextFactory.setKeyStorePassword(str);
    }

    @Deprecated
    public void setTrustPassword(String str) {
        this._sslContextFactory.setTrustStorePassword(str);
    }

    @Deprecated
    public void setProtocol(String str) {
        this._sslContextFactory.setProtocol(str);
    }

    @Deprecated
    public void setProvider(String str) {
        this._sslContextFactory.setProvider(str);
    }

    @Deprecated
    public void setSecureRandomAlgorithm(String str) {
        this._sslContextFactory.setSecureRandomAlgorithm(str);
    }

    @Deprecated
    public void setSslKeyManagerFactoryAlgorithm(String str) {
        this._sslContextFactory.setSslKeyManagerFactoryAlgorithm(str);
    }

    @Deprecated
    public void setSslTrustManagerFactoryAlgorithm(String str) {
        this._sslContextFactory.setTrustManagerFactoryAlgorithm(str);
    }

    @Deprecated
    public void setTruststore(String str) {
        this._sslContextFactory.setTrustStore(str);
    }

    @Deprecated
    public void setTruststoreType(String str) {
        this._sslContextFactory.setTrustStoreType(str);
    }

    @Deprecated
    public void setSslContext(SSLContext sSLContext) {
        this._sslContextFactory.setSslContext(sSLContext);
    }

    @Deprecated
    public SSLContext getSslContext() {
        return this._sslContextFactory.getSslContext();
    }

    @Deprecated
    public void setWantClientAuth(boolean z) {
        this._sslContextFactory.setWantClientAuth(z);
    }

    public void setHandshakeTimeout(int i) {
        this._handshakeTimeout = i;
    }

    public int getHandshakeTimeout() {
        return this._handshakeTimeout;
    }

    public class SslConnectorEndPoint extends SocketConnector.ConnectorEndPoint {
        public /* bridge */ /* synthetic */ void close() throws IOException {
            super.close();
        }

        public /* bridge */ /* synthetic */ void dispatch() throws IOException {
            super.dispatch();
        }

        public /* bridge */ /* synthetic */ int fill(Buffer buffer) throws IOException {
            return super.fill(buffer);
        }

        public /* bridge */ /* synthetic */ Connection getConnection() {
            return super.getConnection();
        }

        public /* bridge */ /* synthetic */ void setConnection(Connection connection) {
            super.setConnection(connection);
        }

        public SslConnectorEndPoint(Socket socket) throws IOException {
            super(socket);
        }

        public void shutdownOutput() throws IOException {
            close();
        }

        public void shutdownInput() throws IOException {
            close();
        }

        public void run() {
            try {
                int handshakeTimeout = SslSocketConnector.this.getHandshakeTimeout();
                int soTimeout = this._socket.getSoTimeout();
                if (handshakeTimeout > 0) {
                    this._socket.setSoTimeout(handshakeTimeout);
                }
                final SSLSocket sSLSocket = (SSLSocket) this._socket;
                sSLSocket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
                    boolean handshook = false;

                    public void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
                        if (!this.handshook) {
                            this.handshook = true;
                        } else if (!SslSocketConnector.this._sslContextFactory.isAllowRenegotiate()) {
                            Logger access$100 = SslSocketConnector.LOG;
                            access$100.warn("SSL renegotiate denied: " + sSLSocket, new Object[0]);
                            try {
                                sSLSocket.close();
                            } catch (IOException e) {
                                SslSocketConnector.LOG.warn(e);
                            }
                        }
                    }
                });
                sSLSocket.startHandshake();
                if (handshakeTimeout > 0) {
                    this._socket.setSoTimeout(soTimeout);
                }
                super.run();
            } catch (SSLException e) {
                SslSocketConnector.LOG.debug(e);
                try {
                    close();
                } catch (IOException e2) {
                    SslSocketConnector.LOG.ignore(e2);
                }
            } catch (IOException e3) {
                SslSocketConnector.LOG.debug(e3);
                try {
                    close();
                } catch (IOException e4) {
                    SslSocketConnector.LOG.ignore(e4);
                }
            }
        }
    }

    @Deprecated
    public String getAlgorithm() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void setAlgorithm(String str) {
        throw new UnsupportedOperationException();
    }
}
