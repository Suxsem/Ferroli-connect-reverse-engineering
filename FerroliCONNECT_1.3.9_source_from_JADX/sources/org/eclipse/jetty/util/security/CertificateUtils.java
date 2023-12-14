package org.eclipse.jetty.util.security;

import java.io.InputStream;
import java.security.KeyStore;
import org.eclipse.jetty.util.resource.Resource;

public class CertificateUtils {
    public static KeyStore getKeyStore(InputStream inputStream, String str, String str2, String str3, String str4) throws Exception {
        KeyStore keyStore;
        char[] cArr = null;
        if (inputStream == null && str == null) {
            return null;
        }
        if (inputStream == null) {
            try {
                inputStream = Resource.newResource(str).getInputStream();
            } catch (Throwable th) {
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        }
        if (str3 != null) {
            keyStore = KeyStore.getInstance(str2, str3);
        } else {
            keyStore = KeyStore.getInstance(str2);
        }
        if (str4 != null) {
            cArr = str4.toCharArray();
        }
        keyStore.load(inputStream, cArr);
        if (inputStream == null) {
            return keyStore;
        }
        inputStream.close();
        return keyStore;
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [java.util.Collection<? extends java.security.cert.CRL>] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Collection<? extends java.security.cert.CRL> loadCRL(java.lang.String r1) throws java.lang.Exception {
        /*
            r0 = 0
            if (r1 == 0) goto L_0x0022
            org.eclipse.jetty.util.resource.Resource r1 = org.eclipse.jetty.util.resource.Resource.newResource((java.lang.String) r1)     // Catch:{ all -> 0x001b }
            java.io.InputStream r0 = r1.getInputStream()     // Catch:{ all -> 0x001b }
            java.lang.String r1 = "X.509"
            java.security.cert.CertificateFactory r1 = java.security.cert.CertificateFactory.getInstance(r1)     // Catch:{ all -> 0x001b }
            java.util.Collection r1 = r1.generateCRLs(r0)     // Catch:{ all -> 0x001b }
            if (r0 == 0) goto L_0x0023
            r0.close()
            goto L_0x0023
        L_0x001b:
            r1 = move-exception
            if (r0 == 0) goto L_0x0021
            r0.close()
        L_0x0021:
            throw r1
        L_0x0022:
            r1 = r0
        L_0x0023:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.security.CertificateUtils.loadCRL(java.lang.String):java.util.Collection");
    }
}
