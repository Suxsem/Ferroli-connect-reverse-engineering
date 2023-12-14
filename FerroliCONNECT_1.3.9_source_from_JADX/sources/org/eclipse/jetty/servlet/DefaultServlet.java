package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.WriterOutputStream;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.InclusiveByteRange;
import org.eclipse.jetty.server.ResourceCache;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.nio.NIOConnector;
import org.eclipse.jetty.server.ssl.SslConnector;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.MultiPartOutputStream;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.resource.ResourceFactory;

public class DefaultServlet extends HttpServlet implements ResourceFactory {
    private static final Logger LOG = Log.getLogger((Class<?>) DefaultServlet.class);
    private static final long serialVersionUID = 4930458713846881193L;
    private boolean _acceptRanges = true;
    private ResourceCache _cache;
    private ByteArrayBuffer _cacheControl;
    private ContextHandler _contextHandler;
    private ServletHolder _defaultHolder;
    private boolean _dirAllowed = true;
    private boolean _etags = false;
    private boolean _gzip = true;
    private MimeTypes _mimeTypes;
    private boolean _pathInfoOnly = false;
    private boolean _redirectWelcome = false;
    private String _relativeResourceBase;
    private Resource _resourceBase;
    private ServletContext _servletContext;
    private ServletHandler _servletHandler;
    private Resource _stylesheet;
    private boolean _useFileMappedBuffer = false;
    private boolean _welcomeExactServlets = false;
    private boolean _welcomeServlets = false;
    private String[] _welcomes;

    public void init() throws UnavailableException {
        this._servletContext = getServletContext();
        this._contextHandler = initContextHandler(this._servletContext);
        this._mimeTypes = this._contextHandler.getMimeTypes();
        this._welcomes = this._contextHandler.getWelcomeFiles();
        if (this._welcomes == null) {
            this._welcomes = new String[]{"index.html", "index.jsp"};
        }
        this._acceptRanges = getInitBoolean("acceptRanges", this._acceptRanges);
        this._dirAllowed = getInitBoolean("dirAllowed", this._dirAllowed);
        this._redirectWelcome = getInitBoolean("redirectWelcome", this._redirectWelcome);
        this._gzip = getInitBoolean("gzip", this._gzip);
        this._pathInfoOnly = getInitBoolean("pathInfoOnly", this._pathInfoOnly);
        if ("exact".equals(getInitParameter("welcomeServlets"))) {
            this._welcomeExactServlets = true;
            this._welcomeServlets = false;
        } else {
            this._welcomeServlets = getInitBoolean("welcomeServlets", this._welcomeServlets);
        }
        if (getInitParameter("aliases") != null) {
            this._contextHandler.setAliases(getInitBoolean("aliases", false));
        }
        boolean isAliases = this._contextHandler.isAliases();
        if (isAliases || FileResource.getCheckAliases()) {
            if (isAliases) {
                this._servletContext.log("Aliases are enabled! Security constraints may be bypassed!!!");
            }
            this._useFileMappedBuffer = getInitBoolean("useFileMappedBuffer", this._useFileMappedBuffer);
            this._relativeResourceBase = getInitParameter("relativeResourceBase");
            String initParameter = getInitParameter("resourceBase");
            if (initParameter != null) {
                if (this._relativeResourceBase == null) {
                    try {
                        this._resourceBase = this._contextHandler.newResource(initParameter);
                    } catch (Exception e) {
                        LOG.warn(Log.EXCEPTION, (Throwable) e);
                        throw new UnavailableException(e.toString());
                    }
                } else {
                    throw new UnavailableException("resourceBase & relativeResourceBase");
                }
            }
            String initParameter2 = getInitParameter("stylesheet");
            if (initParameter2 != null) {
                try {
                    this._stylesheet = Resource.newResource(initParameter2);
                    if (!this._stylesheet.exists()) {
                        LOG.warn("!" + initParameter2, new Object[0]);
                        this._stylesheet = null;
                    }
                } catch (Exception e2) {
                    LOG.warn(e2.toString(), new Object[0]);
                    LOG.debug(e2);
                }
            }
            if (this._stylesheet == null) {
                this._stylesheet = Resource.newResource(getClass().getResource("/jetty-dir.css"));
            }
            String initParameter3 = getInitParameter("cacheControl");
            if (initParameter3 != null) {
                this._cacheControl = new ByteArrayBuffer(initParameter3);
            }
            String initParameter4 = getInitParameter("resourceCache");
            int initInt = getInitInt("maxCacheSize", -2);
            int initInt2 = getInitInt("maxCachedFileSize", -2);
            int initInt3 = getInitInt("maxCachedFiles", -2);
            if (initParameter4 != null) {
                if (!(initInt == -1 && initInt2 == -2 && initInt3 == -2)) {
                    LOG.debug("ignoring resource cache configuration, using resourceCache attribute", new Object[0]);
                }
                if (this._relativeResourceBase == null && this._resourceBase == null) {
                    this._cache = (ResourceCache) this._servletContext.getAttribute(initParameter4);
                    LOG.debug("Cache {}={}", initParameter4, this._cache);
                } else {
                    throw new UnavailableException("resourceCache specified with resource bases");
                }
            }
            this._etags = getInitBoolean("etags", this._etags);
            try {
                if (this._cache == null && initInt3 > 0) {
                    this._cache = new ResourceCache((ResourceCache) null, this, this._mimeTypes, this._useFileMappedBuffer, this._etags);
                    if (initInt > 0) {
                        this._cache.setMaxCacheSize(initInt);
                    }
                    if (initInt2 >= -1) {
                        this._cache.setMaxCachedFileSize(initInt2);
                    }
                    if (initInt3 >= -1) {
                        this._cache.setMaxCachedFiles(initInt3);
                    }
                }
                this._servletHandler = (ServletHandler) this._contextHandler.getChildHandlerByClass(ServletHandler.class);
                for (ServletHolder servletHolder : this._servletHandler.getServlets()) {
                    if (servletHolder.getServletInstance() == this) {
                        this._defaultHolder = servletHolder;
                    }
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("resource base = " + this._resourceBase, new Object[0]);
                }
            } catch (Exception e3) {
                LOG.warn(Log.EXCEPTION, (Throwable) e3);
                throw new UnavailableException(e3.toString());
            }
        } else {
            throw new IllegalStateException("Alias checking disabled");
        }
    }

    /* access modifiers changed from: protected */
    public ContextHandler initContextHandler(ServletContext servletContext) {
        if (ContextHandler.getCurrentContext() != null) {
            return ContextHandler.getCurrentContext().getContextHandler();
        }
        if (servletContext instanceof ContextHandler.Context) {
            return ((ContextHandler.Context) servletContext).getContextHandler();
        }
        throw new IllegalArgumentException("The servletContext " + servletContext + " " + servletContext.getClass().getName() + " is not " + ContextHandler.Context.class.getName());
    }

    public String getInitParameter(String str) {
        ServletContext servletContext = getServletContext();
        String initParameter = servletContext.getInitParameter("org.eclipse.jetty.servlet.Default." + str);
        return initParameter == null ? super.getInitParameter(str) : initParameter;
    }

    private boolean getInitBoolean(String str, boolean z) {
        String initParameter = getInitParameter(str);
        if (initParameter == null || initParameter.length() == 0) {
            return z;
        }
        return initParameter.startsWith("t") || initParameter.startsWith("T") || initParameter.startsWith("y") || initParameter.startsWith("Y") || initParameter.startsWith("1");
    }

    private int getInitInt(String str, int i) {
        String initParameter = getInitParameter(str);
        if (initParameter == null) {
            initParameter = getInitParameter(str);
        }
        return (initParameter == null || initParameter.length() <= 0) ? i : Integer.parseInt(initParameter);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.util.resource.Resource getResource(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = r5._relativeResourceBase
            if (r0 == 0) goto L_0x0008
            java.lang.String r6 = org.eclipse.jetty.util.URIUtil.addPaths(r0, r6)
        L_0x0008:
            r0 = 0
            org.eclipse.jetty.util.resource.Resource r1 = r5._resourceBase     // Catch:{ IOException -> 0x0066 }
            if (r1 == 0) goto L_0x0023
            org.eclipse.jetty.util.resource.Resource r1 = r5._resourceBase     // Catch:{ IOException -> 0x0066 }
            org.eclipse.jetty.util.resource.Resource r1 = r1.addPath(r6)     // Catch:{ IOException -> 0x0066 }
            org.eclipse.jetty.server.handler.ContextHandler r2 = r5._contextHandler     // Catch:{ IOException -> 0x001e }
            boolean r2 = r2.checkAlias(r6, r1)     // Catch:{ IOException -> 0x001e }
            if (r2 != 0) goto L_0x001c
            goto L_0x003c
        L_0x001c:
            r0 = r1
            goto L_0x003c
        L_0x001e:
            r0 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0067
        L_0x0023:
            javax.servlet.ServletContext r1 = r5._servletContext     // Catch:{ IOException -> 0x0066 }
            boolean r1 = r1 instanceof org.eclipse.jetty.server.handler.ContextHandler.Context     // Catch:{ IOException -> 0x0066 }
            if (r1 == 0) goto L_0x0030
            org.eclipse.jetty.server.handler.ContextHandler r1 = r5._contextHandler     // Catch:{ IOException -> 0x0066 }
            org.eclipse.jetty.util.resource.Resource r0 = r1.getResource(r6)     // Catch:{ IOException -> 0x0066 }
            goto L_0x003c
        L_0x0030:
            javax.servlet.ServletContext r1 = r5._servletContext     // Catch:{ IOException -> 0x0066 }
            java.net.URL r1 = r1.getResource(r6)     // Catch:{ IOException -> 0x0066 }
            org.eclipse.jetty.server.handler.ContextHandler r2 = r5._contextHandler     // Catch:{ IOException -> 0x0066 }
            org.eclipse.jetty.util.resource.Resource r0 = r2.newResource((java.net.URL) r1)     // Catch:{ IOException -> 0x0066 }
        L_0x003c:
            org.eclipse.jetty.util.log.Logger r1 = LOG     // Catch:{ IOException -> 0x0066 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ IOException -> 0x0066 }
            if (r1 == 0) goto L_0x006c
            org.eclipse.jetty.util.log.Logger r1 = LOG     // Catch:{ IOException -> 0x0066 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0066 }
            r2.<init>()     // Catch:{ IOException -> 0x0066 }
            java.lang.String r3 = "Resource "
            r2.append(r3)     // Catch:{ IOException -> 0x0066 }
            r2.append(r6)     // Catch:{ IOException -> 0x0066 }
            java.lang.String r3 = "="
            r2.append(r3)     // Catch:{ IOException -> 0x0066 }
            r2.append(r0)     // Catch:{ IOException -> 0x0066 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0066 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ IOException -> 0x0066 }
            r1.debug((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ IOException -> 0x0066 }
            goto L_0x006c
        L_0x0066:
            r1 = move-exception
        L_0x0067:
            org.eclipse.jetty.util.log.Logger r2 = LOG
            r2.ignore(r1)
        L_0x006c:
            if (r0 == 0) goto L_0x0074
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x007e
        L_0x0074:
            java.lang.String r1 = "/jetty-dir.css"
            boolean r6 = r6.endsWith(r1)
            if (r6 == 0) goto L_0x007e
            org.eclipse.jetty.util.resource.Resource r0 = r5._stylesheet
        L_0x007e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.DefaultServlet.getResource(java.lang.String):org.eclipse.jetty.util.resource.Resource");
    }

    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [org.eclipse.jetty.http.HttpContent] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r4v3, types: [org.eclipse.jetty.util.resource.Resource] */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v13 */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: type inference failed for: r4v16 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0057, code lost:
        if (hasDefinedRange(r7) == false) goto L_0x003a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x02dc A[Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }] */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x02e8  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x02ee A[SYNTHETIC, Splitter:B:176:0x02ee] */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x031d A[Catch:{ all -> 0x0332 }] */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x0328  */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x032c  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0337  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x033d  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00e8 A[SYNTHETIC, Splitter:B:61:0x00e8] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x010f A[Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x013f A[Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doGet(javax.servlet.http.HttpServletRequest r17, javax.servlet.http.HttpServletResponse r18) throws javax.servlet.ServletException, java.io.IOException {
        /*
            r16 = this;
            r8 = r16
            r0 = r17
            r9 = r18
            java.lang.String r1 = "javax.servlet.include.request_uri"
            java.lang.Object r1 = r0.getAttribute(r1)
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0012
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r4 = 0
            if (r1 == 0) goto L_0x003c
            boolean r5 = r1.booleanValue()
            if (r5 == 0) goto L_0x003c
            java.lang.String r5 = "javax.servlet.include.servlet_path"
            java.lang.Object r5 = r0.getAttribute(r5)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = "javax.servlet.include.path_info"
            java.lang.Object r6 = r0.getAttribute(r6)
            java.lang.String r6 = (java.lang.String) r6
            if (r5 != 0) goto L_0x003a
            java.lang.String r5 = r17.getServletPath()
            java.lang.String r6 = r17.getPathInfo()
        L_0x003a:
            r7 = r4
            goto L_0x005a
        L_0x003c:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            boolean r5 = r8._pathInfoOnly
            if (r5 == 0) goto L_0x0045
            java.lang.String r5 = "/"
            goto L_0x0049
        L_0x0045:
            java.lang.String r5 = r17.getServletPath()
        L_0x0049:
            java.lang.String r6 = r17.getPathInfo()
            java.lang.String r7 = "Range"
            java.util.Enumeration r7 = r0.getHeaders(r7)
            boolean r10 = r8.hasDefinedRange(r7)
            if (r10 != 0) goto L_0x005a
            goto L_0x003a
        L_0x005a:
            java.lang.String r5 = org.eclipse.jetty.util.URIUtil.addPaths(r5, r6)
            if (r6 != 0) goto L_0x0064
            java.lang.String r6 = r17.getServletPath()
        L_0x0064:
            java.lang.String r10 = "/"
            boolean r6 = r6.endsWith(r10)
            boolean r10 = r1.booleanValue()     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            if (r10 != 0) goto L_0x00e3
            boolean r10 = r8._gzip     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            if (r10 == 0) goto L_0x00e3
            if (r7 != 0) goto L_0x00e3
            if (r6 != 0) goto L_0x00e3
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            r10.<init>()     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            r10.append(r5)     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            java.lang.String r11 = ".gz"
            r10.append(r11)     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            java.lang.String r10 = r10.toString()     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            org.eclipse.jetty.server.ResourceCache r11 = r8._cache     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            if (r11 != 0) goto L_0x0093
            org.eclipse.jetty.util.resource.Resource r10 = r8.getResource(r10)     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            r11 = r4
            goto L_0x00a4
        L_0x0093:
            org.eclipse.jetty.server.ResourceCache r11 = r8._cache     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            org.eclipse.jetty.http.HttpContent r10 = r11.lookup(r10)     // Catch:{ IllegalArgumentException -> 0x030e, all -> 0x030b }
            if (r10 != 0) goto L_0x009d
            r11 = r4
            goto L_0x00a1
        L_0x009d:
            org.eclipse.jetty.util.resource.Resource r11 = r10.getResource()     // Catch:{ IllegalArgumentException -> 0x00df, all -> 0x00da }
        L_0x00a1:
            r15 = r11
            r11 = r10
            r10 = r15
        L_0x00a4:
            if (r10 == 0) goto L_0x00d7
            boolean r12 = r10.exists()     // Catch:{ IllegalArgumentException -> 0x00d2, all -> 0x00cd }
            if (r12 == 0) goto L_0x00d7
            boolean r12 = r10.isDirectory()     // Catch:{ IllegalArgumentException -> 0x00d2, all -> 0x00cd }
            if (r12 != 0) goto L_0x00d7
            java.lang.String r12 = "Vary"
            java.lang.String r13 = "Accept-Encoding"
            r9.addHeader(r12, r13)     // Catch:{ IllegalArgumentException -> 0x00d2, all -> 0x00cd }
            java.lang.String r12 = "Accept-Encoding"
            java.lang.String r12 = r0.getHeader(r12)     // Catch:{ IllegalArgumentException -> 0x00d2, all -> 0x00cd }
            if (r12 == 0) goto L_0x00d7
            java.lang.String r13 = "gzip"
            int r12 = r12.indexOf(r13)     // Catch:{ IllegalArgumentException -> 0x00d2, all -> 0x00cd }
            if (r12 < 0) goto L_0x00d7
            r12 = r11
            r11 = r10
            r10 = 1
            goto L_0x00e6
        L_0x00cd:
            r0 = move-exception
            r4 = r11
            r11 = r10
            goto L_0x0335
        L_0x00d2:
            r0 = move-exception
            r4 = r10
            r12 = r11
            goto L_0x0310
        L_0x00d7:
            r12 = r11
            r11 = r10
            goto L_0x00e5
        L_0x00da:
            r0 = move-exception
            r11 = r4
            r4 = r10
            goto L_0x0335
        L_0x00df:
            r0 = move-exception
            r12 = r10
            goto L_0x0310
        L_0x00e3:
            r11 = r4
            r12 = r11
        L_0x00e5:
            r10 = 0
        L_0x00e6:
            if (r10 != 0) goto L_0x0106
            org.eclipse.jetty.server.ResourceCache r13 = r8._cache     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            if (r13 != 0) goto L_0x00f2
            org.eclipse.jetty.util.resource.Resource r4 = r8.getResource(r5)     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
        L_0x00f0:
            r11 = r4
            goto L_0x0106
        L_0x00f2:
            org.eclipse.jetty.server.ResourceCache r13 = r8._cache     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            org.eclipse.jetty.http.HttpContent r12 = r13.lookup(r5)     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            if (r12 != 0) goto L_0x00fb
            goto L_0x00f0
        L_0x00fb:
            org.eclipse.jetty.util.resource.Resource r4 = r12.getResource()     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            goto L_0x00f0
        L_0x0100:
            r0 = move-exception
            goto L_0x0334
        L_0x0103:
            r0 = move-exception
            goto L_0x0309
        L_0x0106:
            r4 = r12
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            boolean r12 = r12.isDebugEnabled()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r12 == 0) goto L_0x013d
            org.eclipse.jetty.util.log.Logger r12 = LOG     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r13.<init>()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r14 = "uri="
            r13.append(r14)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r14 = r17.getRequestURI()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r13.append(r14)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r14 = " resource="
            r13.append(r14)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r13.append(r11)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r4 == 0) goto L_0x012f
            java.lang.String r14 = " content"
            goto L_0x0131
        L_0x012f:
            java.lang.String r14 = ""
        L_0x0131:
            r13.append(r14)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r13 = r13.toString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.Object[] r14 = new java.lang.Object[r3]     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r12.debug((java.lang.String) r13, (java.lang.Object[]) r14)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
        L_0x013d:
            if (r11 == 0) goto L_0x02d6
            boolean r12 = r11.exists()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r12 != 0) goto L_0x0147
            goto L_0x02d6
        L_0x0147:
            boolean r12 = r11.isDirectory()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r12 != 0) goto L_0x01e5
            if (r6 == 0) goto L_0x0199
            org.eclipse.jetty.server.handler.ContextHandler r6 = r8._contextHandler     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            boolean r6 = r6.isAliases()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r6 == 0) goto L_0x0199
            int r6 = r5.length()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r6 <= r2) goto L_0x0199
            java.lang.String r0 = r17.getQueryString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            int r1 = r5.length()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            int r1 = r1 - r2
            java.lang.String r1 = r5.substring(r3, r1)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r0 == 0) goto L_0x0186
            int r2 = r0.length()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r2 == 0) goto L_0x0186
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r2.<init>()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r2.append(r1)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r1 = "?"
            r2.append(r1)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r2.append(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r1 = r2.toString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
        L_0x0186:
            javax.servlet.ServletContext r0 = r8._servletContext     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = r0.getContextPath()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = org.eclipse.jetty.util.URIUtil.addPaths(r0, r1)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = r9.encodeRedirectURL(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r9.sendRedirect(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            goto L_0x02e1
        L_0x0199:
            if (r4 != 0) goto L_0x01b2
            org.eclipse.jetty.http.HttpContent$ResourceAsHttpContent r2 = new org.eclipse.jetty.http.HttpContent$ResourceAsHttpContent     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            org.eclipse.jetty.http.MimeTypes r3 = r8._mimeTypes     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r6 = r11.toString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            org.eclipse.jetty.io.Buffer r3 = r3.getMimeByExtension(r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            int r6 = r18.getBufferSize()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            boolean r12 = r8._etags     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r2.<init>(r11, r3, r6, r12)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r12 = r2
            goto L_0x01b3
        L_0x01b2:
            r12 = r4
        L_0x01b3:
            boolean r2 = r1.booleanValue()     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            if (r2 != 0) goto L_0x01bf
            boolean r2 = r8.passConditionalHeaders(r0, r9, r11, r12)     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            if (r2 == 0) goto L_0x01e2
        L_0x01bf:
            if (r10 == 0) goto L_0x01d3
            java.lang.String r2 = "Content-Encoding"
            java.lang.String r3 = "gzip"
            r9.setHeader(r2, r3)     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            javax.servlet.ServletContext r2 = r8._servletContext     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            java.lang.String r2 = r2.getMimeType(r5)     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            if (r2 == 0) goto L_0x01d3
            r9.setContentType(r2)     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
        L_0x01d3:
            boolean r4 = r1.booleanValue()     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
            r1 = r16
            r2 = r17
            r3 = r18
            r5 = r11
            r6 = r12
            r1.sendData(r2, r3, r4, r5, r6, r7)     // Catch:{ IllegalArgumentException -> 0x0103, all -> 0x0100 }
        L_0x01e2:
            r2 = r12
            goto L_0x02e2
        L_0x01e5:
            if (r6 == 0) goto L_0x0299
            int r6 = r5.length()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r6 != r2) goto L_0x01f7
            java.lang.String r6 = "org.eclipse.jetty.server.nullPathInfo"
            java.lang.Object r6 = r0.getAttribute(r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r6 == 0) goto L_0x01f7
            goto L_0x0299
        L_0x01f7:
            java.lang.String r6 = r8.getWelcomeFile(r5)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r6 == 0) goto L_0x0270
            org.eclipse.jetty.util.log.Logger r5 = LOG     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r7 = "welcome={}"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r2[r3] = r6     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r5.debug((java.lang.String) r7, (java.lang.Object[]) r2)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            boolean r2 = r8._redirectWelcome     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r2 == 0) goto L_0x0255
            r9.setContentLength(r3)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = r17.getQueryString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r0 == 0) goto L_0x0242
            int r1 = r0.length()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r1 == 0) goto L_0x0242
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r1.<init>()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            javax.servlet.ServletContext r2 = r8._servletContext     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r2 = r2.getContextPath()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r2 = org.eclipse.jetty.util.URIUtil.addPaths(r2, r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r1.append(r2)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r2 = "?"
            r1.append(r2)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r1.append(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = r1.toString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = r9.encodeRedirectURL(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r9.sendRedirect(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            goto L_0x02e1
        L_0x0242:
            javax.servlet.ServletContext r0 = r8._servletContext     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = r0.getContextPath()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = org.eclipse.jetty.util.URIUtil.addPaths(r0, r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r0 = r9.encodeRedirectURL(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r9.sendRedirect(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            goto L_0x02e1
        L_0x0255:
            javax.servlet.RequestDispatcher r2 = r0.getRequestDispatcher(r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r2 == 0) goto L_0x02e1
            boolean r1 = r1.booleanValue()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r1 == 0) goto L_0x0266
            r2.include(r0, r9)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            goto L_0x02e1
        L_0x0266:
            java.lang.String r1 = "org.eclipse.jetty.server.welcome"
            r0.setAttribute(r1, r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r2.forward(r0, r9)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            goto L_0x02e1
        L_0x0270:
            org.eclipse.jetty.http.HttpContent$ResourceAsHttpContent r2 = new org.eclipse.jetty.http.HttpContent$ResourceAsHttpContent     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            org.eclipse.jetty.http.MimeTypes r3 = r8._mimeTypes     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r6 = r11.toString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            org.eclipse.jetty.io.Buffer r3 = r3.getMimeByExtension(r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            boolean r6 = r8._etags     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r2.<init>((org.eclipse.jetty.util.resource.Resource) r11, (org.eclipse.jetty.p119io.Buffer) r3, (boolean) r6)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            boolean r1 = r1.booleanValue()     // Catch:{ IllegalArgumentException -> 0x0295, all -> 0x0291 }
            if (r1 != 0) goto L_0x028d
            boolean r1 = r8.passConditionalHeaders(r0, r9, r11, r2)     // Catch:{ IllegalArgumentException -> 0x0295, all -> 0x0291 }
            if (r1 == 0) goto L_0x02e2
        L_0x028d:
            r8.sendDirectory(r0, r9, r11, r5)     // Catch:{ IllegalArgumentException -> 0x0295, all -> 0x0291 }
            goto L_0x02e2
        L_0x0291:
            r0 = move-exception
            r4 = r2
            goto L_0x0335
        L_0x0295:
            r0 = move-exception
            r12 = r2
            goto L_0x0309
        L_0x0299:
            java.lang.StringBuffer r1 = r17.getRequestURL()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            monitor-enter(r1)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r2 = ";"
            int r2 = r1.lastIndexOf(r2)     // Catch:{ all -> 0x02d3 }
            r5 = 47
            if (r2 >= 0) goto L_0x02ac
            r1.append(r5)     // Catch:{ all -> 0x02d3 }
            goto L_0x02af
        L_0x02ac:
            r1.insert(r2, r5)     // Catch:{ all -> 0x02d3 }
        L_0x02af:
            java.lang.String r0 = r17.getQueryString()     // Catch:{ all -> 0x02d3 }
            if (r0 == 0) goto L_0x02c3
            int r2 = r0.length()     // Catch:{ all -> 0x02d3 }
            if (r2 == 0) goto L_0x02c3
            r2 = 63
            r1.append(r2)     // Catch:{ all -> 0x02d3 }
            r1.append(r0)     // Catch:{ all -> 0x02d3 }
        L_0x02c3:
            r9.setContentLength(r3)     // Catch:{ all -> 0x02d3 }
            java.lang.String r0 = r1.toString()     // Catch:{ all -> 0x02d3 }
            java.lang.String r0 = r9.encodeRedirectURL(r0)     // Catch:{ all -> 0x02d3 }
            r9.sendRedirect(r0)     // Catch:{ all -> 0x02d3 }
            monitor-exit(r1)     // Catch:{ all -> 0x02d3 }
            goto L_0x02e1
        L_0x02d3:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x02d3 }
            throw r0     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
        L_0x02d6:
            boolean r0 = r1.booleanValue()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            if (r0 != 0) goto L_0x02ee
            r0 = 404(0x194, float:5.66E-43)
            r9.sendError(r0)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
        L_0x02e1:
            r2 = r4
        L_0x02e2:
            if (r2 == 0) goto L_0x02e8
            r2.release()
            goto L_0x0331
        L_0x02e8:
            if (r11 == 0) goto L_0x0331
            r11.release()
            goto L_0x0331
        L_0x02ee:
            java.io.FileNotFoundException r0 = new java.io.FileNotFoundException     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r1.<init>()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r2 = "!"
            r1.append(r2)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r1.append(r5)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            java.lang.String r1 = r1.toString()     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            r0.<init>(r1)     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
            throw r0     // Catch:{ IllegalArgumentException -> 0x0307, all -> 0x0305 }
        L_0x0305:
            r0 = move-exception
            goto L_0x0335
        L_0x0307:
            r0 = move-exception
            r12 = r4
        L_0x0309:
            r4 = r11
            goto L_0x0310
        L_0x030b:
            r0 = move-exception
            r11 = r4
            goto L_0x0335
        L_0x030e:
            r0 = move-exception
            r12 = r4
        L_0x0310:
            org.eclipse.jetty.util.log.Logger r1 = LOG     // Catch:{ all -> 0x0332 }
            java.lang.String r2 = "EXCEPTION "
            r1.warn((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x0332 }
            boolean r1 = r18.isCommitted()     // Catch:{ all -> 0x0332 }
            if (r1 != 0) goto L_0x0326
            r1 = 500(0x1f4, float:7.0E-43)
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0332 }
            r9.sendError(r1, r0)     // Catch:{ all -> 0x0332 }
        L_0x0326:
            if (r12 == 0) goto L_0x032c
            r12.release()
            goto L_0x0331
        L_0x032c:
            if (r4 == 0) goto L_0x0331
            r4.release()
        L_0x0331:
            return
        L_0x0332:
            r0 = move-exception
            r11 = r4
        L_0x0334:
            r4 = r12
        L_0x0335:
            if (r4 != 0) goto L_0x033d
            if (r11 == 0) goto L_0x0340
            r11.release()
            goto L_0x0340
        L_0x033d:
            r4.release()
        L_0x0340:
            goto L_0x0342
        L_0x0341:
            throw r0
        L_0x0342:
            goto L_0x0341
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.DefaultServlet.doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    private boolean hasDefinedRange(Enumeration<String> enumeration) {
        return enumeration != null && enumeration.hasMoreElements();
    }

    /* access modifiers changed from: protected */
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }

    /* access modifiers changed from: protected */
    public void doTrace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendError(405);
    }

    /* access modifiers changed from: protected */
    public void doOptions(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.setHeader(HttpHeaders.ALLOW, "GET,HEAD,POST,OPTIONS");
    }

    private String getWelcomeFile(String str) throws MalformedURLException, IOException {
        PathMap.Entry holderEntry;
        String str2 = null;
        if (this._welcomes == null) {
            return null;
        }
        int i = 0;
        while (true) {
            String[] strArr = this._welcomes;
            if (i >= strArr.length) {
                return str2;
            }
            String addPaths = URIUtil.addPaths(str, strArr[i]);
            Resource resource = getResource(addPaths);
            if (resource != null && resource.exists()) {
                return this._welcomes[i];
            }
            if ((this._welcomeServlets || this._welcomeExactServlets) && str2 == null && (holderEntry = this._servletHandler.getHolderEntry(addPaths)) != null && holderEntry.getValue() != this._defaultHolder && (this._welcomeServlets || (this._welcomeExactServlets && holderEntry.getKey().equals(addPaths)))) {
                str2 = addPaths;
            }
            i++;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0055 A[Catch:{ IllegalArgumentException -> 0x0175 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean passConditionalHeaders(javax.servlet.http.HttpServletRequest r18, javax.servlet.http.HttpServletResponse r19, org.eclipse.jetty.util.resource.Resource r20, org.eclipse.jetty.http.HttpContent r21) throws java.io.IOException {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            r2 = r19
            java.lang.String r3 = "If-Modified-Since"
            java.lang.String r4 = r18.getMethod()     // Catch:{ IllegalArgumentException -> 0x0175 }
            java.lang.String r5 = "HEAD"
            boolean r4 = r4.equals(r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r5 = 1
            if (r4 != 0) goto L_0x0174
            boolean r4 = r1._etags     // Catch:{ IllegalArgumentException -> 0x0175 }
            r6 = 412(0x19c, float:5.77E-43)
            r7 = 304(0x130, float:4.26E-43)
            r8 = 0
            if (r4 == 0) goto L_0x00f4
            java.lang.String r4 = "If-Match"
            java.lang.String r4 = r0.getHeader(r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
            java.lang.String r9 = ", "
            if (r4 == 0) goto L_0x0060
            if (r21 == 0) goto L_0x0052
            org.eclipse.jetty.io.Buffer r10 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r10 == 0) goto L_0x0052
            org.eclipse.jetty.util.QuotedStringTokenizer r10 = new org.eclipse.jetty.util.QuotedStringTokenizer     // Catch:{ IllegalArgumentException -> 0x0175 }
            r10.<init>(r4, r9, r8, r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r4 = 0
        L_0x0036:
            if (r4 != 0) goto L_0x0053
            boolean r11 = r10.hasMoreTokens()     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r11 == 0) goto L_0x0053
            java.lang.String r11 = r10.nextToken()     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r12 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            java.lang.String r12 = r12.toString()     // Catch:{ IllegalArgumentException -> 0x0175 }
            boolean r11 = r12.equals(r11)     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r11 == 0) goto L_0x0036
            r4 = 1
            goto L_0x0036
        L_0x0052:
            r4 = 0
        L_0x0053:
            if (r4 != 0) goto L_0x0060
            org.eclipse.jetty.server.Response r0 = org.eclipse.jetty.server.Response.getResponse(r19)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.reset(r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.setStatus(r6)     // Catch:{ IllegalArgumentException -> 0x0175 }
            return r8
        L_0x0060:
            java.lang.String r4 = "If-None-Match"
            java.lang.String r4 = r0.getHeader(r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r4 == 0) goto L_0x00f4
            if (r21 == 0) goto L_0x00f4
            org.eclipse.jetty.io.Buffer r10 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r10 == 0) goto L_0x00f4
            org.eclipse.jetty.io.Buffer r3 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            java.lang.String r3 = r3.toString()     // Catch:{ IllegalArgumentException -> 0x0175 }
            java.lang.String r6 = "o.e.j.s.GzipFilter.ETag"
            java.lang.Object r0 = r0.getAttribute(r6)     // Catch:{ IllegalArgumentException -> 0x0175 }
            boolean r0 = r3.equals(r0)     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r0 == 0) goto L_0x0098
            org.eclipse.jetty.server.Response r0 = org.eclipse.jetty.server.Response.getResponse(r19)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.reset(r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.setStatus(r7)     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.http.HttpFields r0 = r0.getHttpFields()     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r3 = org.eclipse.jetty.http.HttpHeaders.ETAG_BUFFER     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.put((org.eclipse.jetty.p119io.Buffer) r3, (java.lang.String) r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
            return r8
        L_0x0098:
            org.eclipse.jetty.io.Buffer r0 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            java.lang.String r0 = r0.toString()     // Catch:{ IllegalArgumentException -> 0x0175 }
            boolean r0 = r0.equals(r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r0 == 0) goto L_0x00be
            org.eclipse.jetty.server.Response r0 = org.eclipse.jetty.server.Response.getResponse(r19)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.reset(r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.setStatus(r7)     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.http.HttpFields r0 = r0.getHttpFields()     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r3 = org.eclipse.jetty.http.HttpHeaders.ETAG_BUFFER     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r4 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.put((org.eclipse.jetty.p119io.Buffer) r3, (org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
            return r8
        L_0x00be:
            org.eclipse.jetty.util.QuotedStringTokenizer r0 = new org.eclipse.jetty.util.QuotedStringTokenizer     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.<init>(r4, r9, r8, r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
        L_0x00c3:
            boolean r3 = r0.hasMoreTokens()     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r3 == 0) goto L_0x00f3
            java.lang.String r3 = r0.nextToken()     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r4 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            java.lang.String r4 = r4.toString()     // Catch:{ IllegalArgumentException -> 0x0175 }
            boolean r3 = r4.equals(r3)     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r3 == 0) goto L_0x00c3
            org.eclipse.jetty.server.Response r0 = org.eclipse.jetty.server.Response.getResponse(r19)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.reset(r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.setStatus(r7)     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.http.HttpFields r0 = r0.getHttpFields()     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r3 = org.eclipse.jetty.http.HttpHeaders.ETAG_BUFFER     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r4 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.put((org.eclipse.jetty.p119io.Buffer) r3, (org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
            return r8
        L_0x00f3:
            return r5
        L_0x00f4:
            java.lang.String r4 = r0.getHeader(r3)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r9 = -1
            r11 = 1000(0x3e8, double:4.94E-321)
            if (r4 == 0) goto L_0x015c
            org.eclipse.jetty.server.Response r13 = org.eclipse.jetty.server.Response.getResponse(r19)     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r21 == 0) goto L_0x012f
            org.eclipse.jetty.io.Buffer r14 = r21.getLastModified()     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r14 == 0) goto L_0x012f
            java.lang.String r14 = r14.toString()     // Catch:{ IllegalArgumentException -> 0x0175 }
            boolean r4 = r4.equals(r14)     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r4 == 0) goto L_0x012f
            r13.reset(r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r13.setStatus(r7)     // Catch:{ IllegalArgumentException -> 0x0175 }
            boolean r0 = r1._etags     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r0 == 0) goto L_0x012b
            org.eclipse.jetty.http.HttpFields r0 = r13.getHttpFields()     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r3 = org.eclipse.jetty.http.HttpHeaders.ETAG_BUFFER     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r4 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.add((org.eclipse.jetty.p119io.Buffer) r3, (org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
        L_0x012b:
            r13.flushBuffer()     // Catch:{ IllegalArgumentException -> 0x0175 }
            return r8
        L_0x012f:
            long r3 = r0.getDateHeader(r3)     // Catch:{ IllegalArgumentException -> 0x0175 }
            int r14 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r14 == 0) goto L_0x015c
            long r14 = r20.lastModified()     // Catch:{ IllegalArgumentException -> 0x0175 }
            long r14 = r14 / r11
            long r3 = r3 / r11
            int r16 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
            if (r16 > 0) goto L_0x015c
            r13.reset(r5)     // Catch:{ IllegalArgumentException -> 0x0175 }
            r13.setStatus(r7)     // Catch:{ IllegalArgumentException -> 0x0175 }
            boolean r0 = r1._etags     // Catch:{ IllegalArgumentException -> 0x0175 }
            if (r0 == 0) goto L_0x0158
            org.eclipse.jetty.http.HttpFields r0 = r13.getHttpFields()     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r3 = org.eclipse.jetty.http.HttpHeaders.ETAG_BUFFER     // Catch:{ IllegalArgumentException -> 0x0175 }
            org.eclipse.jetty.io.Buffer r4 = r21.getETag()     // Catch:{ IllegalArgumentException -> 0x0175 }
            r0.add((org.eclipse.jetty.p119io.Buffer) r3, (org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ IllegalArgumentException -> 0x0175 }
        L_0x0158:
            r13.flushBuffer()     // Catch:{ IllegalArgumentException -> 0x0175 }
            return r8
        L_0x015c:
            java.lang.String r3 = "If-Unmodified-Since"
            long r3 = r0.getDateHeader(r3)     // Catch:{ IllegalArgumentException -> 0x0175 }
            int r0 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r0 == 0) goto L_0x0174
            long r9 = r20.lastModified()     // Catch:{ IllegalArgumentException -> 0x0175 }
            long r9 = r9 / r11
            long r3 = r3 / r11
            int r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r0 <= 0) goto L_0x0174
            r2.sendError(r6)     // Catch:{ IllegalArgumentException -> 0x0175 }
            return r8
        L_0x0174:
            return r5
        L_0x0175:
            r0 = move-exception
            boolean r3 = r19.isCommitted()
            if (r3 != 0) goto L_0x0185
            r3 = 400(0x190, float:5.6E-43)
            java.lang.String r4 = r0.getMessage()
            r2.sendError(r3, r4)
        L_0x0185:
            goto L_0x0187
        L_0x0186:
            throw r0
        L_0x0187:
            goto L_0x0186
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.DefaultServlet.passConditionalHeaders(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.eclipse.jetty.util.resource.Resource, org.eclipse.jetty.http.HttpContent):boolean");
    }

    /* access modifiers changed from: protected */
    public void sendDirectory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Resource resource, String str) throws IOException {
        if (!this._dirAllowed) {
            httpServletResponse.sendError(403);
            return;
        }
        String addPaths = URIUtil.addPaths(httpServletRequest.getRequestURI(), "/");
        Resource resource2 = this._resourceBase;
        if (resource2 != null) {
            if (resource2 instanceof ResourceCollection) {
                resource = resource2.addPath(str);
            }
        } else if (this._contextHandler.getBaseResource() instanceof ResourceCollection) {
            resource = this._contextHandler.getBaseResource().addPath(str);
        }
        boolean z = true;
        if (str.length() <= 1) {
            z = false;
        }
        String listHTML = resource.getListHTML(addPaths, z);
        if (listHTML == null) {
            httpServletResponse.sendError(403, "No directory");
            return;
        }
        byte[] bytes = listHTML.getBytes("UTF-8");
        httpServletResponse.setContentType("text/html; charset=UTF-8");
        httpServletResponse.setContentLength(bytes.length);
        httpServletResponse.getOutputStream().write(bytes);
    }

    /* access modifiers changed from: protected */
    public void sendData(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, boolean z, Resource resource, HttpContent httpContent, Enumeration enumeration) throws IOException {
        boolean z2;
        long j;
        boolean z3;
        OutputStream outputStream;
        Buffer buffer;
        HttpServletResponse httpServletResponse2 = httpServletResponse;
        HttpContent httpContent2 = httpContent;
        Enumeration enumeration2 = enumeration;
        if (httpContent2 == null) {
            j = resource.length();
            z2 = false;
        } else {
            Connector connector = AbstractHttpConnection.getCurrentConnection().getConnector();
            z2 = (connector instanceof NIOConnector) && ((NIOConnector) connector).getUseDirectBuffers() && !(connector instanceof SslConnector);
            j = httpContent.getContentLength();
        }
        try {
            outputStream = httpServletResponse.getOutputStream();
            z3 = outputStream instanceof HttpOutput ? ((HttpOutput) outputStream).isWritten() : AbstractHttpConnection.getCurrentConnection().getGenerator().isWritten();
        } catch (IllegalStateException unused) {
            outputStream = new WriterOutputStream(httpServletResponse.getWriter());
            z3 = true;
        }
        long j2 = -1;
        if (enumeration2 != null && enumeration.hasMoreElements() && j >= 0) {
            List satisfiableRanges = InclusiveByteRange.satisfiableRanges(enumeration2, j);
            if (satisfiableRanges == null || satisfiableRanges.size() == 0) {
                writeHeaders(httpServletResponse2, httpContent2, j);
                httpServletResponse2.setStatus(416);
                httpServletResponse2.setHeader(HttpHeaders.CONTENT_RANGE, InclusiveByteRange.to416HeaderRangeString(j));
                resource.writeTo(outputStream, 0, j);
            } else if (satisfiableRanges.size() == 1) {
                InclusiveByteRange inclusiveByteRange = (InclusiveByteRange) satisfiableRanges.get(0);
                long size = inclusiveByteRange.getSize(j);
                writeHeaders(httpServletResponse2, httpContent2, size);
                httpServletResponse2.setStatus(206);
                httpServletResponse2.setHeader(HttpHeaders.CONTENT_RANGE, inclusiveByteRange.toHeaderRangeString(j));
                resource.writeTo(outputStream, inclusiveByteRange.getFirst(j), size);
            } else {
                writeHeaders(httpServletResponse2, httpContent2, -1);
                String obj = httpContent.getContentType() == null ? null : httpContent.getContentType().toString();
                if (obj == null) {
                    Logger logger = LOG;
                    logger.warn("Unknown mimetype for " + httpServletRequest.getRequestURI(), new Object[0]);
                }
                MultiPartOutputStream multiPartOutputStream = new MultiPartOutputStream(outputStream);
                httpServletResponse2.setStatus(206);
                String str = httpServletRequest.getHeader(HttpHeaders.REQUEST_RANGE) != null ? "multipart/x-byteranges; boundary=" : "multipart/byteranges; boundary=";
                httpServletResponse2.setContentType(str + multiPartOutputStream.getBoundary());
                InputStream inputStream = resource.getInputStream();
                String[] strArr = new String[satisfiableRanges.size()];
                int i = 0;
                int i2 = 0;
                while (i < satisfiableRanges.size()) {
                    InclusiveByteRange inclusiveByteRange2 = (InclusiveByteRange) satisfiableRanges.get(i);
                    strArr[i] = inclusiveByteRange2.toHeaderRangeString(j);
                    i2 = (int) (((long) i2) + ((long) ((i > 0 ? 2 : 0) + 2 + multiPartOutputStream.getBoundary().length() + 2 + (obj == null ? 0 : 14 + obj.length()) + 2 + 13 + 2 + strArr[i].length() + 2 + 2)) + (inclusiveByteRange2.getLast(j) - inclusiveByteRange2.getFirst(j)) + 1);
                    i++;
                }
                httpServletResponse2.setContentLength(i2 + multiPartOutputStream.getBoundary().length() + 4 + 2 + 2);
                long j3 = 0;
                for (int i3 = 0; i3 < satisfiableRanges.size(); i3++) {
                    InclusiveByteRange inclusiveByteRange3 = (InclusiveByteRange) satisfiableRanges.get(i3);
                    multiPartOutputStream.startPart(obj, new String[]{"Content-Range: " + strArr[i3]});
                    long first = inclusiveByteRange3.getFirst(j);
                    long size2 = inclusiveByteRange3.getSize(j);
                    if (inputStream != null) {
                        if (first < j3) {
                            inputStream.close();
                            inputStream = resource.getInputStream();
                            j3 = 0;
                        }
                        if (j3 < first) {
                            inputStream.skip(first - j3);
                            j3 = first;
                        }
                        C2439IO.copy(inputStream, (OutputStream) multiPartOutputStream, size2);
                        j3 += size2;
                    } else {
                        resource.writeTo(multiPartOutputStream, first, size2);
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                multiPartOutputStream.close();
            }
        } else if (z) {
            resource.writeTo(outputStream, 0, j);
        } else if (httpContent2 == null || z3 || !(outputStream instanceof HttpOutput)) {
            if (!z3) {
                j2 = j;
            }
            writeHeaders(httpServletResponse2, httpContent2, j2);
            if (httpContent2 == null) {
                buffer = null;
            } else {
                buffer = httpContent.getIndirectBuffer();
            }
            if (buffer != null) {
                buffer.writeTo(outputStream);
            } else {
                resource.writeTo(outputStream, 0, j);
            }
        } else if (httpServletResponse2 instanceof Response) {
            writeOptionHeaders(((Response) httpServletResponse2).getHttpFields());
            ((AbstractHttpConnection.Output) outputStream).sendContent(httpContent2);
        } else {
            Buffer directBuffer = z2 ? httpContent.getDirectBuffer() : httpContent.getIndirectBuffer();
            if (directBuffer != null) {
                writeHeaders(httpServletResponse2, httpContent2, j);
                ((AbstractHttpConnection.Output) outputStream).sendContent(directBuffer);
                return;
            }
            writeHeaders(httpServletResponse2, httpContent2, j);
            resource.writeTo(outputStream, 0, j);
        }
    }

    /* access modifiers changed from: protected */
    public void writeHeaders(HttpServletResponse httpServletResponse, HttpContent httpContent, long j) throws IOException {
        if (httpContent.getContentType() != null && httpServletResponse.getContentType() == null) {
            httpServletResponse.setContentType(httpContent.getContentType().toString());
        }
        if (httpServletResponse instanceof Response) {
            Response response = (Response) httpServletResponse;
            HttpFields httpFields = response.getHttpFields();
            if (httpContent.getLastModified() != null) {
                httpFields.put(HttpHeaders.LAST_MODIFIED_BUFFER, httpContent.getLastModified());
            } else if (httpContent.getResource() != null) {
                long lastModified = httpContent.getResource().lastModified();
                if (lastModified != -1) {
                    httpFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, lastModified);
                }
            }
            if (j != -1) {
                response.setLongContentLength(j);
            }
            writeOptionHeaders(httpFields);
            if (this._etags) {
                httpFields.put(HttpHeaders.ETAG_BUFFER, httpContent.getETag());
                return;
            }
            return;
        }
        long lastModified2 = httpContent.getResource().lastModified();
        if (lastModified2 >= 0) {
            httpServletResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified2);
        }
        if (j != -1) {
            if (j < 2147483647L) {
                httpServletResponse.setContentLength((int) j);
            } else {
                httpServletResponse.setHeader("Content-Length", Long.toString(j));
            }
        }
        writeOptionHeaders(httpServletResponse);
        if (this._etags) {
            httpServletResponse.setHeader(HttpHeaders.ETAG, httpContent.getETag().toString());
        }
    }

    /* access modifiers changed from: protected */
    public void writeOptionHeaders(HttpFields httpFields) throws IOException {
        if (this._acceptRanges) {
            httpFields.put(HttpHeaders.ACCEPT_RANGES_BUFFER, HttpHeaderValues.BYTES_BUFFER);
        }
        if (this._cacheControl != null) {
            httpFields.put(HttpHeaders.CACHE_CONTROL_BUFFER, (Buffer) this._cacheControl);
        }
    }

    /* access modifiers changed from: protected */
    public void writeOptionHeaders(HttpServletResponse httpServletResponse) throws IOException {
        if (this._acceptRanges) {
            httpServletResponse.setHeader(HttpHeaders.ACCEPT_RANGES, HttpHeaderValues.BYTES);
        }
        ByteArrayBuffer byteArrayBuffer = this._cacheControl;
        if (byteArrayBuffer != null) {
            httpServletResponse.setHeader("Cache-Control", byteArrayBuffer.toString());
        }
    }

    public void destroy() {
        ResourceCache resourceCache = this._cache;
        if (resourceCache != null) {
            resourceCache.flushCache();
        }
        super.destroy();
    }
}
