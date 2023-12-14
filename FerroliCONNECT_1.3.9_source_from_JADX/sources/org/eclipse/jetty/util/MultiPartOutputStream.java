package org.eclipse.jetty.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MultiPartOutputStream extends FilterOutputStream {
    public static String MULTIPART_MIXED = "multipart/mixed";
    public static String MULTIPART_X_MIXED_REPLACE = "multipart/x-mixed-replace";
    private static final byte[] __CRLF = {13, 10};
    private static final byte[] __DASHDASH = {45, 45};
    private String boundary;
    private byte[] boundaryBytes;
    private boolean inPart;

    public MultiPartOutputStream(OutputStream outputStream) throws IOException {
        super(outputStream);
        this.inPart = false;
        this.boundary = "jetty" + System.identityHashCode(this) + Long.toString(System.currentTimeMillis(), 36);
        this.boundaryBytes = this.boundary.getBytes(StringUtil.__ISO_8859_1);
        this.inPart = false;
    }

    public void close() throws IOException {
        if (this.inPart) {
            this.out.write(__CRLF);
        }
        this.out.write(__DASHDASH);
        this.out.write(this.boundaryBytes);
        this.out.write(__DASHDASH);
        this.out.write(__CRLF);
        this.inPart = false;
        super.close();
    }

    public String getBoundary() {
        return this.boundary;
    }

    public OutputStream getOut() {
        return this.out;
    }

    public void startPart(String str) throws IOException {
        if (this.inPart) {
            this.out.write(__CRLF);
        }
        this.inPart = true;
        this.out.write(__DASHDASH);
        this.out.write(this.boundaryBytes);
        this.out.write(__CRLF);
        if (str != null) {
            OutputStream outputStream = this.out;
            outputStream.write(("Content-Type: " + str).getBytes(StringUtil.__ISO_8859_1));
        }
        this.out.write(__CRLF);
        this.out.write(__CRLF);
    }

    public void startPart(String str, String[] strArr) throws IOException {
        if (this.inPart) {
            this.out.write(__CRLF);
        }
        this.inPart = true;
        this.out.write(__DASHDASH);
        this.out.write(this.boundaryBytes);
        this.out.write(__CRLF);
        if (str != null) {
            OutputStream outputStream = this.out;
            outputStream.write(("Content-Type: " + str).getBytes(StringUtil.__ISO_8859_1));
        }
        this.out.write(__CRLF);
        int i = 0;
        while (strArr != null && i < strArr.length) {
            this.out.write(strArr[i].getBytes(StringUtil.__ISO_8859_1));
            this.out.write(__CRLF);
            i++;
        }
        this.out.write(__CRLF);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
    }
}
