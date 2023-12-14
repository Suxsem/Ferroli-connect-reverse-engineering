package org.eclipse.jetty.http;

import java.io.IOException;
import org.android.agoo.common.AgooConstants;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.BufferCache;
import org.eclipse.jetty.p119io.Buffers;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.p119io.EofException;
import org.eclipse.jetty.p119io.View;
import org.eclipse.jetty.p119io.bio.StreamEndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class HttpParser implements Parser {
    private static final Logger LOG = Log.getLogger((Class<?>) HttpParser.class);
    public static final int STATE_CHUNK = 6;
    public static final int STATE_CHUNKED_CONTENT = 3;
    public static final int STATE_CHUNK_PARAMS = 5;
    public static final int STATE_CHUNK_SIZE = 4;
    public static final int STATE_CONTENT = 2;
    public static final int STATE_END = 0;
    public static final int STATE_END0 = -8;
    public static final int STATE_END1 = -7;
    public static final int STATE_EOF_CONTENT = 1;
    public static final int STATE_FIELD0 = -13;
    public static final int STATE_FIELD2 = -6;
    public static final int STATE_HEADER = -5;
    public static final int STATE_HEADER_IN_NAME = -3;
    public static final int STATE_HEADER_IN_VALUE = -1;
    public static final int STATE_HEADER_NAME = -4;
    public static final int STATE_HEADER_VALUE = -2;
    public static final int STATE_SEEKING_EOF = 7;
    public static final int STATE_SPACE1 = -12;
    public static final int STATE_SPACE2 = -9;
    public static final int STATE_START = -14;
    public static final int STATE_STATUS = -11;
    public static final int STATE_URI = -10;
    private Buffer _body;
    private Buffer _buffer;
    private final Buffers _buffers;
    private BufferCache.CachedBuffer _cached;
    protected int _chunkLength;
    protected int _chunkPosition;
    protected long _contentLength;
    protected long _contentPosition;
    protected final View _contentView;
    private final EndPoint _endp;
    protected byte _eol;
    private boolean _forceContentBuffer;
    private final EventHandler _handler;
    private boolean _headResponse;
    private Buffer _header;
    protected int _length;
    private String _multiLineValue;
    private boolean _persistent;
    private int _responseStatus;
    protected int _state;
    private final View.CaseInsensitive _tok0;
    private final View.CaseInsensitive _tok1;

    public static abstract class EventHandler {
        public abstract void content(Buffer buffer) throws IOException;

        public void earlyEOF() {
        }

        public void headerComplete() throws IOException {
        }

        public void messageComplete(long j) throws IOException {
        }

        public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException {
        }

        public abstract void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException;

        public abstract void startResponse(Buffer buffer, int i, Buffer buffer2) throws IOException;
    }

    public HttpParser(Buffer buffer, EventHandler eventHandler) {
        this._contentView = new View();
        this._state = -14;
        this._endp = null;
        this._buffers = null;
        this._header = buffer;
        this._buffer = buffer;
        this._handler = eventHandler;
        this._tok0 = new View.CaseInsensitive(this._header);
        this._tok1 = new View.CaseInsensitive(this._header);
    }

    public HttpParser(Buffers buffers, EndPoint endPoint, EventHandler eventHandler) {
        this._contentView = new View();
        this._state = -14;
        this._buffers = buffers;
        this._endp = endPoint;
        this._handler = eventHandler;
        this._tok0 = new View.CaseInsensitive();
        this._tok1 = new View.CaseInsensitive();
    }

    public long getContentLength() {
        return this._contentLength;
    }

    public long getContentRead() {
        return this._contentPosition;
    }

    public void setHeadResponse(boolean z) {
        this._headResponse = z;
    }

    public int getState() {
        return this._state;
    }

    public boolean inContentState() {
        return this._state > 0;
    }

    public boolean inHeaderState() {
        return this._state < 0;
    }

    public boolean isChunking() {
        return this._contentLength == -2;
    }

    public boolean isIdle() {
        return isState(-14);
    }

    public boolean isComplete() {
        return isState(0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1._body;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isMoreInBuffer() throws java.io.IOException {
        /*
            r1 = this;
            org.eclipse.jetty.io.Buffer r0 = r1._header
            if (r0 == 0) goto L_0x000a
            boolean r0 = r0.hasContent()
            if (r0 != 0) goto L_0x0014
        L_0x000a:
            org.eclipse.jetty.io.Buffer r0 = r1._body
            if (r0 == 0) goto L_0x0016
            boolean r0 = r0.hasContent()
            if (r0 == 0) goto L_0x0016
        L_0x0014:
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpParser.isMoreInBuffer():boolean");
    }

    public boolean isState(int i) {
        return this._state == i;
    }

    public boolean isPersistent() {
        return this._persistent;
    }

    public void setPersistent(boolean z) {
        this._persistent = z;
        if (!this._persistent) {
            int i = this._state;
            if (i == 0 || i == -14) {
                this._state = 7;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x000d A[LOOP:0: B:5:0x000d->B:8:0x0015, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parse() throws java.io.IOException {
        /*
            r2 = this;
            int r0 = r2._state
            if (r0 != 0) goto L_0x0007
            r2.reset()
        L_0x0007:
            int r0 = r2._state
            r1 = -14
            if (r0 != r1) goto L_0x0018
        L_0x000d:
            int r0 = r2._state
            if (r0 == 0) goto L_0x0017
            int r0 = r2.parseNext()
            if (r0 >= 0) goto L_0x000d
        L_0x0017:
            return
        L_0x0018:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "!START"
            r0.<init>(r1)
            goto L_0x0021
        L_0x0020:
            throw r0
        L_0x0021:
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpParser.parse():void");
    }

    public boolean parseAvailable() throws IOException {
        boolean z = parseNext() > 0;
        while (!isComplete() && (r3 = this._buffer) != null && r3.length() > 0 && !this._contentView.hasContent()) {
            z |= parseNext() > 0;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:366:0x0732, code lost:
        r2 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:367:0x0733, code lost:
        r4 = r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:231:0x042c A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x0486 A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:248:0x048b A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:271:0x04e1 A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x04e5 A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:275:0x04eb A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:290:0x0523 A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006c A[Catch:{ HttpException -> 0x09b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006f A[Catch:{ HttpException -> 0x09b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:374:0x0741 A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:385:0x0766 A[ADDED_TO_REGION, Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:388:0x076c A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:393:0x077e A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:394:0x078d A[Catch:{ NumberFormatException -> 0x0376, HttpException -> 0x09af }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00cc A[Catch:{ HttpException -> 0x09b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:529:0x0470 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00e0 A[Catch:{ HttpException -> 0x09b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0101 A[Catch:{ HttpException -> 0x09b3 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int parseNext() throws java.io.IOException {
        /*
            r20 = this;
            r1 = r20
            r2 = 7
            r3 = 0
            int r4 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            if (r4 != 0) goto L_0x0009
            return r3
        L_0x0009:
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            if (r4 != 0) goto L_0x0013
            org.eclipse.jetty.io.Buffer r4 = r20.getHeaderBuffer()     // Catch:{ HttpException -> 0x09b3 }
            r1._buffer = r4     // Catch:{ HttpException -> 0x09b3 }
        L_0x0013:
            int r4 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            r5 = 2
            r6 = 1
            if (r4 != r5) goto L_0x002b
            long r7 = r1._contentPosition     // Catch:{ HttpException -> 0x09b3 }
            long r9 = r1._contentLength     // Catch:{ HttpException -> 0x09b3 }
            int r4 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r4 != 0) goto L_0x002b
            r1._state = r3     // Catch:{ HttpException -> 0x09b3 }
            org.eclipse.jetty.http.HttpParser$EventHandler r4 = r1._handler     // Catch:{ HttpException -> 0x09b3 }
            long r7 = r1._contentPosition     // Catch:{ HttpException -> 0x09b3 }
            r4.messageComplete(r7)     // Catch:{ HttpException -> 0x09b3 }
            return r6
        L_0x002b:
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            int r4 = r4.length()     // Catch:{ HttpException -> 0x09b3 }
            r8 = -1
            if (r4 != 0) goto L_0x00ee
            int r4 = r20.fill()     // Catch:{ IOException -> 0x0059 }
            org.eclipse.jetty.util.log.Logger r9 = LOG     // Catch:{ IOException -> 0x0055 }
            java.lang.String r10 = "filled {}/{}"
            java.lang.Object[] r11 = new java.lang.Object[r5]     // Catch:{ IOException -> 0x0055 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)     // Catch:{ IOException -> 0x0055 }
            r11[r3] = r12     // Catch:{ IOException -> 0x0055 }
            org.eclipse.jetty.io.Buffer r12 = r1._buffer     // Catch:{ IOException -> 0x0055 }
            int r12 = r12.length()     // Catch:{ IOException -> 0x0055 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ IOException -> 0x0055 }
            r11[r6] = r12     // Catch:{ IOException -> 0x0055 }
            r9.debug((java.lang.String) r10, (java.lang.Object[]) r11)     // Catch:{ IOException -> 0x0055 }
            r9 = 0
            goto L_0x006a
        L_0x0055:
            r0 = move-exception
            r9 = r4
            r4 = r0
            goto L_0x005c
        L_0x0059:
            r0 = move-exception
            r4 = r0
            r9 = -1
        L_0x005c:
            org.eclipse.jetty.util.log.Logger r10 = LOG     // Catch:{ HttpException -> 0x09b3 }
            java.lang.String r11 = r20.toString()     // Catch:{ HttpException -> 0x09b3 }
            r10.debug((java.lang.String) r11, (java.lang.Throwable) r4)     // Catch:{ HttpException -> 0x09b3 }
            r19 = r9
            r9 = r4
            r4 = r19
        L_0x006a:
            if (r4 <= 0) goto L_0x006f
            r4 = 1
            goto L_0x00e2
        L_0x006f:
            if (r4 >= 0) goto L_0x00e1
            r1._persistent = r3     // Catch:{ HttpException -> 0x09b3 }
            int r4 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            if (r4 <= 0) goto L_0x00a3
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            int r4 = r4.length()     // Catch:{ HttpException -> 0x09b3 }
            if (r4 <= 0) goto L_0x00a3
            boolean r4 = r1._headResponse     // Catch:{ HttpException -> 0x09b3 }
            if (r4 != 0) goto L_0x00a3
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            int r5 = r5.length()     // Catch:{ HttpException -> 0x09b3 }
            org.eclipse.jetty.io.Buffer r4 = r4.get(r5)     // Catch:{ HttpException -> 0x09b3 }
            long r10 = r1._contentPosition     // Catch:{ HttpException -> 0x09b3 }
            int r5 = r4.length()     // Catch:{ HttpException -> 0x09b3 }
            long r12 = (long) r5     // Catch:{ HttpException -> 0x09b3 }
            long r10 = r10 + r12
            r1._contentPosition = r10     // Catch:{ HttpException -> 0x09b3 }
            org.eclipse.jetty.io.View r5 = r1._contentView     // Catch:{ HttpException -> 0x09b3 }
            r5.update(r4)     // Catch:{ HttpException -> 0x09b3 }
            org.eclipse.jetty.http.HttpParser$EventHandler r5 = r1._handler     // Catch:{ HttpException -> 0x09b3 }
            r5.content(r4)     // Catch:{ HttpException -> 0x09b3 }
        L_0x00a3:
            int r4 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            if (r4 == 0) goto L_0x00c8
            if (r4 == r6) goto L_0x00be
            if (r4 == r2) goto L_0x00c8
            r1._state = r3     // Catch:{ HttpException -> 0x09b3 }
            boolean r4 = r1._headResponse     // Catch:{ HttpException -> 0x09b3 }
            if (r4 != 0) goto L_0x00b6
            org.eclipse.jetty.http.HttpParser$EventHandler r4 = r1._handler     // Catch:{ HttpException -> 0x09b3 }
            r4.earlyEOF()     // Catch:{ HttpException -> 0x09b3 }
        L_0x00b6:
            org.eclipse.jetty.http.HttpParser$EventHandler r4 = r1._handler     // Catch:{ HttpException -> 0x09b3 }
            long r5 = r1._contentPosition     // Catch:{ HttpException -> 0x09b3 }
            r4.messageComplete(r5)     // Catch:{ HttpException -> 0x09b3 }
            goto L_0x00ca
        L_0x00be:
            r1._state = r3     // Catch:{ HttpException -> 0x09b3 }
            org.eclipse.jetty.http.HttpParser$EventHandler r4 = r1._handler     // Catch:{ HttpException -> 0x09b3 }
            long r5 = r1._contentPosition     // Catch:{ HttpException -> 0x09b3 }
            r4.messageComplete(r5)     // Catch:{ HttpException -> 0x09b3 }
            goto L_0x00ca
        L_0x00c8:
            r1._state = r3     // Catch:{ HttpException -> 0x09b3 }
        L_0x00ca:
            if (r9 != 0) goto L_0x00e0
            boolean r4 = r20.isComplete()     // Catch:{ HttpException -> 0x09b3 }
            if (r4 != 0) goto L_0x00df
            boolean r4 = r20.isIdle()     // Catch:{ HttpException -> 0x09b3 }
            if (r4 == 0) goto L_0x00d9
            goto L_0x00df
        L_0x00d9:
            org.eclipse.jetty.io.EofException r4 = new org.eclipse.jetty.io.EofException     // Catch:{ HttpException -> 0x09b3 }
            r4.<init>()     // Catch:{ HttpException -> 0x09b3 }
            throw r4     // Catch:{ HttpException -> 0x09b3 }
        L_0x00df:
            return r8
        L_0x00e0:
            throw r9     // Catch:{ HttpException -> 0x09b3 }
        L_0x00e1:
            r4 = 0
        L_0x00e2:
            org.eclipse.jetty.io.Buffer r9 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            int r9 = r9.length()     // Catch:{ HttpException -> 0x09b3 }
            r19 = r9
            r9 = r4
            r4 = r19
            goto L_0x00ef
        L_0x00ee:
            r9 = 0
        L_0x00ef:
            org.eclipse.jetty.io.Buffer r10 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            byte[] r10 = r10.array()     // Catch:{ HttpException -> 0x09b3 }
            int r11 = r1._state     // Catch:{ HttpException -> 0x09b3 }
        L_0x00f7:
            int r12 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            r13 = 13
            r2 = 32
            r5 = 10
            if (r12 >= 0) goto L_0x0735
            int r12 = r4 + -1
            if (r4 <= 0) goto L_0x0735
            int r4 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            if (r11 == r4) goto L_0x010e
            int r9 = r9 + 1
            int r4 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            r11 = r4
        L_0x010e:
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09b3 }
            byte r4 = r4.get()     // Catch:{ HttpException -> 0x09b3 }
            byte r14 = r1._eol     // Catch:{ HttpException -> 0x09b3 }
            r15 = 400(0x190, float:5.6E-43)
            if (r14 != r13) goto L_0x0126
            if (r4 != r5) goto L_0x0120
            r1._eol = r5     // Catch:{ HttpException -> 0x09b3 }
            goto L_0x0709
        L_0x0120:
            org.eclipse.jetty.http.HttpException r2 = new org.eclipse.jetty.http.HttpException     // Catch:{ HttpException -> 0x09b3 }
            r2.<init>(r15)     // Catch:{ HttpException -> 0x09b3 }
            throw r2     // Catch:{ HttpException -> 0x09b3 }
        L_0x0126:
            r1._eol = r3     // Catch:{ HttpException -> 0x09b3 }
            int r14 = r1._state     // Catch:{ HttpException -> 0x09b3 }
            r3 = -2
            r15 = 9
            r7 = -5
            switch(r14) {
                case -14: goto L_0x071d;
                case -13: goto L_0x06e1;
                case -12: goto L_0x06b6;
                case -11: goto L_0x064c;
                case -10: goto L_0x0606;
                case -9: goto L_0x059e;
                case -8: goto L_0x0131;
                case -7: goto L_0x0131;
                case -6: goto L_0x0533;
                case -5: goto L_0x0301;
                case -4: goto L_0x0295;
                case -3: goto L_0x023a;
                case -2: goto L_0x01ad;
                case -1: goto L_0x0134;
                default: goto L_0x0131;
            }
        L_0x0131:
            r4 = 0
            goto L_0x0732
        L_0x0134:
            if (r4 == r15) goto L_0x01aa
            if (r4 == r5) goto L_0x0142
            if (r4 == r13) goto L_0x0142
            if (r4 == r2) goto L_0x01aa
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r2 = r2 + r6
            r1._length = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0142:
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x01a5
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.length()     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x0163
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r13 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r13
            r2.update(r3, r5)     // Catch:{ HttpException -> 0x09af }
            goto L_0x01a5
        L_0x0163:
            java.lang.String r2 = r1._multiLineValue     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x0171
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            java.lang.String r3 = "ISO-8859-1"
            java.lang.String r2 = r2.toString((java.lang.String) r3)     // Catch:{ HttpException -> 0x09af }
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
        L_0x0171:
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r13 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r13
            r2.update(r3, r5)     // Catch:{ HttpException -> 0x09af }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x09af }
            r2.<init>()     // Catch:{ HttpException -> 0x09af }
            java.lang.String r3 = r1._multiLineValue     // Catch:{ HttpException -> 0x09af }
            r2.append(r3)     // Catch:{ HttpException -> 0x09af }
            java.lang.String r3 = " "
            r2.append(r3)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            java.lang.String r5 = "ISO-8859-1"
            java.lang.String r3 = r3.toString((java.lang.String) r5)     // Catch:{ HttpException -> 0x09af }
            r2.append(r3)     // Catch:{ HttpException -> 0x09af }
            java.lang.String r2 = r2.toString()     // Catch:{ HttpException -> 0x09af }
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
        L_0x01a5:
            r1._eol = r4     // Catch:{ HttpException -> 0x09af }
            r1._state = r7     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x01aa:
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x01ad:
            if (r4 == r15) goto L_0x0131
            if (r4 == r5) goto L_0x01d1
            if (r4 == r13) goto L_0x01d1
            if (r4 == r2) goto L_0x0131
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 != r8) goto L_0x01be
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r2.mark()     // Catch:{ HttpException -> 0x09af }
        L_0x01be:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.getIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r2 = r2 - r3
            r1._length = r2     // Catch:{ HttpException -> 0x09af }
            r1._state = r8     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x01d1:
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x0234
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.length()     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x01f2
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r13 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r13
            r2.update(r3, r5)     // Catch:{ HttpException -> 0x09af }
            goto L_0x0234
        L_0x01f2:
            java.lang.String r2 = r1._multiLineValue     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x0200
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            java.lang.String r3 = "ISO-8859-1"
            java.lang.String r2 = r2.toString((java.lang.String) r3)     // Catch:{ HttpException -> 0x09af }
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
        L_0x0200:
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r13 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r13
            r2.update(r3, r5)     // Catch:{ HttpException -> 0x09af }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x09af }
            r2.<init>()     // Catch:{ HttpException -> 0x09af }
            java.lang.String r3 = r1._multiLineValue     // Catch:{ HttpException -> 0x09af }
            r2.append(r3)     // Catch:{ HttpException -> 0x09af }
            java.lang.String r3 = " "
            r2.append(r3)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            java.lang.String r5 = "ISO-8859-1"
            java.lang.String r3 = r3.toString((java.lang.String) r5)     // Catch:{ HttpException -> 0x09af }
            r2.append(r3)     // Catch:{ HttpException -> 0x09af }
            java.lang.String r2 = r2.toString()     // Catch:{ HttpException -> 0x09af }
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
        L_0x0234:
            r1._eol = r4     // Catch:{ HttpException -> 0x09af }
            r1._state = r7     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x023a:
            if (r4 == r15) goto L_0x0290
            if (r4 == r5) goto L_0x0272
            if (r4 == r13) goto L_0x0272
            if (r4 == r2) goto L_0x0290
            r2 = 58
            if (r4 == r2) goto L_0x0250
            r2 = 0
            r1._cached = r2     // Catch:{ HttpException -> 0x09af }
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r2 = r2 + r6
            r1._length = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0250:
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x026c
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r1._cached     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x026c
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r4 = r4.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r7 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r7
            r2.update(r4, r5)     // Catch:{ HttpException -> 0x09af }
        L_0x026c:
            r1._length = r8     // Catch:{ HttpException -> 0x09af }
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0272:
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x028a
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r13 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r13
            r2.update(r3, r5)     // Catch:{ HttpException -> 0x09af }
        L_0x028a:
            r1._eol = r4     // Catch:{ HttpException -> 0x09af }
            r1._state = r7     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0290:
            r2 = -4
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0295:
            if (r4 == r15) goto L_0x0131
            if (r4 == r5) goto L_0x02e3
            if (r4 == r13) goto L_0x02e3
            if (r4 == r2) goto L_0x0131
            r2 = 58
            if (r4 == r2) goto L_0x02c1
            r2 = 0
            r1._cached = r2     // Catch:{ HttpException -> 0x09af }
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 != r8) goto L_0x02ad
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r2.mark()     // Catch:{ HttpException -> 0x09af }
        L_0x02ad:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.getIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r2 = r2 - r3
            r1._length = r2     // Catch:{ HttpException -> 0x09af }
            r2 = -3
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x02c1:
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x02dd
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r1._cached     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x02dd
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r4 = r4.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r7 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r7
            r2.update(r4, r5)     // Catch:{ HttpException -> 0x09af }
        L_0x02dd:
            r1._length = r8     // Catch:{ HttpException -> 0x09af }
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x02e3:
            int r2 = r1._length     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x02fb
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r13 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 + r13
            r2.update(r3, r5)     // Catch:{ HttpException -> 0x09af }
        L_0x02fb:
            r1._eol = r4     // Catch:{ HttpException -> 0x09af }
            r1._state = r7     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0301:
            if (r4 == r15) goto L_0x052c
            if (r4 == r2) goto L_0x052c
            r2 = 58
            if (r4 == r2) goto L_0x052c
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r1._cached     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x0326
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.length()     // Catch:{ HttpException -> 0x09af }
            if (r2 > 0) goto L_0x0326
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.length()     // Catch:{ HttpException -> 0x09af }
            if (r2 > 0) goto L_0x0326
            java.lang.String r2 = r1._multiLineValue     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x0322
            goto L_0x0326
        L_0x0322:
            r17 = r4
            goto L_0x0423
        L_0x0326:
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r1._cached     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x032e
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r1._cached     // Catch:{ HttpException -> 0x09af }
        L_0x032c:
            r7 = 0
            goto L_0x0337
        L_0x032e:
            org.eclipse.jetty.http.HttpHeaders r2 = org.eclipse.jetty.http.HttpHeaders.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r7 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r2 = r2.lookup((org.eclipse.jetty.p119io.Buffer) r7)     // Catch:{ HttpException -> 0x09af }
            goto L_0x032c
        L_0x0337:
            r1._cached = r7     // Catch:{ HttpException -> 0x09af }
            java.lang.String r7 = r1._multiLineValue     // Catch:{ HttpException -> 0x09af }
            if (r7 != 0) goto L_0x0340
            org.eclipse.jetty.io.View$CaseInsensitive r7 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            goto L_0x0347
        L_0x0340:
            org.eclipse.jetty.io.ByteArrayBuffer r7 = new org.eclipse.jetty.io.ByteArrayBuffer     // Catch:{ HttpException -> 0x09af }
            java.lang.String r14 = r1._multiLineValue     // Catch:{ HttpException -> 0x09af }
            r7.<init>((java.lang.String) r14)     // Catch:{ HttpException -> 0x09af }
        L_0x0347:
            org.eclipse.jetty.http.HttpHeaders r14 = org.eclipse.jetty.http.HttpHeaders.CACHE     // Catch:{ HttpException -> 0x09af }
            int r14 = r14.getOrdinal((org.eclipse.jetty.p119io.Buffer) r2)     // Catch:{ HttpException -> 0x09af }
            if (r14 < 0) goto L_0x0403
            if (r14 == r6) goto L_0x03c1
            r15 = 5
            if (r14 == r15) goto L_0x0385
            r15 = 12
            if (r14 == r15) goto L_0x035a
            goto L_0x0403
        L_0x035a:
            long r14 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            r17 = -2
            int r16 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r16 == 0) goto L_0x0403
            long r14 = org.eclipse.jetty.p119io.BufferUtil.toLong(r7)     // Catch:{ NumberFormatException -> 0x0376 }
            r1._contentLength = r14     // Catch:{ NumberFormatException -> 0x0376 }
            long r14 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            r17 = r4
            r3 = 0
            int r16 = (r14 > r3 ? 1 : (r14 == r3 ? 0 : -1))
            if (r16 > 0) goto L_0x0405
            r1._contentLength = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0405
        L_0x0376:
            r0 = move-exception
            r2 = r0
            org.eclipse.jetty.util.log.Logger r3 = LOG     // Catch:{ HttpException -> 0x09af }
            r3.ignore(r2)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpException r2 = new org.eclipse.jetty.http.HttpException     // Catch:{ HttpException -> 0x09af }
            r3 = 400(0x190, float:5.6E-43)
            r2.<init>(r3)     // Catch:{ HttpException -> 0x09af }
            throw r2     // Catch:{ HttpException -> 0x09af }
        L_0x0385:
            r17 = r4
            org.eclipse.jetty.http.HttpHeaderValues r3 = org.eclipse.jetty.http.HttpHeaderValues.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r7 = r3.lookup((org.eclipse.jetty.p119io.Buffer) r7)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpHeaderValues r3 = org.eclipse.jetty.http.HttpHeaderValues.CACHE     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getOrdinal((org.eclipse.jetty.p119io.Buffer) r7)     // Catch:{ HttpException -> 0x09af }
            r4 = 2
            if (r4 != r3) goto L_0x039c
            r3 = -2
            r1._contentLength = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0405
        L_0x039c:
            java.lang.String r3 = "ISO-8859-1"
            java.lang.String r3 = r7.toString((java.lang.String) r3)     // Catch:{ HttpException -> 0x09af }
            java.lang.String r4 = "chunked"
            boolean r4 = r3.endsWith(r4)     // Catch:{ HttpException -> 0x09af }
            if (r4 == 0) goto L_0x03af
            r3 = -2
            r1._contentLength = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0405
        L_0x03af:
            java.lang.String r4 = "chunked"
            int r3 = r3.indexOf(r4)     // Catch:{ HttpException -> 0x09af }
            if (r3 >= 0) goto L_0x03b8
            goto L_0x0405
        L_0x03b8:
            org.eclipse.jetty.http.HttpException r2 = new org.eclipse.jetty.http.HttpException     // Catch:{ HttpException -> 0x09af }
            r3 = 400(0x190, float:5.6E-43)
            r4 = 0
            r2.<init>(r3, r4)     // Catch:{ HttpException -> 0x09af }
            throw r2     // Catch:{ HttpException -> 0x09af }
        L_0x03c1:
            r17 = r4
            org.eclipse.jetty.http.HttpHeaderValues r3 = org.eclipse.jetty.http.HttpHeaderValues.CACHE     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getOrdinal((org.eclipse.jetty.p119io.Buffer) r7)     // Catch:{ HttpException -> 0x09af }
            if (r3 == r8) goto L_0x03d8
            if (r3 == r6) goto L_0x03d4
            r4 = 5
            if (r3 == r4) goto L_0x03d1
            goto L_0x0405
        L_0x03d1:
            r1._persistent = r6     // Catch:{ HttpException -> 0x09af }
            goto L_0x0405
        L_0x03d4:
            r3 = 0
            r1._persistent = r3     // Catch:{ HttpException -> 0x09b3 }
            goto L_0x0405
        L_0x03d8:
            java.lang.String r3 = r7.toString()     // Catch:{ HttpException -> 0x09af }
            java.lang.String r4 = ","
            java.lang.String[] r3 = r3.split(r4)     // Catch:{ HttpException -> 0x09af }
            int r4 = r3.length     // Catch:{ HttpException -> 0x09af }
            r14 = 0
        L_0x03e4:
            if (r14 >= r4) goto L_0x0405
            r15 = r3[r14]     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpHeaderValues r5 = org.eclipse.jetty.http.HttpHeaderValues.CACHE     // Catch:{ HttpException -> 0x09af }
            java.lang.String r15 = r15.trim()     // Catch:{ HttpException -> 0x09af }
            int r5 = r5.getOrdinal((java.lang.String) r15)     // Catch:{ HttpException -> 0x09af }
            if (r5 == r6) goto L_0x03fb
            r15 = 5
            if (r5 == r15) goto L_0x03f8
            goto L_0x03fe
        L_0x03f8:
            r1._persistent = r6     // Catch:{ HttpException -> 0x09af }
            goto L_0x03fe
        L_0x03fb:
            r5 = 0
            r1._persistent = r5     // Catch:{ HttpException -> 0x09af }
        L_0x03fe:
            int r14 = r14 + 1
            r5 = 10
            goto L_0x03e4
        L_0x0403:
            r17 = r4
        L_0x0405:
            org.eclipse.jetty.http.HttpParser$EventHandler r3 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r3.parsedHeader(r2, r7)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            r2 = 0
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
        L_0x0423:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r2.setMarkIndex(r8)     // Catch:{ HttpException -> 0x09af }
            r3 = r17
            if (r3 == r13) goto L_0x0470
            r2 = 10
            if (r3 != r2) goto L_0x0431
            goto L_0x0470
        L_0x0431:
            r1._length = r6     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r2.mark()     // Catch:{ HttpException -> 0x09af }
            r2 = -4
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            if (r10 == 0) goto L_0x0131
            org.eclipse.jetty.http.HttpHeaders r2 = org.eclipse.jetty.http.HttpHeaders.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r4 = r12 + 1
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r2.getBest(r10, r3, r4)     // Catch:{ HttpException -> 0x09af }
            r1._cached = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r1._cached     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x0131
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r1._cached     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.length()     // Catch:{ HttpException -> 0x09af }
            r1._length = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            int r4 = r1._length     // Catch:{ HttpException -> 0x09af }
            int r3 = r3 + r4
            r2.setGetIndex(r3)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.length()     // Catch:{ HttpException -> 0x09af }
            r4 = 0
            goto L_0x0733
        L_0x0470:
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x048b
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 304(0x130, float:4.26E-43)
            if (r2 == r4) goto L_0x0486
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 204(0xcc, float:2.86E-43)
            if (r2 == r4) goto L_0x0486
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 200(0xc8, float:2.8E-43)
            if (r2 >= r4) goto L_0x048b
        L_0x0486:
            r4 = 0
            r1._contentLength = r4     // Catch:{ HttpException -> 0x09af }
            goto L_0x04b4
        L_0x048b:
            long r4 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            r9 = -3
            int r2 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r2 != 0) goto L_0x04b4
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x04af
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 304(0x130, float:4.26E-43)
            if (r2 == r4) goto L_0x04af
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 204(0xcc, float:2.86E-43)
            if (r2 == r4) goto L_0x04af
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 200(0xc8, float:2.8E-43)
            if (r2 >= r4) goto L_0x04aa
            goto L_0x04af
        L_0x04aa:
            r4 = -1
            r1._contentLength = r4     // Catch:{ HttpException -> 0x09af }
            goto L_0x04b4
        L_0x04af:
            r4 = 0
            r1._contentLength = r4     // Catch:{ HttpException -> 0x09af }
            goto L_0x04b6
        L_0x04b4:
            r4 = 0
        L_0x04b6:
            r1._contentPosition = r4     // Catch:{ HttpException -> 0x09af }
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            byte r2 = r1._eol     // Catch:{ HttpException -> 0x09af }
            if (r2 != r13) goto L_0x04d8
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            boolean r2 = r2.hasContent()     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x04d8
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r2 = r2.peek()     // Catch:{ HttpException -> 0x09af }
            r3 = 10
            if (r2 != r3) goto L_0x04d8
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r2 = r2.get()     // Catch:{ HttpException -> 0x09af }
            r1._eol = r2     // Catch:{ HttpException -> 0x09af }
        L_0x04d8:
            long r2 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 <= 0) goto L_0x04e5
            r2 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x04e8
        L_0x04e5:
            long r2 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            int r2 = (int) r2     // Catch:{ HttpException -> 0x09af }
        L_0x04e8:
            r3 = -2
            if (r2 == r3) goto L_0x0523
            if (r2 == r8) goto L_0x051b
            if (r2 == 0) goto L_0x04f8
            r2 = 2
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r2.headerComplete()     // Catch:{ HttpException -> 0x09af }
            goto L_0x052b
        L_0x04f8:
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r2.headerComplete()     // Catch:{ HttpException -> 0x09af }
            boolean r2 = r1._persistent     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x0510
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r3 = 100
            if (r2 < r3) goto L_0x050e
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 >= r3) goto L_0x050e
            goto L_0x0510
        L_0x050e:
            r2 = 7
            goto L_0x0511
        L_0x0510:
            r2 = 0
        L_0x0511:
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            r2.messageComplete(r3)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x051b:
            r1._state = r6     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r2.headerComplete()     // Catch:{ HttpException -> 0x09af }
            goto L_0x052b
        L_0x0523:
            r2 = 3
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r2.headerComplete()     // Catch:{ HttpException -> 0x09af }
        L_0x052b:
            return r6
        L_0x052c:
            r1._length = r8     // Catch:{ HttpException -> 0x09af }
            r2 = -2
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0533:
            r3 = r4
            if (r3 == r13) goto L_0x053a
            r2 = 10
            if (r3 != r2) goto L_0x0131
        L_0x053a:
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x0554
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r4 = org.eclipse.jetty.http.HttpVersions.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r5 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r4.lookup((org.eclipse.jetty.p119io.Buffer) r5)     // Catch:{ HttpException -> 0x09af }
            int r5 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r13 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r13 = r13.sliceFromMark()     // Catch:{ HttpException -> 0x09af }
            r2.startResponse(r4, r5, r13)     // Catch:{ HttpException -> 0x09af }
            goto L_0x0570
        L_0x0554:
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r4 = org.eclipse.jetty.http.HttpMethods.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r5 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r4.lookup((org.eclipse.jetty.p119io.Buffer) r5)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r5 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r13 = org.eclipse.jetty.http.HttpVersions.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r14 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r14 = r14.sliceFromMark()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r13 = r13.lookup((org.eclipse.jetty.p119io.Buffer) r14)     // Catch:{ HttpException -> 0x09af }
            r2.startRequest(r4, r5, r13)     // Catch:{ HttpException -> 0x09af }
            r4 = r13
        L_0x0570:
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r2 = org.eclipse.jetty.http.HttpVersions.CACHE     // Catch:{ HttpException -> 0x09af }
            int r2 = r2.getOrdinal((org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ HttpException -> 0x09af }
            r3 = 11
            if (r2 < r3) goto L_0x057e
            r2 = 1
            goto L_0x057f
        L_0x057e:
            r2 = 0
        L_0x057f:
            r1._persistent = r2     // Catch:{ HttpException -> 0x09af }
            r1._state = r7     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            r2 = 0
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0709
        L_0x059e:
            r3 = r4
            if (r3 > r2) goto L_0x05fc
            if (r3 >= 0) goto L_0x05a4
            goto L_0x05fc
        L_0x05a4:
            if (r3 >= r2) goto L_0x0131
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            if (r2 <= 0) goto L_0x05d9
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r4 = org.eclipse.jetty.http.HttpMethods.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r5 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r4.lookup((org.eclipse.jetty.p119io.Buffer) r5)     // Catch:{ HttpException -> 0x09af }
            int r5 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r13 = 0
            r2.startResponse(r4, r5, r13)     // Catch:{ HttpException -> 0x09af }
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            r1._state = r7     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            r2 = 0
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x05d9:
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r3 = org.eclipse.jetty.http.HttpMethods.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r4 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r3.lookup((org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r4 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            r5 = 0
            r2.startRequest(r3, r4, r5)     // Catch:{ HttpException -> 0x09af }
            r2 = 0
            r1._persistent = r2     // Catch:{ HttpException -> 0x09af }
            r2 = 7
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r2.headerComplete()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            r2.messageComplete(r3)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x05fc:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r2.mark()     // Catch:{ HttpException -> 0x09af }
            r2 = -6
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x0606:
            r3 = r4
            if (r3 != r2) goto L_0x0621
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r4 = r4.getIndex()     // Catch:{ HttpException -> 0x09af }
            int r4 = r4 - r6
            r2.update(r3, r4)     // Catch:{ HttpException -> 0x09af }
            r2 = -9
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0709
        L_0x0621:
            if (r3 >= r2) goto L_0x0131
            if (r3 < 0) goto L_0x0131
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r3 = org.eclipse.jetty.http.HttpMethods.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r4 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r3.lookup((org.eclipse.jetty.p119io.Buffer) r4)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r4.sliceFromMark()     // Catch:{ HttpException -> 0x09af }
            r5 = 0
            r2.startRequest(r3, r4, r5)     // Catch:{ HttpException -> 0x09af }
            r2 = 0
            r1._persistent = r2     // Catch:{ HttpException -> 0x09af }
            r2 = 7
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r2.headerComplete()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            r2.messageComplete(r3)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x064c:
            r3 = r4
            if (r3 != r2) goto L_0x0667
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r4 = r4.getIndex()     // Catch:{ HttpException -> 0x09af }
            int r4 = r4 - r6
            r2.update(r3, r4)     // Catch:{ HttpException -> 0x09af }
            r2 = -9
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0709
        L_0x0667:
            r4 = 48
            if (r3 < r4) goto L_0x067c
            r4 = 57
            if (r3 > r4) goto L_0x067c
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 10
            int r2 = r2 * 10
            int r4 = r3 + -48
            int r2 = r2 + r4
            r1._responseStatus = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0709
        L_0x067c:
            if (r3 >= r2) goto L_0x06ae
            if (r3 < 0) goto L_0x06ae
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r4 = org.eclipse.jetty.http.HttpMethods.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r5 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r4.lookup((org.eclipse.jetty.p119io.Buffer) r5)     // Catch:{ HttpException -> 0x09af }
            int r5 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r13 = 0
            r2.startResponse(r4, r5, r13)     // Catch:{ HttpException -> 0x09af }
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            r1._state = r7     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok1     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.getIndex()     // Catch:{ HttpException -> 0x09af }
            r2.setPutIndex(r3)     // Catch:{ HttpException -> 0x09af }
            r2 = 0
            r1._multiLineValue = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0709
        L_0x06ae:
            r2 = -10
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            r1._responseStatus = r8     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x06b6:
            r3 = r4
            if (r3 > r2) goto L_0x06c8
            if (r3 >= 0) goto L_0x06bc
            goto L_0x06c8
        L_0x06bc:
            if (r3 < r2) goto L_0x06c0
            goto L_0x0131
        L_0x06c0:
            org.eclipse.jetty.http.HttpException r2 = new org.eclipse.jetty.http.HttpException     // Catch:{ HttpException -> 0x09af }
            r3 = 400(0x190, float:5.6E-43)
            r2.<init>(r3)     // Catch:{ HttpException -> 0x09af }
            throw r2     // Catch:{ HttpException -> 0x09af }
        L_0x06c8:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r2.mark()     // Catch:{ HttpException -> 0x09af }
            int r2 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            if (r2 < 0) goto L_0x06db
            r2 = -11
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            int r4 = r3 + -48
            r1._responseStatus = r4     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x06db:
            r2 = -10
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            goto L_0x0131
        L_0x06e1:
            r3 = r4
            if (r3 != r2) goto L_0x070f
            org.eclipse.jetty.io.View$CaseInsensitive r2 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.markIndex()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r4 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r4 = r4.getIndex()     // Catch:{ HttpException -> 0x09af }
            int r4 = r4 - r6
            r2.update(r3, r4)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache r2 = org.eclipse.jetty.http.HttpVersions.CACHE     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View$CaseInsensitive r3 = r1._tok0     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.BufferCache$CachedBuffer r2 = r2.get((org.eclipse.jetty.p119io.Buffer) r3)     // Catch:{ HttpException -> 0x09af }
            if (r2 != 0) goto L_0x0702
            r2 = -1
            goto L_0x0703
        L_0x0702:
            r2 = 0
        L_0x0703:
            r1._responseStatus = r2     // Catch:{ HttpException -> 0x09af }
            r2 = -12
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
        L_0x0709:
            r4 = r12
        L_0x070a:
            r2 = 7
            r3 = 0
            r5 = 2
            goto L_0x00f7
        L_0x070f:
            if (r3 >= r2) goto L_0x0131
            if (r3 >= 0) goto L_0x0715
            goto L_0x0131
        L_0x0715:
            org.eclipse.jetty.http.HttpException r2 = new org.eclipse.jetty.http.HttpException     // Catch:{ HttpException -> 0x09af }
            r3 = 400(0x190, float:5.6E-43)
            r2.<init>(r3)     // Catch:{ HttpException -> 0x09af }
            throw r2     // Catch:{ HttpException -> 0x09af }
        L_0x071d:
            r3 = r4
            r4 = -3
            r1._contentLength = r4     // Catch:{ HttpException -> 0x09af }
            r4 = 0
            r1._cached = r4     // Catch:{ HttpException -> 0x09af }
            if (r3 > r2) goto L_0x0729
            if (r3 >= 0) goto L_0x0732
        L_0x0729:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r2.mark()     // Catch:{ HttpException -> 0x09af }
            r2 = -13
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
        L_0x0732:
            r2 = r12
        L_0x0733:
            r4 = r2
            goto L_0x070a
        L_0x0735:
            int r3 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            if (r3 <= 0) goto L_0x075a
            boolean r3 = r1._headResponse     // Catch:{ HttpException -> 0x09af }
            if (r3 == 0) goto L_0x075a
            boolean r3 = r1._persistent     // Catch:{ HttpException -> 0x09af }
            if (r3 != 0) goto L_0x0750
            int r3 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 100
            if (r3 < r4) goto L_0x074e
            int r3 = r1._responseStatus     // Catch:{ HttpException -> 0x09af }
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 >= r4) goto L_0x074e
            goto L_0x0750
        L_0x074e:
            r3 = 7
            goto L_0x0751
        L_0x0750:
            r3 = 0
        L_0x0751:
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r3 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r4 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            r3.messageComplete(r4)     // Catch:{ HttpException -> 0x09af }
        L_0x075a:
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.length()     // Catch:{ HttpException -> 0x09af }
            int r4 = r1._state     // Catch:{ HttpException -> 0x09af }
        L_0x0762:
            int r5 = r1._state     // Catch:{ HttpException -> 0x09af }
            if (r5 <= 0) goto L_0x09ae
            if (r3 <= 0) goto L_0x09ae
            int r5 = r1._state     // Catch:{ HttpException -> 0x09af }
            if (r4 == r5) goto L_0x0770
            int r9 = r9 + 1
            int r4 = r1._state     // Catch:{ HttpException -> 0x09af }
        L_0x0770:
            byte r5 = r1._eol     // Catch:{ HttpException -> 0x09af }
            if (r5 != r13) goto L_0x078d
            org.eclipse.jetty.io.Buffer r5 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r5 = r5.peek()     // Catch:{ HttpException -> 0x09af }
            r7 = 10
            if (r5 != r7) goto L_0x078d
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r3 = r3.get()     // Catch:{ HttpException -> 0x09af }
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.length()     // Catch:{ HttpException -> 0x09af }
            goto L_0x0762
        L_0x078d:
            r5 = 0
            r1._eol = r5     // Catch:{ HttpException -> 0x09af }
            int r7 = r1._state     // Catch:{ HttpException -> 0x09af }
            switch(r7) {
                case 1: goto L_0x0985;
                case 2: goto L_0x0930;
                case 3: goto L_0x0901;
                case 4: goto L_0x0855;
                case 5: goto L_0x080a;
                case 6: goto L_0x07d7;
                case 7: goto L_0x079e;
                default: goto L_0x0795;
            }     // Catch:{ HttpException -> 0x09af }
        L_0x0795:
            r5 = 48
            r7 = 2
        L_0x0798:
            r8 = 10
        L_0x079a:
            r10 = 0
            goto L_0x09a6
        L_0x079e:
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.length()     // Catch:{ HttpException -> 0x09af }
            r7 = 2
            if (r3 <= r7) goto L_0x07af
            r1._state = r5     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.EndPoint r3 = r1._endp     // Catch:{ HttpException -> 0x09af }
            r3.close()     // Catch:{ HttpException -> 0x09af }
            goto L_0x07d1
        L_0x07af:
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.length()     // Catch:{ HttpException -> 0x09af }
            if (r3 <= 0) goto L_0x07d1
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r3 = r3.get()     // Catch:{ HttpException -> 0x09af }
            boolean r3 = java.lang.Character.isWhitespace(r3)     // Catch:{ HttpException -> 0x09af }
            if (r3 != 0) goto L_0x07af
            r3 = 0
            r1._state = r3     // Catch:{ HttpException -> 0x09b3 }
            org.eclipse.jetty.io.EndPoint r3 = r1._endp     // Catch:{ HttpException -> 0x09af }
            r3.close()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r3.clear()     // Catch:{ HttpException -> 0x09af }
            goto L_0x07af
        L_0x07d1:
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r3.clear()     // Catch:{ HttpException -> 0x09af }
            goto L_0x0818
        L_0x07d7:
            r7 = 2
            int r5 = r1._chunkLength     // Catch:{ HttpException -> 0x09af }
            int r8 = r1._chunkPosition     // Catch:{ HttpException -> 0x09af }
            int r5 = r5 - r8
            if (r5 != 0) goto L_0x07e3
            r3 = 3
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0818
        L_0x07e3:
            if (r3 <= r5) goto L_0x07e6
            r3 = r5
        L_0x07e6:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r2 = r2.get(r3)     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            int r5 = r2.length()     // Catch:{ HttpException -> 0x09af }
            long r7 = (long) r5     // Catch:{ HttpException -> 0x09af }
            long r3 = r3 + r7
            r1._contentPosition = r3     // Catch:{ HttpException -> 0x09af }
            int r3 = r1._chunkPosition     // Catch:{ HttpException -> 0x09af }
            int r4 = r2.length()     // Catch:{ HttpException -> 0x09af }
            int r3 = r3 + r4
            r1._chunkPosition = r3     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View r3 = r1._contentView     // Catch:{ HttpException -> 0x09af }
            r3.update(r2)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r3 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r3.content(r2)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x080a:
            r7 = 2
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r3 = r3.get()     // Catch:{ HttpException -> 0x09af }
            if (r3 == r13) goto L_0x081c
            r5 = 10
            if (r3 != r5) goto L_0x0818
            goto L_0x081c
        L_0x0818:
            r5 = 48
            goto L_0x0798
        L_0x081c:
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            int r3 = r1._chunkLength     // Catch:{ HttpException -> 0x09af }
            if (r3 != 0) goto L_0x0851
            byte r2 = r1._eol     // Catch:{ HttpException -> 0x09af }
            if (r2 != r13) goto L_0x0840
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            boolean r2 = r2.hasContent()     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x0840
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r2 = r2.peek()     // Catch:{ HttpException -> 0x09af }
            r3 = 10
            if (r2 != r3) goto L_0x0840
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r2 = r2.get()     // Catch:{ HttpException -> 0x09af }
            r1._eol = r2     // Catch:{ HttpException -> 0x09af }
        L_0x0840:
            boolean r2 = r1._persistent     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x0846
            r2 = 0
            goto L_0x0847
        L_0x0846:
            r2 = 7
        L_0x0847:
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            r2.messageComplete(r3)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x0851:
            r3 = 6
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0818
        L_0x0855:
            r7 = 2
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r3 = r3.get()     // Catch:{ HttpException -> 0x09af }
            if (r3 == r13) goto L_0x08c5
            r5 = 10
            if (r3 != r5) goto L_0x0863
            goto L_0x08c5
        L_0x0863:
            if (r3 <= r2) goto L_0x08be
            r5 = 59
            if (r3 != r5) goto L_0x086a
            goto L_0x08be
        L_0x086a:
            r5 = 48
            if (r3 < r5) goto L_0x087d
            r8 = 57
            if (r3 > r8) goto L_0x087d
            int r8 = r1._chunkLength     // Catch:{ HttpException -> 0x09af }
            int r8 = r8 * 16
            int r3 = r3 + -48
            int r8 = r8 + r3
            r1._chunkLength = r8     // Catch:{ HttpException -> 0x09af }
            goto L_0x0798
        L_0x087d:
            r8 = 97
            if (r3 < r8) goto L_0x0892
            r8 = 102(0x66, float:1.43E-43)
            if (r3 > r8) goto L_0x0892
            int r8 = r1._chunkLength     // Catch:{ HttpException -> 0x09af }
            int r8 = r8 * 16
            int r3 = r3 + 10
            int r3 = r3 + -97
            int r8 = r8 + r3
            r1._chunkLength = r8     // Catch:{ HttpException -> 0x09af }
            goto L_0x0798
        L_0x0892:
            r8 = 65
            if (r3 < r8) goto L_0x08a7
            r8 = 70
            if (r3 > r8) goto L_0x08a7
            int r8 = r1._chunkLength     // Catch:{ HttpException -> 0x09af }
            int r8 = r8 * 16
            int r3 = r3 + 10
            int r3 = r3 + -65
            int r8 = r8 + r3
            r1._chunkLength = r8     // Catch:{ HttpException -> 0x09af }
            goto L_0x0798
        L_0x08a7:
            java.io.IOException r2 = new java.io.IOException     // Catch:{ HttpException -> 0x09af }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x09af }
            r4.<init>()     // Catch:{ HttpException -> 0x09af }
            java.lang.String r5 = "bad chunk char: "
            r4.append(r5)     // Catch:{ HttpException -> 0x09af }
            r4.append(r3)     // Catch:{ HttpException -> 0x09af }
            java.lang.String r3 = r4.toString()     // Catch:{ HttpException -> 0x09af }
            r2.<init>(r3)     // Catch:{ HttpException -> 0x09af }
            throw r2     // Catch:{ HttpException -> 0x09af }
        L_0x08be:
            r5 = 48
            r3 = 5
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0798
        L_0x08c5:
            r5 = 48
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            int r3 = r1._chunkLength     // Catch:{ HttpException -> 0x09af }
            if (r3 != 0) goto L_0x08fc
            byte r2 = r1._eol     // Catch:{ HttpException -> 0x09af }
            if (r2 != r13) goto L_0x08eb
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            boolean r2 = r2.hasContent()     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x08eb
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r2 = r2.peek()     // Catch:{ HttpException -> 0x09af }
            r3 = 10
            if (r2 != r3) goto L_0x08eb
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r2 = r2.get()     // Catch:{ HttpException -> 0x09af }
            r1._eol = r2     // Catch:{ HttpException -> 0x09af }
        L_0x08eb:
            boolean r2 = r1._persistent     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x08f1
            r2 = 0
            goto L_0x08f2
        L_0x08f1:
            r2 = 7
        L_0x08f2:
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            r2.messageComplete(r3)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x08fc:
            r3 = 6
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x0798
        L_0x0901:
            r5 = 48
            r7 = 2
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r3 = r3.peek()     // Catch:{ HttpException -> 0x09af }
            if (r3 == r13) goto L_0x0924
            r8 = 10
            if (r3 != r8) goto L_0x0911
            goto L_0x0926
        L_0x0911:
            if (r3 > r2) goto L_0x091a
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            r3.get()     // Catch:{ HttpException -> 0x09af }
            goto L_0x079a
        L_0x091a:
            r3 = 0
            r1._chunkLength = r3     // Catch:{ HttpException -> 0x09b3 }
            r1._chunkPosition = r3     // Catch:{ HttpException -> 0x09b3 }
            r3 = 4
            r1._state = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x079a
        L_0x0924:
            r8 = 10
        L_0x0926:
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            byte r3 = r3.get()     // Catch:{ HttpException -> 0x09af }
            r1._eol = r3     // Catch:{ HttpException -> 0x09af }
            goto L_0x079a
        L_0x0930:
            long r4 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            long r7 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            long r4 = r4 - r7
            r10 = 0
            int r2 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r2 != 0) goto L_0x094c
            boolean r2 = r1._persistent     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x0941
            r2 = 0
            goto L_0x0942
        L_0x0941:
            r2 = 7
        L_0x0942:
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            r2.messageComplete(r3)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x094c:
            long r7 = (long) r3     // Catch:{ HttpException -> 0x09af }
            int r2 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0952
            int r3 = (int) r4     // Catch:{ HttpException -> 0x09af }
        L_0x0952:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r2 = r2.get(r3)     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            int r5 = r2.length()     // Catch:{ HttpException -> 0x09af }
            long r7 = (long) r5     // Catch:{ HttpException -> 0x09af }
            long r3 = r3 + r7
            r1._contentPosition = r3     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View r3 = r1._contentView     // Catch:{ HttpException -> 0x09af }
            r3.update(r2)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r3 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r3.content(r2)     // Catch:{ HttpException -> 0x09af }
            long r2 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            long r4 = r1._contentLength     // Catch:{ HttpException -> 0x09af }
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 != 0) goto L_0x0984
            boolean r2 = r1._persistent     // Catch:{ HttpException -> 0x09af }
            if (r2 == 0) goto L_0x097a
            r2 = 0
            goto L_0x097b
        L_0x097a:
            r2 = 7
        L_0x097b:
            r1._state = r2     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r2 = r1._handler     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            r2.messageComplete(r3)     // Catch:{ HttpException -> 0x09af }
        L_0x0984:
            return r6
        L_0x0985:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.length()     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.Buffer r2 = r2.get(r3)     // Catch:{ HttpException -> 0x09af }
            long r3 = r1._contentPosition     // Catch:{ HttpException -> 0x09af }
            int r5 = r2.length()     // Catch:{ HttpException -> 0x09af }
            long r7 = (long) r5     // Catch:{ HttpException -> 0x09af }
            long r3 = r3 + r7
            r1._contentPosition = r3     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.io.View r3 = r1._contentView     // Catch:{ HttpException -> 0x09af }
            r3.update(r2)     // Catch:{ HttpException -> 0x09af }
            org.eclipse.jetty.http.HttpParser$EventHandler r3 = r1._handler     // Catch:{ HttpException -> 0x09af }
            r3.content(r2)     // Catch:{ HttpException -> 0x09af }
            return r6
        L_0x09a6:
            org.eclipse.jetty.io.Buffer r3 = r1._buffer     // Catch:{ HttpException -> 0x09af }
            int r3 = r3.length()     // Catch:{ HttpException -> 0x09af }
            goto L_0x0762
        L_0x09ae:
            return r9
        L_0x09af:
            r0 = move-exception
            r2 = r0
            r3 = 0
            goto L_0x09b5
        L_0x09b3:
            r0 = move-exception
            r2 = r0
        L_0x09b5:
            r1._persistent = r3
            r3 = 7
            r1._state = r3
            goto L_0x09bc
        L_0x09bb:
            throw r2
        L_0x09bc:
            goto L_0x09bb
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.http.HttpParser.parseNext():int");
    }

    /* access modifiers changed from: protected */
    public int fill() throws IOException {
        Buffer buffer;
        Buffer buffer2;
        if (this._buffer == null) {
            this._buffer = getHeaderBuffer();
        }
        if (this._state <= 0 || this._buffer != (buffer = this._header) || buffer == null || buffer.hasContent() || (buffer2 = this._body) == null || !buffer2.hasContent()) {
            Buffer buffer3 = this._buffer;
            Buffer buffer4 = this._header;
            if (buffer3 == buffer4 && this._state > 0 && buffer4.length() == 0 && ((this._forceContentBuffer || this._contentLength - this._contentPosition > ((long) this._header.capacity())) && !(this._body == null && this._buffers == null))) {
                if (this._body == null) {
                    this._body = this._buffers.getBuffer();
                }
                this._buffer = this._body;
            }
            if (this._endp == null) {
                return -1;
            }
            if (this._buffer == this._body || this._state > 0) {
                this._buffer.compact();
            }
            if (this._buffer.space() == 0) {
                LOG.warn("HttpParser Full for {} ", this._endp);
                this._buffer.clear();
                StringBuilder sb = new StringBuilder();
                sb.append("Request Entity Too Large: ");
                sb.append(this._buffer == this._body ? AgooConstants.MESSAGE_BODY : "head");
                throw new HttpException(413, sb.toString());
            }
            try {
                return this._endp.fill(this._buffer);
            } catch (IOException e) {
                e = e;
                LOG.debug(e);
                if (!(e instanceof EofException)) {
                    e = new EofException((Throwable) e);
                }
                throw e;
            }
        } else {
            this._buffer = this._body;
            return this._buffer.length();
        }
    }

    public void reset() {
        Buffer buffer;
        View view = this._contentView;
        view.setGetIndex(view.putIndex());
        this._state = this._persistent ? -14 : this._endp.isInputShutdown() ? 0 : 7;
        this._contentLength = -3;
        this._contentPosition = 0;
        this._length = 0;
        this._responseStatus = 0;
        if (this._eol == 13 && (buffer = this._buffer) != null && buffer.hasContent() && this._buffer.peek() == 10) {
            this._eol = this._buffer.get();
        }
        Buffer buffer2 = this._body;
        if (buffer2 != null && buffer2.hasContent()) {
            Buffer buffer3 = this._header;
            if (buffer3 == null) {
                getHeaderBuffer();
            } else {
                buffer3.setMarkIndex(-1);
                this._header.compact();
            }
            int space = this._header.space();
            if (space > this._body.length()) {
                space = this._body.length();
            }
            Buffer buffer4 = this._body;
            buffer4.peek(buffer4.getIndex(), space);
            Buffer buffer5 = this._body;
            buffer5.skip(this._header.put(buffer5.peek(buffer5.getIndex(), space)));
        }
        Buffer buffer6 = this._header;
        if (buffer6 != null) {
            buffer6.setMarkIndex(-1);
            this._header.compact();
        }
        Buffer buffer7 = this._body;
        if (buffer7 != null) {
            buffer7.setMarkIndex(-1);
        }
        this._buffer = this._header;
        returnBuffers();
    }

    public void returnBuffers() {
        Buffer buffer = this._body;
        if (buffer != null && !buffer.hasContent() && this._body.markIndex() == -1 && this._buffers != null) {
            if (this._buffer == this._body) {
                this._buffer = this._header;
            }
            Buffers buffers = this._buffers;
            if (buffers != null) {
                buffers.returnBuffer(this._body);
            }
            this._body = null;
        }
        Buffer buffer2 = this._header;
        if (buffer2 != null && !buffer2.hasContent() && this._header.markIndex() == -1 && this._buffers != null) {
            if (this._buffer == this._header) {
                this._buffer = null;
            }
            this._buffers.returnBuffer(this._header);
            this._header = null;
        }
    }

    public void setState(int i) {
        this._state = i;
        this._contentLength = -3;
    }

    public String toString(Buffer buffer) {
        return "state=" + this._state + " length=" + this._length + " buf=" + buffer.hashCode();
    }

    public String toString() {
        return String.format("%s{s=%d,l=%d,c=%d}", new Object[]{getClass().getSimpleName(), Integer.valueOf(this._state), Integer.valueOf(this._length), Long.valueOf(this._contentLength)});
    }

    public Buffer getHeaderBuffer() {
        if (this._header == null) {
            this._header = this._buffers.getHeader();
            this._tok0.update(this._header);
            this._tok1.update(this._header);
        }
        return this._header;
    }

    public Buffer getBodyBuffer() {
        return this._body;
    }

    public void setForceContentBuffer(boolean z) {
        this._forceContentBuffer = z;
    }

    public Buffer blockForContent(long j) throws IOException {
        if (this._contentView.length() > 0) {
            return this._contentView;
        }
        if (getState() <= 0 || isState(7)) {
            return null;
        }
        try {
            parseNext();
            while (this._contentView.length() == 0 && !isState(0) && !isState(7) && this._endp != null && this._endp.isOpen()) {
                if (!this._endp.isBlocking()) {
                    if (parseNext() <= 0) {
                        if (!this._endp.blockReadable(j)) {
                            this._endp.close();
                            throw new EofException("timeout");
                        }
                    }
                }
                parseNext();
            }
            if (this._contentView.length() > 0) {
                return this._contentView;
            }
            return null;
        } catch (IOException e) {
            this._endp.close();
            throw e;
        }
    }

    public int available() throws IOException {
        View view = this._contentView;
        if (view != null && view.length() > 0) {
            return this._contentView.length();
        }
        if (!this._endp.isBlocking()) {
            parseNext();
            View view2 = this._contentView;
            if (view2 == null) {
                return 0;
            }
            return view2.length();
        } else if (this._state <= 0) {
            return 0;
        } else {
            EndPoint endPoint = this._endp;
            if (!(endPoint instanceof StreamEndPoint) || ((StreamEndPoint) endPoint).getInputStream().available() <= 0) {
                return 0;
            }
            return 1;
        }
    }
}
