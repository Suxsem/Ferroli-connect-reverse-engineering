package org.eclipse.jetty.webapp;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.regex.Pattern;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public class MetaInfConfiguration extends AbstractConfiguration {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) MetaInfConfiguration.class);
    public static final String METAINF_FRAGMENTS = "org.eclipse.jetty.webFragments";
    public static final String METAINF_RESOURCES = "org.eclipse.jetty.resources";
    public static final String METAINF_TLDS = "org.eclipse.jetty.tlds";

    public void configure(WebAppContext webAppContext) throws Exception {
    }

    public void deconfigure(WebAppContext webAppContext) throws Exception {
    }

    public void preConfigure(final WebAppContext webAppContext) throws Exception {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(webAppContext.getMetaData().getOrderedContainerJars());
        arrayList.addAll(webAppContext.getMetaData().getWebInfJars());
        C24621 r1 = new JarScanner() {
            public void processEntry(URI uri, JarEntry jarEntry) {
                try {
                    MetaInfConfiguration.this.processEntry(webAppContext, uri, jarEntry);
                } catch (Exception e) {
                    Logger access$000 = MetaInfConfiguration.LOG;
                    access$000.warn("Problem processing jar entry " + jarEntry, (Throwable) e);
                }
            }
        };
        URI[] uriArr = new URI[arrayList.size()];
        int i = 0;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            uriArr[i] = ((Resource) it.next()).getURI();
            i++;
        }
        r1.scan((Pattern) null, uriArr, true);
    }

    public void postConfigure(WebAppContext webAppContext) throws Exception {
        webAppContext.setAttribute("org.eclipse.jetty.webFragments", (Object) null);
        webAppContext.setAttribute("org.eclipse.jetty.resources", (Object) null);
        webAppContext.setAttribute("org.eclipse.jetty.tlds", (Object) null);
    }

    public void addResource(WebAppContext webAppContext, String str, Resource resource) {
        List list = (List) webAppContext.getAttribute(str);
        if (list == null) {
            list = new ArrayList();
            webAppContext.setAttribute(str, list);
        }
        if (!list.contains(resource)) {
            list.add(resource);
        }
    }

    /* access modifiers changed from: protected */
    public void processEntry(WebAppContext webAppContext, URI uri, JarEntry jarEntry) {
        String name = jarEntry.getName();
        if (name.startsWith("META-INF/")) {
            try {
                if (!name.equals("META-INF/web-fragment.xml") || !webAppContext.isConfigurationDiscovered()) {
                    if (name.equals("META-INF/resources/")) {
                        if (webAppContext.isConfigurationDiscovered()) {
                            addResource(webAppContext, "org.eclipse.jetty.resources", Resource.newResource("jar:" + uri + "!/META-INF/resources"));
                            return;
                        }
                    }
                    if (name.toLowerCase(Locale.ENGLISH).endsWith(".tld")) {
                        addResource(webAppContext, "org.eclipse.jetty.tlds", Resource.newResource("jar:" + uri + "!/" + name));
                        return;
                    }
                    return;
                }
                addResource(webAppContext, "org.eclipse.jetty.webFragments", Resource.newResource(uri));
            } catch (Exception e) {
                ContextHandler.Context servletContext = webAppContext.getServletContext();
                servletContext.log(uri + "!/" + name, (Throwable) e);
            }
        }
    }
}
