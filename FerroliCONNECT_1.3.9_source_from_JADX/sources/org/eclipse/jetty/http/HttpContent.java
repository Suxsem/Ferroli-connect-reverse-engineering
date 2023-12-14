package org.eclipse.jetty.http;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public interface HttpContent {
    long getContentLength();

    Buffer getContentType();

    Buffer getDirectBuffer();

    Buffer getETag();

    Buffer getIndirectBuffer();

    InputStream getInputStream() throws IOException;

    Buffer getLastModified();

    Resource getResource();

    void release();

    public static class ResourceAsHttpContent implements HttpContent {
        private static final Logger LOG = Log.getLogger((Class<?>) ResourceAsHttpContent.class);
        final Buffer _etag;
        final int _maxBuffer;
        final Buffer _mimeType;
        final Resource _resource;

        public Buffer getDirectBuffer() {
            return null;
        }

        public Buffer getLastModified() {
            return null;
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer) {
            this(resource, buffer, -1, false);
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer, int i) {
            this(resource, buffer, i, false);
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer, boolean z) {
            this(resource, buffer, -1, z);
        }

        public ResourceAsHttpContent(Resource resource, Buffer buffer, int i, boolean z) {
            this._resource = resource;
            this._mimeType = buffer;
            this._maxBuffer = i;
            this._etag = z ? new ByteArrayBuffer(resource.getWeakETag()) : null;
        }

        public Buffer getContentType() {
            return this._mimeType;
        }

        public Buffer getETag() {
            return this._etag;
        }

        /* JADX WARNING: type inference failed for: r1v0, types: [org.eclipse.jetty.io.Buffer, java.io.InputStream] */
        public Buffer getIndirectBuffer() {
            ? r1 = 0;
            try {
                if (this._resource.length() > 0) {
                    if (((long) this._maxBuffer) >= this._resource.length()) {
                        ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer((int) this._resource.length());
                        InputStream inputStream = this._resource.getInputStream();
                        byteArrayBuffer.readFrom(inputStream, (int) this._resource.length());
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                LOG.warn("Couldn't close inputStream. Possible file handle leak", (Throwable) e);
                            }
                        }
                        return byteArrayBuffer;
                    }
                }
                return r1;
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            } catch (Throwable th) {
                if (r1 != 0) {
                    try {
                        r1.close();
                    } catch (IOException e3) {
                        LOG.warn("Couldn't close inputStream. Possible file handle leak", (Throwable) e3);
                    }
                }
                throw th;
            }
        }

        public long getContentLength() {
            return this._resource.length();
        }

        public InputStream getInputStream() throws IOException {
            return this._resource.getInputStream();
        }

        public Resource getResource() {
            return this._resource;
        }

        public void release() {
            this._resource.release();
        }
    }
}
