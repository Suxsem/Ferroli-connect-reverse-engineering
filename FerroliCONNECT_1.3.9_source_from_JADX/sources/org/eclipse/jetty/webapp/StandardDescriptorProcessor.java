package org.eclipse.jetty.webapp;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.p107tb.appyunsdk.Constant;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import org.android.agoo.common.AgooConstants;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.security.ConstraintAware;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.JspPropertyGroupServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.xml.XmlParser;

public class StandardDescriptorProcessor extends IterativeDescriptorProcessor {
    private static final Logger LOG = Log.getLogger((Class<?>) StandardDescriptorProcessor.class);
    public static final String STANDARD_PROCESSOR = "org.eclipse.jetty.standardDescriptorProcessor";

    public void end(WebAppContext webAppContext, Descriptor descriptor) {
    }

    public void start(WebAppContext webAppContext, Descriptor descriptor) {
    }

    public StandardDescriptorProcessor() {
        try {
            registerVisitor("context-param", getClass().getDeclaredMethod("visitContextParam", __signature));
            registerVisitor("display-name", getClass().getDeclaredMethod("visitDisplayName", __signature));
            registerVisitor("servlet", getClass().getDeclaredMethod("visitServlet", __signature));
            registerVisitor("servlet-mapping", getClass().getDeclaredMethod("visitServletMapping", __signature));
            registerVisitor("session-config", getClass().getDeclaredMethod("visitSessionConfig", __signature));
            registerVisitor("mime-mapping", getClass().getDeclaredMethod("visitMimeMapping", __signature));
            registerVisitor("welcome-file-list", getClass().getDeclaredMethod("visitWelcomeFileList", __signature));
            registerVisitor("locale-encoding-mapping-list", getClass().getDeclaredMethod("visitLocaleEncodingList", __signature));
            registerVisitor("error-page", getClass().getDeclaredMethod("visitErrorPage", __signature));
            registerVisitor("taglib", getClass().getDeclaredMethod("visitTagLib", __signature));
            registerVisitor("jsp-config", getClass().getDeclaredMethod("visitJspConfig", __signature));
            registerVisitor("security-constraint", getClass().getDeclaredMethod("visitSecurityConstraint", __signature));
            registerVisitor("login-config", getClass().getDeclaredMethod("visitLoginConfig", __signature));
            registerVisitor("security-role", getClass().getDeclaredMethod("visitSecurityRole", __signature));
            registerVisitor("filter", getClass().getDeclaredMethod("visitFilter", __signature));
            registerVisitor("filter-mapping", getClass().getDeclaredMethod("visitFilterMapping", __signature));
            registerVisitor(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, getClass().getDeclaredMethod("visitListener", __signature));
            registerVisitor("distributable", getClass().getDeclaredMethod("visitDistributable", __signature));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void visitContextParam(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        String string = node.getString("param-name", false, true);
        String string2 = node.getString("param-value", false, true);
        MetaData metaData = webAppContext.getMetaData();
        int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin("context-param." + string).ordinal()];
        if (i == 1) {
            webAppContext.getInitParams().put(string, string2);
            MetaData metaData2 = webAppContext.getMetaData();
            metaData2.setOrigin("context-param." + string, descriptor);
        } else if (i == 2 || i == 3 || i == 4) {
            if (!(descriptor instanceof FragmentDescriptor)) {
                webAppContext.getInitParams().put(string, string2);
                MetaData metaData3 = webAppContext.getMetaData();
                metaData3.setOrigin("context-param." + string, descriptor);
            }
        } else if (i == 5 && (descriptor instanceof FragmentDescriptor) && !webAppContext.getInitParams().get(string).equals(string2)) {
            throw new IllegalStateException("Conflicting context-param " + string + "=" + string2 + " in " + descriptor.getResource());
        }
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("ContextParam: " + string + "=" + string2, new Object[0]);
        }
    }

    /* renamed from: org.eclipse.jetty.webapp.StandardDescriptorProcessor$1 */
    static /* synthetic */ class C24641 {
        static final /* synthetic */ int[] $SwitchMap$org$eclipse$jetty$webapp$Origin = new int[Origin.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                org.eclipse.jetty.webapp.Origin[] r0 = org.eclipse.jetty.webapp.Origin.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$eclipse$jetty$webapp$Origin = r0
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$Origin     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.eclipse.jetty.webapp.Origin r1 = org.eclipse.jetty.webapp.Origin.NotSet     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$Origin     // Catch:{ NoSuchFieldError -> 0x001f }
                org.eclipse.jetty.webapp.Origin r1 = org.eclipse.jetty.webapp.Origin.WebXml     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$Origin     // Catch:{ NoSuchFieldError -> 0x002a }
                org.eclipse.jetty.webapp.Origin r1 = org.eclipse.jetty.webapp.Origin.WebDefaults     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$Origin     // Catch:{ NoSuchFieldError -> 0x0035 }
                org.eclipse.jetty.webapp.Origin r1 = org.eclipse.jetty.webapp.Origin.WebOverride     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$Origin     // Catch:{ NoSuchFieldError -> 0x0040 }
                org.eclipse.jetty.webapp.Origin r1 = org.eclipse.jetty.webapp.Origin.WebFragment     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public void visitDisplayName(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        if (!(descriptor instanceof FragmentDescriptor)) {
            webAppContext.setDisplayName(node.toString(false, true));
            webAppContext.getMetaData().setOrigin("display-name", descriptor);
        }
    }

    /* access modifiers changed from: protected */
    public void visitServlet(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        String string;
        int i;
        Descriptor descriptor2 = descriptor;
        XmlParser.Node node2 = node;
        String attribute = node2.getAttribute(AgooConstants.MESSAGE_ID);
        boolean z = false;
        String string2 = node2.getString("servlet-name", false, true);
        ServletHolder servlet = webAppContext.getServletHandler().getServlet(string2);
        if (servlet == null) {
            servlet = webAppContext.getServletHandler().newServletHolder(Holder.Source.DESCRIPTOR);
            servlet.setName(string2);
            webAppContext.getServletHandler().addServlet(servlet);
        }
        Iterator<XmlParser.Node> it = node2.iterator("init-param");
        while (it.hasNext()) {
            XmlParser.Node next = it.next();
            String string3 = next.getString("param-name", z, true);
            String string4 = next.getString("param-value", z, true);
            MetaData metaData = webAppContext.getMetaData();
            int i2 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin(string2 + ".servlet.init-param." + string3).ordinal()];
            if (i2 == 1) {
                servlet.setInitParameter(string3, string4);
                MetaData metaData2 = webAppContext.getMetaData();
                metaData2.setOrigin(string2 + ".servlet.init-param." + string3, descriptor2);
            } else if (i2 == 2 || i2 == 3 || i2 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    servlet.setInitParameter(string3, string4);
                    MetaData metaData3 = webAppContext.getMetaData();
                    metaData3.setOrigin(string2 + ".servlet.init-param." + string3, descriptor2);
                }
            } else if (i2 == 5 && !servlet.getInitParameter(string3).equals(string4)) {
                throw new IllegalStateException("Mismatching init-param " + string3 + "=" + string4 + " in " + descriptor.getResource());
            }
            z = false;
        }
        String string5 = node2.getString("servlet-class", false, true);
        if (attribute != null && attribute.equals("jsp")) {
            try {
                Loader.loadClass(getClass(), string5);
                if (servlet.getInitParameter("scratchdir") == null) {
                    File file = new File(webAppContext.getTempDirectory(), "jsp");
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    servlet.setInitParameter("scratchdir", file.getAbsolutePath());
                }
            } catch (ClassNotFoundException unused) {
                LOG.info("NO JSP Support for {}, did not find {}", webAppContext.getContextPath(), string5);
                string5 = "org.eclipse.jetty.servlet.NoJspServlet";
            }
        }
        if (string5 != null) {
            ((WebDescriptor) descriptor2).addClassName(string5);
            MetaData metaData4 = webAppContext.getMetaData();
            int i3 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData4.getOrigin(string2 + ".servlet.servlet-class").ordinal()];
            if (i3 == 1) {
                servlet.setClassName(string5);
                MetaData metaData5 = webAppContext.getMetaData();
                metaData5.setOrigin(string2 + ".servlet.servlet-class", descriptor2);
            } else if (i3 == 2 || i3 == 3 || i3 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    servlet.setClassName(string5);
                    MetaData metaData6 = webAppContext.getMetaData();
                    metaData6.setOrigin(string2 + ".servlet.servlet-class", descriptor2);
                }
            } else if (i3 == 5 && !string5.equals(servlet.getClassName())) {
                throw new IllegalStateException("Conflicting servlet-class " + string5 + " in " + descriptor.getResource());
            }
        }
        String string6 = node2.getString("jsp-file", false, true);
        if (string6 != null) {
            servlet.setForcedPath(string6);
            ServletHolder servlet2 = webAppContext.getServletHandler().getServlet("jsp");
            if (servlet2 != null) {
                servlet.setClassName(servlet2.getClassName());
            }
        }
        XmlParser.Node node3 = node2.get("load-on-startup");
        if (node3 != null) {
            String lowerCase = node3.toString(false, true).toLowerCase(Locale.ENGLISH);
            if (lowerCase.startsWith("t")) {
                LOG.warn("Deprecated boolean load-on-startup.  Please use integer", new Object[0]);
                i = 1;
            } else {
                if (lowerCase != null) {
                    try {
                        if (lowerCase.trim().length() > 0) {
                            i = Integer.parseInt(lowerCase);
                        }
                    } catch (Exception e) {
                        Logger logger = LOG;
                        logger.warn("Cannot parse load-on-startup " + lowerCase + ". Please use integer", new Object[0]);
                        LOG.ignore(e);
                    }
                }
                i = 0;
            }
            MetaData metaData7 = webAppContext.getMetaData();
            int i4 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData7.getOrigin(string2 + ".servlet.load-on-startup").ordinal()];
            if (i4 == 1) {
                servlet.setInitOrder(i);
                MetaData metaData8 = webAppContext.getMetaData();
                metaData8.setOrigin(string2 + ".servlet.load-on-startup", descriptor2);
            } else if (i4 == 2 || i4 == 3 || i4 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    servlet.setInitOrder(i);
                    MetaData metaData9 = webAppContext.getMetaData();
                    metaData9.setOrigin(string2 + ".servlet.load-on-startup", descriptor2);
                }
            } else if (i4 == 5 && i != servlet.getInitOrder()) {
                throw new IllegalStateException("Conflicting load-on-startup value in " + descriptor.getResource());
            }
        }
        Iterator<XmlParser.Node> it2 = node2.iterator("security-role-ref");
        while (it2.hasNext()) {
            XmlParser.Node next2 = it2.next();
            String string7 = next2.getString("role-name", false, true);
            String string8 = next2.getString("role-link", false, true);
            if (string7 == null || string7.length() <= 0 || string8 == null || string8.length() <= 0) {
                Logger logger2 = LOG;
                logger2.warn("Ignored invalid security-role-ref element: servlet-name=" + servlet.getName() + ", " + next2, new Object[0]);
            } else {
                if (LOG.isDebugEnabled()) {
                    Logger logger3 = LOG;
                    logger3.debug("link role " + string7 + " to " + string8 + " for " + this, new Object[0]);
                }
                MetaData metaData10 = webAppContext.getMetaData();
                int i5 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData10.getOrigin(string2 + ".servlet.role-name." + string7).ordinal()];
                if (i5 == 1) {
                    servlet.setUserRoleLink(string7, string8);
                    MetaData metaData11 = webAppContext.getMetaData();
                    metaData11.setOrigin(string2 + ".servlet.role-name." + string7, descriptor2);
                } else if (i5 == 2 || i5 == 3 || i5 == 4) {
                    if (!(descriptor2 instanceof FragmentDescriptor)) {
                        servlet.setUserRoleLink(string7, string8);
                        MetaData metaData12 = webAppContext.getMetaData();
                        metaData12.setOrigin(string2 + ".servlet.role-name." + string7, descriptor2);
                    }
                } else if (i5 == 5 && !servlet.getUserRoleLink(string7).equals(string8)) {
                    throw new IllegalStateException("Conflicting role-link for role-name " + string7 + " for servlet " + string2 + " in " + descriptor.getResource());
                }
            }
        }
        XmlParser.Node node4 = node2.get("run-as");
        if (!(node4 == null || (string = node4.getString("role-name", false, true)) == null)) {
            MetaData metaData13 = webAppContext.getMetaData();
            int i6 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData13.getOrigin(string2 + ".servlet.run-as").ordinal()];
            if (i6 == 1) {
                servlet.setRunAsRole(string);
                MetaData metaData14 = webAppContext.getMetaData();
                metaData14.setOrigin(string2 + ".servlet.run-as", descriptor2);
            } else if (i6 == 2 || i6 == 3 || i6 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    servlet.setRunAsRole(string);
                    MetaData metaData15 = webAppContext.getMetaData();
                    metaData15.setOrigin(string2 + ".servlet.run-as", descriptor2);
                }
            } else if (i6 == 5 && !servlet.getRunAsRole().equals(string)) {
                throw new IllegalStateException("Conflicting run-as role " + string + " for servlet " + string2 + " in " + descriptor.getResource());
            }
        }
        String string9 = node2.getString("async-supported", false, true);
        if (string9 != null) {
            boolean z2 = string9.length() == 0 || Boolean.valueOf(string9).booleanValue();
            MetaData metaData16 = webAppContext.getMetaData();
            int i7 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData16.getOrigin(string2 + ".servlet.async-supported").ordinal()];
            if (i7 == 1) {
                servlet.setAsyncSupported(z2);
                MetaData metaData17 = webAppContext.getMetaData();
                metaData17.setOrigin(string2 + ".servlet.async-supported", descriptor2);
            } else if (i7 == 2 || i7 == 3 || i7 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    servlet.setAsyncSupported(z2);
                    MetaData metaData18 = webAppContext.getMetaData();
                    metaData18.setOrigin(string2 + ".servlet.async-supported", descriptor2);
                }
            } else if (i7 == 5 && servlet.isAsyncSupported() != z2) {
                throw new IllegalStateException("Conflicting async-supported=" + string9 + " for servlet " + string2 + " in " + descriptor.getResource());
            }
        }
        String string10 = node2.getString("enabled", false, true);
        if (string10 != null) {
            boolean z3 = string10.length() == 0 || Boolean.valueOf(string10).booleanValue();
            MetaData metaData19 = webAppContext.getMetaData();
            int i8 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData19.getOrigin(string2 + ".servlet.enabled").ordinal()];
            if (i8 == 1) {
                servlet.setEnabled(z3);
                MetaData metaData20 = webAppContext.getMetaData();
                metaData20.setOrigin(string2 + ".servlet.enabled", descriptor2);
            } else if (i8 == 2 || i8 == 3 || i8 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    servlet.setEnabled(z3);
                    MetaData metaData21 = webAppContext.getMetaData();
                    metaData21.setOrigin(string2 + ".servlet.enabled", descriptor2);
                }
            } else if (i8 == 5 && servlet.isEnabled() != z3) {
                throw new IllegalStateException("Conflicting value of servlet enabled for servlet " + string2 + " in " + descriptor.getResource());
            }
        }
        XmlParser.Node node5 = node2.get("multipart-config");
        if (node5 != null) {
            String string11 = node5.getString(Constant.WEATHER_LOCATION, false, true);
            String string12 = node5.getString("max-file-size", false, true);
            String string13 = node5.getString("max-request-size", false, true);
            String string14 = node5.getString("file-size-threshold", false, true);
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(string11, (string12 == null || "".equals(string12)) ? -1 : Long.parseLong(string12), (string13 == null || "".equals(string13)) ? -1 : Long.parseLong(string13), (string14 == null || "".equals(string14)) ? 0 : Integer.parseInt(string14));
            MetaData metaData22 = webAppContext.getMetaData();
            int i9 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData22.getOrigin(string2 + ".servlet.multipart-config").ordinal()];
            if (i9 == 1) {
                servlet.getRegistration().setMultipartConfig(multipartConfigElement);
                MetaData metaData23 = webAppContext.getMetaData();
                metaData23.setOrigin(string2 + ".servlet.multipart-config", descriptor2);
            } else if (i9 == 2 || i9 == 3 || i9 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    servlet.getRegistration().setMultipartConfig(multipartConfigElement);
                    MetaData metaData24 = webAppContext.getMetaData();
                    metaData24.setOrigin(string2 + ".servlet.multipart-config", descriptor2);
                }
            } else if (i9 == 5) {
                MultipartConfigElement multipartConfig = ((ServletHolder.Registration) servlet.getRegistration()).getMultipartConfig();
                if (multipartConfig.getMaxFileSize() != multipartConfigElement.getMaxFileSize()) {
                    throw new IllegalStateException("Conflicting multipart-config max-file-size for servlet " + string2 + " in " + descriptor.getResource());
                } else if (multipartConfig.getMaxRequestSize() != multipartConfigElement.getMaxRequestSize()) {
                    throw new IllegalStateException("Conflicting multipart-config max-request-size for servlet " + string2 + " in " + descriptor.getResource());
                } else if (multipartConfig.getFileSizeThreshold() == multipartConfigElement.getFileSizeThreshold()) {
                    if (multipartConfig.getLocation() == null || !(multipartConfigElement.getLocation() == null || multipartConfigElement.getLocation().length() == 0)) {
                        if (multipartConfig.getLocation() != null) {
                            return;
                        }
                        if (multipartConfigElement.getLocation() == null && multipartConfigElement.getLocation().length() <= 0) {
                            return;
                        }
                    }
                    throw new IllegalStateException("Conflicting multipart-config location for servlet " + string2 + " in " + descriptor.getResource());
                } else {
                    throw new IllegalStateException("Conflicting multipart-config file-size-threshold for servlet " + string2 + " in " + descriptor.getResource());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void visitServletMapping(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        boolean z = false;
        String string = node.getString("servlet-name", false, true);
        MetaData metaData = webAppContext.getMetaData();
        int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin(string + ".servlet.mappings").ordinal()];
        if (i == 1) {
            MetaData metaData2 = webAppContext.getMetaData();
            metaData2.setOrigin(string + ".servlet.mappings", descriptor);
            ServletMapping addServletMapping = addServletMapping(string, node, webAppContext, descriptor);
            MetaData metaData3 = webAppContext.getMetaData();
            if (metaData3.getOrigin(string + ".servlet.mappings") == Origin.WebDefaults) {
                z = true;
            }
            addServletMapping.setDefault(z);
        } else if (i == 2 || i == 3 || i == 4) {
            if (!(descriptor instanceof FragmentDescriptor)) {
                addServletMapping(string, node, webAppContext, descriptor);
            }
        } else if (i == 5) {
            addServletMapping(string, node, webAppContext, descriptor);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004a, code lost:
        if (r8 != 5) goto L_0x007c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0082 A[LOOP:0: B:17:0x007c->B:19:0x0082, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void visitSessionConfig(org.eclipse.jetty.webapp.WebAppContext r12, org.eclipse.jetty.webapp.Descriptor r13, org.eclipse.jetty.xml.XmlParser.Node r14) {
        /*
            r11 = this;
            java.lang.String r0 = "session-timeout"
            org.eclipse.jetty.xml.XmlParser$Node r0 = r14.get((java.lang.String) r0)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x001f
            java.lang.String r0 = r0.toString((boolean) r1, (boolean) r2)
            int r0 = java.lang.Integer.parseInt(r0)
            org.eclipse.jetty.server.session.SessionHandler r3 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r3 = r3.getSessionManager()
            int r0 = r0 * 60
            r3.setMaxInactiveInterval(r0)
        L_0x001f:
            java.lang.String r0 = "tracking-mode"
            java.util.Iterator r0 = r14.iterator(r0)
            boolean r3 = r0.hasNext()
            r4 = 5
            r5 = 4
            r6 = 3
            r7 = 2
            if (r3 == 0) goto L_0x009f
            r3 = 0
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            java.lang.String r9 = "session.tracking-mode"
            org.eclipse.jetty.webapp.Origin r8 = r8.getOrigin(r9)
            int[] r10 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 == r2) goto L_0x0070
            if (r8 == r7) goto L_0x004d
            if (r8 == r6) goto L_0x0070
            if (r8 == r5) goto L_0x004d
            if (r8 == r4) goto L_0x004d
            goto L_0x007c
        L_0x004d:
            boolean r3 = r13 instanceof org.eclipse.jetty.webapp.OverrideDescriptor
            if (r3 == 0) goto L_0x0057
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            goto L_0x0068
        L_0x0057:
            java.util.HashSet r3 = new java.util.HashSet
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            java.util.Set r8 = r8.getEffectiveSessionTrackingModes()
            r3.<init>(r8)
        L_0x0068:
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            r8.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x007c
        L_0x0070:
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            r8.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x007c:
            boolean r8 = r0.hasNext()
            if (r8 == 0) goto L_0x0094
            java.lang.Object r8 = r0.next()
            org.eclipse.jetty.xml.XmlParser$Node r8 = (org.eclipse.jetty.xml.XmlParser.Node) r8
            java.lang.String r8 = r8.toString((boolean) r1, (boolean) r2)
            javax.servlet.SessionTrackingMode r8 = javax.servlet.SessionTrackingMode.valueOf(r8)
            r3.add(r8)
            goto L_0x007c
        L_0x0094:
            org.eclipse.jetty.server.session.SessionHandler r0 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r0 = r0.getSessionManager()
            r0.setSessionTrackingModes(r3)
        L_0x009f:
            java.lang.String r0 = "cookie-config"
            org.eclipse.jetty.xml.XmlParser$Node r14 = r14.get((java.lang.String) r0)
            if (r14 == 0) goto L_0x0497
            java.lang.String r0 = "name"
            java.lang.String r0 = r14.getString(r0, r1, r2)
            java.lang.String r3 = " in "
            if (r0 == 0) goto L_0x0137
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            java.lang.String r9 = "cookie-config.name"
            org.eclipse.jetty.webapp.Origin r8 = r8.getOrigin(r9)
            int[] r10 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 == r2) goto L_0x0121
            if (r8 == r7) goto L_0x0106
            if (r8 == r6) goto L_0x0106
            if (r8 == r5) goto L_0x0106
            if (r8 == r4) goto L_0x00ce
            goto L_0x0137
        L_0x00ce:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            java.lang.String r8 = r8.getName()
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x00e5
            goto L_0x0137
        L_0x00e5:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r1 = "Conflicting cookie-config name "
            r14.append(r1)
            r14.append(r0)
            r14.append(r3)
            org.eclipse.jetty.util.resource.Resource r13 = r13.getResource()
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r12.<init>(r13)
            throw r12
        L_0x0106:
            boolean r8 = r13 instanceof org.eclipse.jetty.webapp.FragmentDescriptor
            if (r8 != 0) goto L_0x0137
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setName(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x0137
        L_0x0121:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setName(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x0137:
            java.lang.String r0 = "domain"
            java.lang.String r0 = r14.getString(r0, r1, r2)
            if (r0 == 0) goto L_0x01c5
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            java.lang.String r9 = "cookie-config.domain"
            org.eclipse.jetty.webapp.Origin r8 = r8.getOrigin(r9)
            int[] r10 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 == r2) goto L_0x01af
            if (r8 == r7) goto L_0x0194
            if (r8 == r6) goto L_0x0194
            if (r8 == r5) goto L_0x0194
            if (r8 == r4) goto L_0x015c
            goto L_0x01c5
        L_0x015c:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            java.lang.String r8 = r8.getDomain()
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0173
            goto L_0x01c5
        L_0x0173:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r1 = "Conflicting cookie-config domain "
            r14.append(r1)
            r14.append(r0)
            r14.append(r3)
            org.eclipse.jetty.util.resource.Resource r13 = r13.getResource()
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r12.<init>(r13)
            throw r12
        L_0x0194:
            boolean r8 = r13 instanceof org.eclipse.jetty.webapp.FragmentDescriptor
            if (r8 != 0) goto L_0x01c5
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setDomain(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x01c5
        L_0x01af:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setDomain(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x01c5:
            java.lang.String r0 = "path"
            java.lang.String r0 = r14.getString(r0, r1, r2)
            if (r0 == 0) goto L_0x0253
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            java.lang.String r9 = "cookie-config.path"
            org.eclipse.jetty.webapp.Origin r8 = r8.getOrigin(r9)
            int[] r10 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 == r2) goto L_0x023d
            if (r8 == r7) goto L_0x0222
            if (r8 == r6) goto L_0x0222
            if (r8 == r5) goto L_0x0222
            if (r8 == r4) goto L_0x01ea
            goto L_0x0253
        L_0x01ea:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            java.lang.String r8 = r8.getPath()
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x0201
            goto L_0x0253
        L_0x0201:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r1 = "Conflicting cookie-config path "
            r14.append(r1)
            r14.append(r0)
            r14.append(r3)
            org.eclipse.jetty.util.resource.Resource r13 = r13.getResource()
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r12.<init>(r13)
            throw r12
        L_0x0222:
            boolean r8 = r13 instanceof org.eclipse.jetty.webapp.FragmentDescriptor
            if (r8 != 0) goto L_0x0253
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setPath(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x0253
        L_0x023d:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setPath(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x0253:
            java.lang.String r0 = "comment"
            java.lang.String r0 = r14.getString(r0, r1, r2)
            if (r0 == 0) goto L_0x02e1
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            java.lang.String r9 = "cookie-config.comment"
            org.eclipse.jetty.webapp.Origin r8 = r8.getOrigin(r9)
            int[] r10 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 == r2) goto L_0x02cb
            if (r8 == r7) goto L_0x02b0
            if (r8 == r6) goto L_0x02b0
            if (r8 == r5) goto L_0x02b0
            if (r8 == r4) goto L_0x0278
            goto L_0x02e1
        L_0x0278:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            java.lang.String r8 = r8.getComment()
            boolean r8 = r8.equals(r0)
            if (r8 == 0) goto L_0x028f
            goto L_0x02e1
        L_0x028f:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r1 = "Conflicting cookie-config comment "
            r14.append(r1)
            r14.append(r0)
            r14.append(r3)
            org.eclipse.jetty.util.resource.Resource r13 = r13.getResource()
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r12.<init>(r13)
            throw r12
        L_0x02b0:
            boolean r8 = r13 instanceof org.eclipse.jetty.webapp.FragmentDescriptor
            if (r8 != 0) goto L_0x02e1
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setComment(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x02e1
        L_0x02cb:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setComment(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x02e1:
            java.lang.String r0 = "http-only"
            org.eclipse.jetty.xml.XmlParser$Node r0 = r14.get((java.lang.String) r0)
            if (r0 == 0) goto L_0x0373
            java.lang.String r0 = r0.toString((boolean) r1, (boolean) r2)
            boolean r0 = java.lang.Boolean.parseBoolean(r0)
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            java.lang.String r9 = "cookie-config.http-only"
            org.eclipse.jetty.webapp.Origin r8 = r8.getOrigin(r9)
            int[] r10 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 == r2) goto L_0x035d
            if (r8 == r7) goto L_0x0342
            if (r8 == r6) goto L_0x0342
            if (r8 == r5) goto L_0x0342
            if (r8 == r4) goto L_0x030e
            goto L_0x0373
        L_0x030e:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            boolean r8 = r8.isHttpOnly()
            if (r8 != r0) goto L_0x0321
            goto L_0x0373
        L_0x0321:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r1 = "Conflicting cookie-config http-only "
            r14.append(r1)
            r14.append(r0)
            r14.append(r3)
            org.eclipse.jetty.util.resource.Resource r13 = r13.getResource()
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r12.<init>(r13)
            throw r12
        L_0x0342:
            boolean r8 = r13 instanceof org.eclipse.jetty.webapp.FragmentDescriptor
            if (r8 != 0) goto L_0x0373
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setHttpOnly(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x0373
        L_0x035d:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setHttpOnly(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x0373:
            java.lang.String r0 = "secure"
            org.eclipse.jetty.xml.XmlParser$Node r0 = r14.get((java.lang.String) r0)
            if (r0 == 0) goto L_0x0405
            java.lang.String r0 = r0.toString((boolean) r1, (boolean) r2)
            boolean r0 = java.lang.Boolean.parseBoolean(r0)
            org.eclipse.jetty.webapp.MetaData r8 = r12.getMetaData()
            java.lang.String r9 = "cookie-config.secure"
            org.eclipse.jetty.webapp.Origin r8 = r8.getOrigin(r9)
            int[] r10 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r8 = r8.ordinal()
            r8 = r10[r8]
            if (r8 == r2) goto L_0x03ef
            if (r8 == r7) goto L_0x03d4
            if (r8 == r6) goto L_0x03d4
            if (r8 == r5) goto L_0x03d4
            if (r8 == r4) goto L_0x03a0
            goto L_0x0405
        L_0x03a0:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            boolean r8 = r8.isSecure()
            if (r8 != r0) goto L_0x03b3
            goto L_0x0405
        L_0x03b3:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r1 = "Conflicting cookie-config secure "
            r14.append(r1)
            r14.append(r0)
            r14.append(r3)
            org.eclipse.jetty.util.resource.Resource r13 = r13.getResource()
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            r12.<init>(r13)
            throw r12
        L_0x03d4:
            boolean r8 = r13 instanceof org.eclipse.jetty.webapp.FragmentDescriptor
            if (r8 != 0) goto L_0x0405
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setSecure(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x0405
        L_0x03ef:
            org.eclipse.jetty.server.session.SessionHandler r8 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r8 = r8.getSessionManager()
            javax.servlet.SessionCookieConfig r8 = r8.getSessionCookieConfig()
            r8.setSecure(r0)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            r0.setOrigin((java.lang.String) r9, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x0405:
            java.lang.String r0 = "max-age"
            org.eclipse.jetty.xml.XmlParser$Node r14 = r14.get((java.lang.String) r0)
            if (r14 == 0) goto L_0x0497
            java.lang.String r14 = r14.toString((boolean) r1, (boolean) r2)
            int r14 = java.lang.Integer.parseInt(r14)
            org.eclipse.jetty.webapp.MetaData r0 = r12.getMetaData()
            java.lang.String r1 = "cookie-config.max-age"
            org.eclipse.jetty.webapp.Origin r0 = r0.getOrigin(r1)
            int[] r8 = org.eclipse.jetty.webapp.StandardDescriptorProcessor.C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin
            int r0 = r0.ordinal()
            r0 = r8[r0]
            if (r0 == r2) goto L_0x0481
            if (r0 == r7) goto L_0x0466
            if (r0 == r6) goto L_0x0466
            if (r0 == r5) goto L_0x0466
            if (r0 == r4) goto L_0x0432
            goto L_0x0497
        L_0x0432:
            org.eclipse.jetty.server.session.SessionHandler r12 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r12 = r12.getSessionManager()
            javax.servlet.SessionCookieConfig r12 = r12.getSessionCookieConfig()
            int r12 = r12.getMaxAge()
            if (r12 != r14) goto L_0x0445
            goto L_0x0497
        L_0x0445:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Conflicting cookie-config max-age "
            r0.append(r1)
            r0.append(r14)
            r0.append(r3)
            org.eclipse.jetty.util.resource.Resource r13 = r13.getResource()
            r0.append(r13)
            java.lang.String r13 = r0.toString()
            r12.<init>(r13)
            throw r12
        L_0x0466:
            boolean r0 = r13 instanceof org.eclipse.jetty.webapp.FragmentDescriptor
            if (r0 != 0) goto L_0x0497
            org.eclipse.jetty.server.session.SessionHandler r0 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r0 = r0.getSessionManager()
            javax.servlet.SessionCookieConfig r0 = r0.getSessionCookieConfig()
            r0.setMaxAge(r14)
            org.eclipse.jetty.webapp.MetaData r12 = r12.getMetaData()
            r12.setOrigin((java.lang.String) r1, (org.eclipse.jetty.webapp.Descriptor) r13)
            goto L_0x0497
        L_0x0481:
            org.eclipse.jetty.server.session.SessionHandler r0 = r12.getSessionHandler()
            org.eclipse.jetty.server.SessionManager r0 = r0.getSessionManager()
            javax.servlet.SessionCookieConfig r0 = r0.getSessionCookieConfig()
            r0.setMaxAge(r14)
            org.eclipse.jetty.webapp.MetaData r12 = r12.getMetaData()
            r12.setOrigin((java.lang.String) r1, (org.eclipse.jetty.webapp.Descriptor) r13)
        L_0x0497:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.StandardDescriptorProcessor.visitSessionConfig(org.eclipse.jetty.webapp.WebAppContext, org.eclipse.jetty.webapp.Descriptor, org.eclipse.jetty.xml.XmlParser$Node):void");
    }

    /* access modifiers changed from: protected */
    public void visitMimeMapping(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        String string = node.getString("extension", false, true);
        if (string != null && string.startsWith(".")) {
            string = string.substring(1);
        }
        String string2 = node.getString("mime-type", false, true);
        if (string != null) {
            MetaData metaData = webAppContext.getMetaData();
            int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin("extension." + string).ordinal()];
            if (i == 1) {
                webAppContext.getMimeTypes().addMimeMapping(string, string2);
                MetaData metaData2 = webAppContext.getMetaData();
                metaData2.setOrigin("extension." + string, descriptor);
            } else if (i == 2 || i == 3 || i == 4) {
                if (!(descriptor instanceof FragmentDescriptor)) {
                    webAppContext.getMimeTypes().addMimeMapping(string, string2);
                    MetaData metaData3 = webAppContext.getMetaData();
                    metaData3.setOrigin("extension." + string, descriptor);
                }
            } else if (i == 5) {
                MimeTypes mimeTypes = webAppContext.getMimeTypes();
                Buffer mimeByExtension = mimeTypes.getMimeByExtension("." + string);
                webAppContext.getMimeTypes();
                if (!mimeByExtension.equals(MimeTypes.CACHE.lookup(string2))) {
                    throw new IllegalStateException("Conflicting mime-type " + string2 + " for extension " + string + " in " + descriptor.getResource());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void visitWelcomeFileList(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[webAppContext.getMetaData().getOrigin("welcome-file-list").ordinal()];
        if (i == 1) {
            webAppContext.getMetaData().setOrigin("welcome-file-list", descriptor);
            addWelcomeFiles(webAppContext, node);
        } else if (i == 2) {
            addWelcomeFiles(webAppContext, node);
        } else if (i == 3) {
            if (!(descriptor instanceof DefaultsDescriptor) && !(descriptor instanceof OverrideDescriptor) && !(descriptor instanceof FragmentDescriptor)) {
                webAppContext.setWelcomeFiles(new String[0]);
            }
            addWelcomeFiles(webAppContext, node);
        } else if (i == 4) {
            addWelcomeFiles(webAppContext, node);
        } else if (i == 5) {
            addWelcomeFiles(webAppContext, node);
        }
    }

    /* access modifiers changed from: protected */
    public void visitLocaleEncodingList(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        Iterator<XmlParser.Node> it = node.iterator("locale-encoding-mapping");
        while (it.hasNext()) {
            XmlParser.Node next = it.next();
            String string = next.getString("locale", false, true);
            String string2 = next.getString("encoding", false, true);
            if (string2 != null) {
                MetaData metaData = webAppContext.getMetaData();
                int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin("locale-encoding." + string).ordinal()];
                if (i == 1) {
                    webAppContext.addLocaleEncoding(string, string2);
                    MetaData metaData2 = webAppContext.getMetaData();
                    metaData2.setOrigin("locale-encoding." + string, descriptor);
                } else if (i == 2 || i == 3 || i == 4) {
                    if (!(descriptor instanceof FragmentDescriptor)) {
                        webAppContext.addLocaleEncoding(string, string2);
                        MetaData metaData3 = webAppContext.getMetaData();
                        metaData3.setOrigin("locale-encoding." + string, descriptor);
                    }
                } else if (i == 5 && !string2.equals(webAppContext.getLocaleEncoding(string))) {
                    throw new IllegalStateException("Conflicting loacle-encoding mapping for locale " + string + " in " + descriptor.getResource());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void visitErrorPage(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        int i;
        String string = node.getString("error-code", false, true);
        if (string == null || string.length() == 0) {
            string = node.getString("exception-type", false, true);
            if (string == null || string.length() == 0) {
                string = ErrorPageErrorHandler.GLOBAL_ERROR_PAGE;
            }
            i = 0;
        } else {
            i = Integer.valueOf(string).intValue();
        }
        String string2 = node.getString(Constant.WEATHER_LOCATION, false, true);
        ErrorPageErrorHandler errorPageErrorHandler = (ErrorPageErrorHandler) webAppContext.getErrorHandler();
        MetaData metaData = webAppContext.getMetaData();
        int i2 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin("error." + string).ordinal()];
        if (i2 == 1) {
            if (i > 0) {
                errorPageErrorHandler.addErrorPage(i, string2);
            } else {
                errorPageErrorHandler.addErrorPage(string, string2);
            }
            MetaData metaData2 = webAppContext.getMetaData();
            metaData2.setOrigin("error." + string, descriptor);
        } else if (i2 == 2 || i2 == 3 || i2 == 4) {
            if (descriptor instanceof FragmentDescriptor) {
                return;
            }
            if ((descriptor instanceof OverrideDescriptor) || (descriptor instanceof DefaultsDescriptor)) {
                if (i > 0) {
                    errorPageErrorHandler.addErrorPage(i, string2);
                } else {
                    errorPageErrorHandler.addErrorPage(string, string2);
                }
                MetaData metaData3 = webAppContext.getMetaData();
                metaData3.setOrigin("error." + string, descriptor);
                return;
            }
            throw new IllegalStateException("Duplicate global error-page " + string2);
        } else if (i2 == 5 && !errorPageErrorHandler.getErrorPages().get(string).equals(string2)) {
            throw new IllegalStateException("Conflicting error-code or exception-type " + string + " in " + descriptor.getResource());
        }
    }

    /* access modifiers changed from: protected */
    public void addWelcomeFiles(WebAppContext webAppContext, XmlParser.Node node) {
        Iterator<XmlParser.Node> it = node.iterator("welcome-file");
        while (it.hasNext()) {
            String node2 = it.next().toString(false, true);
            if (node2 != null && node2.trim().length() > 0) {
                webAppContext.setWelcomeFiles((String[]) LazyList.addToArray(webAppContext.getWelcomeFiles(), node2, String.class));
            }
        }
    }

    /* access modifiers changed from: protected */
    public ServletMapping addServletMapping(String str, XmlParser.Node node, WebAppContext webAppContext, Descriptor descriptor) {
        ServletMapping servletMapping = new ServletMapping();
        servletMapping.setServletName(str);
        ArrayList arrayList = new ArrayList();
        Iterator<XmlParser.Node> it = node.iterator("url-pattern");
        while (it.hasNext()) {
            String normalizePattern = normalizePattern(it.next().toString(false, true));
            arrayList.add(normalizePattern);
            MetaData metaData = webAppContext.getMetaData();
            metaData.setOrigin(str + ".servlet.mapping." + normalizePattern, descriptor);
        }
        servletMapping.setPathSpecs((String[]) arrayList.toArray(new String[arrayList.size()]));
        webAppContext.getServletHandler().addServletMapping(servletMapping);
        return servletMapping;
    }

    /* access modifiers changed from: protected */
    public void addFilterMapping(String str, XmlParser.Node node, WebAppContext webAppContext, Descriptor descriptor) {
        FilterMapping filterMapping = new FilterMapping();
        filterMapping.setFilterName(str);
        ArrayList arrayList = new ArrayList();
        Iterator<XmlParser.Node> it = node.iterator("url-pattern");
        while (it.hasNext()) {
            String normalizePattern = normalizePattern(it.next().toString(false, true));
            arrayList.add(normalizePattern);
            MetaData metaData = webAppContext.getMetaData();
            metaData.setOrigin(str + ".filter.mapping." + normalizePattern, descriptor);
        }
        filterMapping.setPathSpecs((String[]) arrayList.toArray(new String[arrayList.size()]));
        ArrayList arrayList2 = new ArrayList();
        Iterator<XmlParser.Node> it2 = node.iterator("servlet-name");
        while (it2.hasNext()) {
            arrayList2.add(it2.next().toString(false, true));
        }
        filterMapping.setServletNames((String[]) arrayList2.toArray(new String[arrayList2.size()]));
        ArrayList arrayList3 = new ArrayList();
        Iterator<XmlParser.Node> it3 = node.iterator("dispatcher");
        while (it3.hasNext()) {
            arrayList3.add(FilterMapping.dispatch(it3.next().toString(false, true)));
        }
        if (arrayList3.size() > 0) {
            filterMapping.setDispatcherTypes(EnumSet.copyOf(arrayList3));
        }
        webAppContext.getServletHandler().addFilterMapping(filterMapping);
    }

    /* access modifiers changed from: protected */
    public void visitTagLib(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        String string = node.getString("taglib-uri", false, true);
        String string2 = node.getString("taglib-location", false, true);
        webAppContext.setResourceAlias(string, string2);
        ServletContextHandler.JspConfig jspConfig = (ServletContextHandler.JspConfig) webAppContext.getServletContext().getJspConfigDescriptor();
        if (jspConfig == null) {
            jspConfig = new ServletContextHandler.JspConfig();
            webAppContext.getServletContext().setJspConfigDescriptor(jspConfig);
        }
        ServletContextHandler.TagLib tagLib = new ServletContextHandler.TagLib();
        tagLib.setTaglibLocation(string2);
        tagLib.setTaglibURI(string);
        jspConfig.addTaglibDescriptor(tagLib);
    }

    /* access modifiers changed from: protected */
    public void visitJspConfig(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        ServletContextHandler.JspConfig jspConfig = (ServletContextHandler.JspConfig) webAppContext.getServletContext().getJspConfigDescriptor();
        if (jspConfig == null) {
            jspConfig = new ServletContextHandler.JspConfig();
            webAppContext.getServletContext().setJspConfigDescriptor(jspConfig);
        }
        for (int i = 0; i < node.size(); i++) {
            Object obj = node.get(i);
            if (obj instanceof XmlParser.Node) {
                XmlParser.Node node2 = (XmlParser.Node) obj;
                if ("taglib".equals(node2.getTag())) {
                    visitTagLib(webAppContext, descriptor, node2);
                }
            }
        }
        Iterator<XmlParser.Node> it = node.iterator("jsp-property-group");
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            ServletContextHandler.JspPropertyGroup jspPropertyGroup = new ServletContextHandler.JspPropertyGroup();
            jspConfig.addJspPropertyGroup(jspPropertyGroup);
            XmlParser.Node next = it.next();
            Iterator<XmlParser.Node> it2 = next.iterator("url-pattern");
            while (it2.hasNext()) {
                String normalizePattern = normalizePattern(it2.next().toString(false, true));
                arrayList.add(normalizePattern);
                jspPropertyGroup.addUrlPattern(normalizePattern);
            }
            jspPropertyGroup.setElIgnored(next.getString("el-ignored", false, true));
            jspPropertyGroup.setPageEncoding(next.getString("page-encoding", false, true));
            jspPropertyGroup.setScriptingInvalid(next.getString("scripting-invalid", false, true));
            jspPropertyGroup.setIsXml(next.getString("is-xml", false, true));
            jspPropertyGroup.setDeferredSyntaxAllowedAsLiteral(next.getString("deferred-syntax-allowed-as-literal", false, true));
            jspPropertyGroup.setTrimDirectiveWhitespaces(next.getString("trim-directive-whitespaces", false, true));
            jspPropertyGroup.setDefaultContentType(next.getString("default-content-type", false, true));
            jspPropertyGroup.setBuffer(next.getString("buffer", false, true));
            jspPropertyGroup.setErrorOnUndeclaredNamespace(next.getString("error-on-undeclared-namespace", false, true));
            Iterator<XmlParser.Node> it3 = next.iterator("include-prelude");
            while (it3.hasNext()) {
                jspPropertyGroup.addIncludePrelude(it3.next().toString(false, true));
            }
            Iterator<XmlParser.Node> it4 = next.iterator("include-coda");
            while (it4.hasNext()) {
                jspPropertyGroup.addIncludeCoda(it4.next().toString(false, true));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(jspConfig.toString(), new Object[0]);
            }
        }
        if (arrayList.size() > 0) {
            ServletHandler servletHandler = webAppContext.getServletHandler();
            if (servletHandler.getServlet(JspPropertyGroupServlet.NAME) == null) {
                servletHandler.addServlet(new ServletHolder(JspPropertyGroupServlet.NAME, (Servlet) new JspPropertyGroupServlet(webAppContext, servletHandler)));
            }
            ServletMapping servletMapping = new ServletMapping();
            servletMapping.setServletName(JspPropertyGroupServlet.NAME);
            servletMapping.setPathSpecs((String[]) arrayList.toArray(new String[arrayList.size()]));
            webAppContext.getServletHandler().addServletMapping(servletMapping);
        }
    }

    /* access modifiers changed from: protected */
    public void visitSecurityConstraint(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        Constraint constraint = new Constraint();
        try {
            XmlParser.Node node2 = node.get("auth-constraint");
            if (node2 != null) {
                constraint.setAuthenticate(true);
                Iterator<XmlParser.Node> it = node2.iterator("role-name");
                ArrayList arrayList = new ArrayList();
                while (it.hasNext()) {
                    arrayList.add(it.next().toString(false, true));
                }
                constraint.setRoles((String[]) arrayList.toArray(new String[arrayList.size()]));
            }
            XmlParser.Node node3 = node.get("user-data-constraint");
            if (node3 != null) {
                String upperCase = node3.get("transport-guarantee").toString(false, true).toUpperCase(Locale.ENGLISH);
                if (!(upperCase == null || upperCase.length() == 0)) {
                    if (!Constraint.NONE.equals(upperCase)) {
                        if ("INTEGRAL".equals(upperCase)) {
                            constraint.setDataConstraint(1);
                        } else if ("CONFIDENTIAL".equals(upperCase)) {
                            constraint.setDataConstraint(2);
                        } else {
                            Logger logger = LOG;
                            logger.warn("Unknown user-data-constraint:" + upperCase, new Object[0]);
                            constraint.setDataConstraint(2);
                        }
                    }
                }
                constraint.setDataConstraint(0);
            }
            Iterator<XmlParser.Node> it2 = node.iterator("web-resource-collection");
            while (it2.hasNext()) {
                XmlParser.Node next = it2.next();
                String string = next.getString("web-resource-name", false, true);
                Constraint constraint2 = (Constraint) constraint.clone();
                constraint2.setName(string);
                Iterator<XmlParser.Node> it3 = next.iterator("url-pattern");
                while (true) {
                    if (it3.hasNext()) {
                        String normalizePattern = normalizePattern(it3.next().toString(false, true));
                        MetaData metaData = webAppContext.getMetaData();
                        metaData.setOrigin("constraint.url." + normalizePattern, descriptor);
                        Iterator<XmlParser.Node> it4 = next.iterator("http-method");
                        Iterator<XmlParser.Node> it5 = next.iterator("http-method-omission");
                        if (it4.hasNext()) {
                            if (!it5.hasNext()) {
                                while (it4.hasNext()) {
                                    String node4 = it4.next().toString(false, true);
                                    ConstraintMapping constraintMapping = new ConstraintMapping();
                                    constraintMapping.setMethod(node4);
                                    constraintMapping.setPathSpec(normalizePattern);
                                    constraintMapping.setConstraint(constraint2);
                                    ((ConstraintAware) webAppContext.getSecurityHandler()).addConstraintMapping(constraintMapping);
                                }
                            } else {
                                throw new IllegalStateException("web-resource-collection cannot contain both http-method and http-method-omission");
                            }
                        } else if (it5.hasNext()) {
                            while (it5.hasNext()) {
                                String node5 = it5.next().toString(false, true);
                                ConstraintMapping constraintMapping2 = new ConstraintMapping();
                                constraintMapping2.setMethodOmissions(new String[]{node5});
                                constraintMapping2.setPathSpec(normalizePattern);
                                constraintMapping2.setConstraint(constraint2);
                                ((ConstraintAware) webAppContext.getSecurityHandler()).addConstraintMapping(constraintMapping2);
                            }
                        } else {
                            ConstraintMapping constraintMapping3 = new ConstraintMapping();
                            constraintMapping3.setPathSpec(normalizePattern);
                            constraintMapping3.setConstraint(constraint2);
                            ((ConstraintAware) webAppContext.getSecurityHandler()).addConstraintMapping(constraintMapping3);
                        }
                    }
                }
            }
        } catch (CloneNotSupportedException e) {
            LOG.warn(e);
        }
    }

    /* access modifiers changed from: protected */
    public void visitLoginConfig(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) throws Exception {
        String str;
        XmlParser.Node node2 = node.get("auth-method");
        if (node2 != null) {
            int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[webAppContext.getMetaData().getOrigin("auth-method").ordinal()];
            if (i == 1) {
                webAppContext.getSecurityHandler().setAuthMethod(node2.toString(false, true));
                webAppContext.getMetaData().setOrigin("auth-method", descriptor);
            } else if (i == 2 || i == 3 || i == 4) {
                if (!(descriptor instanceof FragmentDescriptor)) {
                    webAppContext.getSecurityHandler().setAuthMethod(node2.toString(false, true));
                    webAppContext.getMetaData().setOrigin("auth-method", descriptor);
                }
            } else if (i == 5 && !webAppContext.getSecurityHandler().getAuthMethod().equals(node2.toString(false, true))) {
                throw new IllegalStateException("Conflicting auth-method value in " + descriptor.getResource());
            }
            XmlParser.Node node3 = node.get("realm-name");
            if (node3 == null) {
                str = "default";
            } else {
                str = node3.toString(false, true);
            }
            int i2 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[webAppContext.getMetaData().getOrigin("realm-name").ordinal()];
            if (i2 == 1) {
                webAppContext.getSecurityHandler().setRealmName(str);
                webAppContext.getMetaData().setOrigin("realm-name", descriptor);
            } else if (i2 == 2 || i2 == 3 || i2 == 4) {
                if (!(descriptor instanceof FragmentDescriptor)) {
                    webAppContext.getSecurityHandler().setRealmName(str);
                    webAppContext.getMetaData().setOrigin("realm-name", descriptor);
                }
            } else if (i2 == 5 && !webAppContext.getSecurityHandler().getRealmName().equals(str)) {
                throw new IllegalStateException("Conflicting realm-name value in " + descriptor.getResource());
            }
            if ("FORM".equals(webAppContext.getSecurityHandler().getAuthMethod())) {
                XmlParser.Node node4 = node.get("form-login-config");
                if (node4 != null) {
                    XmlParser.Node node5 = node4.get("form-login-page");
                    String str2 = null;
                    String node6 = node5 != null ? node5.toString(false, true) : null;
                    XmlParser.Node node7 = node4.get("form-error-page");
                    if (node7 != null) {
                        str2 = node7.toString(false, true);
                    }
                    int i3 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[webAppContext.getMetaData().getOrigin("form-login-page").ordinal()];
                    if (i3 == 1) {
                        webAppContext.getSecurityHandler().setInitParameter(FormAuthenticator.__FORM_LOGIN_PAGE, node6);
                        webAppContext.getMetaData().setOrigin("form-login-page", descriptor);
                    } else if (i3 == 2 || i3 == 3 || i3 == 4) {
                        if (!(descriptor instanceof FragmentDescriptor)) {
                            webAppContext.getSecurityHandler().setInitParameter(FormAuthenticator.__FORM_LOGIN_PAGE, node6);
                            webAppContext.getMetaData().setOrigin("form-login-page", descriptor);
                        }
                    } else if (i3 == 5 && !webAppContext.getSecurityHandler().getInitParameter(FormAuthenticator.__FORM_LOGIN_PAGE).equals(node6)) {
                        throw new IllegalStateException("Conflicting form-login-page value in " + descriptor.getResource());
                    }
                    int i4 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[webAppContext.getMetaData().getOrigin("form-error-page").ordinal()];
                    if (i4 == 1) {
                        webAppContext.getSecurityHandler().setInitParameter(FormAuthenticator.__FORM_ERROR_PAGE, str2);
                        webAppContext.getMetaData().setOrigin("form-error-page", descriptor);
                    } else if (i4 == 2 || i4 == 3 || i4 == 4) {
                        if (!(descriptor instanceof FragmentDescriptor)) {
                            webAppContext.getSecurityHandler().setInitParameter(FormAuthenticator.__FORM_ERROR_PAGE, str2);
                            webAppContext.getMetaData().setOrigin("form-error-page", descriptor);
                        }
                    } else if (i4 == 5 && !webAppContext.getSecurityHandler().getInitParameter(FormAuthenticator.__FORM_ERROR_PAGE).equals(str2)) {
                        throw new IllegalStateException("Conflicting form-error-page value in " + descriptor.getResource());
                    }
                } else {
                    throw new IllegalStateException("!form-login-config");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void visitSecurityRole(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        ((ConstraintAware) webAppContext.getSecurityHandler()).addRole(node.get("role-name").toString(false, true));
    }

    /* access modifiers changed from: protected */
    public void visitFilter(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        Descriptor descriptor2 = descriptor;
        XmlParser.Node node2 = node;
        boolean z = false;
        String string = node2.getString("filter-name", false, true);
        FilterHolder filter = webAppContext.getServletHandler().getFilter(string);
        if (filter == null) {
            filter = webAppContext.getServletHandler().newFilterHolder(Holder.Source.DESCRIPTOR);
            filter.setName(string);
            webAppContext.getServletHandler().addFilter(filter);
        }
        String string2 = node2.getString("filter-class", false, true);
        if (string2 != null) {
            ((WebDescriptor) descriptor2).addClassName(string2);
            MetaData metaData = webAppContext.getMetaData();
            int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin(string + ".filter.filter-class").ordinal()];
            if (i == 1) {
                filter.setClassName(string2);
                MetaData metaData2 = webAppContext.getMetaData();
                metaData2.setOrigin(string + ".filter.filter-class", descriptor2);
            } else if (i == 2 || i == 3 || i == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    filter.setClassName(string2);
                    MetaData metaData3 = webAppContext.getMetaData();
                    metaData3.setOrigin(string + ".filter.filter-class", descriptor2);
                }
            } else if (i == 5 && !filter.getClassName().equals(string2)) {
                throw new IllegalStateException("Conflicting filter-class for filter " + string + " in " + descriptor.getResource());
            }
        }
        Iterator<XmlParser.Node> it = node2.iterator("init-param");
        while (it.hasNext()) {
            XmlParser.Node next = it.next();
            String string3 = next.getString("param-name", z, true);
            String string4 = next.getString("param-value", z, true);
            MetaData metaData4 = webAppContext.getMetaData();
            int i2 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData4.getOrigin(string + ".filter.init-param." + string3).ordinal()];
            if (i2 == 1) {
                filter.setInitParameter(string3, string4);
                MetaData metaData5 = webAppContext.getMetaData();
                metaData5.setOrigin(string + ".filter.init-param." + string3, descriptor2);
            } else if (i2 == 2 || i2 == 3 || i2 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    filter.setInitParameter(string3, string4);
                    MetaData metaData6 = webAppContext.getMetaData();
                    metaData6.setOrigin(string + ".filter.init-param." + string3, descriptor2);
                }
            } else if (i2 == 5 && !filter.getInitParameter(string3).equals(string4)) {
                throw new IllegalStateException("Mismatching init-param " + string3 + "=" + string4 + " in " + descriptor.getResource());
            }
            z = false;
        }
        boolean z2 = false;
        String string5 = node2.getString("async-supported", false, true);
        if (string5 != null) {
            filter.setAsyncSupported(string5.length() == 0 || Boolean.valueOf(string5).booleanValue());
        }
        if (string5 != null) {
            if (string5.length() == 0 || Boolean.valueOf(string5).booleanValue()) {
                z2 = true;
            }
            MetaData metaData7 = webAppContext.getMetaData();
            int i3 = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData7.getOrigin(string + ".filter.async-supported").ordinal()];
            if (i3 == 1) {
                filter.setAsyncSupported(z2);
                MetaData metaData8 = webAppContext.getMetaData();
                metaData8.setOrigin(string + ".filter.async-supported", descriptor2);
            } else if (i3 == 2 || i3 == 3 || i3 == 4) {
                if (!(descriptor2 instanceof FragmentDescriptor)) {
                    filter.setAsyncSupported(z2);
                    MetaData metaData9 = webAppContext.getMetaData();
                    metaData9.setOrigin(string + ".filter.async-supported", descriptor2);
                }
            } else if (i3 == 5 && filter.isAsyncSupported() != z2) {
                throw new IllegalStateException("Conflicting async-supported=" + string5 + " for filter " + string + " in " + descriptor.getResource());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void visitFilterMapping(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        String string = node.getString("filter-name", false, true);
        MetaData metaData = webAppContext.getMetaData();
        int i = C24641.$SwitchMap$org$eclipse$jetty$webapp$Origin[metaData.getOrigin(string + ".filter.mappings").ordinal()];
        if (i == 1) {
            MetaData metaData2 = webAppContext.getMetaData();
            metaData2.setOrigin(string + ".filter.mappings", descriptor);
            addFilterMapping(string, node, webAppContext, descriptor);
        } else if (i == 2 || i == 3 || i == 4) {
            if (!(descriptor instanceof FragmentDescriptor)) {
                addFilterMapping(string, node, webAppContext, descriptor);
            }
        } else if (i == 5) {
            addFilterMapping(string, node, webAppContext, descriptor);
        }
    }

    /* access modifiers changed from: protected */
    public void visitListener(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        String string = node.getString("listener-class", false, true);
        if (string != null) {
            try {
                if (string.length() > 0) {
                    EventListener[] eventListeners = webAppContext.getEventListeners();
                    if (eventListeners != null) {
                        int length = eventListeners.length;
                        int i = 0;
                        while (i < length) {
                            if (!eventListeners[i].getClass().getName().equals(string)) {
                                i++;
                            } else {
                                return;
                            }
                        }
                    }
                    ((WebDescriptor) descriptor).addClassName(string);
                    EventListener newListenerInstance = newListenerInstance(webAppContext, webAppContext.loadClass(string));
                    if (!(newListenerInstance instanceof EventListener)) {
                        Logger logger = LOG;
                        logger.warn("Not an EventListener: " + newListenerInstance, new Object[0]);
                        return;
                    }
                    webAppContext.addEventListener(newListenerInstance);
                    MetaData metaData = webAppContext.getMetaData();
                    metaData.setOrigin(string + ".listener", descriptor);
                }
            } catch (Exception e) {
                Logger logger2 = LOG;
                logger2.warn("Could not instantiate listener " + string, (Throwable) e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void visitDistributable(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) {
        ((WebDescriptor) descriptor).setDistributable(true);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Class<? extends java.util.EventListener>, java.lang.Class] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.EventListener newListenerInstance(org.eclipse.jetty.webapp.WebAppContext r2, java.lang.Class<? extends java.util.EventListener> r3) throws javax.servlet.ServletException, java.lang.InstantiationException, java.lang.IllegalAccessException {
        /*
            r1 = this;
            org.eclipse.jetty.server.handler.ContextHandler$Context r2 = r2.getServletContext()     // Catch:{ ServletException -> 0x0009 }
            java.util.EventListener r2 = r2.createListener(r3)     // Catch:{ ServletException -> 0x0009 }
            return r2
        L_0x0009:
            r2 = move-exception
            java.lang.Throwable r3 = r2.getRootCause()
            boolean r0 = r3 instanceof java.lang.InstantiationException
            if (r0 != 0) goto L_0x001a
            boolean r0 = r3 instanceof java.lang.IllegalAccessException
            if (r0 == 0) goto L_0x0019
            java.lang.IllegalAccessException r3 = (java.lang.IllegalAccessException) r3
            throw r3
        L_0x0019:
            throw r2
        L_0x001a:
            java.lang.InstantiationException r3 = (java.lang.InstantiationException) r3
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.StandardDescriptorProcessor.newListenerInstance(org.eclipse.jetty.webapp.WebAppContext, java.lang.Class):java.util.EventListener");
    }

    /* access modifiers changed from: protected */
    public String normalizePattern(String str) {
        if (str == null || str.length() <= 0 || str.startsWith("/") || str.startsWith(Constraint.ANY_ROLE)) {
            return str;
        }
        return "/" + str;
    }
}
