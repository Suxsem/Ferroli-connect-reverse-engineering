package org.eclipse.jetty.util.preventers;

import java.awt.Toolkit;
import org.eclipse.jetty.util.log.Logger;

public class AWTLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        Logger logger = LOG;
        logger.debug("Pinning classloader for java.awt.EventQueue using " + classLoader, new Object[0]);
        Toolkit.getDefaultToolkit();
    }
}
