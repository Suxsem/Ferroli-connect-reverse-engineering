package org.eclipse.jetty.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import kotlin.UByte;
import kotlin.text.Typography;
import org.eclipse.jetty.http.HttpTokens;
import org.eclipse.jetty.util.Utf8Appendable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public class UrlEncoded extends MultiMap implements Cloneable {
    public static final String ENCODING = System.getProperty("org.eclipse.jetty.util.UrlEncoding.charset", "UTF-8");
    private static final Logger LOG = Log.getLogger((Class<?>) UrlEncoded.class);

    public UrlEncoded(UrlEncoded urlEncoded) {
        super(urlEncoded);
    }

    public UrlEncoded() {
        super(6);
    }

    public UrlEncoded(String str) {
        super(6);
        decode(str, ENCODING);
    }

    public UrlEncoded(String str, String str2) {
        super(6);
        decode(str, str2);
    }

    public void decode(String str) {
        decodeTo(str, this, ENCODING, -1);
    }

    public void decode(String str, String str2) {
        decodeTo(str, this, str2, -1);
    }

    public String encode() {
        return encode(ENCODING, false);
    }

    public String encode(String str) {
        return encode(str, false);
    }

    public synchronized String encode(String str, boolean z) {
        return encode(this, str, z);
    }

    public static String encode(MultiMap multiMap, String str, boolean z) {
        if (str == null) {
            str = ENCODING;
        }
        StringBuilder sb = new StringBuilder(128);
        Iterator it = multiMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String obj = entry.getKey().toString();
            Object value = entry.getValue();
            int size = LazyList.size(value);
            if (size == 0) {
                sb.append(encodeString(obj, str));
                if (z) {
                    sb.append('=');
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (i > 0) {
                        sb.append(Typography.amp);
                    }
                    Object obj2 = LazyList.get(value, i);
                    sb.append(encodeString(obj, str));
                    if (obj2 != null) {
                        String obj3 = obj2.toString();
                        if (obj3.length() > 0) {
                            sb.append('=');
                            sb.append(encodeString(obj3, str));
                        } else if (z) {
                            sb.append('=');
                        }
                    } else if (z) {
                        sb.append('=');
                    }
                }
            }
            if (it.hasNext()) {
                sb.append(Typography.amp);
            }
        }
        return sb.toString();
    }

    public static void decodeTo(String str, MultiMap multiMap, String str2) {
        decodeTo(str, multiMap, str2, -1);
    }

    public static void decodeTo(String str, MultiMap multiMap, String str2, int i) {
        String str3;
        if (str2 == null) {
            str2 = ENCODING;
        }
        synchronized (multiMap) {
            String str4 = null;
            int i2 = -1;
            boolean z = false;
            for (int i3 = 0; i3 < str.length(); i3++) {
                char charAt = str.charAt(i3);
                if (charAt != '%') {
                    if (charAt == '&') {
                        int i4 = (i3 - i2) - 1;
                        String decodeString = i4 == 0 ? "" : z ? decodeString(str, i2 + 1, i4, str2) : str.substring(i2 + 1, i3);
                        if (str4 != null) {
                            multiMap.add(str4, decodeString);
                        } else if (decodeString != null && decodeString.length() > 0) {
                            multiMap.add(decodeString, "");
                        }
                        if (i > 0) {
                            if (multiMap.size() > i) {
                                throw new IllegalStateException("Form too many keys");
                            }
                        }
                        i2 = i3;
                        str4 = null;
                    } else if (charAt != '+') {
                        if (charAt == '=') {
                            if (str4 == null) {
                                if (z) {
                                    str4 = decodeString(str, i2 + 1, (i3 - i2) - 1, str2);
                                } else {
                                    str4 = str.substring(i2 + 1, i3);
                                }
                                i2 = i3;
                            }
                        }
                    }
                    z = false;
                }
                z = true;
            }
            if (str4 != null) {
                int length = (str.length() - i2) - 1;
                if (length == 0) {
                    str3 = "";
                } else {
                    str3 = z ? decodeString(str, i2 + 1, length, str2) : str.substring(i2 + 1);
                }
                multiMap.add(str4, str3);
            } else if (i2 < str.length()) {
                String decodeString2 = z ? decodeString(str, i2 + 1, (str.length() - i2) - 1, str2) : str.substring(i2 + 1);
                if (decodeString2 != null && decodeString2.length() > 0) {
                    multiMap.add(decodeString2, "");
                }
            }
        }
    }

    public static void decodeUtf8To(byte[] bArr, int i, int i2, MultiMap multiMap) {
        decodeUtf8To(bArr, i, i2, multiMap, new Utf8StringBuilder());
    }

    public static void decodeUtf8To(byte[] bArr, int i, int i2, MultiMap multiMap, Utf8StringBuilder utf8StringBuilder) {
        Utf8Appendable.NotUtf8Exception e;
        int i3;
        synchronized (multiMap) {
            int i4 = i2 + i;
            String str = null;
            while (i < i4) {
                try {
                    byte b = bArr[i];
                    char c = (char) (b & UByte.MAX_VALUE);
                    if (c == '%') {
                        if (i + 2 < i4) {
                            int i5 = i + 1;
                            if (117 != bArr[i5]) {
                                i3 = i5 + 1;
                                utf8StringBuilder.append((byte) ((TypeUtil.convertHexDigit(bArr[i5]) << 4) + TypeUtil.convertHexDigit(bArr[i3])));
                            } else if (i5 + 4 < i4) {
                                try {
                                    i3 = i5 + 1;
                                } catch (Utf8Appendable.NotUtf8Exception e2) {
                                    e = e2;
                                    i = i5;
                                    LOG.warn(e.toString(), new Object[0]);
                                    LOG.debug(e);
                                    i++;
                                }
                                try {
                                    int i6 = i3 + 1;
                                    int i7 = i6 + 1;
                                    i3 = i7 + 1;
                                    utf8StringBuilder.getStringBuilder().append(Character.toChars((TypeUtil.convertHexDigit(bArr[i3]) << MqttWireMessage.MESSAGE_TYPE_PINGREQ) + (TypeUtil.convertHexDigit(bArr[i6]) << 8) + (TypeUtil.convertHexDigit(bArr[i7]) << 4) + TypeUtil.convertHexDigit(bArr[i3])));
                                } catch (Utf8Appendable.NotUtf8Exception e3) {
                                    int i8 = i3;
                                    e = e3;
                                    i = i8;
                                }
                            } else {
                                utf8StringBuilder.getStringBuilder().append(Utf8Appendable.REPLACEMENT);
                            }
                            i = i3;
                        } else {
                            utf8StringBuilder.getStringBuilder().append(Utf8Appendable.REPLACEMENT);
                        }
                        i = i4;
                    } else if (c == '&') {
                        String utf8StringBuilder2 = utf8StringBuilder.length() == 0 ? "" : utf8StringBuilder.toString();
                        utf8StringBuilder.reset();
                        if (str != null) {
                            multiMap.add(str, utf8StringBuilder2);
                        } else if (utf8StringBuilder2 != null && utf8StringBuilder2.length() > 0) {
                            multiMap.add(utf8StringBuilder2, "");
                        }
                        str = null;
                    } else if (c == '+') {
                        utf8StringBuilder.append(HttpTokens.SPACE);
                    } else if (c != '=') {
                        try {
                            utf8StringBuilder.append(b);
                        } catch (Utf8Appendable.NotUtf8Exception e4) {
                            e = e4;
                            LOG.warn(e.toString(), new Object[0]);
                            LOG.debug(e);
                            i++;
                        }
                    } else if (str != null) {
                        utf8StringBuilder.append(b);
                    } else {
                        str = utf8StringBuilder.toString();
                        utf8StringBuilder.reset();
                    }
                    i++;
                } finally {
                }
            }
            if (str != null) {
                String replacedString = utf8StringBuilder.length() == 0 ? "" : utf8StringBuilder.toReplacedString();
                utf8StringBuilder.reset();
                multiMap.add(str, replacedString);
            } else if (utf8StringBuilder.length() > 0) {
                multiMap.add(utf8StringBuilder.toReplacedString(), "");
            }
        }
    }

    public static void decode88591To(InputStream inputStream, MultiMap multiMap, int i, int i2) throws IOException {
        int read;
        int read2;
        int read3;
        synchronized (multiMap) {
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            int i3 = 0;
            while (true) {
                int read4 = inputStream.read();
                if (read4 >= 0) {
                    char c = (char) read4;
                    if (c == '%') {
                        int read5 = inputStream.read();
                        if (117 == read5) {
                            int read6 = inputStream.read();
                            if (read6 >= 0 && (read2 = inputStream.read()) >= 0 && (read3 = inputStream.read()) >= 0) {
                                stringBuffer.append(Character.toChars((TypeUtil.convertHexDigit(read5) << 12) + (TypeUtil.convertHexDigit(read6) << 8) + (TypeUtil.convertHexDigit(read2) << 4) + TypeUtil.convertHexDigit(read3)));
                            }
                        } else if (read5 >= 0 && (read = inputStream.read()) >= 0) {
                            stringBuffer.append((char) ((TypeUtil.convertHexDigit(read5) << 4) + TypeUtil.convertHexDigit(read)));
                        }
                    } else if (c == '&') {
                        String stringBuffer2 = stringBuffer.length() == 0 ? "" : stringBuffer.toString();
                        stringBuffer.setLength(0);
                        if (str != null) {
                            multiMap.add(str, stringBuffer2);
                        } else if (stringBuffer2 != null && stringBuffer2.length() > 0) {
                            multiMap.add(stringBuffer2, "");
                        }
                        if (i2 > 0) {
                            if (multiMap.size() > i2) {
                                throw new IllegalStateException("Form too many keys");
                            }
                        }
                        str = null;
                    } else if (c == '+') {
                        stringBuffer.append(' ');
                    } else if (c != '=') {
                        stringBuffer.append(c);
                    } else if (str != null) {
                        stringBuffer.append(c);
                    } else {
                        str = stringBuffer.toString();
                        stringBuffer.setLength(0);
                    }
                    if (i >= 0) {
                        i3++;
                        if (i3 > i) {
                            throw new IllegalStateException("Form too large");
                        }
                    }
                } else if (str != null) {
                    String stringBuffer3 = stringBuffer.length() == 0 ? "" : stringBuffer.toString();
                    stringBuffer.setLength(0);
                    multiMap.add(str, stringBuffer3);
                } else if (stringBuffer.length() > 0) {
                    multiMap.add(stringBuffer.toString(), "");
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x000a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void decodeUtf8To(java.io.InputStream r10, org.eclipse.jetty.util.MultiMap r11, int r12, int r13) throws java.io.IOException {
        /*
            monitor-enter(r11)
            org.eclipse.jetty.util.Utf8StringBuilder r0 = new org.eclipse.jetty.util.Utf8StringBuilder     // Catch:{ all -> 0x0119 }
            r0.<init>()     // Catch:{ all -> 0x0119 }
            r1 = 0
            r2 = 0
            r3 = r2
            r4 = 0
        L_0x000a:
            int r5 = r10.read()     // Catch:{ all -> 0x0119 }
            if (r5 < 0) goto L_0x00f2
            char r6 = (char) r5
            r7 = 37
            if (r6 == r7) goto L_0x007b
            r7 = 38
            if (r6 == r7) goto L_0x0042
            r7 = 43
            if (r6 == r7) goto L_0x003b
            r7 = 61
            if (r6 == r7) goto L_0x002a
            byte r5 = (byte) r5
            r0.append(r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            goto L_0x00e2
        L_0x0027:
            r5 = move-exception
            goto L_0x00d2
        L_0x002a:
            if (r3 == 0) goto L_0x0032
            byte r5 = (byte) r5     // Catch:{ NotUtf8Exception -> 0x0027 }
            r0.append(r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            goto L_0x00e2
        L_0x0032:
            java.lang.String r3 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x0027 }
            r0.reset()     // Catch:{ NotUtf8Exception -> 0x0027 }
            goto L_0x00e2
        L_0x003b:
            r5 = 32
            r0.append(r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            goto L_0x00e2
        L_0x0042:
            int r5 = r0.length()     // Catch:{ NotUtf8Exception -> 0x0027 }
            if (r5 != 0) goto L_0x004b
            java.lang.String r5 = ""
            goto L_0x004f
        L_0x004b:
            java.lang.String r5 = r0.toString()     // Catch:{ NotUtf8Exception -> 0x0027 }
        L_0x004f:
            r0.reset()     // Catch:{ NotUtf8Exception -> 0x0027 }
            if (r3 == 0) goto L_0x0058
            r11.add(r3, r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            goto L_0x0065
        L_0x0058:
            if (r5 == 0) goto L_0x0065
            int r6 = r5.length()     // Catch:{ NotUtf8Exception -> 0x0027 }
            if (r6 <= 0) goto L_0x0065
            java.lang.String r6 = ""
            r11.add(r5, r6)     // Catch:{ NotUtf8Exception -> 0x0027 }
        L_0x0065:
            if (r13 <= 0) goto L_0x0079
            int r3 = r11.size()     // Catch:{ NotUtf8Exception -> 0x0076 }
            if (r3 > r13) goto L_0x006e
            goto L_0x0079
        L_0x006e:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ NotUtf8Exception -> 0x0076 }
            java.lang.String r5 = "Form too many keys"
            r3.<init>(r5)     // Catch:{ NotUtf8Exception -> 0x0076 }
            throw r3     // Catch:{ NotUtf8Exception -> 0x0076 }
        L_0x0076:
            r5 = move-exception
            r3 = r2
            goto L_0x00d2
        L_0x0079:
            r3 = r2
            goto L_0x00e2
        L_0x007b:
            int r5 = r10.read()     // Catch:{ NotUtf8Exception -> 0x0027 }
            r6 = 117(0x75, float:1.64E-43)
            if (r6 != r5) goto L_0x00ba
            int r6 = r10.read()     // Catch:{ NotUtf8Exception -> 0x0027 }
            if (r6 < 0) goto L_0x00e2
            int r7 = r10.read()     // Catch:{ NotUtf8Exception -> 0x0027 }
            if (r7 < 0) goto L_0x00e2
            int r8 = r10.read()     // Catch:{ NotUtf8Exception -> 0x0027 }
            if (r8 < 0) goto L_0x00e2
            java.lang.StringBuilder r9 = r0.getStringBuilder()     // Catch:{ NotUtf8Exception -> 0x0027 }
            int r5 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((int) r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            int r5 = r5 << 12
            int r6 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((int) r6)     // Catch:{ NotUtf8Exception -> 0x0027 }
            int r6 = r6 << 8
            int r5 = r5 + r6
            int r6 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((int) r7)     // Catch:{ NotUtf8Exception -> 0x0027 }
            int r6 = r6 << 4
            int r5 = r5 + r6
            int r6 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((int) r8)     // Catch:{ NotUtf8Exception -> 0x0027 }
            int r5 = r5 + r6
            char[] r5 = java.lang.Character.toChars(r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            r9.append(r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            goto L_0x00e2
        L_0x00ba:
            if (r5 < 0) goto L_0x00e2
            int r6 = r10.read()     // Catch:{ NotUtf8Exception -> 0x0027 }
            if (r6 < 0) goto L_0x00e2
            int r5 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((int) r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            int r5 = r5 << 4
            int r6 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((int) r6)     // Catch:{ NotUtf8Exception -> 0x0027 }
            int r5 = r5 + r6
            byte r5 = (byte) r5     // Catch:{ NotUtf8Exception -> 0x0027 }
            r0.append(r5)     // Catch:{ NotUtf8Exception -> 0x0027 }
            goto L_0x00e2
        L_0x00d2:
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0119 }
            java.lang.String r7 = r5.toString()     // Catch:{ all -> 0x0119 }
            java.lang.Object[] r8 = new java.lang.Object[r1]     // Catch:{ all -> 0x0119 }
            r6.warn((java.lang.String) r7, (java.lang.Object[]) r8)     // Catch:{ all -> 0x0119 }
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0119 }
            r6.debug(r5)     // Catch:{ all -> 0x0119 }
        L_0x00e2:
            if (r12 < 0) goto L_0x000a
            int r4 = r4 + 1
            if (r4 > r12) goto L_0x00ea
            goto L_0x000a
        L_0x00ea:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0119 }
            java.lang.String r12 = "Form too large"
            r10.<init>(r12)     // Catch:{ all -> 0x0119 }
            throw r10     // Catch:{ all -> 0x0119 }
        L_0x00f2:
            if (r3 == 0) goto L_0x0108
            int r10 = r0.length()     // Catch:{ all -> 0x0119 }
            if (r10 != 0) goto L_0x00fd
            java.lang.String r10 = ""
            goto L_0x0101
        L_0x00fd:
            java.lang.String r10 = r0.toString()     // Catch:{ all -> 0x0119 }
        L_0x0101:
            r0.reset()     // Catch:{ all -> 0x0119 }
            r11.add(r3, r10)     // Catch:{ all -> 0x0119 }
            goto L_0x0117
        L_0x0108:
            int r10 = r0.length()     // Catch:{ all -> 0x0119 }
            if (r10 <= 0) goto L_0x0117
            java.lang.String r10 = r0.toString()     // Catch:{ all -> 0x0119 }
            java.lang.String r12 = ""
            r11.add(r10, r12)     // Catch:{ all -> 0x0119 }
        L_0x0117:
            monitor-exit(r11)     // Catch:{ all -> 0x0119 }
            return
        L_0x0119:
            r10 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0119 }
            goto L_0x011d
        L_0x011c:
            throw r10
        L_0x011d:
            goto L_0x011c
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decodeUtf8To(java.io.InputStream, org.eclipse.jetty.util.MultiMap, int, int):void");
    }

    public static void decodeUtf16To(InputStream inputStream, MultiMap multiMap, int i, int i2) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StringUtil.__UTF16);
        StringWriter stringWriter = new StringWriter(8192);
        C2439IO.copy((Reader) inputStreamReader, (Writer) stringWriter, (long) i);
        decodeTo(stringWriter.getBuffer().toString(), multiMap, StringUtil.__UTF16, i2);
    }

    public static void decodeTo(InputStream inputStream, MultiMap multiMap, String str, int i, int i2) throws IOException {
        String str2;
        int read;
        int read2;
        int read3;
        String str3;
        if (str == null) {
            str = ENCODING;
        }
        if ("UTF-8".equalsIgnoreCase(str)) {
            decodeUtf8To(inputStream, multiMap, i, i2);
        } else if (StringUtil.__ISO_8859_1.equals(str)) {
            decode88591To(inputStream, multiMap, i, i2);
        } else if (StringUtil.__UTF16.equalsIgnoreCase(str)) {
            decodeUtf16To(inputStream, multiMap, i, i2);
        } else {
            synchronized (multiMap) {
                ByteArrayOutputStream2 byteArrayOutputStream2 = new ByteArrayOutputStream2();
                String str4 = null;
                int i3 = 0;
                while (true) {
                    int read4 = inputStream.read();
                    if (read4 > 0) {
                        char c = (char) read4;
                        if (c == '%') {
                            int read5 = inputStream.read();
                            if (117 == read5) {
                                int read6 = inputStream.read();
                                if (read6 >= 0 && (read2 = inputStream.read()) >= 0 && (read3 = inputStream.read()) >= 0) {
                                    byteArrayOutputStream2.write(new String(Character.toChars((TypeUtil.convertHexDigit(read5) << 12) + (TypeUtil.convertHexDigit(read6) << 8) + (TypeUtil.convertHexDigit(read2) << 4) + TypeUtil.convertHexDigit(read3))).getBytes(str));
                                }
                            } else if (read5 >= 0 && (read = inputStream.read()) >= 0) {
                                byteArrayOutputStream2.write((TypeUtil.convertHexDigit(read5) << 4) + TypeUtil.convertHexDigit(read));
                            }
                        } else if (c == '&') {
                            if (byteArrayOutputStream2.size() == 0) {
                                str3 = "";
                            } else {
                                str3 = byteArrayOutputStream2.toString(str);
                            }
                            byteArrayOutputStream2.setCount(0);
                            if (str4 != null) {
                                multiMap.add(str4, str3);
                            } else if (str3 != null && str3.length() > 0) {
                                multiMap.add(str3, "");
                            }
                            if (i2 > 0) {
                                if (multiMap.size() > i2) {
                                    throw new IllegalStateException("Form too many keys");
                                }
                            }
                            str4 = null;
                        } else if (c == '+') {
                            byteArrayOutputStream2.write(32);
                        } else if (c != '=') {
                            byteArrayOutputStream2.write(read4);
                        } else if (str4 != null) {
                            byteArrayOutputStream2.write(read4);
                        } else {
                            if (byteArrayOutputStream2.size() == 0) {
                                str4 = "";
                            } else {
                                str4 = byteArrayOutputStream2.toString(str);
                            }
                            byteArrayOutputStream2.setCount(0);
                        }
                        i3++;
                        if (i >= 0) {
                            if (i3 > i) {
                                throw new IllegalStateException("Form too large");
                            }
                        }
                    } else {
                        int size = byteArrayOutputStream2.size();
                        if (str4 != null) {
                            if (size == 0) {
                                str2 = "";
                            } else {
                                str2 = byteArrayOutputStream2.toString(str);
                            }
                            byteArrayOutputStream2.setCount(0);
                            multiMap.add(str4, str2);
                        } else if (size > 0) {
                            multiMap.add(byteArrayOutputStream2.toString(str), "");
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:153:0x00cc A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00cf A[Catch:{ UnsupportedEncodingException -> 0x010c }, LOOP:1: B:21:0x0053->B:59:0x00cf, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String decodeString(java.lang.String r17, int r18, int r19, java.lang.String r20) {
        /*
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = 4
            r6 = 117(0x75, float:1.64E-43)
            r0 = 0
            r7 = 32
            r8 = 37
            r9 = 43
            r10 = 255(0xff, float:3.57E-43)
            r11 = 16
            r13 = 0
            if (r4 == 0) goto L_0x012b
            boolean r14 = org.eclipse.jetty.util.StringUtil.isUTF8(r20)
            if (r14 == 0) goto L_0x0021
            goto L_0x012b
        L_0x0021:
            r14 = r0
            r0 = 0
        L_0x0023:
            if (r0 >= r3) goto L_0x010e
            int r15 = r2 + r0
            char r12 = r1.charAt(r15)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            if (r12 < 0) goto L_0x00f1
            if (r12 <= r10) goto L_0x0031
            goto L_0x00f1
        L_0x0031:
            if (r12 != r9) goto L_0x0042
            if (r14 != 0) goto L_0x003d
            java.lang.StringBuffer r14 = new java.lang.StringBuffer     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r14.<init>(r3)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r14.append(r1, r2, r15)     // Catch:{ UnsupportedEncodingException -> 0x010c }
        L_0x003d:
            r14.append(r7)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            goto L_0x0102
        L_0x0042:
            if (r12 != r8) goto L_0x00eb
            if (r14 != 0) goto L_0x004e
            java.lang.StringBuffer r14 = new java.lang.StringBuffer     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r14.<init>(r3)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r14.append(r1, r2, r15)     // Catch:{ UnsupportedEncodingException -> 0x010c }
        L_0x004e:
            byte[] r15 = new byte[r3]     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r16 = r0
            r7 = 0
        L_0x0053:
            if (r12 < 0) goto L_0x00de
            if (r12 > r10) goto L_0x00de
            if (r12 != r8) goto L_0x00ba
            int r0 = r16 + 2
            r12 = 63
            if (r0 >= r3) goto L_0x00b4
            int r0 = r2 + r16
            int r8 = r0 + 1
            char r10 = r1.charAt(r8)     // Catch:{ NumberFormatException -> 0x00a6 }
            if (r6 != r10) goto L_0x0096
            int r8 = r16 + 6
            if (r8 >= r3) goto L_0x008b
            int r0 = r0 + 2
            java.lang.String r10 = new java.lang.String     // Catch:{ NumberFormatException -> 0x0089 }
            int r0 = org.eclipse.jetty.util.TypeUtil.parseInt((java.lang.String) r1, (int) r0, (int) r5, (int) r11)     // Catch:{ NumberFormatException -> 0x0089 }
            char[] r0 = java.lang.Character.toChars(r0)     // Catch:{ NumberFormatException -> 0x0089 }
            r10.<init>(r0)     // Catch:{ NumberFormatException -> 0x0089 }
            byte[] r0 = r10.getBytes(r4)     // Catch:{ NumberFormatException -> 0x0089 }
            int r10 = r0.length     // Catch:{ NumberFormatException -> 0x0089 }
            java.lang.System.arraycopy(r0, r13, r15, r7, r10)     // Catch:{ NumberFormatException -> 0x0089 }
            int r0 = r0.length     // Catch:{ NumberFormatException -> 0x0089 }
            int r7 = r7 + r0
            r16 = r8
            goto L_0x00a2
        L_0x0089:
            r0 = move-exception
            goto L_0x00a9
        L_0x008b:
            int r8 = r7 + 1
            r15[r7] = r12     // Catch:{ NumberFormatException -> 0x0093 }
            r16 = r3
            r7 = r8
            goto L_0x00a2
        L_0x0093:
            r0 = move-exception
            r7 = r8
            goto L_0x00a7
        L_0x0096:
            int r16 = r16 + 3
            r10 = 2
            int r0 = org.eclipse.jetty.util.TypeUtil.parseInt((java.lang.String) r1, (int) r8, (int) r10, (int) r11)     // Catch:{ NumberFormatException -> 0x00a6 }
            byte r0 = (byte) r0     // Catch:{ NumberFormatException -> 0x00a6 }
            r15[r7] = r0     // Catch:{ NumberFormatException -> 0x00a6 }
            int r7 = r7 + 1
        L_0x00a2:
            r0 = r7
            r7 = r16
            goto L_0x00ca
        L_0x00a6:
            r0 = move-exception
        L_0x00a7:
            r8 = r16
        L_0x00a9:
            org.eclipse.jetty.util.log.Logger r10 = LOG     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r10.ignore(r0)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            int r0 = r7 + 1
            r15[r7] = r12     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r7 = r8
            goto L_0x00ca
        L_0x00b4:
            int r0 = r7 + 1
            r15[r7] = r12     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r7 = r3
            goto L_0x00ca
        L_0x00ba:
            if (r12 != r9) goto L_0x00c3
            int r0 = r7 + 1
            r8 = 32
            r15[r7] = r8     // Catch:{ UnsupportedEncodingException -> 0x010c }
            goto L_0x00c8
        L_0x00c3:
            int r0 = r7 + 1
            byte r8 = (byte) r12     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r15[r7] = r8     // Catch:{ UnsupportedEncodingException -> 0x010c }
        L_0x00c8:
            int r7 = r16 + 1
        L_0x00ca:
            if (r7 < r3) goto L_0x00cf
            r16 = r7
            goto L_0x00df
        L_0x00cf:
            int r8 = r2 + r7
            char r12 = r1.charAt(r8)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r16 = r7
            r8 = 37
            r10 = 255(0xff, float:3.57E-43)
            r7 = r0
            goto L_0x0053
        L_0x00de:
            r0 = r7
        L_0x00df:
            int r7 = r16 + -1
            java.lang.String r8 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r8.<init>(r15, r13, r0, r4)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r14.append(r8)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r0 = r7
            goto L_0x0102
        L_0x00eb:
            if (r14 == 0) goto L_0x0102
            r14.append(r12)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            goto L_0x0102
        L_0x00f1:
            if (r14 != 0) goto L_0x00ff
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r7.<init>(r3)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            int r15 = r15 + 1
            r7.append(r1, r2, r15)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            r14 = r7
            goto L_0x0102
        L_0x00ff:
            r14.append(r12)     // Catch:{ UnsupportedEncodingException -> 0x010c }
        L_0x0102:
            int r0 = r0 + 1
            r7 = 32
            r8 = 37
            r10 = 255(0xff, float:3.57E-43)
            goto L_0x0023
        L_0x010c:
            r0 = move-exception
            goto L_0x0125
        L_0x010e:
            if (r14 != 0) goto L_0x0120
            if (r2 != 0) goto L_0x0119
            int r0 = r17.length()     // Catch:{ UnsupportedEncodingException -> 0x010c }
            if (r0 != r3) goto L_0x0119
            return r1
        L_0x0119:
            int r0 = r2 + r3
            java.lang.String r0 = r1.substring(r2, r0)     // Catch:{ UnsupportedEncodingException -> 0x010c }
            return r0
        L_0x0120:
            java.lang.String r0 = r14.toString()     // Catch:{ UnsupportedEncodingException -> 0x010c }
            return r0
        L_0x0125:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            r1.<init>(r0)
            throw r1
        L_0x012b:
            r4 = 0
        L_0x012c:
            if (r4 >= r3) goto L_0x0217
            int r7 = r2 + r4
            char r8 = r1.charAt(r7)
            if (r8 < 0) goto L_0x01f4
            r10 = 255(0xff, float:3.57E-43)
            if (r8 <= r10) goto L_0x013c
            goto L_0x01f4
        L_0x013c:
            if (r8 != r9) goto L_0x015a
            if (r0 != 0) goto L_0x014c
            org.eclipse.jetty.util.Utf8StringBuffer r0 = new org.eclipse.jetty.util.Utf8StringBuffer
            r0.<init>(r3)
            java.lang.StringBuffer r8 = r0.getStringBuffer()
            r8.append(r1, r2, r7)
        L_0x014c:
            java.lang.StringBuffer r7 = r0.getStringBuffer()
            r12 = 32
            r7.append(r12)
            r10 = 2
            r14 = 37
            goto L_0x0211
        L_0x015a:
            r12 = 32
            r14 = 37
            if (r8 != r14) goto L_0x01e9
            if (r0 != 0) goto L_0x016e
            org.eclipse.jetty.util.Utf8StringBuffer r0 = new org.eclipse.jetty.util.Utf8StringBuffer
            r0.<init>(r3)
            java.lang.StringBuffer r8 = r0.getStringBuffer()
            r8.append(r1, r2, r7)
        L_0x016e:
            r8 = r0
            int r15 = r4 + 2
            r9 = 65533(0xfffd, float:9.1831E-41)
            if (r15 >= r3) goto L_0x01de
            int r0 = r7 + 1
            char r10 = r1.charAt(r0)     // Catch:{ NotUtf8Exception -> 0x01cb, NumberFormatException -> 0x01bc }
            if (r6 != r10) goto L_0x01aa
            int r4 = r4 + 5
            if (r4 >= r3) goto L_0x019a
            int r7 = r7 + 2
            java.lang.String r0 = new java.lang.String     // Catch:{ NotUtf8Exception -> 0x01cb, NumberFormatException -> 0x01bc }
            int r7 = org.eclipse.jetty.util.TypeUtil.parseInt((java.lang.String) r1, (int) r7, (int) r5, (int) r11)     // Catch:{ NotUtf8Exception -> 0x01cb, NumberFormatException -> 0x01bc }
            char[] r7 = java.lang.Character.toChars(r7)     // Catch:{ NotUtf8Exception -> 0x01cb, NumberFormatException -> 0x01bc }
            r0.<init>(r7)     // Catch:{ NotUtf8Exception -> 0x01cb, NumberFormatException -> 0x01bc }
            java.lang.StringBuffer r7 = r8.getStringBuffer()     // Catch:{ NotUtf8Exception -> 0x01cb, NumberFormatException -> 0x01bc }
            r7.append(r0)     // Catch:{ NotUtf8Exception -> 0x01cb, NumberFormatException -> 0x01bc }
            r15 = r4
            goto L_0x01a2
        L_0x019a:
            java.lang.StringBuffer r0 = r8.getStringBuffer()     // Catch:{ NotUtf8Exception -> 0x01a7, NumberFormatException -> 0x01a4 }
            r0.append(r9)     // Catch:{ NotUtf8Exception -> 0x01a7, NumberFormatException -> 0x01a4 }
            r15 = r3
        L_0x01a2:
            r10 = 2
            goto L_0x01b3
        L_0x01a4:
            r0 = move-exception
            r4 = r3
            goto L_0x01bd
        L_0x01a7:
            r0 = move-exception
            r4 = r3
            goto L_0x01cc
        L_0x01aa:
            r10 = 2
            int r0 = org.eclipse.jetty.util.TypeUtil.parseInt((java.lang.String) r1, (int) r0, (int) r10, (int) r11)     // Catch:{ NotUtf8Exception -> 0x01b9, NumberFormatException -> 0x01b6 }
            byte r0 = (byte) r0     // Catch:{ NotUtf8Exception -> 0x01b9, NumberFormatException -> 0x01b6 }
            r8.append(r0)     // Catch:{ NotUtf8Exception -> 0x01b9, NumberFormatException -> 0x01b6 }
        L_0x01b3:
            r0 = r8
            r4 = r15
            goto L_0x0211
        L_0x01b6:
            r0 = move-exception
            r4 = r15
            goto L_0x01be
        L_0x01b9:
            r0 = move-exception
            r4 = r15
            goto L_0x01cd
        L_0x01bc:
            r0 = move-exception
        L_0x01bd:
            r10 = 2
        L_0x01be:
            org.eclipse.jetty.util.log.Logger r7 = LOG
            r7.debug(r0)
            java.lang.StringBuffer r0 = r8.getStringBuffer()
            r0.append(r9)
            goto L_0x01e7
        L_0x01cb:
            r0 = move-exception
        L_0x01cc:
            r10 = 2
        L_0x01cd:
            org.eclipse.jetty.util.log.Logger r7 = LOG
            java.lang.String r9 = r0.toString()
            java.lang.Object[] r15 = new java.lang.Object[r13]
            r7.warn((java.lang.String) r9, (java.lang.Object[]) r15)
            org.eclipse.jetty.util.log.Logger r7 = LOG
            r7.debug(r0)
            goto L_0x01e7
        L_0x01de:
            r10 = 2
            java.lang.StringBuffer r0 = r8.getStringBuffer()
            r0.append(r9)
            r4 = r3
        L_0x01e7:
            r0 = r8
            goto L_0x0211
        L_0x01e9:
            r10 = 2
            if (r0 == 0) goto L_0x0211
            java.lang.StringBuffer r7 = r0.getStringBuffer()
            r7.append(r8)
            goto L_0x0211
        L_0x01f4:
            r10 = 2
            r12 = 32
            r14 = 37
            if (r0 != 0) goto L_0x020a
            org.eclipse.jetty.util.Utf8StringBuffer r0 = new org.eclipse.jetty.util.Utf8StringBuffer
            r0.<init>(r3)
            java.lang.StringBuffer r8 = r0.getStringBuffer()
            int r7 = r7 + 1
            r8.append(r1, r2, r7)
            goto L_0x0211
        L_0x020a:
            java.lang.StringBuffer r7 = r0.getStringBuffer()
            r7.append(r8)
        L_0x0211:
            int r4 = r4 + 1
            r9 = 43
            goto L_0x012c
        L_0x0217:
            if (r0 != 0) goto L_0x0229
            if (r2 != 0) goto L_0x0222
            int r0 = r17.length()
            if (r0 != r3) goto L_0x0222
            return r1
        L_0x0222:
            int r0 = r2 + r3
            java.lang.String r0 = r1.substring(r2, r0)
            return r0
        L_0x0229:
            java.lang.String r0 = r0.toReplacedString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.UrlEncoded.decodeString(java.lang.String, int, int, java.lang.String):java.lang.String");
    }

    public static String encodeString(String str) {
        return encodeString(str, ENCODING);
    }

    public static String encodeString(String str, String str2) {
        byte[] bArr;
        int i;
        if (str2 == null) {
            str2 = ENCODING;
        }
        try {
            bArr = str.getBytes(str2);
        } catch (UnsupportedEncodingException unused) {
            bArr = str.getBytes();
        }
        byte[] bArr2 = new byte[(bArr.length * 3)];
        boolean z = true;
        int i2 = 0;
        for (byte b : bArr) {
            if (b == 32) {
                bArr2[i2] = 43;
                i2++;
            } else if ((b < 97 || b > 122) && ((b < 65 || b > 90) && (b < 48 || b > 57))) {
                int i3 = i2 + 1;
                bArr2[i2] = 37;
                byte b2 = (byte) ((b & 240) >> 4);
                if (b2 >= 10) {
                    i = i3 + 1;
                    bArr2[i3] = (byte) ((b2 + 65) - 10);
                } else {
                    i = i3 + 1;
                    bArr2[i3] = (byte) (b2 + 48);
                }
                byte b3 = (byte) (b & 15);
                if (b3 >= 10) {
                    i2 = i + 1;
                    bArr2[i] = (byte) ((b3 + 65) - 10);
                } else {
                    i2 = i + 1;
                    bArr2[i] = (byte) (b3 + 48);
                }
            } else {
                bArr2[i2] = b;
                i2++;
            }
            z = false;
        }
        if (z) {
            return str;
        }
        try {
            return new String(bArr2, 0, i2, str2);
        } catch (UnsupportedEncodingException unused2) {
            return new String(bArr2, 0, i2);
        }
    }

    public Object clone() {
        return new UrlEncoded(this);
    }
}
