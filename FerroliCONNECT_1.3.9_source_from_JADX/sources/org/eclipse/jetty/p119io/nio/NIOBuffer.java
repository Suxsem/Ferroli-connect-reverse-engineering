package org.eclipse.jetty.p119io.nio;

import java.nio.ByteBuffer;
import org.eclipse.jetty.p119io.Buffer;

/* renamed from: org.eclipse.jetty.io.nio.NIOBuffer */
public interface NIOBuffer extends Buffer {
    ByteBuffer getByteBuffer();

    boolean isDirect();
}
