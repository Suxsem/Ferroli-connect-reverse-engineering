package org.eclipse.jetty.util.security;

import anetwork.channel.util.RequestConstant;
import java.security.GeneralSecurityException;
import java.security.InvalidParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class CertificateValidator {
    private static final Logger LOG = Log.getLogger((Class<?>) CertificateValidator.class);
    private static AtomicLong __aliasCount = new AtomicLong();
    private Collection<? extends CRL> _crls;
    private boolean _enableCRLDP = false;
    private boolean _enableOCSP = false;
    private int _maxCertPathLength = -1;
    private String _ocspResponderURL;
    private KeyStore _trustStore;

    public CertificateValidator(KeyStore keyStore, Collection<? extends CRL> collection) {
        if (keyStore != null) {
            this._trustStore = keyStore;
            this._crls = collection;
            return;
        }
        throw new InvalidParameterException("TrustStore must be specified for CertificateValidator.");
    }

    public void validate(KeyStore keyStore) throws CertificateException {
        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                validate(keyStore, aliases.nextElement());
            }
        } catch (KeyStoreException e) {
            throw new CertificateException("Unable to retrieve aliases from keystore", e);
        }
    }

    public String validate(KeyStore keyStore, String str) throws CertificateException {
        if (str == null) {
            return null;
        }
        try {
            validate(keyStore, keyStore.getCertificate(str));
            return str;
        } catch (KeyStoreException e) {
            LOG.debug(e);
            throw new CertificateException("Unable to validate certificate for alias [" + str + "]: " + e.getMessage(), e);
        }
    }

    public void validate(KeyStore keyStore, Certificate certificate) throws CertificateException {
        String str;
        if (certificate != null && (certificate instanceof X509Certificate)) {
            ((X509Certificate) certificate).checkValidity();
            String str2 = null;
            if (keyStore != null) {
                try {
                    str2 = keyStore.getCertificateAlias((X509Certificate) certificate);
                    if (str2 == null) {
                        str2 = "JETTY" + String.format("%016X", new Object[]{Long.valueOf(__aliasCount.incrementAndGet())});
                        keyStore.setCertificateEntry(str2, certificate);
                    }
                    Certificate[] certificateChain = keyStore.getCertificateChain(str2);
                    if (certificateChain == null || certificateChain.length == 0) {
                        throw new IllegalStateException("Unable to retrieve certificate chain");
                    }
                    validate(certificateChain);
                } catch (KeyStoreException e) {
                    LOG.debug(e);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to validate certificate");
                    if (str2 == null) {
                        str = "";
                    } else {
                        str = " for alias [" + str2 + "]";
                    }
                    sb.append(str);
                    sb.append(": ");
                    sb.append(e.getMessage());
                    throw new CertificateException(sb.toString(), e);
                }
            } else {
                throw new InvalidParameterException("Keystore cannot be null");
            }
        }
    }

    public void validate(Certificate[] certificateArr) throws CertificateException {
        try {
            ArrayList arrayList = new ArrayList();
            for (X509Certificate x509Certificate : certificateArr) {
                if (x509Certificate != null) {
                    if (x509Certificate instanceof X509Certificate) {
                        arrayList.add(x509Certificate);
                    } else {
                        throw new IllegalStateException("Invalid certificate type in chain");
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                X509CertSelector x509CertSelector = new X509CertSelector();
                x509CertSelector.setCertificate((X509Certificate) arrayList.get(0));
                PKIXBuilderParameters pKIXBuilderParameters = new PKIXBuilderParameters(this._trustStore, x509CertSelector);
                pKIXBuilderParameters.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(arrayList)));
                pKIXBuilderParameters.setMaxPathLength(this._maxCertPathLength);
                pKIXBuilderParameters.setRevocationEnabled(true);
                if (this._crls != null && !this._crls.isEmpty()) {
                    pKIXBuilderParameters.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(this._crls)));
                }
                if (this._enableOCSP) {
                    Security.setProperty("ocsp.enable", RequestConstant.TRUE);
                }
                if (this._enableCRLDP) {
                    System.setProperty("com.sun.security.enableCRLDP", RequestConstant.TRUE);
                }
                CertPathValidator.getInstance("PKIX").validate(CertPathBuilder.getInstance("PKIX").build(pKIXBuilderParameters).getCertPath(), pKIXBuilderParameters);
                return;
            }
            throw new IllegalStateException("Invalid certificate chain");
        } catch (GeneralSecurityException e) {
            LOG.debug(e);
            throw new CertificateException("Unable to validate certificate: " + e.getMessage(), e);
        }
    }

    public KeyStore getTrustStore() {
        return this._trustStore;
    }

    public Collection<? extends CRL> getCrls() {
        return this._crls;
    }

    public int getMaxCertPathLength() {
        return this._maxCertPathLength;
    }

    public void setMaxCertPathLength(int i) {
        this._maxCertPathLength = i;
    }

    public boolean isEnableCRLDP() {
        return this._enableCRLDP;
    }

    public void setEnableCRLDP(boolean z) {
        this._enableCRLDP = z;
    }

    public boolean isEnableOCSP() {
        return this._enableOCSP;
    }

    public void setEnableOCSP(boolean z) {
        this._enableOCSP = z;
    }

    public String getOcspResponderURL() {
        return this._ocspResponderURL;
    }

    public void setOcspResponderURL(String str) {
        this._ocspResponderURL = str;
    }
}
