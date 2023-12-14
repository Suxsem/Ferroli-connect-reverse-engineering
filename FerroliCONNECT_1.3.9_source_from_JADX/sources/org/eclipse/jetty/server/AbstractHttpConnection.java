package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import org.eclipse.jetty.http.EncodedHttpURI;
import org.eclipse.jetty.http.Generator;
import org.eclipse.jetty.http.HttpBuffers;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.http.Parser;
import org.eclipse.jetty.p119io.AbstractConnection;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.BufferCache;
import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.EofException;
import org.eclipse.jetty.p119io.UncheckedPrintWriter;
import org.eclipse.jetty.server.nio.NIOConnector;
import org.eclipse.jetty.server.ssl.SslConnector;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public abstract class AbstractHttpConnection extends AbstractConnection {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) AbstractHttpConnection.class);
    private static final int UNKNOWN = -2;
    private static final ThreadLocal<AbstractHttpConnection> __currentConnection = new ThreadLocal<>();
    private Object _associatedObject;
    private String _charset;
    protected final Connector _connector;
    private boolean _delayedHandling = false;
    private boolean _earlyEOF = false;
    private boolean _expect = false;
    private boolean _expect100Continue = false;
    private boolean _expect102Processing = false;
    protected final Generator _generator;
    private boolean _head = false;
    private boolean _host = false;
    protected volatile ServletInputStream _in;
    int _include;
    protected volatile Output _out;
    protected final Parser _parser;
    protected volatile PrintWriter _printWriter;
    protected final Request _request;
    protected final HttpFields _requestFields;
    private int _requests;
    protected final Response _response;
    protected final HttpFields _responseFields;
    protected final Server _server;
    protected final HttpURI _uri;
    private int _version = -2;
    protected volatile OutputWriter _writer;

    public abstract Connection handle() throws IOException;

    public static AbstractHttpConnection getCurrentConnection() {
        return __currentConnection.get();
    }

    protected static void setCurrentConnection(AbstractHttpConnection abstractHttpConnection) {
        __currentConnection.set(abstractHttpConnection);
    }

    public AbstractHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(endPoint);
        this._uri = "UTF-8".equals(URIUtil.__CHARSET) ? new HttpURI() : new EncodedHttpURI(URIUtil.__CHARSET);
        this._connector = connector;
        HttpBuffers httpBuffers = (HttpBuffers) this._connector;
        this._parser = newHttpParser(httpBuffers.getRequestBuffers(), endPoint, new RequestHandler());
        this._requestFields = new HttpFields();
        this._responseFields = new HttpFields();
        this._request = new Request(this);
        this._response = new Response(this);
        this._generator = newHttpGenerator(httpBuffers.getResponseBuffers(), endPoint);
        this._generator.setSendServerVersion(server.getSendServerVersion());
        this._server = server;
    }

    protected AbstractHttpConnection(Connector connector, EndPoint endPoint, Server server, Parser parser, Generator generator, Request request) {
        super(endPoint);
        this._uri = URIUtil.__CHARSET.equals("UTF-8") ? new HttpURI() : new EncodedHttpURI(URIUtil.__CHARSET);
        this._connector = connector;
        this._parser = parser;
        this._requestFields = new HttpFields();
        this._responseFields = new HttpFields();
        this._request = request;
        this._response = new Response(this);
        this._generator = generator;
        this._generator.setSendServerVersion(server.getSendServerVersion());
        this._server = server;
    }

    /* access modifiers changed from: protected */
    public HttpParser newHttpParser(Buffers buffers, EndPoint endPoint, HttpParser.EventHandler eventHandler) {
        return new HttpParser(buffers, endPoint, eventHandler);
    }

    /* access modifiers changed from: protected */
    public HttpGenerator newHttpGenerator(Buffers buffers, EndPoint endPoint) {
        return new HttpGenerator(buffers, endPoint);
    }

    public Parser getParser() {
        return this._parser;
    }

    public int getRequests() {
        return this._requests;
    }

    public Server getServer() {
        return this._server;
    }

    public Object getAssociatedObject() {
        return this._associatedObject;
    }

    public void setAssociatedObject(Object obj) {
        this._associatedObject = obj;
    }

    public Connector getConnector() {
        return this._connector;
    }

    public HttpFields getRequestFields() {
        return this._requestFields;
    }

    public HttpFields getResponseFields() {
        return this._responseFields;
    }

    public boolean isConfidential(Request request) {
        Connector connector = this._connector;
        return connector != null && connector.isConfidential(request);
    }

    public boolean isIntegral(Request request) {
        Connector connector = this._connector;
        return connector != null && connector.isIntegral(request);
    }

    public boolean getResolveNames() {
        return this._connector.getResolveNames();
    }

    public Request getRequest() {
        return this._request;
    }

    public Response getResponse() {
        return this._response;
    }

    public ServletInputStream getInputStream() throws IOException {
        if (this._expect100Continue) {
            if (((HttpParser) this._parser).getHeaderBuffer() == null || ((HttpParser) this._parser).getHeaderBuffer().length() < 2) {
                if (!this._generator.isCommitted()) {
                    ((HttpGenerator) this._generator).send1xx(100);
                } else {
                    throw new IllegalStateException("Committed before 100 Continues");
                }
            }
            this._expect100Continue = false;
        }
        if (this._in == null) {
            this._in = new HttpInput(this);
        }
        return this._in;
    }

    public ServletOutputStream getOutputStream() {
        if (this._out == null) {
            this._out = new Output();
        }
        return this._out;
    }

    public PrintWriter getPrintWriter(String str) {
        getOutputStream();
        if (this._writer == null) {
            this._writer = new OutputWriter();
            if (this._server.isUncheckedPrintWriter()) {
                this._printWriter = new UncheckedPrintWriter((Writer) this._writer);
            } else {
                this._printWriter = new PrintWriter(this._writer) {
                    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
                    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x000b */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void close() {
                        /*
                            r2 = this;
                            java.lang.Object r0 = r2.lock
                            monitor-enter(r0)
                            java.io.Writer r1 = r2.out     // Catch:{ IOException -> 0x000b }
                            r1.close()     // Catch:{ IOException -> 0x000b }
                            goto L_0x000e
                        L_0x0009:
                            r1 = move-exception
                            goto L_0x0010
                        L_0x000b:
                            r2.setError()     // Catch:{ all -> 0x0009 }
                        L_0x000e:
                            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
                            return
                        L_0x0010:
                            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
                            throw r1
                        */
                        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractHttpConnection.C24021.close():void");
                    }
                };
            }
        }
        this._writer.setCharacterEncoding(str);
        return this._printWriter;
    }

    public boolean isResponseCommitted() {
        return this._generator.isCommitted();
    }

    public boolean isEarlyEOF() {
        return this._earlyEOF;
    }

    public void reset() {
        this._parser.reset();
        this._parser.returnBuffers();
        this._requestFields.clear();
        this._request.recycle();
        this._generator.reset();
        this._generator.returnBuffers();
        this._responseFields.clear();
        this._response.recycle();
        this._uri.clear();
        this._writer = null;
        this._earlyEOF = false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01c1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01c2, code lost:
        r3 = r0;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01c6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01c7, code lost:
        r4 = r0;
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01dd, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01df, code lost:
        r3 = null;
        r10 = 500;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x01ed, code lost:
        r1._request.getAsyncContinuation().errorComplete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x020c, code lost:
        if (r8.isRunning() != false) goto L_0x020e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0210, code lost:
        if (r1._server != null) goto L_0x0295;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0214, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0215, code lost:
        r3 = r0;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0236, code lost:
        r1._request.getAsyncContinuation().errorComplete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0255, code lost:
        if (r8.isRunning() != false) goto L_0x0257;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0259, code lost:
        if (r1._server != null) goto L_0x0295;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x025c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x025d, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:?, code lost:
        LOG.debug(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:?, code lost:
        r1._request.setHandled(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x026e, code lost:
        if (r1._request.isAsyncStarted() != false) goto L_0x0270;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0270, code lost:
        r1._request.getAsyncContinuation().errorComplete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0279, code lost:
        r3 = r1._request._async.isContinuation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0289, code lost:
        if (r1._request._async.unhandle() == false) goto L_0x028b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x028f, code lost:
        if (r8.isRunning() != false) goto L_0x0291;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0293, code lost:
        if (r1._server != null) goto L_0x0295;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0295, code lost:
        r9 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02a3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x02a4, code lost:
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:?, code lost:
        LOG.debug(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:?, code lost:
        r1._request.setHandled(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x02b5, code lost:
        if (r1._response.isCommitted() == false) goto L_0x02b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x02b7, code lost:
        r1._generator.sendError(500, (java.lang.String) null, (java.lang.String) null, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x02c5, code lost:
        if (r1._request.isAsyncStarted() != false) goto L_0x02c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x02c7, code lost:
        r1._request.getAsyncContinuation().errorComplete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x02d0, code lost:
        r3 = r1._request._async.isContinuation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x02e0, code lost:
        if (r1._request._async.unhandle() != false) goto L_0x02ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x02ec, code lost:
        r9 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x02ee, code lost:
        r9 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x02ef, code lost:
        r12 = r3;
        r3 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x02f6, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x02f7, code lost:
        r3 = r0;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x0307, code lost:
        if (r1._request.isAsyncStarted() != false) goto L_0x0309;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x0309, code lost:
        r1._request.getAsyncContinuation().errorComplete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x032e, code lost:
        r9 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x0330, code lost:
        r9 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01bb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01bc, code lost:
        r3 = r0;
        r4 = r9;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01c1 A[ExcHandler: all (r0v13 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:27:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x01c6 A[ExcHandler: Throwable (r0v12 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:27:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01dd A[Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8, all -> 0x0337, all -> 0x02f2, all -> 0x029e }] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01df A[Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8, all -> 0x0337, all -> 0x02f2, all -> 0x029e }] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x01ed A[Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8, all -> 0x0337, all -> 0x02f2, all -> 0x029e }] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0208 A[Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8, all -> 0x0337, all -> 0x02f2, all -> 0x029e }] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0214 A[ExcHandler: HttpException (r0v11 'e' org.eclipse.jetty.http.HttpException A[CUSTOM_DECLARE]), Splitter:B:27:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x0236 A[Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8, all -> 0x0337, all -> 0x02f2, all -> 0x029e }] */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0251 A[Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8, all -> 0x0337, all -> 0x02f2, all -> 0x029e }] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x025c A[ExcHandler: RuntimeIOException (r0v10 'e' org.eclipse.jetty.io.RuntimeIOException A[CUSTOM_DECLARE]), Splitter:B:27:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02a3 A[ExcHandler: EofException (r0v8 'e' org.eclipse.jetty.io.EofException A[CUSTOM_DECLARE]), Splitter:B:27:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x0301 A[SYNTHETIC, Splitter:B:189:0x0301] */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x032e A[Catch:{ all -> 0x0366 }] */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x0330 A[Catch:{ all -> 0x0366 }] */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x0371  */
    /* JADX WARNING: Removed duplicated region for block: B:223:0x0382  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0064 A[SYNTHETIC, Splitter:B:24:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x0409  */
    /* JADX WARNING: Removed duplicated region for block: B:253:0x041a  */
    /* JADX WARNING: Removed duplicated region for block: B:283:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleRequest() throws java.io.IOException {
        /*
            r16 = this;
            r1 = r16
            java.lang.String r2 = "100 continues not sent"
            r5 = 0
            r6 = 1
            org.eclipse.jetty.util.log.Logger r7 = LOG     // Catch:{ all -> 0x0401 }
            boolean r7 = r7.isDebugEnabled()     // Catch:{ all -> 0x0401 }
            if (r7 == 0) goto L_0x003f
            java.lang.Thread r7 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0039 }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0039 }
            java.lang.Thread r8 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0034 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0034 }
            r9.<init>()     // Catch:{ all -> 0x0034 }
            r9.append(r7)     // Catch:{ all -> 0x0034 }
            java.lang.String r10 = " - "
            r9.append(r10)     // Catch:{ all -> 0x0034 }
            org.eclipse.jetty.http.HttpURI r10 = r1._uri     // Catch:{ all -> 0x0034 }
            r9.append(r10)     // Catch:{ all -> 0x0034 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0034 }
            r8.setName(r9)     // Catch:{ all -> 0x0034 }
            goto L_0x0040
        L_0x0034:
            r0 = move-exception
            r3 = r0
            r4 = 0
            goto L_0x0406
        L_0x0039:
            r0 = move-exception
            r3 = r0
            r4 = 0
            r7 = 0
            goto L_0x0406
        L_0x003f:
            r7 = 0
        L_0x0040:
            org.eclipse.jetty.server.Server r8 = r1._server     // Catch:{ all -> 0x03fc }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x03fc }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x03fc }
            boolean r9 = r9.isContinuation()     // Catch:{ all -> 0x03fc }
            org.eclipse.jetty.server.Request r10 = r1._request     // Catch:{ all -> 0x03fc }
            org.eclipse.jetty.server.AsyncContinuation r10 = r10._async     // Catch:{ all -> 0x03fc }
            boolean r10 = r10.handling()     // Catch:{ all -> 0x03fc }
            if (r10 == 0) goto L_0x005e
            if (r8 == 0) goto L_0x005e
            boolean r10 = r8.isRunning()     // Catch:{ all -> 0x0034 }
            if (r10 == 0) goto L_0x005e
            r10 = 1
            goto L_0x005f
        L_0x005e:
            r10 = 0
        L_0x005f:
            r12 = r9
            r9 = 0
            r11 = 0
        L_0x0062:
            if (r10 == 0) goto L_0x036f
            org.eclipse.jetty.server.Request r10 = r1._request     // Catch:{ all -> 0x036a }
            r10.setHandled(r5)     // Catch:{ all -> 0x036a }
            r10 = 400(0x190, float:5.6E-43)
            r13 = 500(0x1f4, float:7.0E-43)
            org.eclipse.jetty.http.HttpURI r14 = r1._uri     // Catch:{ ContinuationThrowable -> 0x02f6, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            r14.getPort()     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            org.eclipse.jetty.http.HttpURI r14 = r1._uri     // Catch:{ Exception -> 0x0079 }
            java.lang.String r14 = r14.getDecodedPath()     // Catch:{ Exception -> 0x0079 }
            goto L_0x0091
        L_0x0079:
            r0 = move-exception
            r14 = r0
            org.eclipse.jetty.util.log.Logger r15 = LOG     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            java.lang.String r3 = "Failed UTF-8 decode for request path, trying ISO-8859-1"
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            r15.warn((java.lang.String) r3, (java.lang.Object[]) r4)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            org.eclipse.jetty.util.log.Logger r3 = LOG     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            r3.ignore(r14)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            org.eclipse.jetty.http.HttpURI r3 = r1._uri     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            java.lang.String r4 = "ISO-8859-1"
            java.lang.String r14 = r3.getDecodedPath(r4)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
        L_0x0091:
            java.lang.String r4 = org.eclipse.jetty.util.URIUtil.canonicalPath(r14)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01c6, all -> 0x01c1 }
            if (r4 != 0) goto L_0x00c7
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            java.lang.String r3 = r3.getMethod()     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            java.lang.String r15 = "CONNECT"
            boolean r3 = r3.equals(r15)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            if (r3 != 0) goto L_0x00c7
            if (r14 != 0) goto L_0x00c1
            org.eclipse.jetty.http.HttpURI r3 = r1._uri     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            java.lang.String r3 = r3.getScheme()     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            if (r3 == 0) goto L_0x00c1
            org.eclipse.jetty.http.HttpURI r3 = r1._uri     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            java.lang.String r3 = r3.getHost()     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            if (r3 == 0) goto L_0x00c1
            java.lang.String r4 = "/"
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            java.lang.String r14 = ""
            r3.setRequestURI(r14)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            goto L_0x00c7
        L_0x00c1:
            org.eclipse.jetty.http.HttpException r3 = new org.eclipse.jetty.http.HttpException     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r3.<init>(r10)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            throw r3     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
        L_0x00c7:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r3.setPathInfo(r4)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            org.eclipse.jetty.server.AbstractHttpConnection$Output r3 = r1._out     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            if (r3 == 0) goto L_0x00d5
            org.eclipse.jetty.server.AbstractHttpConnection$Output r3 = r1._out     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r3.reopen()     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
        L_0x00d5:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            boolean r3 = r3.isInitial()     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            if (r3 == 0) goto L_0x00f5
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            javax.servlet.DispatcherType r12 = javax.servlet.DispatcherType.REQUEST     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r3.setDispatcherType(r12)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            org.eclipse.jetty.server.Connector r3 = r1._connector     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            org.eclipse.jetty.io.EndPoint r12 = r1._endp     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            org.eclipse.jetty.server.Request r14 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r3.customize(r12, r14)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r8.handle(r1)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r4 = r9
            goto L_0x0171
        L_0x00f5:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            boolean r3 = r3.isExpired()     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            if (r3 == 0) goto L_0x0165
            if (r12 != 0) goto L_0x0165
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            java.lang.String r12 = "javax.servlet.error.exception"
            java.lang.Object r3 = r3.getAttribute(r12)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            org.eclipse.jetty.server.Response r9 = r1._response     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            if (r3 != 0) goto L_0x0112
            java.lang.String r12 = "Async Timeout"
            goto L_0x0114
        L_0x0112:
            java.lang.String r12 = "Async Exception"
        L_0x0114:
            r9.setStatus(r13, r12)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            java.lang.String r12 = "javax.servlet.error.status_code"
            java.lang.Integer r14 = new java.lang.Integer     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            r14.<init>(r13)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            r9.setAttribute(r12, r14)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            java.lang.String r12 = "javax.servlet.error.message"
            org.eclipse.jetty.server.Response r14 = r1._response     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            java.lang.String r14 = r14.getReason()     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            r9.setAttribute(r12, r14)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            javax.servlet.DispatcherType r12 = javax.servlet.DispatcherType.ERROR     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            r9.setDispatcherType(r12)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.handler.ContextHandler r9 = r9.getContextHandler()     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.handler.ErrorHandler r9 = r9.getErrorHandler()     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            boolean r12 = r9 instanceof org.eclipse.jetty.server.handler.ErrorHandler.ErrorPageMapper     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            if (r12 == 0) goto L_0x016d
            org.eclipse.jetty.server.handler.ErrorHandler$ErrorPageMapper r9 = (org.eclipse.jetty.server.handler.ErrorHandler.ErrorPageMapper) r9     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.Request r12 = r1._request     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.AsyncContinuation r12 = r12._async     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            javax.servlet.ServletRequest r12 = r12.getRequest()     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            javax.servlet.http.HttpServletRequest r12 = (javax.servlet.http.HttpServletRequest) r12     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            java.lang.String r9 = r9.getErrorPage(r12)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            if (r9 == 0) goto L_0x016d
            org.eclipse.jetty.server.Request r12 = r1._request     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.AsyncContinuation r12 = r12._async     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            org.eclipse.jetty.server.AsyncContinuation$AsyncEventState r12 = r12.getAsyncEventState()     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            r12.setPath(r9)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            goto L_0x016d
        L_0x0165:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            javax.servlet.DispatcherType r12 = javax.servlet.DispatcherType.ASYNC     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r3.setDispatcherType(r12)     // Catch:{ ContinuationThrowable -> 0x01bb, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x0214, Throwable -> 0x01b7, all -> 0x01c1 }
            r3 = r9
        L_0x016d:
            r8.handleAsync(r1)     // Catch:{ ContinuationThrowable -> 0x01b1, EofException -> 0x02a3, RuntimeIOException -> 0x025c, HttpException -> 0x01ac, Throwable -> 0x01b7, all -> 0x01a8 }
            r4 = r3
        L_0x0171:
            if (r11 == 0) goto L_0x0184
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x0366 }
            boolean r3 = r3.isAsyncStarted()     // Catch:{ all -> 0x0366 }
            if (r3 == 0) goto L_0x0184
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3.getAsyncContinuation()     // Catch:{ all -> 0x0366 }
            r3.errorComplete()     // Catch:{ all -> 0x0366 }
        L_0x0184:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ all -> 0x0366 }
            boolean r3 = r3.isContinuation()     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x0366 }
            boolean r9 = r9.unhandle()     // Catch:{ all -> 0x0366 }
            if (r9 != 0) goto L_0x01a2
            boolean r9 = r8.isRunning()     // Catch:{ all -> 0x0366 }
            if (r9 == 0) goto L_0x01a2
            org.eclipse.jetty.server.Server r9 = r1._server     // Catch:{ all -> 0x0366 }
            if (r9 == 0) goto L_0x01a2
            r9 = 1
            goto L_0x01a3
        L_0x01a2:
            r9 = 0
        L_0x01a3:
            r12 = r3
            r3 = r9
            r10 = 0
            goto L_0x0333
        L_0x01a8:
            r0 = move-exception
            r4 = r3
            goto L_0x0338
        L_0x01ac:
            r0 = move-exception
            r4 = r3
            r3 = r0
            goto L_0x0217
        L_0x01b1:
            r0 = move-exception
            r4 = r3
            r10 = 0
            r3 = r0
            goto L_0x02fa
        L_0x01b7:
            r0 = move-exception
            r3 = r4
            r4 = r0
            goto L_0x01c9
        L_0x01bb:
            r0 = move-exception
            r3 = r0
            r4 = r9
            r10 = 0
            goto L_0x02fa
        L_0x01c1:
            r0 = move-exception
            r3 = r0
            r4 = r9
            goto L_0x0339
        L_0x01c6:
            r0 = move-exception
            r4 = r0
            r3 = 0
        L_0x01c9:
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x0337 }
            org.eclipse.jetty.http.HttpURI r12 = r1._uri     // Catch:{ all -> 0x0337 }
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x0337 }
            r9.warn((java.lang.String) r12, (java.lang.Throwable) r4)     // Catch:{ all -> 0x0337 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x02f2 }
            r9.setHandled(r6)     // Catch:{ all -> 0x02f2 }
            org.eclipse.jetty.http.Generator r9 = r1._generator     // Catch:{ all -> 0x02f2 }
            if (r3 != 0) goto L_0x01df
            r3 = 0
            goto L_0x01e2
        L_0x01df:
            r3 = 0
            r10 = 500(0x1f4, float:7.0E-43)
        L_0x01e2:
            r9.sendError(r10, r3, r3, r6)     // Catch:{ all -> 0x02f2 }
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isAsyncStarted()     // Catch:{ all -> 0x029e }
            if (r3 == 0) goto L_0x01f6
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3.getAsyncContinuation()     // Catch:{ all -> 0x029e }
            r3.errorComplete()     // Catch:{ all -> 0x029e }
        L_0x01f6:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isContinuation()     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x029e }
            boolean r9 = r9.unhandle()     // Catch:{ all -> 0x029e }
            if (r9 != 0) goto L_0x0297
            boolean r9 = r8.isRunning()     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x0297
            org.eclipse.jetty.server.Server r9 = r1._server     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x0297
            goto L_0x0295
        L_0x0214:
            r0 = move-exception
            r3 = r0
            r4 = r9
        L_0x0217:
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x0337 }
            r9.debug(r3)     // Catch:{ all -> 0x0337 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x02f2 }
            r9.setHandled(r6)     // Catch:{ all -> 0x02f2 }
            org.eclipse.jetty.server.Response r9 = r1._response     // Catch:{ all -> 0x02f2 }
            int r10 = r3.getStatus()     // Catch:{ all -> 0x02f2 }
            java.lang.String r3 = r3.getReason()     // Catch:{ all -> 0x02f2 }
            r9.sendError(r10, r3)     // Catch:{ all -> 0x02f2 }
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isAsyncStarted()     // Catch:{ all -> 0x029e }
            if (r3 == 0) goto L_0x023f
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3.getAsyncContinuation()     // Catch:{ all -> 0x029e }
            r3.errorComplete()     // Catch:{ all -> 0x029e }
        L_0x023f:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isContinuation()     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x029e }
            boolean r9 = r9.unhandle()     // Catch:{ all -> 0x029e }
            if (r9 != 0) goto L_0x0297
            boolean r9 = r8.isRunning()     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x0297
            org.eclipse.jetty.server.Server r9 = r1._server     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x0297
            goto L_0x0295
        L_0x025c:
            r0 = move-exception
            r4 = r0
            org.eclipse.jetty.util.log.Logger r3 = LOG     // Catch:{ all -> 0x0337 }
            r3.debug(r4)     // Catch:{ all -> 0x0337 }
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x02f2 }
            r3.setHandled(r6)     // Catch:{ all -> 0x02f2 }
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isAsyncStarted()     // Catch:{ all -> 0x029e }
            if (r3 == 0) goto L_0x0279
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3.getAsyncContinuation()     // Catch:{ all -> 0x029e }
            r3.errorComplete()     // Catch:{ all -> 0x029e }
        L_0x0279:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isContinuation()     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x029e }
            boolean r9 = r9.unhandle()     // Catch:{ all -> 0x029e }
            if (r9 != 0) goto L_0x0297
            boolean r9 = r8.isRunning()     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x0297
            org.eclipse.jetty.server.Server r9 = r1._server     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x0297
        L_0x0295:
            r9 = 1
            goto L_0x0298
        L_0x0297:
            r9 = 0
        L_0x0298:
            r12 = r3
            r3 = r9
            r10 = 0
        L_0x029b:
            r11 = 1
            goto L_0x0333
        L_0x029e:
            r0 = move-exception
            r3 = r0
            r11 = 1
            goto L_0x0407
        L_0x02a3:
            r0 = move-exception
            r4 = r0
            org.eclipse.jetty.util.log.Logger r3 = LOG     // Catch:{ all -> 0x0337 }
            r3.debug(r4)     // Catch:{ all -> 0x0337 }
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x02f2 }
            r3.setHandled(r6)     // Catch:{ all -> 0x02f2 }
            org.eclipse.jetty.server.Response r3 = r1._response     // Catch:{ all -> 0x02f2 }
            boolean r3 = r3.isCommitted()     // Catch:{ all -> 0x02f2 }
            if (r3 != 0) goto L_0x02be
            org.eclipse.jetty.http.Generator r3 = r1._generator     // Catch:{ all -> 0x02f2 }
            r10 = 0
            r3.sendError(r13, r10, r10, r6)     // Catch:{ all -> 0x02f2 }
            goto L_0x02bf
        L_0x02be:
            r10 = 0
        L_0x02bf:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isAsyncStarted()     // Catch:{ all -> 0x029e }
            if (r3 == 0) goto L_0x02d0
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3.getAsyncContinuation()     // Catch:{ all -> 0x029e }
            r3.errorComplete()     // Catch:{ all -> 0x029e }
        L_0x02d0:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ all -> 0x029e }
            boolean r3 = r3.isContinuation()     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x029e }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x029e }
            boolean r9 = r9.unhandle()     // Catch:{ all -> 0x029e }
            if (r9 != 0) goto L_0x02ee
            boolean r9 = r8.isRunning()     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x02ee
            org.eclipse.jetty.server.Server r9 = r1._server     // Catch:{ all -> 0x029e }
            if (r9 == 0) goto L_0x02ee
            r9 = 1
            goto L_0x02ef
        L_0x02ee:
            r9 = 0
        L_0x02ef:
            r12 = r3
            r3 = r9
            goto L_0x029b
        L_0x02f2:
            r0 = move-exception
            r3 = r0
            r11 = 1
            goto L_0x0339
        L_0x02f6:
            r0 = move-exception
            r10 = 0
            r3 = r0
            r4 = r9
        L_0x02fa:
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ all -> 0x0337 }
            r9.ignore(r3)     // Catch:{ all -> 0x0337 }
            if (r11 == 0) goto L_0x0312
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x0366 }
            boolean r3 = r3.isAsyncStarted()     // Catch:{ all -> 0x0366 }
            if (r3 == 0) goto L_0x0312
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3.getAsyncContinuation()     // Catch:{ all -> 0x0366 }
            r3.errorComplete()     // Catch:{ all -> 0x0366 }
        L_0x0312:
            org.eclipse.jetty.server.Request r3 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async     // Catch:{ all -> 0x0366 }
            boolean r3 = r3.isContinuation()     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x0366 }
            boolean r9 = r9.unhandle()     // Catch:{ all -> 0x0366 }
            if (r9 != 0) goto L_0x0330
            boolean r9 = r8.isRunning()     // Catch:{ all -> 0x0366 }
            if (r9 == 0) goto L_0x0330
            org.eclipse.jetty.server.Server r9 = r1._server     // Catch:{ all -> 0x0366 }
            if (r9 == 0) goto L_0x0330
            r9 = 1
            goto L_0x0331
        L_0x0330:
            r9 = 0
        L_0x0331:
            r12 = r3
            r3 = r9
        L_0x0333:
            r9 = r4
            r10 = r3
            goto L_0x0062
        L_0x0337:
            r0 = move-exception
        L_0x0338:
            r3 = r0
        L_0x0339:
            if (r11 == 0) goto L_0x034c
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x0366 }
            boolean r9 = r9.isAsyncStarted()     // Catch:{ all -> 0x0366 }
            if (r9 == 0) goto L_0x034c
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9.getAsyncContinuation()     // Catch:{ all -> 0x0366 }
            r9.errorComplete()     // Catch:{ all -> 0x0366 }
        L_0x034c:
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x0366 }
            r9.isContinuation()     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.Request r9 = r1._request     // Catch:{ all -> 0x0366 }
            org.eclipse.jetty.server.AsyncContinuation r9 = r9._async     // Catch:{ all -> 0x0366 }
            boolean r9 = r9.unhandle()     // Catch:{ all -> 0x0366 }
            if (r9 != 0) goto L_0x0365
            boolean r8 = r8.isRunning()     // Catch:{ all -> 0x0366 }
            if (r8 == 0) goto L_0x0365
            org.eclipse.jetty.server.Server r8 = r1._server     // Catch:{ all -> 0x0366 }
        L_0x0365:
            throw r3     // Catch:{ all -> 0x0366 }
        L_0x0366:
            r0 = move-exception
            r3 = r0
            goto L_0x0407
        L_0x036a:
            r0 = move-exception
            r3 = r0
            r4 = r9
            goto L_0x0407
        L_0x036f:
            if (r7 == 0) goto L_0x0378
            java.lang.Thread r3 = java.lang.Thread.currentThread()
            r3.setName(r7)
        L_0x0378:
            org.eclipse.jetty.server.Request r3 = r1._request
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async
            boolean r3 = r3.isUncompleted()
            if (r3 == 0) goto L_0x03fb
            org.eclipse.jetty.server.Request r3 = r1._request
            org.eclipse.jetty.server.AsyncContinuation r3 = r3._async
            r3.doComplete(r9)
            boolean r3 = r1._expect100Continue
            if (r3 == 0) goto L_0x03a3
            org.eclipse.jetty.util.log.Logger r3 = LOG
            java.lang.Object[] r4 = new java.lang.Object[r5]
            r3.debug((java.lang.String) r2, (java.lang.Object[]) r4)
            r1._expect100Continue = r5
            org.eclipse.jetty.server.Response r2 = r1._response
            boolean r2 = r2.isCommitted()
            if (r2 != 0) goto L_0x03a3
            org.eclipse.jetty.http.Generator r2 = r1._generator
            r2.setPersistent(r5)
        L_0x03a3:
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            boolean r2 = r2.isOpen()
            if (r2 == 0) goto L_0x03f1
            if (r11 == 0) goto L_0x03c5
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            r2.shutdownOutput()
            org.eclipse.jetty.http.Generator r2 = r1._generator
            r2.setPersistent(r5)
            org.eclipse.jetty.http.Generator r2 = r1._generator
            boolean r2 = r2.isComplete()
            if (r2 != 0) goto L_0x03f6
            org.eclipse.jetty.server.Response r2 = r1._response
            r2.complete()
            goto L_0x03f6
        L_0x03c5:
            org.eclipse.jetty.server.Response r2 = r1._response
            boolean r2 = r2.isCommitted()
            if (r2 != 0) goto L_0x03dc
            org.eclipse.jetty.server.Request r2 = r1._request
            boolean r2 = r2.isHandled()
            if (r2 != 0) goto L_0x03dc
            org.eclipse.jetty.server.Response r2 = r1._response
            r3 = 404(0x194, float:5.66E-43)
            r2.sendError(r3)
        L_0x03dc:
            org.eclipse.jetty.server.Response r2 = r1._response
            r2.complete()
            org.eclipse.jetty.http.Generator r2 = r1._generator
            boolean r2 = r2.isPersistent()
            if (r2 == 0) goto L_0x03f6
            org.eclipse.jetty.server.Connector r2 = r1._connector
            org.eclipse.jetty.io.EndPoint r3 = r1._endp
            r2.persist(r3)
            goto L_0x03f6
        L_0x03f1:
            org.eclipse.jetty.server.Response r2 = r1._response
            r2.complete()
        L_0x03f6:
            org.eclipse.jetty.server.Request r2 = r1._request
            r2.setHandled(r6)
        L_0x03fb:
            return
        L_0x03fc:
            r0 = move-exception
            r10 = 0
            r3 = r0
            r4 = r10
            goto L_0x0406
        L_0x0401:
            r0 = move-exception
            r10 = 0
            r3 = r0
            r4 = r10
            r7 = r4
        L_0x0406:
            r11 = 0
        L_0x0407:
            if (r7 == 0) goto L_0x0410
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.setName(r7)
        L_0x0410:
            org.eclipse.jetty.server.Request r7 = r1._request
            org.eclipse.jetty.server.AsyncContinuation r7 = r7._async
            boolean r7 = r7.isUncompleted()
            if (r7 == 0) goto L_0x0493
            org.eclipse.jetty.server.Request r7 = r1._request
            org.eclipse.jetty.server.AsyncContinuation r7 = r7._async
            r7.doComplete(r4)
            boolean r4 = r1._expect100Continue
            if (r4 == 0) goto L_0x043b
            org.eclipse.jetty.util.log.Logger r4 = LOG
            java.lang.Object[] r7 = new java.lang.Object[r5]
            r4.debug((java.lang.String) r2, (java.lang.Object[]) r7)
            r1._expect100Continue = r5
            org.eclipse.jetty.server.Response r2 = r1._response
            boolean r2 = r2.isCommitted()
            if (r2 != 0) goto L_0x043b
            org.eclipse.jetty.http.Generator r2 = r1._generator
            r2.setPersistent(r5)
        L_0x043b:
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            boolean r2 = r2.isOpen()
            if (r2 == 0) goto L_0x0489
            if (r11 == 0) goto L_0x045d
            org.eclipse.jetty.io.EndPoint r2 = r1._endp
            r2.shutdownOutput()
            org.eclipse.jetty.http.Generator r2 = r1._generator
            r2.setPersistent(r5)
            org.eclipse.jetty.http.Generator r2 = r1._generator
            boolean r2 = r2.isComplete()
            if (r2 != 0) goto L_0x048e
            org.eclipse.jetty.server.Response r2 = r1._response
            r2.complete()
            goto L_0x048e
        L_0x045d:
            org.eclipse.jetty.server.Response r2 = r1._response
            boolean r2 = r2.isCommitted()
            if (r2 != 0) goto L_0x0474
            org.eclipse.jetty.server.Request r2 = r1._request
            boolean r2 = r2.isHandled()
            if (r2 != 0) goto L_0x0474
            org.eclipse.jetty.server.Response r2 = r1._response
            r4 = 404(0x194, float:5.66E-43)
            r2.sendError(r4)
        L_0x0474:
            org.eclipse.jetty.server.Response r2 = r1._response
            r2.complete()
            org.eclipse.jetty.http.Generator r2 = r1._generator
            boolean r2 = r2.isPersistent()
            if (r2 == 0) goto L_0x048e
            org.eclipse.jetty.server.Connector r2 = r1._connector
            org.eclipse.jetty.io.EndPoint r4 = r1._endp
            r2.persist(r4)
            goto L_0x048e
        L_0x0489:
            org.eclipse.jetty.server.Response r2 = r1._response
            r2.complete()
        L_0x048e:
            org.eclipse.jetty.server.Request r2 = r1._request
            r2.setHandled(r6)
        L_0x0493:
            goto L_0x0495
        L_0x0494:
            throw r3
        L_0x0495:
            goto L_0x0494
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractHttpConnection.handleRequest():void");
    }

    public void commitResponse(boolean z) throws IOException {
        if (!this._generator.isCommitted()) {
            this._generator.setResponse(this._response.getStatus(), this._response.getReason());
            try {
                if (this._expect100Continue && this._response.getStatus() != 100) {
                    this._generator.setPersistent(false);
                }
                this._generator.completeHeader(this._responseFields, z);
            } catch (RuntimeException e) {
                Logger logger = LOG;
                logger.warn("header full: " + e, new Object[0]);
                this._response.reset();
                this._generator.reset();
                this._generator.setResponse(500, (String) null);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                throw new HttpException(500);
            }
        }
        if (z) {
            this._generator.complete();
        }
    }

    public void completeResponse() throws IOException {
        if (!this._generator.isCommitted()) {
            this._generator.setResponse(this._response.getStatus(), this._response.getReason());
            try {
                this._generator.completeHeader(this._responseFields, true);
            } catch (RuntimeException e) {
                Logger logger = LOG;
                logger.warn("header full: " + e, new Object[0]);
                LOG.debug(e);
                this._response.reset();
                this._generator.reset();
                this._generator.setResponse(500, (String) null);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                throw new HttpException(500);
            }
        }
        this._generator.complete();
    }

    public void flushResponse() throws IOException {
        try {
            commitResponse(false);
            this._generator.flushBuffer();
        } catch (IOException e) {
            e = e;
            if (!(e instanceof EofException)) {
                e = new EofException((Throwable) e);
            }
            throw e;
        }
    }

    public Generator getGenerator() {
        return this._generator;
    }

    public boolean isIncluding() {
        return this._include > 0;
    }

    public void include() {
        this._include++;
    }

    public void included() {
        this._include--;
        if (this._out != null) {
            this._out.reopen();
        }
    }

    public boolean isIdle() {
        return this._generator.isIdle() && (this._parser.isIdle() || this._delayedHandling);
    }

    public boolean isSuspended() {
        return this._request.getAsyncContinuation().isSuspended();
    }

    public void onClose() {
        LOG.debug("closed {}", this);
    }

    public boolean isExpecting100Continues() {
        return this._expect100Continue;
    }

    public boolean isExpecting102Processing() {
        return this._expect102Processing;
    }

    public int getMaxIdleTime() {
        if (this._connector.isLowResources() && this._endp.getMaxIdleTime() == this._connector.getMaxIdleTime()) {
            return this._connector.getLowResourceMaxIdleTime();
        }
        if (this._endp.getMaxIdleTime() > 0) {
            return this._endp.getMaxIdleTime();
        }
        return this._connector.getMaxIdleTime();
    }

    public String toString() {
        return String.format("%s,g=%s,p=%s,r=%d", new Object[]{super.toString(), this._generator, this._parser, Integer.valueOf(this._requests)});
    }

    /* access modifiers changed from: protected */
    public void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        Buffer asImmutableBuffer = buffer2.asImmutableBuffer();
        this._host = false;
        this._expect = false;
        this._expect100Continue = false;
        this._expect102Processing = false;
        this._delayedHandling = false;
        this._charset = null;
        if (this._request.getTimeStamp() == 0) {
            this._request.setTimeStamp(System.currentTimeMillis());
        }
        this._request.setMethod(buffer.toString());
        try {
            this._head = false;
            int ordinal = HttpMethods.CACHE.getOrdinal(buffer);
            if (ordinal == 3) {
                this._head = true;
                this._uri.parse(asImmutableBuffer.array(), asImmutableBuffer.getIndex(), asImmutableBuffer.length());
            } else if (ordinal != 8) {
                this._uri.parse(asImmutableBuffer.array(), asImmutableBuffer.getIndex(), asImmutableBuffer.length());
            } else {
                this._uri.parseConnect(asImmutableBuffer.array(), asImmutableBuffer.getIndex(), asImmutableBuffer.length());
            }
            this._request.setUri(this._uri);
            if (buffer3 == null) {
                this._request.setProtocol("");
                this._version = 9;
                return;
            }
            BufferCache.CachedBuffer cachedBuffer = HttpVersions.CACHE.get(buffer3);
            if (cachedBuffer != null) {
                this._version = HttpVersions.CACHE.getOrdinal((Buffer) cachedBuffer);
                if (this._version <= 0) {
                    this._version = 10;
                }
                this._request.setProtocol(cachedBuffer.toString());
                return;
            }
            throw new HttpException(400, (String) null);
        } catch (Exception e) {
            LOG.debug(e);
            if (e instanceof HttpException) {
                throw ((HttpException) e);
            }
            throw new HttpException(400, (String) null, e);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        if (r0 != 40) goto L_0x0096;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parsedHeader(org.eclipse.jetty.p119io.Buffer r8, org.eclipse.jetty.p119io.Buffer r9) throws java.io.IOException {
        /*
            r7 = this;
            org.eclipse.jetty.http.HttpHeaders r0 = org.eclipse.jetty.http.HttpHeaders.CACHE
            int r0 = r0.getOrdinal((org.eclipse.jetty.p119io.Buffer) r8)
            r1 = 16
            if (r0 == r1) goto L_0x008a
            r1 = 21
            if (r0 == r1) goto L_0x0083
            r1 = 24
            r2 = 1
            if (r0 == r1) goto L_0x0021
            r1 = 27
            if (r0 == r1) goto L_0x001d
            r1 = 40
            if (r0 == r1) goto L_0x0083
            goto L_0x0096
        L_0x001d:
            r7._host = r2
            goto L_0x0096
        L_0x0021:
            int r0 = r7._version
            r1 = 11
            if (r0 < r1) goto L_0x0096
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            org.eclipse.jetty.io.Buffer r9 = r0.lookup((org.eclipse.jetty.p119io.Buffer) r9)
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            int r0 = r0.getOrdinal((org.eclipse.jetty.p119io.Buffer) r9)
            r1 = 6
            if (r0 == r1) goto L_0x007c
            r3 = 7
            if (r0 == r3) goto L_0x0075
            java.lang.String r0 = r9.toString()
            java.lang.String r4 = ","
            java.lang.String[] r0 = r0.split(r4)
            r4 = 0
        L_0x0044:
            if (r0 == 0) goto L_0x0096
            int r5 = r0.length
            if (r4 >= r5) goto L_0x0096
            org.eclipse.jetty.http.HttpHeaderValues r5 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            r6 = r0[r4]
            java.lang.String r6 = r6.trim()
            org.eclipse.jetty.io.BufferCache$CachedBuffer r5 = r5.get((java.lang.String) r6)
            if (r5 != 0) goto L_0x005a
            r7._expect = r2
            goto L_0x0072
        L_0x005a:
            int r5 = r5.getOrdinal()
            if (r5 == r1) goto L_0x006c
            if (r5 == r3) goto L_0x0065
            r7._expect = r2
            goto L_0x0072
        L_0x0065:
            org.eclipse.jetty.http.Generator r5 = r7._generator
            boolean r5 = r5 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect102Processing = r5
            goto L_0x0072
        L_0x006c:
            org.eclipse.jetty.http.Generator r5 = r7._generator
            boolean r5 = r5 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect100Continue = r5
        L_0x0072:
            int r4 = r4 + 1
            goto L_0x0044
        L_0x0075:
            org.eclipse.jetty.http.Generator r0 = r7._generator
            boolean r0 = r0 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect102Processing = r0
            goto L_0x0096
        L_0x007c:
            org.eclipse.jetty.http.Generator r0 = r7._generator
            boolean r0 = r0 instanceof org.eclipse.jetty.http.HttpGenerator
            r7._expect100Continue = r0
            goto L_0x0096
        L_0x0083:
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            org.eclipse.jetty.io.Buffer r9 = r0.lookup((org.eclipse.jetty.p119io.Buffer) r9)
            goto L_0x0096
        L_0x008a:
            org.eclipse.jetty.io.BufferCache r0 = org.eclipse.jetty.http.MimeTypes.CACHE
            org.eclipse.jetty.io.Buffer r9 = r0.lookup((org.eclipse.jetty.p119io.Buffer) r9)
            java.lang.String r0 = org.eclipse.jetty.http.MimeTypes.getCharsetFromContentType(r9)
            r7._charset = r0
        L_0x0096:
            org.eclipse.jetty.http.HttpFields r0 = r7._requestFields
            r0.add((org.eclipse.jetty.p119io.Buffer) r8, (org.eclipse.jetty.p119io.Buffer) r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractHttpConnection.parsedHeader(org.eclipse.jetty.io.Buffer, org.eclipse.jetty.io.Buffer):void");
    }

    /* access modifiers changed from: protected */
    public void headerComplete() throws IOException {
        if (this._endp.isOutputShutdown()) {
            this._endp.close();
            return;
        }
        this._requests++;
        this._generator.setVersion(this._version);
        switch (this._version) {
            case 10:
                this._generator.setHead(this._head);
                if (this._parser.isPersistent()) {
                    this._responseFields.add(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.KEEP_ALIVE_BUFFER);
                    this._generator.setPersistent(true);
                } else if (HttpMethods.CONNECT.equals(this._request.getMethod())) {
                    this._generator.setPersistent(true);
                    this._parser.setPersistent(true);
                }
                if (this._server.getSendDateHeader()) {
                    this._generator.setDate(this._request.getTimeStampBuffer());
                    break;
                }
                break;
            case 11:
                this._generator.setHead(this._head);
                if (!this._parser.isPersistent()) {
                    this._responseFields.add(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                    this._generator.setPersistent(false);
                }
                if (this._server.getSendDateHeader()) {
                    this._generator.setDate(this._request.getTimeStampBuffer());
                }
                if (!this._host) {
                    LOG.debug("!host {}", this);
                    this._generator.setResponse(400, (String) null);
                    this._responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                    this._generator.completeHeader(this._responseFields, true);
                    this._generator.complete();
                    return;
                } else if (this._expect) {
                    LOG.debug("!expectation {}", this);
                    this._generator.setResponse(417, (String) null);
                    this._responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                    this._generator.completeHeader(this._responseFields, true);
                    this._generator.complete();
                    return;
                }
                break;
        }
        String str = this._charset;
        if (str != null) {
            this._request.setCharacterEncodingUnchecked(str);
        }
        if ((((HttpParser) this._parser).getContentLength() > 0 || ((HttpParser) this._parser).isChunking()) && !this._expect100Continue) {
            this._delayedHandling = true;
        } else {
            handleRequest();
        }
    }

    /* access modifiers changed from: protected */
    public void content(Buffer buffer) throws IOException {
        if (this._delayedHandling) {
            this._delayedHandling = false;
            handleRequest();
        }
    }

    public void messageComplete(long j) throws IOException {
        if (this._delayedHandling) {
            this._delayedHandling = false;
            handleRequest();
        }
    }

    public void earlyEOF() {
        this._earlyEOF = true;
    }

    private class RequestHandler extends HttpParser.EventHandler {
        private RequestHandler() {
        }

        public void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            AbstractHttpConnection.this.startRequest(buffer, buffer2, buffer3);
        }

        public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException {
            AbstractHttpConnection.this.parsedHeader(buffer, buffer2);
        }

        public void headerComplete() throws IOException {
            AbstractHttpConnection.this.headerComplete();
        }

        public void content(Buffer buffer) throws IOException {
            AbstractHttpConnection.this.content(buffer);
        }

        public void messageComplete(long j) throws IOException {
            AbstractHttpConnection.this.messageComplete(j);
        }

        public void startResponse(Buffer buffer, int i, Buffer buffer2) {
            if (AbstractHttpConnection.LOG.isDebugEnabled()) {
                Logger access$100 = AbstractHttpConnection.LOG;
                access$100.debug("Bad request!: " + buffer + " " + i + " " + buffer2, new Object[0]);
            }
        }

        public void earlyEOF() {
            AbstractHttpConnection.this.earlyEOF();
        }
    }

    public class Output extends HttpOutput {
        Output() {
            super(AbstractHttpConnection.this);
        }

        public void close() throws IOException {
            if (!isClosed()) {
                if (AbstractHttpConnection.this.isIncluding() || this._generator.isCommitted()) {
                    AbstractHttpConnection.this.flushResponse();
                } else {
                    AbstractHttpConnection.this.commitResponse(true);
                }
                super.close();
            }
        }

        public void flush() throws IOException {
            if (!this._generator.isCommitted()) {
                AbstractHttpConnection.this.commitResponse(false);
            }
            super.flush();
        }

        public void print(String str) throws IOException {
            if (!isClosed()) {
                AbstractHttpConnection.this.getPrintWriter((String) null).print(str);
                return;
            }
            throw new IOException("Closed");
        }

        public void sendResponse(Buffer buffer) throws IOException {
            ((HttpGenerator) this._generator).sendResponse(buffer);
        }

        public void sendContent(Object obj) throws IOException {
            if (isClosed()) {
                throw new IOException("Closed");
            } else if (!this._generator.isWritten()) {
                Resource resource = null;
                if (obj instanceof HttpContent) {
                    HttpContent httpContent = (HttpContent) obj;
                    Buffer contentType = httpContent.getContentType();
                    if (contentType != null && !AbstractHttpConnection.this._responseFields.containsKey(HttpHeaders.CONTENT_TYPE_BUFFER)) {
                        String setCharacterEncoding = AbstractHttpConnection.this._response.getSetCharacterEncoding();
                        if (setCharacterEncoding == null) {
                            AbstractHttpConnection.this._responseFields.add(HttpHeaders.CONTENT_TYPE_BUFFER, contentType);
                        } else if (contentType instanceof BufferCache.CachedBuffer) {
                            BufferCache.CachedBuffer associate = ((BufferCache.CachedBuffer) contentType).getAssociate(setCharacterEncoding);
                            if (associate != null) {
                                AbstractHttpConnection.this._responseFields.put(HttpHeaders.CONTENT_TYPE_BUFFER, (Buffer) associate);
                            } else {
                                HttpFields httpFields = AbstractHttpConnection.this._responseFields;
                                Buffer buffer = HttpHeaders.CONTENT_TYPE_BUFFER;
                                httpFields.put(buffer, contentType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(setCharacterEncoding, ";= "));
                            }
                        } else {
                            HttpFields httpFields2 = AbstractHttpConnection.this._responseFields;
                            Buffer buffer2 = HttpHeaders.CONTENT_TYPE_BUFFER;
                            httpFields2.put(buffer2, contentType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(setCharacterEncoding, ";= "));
                        }
                    }
                    if (httpContent.getContentLength() > 0) {
                        AbstractHttpConnection.this._responseFields.putLongField(HttpHeaders.CONTENT_LENGTH_BUFFER, httpContent.getContentLength());
                    }
                    Buffer lastModified = httpContent.getLastModified();
                    long lastModified2 = httpContent.getResource().lastModified();
                    if (lastModified != null) {
                        AbstractHttpConnection.this._responseFields.put(HttpHeaders.LAST_MODIFIED_BUFFER, lastModified);
                    } else if (!(httpContent.getResource() == null || lastModified2 == -1)) {
                        AbstractHttpConnection.this._responseFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, lastModified2);
                    }
                    Buffer eTag = httpContent.getETag();
                    if (eTag != null) {
                        AbstractHttpConnection.this._responseFields.put(HttpHeaders.ETAG_BUFFER, eTag);
                    }
                    Buffer directBuffer = (AbstractHttpConnection.this._connector instanceof NIOConnector) && ((NIOConnector) AbstractHttpConnection.this._connector).getUseDirectBuffers() && !(AbstractHttpConnection.this._connector instanceof SslConnector) ? httpContent.getDirectBuffer() : httpContent.getIndirectBuffer();
                    obj = directBuffer == null ? httpContent.getInputStream() : directBuffer;
                } else if (obj instanceof Resource) {
                    resource = (Resource) obj;
                    AbstractHttpConnection.this._responseFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, resource.lastModified());
                    obj = resource.getInputStream();
                }
                if (obj instanceof Buffer) {
                    this._generator.addContent((Buffer) obj, true);
                    AbstractHttpConnection.this.commitResponse(true);
                } else if (obj instanceof InputStream) {
                    InputStream inputStream = (InputStream) obj;
                    try {
                        int readFrom = this._generator.getUncheckedBuffer().readFrom(inputStream, this._generator.prepareUncheckedAddContent());
                        while (readFrom >= 0) {
                            this._generator.completeUncheckedAddContent();
                            AbstractHttpConnection.this._out.flush();
                            readFrom = this._generator.getUncheckedBuffer().readFrom(inputStream, this._generator.prepareUncheckedAddContent());
                        }
                        this._generator.completeUncheckedAddContent();
                        AbstractHttpConnection.this._out.flush();
                        if (resource == null) {
                            inputStream.close();
                        }
                    } finally {
                        if (resource != null) {
                            resource.release();
                        } else {
                            inputStream.close();
                        }
                    }
                } else {
                    throw new IllegalArgumentException("unknown content type?");
                }
            } else {
                throw new IllegalStateException("!empty");
            }
        }
    }

    public class OutputWriter extends HttpWriter {
        OutputWriter() {
            super(AbstractHttpConnection.this._out);
        }
    }
}
