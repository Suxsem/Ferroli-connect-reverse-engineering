package anet.channel.util;

import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import com.taobao.accs.common.Constants;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: anet.channel.util.j */
/* compiled from: Taobao */
public class C0611j extends SSLSocketFactory {

    /* renamed from: a */
    private final String f595a = "awcn.TlsSniSocketFactory";

    /* renamed from: b */
    private Method f596b = null;

    /* renamed from: c */
    private String f597c;

    public Socket createSocket() throws IOException {
        return null;
    }

    public Socket createSocket(String str, int i) throws IOException, UnknownHostException {
        return null;
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException, UnknownHostException {
        return null;
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return null;
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return null;
    }

    public C0611j(String str) {
        this.f597c = str;
    }

    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        if (this.f597c == null) {
            this.f597c = str;
        }
        if (ALog.isPrintLog(1)) {
            ALog.m328i("awcn.TlsSniSocketFactory", "customized createSocket", (String) null, Constants.KEY_HOST, this.f597c);
        }
        InetAddress inetAddress = socket.getInetAddress();
        if (z) {
            socket.close();
        }
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        SSLSocket sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(inetAddress, i);
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        if (Build.VERSION.SDK_INT >= 17) {
            sSLCertificateSocketFactory.setHostname(sSLSocket, this.f597c);
        } else {
            try {
                if (this.f596b == null) {
                    this.f596b = sSLSocket.getClass().getMethod("setHostname", new Class[]{String.class});
                    this.f596b.setAccessible(true);
                }
                this.f596b.invoke(sSLSocket, new Object[]{this.f597c});
            } catch (Exception e) {
                ALog.m329w("awcn.TlsSniSocketFactory", "SNI not useable", (String) null, e, new Object[0]);
            }
        }
        SSLSession session = sSLSocket.getSession();
        if (ALog.isPrintLog(1)) {
            ALog.m325d("awcn.TlsSniSocketFactory", (String) null, (String) null, "SSLSession PeerHost", session.getPeerHost());
        }
        return sSLSocket;
    }
}
