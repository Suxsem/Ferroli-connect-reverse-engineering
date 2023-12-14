package org.eclipse.jetty.util.preventers;

import java.sql.DriverManager;
import org.eclipse.jetty.util.log.Logger;

public class DriverManagerLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        Logger logger = LOG;
        logger.debug("Pinning DriverManager classloader with " + classLoader, new Object[0]);
        DriverManager.getDrivers();
    }
}
