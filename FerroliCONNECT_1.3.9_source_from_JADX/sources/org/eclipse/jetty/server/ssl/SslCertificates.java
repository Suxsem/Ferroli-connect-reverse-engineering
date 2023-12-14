package org.eclipse.jetty.server.ssl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class SslCertificates {
    static final String CACHED_INFO_ATTR = CachedInfo.class.getName();
    private static final Logger LOG = Log.getLogger((Class<?>) SslCertificates.class);

    public static X509Certificate[] getCertChain(SSLSession sSLSession) {
        try {
            javax.security.cert.X509Certificate[] peerCertificateChain = sSLSession.getPeerCertificateChain();
            if (peerCertificateChain != null) {
                if (peerCertificateChain.length != 0) {
                    int length = peerCertificateChain.length;
                    X509Certificate[] x509CertificateArr = new X509Certificate[length];
                    CertificateFactory instance = CertificateFactory.getInstance("X.509");
                    for (int i = 0; i < length; i++) {
                        x509CertificateArr[i] = (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(peerCertificateChain[i].getEncoded()));
                    }
                    return x509CertificateArr;
                }
            }
            return null;
        } catch (SSLPeerUnverifiedException unused) {
            return null;
        } catch (Exception e) {
            LOG.warn(Log.EXCEPTION, (Throwable) e);
            return null;
        }
    }

    public static void customize(SSLSession sSLSession, EndPoint endPoint, Request request) throws IOException {
        Integer num;
        X509Certificate[] x509CertificateArr;
        String str;
        request.setScheme("https");
        try {
            String cipherSuite = sSLSession.getCipherSuite();
            CachedInfo cachedInfo = (CachedInfo) sSLSession.getValue(CACHED_INFO_ATTR);
            if (cachedInfo != null) {
                num = cachedInfo.getKeySize();
                x509CertificateArr = cachedInfo.getCerts();
                str = cachedInfo.getIdStr();
            } else {
                Integer num2 = new Integer(ServletSSL.deduceKeyLength(cipherSuite));
                x509CertificateArr = getCertChain(sSLSession);
                String hexString = TypeUtil.toHexString(sSLSession.getId());
                sSLSession.putValue(CACHED_INFO_ATTR, new CachedInfo(num2, x509CertificateArr, hexString));
                num = num2;
                str = hexString;
            }
            if (x509CertificateArr != null) {
                request.setAttribute("javax.servlet.request.X509Certificate", x509CertificateArr);
            }
            request.setAttribute("javax.servlet.request.cipher_suite", cipherSuite);
            request.setAttribute("javax.servlet.request.key_size", num);
            request.setAttribute("javax.servlet.request.ssl_session_id", str);
        } catch (Exception e) {
            LOG.warn(Log.EXCEPTION, (Throwable) e);
        }
    }

    private static class CachedInfo {
        private final X509Certificate[] _certs;
        private final String _idStr;
        private final Integer _keySize;

        CachedInfo(Integer num, X509Certificate[] x509CertificateArr, String str) {
            this._keySize = num;
            this._certs = x509CertificateArr;
            this._idStr = str;
        }

        /* access modifiers changed from: package-private */
        public X509Certificate[] getCerts() {
            return this._certs;
        }

        /* access modifiers changed from: package-private */
        public Integer getKeySize() {
            return this._keySize;
        }

        /* access modifiers changed from: package-private */
        public String getIdStr() {
            return this._idStr;
        }
    }
}
