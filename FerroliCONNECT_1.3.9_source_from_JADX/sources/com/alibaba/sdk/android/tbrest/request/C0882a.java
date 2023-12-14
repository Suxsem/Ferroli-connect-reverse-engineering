package com.alibaba.sdk.android.tbrest.request;

import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.taobao.accs.common.Constants;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: com.alibaba.sdk.android.tbrest.request.a */
/* compiled from: RestSslSocketFactory */
public class C0882a extends SSLSocketFactory {

    /* renamed from: a */
    private Method f1337a = null;

    /* renamed from: j */
    private String f1338j;

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

    public C0882a(String str) {
        this.f1338j = str;
    }

    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        if (this.f1338j == null) {
            this.f1338j = str;
        }
        LogUtil.m1029d(Constants.KEY_HOST + this.f1338j + "port" + i + "autoClose" + z);
        InetAddress inetAddress = socket.getInetAddress();
        if (z) {
            socket.close();
        }
        SSLCertificateSocketFactory sSLCertificateSocketFactory = (SSLCertificateSocketFactory) SSLCertificateSocketFactory.getDefault(0);
        SSLSocket sSLSocket = (SSLSocket) sSLCertificateSocketFactory.createSocket(inetAddress, i);
        sSLSocket.setEnabledProtocols(sSLSocket.getSupportedProtocols());
        if (Build.VERSION.SDK_INT >= 17) {
            sSLCertificateSocketFactory.setHostname(sSLSocket, this.f1338j);
        } else {
            try {
                if (this.f1337a == null) {
                    this.f1337a = sSLSocket.getClass().getMethod("setHostname", new Class[]{String.class});
                    this.f1337a.setAccessible(true);
                }
                this.f1337a.invoke(sSLSocket, new Object[]{this.f1338j});
            } catch (Exception e) {
                LogUtil.m1033w("SNI not useable", e);
            }
        }
        sSLSocket.getSession();
        return sSLSocket;
    }
}
