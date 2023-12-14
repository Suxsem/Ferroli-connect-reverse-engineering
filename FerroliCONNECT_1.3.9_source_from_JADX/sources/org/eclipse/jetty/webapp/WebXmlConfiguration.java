package org.eclipse.jetty.webapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public class WebXmlConfiguration extends AbstractConfiguration {
    private static final Logger LOG = Log.getLogger((Class<?>) WebXmlConfiguration.class);

    public void preConfigure(WebAppContext webAppContext) throws Exception {
        String defaultsDescriptor = webAppContext.getDefaultsDescriptor();
        if (defaultsDescriptor != null && defaultsDescriptor.length() > 0) {
            Resource newSystemResource = Resource.newSystemResource(defaultsDescriptor);
            if (newSystemResource == null) {
                newSystemResource = webAppContext.newResource(defaultsDescriptor);
            }
            webAppContext.getMetaData().setDefaults(newSystemResource);
        }
        Resource findWebXml = findWebXml(webAppContext);
        if (findWebXml != null) {
            webAppContext.getMetaData().setWebXml(findWebXml);
            webAppContext.getServletContext().setEffectiveMajorVersion(webAppContext.getMetaData().getWebXml().getMajorVersion());
            webAppContext.getServletContext().setEffectiveMinorVersion(webAppContext.getMetaData().getWebXml().getMinorVersion());
        }
        for (String next : webAppContext.getOverrideDescriptors()) {
            if (next != null && next.length() > 0) {
                Resource newSystemResource2 = Resource.newSystemResource(next);
                if (newSystemResource2 == null) {
                    newSystemResource2 = webAppContext.newResource(next);
                }
                webAppContext.getMetaData().addOverride(newSystemResource2);
            }
        }
    }

    public void configure(WebAppContext webAppContext) throws Exception {
        if (webAppContext.isStarted()) {
            LOG.debug("Cannot configure webapp after it is started", new Object[0]);
        } else {
            webAppContext.getMetaData().addDescriptorProcessor(new StandardDescriptorProcessor());
        }
    }

    /* access modifiers changed from: protected */
    public Resource findWebXml(WebAppContext webAppContext) throws IOException, MalformedURLException {
        String descriptor = webAppContext.getDescriptor();
        if (descriptor != null) {
            Resource newResource = webAppContext.newResource(descriptor);
            if (newResource.exists() && !newResource.isDirectory()) {
                return newResource;
            }
        }
        Resource webInf = webAppContext.getWebInf();
        if (webInf == null || !webInf.isDirectory()) {
            return null;
        }
        Resource addPath = webInf.addPath("web.xml");
        if (addPath.exists()) {
            return addPath;
        }
        if (!LOG.isDebugEnabled()) {
            return null;
        }
        Logger logger = LOG;
        logger.debug("No WEB-INF/web.xml in " + webAppContext.getWar() + ". Serving files and default/dynamic servlets only", new Object[0]);
        return null;
    }

    public void deconfigure(WebAppContext webAppContext) throws Exception {
        webAppContext.getServletHandler();
        webAppContext.setWelcomeFiles((String[]) null);
        if (webAppContext.getErrorHandler() instanceof ErrorPageErrorHandler) {
            ((ErrorPageErrorHandler) webAppContext.getErrorHandler()).setErrorPages((Map<String, String>) null);
        }
    }
}
