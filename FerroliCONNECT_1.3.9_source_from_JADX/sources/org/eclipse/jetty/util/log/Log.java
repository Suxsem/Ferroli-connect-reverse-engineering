package org.eclipse.jetty.util.log;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Log {
    public static final String EXCEPTION = "EXCEPTION ";
    public static final String IGNORED = "IGNORED ";
    private static Logger LOG;
    public static boolean __ignored;
    private static boolean __initialized;
    public static String __logClass;
    private static final ConcurrentMap<String, Logger> __loggers = new ConcurrentHashMap();
    protected static Properties __props = new Properties();

    static {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            /* JADX WARNING: Removed duplicated region for block: B:20:0x0050  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object run() {
                /*
                    r7 = this;
                    java.lang.Class<org.eclipse.jetty.util.log.Log> r0 = org.eclipse.jetty.util.log.Log.class
                    java.lang.String r1 = "jetty-logging.properties"
                    r2 = 1
                    java.net.URL r0 = org.eclipse.jetty.util.Loader.getResource(r0, r1, r2)
                    r1 = 0
                    if (r0 == 0) goto L_0x0042
                    java.io.InputStream r2 = r0.openStream()     // Catch:{ IOException -> 0x0020, all -> 0x001d }
                    java.util.Properties r3 = org.eclipse.jetty.util.log.Log.__props     // Catch:{ IOException -> 0x001b }
                    r3.load(r2)     // Catch:{ IOException -> 0x001b }
                L_0x0015:
                    org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r2)
                    goto L_0x0042
                L_0x0019:
                    r0 = move-exception
                    goto L_0x003e
                L_0x001b:
                    r3 = move-exception
                    goto L_0x0022
                L_0x001d:
                    r0 = move-exception
                    r2 = r1
                    goto L_0x003e
                L_0x0020:
                    r3 = move-exception
                    r2 = r1
                L_0x0022:
                    java.io.PrintStream r4 = java.lang.System.err     // Catch:{ all -> 0x0019 }
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0019 }
                    r5.<init>()     // Catch:{ all -> 0x0019 }
                    java.lang.String r6 = "Unable to load "
                    r5.append(r6)     // Catch:{ all -> 0x0019 }
                    r5.append(r0)     // Catch:{ all -> 0x0019 }
                    java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x0019 }
                    r4.println(r0)     // Catch:{ all -> 0x0019 }
                    java.io.PrintStream r0 = java.lang.System.err     // Catch:{ all -> 0x0019 }
                    r3.printStackTrace(r0)     // Catch:{ all -> 0x0019 }
                    goto L_0x0015
                L_0x003e:
                    org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r2)
                    throw r0
                L_0x0042:
                    java.util.Properties r0 = java.lang.System.getProperties()
                    java.util.Enumeration r0 = r0.propertyNames()
                L_0x004a:
                    boolean r2 = r0.hasMoreElements()
                    if (r2 == 0) goto L_0x0062
                    java.lang.Object r2 = r0.nextElement()
                    java.lang.String r2 = (java.lang.String) r2
                    java.lang.String r3 = java.lang.System.getProperty(r2)
                    if (r3 == 0) goto L_0x004a
                    java.util.Properties r4 = org.eclipse.jetty.util.log.Log.__props
                    r4.setProperty(r2, r3)
                    goto L_0x004a
                L_0x0062:
                    java.util.Properties r0 = org.eclipse.jetty.util.log.Log.__props
                    java.lang.String r2 = "org.eclipse.jetty.util.log.class"
                    java.lang.String r3 = "org.eclipse.jetty.util.log.Slf4jLog"
                    java.lang.String r0 = r0.getProperty(r2, r3)
                    org.eclipse.jetty.util.log.Log.__logClass = r0
                    java.util.Properties r0 = org.eclipse.jetty.util.log.Log.__props
                    java.lang.String r2 = "org.eclipse.jetty.util.log.IGNORED"
                    java.lang.String r3 = "false"
                    java.lang.String r0 = r0.getProperty(r2, r3)
                    boolean r0 = java.lang.Boolean.parseBoolean(r0)
                    org.eclipse.jetty.util.log.Log.__ignored = r0
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.log.Log.C24541.run():java.lang.Object");
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0015, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r0 = org.eclipse.jetty.util.Loader.loadClass(org.eclipse.jetty.util.log.Log.class, __logClass);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0023, code lost:
        if (LOG == null) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002f, code lost:
        if (LOG.getClass().equals(r0) != false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0031, code lost:
        LOG = (org.eclipse.jetty.util.log.Logger) r0.newInstance();
        LOG.debug("Logging to {} via {}", LOG, r0.getName());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004f, code lost:
        initStandardLogging(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean initialized() {
        /*
            org.eclipse.jetty.util.log.Logger r0 = LOG
            r1 = 1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            java.lang.Class<org.eclipse.jetty.util.log.Log> r0 = org.eclipse.jetty.util.log.Log.class
            monitor-enter(r0)
            boolean r2 = __initialized     // Catch:{ all -> 0x0059 }
            r3 = 0
            if (r2 == 0) goto L_0x0016
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ all -> 0x0059 }
            if (r2 == 0) goto L_0x0013
            goto L_0x0014
        L_0x0013:
            r1 = 0
        L_0x0014:
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            return r1
        L_0x0016:
            __initialized = r1     // Catch:{ all -> 0x0059 }
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            java.lang.Class<org.eclipse.jetty.util.log.Log> r0 = org.eclipse.jetty.util.log.Log.class
            java.lang.String r2 = __logClass     // Catch:{ Throwable -> 0x004e }
            java.lang.Class r0 = org.eclipse.jetty.util.Loader.loadClass(r0, r2)     // Catch:{ Throwable -> 0x004e }
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ Throwable -> 0x004e }
            if (r2 == 0) goto L_0x0031
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ Throwable -> 0x004e }
            java.lang.Class r2 = r2.getClass()     // Catch:{ Throwable -> 0x004e }
            boolean r2 = r2.equals(r0)     // Catch:{ Throwable -> 0x004e }
            if (r2 != 0) goto L_0x0052
        L_0x0031:
            java.lang.Object r2 = r0.newInstance()     // Catch:{ Throwable -> 0x004e }
            org.eclipse.jetty.util.log.Logger r2 = (org.eclipse.jetty.util.log.Logger) r2     // Catch:{ Throwable -> 0x004e }
            LOG = r2     // Catch:{ Throwable -> 0x004e }
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ Throwable -> 0x004e }
            java.lang.String r4 = "Logging to {} via {}"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x004e }
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ Throwable -> 0x004e }
            r5[r3] = r6     // Catch:{ Throwable -> 0x004e }
            java.lang.String r0 = r0.getName()     // Catch:{ Throwable -> 0x004e }
            r5[r1] = r0     // Catch:{ Throwable -> 0x004e }
            r2.debug((java.lang.String) r4, (java.lang.Object[]) r5)     // Catch:{ Throwable -> 0x004e }
            goto L_0x0052
        L_0x004e:
            r0 = move-exception
            initStandardLogging(r0)
        L_0x0052:
            org.eclipse.jetty.util.log.Logger r0 = LOG
            if (r0 == 0) goto L_0x0057
            goto L_0x0058
        L_0x0057:
            r1 = 0
        L_0x0058:
            return r1
        L_0x0059:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0059 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.log.Log.initialized():boolean");
    }

    private static void initStandardLogging(Throwable th) {
        if (th != null && __ignored) {
            th.printStackTrace();
        }
        if (LOG == null) {
            LOG = new StdErrLog();
            Logger logger = LOG;
            logger.debug("Logging to {} via {}", logger, StdErrLog.class.getName());
        }
    }

    public static void setLog(Logger logger) {
        LOG = logger;
    }

    @Deprecated
    public static Logger getLog() {
        initialized();
        return LOG;
    }

    public static Logger getRootLogger() {
        initialized();
        return LOG;
    }

    static boolean isIgnored() {
        return __ignored;
    }

    public static void setLogToParent(String str) {
        ClassLoader classLoader = Log.class.getClassLoader();
        if (classLoader == null || classLoader.getParent() == null) {
            setLog(getLogger(str));
            return;
        }
        try {
            setLog(new LoggerLog(classLoader.getParent().loadClass("org.eclipse.jetty.util.log.Log").getMethod("getLogger", new Class[]{String.class}).invoke((Object) null, new Object[]{str})));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static void debug(Throwable th) {
        if (isDebugEnabled()) {
            LOG.debug(EXCEPTION, th);
        }
    }

    @Deprecated
    public static void debug(String str) {
        if (initialized()) {
            LOG.debug(str, new Object[0]);
        }
    }

    @Deprecated
    public static void debug(String str, Object obj) {
        if (initialized()) {
            LOG.debug(str, obj);
        }
    }

    @Deprecated
    public static void debug(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.debug(str, obj, obj2);
        }
    }

    @Deprecated
    public static void ignore(Throwable th) {
        if (initialized()) {
            LOG.ignore(th);
        }
    }

    @Deprecated
    public static void info(String str) {
        if (initialized()) {
            LOG.info(str, new Object[0]);
        }
    }

    @Deprecated
    public static void info(String str, Object obj) {
        if (initialized()) {
            LOG.info(str, obj);
        }
    }

    @Deprecated
    public static void info(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.info(str, obj, obj2);
        }
    }

    @Deprecated
    public static boolean isDebugEnabled() {
        if (!initialized()) {
            return false;
        }
        return LOG.isDebugEnabled();
    }

    @Deprecated
    public static void warn(String str) {
        if (initialized()) {
            LOG.warn(str, new Object[0]);
        }
    }

    @Deprecated
    public static void warn(String str, Object obj) {
        if (initialized()) {
            LOG.warn(str, obj);
        }
    }

    @Deprecated
    public static void warn(String str, Object obj, Object obj2) {
        if (initialized()) {
            LOG.warn(str, obj, obj2);
        }
    }

    @Deprecated
    public static void warn(String str, Throwable th) {
        if (initialized()) {
            LOG.warn(str, th);
        }
    }

    @Deprecated
    public static void warn(Throwable th) {
        if (initialized()) {
            LOG.warn(EXCEPTION, th);
        }
    }

    public static Logger getLogger(Class<?> cls) {
        return getLogger(cls.getName());
    }

    public static Logger getLogger(String str) {
        if (!initialized()) {
            return null;
        }
        if (str == null) {
            return LOG;
        }
        Logger logger = (Logger) __loggers.get(str);
        return logger == null ? LOG.getLogger(str) : logger;
    }

    static ConcurrentMap<String, Logger> getMutableLoggers() {
        return __loggers;
    }

    public static Map<String, Logger> getLoggers() {
        return Collections.unmodifiableMap(__loggers);
    }
}
