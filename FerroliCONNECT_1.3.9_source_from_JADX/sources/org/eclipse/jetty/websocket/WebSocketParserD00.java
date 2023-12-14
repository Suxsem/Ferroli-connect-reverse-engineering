package org.eclipse.jetty.websocket;

import java.io.IOException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.WebSocketParser;

public class WebSocketParserD00 implements WebSocketParser {
    private static final Logger LOG = Log.getLogger((Class<?>) WebSocketParserD00.class);
    public static final int STATE_DATA = 3;
    public static final int STATE_LENGTH = 2;
    public static final int STATE_SENTINEL_DATA = 1;
    public static final int STATE_START = 0;
    private Buffer _buffer;
    private final WebSocketBuffers _buffers;
    private final EndPoint _endp;
    private final WebSocketParser.FrameHandler _handler;
    private int _length;
    private byte _opcode;
    private int _state;

    public WebSocketParserD00(WebSocketBuffers webSocketBuffers, EndPoint endPoint, WebSocketParser.FrameHandler frameHandler) {
        this._buffers = webSocketBuffers;
        this._endp = endPoint;
        this._handler = frameHandler;
    }

    public boolean isBufferEmpty() {
        Buffer buffer = this._buffer;
        return buffer == null || buffer.length() == 0;
    }

    public Buffer getBuffer() {
        return this._buffer;
    }

    public int parseNext() {
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        int i = 0;
        while (true) {
            int length = this._buffer.length();
            if (length == 0 || (this._state == 3 && length < this._length)) {
                this._buffer.compact();
                if (this._buffer.space() != 0) {
                    try {
                        int fill = this._endp.isOpen() ? this._endp.fill(this._buffer) : -1;
                        if (fill <= 0) {
                            return i;
                        }
                        i += fill;
                        length = this._buffer.length();
                    } catch (IOException e) {
                        LOG.debug(e);
                        if (i > 0) {
                            return i;
                        }
                        return -1;
                    }
                } else {
                    throw new IllegalStateException("FULL");
                }
            }
            while (true) {
                int i2 = length - 1;
                if (length <= 0) {
                    break;
                }
                int i3 = this._state;
                if (i3 == 0) {
                    this._opcode = this._buffer.get();
                    if (this._opcode < 0) {
                        this._length = 0;
                        this._state = 2;
                    } else {
                        this._state = 1;
                        this._buffer.mark(0);
                    }
                } else if (i3 != 1) {
                    if (i3 == 2) {
                        byte b = this._buffer.get();
                        this._length = (this._length << 7) | (b & ByteCompanionObject.MAX_VALUE);
                        if (b >= 0) {
                            this._state = 3;
                            this._buffer.mark(0);
                        }
                    } else if (i3 == 3) {
                        if (this._buffer.markIndex() >= 0 || this._buffer.length() >= this._length) {
                            Buffer sliceFromMark = this._buffer.sliceFromMark(this._length);
                            this._buffer.skip(this._length);
                            this._state = 0;
                            int i4 = i + 1;
                            this._handler.onFrame((byte) 0, this._opcode, sliceFromMark);
                        }
                    }
                } else if ((this._buffer.get() & UByte.MAX_VALUE) == 255) {
                    this._state = 0;
                    int i5 = i + 1;
                    this._handler.onFrame((byte) 0, this._opcode, this._buffer.sliceFromMark((this._buffer.getIndex() - this._buffer.markIndex()) - 1));
                    this._buffer.setMarkIndex(-1);
                    if (this._buffer.length() == 0) {
                        this._buffers.returnBuffer(this._buffer);
                        this._buffer = null;
                    }
                    return i5;
                }
                length = i2;
            }
        }
        Buffer sliceFromMark2 = this._buffer.sliceFromMark(this._length);
        this._buffer.skip(this._length);
        this._state = 0;
        int i42 = i + 1;
        this._handler.onFrame((byte) 0, this._opcode, sliceFromMark2);
        if (this._buffer.length() == 0) {
            this._buffers.returnBuffer(this._buffer);
            this._buffer = null;
        }
        return i42;
    }

    public void fill(Buffer buffer) {
        if (buffer != null && buffer.length() > 0) {
            if (this._buffer == null) {
                this._buffer = this._buffers.getBuffer();
            }
            this._buffer.put(buffer);
            buffer.clear();
        }
    }
}
