package org.eclipse.jetty.http;

import java.io.IOException;

public interface Parser {
    boolean isComplete();

    boolean isIdle();

    boolean isMoreInBuffer() throws IOException;

    boolean isPersistent();

    boolean parseAvailable() throws IOException;

    void reset();

    void returnBuffers();

    void setPersistent(boolean z);
}
