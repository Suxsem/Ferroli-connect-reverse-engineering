package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.AsyncContinuation;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Constraint;

public class ContextHandlerCollection extends HandlerCollection {
    private static final Logger LOG = Log.getLogger((Class<?>) ContextHandlerCollection.class);
    private Class<? extends ContextHandler> _contextClass = ContextHandler.class;
    private volatile PathMap _contextMap;

    public ContextHandlerCollection() {
        super(true);
    }

    public void mapContexts() {
        Handler[] handlerArr;
        Map map;
        PathMap pathMap = new PathMap();
        Handler[] handlers = getHandlers();
        int i = 0;
        while (handlers != null && i < handlers.length) {
            if (handlers[i] instanceof ContextHandler) {
                handlerArr = new Handler[]{handlers[i]};
            } else if (handlers[i] instanceof HandlerContainer) {
                handlerArr = ((HandlerContainer) handlers[i]).getChildHandlersByClass(ContextHandler.class);
            } else {
                continue;
                i++;
            }
            for (Handler handler : handlerArr) {
                ContextHandler contextHandler = (ContextHandler) handler;
                String contextPath = contextHandler.getContextPath();
                if (contextPath == null || contextPath.indexOf(44) >= 0 || contextPath.startsWith(Constraint.ANY_ROLE)) {
                    throw new IllegalArgumentException("Illegal context spec:" + contextPath);
                }
                if (!contextPath.startsWith("/")) {
                    contextPath = '/' + contextPath;
                }
                if (contextPath.length() > 1) {
                    if (contextPath.endsWith("/")) {
                        contextPath = contextPath + Constraint.ANY_ROLE;
                    } else if (!contextPath.endsWith("/*")) {
                        contextPath = contextPath + "/*";
                    }
                }
                Object obj = pathMap.get(contextPath);
                String[] virtualHosts = contextHandler.getVirtualHosts();
                if (virtualHosts != null && virtualHosts.length > 0) {
                    if (obj instanceof Map) {
                        map = (Map) obj;
                    } else {
                        HashMap hashMap = new HashMap();
                        hashMap.put(Constraint.ANY_ROLE, obj);
                        pathMap.put(contextPath, hashMap);
                        map = hashMap;
                    }
                    for (String str : virtualHosts) {
                        map.put(str, LazyList.add(map.get(str), handlers[i]));
                    }
                } else if (obj instanceof Map) {
                    Map map2 = (Map) obj;
                    map2.put(Constraint.ANY_ROLE, LazyList.add(map2.get(Constraint.ANY_ROLE), handlers[i]));
                } else {
                    pathMap.put(contextPath, LazyList.add(obj, handlers[i]));
                }
            }
            continue;
            i++;
        }
        this._contextMap = pathMap;
    }

    public void setHandlers(Handler[] handlerArr) {
        this._contextMap = null;
        super.setHandlers(handlerArr);
        if (isStarted()) {
            mapContexts();
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        mapContexts();
        super.doStart();
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ContextHandler contextHandler;
        Handler[] handlers = getHandlers();
        if (handlers != null && handlers.length != 0) {
            AsyncContinuation asyncContinuation = request.getAsyncContinuation();
            if (!asyncContinuation.isAsync() || (contextHandler = asyncContinuation.getContextHandler()) == null) {
                PathMap pathMap = this._contextMap;
                int i = 0;
                if (pathMap == null || str == null || !str.startsWith("/")) {
                    while (i < handlers.length) {
                        handlers[i].handle(str, request, httpServletRequest, httpServletResponse);
                        if (!request.isHandled()) {
                            i++;
                        } else {
                            return;
                        }
                    }
                    return;
                }
                Object lazyMatches = pathMap.getLazyMatches(str);
                for (int i2 = 0; i2 < LazyList.size(lazyMatches); i2++) {
                    Object value = ((Map.Entry) LazyList.get(lazyMatches, i2)).getValue();
                    if (value instanceof Map) {
                        Map map = (Map) value;
                        String normalizeHostname = normalizeHostname(httpServletRequest.getServerName());
                        Object obj = map.get(normalizeHostname);
                        int i3 = 0;
                        while (i3 < LazyList.size(obj)) {
                            ((Handler) LazyList.get(obj, i3)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (!request.isHandled()) {
                                i3++;
                            } else {
                                return;
                            }
                        }
                        Object obj2 = map.get("*." + normalizeHostname.substring(normalizeHostname.indexOf(".") + 1));
                        int i4 = 0;
                        while (i4 < LazyList.size(obj2)) {
                            ((Handler) LazyList.get(obj2, i4)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (!request.isHandled()) {
                                i4++;
                            } else {
                                return;
                            }
                        }
                        Object obj3 = map.get(Constraint.ANY_ROLE);
                        int i5 = 0;
                        while (i5 < LazyList.size(obj3)) {
                            ((Handler) LazyList.get(obj3, i5)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (!request.isHandled()) {
                                i5++;
                            } else {
                                return;
                            }
                        }
                        continue;
                    } else {
                        int i6 = 0;
                        while (i6 < LazyList.size(value)) {
                            ((Handler) LazyList.get(value, i6)).handle(str, request, httpServletRequest, httpServletResponse);
                            if (!request.isHandled()) {
                                i6++;
                            } else {
                                return;
                            }
                        }
                        continue;
                    }
                }
                return;
            }
            contextHandler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    public ContextHandler addContext(String str, String str2) {
        try {
            ContextHandler contextHandler = (ContextHandler) this._contextClass.newInstance();
            contextHandler.setContextPath(str);
            contextHandler.setResourceBase(str2);
            addHandler(contextHandler);
            return contextHandler;
        } catch (Exception e) {
            LOG.debug(e);
            throw new Error(e);
        }
    }

    public Class getContextClass() {
        return this._contextClass;
    }

    public void setContextClass(Class cls) {
        if (cls == null || !ContextHandler.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException();
        }
        this._contextClass = cls;
    }

    private String normalizeHostname(String str) {
        if (str == null) {
            return null;
        }
        return str.endsWith(".") ? str.substring(0, str.length() - 1) : str;
    }
}
