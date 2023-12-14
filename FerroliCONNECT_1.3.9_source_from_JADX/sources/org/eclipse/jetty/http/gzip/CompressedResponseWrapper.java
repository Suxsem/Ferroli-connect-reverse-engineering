package org.eclipse.jetty.http.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.util.StringUtil;

public abstract class CompressedResponseWrapper extends HttpServletResponseWrapper {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int DEFAULT_MIN_COMPRESS_SIZE = 256;
    private int _bufferSize = 8192;
    private AbstractCompressedStream _compressedStream;
    private long _contentLength = -1;
    private String _etag;
    private Set<String> _mimeTypes;
    private int _minCompressSize = 256;
    private boolean _noCompression;
    protected HttpServletRequest _request;
    private PrintWriter _writer;

    /* access modifiers changed from: protected */
    public abstract AbstractCompressedStream newCompressedStream(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException;

    public CompressedResponseWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        this._request = httpServletRequest;
    }

    public long getContentLength() {
        return this._contentLength;
    }

    public int getBufferSize() {
        return this._bufferSize;
    }

    public int getMinCompressSize() {
        return this._minCompressSize;
    }

    public String getETag() {
        return this._etag;
    }

    public HttpServletRequest getRequest() {
        return this._request;
    }

    public void setMimeTypes(Set<String> set) {
        this._mimeTypes = set;
    }

    public void setBufferSize(int i) {
        this._bufferSize = i;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.setBufferSize(i);
        }
    }

    public void setMinCompressSize(int i) {
        this._minCompressSize = i;
    }

    public void setContentType(String str) {
        int indexOf;
        super.setContentType(str);
        if (!this._noCompression) {
            if (str != null && (indexOf = str.indexOf(";")) > 0) {
                str = str.substring(0, indexOf);
            }
            AbstractCompressedStream abstractCompressedStream = this._compressedStream;
            if (abstractCompressedStream == null || abstractCompressedStream.getOutputStream() == null) {
                if (this._mimeTypes != null || str == null || !str.contains("gzip")) {
                    Set<String> set = this._mimeTypes;
                    if (set == null) {
                        return;
                    }
                    if (str != null && set.contains(StringUtil.asciiToLowerCase(str))) {
                        return;
                    }
                }
                noCompression();
            }
        }
    }

    public void setStatus(int i, String str) {
        super.setStatus(i, str);
        if (i < 200 || i == 204 || i == 205 || i >= 300) {
            noCompression();
        }
    }

    public void setStatus(int i) {
        super.setStatus(i);
        if (i < 200 || i == 204 || i == 205 || i >= 300) {
            noCompression();
        }
    }

    public void setContentLength(int i) {
        if (this._noCompression) {
            super.setContentLength(i);
        } else {
            setContentLength((long) i);
        }
    }

    /* access modifiers changed from: protected */
    public void setContentLength(long j) {
        this._contentLength = j;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.setContentLength();
        } else if (this._noCompression && this._contentLength >= 0) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) getResponse();
            long j2 = this._contentLength;
            if (j2 < 2147483647L) {
                httpServletResponse.setContentLength((int) j2);
            } else {
                httpServletResponse.setHeader("Content-Length", Long.toString(j2));
            }
        }
    }

    public void addHeader(String str, String str2) {
        if ("content-length".equalsIgnoreCase(str)) {
            this._contentLength = Long.parseLong(str2);
            AbstractCompressedStream abstractCompressedStream = this._compressedStream;
            if (abstractCompressedStream != null) {
                abstractCompressedStream.setContentLength();
            }
        } else if ("content-type".equalsIgnoreCase(str)) {
            setContentType(str2);
        } else if ("content-encoding".equalsIgnoreCase(str)) {
            super.addHeader(str, str2);
            if (!isCommitted()) {
                noCompression();
            }
        } else if ("etag".equalsIgnoreCase(str)) {
            this._etag = str2;
        } else {
            super.addHeader(str, str2);
        }
    }

    public void flushBuffer() throws IOException {
        PrintWriter printWriter = this._writer;
        if (printWriter != null) {
            printWriter.flush();
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.flush();
        } else {
            getResponse().flushBuffer();
        }
    }

    public void reset() {
        super.reset();
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.resetBuffer();
        }
        this._writer = null;
        this._compressedStream = null;
        this._noCompression = false;
        this._contentLength = -1;
    }

    public void resetBuffer() {
        super.resetBuffer();
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.resetBuffer();
        }
        this._writer = null;
        this._compressedStream = null;
    }

    public void sendError(int i, String str) throws IOException {
        resetBuffer();
        super.sendError(i, str);
    }

    public void sendError(int i) throws IOException {
        resetBuffer();
        super.sendError(i);
    }

    public void sendRedirect(String str) throws IOException {
        resetBuffer();
        super.sendRedirect(str);
    }

    public void noCompression() {
        if (!this._noCompression) {
            setDeferredHeaders();
        }
        this._noCompression = true;
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            try {
                abstractCompressedStream.doNotCompress(false);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void finish() throws IOException {
        if (this._writer != null && !this._compressedStream.isClosed()) {
            this._writer.flush();
        }
        AbstractCompressedStream abstractCompressedStream = this._compressedStream;
        if (abstractCompressedStream != null) {
            abstractCompressedStream.finish();
        } else {
            setDeferredHeaders();
        }
    }

    private void setDeferredHeaders() {
        if (!isCommitted()) {
            long j = this._contentLength;
            if (j >= 0) {
                if (j < 2147483647L) {
                    super.setContentLength((int) j);
                } else {
                    super.setHeader("Content-Length", Long.toString(j));
                }
            }
            String str = this._etag;
            if (str != null) {
                super.setHeader(HttpHeaders.ETAG, str);
            }
        }
    }

    public void setHeader(String str, String str2) {
        if (this._noCompression) {
            super.setHeader(str, str2);
        } else if ("content-length".equalsIgnoreCase(str)) {
            setContentLength(Long.parseLong(str2));
        } else if ("content-type".equalsIgnoreCase(str)) {
            setContentType(str2);
        } else if ("content-encoding".equalsIgnoreCase(str)) {
            super.setHeader(str, str2);
            if (!isCommitted()) {
                noCompression();
            }
        } else if ("etag".equalsIgnoreCase(str)) {
            this._etag = str2;
        } else {
            super.setHeader(str, str2);
        }
    }

    public boolean containsHeader(String str) {
        if (this._noCompression || !"etag".equalsIgnoreCase(str) || this._etag == null) {
            return super.containsHeader(str);
        }
        return true;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (this._compressedStream == null) {
            if (getResponse().isCommitted() || this._noCompression) {
                return getResponse().getOutputStream();
            }
            this._compressedStream = newCompressedStream(this._request, (HttpServletResponse) getResponse());
        } else if (this._writer != null) {
            throw new IllegalStateException("getWriter() called");
        }
        return this._compressedStream;
    }

    public PrintWriter getWriter() throws IOException {
        if (this._writer == null) {
            if (this._compressedStream != null) {
                throw new IllegalStateException("getOutputStream() called");
            } else if (getResponse().isCommitted() || this._noCompression) {
                return getResponse().getWriter();
            } else {
                this._compressedStream = newCompressedStream(this._request, (HttpServletResponse) getResponse());
                this._writer = newWriter(this._compressedStream, getCharacterEncoding());
            }
        }
        return this._writer;
    }

    public void setIntHeader(String str, int i) {
        if ("content-length".equalsIgnoreCase(str)) {
            this._contentLength = (long) i;
            AbstractCompressedStream abstractCompressedStream = this._compressedStream;
            if (abstractCompressedStream != null) {
                abstractCompressedStream.setContentLength();
                return;
            }
            return;
        }
        super.setIntHeader(str, i);
    }

    /* access modifiers changed from: protected */
    public PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return str == null ? new PrintWriter(outputStream) : new PrintWriter(new OutputStreamWriter(outputStream, str));
    }
}
