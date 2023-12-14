package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.gzip.AbstractCompressedStream;
import org.eclipse.jetty.http.gzip.CompressedResponseWrapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class GzipHandler extends HandlerWrapper {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) GzipHandler.class);
    protected int _bufferSize = 8192;
    protected Set<String> _excluded;
    protected Set<String> _mimeTypes;
    protected int _minGzipSize = 256;
    protected String _vary = "Accept-Encoding, User-Agent";

    public Set<String> getMimeTypes() {
        return this._mimeTypes;
    }

    public void setMimeTypes(Set<String> set) {
        this._mimeTypes = set;
    }

    public void setMimeTypes(String str) {
        if (str != null) {
            this._mimeTypes = new HashSet();
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",", false);
            while (stringTokenizer.hasMoreTokens()) {
                this._mimeTypes.add(stringTokenizer.nextToken());
            }
        }
    }

    public Set<String> getExcluded() {
        return this._excluded;
    }

    public void setExcluded(Set<String> set) {
        this._excluded = set;
    }

    public void setExcluded(String str) {
        if (str != null) {
            this._excluded = new HashSet();
            StringTokenizer stringTokenizer = new StringTokenizer(str, ",", false);
            while (stringTokenizer.hasMoreTokens()) {
                this._excluded.add(stringTokenizer.nextToken());
            }
        }
    }

    public String getVary() {
        return this._vary;
    }

    public void setVary(String str) {
        this._vary = str;
    }

    public int getBufferSize() {
        return this._bufferSize;
    }

    public void setBufferSize(int i) {
        this._bufferSize = i;
    }

    public int getMinGzipSize() {
        return this._minGzipSize;
    }

    public void setMinGzipSize(int i) {
        this._minGzipSize = i;
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if (this._handler != null && isStarted()) {
            String header = httpServletRequest.getHeader("accept-encoding");
            if (header == null || header.indexOf("gzip") < 0 || httpServletResponse.containsHeader("Content-Encoding") || "HEAD".equalsIgnoreCase(httpServletRequest.getMethod())) {
                this._handler.handle(str, request, httpServletRequest, httpServletResponse);
                return;
            }
            if (this._excluded != null) {
                if (this._excluded.contains(httpServletRequest.getHeader(HttpHeaders.USER_AGENT))) {
                    this._handler.handle(str, request, httpServletRequest, httpServletResponse);
                    return;
                }
            }
            final CompressedResponseWrapper newGzipResponseWrapper = newGzipResponseWrapper(httpServletRequest, httpServletResponse);
            try {
                this._handler.handle(str, request, httpServletRequest, newGzipResponseWrapper);
                Continuation continuation = ContinuationSupport.getContinuation(httpServletRequest);
                if (!continuation.isSuspended() || !continuation.isResponseWrapped()) {
                    newGzipResponseWrapper.finish();
                } else {
                    continuation.addContinuationListener(new ContinuationListener() {
                        public void onTimeout(Continuation continuation) {
                        }

                        public void onComplete(Continuation continuation) {
                            try {
                                newGzipResponseWrapper.finish();
                            } catch (IOException e) {
                                GzipHandler.LOG.warn(e);
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                Continuation continuation2 = ContinuationSupport.getContinuation(httpServletRequest);
                if (continuation2.isSuspended() && continuation2.isResponseWrapped()) {
                    continuation2.addContinuationListener(new ContinuationListener() {
                        public void onTimeout(Continuation continuation) {
                        }

                        public void onComplete(Continuation continuation) {
                            try {
                                newGzipResponseWrapper.finish();
                            } catch (IOException e) {
                                GzipHandler.LOG.warn(e);
                            }
                        }
                    });
                } else if (!httpServletResponse.isCommitted()) {
                    newGzipResponseWrapper.resetBuffer();
                    newGzipResponseWrapper.noCompression();
                } else {
                    newGzipResponseWrapper.finish();
                }
                throw th;
            }
        }
    }

    /* access modifiers changed from: protected */
    public CompressedResponseWrapper newGzipResponseWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return new CompressedResponseWrapper(httpServletRequest, httpServletResponse) {
            {
                super.setMimeTypes(GzipHandler.this._mimeTypes);
                super.setBufferSize(GzipHandler.this._bufferSize);
                super.setMinCompressSize(GzipHandler.this._minGzipSize);
            }

            /* access modifiers changed from: protected */
            public AbstractCompressedStream newCompressedStream(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
                return new AbstractCompressedStream("gzip", httpServletRequest, this, GzipHandler.this._vary) {
                    /* access modifiers changed from: protected */
                    public DeflaterOutputStream createStream() throws IOException {
                        return new GZIPOutputStream(this._response.getOutputStream(), GzipHandler.this._bufferSize);
                    }
                };
            }

            /* access modifiers changed from: protected */
            public PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
                return GzipHandler.this.newWriter(outputStream, str);
            }
        };
    }

    /* access modifiers changed from: protected */
    public PrintWriter newWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        return str == null ? new PrintWriter(outputStream) : new PrintWriter(new OutputStreamWriter(outputStream, str));
    }
}
