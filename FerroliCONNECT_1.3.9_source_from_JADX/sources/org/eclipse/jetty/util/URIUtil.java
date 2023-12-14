package org.eclipse.jetty.util;

import java.io.UnsupportedEncodingException;

public class URIUtil implements Cloneable {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String HTTPS_COLON = "https:";
    public static final String HTTP_COLON = "http:";
    public static final String SLASH = "/";
    public static final String __CHARSET = System.getProperty("org.eclipse.jetty.util.URI.charset", "UTF-8");

    private URIUtil() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0009, code lost:
        r0 = encodePath((java.lang.StringBuilder) null, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String encodePath(java.lang.String r1) {
        /*
            if (r1 == 0) goto L_0x0015
            int r0 = r1.length()
            if (r0 != 0) goto L_0x0009
            goto L_0x0015
        L_0x0009:
            r0 = 0
            java.lang.StringBuilder r0 = encodePath(r0, r1)
            if (r0 != 0) goto L_0x0011
            goto L_0x0015
        L_0x0011:
            java.lang.String r1 = r0.toString()
        L_0x0015:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.URIUtil.encodePath(java.lang.String):java.lang.String");
    }

    public static StringBuilder encodePath(StringBuilder sb, String str) {
        StringBuilder sb2;
        byte[] bArr;
        String str2 = str;
        byte[] bArr2 = null;
        int i = 0;
        if (sb == null) {
            int i2 = 0;
            while (true) {
                if (i2 >= str.length()) {
                    sb2 = sb;
                    break;
                }
                char charAt = str2.charAt(i2);
                if (charAt == ' ' || charAt == '%' || charAt == '\'' || charAt == '\"' || charAt == '#' || charAt == ';' || charAt == '<' || charAt == '>' || charAt == '?') {
                    sb2 = new StringBuilder(str.length() * 2);
                } else if (charAt > 127) {
                    try {
                        bArr = str2.getBytes(__CHARSET);
                        sb2 = new StringBuilder(str.length() * 2);
                        break;
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalStateException(e);
                    }
                } else {
                    i2++;
                }
            }
            bArr = null;
            if (sb2 == null) {
                return null;
            }
            bArr2 = bArr;
        } else {
            sb2 = sb;
        }
        synchronized (sb2) {
            if (bArr2 != null) {
                while (i < bArr2.length) {
                    byte b = bArr2[i];
                    if (b == 32) {
                        sb2.append("%20");
                    } else if (b == 37) {
                        sb2.append("%25");
                    } else if (b == 39) {
                        sb2.append("%27");
                    } else if (b == 34) {
                        sb2.append("%22");
                    } else if (b == 35) {
                        sb2.append("%23");
                    } else if (b == 59) {
                        sb2.append("%3B");
                    } else if (b == 60) {
                        sb2.append("%3C");
                    } else if (b == 62) {
                        sb2.append("%3E");
                    } else if (b == 63) {
                        sb2.append("%3F");
                    } else if (b < 0) {
                        sb2.append('%');
                        TypeUtil.toHex(b, (Appendable) sb2);
                    } else {
                        sb2.append((char) b);
                    }
                    i++;
                }
            } else {
                while (i < str.length()) {
                    char charAt2 = str2.charAt(i);
                    if (charAt2 == ' ') {
                        sb2.append("%20");
                    } else if (charAt2 == '%') {
                        sb2.append("%25");
                    } else if (charAt2 == '\'') {
                        sb2.append("%27");
                    } else if (charAt2 == '\"') {
                        sb2.append("%22");
                    } else if (charAt2 == '#') {
                        sb2.append("%23");
                    } else if (charAt2 == ';') {
                        sb2.append("%3B");
                    } else if (charAt2 == '<') {
                        sb2.append("%3C");
                    } else if (charAt2 == '>') {
                        sb2.append("%3E");
                    } else if (charAt2 != '?') {
                        sb2.append(charAt2);
                    } else {
                        sb2.append("%3F");
                    }
                    i++;
                }
            }
        }
        return sb2;
    }

    public static StringBuilder encodeString(StringBuilder sb, String str, String str2) {
        if (sb == null) {
            int i = 0;
            while (true) {
                if (i >= str.length()) {
                    break;
                }
                char charAt = str.charAt(i);
                if (charAt == '%' || str2.indexOf(charAt) >= 0) {
                    sb = new StringBuilder(str.length() << 1);
                } else {
                    i++;
                }
            }
            if (sb == null) {
                return null;
            }
        }
        synchronized (sb) {
            for (int i2 = 0; i2 < str.length(); i2++) {
                char charAt2 = str.charAt(i2);
                if (charAt2 != '%') {
                    if (str2.indexOf(charAt2) < 0) {
                        sb.append(charAt2);
                    }
                }
                sb.append('%');
                StringUtil.append(sb, (byte) (charAt2 & 255), 16);
            }
        }
        return sb;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006d, code lost:
        r0 = r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String decodePath(java.lang.String r11) {
        /*
            r0 = 0
            if (r11 != 0) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = r11.length()
            r2 = 0
            r3 = r0
            r4 = r3
            r0 = 0
            r5 = 0
            r6 = 0
        L_0x000e:
            if (r0 >= r1) goto L_0x006d
            char r7 = r11.charAt(r0)
            r8 = 37
            if (r7 != r8) goto L_0x0038
            int r8 = r0 + 2
            if (r8 >= r1) goto L_0x0038
            if (r3 != 0) goto L_0x0025
            char[] r3 = new char[r1]
            byte[] r4 = new byte[r1]
            r11.getChars(r2, r0, r3, r2)
        L_0x0025:
            int r7 = r5 + 1
            int r0 = r0 + 1
            r9 = 16
            r10 = 2
            int r0 = org.eclipse.jetty.util.TypeUtil.parseInt((java.lang.String) r11, (int) r0, (int) r10, (int) r9)
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r0 = (byte) r0
            r4[r5] = r0
            r5 = r7
            r0 = r8
            goto L_0x006a
        L_0x0038:
            r8 = 59
            if (r7 != r8) goto L_0x0044
            if (r3 != 0) goto L_0x006d
            char[] r3 = new char[r1]
            r11.getChars(r2, r0, r3, r2)
            goto L_0x006e
        L_0x0044:
            if (r4 != 0) goto L_0x0049
            int r6 = r6 + 1
            goto L_0x006a
        L_0x0049:
            if (r5 <= 0) goto L_0x0065
            java.lang.String r8 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0053 }
            java.lang.String r9 = __CHARSET     // Catch:{ UnsupportedEncodingException -> 0x0053 }
            r8.<init>(r4, r2, r5, r9)     // Catch:{ UnsupportedEncodingException -> 0x0053 }
            goto L_0x0058
        L_0x0053:
            java.lang.String r8 = new java.lang.String
            r8.<init>(r4, r2, r5)
        L_0x0058:
            int r5 = r8.length()
            r8.getChars(r2, r5, r3, r6)
            int r5 = r8.length()
            int r6 = r6 + r5
            r5 = 0
        L_0x0065:
            int r8 = r6 + 1
            r3[r6] = r7
            r6 = r8
        L_0x006a:
            int r0 = r0 + 1
            goto L_0x000e
        L_0x006d:
            r0 = r6
        L_0x006e:
            if (r3 != 0) goto L_0x0071
            return r11
        L_0x0071:
            if (r5 <= 0) goto L_0x008c
            java.lang.String r11 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x007b }
            java.lang.String r1 = __CHARSET     // Catch:{ UnsupportedEncodingException -> 0x007b }
            r11.<init>(r4, r2, r5, r1)     // Catch:{ UnsupportedEncodingException -> 0x007b }
            goto L_0x0080
        L_0x007b:
            java.lang.String r11 = new java.lang.String
            r11.<init>(r4, r2, r5)
        L_0x0080:
            int r1 = r11.length()
            r11.getChars(r2, r1, r3, r0)
            int r11 = r11.length()
            int r0 = r0 + r11
        L_0x008c:
            java.lang.String r11 = new java.lang.String
            r11.<init>(r3, r2, r0)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.URIUtil.decodePath(java.lang.String):java.lang.String");
    }

    public static String decodePath(byte[] bArr, int i, int i2) {
        int i3;
        byte[] bArr2 = null;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i4 >= i2) {
                break;
            }
            int i6 = i4 + i;
            byte b = bArr[i6];
            if (b == 37 && (i3 = i4 + 2) < i2) {
                b = (byte) (TypeUtil.parseInt(bArr, i6 + 1, 2, 16) & 255);
                i4 = i3;
            } else if (b == 59) {
                i2 = i4;
                break;
            } else if (bArr2 == null) {
                i5++;
                i4++;
            }
            if (bArr2 == null) {
                bArr2 = new byte[i2];
                for (int i7 = 0; i7 < i5; i7++) {
                    bArr2[i7] = bArr[i7 + i];
                }
            }
            bArr2[i5] = b;
            i5++;
            i4++;
        }
        if (bArr2 == null) {
            return StringUtil.toString(bArr, i, i2, __CHARSET);
        }
        return StringUtil.toString(bArr2, 0, i5, __CHARSET);
    }

    public static String addPaths(String str, String str2) {
        if (str == null || str.length() == 0) {
            return (str == null || str2 != null) ? str2 : str;
        }
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        int indexOf = str.indexOf(59);
        if (indexOf < 0) {
            indexOf = str.indexOf(63);
        }
        if (indexOf == 0) {
            return str2 + str;
        }
        if (indexOf < 0) {
            indexOf = str.length();
        }
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + 2);
        sb.append(str);
        int i = indexOf - 1;
        if (sb.charAt(i) == '/') {
            if (str2.startsWith("/")) {
                sb.deleteCharAt(i);
                sb.insert(i, str2);
            } else {
                sb.insert(indexOf, str2);
            }
        } else if (str2.startsWith("/")) {
            sb.insert(indexOf, str2);
        } else {
            sb.insert(indexOf, '/');
            sb.insert(indexOf + 1, str2);
        }
        return sb.toString();
    }

    public static String parentPath(String str) {
        int lastIndexOf;
        if (str == null || "/".equals(str) || (lastIndexOf = str.lastIndexOf(47, str.length() - 2)) < 0) {
            return null;
        }
        return str.substring(0, lastIndexOf + 1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0071, code lost:
        if (r6.charAt(r9 - 1) == '.') goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b7, code lost:
        if (r6.charAt(r9 - 1) == '.') goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00db, code lost:
        if (r6.charAt(r9 - 1) == '.') goto L_0x0073;
     */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0140 A[LOOP:4: B:107:0x0138->B:110:0x0140, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String canonicalPath(java.lang.String r14) {
        /*
            if (r14 == 0) goto L_0x0155
            int r0 = r14.length()
            if (r0 != 0) goto L_0x000a
            goto L_0x0155
        L_0x000a:
            int r0 = r14.length()
            r1 = 47
            int r2 = r14.lastIndexOf(r1, r0)
        L_0x0014:
            r3 = 3
            r4 = 2
            r5 = 46
            if (r0 <= 0) goto L_0x0044
            int r6 = r0 - r2
            if (r6 == r4) goto L_0x0032
            if (r6 == r3) goto L_0x0021
            goto L_0x003a
        L_0x0021:
            int r6 = r2 + 1
            char r6 = r14.charAt(r6)
            if (r6 != r5) goto L_0x003a
            int r6 = r2 + 2
            char r6 = r14.charAt(r6)
            if (r6 == r5) goto L_0x0044
            goto L_0x003a
        L_0x0032:
            int r6 = r2 + 1
            char r6 = r14.charAt(r6)
            if (r6 == r5) goto L_0x0044
        L_0x003a:
            int r0 = r2 + -1
            int r0 = r14.lastIndexOf(r1, r0)
            r13 = r2
            r2 = r0
            r0 = r13
            goto L_0x0014
        L_0x0044:
            if (r2 < r0) goto L_0x0047
            return r14
        L_0x0047:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r14)
            r14 = 0
            r7 = -1
            r8 = r2
            r2 = 0
            r9 = -1
            r10 = -1
        L_0x0052:
            if (r0 <= 0) goto L_0x0148
            int r11 = r0 - r8
            if (r11 == r4) goto L_0x00ba
            if (r11 == r3) goto L_0x0077
            if (r2 <= 0) goto L_0x0127
            int r2 = r2 + -1
            if (r2 != 0) goto L_0x0127
            if (r8 < 0) goto L_0x0064
            r10 = r8
            goto L_0x0065
        L_0x0064:
            r10 = 0
        L_0x0065:
            int r11 = r6.length()
            if (r9 != r11) goto L_0x0127
            int r11 = r9 + -1
            char r11 = r6.charAt(r11)
            if (r11 != r5) goto L_0x0127
        L_0x0073:
            int r10 = r10 + 1
            goto L_0x0127
        L_0x0077:
            int r11 = r8 + 1
            char r11 = r6.charAt(r11)
            if (r11 != r5) goto L_0x009e
            int r11 = r8 + 2
            char r11 = r6.charAt(r11)
            if (r11 == r5) goto L_0x0088
            goto L_0x009e
        L_0x0088:
            if (r9 >= 0) goto L_0x008b
            r9 = r0
        L_0x008b:
            int r2 = r2 + 1
            int r0 = r8 + -1
        L_0x008f:
            if (r0 < 0) goto L_0x009a
            char r10 = r6.charAt(r0)
            if (r10 == r1) goto L_0x009a
            int r0 = r0 + -1
            goto L_0x008f
        L_0x009a:
            r10 = r8
            r8 = r0
            r0 = r10
            goto L_0x0052
        L_0x009e:
            if (r2 <= 0) goto L_0x0127
            int r2 = r2 + -1
            if (r2 != 0) goto L_0x0127
            if (r8 < 0) goto L_0x00a8
            r10 = r8
            goto L_0x00a9
        L_0x00a8:
            r10 = 0
        L_0x00a9:
            if (r10 <= 0) goto L_0x0127
            int r11 = r6.length()
            if (r9 != r11) goto L_0x0127
            int r11 = r9 + -1
            char r11 = r6.charAt(r11)
            if (r11 != r5) goto L_0x0127
            goto L_0x0073
        L_0x00ba:
            int r11 = r8 + 1
            char r12 = r6.charAt(r11)
            if (r12 == r5) goto L_0x00de
            if (r2 <= 0) goto L_0x0127
            int r2 = r2 + -1
            if (r2 != 0) goto L_0x0127
            if (r8 < 0) goto L_0x00cc
            r10 = r8
            goto L_0x00cd
        L_0x00cc:
            r10 = 0
        L_0x00cd:
            if (r10 <= 0) goto L_0x0127
            int r11 = r6.length()
            if (r9 != r11) goto L_0x0127
            int r11 = r9 + -1
            char r11 = r6.charAt(r11)
            if (r11 != r5) goto L_0x0127
            goto L_0x0073
        L_0x00de:
            if (r8 >= 0) goto L_0x00f4
            int r12 = r6.length()
            if (r12 <= r4) goto L_0x00f4
            r12 = 1
            char r12 = r6.charAt(r12)
            if (r12 != r1) goto L_0x00f4
            char r12 = r6.charAt(r4)
            if (r12 != r1) goto L_0x00f4
            goto L_0x0127
        L_0x00f4:
            if (r9 >= 0) goto L_0x00f7
            r9 = r0
        L_0x00f7:
            if (r8 < 0) goto L_0x0118
            if (r8 != 0) goto L_0x0102
            char r10 = r6.charAt(r8)
            if (r10 != r1) goto L_0x0102
            goto L_0x0118
        L_0x0102:
            int r10 = r6.length()
            if (r0 != r10) goto L_0x010a
            r10 = r11
            goto L_0x010b
        L_0x010a:
            r10 = r8
        L_0x010b:
            int r0 = r8 + -1
        L_0x010d:
            if (r0 < 0) goto L_0x0143
            char r11 = r6.charAt(r0)
            if (r11 == r1) goto L_0x0143
            int r0 = r0 + -1
            goto L_0x010d
        L_0x0118:
            int r10 = r6.length()
            if (r9 >= r10) goto L_0x0126
            char r10 = r6.charAt(r9)
            if (r10 != r1) goto L_0x0126
            int r9 = r9 + 1
        L_0x0126:
            r10 = r11
        L_0x0127:
            if (r2 > 0) goto L_0x0136
            if (r10 < 0) goto L_0x0136
            if (r9 < r10) goto L_0x0136
            r6.delete(r10, r9)
            if (r2 <= 0) goto L_0x0134
            r9 = r0
            goto L_0x0135
        L_0x0134:
            r9 = -1
        L_0x0135:
            r10 = -1
        L_0x0136:
            int r0 = r8 + -1
        L_0x0138:
            if (r0 < 0) goto L_0x0143
            char r11 = r6.charAt(r0)
            if (r11 == r1) goto L_0x0143
            int r0 = r0 + -1
            goto L_0x0138
        L_0x0143:
            r13 = r8
            r8 = r0
            r0 = r13
            goto L_0x0052
        L_0x0148:
            if (r2 <= 0) goto L_0x014c
            r14 = 0
            return r14
        L_0x014c:
            if (r9 < 0) goto L_0x0151
            r6.delete(r10, r9)
        L_0x0151:
            java.lang.String r14 = r6.toString()
        L_0x0155:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.URIUtil.canonicalPath(java.lang.String):java.lang.String");
    }

    public static String compactPath(String str) {
        int i;
        if (str == null || str.length() == 0) {
            return str;
        }
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char charAt = str.charAt(i2);
            if (charAt == '/') {
                i3++;
                if (i3 == 2) {
                    break;
                }
            } else if (charAt == '?') {
                return str;
            } else {
                i3 = 0;
            }
            i2++;
        }
        if (i < 2) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length());
        stringBuffer.append(str, 0, i2);
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt2 = str.charAt(i2);
            if (charAt2 != '/') {
                if (charAt2 == '?') {
                    stringBuffer.append(str, i2, length);
                    break;
                }
                stringBuffer.append(charAt2);
                i = 0;
            } else {
                int i4 = i + 1;
                if (i == 0) {
                    stringBuffer.append(charAt2);
                }
                i = i4;
            }
            i2++;
        }
        return stringBuffer.toString();
    }

    public static boolean hasScheme(String str) {
        int i = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt != ':') {
                if ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && (i <= 0 || ((charAt < '0' || charAt > '9') && charAt != '.' && charAt != '+' && charAt != '-')))) {
                    break;
                }
                i++;
            } else {
                return true;
            }
        }
        return false;
    }
}
