package org.eclipse.jetty.server.ssl;

import java.io.File;
import java.security.Security;
import javax.net.ssl.SSLContext;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;

public interface SslConnector extends Connector {
    @Deprecated
    public static final String DEFAULT_KEYSTORE = (System.getProperty("user.home") + File.separator + ".keystore");
    @Deprecated
    public static final String DEFAULT_KEYSTORE_ALGORITHM;
    @Deprecated
    public static final String DEFAULT_TRUSTSTORE_ALGORITHM;
    @Deprecated
    public static final String KEYPASSWORD_PROPERTY = "org.eclipse.jetty.ssl.keypassword";
    @Deprecated
    public static final String PASSWORD_PROPERTY = "org.eclipse.jetty.ssl.password";

    @Deprecated
    String[] getExcludeCipherSuites();

    @Deprecated
    String[] getIncludeCipherSuites();

    @Deprecated
    String getKeystore();

    @Deprecated
    String getKeystoreType();

    @Deprecated
    boolean getNeedClientAuth();

    @Deprecated
    String getProtocol();

    @Deprecated
    String getProvider();

    @Deprecated
    String getSecureRandomAlgorithm();

    @Deprecated
    SSLContext getSslContext();

    SslContextFactory getSslContextFactory();

    @Deprecated
    String getSslKeyManagerFactoryAlgorithm();

    @Deprecated
    String getSslTrustManagerFactoryAlgorithm();

    @Deprecated
    String getTruststore();

    @Deprecated
    String getTruststoreType();

    @Deprecated
    boolean getWantClientAuth();

    @Deprecated
    boolean isAllowRenegotiate();

    @Deprecated
    void setAllowRenegotiate(boolean z);

    @Deprecated
    void setExcludeCipherSuites(String[] strArr);

    @Deprecated
    void setIncludeCipherSuites(String[] strArr);

    @Deprecated
    void setKeyPassword(String str);

    @Deprecated
    void setKeystore(String str);

    @Deprecated
    void setKeystoreType(String str);

    @Deprecated
    void setNeedClientAuth(boolean z);

    @Deprecated
    void setPassword(String str);

    @Deprecated
    void setProtocol(String str);

    @Deprecated
    void setProvider(String str);

    @Deprecated
    void setSecureRandomAlgorithm(String str);

    @Deprecated
    void setSslContext(SSLContext sSLContext);

    @Deprecated
    void setSslKeyManagerFactoryAlgorithm(String str);

    @Deprecated
    void setSslTrustManagerFactoryAlgorithm(String str);

    @Deprecated
    void setTrustPassword(String str);

    @Deprecated
    void setTruststore(String str);

    @Deprecated
    void setTruststoreType(String str);

    @Deprecated
    void setWantClientAuth(boolean z);

    static {
        String str = "SunX509";
        DEFAULT_KEYSTORE_ALGORITHM = Security.getProperty(SSLSocketFactoryFactory.SYSKEYMGRALGO) == null ? str : Security.getProperty(SSLSocketFactoryFactory.SYSKEYMGRALGO);
        if (Security.getProperty(SSLSocketFactoryFactory.SYSTRUSTMGRALGO) != null) {
            str = Security.getProperty(SSLSocketFactoryFactory.SYSTRUSTMGRALGO);
        }
        DEFAULT_TRUSTSTORE_ALGORITHM = str;
    }
}
