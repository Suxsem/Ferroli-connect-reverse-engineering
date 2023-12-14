package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Locale;
import javax.servlet.http.Cookie;
import kotlin.text.Typography;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.RolloverFileOutputStream;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class NCSARequestLog extends AbstractLifeCycle implements RequestLog {
    private static final Logger LOG = Log.getLogger((Class<?>) NCSARequestLog.class);
    private static ThreadLocal<StringBuilder> _buffers = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        public StringBuilder initialValue() {
            return new StringBuilder(256);
        }
    };
    private boolean _append = true;
    private boolean _closeOut;
    private boolean _extended = true;
    private transient OutputStream _fileOut;
    private String _filename;
    private String _filenameDateFormat = null;
    private transient PathMap _ignorePathMap;
    private String[] _ignorePaths;
    private boolean _logCookies = false;
    private transient DateCache _logDateCache;
    private String _logDateFormat = "dd/MMM/yyyy:HH:mm:ss Z";
    private boolean _logDispatch = false;
    private boolean _logLatency = false;
    private Locale _logLocale = Locale.getDefault();
    private boolean _logServer = false;
    private String _logTimeZone = "GMT";
    private transient OutputStream _out;
    private boolean _preferProxiedForAddress;
    private int _retainDays = 31;
    private transient Writer _writer;

    public NCSARequestLog() {
    }

    public NCSARequestLog(String str) {
        setFilename(str);
    }

    public void setFilename(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() == 0) {
                str = null;
            }
        }
        this._filename = str;
    }

    public String getFilename() {
        return this._filename;
    }

    public String getDatedFilename() {
        OutputStream outputStream = this._fileOut;
        if (outputStream instanceof RolloverFileOutputStream) {
            return ((RolloverFileOutputStream) outputStream).getDatedFilename();
        }
        return null;
    }

    public void setLogDateFormat(String str) {
        this._logDateFormat = str;
    }

    public String getLogDateFormat() {
        return this._logDateFormat;
    }

    public void setLogLocale(Locale locale) {
        this._logLocale = locale;
    }

    public Locale getLogLocale() {
        return this._logLocale;
    }

    public void setLogTimeZone(String str) {
        this._logTimeZone = str;
    }

    public String getLogTimeZone() {
        return this._logTimeZone;
    }

    public void setRetainDays(int i) {
        this._retainDays = i;
    }

    public int getRetainDays() {
        return this._retainDays;
    }

    public void setExtended(boolean z) {
        this._extended = z;
    }

    public boolean isExtended() {
        return this._extended;
    }

    public void setAppend(boolean z) {
        this._append = z;
    }

    public boolean isAppend() {
        return this._append;
    }

    public void setIgnorePaths(String[] strArr) {
        this._ignorePaths = strArr;
    }

    public String[] getIgnorePaths() {
        return this._ignorePaths;
    }

    public void setLogCookies(boolean z) {
        this._logCookies = z;
    }

    public boolean getLogCookies() {
        return this._logCookies;
    }

    public void setLogServer(boolean z) {
        this._logServer = z;
    }

    public boolean getLogServer() {
        return this._logServer;
    }

    public void setLogLatency(boolean z) {
        this._logLatency = z;
    }

    public boolean getLogLatency() {
        return this._logLatency;
    }

    public void setPreferProxiedForAddress(boolean z) {
        this._preferProxiedForAddress = z;
    }

    public boolean getPreferProxiedForAddress() {
        return this._preferProxiedForAddress;
    }

    public void setFilenameDateFormat(String str) {
        this._filenameDateFormat = str;
    }

    public String getFilenameDateFormat() {
        return this._filenameDateFormat;
    }

    public void setLogDispatch(boolean z) {
        this._logDispatch = z;
    }

    public boolean isLogDispatch() {
        return this._logDispatch;
    }

    public void log(Request request, Response response) {
        Request request2 = request;
        try {
            if ((this._ignorePathMap == null || this._ignorePathMap.getMatch(request.getRequestURI()) == null) && this._fileOut != null) {
                StringBuilder sb = _buffers.get();
                sb.setLength(0);
                if (this._logServer) {
                    sb.append(request.getServerName());
                    sb.append(' ');
                }
                String str = null;
                if (this._preferProxiedForAddress) {
                    str = request2.getHeader(HttpHeaders.X_FORWARDED_FOR);
                }
                if (str == null) {
                    str = request.getRemoteAddr();
                }
                sb.append(str);
                sb.append(" - ");
                Authentication authentication = request.getAuthentication();
                if (authentication instanceof Authentication.User) {
                    sb.append(((Authentication.User) authentication).getUserIdentity().getUserPrincipal().getName());
                } else {
                    sb.append(" - ");
                }
                sb.append(" [");
                if (this._logDateCache != null) {
                    sb.append(this._logDateCache.format(request.getTimeStamp()));
                } else {
                    sb.append(request.getTimeStampBuffer().toString());
                }
                sb.append("] \"");
                sb.append(request.getMethod());
                sb.append(' ');
                sb.append(request.getUri().toString());
                sb.append(' ');
                sb.append(request.getProtocol());
                sb.append("\" ");
                if (request.getAsyncContinuation().isInitial()) {
                    int status = response.getStatus();
                    if (status <= 0) {
                        status = 404;
                    }
                    sb.append((char) (((status / 100) % 10) + 48));
                    sb.append((char) (((status / 10) % 10) + 48));
                    sb.append((char) ((status % 10) + 48));
                } else {
                    sb.append("Async");
                }
                long contentCount = response.getContentCount();
                if (contentCount >= 0) {
                    sb.append(' ');
                    if (contentCount > 99999) {
                        sb.append(contentCount);
                    } else {
                        if (contentCount > 9999) {
                            sb.append((char) ((int) (((contentCount / 10000) % 10) + 48)));
                        }
                        if (contentCount > 999) {
                            sb.append((char) ((int) (((contentCount / 1000) % 10) + 48)));
                        }
                        if (contentCount > 99) {
                            sb.append((char) ((int) (((contentCount / 100) % 10) + 48)));
                        }
                        if (contentCount > 9) {
                            sb.append((char) ((int) (((contentCount / 10) % 10) + 48)));
                        }
                        sb.append((char) ((int) ((contentCount % 10) + 48)));
                    }
                    sb.append(' ');
                } else {
                    sb.append(" - ");
                }
                if (this._extended) {
                    logExtended(request2, response, sb);
                }
                if (this._logCookies) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        if (cookies.length != 0) {
                            sb.append(" \"");
                            for (int i = 0; i < cookies.length; i++) {
                                if (i != 0) {
                                    sb.append(';');
                                }
                                sb.append(cookies[i].getName());
                                sb.append('=');
                                sb.append(cookies[i].getValue());
                            }
                            sb.append(Typography.quote);
                        }
                    }
                    sb.append(" -");
                }
                if (this._logDispatch || this._logLatency) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (this._logDispatch) {
                        long dispatchTime = request.getDispatchTime();
                        sb.append(' ');
                        if (dispatchTime == 0) {
                            dispatchTime = request.getTimeStamp();
                        }
                        sb.append(currentTimeMillis - dispatchTime);
                    }
                    if (this._logLatency) {
                        sb.append(' ');
                        sb.append(currentTimeMillis - request.getTimeStamp());
                    }
                }
                sb.append(StringUtil.__LINE_SEPARATOR);
                write(sb.toString());
            }
        } catch (IOException e) {
            LOG.warn(e);
        }
    }

    /* access modifiers changed from: protected */
    public void write(String str) throws IOException {
        synchronized (this) {
            if (this._writer != null) {
                this._writer.write(str);
                this._writer.flush();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void logExtended(Request request, Response response, StringBuilder sb) throws IOException {
        String header = request.getHeader(HttpHeaders.REFERER);
        if (header == null) {
            sb.append("\"-\" ");
        } else {
            sb.append(Typography.quote);
            sb.append(header);
            sb.append("\" ");
        }
        String header2 = request.getHeader(HttpHeaders.USER_AGENT);
        if (header2 == null) {
            sb.append("\"-\" ");
            return;
        }
        sb.append(Typography.quote);
        sb.append(header2);
        sb.append(Typography.quote);
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    protected synchronized void doStart() throws java.lang.Exception {
        /*
            r9 = this;
            monitor-enter(r9)
            java.lang.String r0 = r9._logDateFormat     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0017
            org.eclipse.jetty.util.DateCache r0 = new org.eclipse.jetty.util.DateCache     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = r9._logDateFormat     // Catch:{ all -> 0x0096 }
            java.util.Locale r2 = r9._logLocale     // Catch:{ all -> 0x0096 }
            r0.<init>((java.lang.String) r1, (java.util.Locale) r2)     // Catch:{ all -> 0x0096 }
            r9._logDateCache = r0     // Catch:{ all -> 0x0096 }
            org.eclipse.jetty.util.DateCache r0 = r9._logDateCache     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = r9._logTimeZone     // Catch:{ all -> 0x0096 }
            r0.setTimeZoneID(r1)     // Catch:{ all -> 0x0096 }
        L_0x0017:
            java.lang.String r0 = r9._filename     // Catch:{ all -> 0x0096 }
            r1 = 0
            if (r0 == 0) goto L_0x0053
            org.eclipse.jetty.util.RolloverFileOutputStream r0 = new org.eclipse.jetty.util.RolloverFileOutputStream     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = r9._filename     // Catch:{ all -> 0x0096 }
            boolean r4 = r9._append     // Catch:{ all -> 0x0096 }
            int r5 = r9._retainDays     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = r9._logTimeZone     // Catch:{ all -> 0x0096 }
            java.util.TimeZone r6 = java.util.TimeZone.getTimeZone(r2)     // Catch:{ all -> 0x0096 }
            java.lang.String r7 = r9._filenameDateFormat     // Catch:{ all -> 0x0096 }
            r8 = 0
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0096 }
            r9._fileOut = r0     // Catch:{ all -> 0x0096 }
            r0 = 1
            r9._closeOut = r0     // Catch:{ all -> 0x0096 }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x0096 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0096 }
            r2.<init>()     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = "Opened "
            r2.append(r3)     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = r9.getDatedFilename()     // Catch:{ all -> 0x0096 }
            r2.append(r3)     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0096 }
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x0096 }
            r0.info((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x0096 }
            goto L_0x0057
        L_0x0053:
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ all -> 0x0096 }
            r9._fileOut = r0     // Catch:{ all -> 0x0096 }
        L_0x0057:
            java.io.OutputStream r0 = r9._fileOut     // Catch:{ all -> 0x0096 }
            r9._out = r0     // Catch:{ all -> 0x0096 }
            java.lang.String[] r0 = r9._ignorePaths     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0080
            java.lang.String[] r0 = r9._ignorePaths     // Catch:{ all -> 0x0096 }
            int r0 = r0.length     // Catch:{ all -> 0x0096 }
            if (r0 <= 0) goto L_0x0080
            org.eclipse.jetty.http.PathMap r0 = new org.eclipse.jetty.http.PathMap     // Catch:{ all -> 0x0096 }
            r0.<init>()     // Catch:{ all -> 0x0096 }
            r9._ignorePathMap = r0     // Catch:{ all -> 0x0096 }
        L_0x006b:
            java.lang.String[] r0 = r9._ignorePaths     // Catch:{ all -> 0x0096 }
            int r0 = r0.length     // Catch:{ all -> 0x0096 }
            if (r1 >= r0) goto L_0x0083
            org.eclipse.jetty.http.PathMap r0 = r9._ignorePathMap     // Catch:{ all -> 0x0096 }
            java.lang.String[] r2 = r9._ignorePaths     // Catch:{ all -> 0x0096 }
            r2 = r2[r1]     // Catch:{ all -> 0x0096 }
            java.lang.String[] r3 = r9._ignorePaths     // Catch:{ all -> 0x0096 }
            r3 = r3[r1]     // Catch:{ all -> 0x0096 }
            r0.put(r2, r3)     // Catch:{ all -> 0x0096 }
            int r1 = r1 + 1
            goto L_0x006b
        L_0x0080:
            r0 = 0
            r9._ignorePathMap = r0     // Catch:{ all -> 0x0096 }
        L_0x0083:
            monitor-enter(r9)     // Catch:{ all -> 0x0096 }
            java.io.OutputStreamWriter r0 = new java.io.OutputStreamWriter     // Catch:{ all -> 0x0093 }
            java.io.OutputStream r1 = r9._out     // Catch:{ all -> 0x0093 }
            r0.<init>(r1)     // Catch:{ all -> 0x0093 }
            r9._writer = r0     // Catch:{ all -> 0x0093 }
            monitor-exit(r9)     // Catch:{ all -> 0x0093 }
            super.doStart()     // Catch:{ all -> 0x0096 }
            monitor-exit(r9)
            return
        L_0x0093:
            r0 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0093 }
            throw r0     // Catch:{ all -> 0x0096 }
        L_0x0096:
            r0 = move-exception
            monitor-exit(r9)
            goto L_0x009a
        L_0x0099:
            throw r0
        L_0x009a:
            goto L_0x0099
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.NCSARequestLog.doStart():void");
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        synchronized (this) {
            super.doStop();
            try {
                if (this._writer != null) {
                    this._writer.flush();
                }
            } catch (IOException e) {
                LOG.ignore(e);
            }
            if (this._out != null && this._closeOut) {
                try {
                    this._out.close();
                } catch (IOException e2) {
                    LOG.ignore(e2);
                }
            }
            this._out = null;
            this._fileOut = null;
            this._closeOut = false;
            this._logDateCache = null;
            this._writer = null;
        }
    }
}
