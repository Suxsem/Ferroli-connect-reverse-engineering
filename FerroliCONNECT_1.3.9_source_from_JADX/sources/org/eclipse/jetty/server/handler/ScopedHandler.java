package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;

public abstract class ScopedHandler extends HandlerWrapper {
    private static final ThreadLocal<ScopedHandler> __outerScope = new ThreadLocal<>();
    protected ScopedHandler _nextScope;
    protected ScopedHandler _outerScope;

    public abstract void doHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException;

    public abstract void doScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException;

    /* access modifiers changed from: protected */
    public boolean never() {
        return false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=org.eclipse.jetty.server.handler.ScopedHandler, code=java.lang.Object, for r3v0, types: [org.eclipse.jetty.server.handler.ScopedHandler, java.lang.Object, org.eclipse.jetty.server.handler.HandlerWrapper] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doStart() throws java.lang.Exception {
        /*
            r3 = this;
            r0 = 0
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ScopedHandler> r1 = __outerScope     // Catch:{ all -> 0x002b }
            java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x002b }
            org.eclipse.jetty.server.handler.ScopedHandler r1 = (org.eclipse.jetty.server.handler.ScopedHandler) r1     // Catch:{ all -> 0x002b }
            r3._outerScope = r1     // Catch:{ all -> 0x002b }
            org.eclipse.jetty.server.handler.ScopedHandler r1 = r3._outerScope     // Catch:{ all -> 0x002b }
            if (r1 != 0) goto L_0x0014
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ScopedHandler> r1 = __outerScope     // Catch:{ all -> 0x002b }
            r1.set(r3)     // Catch:{ all -> 0x002b }
        L_0x0014:
            super.doStart()     // Catch:{ all -> 0x002b }
            java.lang.Class<org.eclipse.jetty.server.handler.ScopedHandler> r1 = org.eclipse.jetty.server.handler.ScopedHandler.class
            org.eclipse.jetty.server.Handler r1 = r3.getChildHandlerByClass(r1)     // Catch:{ all -> 0x002b }
            org.eclipse.jetty.server.handler.ScopedHandler r1 = (org.eclipse.jetty.server.handler.ScopedHandler) r1     // Catch:{ all -> 0x002b }
            r3._nextScope = r1     // Catch:{ all -> 0x002b }
            org.eclipse.jetty.server.handler.ScopedHandler r1 = r3._outerScope
            if (r1 != 0) goto L_0x002a
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ScopedHandler> r1 = __outerScope
            r1.set(r0)
        L_0x002a:
            return
        L_0x002b:
            r1 = move-exception
            org.eclipse.jetty.server.handler.ScopedHandler r2 = r3._outerScope
            if (r2 != 0) goto L_0x0035
            java.lang.ThreadLocal<org.eclipse.jetty.server.handler.ScopedHandler> r2 = __outerScope
            r2.set(r0)
        L_0x0035:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.ScopedHandler.doStart():void");
    }

    public final void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._outerScope == null) {
            doScope(str, request, httpServletRequest, httpServletResponse);
        } else {
            doHandle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    public final void nextScope(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ScopedHandler scopedHandler = this._nextScope;
        if (scopedHandler != null) {
            scopedHandler.doScope(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        ScopedHandler scopedHandler2 = this._outerScope;
        if (scopedHandler2 != null) {
            scopedHandler2.doHandle(str, request, httpServletRequest, httpServletResponse);
        } else {
            doHandle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    public final void nextHandle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ScopedHandler scopedHandler = this._nextScope;
        if (scopedHandler != null && scopedHandler == this._handler) {
            this._nextScope.doHandle(str, request, httpServletRequest, httpServletResponse);
        } else if (this._handler != null) {
            this._handler.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }
}
