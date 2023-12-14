package org.eclipse.jetty.server;

import javax.servlet.http.Cookie;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class CookieCutter {
    private static final Logger LOG = Log.getLogger((Class<?>) CookieCutter.class);
    private Cookie[] _cookies;
    int _fields;
    private Cookie[] _lastCookies;
    Object _lazyFields;

    public Cookie[] getCookies() {
        Object obj;
        Cookie[] cookieArr = this._cookies;
        if (cookieArr != null) {
            return cookieArr;
        }
        if (this._lastCookies == null || (obj = this._lazyFields) == null || this._fields != LazyList.size(obj)) {
            parseFields();
        } else {
            this._cookies = this._lastCookies;
        }
        Cookie[] cookieArr2 = this._cookies;
        this._lastCookies = cookieArr2;
        return cookieArr2;
    }

    public void setCookies(Cookie[] cookieArr) {
        this._cookies = cookieArr;
        this._lastCookies = null;
        this._lazyFields = null;
        this._fields = 0;
    }

    public void reset() {
        this._cookies = null;
        this._fields = 0;
    }

    public void addCookieField(String str) {
        if (str != null) {
            String trim = str.trim();
            if (trim.length() != 0) {
                int size = LazyList.size(this._lazyFields);
                int i = this._fields;
                if (size > i) {
                    if (!trim.equals(LazyList.get(this._lazyFields, i))) {
                        while (true) {
                            int size2 = LazyList.size(this._lazyFields);
                            int i2 = this._fields;
                            if (size2 <= i2) {
                                break;
                            }
                            this._lazyFields = LazyList.remove(this._lazyFields, i2);
                        }
                    } else {
                        this._fields++;
                        return;
                    }
                }
                this._cookies = null;
                this._lastCookies = null;
                Object obj = this._lazyFields;
                int i3 = this._fields;
                this._fields = i3 + 1;
                this._lazyFields = LazyList.add(obj, i3, trim);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v0, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v6, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v9, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v10, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v11, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v12, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v14, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v29, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v19, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v34, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v38, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v39, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v40, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v30, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v44, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v48, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v32, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v49, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v50, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v33, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v54, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v34, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v55, resolved type: java.lang.Object} */
    /* JADX WARNING: type inference failed for: r16v5 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0190, code lost:
        r16 = r16;
        r15 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0190, code lost:
        r16 = r16;
        r15 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0190, code lost:
        r16 = r16;
        r15 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005b, code lost:
        if (r5 != r8) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005d, code lost:
        if (r11 == false) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005f, code lost:
        r16 = r6.substring(r13, r5 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0066, code lost:
        r14 = r5;
        r9 = r13;
        r16 = "";
        r15 = r6.substring(r13, r5 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0071, code lost:
        r14 = r5;
        r9 = r13;
        r15 = r15;
        r16 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0073, code lost:
        r7 = false;
        r16 = r16;
        r15 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00e2, code lost:
        if (r13 < 0) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00e4, code lost:
        r15 = r6.substring(r13, r14 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00ea, code lost:
        r11 = true;
        r13 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00ee, code lost:
        if (r13 < 0) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00f0, code lost:
        r16 = "";
        r15 = r6.substring(r13, r14 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00f8, code lost:
        r7 = r0;
        r9 = -1;
        r16 = r16;
        r15 = r15;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x010c A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseFields() {
        /*
            r20 = this;
            r1 = r20
            r2 = 0
            r1._lastCookies = r2
            r1._cookies = r2
        L_0x0007:
            java.lang.Object r0 = r1._lazyFields
            int r0 = org.eclipse.jetty.util.LazyList.size(r0)
            int r3 = r1._fields
            if (r0 <= r3) goto L_0x001a
            java.lang.Object r0 = r1._lazyFields
            java.lang.Object r0 = org.eclipse.jetty.util.LazyList.remove((java.lang.Object) r0, (int) r3)
            r1._lazyFields = r0
            goto L_0x0007
        L_0x001a:
            r0 = r2
            r4 = 0
            r5 = 0
        L_0x001d:
            int r6 = r1._fields
            if (r4 >= r6) goto L_0x019d
            java.lang.Object r6 = r1._lazyFields
            java.lang.Object r6 = org.eclipse.jetty.util.LazyList.get(r6, r4)
            java.lang.String r6 = (java.lang.String) r6
            int r7 = r6.length()
            int r8 = r7 + -1
            r10 = r0
            r3 = r2
            r15 = r3
            r16 = r15
            r2 = r5
            r0 = 0
            r5 = 0
            r11 = 0
            r12 = 0
            r13 = -1
            r14 = -1
        L_0x003b:
            if (r5 >= r7) goto L_0x0196
            char r9 = r6.charAt(r5)
            r17 = r7
            r7 = 34
            java.lang.String r18 = ""
            r19 = 1
            if (r0 == 0) goto L_0x0076
            if (r12 == 0) goto L_0x0050
            r12 = 0
            goto L_0x0190
        L_0x0050:
            if (r9 == r7) goto L_0x005b
            r7 = 92
            if (r9 == r7) goto L_0x0058
            goto L_0x0190
        L_0x0058:
            r12 = 1
            goto L_0x0190
        L_0x005b:
            if (r5 != r8) goto L_0x0071
            if (r11 == 0) goto L_0x0066
            int r0 = r5 + 1
            java.lang.String r16 = r6.substring(r13, r0)
            goto L_0x0071
        L_0x0066:
            int r0 = r5 + 1
            java.lang.String r15 = r6.substring(r13, r0)
            r14 = r5
            r9 = r13
            r16 = r18
            goto L_0x0073
        L_0x0071:
            r14 = r5
            r9 = r13
        L_0x0073:
            r7 = 0
            goto L_0x010a
        L_0x0076:
            r7 = 9
            if (r11 == 0) goto L_0x00ba
            if (r9 == r7) goto L_0x0190
            r7 = 32
            if (r9 == r7) goto L_0x0190
            r7 = 34
            if (r9 == r7) goto L_0x00ab
            r7 = 59
            if (r9 == r7) goto L_0x0099
            if (r13 >= 0) goto L_0x008c
            r9 = r5
            goto L_0x008d
        L_0x008c:
            r9 = r13
        L_0x008d:
            if (r5 != r8) goto L_0x00de
            int r7 = r5 + 1
            java.lang.String r16 = r6.substring(r9, r7)
        L_0x0095:
            r7 = r0
            r14 = r5
            goto L_0x010a
        L_0x0099:
            if (r13 < 0) goto L_0x00a4
            int r7 = r14 + 1
            java.lang.String r7 = r6.substring(r13, r7)
            r16 = r7
            goto L_0x00a6
        L_0x00a4:
            r16 = r18
        L_0x00a6:
            r7 = r0
            r9 = -1
            r11 = 0
            goto L_0x010a
        L_0x00ab:
            if (r13 >= 0) goto L_0x00b0
            r9 = r5
            r0 = 1
            goto L_0x00b1
        L_0x00b0:
            r9 = r13
        L_0x00b1:
            if (r5 != r8) goto L_0x00de
            int r7 = r5 + 1
            java.lang.String r16 = r6.substring(r9, r7)
            goto L_0x0095
        L_0x00ba:
            if (r9 == r7) goto L_0x0190
            r7 = 32
            if (r9 == r7) goto L_0x0190
            r7 = 34
            if (r9 == r7) goto L_0x00fb
            r7 = 59
            if (r9 == r7) goto L_0x00ee
            r7 = 61
            if (r9 == r7) goto L_0x00e2
            if (r13 >= 0) goto L_0x00d0
            r9 = r5
            goto L_0x00d1
        L_0x00d0:
            r9 = r13
        L_0x00d1:
            if (r5 != r8) goto L_0x00de
            int r7 = r5 + 1
            java.lang.String r15 = r6.substring(r9, r7)
        L_0x00d9:
            r7 = r0
            r14 = r5
            r16 = r18
            goto L_0x010a
        L_0x00de:
            r14 = r5
        L_0x00df:
            r13 = r9
            goto L_0x0190
        L_0x00e2:
            if (r13 < 0) goto L_0x00ea
            int r7 = r14 + 1
            java.lang.String r15 = r6.substring(r13, r7)
        L_0x00ea:
            r11 = 1
            r13 = -1
            goto L_0x0190
        L_0x00ee:
            if (r13 < 0) goto L_0x00f8
            int r7 = r14 + 1
            java.lang.String r15 = r6.substring(r13, r7)
            r16 = r18
        L_0x00f8:
            r7 = r0
            r9 = -1
            goto L_0x010a
        L_0x00fb:
            if (r13 >= 0) goto L_0x0100
            r9 = r5
            r0 = 1
            goto L_0x0101
        L_0x0100:
            r9 = r13
        L_0x0101:
            if (r5 != r8) goto L_0x00de
            int r7 = r5 + 1
            java.lang.String r15 = r6.substring(r9, r7)
            goto L_0x00d9
        L_0x010a:
            if (r16 == 0) goto L_0x018d
            if (r15 == 0) goto L_0x018d
            java.lang.String r0 = org.eclipse.jetty.util.QuotedStringTokenizer.unquoteOnly(r15)
            java.lang.String r13 = org.eclipse.jetty.util.QuotedStringTokenizer.unquoteOnly(r16)
            java.lang.String r15 = "$"
            boolean r15 = r0.startsWith(r15)     // Catch:{ Exception -> 0x017f }
            if (r15 == 0) goto L_0x016e
            java.util.Locale r15 = java.util.Locale.ENGLISH     // Catch:{ Exception -> 0x017f }
            java.lang.String r0 = r0.toLowerCase(r15)     // Catch:{ Exception -> 0x017f }
            java.lang.String r15 = "$path"
            boolean r15 = r15.equals(r0)     // Catch:{ Exception -> 0x017f }
            if (r15 == 0) goto L_0x0132
            if (r3 == 0) goto L_0x016c
            r3.setPath(r13)     // Catch:{ Exception -> 0x017f }
            goto L_0x016c
        L_0x0132:
            java.lang.String r15 = "$domain"
            boolean r15 = r15.equals(r0)     // Catch:{ Exception -> 0x017f }
            if (r15 == 0) goto L_0x0140
            if (r3 == 0) goto L_0x016c
            r3.setDomain(r13)     // Catch:{ Exception -> 0x017f }
            goto L_0x016c
        L_0x0140:
            java.lang.String r15 = "$port"
            boolean r15 = r15.equals(r0)     // Catch:{ Exception -> 0x017f }
            if (r15 == 0) goto L_0x015f
            if (r3 == 0) goto L_0x016c
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x017f }
            r0.<init>()     // Catch:{ Exception -> 0x017f }
            java.lang.String r15 = "$port="
            r0.append(r15)     // Catch:{ Exception -> 0x017f }
            r0.append(r13)     // Catch:{ Exception -> 0x017f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x017f }
            r3.setComment(r0)     // Catch:{ Exception -> 0x017f }
            goto L_0x016c
        L_0x015f:
            java.lang.String r15 = "$version"
            boolean r0 = r15.equals(r0)     // Catch:{ Exception -> 0x017f }
            if (r0 == 0) goto L_0x016c
            int r0 = java.lang.Integer.parseInt(r13)     // Catch:{ Exception -> 0x017f }
            r2 = r0
        L_0x016c:
            r15 = r3
            goto L_0x0186
        L_0x016e:
            javax.servlet.http.Cookie r15 = new javax.servlet.http.Cookie     // Catch:{ Exception -> 0x017f }
            r15.<init>(r0, r13)     // Catch:{ Exception -> 0x017f }
            if (r2 <= 0) goto L_0x0178
            r15.setVersion(r2)     // Catch:{ Exception -> 0x017d }
        L_0x0178:
            java.lang.Object r10 = org.eclipse.jetty.util.LazyList.add(r10, r15)     // Catch:{ Exception -> 0x017d }
            goto L_0x0186
        L_0x017d:
            r0 = move-exception
            goto L_0x0181
        L_0x017f:
            r0 = move-exception
            r15 = r3
        L_0x0181:
            org.eclipse.jetty.util.log.Logger r3 = LOG
            r3.debug(r0)
        L_0x0186:
            r0 = r7
            r13 = r9
            r3 = r15
            r15 = 0
            r16 = 0
            goto L_0x0190
        L_0x018d:
            r0 = r7
            goto L_0x00df
        L_0x0190:
            int r5 = r5 + 1
            r7 = r17
            goto L_0x003b
        L_0x0196:
            int r4 = r4 + 1
            r5 = r2
            r0 = r10
            r2 = 0
            goto L_0x001d
        L_0x019d:
            java.lang.Class<javax.servlet.http.Cookie> r2 = javax.servlet.http.Cookie.class
            java.lang.Object r0 = org.eclipse.jetty.util.LazyList.toArray(r0, r2)
            javax.servlet.http.Cookie[] r0 = (javax.servlet.http.Cookie[]) r0
            javax.servlet.http.Cookie[] r0 = (javax.servlet.http.Cookie[]) r0
            r1._cookies = r0
            javax.servlet.http.Cookie[] r0 = r1._cookies
            r1._lastCookies = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.CookieCutter.parseFields():void");
    }
}
