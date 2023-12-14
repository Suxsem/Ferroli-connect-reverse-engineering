package org.eclipse.jetty.util.preventers;

public class GCThreadLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("sun.misc.GC").getMethod("requestLatency", new Class[]{Long.TYPE}).invoke((Object) null, new Object[]{9223372036854775806L});
        } catch (ClassNotFoundException e) {
            LOG.ignore(e);
        } catch (Exception e2) {
            LOG.warn(e2);
        }
    }
}
