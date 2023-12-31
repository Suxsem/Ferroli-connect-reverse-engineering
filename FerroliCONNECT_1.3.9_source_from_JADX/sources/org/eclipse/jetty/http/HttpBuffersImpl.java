package org.eclipse.jetty.http;

import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.BuffersFactory;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

public class HttpBuffersImpl extends AbstractLifeCycle implements HttpBuffers {
    private int _maxBuffers = 1024;
    private int _requestBufferSize = 16384;
    private Buffers.Type _requestBufferType = Buffers.Type.BYTE_ARRAY;
    private Buffers _requestBuffers;
    private int _requestHeaderSize = 6144;
    private Buffers.Type _requestHeaderType = Buffers.Type.BYTE_ARRAY;
    private int _responseBufferSize = 32768;
    private Buffers.Type _responseBufferType = Buffers.Type.BYTE_ARRAY;
    private Buffers _responseBuffers;
    private int _responseHeaderSize = 6144;
    private Buffers.Type _responseHeaderType = Buffers.Type.BYTE_ARRAY;

    public int getRequestBufferSize() {
        return this._requestBufferSize;
    }

    public void setRequestBufferSize(int i) {
        this._requestBufferSize = i;
    }

    public int getRequestHeaderSize() {
        return this._requestHeaderSize;
    }

    public void setRequestHeaderSize(int i) {
        this._requestHeaderSize = i;
    }

    public int getResponseBufferSize() {
        return this._responseBufferSize;
    }

    public void setResponseBufferSize(int i) {
        this._responseBufferSize = i;
    }

    public int getResponseHeaderSize() {
        return this._responseHeaderSize;
    }

    public void setResponseHeaderSize(int i) {
        this._responseHeaderSize = i;
    }

    public Buffers.Type getRequestBufferType() {
        return this._requestBufferType;
    }

    public void setRequestBufferType(Buffers.Type type) {
        this._requestBufferType = type;
    }

    public Buffers.Type getRequestHeaderType() {
        return this._requestHeaderType;
    }

    public void setRequestHeaderType(Buffers.Type type) {
        this._requestHeaderType = type;
    }

    public Buffers.Type getResponseBufferType() {
        return this._responseBufferType;
    }

    public void setResponseBufferType(Buffers.Type type) {
        this._responseBufferType = type;
    }

    public Buffers.Type getResponseHeaderType() {
        return this._responseHeaderType;
    }

    public void setResponseHeaderType(Buffers.Type type) {
        this._responseHeaderType = type;
    }

    public void setRequestBuffers(Buffers buffers) {
        this._requestBuffers = buffers;
    }

    public void setResponseBuffers(Buffers buffers) {
        this._responseBuffers = buffers;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        Buffers.Type type = this._requestHeaderType;
        int i = this._requestHeaderSize;
        Buffers.Type type2 = this._requestBufferType;
        this._requestBuffers = BuffersFactory.newBuffers(type, i, type2, this._requestBufferSize, type2, getMaxBuffers());
        Buffers.Type type3 = this._responseHeaderType;
        int i2 = this._responseHeaderSize;
        Buffers.Type type4 = this._responseBufferType;
        this._responseBuffers = BuffersFactory.newBuffers(type3, i2, type4, this._responseBufferSize, type4, getMaxBuffers());
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        this._requestBuffers = null;
        this._responseBuffers = null;
    }

    public Buffers getRequestBuffers() {
        return this._requestBuffers;
    }

    public Buffers getResponseBuffers() {
        return this._responseBuffers;
    }

    public void setMaxBuffers(int i) {
        this._maxBuffers = i;
    }

    public int getMaxBuffers() {
        return this._maxBuffers;
    }

    public String toString() {
        return this._requestBuffers + "/" + this._responseBuffers;
    }
}
