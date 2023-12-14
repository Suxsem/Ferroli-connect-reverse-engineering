package org.eclipse.jetty.p119io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/* renamed from: org.eclipse.jetty.io.WriterOutputStream */
public class WriterOutputStream extends OutputStream {
    private final byte[] _buf = new byte[1];
    protected final String _encoding;
    protected final Writer _writer;

    public WriterOutputStream(Writer writer, String str) {
        this._writer = writer;
        this._encoding = str;
    }

    public WriterOutputStream(Writer writer) {
        this._writer = writer;
        this._encoding = null;
    }

    public void close() throws IOException {
        this._writer.close();
    }

    public void flush() throws IOException {
        this._writer.flush();
    }

    public void write(byte[] bArr) throws IOException {
        String str = this._encoding;
        if (str == null) {
            this._writer.write(new String(bArr));
        } else {
            this._writer.write(new String(bArr, str));
        }
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        String str = this._encoding;
        if (str == null) {
            this._writer.write(new String(bArr, i, i2));
        } else {
            this._writer.write(new String(bArr, i, i2, str));
        }
    }

    public synchronized void write(int i) throws IOException {
        this._buf[0] = (byte) i;
        write(this._buf);
    }
}
