package org.eclipse.jetty.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import kotlin.UByte;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.util.Utf8StringBuilder;

public class HttpURI {
    private static final int ASTERISK = 10;
    private static final int AUTH = 4;
    private static final int AUTH_OR_PATH = 1;
    private static final int IPV6 = 5;
    private static final int PARAM = 8;
    private static final int PATH = 7;
    private static final int PORT = 6;
    private static final int QUERY = 9;
    private static final int SCHEME_OR_PATH = 2;
    private static final int START = 0;
    private static final byte[] __empty = new byte[0];
    int _authority;
    boolean _encoded = false;
    int _end;
    int _fragment;
    int _host;
    int _param;
    boolean _partial = false;
    int _path;
    int _port;
    int _portValue;
    int _query;
    byte[] _raw = __empty;
    String _rawString;
    int _scheme;
    final Utf8StringBuilder _utf8b = new Utf8StringBuilder(64);

    public HttpURI() {
    }

    public HttpURI(boolean z) {
        this._partial = z;
    }

    public HttpURI(String str) {
        this._rawString = str;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            parse(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public HttpURI(byte[] bArr, int i, int i2) {
        parse2(bArr, i, i2);
    }

    public HttpURI(URI uri) {
        parse(uri.toASCIIString());
    }

    public void parse(String str) {
        byte[] bytes = str.getBytes();
        parse2(bytes, 0, bytes.length);
        this._rawString = str;
    }

    public void parse(byte[] bArr, int i, int i2) {
        this._rawString = null;
        parse2(bArr, i, i2);
    }

    public void parseConnect(byte[] bArr, int i, int i2) {
        this._rawString = null;
        this._encoded = false;
        this._raw = bArr;
        int i3 = i + i2;
        this._end = i3;
        this._scheme = i;
        this._authority = i;
        this._host = i;
        int i4 = this._end;
        this._port = i4;
        this._portValue = -1;
        this._path = i4;
        this._param = i4;
        this._query = i4;
        this._fragment = i4;
        int i5 = i;
        char c = 4;
        while (true) {
            if (i5 >= i3) {
                break;
            }
            char c2 = (char) (this._raw[i5] & UByte.MAX_VALUE);
            int i6 = i5 + 1;
            if (c == 4) {
                if (c2 == ':') {
                    this._port = i5;
                    break;
                } else if (c2 == '[') {
                    c = 5;
                }
            } else if (c != 5) {
                continue;
            } else if (c2 == '/') {
                throw new IllegalArgumentException("No closing ']' for " + StringUtil.toString(this._raw, i, i2, URIUtil.__CHARSET));
            } else if (c2 == ']') {
                c = 4;
            }
            i5 = i6;
        }
        int i7 = this._port;
        int i8 = this._path;
        if (i7 < i8) {
            this._portValue = TypeUtil.parseInt(this._raw, i7 + 1, (i8 - i7) - 1, 10);
            this._path = i;
            return;
        }
        throw new IllegalArgumentException("No port");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01b1, code lost:
        r9 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005a, code lost:
        r10 = 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e0, code lost:
        r3 = r14;
     */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0144  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parse2(byte[] r17, int r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            r2 = r19
            r3 = 0
            r0._encoded = r3
            r4 = r17
            r0._raw = r4
            int r4 = r1 + r2
            r0._end = r4
            r0._scheme = r1
            r0._authority = r1
            r0._host = r1
            r0._port = r1
            r5 = -1
            r0._portValue = r5
            r0._path = r1
            int r5 = r0._end
            r0._param = r5
            r0._query = r5
            r0._fragment = r5
            r9 = 1
            r3 = r1
            r11 = r3
            r10 = 0
        L_0x002a:
            if (r3 >= r4) goto L_0x01b4
            byte[] r12 = r0._raw
            byte r13 = r12[r3]
            r13 = r13 & 255(0xff, float:3.57E-43)
            char r13 = (char) r13
            int r14 = r3 + 1
            r5 = 59
            r6 = 63
            r7 = 35
            r8 = 47
            r15 = 58
            switch(r10) {
                case 0: goto L_0x0179;
                case 1: goto L_0x014c;
                case 2: goto L_0x00e3;
                case 3: goto L_0x0042;
                case 4: goto L_0x00c1;
                case 5: goto L_0x0099;
                case 6: goto L_0x0087;
                case 7: goto L_0x0064;
                case 8: goto L_0x0052;
                case 9: goto L_0x004c;
                case 10: goto L_0x0044;
                default: goto L_0x0042;
            }
        L_0x0042:
            goto L_0x01b0
        L_0x0044:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "only '*'"
            r1.<init>(r2)
            throw r1
        L_0x004c:
            if (r13 != r7) goto L_0x01b0
            r0._fragment = r3
            goto L_0x01b0
        L_0x0052:
            if (r13 == r7) goto L_0x005e
            if (r13 == r6) goto L_0x0058
            goto L_0x00e0
        L_0x0058:
            r0._query = r3
        L_0x005a:
            r10 = 9
            goto L_0x00e0
        L_0x005e:
            r0._query = r3
            r0._fragment = r3
            goto L_0x01b0
        L_0x0064:
            if (r13 == r7) goto L_0x007f
            r7 = 37
            if (r13 == r7) goto L_0x007b
            if (r13 == r5) goto L_0x0075
            if (r13 == r6) goto L_0x0070
            goto L_0x00e0
        L_0x0070:
            r0._param = r3
            r0._query = r3
            goto L_0x005a
        L_0x0075:
            r0._param = r3
            r10 = 8
            goto L_0x00e0
        L_0x007b:
            r0._encoded = r9
            goto L_0x00e0
        L_0x007f:
            r0._param = r3
            r0._query = r3
            r0._fragment = r3
            goto L_0x01b0
        L_0x0087:
            if (r13 != r8) goto L_0x01b0
            r0._path = r3
            int r5 = r0._port
            int r6 = r0._authority
            if (r5 > r6) goto L_0x0095
            int r5 = r0._path
            r0._port = r5
        L_0x0095:
            r11 = r3
            r3 = r14
        L_0x0097:
            r10 = 7
            goto L_0x002a
        L_0x0099:
            if (r13 == r8) goto L_0x00a2
            r3 = 93
            if (r13 == r3) goto L_0x00a0
            goto L_0x00e0
        L_0x00a0:
            r10 = 4
            goto L_0x00e0
        L_0x00a2:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "No closing ']' for "
            r4.append(r5)
            byte[] r5 = r0._raw
            java.lang.String r6 = org.eclipse.jetty.util.URIUtil.__CHARSET
            java.lang.String r1 = org.eclipse.jetty.util.StringUtil.toString(r5, r1, r2, r6)
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r3.<init>(r1)
            throw r3
        L_0x00c1:
            if (r13 == r8) goto L_0x00d8
            if (r13 == r15) goto L_0x00d4
            r3 = 64
            if (r13 == r3) goto L_0x00d1
            r3 = 91
            if (r13 == r3) goto L_0x00ce
            goto L_0x00e0
        L_0x00ce:
            r3 = 5
            r10 = 5
            goto L_0x00e0
        L_0x00d1:
            r0._host = r14
            goto L_0x00e0
        L_0x00d4:
            r0._port = r3
            r10 = 6
            goto L_0x00e0
        L_0x00d8:
            r0._path = r3
            int r5 = r0._path
            r0._port = r5
            r11 = r3
            r10 = 7
        L_0x00e0:
            r3 = r14
            goto L_0x002a
        L_0x00e3:
            r9 = 6
            if (r2 <= r9) goto L_0x0108
            r9 = 116(0x74, float:1.63E-43)
            if (r13 != r9) goto L_0x0108
            int r9 = r1 + 3
            byte r5 = r12[r9]
            if (r5 != r15) goto L_0x00f6
            int r14 = r1 + 4
            r5 = r9
        L_0x00f3:
            r13 = 58
            goto L_0x0109
        L_0x00f6:
            int r5 = r1 + 4
            byte r9 = r12[r5]
            if (r9 != r15) goto L_0x00ff
            int r14 = r1 + 5
            goto L_0x00f3
        L_0x00ff:
            int r5 = r1 + 5
            byte r9 = r12[r5]
            if (r9 != r15) goto L_0x0108
            int r14 = r1 + 6
            goto L_0x00f3
        L_0x0108:
            r5 = r3
        L_0x0109:
            if (r13 == r7) goto L_0x0144
            if (r13 == r8) goto L_0x0140
            if (r13 == r6) goto L_0x0137
            if (r13 == r15) goto L_0x011e
            r3 = 59
            if (r13 == r3) goto L_0x0117
            goto L_0x01b0
        L_0x0117:
            r0._param = r5
            r3 = r14
            r10 = 8
            goto L_0x01b1
        L_0x011e:
            int r3 = r14 + 1
            r0._authority = r14
            r0._path = r14
            byte[] r5 = r0._raw
            byte r5 = r5[r3]
            r5 = r5 & 255(0xff, float:3.57E-43)
            char r5 = (char) r5
            if (r5 != r8) goto L_0x0131
            r11 = r14
            r10 = 1
            goto L_0x01b1
        L_0x0131:
            r0._host = r14
            r0._port = r14
            r11 = r14
            goto L_0x0141
        L_0x0137:
            r0._param = r5
            r0._query = r5
            r3 = r14
            r10 = 9
            goto L_0x01b1
        L_0x0140:
            r3 = r14
        L_0x0141:
            r10 = 7
            goto L_0x01b1
        L_0x0144:
            r0._param = r5
            r0._query = r5
            r0._fragment = r5
            goto L_0x01b0
        L_0x014c:
            boolean r3 = r0._partial
            if (r3 != 0) goto L_0x0156
            int r3 = r0._scheme
            int r5 = r0._authority
            if (r3 == r5) goto L_0x0165
        L_0x0156:
            if (r13 != r8) goto L_0x0165
            r0._host = r14
            int r3 = r0._end
            r0._port = r3
            r0._path = r3
            r3 = r14
            r9 = 1
            r10 = 4
            goto L_0x002a
        L_0x0165:
            r3 = 59
            if (r13 == r3) goto L_0x0174
            if (r13 == r6) goto L_0x0174
            if (r13 != r7) goto L_0x016e
            goto L_0x0174
        L_0x016e:
            r0._host = r11
            r0._port = r11
            r3 = r14
            goto L_0x0176
        L_0x0174:
            int r3 = r14 + -1
        L_0x0176:
            r9 = 1
            goto L_0x0097
        L_0x0179:
            if (r13 == r7) goto L_0x01a9
            r5 = 42
            if (r13 == r5) goto L_0x01a4
            if (r13 == r8) goto L_0x019e
            r5 = 59
            if (r13 == r5) goto L_0x0195
            if (r13 == r6) goto L_0x018a
            r10 = 2
        L_0x0188:
            r11 = r3
            goto L_0x01b0
        L_0x018a:
            r0._param = r3
            r0._query = r3
            r11 = r3
            r3 = r14
            r9 = 1
            r10 = 9
            goto L_0x002a
        L_0x0195:
            r0._param = r3
            r11 = r3
            r3 = r14
            r9 = 1
            r10 = 8
            goto L_0x002a
        L_0x019e:
            r11 = r3
            r3 = r14
            r9 = 1
            r10 = 1
            goto L_0x002a
        L_0x01a4:
            r0._path = r3
            r10 = 10
            goto L_0x0188
        L_0x01a9:
            r0._param = r3
            r0._query = r3
            r0._fragment = r3
            goto L_0x0188
        L_0x01b0:
            r3 = r14
        L_0x01b1:
            r9 = 1
            goto L_0x002a
        L_0x01b4:
            int r1 = r0._port
            int r2 = r0._path
            if (r1 >= r2) goto L_0x01c9
            byte[] r3 = r0._raw
            int r4 = r1 + 1
            int r2 = r2 - r1
            r1 = 1
            int r2 = r2 - r1
            r1 = 10
            int r1 = org.eclipse.jetty.util.TypeUtil.parseInt((byte[]) r3, (int) r4, (int) r2, (int) r1)
            r0._portValue = r1
        L_0x01c9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpURI.parse2(byte[], int, int):void");
    }

    private String toUtf8String(int i, int i2) {
        this._utf8b.reset();
        this._utf8b.append(this._raw, i, i2);
        return this._utf8b.toString();
    }

    public String getScheme() {
        int i = this._scheme;
        int i2 = this._authority;
        if (i == i2) {
            return null;
        }
        int i3 = i2 - i;
        if (i3 == 5) {
            byte[] bArr = this._raw;
            if (bArr[i] == 104 && bArr[i + 1] == 116 && bArr[i + 2] == 116 && bArr[i + 3] == 112) {
                return "http";
            }
        }
        if (i3 == 6) {
            byte[] bArr2 = this._raw;
            int i4 = this._scheme;
            if (bArr2[i4] == 104 && bArr2[i4 + 1] == 116 && bArr2[i4 + 2] == 116 && bArr2[i4 + 3] == 112 && bArr2[i4 + 4] == 115) {
                return "https";
            }
        }
        int i5 = this._scheme;
        return toUtf8String(i5, (this._authority - i5) - 1);
    }

    public String getAuthority() {
        int i = this._authority;
        int i2 = this._path;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getHost() {
        int i = this._host;
        int i2 = this._port;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public int getPort() {
        return this._portValue;
    }

    public String getPath() {
        int i = this._path;
        int i2 = this._param;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getDecodedPath() {
        int i = this._path;
        int i2 = this._param;
        if (i == i2) {
            return null;
        }
        int i3 = i2 - i;
        boolean z = false;
        while (i < this._param) {
            byte b = this._raw[i];
            if (b == 37) {
                if (!z) {
                    this._utf8b.reset();
                    Utf8StringBuilder utf8StringBuilder = this._utf8b;
                    byte[] bArr = this._raw;
                    int i4 = this._path;
                    utf8StringBuilder.append(bArr, i4, i - i4);
                    z = true;
                }
                int i5 = i + 2;
                int i6 = this._param;
                if (i5 < i6) {
                    byte[] bArr2 = this._raw;
                    int i7 = i + 1;
                    if (bArr2[i7] == 117) {
                        i += 5;
                        if (i < i6) {
                            try {
                                this._utf8b.getStringBuilder().append(new String(Character.toChars(TypeUtil.parseInt(bArr2, i5, 4, 16))));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            throw new IllegalArgumentException("Bad %u encoding: " + this);
                        }
                    } else {
                        this._utf8b.append((byte) (TypeUtil.parseInt(bArr2, i7, 2, 16) & 255));
                        i = i5;
                    }
                } else {
                    throw new IllegalArgumentException("Bad % encoding: " + this);
                }
            } else if (z) {
                this._utf8b.append(b);
            }
            i++;
        }
        if (!z) {
            return toUtf8String(this._path, i3);
        }
        return this._utf8b.toString();
    }

    public String getDecodedPath(String str) {
        int i = this._path;
        int i2 = this._param;
        byte[] bArr = null;
        if (i == i2) {
            return null;
        }
        int i3 = i2 - i;
        int i4 = 0;
        while (true) {
            int i5 = this._param;
            if (i < i5) {
                byte[] bArr2 = this._raw;
                byte b = bArr2[i];
                if (b == 37) {
                    if (bArr == null) {
                        bArr = new byte[i3];
                        System.arraycopy(bArr2, this._path, bArr, 0, i4);
                    }
                    int i6 = i + 2;
                    int i7 = this._param;
                    if (i6 < i7) {
                        byte[] bArr3 = this._raw;
                        int i8 = i + 1;
                        if (bArr3[i8] == 117) {
                            i += 5;
                            if (i < i7) {
                                try {
                                    byte[] bytes = new String(Character.toChars(TypeUtil.parseInt(bArr3, i6, 4, 16))).getBytes(str);
                                    System.arraycopy(bytes, 0, bArr, i4, bytes.length);
                                    i4 += bytes.length;
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                throw new IllegalArgumentException("Bad %u encoding: " + this);
                            }
                        } else {
                            bArr[i4] = (byte) (TypeUtil.parseInt(bArr3, i8, 2, 16) & 255);
                            i = i6;
                            i4++;
                        }
                    } else {
                        throw new IllegalArgumentException("Bad % encoding: " + this);
                    }
                } else if (bArr == null) {
                    i4++;
                } else {
                    bArr[i4] = b;
                    i4++;
                }
                i++;
            } else if (bArr != null) {
                return StringUtil.toString(bArr, 0, i4, str);
            } else {
                byte[] bArr4 = this._raw;
                int i9 = this._path;
                return StringUtil.toString(bArr4, i9, i5 - i9, str);
            }
        }
    }

    public String getPathAndParam() {
        int i = this._path;
        int i2 = this._query;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getCompletePath() {
        int i = this._path;
        int i2 = this._end;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i, i2 - i);
    }

    public String getParam() {
        int i = this._param;
        int i2 = this._query;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i + 1, (i2 - i) - 1);
    }

    public String getQuery() {
        int i = this._query;
        int i2 = this._fragment;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i + 1, (i2 - i) - 1);
    }

    public String getQuery(String str) {
        int i = this._query;
        int i2 = this._fragment;
        if (i == i2) {
            return null;
        }
        return StringUtil.toString(this._raw, i + 1, (i2 - i) - 1, str);
    }

    public boolean hasQuery() {
        return this._fragment > this._query;
    }

    public String getFragment() {
        int i = this._fragment;
        int i2 = this._end;
        if (i == i2) {
            return null;
        }
        return toUtf8String(i + 1, (i2 - i) - 1);
    }

    public void decodeQueryTo(MultiMap multiMap) {
        if (this._query != this._fragment) {
            this._utf8b.reset();
            byte[] bArr = this._raw;
            int i = this._query;
            UrlEncoded.decodeUtf8To(bArr, i + 1, (this._fragment - i) - 1, multiMap, this._utf8b);
        }
    }

    public void decodeQueryTo(MultiMap multiMap, String str) throws UnsupportedEncodingException {
        if (this._query != this._fragment) {
            if (str == null || StringUtil.isUTF8(str)) {
                byte[] bArr = this._raw;
                int i = this._query;
                UrlEncoded.decodeUtf8To(bArr, i + 1, (this._fragment - i) - 1, multiMap);
                return;
            }
            byte[] bArr2 = this._raw;
            int i2 = this._query;
            UrlEncoded.decodeTo(StringUtil.toString(bArr2, i2 + 1, (this._fragment - i2) - 1, str), multiMap, str);
        }
    }

    public void clear() {
        this._end = 0;
        this._fragment = 0;
        this._query = 0;
        this._param = 0;
        this._path = 0;
        this._port = 0;
        this._host = 0;
        this._authority = 0;
        this._scheme = 0;
        this._raw = __empty;
        this._rawString = "";
        this._encoded = false;
    }

    public String toString() {
        if (this._rawString == null) {
            int i = this._scheme;
            this._rawString = toUtf8String(i, this._end - i);
        }
        return this._rawString;
    }

    public void writeTo(Utf8StringBuilder utf8StringBuilder) {
        byte[] bArr = this._raw;
        int i = this._scheme;
        utf8StringBuilder.append(bArr, i, this._end - i);
    }
}
