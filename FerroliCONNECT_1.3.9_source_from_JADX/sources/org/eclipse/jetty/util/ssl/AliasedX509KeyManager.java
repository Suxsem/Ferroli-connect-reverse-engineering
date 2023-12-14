package org.eclipse.jetty.util.ssl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509KeyManager;

public class AliasedX509KeyManager implements X509KeyManager {
    private String _keyAlias;
    private X509KeyManager _keyManager;

    public AliasedX509KeyManager(String str, X509KeyManager x509KeyManager) throws Exception {
        this._keyAlias = str;
        this._keyManager = x509KeyManager;
    }

    public String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
        String str = this._keyAlias;
        return str == null ? this._keyManager.chooseClientAlias(strArr, principalArr, socket) : str;
    }

    public String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
        String str2 = this._keyAlias;
        return str2 == null ? this._keyManager.chooseServerAlias(str, principalArr, socket) : str2;
    }

    public String[] getClientAliases(String str, Principal[] principalArr) {
        return this._keyManager.getClientAliases(str, principalArr);
    }

    public String[] getServerAliases(String str, Principal[] principalArr) {
        return this._keyManager.getServerAliases(str, principalArr);
    }

    public X509Certificate[] getCertificateChain(String str) {
        return this._keyManager.getCertificateChain(str);
    }

    public PrivateKey getPrivateKey(String str) {
        return this._keyManager.getPrivateKey(str);
    }
}
