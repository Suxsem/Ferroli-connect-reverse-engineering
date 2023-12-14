package org.eclipse.jetty.webapp;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionListener;
import org.eclipse.jetty.security.ConstraintAware;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppClassLoader;

public class WebAppContext extends ServletContextHandler implements WebAppClassLoader.Context {
    public static final String BASETEMPDIR = "org.eclipse.jetty.webapp.basetempdir";
    public static final String ERROR_PAGE = "org.eclipse.jetty.server.error_page";
    private static final Logger LOG = Log.getLogger((Class<?>) WebAppContext.class);
    public static final String SERVER_CONFIG = "org.eclipse.jetty.webapp.configuration";
    public static final String SERVER_SRV_CLASSES = "org.eclipse.jetty.webapp.serverClasses";
    public static final String SERVER_SYS_CLASSES = "org.eclipse.jetty.webapp.systemClasses";
    public static final String TEMPDIR = "javax.servlet.context.tempdir";
    public static final String WEB_DEFAULTS_XML = "org/eclipse/jetty/webapp/webdefault.xml";
    private static String[] __dftConfigurationClasses = {"org.eclipse.jetty.webapp.WebInfConfiguration", "org.eclipse.jetty.webapp.WebXmlConfiguration", "org.eclipse.jetty.webapp.MetaInfConfiguration", "org.eclipse.jetty.webapp.FragmentConfiguration", JettyWebXmlConfiguration.XML_CONFIGURATION};
    public static final String[] __dftServerClasses = {"-org.eclipse.jetty.continuation.", "-org.eclipse.jetty.jndi.", "-org.eclipse.jetty.plus.jaas.", "-org.eclipse.jetty.websocket.WebSocket", "-org.eclipse.jetty.websocket.WebSocketFactory", "-org.eclipse.jetty.websocket.WebSocketServlet", "-org.eclipse.jetty.servlet.DefaultServlet", "-org.eclipse.jetty.servlet.listener.", "org.eclipse.jetty."};
    public static final String[] __dftSystemClasses = {"java.", "javax.", "org.xml.", "org.w3c.", "org.apache.commons.logging.", "org.eclipse.jetty.continuation.", "org.eclipse.jetty.jndi.", "org.eclipse.jetty.plus.jaas.", "org.eclipse.jetty.websocket.WebSocket", "org.eclipse.jetty.websocket.WebSocketFactory", "org.eclipse.jetty.websocket.WebSocketServlet", "org.eclipse.jetty.servlet.DefaultServlet"};
    private String[] __dftProtectedTargets = {"/web-inf", "/meta-inf"};
    private boolean _allowDuplicateFragmentNames = false;
    private String[] _configurationClasses = __dftConfigurationClasses;
    private boolean _configurationClassesSet = false;
    private boolean _configurationDiscovered = true;
    private Configuration[] _configurations;
    private boolean _configurationsSet = false;
    /* access modifiers changed from: private */
    public String[] _contextWhiteList = null;
    private boolean _copyDir = false;
    private boolean _copyWebInf = false;
    private String _defaultsDescriptor = WEB_DEFAULTS_XML;
    private String _descriptor = null;
    private boolean _distributable = false;
    private String _extraClasspath;
    private boolean _extractWAR = true;
    private boolean _logUrlOnStart = false;
    private MetaData _metadata = new MetaData();
    private final List<String> _overrideDescriptors = new ArrayList();
    private boolean _ownClassLoader = false;
    private boolean _parentLoaderPriority = Boolean.getBoolean("org.eclipse.jetty.server.webapp.parentLoaderPriority");
    private PermissionCollection _permissions;
    private Map<String, String> _resourceAliases;
    private ClasspathPattern _serverClasses = null;
    private ClasspathPattern _systemClasses = null;
    private boolean _throwUnavailableOnStartupException = false;
    private File _tmpDir;
    private Throwable _unavailableException;
    private String _war;

    public static WebAppContext getCurrentWebAppContext() {
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext == null) {
            return null;
        }
        ContextHandler contextHandler = currentContext.getContextHandler();
        if (contextHandler instanceof WebAppContext) {
            return (WebAppContext) contextHandler;
        }
        return null;
    }

    public WebAppContext() {
        super(3);
        this._scontext = new Context();
        setErrorHandler(new ErrorPageErrorHandler());
        setProtectedTargets(this.__dftProtectedTargets);
    }

    public WebAppContext(String str, String str2) {
        super((HandlerContainer) null, str2, 3);
        this._scontext = new Context();
        setContextPath(str2);
        setWar(str);
        setErrorHandler(new ErrorPageErrorHandler());
        setProtectedTargets(this.__dftProtectedTargets);
    }

    public WebAppContext(HandlerContainer handlerContainer, String str, String str2) {
        super(handlerContainer, str2, 3);
        this._scontext = new Context();
        setWar(str);
        setErrorHandler(new ErrorPageErrorHandler());
        setProtectedTargets(this.__dftProtectedTargets);
    }

    public WebAppContext(SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler) {
        super((HandlerContainer) null, sessionHandler, securityHandler, servletHandler, errorHandler);
        this._scontext = new Context();
        setErrorHandler(errorHandler == null ? new ErrorPageErrorHandler() : errorHandler);
        setProtectedTargets(this.__dftProtectedTargets);
    }

    public void setDisplayName(String str) {
        super.setDisplayName(str);
        ClassLoader classLoader = getClassLoader();
        if (classLoader != null && (classLoader instanceof WebAppClassLoader) && str != null) {
            ((WebAppClassLoader) classLoader).setName(str);
        }
    }

    public Throwable getUnavailableException() {
        return this._unavailableException;
    }

    public void setResourceAlias(String str, String str2) {
        if (this._resourceAliases == null) {
            this._resourceAliases = new HashMap(5);
        }
        this._resourceAliases.put(str, str2);
    }

    public Map<String, String> getResourceAliases() {
        Map<String, String> map = this._resourceAliases;
        if (map == null) {
            return null;
        }
        return map;
    }

    public void setResourceAliases(Map<String, String> map) {
        this._resourceAliases = map;
    }

    public String getResourceAlias(String str) {
        Map<String, String> map = this._resourceAliases;
        if (map == null) {
            return null;
        }
        String str2 = map.get(str);
        int length = str.length();
        while (str2 == null) {
            length = str.lastIndexOf("/", length - 1);
            if (length < 0) {
                break;
            }
            int i = length + 1;
            String str3 = this._resourceAliases.get(str.substring(0, i));
            if (str3 != null) {
                str2 = str3 + str.substring(i);
            }
        }
        return str2;
    }

    public String removeResourceAlias(String str) {
        Map<String, String> map = this._resourceAliases;
        if (map == null) {
            return null;
        }
        return map.remove(str);
    }

    public void setClassLoader(ClassLoader classLoader) {
        super.setClassLoader(classLoader);
        if (classLoader != null && (classLoader instanceof WebAppClassLoader) && getDisplayName() != null) {
            ((WebAppClassLoader) classLoader).setName(getDisplayName());
        }
    }

    public Resource getResource(String str) throws MalformedURLException {
        if (str == null || !str.startsWith("/")) {
            throw new MalformedURLException(str);
        }
        int i = 0;
        Throwable th = null;
        Resource resource = null;
        while (str != null) {
            int i2 = i + 1;
            if (i >= 100) {
                break;
            }
            try {
                resource = super.getResource(str);
                if (resource != null && resource.exists()) {
                    return resource;
                }
                str = getResourceAlias(str);
                i = i2;
            } catch (IOException e) {
                LOG.ignore(e);
                if (th == null) {
                    th = e;
                }
            }
        }
        if (th == null || !(th instanceof MalformedURLException)) {
            return resource;
        }
        throw ((MalformedURLException) th);
    }

    public boolean isConfigurationDiscovered() {
        return this._configurationDiscovered;
    }

    public void setConfigurationDiscovered(boolean z) {
        this._configurationDiscovered = z;
    }

    public void preConfigure() throws Exception {
        loadConfigurations();
        loadSystemClasses();
        loadServerClasses();
        this._ownClassLoader = false;
        if (getClassLoader() == null) {
            setClassLoader(new WebAppClassLoader(this));
            this._ownClassLoader = true;
        }
        if (LOG.isDebugEnabled()) {
            ClassLoader classLoader = getClassLoader();
            LOG.debug("Thread Context classloader {}", classLoader);
            for (ClassLoader parent = classLoader.getParent(); parent != null; parent = parent.getParent()) {
                LOG.debug("Parent class loader: {} ", parent);
            }
        }
        int i = 0;
        while (true) {
            Configuration[] configurationArr = this._configurations;
            if (i < configurationArr.length) {
                LOG.debug("preConfigure {} with {}", this, configurationArr[i]);
                this._configurations[i].preConfigure(this);
                i++;
            } else {
                return;
            }
        }
    }

    public void configure() throws Exception {
        int i = 0;
        while (true) {
            Configuration[] configurationArr = this._configurations;
            if (i < configurationArr.length) {
                LOG.debug("configure {} with {}", this, configurationArr[i]);
                this._configurations[i].configure(this);
                i++;
            } else {
                return;
            }
        }
    }

    public void postConfigure() throws Exception {
        int i = 0;
        while (true) {
            Configuration[] configurationArr = this._configurations;
            if (i < configurationArr.length) {
                LOG.debug("postConfigure {} with {}", this, configurationArr[i]);
                this._configurations[i].postConfigure(this);
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        try {
            this._metadata.setAllowDuplicateFragmentNames(isAllowDuplicateFragmentNames());
            preConfigure();
            super.doStart();
            postConfigure();
            if (isLogUrlOnStart()) {
                dumpUrl();
            }
        } catch (Exception e) {
            Logger logger = LOG;
            logger.warn("Failed startup of context " + this, (Throwable) e);
            this._unavailableException = e;
            setAvailable(false);
            if (isThrowUnavailableOnStartupException()) {
                throw e;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        try {
            int length = this._configurations.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                this._configurations[i].deconfigure(this);
                length = i;
            }
            if (this._metadata != null) {
                this._metadata.clear();
            }
            this._metadata = new MetaData();
        } finally {
            if (this._ownClassLoader) {
                setClassLoader((ClassLoader) null);
            }
            setAvailable(true);
            this._unavailableException = null;
        }
    }

    public void destroy() {
        MultiException multiException = new MultiException();
        Configuration[] configurationArr = this._configurations;
        if (configurationArr != null) {
            int length = configurationArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                try {
                    this._configurations[i].destroy(this);
                } catch (Exception e) {
                    multiException.add(e);
                }
                length = i;
            }
        }
        this._configurations = null;
        super.destroy();
        multiException.ifExceptionThrowRuntime();
    }

    private void dumpUrl() {
        Connector[] connectors = getServer().getConnectors();
        for (Connector name : connectors) {
            String name2 = name.getName();
            String displayName = getDisplayName();
            if (displayName == null) {
                displayName = "WebApp@" + connectors.hashCode();
            }
            LOG.info(displayName + " at http://" + name2 + getContextPath(), new Object[0]);
        }
    }

    public String[] getConfigurationClasses() {
        return this._configurationClasses;
    }

    public Configuration[] getConfigurations() {
        return this._configurations;
    }

    public String getDefaultsDescriptor() {
        return this._defaultsDescriptor;
    }

    @Deprecated
    public String getOverrideDescriptor() {
        if (this._overrideDescriptors.size() != 1) {
            return null;
        }
        return this._overrideDescriptors.get(0);
    }

    public List<String> getOverrideDescriptors() {
        return Collections.unmodifiableList(this._overrideDescriptors);
    }

    public PermissionCollection getPermissions() {
        return this._permissions;
    }

    public String[] getServerClasses() {
        if (this._serverClasses == null) {
            loadServerClasses();
        }
        return this._serverClasses.getPatterns();
    }

    public void addServerClass(String str) {
        if (this._serverClasses == null) {
            loadServerClasses();
        }
        this._serverClasses.addPattern(str);
    }

    public String[] getSystemClasses() {
        if (this._systemClasses == null) {
            loadSystemClasses();
        }
        return this._systemClasses.getPatterns();
    }

    public void addSystemClass(String str) {
        if (this._systemClasses == null) {
            loadSystemClasses();
        }
        this._systemClasses.addPattern(str);
    }

    public boolean isServerClass(String str) {
        if (this._serverClasses == null) {
            loadServerClasses();
        }
        return this._serverClasses.match(str);
    }

    public boolean isSystemClass(String str) {
        if (this._systemClasses == null) {
            loadSystemClasses();
        }
        return this._systemClasses.match(str);
    }

    /* access modifiers changed from: protected */
    public void loadSystemClasses() {
        Object attribute;
        if (this._systemClasses == null) {
            Server server = getServer();
            if (!(server == null || (attribute = server.getAttribute(SERVER_SYS_CLASSES)) == null || !(attribute instanceof String[]))) {
                this._systemClasses = new ClasspathPattern((String[]) attribute);
            }
            if (this._systemClasses == null) {
                this._systemClasses = new ClasspathPattern(__dftSystemClasses);
            }
        }
    }

    private void loadServerClasses() {
        Object attribute;
        if (this._serverClasses == null) {
            Server server = getServer();
            if (!(server == null || (attribute = server.getAttribute(SERVER_SRV_CLASSES)) == null || !(attribute instanceof String[]))) {
                this._serverClasses = new ClasspathPattern((String[]) attribute);
            }
            if (this._serverClasses == null) {
                this._serverClasses = new ClasspathPattern(__dftServerClasses);
            }
        }
    }

    public String getWar() {
        if (this._war == null) {
            this._war = getResourceBase();
        }
        return this._war;
    }

    public Resource getWebInf() throws IOException {
        if (super.getBaseResource() == null) {
            return null;
        }
        Resource addPath = super.getBaseResource().addPath("WEB-INF/");
        if (!addPath.exists() || !addPath.isDirectory()) {
            return null;
        }
        return addPath;
    }

    public boolean isDistributable() {
        return this._distributable;
    }

    public boolean isExtractWAR() {
        return this._extractWAR;
    }

    public boolean isCopyWebDir() {
        return this._copyDir;
    }

    public boolean isCopyWebInf() {
        return this._copyWebInf;
    }

    public boolean isParentLoaderPriority() {
        return this._parentLoaderPriority;
    }

    public String[] getDefaultConfigurationClasses() {
        return __dftConfigurationClasses;
    }

    public String[] getDefaultServerClasses() {
        return __dftServerClasses;
    }

    public String[] getDefaultSystemClasses() {
        return __dftSystemClasses;
    }

    /* access modifiers changed from: protected */
    public void loadConfigurations() throws Exception {
        if (this._configurations == null) {
            if (!this._configurationClassesSet) {
                this._configurationClasses = __dftConfigurationClasses;
            }
            this._configurations = new Configuration[this._configurationClasses.length];
            for (int i = 0; i < this._configurationClasses.length; i++) {
                this._configurations[i] = (Configuration) Loader.loadClass(getClass(), this._configurationClasses[i]).newInstance();
            }
        }
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        if (this._war == null) {
            str = "";
        } else {
            str = "," + this._war;
        }
        sb.append(str);
        return sb.toString();
    }

    public void setConfigurationClasses(String[] strArr) {
        String[] strArr2;
        if (!isRunning()) {
            if (strArr == null) {
                strArr2 = null;
            } else {
                strArr2 = (String[]) strArr.clone();
            }
            this._configurationClasses = strArr2;
            this._configurationClassesSet = true;
            this._configurations = null;
            return;
        }
        throw new IllegalStateException();
    }

    public void setConfigurations(Configuration[] configurationArr) {
        Configuration[] configurationArr2;
        if (!isRunning()) {
            if (configurationArr == null) {
                configurationArr2 = null;
            } else {
                configurationArr2 = (Configuration[]) configurationArr.clone();
            }
            this._configurations = configurationArr2;
            this._configurationsSet = true;
            return;
        }
        throw new IllegalStateException();
    }

    public void setDefaultsDescriptor(String str) {
        this._defaultsDescriptor = str;
    }

    @Deprecated
    public void setOverrideDescriptor(String str) {
        this._overrideDescriptors.clear();
        this._overrideDescriptors.add(str);
    }

    public void setOverrideDescriptors(List<String> list) {
        this._overrideDescriptors.clear();
        this._overrideDescriptors.addAll(list);
    }

    public void addOverrideDescriptor(String str) {
        this._overrideDescriptors.add(str);
    }

    public String getDescriptor() {
        return this._descriptor;
    }

    public void setDescriptor(String str) {
        this._descriptor = str;
    }

    public void setDistributable(boolean z) {
        this._distributable = z;
    }

    public void setEventListeners(EventListener[] eventListenerArr) {
        if (this._sessionHandler != null) {
            this._sessionHandler.clearEventListeners();
        }
        super.setEventListeners(eventListenerArr);
        int i = 0;
        while (eventListenerArr != null && i < eventListenerArr.length) {
            EventListener eventListener = eventListenerArr[i];
            if (((eventListener instanceof HttpSessionActivationListener) || (eventListener instanceof HttpSessionAttributeListener) || (eventListener instanceof HttpSessionBindingListener) || (eventListener instanceof HttpSessionListener)) && this._sessionHandler != null) {
                this._sessionHandler.addEventListener(eventListener);
            }
            i++;
        }
    }

    public void setExtractWAR(boolean z) {
        this._extractWAR = z;
    }

    public void setCopyWebDir(boolean z) {
        this._copyDir = z;
    }

    public void setCopyWebInf(boolean z) {
        this._copyWebInf = z;
    }

    public void setParentLoaderPriority(boolean z) {
        this._parentLoaderPriority = z;
    }

    public void setPermissions(PermissionCollection permissionCollection) {
        this._permissions = permissionCollection;
    }

    public void setContextWhiteList(String[] strArr) {
        this._contextWhiteList = strArr;
    }

    public void setServerClasses(String[] strArr) {
        this._serverClasses = new ClasspathPattern(strArr);
    }

    public void setSystemClasses(String[] strArr) {
        this._systemClasses = new ClasspathPattern(strArr);
    }

    public void setTempDirectory(File file) {
        if (!isStarted()) {
            if (file != null) {
                try {
                    file = new File(file.getCanonicalPath());
                } catch (IOException e) {
                    LOG.warn(Log.EXCEPTION, (Throwable) e);
                }
            }
            if (file != null && !file.exists()) {
                file.mkdir();
                file.deleteOnExit();
            }
            if (file == null || (file.exists() && file.isDirectory() && file.canWrite())) {
                if (file != null) {
                    try {
                        file = file.getCanonicalFile();
                    } catch (Exception e2) {
                        LOG.warn(e2);
                    }
                }
                this._tmpDir = file;
                setAttribute("javax.servlet.context.tempdir", this._tmpDir);
                return;
            }
            throw new IllegalArgumentException("Bad temp directory: " + file);
        }
        throw new IllegalStateException("Started");
    }

    public File getTempDirectory() {
        return this._tmpDir;
    }

    public void setWar(String str) {
        this._war = str;
    }

    public String getExtraClasspath() {
        return this._extraClasspath;
    }

    public void setExtraClasspath(String str) {
        this._extraClasspath = str;
    }

    public boolean isLogUrlOnStart() {
        return this._logUrlOnStart;
    }

    public void setLogUrlOnStart(boolean z) {
        this._logUrlOnStart = z;
    }

    public void setServer(Server server) {
        String[] strArr;
        super.setServer(server);
        if (!this._configurationsSet && !this._configurationClassesSet && server != null && (strArr = (String[]) server.getAttribute(SERVER_CONFIG)) != null) {
            setConfigurationClasses(strArr);
        }
    }

    public boolean isAllowDuplicateFragmentNames() {
        return this._allowDuplicateFragmentNames;
    }

    public void setAllowDuplicateFragmentNames(boolean z) {
        this._allowDuplicateFragmentNames = z;
    }

    public void setThrowUnavailableOnStartupException(boolean z) {
        this._throwUnavailableOnStartupException = z;
    }

    public boolean isThrowUnavailableOnStartupException() {
        return this._throwUnavailableOnStartupException;
    }

    /* access modifiers changed from: protected */
    public void startContext() throws Exception {
        configure();
        this._metadata.resolve(this);
        super.startContext();
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic dynamic, ServletSecurityElement servletSecurityElement) {
        HashSet hashSet = new HashSet();
        Collection<String> mappings = dynamic.getMappings();
        if (mappings != null) {
            ConstraintSecurityHandler.createConstraint(dynamic.getName(), servletSecurityElement);
            for (String next : mappings) {
                MetaData metaData = getMetaData();
                switch (metaData.getOrigin("constraint.url." + next)) {
                    case NotSet:
                        for (ConstraintMapping addConstraintMapping : ConstraintSecurityHandler.createConstraintsWithMappingsForPath(dynamic.getName(), next, servletSecurityElement)) {
                            ((ConstraintAware) getSecurityHandler()).addConstraintMapping(addConstraintMapping);
                        }
                        MetaData metaData2 = getMetaData();
                        metaData2.setOrigin("constraint.url." + next, Origin.API);
                        break;
                    case WebXml:
                    case WebDefaults:
                    case WebOverride:
                    case WebFragment:
                        hashSet.add(next);
                        break;
                    case Annotation:
                    case API:
                        List<ConstraintMapping> removeConstraintMappingsForPath = ConstraintSecurityHandler.removeConstraintMappingsForPath(next, ((ConstraintAware) getSecurityHandler()).getConstraintMappings());
                        removeConstraintMappingsForPath.addAll(ConstraintSecurityHandler.createConstraintsWithMappingsForPath(dynamic.getName(), next, servletSecurityElement));
                        ((ConstraintSecurityHandler) getSecurityHandler()).setConstraintMappings(removeConstraintMappingsForPath);
                        break;
                }
            }
        }
        return hashSet;
    }

    public class Context extends ServletContextHandler.Context {
        public Context() {
            super();
        }

        public URL getResource(String str) throws MalformedURLException {
            Resource resource = WebAppContext.this.getResource(str);
            if (resource == null || !resource.exists()) {
                return null;
            }
            if (resource.isDirectory() && (resource instanceof ResourceCollection) && !WebAppContext.this.isExtractWAR()) {
                Resource[] resources = ((ResourceCollection) resource).getResources();
                int length = resources.length;
                while (true) {
                    int i = length - 1;
                    if (length <= 0) {
                        break;
                    } else if (resources[i].getName().startsWith("jar:file")) {
                        return resources[i].getURL();
                    } else {
                        length = i;
                    }
                }
            }
            return resource.getURL();
        }

        public ServletContext getContext(String str) {
            ServletContext context = super.getContext(str);
            if (context == null || WebAppContext.this._contextWhiteList == null) {
                return context;
            }
            for (String equals : WebAppContext.this._contextWhiteList) {
                if (equals.equals(str)) {
                    return context;
                }
            }
            return null;
        }
    }

    public MetaData getMetaData() {
        return this._metadata;
    }
}
