package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.eclipse.jetty.http.AbstractGenerator;
import org.eclipse.jetty.util.ByteArrayOutputStream2;
import org.eclipse.jetty.util.StringUtil;

public class HttpWriter extends Writer {
    public static final int MAX_OUTPUT_CHARS = 512;
    private static final int WRITE_CONV = 0;
    private static final int WRITE_ISO1 = 1;
    private static final int WRITE_UTF8 = 2;
    final AbstractGenerator _generator = this._out._generator;
    final HttpOutput _out;
    int _surrogate = 0;
    int _writeMode;

    public HttpWriter(HttpOutput httpOutput) {
        this._out = httpOutput;
    }

    public void setCharacterEncoding(String str) {
        if (str == null || StringUtil.__ISO_8859_1.equalsIgnoreCase(str)) {
            this._writeMode = 1;
        } else if ("UTF-8".equalsIgnoreCase(str)) {
            this._writeMode = 2;
        } else {
            this._writeMode = 0;
            if (this._out._characterEncoding == null || !this._out._characterEncoding.equalsIgnoreCase(str)) {
                this._out._converter = null;
            }
        }
        HttpOutput httpOutput = this._out;
        httpOutput._characterEncoding = str;
        if (httpOutput._bytes == null) {
            this._out._bytes = new ByteArrayOutputStream2(512);
        }
    }

    public void close() throws IOException {
        this._out.close();
    }

    public void flush() throws IOException {
        this._out.flush();
    }

    public void write(String str, int i, int i2) throws IOException {
        while (i2 > 512) {
            write(str, i, 512);
            i += 512;
            i2 -= 512;
        }
        if (this._out._chars == null) {
            this._out._chars = new char[512];
        }
        char[] cArr = this._out._chars;
        str.getChars(i, i + i2, cArr, 0);
        write(cArr, 0, i2);
    }

    public void write(char[] cArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        HttpOutput httpOutput = this._out;
        while (i2 > 0) {
            httpOutput._bytes.reset();
            int i5 = 512;
            if (i2 <= 512) {
                i5 = i2;
            }
            int i6 = this._writeMode;
            if (i6 != 0) {
                int i7 = 0;
                if (i6 == 1) {
                    byte[] buf = httpOutput._bytes.getBuf();
                    int count = httpOutput._bytes.getCount();
                    if (i5 > buf.length - count) {
                        i5 = buf.length - count;
                    }
                    while (i7 < i5) {
                        char c = cArr[i + i7];
                        int i8 = count + 1;
                        buf[count] = (byte) (c < 256 ? c : '?');
                        i7++;
                        count = i8;
                    }
                    if (count >= 0) {
                        httpOutput._bytes.setCount(count);
                    }
                } else if (i6 == 2) {
                    byte[] buf2 = httpOutput._bytes.getBuf();
                    int count2 = httpOutput._bytes.getCount();
                    if (count2 + i5 > buf2.length) {
                        i5 = buf2.length - count2;
                    }
                    int i9 = count2;
                    int i10 = 0;
                    while (true) {
                        if (i10 >= i5) {
                            break;
                        }
                        char c2 = cArr[i + i10];
                        if (this._surrogate == 0) {
                            boolean isHighSurrogate = Character.isHighSurrogate((char) c2);
                            i3 = c2;
                            if (isHighSurrogate) {
                                this._surrogate = c2;
                                i10++;
                            }
                        } else {
                            char c3 = (char) c2;
                            if (Character.isLowSurrogate(c3)) {
                                i3 = Character.toCodePoint((char) this._surrogate, c3);
                            } else {
                                i3 = this._surrogate;
                                this._surrogate = 0;
                                i10--;
                            }
                        }
                        if ((i3 & -128) != 0) {
                            if ((i3 & -2048) != 0) {
                                if ((-65536 & i3) == 0) {
                                    if (i9 + 3 > buf2.length) {
                                        break;
                                    }
                                    int i11 = i9 + 1;
                                    buf2[i9] = (byte) ((i3 >> 12) | 224);
                                    int i12 = i11 + 1;
                                    buf2[i11] = (byte) (((i3 >> 6) & 63) | 128);
                                    i4 = i12 + 1;
                                    buf2[i12] = (byte) ((i3 & 63) | 128);
                                } else if ((-14680064 & i3) == 0) {
                                    if (i9 + 4 > buf2.length) {
                                        break;
                                    }
                                    int i13 = i9 + 1;
                                    buf2[i9] = (byte) ((i3 >> 18) | 240);
                                    int i14 = i13 + 1;
                                    buf2[i13] = (byte) (((i3 >> 12) & 63) | 128);
                                    int i15 = i14 + 1;
                                    buf2[i14] = (byte) (((i3 >> 6) & 63) | 128);
                                    i9 = i15 + 1;
                                    buf2[i15] = (byte) ((i3 & 63) | 128);
                                } else if ((-201326592 & i3) == 0) {
                                    if (i9 + 5 > buf2.length) {
                                        break;
                                    }
                                    int i16 = i9 + 1;
                                    buf2[i9] = (byte) ((i3 >> 24) | 248);
                                    int i17 = i16 + 1;
                                    buf2[i16] = (byte) (((i3 >> 18) & 63) | 128);
                                    int i18 = i17 + 1;
                                    buf2[i17] = (byte) (((i3 >> 12) & 63) | 128);
                                    int i19 = i18 + 1;
                                    buf2[i18] = (byte) (((i3 >> 6) & 63) | 128);
                                    i4 = i19 + 1;
                                    buf2[i19] = (byte) ((i3 & 63) | 128);
                                } else if ((Integer.MIN_VALUE & i3) != 0) {
                                    buf2[i9] = 63;
                                    i9++;
                                } else if (i9 + 6 > buf2.length) {
                                    break;
                                } else {
                                    int i20 = i9 + 1;
                                    buf2[i9] = (byte) ((i3 >> 30) | 252);
                                    int i21 = i20 + 1;
                                    buf2[i20] = (byte) (((i3 >> 24) & 63) | 128);
                                    int i22 = i21 + 1;
                                    buf2[i21] = (byte) (((i3 >> 18) & 63) | 128);
                                    int i23 = i22 + 1;
                                    buf2[i22] = (byte) (((i3 >> 12) & 63) | 128);
                                    int i24 = i23 + 1;
                                    buf2[i23] = (byte) (((i3 >> 6) & 63) | 128);
                                    i9 = i24 + 1;
                                    buf2[i24] = (byte) ((i3 & 63) | 128);
                                }
                                i9 = i4;
                            } else if (i9 + 2 > buf2.length) {
                                break;
                            } else {
                                int i25 = i9 + 1;
                                buf2[i9] = (byte) ((i3 >> 6) | 192);
                                i9 = i25 + 1;
                                buf2[i25] = (byte) ((i3 & 63) | 128);
                            }
                            this._surrogate = 0;
                            if (i9 == buf2.length) {
                                i10++;
                                break;
                            }
                            i10++;
                        } else if (i9 >= buf2.length) {
                            break;
                        } else {
                            buf2[i9] = (byte) i3;
                            i9++;
                            i10++;
                        }
                    }
                    i5 = i10;
                    httpOutput._bytes.setCount(i9);
                } else {
                    throw new IllegalStateException();
                }
            } else {
                Writer converter = getConverter();
                converter.write(cArr, i, i5);
                converter.flush();
            }
            httpOutput._bytes.writeTo(httpOutput);
            i2 -= i5;
            i += i5;
        }
    }

    private Writer getConverter() throws IOException {
        if (this._out._converter == null) {
            HttpOutput httpOutput = this._out;
            httpOutput._converter = new OutputStreamWriter(httpOutput._bytes, this._out._characterEncoding);
        }
        return this._out._converter;
    }
}
