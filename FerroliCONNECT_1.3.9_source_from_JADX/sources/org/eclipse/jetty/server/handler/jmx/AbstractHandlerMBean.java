package org.eclipse.jetty.server.handler.jmx;

import java.io.IOException;
import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class AbstractHandlerMBean extends ObjectMBean {
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractHandlerMBean.class);

    public AbstractHandlerMBean(Object obj) {
        super(obj);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0012, code lost:
        r0 = (org.eclipse.jetty.server.handler.AbstractHandler) r4._managed;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getObjectContextBasis() {
        /*
            r4 = this;
            java.lang.Object r0 = r4._managed
            if (r0 == 0) goto L_0x002d
            java.lang.Object r0 = r4._managed
            boolean r0 = r0 instanceof org.eclipse.jetty.server.handler.ContextHandler
            r1 = 0
            if (r0 == 0) goto L_0x000c
            return r1
        L_0x000c:
            java.lang.Object r0 = r4._managed
            boolean r0 = r0 instanceof org.eclipse.jetty.server.handler.AbstractHandler
            if (r0 == 0) goto L_0x002a
            java.lang.Object r0 = r4._managed
            org.eclipse.jetty.server.handler.AbstractHandler r0 = (org.eclipse.jetty.server.handler.AbstractHandler) r0
            org.eclipse.jetty.server.Server r2 = r0.getServer()
            if (r2 == 0) goto L_0x002a
            java.lang.Class<org.eclipse.jetty.server.handler.ContextHandler> r3 = org.eclipse.jetty.server.handler.ContextHandler.class
            org.eclipse.jetty.server.HandlerContainer r0 = org.eclipse.jetty.server.handler.AbstractHandlerContainer.findContainerOf(r2, r3, r0)
            org.eclipse.jetty.server.handler.ContextHandler r0 = (org.eclipse.jetty.server.handler.ContextHandler) r0
            if (r0 == 0) goto L_0x002a
            java.lang.String r1 = r4.getContextName(r0)
        L_0x002a:
            if (r1 == 0) goto L_0x002d
            return r1
        L_0x002d:
            java.lang.String r0 = org.eclipse.jetty.server.handler.jmx.AbstractHandlerMBean.super.getObjectContextBasis()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.jmx.AbstractHandlerMBean.getObjectContextBasis():java.lang.String");
    }

    public String getObjectNameBasis() {
        if (this._managed != null) {
            String str = null;
            if (this._managed instanceof ContextHandler) {
                str = getContextName((ContextHandler) this._managed);
            }
            if (str != null) {
                return str;
            }
        }
        return AbstractHandlerMBean.super.getObjectNameBasis();
    }

    /* access modifiers changed from: protected */
    public String getContextName(ContextHandler contextHandler) {
        String str;
        if (contextHandler.getContextPath() == null || contextHandler.getContextPath().length() <= 0) {
            str = null;
        } else {
            int lastIndexOf = contextHandler.getContextPath().lastIndexOf(47);
            str = lastIndexOf < 0 ? contextHandler.getContextPath() : contextHandler.getContextPath().substring(lastIndexOf + 1);
            if (str == null || str.length() == 0) {
                str = "ROOT";
            }
        }
        if (str != null || contextHandler.getBaseResource() == null) {
            return str;
        }
        try {
            if (contextHandler.getBaseResource().getFile() != null) {
                return contextHandler.getBaseResource().getFile().getName();
            }
            return str;
        } catch (IOException e) {
            LOG.ignore(e);
            return contextHandler.getBaseResource().getName();
        }
    }
}
