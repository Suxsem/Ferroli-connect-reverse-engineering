package org.eclipse.jetty.util.resource;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JarResource extends URLResource {
    private static final Logger LOG = Log.getLogger((Class<?>) JarResource.class);
    protected JarURLConnection _jarConnection;

    public File getFile() throws IOException {
        return null;
    }

    JarResource(URL url) {
        super(url, (URLConnection) null);
    }

    JarResource(URL url, boolean z) {
        super(url, (URLConnection) null, z);
    }

    public synchronized void release() {
        this._jarConnection = null;
        super.release();
    }

    /* access modifiers changed from: protected */
    public synchronized boolean checkConnection() {
        super.checkConnection();
        try {
            if (this._jarConnection != this._connection) {
                newConnection();
            }
        } catch (IOException e) {
            LOG.ignore(e);
            this._jarConnection = null;
        }
        return this._jarConnection != null;
    }

    /* access modifiers changed from: protected */
    public void newConnection() throws IOException {
        this._jarConnection = (JarURLConnection) this._connection;
    }

    public boolean exists() {
        if (this._urlString.endsWith("!/")) {
            return checkConnection();
        }
        return super.exists();
    }

    public InputStream getInputStream() throws IOException {
        checkConnection();
        if (!this._urlString.endsWith("!/")) {
            return new FilterInputStream(super.getInputStream()) {
                public void close() throws IOException {
                    this.in = C2439IO.getClosedStream();
                }
            };
        }
        return new URL(this._urlString.substring(4, this._urlString.length() - 2)).openStream();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00da, code lost:
        if (r8.equals("") == false) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e3, code lost:
        if (r8.startsWith(r0) == false) goto L_0x00e5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void copyTo(java.io.File r15) throws java.io.IOException {
        /*
            r14 = this;
            boolean r0 = r14.exists()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            org.eclipse.jetty.util.log.Logger r0 = LOG
            boolean r0 = r0.isDebugEnabled()
            r1 = 0
            if (r0 == 0) goto L_0x0030
            org.eclipse.jetty.util.log.Logger r0 = LOG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Extract "
            r2.append(r3)
            r2.append(r14)
            java.lang.String r3 = " to "
            r2.append(r3)
            r2.append(r15)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r1]
            r0.debug((java.lang.String) r2, (java.lang.Object[]) r3)
        L_0x0030:
            java.net.URL r0 = r14.getURL()
            java.lang.String r0 = r0.toExternalForm()
            java.lang.String r0 = r0.trim()
            java.lang.String r2 = "!/"
            int r2 = r0.indexOf(r2)
            if (r2 < 0) goto L_0x0046
            r3 = 4
            goto L_0x0047
        L_0x0046:
            r3 = 0
        L_0x0047:
            if (r2 < 0) goto L_0x01bc
            java.net.URL r4 = new java.net.URL
            java.lang.String r3 = r0.substring(r3, r2)
            r4.<init>(r3)
            int r2 = r2 + 2
            int r3 = r0.length()
            r5 = 0
            if (r2 >= r3) goto L_0x0060
            java.lang.String r0 = r0.substring(r2)
            goto L_0x0061
        L_0x0060:
            r0 = r5
        L_0x0061:
            java.lang.String r2 = "/"
            r3 = 1
            if (r0 == 0) goto L_0x006e
            boolean r6 = r0.endsWith(r2)
            if (r6 == 0) goto L_0x006e
            r6 = 1
            goto L_0x006f
        L_0x006e:
            r6 = 0
        L_0x006f:
            org.eclipse.jetty.util.log.Logger r7 = LOG
            boolean r7 = r7.isDebugEnabled()
            if (r7 == 0) goto L_0x0097
            org.eclipse.jetty.util.log.Logger r7 = LOG
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Extracting entry = "
            r8.append(r9)
            r8.append(r0)
            java.lang.String r9 = " from jar "
            r8.append(r9)
            r8.append(r4)
            java.lang.String r8 = r8.toString()
            java.lang.Object[] r9 = new java.lang.Object[r1]
            r7.debug((java.lang.String) r8, (java.lang.Object[]) r9)
        L_0x0097:
            java.net.URLConnection r4 = r4.openConnection()
            java.io.InputStream r4 = r4.getInputStream()
            java.util.jar.JarInputStream r7 = new java.util.jar.JarInputStream
            r7.<init>(r4)
        L_0x00a4:
            java.util.jar.JarEntry r4 = r7.getNextJarEntry()
            if (r4 == 0) goto L_0x018a
            java.lang.String r8 = r4.getName()
            if (r0 == 0) goto L_0x00dd
            boolean r9 = r8.startsWith(r0)
            if (r9 == 0) goto L_0x00dd
            if (r6 != 0) goto L_0x00ca
            int r9 = r0.length()
            int r9 = r9 + r3
            int r10 = r8.length()
            if (r9 != r10) goto L_0x00ca
            boolean r9 = r8.endsWith(r2)
            if (r9 == 0) goto L_0x00ca
            r6 = 1
        L_0x00ca:
            if (r6 == 0) goto L_0x00e8
            int r9 = r0.length()
            java.lang.String r8 = r8.substring(r9)
            java.lang.String r9 = ""
            boolean r9 = r8.equals(r9)
            if (r9 != 0) goto L_0x00e5
            goto L_0x00e8
        L_0x00dd:
            if (r0 == 0) goto L_0x00e8
            boolean r9 = r8.startsWith(r0)
            if (r9 != 0) goto L_0x00e8
        L_0x00e5:
            r9 = r6
            r6 = 0
            goto L_0x00ea
        L_0x00e8:
            r9 = r6
            r6 = 1
        L_0x00ea:
            if (r6 != 0) goto L_0x010d
            org.eclipse.jetty.util.log.Logger r4 = LOG
            boolean r4 = r4.isDebugEnabled()
            if (r4 == 0) goto L_0x017f
            org.eclipse.jetty.util.log.Logger r4 = LOG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r10 = "Skipping entry: "
            r6.append(r10)
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            java.lang.Object[] r8 = new java.lang.Object[r1]
            r4.debug((java.lang.String) r6, (java.lang.Object[]) r8)
            goto L_0x017f
        L_0x010d:
            r6 = 92
            r10 = 47
            java.lang.String r6 = r8.replace(r6, r10)
            java.lang.String r6 = org.eclipse.jetty.util.URIUtil.canonicalPath(r6)
            if (r6 != 0) goto L_0x013c
            org.eclipse.jetty.util.log.Logger r4 = LOG
            boolean r4 = r4.isDebugEnabled()
            if (r4 == 0) goto L_0x017f
            org.eclipse.jetty.util.log.Logger r4 = LOG
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r10 = "Invalid entry: "
            r6.append(r10)
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            java.lang.Object[] r8 = new java.lang.Object[r1]
            r4.debug((java.lang.String) r6, (java.lang.Object[]) r8)
            goto L_0x017f
        L_0x013c:
            java.io.File r6 = new java.io.File
            r6.<init>(r15, r8)
            boolean r8 = r4.isDirectory()
            if (r8 == 0) goto L_0x0151
            boolean r4 = r6.exists()
            if (r4 != 0) goto L_0x017f
            r6.mkdirs()
            goto L_0x017f
        L_0x0151:
            java.io.File r8 = new java.io.File
            java.lang.String r10 = r6.getParent()
            r8.<init>(r10)
            boolean r10 = r8.exists()
            if (r10 != 0) goto L_0x0163
            r8.mkdirs()
        L_0x0163:
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x0185 }
            r8.<init>(r6)     // Catch:{ all -> 0x0185 }
            org.eclipse.jetty.util.C2439IO.copy((java.io.InputStream) r7, (java.io.OutputStream) r8)     // Catch:{ all -> 0x0182 }
            org.eclipse.jetty.util.C2439IO.close((java.io.OutputStream) r8)
            long r10 = r4.getTime()
            r12 = 0
            int r8 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r8 < 0) goto L_0x017f
            long r10 = r4.getTime()
            r6.setLastModified(r10)
        L_0x017f:
            r6 = r9
            goto L_0x00a4
        L_0x0182:
            r15 = move-exception
            r5 = r8
            goto L_0x0186
        L_0x0185:
            r15 = move-exception
        L_0x0186:
            org.eclipse.jetty.util.C2439IO.close((java.io.OutputStream) r5)
            throw r15
        L_0x018a:
            if (r0 == 0) goto L_0x0196
            if (r0 == 0) goto L_0x01b8
            java.lang.String r1 = "META-INF/MANIFEST.MF"
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x01b8
        L_0x0196:
            java.util.jar.Manifest r0 = r7.getManifest()
            if (r0 == 0) goto L_0x01b8
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "META-INF"
            r1.<init>(r15, r2)
            r1.mkdir()
            java.io.File r15 = new java.io.File
            java.lang.String r2 = "MANIFEST.MF"
            r15.<init>(r1, r2)
            java.io.FileOutputStream r1 = new java.io.FileOutputStream
            r1.<init>(r15)
            r0.write(r1)
            r1.close()
        L_0x01b8:
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r7)
            return
        L_0x01bc:
            java.io.IOException r15 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Not a valid jar url: "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r15.<init>(r0)
            goto L_0x01d4
        L_0x01d3:
            throw r15
        L_0x01d4:
            goto L_0x01d3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.resource.JarResource.copyTo(java.io.File):void");
    }

    public static Resource newJarResource(Resource resource) throws IOException {
        if (resource instanceof JarResource) {
            return resource;
        }
        return Resource.newResource("jar:" + resource + "!/");
    }
}
