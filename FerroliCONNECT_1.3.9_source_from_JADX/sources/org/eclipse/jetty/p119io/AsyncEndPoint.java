package org.eclipse.jetty.p119io;

import org.eclipse.jetty.util.thread.Timeout;

/* renamed from: org.eclipse.jetty.io.AsyncEndPoint */
public interface AsyncEndPoint extends ConnectedEndPoint {
    void asyncDispatch();

    void cancelTimeout(Timeout.Task task);

    void dispatch();

    boolean hasProgressed();

    boolean isCheckForIdle();

    boolean isWritable();

    void onIdleExpired(long j);

    void scheduleTimeout(Timeout.Task task, long j);

    void scheduleWrite();

    void setCheckForIdle(boolean z);
}
