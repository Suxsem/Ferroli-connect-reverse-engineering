package org.eclipse.jetty.webapp;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;

public class WebAppClassLoader extends URLClassLoader {
    private static final Logger LOG = Log.getLogger((Class<?>) WebAppClassLoader.class);
    private final Context _context;
    private final Set<String> _extensions;
    private String _name;
    private final ClassLoader _parent;

    public interface Context {
        String getExtraClasspath();

        PermissionCollection getPermissions();

        boolean isParentLoaderPriority();

        boolean isServerClass(String str);

        boolean isSystemClass(String str);

        Resource newResource(String str) throws IOException;
    }

    public WebAppClassLoader(Context context) throws IOException {
        this((ClassLoader) null, context);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public WebAppClassLoader(ClassLoader classLoader, Context context) throws IOException {
        super(new URL[0], classLoader == null ? Thread.currentThread().getContextClassLoader() != null ? Thread.currentThread().getContextClassLoader() : WebAppClassLoader.class.getClassLoader() != null ? WebAppClassLoader.class.getClassLoader() : ClassLoader.getSystemClassLoader() : classLoader);
        this._extensions = new HashSet();
        this._name = String.valueOf(hashCode());
        this._parent = getParent();
        this._context = context;
        if (this._parent != null) {
            this._extensions.add(".jar");
            this._extensions.add(".zip");
            String property = System.getProperty(WebAppClassLoader.class.getName() + ".extensions");
            if (property != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(property, ",;");
                while (stringTokenizer.hasMoreTokens()) {
                    this._extensions.add(stringTokenizer.nextToken().trim());
                }
            }
            if (context.getExtraClasspath() != null) {
                addClassPath(context.getExtraClasspath());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("no parent classloader!");
    }

    public String getName() {
        return this._name;
    }

    public void setName(String str) {
        this._name = str;
    }

    public Context getContext() {
        return this._context;
    }

    public void addClassPath(Resource resource) throws IOException {
        if (resource instanceof ResourceCollection) {
            for (Resource addClassPath : ((ResourceCollection) resource).getResources()) {
                addClassPath(addClassPath);
            }
            return;
        }
        addClassPath(resource.toString());
    }

    public void addClassPath(String str) throws IOException {
        if (str != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",;");
            while (stringTokenizer.hasMoreTokens()) {
                Resource newResource = this._context.newResource(stringTokenizer.nextToken().trim());
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Path resource=" + newResource, new Object[0]);
                }
                if (newResource.isDirectory() && (newResource instanceof ResourceCollection)) {
                    addClassPath(newResource);
                } else if (newResource.getFile() != null) {
                    addURL(newResource.getURL());
                } else if (newResource.isDirectory()) {
                    addURL(newResource.getURL());
                } else {
                    throw new IllegalArgumentException("!file: " + newResource);
                }
            }
        }
    }

    private boolean isFileSupported(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf != -1 && this._extensions.contains(str.substring(lastIndexOf));
    }

    public void addJars(Resource resource) {
        if (resource.exists() && resource.isDirectory()) {
            String[] list = resource.list();
            int i = 0;
            while (list != null && i < list.length) {
                try {
                    Resource addPath = resource.addPath(list[i]);
                    if (isFileSupported(addPath.getName().toLowerCase(Locale.ENGLISH))) {
                        addClassPath(StringUtil.replace(StringUtil.replace(addPath.toString(), ",", "%2C"), ";", "%3B"));
                    }
                } catch (Exception e) {
                    LOG.warn(Log.EXCEPTION, (Throwable) e);
                }
                i++;
            }
        }
    }

    public PermissionCollection getPermissions(CodeSource codeSource) {
        PermissionCollection permissions = this._context.getPermissions();
        return permissions == null ? super.getPermissions(codeSource) : permissions;
    }

    public Enumeration<URL> getResources(String str) throws IOException {
        Enumeration<URL> enumeration;
        boolean isSystemClass = this._context.isSystemClass(str);
        Enumeration enumeration2 = null;
        if (this._context.isServerClass(str)) {
            enumeration = null;
        } else {
            enumeration = this._parent.getResources(str);
        }
        List<URL> list = toList(enumeration);
        if (!isSystemClass || list.isEmpty()) {
            enumeration2 = findResources(str);
        }
        List<URL> list2 = toList(enumeration2);
        if (this._context.isParentLoaderPriority()) {
            list.addAll(list2);
            return Collections.enumeration(list);
        }
        list2.addAll(list);
        return Collections.enumeration(list2);
    }

    private List<URL> toList(Enumeration<URL> enumeration) {
        if (enumeration == null) {
            return new ArrayList();
        }
        return Collections.list(enumeration);
    }

    public URL getResource(String str) {
        boolean z;
        ClassLoader classLoader;
        boolean isSystemClass = this._context.isSystemClass(str);
        boolean isServerClass = this._context.isServerClass(str);
        URL url = null;
        if (isSystemClass && isServerClass) {
            return null;
        }
        if (this._parent == null || ((!this._context.isParentLoaderPriority() && !isSystemClass) || isServerClass)) {
            z = false;
        } else {
            ClassLoader classLoader2 = this._parent;
            if (classLoader2 != null) {
                url = classLoader2.getResource(str);
            }
            z = true;
        }
        if (url == null && (url = findResource(str)) == null && str.startsWith("/")) {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("HACK leading / off " + str, new Object[0]);
            }
            url = findResource(str.substring(1));
        }
        if (url == null && !z && !isServerClass && (classLoader = this._parent) != null) {
            url = classLoader.getResource(str);
        }
        if (url != null && LOG.isDebugEnabled()) {
            Logger logger2 = LOG;
            logger2.debug("getResource(" + str + ")=" + url, new Object[0]);
        }
        return url;
    }

    public Class<?> loadClass(String str) throws ClassNotFoundException {
        return loadClass(str, false);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a2, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0058 A[SYNTHETIC, Splitter:B:25:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0060 A[SYNTHETIC, Splitter:B:29:0x0060] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a3 A[SYNTHETIC, Splitter:B:43:0x00a3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.Class<?> loadClass(java.lang.String r9, boolean r10) throws java.lang.ClassNotFoundException {
        /*
            r8 = this;
            monitor-enter(r8)
            java.lang.Class r0 = r8.findLoadedClass(r9)     // Catch:{ all -> 0x00a4 }
            org.eclipse.jetty.webapp.WebAppClassLoader$Context r1 = r8._context     // Catch:{ all -> 0x00a4 }
            boolean r1 = r1.isSystemClass(r9)     // Catch:{ all -> 0x00a4 }
            org.eclipse.jetty.webapp.WebAppClassLoader$Context r2 = r8._context     // Catch:{ all -> 0x00a4 }
            boolean r2 = r2.isServerClass(r9)     // Catch:{ all -> 0x00a4 }
            r3 = 0
            if (r1 == 0) goto L_0x0018
            if (r2 == 0) goto L_0x0018
            monitor-exit(r8)
            return r3
        L_0x0018:
            r4 = 0
            if (r0 != 0) goto L_0x0055
            java.lang.ClassLoader r5 = r8._parent     // Catch:{ all -> 0x00a4 }
            if (r5 == 0) goto L_0x0055
            org.eclipse.jetty.webapp.WebAppClassLoader$Context r5 = r8._context     // Catch:{ all -> 0x00a4 }
            boolean r5 = r5.isParentLoaderPriority()     // Catch:{ all -> 0x00a4 }
            if (r5 != 0) goto L_0x0029
            if (r1 == 0) goto L_0x0055
        L_0x0029:
            if (r2 != 0) goto L_0x0055
            r1 = 1
            java.lang.ClassLoader r5 = r8._parent     // Catch:{ ClassNotFoundException -> 0x0053 }
            java.lang.Class r0 = r5.loadClass(r9)     // Catch:{ ClassNotFoundException -> 0x0053 }
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ ClassNotFoundException -> 0x0053 }
            boolean r5 = r5.isDebugEnabled()     // Catch:{ ClassNotFoundException -> 0x0053 }
            if (r5 == 0) goto L_0x0056
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ ClassNotFoundException -> 0x0053 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0053 }
            r6.<init>()     // Catch:{ ClassNotFoundException -> 0x0053 }
            java.lang.String r7 = "loaded "
            r6.append(r7)     // Catch:{ ClassNotFoundException -> 0x0053 }
            r6.append(r0)     // Catch:{ ClassNotFoundException -> 0x0053 }
            java.lang.String r6 = r6.toString()     // Catch:{ ClassNotFoundException -> 0x0053 }
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ ClassNotFoundException -> 0x0053 }
            r5.debug((java.lang.String) r6, (java.lang.Object[]) r7)     // Catch:{ ClassNotFoundException -> 0x0053 }
            goto L_0x0056
        L_0x0053:
            r3 = move-exception
            goto L_0x0056
        L_0x0055:
            r1 = 0
        L_0x0056:
            if (r0 != 0) goto L_0x005e
            java.lang.Class r0 = r8.findClass(r9)     // Catch:{ ClassNotFoundException -> 0x005d }
            goto L_0x005e
        L_0x005d:
            r3 = move-exception
        L_0x005e:
            if (r0 != 0) goto L_0x006e
            java.lang.ClassLoader r5 = r8._parent     // Catch:{ all -> 0x00a4 }
            if (r5 == 0) goto L_0x006e
            if (r1 != 0) goto L_0x006e
            if (r2 != 0) goto L_0x006e
            java.lang.ClassLoader r0 = r8._parent     // Catch:{ all -> 0x00a4 }
            java.lang.Class r0 = r0.loadClass(r9)     // Catch:{ all -> 0x00a4 }
        L_0x006e:
            if (r0 == 0) goto L_0x00a3
            if (r10 == 0) goto L_0x0075
            r8.resolveClass(r0)     // Catch:{ all -> 0x00a4 }
        L_0x0075:
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x00a4 }
            boolean r9 = r9.isDebugEnabled()     // Catch:{ all -> 0x00a4 }
            if (r9 == 0) goto L_0x00a1
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x00a4 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a4 }
            r10.<init>()     // Catch:{ all -> 0x00a4 }
            java.lang.String r1 = "loaded "
            r10.append(r1)     // Catch:{ all -> 0x00a4 }
            r10.append(r0)     // Catch:{ all -> 0x00a4 }
            java.lang.String r1 = " from "
            r10.append(r1)     // Catch:{ all -> 0x00a4 }
            java.lang.ClassLoader r1 = r0.getClassLoader()     // Catch:{ all -> 0x00a4 }
            r10.append(r1)     // Catch:{ all -> 0x00a4 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00a4 }
            java.lang.Object[] r1 = new java.lang.Object[r4]     // Catch:{ all -> 0x00a4 }
            r9.debug((java.lang.String) r10, (java.lang.Object[]) r1)     // Catch:{ all -> 0x00a4 }
        L_0x00a1:
            monitor-exit(r8)
            return r0
        L_0x00a3:
            throw r3     // Catch:{ all -> 0x00a4 }
        L_0x00a4:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.WebAppClassLoader.loadClass(java.lang.String, boolean):java.lang.Class");
    }

    public String toString() {
        return "WebAppClassLoader=" + this._name + "@" + Long.toHexString((long) hashCode());
    }
}
