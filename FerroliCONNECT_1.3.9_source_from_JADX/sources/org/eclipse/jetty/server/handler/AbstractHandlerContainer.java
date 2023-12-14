package org.eclipse.jetty.server.handler;

import java.io.IOException;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.TypeUtil;

public abstract class AbstractHandlerContainer extends AbstractHandler implements HandlerContainer {
    /* access modifiers changed from: protected */
    public Object expandChildren(Object obj, Class<?> cls) {
        return obj;
    }

    public Handler[] getChildHandlers() {
        return (Handler[]) LazyList.toArray(expandChildren((Object) null, (Class<?>) null), Handler.class);
    }

    public Handler[] getChildHandlersByClass(Class<?> cls) {
        return (Handler[]) LazyList.toArray(expandChildren((Object) null, cls), cls);
    }

    public <T extends Handler> T getChildHandlerByClass(Class<T> cls) {
        Object expandChildren = expandChildren((Object) null, cls);
        if (expandChildren == null) {
            return null;
        }
        return (Handler) LazyList.get(expandChildren, 0);
    }

    /* access modifiers changed from: protected */
    public Object expandHandler(Handler handler, Object obj, Class<Handler> cls) {
        if (handler == null) {
            return obj;
        }
        if (cls == null || cls.isAssignableFrom(handler.getClass())) {
            obj = LazyList.add(obj, handler);
        }
        if (handler instanceof AbstractHandlerContainer) {
            return ((AbstractHandlerContainer) handler).expandChildren(obj, cls);
        }
        if (!(handler instanceof HandlerContainer)) {
            return obj;
        }
        HandlerContainer handlerContainer = (HandlerContainer) handler;
        return LazyList.addArray(obj, cls == null ? handlerContainer.getChildHandlers() : handlerContainer.getChildHandlersByClass(cls));
    }

    public static <T extends HandlerContainer> T findContainerOf(HandlerContainer handlerContainer, Class<T> cls, Handler handler) {
        T[] childHandlersByClass;
        if (!(handlerContainer == null || handler == null || (childHandlersByClass = handlerContainer.getChildHandlersByClass(cls)) == null)) {
            for (T t : childHandlersByClass) {
                T t2 = (HandlerContainer) t;
                Handler[] childHandlersByClass2 = t2.getChildHandlersByClass(handler.getClass());
                if (childHandlersByClass2 != null) {
                    for (Handler handler2 : childHandlersByClass2) {
                        if (handler2 == handler) {
                            return t2;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        dump(appendable, str, getBeans(), TypeUtil.asList(getHandlers()));
    }
}
