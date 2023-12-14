package org.eclipse.jetty.util.ssl;

import anetwork.channel.util.RequestConstant;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.security.InvalidParameterException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.CertificateUtils;
import org.eclipse.jetty.util.security.CertificateValidator;
import org.eclipse.jetty.util.security.Password;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;

public class SslContextFactory extends AbstractLifeCycle {
    public static final String DEFAULT_KEYMANAGERFACTORY_ALGORITHM;
    public static final String DEFAULT_KEYSTORE_PATH = (System.getProperty("user.home") + File.separator + ".keystore");
    public static final String DEFAULT_TRUSTMANAGERFACTORY_ALGORITHM;
    public static final String KEYPASSWORD_PROPERTY = "org.eclipse.jetty.ssl.keypassword";
    private static final Logger LOG = Log.getLogger((Class<?>) SslContextFactory.class);
    public static final String PASSWORD_PROPERTY = "org.eclipse.jetty.ssl.password";
    public static final TrustManager[] TRUST_ALL_CERTS = {new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }};
    private boolean _allowRenegotiate;
    private String _certAlias;
    private SSLContext _context;
    private String _crlPath;
    private boolean _enableCRLDP;
    private boolean _enableOCSP;
    private final Set<String> _excludeCipherSuites;
    private final Set<String> _excludeProtocols;
    private Set<String> _includeCipherSuites;
    private Set<String> _includeProtocols;
    private String _keyManagerFactoryAlgorithm;
    private transient Password _keyManagerPassword;
    private KeyStore _keyStore;
    private InputStream _keyStoreInputStream;
    private transient Password _keyStorePassword;
    private String _keyStorePath;
    private String _keyStoreProvider;
    private String _keyStoreType;
    private int _maxCertPathLength;
    private boolean _needClientAuth;
    private String _ocspResponderURL;
    private String _secureRandomAlgorithm;
    private boolean _sessionCachingEnabled;
    private String _sslProtocol;
    private String _sslProvider;
    private int _sslSessionCacheSize;
    private int _sslSessionTimeout;
    private boolean _trustAll;
    private String _trustManagerFactoryAlgorithm;
    private KeyStore _trustStore;
    private InputStream _trustStoreInputStream;
    private transient Password _trustStorePassword;
    private String _trustStorePath;
    private String _trustStoreProvider;
    private String _trustStoreType;
    private boolean _validateCerts;
    private boolean _validatePeerCerts;
    private boolean _wantClientAuth;

    static {
        String str = "SunX509";
        DEFAULT_KEYMANAGERFACTORY_ALGORITHM = Security.getProperty(SSLSocketFactoryFactory.SYSKEYMGRALGO) == null ? str : Security.getProperty(SSLSocketFactoryFactory.SYSKEYMGRALGO);
        if (Security.getProperty(SSLSocketFactoryFactory.SYSTRUSTMGRALGO) != null) {
            str = Security.getProperty(SSLSocketFactoryFactory.SYSTRUSTMGRALGO);
        }
        DEFAULT_TRUSTMANAGERFACTORY_ALGORITHM = str;
    }

    public SslContextFactory() {
        this._excludeProtocols = new LinkedHashSet();
        this._includeProtocols = null;
        this._excludeCipherSuites = new LinkedHashSet();
        this._includeCipherSuites = null;
        this._keyStoreType = "JKS";
        this._trustStoreType = "JKS";
        this._needClientAuth = false;
        this._wantClientAuth = false;
        this._allowRenegotiate = true;
        this._sslProtocol = SSLSocketFactoryFactory.DEFAULT_PROTOCOL;
        this._keyManagerFactoryAlgorithm = DEFAULT_KEYMANAGERFACTORY_ALGORITHM;
        this._trustManagerFactoryAlgorithm = DEFAULT_TRUSTMANAGERFACTORY_ALGORITHM;
        this._maxCertPathLength = -1;
        this._enableCRLDP = false;
        this._enableOCSP = false;
        this._sessionCachingEnabled = true;
        this._trustAll = true;
    }

    public SslContextFactory(boolean z) {
        this._excludeProtocols = new LinkedHashSet();
        this._includeProtocols = null;
        this._excludeCipherSuites = new LinkedHashSet();
        this._includeCipherSuites = null;
        this._keyStoreType = "JKS";
        this._trustStoreType = "JKS";
        this._needClientAuth = false;
        this._wantClientAuth = false;
        this._allowRenegotiate = true;
        this._sslProtocol = SSLSocketFactoryFactory.DEFAULT_PROTOCOL;
        this._keyManagerFactoryAlgorithm = DEFAULT_KEYMANAGERFACTORY_ALGORITHM;
        this._trustManagerFactoryAlgorithm = DEFAULT_TRUSTMANAGERFACTORY_ALGORITHM;
        this._maxCertPathLength = -1;
        this._enableCRLDP = false;
        this._enableOCSP = false;
        this._sessionCachingEnabled = true;
        this._trustAll = z;
    }

    public SslContextFactory(String str) {
        this._excludeProtocols = new LinkedHashSet();
        this._includeProtocols = null;
        this._excludeCipherSuites = new LinkedHashSet();
        this._includeCipherSuites = null;
        this._keyStoreType = "JKS";
        this._trustStoreType = "JKS";
        this._needClientAuth = false;
        this._wantClientAuth = false;
        this._allowRenegotiate = true;
        this._sslProtocol = SSLSocketFactoryFactory.DEFAULT_PROTOCOL;
        this._keyManagerFactoryAlgorithm = DEFAULT_KEYMANAGERFACTORY_ALGORITHM;
        this._trustManagerFactoryAlgorithm = DEFAULT_TRUSTMANAGERFACTORY_ALGORITHM;
        this._maxCertPathLength = -1;
        this._enableCRLDP = false;
        this._enableOCSP = false;
        this._sessionCachingEnabled = true;
        this._keyStorePath = str;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        String str;
        TrustManager[] trustManagerArr;
        if (this._context == null) {
            SecureRandom secureRandom = null;
            if (this._keyStore == null && this._keyStoreInputStream == null && this._keyStorePath == null && this._trustStore == null && this._trustStoreInputStream == null && this._trustStorePath == null) {
                if (this._trustAll) {
                    LOG.debug("No keystore or trust store configured.  ACCEPTING UNTRUSTED CERTIFICATES!!!!!", new Object[0]);
                    trustManagerArr = TRUST_ALL_CERTS;
                } else {
                    trustManagerArr = null;
                }
                String str2 = this._secureRandomAlgorithm;
                SecureRandom instance = str2 == null ? null : SecureRandom.getInstance(str2);
                this._context = SSLContext.getInstance(this._sslProtocol);
                this._context.init((KeyManager[]) null, trustManagerArr, instance);
                return;
            }
            checkKeyStore();
            KeyStore loadKeyStore = loadKeyStore();
            KeyStore loadTrustStore = loadTrustStore();
            Collection<? extends CRL> loadCRL = loadCRL(this._crlPath);
            if (this._validateCerts && loadKeyStore != null) {
                if (this._certAlias == null) {
                    ArrayList<T> list = Collections.list(loadKeyStore.aliases());
                    this._certAlias = list.size() == 1 ? (String) list.get(0) : null;
                }
                String str3 = this._certAlias;
                Certificate certificate = str3 == null ? null : loadKeyStore.getCertificate(str3);
                if (certificate == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("No certificate found in the keystore");
                    if (this._certAlias == null) {
                        str = "";
                    } else {
                        str = " for alias " + this._certAlias;
                    }
                    sb.append(str);
                    throw new Exception(sb.toString());
                }
                CertificateValidator certificateValidator = new CertificateValidator(loadTrustStore, loadCRL);
                certificateValidator.setMaxCertPathLength(this._maxCertPathLength);
                certificateValidator.setEnableCRLDP(this._enableCRLDP);
                certificateValidator.setEnableOCSP(this._enableOCSP);
                certificateValidator.setOcspResponderURL(this._ocspResponderURL);
                certificateValidator.validate(loadKeyStore, certificate);
            }
            KeyManager[] keyManagers = getKeyManagers(loadKeyStore);
            TrustManager[] trustManagers = getTrustManagers(loadTrustStore, loadCRL);
            String str4 = this._secureRandomAlgorithm;
            if (str4 != null) {
                secureRandom = SecureRandom.getInstance(str4);
            }
            String str5 = this._sslProvider;
            this._context = str5 == null ? SSLContext.getInstance(this._sslProtocol) : SSLContext.getInstance(this._sslProtocol, str5);
            this._context.init(keyManagers, trustManagers, secureRandom);
            SSLEngine newSslEngine = newSslEngine();
            LOG.info("Enabled Protocols {} of {}", Arrays.asList(newSslEngine.getEnabledProtocols()), Arrays.asList(newSslEngine.getSupportedProtocols()));
            if (LOG.isDebugEnabled()) {
                LOG.debug("Enabled Ciphers   {} of {}", Arrays.asList(newSslEngine.getEnabledCipherSuites()), Arrays.asList(newSslEngine.getSupportedCipherSuites()));
            }
        }
    }

    public String[] getExcludeProtocols() {
        Set<String> set = this._excludeProtocols;
        return (String[]) set.toArray(new String[set.size()]);
    }

    public void setExcludeProtocols(String... strArr) {
        checkNotStarted();
        this._excludeProtocols.clear();
        this._excludeProtocols.addAll(Arrays.asList(strArr));
    }

    public void addExcludeProtocols(String... strArr) {
        checkNotStarted();
        this._excludeProtocols.addAll(Arrays.asList(strArr));
    }

    public String[] getIncludeProtocols() {
        Set<String> set = this._includeProtocols;
        return (String[]) set.toArray(new String[set.size()]);
    }

    public void setIncludeProtocols(String... strArr) {
        checkNotStarted();
        this._includeProtocols = new LinkedHashSet(Arrays.asList(strArr));
    }

    public String[] getExcludeCipherSuites() {
        Set<String> set = this._excludeCipherSuites;
        return (String[]) set.toArray(new String[set.size()]);
    }

    public void setExcludeCipherSuites(String... strArr) {
        checkNotStarted();
        this._excludeCipherSuites.clear();
        this._excludeCipherSuites.addAll(Arrays.asList(strArr));
    }

    public void addExcludeCipherSuites(String... strArr) {
        checkNotStarted();
        this._excludeCipherSuites.addAll(Arrays.asList(strArr));
    }

    public String[] getIncludeCipherSuites() {
        Set<String> set = this._includeCipherSuites;
        return (String[]) set.toArray(new String[set.size()]);
    }

    public void setIncludeCipherSuites(String... strArr) {
        checkNotStarted();
        this._includeCipherSuites = new LinkedHashSet(Arrays.asList(strArr));
    }

    public String getKeyStorePath() {
        return this._keyStorePath;
    }

    @Deprecated
    public String getKeyStore() {
        return this._keyStorePath;
    }

    public void setKeyStorePath(String str) {
        checkNotStarted();
        this._keyStorePath = str;
    }

    @Deprecated
    public void setKeyStore(String str) {
        checkNotStarted();
        this._keyStorePath = str;
    }

    public String getKeyStoreProvider() {
        return this._keyStoreProvider;
    }

    public void setKeyStoreProvider(String str) {
        checkNotStarted();
        this._keyStoreProvider = str;
    }

    public String getKeyStoreType() {
        return this._keyStoreType;
    }

    public void setKeyStoreType(String str) {
        checkNotStarted();
        this._keyStoreType = str;
    }

    @Deprecated
    public InputStream getKeyStoreInputStream() {
        checkKeyStore();
        return this._keyStoreInputStream;
    }

    @Deprecated
    public void setKeyStoreInputStream(InputStream inputStream) {
        checkNotStarted();
        this._keyStoreInputStream = inputStream;
    }

    public String getCertAlias() {
        return this._certAlias;
    }

    public void setCertAlias(String str) {
        checkNotStarted();
        this._certAlias = str;
    }

    public String getTrustStore() {
        return this._trustStorePath;
    }

    public void setTrustStore(String str) {
        checkNotStarted();
        this._trustStorePath = str;
    }

    public String getTrustStoreProvider() {
        return this._trustStoreProvider;
    }

    public void setTrustStoreProvider(String str) {
        checkNotStarted();
        this._trustStoreProvider = str;
    }

    public String getTrustStoreType() {
        return this._trustStoreType;
    }

    public void setTrustStoreType(String str) {
        checkNotStarted();
        this._trustStoreType = str;
    }

    @Deprecated
    public InputStream getTrustStoreInputStream() {
        checkKeyStore();
        return this._trustStoreInputStream;
    }

    @Deprecated
    public void setTrustStoreInputStream(InputStream inputStream) {
        checkNotStarted();
        this._trustStoreInputStream = inputStream;
    }

    public boolean getNeedClientAuth() {
        return this._needClientAuth;
    }

    public void setNeedClientAuth(boolean z) {
        checkNotStarted();
        this._needClientAuth = z;
    }

    public boolean getWantClientAuth() {
        return this._wantClientAuth;
    }

    public void setWantClientAuth(boolean z) {
        checkNotStarted();
        this._wantClientAuth = z;
    }

    @Deprecated
    public boolean getValidateCerts() {
        return this._validateCerts;
    }

    public boolean isValidateCerts() {
        return this._validateCerts;
    }

    public void setValidateCerts(boolean z) {
        checkNotStarted();
        this._validateCerts = z;
    }

    public boolean isValidatePeerCerts() {
        return this._validatePeerCerts;
    }

    public void setValidatePeerCerts(boolean z) {
        checkNotStarted();
        this._validatePeerCerts = z;
    }

    public boolean isAllowRenegotiate() {
        return this._allowRenegotiate;
    }

    public void setAllowRenegotiate(boolean z) {
        checkNotStarted();
        this._allowRenegotiate = z;
    }

    public void setKeyStorePassword(String str) {
        checkNotStarted();
        this._keyStorePassword = Password.getPassword("org.eclipse.jetty.ssl.password", str, (String) null);
    }

    public void setKeyManagerPassword(String str) {
        checkNotStarted();
        this._keyManagerPassword = Password.getPassword("org.eclipse.jetty.ssl.keypassword", str, (String) null);
    }

    public void setTrustStorePassword(String str) {
        checkNotStarted();
        this._trustStorePassword = Password.getPassword("org.eclipse.jetty.ssl.password", str, (String) null);
    }

    public String getProvider() {
        return this._sslProvider;
    }

    public void setProvider(String str) {
        checkNotStarted();
        this._sslProvider = str;
    }

    public String getProtocol() {
        return this._sslProtocol;
    }

    public void setProtocol(String str) {
        checkNotStarted();
        this._sslProtocol = str;
    }

    public String getSecureRandomAlgorithm() {
        return this._secureRandomAlgorithm;
    }

    public void setSecureRandomAlgorithm(String str) {
        checkNotStarted();
        this._secureRandomAlgorithm = str;
    }

    public String getSslKeyManagerFactoryAlgorithm() {
        return this._keyManagerFactoryAlgorithm;
    }

    public void setSslKeyManagerFactoryAlgorithm(String str) {
        checkNotStarted();
        this._keyManagerFactoryAlgorithm = str;
    }

    public String getTrustManagerFactoryAlgorithm() {
        return this._trustManagerFactoryAlgorithm;
    }

    public boolean isTrustAll() {
        return this._trustAll;
    }

    public void setTrustAll(boolean z) {
        this._trustAll = z;
    }

    public void setTrustManagerFactoryAlgorithm(String str) {
        checkNotStarted();
        this._trustManagerFactoryAlgorithm = str;
    }

    public String getCrlPath() {
        return this._crlPath;
    }

    public void setCrlPath(String str) {
        checkNotStarted();
        this._crlPath = str;
    }

    public int getMaxCertPathLength() {
        return this._maxCertPathLength;
    }

    public void setMaxCertPathLength(int i) {
        checkNotStarted();
        this._maxCertPathLength = i;
    }

    public SSLContext getSslContext() {
        if (isStarted()) {
            return this._context;
        }
        throw new IllegalStateException(getState());
    }

    public void setSslContext(SSLContext sSLContext) {
        checkNotStarted();
        this._context = sSLContext;
    }

    /* access modifiers changed from: protected */
    public KeyStore loadKeyStore() throws Exception {
        KeyStore keyStore = this._keyStore;
        if (keyStore != null) {
            return keyStore;
        }
        InputStream inputStream = this._keyStoreInputStream;
        String str = this._keyStorePath;
        String str2 = this._keyStoreType;
        String str3 = this._keyStoreProvider;
        Password password = this._keyStorePassword;
        return getKeyStore(inputStream, str, str2, str3, password == null ? null : password.toString());
    }

    /* access modifiers changed from: protected */
    public KeyStore loadTrustStore() throws Exception {
        KeyStore keyStore = this._trustStore;
        if (keyStore != null) {
            return keyStore;
        }
        InputStream inputStream = this._trustStoreInputStream;
        String str = this._trustStorePath;
        String str2 = this._trustStoreType;
        String str3 = this._trustStoreProvider;
        Password password = this._trustStorePassword;
        return getKeyStore(inputStream, str, str2, str3, password == null ? null : password.toString());
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public KeyStore getKeyStore(InputStream inputStream, String str, String str2, String str3, String str4) throws Exception {
        return CertificateUtils.getKeyStore(inputStream, str, str2, str3, str4);
    }

    /* access modifiers changed from: protected */
    public Collection<? extends CRL> loadCRL(String str) throws Exception {
        return CertificateUtils.loadCRL(str);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: javax.net.ssl.KeyManager[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: char[]} */
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public javax.net.ssl.KeyManager[] getKeyManagers(java.security.KeyStore r5) throws java.lang.Exception {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x003f
            java.lang.String r1 = r4._keyManagerFactoryAlgorithm
            javax.net.ssl.KeyManagerFactory r1 = javax.net.ssl.KeyManagerFactory.getInstance(r1)
            org.eclipse.jetty.util.security.Password r2 = r4._keyManagerPassword
            if (r2 != 0) goto L_0x0012
            org.eclipse.jetty.util.security.Password r2 = r4._keyStorePassword
            if (r2 != 0) goto L_0x0012
            goto L_0x001a
        L_0x0012:
            java.lang.String r0 = r2.toString()
            char[] r0 = r0.toCharArray()
        L_0x001a:
            r1.init(r5, r0)
            javax.net.ssl.KeyManager[] r0 = r1.getKeyManagers()
            java.lang.String r5 = r4._certAlias
            if (r5 == 0) goto L_0x003f
            r5 = 0
        L_0x0026:
            int r1 = r0.length
            if (r5 >= r1) goto L_0x003f
            r1 = r0[r5]
            boolean r1 = r1 instanceof javax.net.ssl.X509KeyManager
            if (r1 == 0) goto L_0x003c
            org.eclipse.jetty.util.ssl.AliasedX509ExtendedKeyManager r1 = new org.eclipse.jetty.util.ssl.AliasedX509ExtendedKeyManager
            java.lang.String r2 = r4._certAlias
            r3 = r0[r5]
            javax.net.ssl.X509KeyManager r3 = (javax.net.ssl.X509KeyManager) r3
            r1.<init>(r2, r3)
            r0[r5] = r1
        L_0x003c:
            int r5 = r5 + 1
            goto L_0x0026
        L_0x003f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ssl.SslContextFactory.getKeyManagers(java.security.KeyStore):javax.net.ssl.KeyManager[]");
    }

    /* access modifiers changed from: protected */
    public TrustManager[] getTrustManagers(KeyStore keyStore, Collection<? extends CRL> collection) throws Exception {
        if (keyStore == null) {
            return null;
        }
        if (!this._validatePeerCerts || !this._trustManagerFactoryAlgorithm.equalsIgnoreCase("PKIX")) {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(this._trustManagerFactoryAlgorithm);
            instance.init(keyStore);
            return instance.getTrustManagers();
        }
        PKIXBuilderParameters pKIXBuilderParameters = new PKIXBuilderParameters(keyStore, new X509CertSelector());
        pKIXBuilderParameters.setMaxPathLength(this._maxCertPathLength);
        pKIXBuilderParameters.setRevocationEnabled(true);
        if (collection != null && !collection.isEmpty()) {
            pKIXBuilderParameters.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(collection)));
        }
        if (this._enableCRLDP) {
            System.setProperty("com.sun.security.enableCRLDP", RequestConstant.TRUE);
        }
        if (this._enableOCSP) {
            Security.setProperty("ocsp.enable", RequestConstant.TRUE);
            String str = this._ocspResponderURL;
            if (str != null) {
                Security.setProperty("ocsp.responderURL", str);
            }
        }
        TrustManagerFactory instance2 = TrustManagerFactory.getInstance(this._trustManagerFactoryAlgorithm);
        instance2.init(new CertPathTrustManagerParameters(pKIXBuilderParameters));
        return instance2.getTrustManagers();
    }

    public void checkKeyStore() {
        if (this._context == null) {
            if (this._keyStore == null && this._keyStoreInputStream == null && this._keyStorePath == null) {
                throw new IllegalStateException("SSL doesn't have a valid keystore");
            }
            if (this._trustStore == null && this._trustStoreInputStream == null && this._trustStorePath == null) {
                this._trustStore = this._keyStore;
                this._trustStorePath = this._keyStorePath;
                this._trustStoreInputStream = this._keyStoreInputStream;
                this._trustStoreType = this._keyStoreType;
                this._trustStoreProvider = this._keyStoreProvider;
                this._trustStorePassword = this._keyStorePassword;
                this._trustManagerFactoryAlgorithm = this._keyManagerFactoryAlgorithm;
            }
            InputStream inputStream = this._keyStoreInputStream;
            if (inputStream != null && inputStream == this._trustStoreInputStream) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    C2439IO.copy(this._keyStoreInputStream, (OutputStream) byteArrayOutputStream);
                    this._keyStoreInputStream.close();
                    this._keyStoreInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    this._trustStoreInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public String[] selectProtocols(String[] strArr, String[] strArr2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Set<String> set = this._includeProtocols;
        if (set != null) {
            for (String next : set) {
                if (Arrays.asList(strArr2).contains(next)) {
                    linkedHashSet.add(next);
                }
            }
        } else {
            linkedHashSet.addAll(Arrays.asList(strArr));
        }
        Set<String> set2 = this._excludeProtocols;
        if (set2 != null) {
            linkedHashSet.removeAll(set2);
        }
        return (String[]) linkedHashSet.toArray(new String[linkedHashSet.size()]);
    }

    public String[] selectCipherSuites(String[] strArr, String[] strArr2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Set<String> set = this._includeCipherSuites;
        if (set != null) {
            for (String next : set) {
                if (Arrays.asList(strArr2).contains(next)) {
                    linkedHashSet.add(next);
                }
            }
        } else {
            linkedHashSet.addAll(Arrays.asList(strArr));
        }
        Set<String> set2 = this._excludeCipherSuites;
        if (set2 != null) {
            linkedHashSet.removeAll(set2);
        }
        return (String[]) linkedHashSet.toArray(new String[linkedHashSet.size()]);
    }

    /* access modifiers changed from: protected */
    public void checkNotStarted() {
        if (isStarted()) {
            throw new IllegalStateException("Cannot modify configuration when " + getState());
        }
    }

    public boolean isEnableCRLDP() {
        return this._enableCRLDP;
    }

    public void setEnableCRLDP(boolean z) {
        checkNotStarted();
        this._enableCRLDP = z;
    }

    public boolean isEnableOCSP() {
        return this._enableOCSP;
    }

    public void setEnableOCSP(boolean z) {
        checkNotStarted();
        this._enableOCSP = z;
    }

    public String getOcspResponderURL() {
        return this._ocspResponderURL;
    }

    public void setOcspResponderURL(String str) {
        checkNotStarted();
        this._ocspResponderURL = str;
    }

    public void setKeyStore(KeyStore keyStore) {
        checkNotStarted();
        this._keyStore = keyStore;
    }

    public void setTrustStore(KeyStore keyStore) {
        checkNotStarted();
        this._trustStore = keyStore;
    }

    public void setKeyStoreResource(Resource resource) {
        checkNotStarted();
        try {
            this._keyStoreInputStream = resource.getInputStream();
        } catch (IOException unused) {
            throw new InvalidParameterException("Unable to get resource input stream for resource " + resource.toString());
        }
    }

    public void setTrustStoreResource(Resource resource) {
        checkNotStarted();
        try {
            this._trustStoreInputStream = resource.getInputStream();
        } catch (IOException unused) {
            throw new InvalidParameterException("Unable to get resource input stream for resource " + resource.toString());
        }
    }

    public boolean isSessionCachingEnabled() {
        return this._sessionCachingEnabled;
    }

    public void setSessionCachingEnabled(boolean z) {
        this._sessionCachingEnabled = z;
    }

    public int getSslSessionCacheSize() {
        return this._sslSessionCacheSize;
    }

    public void setSslSessionCacheSize(int i) {
        this._sslSessionCacheSize = i;
    }

    public int getSslSessionTimeout() {
        return this._sslSessionTimeout;
    }

    public void setSslSessionTimeout(int i) {
        this._sslSessionTimeout = i;
    }

    public SSLServerSocket newSslServerSocket(String str, int i, int i2) throws IOException {
        SSLServerSocketFactory serverSocketFactory = this._context.getServerSocketFactory();
        SSLServerSocket sSLServerSocket = (SSLServerSocket) (str == null ? serverSocketFactory.createServerSocket(i, i2) : serverSocketFactory.createServerSocket(i, i2, InetAddress.getByName(str)));
        if (getWantClientAuth()) {
            sSLServerSocket.setWantClientAuth(getWantClientAuth());
        }
        if (getNeedClientAuth()) {
            sSLServerSocket.setNeedClientAuth(getNeedClientAuth());
        }
        sSLServerSocket.setEnabledCipherSuites(selectCipherSuites(sSLServerSocket.getEnabledCipherSuites(), sSLServerSocket.getSupportedCipherSuites()));
        sSLServerSocket.setEnabledProtocols(selectProtocols(sSLServerSocket.getEnabledProtocols(), sSLServerSocket.getSupportedProtocols()));
        return sSLServerSocket;
    }

    public SSLSocket newSslSocket() throws IOException {
        SSLSocket sSLSocket = (SSLSocket) this._context.getSocketFactory().createSocket();
        if (getWantClientAuth()) {
            sSLSocket.setWantClientAuth(getWantClientAuth());
        }
        if (getNeedClientAuth()) {
            sSLSocket.setNeedClientAuth(getNeedClientAuth());
        }
        sSLSocket.setEnabledCipherSuites(selectCipherSuites(sSLSocket.getEnabledCipherSuites(), sSLSocket.getSupportedCipherSuites()));
        sSLSocket.setEnabledProtocols(selectProtocols(sSLSocket.getEnabledProtocols(), sSLSocket.getSupportedProtocols()));
        return sSLSocket;
    }

    public SSLEngine newSslEngine(String str, int i) {
        SSLEngine createSSLEngine = isSessionCachingEnabled() ? this._context.createSSLEngine(str, i) : this._context.createSSLEngine();
        customize(createSSLEngine);
        return createSSLEngine;
    }

    public SSLEngine newSslEngine() {
        SSLEngine createSSLEngine = this._context.createSSLEngine();
        customize(createSSLEngine);
        return createSSLEngine;
    }

    public void customize(SSLEngine sSLEngine) {
        if (getWantClientAuth()) {
            sSLEngine.setWantClientAuth(getWantClientAuth());
        }
        if (getNeedClientAuth()) {
            sSLEngine.setNeedClientAuth(getNeedClientAuth());
        }
        sSLEngine.setEnabledCipherSuites(selectCipherSuites(sSLEngine.getEnabledCipherSuites(), sSLEngine.getSupportedCipherSuites()));
        sSLEngine.setEnabledProtocols(selectProtocols(sSLEngine.getEnabledProtocols(), sSLEngine.getSupportedProtocols()));
    }

    public String toString() {
        return String.format("%s@%x(%s,%s)", new Object[]{getClass().getSimpleName(), Integer.valueOf(hashCode()), this._keyStorePath, this._trustStorePath});
    }
}
