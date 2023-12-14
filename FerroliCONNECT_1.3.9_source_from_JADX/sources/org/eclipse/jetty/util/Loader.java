package org.eclipse.jetty.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import org.eclipse.jetty.util.resource.Resource;

public class Loader {
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0037 A[ADDED_TO_REGION, EDGE_INSN: B:25:0x0037->B:17:0x0037 ?: BREAK  , SYNTHETIC] */
    public static java.net.URL getResource(java.lang.Class<?> r3, java.lang.String r4, boolean r5) {
        /*
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r0 = r0.getContextClassLoader()
            r1 = 0
            r2 = r0
            r0 = r1
        L_0x000b:
            if (r0 != 0) goto L_0x001e
            if (r2 == 0) goto L_0x001e
            java.net.URL r0 = r2.getResource(r4)
            if (r0 != 0) goto L_0x001c
            if (r5 == 0) goto L_0x001c
            java.lang.ClassLoader r2 = r2.getParent()
            goto L_0x000b
        L_0x001c:
            r2 = r1
            goto L_0x000b
        L_0x001e:
            if (r3 != 0) goto L_0x0022
        L_0x0020:
            r3 = r1
            goto L_0x0026
        L_0x0022:
            java.lang.ClassLoader r3 = r3.getClassLoader()
        L_0x0026:
            if (r0 != 0) goto L_0x0037
            if (r3 == 0) goto L_0x0037
            java.net.URL r0 = r3.getResource(r4)
            if (r0 != 0) goto L_0x0020
            if (r5 == 0) goto L_0x0020
            java.lang.ClassLoader r3 = r3.getParent()
            goto L_0x0026
        L_0x0037:
            if (r0 != 0) goto L_0x003d
            java.net.URL r0 = java.lang.ClassLoader.getSystemResource(r4)
        L_0x003d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.Loader.getResource(java.lang.Class, java.lang.String, boolean):java.net.URL");
    }

    public static Class loadClass(Class cls, String str) throws ClassNotFoundException {
        return loadClass(cls, str, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0044 A[SYNTHETIC, Splitter:B:26:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x004f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Class loadClass(java.lang.Class r5, java.lang.String r6, boolean r7) throws java.lang.ClassNotFoundException {
        /*
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r0 = r0.getContextClassLoader()
            r1 = 0
            r2 = r0
            r0 = r1
            r3 = r0
        L_0x000c:
            if (r0 != 0) goto L_0x0024
            if (r2 == 0) goto L_0x0024
            java.lang.Class r0 = r2.loadClass(r6)     // Catch:{ ClassNotFoundException -> 0x0015 }
            goto L_0x0019
        L_0x0015:
            r4 = move-exception
            if (r3 != 0) goto L_0x0019
            r3 = r4
        L_0x0019:
            if (r0 != 0) goto L_0x0022
            if (r7 == 0) goto L_0x0022
            java.lang.ClassLoader r2 = r2.getParent()
            goto L_0x000c
        L_0x0022:
            r2 = r1
            goto L_0x000c
        L_0x0024:
            if (r5 != 0) goto L_0x0028
        L_0x0026:
            r5 = r1
            goto L_0x002c
        L_0x0028:
            java.lang.ClassLoader r5 = r5.getClassLoader()
        L_0x002c:
            if (r0 != 0) goto L_0x0042
            if (r5 == 0) goto L_0x0042
            java.lang.Class r0 = r5.loadClass(r6)     // Catch:{ ClassNotFoundException -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r2 = move-exception
            if (r3 != 0) goto L_0x0039
            r3 = r2
        L_0x0039:
            if (r0 != 0) goto L_0x0026
            if (r7 == 0) goto L_0x0026
            java.lang.ClassLoader r5 = r5.getParent()
            goto L_0x002c
        L_0x0042:
            if (r0 != 0) goto L_0x004d
            java.lang.Class r0 = java.lang.Class.forName(r6)     // Catch:{ ClassNotFoundException -> 0x0049 }
            goto L_0x004d
        L_0x0049:
            r5 = move-exception
            if (r3 != 0) goto L_0x004d
            r3 = r5
        L_0x004d:
            if (r0 == 0) goto L_0x0050
            return r0
        L_0x0050:
            goto L_0x0052
        L_0x0051:
            throw r3
        L_0x0052:
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.Loader.loadClass(java.lang.Class, java.lang.String, boolean):java.lang.Class");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0044 A[SYNTHETIC, Splitter:B:26:0x0044] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x004f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ResourceBundle getResourceBundle(java.lang.Class<?> r5, java.lang.String r6, boolean r7, java.util.Locale r8) throws java.util.MissingResourceException {
        /*
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r0 = r0.getContextClassLoader()
            r1 = 0
            r2 = r0
            r0 = r1
            r3 = r0
        L_0x000c:
            if (r0 != 0) goto L_0x0024
            if (r2 == 0) goto L_0x0024
            java.util.ResourceBundle r0 = java.util.ResourceBundle.getBundle(r6, r8, r2)     // Catch:{ MissingResourceException -> 0x0015 }
            goto L_0x0019
        L_0x0015:
            r4 = move-exception
            if (r3 != 0) goto L_0x0019
            r3 = r4
        L_0x0019:
            if (r0 != 0) goto L_0x0022
            if (r7 == 0) goto L_0x0022
            java.lang.ClassLoader r2 = r2.getParent()
            goto L_0x000c
        L_0x0022:
            r2 = r1
            goto L_0x000c
        L_0x0024:
            if (r5 != 0) goto L_0x0028
        L_0x0026:
            r5 = r1
            goto L_0x002c
        L_0x0028:
            java.lang.ClassLoader r5 = r5.getClassLoader()
        L_0x002c:
            if (r0 != 0) goto L_0x0042
            if (r5 == 0) goto L_0x0042
            java.util.ResourceBundle r0 = java.util.ResourceBundle.getBundle(r6, r8, r5)     // Catch:{ MissingResourceException -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r2 = move-exception
            if (r3 != 0) goto L_0x0039
            r3 = r2
        L_0x0039:
            if (r0 != 0) goto L_0x0026
            if (r7 == 0) goto L_0x0026
            java.lang.ClassLoader r5 = r5.getParent()
            goto L_0x002c
        L_0x0042:
            if (r0 != 0) goto L_0x004d
            java.util.ResourceBundle r0 = java.util.ResourceBundle.getBundle(r6, r8)     // Catch:{ MissingResourceException -> 0x0049 }
            goto L_0x004d
        L_0x0049:
            r5 = move-exception
            if (r3 != 0) goto L_0x004d
            r3 = r5
        L_0x004d:
            if (r0 == 0) goto L_0x0050
            return r0
        L_0x0050:
            goto L_0x0052
        L_0x0051:
            throw r3
        L_0x0052:
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.Loader.getResourceBundle(java.lang.Class, java.lang.String, boolean, java.util.Locale):java.util.ResourceBundle");
    }

    public static String getClassPath(ClassLoader classLoader) throws Exception {
        StringBuilder sb = new StringBuilder();
        while (classLoader != null && (classLoader instanceof URLClassLoader)) {
            URL[] uRLs = ((URLClassLoader) classLoader).getURLs();
            if (uRLs != null) {
                for (URL newResource : uRLs) {
                    File file = Resource.newResource(newResource).getFile();
                    if (file != null && file.exists()) {
                        if (sb.length() > 0) {
                            sb.append(File.pathSeparatorChar);
                        }
                        sb.append(file.getAbsolutePath());
                    }
                }
            }
            classLoader = classLoader.getParent();
        }
        return sb.toString();
    }
}
