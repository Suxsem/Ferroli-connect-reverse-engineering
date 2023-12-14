package org.eclipse.jetty.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;
import org.eclipse.jetty.security.ConstraintAware;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

public class ServletContextHandler extends ContextHandler {
    public static final int NO_SECURITY = 0;
    public static final int NO_SESSIONS = 0;
    public static final int SECURITY = 2;
    public static final int SESSIONS = 1;
    protected final List<Decorator> _decorators;
    protected Class<? extends SecurityHandler> _defaultSecurityHandlerClass;
    protected JspConfigDescriptor _jspConfig;
    protected int _options;
    private boolean _restrictListeners;
    protected Object _restrictedContextListeners;
    protected SecurityHandler _securityHandler;
    protected ServletHandler _servletHandler;
    protected SessionHandler _sessionHandler;
    protected HandlerWrapper _wrapper;

    public interface Decorator {
        void decorateFilterHolder(FilterHolder filterHolder) throws ServletException;

        <T extends Filter> T decorateFilterInstance(T t) throws ServletException;

        <T extends EventListener> T decorateListenerInstance(T t) throws ServletException;

        void decorateServletHolder(ServletHolder servletHolder) throws ServletException;

        <T extends Servlet> T decorateServletInstance(T t) throws ServletException;

        void destroyFilterInstance(Filter filter);

        void destroyListenerInstance(EventListener eventListener);

        void destroyServletInstance(Servlet servlet);
    }

    public ServletContextHandler() {
        this((HandlerContainer) null, (SessionHandler) null, (SecurityHandler) null, (ServletHandler) null, (ErrorHandler) null);
    }

    public ServletContextHandler(int i) {
        this((HandlerContainer) null, (String) null, i);
    }

    public ServletContextHandler(HandlerContainer handlerContainer, String str) {
        this(handlerContainer, str, (SessionHandler) null, (SecurityHandler) null, (ServletHandler) null, (ErrorHandler) null);
    }

    public ServletContextHandler(HandlerContainer handlerContainer, String str, int i) {
        this(handlerContainer, str, (SessionHandler) null, (SecurityHandler) null, (ServletHandler) null, (ErrorHandler) null);
        this._options = i;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ServletContextHandler(HandlerContainer handlerContainer, String str, boolean z, boolean z2) {
        this(handlerContainer, str, z | (z2 ? (char) 2 : 0) ? 1 : 0);
    }

    public ServletContextHandler(HandlerContainer handlerContainer, SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler) {
        this(handlerContainer, (String) null, sessionHandler, securityHandler, servletHandler, errorHandler);
    }

    public ServletContextHandler(HandlerContainer handlerContainer, String str, SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler) {
        super((ContextHandler.Context) null);
        this._decorators = new ArrayList();
        this._defaultSecurityHandlerClass = ConstraintSecurityHandler.class;
        this._restrictListeners = true;
        this._scontext = new Context();
        this._sessionHandler = sessionHandler;
        this._securityHandler = securityHandler;
        this._servletHandler = servletHandler;
        if (errorHandler != null) {
            setErrorHandler(errorHandler);
        }
        if (str != null) {
            setContextPath(str);
        }
        if (handlerContainer instanceof HandlerWrapper) {
            ((HandlerWrapper) handlerContainer).setHandler(this);
        } else if (handlerContainer instanceof HandlerCollection) {
            ((HandlerCollection) handlerContainer).addHandler(this);
        }
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
        List<Decorator> list = this._decorators;
        if (list != null) {
            list.clear();
        }
        HandlerWrapper handlerWrapper = this._wrapper;
        if (handlerWrapper != null) {
            handlerWrapper.setHandler((Handler) null);
        }
    }

    public Class<? extends SecurityHandler> getDefaultSecurityHandlerClass() {
        return this._defaultSecurityHandlerClass;
    }

    public void setDefaultSecurityHandlerClass(Class<? extends SecurityHandler> cls) {
        this._defaultSecurityHandlerClass = cls;
    }

    /* access modifiers changed from: protected */
    public SessionHandler newSessionHandler() {
        return new SessionHandler();
    }

    /* access modifiers changed from: protected */
    public SecurityHandler newSecurityHandler() {
        try {
            return (SecurityHandler) this._defaultSecurityHandlerClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public ServletHandler newServletHandler() {
        return new ServletHandler();
    }

    /* access modifiers changed from: protected */
    public void startContext() throws Exception {
        getSessionHandler();
        getSecurityHandler();
        getServletHandler();
        HandlerWrapper handlerWrapper = this._servletHandler;
        SecurityHandler securityHandler = this._securityHandler;
        if (securityHandler != null) {
            securityHandler.setHandler(handlerWrapper);
            handlerWrapper = this._securityHandler;
        }
        SessionHandler sessionHandler = this._sessionHandler;
        if (sessionHandler != null) {
            sessionHandler.setHandler(handlerWrapper);
            handlerWrapper = this._sessionHandler;
        }
        this._wrapper = this;
        while (true) {
            HandlerWrapper handlerWrapper2 = this._wrapper;
            if (handlerWrapper2 == handlerWrapper || !(handlerWrapper2.getHandler() instanceof HandlerWrapper)) {
                HandlerWrapper handlerWrapper3 = this._wrapper;
            } else {
                this._wrapper = (HandlerWrapper) this._wrapper.getHandler();
            }
        }
        HandlerWrapper handlerWrapper32 = this._wrapper;
        if (handlerWrapper32 != handlerWrapper) {
            if (handlerWrapper32.getHandler() == null) {
                this._wrapper.setHandler(handlerWrapper);
            } else {
                throw new IllegalStateException("!ScopedHandler");
            }
        }
        super.startContext();
        ServletHandler servletHandler = this._servletHandler;
        if (servletHandler != null && servletHandler.isStarted()) {
            for (int size = this._decorators.size() - 1; size >= 0; size--) {
                Decorator decorator = this._decorators.get(size);
                if (this._servletHandler.getFilters() != null) {
                    for (FilterHolder decorateFilterHolder : this._servletHandler.getFilters()) {
                        decorator.decorateFilterHolder(decorateFilterHolder);
                    }
                }
                if (this._servletHandler.getServlets() != null) {
                    for (ServletHolder decorateServletHolder : this._servletHandler.getServlets()) {
                        decorator.decorateServletHolder(decorateServletHolder);
                    }
                }
            }
            this._servletHandler.initialize();
        }
    }

    public SecurityHandler getSecurityHandler() {
        if (this._securityHandler == null && (this._options & 2) != 0 && !isStarted()) {
            this._securityHandler = newSecurityHandler();
        }
        return this._securityHandler;
    }

    public ServletHandler getServletHandler() {
        if (this._servletHandler == null && !isStarted()) {
            this._servletHandler = newServletHandler();
        }
        return this._servletHandler;
    }

    public SessionHandler getSessionHandler() {
        if (this._sessionHandler == null && (this._options & 1) != 0 && !isStarted()) {
            this._sessionHandler = newSessionHandler();
        }
        return this._sessionHandler;
    }

    public ServletHolder addServlet(String str, String str2) {
        return getServletHandler().addServletWithMapping(str, str2);
    }

    public ServletHolder addServlet(Class<? extends Servlet> cls, String str) {
        return getServletHandler().addServletWithMapping(cls.getName(), str);
    }

    public void addServlet(ServletHolder servletHolder, String str) {
        getServletHandler().addServletWithMapping(servletHolder, str);
    }

    public void addFilter(FilterHolder filterHolder, String str, EnumSet<DispatcherType> enumSet) {
        getServletHandler().addFilterWithMapping(filterHolder, str, enumSet);
    }

    public FilterHolder addFilter(Class<? extends Filter> cls, String str, EnumSet<DispatcherType> enumSet) {
        return getServletHandler().addFilterWithMapping(cls, str, enumSet);
    }

    public FilterHolder addFilter(String str, String str2, EnumSet<DispatcherType> enumSet) {
        return getServletHandler().addFilterWithMapping(str, str2, enumSet);
    }

    /* access modifiers changed from: protected */
    public ServletRegistration.Dynamic dynamicHolderAdded(ServletHolder servletHolder) {
        return servletHolder.getRegistration();
    }

    /* access modifiers changed from: protected */
    public void addRoles(String... strArr) {
        SecurityHandler securityHandler = this._securityHandler;
        if (securityHandler != null && (securityHandler instanceof ConstraintAware)) {
            HashSet hashSet = new HashSet();
            Set<String> roles = ((ConstraintAware) this._securityHandler).getRoles();
            if (roles != null) {
                hashSet.addAll(roles);
            }
            hashSet.addAll(Arrays.asList(strArr));
            ((ConstraintSecurityHandler) this._securityHandler).setRoles(hashSet);
        }
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic dynamic, ServletSecurityElement servletSecurityElement) {
        Collection<String> mappings = dynamic.getMappings();
        if (mappings != null) {
            for (String createConstraintsWithMappingsForPath : mappings) {
                for (ConstraintMapping addConstraintMapping : ConstraintSecurityHandler.createConstraintsWithMappingsForPath(dynamic.getName(), createConstraintsWithMappingsForPath, servletSecurityElement)) {
                    ((ConstraintAware) getSecurityHandler()).addConstraintMapping(addConstraintMapping);
                }
            }
        }
        return Collections.emptySet();
    }

    public void restrictEventListener(EventListener eventListener) {
        if (this._restrictListeners && (eventListener instanceof ServletContextListener)) {
            this._restrictedContextListeners = LazyList.add(this._restrictedContextListeners, eventListener);
        }
    }

    public boolean isRestrictListeners() {
        return this._restrictListeners;
    }

    public void setRestrictListeners(boolean z) {
        this._restrictListeners = z;
    }

    public void callContextInitialized(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        try {
            if (LazyList.contains(this._restrictedContextListeners, servletContextListener)) {
                getServletContext().setEnabled(false);
            }
            super.callContextInitialized(servletContextListener, servletContextEvent);
        } finally {
            getServletContext().setEnabled(true);
        }
    }

    public void callContextDestroyed(ServletContextListener servletContextListener, ServletContextEvent servletContextEvent) {
        super.callContextDestroyed(servletContextListener, servletContextEvent);
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        if (!isStarted()) {
            this._sessionHandler = sessionHandler;
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    public void setSecurityHandler(SecurityHandler securityHandler) {
        if (!isStarted()) {
            this._securityHandler = securityHandler;
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    public void setServletHandler(ServletHandler servletHandler) {
        if (!isStarted()) {
            this._servletHandler = servletHandler;
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    public List<Decorator> getDecorators() {
        return Collections.unmodifiableList(this._decorators);
    }

    public void setDecorators(List<Decorator> list) {
        this._decorators.clear();
        this._decorators.addAll(list);
    }

    public void addDecorator(Decorator decorator) {
        this._decorators.add(decorator);
    }

    /* access modifiers changed from: package-private */
    public void destroyServlet(Servlet servlet) {
        for (Decorator destroyServletInstance : this._decorators) {
            destroyServletInstance.destroyServletInstance(servlet);
        }
    }

    /* access modifiers changed from: package-private */
    public void destroyFilter(Filter filter) {
        for (Decorator destroyFilterInstance : this._decorators) {
            destroyFilterInstance.destroyFilterInstance(filter);
        }
    }

    public static class JspPropertyGroup implements JspPropertyGroupDescriptor {
        private String _buffer;
        private String _defaultContentType;
        private String _deferredSyntaxAllowedAsLiteral;
        private String _elIgnored;
        private String _errorOnUndeclaredNamespace;
        private List<String> _includeCodas = new ArrayList();
        private List<String> _includePreludes = new ArrayList();
        private String _isXml;
        private String _pageEncoding;
        private String _scriptingInvalid;
        private String _trimDirectiveWhitespaces;
        private List<String> _urlPatterns = new ArrayList();

        public Collection<String> getUrlPatterns() {
            return new ArrayList(this._urlPatterns);
        }

        public void addUrlPattern(String str) {
            if (!this._urlPatterns.contains(str)) {
                this._urlPatterns.add(str);
            }
        }

        public String getElIgnored() {
            return this._elIgnored;
        }

        public void setElIgnored(String str) {
            this._elIgnored = str;
        }

        public String getPageEncoding() {
            return this._pageEncoding;
        }

        public void setPageEncoding(String str) {
            this._pageEncoding = str;
        }

        public void setScriptingInvalid(String str) {
            this._scriptingInvalid = str;
        }

        public void setIsXml(String str) {
            this._isXml = str;
        }

        public void setDeferredSyntaxAllowedAsLiteral(String str) {
            this._deferredSyntaxAllowedAsLiteral = str;
        }

        public void setTrimDirectiveWhitespaces(String str) {
            this._trimDirectiveWhitespaces = str;
        }

        public void setDefaultContentType(String str) {
            this._defaultContentType = str;
        }

        public void setBuffer(String str) {
            this._buffer = str;
        }

        public void setErrorOnUndeclaredNamespace(String str) {
            this._errorOnUndeclaredNamespace = str;
        }

        public String getScriptingInvalid() {
            return this._scriptingInvalid;
        }

        public String getIsXml() {
            return this._isXml;
        }

        public Collection<String> getIncludePreludes() {
            return new ArrayList(this._includePreludes);
        }

        public void addIncludePrelude(String str) {
            if (!this._includePreludes.contains(str)) {
                this._includePreludes.add(str);
            }
        }

        public Collection<String> getIncludeCodas() {
            return new ArrayList(this._includeCodas);
        }

        public void addIncludeCoda(String str) {
            if (!this._includeCodas.contains(str)) {
                this._includeCodas.add(str);
            }
        }

        public String getDeferredSyntaxAllowedAsLiteral() {
            return this._deferredSyntaxAllowedAsLiteral;
        }

        public String getTrimDirectiveWhitespaces() {
            return this._trimDirectiveWhitespaces;
        }

        public String getDefaultContentType() {
            return this._defaultContentType;
        }

        public String getBuffer() {
            return this._buffer;
        }

        public String getErrorOnUndeclaredNamespace() {
            return this._errorOnUndeclaredNamespace;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("JspPropertyGroupDescriptor:");
            stringBuffer.append(" el-ignored=" + this._elIgnored);
            stringBuffer.append(" is-xml=" + this._isXml);
            stringBuffer.append(" page-encoding=" + this._pageEncoding);
            stringBuffer.append(" scripting-invalid=" + this._scriptingInvalid);
            stringBuffer.append(" deferred-syntax-allowed-as-literal=" + this._deferredSyntaxAllowedAsLiteral);
            stringBuffer.append(" trim-directive-whitespaces" + this._trimDirectiveWhitespaces);
            stringBuffer.append(" default-content-type=" + this._defaultContentType);
            stringBuffer.append(" buffer=" + this._buffer);
            stringBuffer.append(" error-on-undeclared-namespace=" + this._errorOnUndeclaredNamespace);
            for (String str : this._includePreludes) {
                stringBuffer.append(" include-prelude=" + str);
            }
            for (String str2 : this._includeCodas) {
                stringBuffer.append(" include-coda=" + str2);
            }
            return stringBuffer.toString();
        }
    }

    public static class TagLib implements TaglibDescriptor {
        private String _location;
        private String _uri;

        public String getTaglibURI() {
            return this._uri;
        }

        public void setTaglibURI(String str) {
            this._uri = str;
        }

        public String getTaglibLocation() {
            return this._location;
        }

        public void setTaglibLocation(String str) {
            this._location = str;
        }

        public String toString() {
            return "TagLibDescriptor: taglib-uri=" + this._uri + " location=" + this._location;
        }
    }

    public static class JspConfig implements JspConfigDescriptor {
        private List<JspPropertyGroupDescriptor> _jspPropertyGroups = new ArrayList();
        private List<TaglibDescriptor> _taglibs = new ArrayList();

        public Collection<TaglibDescriptor> getTaglibs() {
            return new ArrayList(this._taglibs);
        }

        public void addTaglibDescriptor(TaglibDescriptor taglibDescriptor) {
            this._taglibs.add(taglibDescriptor);
        }

        public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
            return new ArrayList(this._jspPropertyGroups);
        }

        public void addJspPropertyGroup(JspPropertyGroupDescriptor jspPropertyGroupDescriptor) {
            this._jspPropertyGroups.add(jspPropertyGroupDescriptor);
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("JspConfigDescriptor: \n");
            for (TaglibDescriptor taglibDescriptor : this._taglibs) {
                stringBuffer.append(taglibDescriptor + "\n");
            }
            for (JspPropertyGroupDescriptor jspPropertyGroupDescriptor : this._jspPropertyGroups) {
                stringBuffer.append(jspPropertyGroupDescriptor + "\n");
            }
            return stringBuffer.toString();
        }
    }

    public class Context extends ContextHandler.Context {
        public Context() {
            super();
        }

        public RequestDispatcher getNamedDispatcher(String str) {
            ServletHolder servlet;
            ServletContextHandler servletContextHandler = ServletContextHandler.this;
            if (servletContextHandler._servletHandler == null || (servlet = ServletContextHandler.this._servletHandler.getServlet(str)) == null || !servlet.isEnabled()) {
                return null;
            }
            return new Dispatcher(servletContextHandler, str);
        }

        public FilterRegistration.Dynamic addFilter(String str, Class<? extends Filter> cls) {
            if (ServletContextHandler.this.isStarted()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                ServletHandler servletHandler = ServletContextHandler.this.getServletHandler();
                FilterHolder filter = servletHandler.getFilter(str);
                if (filter == null) {
                    FilterHolder newFilterHolder = servletHandler.newFilterHolder(Holder.Source.JAVAX_API);
                    newFilterHolder.setName(str);
                    newFilterHolder.setHeldClass(cls);
                    servletHandler.addFilter(newFilterHolder);
                    return newFilterHolder.getRegistration();
                } else if (filter.getClassName() != null || filter.getHeldClass() != null) {
                    return null;
                } else {
                    filter.setHeldClass(cls);
                    return filter.getRegistration();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public FilterRegistration.Dynamic addFilter(String str, String str2) {
            if (ServletContextHandler.this.isStarted()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                ServletHandler servletHandler = ServletContextHandler.this.getServletHandler();
                FilterHolder filter = servletHandler.getFilter(str);
                if (filter == null) {
                    FilterHolder newFilterHolder = servletHandler.newFilterHolder(Holder.Source.JAVAX_API);
                    newFilterHolder.setName(str);
                    newFilterHolder.setClassName(str2);
                    servletHandler.addFilter(newFilterHolder);
                    return newFilterHolder.getRegistration();
                } else if (filter.getClassName() != null || filter.getHeldClass() != null) {
                    return null;
                } else {
                    filter.setClassName(str2);
                    return filter.getRegistration();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public FilterRegistration.Dynamic addFilter(String str, Filter filter) {
            if (ServletContextHandler.this.isStarted()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                ServletHandler servletHandler = ServletContextHandler.this.getServletHandler();
                FilterHolder filter2 = servletHandler.getFilter(str);
                if (filter2 == null) {
                    FilterHolder newFilterHolder = servletHandler.newFilterHolder(Holder.Source.JAVAX_API);
                    newFilterHolder.setName(str);
                    newFilterHolder.setFilter(filter);
                    servletHandler.addFilter(newFilterHolder);
                    return newFilterHolder.getRegistration();
                } else if (filter2.getClassName() != null || filter2.getHeldClass() != null) {
                    return null;
                } else {
                    filter2.setFilter(filter);
                    return filter2.getRegistration();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public ServletRegistration.Dynamic addServlet(String str, Class<? extends Servlet> cls) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                ServletHandler servletHandler = ServletContextHandler.this.getServletHandler();
                ServletHolder servlet = servletHandler.getServlet(str);
                if (servlet == null) {
                    ServletHolder newServletHolder = servletHandler.newServletHolder(Holder.Source.JAVAX_API);
                    newServletHolder.setName(str);
                    newServletHolder.setHeldClass(cls);
                    servletHandler.addServlet(newServletHolder);
                    return ServletContextHandler.this.dynamicHolderAdded(newServletHolder);
                } else if (servlet.getClassName() != null || servlet.getHeldClass() != null) {
                    return null;
                } else {
                    servlet.setHeldClass(cls);
                    return servlet.getRegistration();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public ServletRegistration.Dynamic addServlet(String str, String str2) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                ServletHandler servletHandler = ServletContextHandler.this.getServletHandler();
                ServletHolder servlet = servletHandler.getServlet(str);
                if (servlet == null) {
                    ServletHolder newServletHolder = servletHandler.newServletHolder(Holder.Source.JAVAX_API);
                    newServletHolder.setName(str);
                    newServletHolder.setClassName(str2);
                    servletHandler.addServlet(newServletHolder);
                    return ServletContextHandler.this.dynamicHolderAdded(newServletHolder);
                } else if (servlet.getClassName() != null || servlet.getHeldClass() != null) {
                    return null;
                } else {
                    servlet.setClassName(str2);
                    return servlet.getRegistration();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public ServletRegistration.Dynamic addServlet(String str, Servlet servlet) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                ServletHandler servletHandler = ServletContextHandler.this.getServletHandler();
                ServletHolder servlet2 = servletHandler.getServlet(str);
                if (servlet2 == null) {
                    ServletHolder newServletHolder = servletHandler.newServletHolder(Holder.Source.JAVAX_API);
                    newServletHolder.setName(str);
                    newServletHolder.setServlet(servlet);
                    servletHandler.addServlet(newServletHolder);
                    return ServletContextHandler.this.dynamicHolderAdded(newServletHolder);
                } else if (servlet2.getClassName() != null || servlet2.getHeldClass() != null) {
                    return null;
                } else {
                    servlet2.setServlet(servlet);
                    return servlet2.getRegistration();
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public boolean setInitParameter(String str, String str2) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                return super.setInitParameter(str, str2);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public <T extends Filter> T createFilter(Class<T> cls) throws ServletException {
            try {
                T t = (Filter) cls.newInstance();
                for (int size = ServletContextHandler.this._decorators.size() - 1; size >= 0; size--) {
                    t = ServletContextHandler.this._decorators.get(size).decorateFilterInstance(t);
                }
                return t;
            } catch (InstantiationException e) {
                throw new ServletException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new ServletException((Throwable) e2);
            }
        }

        public <T extends Servlet> T createServlet(Class<T> cls) throws ServletException {
            try {
                T t = (Servlet) cls.newInstance();
                for (int size = ServletContextHandler.this._decorators.size() - 1; size >= 0; size--) {
                    t = ServletContextHandler.this._decorators.get(size).decorateServletInstance(t);
                }
                return t;
            } catch (InstantiationException e) {
                throw new ServletException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new ServletException((Throwable) e2);
            }
        }

        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
            if (ServletContextHandler.this._sessionHandler != null) {
                return ServletContextHandler.this._sessionHandler.getSessionManager().getDefaultSessionTrackingModes();
            }
            return null;
        }

        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
            if (ServletContextHandler.this._sessionHandler != null) {
                return ServletContextHandler.this._sessionHandler.getSessionManager().getEffectiveSessionTrackingModes();
            }
            return null;
        }

        public FilterRegistration getFilterRegistration(String str) {
            if (this._enabled) {
                FilterHolder filter = ServletContextHandler.this.getServletHandler().getFilter(str);
                if (filter == null) {
                    return null;
                }
                return filter.getRegistration();
            }
            throw new UnsupportedOperationException();
        }

        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
            if (this._enabled) {
                HashMap hashMap = new HashMap();
                FilterHolder[] filters = ServletContextHandler.this.getServletHandler().getFilters();
                if (filters != null) {
                    for (FilterHolder filterHolder : filters) {
                        hashMap.put(filterHolder.getName(), filterHolder.getRegistration());
                    }
                }
                return hashMap;
            }
            throw new UnsupportedOperationException();
        }

        public ServletRegistration getServletRegistration(String str) {
            if (this._enabled) {
                ServletHolder servlet = ServletContextHandler.this.getServletHandler().getServlet(str);
                if (servlet == null) {
                    return null;
                }
                return servlet.getRegistration();
            }
            throw new UnsupportedOperationException();
        }

        public Map<String, ? extends ServletRegistration> getServletRegistrations() {
            if (this._enabled) {
                HashMap hashMap = new HashMap();
                ServletHolder[] servlets = ServletContextHandler.this.getServletHandler().getServlets();
                if (servlets != null) {
                    for (ServletHolder servletHolder : servlets) {
                        hashMap.put(servletHolder.getName(), servletHolder.getRegistration());
                    }
                }
                return hashMap;
            }
            throw new UnsupportedOperationException();
        }

        public SessionCookieConfig getSessionCookieConfig() {
            if (!this._enabled) {
                throw new UnsupportedOperationException();
            } else if (ServletContextHandler.this._sessionHandler != null) {
                return ServletContextHandler.this._sessionHandler.getSessionManager().getSessionCookieConfig();
            } else {
                return null;
            }
        }

        public void setSessionTrackingModes(Set<SessionTrackingMode> set) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (!this._enabled) {
                throw new UnsupportedOperationException();
            } else if (ServletContextHandler.this._sessionHandler != null) {
                ServletContextHandler.this._sessionHandler.getSessionManager().setSessionTrackingModes(set);
            }
        }

        public void addListener(String str) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                super.addListener(str);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public <T extends EventListener> void addListener(T t) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                super.addListener(t);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public void addListener(Class<? extends EventListener> cls) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                super.addListener(cls);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public <T extends EventListener> T createListener(Class<T> cls) throws ServletException {
            try {
                T createListener = super.createListener(cls);
                for (int size = ServletContextHandler.this._decorators.size() - 1; size >= 0; size--) {
                    createListener = ServletContextHandler.this._decorators.get(size).decorateListenerInstance(createListener);
                }
                return createListener;
            } catch (ServletException e) {
                throw e;
            } catch (Exception e2) {
                throw new ServletException((Throwable) e2);
            }
        }

        public JspConfigDescriptor getJspConfigDescriptor() {
            return ServletContextHandler.this._jspConfig;
        }

        public void setJspConfigDescriptor(JspConfigDescriptor jspConfigDescriptor) {
            ServletContextHandler.this._jspConfig = jspConfigDescriptor;
        }

        public void declareRoles(String... strArr) {
            if (!ServletContextHandler.this.isStarting()) {
                throw new IllegalStateException();
            } else if (this._enabled) {
                ServletContextHandler.this.addRoles(strArr);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }
}
