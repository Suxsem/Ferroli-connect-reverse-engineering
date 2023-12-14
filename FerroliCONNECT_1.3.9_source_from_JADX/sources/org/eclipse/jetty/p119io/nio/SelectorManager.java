package org.eclipse.jetty.p119io.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.jetty.p119io.AsyncEndPoint;
import org.eclipse.jetty.p119io.ConnectedEndPoint;
import org.eclipse.jetty.p119io.Connection;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* renamed from: org.eclipse.jetty.io.nio.SelectorManager */
public abstract class SelectorManager extends AbstractLifeCycle implements Dumpable {
    public static final Logger LOG = Log.getLogger("org.eclipse.jetty.io.nio");
    /* access modifiers changed from: private */
    public static final int __BUSY_PAUSE = Integer.getInteger("org.eclipse.jetty.io.nio.BUSY_PAUSE", 50).intValue();
    /* access modifiers changed from: private */
    public static final int __IDLE_TICK = Integer.getInteger("org.eclipse.jetty.io.nio.IDLE_TICK", 400).intValue();
    /* access modifiers changed from: private */
    public static final int __MAX_SELECTS = Integer.getInteger("org.eclipse.jetty.io.nio.MAX_SELECTS", 100000).intValue();
    /* access modifiers changed from: private */
    public static final int __MONITOR_PERIOD = Integer.getInteger("org.eclipse.jetty.io.nio.MONITOR_PERIOD", 1000).intValue();
    private boolean _deferringInterestedOps0 = true;
    /* access modifiers changed from: private */
    public long _lowResourcesConnections;
    /* access modifiers changed from: private */
    public int _lowResourcesMaxIdleTime;
    /* access modifiers changed from: private */
    public int _maxIdleTime;
    /* access modifiers changed from: private */
    public SelectSet[] _selectSet;
    private int _selectSets = 1;
    private int _selectorPriorityDelta = 0;
    private volatile int _set = 0;

    /* renamed from: org.eclipse.jetty.io.nio.SelectorManager$ChangeTask */
    private interface ChangeTask extends Runnable {
    }

    public abstract boolean dispatch(Runnable runnable);

    /* access modifiers changed from: protected */
    public abstract void endPointClosed(SelectChannelEndPoint selectChannelEndPoint);

    /* access modifiers changed from: protected */
    public abstract void endPointOpened(SelectChannelEndPoint selectChannelEndPoint);

    /* access modifiers changed from: protected */
    public abstract void endPointUpgraded(ConnectedEndPoint connectedEndPoint, Connection connection);

    public abstract AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint, Object obj);

    /* access modifiers changed from: protected */
    public abstract SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectSet selectSet, SelectionKey selectionKey) throws IOException;

    public void setMaxIdleTime(long j) {
        this._maxIdleTime = (int) j;
    }

    public void setSelectSets(int i) {
        this._selectSets = i;
        this._lowResourcesConnections = (this._lowResourcesConnections * ((long) this._selectSets)) / ((long) this._selectSets);
    }

    public long getMaxIdleTime() {
        return (long) this._maxIdleTime;
    }

    public int getSelectSets() {
        return this._selectSets;
    }

    public SelectSet getSelectSet(int i) {
        return this._selectSet[i];
    }

    public void register(SocketChannel socketChannel, Object obj) {
        int i = this._set;
        this._set = i + 1;
        if (i < 0) {
            i = -i;
        }
        int i2 = i % this._selectSets;
        SelectSet[] selectSetArr = this._selectSet;
        if (selectSetArr != null) {
            SelectSet selectSet = selectSetArr[i2];
            selectSet.addChange(socketChannel, obj);
            selectSet.wakeup();
        }
    }

    public void register(SocketChannel socketChannel) {
        int i = this._set;
        this._set = i + 1;
        if (i < 0) {
            i = -i;
        }
        int i2 = i % this._selectSets;
        SelectSet[] selectSetArr = this._selectSet;
        if (selectSetArr != null) {
            SelectSet selectSet = selectSetArr[i2];
            selectSet.addChange(socketChannel);
            selectSet.wakeup();
        }
    }

    public void register(ServerSocketChannel serverSocketChannel) {
        int i = this._set;
        this._set = i + 1;
        if (i < 0) {
            i = -i;
        }
        SelectSet selectSet = this._selectSet[i % this._selectSets];
        selectSet.addChange(serverSocketChannel);
        selectSet.wakeup();
    }

    public int getSelectorPriorityDelta() {
        return this._selectorPriorityDelta;
    }

    public void setSelectorPriorityDelta(int i) {
        this._selectorPriorityDelta = i;
    }

    public long getLowResourcesConnections() {
        return this._lowResourcesConnections * ((long) this._selectSets);
    }

    public void setLowResourcesConnections(long j) {
        int i = this._selectSets;
        this._lowResourcesConnections = ((j + ((long) i)) - 1) / ((long) i);
    }

    public long getLowResourcesMaxIdleTime() {
        return (long) this._lowResourcesMaxIdleTime;
    }

    public void setLowResourcesMaxIdleTime(long j) {
        this._lowResourcesMaxIdleTime = (int) j;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        this._selectSet = new SelectSet[this._selectSets];
        final int i = 0;
        int i2 = 0;
        while (true) {
            SelectSet[] selectSetArr = this._selectSet;
            if (i2 >= selectSetArr.length) {
                break;
            }
            selectSetArr[i2] = new SelectSet(i2);
            i2++;
        }
        super.doStart();
        while (i < getSelectSets()) {
            if (dispatch(new Runnable() {
                public void run() {
                    String name = Thread.currentThread().getName();
                    int priority = Thread.currentThread().getPriority();
                    try {
                        SelectSet[] access$000 = SelectorManager.this._selectSet;
                        if (access$000 == null) {
                            SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                            Thread.currentThread().setName(name);
                            if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                                Thread.currentThread().setPriority(priority);
                                return;
                            }
                            return;
                        }
                        SelectSet selectSet = access$000[i];
                        Thread currentThread = Thread.currentThread();
                        currentThread.setName(name + " Selector" + i);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(Thread.currentThread().getPriority() + SelectorManager.this.getSelectorPriorityDelta());
                        }
                        SelectorManager.LOG.debug("Starting {} on {}", Thread.currentThread(), this);
                        while (SelectorManager.this.isRunning()) {
                            selectSet.doSelect();
                        }
                        SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                        Thread.currentThread().setName(name);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(priority);
                        }
                    } catch (IOException e) {
                        SelectorManager.LOG.ignore(e);
                    } catch (Exception e2) {
                        SelectorManager.LOG.warn(e2);
                    } catch (Throwable th) {
                        SelectorManager.LOG.debug("Stopped {} on {}", Thread.currentThread(), this);
                        Thread.currentThread().setName(name);
                        if (SelectorManager.this.getSelectorPriorityDelta() != 0) {
                            Thread.currentThread().setPriority(priority);
                        }
                        throw th;
                    }
                }
            })) {
                i++;
            } else {
                throw new IllegalStateException("!Selecting");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        SelectSet[] selectSetArr = this._selectSet;
        this._selectSet = null;
        if (selectSetArr != null) {
            for (SelectSet selectSet : selectSetArr) {
                if (selectSet != null) {
                    selectSet.stop();
                }
            }
        }
        super.doStop();
    }

    /* access modifiers changed from: protected */
    public void connectionFailed(SocketChannel socketChannel, Throwable th, Object obj) {
        Logger logger = LOG;
        logger.warn(th + "," + socketChannel + "," + obj, new Object[0]);
        LOG.debug(th);
    }

    public String dump() {
        return AggregateLifeCycle.dump((Dumpable) this);
    }

    public void dump(Appendable appendable, String str) throws IOException {
        AggregateLifeCycle.dumpObject(appendable, this);
        AggregateLifeCycle.dump(appendable, str, TypeUtil.asList(this._selectSet));
    }

    /* renamed from: org.eclipse.jetty.io.nio.SelectorManager$SelectSet */
    public class SelectSet implements Dumpable {
        private int _busySelects;
        private final ConcurrentLinkedQueue<Object> _changes = new ConcurrentLinkedQueue<>();
        /* access modifiers changed from: private */
        public ConcurrentMap<SelectChannelEndPoint, Object> _endPoints = new ConcurrentHashMap();
        private volatile long _idleTick;
        private long _monitorNext;
        private boolean _paused;
        private boolean _pausing;
        private volatile Thread _selecting;
        private volatile Selector _selector;
        private final int _setID;
        private final Timeout _timeout;

        SelectSet(int i) throws Exception {
            this._setID = i;
            this._idleTick = System.currentTimeMillis();
            this._timeout = new Timeout(this);
            this._timeout.setDuration(0);
            this._selector = Selector.open();
            this._monitorNext = System.currentTimeMillis() + ((long) SelectorManager.__MONITOR_PERIOD);
        }

        public void addChange(Object obj) {
            this._changes.add(obj);
        }

        public void addChange(SelectableChannel selectableChannel, Object obj) {
            if (obj == null) {
                addChange(selectableChannel);
            } else if (obj instanceof EndPoint) {
                addChange(obj);
            } else {
                addChange(new ChannelAndAttachment(selectableChannel, obj));
            }
        }

        /* JADX WARNING: type inference failed for: r4v20, types: [java.nio.channels.ByteChannel] */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x01d8, code lost:
            r6 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x01da, code lost:
            r6 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x01e6, code lost:
            r5.cancel();
            r9.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:0x01ec, code lost:
            throw r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x020a, code lost:
            r6 = e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:152:0x020b, code lost:
            r9 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x0214, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.warn(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x021a, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.ignore(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:?, code lost:
            r9.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:0x0225, code lost:
            r6 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:163:?, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.debug(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:0x0240, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:171:0x0241, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.ignore(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0030, code lost:
            r2 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:194:0x02d6, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:195:0x02d8, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:197:?, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.ignore(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:199:0x02e1, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
            r5 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:202:0x02e8, code lost:
            if (r14.this$0.isRunning() != false) goto L_0x02ea;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:203:0x02ea, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.warn(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:204:0x02f0, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.ignore(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:206:0x02f7, code lost:
            r14._selecting = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:207:0x02fa, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x006b, code lost:
            r2 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0082, code lost:
            r4 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0083, code lost:
            r5 = r2;
            r2 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x00a6, code lost:
            r2 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00a7, code lost:
            r5 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ae, code lost:
            if (r14.this$0.isRunning() == false) goto L_0x00b6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b0, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.warn(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b6, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.debug(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x00bb, code lost:
            if (r5 == null) goto L_0x00ce;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
            r5.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x00c1, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.debug(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x00c8, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x00c9, code lost:
            org.eclipse.jetty.p119io.nio.SelectorManager.LOG.ignore(r2);
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:156:0x0214 A[Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }] */
        /* JADX WARNING: Removed duplicated region for block: B:157:0x021a A[Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }] */
        /* JADX WARNING: Removed duplicated region for block: B:159:0x0221 A[SYNTHETIC, Splitter:B:159:0x0221] */
        /* JADX WARNING: Removed duplicated region for block: B:170:0x0240 A[Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }, ExcHandler: CancelledKeyException (r5v4 'e' java.nio.channels.CancelledKeyException A[CUSTOM_DECLARE, Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }]), Splitter:B:111:0x0182] */
        /* JADX WARNING: Removed duplicated region for block: B:199:0x02e1 A[ExcHandler: ClosedSelectorException (r1v0 'e' java.nio.channels.ClosedSelectorException A[CUSTOM_DECLARE]), Splitter:B:1:0x0001] */
        /* JADX WARNING: Removed duplicated region for block: B:216:0x00ce A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x00b0 A[Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }] */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x00b6 A[Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }] */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x00bd A[SYNTHETIC, Splitter:B:59:0x00bd] */
        /* JADX WARNING: Removed duplicated region for block: B:64:0x00c8 A[Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }, ExcHandler: CancelledKeyException (r2v49 'e' java.nio.channels.CancelledKeyException A[CUSTOM_DECLARE, Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8, all -> 0x02d6 }]), Splitter:B:12:0x0021] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void doSelect() throws java.io.IOException {
            /*
                r14 = this;
                r0 = 0
                java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r14._selecting = r1     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                java.nio.channels.Selector r1 = r14._selector     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r1 != 0) goto L_0x000e
                r14._selecting = r0
                return
            L_0x000e:
                java.util.concurrent.ConcurrentLinkedQueue<java.lang.Object> r2 = r14._changes     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r2 = r2.size()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x0014:
                int r3 = r2 + -1
                r4 = 1
                if (r2 <= 0) goto L_0x00d1
                java.util.concurrent.ConcurrentLinkedQueue<java.lang.Object> r2 = r14._changes     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                java.lang.Object r2 = r2.poll()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 == 0) goto L_0x00d1
                boolean r5 = r2 instanceof org.eclipse.jetty.p119io.EndPoint     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                if (r5 == 0) goto L_0x0034
                org.eclipse.jetty.io.nio.SelectChannelEndPoint r2 = (org.eclipse.jetty.p119io.nio.SelectChannelEndPoint) r2     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                java.nio.channels.ByteChannel r4 = r2.getChannel()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                r2.doUpdateKey()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x0030, ClosedSelectorException -> 0x02e1 }
                goto L_0x00ce
            L_0x0030:
                r2 = move-exception
                r5 = r4
                goto L_0x00a8
            L_0x0034:
                boolean r5 = r2 instanceof org.eclipse.jetty.p119io.nio.SelectorManager.ChannelAndAttachment     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                if (r5 == 0) goto L_0x006d
                org.eclipse.jetty.io.nio.SelectorManager$ChannelAndAttachment r2 = (org.eclipse.jetty.p119io.nio.SelectorManager.ChannelAndAttachment) r2     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                java.nio.channels.SelectableChannel r5 = r2._channel     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                java.lang.Object r2 = r2._attachment     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                boolean r6 = r5 instanceof java.nio.channels.SocketChannel     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                if (r6 == 0) goto L_0x005e
                r6 = r5
                java.nio.channels.SocketChannel r6 = (java.nio.channels.SocketChannel) r6     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                boolean r6 = r6.isConnected()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                if (r6 == 0) goto L_0x005e
                java.nio.channels.SelectionKey r2 = r5.register(r1, r4, r2)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                r4 = r5
                java.nio.channels.SocketChannel r4 = (java.nio.channels.SocketChannel) r4     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                org.eclipse.jetty.io.nio.SelectChannelEndPoint r4 = r14.createEndPoint(r4, r2)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                r2.attach(r4)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                r4.schedule()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                goto L_0x00ce
            L_0x005e:
                boolean r4 = r5.isOpen()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                if (r4 == 0) goto L_0x00ce
                r4 = 8
                r5.register(r1, r4, r2)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x006b, ClosedSelectorException -> 0x02e1 }
                goto L_0x00ce
            L_0x006b:
                r2 = move-exception
                goto L_0x00a8
            L_0x006d:
                boolean r5 = r2 instanceof java.nio.channels.SocketChannel     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                if (r5 == 0) goto L_0x0086
                java.nio.channels.SocketChannel r2 = (java.nio.channels.SocketChannel) r2     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                java.nio.channels.SelectionKey r4 = r2.register(r1, r4, r0)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x0082, ClosedSelectorException -> 0x02e1 }
                org.eclipse.jetty.io.nio.SelectChannelEndPoint r5 = r14.createEndPoint(r2, r4)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x0082, ClosedSelectorException -> 0x02e1 }
                r4.attach(r5)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x0082, ClosedSelectorException -> 0x02e1 }
                r5.schedule()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x0082, ClosedSelectorException -> 0x02e1 }
                goto L_0x00ce
            L_0x0082:
                r4 = move-exception
                r5 = r2
                r2 = r4
                goto L_0x00a8
            L_0x0086:
                boolean r4 = r2 instanceof org.eclipse.jetty.p119io.nio.SelectorManager.ChangeTask     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                if (r4 == 0) goto L_0x0090
                java.lang.Runnable r2 = (java.lang.Runnable) r2     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                r2.run()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                goto L_0x00ce
            L_0x0090:
                boolean r4 = r2 instanceof java.lang.Runnable     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                if (r4 == 0) goto L_0x009c
                org.eclipse.jetty.io.nio.SelectorManager r4 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                java.lang.Runnable r2 = (java.lang.Runnable) r2     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                r4.dispatch(r2)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                goto L_0x00ce
            L_0x009c:
                java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                java.lang.String r2 = r2.toString()     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                r4.<init>(r2)     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
                throw r4     // Catch:{ CancelledKeyException -> 0x00c8, Throwable -> 0x00a6, ClosedSelectorException -> 0x02e1 }
            L_0x00a6:
                r2 = move-exception
                r5 = r0
            L_0x00a8:
                org.eclipse.jetty.io.nio.SelectorManager r4 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                boolean r4 = r4.isRunning()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r4 == 0) goto L_0x00b6
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r4.warn(r2)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x00bb
            L_0x00b6:
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r4.debug(r2)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x00bb:
                if (r5 == 0) goto L_0x00ce
                r5.close()     // Catch:{ IOException -> 0x00c1 }
                goto L_0x00ce
            L_0x00c1:
                r2 = move-exception
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r4.debug(r2)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x00ce
            L_0x00c8:
                r2 = move-exception
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r4.ignore(r2)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x00ce:
                r2 = r3
                goto L_0x0014
            L_0x00d1:
                int r2 = r1.selectNow()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r5 = java.lang.System.currentTimeMillis()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r3 = 0
                r7 = 0
                if (r2 != 0) goto L_0x0162
                java.util.Set r2 = r1.selectedKeys()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                boolean r2 = r2.isEmpty()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 == 0) goto L_0x0162
                boolean r2 = r14._pausing     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 == 0) goto L_0x00ff
                int r2 = org.eclipse.jetty.p119io.nio.SelectorManager.__BUSY_PAUSE     // Catch:{ InterruptedException -> 0x00f5 }
                long r5 = (long) r2     // Catch:{ InterruptedException -> 0x00f5 }
                java.lang.Thread.sleep(r5)     // Catch:{ InterruptedException -> 0x00f5 }
                goto L_0x00fb
            L_0x00f5:
                r2 = move-exception
                org.eclipse.jetty.util.log.Logger r5 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r5.ignore(r2)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x00fb:
                long r5 = java.lang.System.currentTimeMillis()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x00ff:
                org.eclipse.jetty.util.thread.Timeout r2 = r14._timeout     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r2.setNow(r5)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.util.thread.Timeout r2 = r14._timeout     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r9 = r2.getTimeToNext()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                java.util.concurrent.ConcurrentLinkedQueue<java.lang.Object> r2 = r14._changes     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r2 = r2.size()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 != 0) goto L_0x0118
                int r2 = org.eclipse.jetty.p119io.nio.SelectorManager.__IDLE_TICK     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r11 = (long) r2     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x0119
            L_0x0118:
                r11 = r7
            L_0x0119:
                int r2 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r2 <= 0) goto L_0x0126
                int r2 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r2 < 0) goto L_0x0126
                int r2 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
                if (r2 <= 0) goto L_0x0126
                goto L_0x0127
            L_0x0126:
                r9 = r11
            L_0x0127:
                int r2 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r2 <= 0) goto L_0x0162
                r1.select(r9)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r9 = java.lang.System.currentTimeMillis()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.util.thread.Timeout r2 = r14._timeout     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r2.setNow(r9)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r2 = org.eclipse.jetty.p119io.nio.SelectorManager.__MONITOR_PERIOD     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 <= 0) goto L_0x0162
                long r9 = r9 - r5
                r5 = 1
                int r2 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
                if (r2 > 0) goto L_0x0162
                int r2 = r14._busySelects     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r2 = r2 + r4
                r14._busySelects = r2     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r5 = org.eclipse.jetty.p119io.nio.SelectorManager.__MAX_SELECTS     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 <= r5) goto L_0x0162
                r14._pausing = r4     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                boolean r2 = r14._paused     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 != 0) goto L_0x0162
                r14._paused = r4     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                java.lang.String r5 = "Selector {} is too busy, pausing!"
                java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r6[r3] = r14     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r2.warn((java.lang.String) r5, (java.lang.Object[]) r6)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x0162:
                java.nio.channels.Selector r2 = r14._selector     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 == 0) goto L_0x02d3
                boolean r2 = r1.isOpen()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r2 != 0) goto L_0x016e
                goto L_0x02d3
            L_0x016e:
                java.util.Set r2 = r1.selectedKeys()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                java.util.Iterator r2 = r2.iterator()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x0176:
                boolean r5 = r2.hasNext()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r5 == 0) goto L_0x0248
                java.lang.Object r5 = r2.next()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                java.nio.channels.SelectionKey r5 = (java.nio.channels.SelectionKey) r5     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                boolean r6 = r5.isValid()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                if (r6 != 0) goto L_0x0197
                r5.cancel()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                java.lang.Object r6 = r5.attachment()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                org.eclipse.jetty.io.nio.SelectChannelEndPoint r6 = (org.eclipse.jetty.p119io.nio.SelectChannelEndPoint) r6     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                if (r6 == 0) goto L_0x0176
                r6.doUpdateKey()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                goto L_0x0176
            L_0x0197:
                java.lang.Object r6 = r5.attachment()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                boolean r9 = r6 instanceof org.eclipse.jetty.p119io.nio.SelectChannelEndPoint     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                if (r9 == 0) goto L_0x01b1
                boolean r9 = r5.isReadable()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                if (r9 != 0) goto L_0x01ab
                boolean r9 = r5.isWritable()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                if (r9 == 0) goto L_0x0176
            L_0x01ab:
                org.eclipse.jetty.io.nio.SelectChannelEndPoint r6 = (org.eclipse.jetty.p119io.nio.SelectChannelEndPoint) r6     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                r6.schedule()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                goto L_0x0176
            L_0x01b1:
                boolean r9 = r5.isConnectable()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                if (r9 == 0) goto L_0x01ed
                java.nio.channels.SelectableChannel r9 = r5.channel()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                java.nio.channels.SocketChannel r9 = (java.nio.channels.SocketChannel) r9     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                boolean r6 = r9.finishConnect()     // Catch:{ Exception -> 0x01dc }
                if (r6 == 0) goto L_0x01d1
                r5.interestOps(r4)     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                org.eclipse.jetty.io.nio.SelectChannelEndPoint r6 = r14.createEndPoint(r9, r5)     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                r5.attach(r6)     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                r6.schedule()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                goto L_0x0176
            L_0x01d1:
                r5.cancel()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
            L_0x01d4:
                r9.close()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                goto L_0x0176
            L_0x01d8:
                r6 = move-exception
                goto L_0x020c
            L_0x01da:
                r6 = move-exception
                goto L_0x01e6
            L_0x01dc:
                r10 = move-exception
                org.eclipse.jetty.io.nio.SelectorManager r11 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ all -> 0x01da }
                r11.connectionFailed(r9, r10, r6)     // Catch:{ all -> 0x01da }
                r5.cancel()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                goto L_0x01d4
            L_0x01e6:
                r5.cancel()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                r9.close()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
                throw r6     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x01d8, ClosedSelectorException -> 0x02e1 }
            L_0x01ed:
                java.nio.channels.SelectableChannel r6 = r5.channel()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                java.nio.channels.SocketChannel r6 = (java.nio.channels.SocketChannel) r6     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x020a, ClosedSelectorException -> 0x02e1 }
                org.eclipse.jetty.io.nio.SelectChannelEndPoint r9 = r14.createEndPoint(r6, r5)     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x0205, ClosedSelectorException -> 0x02e1 }
                r5.attach(r9)     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x0205, ClosedSelectorException -> 0x02e1 }
                boolean r10 = r5.isReadable()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x0205, ClosedSelectorException -> 0x02e1 }
                if (r10 == 0) goto L_0x0176
                r9.schedule()     // Catch:{ CancelledKeyException -> 0x0240, Exception -> 0x0205, ClosedSelectorException -> 0x02e1 }
                goto L_0x0176
            L_0x0205:
                r9 = move-exception
                r13 = r9
                r9 = r6
                r6 = r13
                goto L_0x020c
            L_0x020a:
                r6 = move-exception
                r9 = r0
            L_0x020c:
                org.eclipse.jetty.io.nio.SelectorManager r10 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                boolean r10 = r10.isRunning()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r10 == 0) goto L_0x021a
                org.eclipse.jetty.util.log.Logger r10 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r10.warn(r6)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x021f
            L_0x021a:
                org.eclipse.jetty.util.log.Logger r10 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r10.ignore(r6)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x021f:
                if (r9 == 0) goto L_0x022b
                r9.close()     // Catch:{ IOException -> 0x0225 }
                goto L_0x022b
            L_0x0225:
                r6 = move-exception
                org.eclipse.jetty.util.log.Logger r9 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r9.debug(r6)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x022b:
                if (r5 == 0) goto L_0x0176
                java.nio.channels.SelectableChannel r6 = r5.channel()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                boolean r6 = r6 instanceof java.nio.channels.ServerSocketChannel     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r6 != 0) goto L_0x0176
                boolean r6 = r5.isValid()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r6 == 0) goto L_0x0176
                r5.cancel()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x0176
            L_0x0240:
                r5 = move-exception
                org.eclipse.jetty.util.log.Logger r6 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r6.ignore(r5)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x0176
            L_0x0248:
                java.util.Set r2 = r1.selectedKeys()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r2.clear()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r4 = java.lang.System.currentTimeMillis()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.util.thread.Timeout r2 = r14._timeout     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r2.setNow(r4)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.util.thread.Timeout r2 = r14._timeout     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.util.thread.Timeout$Task r2 = r2.expired()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x025e:
                if (r2 == 0) goto L_0x0272
                boolean r6 = r2 instanceof java.lang.Runnable     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r6 == 0) goto L_0x026b
                org.eclipse.jetty.io.nio.SelectorManager r6 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                java.lang.Runnable r2 = (java.lang.Runnable) r2     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r6.dispatch(r2)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x026b:
                org.eclipse.jetty.util.thread.Timeout r2 = r14._timeout     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.util.thread.Timeout$Task r2 = r2.expired()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x025e
            L_0x0272:
                long r9 = r14._idleTick     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r9 = r4 - r9
                int r2 = org.eclipse.jetty.p119io.nio.SelectorManager.__IDLE_TICK     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r11 = (long) r2     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r2 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
                if (r2 <= 0) goto L_0x02ba
                r14._idleTick = r4     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.io.nio.SelectorManager r2 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r9 = r2._lowResourcesConnections     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r2 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r2 <= 0) goto L_0x02af
                java.util.Set r1 = r1.keys()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r1 = r1.size()     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r1 = (long) r1     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.io.nio.SelectorManager r6 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r6 = r6._lowResourcesConnections     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r8 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
                if (r8 <= 0) goto L_0x02af
                org.eclipse.jetty.io.nio.SelectorManager r1 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r1 = r1._maxIdleTime     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r1 = (long) r1     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r1 = r1 + r4
                org.eclipse.jetty.io.nio.SelectorManager r6 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r6 = r6._lowResourcesMaxIdleTime     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r6 = (long) r6     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r1 = r1 - r6
                goto L_0x02b0
            L_0x02af:
                r1 = r4
            L_0x02b0:
                org.eclipse.jetty.io.nio.SelectorManager r6 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                org.eclipse.jetty.io.nio.SelectorManager$SelectSet$1 r7 = new org.eclipse.jetty.io.nio.SelectorManager$SelectSet$1     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r7.<init>(r1)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r6.dispatch(r7)     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
            L_0x02ba:
                int r1 = org.eclipse.jetty.p119io.nio.SelectorManager.__MONITOR_PERIOD     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                if (r1 <= 0) goto L_0x02de
                long r1 = r14._monitorNext     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
                if (r6 <= 0) goto L_0x02de
                r14._busySelects = r3     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                r14._pausing = r3     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                int r1 = org.eclipse.jetty.p119io.nio.SelectorManager.__MONITOR_PERIOD     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r1 = (long) r1     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                long r4 = r4 + r1
                r14._monitorNext = r4     // Catch:{ ClosedSelectorException -> 0x02e1, CancelledKeyException -> 0x02d8 }
                goto L_0x02de
            L_0x02d3:
                r14._selecting = r0
                return
            L_0x02d6:
                r1 = move-exception
                goto L_0x02f7
            L_0x02d8:
                r1 = move-exception
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ all -> 0x02d6 }
                r2.ignore(r1)     // Catch:{ all -> 0x02d6 }
            L_0x02de:
                r14._selecting = r0
                goto L_0x02f6
            L_0x02e1:
                r1 = move-exception
                org.eclipse.jetty.io.nio.SelectorManager r2 = org.eclipse.jetty.p119io.nio.SelectorManager.this     // Catch:{ all -> 0x02d6 }
                boolean r2 = r2.isRunning()     // Catch:{ all -> 0x02d6 }
                if (r2 == 0) goto L_0x02f0
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ all -> 0x02d6 }
                r2.warn(r1)     // Catch:{ all -> 0x02d6 }
                goto L_0x02de
            L_0x02f0:
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG     // Catch:{ all -> 0x02d6 }
                r2.ignore(r1)     // Catch:{ all -> 0x02d6 }
                goto L_0x02de
            L_0x02f6:
                return
            L_0x02f7:
                r14._selecting = r0
                goto L_0x02fb
            L_0x02fa:
                throw r1
            L_0x02fb:
                goto L_0x02fa
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SelectorManager.SelectSet.doSelect():void");
        }

        /* access modifiers changed from: private */
        public void renewSelector() {
            try {
                synchronized (this) {
                    Selector selector = this._selector;
                    if (selector != null) {
                        Selector open = Selector.open();
                        for (SelectionKey next : selector.keys()) {
                            if (next.isValid()) {
                                if (next.interestOps() != 0) {
                                    SelectableChannel channel = next.channel();
                                    Object attachment = next.attachment();
                                    if (attachment == null) {
                                        addChange(channel);
                                    } else {
                                        addChange(channel, attachment);
                                    }
                                }
                            }
                        }
                        this._selector.close();
                        this._selector = open;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("recreating selector", e);
            }
        }

        public SelectorManager getManager() {
            return SelectorManager.this;
        }

        public long getNow() {
            return this._timeout.getNow();
        }

        public void scheduleTimeout(Timeout.Task task, long j) {
            if (task instanceof Runnable) {
                this._timeout.schedule(task, j);
                return;
            }
            throw new IllegalArgumentException("!Runnable");
        }

        public void cancelTimeout(Timeout.Task task) {
            task.cancel();
        }

        public void wakeup() {
            try {
                Selector selector = this._selector;
                if (selector != null) {
                    selector.wakeup();
                }
            } catch (Exception unused) {
                addChange(new ChangeTask() {
                    public void run() {
                        SelectSet.this.renewSelector();
                    }
                });
                renewSelector();
            }
        }

        private SelectChannelEndPoint createEndPoint(SocketChannel socketChannel, SelectionKey selectionKey) throws IOException {
            SelectChannelEndPoint newEndPoint = SelectorManager.this.newEndPoint(socketChannel, this, selectionKey);
            SelectorManager.LOG.debug("created {}", newEndPoint);
            SelectorManager.this.endPointOpened(newEndPoint);
            this._endPoints.put(newEndPoint, this);
            return newEndPoint;
        }

        public void destroyEndPoint(SelectChannelEndPoint selectChannelEndPoint) {
            SelectorManager.LOG.debug("destroyEndPoint {}", selectChannelEndPoint);
            this._endPoints.remove(selectChannelEndPoint);
            SelectorManager.this.endPointClosed(selectChannelEndPoint);
        }

        /* access modifiers changed from: package-private */
        public Selector getSelector() {
            return this._selector;
        }

        /* access modifiers changed from: package-private */
        public void stop() throws Exception {
            int i = 0;
            while (i < 100) {
                try {
                    if (this._selecting == null) {
                        break;
                    }
                    wakeup();
                    Thread.sleep(10);
                    i++;
                } catch (Exception e) {
                    SelectorManager.LOG.ignore(e);
                }
            }
            synchronized (this) {
                for (SelectionKey next : this._selector.keys()) {
                    if (next != null) {
                        Object attachment = next.attachment();
                        if (attachment instanceof EndPoint) {
                            try {
                                ((EndPoint) attachment).close();
                            } catch (IOException e2) {
                                SelectorManager.LOG.ignore(e2);
                            }
                        } else {
                            continue;
                        }
                    }
                }
                this._timeout.cancelAll();
                try {
                    Selector selector = this._selector;
                    if (selector != null) {
                        selector.close();
                    }
                } catch (IOException e3) {
                    SelectorManager.LOG.ignore(e3);
                }
                this._selector = null;
            }
        }

        public String dump() {
            return AggregateLifeCycle.dump((Dumpable) this);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.String} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.String} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.StackTraceElement} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void dump(java.lang.Appendable r8, java.lang.String r9) throws java.io.IOException {
            /*
                r7 = this;
                java.lang.String r0 = java.lang.String.valueOf(r7)
                java.lang.Appendable r0 = r8.append(r0)
                java.lang.String r1 = " id="
                java.lang.Appendable r0 = r0.append(r1)
                int r1 = r7._setID
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.Appendable r0 = r0.append(r1)
                java.lang.String r1 = "\n"
                r0.append(r1)
                java.lang.Thread r0 = r7._selecting
                if (r0 != 0) goto L_0x0023
                r0 = 0
                goto L_0x0027
            L_0x0023:
                java.lang.StackTraceElement[] r0 = r0.getStackTrace()
            L_0x0027:
                r1 = 0
                if (r0 == 0) goto L_0x0040
                int r2 = r0.length
                r3 = 0
            L_0x002c:
                if (r3 >= r2) goto L_0x0040
                r4 = r0[r3]
                java.lang.String r5 = r4.getClassName()
                java.lang.String r6 = "org.eclipse.jetty."
                boolean r5 = r5.startsWith(r6)
                if (r5 == 0) goto L_0x003d
                goto L_0x0042
            L_0x003d:
                int r3 = r3 + 1
                goto L_0x002c
            L_0x0040:
                java.lang.String r4 = "not selecting"
            L_0x0042:
                java.nio.channels.Selector r0 = r7._selector
                if (r0 == 0) goto L_0x007b
                java.util.ArrayList r2 = new java.util.ArrayList
                java.util.Set r0 = r0.keys()
                int r0 = r0.size()
                int r0 = r0 * 2
                r2.<init>(r0)
                r2.add(r4)
                java.util.concurrent.CountDownLatch r0 = new java.util.concurrent.CountDownLatch
                r3 = 1
                r0.<init>(r3)
                org.eclipse.jetty.io.nio.SelectorManager$SelectSet$3 r4 = new org.eclipse.jetty.io.nio.SelectorManager$SelectSet$3
                r4.<init>(r2, r0)
                r7.addChange(r4)
                r4 = 5
                java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x006e }
                r0.await(r4, r6)     // Catch:{ InterruptedException -> 0x006e }
                goto L_0x0074
            L_0x006e:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.p119io.nio.SelectorManager.LOG
                r4.ignore(r0)
            L_0x0074:
                java.util.Collection[] r0 = new java.util.Collection[r3]
                r0[r1] = r2
                org.eclipse.jetty.util.component.AggregateLifeCycle.dump(r8, r9, r0)
            L_0x007b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.p119io.nio.SelectorManager.SelectSet.dump(java.lang.Appendable, java.lang.String):void");
        }

        public void dumpKeyState(List<Object> list) {
            Selector selector = this._selector;
            Set<SelectionKey> keys = selector.keys();
            list.add(selector + " keys=" + keys.size());
            for (SelectionKey next : keys) {
                if (next.isValid()) {
                    list.add(next.attachment() + " iOps=" + next.interestOps() + " rOps=" + next.readyOps());
                } else {
                    list.add(next.attachment() + " iOps=-1 rOps=-1");
                }
            }
        }

        public String toString() {
            Selector selector = this._selector;
            Object[] objArr = new Object[3];
            objArr[0] = super.toString();
            int i = -1;
            objArr[1] = Integer.valueOf((selector == null || !selector.isOpen()) ? -1 : selector.keys().size());
            if (selector != null && selector.isOpen()) {
                i = selector.selectedKeys().size();
            }
            objArr[2] = Integer.valueOf(i);
            return String.format("%s keys=%d selected=%d", objArr);
        }
    }

    /* renamed from: org.eclipse.jetty.io.nio.SelectorManager$ChannelAndAttachment */
    private static class ChannelAndAttachment {
        final Object _attachment;
        final SelectableChannel _channel;

        public ChannelAndAttachment(SelectableChannel selectableChannel, Object obj) {
            this._channel = selectableChannel;
            this._attachment = obj;
        }
    }

    public boolean isDeferringInterestedOps0() {
        return this._deferringInterestedOps0;
    }

    public void setDeferringInterestedOps0(boolean z) {
        this._deferringInterestedOps0 = z;
    }
}
