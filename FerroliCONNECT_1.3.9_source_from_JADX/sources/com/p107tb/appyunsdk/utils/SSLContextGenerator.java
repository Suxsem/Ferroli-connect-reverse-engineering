package com.p107tb.appyunsdk.utils;

import java.io.Closeable;
import java.io.IOException;

/* renamed from: com.tb.appyunsdk.utils.SSLContextGenerator */
public class SSLContextGenerator {
    private static final String ALGORITHM = "X509";
    private static final String KEY_TYPE = "BKS";

    /* JADX WARNING: Unknown top exception splitter block from list: {B:37:0x0092=Splitter:B:37:0x0092, B:53:0x00c2=Splitter:B:53:0x00c2, B:33:0x0086=Splitter:B:33:0x0086, B:49:0x00b6=Splitter:B:49:0x00b6, B:45:0x00aa=Splitter:B:45:0x00aa, B:41:0x009e=Splitter:B:41:0x009e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static javax.net.ssl.SSLContext getSslContext(java.io.InputStream r7, java.io.InputStream r8, java.lang.String r9) {
        /*
            java.lang.String r0 = "X509"
            java.lang.String r1 = "BKS"
            r2 = 0
            r3 = 1
            r4 = 0
            if (r8 == 0) goto L_0x001c
            java.security.KeyStore r5 = java.security.KeyStore.getInstance(r1)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            char[] r6 = r9.toCharArray()     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            r5.load(r8, r6)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            javax.net.ssl.TrustManagerFactory r6 = javax.net.ssl.TrustManagerFactory.getInstance(r0)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            r6.init(r5)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            goto L_0x001d
        L_0x001c:
            r6 = r4
        L_0x001d:
            if (r7 == 0) goto L_0x0036
            javax.net.ssl.KeyManagerFactory r0 = javax.net.ssl.KeyManagerFactory.getInstance(r0)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            java.security.KeyStore r1 = java.security.KeyStore.getInstance(r1)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            char[] r5 = r9.toCharArray()     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            r1.load(r7, r5)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            char[] r7 = r9.toCharArray()     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            r0.init(r1, r7)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            goto L_0x0037
        L_0x0036:
            r0 = r4
        L_0x0037:
            java.lang.String r7 = "TLS"
            javax.net.ssl.SSLContext r7 = javax.net.ssl.SSLContext.getInstance(r7)     // Catch:{ NoSuchAlgorithmException -> 0x00c1, KeyStoreException -> 0x00b5, IOException -> 0x00a9, CertificateException -> 0x009d, KeyManagementException -> 0x0091, UnrecoverableKeyException -> 0x0085 }
            if (r0 == 0) goto L_0x004d
            if (r6 == 0) goto L_0x004d
            javax.net.ssl.KeyManager[] r9 = r0.getKeyManagers()     // Catch:{ NoSuchAlgorithmException -> 0x006b, KeyStoreException -> 0x0067, IOException -> 0x0063, CertificateException -> 0x005f, KeyManagementException -> 0x005b, UnrecoverableKeyException -> 0x0057 }
            javax.net.ssl.TrustManager[] r0 = r6.getTrustManagers()     // Catch:{ NoSuchAlgorithmException -> 0x006b, KeyStoreException -> 0x0067, IOException -> 0x0063, CertificateException -> 0x005f, KeyManagementException -> 0x005b, UnrecoverableKeyException -> 0x0057 }
            r7.init(r9, r0, r4)     // Catch:{ NoSuchAlgorithmException -> 0x006b, KeyStoreException -> 0x0067, IOException -> 0x0063, CertificateException -> 0x005f, KeyManagementException -> 0x005b, UnrecoverableKeyException -> 0x0057 }
            goto L_0x007a
        L_0x004d:
            if (r0 == 0) goto L_0x006f
            javax.net.ssl.KeyManager[] r9 = r0.getKeyManagers()     // Catch:{ NoSuchAlgorithmException -> 0x006b, KeyStoreException -> 0x0067, IOException -> 0x0063, CertificateException -> 0x005f, KeyManagementException -> 0x005b, UnrecoverableKeyException -> 0x0057 }
            r7.init(r9, r4, r4)     // Catch:{ NoSuchAlgorithmException -> 0x006b, KeyStoreException -> 0x0067, IOException -> 0x0063, CertificateException -> 0x005f, KeyManagementException -> 0x005b, UnrecoverableKeyException -> 0x0057 }
            goto L_0x007a
        L_0x0057:
            r9 = move-exception
            r4 = r7
            r7 = r9
            goto L_0x0086
        L_0x005b:
            r9 = move-exception
            r4 = r7
            r7 = r9
            goto L_0x0092
        L_0x005f:
            r9 = move-exception
            r4 = r7
            r7 = r9
            goto L_0x009e
        L_0x0063:
            r9 = move-exception
            r4 = r7
            r7 = r9
            goto L_0x00aa
        L_0x0067:
            r9 = move-exception
            r4 = r7
            r7 = r9
            goto L_0x00b6
        L_0x006b:
            r9 = move-exception
            r4 = r7
            r7 = r9
            goto L_0x00c2
        L_0x006f:
            if (r6 == 0) goto L_0x0079
            javax.net.ssl.TrustManager[] r9 = r6.getTrustManagers()     // Catch:{ NoSuchAlgorithmException -> 0x006b, KeyStoreException -> 0x0067, IOException -> 0x0063, CertificateException -> 0x005f, KeyManagementException -> 0x005b, UnrecoverableKeyException -> 0x0057 }
            r7.init(r4, r9, r4)     // Catch:{ NoSuchAlgorithmException -> 0x006b, KeyStoreException -> 0x0067, IOException -> 0x0063, CertificateException -> 0x005f, KeyManagementException -> 0x005b, UnrecoverableKeyException -> 0x0057 }
            goto L_0x007a
        L_0x0079:
            r7 = r4
        L_0x007a:
            java.io.Closeable[] r9 = new java.io.Closeable[r3]
            r9[r2] = r8
            closeStream(r9)
            r4 = r7
            goto L_0x00cc
        L_0x0083:
            r7 = move-exception
            goto L_0x00cd
        L_0x0085:
            r7 = move-exception
        L_0x0086:
            r7.printStackTrace()     // Catch:{ all -> 0x0083 }
            java.io.Closeable[] r7 = new java.io.Closeable[r3]
            r7[r2] = r8
            closeStream(r7)
            goto L_0x00cc
        L_0x0091:
            r7 = move-exception
        L_0x0092:
            r7.printStackTrace()     // Catch:{ all -> 0x0083 }
            java.io.Closeable[] r7 = new java.io.Closeable[r3]
            r7[r2] = r8
            closeStream(r7)
            goto L_0x00cc
        L_0x009d:
            r7 = move-exception
        L_0x009e:
            r7.printStackTrace()     // Catch:{ all -> 0x0083 }
            java.io.Closeable[] r7 = new java.io.Closeable[r3]
            r7[r2] = r8
            closeStream(r7)
            goto L_0x00cc
        L_0x00a9:
            r7 = move-exception
        L_0x00aa:
            r7.printStackTrace()     // Catch:{ all -> 0x0083 }
            java.io.Closeable[] r7 = new java.io.Closeable[r3]
            r7[r2] = r8
            closeStream(r7)
            goto L_0x00cc
        L_0x00b5:
            r7 = move-exception
        L_0x00b6:
            r7.printStackTrace()     // Catch:{ all -> 0x0083 }
            java.io.Closeable[] r7 = new java.io.Closeable[r3]
            r7[r2] = r8
            closeStream(r7)
            goto L_0x00cc
        L_0x00c1:
            r7 = move-exception
        L_0x00c2:
            r7.printStackTrace()     // Catch:{ all -> 0x0083 }
            java.io.Closeable[] r7 = new java.io.Closeable[r3]
            r7[r2] = r8
            closeStream(r7)
        L_0x00cc:
            return r4
        L_0x00cd:
            java.io.Closeable[] r9 = new java.io.Closeable[r3]
            r9[r2] = r8
            closeStream(r9)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p107tb.appyunsdk.utils.SSLContextGenerator.getSslContext(java.io.InputStream, java.io.InputStream, java.lang.String):javax.net.ssl.SSLContext");
    }

    private static void closeStream(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
