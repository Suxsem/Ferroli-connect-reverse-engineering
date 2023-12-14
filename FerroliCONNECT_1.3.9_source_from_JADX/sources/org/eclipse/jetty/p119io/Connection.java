package org.eclipse.jetty.p119io;

import java.io.IOException;

/* renamed from: org.eclipse.jetty.io.Connection */
public interface Connection {
    long getTimeStamp();

    Connection handle() throws IOException;

    boolean isIdle();

    boolean isSuspended();

    void onClose();

    void onIdleExpired(long j);
}
