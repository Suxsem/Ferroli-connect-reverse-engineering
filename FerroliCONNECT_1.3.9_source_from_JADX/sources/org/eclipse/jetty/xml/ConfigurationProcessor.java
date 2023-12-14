package org.eclipse.jetty.xml;

import java.net.URL;
import org.eclipse.jetty.xml.XmlParser;

public interface ConfigurationProcessor {
    Object configure() throws Exception;

    Object configure(Object obj) throws Exception;

    void init(URL url, XmlParser.Node node, XmlConfiguration xmlConfiguration);
}
