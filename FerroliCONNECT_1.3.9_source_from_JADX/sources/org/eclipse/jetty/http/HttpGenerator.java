package org.eclipse.jetty.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.BufferCache;
import org.eclipse.jetty.p119io.BufferUtil;
import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.EofException;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class HttpGenerator extends AbstractGenerator {
    private static final int CHUNK_SPACE = 12;
    private static final byte[] CONNECTION_ = StringUtil.getBytes("Connection: ");
    private static final byte[] CONNECTION_CLOSE = StringUtil.getBytes("Connection: close\r\n");
    private static final byte[] CONNECTION_KEEP_ALIVE = StringUtil.getBytes("Connection: keep-alive\r\n");
    private static final byte[] CONTENT_LENGTH_0 = StringUtil.getBytes("Content-Length: 0\r\n");
    private static final byte[] CRLF = StringUtil.getBytes("\r\n");
    private static final byte[] LAST_CHUNK = {48, 13, 10, 13, 10};
    private static final Logger LOG = Log.getLogger((Class<?>) HttpGenerator.class);
    private static byte[] SERVER = StringUtil.getBytes("Server: Jetty(7.0.x)\r\n");
    private static final byte[] TRANSFER_ENCODING_CHUNKED = StringUtil.getBytes("Transfer-Encoding: chunked\r\n");
    private static final Status[] __status = new Status[508];
    private boolean _bufferChunked = false;
    protected boolean _bypass = false;
    private boolean _needCRLF = false;
    private boolean _needEOC = false;

    static {
        int length = HttpVersions.HTTP_1_1_BUFFER.length();
        for (int i = 0; i < __status.length; i++) {
            HttpStatus.Code code = HttpStatus.getCode(i);
            if (code != null) {
                String message = code.getMessage();
                int i2 = length + 5;
                byte[] bArr = new byte[(message.length() + i2 + 2)];
                HttpVersions.HTTP_1_1_BUFFER.peek(0, bArr, 0, length);
                bArr[length + 0] = HttpTokens.SPACE;
                bArr[length + 1] = (byte) ((i / 100) + 48);
                bArr[length + 2] = (byte) (((i % 100) / 10) + 48);
                bArr[length + 3] = (byte) ((i % 10) + 48);
                bArr[length + 4] = HttpTokens.SPACE;
                for (int i3 = 0; i3 < message.length(); i3++) {
                    bArr[i2 + i3] = (byte) message.charAt(i3);
                }
                bArr[message.length() + i2] = 13;
                bArr[length + 6 + message.length()] = 10;
                __status[i] = new Status();
                __status[i]._reason = new ByteArrayBuffer(bArr, i2, (bArr.length - length) - 7, 0);
                __status[i]._schemeCode = new ByteArrayBuffer(bArr, 0, i2, 0);
                __status[i]._responseLine = new ByteArrayBuffer(bArr, 0, bArr.length, 0);
            }
        }
    }

    private static class Status {
        Buffer _reason;
        Buffer _responseLine;
        Buffer _schemeCode;

        private Status() {
        }
    }

    public static Buffer getReasonBuffer(int i) {
        Status[] statusArr = __status;
        Status status = i < statusArr.length ? statusArr[i] : null;
        if (status != null) {
            return status._reason;
        }
        return null;
    }

    public static void setServerVersion(String str) {
        SERVER = StringUtil.getBytes("Server: Jetty(" + str + ")\r\n");
    }

    public HttpGenerator(Buffers buffers, EndPoint endPoint) {
        super(buffers, endPoint);
    }

    public void reset() {
        if (this._persistent != null && !this._persistent.booleanValue() && this._endp != null && !this._endp.isOutputShutdown()) {
            try {
                this._endp.shutdownOutput();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
        super.reset();
        if (this._buffer != null) {
            this._buffer.clear();
        }
        if (this._header != null) {
            this._header.clear();
        }
        if (this._content != null) {
            this._content = null;
        }
        this._bypass = false;
        this._needCRLF = false;
        this._needEOC = false;
        this._bufferChunked = false;
        this._method = null;
        this._uri = null;
        this._noContent = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addContent(org.eclipse.jetty.p119io.Buffer r7, boolean r8) throws java.io.IOException {
        /*
            r6 = this;
            boolean r0 = r6._noContent
            if (r0 != 0) goto L_0x0109
            boolean r0 = r6._last
            r1 = 1
            if (r0 != 0) goto L_0x00f9
            int r0 = r6._state
            r2 = 4
            if (r0 != r2) goto L_0x0010
            goto L_0x00f9
        L_0x0010:
            r6._last = r8
            org.eclipse.jetty.io.Buffer r8 = r6._content
            if (r8 == 0) goto L_0x001e
            org.eclipse.jetty.io.Buffer r8 = r6._content
            int r8 = r8.length()
            if (r8 > 0) goto L_0x0022
        L_0x001e:
            boolean r8 = r6._bufferChunked
            if (r8 == 0) goto L_0x008a
        L_0x0022:
            org.eclipse.jetty.io.EndPoint r8 = r6._endp
            boolean r8 = r8.isOutputShutdown()
            if (r8 != 0) goto L_0x0084
            r6.flushBuffer()
            org.eclipse.jetty.io.Buffer r8 = r6._content
            if (r8 == 0) goto L_0x008a
            org.eclipse.jetty.io.Buffer r8 = r6._content
            int r8 = r8.length()
            if (r8 <= 0) goto L_0x008a
            boolean r8 = r6._bufferChunked
            if (r8 == 0) goto L_0x006a
            org.eclipse.jetty.io.Buffers r8 = r6._buffers
            org.eclipse.jetty.io.Buffer r0 = r6._content
            int r0 = r0.length()
            int r0 = r0 + 12
            int r2 = r7.length()
            int r0 = r0 + r2
            org.eclipse.jetty.io.Buffer r8 = r8.getBuffer(r0)
            org.eclipse.jetty.io.Buffer r0 = r6._content
            r8.put((org.eclipse.jetty.p119io.Buffer) r0)
            byte[] r0 = org.eclipse.jetty.http.HttpTokens.CRLF
            r8.put((byte[]) r0)
            int r0 = r7.length()
            org.eclipse.jetty.p119io.BufferUtil.putHexInt(r8, r0)
            byte[] r0 = org.eclipse.jetty.http.HttpTokens.CRLF
            r8.put((byte[]) r0)
            r8.put((org.eclipse.jetty.p119io.Buffer) r7)
            goto L_0x008b
        L_0x006a:
            org.eclipse.jetty.io.Buffers r8 = r6._buffers
            org.eclipse.jetty.io.Buffer r0 = r6._content
            int r0 = r0.length()
            int r2 = r7.length()
            int r0 = r0 + r2
            org.eclipse.jetty.io.Buffer r8 = r8.getBuffer(r0)
            org.eclipse.jetty.io.Buffer r0 = r6._content
            r8.put((org.eclipse.jetty.p119io.Buffer) r0)
            r8.put((org.eclipse.jetty.p119io.Buffer) r7)
            goto L_0x008b
        L_0x0084:
            org.eclipse.jetty.io.EofException r7 = new org.eclipse.jetty.io.EofException
            r7.<init>()
            throw r7
        L_0x008a:
            r8 = r7
        L_0x008b:
            r6._content = r8
            long r2 = r6._contentWritten
            int r7 = r8.length()
            long r4 = (long) r7
            long r2 = r2 + r4
            r6._contentWritten = r2
            boolean r7 = r6._head
            r0 = 0
            if (r7 == 0) goto L_0x00a2
            r8.clear()
            r6._content = r0
            goto L_0x00f8
        L_0x00a2:
            org.eclipse.jetty.io.EndPoint r7 = r6._endp
            if (r7 == 0) goto L_0x00d1
            org.eclipse.jetty.io.Buffer r7 = r6._buffer
            if (r7 == 0) goto L_0x00b2
            org.eclipse.jetty.io.Buffer r7 = r6._buffer
            int r7 = r7.length()
            if (r7 != 0) goto L_0x00d1
        L_0x00b2:
            org.eclipse.jetty.io.Buffer r7 = r6._content
            int r7 = r7.length()
            if (r7 <= 0) goto L_0x00d1
            boolean r7 = r6._last
            if (r7 != 0) goto L_0x00ce
            boolean r7 = r6.isCommitted()
            if (r7 == 0) goto L_0x00d1
            org.eclipse.jetty.io.Buffer r7 = r6._content
            int r7 = r7.length()
            r8 = 1024(0x400, float:1.435E-42)
            if (r7 <= r8) goto L_0x00d1
        L_0x00ce:
            r6._bypass = r1
            goto L_0x00f8
        L_0x00d1:
            boolean r7 = r6._bufferChunked
            if (r7 != 0) goto L_0x00f8
            org.eclipse.jetty.io.Buffer r7 = r6._buffer
            if (r7 != 0) goto L_0x00e1
            org.eclipse.jetty.io.Buffers r7 = r6._buffers
            org.eclipse.jetty.io.Buffer r7 = r7.getBuffer()
            r6._buffer = r7
        L_0x00e1:
            org.eclipse.jetty.io.Buffer r7 = r6._buffer
            org.eclipse.jetty.io.Buffer r8 = r6._content
            int r7 = r7.put((org.eclipse.jetty.p119io.Buffer) r8)
            org.eclipse.jetty.io.Buffer r8 = r6._content
            r8.skip(r7)
            org.eclipse.jetty.io.Buffer r7 = r6._content
            int r7 = r7.length()
            if (r7 != 0) goto L_0x00f8
            r6._content = r0
        L_0x00f8:
            return
        L_0x00f9:
            org.eclipse.jetty.util.log.Logger r8 = LOG
            java.lang.Object[] r0 = new java.lang.Object[r1]
            r1 = 0
            r0[r1] = r7
            java.lang.String r1 = "Ignoring extra content {}"
            r8.warn((java.lang.String) r1, (java.lang.Object[]) r0)
            r7.clear()
            return
        L_0x0109:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "NO CONTENT"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpGenerator.addContent(org.eclipse.jetty.io.Buffer, boolean):void");
    }

    public void sendResponse(Buffer buffer) throws IOException {
        if (this._noContent || this._state != 0 || ((this._content != null && this._content.length() > 0) || this._bufferChunked || this._head)) {
            throw new IllegalStateException();
        }
        this._last = true;
        this._content = buffer;
        this._bypass = true;
        this._state = 3;
        long length = (long) buffer.length();
        this._contentWritten = length;
        this._contentLength = length;
    }

    public int prepareUncheckedAddContent() throws IOException {
        if (this._noContent || this._last || this._state == 4) {
            return -1;
        }
        Buffer buffer = this._content;
        if ((buffer != null && buffer.length() > 0) || this._bufferChunked) {
            flushBuffer();
            if ((buffer != null && buffer.length() > 0) || this._bufferChunked) {
                throw new IllegalStateException("FULL");
            }
        }
        if (this._buffer == null) {
            this._buffer = this._buffers.getBuffer();
        }
        this._contentWritten -= (long) this._buffer.length();
        if (this._head) {
            return Integer.MAX_VALUE;
        }
        return this._buffer.space() - (this._contentLength == -2 ? 12 : 0);
    }

    public boolean isBufferFull() {
        return super.isBufferFull() || this._bufferChunked || this._bypass || (this._contentLength == -2 && this._buffer != null && this._buffer.space() < 12);
    }

    public void send1xx(int i) throws IOException {
        if (this._state == 0) {
            if (i < 100 || i > 199) {
                throw new IllegalArgumentException("!1xx");
            }
            Status status = __status[i];
            if (status != null) {
                if (this._header == null) {
                    this._header = this._buffers.getHeader();
                }
                this._header.put(status._responseLine);
                this._header.put(HttpTokens.CRLF);
                while (this._header.length() > 0) {
                    try {
                        int flush = this._endp.flush(this._header);
                        if (flush < 0) {
                            throw new EofException();
                        } else if (flush == 0) {
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        LOG.debug(e);
                        throw new InterruptedIOException(e.toString());
                    }
                }
                return;
            }
            throw new IllegalArgumentException(i + "?");
        }
    }

    public boolean isRequest() {
        return this._method != null;
    }

    public boolean isResponse() {
        return this._method == null;
    }

    public void completeHeader(HttpFields httpFields, boolean z) throws IOException {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        HttpFields.Field field;
        HttpFields.Field field2;
        long j;
        HttpFields httpFields2 = httpFields;
        if (this._state == 0) {
            if (isResponse() && this._status == 0) {
                throw new EofException();
            } else if (!this._last || z) {
                this._last |= z;
                if (this._header == null) {
                    this._header = this._buffers.getHeader();
                }
                try {
                    int i = 48;
                    StringBuilder sb = null;
                    boolean z6 = false;
                    if (isRequest()) {
                        this._persistent = true;
                        if (this._version == 9) {
                            this._contentLength = 0;
                            this._header.put(this._method);
                            this._header.put((byte) HttpTokens.SPACE);
                            this._header.put(this._uri.getBytes("UTF-8"));
                            this._header.put(HttpTokens.CRLF);
                            this._state = 3;
                            this._noContent = true;
                            return;
                        }
                        this._header.put(this._method);
                        this._header.put((byte) HttpTokens.SPACE);
                        this._header.put(this._uri.getBytes("UTF-8"));
                        this._header.put((byte) HttpTokens.SPACE);
                        this._header.put(this._version == 10 ? HttpVersions.HTTP_1_0_BUFFER : HttpVersions.HTTP_1_1_BUFFER);
                        this._header.put(HttpTokens.CRLF);
                    } else if (this._version == 9) {
                        this._persistent = false;
                        this._contentLength = -1;
                        this._state = 2;
                        return;
                    } else {
                        if (this._persistent == null) {
                            this._persistent = Boolean.valueOf(this._version > 10);
                        }
                        Status status = this._status < __status.length ? __status[this._status] : null;
                        if (status == null) {
                            this._header.put(HttpVersions.HTTP_1_1_BUFFER);
                            this._header.put((byte) HttpTokens.SPACE);
                            this._header.put((byte) ((this._status / 100) + 48));
                            this._header.put((byte) (((this._status % 100) / 10) + 48));
                            this._header.put((byte) ((this._status % 10) + 48));
                            this._header.put((byte) HttpTokens.SPACE);
                            if (this._reason == null) {
                                this._header.put((byte) ((this._status / 100) + 48));
                                this._header.put((byte) (((this._status % 100) / 10) + 48));
                                this._header.put((byte) ((this._status % 10) + 48));
                            } else {
                                this._header.put(this._reason);
                            }
                            this._header.put(HttpTokens.CRLF);
                        } else if (this._reason == null) {
                            this._header.put(status._responseLine);
                        } else {
                            this._header.put(status._schemeCode);
                            this._header.put(this._reason);
                            this._header.put(HttpTokens.CRLF);
                        }
                        if (this._status < 200 && this._status >= 100) {
                            this._noContent = true;
                            this._content = null;
                            if (this._buffer != null) {
                                this._buffer.clear();
                            }
                            if (this._status != 101) {
                                this._header.put(HttpTokens.CRLF);
                                this._state = 2;
                                return;
                            }
                        } else if (this._status == 204 || this._status == 304) {
                            this._noContent = true;
                            this._content = null;
                            if (this._buffer != null) {
                                this._buffer.clear();
                            }
                        }
                    }
                    if (this._status >= 200 && this._date != null) {
                        this._header.put(HttpHeaders.DATE_BUFFER);
                        this._header.put((byte) HttpTokens.COLON);
                        this._header.put((byte) HttpTokens.SPACE);
                        this._header.put(this._date);
                        this._header.put(CRLF);
                    }
                    if (httpFields2 != null) {
                        int size = httpFields.size();
                        HttpFields.Field field3 = null;
                        HttpFields.Field field4 = null;
                        int i2 = 0;
                        z5 = false;
                        z4 = false;
                        z3 = false;
                        z2 = false;
                        while (i2 < size) {
                            HttpFields.Field field5 = httpFields2.getField(i2);
                            if (field5 != null) {
                                int nameOrdinal = field5.getNameOrdinal();
                                if (nameOrdinal == 1) {
                                    if (isRequest()) {
                                        field5.putTo(this._header);
                                    }
                                    int valueOrdinal = field5.getValueOrdinal();
                                    if (valueOrdinal != -1) {
                                        if (valueOrdinal != 1) {
                                            if (valueOrdinal != 5) {
                                                if (valueOrdinal != 11) {
                                                    if (sb == null) {
                                                        sb = new StringBuilder();
                                                    } else {
                                                        sb.append(',');
                                                    }
                                                    sb.append(field5.getValue());
                                                } else if (isResponse()) {
                                                    field5.putTo(this._header);
                                                }
                                            } else if (this._version == 10) {
                                                if (isResponse()) {
                                                    this._persistent = true;
                                                }
                                                z4 = true;
                                            }
                                        }
                                        if (isResponse()) {
                                            this._persistent = false;
                                        }
                                        if (!this._persistent.booleanValue() && isResponse() && this._contentLength == -3) {
                                            this._contentLength = -1;
                                        }
                                        z3 = true;
                                    } else {
                                        String[] split = field5.getValue().split(",");
                                        int i3 = 0;
                                        while (split != null && i3 < split.length) {
                                            BufferCache.CachedBuffer cachedBuffer = HttpHeaderValues.CACHE.get(split[i3].trim());
                                            if (cachedBuffer != null) {
                                                int ordinal = cachedBuffer.getOrdinal();
                                                if (ordinal == 1) {
                                                    if (isResponse()) {
                                                        this._persistent = false;
                                                    }
                                                    if (!this._persistent.booleanValue() && isResponse() && this._contentLength == -3) {
                                                        this._contentLength = -1;
                                                    }
                                                    z4 = false;
                                                    z3 = true;
                                                } else if (ordinal != 5) {
                                                    if (sb == null) {
                                                        sb = new StringBuilder();
                                                    } else {
                                                        sb.append(',');
                                                    }
                                                    sb.append(split[i3]);
                                                } else if (this._version == 10) {
                                                    if (isResponse()) {
                                                        this._persistent = true;
                                                    }
                                                    z4 = true;
                                                }
                                            } else {
                                                if (sb == null) {
                                                    sb = new StringBuilder();
                                                } else {
                                                    sb.append(',');
                                                }
                                                sb.append(split[i3]);
                                            }
                                            i3++;
                                        }
                                    }
                                } else if (nameOrdinal != 5) {
                                    if (nameOrdinal == 12) {
                                        this._contentLength = field5.getLongValue();
                                        if (this._contentLength >= this._contentWritten) {
                                            if (!this._last || this._contentLength == this._contentWritten) {
                                                field3 = field5;
                                                field5.putTo(this._header);
                                            }
                                        }
                                        field3 = null;
                                        field5.putTo(this._header);
                                    } else if (nameOrdinal == 16) {
                                        if (BufferUtil.isPrefix(MimeTypes.MULTIPART_BYTERANGES_BUFFER, field5.getValueBuffer())) {
                                            this._contentLength = -4;
                                        }
                                        field5.putTo(this._header);
                                        z5 = true;
                                    } else if (nameOrdinal != i) {
                                        field5.putTo(this._header);
                                    } else if (getSendServerVersion()) {
                                        field5.putTo(this._header);
                                        z2 = true;
                                    }
                                } else if (this._version == 11) {
                                    field4 = field5;
                                }
                            }
                            i2++;
                            i = 48;
                        }
                        field = field3;
                        field2 = field4;
                    } else {
                        field2 = null;
                        sb = null;
                        field = null;
                        z5 = false;
                        z4 = false;
                        z3 = false;
                        z2 = false;
                    }
                    int i4 = (int) this._contentLength;
                    if (i4 != -3) {
                        if (i4 != -2) {
                            if (i4 == -1) {
                                this._persistent = Boolean.valueOf(isRequest());
                            } else if (i4 == 0) {
                                if (field == null && isResponse() && this._status >= 200 && this._status != 204 && this._status != 304) {
                                    this._header.put(CONTENT_LENGTH_0);
                                }
                            }
                        }
                    } else if (this._contentWritten == 0 && isResponse() && (this._status < 200 || this._status == 204 || this._status == 304)) {
                        this._contentLength = 0;
                    } else if (this._last) {
                        this._contentLength = this._contentWritten;
                        if (field == null && ((isResponse() || this._contentLength > 0 || z5) && !this._noContent)) {
                            this._header.put(HttpHeaders.CONTENT_LENGTH_BUFFER);
                            this._header.put((byte) HttpTokens.COLON);
                            this._header.put((byte) HttpTokens.SPACE);
                            BufferUtil.putDecLong(this._header, this._contentLength);
                            this._header.put(HttpTokens.CRLF);
                        }
                    } else {
                        if (this._persistent.booleanValue()) {
                            if (this._version >= 11) {
                                j = -2;
                                this._contentLength = j;
                                if (isRequest() && this._contentLength == -1) {
                                    this._contentLength = 0;
                                    this._noContent = true;
                                }
                            }
                        }
                        j = -1;
                        this._contentLength = j;
                        this._contentLength = 0;
                        this._noContent = true;
                    }
                    if (this._contentLength == -2) {
                        if (field2 == null || 2 == field2.getValueOrdinal()) {
                            this._header.put(TRANSFER_ENCODING_CHUNKED);
                        } else if (field2.getValue().endsWith(HttpHeaderValues.CHUNKED)) {
                            field2.putTo(this._header);
                        } else {
                            throw new IllegalArgumentException("BAD TE");
                        }
                    }
                    if (this._contentLength == -1) {
                        this._persistent = false;
                    } else {
                        z6 = z4;
                    }
                    if (isResponse()) {
                        if (!this._persistent.booleanValue() && (z3 || this._version > 10)) {
                            this._header.put(CONNECTION_CLOSE);
                            if (sb != null) {
                                this._header.setPutIndex(this._header.putIndex() - 2);
                                this._header.put((byte) 44);
                                this._header.put(sb.toString().getBytes());
                                this._header.put(CRLF);
                            }
                        } else if (z6) {
                            this._header.put(CONNECTION_KEEP_ALIVE);
                            if (sb != null) {
                                this._header.setPutIndex(this._header.putIndex() - 2);
                                this._header.put((byte) 44);
                                this._header.put(sb.toString().getBytes());
                                this._header.put(CRLF);
                            }
                        } else if (sb != null) {
                            this._header.put(CONNECTION_);
                            this._header.put(sb.toString().getBytes());
                            this._header.put(CRLF);
                        }
                    }
                    if (!z2 && this._status > 199 && getSendServerVersion()) {
                        this._header.put(SERVER);
                    }
                    this._header.put(HttpTokens.CRLF);
                    this._state = 2;
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new RuntimeException("Header>" + this._header.capacity(), e);
                }
            } else {
                throw new IllegalStateException("last?");
            }
        }
    }

    public void complete() throws IOException {
        if (this._state != 4) {
            super.complete();
            if (this._state < 3) {
                this._state = 3;
                if (this._contentLength == -2) {
                    this._needEOC = true;
                }
            }
            flushBuffer();
        }
    }

    public int flushBuffer() throws IOException {
        try {
            if (this._state != 0) {
                prepareBuffers();
                if (this._endp == null) {
                    if (this._needCRLF && this._buffer != null) {
                        this._buffer.put(HttpTokens.CRLF);
                    }
                    if (this._needEOC && this._buffer != null && !this._head) {
                        this._buffer.put(LAST_CHUNK);
                    }
                    this._needCRLF = false;
                    this._needEOC = false;
                    return 0;
                }
                int flushMask = flushMask();
                int i = 0;
                int i2 = -1;
                while (true) {
                    switch (flushMask) {
                        case 0:
                            if (this._header != null) {
                                this._header.clear();
                            }
                            this._bypass = false;
                            this._bufferChunked = false;
                            if (this._buffer != null) {
                                this._buffer.clear();
                                if (this._contentLength == -2) {
                                    this._buffer.setPutIndex(12);
                                    this._buffer.setGetIndex(12);
                                    if (!(this._content == null || this._content.length() >= this._buffer.space() || this._state == 3)) {
                                        this._buffer.put(this._content);
                                        this._content.clear();
                                        this._content = null;
                                    }
                                }
                            }
                            if (this._needCRLF || this._needEOC || !(this._content == null || this._content.length() == 0)) {
                                prepareBuffers();
                            } else {
                                if (this._state == 3) {
                                    this._state = 4;
                                }
                                if (this._state == 4 && this._persistent != null && !this._persistent.booleanValue() && this._status != 100 && this._method == null) {
                                    this._endp.shutdownOutput();
                                }
                            }
                            i2 = 0;
                            break;
                        case 1:
                            i2 = this._endp.flush(this._content);
                            break;
                        case 2:
                            i2 = this._endp.flush(this._buffer);
                            break;
                        case 3:
                            i2 = this._endp.flush(this._buffer, this._content, (Buffer) null);
                            break;
                        case 4:
                            i2 = this._endp.flush(this._header);
                            break;
                        case 5:
                            i2 = this._endp.flush(this._header, this._content, (Buffer) null);
                            break;
                        case 6:
                            i2 = this._endp.flush(this._header, this._buffer, (Buffer) null);
                            break;
                        case 7:
                            throw new IllegalStateException();
                    }
                    if (i2 > 0) {
                        i += i2;
                    }
                    int flushMask2 = flushMask();
                    if (i2 > 0 || (flushMask2 != 0 && flushMask == 0)) {
                        flushMask = flushMask2;
                    }
                }
                return i;
            }
            throw new IllegalStateException("State==HEADER");
        } catch (IOException e) {
            e = e;
            LOG.ignore(e);
            if (!(e instanceof EofException)) {
                e = new EofException((Throwable) e);
            }
            throw e;
        }
    }

    private int flushMask() {
        int i = 0;
        int i2 = ((this._header == null || this._header.length() <= 0) ? 0 : 4) | ((this._buffer == null || this._buffer.length() <= 0) ? 0 : 2);
        if (this._bypass && this._content != null && this._content.length() > 0) {
            i = 1;
        }
        return i2 | i;
    }

    private void prepareBuffers() {
        int length;
        if (!this._bufferChunked) {
            if (!this._bypass && this._content != null && this._content.length() > 0 && this._buffer != null && this._buffer.space() > 0) {
                this._content.skip(this._buffer.put(this._content));
                if (this._content.length() == 0) {
                    this._content = null;
                }
            }
            if (this._contentLength == -2) {
                if (this._bypass && ((this._buffer == null || this._buffer.length() == 0) && this._content != null)) {
                    int length2 = this._content.length();
                    this._bufferChunked = true;
                    if (this._header == null) {
                        this._header = this._buffers.getHeader();
                    }
                    if (this._needCRLF) {
                        if (this._header.length() <= 0) {
                            this._header.put(HttpTokens.CRLF);
                            this._needCRLF = false;
                        } else {
                            throw new IllegalStateException("EOC");
                        }
                    }
                    BufferUtil.putHexInt(this._header, length2);
                    this._header.put(HttpTokens.CRLF);
                    this._needCRLF = true;
                } else if (this._buffer != null && (length = this._buffer.length()) > 0) {
                    this._bufferChunked = true;
                    if (this._buffer.getIndex() == 12) {
                        this._buffer.poke(this._buffer.getIndex() - 2, HttpTokens.CRLF, 0, 2);
                        this._buffer.setGetIndex(this._buffer.getIndex() - 2);
                        BufferUtil.prependHexInt(this._buffer, length);
                        if (this._needCRLF) {
                            this._buffer.poke(this._buffer.getIndex() - 2, HttpTokens.CRLF, 0, 2);
                            this._buffer.setGetIndex(this._buffer.getIndex() - 2);
                            this._needCRLF = false;
                        }
                    } else {
                        if (this._header == null) {
                            this._header = this._buffers.getHeader();
                        }
                        if (this._needCRLF) {
                            if (this._header.length() <= 0) {
                                this._header.put(HttpTokens.CRLF);
                                this._needCRLF = false;
                            } else {
                                throw new IllegalStateException("EOC");
                            }
                        }
                        BufferUtil.putHexInt(this._header, length);
                        this._header.put(HttpTokens.CRLF);
                    }
                    if (this._buffer.space() >= 2) {
                        this._buffer.put(HttpTokens.CRLF);
                    } else {
                        this._needCRLF = true;
                    }
                }
                if (this._needEOC && (this._content == null || this._content.length() == 0)) {
                    if (this._header == null && this._buffer == null) {
                        this._header = this._buffers.getHeader();
                    }
                    if (this._needCRLF) {
                        if (this._buffer == null && this._header != null && this._header.space() >= HttpTokens.CRLF.length) {
                            this._header.put(HttpTokens.CRLF);
                            this._needCRLF = false;
                        } else if (this._buffer != null && this._buffer.space() >= HttpTokens.CRLF.length) {
                            this._buffer.put(HttpTokens.CRLF);
                            this._needCRLF = false;
                        }
                    }
                    if (!this._needCRLF && this._needEOC) {
                        if (this._buffer == null && this._header != null && this._header.space() >= LAST_CHUNK.length) {
                            if (!this._head) {
                                this._header.put(LAST_CHUNK);
                                this._bufferChunked = true;
                            }
                            this._needEOC = false;
                        } else if (this._buffer != null && this._buffer.space() >= LAST_CHUNK.length) {
                            if (!this._head) {
                                this._buffer.put(LAST_CHUNK);
                                this._bufferChunked = true;
                            }
                            this._needEOC = false;
                        }
                    }
                }
            }
        }
        if (this._content != null && this._content.length() == 0) {
            this._content = null;
        }
    }

    public int getBytesBuffered() {
        int i = 0;
        int length = (this._header == null ? 0 : this._header.length()) + (this._buffer == null ? 0 : this._buffer.length());
        if (this._content != null) {
            i = this._content.length();
        }
        return length + i;
    }

    public boolean isEmpty() {
        return (this._header == null || this._header.length() == 0) && (this._buffer == null || this._buffer.length() == 0) && (this._content == null || this._content.length() == 0);
    }

    public String toString() {
        Buffer buffer = this._header;
        Buffer buffer2 = this._buffer;
        Buffer buffer3 = this._content;
        Object[] objArr = new Object[5];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = Integer.valueOf(this._state);
        int i = -1;
        objArr[2] = Integer.valueOf(buffer == null ? -1 : buffer.length());
        objArr[3] = Integer.valueOf(buffer2 == null ? -1 : buffer2.length());
        if (buffer3 != null) {
            i = buffer3.length();
        }
        objArr[4] = Integer.valueOf(i);
        return String.format("%s{s=%d,h=%d,b=%d,c=%d}", objArr);
    }
}
