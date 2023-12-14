package org.eclipse.jetty.p119io;

/* renamed from: org.eclipse.jetty.io.Buffers */
public interface Buffers {

    /* renamed from: org.eclipse.jetty.io.Buffers$Type */
    public enum Type {
        BYTE_ARRAY,
        DIRECT,
        INDIRECT
    }

    Buffer getBuffer();

    Buffer getBuffer(int i);

    Buffer getHeader();

    void returnBuffer(Buffer buffer);
}
