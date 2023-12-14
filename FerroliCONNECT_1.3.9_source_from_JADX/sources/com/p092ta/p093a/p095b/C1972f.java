package com.p092ta.p093a.p095b;

import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import android.text.TextUtils;
import com.p092ta.p093a.p096c.C1982f;
import com.taobao.accs.common.Constants;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: com.ta.a.b.f */
class C1972f extends SSLSocketFactory {

    /* renamed from: a */
    private Method f3156a = null;

    /* renamed from: c */
    private String f3157c;
    private HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

    public Socket createSocket() throws IOException {
        return null;
    }

    public Socket createSocket(String str, int i) throws IOException {
        return null;
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        return null;
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return null;
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return null;
    }

    public C1972f(String str) {
        this.f3157c = str;
    }

    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        SSLSocket sSLSocket;
        C1982f.m3371b("", "peerHost", this.f3157c, Constants.KEY_HOST, str, "port", Integer.valueOf(i), "autoClose", Boolean.valueOf(z));
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        if (Build.VERSION.SDK_INT < 24) {
            sSLCertificateSocketFactory.setTrustManagers(C1973g.getTrustManagers());
        } else {
            sSLCertificateSocketFactory.setTrustManagers(C1969c.getTrustManagers());
        }
        C1982f.m3366a("", "createSocket");
        if (Build.VERSION.SDK_INT < 23) {
            InetAddress inetAddress = socket.getInetAddress();
            if (z) {
                socket.close();
            }
            sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(inetAddress, i);
        } else {
            sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(socket, this.f3157c, i, true);
        }
        C1982f.m3366a("", "createSocket end");
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        if (Build.VERSION.SDK_INT >= 17) {
            sSLCertificateSocketFactory.setHostname(sSLSocket, this.f3157c);
        } else {
            try {
                if (this.f3156a == null) {
                    this.f3156a = sSLSocket.getClass().getMethod("setHostname", new Class[]{String.class});
                    this.f3156a.setAccessible(true);
                }
                this.f3156a.invoke(sSLSocket, new Object[]{this.f3157c});
            } catch (Exception e) {
                C1982f.m3366a("", "SNI not useable", e);
            }
        }
        SSLSession session = sSLSocket.getSession();
        if (this.hostnameVerifier.verify(this.f3157c, session)) {
            C1982f.m3371b("", "SSLSession PeerHost", session.getPeerHost());
            return sSLSocket;
        }
        throw new SSLPeerUnverifiedException("Cannot verify hostname: " + this.f3157c);
    }

    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(this.f3157c) || !(obj instanceof C1972f)) {
            return false;
        }
        String str = ((C1972f) obj).f3157c;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return this.f3157c.equals(str);
    }
}
