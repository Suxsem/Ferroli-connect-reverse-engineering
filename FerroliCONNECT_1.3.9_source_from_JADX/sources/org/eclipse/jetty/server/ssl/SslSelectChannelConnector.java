package org.eclipse.jetty.server.ssl;

import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.BuffersFactory;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.RuntimeIOException;
import org.eclipse.jetty.p119io.nio.AsyncConnection;
import org.eclipse.jetty.p119io.nio.SslConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class SslSelectChannelConnector extends SelectChannelConnector implements SslConnector {
    private Buffers _sslBuffers;
    private final SslContextFactory _sslContextFactory;

    public SslSelectChannelConnector() {
        this(new SslContextFactory(SslContextFactory.DEFAULT_KEYSTORE_PATH));
        setSoLingerTime(RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT);
    }

    public SslSelectChannelConnector(SslContextFactory sslContextFactory) {
        this._sslContextFactory = sslContextFactory;
        addBean(this._sslContextFactory);
        setUseDirectBuffers(false);
        setSoLingerTime(RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT);
    }

    public void customize(EndPoint endPoint, Request request) throws IOException {
        request.setScheme("https");
        super.customize(endPoint, request);
        SslCertificates.customize(((SslConnection.SslEndPoint) endPoint).getSslEngine().getSession(), endPoint, request);
    }

    @Deprecated
    public boolean isAllowRenegotiate() {
        return this._sslContextFactory.isAllowRenegotiate();
    }

    @Deprecated
    public void setAllowRenegotiate(boolean z) {
        this._sslContextFactory.setAllowRenegotiate(z);
    }

    @Deprecated
    public String[] getExcludeCipherSuites() {
        return this._sslContextFactory.getExcludeCipherSuites();
    }

    @Deprecated
    public void setExcludeCipherSuites(String[] strArr) {
        this._sslContextFactory.setExcludeCipherSuites(strArr);
    }

    @Deprecated
    public String[] getIncludeCipherSuites() {
        return this._sslContextFactory.getIncludeCipherSuites();
    }

    @Deprecated
    public void setIncludeCipherSuites(String[] strArr) {
        this._sslContextFactory.setIncludeCipherSuites(strArr);
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
    public void setKeyPassword(String str) {
        this._sslContextFactory.setKeyManagerPassword(str);
    }

    @Deprecated
    public String getAlgorithm() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void setAlgorithm(String str) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public String getProtocol() {
        return this._sslContextFactory.getProtocol();
    }

    @Deprecated
    public void setProtocol(String str) {
        this._sslContextFactory.setProtocol(str);
    }

    @Deprecated
    public void setKeystore(String str) {
        this._sslContextFactory.setKeyStorePath(str);
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
    public boolean getWantClientAuth() {
        return this._sslContextFactory.getWantClientAuth();
    }

    @Deprecated
    public void setNeedClientAuth(boolean z) {
        this._sslContextFactory.setNeedClientAuth(z);
    }

    @Deprecated
    public void setWantClientAuth(boolean z) {
        this._sslContextFactory.setWantClientAuth(z);
    }

    @Deprecated
    public void setKeystoreType(String str) {
        this._sslContextFactory.setKeyStoreType(str);
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

    @Deprecated
    public String getTruststoreType() {
        return this._sslContextFactory.getTrustStoreType();
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

    public SslContextFactory getSslContextFactory() {
        return this._sslContextFactory;
    }

    public boolean isConfidential(Request request) {
        int confidentialPort = getConfidentialPort();
        return confidentialPort == 0 || confidentialPort == request.getServerPort();
    }

    public boolean isIntegral(Request request) {
        int integralPort = getIntegralPort();
        return integralPort == 0 || integralPort == request.getServerPort();
    }

    /* access modifiers changed from: protected */
    public AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint) {
        try {
            SslConnection newSslConnection = newSslConnection(asyncEndPoint, createSSLEngine(socketChannel));
            newSslConnection.getSslEndPoint().setConnection(newPlainConnection(socketChannel, newSslConnection.getSslEndPoint()));
            newSslConnection.setAllowRenegotiate(this._sslContextFactory.isAllowRenegotiate());
            return newSslConnection;
        } catch (IOException e) {
            throw new RuntimeIOException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public AsyncConnection newPlainConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint) {
        return super.newConnection(socketChannel, asyncEndPoint);
    }

    /* access modifiers changed from: protected */
    public SslConnection newSslConnection(AsyncEndPoint asyncEndPoint, SSLEngine sSLEngine) {
        return new SslConnection(sSLEngine, asyncEndPoint);
    }

    /* access modifiers changed from: protected */
    public SSLEngine createSSLEngine(SocketChannel socketChannel) throws IOException {
        SSLEngine sSLEngine;
        if (socketChannel != null) {
            sSLEngine = this._sslContextFactory.newSslEngine(socketChannel.socket().getInetAddress().getHostAddress(), socketChannel.socket().getPort());
        } else {
            sSLEngine = this._sslContextFactory.newSslEngine();
        }
        sSLEngine.setUseClientMode(false);
        return sSLEngine;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        this._sslContextFactory.checkKeyStore();
        this._sslContextFactory.start();
        SSLEngine newSslEngine = this._sslContextFactory.newSslEngine();
        newSslEngine.setUseClientMode(false);
        SSLSession session = newSslEngine.getSession();
        this._sslBuffers = BuffersFactory.newBuffers(getUseDirectBuffers() ? Buffers.Type.DIRECT : Buffers.Type.INDIRECT, session.getApplicationBufferSize(), getUseDirectBuffers() ? Buffers.Type.DIRECT : Buffers.Type.INDIRECT, session.getApplicationBufferSize(), getUseDirectBuffers() ? Buffers.Type.DIRECT : Buffers.Type.INDIRECT, getMaxBuffers());
        if (getRequestHeaderSize() < session.getApplicationBufferSize()) {
            setRequestHeaderSize(session.getApplicationBufferSize());
        }
        if (getRequestBufferSize() < session.getApplicationBufferSize()) {
            setRequestBufferSize(session.getApplicationBufferSize());
        }
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        this._sslBuffers = null;
        super.doStop();
    }

    public Buffers getSslBuffers() {
        return this._sslBuffers;
    }
}
