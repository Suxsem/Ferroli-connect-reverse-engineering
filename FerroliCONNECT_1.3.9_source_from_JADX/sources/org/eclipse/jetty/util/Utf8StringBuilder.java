package org.eclipse.jetty.util;

public class Utf8StringBuilder extends Utf8Appendable {
    final StringBuilder _buffer = ((StringBuilder) this._appendable);

    public Utf8StringBuilder() {
        super(new StringBuilder());
    }

    public Utf8StringBuilder(int i) {
        super(new StringBuilder(i));
    }

    public int length() {
        return this._buffer.length();
    }

    public void reset() {
        super.reset();
        this._buffer.setLength(0);
    }

    public StringBuilder getStringBuilder() {
        checkState();
        return this._buffer;
    }

    public String toString() {
        checkState();
        return this._buffer.toString();
    }
}
