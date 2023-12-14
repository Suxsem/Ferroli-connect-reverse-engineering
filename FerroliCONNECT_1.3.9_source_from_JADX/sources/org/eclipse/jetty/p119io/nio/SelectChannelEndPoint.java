package org.eclipse.jetty.p119io.nio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Locale;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ConnectedEndPoint;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EofException;
import org.eclipse.jetty.p119io.nio.SelectorManager;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* renamed from: org.eclipse.jetty.io.nio.SelectChannelEndPoint */
public class SelectChannelEndPoint extends ChannelEndPoint implements AsyncEndPoint, ConnectedEndPoint {
    public static final Logger LOG = Log.getLogger("org.eclipse.jetty.io.nio");
    private static final int STATE_ASYNC = 2;
    private static final int STATE_DISPATCHED = 1;
    private static final int STATE_NEEDS_DISPATCH = -1;
    private static final int STATE_UNDISPATCHED = 0;
    private final boolean WORK_AROUND_JVM_BUG_6346658 = System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("win");
    private volatile boolean _checkIdle;
    private volatile AsyncConnection _connection;
    private final Runnable _handler = new Runnable() {
        public void run() {
            SelectChannelEndPoint.this.handle();
        }
    };
    private volatile long _idleTimestamp;
    private int _interestOps;
    private boolean _interruptable;
    private boolean _ishut;
    private SelectionKey _key;
    private final SelectorManager _manager;
    private boolean _onIdle;
    private boolean _open;
    private boolean _readBlocked;
    private final SelectorManager.SelectSet _selectSet;
    private int _state;
    private volatile boolean _writable = true;
    private boolean _writeBlocked;

    public boolean hasProgressed() {
        return false;
    }

    public SelectChannelEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey, int i) throws IOException {
        super(socketChannel, i);
        this._manager = selectSet.getManager();
        this._selectSet = selectSet;
        this._state = 0;
        this._onIdle = false;
        this._open = true;
        this._key = selectionKey;
        setCheckForIdle(true);
    }

    public SelectionKey getSelectionKey() {
        SelectionKey selectionKey;
        synchronized (this) {
            selectionKey = this._key;
        }
        return selectionKey;
    }

    public SelectorManager getSelectManager() {
        return this._manager;
    }

    public Connection getConnection() {
        return this._connection;
    }

    public void setConnection(Connection connection) {
        AsyncConnection asyncConnection = this._connection;
        this._connection = (AsyncConnection) connection;
        if (asyncConnection != null && asyncConnection != this._connection) {
            this._manager.endPointUpgraded(this, asyncConnection);
        }
    }

    public long getIdleTimestamp() {
        return this._idleTimestamp;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0063, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0090, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void schedule() {
        /*
            r4 = this;
            monitor-enter(r4)
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            r1 = 0
            if (r0 == 0) goto L_0x0091
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            boolean r0 = r0.isValid()     // Catch:{ all -> 0x009a }
            if (r0 != 0) goto L_0x0010
            goto L_0x0091
        L_0x0010:
            boolean r0 = r4._readBlocked     // Catch:{ all -> 0x009a }
            r2 = 1
            if (r0 != 0) goto L_0x0064
            boolean r0 = r4._writeBlocked     // Catch:{ all -> 0x009a }
            if (r0 == 0) goto L_0x001a
            goto L_0x0064
        L_0x001a:
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            int r0 = r0.readyOps()     // Catch:{ all -> 0x009a }
            r3 = 4
            r0 = r0 & r3
            if (r0 != r3) goto L_0x0040
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            int r0 = r0.interestOps()     // Catch:{ all -> 0x009a }
            r0 = r0 & r3
            if (r0 != r3) goto L_0x0040
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            int r0 = r0.interestOps()     // Catch:{ all -> 0x009a }
            r0 = r0 & -5
            r4._interestOps = r0     // Catch:{ all -> 0x009a }
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            int r3 = r4._interestOps     // Catch:{ all -> 0x009a }
            r0.interestOps(r3)     // Catch:{ all -> 0x009a }
            r4._writable = r2     // Catch:{ all -> 0x009a }
        L_0x0040:
            int r0 = r4._state     // Catch:{ all -> 0x009a }
            if (r0 < r2) goto L_0x004a
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            r0.interestOps(r1)     // Catch:{ all -> 0x009a }
            goto L_0x0062
        L_0x004a:
            r4.dispatch()     // Catch:{ all -> 0x009a }
            int r0 = r4._state     // Catch:{ all -> 0x009a }
            if (r0 < r2) goto L_0x0062
            org.eclipse.jetty.io.nio.SelectorManager$SelectSet r0 = r4._selectSet     // Catch:{ all -> 0x009a }
            org.eclipse.jetty.io.nio.SelectorManager r0 = r0.getManager()     // Catch:{ all -> 0x009a }
            boolean r0 = r0.isDeferringInterestedOps0()     // Catch:{ all -> 0x009a }
            if (r0 != 0) goto L_0x0062
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            r0.interestOps(r1)     // Catch:{ all -> 0x009a }
        L_0x0062:
            monitor-exit(r4)     // Catch:{ all -> 0x009a }
            return
        L_0x0064:
            boolean r0 = r4._readBlocked     // Catch:{ all -> 0x009a }
            if (r0 == 0) goto L_0x0072
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            boolean r0 = r0.isReadable()     // Catch:{ all -> 0x009a }
            if (r0 == 0) goto L_0x0072
            r4._readBlocked = r1     // Catch:{ all -> 0x009a }
        L_0x0072:
            boolean r0 = r4._writeBlocked     // Catch:{ all -> 0x009a }
            if (r0 == 0) goto L_0x0080
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            boolean r0 = r0.isWritable()     // Catch:{ all -> 0x009a }
            if (r0 == 0) goto L_0x0080
            r4._writeBlocked = r1     // Catch:{ all -> 0x009a }
        L_0x0080:
            r4.notifyAll()     // Catch:{ all -> 0x009a }
            java.nio.channels.SelectionKey r0 = r4._key     // Catch:{ all -> 0x009a }
            r0.interestOps(r1)     // Catch:{ all -> 0x009a }
            int r0 = r4._state     // Catch:{ all -> 0x009a }
            if (r0 >= r2) goto L_0x008f
            r4.updateKey()     // Catch:{ all -> 0x009a }
        L_0x008f:
            monitor-exit(r4)     // Catch:{ all -> 0x009a }
            return
        L_0x0091:
            r4._readBlocked = r1     // Catch:{ all -> 0x009a }
            r4._writeBlocked = r1     // Catch:{ all -> 0x009a }
            r4.notifyAll()     // Catch:{ all -> 0x009a }
            monitor-exit(r4)     // Catch:{ all -> 0x009a }
            return
        L_0x009a:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x009a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SelectChannelEndPoint.schedule():void");
    }

    public void asyncDispatch() {
        synchronized (this) {
            int i = this._state;
            if (i == -1 || i == 0) {
                dispatch();
            } else if (i == 1 || i == 2) {
                this._state = 2;
            }
        }
    }

    public void dispatch() {
        synchronized (this) {
            if (this._state <= 0) {
                if (this._onIdle) {
                    this._state = -1;
                } else {
                    this._state = 1;
                    if (!this._manager.dispatch(this._handler)) {
                        this._state = -1;
                        Logger logger = LOG;
                        logger.warn("Dispatched Failed! " + this + " to " + this._manager, new Object[0]);
                        updateKey();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean undispatch() {
        synchronized (this) {
            if (this._state != 2) {
                this._state = 0;
                updateKey();
                return true;
            }
            this._state = 1;
            return false;
        }
    }

    public void cancelTimeout(Timeout.Task task) {
        getSelectSet().cancelTimeout(task);
    }

    public void scheduleTimeout(Timeout.Task task, long j) {
        getSelectSet().scheduleTimeout(task, j);
    }

    public void setCheckForIdle(boolean z) {
        if (z) {
            this._idleTimestamp = System.currentTimeMillis();
            this._checkIdle = true;
            return;
        }
        this._checkIdle = false;
    }

    public boolean isCheckForIdle() {
        return this._checkIdle;
    }

    /* access modifiers changed from: protected */
    public void notIdle() {
        this._idleTimestamp = System.currentTimeMillis();
    }

    public void checkIdleTimestamp(long j) {
        if (isCheckForIdle() && this._maxIdleTime > 0) {
            final long j2 = j - this._idleTimestamp;
            if (j2 > ((long) this._maxIdleTime)) {
                setCheckForIdle(false);
                this._manager.dispatch(new Runnable() {
                    public void run() {
                        try {
                            SelectChannelEndPoint.this.onIdleExpired(j2);
                        } finally {
                            SelectChannelEndPoint.this.setCheckForIdle(true);
                        }
                    }
                });
            }
        }
    }

    public void onIdleExpired(long j) {
        try {
            synchronized (this) {
                this._onIdle = true;
            }
            this._connection.onIdleExpired(j);
            synchronized (this) {
                this._onIdle = false;
                if (this._state == -1) {
                    dispatch();
                }
            }
        } catch (Throwable th) {
            synchronized (this) {
                this._onIdle = false;
                if (this._state == -1) {
                    dispatch();
                }
                throw th;
            }
        }
    }

    public int fill(Buffer buffer) throws IOException {
        int fill = super.fill(buffer);
        if (fill > 0) {
            notIdle();
        }
        return fill;
    }

    public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        int flush = super.flush(buffer, buffer2, buffer3);
        if (flush == 0 && ((buffer != null && buffer.hasContent()) || ((buffer2 != null && buffer2.hasContent()) || (buffer3 != null && buffer3.hasContent())))) {
            synchronized (this) {
                this._writable = false;
                if (this._state < 1) {
                    updateKey();
                }
            }
        } else if (flush > 0) {
            this._writable = true;
            notIdle();
        }
        return flush;
    }

    public int flush(Buffer buffer) throws IOException {
        int flush = super.flush(buffer);
        if (flush == 0 && buffer != null && buffer.hasContent()) {
            synchronized (this) {
                this._writable = false;
                if (this._state < 1) {
                    updateKey();
                }
            }
        } else if (flush > 0) {
            this._writable = true;
            notIdle();
        }
        return flush;
    }

    public boolean blockReadable(long j) throws IOException {
        SelectorManager.SelectSet selectSet;
        synchronized (this) {
            if (!isInputShutdown()) {
                long now = this._selectSet.getNow();
                long j2 = now + j;
                boolean isCheckForIdle = isCheckForIdle();
                setCheckForIdle(true);
                try {
                    this._readBlocked = true;
                    while (!isInputShutdown() && this._readBlocked) {
                        updateKey();
                        wait(j > 0 ? j2 - now : 10000);
                        selectSet = this._selectSet;
                        now = selectSet.getNow();
                        if (this._readBlocked && j > 0 && now >= j2) {
                            this._readBlocked = false;
                            setCheckForIdle(isCheckForIdle);
                            return false;
                        }
                    }
                    this._readBlocked = false;
                    setCheckForIdle(isCheckForIdle);
                    return true;
                } catch (InterruptedException e) {
                    LOG.warn(e);
                    if (!this._interruptable) {
                        selectSet = this._selectSet;
                    } else {
                        throw new InterruptedIOException() {
                            {
                                initCause(e);
                            }
                        };
                    }
                } catch (Throwable th) {
                    this._readBlocked = false;
                    setCheckForIdle(isCheckForIdle);
                    throw th;
                }
            } else {
                throw new EofException();
            }
        }
    }

    public boolean blockWritable(long j) throws IOException {
        SelectorManager.SelectSet selectSet;
        synchronized (this) {
            if (!isOutputShutdown()) {
                long now = this._selectSet.getNow();
                long j2 = now + j;
                boolean isCheckForIdle = isCheckForIdle();
                setCheckForIdle(true);
                try {
                    this._writeBlocked = true;
                    while (this._writeBlocked && !isOutputShutdown()) {
                        updateKey();
                        wait(j > 0 ? j2 - now : 10000);
                        selectSet = this._selectSet;
                        now = selectSet.getNow();
                        if (this._writeBlocked && j > 0 && now >= j2) {
                            this._writeBlocked = false;
                            setCheckForIdle(isCheckForIdle);
                            return false;
                        }
                    }
                    this._writeBlocked = false;
                    setCheckForIdle(isCheckForIdle);
                    return true;
                } catch (InterruptedException e) {
                    LOG.warn(e);
                    if (!this._interruptable) {
                        selectSet = this._selectSet;
                    } else {
                        throw new InterruptedIOException() {
                            {
                                initCause(e);
                            }
                        };
                    }
                } catch (Throwable th) {
                    this._writeBlocked = false;
                    setCheckForIdle(isCheckForIdle);
                    throw th;
                }
            } else {
                throw new EofException();
            }
        }
    }

    public void setInterruptable(boolean z) {
        synchronized (this) {
            this._interruptable = z;
        }
    }

    public boolean isInterruptable() {
        return this._interruptable;
    }

    public void scheduleWrite() {
        if (this._writable) {
            LOG.debug("Required scheduleWrite {}", this);
        }
        this._writable = false;
        updateKey();
    }

    public boolean isWritable() {
        return this._writable;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0026 A[Catch:{ Exception -> 0x0063 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003a A[ADDED_TO_REGION, Catch:{ Exception -> 0x0063 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0047 A[ADDED_TO_REGION, Catch:{ Exception -> 0x0063 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateKey() {
        /*
            r6 = this;
            monitor-enter(r6)
            java.nio.channels.ByteChannel r0 = r6.getChannel()     // Catch:{ all -> 0x007f }
            boolean r0 = r0.isOpen()     // Catch:{ all -> 0x007f }
            r1 = -1
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x006c
            boolean r0 = r6._readBlocked     // Catch:{ all -> 0x007f }
            if (r0 != 0) goto L_0x0021
            int r0 = r6._state     // Catch:{ all -> 0x007f }
            if (r0 >= r3) goto L_0x001f
            org.eclipse.jetty.io.nio.AsyncConnection r0 = r6._connection     // Catch:{ all -> 0x007f }
            boolean r0 = r0.isSuspended()     // Catch:{ all -> 0x007f }
            if (r0 != 0) goto L_0x001f
            goto L_0x0021
        L_0x001f:
            r0 = 0
            goto L_0x0022
        L_0x0021:
            r0 = 1
        L_0x0022:
            boolean r4 = r6._writeBlocked     // Catch:{ all -> 0x007f }
            if (r4 != 0) goto L_0x0031
            int r4 = r6._state     // Catch:{ all -> 0x007f }
            if (r4 >= r3) goto L_0x002f
            boolean r4 = r6._writable     // Catch:{ all -> 0x007f }
            if (r4 != 0) goto L_0x002f
            goto L_0x0031
        L_0x002f:
            r4 = 0
            goto L_0x0032
        L_0x0031:
            r4 = 1
        L_0x0032:
            java.net.Socket r5 = r6._socket     // Catch:{ all -> 0x007f }
            boolean r5 = r5.isInputShutdown()     // Catch:{ all -> 0x007f }
            if (r5 != 0) goto L_0x003e
            if (r0 == 0) goto L_0x003e
            r0 = 1
            goto L_0x003f
        L_0x003e:
            r0 = 0
        L_0x003f:
            java.net.Socket r5 = r6._socket     // Catch:{ all -> 0x007f }
            boolean r5 = r5.isOutputShutdown()     // Catch:{ all -> 0x007f }
            if (r5 != 0) goto L_0x004b
            if (r4 == 0) goto L_0x004b
            r4 = 4
            goto L_0x004c
        L_0x004b:
            r4 = 0
        L_0x004c:
            r0 = r0 | r4
            r6._interestOps = r0     // Catch:{ all -> 0x007f }
            java.nio.channels.SelectionKey r0 = r6._key     // Catch:{ Exception -> 0x0063 }
            if (r0 == 0) goto L_0x006c
            java.nio.channels.SelectionKey r0 = r6._key     // Catch:{ Exception -> 0x0063 }
            boolean r0 = r0.isValid()     // Catch:{ Exception -> 0x0063 }
            if (r0 == 0) goto L_0x006c
            java.nio.channels.SelectionKey r0 = r6._key     // Catch:{ Exception -> 0x0063 }
            int r0 = r0.interestOps()     // Catch:{ Exception -> 0x0063 }
            r1 = r0
            goto L_0x006c
        L_0x0063:
            r0 = move-exception
            r4 = 0
            r6._key = r4     // Catch:{ all -> 0x007f }
            org.eclipse.jetty.util.log.Logger r4 = LOG     // Catch:{ all -> 0x007f }
            r4.ignore(r0)     // Catch:{ all -> 0x007f }
        L_0x006c:
            int r0 = r6._interestOps     // Catch:{ all -> 0x007f }
            if (r0 == r1) goto L_0x0071
            r2 = 1
        L_0x0071:
            monitor-exit(r6)     // Catch:{ all -> 0x007f }
            if (r2 == 0) goto L_0x007e
            org.eclipse.jetty.io.nio.SelectorManager$SelectSet r0 = r6._selectSet
            r0.addChange(r6)
            org.eclipse.jetty.io.nio.SelectorManager$SelectSet r0 = r6._selectSet
            r0.wakeup()
        L_0x007e:
            return
        L_0x007f:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x007f }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SelectChannelEndPoint.updateKey():void");
    }

    /* access modifiers changed from: package-private */
    public void doUpdateKey() {
        synchronized (this) {
            if (!getChannel().isOpen()) {
                if (this._key != null && this._key.isValid()) {
                    this._key.cancel();
                }
                if (this._open) {
                    this._open = false;
                    this._selectSet.destroyEndPoint(this);
                }
                this._key = null;
            } else if (this._interestOps > 0) {
                if (this._key != null) {
                    if (this._key.isValid()) {
                        this._key.interestOps(this._interestOps);
                    }
                }
                if (((SelectableChannel) getChannel()).isRegistered()) {
                    updateKey();
                } else {
                    try {
                        this._key = ((SelectableChannel) getChannel()).register(this._selectSet.getSelector(), this._interestOps, this);
                    } catch (Exception e) {
                        LOG.ignore(e);
                        if (this._key != null && this._key.isValid()) {
                            this._key.cancel();
                        }
                        if (this._open) {
                            this._selectSet.destroyEndPoint(this);
                        }
                        this._open = false;
                        this._key = null;
                    }
                }
            } else if (this._key == null || !this._key.isValid()) {
                this._key = null;
            } else {
                this._key.interestOps(0);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x01ae A[Catch:{ all -> 0x0191, all -> 0x015c, all -> 0x0123, all -> 0x00df, all -> 0x0091, all -> 0x0061, Throwable -> 0x0193, all -> 0x004a, all -> 0x01b2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x01b0 A[Catch:{ all -> 0x0191, all -> 0x015c, all -> 0x0123, all -> 0x00df, all -> 0x0091, all -> 0x0061, Throwable -> 0x0193, all -> 0x004a, all -> 0x01b2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handle() {
        /*
            r10 = this;
            java.lang.String r0 = "onInputShutdown failed"
            r1 = 1
            r2 = 1
        L_0x0004:
            java.lang.String r3 = "SCEP.run() finally DISPATCHED"
            r4 = 0
            if (r2 == 0) goto L_0x01c9
        L_0x0009:
            org.eclipse.jetty.io.nio.AsyncConnection r5 = r10._connection     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            org.eclipse.jetty.io.Connection r5 = r5.handle()     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            org.eclipse.jetty.io.nio.AsyncConnection r5 = (org.eclipse.jetty.p119io.nio.AsyncConnection) r5     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            org.eclipse.jetty.io.nio.AsyncConnection r6 = r10._connection     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            if (r5 == r6) goto L_0x002f
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            java.lang.String r7 = "{} replaced {}"
            r8 = 2
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            r8[r4] = r5     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            org.eclipse.jetty.io.nio.AsyncConnection r9 = r10._connection     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            r8[r1] = r9     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            r6.debug((java.lang.String) r7, (java.lang.Object[]) r8)     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            org.eclipse.jetty.io.nio.AsyncConnection r6 = r10._connection     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            r10._connection = r5     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            org.eclipse.jetty.io.nio.SelectorManager r5 = r10._manager     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            r5.endPointUpgraded(r10, r6)     // Catch:{ ClosedChannelException -> 0x013a, EofException -> 0x00f6, IOException -> 0x00ae, Throwable -> 0x0064 }
            goto L_0x0009
        L_0x002f:
            boolean r5 = r10._ishut     // Catch:{ all -> 0x01b2 }
            if (r5 != 0) goto L_0x00a8
            boolean r5 = r10.isInputShutdown()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            boolean r5 = r10.isOpen()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            r10._ishut = r1     // Catch:{ all -> 0x01b2 }
            org.eclipse.jetty.io.nio.AsyncConnection r5 = r10._connection     // Catch:{ Throwable -> 0x004c }
            r5.onInputShutdown()     // Catch:{ Throwable -> 0x004c }
        L_0x0046:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            goto L_0x00a8
        L_0x004a:
            r0 = move-exception
            goto L_0x005d
        L_0x004c:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x004a }
            r6.warn((java.lang.String) r0, (java.lang.Throwable) r5)     // Catch:{ all -> 0x004a }
            r10.close()     // Catch:{ IOException -> 0x0056 }
            goto L_0x0046
        L_0x0056:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x004a }
            r6.ignore(r5)     // Catch:{ all -> 0x004a }
            goto L_0x0046
        L_0x005d:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            throw r0     // Catch:{ all -> 0x01b2 }
        L_0x0061:
            r5 = move-exception
            goto L_0x0176
        L_0x0064:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0061 }
            java.lang.String r7 = "handle failed"
            r6.warn((java.lang.String) r7, (java.lang.Throwable) r5)     // Catch:{ all -> 0x0061 }
            r10.close()     // Catch:{ IOException -> 0x0070 }
            goto L_0x0076
        L_0x0070:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0061 }
            r6.ignore(r5)     // Catch:{ all -> 0x0061 }
        L_0x0076:
            boolean r5 = r10._ishut     // Catch:{ all -> 0x01b2 }
            if (r5 != 0) goto L_0x00a8
            boolean r5 = r10.isInputShutdown()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            boolean r5 = r10.isOpen()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            r10._ishut = r1     // Catch:{ all -> 0x01b2 }
            org.eclipse.jetty.io.nio.AsyncConnection r5 = r10._connection     // Catch:{ Throwable -> 0x0093 }
            r5.onInputShutdown()     // Catch:{ Throwable -> 0x0093 }
        L_0x008d:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            goto L_0x00a8
        L_0x0091:
            r0 = move-exception
            goto L_0x00a4
        L_0x0093:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0091 }
            r6.warn((java.lang.String) r0, (java.lang.Throwable) r5)     // Catch:{ all -> 0x0091 }
            r10.close()     // Catch:{ IOException -> 0x009d }
            goto L_0x008d
        L_0x009d:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0091 }
            r6.ignore(r5)     // Catch:{ all -> 0x0091 }
            goto L_0x008d
        L_0x00a4:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            throw r0     // Catch:{ all -> 0x01b2 }
        L_0x00a8:
            boolean r2 = r10.undispatch()     // Catch:{ all -> 0x01b2 }
            goto L_0x0173
        L_0x00ae:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0061 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0061 }
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0061 }
            r6.warn((java.lang.String) r5, (java.lang.Object[]) r7)     // Catch:{ all -> 0x0061 }
            r10.close()     // Catch:{ IOException -> 0x00be }
            goto L_0x00c4
        L_0x00be:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0061 }
            r6.ignore(r5)     // Catch:{ all -> 0x0061 }
        L_0x00c4:
            boolean r5 = r10._ishut     // Catch:{ all -> 0x01b2 }
            if (r5 != 0) goto L_0x00a8
            boolean r5 = r10.isInputShutdown()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            boolean r5 = r10.isOpen()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            r10._ishut = r1     // Catch:{ all -> 0x01b2 }
            org.eclipse.jetty.io.nio.AsyncConnection r5 = r10._connection     // Catch:{ Throwable -> 0x00e1 }
            r5.onInputShutdown()     // Catch:{ Throwable -> 0x00e1 }
        L_0x00db:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            goto L_0x00a8
        L_0x00df:
            r0 = move-exception
            goto L_0x00f2
        L_0x00e1:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x00df }
            r6.warn((java.lang.String) r0, (java.lang.Throwable) r5)     // Catch:{ all -> 0x00df }
            r10.close()     // Catch:{ IOException -> 0x00eb }
            goto L_0x00db
        L_0x00eb:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x00df }
            r6.ignore(r5)     // Catch:{ all -> 0x00df }
            goto L_0x00db
        L_0x00f2:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            throw r0     // Catch:{ all -> 0x01b2 }
        L_0x00f6:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0061 }
            java.lang.String r7 = "EOF"
            r6.debug((java.lang.String) r7, (java.lang.Throwable) r5)     // Catch:{ all -> 0x0061 }
            r10.close()     // Catch:{ IOException -> 0x0102 }
            goto L_0x0108
        L_0x0102:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0061 }
            r6.ignore(r5)     // Catch:{ all -> 0x0061 }
        L_0x0108:
            boolean r5 = r10._ishut     // Catch:{ all -> 0x01b2 }
            if (r5 != 0) goto L_0x00a8
            boolean r5 = r10.isInputShutdown()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            boolean r5 = r10.isOpen()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            r10._ishut = r1     // Catch:{ all -> 0x01b2 }
            org.eclipse.jetty.io.nio.AsyncConnection r5 = r10._connection     // Catch:{ Throwable -> 0x0125 }
            r5.onInputShutdown()     // Catch:{ Throwable -> 0x0125 }
        L_0x011f:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            goto L_0x00a8
        L_0x0123:
            r0 = move-exception
            goto L_0x0136
        L_0x0125:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0123 }
            r6.warn((java.lang.String) r0, (java.lang.Throwable) r5)     // Catch:{ all -> 0x0123 }
            r10.close()     // Catch:{ IOException -> 0x012f }
            goto L_0x011f
        L_0x012f:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0123 }
            r6.ignore(r5)     // Catch:{ all -> 0x0123 }
            goto L_0x011f
        L_0x0136:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            throw r0     // Catch:{ all -> 0x01b2 }
        L_0x013a:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0061 }
            r6.ignore(r5)     // Catch:{ all -> 0x0061 }
            boolean r5 = r10._ishut     // Catch:{ all -> 0x01b2 }
            if (r5 != 0) goto L_0x00a8
            boolean r5 = r10.isInputShutdown()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            boolean r5 = r10.isOpen()     // Catch:{ all -> 0x01b2 }
            if (r5 == 0) goto L_0x00a8
            r10._ishut = r1     // Catch:{ all -> 0x01b2 }
            org.eclipse.jetty.io.nio.AsyncConnection r5 = r10._connection     // Catch:{ Throwable -> 0x015e }
            r5.onInputShutdown()     // Catch:{ Throwable -> 0x015e }
        L_0x0157:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            goto L_0x00a8
        L_0x015c:
            r0 = move-exception
            goto L_0x016f
        L_0x015e:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x015c }
            r6.warn((java.lang.String) r0, (java.lang.Throwable) r5)     // Catch:{ all -> 0x015c }
            r10.close()     // Catch:{ IOException -> 0x0168 }
            goto L_0x0157
        L_0x0168:
            r5 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x015c }
            r6.ignore(r5)     // Catch:{ all -> 0x015c }
            goto L_0x0157
        L_0x016f:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            throw r0     // Catch:{ all -> 0x01b2 }
        L_0x0173:
            r2 = r2 ^ r1
            goto L_0x0004
        L_0x0176:
            boolean r6 = r10._ishut     // Catch:{ all -> 0x01b2 }
            if (r6 != 0) goto L_0x01a8
            boolean r6 = r10.isInputShutdown()     // Catch:{ all -> 0x01b2 }
            if (r6 == 0) goto L_0x01a8
            boolean r6 = r10.isOpen()     // Catch:{ all -> 0x01b2 }
            if (r6 == 0) goto L_0x01a8
            r10._ishut = r1     // Catch:{ all -> 0x01b2 }
            org.eclipse.jetty.io.nio.AsyncConnection r6 = r10._connection     // Catch:{ Throwable -> 0x0193 }
            r6.onInputShutdown()     // Catch:{ Throwable -> 0x0193 }
        L_0x018d:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            goto L_0x01a8
        L_0x0191:
            r0 = move-exception
            goto L_0x01a4
        L_0x0193:
            r6 = move-exception
            org.eclipse.jetty.util.log.Logger r7 = LOG     // Catch:{ all -> 0x0191 }
            r7.warn((java.lang.String) r0, (java.lang.Throwable) r6)     // Catch:{ all -> 0x0191 }
            r10.close()     // Catch:{ IOException -> 0x019d }
            goto L_0x018d
        L_0x019d:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r6 = LOG     // Catch:{ all -> 0x0191 }
            r6.ignore(r0)     // Catch:{ all -> 0x0191 }
            goto L_0x018d
        L_0x01a4:
            r10.updateKey()     // Catch:{ all -> 0x01b2 }
            throw r0     // Catch:{ all -> 0x01b2 }
        L_0x01a8:
            boolean r0 = r10.undispatch()     // Catch:{ all -> 0x01b2 }
            if (r0 != 0) goto L_0x01b0
            r2 = 1
            goto L_0x01b1
        L_0x01b0:
            r2 = 0
        L_0x01b1:
            throw r5     // Catch:{ all -> 0x01b2 }
        L_0x01b2:
            r0 = move-exception
            if (r2 == 0) goto L_0x01c8
            boolean r2 = r10.undispatch()
        L_0x01b9:
            r2 = r2 ^ r1
            if (r2 == 0) goto L_0x01c8
            org.eclipse.jetty.util.log.Logger r2 = LOG
            java.lang.Object[] r5 = new java.lang.Object[r4]
            r2.warn((java.lang.String) r3, (java.lang.Object[]) r5)
            boolean r2 = r10.undispatch()
            goto L_0x01b9
        L_0x01c8:
            throw r0
        L_0x01c9:
            if (r2 == 0) goto L_0x01de
            boolean r0 = r10.undispatch()
        L_0x01cf:
            r0 = r0 ^ r1
            if (r0 == 0) goto L_0x01de
            org.eclipse.jetty.util.log.Logger r0 = LOG
            java.lang.Object[] r2 = new java.lang.Object[r4]
            r0.warn((java.lang.String) r3, (java.lang.Object[]) r2)
            boolean r0 = r10.undispatch()
            goto L_0x01cf
        L_0x01de:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SelectChannelEndPoint.handle():void");
    }

    public void close() throws IOException {
        if (this.WORK_AROUND_JVM_BUG_6346658) {
            try {
                SelectionKey selectionKey = this._key;
                if (selectionKey != null) {
                    selectionKey.cancel();
                }
            } catch (Throwable th) {
                LOG.ignore(th);
            }
        }
        try {
            super.close();
        } catch (IOException e) {
            LOG.ignore(e);
        } catch (Throwable th2) {
            updateKey();
            throw th2;
        }
        updateKey();
    }

    public String toString() {
        SelectionKey selectionKey = this._key;
        String str = "";
        if (selectionKey == null) {
            str = str + "-";
        } else if (selectionKey.isValid()) {
            if (selectionKey.isReadable()) {
                str = str + "r";
            }
            if (selectionKey.isWritable()) {
                str = str + "w";
            }
        } else {
            str = str + "!";
        }
        return String.format("SCEP@%x{l(%s)<->r(%s),s=%d,open=%b,ishut=%b,oshut=%b,rb=%b,wb=%b,w=%b,i=%d%s}-{%s}", new Object[]{Integer.valueOf(hashCode()), this._socket.getRemoteSocketAddress(), this._socket.getLocalSocketAddress(), Integer.valueOf(this._state), Boolean.valueOf(isOpen()), Boolean.valueOf(isInputShutdown()), Boolean.valueOf(isOutputShutdown()), Boolean.valueOf(this._readBlocked), Boolean.valueOf(this._writeBlocked), Boolean.valueOf(this._writable), Integer.valueOf(this._interestOps), str, this._connection});
    }

    public SelectorManager.SelectSet getSelectSet() {
        return this._selectSet;
    }

    public void setMaxIdleTime(int i) throws IOException {
        this._maxIdleTime = i;
    }
}
