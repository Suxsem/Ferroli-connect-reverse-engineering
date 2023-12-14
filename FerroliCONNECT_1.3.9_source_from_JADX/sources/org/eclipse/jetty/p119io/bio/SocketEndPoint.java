package org.eclipse.jetty.p119io.bio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* renamed from: org.eclipse.jetty.io.bio.SocketEndPoint */
public class SocketEndPoint extends StreamEndPoint {
    private static final Logger LOG = Log.getLogger((Class<?>) SocketEndPoint.class);
    final InetSocketAddress _local = ((InetSocketAddress) this._socket.getLocalSocketAddress());
    final InetSocketAddress _remote = ((InetSocketAddress) this._socket.getRemoteSocketAddress());
    final Socket _socket;

    public SocketEndPoint(Socket socket) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
        this._socket = socket;
        super.setMaxIdleTime(this._socket.getSoTimeout());
    }

    protected SocketEndPoint(Socket socket, int i) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
        this._socket = socket;
        this._socket.setSoTimeout(i > 0 ? i : 0);
        super.setMaxIdleTime(i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1._socket;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isOpen() {
        /*
            r1 = this;
            boolean r0 = super.isOpen()
            if (r0 == 0) goto L_0x0012
            java.net.Socket r0 = r1._socket
            if (r0 == 0) goto L_0x0012
            boolean r0 = r0.isClosed()
            if (r0 != 0) goto L_0x0012
            r0 = 1
            goto L_0x0013
        L_0x0012:
            r0 = 0
        L_0x0013:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.bio.SocketEndPoint.isOpen():boolean");
    }

    public boolean isInputShutdown() {
        Socket socket = this._socket;
        if (socket instanceof SSLSocket) {
            return super.isInputShutdown();
        }
        return socket.isClosed() || this._socket.isInputShutdown();
    }

    public boolean isOutputShutdown() {
        Socket socket = this._socket;
        if (socket instanceof SSLSocket) {
            return super.isOutputShutdown();
        }
        return socket.isClosed() || this._socket.isOutputShutdown();
    }

    /* access modifiers changed from: protected */
    public final void shutdownSocketOutput() throws IOException {
        if (!this._socket.isClosed()) {
            if (!this._socket.isOutputShutdown()) {
                this._socket.shutdownOutput();
            }
            if (this._socket.isInputShutdown()) {
                this._socket.close();
            }
        }
    }

    public void shutdownOutput() throws IOException {
        if (this._socket instanceof SSLSocket) {
            super.shutdownOutput();
        } else {
            shutdownSocketOutput();
        }
    }

    public void shutdownSocketInput() throws IOException {
        if (!this._socket.isClosed()) {
            if (!this._socket.isInputShutdown()) {
                this._socket.shutdownInput();
            }
            if (this._socket.isOutputShutdown()) {
                this._socket.close();
            }
        }
    }

    public void shutdownInput() throws IOException {
        if (this._socket instanceof SSLSocket) {
            super.shutdownInput();
        } else {
            shutdownSocketInput();
        }
    }

    public void close() throws IOException {
        this._socket.close();
        this._in = null;
        this._out = null;
    }

    public String getLocalAddr() {
        InetSocketAddress inetSocketAddress = this._local;
        return (inetSocketAddress == null || inetSocketAddress.getAddress() == null || this._local.getAddress().isAnyLocalAddress()) ? StringUtil.ALL_INTERFACES : this._local.getAddress().getHostAddress();
    }

    public String getLocalHost() {
        InetSocketAddress inetSocketAddress = this._local;
        return (inetSocketAddress == null || inetSocketAddress.getAddress() == null || this._local.getAddress().isAnyLocalAddress()) ? StringUtil.ALL_INTERFACES : this._local.getAddress().getCanonicalHostName();
    }

    public int getLocalPort() {
        InetSocketAddress inetSocketAddress = this._local;
        if (inetSocketAddress == null) {
            return -1;
        }
        return inetSocketAddress.getPort();
    }

    public String getRemoteAddr() {
        InetAddress address;
        InetSocketAddress inetSocketAddress = this._remote;
        if (inetSocketAddress == null || (address = inetSocketAddress.getAddress()) == null) {
            return null;
        }
        return address.getHostAddress();
    }

    public String getRemoteHost() {
        InetSocketAddress inetSocketAddress = this._remote;
        if (inetSocketAddress == null) {
            return null;
        }
        return inetSocketAddress.getAddress().getCanonicalHostName();
    }

    public int getRemotePort() {
        InetSocketAddress inetSocketAddress = this._remote;
        if (inetSocketAddress == null) {
            return -1;
        }
        return inetSocketAddress.getPort();
    }

    public Object getTransport() {
        return this._socket;
    }

    public void setMaxIdleTime(int i) throws IOException {
        if (i != getMaxIdleTime()) {
            this._socket.setSoTimeout(i > 0 ? i : 0);
        }
        super.setMaxIdleTime(i);
    }

    /* access modifiers changed from: protected */
    public void idleExpired() throws IOException {
        try {
            if (!isInputShutdown()) {
                shutdownInput();
            }
        } catch (IOException e) {
            LOG.ignore(e);
            this._socket.close();
        }
    }

    public String toString() {
        return this._local + " <--> " + this._remote;
    }
}
