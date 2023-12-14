package com.igexin.push.extension.distribution.gbd.p086i;

import java.security.Provider;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.b */
class C1534b extends Provider {
    public C1534b() {
        super("Crypto", 1.0d, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }
}
