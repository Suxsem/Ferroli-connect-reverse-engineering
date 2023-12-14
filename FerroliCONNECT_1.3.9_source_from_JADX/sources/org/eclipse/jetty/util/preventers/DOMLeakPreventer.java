package org.eclipse.jetty.util.preventers;

import javax.xml.parsers.DocumentBuilderFactory;

public class DOMLeakPreventer extends AbstractLeakPreventer {
    public void prevent(ClassLoader classLoader) {
        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (Exception e) {
            LOG.warn(e);
        }
    }
}
