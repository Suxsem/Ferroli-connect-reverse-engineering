package org.eclipse.jetty.http.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.DeflaterOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kotlin.text.Typography;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.util.ByteArrayOutputStream2;

public abstract class AbstractCompressedStream extends ServletOutputStream {
    protected ByteArrayOutputStream2 _bOut;
    protected boolean _closed;
    protected DeflaterOutputStream _compressedOutputStream;
    protected boolean _doNotCompress;
    private final String _encoding;
    protected OutputStream _out;
    protected final HttpServletResponse _response;
    protected final String _vary;
    protected final CompressedResponseWrapper _wrapper;

    /* access modifiers changed from: protected */
    public abstract DeflaterOutputStream createStream() throws IOException;

    public AbstractCompressedStream(String str, HttpServletRequest httpServletRequest, CompressedResponseWrapper compressedResponseWrapper, String str2) throws IOException {
        this._encoding = str;
        this._wrapper = compressedResponseWrapper;
        this._response = (HttpServletResponse) compressedResponseWrapper.getResponse();
        this._vary = str2;
        if (this._wrapper.getMinCompressSize() == 0) {
            doCompress();
        }
    }

    public void resetBuffer() {
        if (this._response.isCommitted() || this._compressedOutputStream != null) {
            throw new IllegalStateException("Committed");
        }
        this._closed = false;
        this._out = null;
        this._bOut = null;
        this._doNotCompress = false;
    }

    public void setBufferSize(int i) {
        ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
        if (byteArrayOutputStream2 != null && byteArrayOutputStream2.getBuf().length < i) {
            ByteArrayOutputStream2 byteArrayOutputStream22 = new ByteArrayOutputStream2(i);
            byteArrayOutputStream22.write(this._bOut.getBuf(), 0, this._bOut.size());
            this._bOut = byteArrayOutputStream22;
        }
    }

    public void setContentLength() {
        if (this._doNotCompress) {
            long contentLength = this._wrapper.getContentLength();
            if (contentLength < 0) {
                return;
            }
            if (contentLength < 2147483647L) {
                this._response.setContentLength((int) contentLength);
            } else {
                this._response.setHeader("Content-Length", Long.toString(contentLength));
            }
        }
    }

    public void flush() throws IOException {
        if (this._out == null || this._bOut != null) {
            long contentLength = this._wrapper.getContentLength();
            if (contentLength <= 0 || contentLength >= ((long) this._wrapper.getMinCompressSize())) {
                doCompress();
            } else {
                doNotCompress(false);
            }
        }
        this._out.flush();
    }

    public void close() throws IOException {
        if (!this._closed) {
            if (this._wrapper.getRequest().getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI) != null) {
                flush();
                return;
            }
            if (this._bOut != null) {
                long contentLength = this._wrapper.getContentLength();
                if (contentLength < 0) {
                    contentLength = (long) this._bOut.getCount();
                    this._wrapper.setContentLength(contentLength);
                }
                if (contentLength < ((long) this._wrapper.getMinCompressSize())) {
                    doNotCompress(false);
                } else {
                    doCompress();
                }
            } else if (this._out == null) {
                doNotCompress(false);
            }
            DeflaterOutputStream deflaterOutputStream = this._compressedOutputStream;
            if (deflaterOutputStream != null) {
                deflaterOutputStream.close();
            } else {
                this._out.close();
            }
            this._closed = true;
        }
    }

    public void finish() throws IOException {
        if (!this._closed) {
            if (this._out == null || this._bOut != null) {
                long contentLength = this._wrapper.getContentLength();
                if (contentLength < 0 || contentLength >= ((long) this._wrapper.getMinCompressSize())) {
                    doCompress();
                } else {
                    doNotCompress(false);
                }
            }
            DeflaterOutputStream deflaterOutputStream = this._compressedOutputStream;
            if (deflaterOutputStream != null && !this._closed) {
                this._closed = true;
                deflaterOutputStream.close();
            }
        }
    }

    public void write(int i) throws IOException {
        checkOut(1);
        this._out.write(i);
    }

    public void write(byte[] bArr) throws IOException {
        checkOut(bArr.length);
        this._out.write(bArr);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        checkOut(i2);
        this._out.write(bArr, i, i2);
    }

    public void doCompress() throws IOException {
        if (this._compressedOutputStream != null) {
            return;
        }
        if (!this._response.isCommitted()) {
            String str = this._encoding;
            if (str != null) {
                setHeader("Content-Encoding", str);
                if (this._response.containsHeader("Content-Encoding")) {
                    addHeader(HttpHeaders.VARY, this._vary);
                    DeflaterOutputStream createStream = createStream();
                    this._compressedOutputStream = createStream;
                    this._out = createStream;
                    OutputStream outputStream = this._out;
                    if (outputStream != null) {
                        ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
                        if (byteArrayOutputStream2 != null) {
                            outputStream.write(byteArrayOutputStream2.getBuf(), 0, this._bOut.getCount());
                            this._bOut = null;
                        }
                        String eTag = this._wrapper.getETag();
                        if (eTag != null) {
                            setHeader(HttpHeaders.ETAG, eTag.substring(0, eTag.length() - 1) + '-' + this._encoding + Typography.quote);
                            return;
                        }
                        return;
                    }
                }
            }
            doNotCompress(true);
            return;
        }
        throw new IllegalStateException();
    }

    public void doNotCompress(boolean z) throws IOException {
        if (this._compressedOutputStream != null) {
            throw new IllegalStateException("Compressed output stream is already assigned.");
        } else if (this._out == null || this._bOut != null) {
            if (z) {
                addHeader(HttpHeaders.VARY, this._vary);
            }
            if (this._wrapper.getETag() != null) {
                setHeader(HttpHeaders.ETAG, this._wrapper.getETag());
            }
            this._doNotCompress = true;
            this._out = this._response.getOutputStream();
            setContentLength();
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
            if (byteArrayOutputStream2 != null) {
                this._out.write(byteArrayOutputStream2.getBuf(), 0, this._bOut.getCount());
            }
            this._bOut = null;
        }
    }

    private void checkOut(int i) throws IOException {
        if (this._closed) {
            throw new IOException("CLOSED");
        } else if (this._out != null) {
            ByteArrayOutputStream2 byteArrayOutputStream2 = this._bOut;
            if (byteArrayOutputStream2 != null && i >= byteArrayOutputStream2.getBuf().length - this._bOut.getCount()) {
                long contentLength = this._wrapper.getContentLength();
                if (contentLength < 0 || contentLength >= ((long) this._wrapper.getMinCompressSize())) {
                    doCompress();
                } else {
                    doNotCompress(false);
                }
            }
        } else if (i > this._wrapper.getBufferSize()) {
            long contentLength2 = this._wrapper.getContentLength();
            if (contentLength2 < 0 || contentLength2 >= ((long) this._wrapper.getMinCompressSize())) {
                doCompress();
            } else {
                doNotCompress(false);
            }
        } else {
            ByteArrayOutputStream2 byteArrayOutputStream22 = new ByteArrayOutputStream2(this._wrapper.getBufferSize());
            this._bOut = byteArrayOutputStream22;
            this._out = byteArrayOutputStream22;
        }
    }

    public OutputStream getOutputStream() {
        return this._out;
    }

    public boolean isClosed() {
        return this._closed;
    }

    /* access modifiers changed from: protected */
    public PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return str == null ? new PrintWriter(outputStream) : new PrintWriter(new OutputStreamWriter(outputStream, str));
    }

    /* access modifiers changed from: protected */
    public void addHeader(String str, String str2) {
        this._response.addHeader(str, str2);
    }

    /* access modifiers changed from: protected */
    public void setHeader(String str, String str2) {
        this._response.setHeader(str, str2);
    }
}
