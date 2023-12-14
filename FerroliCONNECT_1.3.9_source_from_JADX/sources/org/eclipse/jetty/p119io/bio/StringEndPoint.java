package org.eclipse.jetty.p119io.bio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/* renamed from: org.eclipse.jetty.io.bio.StringEndPoint */
public class StringEndPoint extends StreamEndPoint {
    ByteArrayInputStream _bin;
    ByteArrayOutputStream _bout;
    String _encoding;

    public StringEndPoint() {
        super((InputStream) null, (OutputStream) null);
        this._encoding = "UTF-8";
        this._bin = new ByteArrayInputStream(new byte[0]);
        this._bout = new ByteArrayOutputStream();
        this._in = this._bin;
        this._out = this._bout;
    }

    public StringEndPoint(String str) {
        this();
        if (str != null) {
            this._encoding = str;
        }
    }

    public void setInput(String str) {
        try {
            this._bin = new ByteArrayInputStream(str.getBytes(this._encoding));
            this._in = this._bin;
            this._bout = new ByteArrayOutputStream();
            this._out = this._bout;
            this._ishut = false;
            this._oshut = false;
        } catch (Exception e) {
            throw new IllegalStateException(e.toString());
        }
    }

    public String getOutput() {
        try {
            String str = new String(this._bout.toByteArray(), this._encoding);
            this._bout.reset();
            return str;
        } catch (Exception e) {
            throw new IllegalStateException(this._encoding) {
                {
                    initCause(e);
                }
            };
        }
    }

    public boolean hasMore() {
        return this._bin.available() > 0;
    }
}
