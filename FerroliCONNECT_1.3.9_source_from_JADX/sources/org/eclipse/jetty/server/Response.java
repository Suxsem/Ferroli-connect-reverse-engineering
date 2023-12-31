package org.eclipse.jetty.server;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.taobao.accs.common.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.BufferCache;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.util.ByteArrayISO8859Writer;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class Response implements HttpServletResponse {
    public static final String HTTP_ONLY_COMMENT = "__HTTP_ONLY__";
    private static final Logger LOG = Log.getLogger((Class<?>) Response.class);
    public static final int NONE = 0;
    public static final String SET_INCLUDE_HEADER_PREFIX = "org.eclipse.jetty.server.include.";
    public static final int STREAM = 1;
    public static final int WRITER = 2;
    private BufferCache.CachedBuffer _cachedMimeType;
    private String _characterEncoding;
    private final AbstractHttpConnection _connection;
    private String _contentType;
    private boolean _explicitEncoding;
    private Locale _locale;
    private String _mimeType;
    private volatile int _outputState;
    private String _reason;
    private int _status = 200;
    private PrintWriter _writer;

    public static Response getResponse(HttpServletResponse httpServletResponse) {
        if (httpServletResponse instanceof Response) {
            return (Response) httpServletResponse;
        }
        return AbstractHttpConnection.getCurrentConnection().getResponse();
    }

    public Response(AbstractHttpConnection abstractHttpConnection) {
        this._connection = abstractHttpConnection;
    }

    /* access modifiers changed from: protected */
    public void recycle() {
        this._status = 200;
        this._reason = null;
        this._locale = null;
        this._mimeType = null;
        this._cachedMimeType = null;
        this._characterEncoding = null;
        this._explicitEncoding = false;
        this._contentType = null;
        this._writer = null;
        this._outputState = 0;
    }

    public void addCookie(HttpCookie httpCookie) {
        this._connection.getResponseFields().addSetCookie(httpCookie);
    }

    public void addCookie(Cookie cookie) {
        String str;
        boolean z;
        String comment = cookie.getComment();
        if (comment == null || comment.indexOf(HTTP_ONLY_COMMENT) < 0) {
            str = comment;
            z = false;
        } else {
            String trim = comment.replace(HTTP_ONLY_COMMENT, "").trim();
            if (trim.length() == 0) {
                trim = null;
            }
            str = trim;
            z = true;
        }
        this._connection.getResponseFields().addSetCookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), (long) cookie.getMaxAge(), str, cookie.getSecure(), z || cookie.isHttpOnly(), cookie.getVersion());
    }

    public boolean containsHeader(String str) {
        return this._connection.getResponseFields().containsKey(str);
    }

    public String encodeURL(String str) {
        HttpURI httpURI;
        Request request = this._connection.getRequest();
        SessionManager sessionManager = request.getSessionManager();
        if (sessionManager == null) {
            return str;
        }
        if (!sessionManager.isCheckingRemoteSessionIdEncoding() || !URIUtil.hasScheme(str)) {
            httpURI = null;
        } else {
            httpURI = new HttpURI(str);
            String path = httpURI.getPath();
            if (path == null) {
                path = "";
            }
            int port = httpURI.getPort();
            if (port < 0) {
                port = "https".equalsIgnoreCase(httpURI.getScheme()) ? Constants.PORT : 80;
            }
            if (!request.getServerName().equalsIgnoreCase(httpURI.getHost()) || request.getServerPort() != port || !path.startsWith(request.getContextPath())) {
                return str;
            }
        }
        String sessionIdPathParameterNamePrefix = sessionManager.getSessionIdPathParameterNamePrefix();
        if (sessionIdPathParameterNamePrefix == null) {
            return str;
        }
        if (str == null) {
            return null;
        }
        if ((!sessionManager.isUsingCookies() || !request.isRequestedSessionIdFromCookie()) && sessionManager.isUsingURLs()) {
            HttpSession session = request.getSession(false);
            if (session == null || !sessionManager.isValid(session)) {
                return str;
            }
            String nodeId = sessionManager.getNodeId(session);
            if (httpURI == null) {
                httpURI = new HttpURI(str);
            }
            int indexOf = str.indexOf(sessionIdPathParameterNamePrefix);
            if (indexOf != -1) {
                int indexOf2 = str.indexOf("?", indexOf);
                if (indexOf2 < 0) {
                    indexOf2 = str.indexOf(MqttTopic.MULTI_LEVEL_WILDCARD, indexOf);
                }
                if (indexOf2 <= indexOf) {
                    return str.substring(0, indexOf + sessionIdPathParameterNamePrefix.length()) + nodeId;
                }
                return str.substring(0, indexOf + sessionIdPathParameterNamePrefix.length()) + nodeId + str.substring(indexOf2);
            }
            int indexOf3 = str.indexOf(63);
            if (indexOf3 < 0) {
                indexOf3 = str.indexOf(35);
            }
            String str2 = "/";
            if (indexOf3 < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                if ((!"https".equalsIgnoreCase(httpURI.getScheme()) && !"http".equalsIgnoreCase(httpURI.getScheme())) || httpURI.getPath() != null) {
                    str2 = "";
                }
                sb.append(str2);
                sb.append(sessionIdPathParameterNamePrefix);
                sb.append(nodeId);
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str.substring(0, indexOf3));
            if ((!"https".equalsIgnoreCase(httpURI.getScheme()) && !"http".equalsIgnoreCase(httpURI.getScheme())) || httpURI.getPath() != null) {
                str2 = "";
            }
            sb2.append(str2);
            sb2.append(sessionIdPathParameterNamePrefix);
            sb2.append(nodeId);
            sb2.append(str.substring(indexOf3));
            return sb2.toString();
        }
        int indexOf4 = str.indexOf(sessionIdPathParameterNamePrefix);
        if (indexOf4 == -1) {
            return str;
        }
        int indexOf5 = str.indexOf("?", indexOf4);
        if (indexOf5 < 0) {
            indexOf5 = str.indexOf(MqttTopic.MULTI_LEVEL_WILDCARD, indexOf4);
        }
        if (indexOf5 <= indexOf4) {
            return str.substring(0, indexOf4);
        }
        return str.substring(0, indexOf4) + str.substring(indexOf5);
    }

    public String encodeRedirectURL(String str) {
        return encodeURL(str);
    }

    @Deprecated
    public String encodeUrl(String str) {
        return encodeURL(str);
    }

    @Deprecated
    public String encodeRedirectUrl(String str) {
        return encodeRedirectURL(str);
    }

    public void sendError(int i, String str) throws IOException {
        if (!this._connection.isIncluding()) {
            if (isCommitted()) {
                Logger logger = LOG;
                logger.warn("Committed before " + i + " " + str, new Object[0]);
            }
            resetBuffer();
            this._characterEncoding = null;
            setHeader(HttpHeaders.EXPIRES, (String) null);
            setHeader(HttpHeaders.LAST_MODIFIED, (String) null);
            setHeader("Cache-Control", (String) null);
            setHeader("Content-Type", (String) null);
            setHeader("Content-Length", (String) null);
            this._outputState = 0;
            setStatus(i, str);
            if (str == null) {
                str = HttpStatus.getMessage(i);
            }
            if (i != 204 && i != 304 && i != 206 && i >= 200) {
                Request request = this._connection.getRequest();
                ContextHandler.Context context = request.getContext();
                ErrorHandler errorHandler = context != null ? context.getContextHandler().getErrorHandler() : null;
                if (errorHandler == null) {
                    errorHandler = (ErrorHandler) this._connection.getConnector().getServer().getBean(ErrorHandler.class);
                }
                if (errorHandler != null) {
                    request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, new Integer(i));
                    request.setAttribute(RequestDispatcher.ERROR_MESSAGE, str);
                    request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
                    request.setAttribute(RequestDispatcher.ERROR_SERVLET_NAME, request.getServletName());
                    errorHandler.handle((String) null, this._connection.getRequest(), this._connection.getRequest(), this);
                } else {
                    setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
                    setContentType(MimeTypes.TEXT_HTML_8859_1);
                    ByteArrayISO8859Writer byteArrayISO8859Writer = new ByteArrayISO8859Writer(2048);
                    if (str != null) {
                        str = StringUtil.replace(StringUtil.replace(StringUtil.replace(str, DispatchConstants.SIGN_SPLIT_SYMBOL, "&amp;"), "<", "&lt;"), ">", "&gt;");
                    }
                    String requestURI = request.getRequestURI();
                    if (requestURI != null) {
                        requestURI = StringUtil.replace(StringUtil.replace(StringUtil.replace(requestURI, DispatchConstants.SIGN_SPLIT_SYMBOL, "&amp;"), "<", "&lt;"), ">", "&gt;");
                    }
                    byteArrayISO8859Writer.write("<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html;charset=ISO-8859-1\"/>\n");
                    byteArrayISO8859Writer.write("<title>Error ");
                    byteArrayISO8859Writer.write(Integer.toString(i));
                    byteArrayISO8859Writer.write(' ');
                    if (str == null) {
                        str = HttpStatus.getMessage(i);
                    }
                    byteArrayISO8859Writer.write(str);
                    byteArrayISO8859Writer.write("</title>\n</head>\n<body>\n<h2>HTTP ERROR: ");
                    byteArrayISO8859Writer.write(Integer.toString(i));
                    byteArrayISO8859Writer.write("</h2>\n<p>Problem accessing ");
                    byteArrayISO8859Writer.write(requestURI);
                    byteArrayISO8859Writer.write(". Reason:\n<pre>    ");
                    byteArrayISO8859Writer.write(str);
                    byteArrayISO8859Writer.write("</pre>");
                    byteArrayISO8859Writer.write("</p>\n<hr /><i><small>Powered by Jetty://</small></i>");
                    for (int i2 = 0; i2 < 20; i2++) {
                        byteArrayISO8859Writer.write("\n                                                ");
                    }
                    byteArrayISO8859Writer.write("\n</body>\n</html>\n");
                    byteArrayISO8859Writer.flush();
                    setContentLength(byteArrayISO8859Writer.size());
                    byteArrayISO8859Writer.writeTo(getOutputStream());
                    byteArrayISO8859Writer.destroy();
                }
            } else if (i != 206) {
                this._connection.getRequestFields().remove(HttpHeaders.CONTENT_TYPE_BUFFER);
                this._connection.getRequestFields().remove(HttpHeaders.CONTENT_LENGTH_BUFFER);
                this._characterEncoding = null;
                this._mimeType = null;
                this._cachedMimeType = null;
            }
            complete();
        }
    }

    public void sendError(int i) throws IOException {
        if (i == 102) {
            sendProcessing();
        } else {
            sendError(i, (String) null);
        }
    }

    public void sendProcessing() throws IOException {
        if (this._connection.isExpecting102Processing() && !isCommitted()) {
            ((HttpGenerator) this._connection.getGenerator()).send1xx(102);
        }
    }

    public void sendRedirect(String str) throws IOException {
        if (!this._connection.isIncluding()) {
            if (str != null) {
                if (!URIUtil.hasScheme(str)) {
                    StringBuilder rootURL = this._connection.getRequest().getRootURL();
                    if (str.startsWith("/")) {
                        rootURL.append(str);
                    } else {
                        String requestURI = this._connection.getRequest().getRequestURI();
                        if (!requestURI.endsWith("/")) {
                            requestURI = URIUtil.parentPath(requestURI);
                        }
                        String addPaths = URIUtil.addPaths(requestURI, str);
                        if (addPaths != null) {
                            if (!addPaths.startsWith("/")) {
                                rootURL.append('/');
                            }
                            rootURL.append(addPaths);
                        } else {
                            throw new IllegalStateException("path cannot be above root");
                        }
                    }
                    str = rootURL.toString();
                    HttpURI httpURI = new HttpURI(str);
                    String decodedPath = httpURI.getDecodedPath();
                    String canonicalPath = URIUtil.canonicalPath(decodedPath);
                    if (canonicalPath == null) {
                        throw new IllegalArgumentException();
                    } else if (!canonicalPath.equals(decodedPath)) {
                        StringBuilder rootURL2 = this._connection.getRequest().getRootURL();
                        rootURL2.append(URIUtil.encodePath(canonicalPath));
                        String param = httpURI.getParam();
                        if (param != null) {
                            rootURL2.append(';');
                            rootURL2.append(param);
                        }
                        String query = httpURI.getQuery();
                        if (query != null) {
                            rootURL2.append('?');
                            rootURL2.append(query);
                        }
                        String fragment = httpURI.getFragment();
                        if (fragment != null) {
                            rootURL2.append('#');
                            rootURL2.append(fragment);
                        }
                        str = rootURL2.toString();
                    }
                }
                resetBuffer();
                setHeader("Location", str);
                setStatus(302);
                complete();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    public void setDateHeader(String str, long j) {
        if (!this._connection.isIncluding()) {
            this._connection.getResponseFields().putDateField(str, j);
        }
    }

    public void addDateHeader(String str, long j) {
        if (!this._connection.isIncluding()) {
            this._connection.getResponseFields().addDateField(str, j);
        }
    }

    public void setHeader(String str, String str2) {
        if ("Content-Type".equalsIgnoreCase(str)) {
            setContentType(str2);
            return;
        }
        if (this._connection.isIncluding()) {
            if (str.startsWith(SET_INCLUDE_HEADER_PREFIX)) {
                str = str.substring(33);
            } else {
                return;
            }
        }
        this._connection.getResponseFields().put(str, str2);
        if (!"Content-Length".equalsIgnoreCase(str)) {
            return;
        }
        if (str2 == null) {
            this._connection._generator.setContentLength(-1);
        } else {
            this._connection._generator.setContentLength(Long.parseLong(str2));
        }
    }

    public Collection<String> getHeaderNames() {
        return this._connection.getResponseFields().getFieldNamesCollection();
    }

    public String getHeader(String str) {
        return this._connection.getResponseFields().getStringField(str);
    }

    public Collection<String> getHeaders(String str) {
        Collection<String> valuesCollection = this._connection.getResponseFields().getValuesCollection(str);
        return valuesCollection == null ? Collections.EMPTY_LIST : valuesCollection;
    }

    public void addHeader(String str, String str2) {
        if (this._connection.isIncluding()) {
            if (str.startsWith(SET_INCLUDE_HEADER_PREFIX)) {
                str = str.substring(33);
            } else {
                return;
            }
        }
        if ("Content-Type".equalsIgnoreCase(str)) {
            setContentType(str2);
            return;
        }
        this._connection.getResponseFields().add(str, str2);
        if ("Content-Length".equalsIgnoreCase(str)) {
            this._connection._generator.setContentLength(Long.parseLong(str2));
        }
    }

    public void setIntHeader(String str, int i) {
        if (!this._connection.isIncluding()) {
            long j = (long) i;
            this._connection.getResponseFields().putLongField(str, j);
            if ("Content-Length".equalsIgnoreCase(str)) {
                this._connection._generator.setContentLength(j);
            }
        }
    }

    public void addIntHeader(String str, int i) {
        if (!this._connection.isIncluding()) {
            long j = (long) i;
            this._connection.getResponseFields().addLongField(str, j);
            if ("Content-Length".equalsIgnoreCase(str)) {
                this._connection._generator.setContentLength(j);
            }
        }
    }

    public void setStatus(int i) {
        setStatus(i, (String) null);
    }

    public void setStatus(int i, String str) {
        if (i <= 0) {
            throw new IllegalArgumentException();
        } else if (!this._connection.isIncluding()) {
            this._status = i;
            this._reason = str;
        }
    }

    public String getCharacterEncoding() {
        if (this._characterEncoding == null) {
            this._characterEncoding = StringUtil.__ISO_8859_1;
        }
        return this._characterEncoding;
    }

    /* access modifiers changed from: package-private */
    public String getSetCharacterEncoding() {
        return this._characterEncoding;
    }

    public String getContentType() {
        return this._contentType;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (this._outputState == 0 || this._outputState == 1) {
            ServletOutputStream outputStream = this._connection.getOutputStream();
            this._outputState = 1;
            return outputStream;
        }
        throw new IllegalStateException("WRITER");
    }

    public boolean isWriting() {
        return this._outputState == 2;
    }

    public boolean isOutputing() {
        return this._outputState != 0;
    }

    public PrintWriter getWriter() throws IOException {
        if (this._outputState == 0 || this._outputState == 2) {
            if (this._writer == null) {
                String str = this._characterEncoding;
                if (str == null) {
                    BufferCache.CachedBuffer cachedBuffer = this._cachedMimeType;
                    if (cachedBuffer != null) {
                        str = MimeTypes.getCharsetFromContentType(cachedBuffer);
                    }
                    if (str == null) {
                        str = StringUtil.__ISO_8859_1;
                    }
                    setCharacterEncoding(str);
                }
                this._writer = this._connection.getPrintWriter(str);
            }
            this._outputState = 2;
            return this._writer;
        }
        throw new IllegalStateException("STREAM");
    }

    public void setCharacterEncoding(String str) {
        BufferCache.CachedBuffer associate;
        if (!this._connection.isIncluding() && this._outputState == 0 && !isCommitted()) {
            this._explicitEncoding = true;
            if (str != null) {
                this._characterEncoding = str;
                String str2 = this._contentType;
                if (str2 != null) {
                    int indexOf = str2.indexOf(59);
                    if (indexOf < 0) {
                        this._contentType = null;
                        BufferCache.CachedBuffer cachedBuffer = this._cachedMimeType;
                        if (!(cachedBuffer == null || (associate = cachedBuffer.getAssociate(this._characterEncoding)) == null)) {
                            this._contentType = associate.toString();
                            this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, (Buffer) associate);
                        }
                        if (this._contentType == null) {
                            this._contentType = this._mimeType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                            this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                            return;
                        }
                        return;
                    }
                    int indexOf2 = this._contentType.indexOf("charset=", indexOf);
                    if (indexOf2 < 0) {
                        this._contentType += ";charset=" + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                    } else {
                        int i = indexOf2 + 8;
                        int indexOf3 = this._contentType.indexOf(" ", i);
                        if (indexOf3 < 0) {
                            this._contentType = this._contentType.substring(0, i) + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                        } else {
                            this._contentType = this._contentType.substring(0, i) + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ") + this._contentType.substring(indexOf3);
                        }
                    }
                    this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                }
            } else if (this._characterEncoding != null) {
                this._characterEncoding = null;
                BufferCache.CachedBuffer cachedBuffer2 = this._cachedMimeType;
                if (cachedBuffer2 != null) {
                    this._contentType = cachedBuffer2.toString();
                } else {
                    String str3 = this._mimeType;
                    if (str3 != null) {
                        this._contentType = str3;
                    } else {
                        this._contentType = null;
                    }
                }
                if (this._contentType == null) {
                    this._connection.getResponseFields().remove(HttpHeaders.CONTENT_TYPE_BUFFER);
                } else {
                    this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                }
            }
        }
    }

    public void setContentLength(int i) {
        if (!isCommitted() && !this._connection.isIncluding()) {
            long j = (long) i;
            this._connection._generator.setContentLength(j);
            if (i > 0) {
                this._connection.getResponseFields().putLongField("Content-Length", j);
                if (!this._connection._generator.isAllContentWritten()) {
                    return;
                }
                if (this._outputState == 2) {
                    this._writer.close();
                } else if (this._outputState == 1) {
                    try {
                        getOutputStream().close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void setLongContentLength(long j) {
        if (!isCommitted() && !this._connection.isIncluding()) {
            this._connection._generator.setContentLength(j);
            this._connection.getResponseFields().putLongField("Content-Length", j);
        }
    }

    public void setContentType(String str) {
        if (!isCommitted() && !this._connection.isIncluding()) {
            if (str == null) {
                if (this._locale == null) {
                    this._characterEncoding = null;
                }
                this._mimeType = null;
                this._cachedMimeType = null;
                this._contentType = null;
                this._connection.getResponseFields().remove(HttpHeaders.CONTENT_TYPE_BUFFER);
                return;
            }
            int indexOf = str.indexOf(59);
            if (indexOf > 0) {
                this._mimeType = str.substring(0, indexOf).trim();
                this._cachedMimeType = MimeTypes.CACHE.get(this._mimeType);
                int i = indexOf + 1;
                int indexOf2 = str.indexOf("charset=", i);
                if (indexOf2 >= 0) {
                    this._explicitEncoding = true;
                    int i2 = indexOf2 + 8;
                    int indexOf3 = str.indexOf(32, i2);
                    if (this._outputState == 2) {
                        if ((indexOf2 == i && indexOf3 < 0) || (indexOf2 == indexOf + 2 && indexOf3 < 0 && str.charAt(i) == ' ')) {
                            BufferCache.CachedBuffer cachedBuffer = this._cachedMimeType;
                            if (cachedBuffer != null) {
                                BufferCache.CachedBuffer associate = cachedBuffer.getAssociate(this._characterEncoding);
                                if (associate != null) {
                                    this._contentType = associate.toString();
                                    this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, (Buffer) associate);
                                    return;
                                }
                                this._contentType = this._mimeType + ";charset=" + this._characterEncoding;
                                this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                                return;
                            }
                            this._contentType = this._mimeType + ";charset=" + this._characterEncoding;
                            this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                        } else if (indexOf3 < 0) {
                            this._contentType = str.substring(0, indexOf2) + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                            this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                        } else {
                            this._contentType = str.substring(0, indexOf2) + str.substring(indexOf3) + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                            this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                        }
                    } else if ((indexOf2 == i && indexOf3 < 0) || (indexOf2 == indexOf + 2 && indexOf3 < 0 && str.charAt(i) == ' ')) {
                        this._cachedMimeType = MimeTypes.CACHE.get(this._mimeType);
                        this._characterEncoding = QuotedStringTokenizer.unquote(str.substring(i2));
                        BufferCache.CachedBuffer cachedBuffer2 = this._cachedMimeType;
                        if (cachedBuffer2 != null) {
                            BufferCache.CachedBuffer associate2 = cachedBuffer2.getAssociate(this._characterEncoding);
                            if (associate2 != null) {
                                this._contentType = associate2.toString();
                                this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, (Buffer) associate2);
                                return;
                            }
                            this._contentType = str;
                            this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                            return;
                        }
                        this._contentType = str;
                        this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                    } else if (indexOf3 > 0) {
                        this._characterEncoding = QuotedStringTokenizer.unquote(str.substring(i2, indexOf3));
                        this._contentType = str;
                        this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                    } else {
                        this._characterEncoding = QuotedStringTokenizer.unquote(str.substring(i2));
                        this._contentType = str;
                        this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                    }
                } else {
                    this._cachedMimeType = null;
                    if (this._characterEncoding != null) {
                        str = str + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                    }
                    this._contentType = str;
                    this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                }
            } else {
                this._mimeType = str;
                this._cachedMimeType = MimeTypes.CACHE.get(this._mimeType);
                String str2 = this._characterEncoding;
                if (str2 != null) {
                    BufferCache.CachedBuffer cachedBuffer3 = this._cachedMimeType;
                    if (cachedBuffer3 != null) {
                        BufferCache.CachedBuffer associate3 = cachedBuffer3.getAssociate(str2);
                        if (associate3 != null) {
                            this._contentType = associate3.toString();
                            this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, (Buffer) associate3);
                            return;
                        }
                        this._contentType = this._mimeType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                        this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                        return;
                    }
                    this._contentType = str + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(this._characterEncoding, ";= ");
                    this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                    return;
                }
                BufferCache.CachedBuffer cachedBuffer4 = this._cachedMimeType;
                if (cachedBuffer4 != null) {
                    this._contentType = cachedBuffer4.toString();
                    this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, (Buffer) this._cachedMimeType);
                    return;
                }
                this._contentType = str;
                this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
            }
        }
    }

    public void setBufferSize(int i) {
        if (isCommitted() || getContentCount() > 0) {
            throw new IllegalStateException("Committed or content written");
        }
        this._connection.getGenerator().increaseContentBufferSize(i);
    }

    public int getBufferSize() {
        return this._connection.getGenerator().getContentBufferSize();
    }

    public void flushBuffer() throws IOException {
        this._connection.flushResponse();
    }

    public void reset() {
        resetBuffer();
        fwdReset();
        this._status = 200;
        this._reason = null;
        HttpFields responseFields = this._connection.getResponseFields();
        responseFields.clear();
        String stringField = this._connection.getRequestFields().getStringField(HttpHeaders.CONNECTION_BUFFER);
        if (stringField != null) {
            String[] split = stringField.split(",");
            int i = 0;
            while (split != null && i < split.length) {
                BufferCache.CachedBuffer cachedBuffer = HttpHeaderValues.CACHE.get(split[0].trim());
                if (cachedBuffer != null) {
                    int ordinal = cachedBuffer.getOrdinal();
                    if (ordinal == 1) {
                        responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                    } else if (ordinal != 5) {
                        if (ordinal == 8) {
                            responseFields.put(HttpHeaders.CONNECTION_BUFFER, "TE");
                        }
                    } else if (HttpVersions.HTTP_1_0.equalsIgnoreCase(this._connection.getRequest().getProtocol())) {
                        responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.KEEP_ALIVE);
                    }
                }
                i++;
            }
        }
    }

    public void reset(boolean z) {
        if (!z) {
            reset();
            return;
        }
        HttpFields responseFields = this._connection.getResponseFields();
        ArrayList arrayList = new ArrayList(5);
        Enumeration<String> values = responseFields.getValues("Set-Cookie");
        while (values.hasMoreElements()) {
            arrayList.add(values.nextElement());
        }
        reset();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            responseFields.add("Set-Cookie", (String) it.next());
        }
    }

    public void fwdReset() {
        resetBuffer();
        this._writer = null;
        this._outputState = 0;
    }

    public void resetBuffer() {
        if (!isCommitted()) {
            this._connection.getGenerator().resetBuffer();
            return;
        }
        throw new IllegalStateException("Committed");
    }

    public boolean isCommitted() {
        return this._connection.isResponseCommitted();
    }

    public void setLocale(Locale locale) {
        String localeEncoding;
        if (locale != null && !isCommitted() && !this._connection.isIncluding()) {
            this._locale = locale;
            this._connection.getResponseFields().put(HttpHeaders.CONTENT_LANGUAGE_BUFFER, locale.toString().replace('_', '-'));
            if (!this._explicitEncoding && this._outputState == 0 && this._connection.getRequest().getContext() != null && (localeEncoding = this._connection.getRequest().getContext().getContextHandler().getLocaleEncoding(locale)) != null && localeEncoding.length() > 0) {
                this._characterEncoding = localeEncoding;
                String contentType = getContentType();
                if (contentType != null) {
                    this._characterEncoding = localeEncoding;
                    int indexOf = contentType.indexOf(59);
                    if (indexOf < 0) {
                        this._mimeType = contentType;
                        this._contentType = contentType + ";charset=" + localeEncoding;
                    } else {
                        this._mimeType = contentType.substring(0, indexOf);
                        String str = this._mimeType + ";charset=" + localeEncoding;
                        this._mimeType = str;
                        this._contentType = str;
                    }
                    this._cachedMimeType = MimeTypes.CACHE.get(this._mimeType);
                    this._connection.getResponseFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, this._contentType);
                }
            }
        }
    }

    public Locale getLocale() {
        Locale locale = this._locale;
        return locale == null ? Locale.getDefault() : locale;
    }

    public int getStatus() {
        return this._status;
    }

    public String getReason() {
        return this._reason;
    }

    public void complete() throws IOException {
        this._connection.completeResponse();
    }

    public long getContentCount() {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        if (abstractHttpConnection == null || abstractHttpConnection.getGenerator() == null) {
            return -1;
        }
        return this._connection.getGenerator().getContentWritten();
    }

    public HttpFields getHttpFields() {
        return this._connection.getResponseFields();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 ");
        sb.append(this._status);
        sb.append(" ");
        String str = this._reason;
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(System.getProperty("line.separator"));
        sb.append(this._connection.getResponseFields().toString());
        return sb.toString();
    }

    private static class NullOutput extends ServletOutputStream {
        public void print(String str) throws IOException {
        }

        public void println(String str) throws IOException {
        }

        public void write(int i) throws IOException {
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
        }

        private NullOutput() {
        }
    }
}
