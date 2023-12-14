package org.eclipse.jetty.util.preventers;

public class Java2DLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("sun.java2d.Disposer", true, classLoader);
        } catch (ClassNotFoundException e) {
            LOG.ignore(e);
        }
    }
}
