package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.WriterOutputStream;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;

public class ResourceHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger((Class<?>) ResourceHandler.class);
    boolean _aliases;
    Resource _baseResource;
    ByteArrayBuffer _cacheControl;
    ContextHandler _context;
    Resource _defaultStylesheet;
    boolean _directory;
    boolean _etags;
    MimeTypes _mimeTypes = new MimeTypes();
    Resource _stylesheet;
    String[] _welcomeFiles = {"index.html"};

    public MimeTypes getMimeTypes() {
        return this._mimeTypes;
    }

    public void setMimeTypes(MimeTypes mimeTypes) {
        this._mimeTypes = mimeTypes;
    }

    public boolean isAliases() {
        return this._aliases;
    }

    public void setAliases(boolean z) {
        this._aliases = z;
    }

    public boolean isDirectoriesListed() {
        return this._directory;
    }

    public void setDirectoriesListed(boolean z) {
        this._directory = z;
    }

    public boolean isEtags() {
        return this._etags;
    }

    public void setEtags(boolean z) {
        this._etags = z;
    }

    public void doStart() throws Exception {
        ContextHandler contextHandler;
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext == null) {
            contextHandler = null;
        } else {
            contextHandler = currentContext.getContextHandler();
        }
        this._context = contextHandler;
        ContextHandler contextHandler2 = this._context;
        if (contextHandler2 != null) {
            this._aliases = contextHandler2.isAliases();
        }
        if (this._aliases || FileResource.getCheckAliases()) {
            super.doStart();
            return;
        }
        throw new IllegalStateException("Alias checking disabled");
    }

    public Resource getBaseResource() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource;
    }

    public String getResourceBase() {
        Resource resource = this._baseResource;
        if (resource == null) {
            return null;
        }
        return resource.toString();
    }

    public void setBaseResource(Resource resource) {
        this._baseResource = resource;
    }

    public void setResourceBase(String str) {
        try {
            setBaseResource(Resource.newResource(str));
        } catch (Exception e) {
            LOG.warn(e.toString(), new Object[0]);
            LOG.debug(e);
            throw new IllegalArgumentException(str);
        }
    }

    public Resource getStylesheet() {
        Resource resource = this._stylesheet;
        if (resource != null) {
            return resource;
        }
        if (this._defaultStylesheet == null) {
            try {
                this._defaultStylesheet = Resource.newResource(getClass().getResource("/jetty-dir.css"));
            } catch (IOException e) {
                LOG.warn(e.toString(), new Object[0]);
                LOG.debug(e);
            }
        }
        return this._defaultStylesheet;
    }

    public void setStylesheet(String str) {
        try {
            this._stylesheet = Resource.newResource(str);
            if (!this._stylesheet.exists()) {
                Logger logger = LOG;
                logger.warn("unable to find custom stylesheet: " + str, new Object[0]);
                this._stylesheet = null;
            }
        } catch (Exception e) {
            LOG.warn(e.toString(), new Object[0]);
            LOG.debug(e);
            throw new IllegalArgumentException(str.toString());
        }
    }

    public String getCacheControl() {
        return this._cacheControl.toString();
    }

    public void setCacheControl(String str) {
        this._cacheControl = str == null ? null : new ByteArrayBuffer(str);
    }

    public Resource getResource(String str) throws MalformedURLException {
        ContextHandler contextHandler;
        if (str == null || !str.startsWith("/")) {
            throw new MalformedURLException(str);
        }
        Resource resource = this._baseResource;
        if (resource == null && ((contextHandler = this._context) == null || (resource = contextHandler.getBaseResource()) == null)) {
            return null;
        }
        try {
            return resource.addPath(URIUtil.canonicalPath(str));
        } catch (Exception e) {
            LOG.ignore(e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Resource getResource(HttpServletRequest httpServletRequest) throws MalformedURLException {
        String str;
        String str2;
        Boolean valueOf = Boolean.valueOf(httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI) != null);
        if (valueOf == null || !valueOf.booleanValue()) {
            str2 = httpServletRequest.getServletPath();
            str = httpServletRequest.getPathInfo();
        } else {
            str2 = (String) httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH);
            str = (String) httpServletRequest.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO);
            if (str2 == null && str == null) {
                str2 = httpServletRequest.getServletPath();
                str = httpServletRequest.getPathInfo();
            }
        }
        return getResource(URIUtil.addPaths(str2, str));
    }

    public String[] getWelcomeFiles() {
        return this._welcomeFiles;
    }

    public void setWelcomeFiles(String[] strArr) {
        this._welcomeFiles = strArr;
    }

    /* access modifiers changed from: protected */
    public Resource getWelcome(Resource resource) throws MalformedURLException, IOException {
        int i = 0;
        while (true) {
            String[] strArr = this._welcomeFiles;
            if (i >= strArr.length) {
                return null;
            }
            Resource addPath = resource.addPath(strArr[i]);
            if (addPath.exists() && !addPath.isDirectory()) {
                return addPath;
            }
            i++;
        }
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        boolean z;
        Resource resource;
        String str2;
        OutputStream writerOutputStream;
        Request request2 = request;
        HttpServletRequest httpServletRequest2 = httpServletRequest;
        HttpServletResponse httpServletResponse2 = httpServletResponse;
        if (!request.isHandled()) {
            if ("GET".equals(httpServletRequest.getMethod())) {
                z = false;
            } else if (!"HEAD".equals(httpServletRequest.getMethod())) {
                super.handle(str, request, httpServletRequest, httpServletResponse);
                return;
            } else {
                z = true;
            }
            Resource resource2 = getResource(httpServletRequest2);
            if (resource2 == null || !resource2.exists()) {
                if (str.endsWith("/jetty-dir.css")) {
                    resource2 = getStylesheet();
                    if (resource2 != null) {
                        httpServletResponse2.setContentType("text/css");
                    } else {
                        return;
                    }
                } else {
                    super.handle(str, request, httpServletRequest, httpServletResponse);
                    return;
                }
            }
            if (this._aliases || resource2.getAlias() == null) {
                request2.setHandled(true);
                if (!resource2.isDirectory()) {
                    resource = resource2;
                } else if (!httpServletRequest.getPathInfo().endsWith("/")) {
                    httpServletResponse2.sendRedirect(httpServletResponse2.encodeRedirectURL(URIUtil.addPaths(httpServletRequest.getRequestURI(), "/")));
                    return;
                } else {
                    Resource welcome = getWelcome(resource2);
                    if (welcome == null || !welcome.exists()) {
                        doDirectory(httpServletRequest2, httpServletResponse2, resource2);
                        request2.setHandled(true);
                        return;
                    }
                    resource = welcome;
                }
                long lastModified = resource.lastModified();
                String str3 = null;
                if (this._etags) {
                    String header = httpServletRequest2.getHeader(HttpHeaders.IF_NONE_MATCH);
                    str2 = resource.getWeakETag();
                    if (!(header == null || resource == null || !header.equals(str2))) {
                        httpServletResponse2.setStatus(304);
                        request.getResponse().getHttpFields().put(HttpHeaders.ETAG_BUFFER, str2);
                        return;
                    }
                } else {
                    str2 = null;
                }
                if (lastModified > 0) {
                    long dateHeader = httpServletRequest2.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
                    if (dateHeader > 0 && lastModified / 1000 <= dateHeader / 1000) {
                        httpServletResponse2.setStatus(304);
                        return;
                    }
                }
                Buffer mimeByExtension = this._mimeTypes.getMimeByExtension(resource.toString());
                if (mimeByExtension == null) {
                    mimeByExtension = this._mimeTypes.getMimeByExtension(httpServletRequest.getPathInfo());
                }
                if (mimeByExtension != null) {
                    str3 = mimeByExtension.toString();
                }
                doResponseHeaders(httpServletResponse2, resource, str3);
                httpServletResponse2.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);
                if (this._etags) {
                    request.getResponse().getHttpFields().put(HttpHeaders.ETAG_BUFFER, str2);
                }
                if (!z) {
                    try {
                        writerOutputStream = httpServletResponse.getOutputStream();
                    } catch (IllegalStateException unused) {
                        writerOutputStream = new WriterOutputStream(httpServletResponse.getWriter());
                    }
                    OutputStream outputStream = writerOutputStream;
                    if (outputStream instanceof AbstractHttpConnection.Output) {
                        ((AbstractHttpConnection.Output) outputStream).sendContent(resource.getInputStream());
                    } else {
                        resource.writeTo(outputStream, 0, resource.length());
                    }
                }
            } else {
                LOG.info(resource2 + " aliased to " + resource2.getAlias(), new Object[0]);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doDirectory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Resource resource) throws IOException {
        if (this._directory) {
            String listHTML = resource.getListHTML(httpServletRequest.getRequestURI(), httpServletRequest.getPathInfo().lastIndexOf("/") > 0);
            httpServletResponse.setContentType("text/html; charset=UTF-8");
            httpServletResponse.getWriter().println(listHTML);
            return;
        }
        httpServletResponse.sendError(403);
    }

    /* access modifiers changed from: protected */
    public void doResponseHeaders(HttpServletResponse httpServletResponse, Resource resource, String str) {
        if (str != null) {
            httpServletResponse.setContentType(str);
        }
        long length = resource.length();
        if (httpServletResponse instanceof Response) {
            HttpFields httpFields = ((Response) httpServletResponse).getHttpFields();
            if (length > 0) {
                httpFields.putLongField(HttpHeaders.CONTENT_LENGTH_BUFFER, length);
            }
            if (this._cacheControl != null) {
                httpFields.put(HttpHeaders.CACHE_CONTROL_BUFFER, (Buffer) this._cacheControl);
                return;
            }
            return;
        }
        if (length > 0) {
            httpServletResponse.setHeader("Content-Length", Long.toString(length));
        }
        ByteArrayBuffer byteArrayBuffer = this._cacheControl;
        if (byteArrayBuffer != null) {
            httpServletResponse.setHeader("Cache-Control", byteArrayBuffer.toString());
        }
    }
}
