package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

public class HotSwapHandler extends AbstractHandlerContainer {
    private volatile Handler _handler;

    public Handler getHandler() {
        return this._handler;
    }

    public Handler[] getHandlers() {
        return new Handler[]{this._handler};
    }

    public void setHandler(Handler handler) {
        if (handler != null) {
            try {
                Handler handler2 = this._handler;
                this._handler = handler;
                Server server = getServer();
                handler.setServer(server);
                addBean(handler);
                if (server != null) {
                    server.getContainer().update((Object) this, (Object) handler2, (Object) handler, "handler");
                }
                if (handler2 != null) {
                    removeBean(handler2);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Parameter handler is null.");
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
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
            if (!isRunning()) {
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
            throw new IllegalStateException(AbstractLifeCycle.RUNNING);
        }
    }

    /* access modifiers changed from: protected */
    public Object expandChildren(Object obj, Class cls) {
        return expandHandler(this._handler, obj, cls);
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
