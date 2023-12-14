package org.eclipse.jetty.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.http.HttpBuffers;
import org.eclipse.jetty.http.HttpBuffersImpl;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.statistic.CounterStatistic;
import org.eclipse.jetty.util.statistic.SampleStatistic;
import org.eclipse.jetty.util.thread.ThreadPool;

public abstract class AbstractConnector extends AggregateLifeCycle implements HttpBuffers, Connector, Dumpable {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) AbstractConnector.class);
    private int _acceptQueueSize = 0;
    /* access modifiers changed from: private */
    public int _acceptorPriorityOffset = 0;
    /* access modifiers changed from: private */
    public transient Thread[] _acceptorThreads;
    private int _acceptors = 1;
    protected final HttpBuffersImpl _buffers = new HttpBuffersImpl();
    private int _confidentialPort = 0;
    private String _confidentialScheme = "https";
    private final SampleStatistic _connectionDurationStats = new SampleStatistic();
    private final CounterStatistic _connectionStats = new CounterStatistic();
    private boolean _forwarded;
    private String _forwardedCipherSuiteHeader;
    private String _forwardedForHeader = HttpHeaders.X_FORWARDED_FOR;
    private String _forwardedHostHeader = HttpHeaders.X_FORWARDED_HOST;
    private String _forwardedProtoHeader = HttpHeaders.X_FORWARDED_PROTO;
    private String _forwardedServerHeader = HttpHeaders.X_FORWARDED_SERVER;
    private String _forwardedSslSessionIdHeader;
    private String _host;
    private String _hostHeader;
    private int _integralPort = 0;
    private String _integralScheme = "https";
    protected int _lowResourceMaxIdleTime = -1;
    protected int _maxIdleTime = 200000;
    private String _name;
    private int _port = 0;
    private final SampleStatistic _requestStats = new SampleStatistic();
    private boolean _reuseAddress = true;
    private Server _server;
    protected int _soLingerTime = -1;
    private final AtomicLong _statsStartedAt = new AtomicLong(-1);
    private ThreadPool _threadPool;
    private boolean _useDNS;

    /* access modifiers changed from: protected */
    public abstract void accept(int i) throws IOException, InterruptedException;

    public boolean isIntegral(Request request) {
        return false;
    }

    public void persist(EndPoint endPoint) throws IOException {
    }

    public void stopAccept(int i) throws Exception {
    }

    public AbstractConnector() {
        addBean(this._buffers);
    }

    public Server getServer() {
        return this._server;
    }

    public void setServer(Server server) {
        this._server = server;
    }

    public ThreadPool getThreadPool() {
        return this._threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        removeBean(this._threadPool);
        this._threadPool = threadPool;
        addBean(this._threadPool);
    }

    public void setHost(String str) {
        this._host = str;
    }

    public String getHost() {
        return this._host;
    }

    public void setPort(int i) {
        this._port = i;
    }

    public int getPort() {
        return this._port;
    }

    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    public void setMaxIdleTime(int i) {
        this._maxIdleTime = i;
    }

    public int getLowResourcesMaxIdleTime() {
        return this._lowResourceMaxIdleTime;
    }

    public void setLowResourcesMaxIdleTime(int i) {
        this._lowResourceMaxIdleTime = i;
    }

    @Deprecated
    public final int getLowResourceMaxIdleTime() {
        return getLowResourcesMaxIdleTime();
    }

    @Deprecated
    public final void setLowResourceMaxIdleTime(int i) {
        setLowResourcesMaxIdleTime(i);
    }

    public int getSoLingerTime() {
        return this._soLingerTime;
    }

    public int getAcceptQueueSize() {
        return this._acceptQueueSize;
    }

    public void setAcceptQueueSize(int i) {
        this._acceptQueueSize = i;
    }

    public int getAcceptors() {
        return this._acceptors;
    }

    public void setAcceptors(int i) {
        if (i > Runtime.getRuntime().availableProcessors() * 2) {
            Logger logger = LOG;
            logger.warn("Acceptors should be <=2*availableProcessors: " + this, new Object[0]);
        }
        this._acceptors = i;
    }

    public void setSoLingerTime(int i) {
        this._soLingerTime = i;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        if (this._server != null) {
            open();
            if (this._threadPool == null) {
                this._threadPool = this._server.getThreadPool();
                addBean(this._threadPool, false);
            }
            super.doStart();
            synchronized (this) {
                this._acceptorThreads = new Thread[getAcceptors()];
                int i = 0;
                while (i < this._acceptorThreads.length) {
                    if (this._threadPool.dispatch(new Acceptor(i))) {
                        i++;
                    } else {
                        throw new IllegalStateException("!accepting");
                    }
                }
                if (this._threadPool.isLowOnThreads()) {
                    LOG.warn("insufficient threads configured for {}", this);
                }
            }
            LOG.info("Started {}", this);
            return;
        }
        throw new IllegalStateException("No server");
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        Thread[] threadArr;
        try {
            close();
        } catch (IOException e) {
            LOG.warn(e);
        }
        super.doStop();
        synchronized (this) {
            threadArr = this._acceptorThreads;
            this._acceptorThreads = null;
        }
        if (threadArr != null) {
            for (Thread thread : threadArr) {
                if (thread != null) {
                    thread.interrupt();
                }
            }
        }
    }

    public void join() throws InterruptedException {
        Thread[] threadArr;
        synchronized (this) {
            threadArr = this._acceptorThreads;
        }
        if (threadArr != null) {
            for (Thread thread : threadArr) {
                if (thread != null) {
                    thread.join();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void configure(Socket socket) throws IOException {
        try {
            socket.setTcpNoDelay(true);
            if (this._soLingerTime >= 0) {
                socket.setSoLinger(true, this._soLingerTime / 1000);
            } else {
                socket.setSoLinger(false, 0);
            }
        } catch (Exception e) {
            LOG.ignore(e);
        }
    }

    public void customize(EndPoint endPoint, Request request) throws IOException {
        if (isForwarded()) {
            checkForwardedHeaders(endPoint, request);
        }
    }

    /* access modifiers changed from: protected */
    public void checkForwardedHeaders(EndPoint endPoint, Request request) throws IOException {
        String stringField;
        String stringField2;
        HttpFields requestFields = request.getConnection().getRequestFields();
        if (!(getForwardedCipherSuiteHeader() == null || (stringField2 = requestFields.getStringField(getForwardedCipherSuiteHeader())) == null)) {
            request.setAttribute("javax.servlet.request.cipher_suite", stringField2);
        }
        if (!(getForwardedSslSessionIdHeader() == null || (stringField = requestFields.getStringField(getForwardedSslSessionIdHeader())) == null)) {
            request.setAttribute("javax.servlet.request.ssl_session_id", stringField);
            request.setScheme("https");
        }
        String leftMostFieldValue = getLeftMostFieldValue(requestFields, getForwardedHostHeader());
        String leftMostFieldValue2 = getLeftMostFieldValue(requestFields, getForwardedServerHeader());
        String leftMostFieldValue3 = getLeftMostFieldValue(requestFields, getForwardedForHeader());
        String leftMostFieldValue4 = getLeftMostFieldValue(requestFields, getForwardedProtoHeader());
        InetAddress inetAddress = null;
        if (this._hostHeader != null) {
            requestFields.put(HttpHeaders.HOST_BUFFER, this._hostHeader);
            request.setServerName((String) null);
            request.setServerPort(-1);
            request.getServerName();
        } else if (leftMostFieldValue != null) {
            requestFields.put(HttpHeaders.HOST_BUFFER, leftMostFieldValue);
            request.setServerName((String) null);
            request.setServerPort(-1);
            request.getServerName();
        } else if (leftMostFieldValue2 != null) {
            request.setServerName(leftMostFieldValue2);
        }
        if (leftMostFieldValue3 != null) {
            request.setRemoteAddr(leftMostFieldValue3);
            if (this._useDNS) {
                try {
                    inetAddress = InetAddress.getByName(leftMostFieldValue3);
                } catch (UnknownHostException e) {
                    LOG.ignore(e);
                }
            }
            if (inetAddress != null) {
                leftMostFieldValue3 = inetAddress.getHostName();
            }
            request.setRemoteHost(leftMostFieldValue3);
        }
        if (leftMostFieldValue4 != null) {
            request.setScheme(leftMostFieldValue4);
        }
    }

    /* access modifiers changed from: protected */
    public String getLeftMostFieldValue(HttpFields httpFields, String str) {
        String stringField;
        if (str == null || (stringField = httpFields.getStringField(str)) == null) {
            return null;
        }
        int indexOf = stringField.indexOf(44);
        if (indexOf == -1) {
            return stringField;
        }
        return stringField.substring(0, indexOf);
    }

    public int getConfidentialPort() {
        return this._confidentialPort;
    }

    public String getConfidentialScheme() {
        return this._confidentialScheme;
    }

    public int getIntegralPort() {
        return this._integralPort;
    }

    public String getIntegralScheme() {
        return this._integralScheme;
    }

    public boolean isConfidential(Request request) {
        return this._forwarded && request.getScheme().equalsIgnoreCase("https");
    }

    public void setConfidentialPort(int i) {
        this._confidentialPort = i;
    }

    public void setConfidentialScheme(String str) {
        this._confidentialScheme = str;
    }

    public void setIntegralPort(int i) {
        this._integralPort = i;
    }

    public void setIntegralScheme(String str) {
        this._integralScheme = str;
    }

    public boolean getResolveNames() {
        return this._useDNS;
    }

    public void setResolveNames(boolean z) {
        this._useDNS = z;
    }

    public boolean isForwarded() {
        return this._forwarded;
    }

    public void setForwarded(boolean z) {
        if (z) {
            LOG.debug("{} is forwarded", this);
        }
        this._forwarded = z;
    }

    public String getHostHeader() {
        return this._hostHeader;
    }

    public void setHostHeader(String str) {
        this._hostHeader = str;
    }

    public String getForwardedHostHeader() {
        return this._forwardedHostHeader;
    }

    public void setForwardedHostHeader(String str) {
        this._forwardedHostHeader = str;
    }

    public String getForwardedServerHeader() {
        return this._forwardedServerHeader;
    }

    public void setForwardedServerHeader(String str) {
        this._forwardedServerHeader = str;
    }

    public String getForwardedForHeader() {
        return this._forwardedForHeader;
    }

    public void setForwardedForHeader(String str) {
        this._forwardedForHeader = str;
    }

    public String getForwardedProtoHeader() {
        return this._forwardedProtoHeader;
    }

    public void setForwardedProtoHeader(String str) {
        this._forwardedProtoHeader = str;
    }

    public String getForwardedCipherSuiteHeader() {
        return this._forwardedCipherSuiteHeader;
    }

    public void setForwardedCipherSuiteHeader(String str) {
        this._forwardedCipherSuiteHeader = str;
    }

    public String getForwardedSslSessionIdHeader() {
        return this._forwardedSslSessionIdHeader;
    }

    public void setForwardedSslSessionIdHeader(String str) {
        this._forwardedSslSessionIdHeader = str;
    }

    public int getRequestBufferSize() {
        return this._buffers.getRequestBufferSize();
    }

    public void setRequestBufferSize(int i) {
        this._buffers.setRequestBufferSize(i);
    }

    public int getRequestHeaderSize() {
        return this._buffers.getRequestHeaderSize();
    }

    public void setRequestHeaderSize(int i) {
        this._buffers.setRequestHeaderSize(i);
    }

    public int getResponseBufferSize() {
        return this._buffers.getResponseBufferSize();
    }

    public void setResponseBufferSize(int i) {
        this._buffers.setResponseBufferSize(i);
    }

    public int getResponseHeaderSize() {
        return this._buffers.getResponseHeaderSize();
    }

    public void setResponseHeaderSize(int i) {
        this._buffers.setResponseHeaderSize(i);
    }

    public Buffers.Type getRequestBufferType() {
        return this._buffers.getRequestBufferType();
    }

    public Buffers.Type getRequestHeaderType() {
        return this._buffers.getRequestHeaderType();
    }

    public Buffers.Type getResponseBufferType() {
        return this._buffers.getResponseBufferType();
    }

    public Buffers.Type getResponseHeaderType() {
        return this._buffers.getResponseHeaderType();
    }

    public void setRequestBuffers(Buffers buffers) {
        this._buffers.setRequestBuffers(buffers);
    }

    public void setResponseBuffers(Buffers buffers) {
        this._buffers.setResponseBuffers(buffers);
    }

    public Buffers getRequestBuffers() {
        return this._buffers.getRequestBuffers();
    }

    public Buffers getResponseBuffers() {
        return this._buffers.getResponseBuffers();
    }

    public void setMaxBuffers(int i) {
        this._buffers.setMaxBuffers(i);
    }

    public int getMaxBuffers() {
        return this._buffers.getMaxBuffers();
    }

    public String toString() {
        Object[] objArr = new Object[3];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = getHost() == null ? StringUtil.ALL_INTERFACES : getHost();
        objArr[2] = Integer.valueOf(getLocalPort() <= 0 ? getPort() : getLocalPort());
        return String.format("%s@%s:%d", objArr);
    }

    private class Acceptor implements Runnable {
        int _acceptor = 0;

        Acceptor(int i) {
            this._acceptor = i;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
            r0.setPriority(r1 - org.eclipse.jetty.server.AbstractConnector.access$100(r6.this$0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0063, code lost:
            if (r6.this$0.isRunning() == false) goto L_0x0099;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x006b, code lost:
            if (r6.this$0.getConnection() == null) goto L_0x0099;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
            r6.this$0.accept(r6._acceptor);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0075, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            org.eclipse.jetty.server.AbstractConnector.access$200().warn(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x007e, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x007f, code lost:
            org.eclipse.jetty.server.AbstractConnector.access$200().ignore(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0087, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0088, code lost:
            org.eclipse.jetty.server.AbstractConnector.access$200().ignore(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0090, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0091, code lost:
            org.eclipse.jetty.server.AbstractConnector.access$200().ignore(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0099, code lost:
            r0.setPriority(r1);
            r0.setName(r2);
            r4 = r6.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a1, code lost:
            monitor-enter(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a8, code lost:
            if (org.eclipse.jetty.server.AbstractConnector.access$000(r6.this$0) == null) goto L_0x00b4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00aa, code lost:
            org.eclipse.jetty.server.AbstractConnector.access$000(r6.this$0)[r6._acceptor] = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b4, code lost:
            monitor-exit(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b5, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b9, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ba, code lost:
            r0.setPriority(r1);
            r0.setName(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c2, code lost:
            monitor-enter(r6.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c9, code lost:
            if (org.eclipse.jetty.server.AbstractConnector.access$000(r6.this$0) != null) goto L_0x00cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00cb, code lost:
            org.eclipse.jetty.server.AbstractConnector.access$000(r6.this$0)[r6._acceptor] = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d6, code lost:
            throw r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x004d, code lost:
            r1 = r0.getPriority();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r6 = this;
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                org.eclipse.jetty.server.AbstractConnector r1 = org.eclipse.jetty.server.AbstractConnector.this
                monitor-enter(r1)
                org.eclipse.jetty.server.AbstractConnector r2 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00da }
                java.lang.Thread[] r2 = r2._acceptorThreads     // Catch:{ all -> 0x00da }
                if (r2 != 0) goto L_0x0011
                monitor-exit(r1)     // Catch:{ all -> 0x00da }
                return
            L_0x0011:
                org.eclipse.jetty.server.AbstractConnector r2 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00da }
                java.lang.Thread[] r2 = r2._acceptorThreads     // Catch:{ all -> 0x00da }
                int r3 = r6._acceptor     // Catch:{ all -> 0x00da }
                r2[r3] = r0     // Catch:{ all -> 0x00da }
                org.eclipse.jetty.server.AbstractConnector r2 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00da }
                java.lang.Thread[] r2 = r2._acceptorThreads     // Catch:{ all -> 0x00da }
                int r3 = r6._acceptor     // Catch:{ all -> 0x00da }
                r2 = r2[r3]     // Catch:{ all -> 0x00da }
                java.lang.String r2 = r2.getName()     // Catch:{ all -> 0x00da }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00da }
                r3.<init>()     // Catch:{ all -> 0x00da }
                r3.append(r2)     // Catch:{ all -> 0x00da }
                java.lang.String r4 = " Acceptor"
                r3.append(r4)     // Catch:{ all -> 0x00da }
                int r4 = r6._acceptor     // Catch:{ all -> 0x00da }
                r3.append(r4)     // Catch:{ all -> 0x00da }
                java.lang.String r4 = " "
                r3.append(r4)     // Catch:{ all -> 0x00da }
                org.eclipse.jetty.server.AbstractConnector r4 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00da }
                r3.append(r4)     // Catch:{ all -> 0x00da }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00da }
                r0.setName(r3)     // Catch:{ all -> 0x00da }
                monitor-exit(r1)     // Catch:{ all -> 0x00da }
                int r1 = r0.getPriority()
                r3 = 0
                org.eclipse.jetty.server.AbstractConnector r4 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00b9 }
                int r4 = r4._acceptorPriorityOffset     // Catch:{ all -> 0x00b9 }
                int r4 = r1 - r4
                r0.setPriority(r4)     // Catch:{ all -> 0x00b9 }
            L_0x005d:
                org.eclipse.jetty.server.AbstractConnector r4 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00b9 }
                boolean r4 = r4.isRunning()     // Catch:{ all -> 0x00b9 }
                if (r4 == 0) goto L_0x0099
                org.eclipse.jetty.server.AbstractConnector r4 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00b9 }
                java.lang.Object r4 = r4.getConnection()     // Catch:{ all -> 0x00b9 }
                if (r4 == 0) goto L_0x0099
                org.eclipse.jetty.server.AbstractConnector r4 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ EofException -> 0x0090, IOException -> 0x0087, InterruptedException -> 0x007e, Throwable -> 0x0075 }
                int r5 = r6._acceptor     // Catch:{ EofException -> 0x0090, IOException -> 0x0087, InterruptedException -> 0x007e, Throwable -> 0x0075 }
                r4.accept(r5)     // Catch:{ EofException -> 0x0090, IOException -> 0x0087, InterruptedException -> 0x007e, Throwable -> 0x0075 }
                goto L_0x005d
            L_0x0075:
                r4 = move-exception
                org.eclipse.jetty.util.log.Logger r5 = org.eclipse.jetty.server.AbstractConnector.LOG     // Catch:{ all -> 0x00b9 }
                r5.warn(r4)     // Catch:{ all -> 0x00b9 }
                goto L_0x005d
            L_0x007e:
                r4 = move-exception
                org.eclipse.jetty.util.log.Logger r5 = org.eclipse.jetty.server.AbstractConnector.LOG     // Catch:{ all -> 0x00b9 }
                r5.ignore(r4)     // Catch:{ all -> 0x00b9 }
                goto L_0x005d
            L_0x0087:
                r4 = move-exception
                org.eclipse.jetty.util.log.Logger r5 = org.eclipse.jetty.server.AbstractConnector.LOG     // Catch:{ all -> 0x00b9 }
                r5.ignore(r4)     // Catch:{ all -> 0x00b9 }
                goto L_0x005d
            L_0x0090:
                r4 = move-exception
                org.eclipse.jetty.util.log.Logger r5 = org.eclipse.jetty.server.AbstractConnector.LOG     // Catch:{ all -> 0x00b9 }
                r5.ignore(r4)     // Catch:{ all -> 0x00b9 }
                goto L_0x005d
            L_0x0099:
                r0.setPriority(r1)
                r0.setName(r2)
                org.eclipse.jetty.server.AbstractConnector r4 = org.eclipse.jetty.server.AbstractConnector.this
                monitor-enter(r4)
                org.eclipse.jetty.server.AbstractConnector r0 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00b6 }
                java.lang.Thread[] r0 = r0._acceptorThreads     // Catch:{ all -> 0x00b6 }
                if (r0 == 0) goto L_0x00b4
                org.eclipse.jetty.server.AbstractConnector r0 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00b6 }
                java.lang.Thread[] r0 = r0._acceptorThreads     // Catch:{ all -> 0x00b6 }
                int r1 = r6._acceptor     // Catch:{ all -> 0x00b6 }
                r0[r1] = r3     // Catch:{ all -> 0x00b6 }
            L_0x00b4:
                monitor-exit(r4)     // Catch:{ all -> 0x00b6 }
                return
            L_0x00b6:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x00b6 }
                throw r0
            L_0x00b9:
                r4 = move-exception
                r0.setPriority(r1)
                r0.setName(r2)
                org.eclipse.jetty.server.AbstractConnector r0 = org.eclipse.jetty.server.AbstractConnector.this
                monitor-enter(r0)
                org.eclipse.jetty.server.AbstractConnector r1 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00d7 }
                java.lang.Thread[] r1 = r1._acceptorThreads     // Catch:{ all -> 0x00d7 }
                if (r1 == 0) goto L_0x00d5
                org.eclipse.jetty.server.AbstractConnector r1 = org.eclipse.jetty.server.AbstractConnector.this     // Catch:{ all -> 0x00d7 }
                java.lang.Thread[] r1 = r1._acceptorThreads     // Catch:{ all -> 0x00d7 }
                int r2 = r6._acceptor     // Catch:{ all -> 0x00d7 }
                r1[r2] = r3     // Catch:{ all -> 0x00d7 }
            L_0x00d5:
                monitor-exit(r0)     // Catch:{ all -> 0x00d7 }
                throw r4
            L_0x00d7:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00d7 }
                throw r1
            L_0x00da:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x00da }
                goto L_0x00de
            L_0x00dd:
                throw r0
            L_0x00de:
                goto L_0x00dd
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractConnector.Acceptor.run():void");
        }
    }

    public String getName() {
        if (this._name == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getHost() == null ? StringUtil.ALL_INTERFACES : getHost());
            sb.append(":");
            sb.append(getLocalPort() <= 0 ? getPort() : getLocalPort());
            this._name = sb.toString();
        }
        return this._name;
    }

    public void setName(String str) {
        this._name = str;
    }

    public int getRequests() {
        return (int) this._requestStats.getTotal();
    }

    public long getConnectionsDurationTotal() {
        return this._connectionDurationStats.getTotal();
    }

    public int getConnections() {
        return (int) this._connectionStats.getTotal();
    }

    public int getConnectionsOpen() {
        return (int) this._connectionStats.getCurrent();
    }

    public int getConnectionsOpenMax() {
        return (int) this._connectionStats.getMax();
    }

    public double getConnectionsDurationMean() {
        return this._connectionDurationStats.getMean();
    }

    public long getConnectionsDurationMax() {
        return this._connectionDurationStats.getMax();
    }

    public double getConnectionsDurationStdDev() {
        return this._connectionDurationStats.getStdDev();
    }

    public double getConnectionsRequestsMean() {
        return this._requestStats.getMean();
    }

    public int getConnectionsRequestsMax() {
        return (int) this._requestStats.getMax();
    }

    public double getConnectionsRequestsStdDev() {
        return this._requestStats.getStdDev();
    }

    public void statsReset() {
        updateNotEqual(this._statsStartedAt, -1, System.currentTimeMillis());
        this._requestStats.reset();
        this._connectionStats.reset();
        this._connectionDurationStats.reset();
    }

    public void setStatsOn(boolean z) {
        long j = -1;
        if (!z || this._statsStartedAt.get() == -1) {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Statistics on = " + z + " for " + this, new Object[0]);
            }
            statsReset();
            AtomicLong atomicLong = this._statsStartedAt;
            if (z) {
                j = System.currentTimeMillis();
            }
            atomicLong.set(j);
        }
    }

    public boolean getStatsOn() {
        return this._statsStartedAt.get() != -1;
    }

    public long getStatsOnMs() {
        long j = this._statsStartedAt.get();
        if (j != -1) {
            return System.currentTimeMillis() - j;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void connectionOpened(Connection connection) {
        if (this._statsStartedAt.get() != -1) {
            this._connectionStats.increment();
        }
    }

    /* access modifiers changed from: protected */
    public void connectionUpgraded(Connection connection, Connection connection2) {
        this._requestStats.set(connection instanceof AbstractHttpConnection ? (long) ((AbstractHttpConnection) connection).getRequests() : 0);
    }

    /* access modifiers changed from: protected */
    public void connectionClosed(Connection connection) {
        connection.onClose();
        if (this._statsStartedAt.get() != -1) {
            long currentTimeMillis = System.currentTimeMillis() - connection.getTimeStamp();
            this._requestStats.set((long) (connection instanceof AbstractHttpConnection ? ((AbstractHttpConnection) connection).getRequests() : 0));
            this._connectionStats.decrement();
            this._connectionDurationStats.set(currentTimeMillis);
        }
    }

    public int getAcceptorPriorityOffset() {
        return this._acceptorPriorityOffset;
    }

    public void setAcceptorPriorityOffset(int i) {
        this._acceptorPriorityOffset = i;
    }

    public boolean getReuseAddress() {
        return this._reuseAddress;
    }

    public void setReuseAddress(boolean z) {
        this._reuseAddress = z;
    }

    public boolean isLowResources() {
        ThreadPool threadPool = this._threadPool;
        if (threadPool != null) {
            return threadPool.isLowOnThreads();
        }
        return this._server.getThreadPool().isLowOnThreads();
    }

    private void updateNotEqual(AtomicLong atomicLong, long j, long j2) {
        long j3 = atomicLong.get();
        while (j != j3 && !atomicLong.compareAndSet(j3, j2)) {
            j3 = atomicLong.get();
        }
    }
}
