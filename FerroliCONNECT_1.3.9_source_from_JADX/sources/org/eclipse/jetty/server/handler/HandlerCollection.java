package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

public class HandlerCollection extends AbstractHandlerContainer {
    /* access modifiers changed from: private */
    public volatile Handler[] _handlers;
    private final boolean _mutableWhenRunning;
    private boolean _parallelStart;

    public HandlerCollection() {
        this._parallelStart = false;
        this._mutableWhenRunning = false;
    }

    public HandlerCollection(boolean z) {
        this._parallelStart = false;
        this._mutableWhenRunning = z;
    }

    public Handler[] getHandlers() {
        return this._handlers;
    }

    public void setHandlers(Handler[] handlerArr) {
        if (this._mutableWhenRunning || !isStarted()) {
            Handler[] handlerArr2 = this._handlers == null ? null : (Handler[]) this._handlers.clone();
            this._handlers = handlerArr;
            Server server = getServer();
            MultiException multiException = new MultiException();
            int i = 0;
            int i2 = 0;
            while (handlerArr != null && i2 < handlerArr.length) {
                if (handlerArr[i2].getServer() != server) {
                    handlerArr[i2].setServer(server);
                }
                i2++;
            }
            if (getServer() != null) {
                getServer().getContainer().update((Object) this, (Object[]) handlerArr2, (Object[]) handlerArr, "handler");
            }
            while (handlerArr2 != null && i < handlerArr2.length) {
                if (handlerArr2[i] != null) {
                    try {
                        if (handlerArr2[i].isStarted()) {
                            handlerArr2[i].stop();
                        }
                    } catch (Throwable th) {
                        multiException.add(th);
                    }
                }
                i++;
            }
            multiException.ifExceptionThrowRuntime();
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    public boolean isParallelStart() {
        return this._parallelStart;
    }

    public void setParallelStart(boolean z) {
        this._parallelStart = z;
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._handlers != null && isStarted()) {
            MultiException multiException = null;
            for (int i = 0; i < this._handlers.length; i++) {
                try {
                    this._handlers[i].handle(str, request, httpServletRequest, httpServletResponse);
                } catch (IOException e) {
                    throw e;
                } catch (RuntimeException e2) {
                    throw e2;
                } catch (Exception e3) {
                    if (multiException == null) {
                        multiException = new MultiException();
                    }
                    multiException.add(e3);
                }
            }
            if (multiException == null) {
                return;
            }
            if (multiException.size() == 1) {
                throw new ServletException(multiException.getThrowable(0));
            }
            throw new ServletException((Throwable) multiException);
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        MultiException multiException = new MultiException();
        if (this._handlers != null) {
            if (this._parallelStart) {
                CountDownLatch countDownLatch = new CountDownLatch(this._handlers.length);
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                for (int i = 0; i < this._handlers.length; i++) {
                    final ClassLoader classLoader = contextClassLoader;
                    final int i2 = i;
                    final MultiException multiException2 = multiException;
                    final CountDownLatch countDownLatch2 = countDownLatch;
                    getServer().getThreadPool().dispatch(new Runnable() {
                        public void run() {
                            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                            try {
                                Thread.currentThread().setContextClassLoader(classLoader);
                                HandlerCollection.this._handlers[i2].start();
                            } catch (Throwable th) {
                                Thread.currentThread().setContextClassLoader(contextClassLoader);
                                countDownLatch2.countDown();
                                throw th;
                            }
                            Thread.currentThread().setContextClassLoader(contextClassLoader);
                            countDownLatch2.countDown();
                        }
                    });
                }
                countDownLatch.await();
            } else {
                for (int i3 = 0; i3 < this._handlers.length; i3++) {
                    try {
                        this._handlers[i3].start();
                    } catch (Throwable th) {
                        multiException.add(th);
                    }
                }
            }
        }
        super.doStart();
        multiException.ifExceptionThrow();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        MultiException multiException = new MultiException();
        try {
            super.doStop();
        } catch (Throwable th) {
            multiException.add(th);
        }
        if (this._handlers != null) {
            int length = this._handlers.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                try {
                    this._handlers[i].stop();
                } catch (Throwable th2) {
                    multiException.add(th2);
                }
                length = i;
            }
        }
        multiException.ifExceptionThrow();
    }

    public void setServer(Server server) {
        if (!isStarted()) {
            Server server2 = getServer();
            super.setServer(server);
            Handler[] handlers = getHandlers();
            int i = 0;
            while (handlers != null && i < handlers.length) {
                handlers[i].setServer(server);
                i++;
            }
            if (server != null && server != server2) {
                server.getContainer().update((Object) this, (Object[]) null, (Object[]) this._handlers, "handler");
                return;
            }
            return;
        }
        throw new IllegalStateException(AbstractLifeCycle.STARTED);
    }

    public void addHandler(Handler handler) {
        setHandlers((Handler[]) LazyList.addToArray(getHandlers(), handler, Handler.class));
    }

    public void removeHandler(Handler handler) {
        Handler[] handlers = getHandlers();
        if (handlers != null && handlers.length > 0) {
            setHandlers((Handler[]) LazyList.removeFromArray(handlers, handler));
        }
    }

    /* access modifiers changed from: protected */
    public Object expandChildren(Object obj, Class cls) {
        Handler[] handlers = getHandlers();
        int i = 0;
        while (handlers != null && i < handlers.length) {
            obj = expandHandler(handlers[i], obj, cls);
            i++;
        }
        return obj;
    }

    public void destroy() {
        if (isStopped()) {
            Handler[] childHandlers = getChildHandlers();
            setHandlers((Handler[]) null);
            for (Handler destroy : childHandlers) {
                destroy.destroy();
            }
            super.destroy();
            return;
        }
        throw new IllegalStateException("!STOPPED");
    }
}
