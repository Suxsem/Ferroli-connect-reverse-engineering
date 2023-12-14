package org.eclipse.jetty.continuation;

import java.lang.reflect.Constructor;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;

public class ContinuationSupport {
    static final boolean __jetty6;
    static final Constructor<? extends Continuation> __newJetty6Continuation;
    static final Constructor<? extends Continuation> __newServlet3Continuation;
    static final boolean __servlet3;
    static final Class<?> __waitingContinuation;

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.Class<?>, java.lang.reflect.Constructor<? extends org.eclipse.jetty.continuation.Continuation>] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.Class<?>] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 0
            r1 = 1
            r2 = 0
            java.lang.Class<javax.servlet.ServletRequest> r3 = javax.servlet.ServletRequest.class
            java.lang.String r4 = "startAsync"
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            java.lang.reflect.Method r3 = r3.getMethod(r4, r5)     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            if (r3 == 0) goto L_0x0011
            r3 = 1
            goto L_0x0012
        L_0x0011:
            r3 = 0
        L_0x0012:
            if (r3 == 0) goto L_0x0033
            java.lang.Class<org.eclipse.jetty.continuation.ContinuationSupport> r3 = org.eclipse.jetty.continuation.ContinuationSupport.class
            java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            java.lang.String r4 = "org.eclipse.jetty.continuation.Servlet3Continuation"
            java.lang.Class r3 = r3.loadClass(r4)     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            java.lang.Class<org.eclipse.jetty.continuation.Continuation> r4 = org.eclipse.jetty.continuation.Continuation.class
            java.lang.Class r3 = r3.asSubclass(r4)     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            java.lang.Class<javax.servlet.ServletRequest> r5 = javax.servlet.ServletRequest.class
            r4[r2] = r5     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            java.lang.reflect.Constructor r3 = r3.getConstructor(r4)     // Catch:{ Exception -> 0x0040, all -> 0x003a }
            r4 = r3
            r3 = 1
            goto L_0x0035
        L_0x0033:
            r4 = r0
            r3 = 0
        L_0x0035:
            __servlet3 = r3
            __newServlet3Continuation = r4
            goto L_0x0044
        L_0x003a:
            r1 = move-exception
            __servlet3 = r2
            __newServlet3Continuation = r0
            throw r1
        L_0x0040:
            __servlet3 = r2
            __newServlet3Continuation = r0
        L_0x0044:
            java.lang.Class<org.eclipse.jetty.continuation.ContinuationSupport> r3 = org.eclipse.jetty.continuation.ContinuationSupport.class
            java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            java.lang.String r4 = "org.mortbay.util.ajax.Continuation"
            java.lang.Class r3 = r3.loadClass(r4)     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            if (r3 == 0) goto L_0x0054
            r4 = 1
            goto L_0x0055
        L_0x0054:
            r4 = 0
        L_0x0055:
            if (r4 == 0) goto L_0x0077
            java.lang.Class<org.eclipse.jetty.continuation.ContinuationSupport> r4 = org.eclipse.jetty.continuation.ContinuationSupport.class
            java.lang.ClassLoader r4 = r4.getClassLoader()     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            java.lang.String r5 = "org.eclipse.jetty.continuation.Jetty6Continuation"
            java.lang.Class r4 = r4.loadClass(r5)     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            java.lang.Class<org.eclipse.jetty.continuation.Continuation> r5 = org.eclipse.jetty.continuation.Continuation.class
            java.lang.Class r4 = r4.asSubclass(r5)     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            r5 = 2
            java.lang.Class[] r5 = new java.lang.Class[r5]     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            java.lang.Class<javax.servlet.ServletRequest> r6 = javax.servlet.ServletRequest.class
            r5[r2] = r6     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            r5[r1] = r3     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            java.lang.reflect.Constructor r2 = r4.getConstructor(r5)     // Catch:{ Exception -> 0x0084, all -> 0x007e }
            goto L_0x0079
        L_0x0077:
            r2 = r0
            r1 = 0
        L_0x0079:
            __jetty6 = r1
            __newJetty6Continuation = r2
            goto L_0x0088
        L_0x007e:
            r1 = move-exception
            __jetty6 = r2
            __newJetty6Continuation = r0
            throw r1
        L_0x0084:
            __jetty6 = r2
            __newJetty6Continuation = r0
        L_0x0088:
            java.lang.Class<org.eclipse.jetty.continuation.ContinuationSupport> r1 = org.eclipse.jetty.continuation.ContinuationSupport.class
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            java.lang.String r2 = "org.mortbay.util.ajax.WaitingContinuation"
            java.lang.Class r0 = r1.loadClass(r2)     // Catch:{ Exception -> 0x0099, all -> 0x0095 }
            goto L_0x0099
        L_0x0095:
            r1 = move-exception
            __waitingContinuation = r0
            throw r1
        L_0x0099:
            __waitingContinuation = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.continuation.ContinuationSupport.<clinit>():void");
    }

    public static Continuation getContinuation(ServletRequest servletRequest) {
        Continuation continuation;
        Continuation continuation2 = (Continuation) servletRequest.getAttribute(Continuation.ATTRIBUTE);
        if (continuation2 != null) {
            return continuation2;
        }
        while (servletRequest instanceof ServletRequestWrapper) {
            servletRequest = ((ServletRequestWrapper) servletRequest).getRequest();
        }
        if (__servlet3) {
            try {
                Continuation continuation3 = (Continuation) __newServlet3Continuation.newInstance(new Object[]{servletRequest});
                servletRequest.setAttribute(Continuation.ATTRIBUTE, continuation3);
                return continuation3;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (__jetty6) {
            Object attribute = servletRequest.getAttribute("org.mortbay.jetty.ajax.Continuation");
            if (attribute != null) {
                try {
                    if (__waitingContinuation != null) {
                        if (!__waitingContinuation.isInstance(attribute)) {
                            continuation = (Continuation) __newJetty6Continuation.newInstance(new Object[]{servletRequest, attribute});
                            servletRequest.setAttribute(Continuation.ATTRIBUTE, continuation);
                            return continuation;
                        }
                    }
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
            continuation = new FauxContinuation(servletRequest);
            servletRequest.setAttribute(Continuation.ATTRIBUTE, continuation);
            return continuation;
        } else {
            throw new IllegalStateException("!(Jetty || Servlet 3.0 || ContinuationFilter)");
        }
    }

    @Deprecated
    public static Continuation getContinuation(ServletRequest servletRequest, ServletResponse servletResponse) {
        return getContinuation(servletRequest);
    }
}
