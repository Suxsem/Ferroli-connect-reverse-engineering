package org.eclipse.jetty.util;

public class Utf8StringBuffer extends Utf8Appendable {
    final StringBuffer _buffer = ((StringBuffer) this._appendable);

    public Utf8StringBuffer() {
        super(new StringBuffer());
    }

    public Utf8StringBuffer(int i) {
        super(new StringBuffer(i));
    }

    public int length() {
        return this._buffer.length();
    }

    public void reset() {
        super.reset();
        this._buffer.setLength(0);
    }

    public StringBuffer getStringBuffer() {
        checkState();
        return this._buffer;
    }

    public String toString() {
        checkState();
        return this._buffer.toString();
    }
}
