package org.eclipse.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.http.Generator;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.Parser;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class BlockingHttpConnection extends AbstractHttpConnection {
    private static final Logger LOG = Log.getLogger((Class<?>) BlockingHttpConnection.class);

    public BlockingHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(connector, endPoint, server);
    }

    public BlockingHttpConnection(Connector connector, EndPoint endPoint, Server server, Parser parser, Generator generator, Request request) {
        super(connector, endPoint, server, parser, generator, request);
    }

    /* access modifiers changed from: protected */
    public void handleRequest() throws IOException {
        super.handleRequest();
    }

    public Connection handle() throws IOException {
        EndPoint endPoint;
        Connection connection;
        Connection connection2;
        try {
            setCurrentConnection(this);
            Connection connection3 = this;
            while (this._endp.isOpen() && connection3 == this) {
                try {
                    if (!this._parser.isComplete() && !this._endp.isInputShutdown()) {
                        this._parser.parseAvailable();
                    }
                    if (this._generator.isCommitted() && !this._generator.isComplete() && !this._endp.isOutputShutdown()) {
                        this._generator.flushBuffer();
                    }
                    this._endp.flush();
                    if (this._parser.isComplete() && this._generator.isComplete()) {
                        reset();
                        if (this._response.getStatus() == 101 && (connection2 = (Connection) this._request.getAttribute("org.eclipse.jetty.io.Connection")) != null) {
                            connection3 = connection2;
                        }
                        if (!this._generator.isPersistent() && !this._endp.isOutputShutdown()) {
                            LOG.warn("Safety net oshut!!! Please open a bugzilla", new Object[0]);
                            this._endp.shutdownOutput();
                        }
                    }
                    if (this._endp.isInputShutdown() && this._generator.isIdle() && !this._request.getAsyncContinuation().isSuspended()) {
                        endPoint = this._endp;
                        endPoint.close();
                    }
                } catch (HttpException e) {
                    if (LOG.isDebugEnabled()) {
                        Logger logger = LOG;
                        logger.debug("uri=" + this._uri, new Object[0]);
                        Logger logger2 = LOG;
                        logger2.debug("fields=" + this._requestFields, new Object[0]);
                        LOG.debug(e);
                    }
                    this._generator.sendError(e.getStatus(), e.getReason(), (String) null, true);
                    this._parser.reset();
                    this._endp.shutdownOutput();
                    if (this._parser.isComplete() && this._generator.isComplete()) {
                        reset();
                        if (this._response.getStatus() == 101 && (connection = (Connection) this._request.getAttribute("org.eclipse.jetty.io.Connection")) != null) {
                            connection3 = connection;
                        }
                        if (!this._generator.isPersistent() && !this._endp.isOutputShutdown()) {
                            LOG.warn("Safety net oshut!!! Please open a bugzilla", new Object[0]);
                            this._endp.shutdownOutput();
                        }
                    }
                    if (this._endp.isInputShutdown() && this._generator.isIdle() && !this._request.getAsyncContinuation().isSuspended()) {
                        endPoint = this._endp;
                    }
                }
            }
            setCurrentConnection((AbstractHttpConnection) null);
            this._parser.returnBuffers();
            this._generator.returnBuffers();
            return connection3;
        } catch (Throwable th) {
            setCurrentConnection((AbstractHttpConnection) null);
            this._parser.returnBuffers();
            this._generator.returnBuffers();
            throw th;
        }
    }
}
