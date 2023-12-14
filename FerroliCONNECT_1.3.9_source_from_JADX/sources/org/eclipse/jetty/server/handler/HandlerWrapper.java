package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

public class HandlerWrapper extends AbstractHandlerContainer {
    protected Handler _handler;

    public Handler getHandler() {
        return this._handler;
    }

    public Handler[] getHandlers() {
        Handler handler = this._handler;
        if (handler == null) {
            return new Handler[0];
        }
        return new Handler[]{handler};
    }

    public void setHandler(Handler handler) {
        if (!isStarted()) {
            Handler handler2 = this._handler;
            this._handler = handler;
            if (handler != null) {
                handler.setServer(getServer());
            }
            if (getServer() != null) {
                getServer().getContainer().update((Object) this, (Object) handler2, (Object) handler, "handler");
                return;
            }
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        Handler handler = this._handler;
        if (handler != null) {
            handler.start();
        }
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        Handler handler = this._handler;
        if (handler != null) {
            handler.stop();
        }
        super.doStop();
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._handler != null && isStarted()) {
            this._handler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    public void setServer(Server server) {
        Server server2 = getServer();
        if (server != server2) {
            if (!isStarted()) {
                super.setServer(server);
                Handler handler = getHandler();
                if (handler != null) {
                    handler.setServer(server);
                }
                if (server != null && server != server2) {
                    server.getContainer().update((Object) this, (Object) null, (Object) this._handler, "handler");
                    return;
                }
                return;
            }
            throw new IllegalStateException(AbstractLifeCycle.STARTED);
        }
    }

    /* access modifiers changed from: protected */
    public Object expandChildren(Object obj, Class cls) {
        return expandHandler(this._handler, obj, cls);
    }

    public <H extends Handler> H getNestedHandlerByClass(Class<H> cls) {
        HandlerWrapper handlerWrapper = this;
        while (handlerWrapper != null) {
            if (cls.isInstance(handlerWrapper)) {
                return handlerWrapper;
            }
            Handler handler = handlerWrapper.getHandler();
            if (!(handler instanceof HandlerWrapper)) {
                return null;
            }
            handlerWrapper = (HandlerWrapper) handler;
        }
        return null;
    }

    public void destroy() {
        if (isStopped()) {
            Handler handler = getHandler();
            if (handler != null) {
                setHandler((Handler) null);
                handler.destroy();
            }
            super.destroy();
            return;
        }
        throw new IllegalStateException("!STOPPED");
    }
}
