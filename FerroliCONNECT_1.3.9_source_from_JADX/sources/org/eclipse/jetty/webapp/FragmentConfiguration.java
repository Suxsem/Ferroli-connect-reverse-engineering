package org.eclipse.jetty.webapp;

import java.util.List;
import org.eclipse.jetty.util.resource.Resource;

public class FragmentConfiguration extends AbstractConfiguration {
    public static final String FRAGMENT_RESOURCES = "org.eclipse.jetty.webFragments";

    public void preConfigure(WebAppContext webAppContext) throws Exception {
        if (webAppContext.isConfigurationDiscovered()) {
            findWebFragments(webAppContext, webAppContext.getMetaData());
        }
    }

    public void configure(WebAppContext webAppContext) throws Exception {
        if (webAppContext.isConfigurationDiscovered()) {
            webAppContext.getMetaData().orderFragments();
        }
    }

    public void postConfigure(WebAppContext webAppContext) throws Exception {
        webAppContext.setAttribute("org.eclipse.jetty.webFragments", (Object) null);
    }

    public void findWebFragments(WebAppContext webAppContext, MetaData metaData) throws Exception {
        List<Resource> list = (List) webAppContext.getAttribute("org.eclipse.jetty.webFragments");
        if (list != null) {
            for (Resource resource : list) {
                if (resource.isDirectory()) {
                    metaData.addFragment(resource, Resource.newResource(resource.getURL() + "/META-INF/web-fragment.xml"));
                } else {
                    metaData.addFragment(resource, Resource.newResource("jar:" + resource.getURL() + "!/META-INF/web-fragment.xml"));
                }
            }
        }
    }
}
