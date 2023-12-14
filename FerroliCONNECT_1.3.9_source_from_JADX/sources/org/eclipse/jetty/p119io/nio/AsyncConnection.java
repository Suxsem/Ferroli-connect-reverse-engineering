package org.eclipse.jetty.p119io.nio;

import java.io.IOException;
import org.eclipse.jetty.p119io.Connection;

/* renamed from: org.eclipse.jetty.io.nio.AsyncConnection */
public interface AsyncConnection extends Connection {
    void onInputShutdown() throws IOException;
}
