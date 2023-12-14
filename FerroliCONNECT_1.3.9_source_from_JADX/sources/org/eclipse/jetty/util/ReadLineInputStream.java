package org.eclipse.jetty.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadLineInputStream extends BufferedInputStream {
    boolean _seenCRLF;
    boolean _skipLF;

    public ReadLineInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public ReadLineInputStream(InputStream inputStream, int i) {
        super(inputStream, i);
    }

    public String readLine() throws IOException {
        mark(this.buf.length);
        while (true) {
            int read = super.read();
            if (this.markpos < 0) {
                throw new IOException("Buffer size exceeded: no line terminator");
            } else if (read == -1) {
                int i = this.markpos;
                this.markpos = -1;
                if (this.pos > i) {
                    return new String(this.buf, i, this.pos - i, StringUtil.__UTF8_CHARSET);
                }
                return null;
            } else if (read == 13) {
                int i2 = this.pos;
                if (!this._seenCRLF || this.pos >= this.count) {
                    this._skipLF = true;
                } else if (this.buf[this.pos] == 10) {
                    this.pos++;
                }
                int i3 = this.markpos;
                this.markpos = -1;
                return new String(this.buf, i3, (i2 - i3) - 1, StringUtil.__UTF8_CHARSET);
            } else if (read == 10) {
                if (this._skipLF) {
                    this._skipLF = false;
                    this._seenCRLF = true;
                    this.markpos++;
                } else {
                    int i4 = this.markpos;
                    this.markpos = -1;
                    return new String(this.buf, i4, (this.pos - i4) - 1, StringUtil.__UTF8_CHARSET);
                }
            }
        }
    }

    public synchronized int read() throws IOException {
        int read;
        read = super.read();
        if (this._skipLF) {
            this._skipLF = false;
            if (this._seenCRLF && read == 10) {
                read = super.read();
            }
        }
        return read;
    }

    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        if (this._skipLF && i2 > 0) {
            this._skipLF = false;
            if (this._seenCRLF) {
                int read = super.read();
                if (read == -1) {
                    return -1;
                }
                if (read != 10) {
                    bArr[i] = (byte) (read & 255);
                    return super.read(bArr, i + 1, i2 - 1) + 1;
                }
            }
        }
        return super.read(bArr, i, i2);
    }
}
