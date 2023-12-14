package org.eclipse.jetty.util.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import kotlin.text.Typography;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public abstract class Resource implements ResourceFactory {
    private static final Logger LOG = Log.getLogger((Class<?>) Resource.class);
    public static boolean __defaultUseCaches = true;
    volatile Object _associate;

    public abstract Resource addPath(String str) throws IOException, MalformedURLException;

    public abstract boolean delete() throws SecurityException;

    public abstract boolean exists();

    public URL getAlias() {
        return null;
    }

    public abstract File getFile() throws IOException;

    public abstract InputStream getInputStream() throws IOException;

    public abstract String getName();

    public abstract OutputStream getOutputStream() throws IOException, SecurityException;

    public abstract URL getURL();

    public abstract boolean isContainedIn(Resource resource) throws MalformedURLException;

    public abstract boolean isDirectory();

    public abstract long lastModified();

    public abstract long length();

    public abstract String[] list();

    public abstract void release();

    public abstract boolean renameTo(Resource resource) throws SecurityException;

    public static void setDefaultUseCaches(boolean z) {
        __defaultUseCaches = z;
    }

    public static boolean getDefaultUseCaches() {
        return __defaultUseCaches;
    }

    public static Resource newResource(URI uri) throws IOException {
        return newResource(uri.toURL());
    }

    public static Resource newResource(URL url) throws IOException {
        return newResource(url, __defaultUseCaches);
    }

    static Resource newResource(URL url, boolean z) {
        if (url == null) {
            return null;
        }
        String externalForm = url.toExternalForm();
        if (externalForm.startsWith("file:")) {
            try {
                return new FileResource(url);
            } catch (Exception e) {
                LOG.debug(Log.EXCEPTION, (Throwable) e);
                return new BadResource(url, e.toString());
            }
        } else if (externalForm.startsWith("jar:file:")) {
            return new JarFileResource(url, z);
        } else {
            if (externalForm.startsWith("jar:")) {
                return new JarResource(url, z);
            }
            return new URLResource(url, (URLConnection) null, z);
        }
    }

    public static Resource newResource(String str) throws MalformedURLException, IOException {
        return newResource(str, __defaultUseCaches);
    }

    public static Resource newResource(String str, boolean z) throws MalformedURLException, IOException {
        try {
            return newResource(new URL(str));
        } catch (MalformedURLException e) {
            if (str.startsWith("ftp:") || str.startsWith("file:") || str.startsWith("jar:")) {
                Logger logger = LOG;
                logger.warn("Bad Resource: " + str, new Object[0]);
                throw e;
            }
            try {
                if (str.startsWith("./")) {
                    str = str.substring(2);
                }
                File canonicalFile = new File(str).getCanonicalFile();
                URL url = toURL(canonicalFile);
                URLConnection openConnection = url.openConnection();
                openConnection.setUseCaches(z);
                return new FileResource(url, openConnection, canonicalFile);
            } catch (Exception e2) {
                LOG.debug(Log.EXCEPTION, (Throwable) e2);
                throw e;
            }
        }
    }

    public static Resource newResource(File file) throws MalformedURLException, IOException {
        File canonicalFile = file.getCanonicalFile();
        URL url = toURL(canonicalFile);
        return new FileResource(url, url.openConnection(), canonicalFile);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x005c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.eclipse.jetty.util.resource.Resource newSystemResource(java.lang.String r6) throws java.io.IOException {
        /*
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            java.lang.ClassLoader r0 = r0.getContextClassLoader()
            r1 = 1
            java.lang.String r2 = "/"
            r3 = 0
            if (r0 == 0) goto L_0x0025
            java.net.URL r4 = r0.getResource(r6)     // Catch:{ IllegalArgumentException -> 0x0025 }
            if (r4 != 0) goto L_0x0023
            boolean r5 = r6.startsWith(r2)     // Catch:{ IllegalArgumentException -> 0x0025 }
            if (r5 == 0) goto L_0x0023
            java.lang.String r4 = r6.substring(r1)     // Catch:{ IllegalArgumentException -> 0x0025 }
            java.net.URL r0 = r0.getResource(r4)     // Catch:{ IllegalArgumentException -> 0x0025 }
            goto L_0x0026
        L_0x0023:
            r0 = r4
            goto L_0x0026
        L_0x0025:
            r0 = r3
        L_0x0026:
            if (r0 != 0) goto L_0x0044
            java.lang.Class<org.eclipse.jetty.util.resource.Resource> r4 = org.eclipse.jetty.util.resource.Resource.class
            java.lang.ClassLoader r4 = r4.getClassLoader()
            if (r4 == 0) goto L_0x0044
            java.net.URL r0 = r4.getResource(r6)
            if (r0 != 0) goto L_0x0044
            boolean r5 = r6.startsWith(r2)
            if (r5 == 0) goto L_0x0044
            java.lang.String r0 = r6.substring(r1)
            java.net.URL r0 = r4.getResource(r0)
        L_0x0044:
            if (r0 != 0) goto L_0x005a
            java.net.URL r0 = java.lang.ClassLoader.getSystemResource(r6)
            if (r0 != 0) goto L_0x005a
            boolean r2 = r6.startsWith(r2)
            if (r2 == 0) goto L_0x005a
            java.lang.String r6 = r6.substring(r1)
            java.net.URL r0 = java.lang.ClassLoader.getSystemResource(r6)
        L_0x005a:
            if (r0 != 0) goto L_0x005d
            return r3
        L_0x005d:
            org.eclipse.jetty.util.resource.Resource r6 = newResource((java.net.URL) r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.resource.Resource.newSystemResource(java.lang.String):org.eclipse.jetty.util.resource.Resource");
    }

    public static Resource newClassPathResource(String str) {
        return newClassPathResource(str, true, false);
    }

    public static Resource newClassPathResource(String str, boolean z, boolean z2) {
        URL resource = Resource.class.getResource(str);
        if (resource == null) {
            resource = Loader.getResource(Resource.class, str, z2);
        }
        if (resource == null) {
            return null;
        }
        return newResource(resource, z);
    }

    public static boolean isContainedIn(Resource resource, Resource resource2) throws MalformedURLException {
        return resource.isContainedIn(resource2);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        release();
    }

    public URI getURI() {
        try {
            return getURL().toURI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Resource getResource(String str) {
        try {
            return addPath(str);
        } catch (Exception e) {
            LOG.debug(e);
            return null;
        }
    }

    public String encode(String str) {
        return URIUtil.encodePath(str);
    }

    public Object getAssociate() {
        return this._associate;
    }

    public void setAssociate(Object obj) {
        this._associate = obj;
    }

    public String getListHTML(String str, boolean z) throws IOException {
        String[] list;
        String canonicalPath = URIUtil.canonicalPath(str);
        if (canonicalPath == null || !isDirectory() || (list = list()) == null) {
            return null;
        }
        Arrays.sort(list);
        String decodePath = URIUtil.decodePath(canonicalPath);
        String str2 = "Directory: " + deTag(decodePath);
        StringBuilder sb = new StringBuilder(4096);
        sb.append("<HTML><HEAD>");
        sb.append("<LINK HREF=\"");
        sb.append("jetty-dir.css");
        sb.append("\" REL=\"stylesheet\" TYPE=\"text/css\"/><TITLE>");
        sb.append(str2);
        sb.append("</TITLE></HEAD><BODY>\n<H1>");
        sb.append(str2);
        sb.append("</H1>\n<TABLE BORDER=0>\n");
        if (z) {
            sb.append("<TR><TD><A HREF=\"");
            sb.append(URIUtil.addPaths(canonicalPath, "../"));
            sb.append("\">Parent Directory</A></TD><TD></TD><TD></TD></TR>\n");
        }
        String hrefEncodeURI = hrefEncodeURI(canonicalPath);
        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance(2, 2);
        for (int i = 0; i < list.length; i++) {
            Resource addPath = addPath(list[i]);
            sb.append("\n<TR><TD><A HREF=\"");
            String addPaths = URIUtil.addPaths(hrefEncodeURI, URIUtil.encodePath(list[i]));
            sb.append(addPaths);
            if (addPath.isDirectory() && !addPaths.endsWith("/")) {
                sb.append("/");
            }
            sb.append("\">");
            sb.append(deTag(list[i]));
            sb.append("&nbsp;");
            sb.append("</A></TD><TD ALIGN=right>");
            sb.append(addPath.length());
            sb.append(" bytes&nbsp;</TD><TD>");
            sb.append(dateTimeInstance.format(new Date(addPath.lastModified())));
            sb.append("</TD></TR>");
        }
        sb.append("</TABLE>\n");
        sb.append("</BODY></HTML>\n");
        return sb.toString();
    }

    private static String hrefEncodeURI(String str) {
        StringBuffer stringBuffer;
        int i = 0;
        while (true) {
            if (i >= str.length()) {
                stringBuffer = null;
                break;
            }
            char charAt = str.charAt(i);
            if (charAt == '\"' || charAt == '\'' || charAt == '<' || charAt == '>') {
                stringBuffer = new StringBuffer(str.length() << 1);
            } else {
                i++;
            }
        }
        if (stringBuffer == null) {
            return str;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt2 = str.charAt(i2);
            if (charAt2 == '\"') {
                stringBuffer.append("%22");
            } else if (charAt2 == '\'') {
                stringBuffer.append("%27");
            } else if (charAt2 == '<') {
                stringBuffer.append("%3C");
            } else if (charAt2 != '>') {
                stringBuffer.append(charAt2);
            } else {
                stringBuffer.append("%3E");
            }
        }
        return stringBuffer.toString();
    }

    private static String deTag(String str) {
        return StringUtil.replace(StringUtil.replace(str, "<", "&lt;"), ">", "&gt;");
    }

    public void writeTo(OutputStream outputStream, long j, long j2) throws IOException {
        InputStream inputStream = getInputStream();
        try {
            inputStream.skip(j);
            if (j2 < 0) {
                C2439IO.copy(inputStream, outputStream);
            } else {
                C2439IO.copy(inputStream, outputStream, j2);
            }
        } finally {
            inputStream.close();
        }
    }

    public void copyTo(File file) throws IOException {
        if (!file.exists()) {
            writeTo(new FileOutputStream(file), 0, -1);
            return;
        }
        throw new IllegalArgumentException(file + " exists");
    }

    public String getWeakETag() {
        try {
            StringBuilder sb = new StringBuilder(32);
            sb.append("W/\"");
            String name = getName();
            int length = name.length();
            long j = 0;
            for (int i = 0; i < length; i++) {
                j = (j * 31) + ((long) name.charAt(i));
            }
            B64Code.encode(lastModified() ^ j, (Appendable) sb);
            B64Code.encode(length() ^ j, (Appendable) sb);
            sb.append(Typography.quote);
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static URL toURL(File file) throws MalformedURLException {
        return file.toURI().toURL();
    }
}
