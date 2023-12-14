package org.eclipse.jetty.util.preventers;

public class LDAPLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("com.sun.jndi.LdapPoolManager", true, classLoader);
        } catch (ClassNotFoundException e) {
            LOG.ignore(e);
        }
    }
}
