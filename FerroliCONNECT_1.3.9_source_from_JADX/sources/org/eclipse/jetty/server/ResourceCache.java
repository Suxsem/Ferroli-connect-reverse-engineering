package org.eclipse.jetty.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.View;
import org.eclipse.jetty.p119io.nio.DirectNIOBuffer;
import org.eclipse.jetty.p119io.nio.IndirectNIOBuffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceFactory;

public class ResourceCache {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) ResourceCache.class);
    /* access modifiers changed from: private */
    public final ConcurrentMap<String, Content> _cache;
    /* access modifiers changed from: private */
    public final AtomicInteger _cachedFiles;
    /* access modifiers changed from: private */
    public final AtomicInteger _cachedSize;
    /* access modifiers changed from: private */
    public final boolean _etags;
    private final ResourceFactory _factory;
    private int _maxCacheSize = 33554432;
    private int _maxCachedFileSize = 4194304;
    private int _maxCachedFiles = 2048;
    /* access modifiers changed from: private */
    public final MimeTypes _mimeTypes;
    private final ResourceCache _parent;
    private boolean _useFileMappedBuffer = true;

    public ResourceCache(ResourceCache resourceCache, ResourceFactory resourceFactory, MimeTypes mimeTypes, boolean z, boolean z2) {
        this._factory = resourceFactory;
        this._cache = new ConcurrentHashMap();
        this._cachedSize = new AtomicInteger();
        this._cachedFiles = new AtomicInteger();
        this._mimeTypes = mimeTypes;
        this._parent = resourceCache;
        this._etags = z2;
        this._useFileMappedBuffer = z;
    }

    public int getCachedSize() {
        return this._cachedSize.get();
    }

    public int getCachedFiles() {
        return this._cachedFiles.get();
    }

    public int getMaxCachedFileSize() {
        return this._maxCachedFileSize;
    }

    public void setMaxCachedFileSize(int i) {
        this._maxCachedFileSize = i;
        shrinkCache();
    }

    public int getMaxCacheSize() {
        return this._maxCacheSize;
    }

    public void setMaxCacheSize(int i) {
        this._maxCacheSize = i;
        shrinkCache();
    }

    public int getMaxCachedFiles() {
        return this._maxCachedFiles;
    }

    public void setMaxCachedFiles(int i) {
        this._maxCachedFiles = i;
        shrinkCache();
    }

    public boolean isUseFileMappedBuffer() {
        return this._useFileMappedBuffer;
    }

    public void setUseFileMappedBuffer(boolean z) {
        this._useFileMappedBuffer = z;
    }

    public void flushCache() {
        if (this._cache == null) {
            return;
        }
        while (this._cache.size() > 0) {
            for (String remove : this._cache.keySet()) {
                Content content = (Content) this._cache.remove(remove);
                if (content != null) {
                    content.invalidate();
                }
            }
        }
    }

    public HttpContent lookup(String str) throws IOException {
        HttpContent lookup;
        Content content = (Content) this._cache.get(str);
        if (content != null && content.isValid()) {
            return content;
        }
        HttpContent load = load(str, this._factory.getResource(str));
        if (load != null) {
            return load;
        }
        ResourceCache resourceCache = this._parent;
        if (resourceCache == null || (lookup = resourceCache.lookup(str)) == null) {
            return null;
        }
        return lookup;
    }

    /* access modifiers changed from: protected */
    public boolean isCacheable(Resource resource) {
        long length = resource.length();
        return length > 0 && length < ((long) this._maxCachedFileSize) && length < ((long) this._maxCacheSize);
    }

    private HttpContent load(String str, Resource resource) throws IOException {
        if (resource == null || !resource.exists()) {
            return null;
        }
        if (resource.isDirectory() || !isCacheable(resource)) {
            return new HttpContent.ResourceAsHttpContent(resource, this._mimeTypes.getMimeByExtension(resource.toString()), getMaxCachedFileSize(), this._etags);
        }
        Content content = new Content(str, resource);
        shrinkCache();
        Content putIfAbsent = this._cache.putIfAbsent(str, content);
        if (putIfAbsent == null) {
            return content;
        }
        content.invalidate();
        return putIfAbsent;
    }

    private void shrinkCache() {
        while (this._cache.size() > 0) {
            if (this._cachedFiles.get() > this._maxCachedFiles || this._cachedSize.get() > this._maxCacheSize) {
                TreeSet<Content> treeSet = new TreeSet<>(new Comparator<Content>() {
                    public int compare(Content content, Content content2) {
                        if (content._lastAccessed < content2._lastAccessed) {
                            return -1;
                        }
                        if (content._lastAccessed > content2._lastAccessed) {
                            return 1;
                        }
                        if (content._length < content2._length) {
                            return -1;
                        }
                        return content._key.compareTo(content2._key);
                    }
                });
                for (Content add : this._cache.values()) {
                    treeSet.add(add);
                }
                for (Content content : treeSet) {
                    if (this._cachedFiles.get() <= this._maxCachedFiles && this._cachedSize.get() <= this._maxCacheSize) {
                        break;
                    } else if (content == this._cache.remove(content.getKey())) {
                        content.invalidate();
                    }
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public Buffer getIndirectBuffer(Resource resource) {
        try {
            int length = (int) resource.length();
            if (length < 0) {
                Logger logger = LOG;
                logger.warn("invalid resource: " + String.valueOf(resource) + " " + length, new Object[0]);
                return null;
            }
            IndirectNIOBuffer indirectNIOBuffer = new IndirectNIOBuffer(length);
            InputStream inputStream = resource.getInputStream();
            indirectNIOBuffer.readFrom(inputStream, length);
            inputStream.close();
            return indirectNIOBuffer;
        } catch (IOException e) {
            LOG.warn(e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Buffer getDirectBuffer(Resource resource) {
        try {
            if (this._useFileMappedBuffer && resource.getFile() != null) {
                return new DirectNIOBuffer(resource.getFile());
            }
            int length = (int) resource.length();
            if (length < 0) {
                Logger logger = LOG;
                logger.warn("invalid resource: " + String.valueOf(resource) + " " + length, new Object[0]);
                return null;
            }
            DirectNIOBuffer directNIOBuffer = new DirectNIOBuffer(length);
            InputStream inputStream = resource.getInputStream();
            directNIOBuffer.readFrom(inputStream, length);
            inputStream.close();
            return directNIOBuffer;
        } catch (IOException e) {
            LOG.warn(e);
            return null;
        }
    }

    public String toString() {
        return "ResourceCache[" + this._parent + "," + this._factory + "]@" + hashCode();
    }

    public class Content implements HttpContent {
        final Buffer _contentType;
        AtomicReference<Buffer> _directBuffer = new AtomicReference<>();
        final Buffer _etagBuffer;
        AtomicReference<Buffer> _indirectBuffer = new AtomicReference<>();
        final String _key;
        volatile long _lastAccessed;
        final long _lastModified;
        final Buffer _lastModifiedBytes;
        final int _length;
        final Resource _resource;

        public boolean isMiss() {
            return false;
        }

        public void release() {
        }

        Content(String str, Resource resource) {
            this._key = str;
            this._resource = resource;
            this._contentType = ResourceCache.this._mimeTypes.getMimeByExtension(this._resource.toString());
            boolean exists = resource.exists();
            this._lastModified = exists ? resource.lastModified() : -1;
            long j = this._lastModified;
            ByteArrayBuffer byteArrayBuffer = null;
            this._lastModifiedBytes = j < 0 ? null : new ByteArrayBuffer(HttpFields.formatDate(j));
            this._length = exists ? (int) resource.length() : 0;
            ResourceCache.this._cachedSize.addAndGet(this._length);
            ResourceCache.this._cachedFiles.incrementAndGet();
            this._lastAccessed = System.currentTimeMillis();
            this._etagBuffer = ResourceCache.this._etags ? new ByteArrayBuffer(resource.getWeakETag()) : byteArrayBuffer;
        }

        public String getKey() {
            return this._key;
        }

        public boolean isCached() {
            return this._key != null;
        }

        public Resource getResource() {
            return this._resource;
        }

        public Buffer getETag() {
            return this._etagBuffer;
        }

        /* access modifiers changed from: package-private */
        public boolean isValid() {
            if (this._lastModified == this._resource.lastModified() && ((long) this._length) == this._resource.length()) {
                this._lastAccessed = System.currentTimeMillis();
                return true;
            } else if (this != ResourceCache.this._cache.remove(this._key)) {
                return false;
            } else {
                invalidate();
                return false;
            }
        }

        /* access modifiers changed from: protected */
        public void invalidate() {
            ResourceCache.this._cachedSize.addAndGet(-this._length);
            ResourceCache.this._cachedFiles.decrementAndGet();
            this._resource.release();
        }

        public Buffer getLastModified() {
            return this._lastModifiedBytes;
        }

        public Buffer getContentType() {
            return this._contentType;
        }

        public Buffer getIndirectBuffer() {
            Buffer buffer = this._indirectBuffer.get();
            if (buffer == null) {
                Buffer indirectBuffer = ResourceCache.this.getIndirectBuffer(this._resource);
                if (indirectBuffer == null) {
                    Logger access$500 = ResourceCache.LOG;
                    access$500.warn("Could not load " + this, new Object[0]);
                } else {
                    buffer = this._indirectBuffer.compareAndSet((Object) null, indirectBuffer) ? indirectBuffer : this._indirectBuffer.get();
                }
            }
            if (buffer == null) {
                return null;
            }
            return new View(buffer);
        }

        public Buffer getDirectBuffer() {
            Buffer buffer = this._directBuffer.get();
            if (buffer == null) {
                Buffer directBuffer = ResourceCache.this.getDirectBuffer(this._resource);
                if (directBuffer == null) {
                    Logger access$500 = ResourceCache.LOG;
                    access$500.warn("Could not load " + this, new Object[0]);
                } else {
                    buffer = this._directBuffer.compareAndSet((Object) null, directBuffer) ? directBuffer : this._directBuffer.get();
                }
            }
            if (buffer == null) {
                return null;
            }
            return new View(buffer);
        }

        public long getContentLength() {
            return (long) this._length;
        }

        public InputStream getInputStream() throws IOException {
            Buffer indirectBuffer = getIndirectBuffer();
            if (indirectBuffer == null || indirectBuffer.array() == null) {
                return this._resource.getInputStream();
            }
            return new ByteArrayInputStream(indirectBuffer.array(), indirectBuffer.getIndex(), indirectBuffer.length());
        }

        public String toString() {
            Resource resource = this._resource;
            return String.format("%s %s %d %s %s", new Object[]{resource, Boolean.valueOf(resource.exists()), Long.valueOf(this._resource.lastModified()), this._contentType, this._lastModifiedBytes});
        }
    }
}
