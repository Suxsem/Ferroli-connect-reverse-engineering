package org.eclipse.jetty.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.BufferCache;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class MimeTypes {
    public static final BufferCache CACHE = new BufferCache();
    public static final String FORM_ENCODED = "application/x-www-form-urlencoded";
    public static final BufferCache.CachedBuffer FORM_ENCODED_BUFFER = CACHE.add(FORM_ENCODED, 1);
    private static final int FORM_ENCODED_ORDINAL = 1;
    private static final Logger LOG = Log.getLogger((Class<?>) MimeTypes.class);
    public static final String MESSAGE_HTTP = "message/http";
    public static final BufferCache.CachedBuffer MESSAGE_HTTP_BUFFER = CACHE.add(MESSAGE_HTTP, 2);
    private static final int MESSAGE_HTTP_ORDINAL = 2;
    public static final String MULTIPART_BYTERANGES = "multipart/byteranges";
    public static final BufferCache.CachedBuffer MULTIPART_BYTERANGES_BUFFER = CACHE.add(MULTIPART_BYTERANGES, 3);
    private static final int MULTIPART_BYTERANGES_ORDINAL = 3;
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_HTML_8859_1 = "text/html;charset=ISO-8859-1";
    public static final BufferCache.CachedBuffer TEXT_HTML_8859_1_BUFFER = CACHE.add(TEXT_HTML_8859_1, 8);
    private static final int TEXT_HTML_8859_1_ORDINAL = 8;
    public static final BufferCache.CachedBuffer TEXT_HTML_BUFFER = CACHE.add(TEXT_HTML, 4);
    private static final int TEXT_HTML_ORDINAL = 4;
    public static final String TEXT_HTML_UTF_8 = "text/html;charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_HTML_UTF_8_BUFFER = CACHE.add(TEXT_HTML_UTF_8, 11);
    private static final int TEXT_HTML_UTF_8_ORDINAL = 11;
    private static final String TEXT_HTML__8859_1 = "text/html; charset=ISO-8859-1";
    public static final BufferCache.CachedBuffer TEXT_HTML__8859_1_BUFFER = CACHE.add(TEXT_HTML__8859_1, 8);
    private static final String TEXT_HTML__UTF_8 = "text/html; charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_HTML__UTF_8_BUFFER = CACHE.add(TEXT_HTML__UTF_8, 11);
    public static final String TEXT_JSON = "text/json";
    public static final BufferCache.CachedBuffer TEXT_JSON_BUFFER = CACHE.add(TEXT_JSON, 7);
    private static final int TEXT_JSON_ORDINAL = 7;
    public static final String TEXT_JSON_UTF_8 = "text/json;charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_JSON_UTF_8_BUFFER = CACHE.add(TEXT_JSON_UTF_8, 14);
    private static final int TEXT_JSON_UTF_8_ORDINAL = 14;
    private static final String TEXT_JSON__UTF_8 = "text/json; charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_JSON__UTF_8_BUFFER = CACHE.add(TEXT_JSON__UTF_8, 14);
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_PLAIN_8859_1 = "text/plain;charset=ISO-8859-1";
    public static final BufferCache.CachedBuffer TEXT_PLAIN_8859_1_BUFFER = CACHE.add(TEXT_PLAIN_8859_1, 9);
    private static final int TEXT_PLAIN_8859_1_ORDINAL = 9;
    public static final BufferCache.CachedBuffer TEXT_PLAIN_BUFFER = CACHE.add(TEXT_PLAIN, 5);
    private static final int TEXT_PLAIN_ORDINAL = 5;
    public static final String TEXT_PLAIN_UTF_8 = "text/plain;charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_PLAIN_UTF_8_BUFFER = CACHE.add(TEXT_PLAIN_UTF_8, 12);
    private static final int TEXT_PLAIN_UTF_8_ORDINAL = 12;
    private static final String TEXT_PLAIN__8859_1 = "text/plain; charset=ISO-8859-1";
    public static final BufferCache.CachedBuffer TEXT_PLAIN__8859_1_BUFFER = CACHE.add(TEXT_PLAIN__8859_1, 9);
    private static final String TEXT_PLAIN__UTF_8 = "text/plain; charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_PLAIN__UTF_8_BUFFER = CACHE.add(TEXT_PLAIN__UTF_8, 12);
    public static final String TEXT_XML = "text/xml";
    public static final String TEXT_XML_8859_1 = "text/xml;charset=ISO-8859-1";
    public static final BufferCache.CachedBuffer TEXT_XML_8859_1_BUFFER = CACHE.add(TEXT_XML_8859_1, 10);
    private static final int TEXT_XML_8859_1_ORDINAL = 10;
    public static final BufferCache.CachedBuffer TEXT_XML_BUFFER = CACHE.add(TEXT_XML, 6);
    private static final int TEXT_XML_ORDINAL = 6;
    public static final String TEXT_XML_UTF_8 = "text/xml;charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_XML_UTF_8_BUFFER = CACHE.add(TEXT_XML_UTF_8, 13);
    private static final int TEXT_XML_UTF_8_ORDINAL = 13;
    private static final String TEXT_XML__8859_1 = "text/xml; charset=ISO-8859-1";
    public static final BufferCache.CachedBuffer TEXT_XML__8859_1_BUFFER = CACHE.add(TEXT_XML__8859_1, 10);
    private static final String TEXT_XML__UTF_8 = "text/xml; charset=UTF-8";
    public static final BufferCache.CachedBuffer TEXT_XML__UTF_8_BUFFER = CACHE.add(TEXT_XML__UTF_8, 13);
    private static final Map __dftMimeMap = new HashMap();
    private static final Map __encodings = new HashMap();
    private static int __index = 15;
    private Map _mimeMap;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("org/eclipse/jetty/http/mime");
            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String nextElement = keys.nextElement();
                __dftMimeMap.put(StringUtil.asciiToLowerCase(nextElement), normalizeMimeType(bundle.getString(nextElement)));
            }
        } catch (MissingResourceException e) {
            LOG.warn(e.toString(), new Object[0]);
            LOG.debug(e);
        }
        try {
            ResourceBundle bundle2 = ResourceBundle.getBundle("org/eclipse/jetty/http/encoding");
            Enumeration<String> keys2 = bundle2.getKeys();
            while (keys2.hasMoreElements()) {
                Buffer normalizeMimeType = normalizeMimeType(keys2.nextElement());
                __encodings.put(normalizeMimeType, bundle2.getString(normalizeMimeType.toString()));
            }
        } catch (MissingResourceException e2) {
            LOG.warn(e2.toString(), new Object[0]);
            LOG.debug(e2);
        }
        TEXT_HTML_BUFFER.setAssociate(StringUtil.__ISO_8859_1, TEXT_HTML_8859_1_BUFFER);
        TEXT_HTML_BUFFER.setAssociate("ISO_8859_1", TEXT_HTML_8859_1_BUFFER);
        TEXT_HTML_BUFFER.setAssociate("iso-8859-1", TEXT_HTML_8859_1_BUFFER);
        TEXT_PLAIN_BUFFER.setAssociate(StringUtil.__ISO_8859_1, TEXT_PLAIN_8859_1_BUFFER);
        TEXT_PLAIN_BUFFER.setAssociate("ISO_8859_1", TEXT_PLAIN_8859_1_BUFFER);
        TEXT_PLAIN_BUFFER.setAssociate("iso-8859-1", TEXT_PLAIN_8859_1_BUFFER);
        TEXT_XML_BUFFER.setAssociate(StringUtil.__ISO_8859_1, TEXT_XML_8859_1_BUFFER);
        TEXT_XML_BUFFER.setAssociate("ISO_8859_1", TEXT_XML_8859_1_BUFFER);
        TEXT_XML_BUFFER.setAssociate("iso-8859-1", TEXT_XML_8859_1_BUFFER);
        TEXT_HTML_BUFFER.setAssociate("UTF-8", TEXT_HTML_UTF_8_BUFFER);
        TEXT_HTML_BUFFER.setAssociate(StringUtil.__UTF8Alt, TEXT_HTML_UTF_8_BUFFER);
        TEXT_HTML_BUFFER.setAssociate("utf8", TEXT_HTML_UTF_8_BUFFER);
        TEXT_HTML_BUFFER.setAssociate("utf-8", TEXT_HTML_UTF_8_BUFFER);
        TEXT_PLAIN_BUFFER.setAssociate("UTF-8", TEXT_PLAIN_UTF_8_BUFFER);
        TEXT_PLAIN_BUFFER.setAssociate(StringUtil.__UTF8Alt, TEXT_PLAIN_UTF_8_BUFFER);
        TEXT_PLAIN_BUFFER.setAssociate("utf8", TEXT_PLAIN_UTF_8_BUFFER);
        TEXT_PLAIN_BUFFER.setAssociate("utf-8", TEXT_PLAIN_UTF_8_BUFFER);
        TEXT_XML_BUFFER.setAssociate("UTF-8", TEXT_XML_UTF_8_BUFFER);
        TEXT_XML_BUFFER.setAssociate(StringUtil.__UTF8Alt, TEXT_XML_UTF_8_BUFFER);
        TEXT_XML_BUFFER.setAssociate("utf8", TEXT_XML_UTF_8_BUFFER);
        TEXT_XML_BUFFER.setAssociate("utf-8", TEXT_XML_UTF_8_BUFFER);
        TEXT_JSON_BUFFER.setAssociate("UTF-8", TEXT_JSON_UTF_8_BUFFER);
        TEXT_JSON_BUFFER.setAssociate(StringUtil.__UTF8Alt, TEXT_JSON_UTF_8_BUFFER);
        TEXT_JSON_BUFFER.setAssociate("utf8", TEXT_JSON_UTF_8_BUFFER);
        TEXT_JSON_BUFFER.setAssociate("utf-8", TEXT_JSON_UTF_8_BUFFER);
    }

    public synchronized Map getMimeMap() {
        return this._mimeMap;
    }

    public void setMimeMap(Map map) {
        if (map == null) {
            this._mimeMap = null;
            return;
        }
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            hashMap.put(entry.getKey(), normalizeMimeType(entry.getValue().toString()));
        }
        this._mimeMap = hashMap;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: org.eclipse.jetty.io.Buffer} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.p119io.Buffer getMimeByExtension(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x0036
            r1 = -1
        L_0x0004:
            if (r0 != 0) goto L_0x0036
            int r1 = r1 + 1
            java.lang.String r2 = "."
            int r1 = r5.indexOf(r2, r1)
            if (r1 < 0) goto L_0x0036
            int r2 = r5.length()
            if (r1 < r2) goto L_0x0017
            goto L_0x0036
        L_0x0017:
            int r2 = r1 + 1
            java.lang.String r2 = r5.substring(r2)
            java.lang.String r2 = org.eclipse.jetty.util.StringUtil.asciiToLowerCase(r2)
            java.util.Map r3 = r4._mimeMap
            if (r3 == 0) goto L_0x002b
            java.lang.Object r0 = r3.get(r2)
            org.eclipse.jetty.io.Buffer r0 = (org.eclipse.jetty.p119io.Buffer) r0
        L_0x002b:
            if (r0 != 0) goto L_0x0004
            java.util.Map r0 = __dftMimeMap
            java.lang.Object r0 = r0.get(r2)
            org.eclipse.jetty.io.Buffer r0 = (org.eclipse.jetty.p119io.Buffer) r0
            goto L_0x0004
        L_0x0036:
            if (r0 != 0) goto L_0x0050
            java.util.Map r5 = r4._mimeMap
            java.lang.String r1 = "*"
            if (r5 == 0) goto L_0x0045
            java.lang.Object r5 = r5.get(r1)
            r0 = r5
            org.eclipse.jetty.io.Buffer r0 = (org.eclipse.jetty.p119io.Buffer) r0
        L_0x0045:
            if (r0 != 0) goto L_0x0050
            java.util.Map r5 = __dftMimeMap
            java.lang.Object r5 = r5.get(r1)
            r0 = r5
            org.eclipse.jetty.io.Buffer r0 = (org.eclipse.jetty.p119io.Buffer) r0
        L_0x0050:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.MimeTypes.getMimeByExtension(java.lang.String):org.eclipse.jetty.io.Buffer");
    }

    public void addMimeMapping(String str, String str2) {
        if (this._mimeMap == null) {
            this._mimeMap = new HashMap();
        }
        this._mimeMap.put(StringUtil.asciiToLowerCase(str), normalizeMimeType(str2));
    }

    private static synchronized Buffer normalizeMimeType(String str) {
        BufferCache.CachedBuffer cachedBuffer;
        synchronized (MimeTypes.class) {
            cachedBuffer = CACHE.get(str);
            if (cachedBuffer == null) {
                BufferCache bufferCache = CACHE;
                int i = __index;
                __index = i + 1;
                cachedBuffer = bufferCache.add(str, i);
            }
        }
        return cachedBuffer;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0096, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a3, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x00a8, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCharsetFromContentType(org.eclipse.jetty.p119io.Buffer r13) {
        /*
            boolean r0 = r13 instanceof org.eclipse.jetty.p119io.BufferCache.CachedBuffer
            java.lang.String r1 = "UTF-8"
            if (r0 == 0) goto L_0x0015
            r0 = r13
            org.eclipse.jetty.io.BufferCache$CachedBuffer r0 = (org.eclipse.jetty.p119io.BufferCache.CachedBuffer) r0
            int r0 = r0.getOrdinal()
            switch(r0) {
                case 8: goto L_0x0012;
                case 9: goto L_0x0012;
                case 10: goto L_0x0012;
                case 11: goto L_0x0011;
                case 12: goto L_0x0011;
                case 13: goto L_0x0011;
                case 14: goto L_0x0011;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x0015
        L_0x0011:
            return r1
        L_0x0012:
            java.lang.String r13 = "ISO-8859-1"
            return r13
        L_0x0015:
            int r0 = r13.getIndex()
            int r2 = r13.putIndex()
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x0021:
            r7 = 10
            if (r0 >= r2) goto L_0x00ac
            byte r8 = r13.peek(r0)
            r9 = 34
            r10 = 1
            if (r5 == 0) goto L_0x0035
            if (r4 == r7) goto L_0x0035
            if (r9 != r8) goto L_0x00a8
            r5 = 0
            goto L_0x00a8
        L_0x0035:
            r11 = 59
            r12 = 32
            switch(r4) {
                case 0: goto L_0x00a1;
                case 1: goto L_0x0098;
                case 2: goto L_0x0090;
                case 3: goto L_0x008a;
                case 4: goto L_0x0084;
                case 5: goto L_0x007e;
                case 6: goto L_0x0078;
                case 7: goto L_0x0071;
                case 8: goto L_0x0067;
                case 9: goto L_0x0058;
                case 10: goto L_0x003e;
                default: goto L_0x003c;
            }
        L_0x003c:
            goto L_0x00a8
        L_0x003e:
            if (r5 != 0) goto L_0x0044
            if (r11 == r8) goto L_0x0048
            if (r12 == r8) goto L_0x0048
        L_0x0044:
            if (r5 == 0) goto L_0x00a8
            if (r9 != r8) goto L_0x00a8
        L_0x0048:
            org.eclipse.jetty.io.BufferCache r2 = CACHE
            int r0 = r0 - r6
            org.eclipse.jetty.io.Buffer r13 = r13.peek(r6, r0)
            org.eclipse.jetty.io.Buffer r13 = r2.lookup((org.eclipse.jetty.p119io.Buffer) r13)
            java.lang.String r13 = r13.toString((java.lang.String) r1)
            return r13
        L_0x0058:
            if (r12 != r8) goto L_0x005b
            goto L_0x00a8
        L_0x005b:
            if (r9 != r8) goto L_0x0063
            int r4 = r0 + 1
            r6 = r4
            r4 = 10
            goto L_0x00a3
        L_0x0063:
            r6 = r0
            r4 = 10
            goto L_0x00a8
        L_0x0067:
            r7 = 61
            if (r7 != r8) goto L_0x006e
            r4 = 9
            goto L_0x00a8
        L_0x006e:
            if (r12 == r8) goto L_0x00a8
            goto L_0x0096
        L_0x0071:
            r4 = 116(0x74, float:1.63E-43)
            if (r4 != r8) goto L_0x0096
            r4 = 8
            goto L_0x00a8
        L_0x0078:
            r4 = 101(0x65, float:1.42E-43)
            if (r4 != r8) goto L_0x0096
            r4 = 7
            goto L_0x00a8
        L_0x007e:
            r4 = 115(0x73, float:1.61E-43)
            if (r4 != r8) goto L_0x0096
            r4 = 6
            goto L_0x00a8
        L_0x0084:
            r4 = 114(0x72, float:1.6E-43)
            if (r4 != r8) goto L_0x0096
            r4 = 5
            goto L_0x00a8
        L_0x008a:
            r4 = 97
            if (r4 != r8) goto L_0x0096
            r4 = 4
            goto L_0x00a8
        L_0x0090:
            r4 = 104(0x68, float:1.46E-43)
            if (r4 != r8) goto L_0x0096
            r4 = 3
            goto L_0x00a8
        L_0x0096:
            r4 = 0
            goto L_0x00a8
        L_0x0098:
            r7 = 99
            if (r7 != r8) goto L_0x009e
            r4 = 2
            goto L_0x00a8
        L_0x009e:
            if (r12 == r8) goto L_0x00a8
            goto L_0x0096
        L_0x00a1:
            if (r9 != r8) goto L_0x00a5
        L_0x00a3:
            r5 = 1
            goto L_0x00a8
        L_0x00a5:
            if (r11 != r8) goto L_0x00a8
            r4 = 1
        L_0x00a8:
            int r0 = r0 + 1
            goto L_0x0021
        L_0x00ac:
            if (r4 != r7) goto L_0x00be
            org.eclipse.jetty.io.BufferCache r2 = CACHE
            int r0 = r0 - r6
            org.eclipse.jetty.io.Buffer r13 = r13.peek(r6, r0)
            org.eclipse.jetty.io.Buffer r13 = r2.lookup((org.eclipse.jetty.p119io.Buffer) r13)
            java.lang.String r13 = r13.toString((java.lang.String) r1)
            return r13
        L_0x00be:
            java.util.Map r0 = __encodings
            java.lang.Object r13 = r0.get(r13)
            java.lang.String r13 = (java.lang.String) r13
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.MimeTypes.getCharsetFromContentType(org.eclipse.jetty.io.Buffer):java.lang.String");
    }
}
