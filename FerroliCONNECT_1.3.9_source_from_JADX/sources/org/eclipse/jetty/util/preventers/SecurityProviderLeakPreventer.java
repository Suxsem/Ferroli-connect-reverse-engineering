package org.eclipse.jetty.util.preventers;

import java.security.Security;

public class SecurityProviderLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        Security.getProviders();
    }
}
