package org.eclipse.jetty.p119io;

import org.eclipse.jetty.p119io.Buffers;

/* renamed from: org.eclipse.jetty.io.ThreadLocalBuffers */
public class ThreadLocalBuffers extends AbstractBuffers {
    private final ThreadLocal<ThreadBuffers> _buffers = new ThreadLocal<ThreadBuffers>() {
        /* access modifiers changed from: protected */
        public ThreadBuffers initialValue() {
            return new ThreadBuffers();
        }
    };

    public ThreadLocalBuffers(Buffers.Type type, int i, Buffers.Type type2, int i2, Buffers.Type type3) {
        super(type, i, type2, i2, type3);
    }

    public Buffer getBuffer() {
        ThreadBuffers threadBuffers = this._buffers.get();
        if (threadBuffers._buffer != null) {
            Buffer buffer = threadBuffers._buffer;
            threadBuffers._buffer = null;
            return buffer;
        } else if (threadBuffers._other == null || !isBuffer(threadBuffers._other)) {
            return newBuffer();
        } else {
            Buffer buffer2 = threadBuffers._other;
            threadBuffers._other = null;
            return buffer2;
        }
    }

    public Buffer getHeader() {
        ThreadBuffers threadBuffers = this._buffers.get();
        if (threadBuffers._header != null) {
            Buffer buffer = threadBuffers._header;
            threadBuffers._header = null;
            return buffer;
        } else if (threadBuffers._other == null || !isHeader(threadBuffers._other)) {
            return newHeader();
        } else {
            Buffer buffer2 = threadBuffers._other;
            threadBuffers._other = null;
            return buffer2;
        }
    }

    public Buffer getBuffer(int i) {
        ThreadBuffers threadBuffers = this._buffers.get();
        if (threadBuffers._other == null || threadBuffers._other.capacity() != i) {
            return newBuffer(i);
        }
        Buffer buffer = threadBuffers._other;
        threadBuffers._other = null;
        return buffer;
    }

    public void returnBuffer(Buffer buffer) {
        buffer.clear();
        if (!buffer.isVolatile() && !buffer.isImmutable()) {
            ThreadBuffers threadBuffers = this._buffers.get();
            if (threadBuffers._header == null && isHeader(buffer)) {
                threadBuffers._header = buffer;
            } else if (threadBuffers._buffer != null || !isBuffer(buffer)) {
                threadBuffers._other = buffer;
            } else {
                threadBuffers._buffer = buffer;
            }
        }
    }

    public String toString() {
        return "{{" + getHeaderSize() + "," + getBufferSize() + "}}";
    }

    /* renamed from: org.eclipse.jetty.io.ThreadLocalBuffers$ThreadBuffers */
    protected static class ThreadBuffers {
        Buffer _buffer;
        Buffer _header;
        Buffer _other;

        protected ThreadBuffers() {
        }
    }
}
