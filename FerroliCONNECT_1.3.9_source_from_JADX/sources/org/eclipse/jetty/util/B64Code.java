package org.eclipse.jetty.util;

import android.support.p000v4.view.PointerIconCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class B64Code {
    static final char __pad = '=';
    static final char[] __rfc1421alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    static final byte[] __rfc1421nibbles = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            __rfc1421nibbles[i] = -1;
        }
        for (byte b = 0; b < 64; b = (byte) (b + 1)) {
            __rfc1421nibbles[(byte) __rfc1421alphabet[b]] = b;
        }
        __rfc1421nibbles[61] = 0;
    }

    public static String encode(String str) {
        try {
            return encode(str, (String) null);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }

    public static String encode(String str, String str2) throws UnsupportedEncodingException {
        byte[] bArr;
        if (str2 == null) {
            bArr = str.getBytes(StringUtil.__ISO_8859_1);
        } else {
            bArr = str.getBytes(str2);
        }
        return new String(encode(bArr));
    }

    public static char[] encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        char[] cArr = new char[(((length + 2) / 3) * 4)];
        int i = (length / 3) * 3;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = i2 + 1;
            byte b = bArr[i2];
            int i5 = i4 + 1;
            byte b2 = bArr[i4];
            int i6 = i5 + 1;
            byte b3 = bArr[i5];
            int i7 = i3 + 1;
            char[] cArr2 = __rfc1421alphabet;
            cArr[i3] = cArr2[(b >>> 2) & 63];
            int i8 = i7 + 1;
            cArr[i7] = cArr2[((b << 4) & 63) | ((b2 >>> 4) & 15)];
            int i9 = i8 + 1;
            cArr[i8] = cArr2[((b2 << 2) & 63) | ((b3 >>> 6) & 3)];
            i3 = i9 + 1;
            cArr[i9] = cArr2[b3 & 63];
            i2 = i6;
        }
        if (length != i2) {
            int i10 = length % 3;
            if (i10 == 1) {
                byte b4 = bArr[i2];
                int i11 = i3 + 1;
                char[] cArr3 = __rfc1421alphabet;
                cArr[i3] = cArr3[(b4 >>> 2) & 63];
                int i12 = i11 + 1;
                cArr[i11] = cArr3[(b4 << 4) & 63];
                cArr[i12] = __pad;
                cArr[i12 + 1] = __pad;
            } else if (i10 == 2) {
                int i13 = i2 + 1;
                byte b5 = bArr[i2];
                byte b6 = bArr[i13];
                int i14 = i3 + 1;
                char[] cArr4 = __rfc1421alphabet;
                cArr[i3] = cArr4[(b5 >>> 2) & 63];
                int i15 = i14 + 1;
                cArr[i14] = cArr4[((b5 << 4) & 63) | ((b6 >>> 4) & 15)];
                cArr[i15] = cArr4[(b6 << 2) & 63];
                cArr[i15 + 1] = __pad;
            }
        }
        return cArr;
    }

    public static char[] encode(byte[] bArr, boolean z) {
        if (bArr == null) {
            return null;
        }
        if (!z) {
            return encode(bArr);
        }
        int length = bArr.length;
        int i = ((length + 2) / 3) * 4;
        char[] cArr = new char[(i + ((i / 76) * 2) + 2)];
        int i2 = (length / 3) * 3;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < i2) {
            int i6 = i3 + 1;
            byte b = bArr[i3];
            int i7 = i6 + 1;
            byte b2 = bArr[i6];
            int i8 = i7 + 1;
            byte b3 = bArr[i7];
            int i9 = i4 + 1;
            char[] cArr2 = __rfc1421alphabet;
            cArr[i4] = cArr2[(b >>> 2) & 63];
            int i10 = i9 + 1;
            cArr[i9] = cArr2[((b << 4) & 63) | ((b2 >>> 4) & 15)];
            int i11 = i10 + 1;
            cArr[i10] = cArr2[((b2 << 2) & 63) | ((b3 >>> 6) & 3)];
            i4 = i11 + 1;
            cArr[i11] = cArr2[b3 & 63];
            i5 += 4;
            if (i5 % 76 == 0) {
                int i12 = i4 + 1;
                cArr[i4] = 13;
                i4 = i12 + 1;
                cArr[i12] = 10;
            }
            i3 = i8;
        }
        if (length != i3) {
            int i13 = length % 3;
            if (i13 == 1) {
                byte b4 = bArr[i3];
                int i14 = i4 + 1;
                char[] cArr3 = __rfc1421alphabet;
                cArr[i4] = cArr3[(b4 >>> 2) & 63];
                int i15 = i14 + 1;
                cArr[i14] = cArr3[(b4 << 4) & 63];
                int i16 = i15 + 1;
                cArr[i15] = __pad;
                i4 = i16 + 1;
                cArr[i16] = __pad;
            } else if (i13 == 2) {
                int i17 = i3 + 1;
                byte b5 = bArr[i3];
                byte b6 = bArr[i17];
                int i18 = i4 + 1;
                char[] cArr4 = __rfc1421alphabet;
                cArr[i4] = cArr4[(b5 >>> 2) & 63];
                int i19 = i18 + 1;
                cArr[i18] = cArr4[((b5 << 4) & 63) | ((b6 >>> 4) & 15)];
                int i20 = i19 + 1;
                cArr[i19] = cArr4[(b6 << 2) & 63];
                i4 = i20 + 1;
                cArr[i20] = __pad;
            }
        }
        cArr[i4] = 13;
        cArr[i4 + 1] = 10;
        return cArr;
    }

    public static String decode(String str, String str2) throws UnsupportedEncodingException {
        byte[] decode = decode(str);
        if (str2 == null) {
            return new String(decode);
        }
        return new String(decode, str2);
    }

    public static byte[] decode(char[] cArr) {
        int i;
        int i2;
        int i3;
        byte b;
        int i4;
        byte b2;
        int i5;
        if (cArr == null) {
            return null;
        }
        int length = cArr.length;
        if (length % 4 == 0) {
            int i6 = length - 1;
            while (i6 >= 0 && cArr[i6] == '=') {
                i6--;
            }
            int i7 = 0;
            if (i6 < 0) {
                return new byte[0];
            }
            int i8 = ((i6 + 1) * 3) / 4;
            byte[] bArr = new byte[i8];
            int i9 = (i8 / 3) * 3;
            int i10 = 0;
            while (i7 < i9) {
                try {
                    i2 = i10 + 1;
                } catch (IndexOutOfBoundsException unused) {
                    i2 = i10;
                    throw new IllegalArgumentException("char " + i2 + " was not B64 encoded");
                }
                try {
                    b = __rfc1421nibbles[cArr[i10]];
                    i4 = i2 + 1;
                    try {
                        b2 = __rfc1421nibbles[cArr[i2]];
                        i5 = i4 + 1;
                    } catch (IndexOutOfBoundsException unused2) {
                        i2 = i4;
                        throw new IllegalArgumentException("char " + i2 + " was not B64 encoded");
                    }
                } catch (IndexOutOfBoundsException unused3) {
                    throw new IllegalArgumentException("char " + i2 + " was not B64 encoded");
                }
                try {
                    byte b3 = __rfc1421nibbles[cArr[i4]];
                    int i11 = i5 + 1;
                    try {
                        byte b4 = __rfc1421nibbles[cArr[i5]];
                        if (b < 0 || b2 < 0 || b3 < 0 || b4 < 0) {
                            throw new IllegalArgumentException("Not B64 encoded");
                        }
                        int i12 = i7 + 1;
                        bArr[i7] = (byte) ((b << 2) | (b2 >>> 4));
                        int i13 = i12 + 1;
                        bArr[i12] = (byte) ((b2 << 4) | (b3 >>> 2));
                        bArr[i13] = (byte) ((b3 << 6) | b4);
                        i7 = i13 + 1;
                        i10 = i11;
                    } catch (IndexOutOfBoundsException unused4) {
                        i2 = i11;
                        throw new IllegalArgumentException("char " + i2 + " was not B64 encoded");
                    }
                } catch (IndexOutOfBoundsException unused5) {
                    i2 = i5;
                    throw new IllegalArgumentException("char " + i2 + " was not B64 encoded");
                }
            }
            if (i8 != i7) {
                int i14 = i8 % 3;
                if (i14 == 1) {
                    i = i10 + 1;
                    byte b5 = __rfc1421nibbles[cArr[i10]];
                    i3 = i + 1;
                    byte b6 = __rfc1421nibbles[cArr[i]];
                    if (b5 < 0 || b6 < 0) {
                        throw new IllegalArgumentException("Not B64 encoded");
                    }
                    bArr[i7] = (byte) ((b6 >>> 4) | (b5 << 2));
                } else if (i14 == 2) {
                    i = i10 + 1;
                    try {
                        byte b7 = __rfc1421nibbles[cArr[i10]];
                        i3 = i + 1;
                        try {
                            byte b8 = __rfc1421nibbles[cArr[i]];
                            i2 = i3 + 1;
                            byte b9 = __rfc1421nibbles[cArr[i3]];
                            if (b7 < 0 || b8 < 0 || b9 < 0) {
                                throw new IllegalArgumentException("Not B64 encoded");
                            }
                            bArr[i7] = (byte) ((b7 << 2) | (b8 >>> 4));
                            bArr[i7 + 1] = (byte) ((b9 >>> 2) | (b8 << 4));
                        } catch (IndexOutOfBoundsException unused6) {
                            i2 = i3;
                            throw new IllegalArgumentException("char " + i2 + " was not B64 encoded");
                        }
                    } catch (IndexOutOfBoundsException unused7) {
                        i2 = i;
                        throw new IllegalArgumentException("char " + i2 + " was not B64 encoded");
                    }
                }
            }
            return bArr;
        }
        throw new IllegalArgumentException("Input block size is not 4");
    }

    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((str.length() * 4) / 3);
        decode(str, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void decode(String str, ByteArrayOutputStream byteArrayOutputStream) {
        if (str != null) {
            if (byteArrayOutputStream != null) {
                byte[] bArr = new byte[4];
                int i = 0;
                int i2 = 0;
                while (i < str.length()) {
                    int i3 = i + 1;
                    char charAt = str.charAt(i);
                    if (charAt != '=') {
                        if (!Character.isWhitespace(charAt)) {
                            byte[] bArr2 = __rfc1421nibbles;
                            if (bArr2[charAt] >= 0) {
                                int i4 = i2 + 1;
                                bArr[i2] = bArr2[charAt];
                                if (i4 != 1) {
                                    if (i4 == 2) {
                                        byteArrayOutputStream.write((bArr[1] >>> 4) | (bArr[0] << 2));
                                    } else if (i4 == 3) {
                                        byteArrayOutputStream.write((bArr[1] << 4) | (bArr[2] >>> 2));
                                    } else if (i4 == 4) {
                                        byteArrayOutputStream.write((bArr[2] << 6) | bArr[3]);
                                        i2 = 0;
                                    }
                                }
                                i2 = i4;
                            } else {
                                throw new IllegalArgumentException("Not B64 encoded");
                            }
                        }
                        i = i3;
                    } else {
                        return;
                    }
                }
                return;
            }
            throw new IllegalArgumentException("No outputstream for decoded bytes");
        }
    }

    public static void encode(int i, Appendable appendable) throws IOException {
        appendable.append(__rfc1421alphabet[((-67108864 & i) >> 26) & 63]);
        appendable.append(__rfc1421alphabet[((66060288 & i) >> 20) & 63]);
        appendable.append(__rfc1421alphabet[((1032192 & i) >> 14) & 63]);
        appendable.append(__rfc1421alphabet[((i & 16128) >> 8) & 63]);
        appendable.append(__rfc1421alphabet[((i & 252) >> 2) & 63]);
        appendable.append(__rfc1421alphabet[((i & 3) << 4) & 63]);
        appendable.append(__pad);
    }

    public static void encode(long j, Appendable appendable) throws IOException {
        int i = (int) ((j >> 32) & -4);
        appendable.append(__rfc1421alphabet[((-67108864 & i) >> 26) & 63]);
        appendable.append(__rfc1421alphabet[((66060288 & i) >> 20) & 63]);
        appendable.append(__rfc1421alphabet[((1032192 & i) >> 14) & 63]);
        appendable.append(__rfc1421alphabet[((i & 16128) >> 8) & 63]);
        appendable.append(__rfc1421alphabet[((i & 252) >> 2) & 63]);
        appendable.append(__rfc1421alphabet[(((i & 3) << 4) + (((int) (j >> 28)) & 15)) & 63]);
        int i2 = 268435455 & ((int) j);
        appendable.append(__rfc1421alphabet[((264241152 & i2) >> 22) & 63]);
        appendable.append(__rfc1421alphabet[((4128768 & i2) >> 16) & 63]);
        appendable.append(__rfc1421alphabet[((64512 & i2) >> 10) & 63]);
        appendable.append(__rfc1421alphabet[((i2 & PointerIconCompat.TYPE_TEXT) >> 4) & 63]);
        appendable.append(__rfc1421alphabet[((i2 & 15) << 2) & 63]);
    }
}
