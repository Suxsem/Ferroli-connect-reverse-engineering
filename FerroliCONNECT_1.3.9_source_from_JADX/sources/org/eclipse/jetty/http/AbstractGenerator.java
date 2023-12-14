package org.eclipse.jetty.http;

import java.io.IOException;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.EofException;
import org.eclipse.jetty.p119io.View;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public abstract class AbstractGenerator implements Generator {
    private static final Logger LOG = Log.getLogger((Class<?>) AbstractGenerator.class);
    public static final byte[] NO_BYTES = new byte[0];
    public static final int STATE_CONTENT = 2;
    public static final int STATE_END = 4;
    public static final int STATE_FLUSHING = 3;
    public static final int STATE_HEADER = 0;
    protected Buffer _buffer;
    protected final Buffers _buffers;
    protected Buffer _content;
    protected long _contentLength = -3;
    protected long _contentWritten = 0;
    protected Buffer _date;
    protected final EndPoint _endp;
    protected boolean _head = false;
    protected Buffer _header;
    protected boolean _last = false;
    protected Buffer _method;
    protected boolean _noContent = false;
    protected Boolean _persistent = null;
    protected Buffer _reason;
    private boolean _sendServerVersion;
    protected int _state = 0;
    protected int _status = 0;
    protected String _uri;
    protected int _version = 11;

    public abstract void completeHeader(HttpFields httpFields, boolean z) throws IOException;

    public abstract int flushBuffer() throws IOException;

    public abstract boolean isRequest();

    public abstract boolean isResponse();

    public abstract int prepareUncheckedAddContent() throws IOException;

    public AbstractGenerator(Buffers buffers, EndPoint endPoint) {
        this._buffers = buffers;
        this._endp = endPoint;
    }

    public boolean isOpen() {
        return this._endp.isOpen();
    }

    public void reset() {
        this._state = 0;
        this._status = 0;
        this._version = 11;
        this._reason = null;
        this._last = false;
        this._head = false;
        this._noContent = false;
        this._persistent = null;
        this._contentWritten = 0;
        this._contentLength = -3;
        this._date = null;
        this._content = null;
        this._method = null;
    }

    public void returnBuffers() {
        Buffer buffer = this._buffer;
        if (buffer != null && buffer.length() == 0) {
            this._buffers.returnBuffer(this._buffer);
            this._buffer = null;
        }
        Buffer buffer2 = this._header;
        if (buffer2 != null && buffer2.length() == 0) {
            this._buffers.returnBuffer(this._header);
            this._header = null;
        }
    }

    public void resetBuffer() {
        if (this._state < 3) {
            this._last = false;
            this._persistent = null;
            this._contentWritten = 0;
            this._contentLength = -3;
            this._content = null;
            Buffer buffer = this._buffer;
            if (buffer != null) {
                buffer.clear();
                return;
            }
            return;
        }
        throw new IllegalStateException("Flushed");
    }

    public int getContentBufferSize() {
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        return this._buffer.capacity();
    }

    public void increaseContentBufferSize(int i) {
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        if (i > this._buffer.capacity()) {
            Buffer buffer = this._buffers.getBuffer(i);
            buffer.put(this._buffer);
            this._buffers.returnBuffer(this._buffer);
            this._buffer = buffer;
        }
    }

    public Buffer getUncheckedBuffer() {
        return this._buffer;
    }

    public boolean getSendServerVersion() {
        return this._sendServerVersion;
    }

    public void setSendServerVersion(boolean z) {
        this._sendServerVersion = z;
    }

    public int getState() {
        return this._state;
    }

    public boolean isState(int i) {
        return this._state == i;
    }

    public boolean isComplete() {
        return this._state == 4;
    }

    public boolean isIdle() {
        return this._state == 0 && this._method == null && this._status == 0;
    }

    public boolean isCommitted() {
        return this._state != 0;
    }

    public boolean isHead() {
        return this._head;
    }

    public void setContentLength(long j) {
        if (j < 0) {
            this._contentLength = -3;
        } else {
            this._contentLength = j;
        }
    }

    public void setHead(boolean z) {
        this._head = z;
    }

    public boolean isPersistent() {
        Boolean bool = this._persistent;
        if (bool != null) {
            return bool.booleanValue();
        }
        return isRequest() || this._version > 10;
    }

    public void setPersistent(boolean z) {
        this._persistent = Boolean.valueOf(z);
    }

    public void setVersion(int i) {
        if (this._state == 0) {
            this._version = i;
            if (this._version == 9 && this._method != null) {
                this._noContent = true;
                return;
            }
            return;
        }
        throw new IllegalStateException("STATE!=START " + this._state);
    }

    public int getVersion() {
        return this._version;
    }

    public void setDate(Buffer buffer) {
        this._date = buffer;
    }

    public void setRequest(String str, String str2) {
        if (str == null || "GET".equals(str)) {
            this._method = HttpMethods.GET_BUFFER;
        } else {
            this._method = HttpMethods.CACHE.lookup(str);
        }
        this._uri = str2;
        if (this._version == 9) {
            this._noContent = true;
        }
    }

    public void setResponse(int i, String str) {
        if (this._state == 0) {
            this._method = null;
            this._status = i;
            if (str != null) {
                int length = str.length();
                if (length > 1024) {
                    length = 1024;
                }
                this._reason = new ByteArrayBuffer(length);
                for (int i2 = 0; i2 < length; i2++) {
                    char charAt = str.charAt(i2);
                    if (charAt == 13 || charAt == 10) {
                        this._reason.put((byte) HttpTokens.SPACE);
                    } else {
                        this._reason.put((byte) charAt);
                    }
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("STATE!=START");
    }

    /* access modifiers changed from: package-private */
    public void uncheckedAddContent(int i) {
        this._buffer.put((byte) i);
    }

    public void completeUncheckedAddContent() {
        if (this._noContent) {
            Buffer buffer = this._buffer;
            if (buffer != null) {
                buffer.clear();
                return;
            }
            return;
        }
        this._contentWritten += (long) this._buffer.length();
        if (this._head) {
            this._buffer.clear();
        }
    }

    public boolean isBufferFull() {
        Buffer buffer = this._buffer;
        if (buffer == null || buffer.space() != 0) {
            Buffer buffer2 = this._content;
            if (buffer2 == null || buffer2.length() <= 0) {
                return false;
            }
            return true;
        }
        if (this._buffer.length() == 0 && !this._buffer.isImmutable()) {
            this._buffer.compact();
        }
        if (this._buffer.space() == 0) {
            return true;
        }
        return false;
    }

    public boolean isWritten() {
        return this._contentWritten > 0;
    }

    public boolean isAllContentWritten() {
        long j = this._contentLength;
        return j >= 0 && this._contentWritten >= j;
    }

    public void complete() throws IOException {
        if (this._state != 0) {
            long j = this._contentLength;
            if (j >= 0 && j != this._contentWritten && !this._head) {
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("ContentLength written==" + this._contentWritten + " != contentLength==" + this._contentLength, new Object[0]);
                }
                this._persistent = false;
                return;
            }
            return;
        }
        throw new IllegalStateException("State==HEADER");
    }

    public void flush(long j) throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = j + currentTimeMillis;
        Buffer buffer = this._content;
        Buffer buffer2 = this._buffer;
        if ((buffer != null && buffer.length() > 0) || ((buffer2 != null && buffer2.length() > 0) || isBufferFull())) {
            flushBuffer();
            while (currentTimeMillis < j2) {
                if (((buffer != null && buffer.length() > 0) || (buffer2 != null && buffer2.length() > 0)) && this._endp.isOpen() && !this._endp.isOutputShutdown()) {
                    blockForOutput(j2 - currentTimeMillis);
                    currentTimeMillis = System.currentTimeMillis();
                } else {
                    return;
                }
            }
        }
    }

    public void sendError(int i, String str, String str2, boolean z) throws IOException {
        if (z) {
            this._persistent = false;
        }
        if (isCommitted()) {
            LOG.debug("sendError on committed: {} {}", Integer.valueOf(i), str);
            return;
        }
        LOG.debug("sendError: {} {}", Integer.valueOf(i), str);
        setResponse(i, str);
        if (str2 != null) {
            completeHeader((HttpFields) null, false);
            addContent(new View(new ByteArrayBuffer(str2)), true);
        } else if (i >= 400) {
            completeHeader((HttpFields) null, false);
            StringBuilder sb = new StringBuilder();
            sb.append("Error: ");
            if (str == null) {
                str = "" + i;
            }
            sb.append(str);
            addContent(new View(new ByteArrayBuffer(sb.toString())), true);
        } else {
            completeHeader((HttpFields) null, true);
        }
        complete();
    }

    public long getContentWritten() {
        return this._contentWritten;
    }

    public void blockForOutput(long j) throws IOException {
        if (this._endp.isBlocking()) {
            try {
                flushBuffer();
            } catch (IOException e) {
                this._endp.close();
                throw e;
            }
        } else if (this._endp.blockWritable(j)) {
            flushBuffer();
        } else {
            this._endp.close();
            throw new EofException("timeout");
        }
    }
}
