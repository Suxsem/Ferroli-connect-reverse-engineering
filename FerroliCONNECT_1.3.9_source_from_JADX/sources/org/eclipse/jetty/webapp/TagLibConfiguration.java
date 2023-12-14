package org.eclipse.jetty.webapp;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlParser;

public class TagLibConfiguration extends AbstractConfiguration {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) TagLibConfiguration.class);
    public static final String TLD_RESOURCES = "org.eclipse.jetty.tlds";

    public void cloneConfigure(WebAppContext webAppContext, WebAppContext webAppContext2) throws Exception {
    }

    public void configure(WebAppContext webAppContext) throws Exception {
    }

    public void deconfigure(WebAppContext webAppContext) throws Exception {
    }

    public void postConfigure(WebAppContext webAppContext) throws Exception {
    }

    public class TagLibListener implements ServletContextListener {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private WebAppContext _context;
        private List<EventListener> _tldListeners;

        public TagLibListener(WebAppContext webAppContext) {
            this._context = webAppContext;
        }

        public void contextDestroyed(ServletContextEvent servletContextEvent) {
            List<EventListener> list = this._tldListeners;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    EventListener eventListener = this._tldListeners.get(size);
                    if (eventListener instanceof ServletContextListener) {
                        ((ServletContextListener) eventListener).contextDestroyed(servletContextEvent);
                    }
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x0032 A[Catch:{ ClassNotFoundException -> 0x0065 }] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x007c A[Catch:{ Exception -> 0x0063 }, RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x007d A[Catch:{ Exception -> 0x0063 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void contextInitialized(javax.servlet.ServletContextEvent r5) {
            /*
                r4 = this;
                org.eclipse.jetty.webapp.WebAppContext r0 = r4._context     // Catch:{ ClassNotFoundException -> 0x0065 }
                java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0065 }
                if (r0 == 0) goto L_0x0014
                java.lang.ClassLoader r1 = r0.getParent()     // Catch:{ ClassNotFoundException -> 0x0065 }
                if (r1 != 0) goto L_0x000f
                goto L_0x0014
            L_0x000f:
                java.lang.ClassLoader r0 = r0.getParent()     // Catch:{ ClassNotFoundException -> 0x0065 }
                goto L_0x001c
            L_0x0014:
                java.lang.Class r0 = r4.getClass()     // Catch:{ ClassNotFoundException -> 0x0065 }
                java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0065 }
            L_0x001c:
                java.lang.String r1 = "org.apache.jasper.compiler.TldLocationsCache"
                r0.loadClass(r1)     // Catch:{ ClassNotFoundException -> 0x0065 }
                org.eclipse.jetty.webapp.WebAppContext r0 = r4._context     // Catch:{ ClassNotFoundException -> 0x0065 }
                java.lang.String r1 = "org.eclipse.jetty.tlds"
                java.lang.Object r0 = r0.getAttribute(r1)     // Catch:{ ClassNotFoundException -> 0x0065 }
                java.util.Collection r0 = (java.util.Collection) r0     // Catch:{ ClassNotFoundException -> 0x0065 }
                java.util.HashMap r1 = new java.util.HashMap     // Catch:{ ClassNotFoundException -> 0x0065 }
                r1.<init>()     // Catch:{ ClassNotFoundException -> 0x0065 }
                if (r0 == 0) goto L_0x006d
                java.util.Iterator r0 = r0.iterator()     // Catch:{ ClassNotFoundException -> 0x0065 }
            L_0x0036:
                boolean r2 = r0.hasNext()     // Catch:{ ClassNotFoundException -> 0x0065 }
                if (r2 == 0) goto L_0x0059
                java.lang.Object r2 = r0.next()     // Catch:{ ClassNotFoundException -> 0x0065 }
                org.eclipse.jetty.util.resource.Resource r2 = (org.eclipse.jetty.util.resource.Resource) r2     // Catch:{ ClassNotFoundException -> 0x0065 }
                org.eclipse.jetty.util.resource.Resource r2 = r4.extractJarResource(r2)     // Catch:{ ClassNotFoundException -> 0x0065 }
                java.net.URI r3 = r2.getURI()     // Catch:{ ClassNotFoundException -> 0x0065 }
                boolean r3 = r1.containsKey(r3)     // Catch:{ ClassNotFoundException -> 0x0065 }
                if (r3 != 0) goto L_0x0036
                java.net.URI r2 = r2.getURI()     // Catch:{ ClassNotFoundException -> 0x0065 }
                r3 = 0
                r1.put(r2, r3)     // Catch:{ ClassNotFoundException -> 0x0065 }
                goto L_0x0036
            L_0x0059:
                javax.servlet.ServletContext r0 = r5.getServletContext()     // Catch:{ ClassNotFoundException -> 0x0065 }
                java.lang.String r2 = "com.sun.appserv.tld.map"
                r0.setAttribute(r2, r1)     // Catch:{ ClassNotFoundException -> 0x0065 }
                goto L_0x006d
            L_0x0063:
                r5 = move-exception
                goto L_0x009f
            L_0x0065:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.webapp.TagLibConfiguration.LOG     // Catch:{ Exception -> 0x0063 }
                r1.ignore(r0)     // Catch:{ Exception -> 0x0063 }
            L_0x006d:
                java.util.Set r0 = r4.findTldResources()     // Catch:{ Exception -> 0x0063 }
                java.util.List r0 = r4.parseTlds(r0)     // Catch:{ Exception -> 0x0063 }
                r4.processTlds(r0)     // Catch:{ Exception -> 0x0063 }
                java.util.List<java.util.EventListener> r0 = r4._tldListeners     // Catch:{ Exception -> 0x0063 }
                if (r0 != 0) goto L_0x007d
                return
            L_0x007d:
                java.util.List<java.util.EventListener> r0 = r4._tldListeners     // Catch:{ Exception -> 0x0063 }
                java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x0063 }
            L_0x0083:
                boolean r1 = r0.hasNext()     // Catch:{ Exception -> 0x0063 }
                if (r1 == 0) goto L_0x00a6
                java.lang.Object r1 = r0.next()     // Catch:{ Exception -> 0x0063 }
                java.util.EventListener r1 = (java.util.EventListener) r1     // Catch:{ Exception -> 0x0063 }
                boolean r2 = r1 instanceof javax.servlet.ServletContextListener     // Catch:{ Exception -> 0x0063 }
                if (r2 == 0) goto L_0x0099
                javax.servlet.ServletContextListener r1 = (javax.servlet.ServletContextListener) r1     // Catch:{ Exception -> 0x0063 }
                r1.contextInitialized(r5)     // Catch:{ Exception -> 0x0063 }
                goto L_0x0083
            L_0x0099:
                org.eclipse.jetty.webapp.WebAppContext r2 = r4._context     // Catch:{ Exception -> 0x0063 }
                r2.addEventListener(r1)     // Catch:{ Exception -> 0x0063 }
                goto L_0x0083
            L_0x009f:
                org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.webapp.TagLibConfiguration.LOG
                r0.warn(r5)
            L_0x00a6:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.TagLibConfiguration.TagLibListener.contextInitialized(javax.servlet.ServletContextEvent):void");
        }

        private Resource extractJarResource(Resource resource) {
            if (resource == null) {
                return null;
            }
            try {
                String url = resource.getURI().toURL().toString();
                int lastIndexOf = url.lastIndexOf("!/");
                if (lastIndexOf >= 0) {
                    url = url.substring(0, lastIndexOf);
                }
                if (url.startsWith("jar:")) {
                    url = url.substring(4);
                }
                return Resource.newResource(url);
            } catch (IOException e) {
                TagLibConfiguration.LOG.warn(e);
                return null;
            }
        }

        private Set<Resource> findTldResources() throws IOException {
            HashSet hashSet = new HashSet();
            if (!(this._context.getResourceAliases() == null || this._context.getBaseResource() == null || !this._context.getBaseResource().exists())) {
                for (String next : this._context.getResourceAliases().values()) {
                    if (next != null && next.toLowerCase(Locale.ENGLISH).endsWith(".tld")) {
                        if (!next.startsWith("/")) {
                            next = "/WEB-INF/" + next;
                        }
                        hashSet.add(this._context.getBaseResource().addPath(next));
                    }
                }
            }
            Resource webInf = this._context.getWebInf();
            int i = 0;
            if (webInf != null) {
                String[] list = webInf.list();
                int i2 = 0;
                while (list != null && i2 < list.length) {
                    if (list[i2] != null && list[i2].toLowerCase(Locale.ENGLISH).endsWith(".tld")) {
                        hashSet.add(webInf.addPath(list[i2]));
                    }
                    i2++;
                }
            }
            if (webInf != null) {
                Resource addPath = this._context.getWebInf().addPath("/tlds/");
                if (addPath.exists() && addPath.isDirectory()) {
                    String[] list2 = addPath.list();
                    while (list2 != null && i < list2.length) {
                        if (list2[i] != null && list2[i].toLowerCase(Locale.ENGLISH).endsWith(".tld")) {
                            hashSet.add(addPath.addPath(list2[i]));
                        }
                        i++;
                    }
                }
            }
            Collection collection = (Collection) this._context.getAttribute("org.eclipse.jetty.tlds");
            if (collection != null) {
                hashSet.addAll(collection);
            }
            return hashSet;
        }

        private List<TldDescriptor> parseTlds(Set<Resource> set) {
            Exception e;
            ArrayList arrayList = new ArrayList();
            Resource resource = null;
            for (Resource resource2 : set) {
                try {
                    try {
                        if (TagLibConfiguration.LOG.isDebugEnabled()) {
                            Logger access$000 = TagLibConfiguration.LOG;
                            access$000.debug("TLD=" + resource2, new Object[0]);
                        }
                        TldDescriptor tldDescriptor = new TldDescriptor(resource2);
                        tldDescriptor.parse();
                        arrayList.add(tldDescriptor);
                    } catch (Exception e2) {
                        e = e2;
                        Logger access$0002 = TagLibConfiguration.LOG;
                        access$0002.warn("Unable to parse TLD: " + resource2, (Throwable) e);
                        resource = resource2;
                    }
                } catch (Exception e3) {
                    Exception exc = e3;
                    resource2 = resource;
                    e = exc;
                    Logger access$00022 = TagLibConfiguration.LOG;
                    access$00022.warn("Unable to parse TLD: " + resource2, (Throwable) e);
                    resource = resource2;
                }
                resource = resource2;
            }
            return arrayList;
        }

        private void processTlds(List<TldDescriptor> list) throws Exception {
            TldProcessor tldProcessor = new TldProcessor();
            for (TldDescriptor process : list) {
                tldProcessor.process(this._context, process);
            }
            this._tldListeners = new ArrayList(tldProcessor.getListeners());
        }
    }

    public static class TldDescriptor extends Descriptor {
        protected static XmlParser __parserSingleton;

        public TldDescriptor(Resource resource) {
            super(resource);
        }

        public void ensureParser() throws ClassNotFoundException {
            if (__parserSingleton == null) {
                __parserSingleton = newParser();
            }
            this._parser = __parserSingleton;
        }

        /* JADX WARNING: Removed duplicated region for block: B:30:0x0071  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x0078  */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x007b  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x0084  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x008c  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x0096  */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x00a2  */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00ae  */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x00ba  */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x00cd  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00d4  */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00db  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.eclipse.jetty.xml.XmlParser newParser() throws java.lang.ClassNotFoundException {
            /*
                r12 = this;
                java.lang.String r0 = "javax/servlet/jsp/resources/web-jsptaglibrary_2_1.xsd"
                java.lang.String r1 = "javax/servlet/jsp/resources/web-jsptaglibrary_2_0.xsd"
                java.lang.String r2 = "javax/servlet/jsp/resources/web-jsptaglibrary_1_2.dtd"
                java.lang.String r3 = "javax/servlet/jsp/resources/web-jsptaglibrary_1_1.dtd"
                org.eclipse.jetty.xml.XmlParser r4 = new org.eclipse.jetty.xml.XmlParser
                r5 = 0
                r4.<init>(r5)
                r5 = 0
                r6 = 1
                java.lang.Class<org.eclipse.jetty.webapp.WebXmlConfiguration> r7 = org.eclipse.jetty.webapp.WebXmlConfiguration.class
                java.lang.String r8 = "javax.servlet.jsp.JspPage"
                java.lang.Class r7 = org.eclipse.jetty.util.Loader.loadClass(r7, r8)     // Catch:{ Exception -> 0x0065, all -> 0x0060 }
                java.net.URL r8 = r7.getResource(r3)     // Catch:{ Exception -> 0x0065, all -> 0x0060 }
                java.net.URL r9 = r7.getResource(r2)     // Catch:{ Exception -> 0x005b, all -> 0x0055 }
                java.net.URL r5 = r7.getResource(r1)     // Catch:{ Exception -> 0x0050, all -> 0x004a }
                java.net.URL r7 = r7.getResource(r0)     // Catch:{ Exception -> 0x0050, all -> 0x004a }
                if (r8 != 0) goto L_0x0030
                java.lang.Class<javax.servlet.Servlet> r8 = javax.servlet.Servlet.class
                java.net.URL r8 = org.eclipse.jetty.util.Loader.getResource(r8, r3, r6)
            L_0x0030:
                if (r9 != 0) goto L_0x0038
                java.lang.Class<javax.servlet.Servlet> r3 = javax.servlet.Servlet.class
                java.net.URL r9 = org.eclipse.jetty.util.Loader.getResource(r3, r2, r6)
            L_0x0038:
                if (r5 != 0) goto L_0x0040
                java.lang.Class<javax.servlet.Servlet> r2 = javax.servlet.Servlet.class
                java.net.URL r5 = org.eclipse.jetty.util.Loader.getResource(r2, r1, r6)
            L_0x0040:
                if (r7 != 0) goto L_0x0094
                java.lang.Class<javax.servlet.Servlet> r1 = javax.servlet.Servlet.class
                java.net.URL r7 = org.eclipse.jetty.util.Loader.getResource(r1, r0, r6)
                goto L_0x0094
            L_0x004a:
                r4 = move-exception
                r11 = r8
                r8 = r5
                r5 = r11
                goto L_0x00cb
            L_0x0050:
                r7 = move-exception
                r11 = r8
                r8 = r5
                r5 = r11
                goto L_0x0068
            L_0x0055:
                r4 = move-exception
                r9 = r5
                r5 = r8
                r8 = r9
                goto L_0x00cb
            L_0x005b:
                r7 = move-exception
                r9 = r5
                r5 = r8
                r8 = r9
                goto L_0x0068
            L_0x0060:
                r4 = move-exception
                r8 = r5
                r9 = r8
                goto L_0x00cb
            L_0x0065:
                r7 = move-exception
                r8 = r5
                r9 = r8
            L_0x0068:
                org.eclipse.jetty.util.log.Logger r10 = org.eclipse.jetty.webapp.TagLibConfiguration.LOG     // Catch:{ all -> 0x00ca }
                r10.ignore(r7)     // Catch:{ all -> 0x00ca }
                if (r5 != 0) goto L_0x0078
                java.lang.Class<javax.servlet.Servlet> r5 = javax.servlet.Servlet.class
                java.net.URL r3 = org.eclipse.jetty.util.Loader.getResource(r5, r3, r6)
                goto L_0x0079
            L_0x0078:
                r3 = r5
            L_0x0079:
                if (r9 != 0) goto L_0x0082
                java.lang.Class<javax.servlet.Servlet> r5 = javax.servlet.Servlet.class
                java.net.URL r2 = org.eclipse.jetty.util.Loader.getResource(r5, r2, r6)
                r9 = r2
            L_0x0082:
                if (r8 != 0) goto L_0x008c
                java.lang.Class<javax.servlet.Servlet> r2 = javax.servlet.Servlet.class
                java.net.URL r1 = org.eclipse.jetty.util.Loader.getResource(r2, r1, r6)
                r5 = r1
                goto L_0x008d
            L_0x008c:
                r5 = r8
            L_0x008d:
                java.lang.Class<javax.servlet.Servlet> r1 = javax.servlet.Servlet.class
                java.net.URL r7 = org.eclipse.jetty.util.Loader.getResource(r1, r0, r6)
                r8 = r3
            L_0x0094:
                if (r8 == 0) goto L_0x00a0
                java.lang.String r0 = "web-jsptaglib_1_1.dtd"
                r12.redirect(r4, r0, r8)
                java.lang.String r0 = "web-jsptaglibrary_1_1.dtd"
                r12.redirect(r4, r0, r8)
            L_0x00a0:
                if (r9 == 0) goto L_0x00ac
                java.lang.String r0 = "web-jsptaglib_1_2.dtd"
                r12.redirect(r4, r0, r9)
                java.lang.String r0 = "web-jsptaglibrary_1_2.dtd"
                r12.redirect(r4, r0, r9)
            L_0x00ac:
                if (r5 == 0) goto L_0x00b8
                java.lang.String r0 = "web-jsptaglib_2_0.xsd"
                r12.redirect(r4, r0, r5)
                java.lang.String r0 = "web-jsptaglibrary_2_0.xsd"
                r12.redirect(r4, r0, r5)
            L_0x00b8:
                if (r7 == 0) goto L_0x00c4
                java.lang.String r0 = "web-jsptaglib_2_1.xsd"
                r12.redirect(r4, r0, r7)
                java.lang.String r0 = "web-jsptaglibrary_2_1.xsd"
                r12.redirect(r4, r0, r7)
            L_0x00c4:
                java.lang.String r0 = "/taglib/listener/listener-class"
                r4.setXpath(r0)
                return r4
            L_0x00ca:
                r4 = move-exception
            L_0x00cb:
                if (r5 != 0) goto L_0x00d2
                java.lang.Class<javax.servlet.Servlet> r5 = javax.servlet.Servlet.class
                org.eclipse.jetty.util.Loader.getResource(r5, r3, r6)
            L_0x00d2:
                if (r9 != 0) goto L_0x00d9
                java.lang.Class<javax.servlet.Servlet> r3 = javax.servlet.Servlet.class
                org.eclipse.jetty.util.Loader.getResource(r3, r2, r6)
            L_0x00d9:
                if (r8 != 0) goto L_0x00e0
                java.lang.Class<javax.servlet.Servlet> r2 = javax.servlet.Servlet.class
                org.eclipse.jetty.util.Loader.getResource(r2, r1, r6)
            L_0x00e0:
                java.lang.Class<javax.servlet.Servlet> r1 = javax.servlet.Servlet.class
                org.eclipse.jetty.util.Loader.getResource(r1, r0, r6)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.TagLibConfiguration.TldDescriptor.newParser():org.eclipse.jetty.xml.XmlParser");
        }

        public void parse() throws Exception {
            ensureParser();
            try {
                this._root = this._parser.parse(this._xml.getInputStream());
            } catch (Exception unused) {
                this._root = this._parser.parse(this._xml.getURL().toString());
            }
            if (this._root == null) {
                TagLibConfiguration.LOG.warn("No TLD root in {}", this._xml);
            }
        }
    }

    public class TldProcessor extends IterativeDescriptorProcessor {
        public static final String TAGLIB_PROCESSOR = "org.eclipse.jetty.tagLibProcessor";
        List<EventListener> _listeners = new ArrayList();
        XmlParser _parser;
        List<XmlParser.Node> _roots = new ArrayList();

        public void end(WebAppContext webAppContext, Descriptor descriptor) {
        }

        public void start(WebAppContext webAppContext, Descriptor descriptor) {
        }

        public TldProcessor() throws Exception {
            registerVisitor(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, getClass().getDeclaredMethod("visitListener", __signature));
        }

        public void visitListener(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
            String string = node.getString("listener-class", false, true);
            if (TagLibConfiguration.LOG.isDebugEnabled()) {
                Logger access$000 = TagLibConfiguration.LOG;
                access$000.debug("listener=" + string, new Object[0]);
            }
            try {
                this._listeners.add((EventListener) webAppContext.loadClass(string).newInstance());
            } catch (Exception e) {
                Logger access$0002 = TagLibConfiguration.LOG;
                access$0002.warn("Could not instantiate listener " + string + ": " + e, new Object[0]);
                TagLibConfiguration.LOG.debug(e);
            } catch (Error e2) {
                Logger access$0003 = TagLibConfiguration.LOG;
                access$0003.warn("Could not instantiate listener " + string + ": " + e2, new Object[0]);
                TagLibConfiguration.LOG.debug(e2);
            }
        }

        public List<EventListener> getListeners() {
            return this._listeners;
        }
    }

    public void preConfigure(WebAppContext webAppContext) throws Exception {
        try {
            Loader.loadClass(WebXmlConfiguration.class, "javax.servlet.jsp.JspPage");
            webAppContext.addEventListener(new TagLibListener(webAppContext));
        } catch (Exception unused) {
        }
    }
}
