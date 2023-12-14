package org.eclipse.jetty.webapp;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyWebXmlConfiguration extends AbstractConfiguration {
    public static final String JETTY_WEB_XML = "jetty-web.xml";
    private static final Logger LOG = Log.getLogger((Class<?>) JettyWebXmlConfiguration.class);
    public static final String PROPERTY_THIS_WEB_INF_URL = "this.web-inf.url";
    public static final String XML_CONFIGURATION = "org.eclipse.jetty.webapp.JettyWebXmlConfiguration";

    public void configure(WebAppContext webAppContext) throws Exception {
        if (webAppContext.isStarted()) {
            LOG.debug("Cannot configure webapp after it is started", new Object[0]);
            return;
        }
        LOG.debug("Configuring web-jetty.xml", new Object[0]);
        Resource webInf = webAppContext.getWebInf();
        if (webInf != null && webInf.isDirectory()) {
            Resource addPath = webInf.addPath("jetty8-web.xml");
            if (!addPath.exists()) {
                addPath = webInf.addPath(JETTY_WEB_XML);
            }
            if (!addPath.exists()) {
                addPath = webInf.addPath("web-jetty.xml");
            }
            if (addPath.exists()) {
                String[] serverClasses = webAppContext.getServerClasses();
                try {
                    webAppContext.setServerClasses((String[]) null);
                    if (LOG.isDebugEnabled()) {
                        Logger logger = LOG;
                        logger.debug("Configure: " + addPath, new Object[0]);
                    }
                    XmlConfiguration xmlConfiguration = (XmlConfiguration) webAppContext.getAttribute(XML_CONFIGURATION);
                    if (xmlConfiguration == null) {
                        xmlConfiguration = new XmlConfiguration(addPath.getURL());
                    } else {
                        webAppContext.removeAttribute(XML_CONFIGURATION);
                    }
                    setupXmlConfiguration(webAppContext, xmlConfiguration, webInf);
                    xmlConfiguration.configure(webAppContext);
                } catch (ClassNotFoundException e) {
                    LOG.warn("Unable to process jetty-web.xml", (Throwable) e);
                } catch (Throwable th) {
                    if (webAppContext.getServerClasses() == null) {
                        webAppContext.setServerClasses(serverClasses);
                    }
                    throw th;
                }
                if (webAppContext.getServerClasses() == null) {
                    webAppContext.setServerClasses(serverClasses);
                }
            }
        }
    }

    private void setupXmlConfiguration(WebAppContext webAppContext, XmlConfiguration xmlConfiguration, Resource resource) {
        setupXmlConfiguration(xmlConfiguration, resource);
    }

    private void setupXmlConfiguration(XmlConfiguration xmlConfiguration, Resource resource) {
        Map properties = xmlConfiguration.getProperties();
        if (properties == null) {
            properties = new HashMap();
            xmlConfiguration.setProperties(properties);
        }
        properties.put(PROPERTY_THIS_WEB_INF_URL, String.valueOf(resource.getURL()));
    }
}
