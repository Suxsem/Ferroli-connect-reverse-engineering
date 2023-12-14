package org.eclipse.jetty.webapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.PatternMatcher;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.JarResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;

public class WebInfConfiguration extends AbstractConfiguration {
    public static final String CONTAINER_JAR_PATTERN = "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern";
    private static final Logger LOG = Log.getLogger((Class<?>) WebInfConfiguration.class);
    public static final String RESOURCE_URLS = "org.eclipse.jetty.resources";
    public static final String TEMPDIR_CONFIGURED = "org.eclipse.jetty.tmpdirConfigured";
    public static final String WEBINF_JAR_PATTERN = "org.eclipse.jetty.server.webapp.WebInfIncludeJarPattern";
    protected Resource _preUnpackBaseResource;

    public void preConfigure(WebAppContext webAppContext) throws Exception {
        Pattern pattern;
        Pattern pattern2;
        URI[] uriArr;
        final WebAppContext webAppContext2 = webAppContext;
        File findWorkDirectory = findWorkDirectory(webAppContext);
        int i = 0;
        if (findWorkDirectory != null) {
            makeTempDirectory(findWorkDirectory, webAppContext2, false);
        }
        resolveTempDirectory(webAppContext);
        unpack(webAppContext);
        String str = (String) webAppContext2.getAttribute(WEBINF_JAR_PATTERN);
        if (str == null) {
            pattern = null;
        } else {
            pattern = Pattern.compile(str);
        }
        String str2 = (String) webAppContext2.getAttribute(CONTAINER_JAR_PATTERN);
        if (str2 == null) {
            pattern2 = null;
        } else {
            pattern2 = Pattern.compile(str2);
        }
        C24661 r6 = new PatternMatcher() {
            public void matched(URI uri) throws Exception {
                webAppContext2.getMetaData().addContainerJar(Resource.newResource(uri));
            }
        };
        ClassLoader parent = webAppContext.getClassLoader() != null ? webAppContext.getClassLoader().getParent() : null;
        while (parent != null && (parent instanceof URLClassLoader)) {
            URL[] uRLs = ((URLClassLoader) parent).getURLs();
            if (uRLs != null) {
                URI[] uriArr2 = new URI[uRLs.length];
                int i2 = 0;
                for (URL url : uRLs) {
                    try {
                        uriArr2[i2] = url.toURI();
                    } catch (URISyntaxException unused) {
                        uriArr2[i2] = new URI(url.toString().replaceAll(" ", "%20"));
                    }
                    i2++;
                }
                r6.match(pattern2, uriArr2, false);
            }
            parent = parent.getParent();
        }
        C24672 r4 = new PatternMatcher() {
            public void matched(URI uri) throws Exception {
                webAppContext2.getMetaData().addWebInfJar(Resource.newResource(uri));
            }
        };
        List<Resource> findJars = findJars(webAppContext);
        if (findJars != null) {
            uriArr = new URI[findJars.size()];
            for (Resource uri : findJars) {
                uriArr[i] = uri.getURI();
                i++;
            }
        } else {
            uriArr = null;
        }
        r4.match(pattern, uriArr, true);
    }

    public void configure(WebAppContext webAppContext) throws Exception {
        if (!webAppContext.isStarted()) {
            Resource webInf = webAppContext.getWebInf();
            if (webInf != null && webInf.isDirectory() && (webAppContext.getClassLoader() instanceof WebAppClassLoader)) {
                Resource addPath = webInf.addPath("classes/");
                if (addPath.exists()) {
                    ((WebAppClassLoader) webAppContext.getClassLoader()).addClassPath(addPath);
                }
                Resource addPath2 = webInf.addPath("lib/");
                if (addPath2.exists() || addPath2.isDirectory()) {
                    ((WebAppClassLoader) webAppContext.getClassLoader()).addJars(addPath2);
                }
            }
            List<Resource> list = (List) webAppContext.getAttribute("org.eclipse.jetty.resources");
            if (list != null) {
                int i = 1;
                Resource[] resourceArr = new Resource[(list.size() + 1)];
                resourceArr[0] = webAppContext.getBaseResource();
                for (Resource resource : list) {
                    resourceArr[i] = resource;
                    i++;
                }
                webAppContext.setBaseResource(new ResourceCollection(resourceArr));
            }
        } else if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("Cannot configure webapp " + webAppContext + " after it is started", new Object[0]);
        }
    }

    public void deconfigure(WebAppContext webAppContext) throws Exception {
        Boolean bool = (Boolean) webAppContext.getAttribute(TEMPDIR_CONFIGURED);
        if (webAppContext.getTempDirectory() != null && ((bool == null || !bool.booleanValue()) && !isTempWorkDirectory(webAppContext.getTempDirectory()))) {
            C2439IO.delete(webAppContext.getTempDirectory());
            webAppContext.setTempDirectory((File) null);
            webAppContext.setAttribute(TEMPDIR_CONFIGURED, (Object) null);
            webAppContext.setAttribute("javax.servlet.context.tempdir", (Object) null);
        }
        webAppContext.setBaseResource(this._preUnpackBaseResource);
    }

    public void cloneConfigure(WebAppContext webAppContext, WebAppContext webAppContext2) throws Exception {
        File createTempFile = File.createTempFile(getCanonicalNameForWebAppTmpDir(webAppContext2), "", webAppContext.getTempDirectory().getParentFile());
        if (createTempFile.exists()) {
            C2439IO.delete(createTempFile);
        }
        createTempFile.mkdir();
        createTempFile.deleteOnExit();
        webAppContext2.setTempDirectory(createTempFile);
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void resolveTempDirectory(org.eclipse.jetty.webapp.WebAppContext r6) {
        /*
            r5 = this;
            java.lang.String r0 = "java.io.tmpdir"
            java.io.File r1 = r6.getTempDirectory()
            if (r1 == 0) goto L_0x001c
            boolean r2 = r1.isDirectory()
            if (r2 == 0) goto L_0x001c
            boolean r1 = r1.canWrite()
            if (r1 == 0) goto L_0x001c
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            java.lang.String r1 = "org.eclipse.jetty.tmpdirConfigured"
            r6.setAttribute(r1, r0)
            return
        L_0x001c:
            java.lang.String r1 = "javax.servlet.context.tempdir"
            java.lang.Object r2 = r6.getAttribute(r1)
            java.io.File r2 = r5.asFile(r2)
            if (r2 == 0) goto L_0x003b
            boolean r3 = r2.isDirectory()
            if (r3 == 0) goto L_0x003b
            boolean r3 = r2.canWrite()
            if (r3 == 0) goto L_0x003b
            r6.setAttribute(r1, r2)
            r6.setTempDirectory(r2)
            return
        L_0x003b:
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0089 }
            java.lang.String r2 = "jetty.home"
            java.lang.String r2 = java.lang.System.getProperty(r2)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r3 = "work"
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0089 }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0089 }
            r3 = 0
            if (r2 == 0) goto L_0x005f
            boolean r2 = r1.canWrite()     // Catch:{ Exception -> 0x0089 }
            if (r2 == 0) goto L_0x005f
            boolean r2 = r1.isDirectory()     // Catch:{ Exception -> 0x0089 }
            if (r2 == 0) goto L_0x005f
            r5.makeTempDirectory(r1, r6, r3)     // Catch:{ Exception -> 0x0089 }
            goto L_0x008f
        L_0x005f:
            java.lang.String r1 = "org.eclipse.jetty.webapp.basetempdir"
            java.lang.Object r1 = r6.getAttribute(r1)     // Catch:{ Exception -> 0x0089 }
            java.io.File r1 = r5.asFile(r1)     // Catch:{ Exception -> 0x0089 }
            if (r1 == 0) goto L_0x007b
            boolean r2 = r1.isDirectory()     // Catch:{ Exception -> 0x0089 }
            if (r2 == 0) goto L_0x007b
            boolean r2 = r1.canWrite()     // Catch:{ Exception -> 0x0089 }
            if (r2 == 0) goto L_0x007b
            r5.makeTempDirectory(r1, r6, r3)     // Catch:{ Exception -> 0x0089 }
            goto L_0x008f
        L_0x007b:
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0089 }
            java.lang.String r2 = java.lang.System.getProperty(r0)     // Catch:{ Exception -> 0x0089 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0089 }
            r2 = 1
            r5.makeTempDirectory(r1, r6, r2)     // Catch:{ Exception -> 0x0089 }
            goto L_0x008f
        L_0x0089:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.ignore(r1)
        L_0x008f:
            java.io.File r1 = r6.getTempDirectory()
            if (r1 != 0) goto L_0x00d4
            java.lang.String r1 = "JettyContext"
            java.lang.String r2 = ""
            java.io.File r1 = java.io.File.createTempFile(r1, r2)     // Catch:{ IOException -> 0x00b0 }
            boolean r2 = r1.exists()     // Catch:{ IOException -> 0x00b0 }
            if (r2 == 0) goto L_0x00a6
            org.eclipse.jetty.util.C2439IO.delete(r1)     // Catch:{ IOException -> 0x00b0 }
        L_0x00a6:
            r1.mkdir()     // Catch:{ IOException -> 0x00b0 }
            r1.deleteOnExit()     // Catch:{ IOException -> 0x00b0 }
            r6.setTempDirectory(r1)     // Catch:{ IOException -> 0x00b0 }
            goto L_0x00d4
        L_0x00b0:
            r1 = move-exception
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Cannot create tmp dir in "
            r3.append(r4)
            java.lang.String r0 = java.lang.System.getProperty(r0)
            r3.append(r0)
            java.lang.String r0 = " for context "
            r3.append(r0)
            r3.append(r6)
            java.lang.String r6 = r3.toString()
            r2.<init>(r6, r1)
            throw r2
        L_0x00d4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.WebInfConfiguration.resolveTempDirectory(org.eclipse.jetty.webapp.WebAppContext):void");
    }

    private File asFile(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof File) {
            return (File) obj;
        }
        if (obj instanceof String) {
            return new File((String) obj);
        }
        return null;
    }

    public void makeTempDirectory(File file, WebAppContext webAppContext, boolean z) throws IOException {
        if (file != null && file.exists() && file.canWrite() && file.isDirectory()) {
            String canonicalNameForWebAppTmpDir = getCanonicalNameForWebAppTmpDir(webAppContext);
            File file2 = new File(file, canonicalNameForWebAppTmpDir);
            if (z && file2.exists()) {
                if (!C2439IO.delete(file2) && LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Failed to delete temp dir " + file2, new Object[0]);
                }
                if (file2.exists()) {
                    String file3 = file2.toString();
                    file2 = File.createTempFile(canonicalNameForWebAppTmpDir + "_", "");
                    if (file2.exists()) {
                        C2439IO.delete(file2);
                    }
                    Logger logger2 = LOG;
                    logger2.warn("Can't reuse " + file3 + ", using " + file2, new Object[0]);
                }
            }
            if (!file2.exists()) {
                file2.mkdir();
            }
            if (!isTempWorkDirectory(file2)) {
                file2.deleteOnExit();
            }
            if (LOG.isDebugEnabled()) {
                Logger logger3 = LOG;
                logger3.debug("Set temp dir " + file2, new Object[0]);
            }
            webAppContext.setTempDirectory(file2);
        }
    }

    public void unpack(WebAppContext webAppContext) throws IOException {
        Resource resource;
        File file;
        Resource baseResource = webAppContext.getBaseResource();
        this._preUnpackBaseResource = webAppContext.getBaseResource();
        if (baseResource == null) {
            String war = webAppContext.getWar();
            if (war == null || war.length() <= 0) {
                resource = webAppContext.getBaseResource();
            } else {
                resource = webAppContext.newResource(war);
            }
            if (resource.getAlias() != null) {
                Logger logger = LOG;
                logger.debug(resource + " anti-aliased to " + resource.getAlias(), new Object[0]);
                resource = webAppContext.newResource(resource.getAlias());
            }
            if (LOG.isDebugEnabled()) {
                Logger logger2 = LOG;
                logger2.debug("Try webapp=" + resource + ", exists=" + resource.exists() + ", directory=" + resource.isDirectory() + " file=" + resource.getFile(), new Object[0]);
            }
            if (resource.exists() && !resource.isDirectory() && !resource.toString().startsWith("jar:")) {
                Resource newJarResource = JarResource.newJarResource(resource);
                if (newJarResource.exists() && newJarResource.isDirectory()) {
                    resource = newJarResource;
                }
            }
            if (resource.exists() && ((webAppContext.isCopyWebDir() && resource.getFile() != null && resource.getFile().isDirectory()) || ((webAppContext.isExtractWAR() && resource.getFile() != null && !resource.getFile().isDirectory()) || ((webAppContext.isExtractWAR() && resource.getFile() == null) || !resource.isDirectory())))) {
                File file2 = null;
                if (!(war == null || (file = Resource.newResource(war).getFile()) == null || !file.getName().toLowerCase(Locale.ENGLISH).endsWith(".war"))) {
                    File file3 = new File(file.getParent(), file.getName().substring(0, file.getName().length() - 4));
                    if (file3.exists() && file3.isDirectory() && file3.canWrite()) {
                        file2 = file3;
                    }
                }
                if (file2 == null) {
                    file2 = new File(webAppContext.getTempDirectory(), "webapp");
                }
                if (resource.getFile() == null || !resource.getFile().isDirectory()) {
                    File file4 = new File(webAppContext.getTempDirectory(), ".extract_lock");
                    if (!file2.exists()) {
                        file4.createNewFile();
                        file2.mkdir();
                        Logger logger3 = LOG;
                        logger3.info("Extract " + resource + " to " + file2, new Object[0]);
                        JarResource.newJarResource(resource).copyTo(file2);
                        file4.delete();
                    } else if (resource.lastModified() > file2.lastModified() || file4.exists()) {
                        file4.createNewFile();
                        C2439IO.delete(file2);
                        file2.mkdir();
                        Logger logger4 = LOG;
                        logger4.info("Extract " + resource + " to " + file2, new Object[0]);
                        JarResource.newJarResource(resource).copyTo(file2);
                        file4.delete();
                    }
                } else {
                    Logger logger5 = LOG;
                    logger5.info("Copy " + resource + " to " + file2, new Object[0]);
                    resource.copyTo(file2);
                }
                resource = Resource.newResource(file2.getCanonicalPath());
            }
            if (!resource.exists() || !resource.isDirectory()) {
                Logger logger6 = LOG;
                logger6.warn("Web application not found " + war, new Object[0]);
                throw new FileNotFoundException(war);
            }
            webAppContext.setBaseResource(resource);
            if (LOG.isDebugEnabled()) {
                Logger logger7 = LOG;
                logger7.debug("webapp=" + resource, new Object[0]);
            }
            baseResource = resource;
        }
        if (webAppContext.isCopyWebInf() && !webAppContext.isCopyWebDir()) {
            Resource addPath = baseResource.addPath("WEB-INF/");
            File file5 = new File(webAppContext.getTempDirectory(), "webinf");
            if (file5.exists()) {
                C2439IO.delete(file5);
            }
            file5.mkdir();
            Resource addPath2 = addPath.addPath("lib/");
            File file6 = new File(file5, "WEB-INF");
            file6.mkdir();
            if (addPath2.exists()) {
                File file7 = new File(file6, "lib");
                if (file7.exists()) {
                    C2439IO.delete(file7);
                }
                file7.mkdir();
                Logger logger8 = LOG;
                logger8.info("Copying WEB-INF/lib " + addPath2 + " to " + file7, new Object[0]);
                addPath2.copyTo(file7);
            }
            Resource addPath3 = addPath.addPath("classes/");
            if (addPath3.exists()) {
                File file8 = new File(file6, "classes");
                if (file8.exists()) {
                    C2439IO.delete(file8);
                }
                file8.mkdir();
                Logger logger9 = LOG;
                logger9.info("Copying WEB-INF/classes from " + addPath3 + " to " + file8.getAbsolutePath(), new Object[0]);
                addPath3.copyTo(file8);
            }
            ResourceCollection resourceCollection = new ResourceCollection(Resource.newResource(file5.getCanonicalPath()), baseResource);
            if (LOG.isDebugEnabled()) {
                Logger logger10 = LOG;
                logger10.debug("context.resourcebase = " + resourceCollection, new Object[0]);
            }
            webAppContext.setBaseResource(resourceCollection);
        }
    }

    public File findWorkDirectory(WebAppContext webAppContext) throws IOException {
        Resource webInf;
        if (webAppContext.getBaseResource() == null || (webInf = webAppContext.getWebInf()) == null || !webInf.exists()) {
            return null;
        }
        return new File(webInf.getFile(), "work");
    }

    public boolean isTempWorkDirectory(File file) {
        if (file == null) {
            return false;
        }
        if (file.getName().equalsIgnoreCase("work")) {
            return true;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return false;
        }
        return parentFile.getName().equalsIgnoreCase("work");
    }

    public static String getCanonicalNameForWebAppTmpDir(WebAppContext webAppContext) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("jetty-");
        if (webAppContext.getServer() != null) {
            Connector[] connectors = webAppContext.getServer().getConnectors();
            if (connectors.length > 0) {
                String host = (connectors == null || connectors[0] == null) ? "" : connectors[0].getHost();
                if (host == null) {
                    host = StringUtil.ALL_INTERFACES;
                }
                stringBuffer.append(host);
                stringBuffer.append("-");
                int localPort = (connectors == null || connectors[0] == null) ? 0 : connectors[0].getLocalPort();
                if (localPort < 0) {
                    localPort = connectors[0].getPort();
                }
                stringBuffer.append(localPort);
                stringBuffer.append("-");
            }
        }
        try {
            Resource baseResource = webAppContext.getBaseResource();
            if (baseResource == null) {
                if (webAppContext.getWar() == null || webAppContext.getWar().length() == 0) {
                    webAppContext.newResource(webAppContext.getResourceBase());
                }
                baseResource = webAppContext.newResource(webAppContext.getWar());
            }
            String decodePath = URIUtil.decodePath(baseResource.getURL().getPath());
            if (decodePath.endsWith("/")) {
                decodePath = decodePath.substring(0, decodePath.length() - 1);
            }
            if (decodePath.endsWith("!")) {
                decodePath = decodePath.substring(0, decodePath.length() - 1);
            }
            stringBuffer.append(decodePath.substring(decodePath.lastIndexOf("/") + 1, decodePath.length()));
            stringBuffer.append("-");
        } catch (Exception e) {
            LOG.warn("Can't generate resourceBase as part of webapp tmp dir name", (Throwable) e);
        }
        stringBuffer.append(webAppContext.getContextPath().replace('/', '_').replace('\\', '_'));
        stringBuffer.append("-");
        String[] virtualHosts = webAppContext.getVirtualHosts();
        if (virtualHosts == null || virtualHosts.length <= 0) {
            stringBuffer.append("any");
        } else {
            stringBuffer.append(virtualHosts[0]);
        }
        for (int i = 0; i < stringBuffer.length(); i++) {
            char charAt = stringBuffer.charAt(i);
            if (!Character.isJavaIdentifierPart(charAt) && "-.".indexOf(charAt) < 0) {
                stringBuffer.setCharAt(i, '.');
            }
        }
        stringBuffer.append("-");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public List<Resource> findJars(WebAppContext webAppContext) throws Exception {
        String str;
        ArrayList arrayList = new ArrayList();
        Resource webInf = webAppContext.getWebInf();
        if (webInf == null || !webInf.exists()) {
            return null;
        }
        Resource addPath = webInf.addPath("/lib");
        if (addPath.exists() && addPath.isDirectory()) {
            String[] list = addPath.list();
            int i = 0;
            while (list != null && i < list.length) {
                try {
                    Resource addPath2 = addPath.addPath(list[i]);
                    String lowerCase = addPath2.getName().toLowerCase(Locale.ENGLISH);
                    int lastIndexOf = lowerCase.lastIndexOf(46);
                    if (lastIndexOf < 0) {
                        str = null;
                    } else {
                        str = lowerCase.substring(lastIndexOf);
                    }
                    if (str != null && (str.equals(".jar") || str.equals(".zip"))) {
                        arrayList.add(addPath2);
                    }
                } catch (Exception e) {
                    LOG.warn(Log.EXCEPTION, (Throwable) e);
                }
                i++;
            }
        }
        return arrayList;
    }
}
