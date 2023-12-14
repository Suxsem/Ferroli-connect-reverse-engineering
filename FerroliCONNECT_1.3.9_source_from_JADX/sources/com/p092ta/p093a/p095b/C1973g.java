package com.p092ta.p093a.p095b;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* renamed from: com.ta.a.b.g */
class C1973g implements X509TrustManager {

    /* renamed from: a */
    private static TrustManager[] f3158a;

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
    }

    C1973g() {
    }

    static synchronized TrustManager[] getTrustManagers() {
        TrustManager[] trustManagerArr;
        synchronized (C1973g.class) {
            if (f3158a == null) {
                f3158a = new TrustManager[]{new C1973g()};
            }
            trustManagerArr = f3158a;
        }
        return trustManagerArr;
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (x509CertificateArr == null || x509CertificateArr.length <= 0) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
        }
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance("X509");
            instance.init((KeyStore) null);
            if (instance != null && instance.getTrustManagers() != null) {
                TrustManager[] trustManagers = instance.getTrustManagers();
                int length = trustManagers.length;
                int i = 0;
                while (i < length) {
                    try {
                        ((X509TrustManager) trustManagers[i]).checkServerTrusted(x509CertificateArr, str);
                        i++;
                    } catch (CertificateException e) {
                        Throwable th = e;
                        while (th != null) {
                            if (!(th instanceof CertificateExpiredException) && !(th instanceof CertificateNotYetValidException)) {
                                th = th.getCause();
                            } else {
                                return;
                            }
                        }
                        throw e;
                    }
                }
            }
        } catch (NoSuchAlgorithmException e2) {
            throw new CertificateException(e2);
        } catch (KeyStoreException e3) {
            throw new CertificateException(e3);
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
