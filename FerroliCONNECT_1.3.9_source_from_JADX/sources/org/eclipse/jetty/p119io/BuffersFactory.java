package org.eclipse.jetty.p119io;

import org.eclipse.jetty.p119io.Buffers;

/* renamed from: org.eclipse.jetty.io.BuffersFactory */
public class BuffersFactory {
    public static Buffers newBuffers(Buffers.Type type, int i, Buffers.Type type2, int i2, Buffers.Type type3, int i3) {
        if (i3 >= 0) {
            return new PooledBuffers(type, i, type2, i2, type3, i3);
        }
        return new ThreadLocalBuffers(type, i, type2, i2, type3);
    }
}
