package org.eclipse.jetty.util.preventers;

public class LoginConfigurationLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("javax.security.auth.login.Configuration", true, classLoader);
        } catch (ClassNotFoundException e) {
            LOG.warn(e);
        }
    }
}
