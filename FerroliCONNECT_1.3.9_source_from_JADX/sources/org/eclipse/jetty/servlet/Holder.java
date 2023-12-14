package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.Registration;
import javax.servlet.ServletContext;
import javax.servlet.UnavailableException;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class Holder<T> extends AbstractLifeCycle implements Dumpable {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) Holder.class);
    protected boolean _asyncSupported;
    protected transient Class<? extends T> _class;
    protected String _className;
    protected String _displayName;
    protected boolean _extInstance;
    protected final Map<String, String> _initParams = new HashMap(3);
    protected String _name;
    protected ServletHandler _servletHandler;
    private final Source _source;

    public enum Source {
        EMBEDDED,
        JAVAX_API,
        DESCRIPTOR,
        ANNOTATION
    }

    public void destroyInstance(Object obj) throws Exception {
    }

    protected Holder(Source source) {
        this._source = source;
        int i = C24371.$SwitchMap$org$eclipse$jetty$servlet$Holder$Source[this._source.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            this._asyncSupported = false;
        } else {
            this._asyncSupported = true;
        }
    }

    /* renamed from: org.eclipse.jetty.servlet.Holder$1 */
    static /* synthetic */ class C24371 {
        static final /* synthetic */ int[] $SwitchMap$org$eclipse$jetty$servlet$Holder$Source = new int[Source.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.eclipse.jetty.servlet.Holder$Source[] r0 = org.eclipse.jetty.servlet.Holder.Source.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$eclipse$jetty$servlet$Holder$Source = r0
                int[] r0 = $SwitchMap$org$eclipse$jetty$servlet$Holder$Source     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.eclipse.jetty.servlet.Holder$Source r1 = org.eclipse.jetty.servlet.Holder.Source.JAVAX_API     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$eclipse$jetty$servlet$Holder$Source     // Catch:{ NoSuchFieldError -> 0x001f }
                org.eclipse.jetty.servlet.Holder$Source r1 = org.eclipse.jetty.servlet.Holder.Source.DESCRIPTOR     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$eclipse$jetty$servlet$Holder$Source     // Catch:{ NoSuchFieldError -> 0x002a }
                org.eclipse.jetty.servlet.Holder$Source r1 = org.eclipse.jetty.servlet.Holder.Source.ANNOTATION     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.Holder.C24371.<clinit>():void");
        }
    }

    public Source getSource() {
        return this._source;
    }

    public boolean isInstance() {
        return this._extInstance;
    }

    public void doStart() throws Exception {
        String str;
        if (this._class == null && ((str = this._className) == null || str.equals(""))) {
            throw new UnavailableException("No class for Servlet or Filter for " + this._name);
        } else if (this._class == null) {
            try {
                this._class = Loader.loadClass(Holder.class, this._className);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Holding {}", this._class);
                }
            } catch (Exception e) {
                LOG.warn(e);
                throw new UnavailableException(e.getMessage());
            }
        }
    }

    public void doStop() throws Exception {
        if (!this._extInstance) {
            this._class = null;
        }
    }

    public String getClassName() {
        return this._className;
    }

    public Class<? extends T> getHeldClass() {
        return this._class;
    }

    public String getDisplayName() {
        return this._displayName;
    }

    public String getInitParameter(String str) {
        Map<String, String> map = this._initParams;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public Enumeration getInitParameterNames() {
        Map<String, String> map = this._initParams;
        if (map == null) {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }
        return Collections.enumeration(map.keySet());
    }

    public Map<String, String> getInitParameters() {
        return this._initParams;
    }

    public String getName() {
        return this._name;
    }

    public ServletHandler getServletHandler() {
        return this._servletHandler;
    }

    public void setClassName(String str) {
        this._className = str;
        this._class = null;
        if (this._name == null) {
            this._name = str + "-" + Integer.toHexString(hashCode());
        }
    }

    public void setHeldClass(Class<? extends T> cls) {
        this._class = cls;
        if (cls != null) {
            this._className = cls.getName();
            if (this._name == null) {
                this._name = cls.getName() + "-" + Integer.toHexString(hashCode());
            }
        }
    }

    public void setDisplayName(String str) {
        this._displayName = str;
    }

    public void setInitParameter(String str, String str2) {
        this._initParams.put(str, str2);
    }

    public void setInitParameters(Map<String, String> map) {
        this._initParams.clear();
        this._initParams.putAll(map);
    }

    public void setName(String str) {
        this._name = str;
    }

    public void setServletHandler(ServletHandler servletHandler) {
        this._servletHandler = servletHandler;
    }

    public void setAsyncSupported(boolean z) {
        this._asyncSupported = z;
    }

    public boolean isAsyncSupported() {
        return this._asyncSupported;
    }

    public String toString() {
        return this._name;
    }

    /* access modifiers changed from: protected */
    public void illegalStateIfContextStarted() {
        ContextHandler.Context context;
        ServletHandler servletHandler = this._servletHandler;
        if (servletHandler != null && (context = (ContextHandler.Context) servletHandler.getServletContext()) != null && context.getContextHandler().isStarted()) {
            throw new IllegalStateException("Started");
        }
    }

    public void dump(Appendable appendable, String str) throws IOException {
        appendable.append(this._name).append("==").append(this._className).append(" - ").append(AbstractLifeCycle.getState(this)).append("\n");
        AggregateLifeCycle.dump(appendable, str, this._initParams.entrySet());
    }

    public String dump() {
        return AggregateLifeCycle.dump((Dumpable) this);
    }

    protected class HolderConfig {
        protected HolderConfig() {
        }

        public ServletContext getServletContext() {
            return Holder.this._servletHandler.getServletContext();
        }

        public String getInitParameter(String str) {
            return Holder.this.getInitParameter(str);
        }

        public Enumeration getInitParameterNames() {
            return Holder.this.getInitParameterNames();
        }
    }

    protected class HolderRegistration implements Registration.Dynamic {
        protected HolderRegistration() {
        }

        public void setAsyncSupported(boolean z) {
            Holder.this.illegalStateIfContextStarted();
            Holder.this.setAsyncSupported(z);
        }

        public void setDescription(String str) {
            if (Holder.LOG.isDebugEnabled()) {
                Logger access$000 = Holder.LOG;
                access$000.debug(this + " is " + str, new Object[0]);
            }
        }

        public String getClassName() {
            return Holder.this.getClassName();
        }

        public String getInitParameter(String str) {
            return Holder.this.getInitParameter(str);
        }

        public Map<String, String> getInitParameters() {
            return Holder.this.getInitParameters();
        }

        public String getName() {
            return Holder.this.getName();
        }

        public boolean setInitParameter(String str, String str2) {
            Holder.this.illegalStateIfContextStarted();
            if (str == null) {
                throw new IllegalArgumentException("init parameter name required");
            } else if (str2 == null) {
                throw new IllegalArgumentException("non-null value required for init parameter " + str);
            } else if (Holder.this.getInitParameter(str) != null) {
                return false;
            } else {
                Holder.this.setInitParameter(str, str2);
                return true;
            }
        }

        public Set<String> setInitParameters(Map<String, String> map) {
            Holder.this.illegalStateIfContextStarted();
            HashSet hashSet = null;
            for (Map.Entry next : map.entrySet()) {
                if (next.getKey() == null) {
                    throw new IllegalArgumentException("init parameter name required");
                } else if (next.getValue() == null) {
                    throw new IllegalArgumentException("non-null value required for init parameter " + ((String) next.getKey()));
                } else if (Holder.this.getInitParameter((String) next.getKey()) != null) {
                    if (hashSet == null) {
                        hashSet = new HashSet();
                    }
                    hashSet.add(next.getKey());
                }
            }
            if (hashSet != null) {
                return hashSet;
            }
            Holder.this.getInitParameters().putAll(map);
            return Collections.emptySet();
        }
    }
}
