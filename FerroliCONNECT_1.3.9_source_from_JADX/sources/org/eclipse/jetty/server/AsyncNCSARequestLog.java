package org.eclipse.jetty.server;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class AsyncNCSARequestLog extends NCSARequestLog {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) AsyncNCSARequestLog.class);
    /* access modifiers changed from: private */
    public final BlockingQueue<String> _queue;
    private transient WriterThread _thread;
    private boolean _warnedFull;

    public AsyncNCSARequestLog() {
        this((String) null, (BlockingQueue<String>) null);
    }

    public AsyncNCSARequestLog(BlockingQueue<String> blockingQueue) {
        this((String) null, blockingQueue);
    }

    public AsyncNCSARequestLog(String str) {
        this(str, (BlockingQueue<String>) null);
    }

    public AsyncNCSARequestLog(String str, BlockingQueue<String> blockingQueue) {
        super(str);
        this._queue = blockingQueue == null ? new BlockingArrayQueue<>(1024) : blockingQueue;
    }

    private class WriterThread extends Thread {
        WriterThread() {
            setName("AsyncNCSARequestLog@" + Integer.toString(AsyncNCSARequestLog.this.hashCode(), 16));
        }

        public void run() {
            while (AsyncNCSARequestLog.this.isRunning()) {
                try {
                    String str = (String) AsyncNCSARequestLog.this._queue.poll(10, TimeUnit.SECONDS);
                    if (str != null) {
                        AsyncNCSARequestLog.super.write(str);
                    }
                    while (!AsyncNCSARequestLog.this._queue.isEmpty()) {
                        String str2 = (String) AsyncNCSARequestLog.this._queue.poll();
                        if (str2 != null) {
                            AsyncNCSARequestLog.super.write(str2);
                        }
                    }
                } catch (IOException e) {
                    AsyncNCSARequestLog.LOG.warn(e);
                } catch (InterruptedException e2) {
                    AsyncNCSARequestLog.LOG.ignore(e2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void doStart() throws Exception {
        super.doStart();
        this._thread = new WriterThread();
        this._thread.start();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        this._thread.interrupt();
        this._thread.join();
        super.doStop();
        this._thread = null;
    }

    /* access modifiers changed from: protected */
    public void write(String str) throws IOException {
        if (!this._queue.offer(str)) {
            if (this._warnedFull) {
                LOG.warn("Log Queue overflow", new Object[0]);
            }
            this._warnedFull = true;
        }
    }
}
