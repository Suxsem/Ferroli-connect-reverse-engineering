package org.eclipse.jetty.continuation;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;

public class ContinuationFilter implements Filter {
    static boolean __debug;
    static boolean _initialized;
    ServletContext _context;
    private boolean _debug;
    private boolean _faux;
    private boolean _filtered;
    private boolean _jetty6;

    public interface FilteredContinuation extends Continuation {
        boolean enter(ServletResponse servletResponse);

        boolean exit();
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        boolean equals = "org.eclipse.jetty.servlet".equals(filterConfig.getClass().getPackage().getName());
        this._context = filterConfig.getServletContext();
        String initParameter = filterConfig.getInitParameter(MqttServiceConstants.TRACE_DEBUG);
        boolean z = false;
        this._debug = initParameter != null && Boolean.parseBoolean(initParameter);
        if (this._debug) {
            __debug = true;
        }
        String initParameter2 = filterConfig.getInitParameter("jetty6");
        if (initParameter2 == null) {
            initParameter2 = filterConfig.getInitParameter("partial");
        }
        if (initParameter2 != null) {
            this._jetty6 = Boolean.parseBoolean(initParameter2);
        } else {
            this._jetty6 = ContinuationSupport.__jetty6 && !equals;
        }
        String initParameter3 = filterConfig.getInitParameter("faux");
        if (initParameter3 != null) {
            this._faux = Boolean.parseBoolean(initParameter3);
        } else {
            this._faux = !equals && !this._jetty6 && this._context.getMajorVersion() < 3;
        }
        if (this._faux || this._jetty6) {
            z = true;
        }
        this._filtered = z;
        if (this._debug) {
            ServletContext servletContext = this._context;
            servletContext.log("ContinuationFilter  jetty=" + equals + " jetty6=" + this._jetty6 + " faux=" + this._faux + " filtered=" + this._filtered + " servlet3=" + ContinuationSupport.__servlet3);
        }
        _initialized = true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation} */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002d, code lost:
        if (r3.enter(r8) != false) goto L_0x002f;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doFilter(javax.servlet.ServletRequest r7, javax.servlet.ServletResponse r8, javax.servlet.FilterChain r9) throws java.io.IOException, javax.servlet.ServletException {
        /*
            r6 = this;
            boolean r0 = r6._filtered
            if (r0 == 0) goto L_0x006c
            java.lang.String r0 = "org.eclipse.jetty.continuation"
            java.lang.Object r1 = r7.getAttribute(r0)
            org.eclipse.jetty.continuation.Continuation r1 = (org.eclipse.jetty.continuation.Continuation) r1
            boolean r2 = r6._faux
            if (r2 == 0) goto L_0x001f
            if (r1 == 0) goto L_0x0016
            boolean r2 = r1 instanceof org.eclipse.jetty.continuation.FauxContinuation
            if (r2 != 0) goto L_0x001f
        L_0x0016:
            org.eclipse.jetty.continuation.FauxContinuation r1 = new org.eclipse.jetty.continuation.FauxContinuation
            r1.<init>(r7)
            r7.setAttribute(r0, r1)
            goto L_0x0021
        L_0x001f:
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r1 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r1
        L_0x0021:
            r2 = 0
            r3 = r1
        L_0x0023:
            r1 = 0
        L_0x0024:
            if (r1 != 0) goto L_0x0076
            r1 = 1
            if (r3 == 0) goto L_0x002f
            boolean r4 = r3.enter(r8)     // Catch:{ ContinuationThrowable -> 0x0045 }
            if (r4 == 0) goto L_0x0032
        L_0x002f:
            r9.doFilter(r7, r8)     // Catch:{ ContinuationThrowable -> 0x0045 }
        L_0x0032:
            if (r3 != 0) goto L_0x003a
            java.lang.Object r3 = r7.getAttribute(r0)
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r3 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r3
        L_0x003a:
            if (r3 == 0) goto L_0x0024
            boolean r4 = r3.exit()
            if (r4 == 0) goto L_0x0023
            goto L_0x0024
        L_0x0043:
            r8 = move-exception
            goto L_0x005c
        L_0x0045:
            r4 = move-exception
            java.lang.String r5 = "faux"
            r6.debug(r5, r4)     // Catch:{ all -> 0x0043 }
            if (r3 != 0) goto L_0x0053
            java.lang.Object r3 = r7.getAttribute(r0)
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r3 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r3
        L_0x0053:
            if (r3 == 0) goto L_0x0024
            boolean r4 = r3.exit()
            if (r4 == 0) goto L_0x0023
            goto L_0x0024
        L_0x005c:
            if (r3 != 0) goto L_0x0065
            java.lang.Object r7 = r7.getAttribute(r0)
            r3 = r7
            org.eclipse.jetty.continuation.ContinuationFilter$FilteredContinuation r3 = (org.eclipse.jetty.continuation.ContinuationFilter.FilteredContinuation) r3
        L_0x0065:
            if (r3 == 0) goto L_0x006b
            boolean r7 = r3.exit()
        L_0x006b:
            throw r8
        L_0x006c:
            r9.doFilter(r7, r8)     // Catch:{ ContinuationThrowable -> 0x0070 }
            goto L_0x0076
        L_0x0070:
            r7 = move-exception
            java.lang.String r8 = "caught"
            r6.debug(r8, r7)
        L_0x0076:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.continuation.ContinuationFilter.doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain):void");
    }

    private void debug(String str) {
        if (this._debug) {
            this._context.log(str);
        }
    }

    private void debug(String str, Throwable th) {
        if (!this._debug) {
            return;
        }
        if (th instanceof ContinuationThrowable) {
            ServletContext servletContext = this._context;
            servletContext.log(str + ":" + th);
            return;
        }
        this._context.log(str, th);
    }
}
