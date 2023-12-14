package org.eclipse.jetty.p119io;

import org.eclipse.jetty.p119io.Buffer;

/* renamed from: org.eclipse.jetty.io.View */
public class View extends AbstractBuffer {
    Buffer _buffer;

    public void compact() {
    }

    public boolean isVolatile() {
        return true;
    }

    public View(Buffer buffer, int i, int i2, int i3, int i4) {
        super(2, !buffer.isImmutable());
        this._buffer = buffer.buffer();
        setPutIndex(i3);
        setGetIndex(i2);
        setMarkIndex(i);
        this._access = i4;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public View(Buffer buffer) {
        super(2, !buffer.isImmutable());
        int i = 1;
        this._buffer = buffer.buffer();
        setPutIndex(buffer.putIndex());
        setGetIndex(buffer.getIndex());
        setMarkIndex(buffer.markIndex());
        this._access = !buffer.isReadOnly() ? 2 : i;
    }

    public View() {
        super(2, true);
    }

    public void update(Buffer buffer) {
        int i = 2;
        this._access = 2;
        this._buffer = buffer.buffer();
        setGetIndex(0);
        setPutIndex(buffer.putIndex());
        setGetIndex(buffer.getIndex());
        setMarkIndex(buffer.markIndex());
        if (buffer.isReadOnly()) {
            i = 1;
        }
        this._access = i;
    }

    public void update(int i, int i2) {
        int i3 = this._access;
        this._access = 2;
        setGetIndex(0);
        setPutIndex(i2);
        setGetIndex(i);
        setMarkIndex(-1);
        this._access = i3;
    }

    public byte[] array() {
        return this._buffer.array();
    }

    public Buffer buffer() {
        return this._buffer.buffer();
    }

    public int capacity() {
        return this._buffer.capacity();
    }

    public void clear() {
        setMarkIndex(-1);
        setGetIndex(0);
        setPutIndex(this._buffer.getIndex());
        setGetIndex(this._buffer.getIndex());
    }

    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof Buffer) && obj.equals(this)) || super.equals(obj);
    }

    public boolean isReadOnly() {
        return this._buffer.isReadOnly();
    }

    public byte peek(int i) {
        return this._buffer.peek(i);
    }

    public int peek(int i, byte[] bArr, int i2, int i3) {
        return this._buffer.peek(i, bArr, i2, i3);
    }

    public Buffer peek(int i, int i2) {
        return this._buffer.peek(i, i2);
    }

    public int poke(int i, Buffer buffer) {
        return this._buffer.poke(i, buffer);
    }

    public void poke(int i, byte b) {
        this._buffer.poke(i, b);
    }

    public int poke(int i, byte[] bArr, int i2, int i3) {
        return this._buffer.poke(i, bArr, i2, i3);
    }

    public String toString() {
        if (this._buffer == null) {
            return "INVALID";
        }
        return super.toString();
    }

    /* renamed from: org.eclipse.jetty.io.View$CaseInsensitive */
    public static class CaseInsensitive extends View implements Buffer.CaseInsensitve {
        public CaseInsensitive() {
        }

        public CaseInsensitive(Buffer buffer, int i, int i2, int i3, int i4) {
            super(buffer, i, i2, i3, i4);
        }

        public CaseInsensitive(Buffer buffer) {
            super(buffer);
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof Buffer) && ((Buffer) obj).equalsIgnoreCase(this)) || View.super.equals(obj);
        }
    }
}
