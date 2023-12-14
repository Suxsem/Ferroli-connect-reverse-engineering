package org.eclipse.jetty.server.handler;

import java.io.IOException;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public abstract class AbstractHandler extends AggregateLifeCycle implements Handler {
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractHandler.class);
    private Server _server;

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        LOG.debug("starting {}", this);
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        LOG.debug("stopping {}", this);
        super.doStop();
    }

    public void setServer(Server server) {
        Server server2 = this._server;
        if (!(server2 == null || server2 == server)) {
            server2.getContainer().removeBean(this);
        }
        this._server = server;
        Server server3 = this._server;
        if (server3 != null && server3 != server2) {
            server3.getContainer().addBean(this);
        }
    }

    public Server getServer() {
        return this._server;
    }

    public void destroy() {
        if (isStopped()) {
            super.destroy();
            Server server = this._server;
            if (server != null) {
                server.getContainer().removeBean(this);
                return;
            }
            return;
        }
        throw new IllegalStateException("!STOPPED");
    }

    public void dumpThis(Appendable appendable) throws IOException {
        appendable.append(toString()).append(" - ").append(getState()).append(10);
    }
}
