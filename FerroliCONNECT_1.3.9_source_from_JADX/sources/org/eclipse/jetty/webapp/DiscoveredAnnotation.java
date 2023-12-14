package org.eclipse.jetty.webapp;

import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public abstract class DiscoveredAnnotation {
    private static final Logger LOG = Log.getLogger((Class<?>) DiscoveredAnnotation.class);
    protected String _className;
    protected Class<?> _clazz;
    protected WebAppContext _context;
    protected Resource _resource;

    public abstract void apply();

    public DiscoveredAnnotation(WebAppContext webAppContext, String str) {
        this(webAppContext, str, (Resource) null);
    }

    public DiscoveredAnnotation(WebAppContext webAppContext, String str, Resource resource) {
        this._context = webAppContext;
        this._className = str;
        this._resource = resource;
    }

    public Resource getResource() {
        return this._resource;
    }

    public Class<?> getTargetClass() {
        Class<?> cls = this._clazz;
        if (cls != null) {
            return cls;
        }
        loadClass();
        return this._clazz;
    }

    private void loadClass() {
        String str;
        if (this._clazz == null && (str = this._className) != null) {
            try {
                this._clazz = Loader.loadClass((Class) null, str);
            } catch (Exception e) {
                LOG.warn(e);
            }
        }
    }
}
