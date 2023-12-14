package org.eclipse.jetty.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class MultiPartInputStream {
    private static final Logger LOG = Log.getLogger((Class<?>) MultiPartInputStream.class);
    public static final MultipartConfigElement __DEFAULT_MULTIPART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));
    protected MultipartConfigElement _config;
    protected String _contentType;
    protected File _contextTmpDir;
    protected boolean _deleteOnExit;
    protected InputStream _in;
    protected MultiMap<String> _parts;
    protected File _tmpDir;

    public class MultiPart implements Part {
        protected ByteArrayOutputStream2 _bout;
        protected String _contentType;
        protected File _file;
        protected String _filename;
        protected MultiMap<String> _headers;
        protected String _name;
        protected OutputStream _out;
        protected long _size = 0;
        protected boolean _temporary = true;

        public MultiPart(String str, String str2) throws IOException {
            this._name = str;
            this._filename = str2;
        }

        /* access modifiers changed from: protected */
        public void setContentType(String str) {
            this._contentType = str;
        }

        /* access modifiers changed from: protected */
        public void open() throws IOException {
            String str = this._filename;
            if (str == null || str.trim().length() <= 0) {
                ByteArrayOutputStream2 byteArrayOutputStream2 = new ByteArrayOutputStream2();
                this._bout = byteArrayOutputStream2;
                this._out = byteArrayOutputStream2;
                return;
            }
            createFile();
        }

        /* access modifiers changed from: protected */
        public void close() throws IOException {
            this._out.close();
        }

        /* access modifiers changed from: protected */
        public void write(int i) throws IOException {
            if (MultiPartInputStream.this._config.getMaxFileSize() <= 0 || this._size + 1 <= MultiPartInputStream.this._config.getMaxFileSize()) {
                if (MultiPartInputStream.this._config.getFileSizeThreshold() > 0 && this._size + 1 > ((long) MultiPartInputStream.this._config.getFileSizeThreshold()) && this._file == null) {
                    createFile();
                }
                this._out.write(i);
                this._size++;
                return;
            }
            throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
        }

        /* access modifiers changed from: protected */
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (MultiPartInputStream.this._config.getMaxFileSize() <= 0 || this._size + ((long) i2) <= MultiPartInputStream.this._config.getMaxFileSize()) {
                if (MultiPartInputStream.this._config.getFileSizeThreshold() > 0 && this._size + ((long) i2) > ((long) MultiPartInputStream.this._config.getFileSizeThreshold()) && this._file == null) {
                    createFile();
                }
                this._out.write(bArr, i, i2);
                this._size += (long) i2;
                return;
            }
            throw new IllegalStateException("Multipart Mime part " + this._name + " exceeds max filesize");
        }

        /* access modifiers changed from: protected */
        public void createFile() throws IOException {
            OutputStream outputStream;
            this._file = File.createTempFile("MultiPart", "", MultiPartInputStream.this._tmpDir);
            if (MultiPartInputStream.this._deleteOnExit) {
                this._file.deleteOnExit();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this._file));
            if (this._size > 0 && (outputStream = this._out) != null) {
                outputStream.flush();
                this._bout.writeTo(bufferedOutputStream);
                this._out.close();
                this._bout = null;
            }
            this._out = bufferedOutputStream;
        }

        /* access modifiers changed from: protected */
        public void setHeaders(MultiMap<String> multiMap) {
            this._headers = multiMap;
        }

        public String getContentType() {
            return this._contentType;
        }

        public String getHeader(String str) {
            if (str == null) {
                return null;
            }
            return (String) this._headers.getValue(str.toLowerCase(Locale.ENGLISH), 0);
        }

        public Collection<String> getHeaderNames() {
            return this._headers.keySet();
        }

        public Collection<String> getHeaders(String str) {
            return this._headers.getValues(str);
        }

        public InputStream getInputStream() throws IOException {
            File file = this._file;
            if (file != null) {
                return new BufferedInputStream(new FileInputStream(file));
            }
            return new ByteArrayInputStream(this._bout.getBuf(), 0, this._bout.size());
        }

        public byte[] getBytes() {
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bout;
            if (byteArrayOutputStream2 != null) {
                return byteArrayOutputStream2.toByteArray();
            }
            return null;
        }

        public String getName() {
            return this._name;
        }

        public long getSize() {
            return this._size;
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x0033  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void write(java.lang.String r4) throws java.io.IOException {
            /*
                r3 = this;
                java.io.File r0 = r3._file
                r1 = 0
                if (r0 != 0) goto L_0x0039
                r3._temporary = r1
                java.io.File r0 = new java.io.File
                org.eclipse.jetty.util.MultiPartInputStream r1 = org.eclipse.jetty.util.MultiPartInputStream.this
                java.io.File r1 = r1._tmpDir
                r0.<init>(r1, r4)
                r3._file = r0
                r4 = 0
                java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x002f }
                java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x002f }
                java.io.File r2 = r3._file     // Catch:{ all -> 0x002f }
                r1.<init>(r2)     // Catch:{ all -> 0x002f }
                r0.<init>(r1)     // Catch:{ all -> 0x002f }
                org.eclipse.jetty.util.ByteArrayOutputStream2 r1 = r3._bout     // Catch:{ all -> 0x002d }
                r1.writeTo(r0)     // Catch:{ all -> 0x002d }
                r0.flush()     // Catch:{ all -> 0x002d }
                r0.close()
                r3._bout = r4
                goto L_0x004e
            L_0x002d:
                r1 = move-exception
                goto L_0x0031
            L_0x002f:
                r1 = move-exception
                r0 = r4
            L_0x0031:
                if (r0 == 0) goto L_0x0036
                r0.close()
            L_0x0036:
                r3._bout = r4
                throw r1
            L_0x0039:
                r3._temporary = r1
                java.io.File r0 = new java.io.File
                org.eclipse.jetty.util.MultiPartInputStream r1 = org.eclipse.jetty.util.MultiPartInputStream.this
                java.io.File r1 = r1._tmpDir
                r0.<init>(r1, r4)
                java.io.File r4 = r3._file
                boolean r4 = r4.renameTo(r0)
                if (r4 == 0) goto L_0x004e
                r3._file = r0
            L_0x004e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.MultiPartInputStream.MultiPart.write(java.lang.String):void");
        }

        public void delete() throws IOException {
            File file = this._file;
            if (file != null && file.exists()) {
                this._file.delete();
            }
        }

        public void cleanUp() throws IOException {
            File file;
            if (this._temporary && (file = this._file) != null && file.exists()) {
                this._file.delete();
            }
        }

        public File getFile() {
            return this._file;
        }

        public String getContentDispositionFilename() {
            return this._filename;
        }
    }

    public MultiPartInputStream(InputStream inputStream, String str, MultipartConfigElement multipartConfigElement, File file) {
        this._in = new ReadLineInputStream(inputStream);
        this._contentType = str;
        this._config = multipartConfigElement;
        this._contextTmpDir = file;
        if (this._contextTmpDir == null) {
            this._contextTmpDir = new File(System.getProperty("java.io.tmpdir"));
        }
        if (this._config == null) {
            this._config = new MultipartConfigElement(this._contextTmpDir.getAbsolutePath());
        }
    }

    public Collection<Part> getParsedParts() {
        MultiMap<String> multiMap = this._parts;
        if (multiMap == null) {
            return Collections.emptyList();
        }
        Collection<Object> values = multiMap.values();
        ArrayList arrayList = new ArrayList();
        for (Object list : values) {
            arrayList.addAll(LazyList.getList(list, false));
        }
        return arrayList;
    }

    public void deleteParts() throws MultiException {
        Collection<Part> parsedParts = getParsedParts();
        MultiException multiException = new MultiException();
        Iterator<Part> it = parsedParts.iterator();
        while (it.hasNext()) {
            try {
                ((MultiPart) it.next()).cleanUp();
            } catch (Exception e) {
                multiException.add(e);
            }
        }
        this._parts.clear();
        multiException.ifExceptionThrowMulti();
    }

    public Collection<Part> getParts() throws IOException, ServletException {
        parse();
        Collection<Object> values = this._parts.values();
        ArrayList arrayList = new ArrayList();
        for (Object list : values) {
            arrayList.addAll(LazyList.getList(list, false));
        }
        return arrayList;
    }

    public Part getPart(String str) throws IOException, ServletException {
        parse();
        return (Part) this._parts.getValue(str, 0);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x021e, code lost:
        if (r14 == false) goto L_0x0223;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0220, code lost:
        r3.write(10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0223, code lost:
        if (r5 <= 0) goto L_0x0229;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0225, code lost:
        r3.write(r6, 0, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0229, code lost:
        r3.write(r15);
        r4 = -2;
        r5 = -1;
        r7 = false;
        r12 = -2;
        r14 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0232, code lost:
        if (r15 != r12) goto L_0x0241;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0234, code lost:
        r2.mark(1);
        r12 = r2.read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x023c, code lost:
        if (r12 == 10) goto L_0x0242;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x023e, code lost:
        r2.reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0241, code lost:
        r12 = -2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0242, code lost:
        if (r5 <= 0) goto L_0x024d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0247, code lost:
        if (r5 < (r6.length - 2)) goto L_0x024a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0252, code lost:
        if (r5 != (r6.length - 1)) goto L_0x0267;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0254, code lost:
        if (r7 == false) goto L_0x025b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0256, code lost:
        r3.write(13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x025b, code lost:
        if (r14 == false) goto L_0x0260;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x025d, code lost:
        r3.write(10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0260, code lost:
        r3.write(r6, 0, r5);
        r5 = -1;
        r7 = false;
        r14 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0267, code lost:
        if (r5 > 0) goto L_0x028e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x026a, code lost:
        if (r15 != -1) goto L_0x026d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x026d, code lost:
        if (r7 == false) goto L_0x0274;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x026f, code lost:
        r3.write(13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0274, code lost:
        if (r14 == false) goto L_0x0279;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0276, code lost:
        r3.write(10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x027b, code lost:
        if (r15 != 13) goto L_0x027f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x027d, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x027f, code lost:
        r5 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0280, code lost:
        if (r15 == 10) goto L_0x0287;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0282, code lost:
        if (r12 != 10) goto L_0x0285;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0285, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0287, code lost:
        r7 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0288, code lost:
        if (r12 != 10) goto L_0x028b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x028a, code lost:
        r12 = -2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x028b, code lost:
        r4 = -2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x028f, code lost:
        if (r5 != r6.length) goto L_0x0292;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0291, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0292, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0297, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0298, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x029b, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x02a3, code lost:
        throw new java.io.IOException("Missing content-disposition");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x0173, code lost:
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x011a, code lost:
        if (r13 == null) goto L_0x029c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x011c, code lost:
        r12 = new org.eclipse.jetty.util.QuotedStringTokenizer(r13, r4, r7, r5);
        r7 = null;
        r13 = false;
        r20 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0129, code lost:
        if (r12.hasMoreTokens() == false) goto L_0x016a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x012b, code lost:
        r5 = r12.nextToken().trim();
        r22 = r3;
        r3 = r5.toLowerCase(java.util.Locale.ENGLISH);
        r23 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0143, code lost:
        if (r5.startsWith("form-data") == false) goto L_0x0147;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0145, code lost:
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x014d, code lost:
        if (r3.startsWith("name=") == false) goto L_0x0156;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x014f, code lost:
        r7 = value(r5, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x015c, code lost:
        if (r3.startsWith("filename=") == false) goto L_0x0164;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x015e, code lost:
        r20 = filenameValue(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0164, code lost:
        r3 = r22;
        r4 = r23;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x016a, code lost:
        r22 = r3;
        r23 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x016e, code lost:
        if (r13 != false) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0171, code lost:
        if (r7 != null) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0173, code lost:
        r3 = r22;
        r4 = r23;
        r5 = true;
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x017a, code lost:
        r3 = new org.eclipse.jetty.util.MultiPartInputStream.MultiPart(r1, r7, r20);
        r3.setHeaders(r2);
        r3.setContentType(r14);
        r1._parts.add(r7, r3);
        r3.open();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0195, code lost:
        if ("base64".equalsIgnoreCase(r15) == false) goto L_0x01a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0197, code lost:
        r2 = new org.eclipse.jetty.util.MultiPartInputStream.Base64InputStream((org.eclipse.jetty.util.ReadLineInputStream) r1._in);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01a7, code lost:
        if ("quoted-printable".equalsIgnoreCase(r15) == false) goto L_0x01b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01a9, code lost:
        r2 = new org.eclipse.jetty.util.MultiPartInputStream.C24421(r1, r1._in);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01b1, code lost:
        r2 = r1._in;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01b3, code lost:
        r4 = -2;
        r5 = false;
        r7 = false;
        r12 = -2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01b7, code lost:
        r14 = r7;
        r7 = r5;
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01bb, code lost:
        if (r12 == r4) goto L_0x01bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01bd, code lost:
        r15 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:?, code lost:
        r15 = r2.read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01c5, code lost:
        if (r15 == -1) goto L_0x0242;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01c7, code lost:
        r10 = r10 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01d5, code lost:
        if (r1._config.getMaxRequestSize() <= 0) goto L_0x0200;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01df, code lost:
        if (r10 > r1._config.getMaxRequestSize()) goto L_0x01e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ff, code lost:
        throw new java.lang.IllegalStateException("Request exceeds maxRequestSize (" + r1._config.getMaxRequestSize() + ")");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0200, code lost:
        r12 = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0202, code lost:
        if (r15 == 13) goto L_0x0232;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0204, code lost:
        if (r15 != 10) goto L_0x0209;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0206, code lost:
        r12 = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0209, code lost:
        if (r5 < 0) goto L_0x0217;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x020c, code lost:
        if (r5 >= r6.length) goto L_0x0217;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0210, code lost:
        if (r15 != r6[r5]) goto L_0x0217;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0212, code lost:
        r5 = r5 + 1;
        r4 = -2;
        r12 = -2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0217, code lost:
        if (r7 == false) goto L_0x021e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0219, code lost:
        r3.write(13);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parse() throws java.io.IOException, javax.servlet.ServletException {
        /*
            r26 = this;
            r1 = r26
            java.lang.String r2 = "Badly formatted multipart request"
            org.eclipse.jetty.util.MultiMap<java.lang.String> r0 = r1._parts
            if (r0 == 0) goto L_0x0009
            return
        L_0x0009:
            org.eclipse.jetty.util.MultiMap r0 = new org.eclipse.jetty.util.MultiMap
            r0.<init>()
            r1._parts = r0
            java.lang.String r0 = r1._contentType
            if (r0 == 0) goto L_0x0355
            java.lang.String r3 = "multipart/form-data"
            boolean r0 = r0.startsWith(r3)
            if (r0 != 0) goto L_0x001e
            goto L_0x0355
        L_0x001e:
            javax.servlet.MultipartConfigElement r0 = r1._config
            java.lang.String r0 = r0.getLocation()
            java.lang.String r3 = ""
            if (r0 != 0) goto L_0x002d
            java.io.File r0 = r1._contextTmpDir
            r1._tmpDir = r0
            goto L_0x0061
        L_0x002d:
            javax.servlet.MultipartConfigElement r0 = r1._config
            java.lang.String r0 = r0.getLocation()
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x003e
            java.io.File r0 = r1._contextTmpDir
            r1._tmpDir = r0
            goto L_0x0061
        L_0x003e:
            java.io.File r0 = new java.io.File
            javax.servlet.MultipartConfigElement r4 = r1._config
            java.lang.String r4 = r4.getLocation()
            r0.<init>(r4)
            boolean r4 = r0.isAbsolute()
            if (r4 == 0) goto L_0x0052
            r1._tmpDir = r0
            goto L_0x0061
        L_0x0052:
            java.io.File r0 = new java.io.File
            java.io.File r4 = r1._contextTmpDir
            javax.servlet.MultipartConfigElement r5 = r1._config
            java.lang.String r5 = r5.getLocation()
            r0.<init>(r4, r5)
            r1._tmpDir = r0
        L_0x0061:
            java.io.File r0 = r1._tmpDir
            boolean r0 = r0.exists()
            if (r0 != 0) goto L_0x006e
            java.io.File r0 = r1._tmpDir
            r0.mkdirs()
        L_0x006e:
            java.lang.String r0 = r1._contentType
            java.lang.String r4 = "boundary="
            int r0 = r0.indexOf(r4)
            java.lang.String r4 = ";"
            r5 = 1
            if (r0 < 0) goto L_0x009c
            java.lang.String r6 = r1._contentType
            int r6 = r6.indexOf(r4, r0)
            if (r6 >= 0) goto L_0x0089
            java.lang.String r6 = r1._contentType
            int r6 = r6.length()
        L_0x0089:
            java.lang.String r7 = r1._contentType
            java.lang.String r0 = r7.substring(r0, r6)
            java.lang.String r0 = r1.value(r0, r5)
            java.lang.String r0 = r0.trim()
            java.lang.String r0 = org.eclipse.jetty.util.QuotedStringTokenizer.unquote(r0)
            goto L_0x009d
        L_0x009c:
            r0 = r3
        L_0x009d:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "--"
            r6.append(r7)
            r6.append(r0)
            java.lang.String r0 = r6.toString()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r0)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "ISO-8859-1"
            byte[] r6 = r6.getBytes(r7)
            r7 = 0
            java.io.InputStream r8 = r1._in     // Catch:{ IOException -> 0x034b }
            org.eclipse.jetty.util.ReadLineInputStream r8 = (org.eclipse.jetty.util.ReadLineInputStream) r8     // Catch:{ IOException -> 0x034b }
            java.lang.String r8 = r8.readLine()     // Catch:{ IOException -> 0x034b }
            if (r8 == 0) goto L_0x0343
            java.lang.String r8 = r8.trim()
            r9 = 0
        L_0x00d3:
            if (r8 == 0) goto L_0x00f5
            boolean r10 = r8.equals(r0)
            if (r10 != 0) goto L_0x00f5
            if (r9 != 0) goto L_0x00e5
            org.eclipse.jetty.util.log.Logger r8 = LOG
            java.lang.Object[] r9 = new java.lang.Object[r7]
            r8.warn((java.lang.String) r2, (java.lang.Object[]) r9)
            r9 = 1
        L_0x00e5:
            java.io.InputStream r8 = r1._in
            org.eclipse.jetty.util.ReadLineInputStream r8 = (org.eclipse.jetty.util.ReadLineInputStream) r8
            java.lang.String r8 = r8.readLine()
            if (r8 != 0) goto L_0x00f0
            goto L_0x00d3
        L_0x00f0:
            java.lang.String r8 = r8.trim()
            goto L_0x00d3
        L_0x00f5:
            if (r8 == 0) goto L_0x033b
            r0 = 0
            r10 = 0
        L_0x00fa:
            if (r0 != 0) goto L_0x0330
            org.eclipse.jetty.util.MultiMap r2 = new org.eclipse.jetty.util.MultiMap
            r2.<init>()
            r13 = 0
            r14 = 0
            r15 = 0
        L_0x0104:
            java.io.InputStream r12 = r1._in
            org.eclipse.jetty.util.ReadLineInputStream r12 = (org.eclipse.jetty.util.ReadLineInputStream) r12
            java.lang.String r12 = r12.readLine()
            if (r12 != 0) goto L_0x0110
            goto L_0x0330
        L_0x0110:
            boolean r17 = r3.equals(r12)
            java.lang.String r8 = ")"
            java.lang.String r9 = "Request exceeds maxRequestSize ("
            if (r17 == 0) goto L_0x02a4
            if (r13 == 0) goto L_0x029c
            org.eclipse.jetty.util.QuotedStringTokenizer r12 = new org.eclipse.jetty.util.QuotedStringTokenizer
            r12.<init>(r13, r4, r7, r5)
            r7 = 0
            r13 = 0
            r20 = 0
        L_0x0125:
            boolean r16 = r12.hasMoreTokens()
            if (r16 == 0) goto L_0x016a
            java.lang.String r16 = r12.nextToken()
            java.lang.String r5 = r16.trim()
            r22 = r3
            java.util.Locale r3 = java.util.Locale.ENGLISH
            java.lang.String r3 = r5.toLowerCase(r3)
            r23 = r4
            java.lang.String r4 = "form-data"
            boolean r4 = r5.startsWith(r4)
            if (r4 == 0) goto L_0x0147
            r13 = 1
            goto L_0x0164
        L_0x0147:
            java.lang.String r4 = "name="
            boolean r4 = r3.startsWith(r4)
            if (r4 == 0) goto L_0x0156
            r4 = 1
            java.lang.String r3 = r1.value(r5, r4)
            r7 = r3
            goto L_0x0164
        L_0x0156:
            java.lang.String r4 = "filename="
            boolean r3 = r3.startsWith(r4)
            if (r3 == 0) goto L_0x0164
            java.lang.String r3 = r1.filenameValue(r5)
            r20 = r3
        L_0x0164:
            r3 = r22
            r4 = r23
            r5 = 1
            goto L_0x0125
        L_0x016a:
            r22 = r3
            r23 = r4
            if (r13 != 0) goto L_0x0171
            goto L_0x0173
        L_0x0171:
            if (r7 != 0) goto L_0x017a
        L_0x0173:
            r3 = r22
            r4 = r23
            r5 = 1
            r7 = 0
            goto L_0x00fa
        L_0x017a:
            org.eclipse.jetty.util.MultiPartInputStream$MultiPart r3 = new org.eclipse.jetty.util.MultiPartInputStream$MultiPart
            r4 = r20
            r3.<init>(r7, r4)
            r3.setHeaders(r2)
            r3.setContentType(r14)
            org.eclipse.jetty.util.MultiMap<java.lang.String> r2 = r1._parts
            r2.add(r7, r3)
            r3.open()
            java.lang.String r2 = "base64"
            boolean r2 = r2.equalsIgnoreCase(r15)
            if (r2 == 0) goto L_0x01a1
            org.eclipse.jetty.util.MultiPartInputStream$Base64InputStream r2 = new org.eclipse.jetty.util.MultiPartInputStream$Base64InputStream
            java.io.InputStream r4 = r1._in
            org.eclipse.jetty.util.ReadLineInputStream r4 = (org.eclipse.jetty.util.ReadLineInputStream) r4
            r2.<init>(r4)
            goto L_0x01b3
        L_0x01a1:
            java.lang.String r2 = "quoted-printable"
            boolean r2 = r2.equalsIgnoreCase(r15)
            if (r2 == 0) goto L_0x01b1
            org.eclipse.jetty.util.MultiPartInputStream$1 r2 = new org.eclipse.jetty.util.MultiPartInputStream$1
            java.io.InputStream r4 = r1._in
            r2.<init>(r4)
            goto L_0x01b3
        L_0x01b1:
            java.io.InputStream r2 = r1._in
        L_0x01b3:
            r4 = -2
            r5 = 0
            r7 = 0
            r12 = -2
        L_0x01b7:
            r13 = -1
            r14 = r7
            r7 = r5
            r5 = 0
        L_0x01bb:
            if (r12 == r4) goto L_0x01bf
            r15 = r12
            goto L_0x01c3
        L_0x01bf:
            int r15 = r2.read()     // Catch:{ all -> 0x0297 }
        L_0x01c3:
            r4 = 10
            if (r15 == r13) goto L_0x0242
            r24 = 1
            long r10 = r10 + r24
            javax.servlet.MultipartConfigElement r12 = r1._config     // Catch:{ all -> 0x0297 }
            long r24 = r12.getMaxRequestSize()     // Catch:{ all -> 0x0297 }
            r18 = 0
            int r12 = (r24 > r18 ? 1 : (r24 == r18 ? 0 : -1))
            if (r12 <= 0) goto L_0x0200
            javax.servlet.MultipartConfigElement r12 = r1._config     // Catch:{ all -> 0x0297 }
            long r24 = r12.getMaxRequestSize()     // Catch:{ all -> 0x0297 }
            int r12 = (r10 > r24 ? 1 : (r10 == r24 ? 0 : -1))
            if (r12 > 0) goto L_0x01e2
            goto L_0x0200
        L_0x01e2:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0297 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0297 }
            r2.<init>()     // Catch:{ all -> 0x0297 }
            r2.append(r9)     // Catch:{ all -> 0x0297 }
            javax.servlet.MultipartConfigElement r4 = r1._config     // Catch:{ all -> 0x0297 }
            long r4 = r4.getMaxRequestSize()     // Catch:{ all -> 0x0297 }
            r2.append(r4)     // Catch:{ all -> 0x0297 }
            r2.append(r8)     // Catch:{ all -> 0x0297 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0297 }
            r0.<init>(r2)     // Catch:{ all -> 0x0297 }
            throw r0     // Catch:{ all -> 0x0297 }
        L_0x0200:
            r12 = 13
            if (r15 == r12) goto L_0x0232
            if (r15 != r4) goto L_0x0209
            r12 = 13
            goto L_0x0232
        L_0x0209:
            if (r5 < 0) goto L_0x0217
            int r12 = r6.length     // Catch:{ all -> 0x0297 }
            if (r5 >= r12) goto L_0x0217
            byte r12 = r6[r5]     // Catch:{ all -> 0x0297 }
            if (r15 != r12) goto L_0x0217
            int r5 = r5 + 1
            r4 = -2
            r12 = -2
            goto L_0x01bb
        L_0x0217:
            if (r7 == 0) goto L_0x021e
            r7 = 13
            r3.write((int) r7)     // Catch:{ all -> 0x0297 }
        L_0x021e:
            if (r14 == 0) goto L_0x0223
            r3.write((int) r4)     // Catch:{ all -> 0x0297 }
        L_0x0223:
            if (r5 <= 0) goto L_0x0229
            r4 = 0
            r3.write(r6, r4, r5)     // Catch:{ all -> 0x0297 }
        L_0x0229:
            r3.write((int) r15)     // Catch:{ all -> 0x0297 }
            r4 = -2
            r5 = -1
            r7 = 0
            r12 = -2
            r14 = 0
            goto L_0x01bb
        L_0x0232:
            if (r15 != r12) goto L_0x0241
            r12 = 1
            r2.mark(r12)     // Catch:{ all -> 0x0297 }
            int r12 = r2.read()     // Catch:{ all -> 0x0297 }
            if (r12 == r4) goto L_0x0242
            r2.reset()     // Catch:{ all -> 0x0297 }
        L_0x0241:
            r12 = -2
        L_0x0242:
            if (r5 <= 0) goto L_0x024d
            int r13 = r6.length     // Catch:{ all -> 0x0297 }
            int r13 = r13 + -2
            if (r5 < r13) goto L_0x024a
            goto L_0x024d
        L_0x024a:
            r21 = 1
            goto L_0x0254
        L_0x024d:
            int r13 = r6.length     // Catch:{ all -> 0x0297 }
            r21 = 1
            int r13 = r13 + -1
            if (r5 != r13) goto L_0x0267
        L_0x0254:
            if (r7 == 0) goto L_0x025b
            r7 = 13
            r3.write((int) r7)     // Catch:{ all -> 0x0297 }
        L_0x025b:
            if (r14 == 0) goto L_0x0260
            r3.write((int) r4)     // Catch:{ all -> 0x0297 }
        L_0x0260:
            r7 = 0
            r3.write(r6, r7, r5)     // Catch:{ all -> 0x0297 }
            r5 = -1
            r7 = 0
            r14 = 0
        L_0x0267:
            if (r5 > 0) goto L_0x028e
            r13 = -1
            if (r15 != r13) goto L_0x026d
            goto L_0x028e
        L_0x026d:
            if (r7 == 0) goto L_0x0274
            r5 = 13
            r3.write((int) r5)     // Catch:{ all -> 0x0297 }
        L_0x0274:
            if (r14 == 0) goto L_0x0279
            r3.write((int) r4)     // Catch:{ all -> 0x0297 }
        L_0x0279:
            r5 = 13
            if (r15 != r5) goto L_0x027f
            r5 = 1
            goto L_0x0280
        L_0x027f:
            r5 = 0
        L_0x0280:
            if (r15 == r4) goto L_0x0287
            if (r12 != r4) goto L_0x0285
            goto L_0x0287
        L_0x0285:
            r7 = 0
            goto L_0x0288
        L_0x0287:
            r7 = 1
        L_0x0288:
            if (r12 != r4) goto L_0x028b
            r12 = -2
        L_0x028b:
            r4 = -2
            goto L_0x01b7
        L_0x028e:
            int r2 = r6.length     // Catch:{ all -> 0x0297 }
            if (r5 != r2) goto L_0x0292
            r0 = 1
        L_0x0292:
            r3.close()
            goto L_0x0173
        L_0x0297:
            r0 = move-exception
            r3.close()
            throw r0
        L_0x029c:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r2 = "Missing content-disposition"
            r0.<init>(r2)
            throw r0
        L_0x02a4:
            r22 = r3
            r23 = r4
            r21 = 1
            int r3 = r12.length()
            long r3 = (long) r3
            long r10 = r10 + r3
            javax.servlet.MultipartConfigElement r3 = r1._config
            long r3 = r3.getMaxRequestSize()
            r18 = 0
            int r5 = (r3 > r18 ? 1 : (r3 == r18 ? 0 : -1))
            if (r5 <= 0) goto L_0x02e5
            javax.servlet.MultipartConfigElement r3 = r1._config
            long r3 = r3.getMaxRequestSize()
            int r5 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x02c7
            goto L_0x02e5
        L_0x02c7:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r9)
            javax.servlet.MultipartConfigElement r3 = r1._config
            long r3 = r3.getMaxRequestSize()
            r2.append(r3)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x02e5:
            r3 = 58
            r4 = 0
            int r3 = r12.indexOf(r3, r4)
            if (r3 <= 0) goto L_0x0328
            java.lang.String r5 = r12.substring(r4, r3)
            java.lang.String r4 = r5.trim()
            java.util.Locale r5 = java.util.Locale.ENGLISH
            java.lang.String r4 = r4.toLowerCase(r5)
            int r3 = r3 + 1
            int r5 = r12.length()
            java.lang.String r3 = r12.substring(r3, r5)
            java.lang.String r3 = r3.trim()
            r2.put(r4, r3)
            java.lang.String r5 = "content-disposition"
            boolean r5 = r4.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x0316
            r13 = r3
        L_0x0316:
            java.lang.String r5 = "content-type"
            boolean r5 = r4.equalsIgnoreCase(r5)
            if (r5 == 0) goto L_0x031f
            r14 = r3
        L_0x031f:
            java.lang.String r5 = "content-transfer-encoding"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0328
            r15 = r3
        L_0x0328:
            r3 = r22
            r4 = r23
            r5 = 1
            r7 = 0
            goto L_0x0104
        L_0x0330:
            if (r0 == 0) goto L_0x0333
            return
        L_0x0333:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r2 = "Incomplete parts"
            r0.<init>(r2)
            throw r0
        L_0x033b:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r2 = "Missing initial multi part boundary"
            r0.<init>(r2)
            throw r0
        L_0x0343:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r2 = "Missing content for multipart request"
            r0.<init>(r2)
            throw r0
        L_0x034b:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r3 = LOG
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r3.warn((java.lang.String) r2, (java.lang.Object[]) r4)
            throw r0
        L_0x0355:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.MultiPartInputStream.parse():void");
    }

    public void setDeleteOnExit(boolean z) {
        this._deleteOnExit = z;
    }

    public boolean isDeleteOnExit() {
        return this._deleteOnExit;
    }

    private String value(String str, boolean z) {
        return QuotedStringTokenizer.unquoteOnly(str.substring(str.indexOf(61) + 1).trim());
    }

    private String filenameValue(String str) {
        String trim = str.substring(str.indexOf(61) + 1).trim();
        if (!trim.matches(".??[a-z,A-Z]\\:\\\\[^\\\\].*")) {
            return QuotedStringTokenizer.unquoteOnly(trim, true);
        }
        char charAt = trim.charAt(0);
        if (charAt == '\"' || charAt == '\'') {
            trim = trim.substring(1);
        }
        char charAt2 = trim.charAt(trim.length() - 1);
        return (charAt2 == '\"' || charAt2 == '\'') ? trim.substring(0, trim.length() - 1) : trim;
    }

    private static class Base64InputStream extends InputStream {
        byte[] _buffer;
        ReadLineInputStream _in;
        String _line;
        int _pos;

        public Base64InputStream(ReadLineInputStream readLineInputStream) {
            this._in = readLineInputStream;
        }

        public int read() throws IOException {
            byte[] bArr = this._buffer;
            if (bArr == null || this._pos >= bArr.length) {
                this._line = this._in.readLine();
                String str = this._line;
                if (str == null) {
                    return -1;
                }
                if (str.startsWith("--")) {
                    this._buffer = (this._line + "\r\n").getBytes();
                } else if (this._line.length() == 0) {
                    this._buffer = "\r\n".getBytes();
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(((this._line.length() * 4) / 3) + 2);
                    B64Code.decode(this._line, byteArrayOutputStream);
                    byteArrayOutputStream.write(13);
                    byteArrayOutputStream.write(10);
                    this._buffer = byteArrayOutputStream.toByteArray();
                }
                this._pos = 0;
            }
            byte[] bArr2 = this._buffer;
            int i = this._pos;
            this._pos = i + 1;
            return bArr2[i];
        }
    }
}
