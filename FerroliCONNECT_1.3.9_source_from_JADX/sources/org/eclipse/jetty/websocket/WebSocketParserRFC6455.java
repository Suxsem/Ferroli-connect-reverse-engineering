package org.eclipse.jetty.websocket;

import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.WebSocketParser;

public class WebSocketParserRFC6455 implements WebSocketParser {
    private static final Logger LOG = Log.getLogger((Class<?>) WebSocketParserRFC6455.class);
    private Buffer _buffer;
    private final WebSocketBuffers _buffers;
    private int _bytesNeeded;
    private final EndPoint _endp;
    private byte _flags;
    private boolean _fragmentFrames = true;
    private final WebSocketParser.FrameHandler _handler;
    private long _length;

    /* renamed from: _m */
    private int f4124_m;
    private final byte[] _mask = new byte[4];
    private boolean _masked;
    private byte _opcode;
    private final boolean _shouldBeMasked;
    private boolean _skip;
    private State _state;

    public enum State {
        START(0),
        OPCODE(1),
        LENGTH_7(1),
        LENGTH_16(2),
        LENGTH_63(8),
        MASK(4),
        PAYLOAD(0),
        DATA(0),
        SKIP(1),
        SEEK_EOF(1);
        
        int _needs;

        private State(int i) {
            this._needs = i;
        }

        /* access modifiers changed from: package-private */
        public int getNeeds() {
            return this._needs;
        }
    }

    public WebSocketParserRFC6455(WebSocketBuffers webSocketBuffers, EndPoint endPoint, WebSocketParser.FrameHandler frameHandler, boolean z) {
        this._buffers = webSocketBuffers;
        this._endp = endPoint;
        this._handler = frameHandler;
        this._shouldBeMasked = z;
        this._state = State.START;
    }

    public boolean isFakeFragments() {
        return this._fragmentFrames;
    }

    public void setFakeFragments(boolean z) {
        this._fragmentFrames = z;
    }

    public boolean isBufferEmpty() {
        Buffer buffer = this._buffer;
        return buffer == null || buffer.length() == 0;
    }

    public Buffer getBuffer() {
        return this._buffer;
    }

    /* JADX WARNING: Removed duplicated region for block: B:143:0x0384 A[LOOP:0: B:4:0x0011->B:143:0x0384, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0322 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int parseNext() {
        /*
            r16 = this;
            r1 = r16
            org.eclipse.jetty.io.Buffer r0 = r1._buffer
            if (r0 != 0) goto L_0x000e
            org.eclipse.jetty.websocket.WebSocketBuffers r0 = r1._buffers
            org.eclipse.jetty.io.Buffer r0 = r0.getBuffer()
            r1._buffer = r0
        L_0x000e:
            r3 = 0
            r0 = 0
            r4 = -1
        L_0x0011:
            r5 = 1
            if (r0 != 0) goto L_0x0388
            org.eclipse.jetty.io.EndPoint r6 = r1._endp
            boolean r6 = r6.isInputShutdown()
            if (r6 == 0) goto L_0x0024
            org.eclipse.jetty.io.Buffer r6 = r1._buffer
            int r6 = r6.length()
            if (r6 <= 0) goto L_0x0388
        L_0x0024:
            org.eclipse.jetty.io.Buffer r6 = r1._buffer
            int r6 = r6.length()
        L_0x002a:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = r1._state
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r8 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.SKIP
            if (r7 != r8) goto L_0x0032
            r7 = 1
            goto L_0x0034
        L_0x0032:
            int r7 = r1._bytesNeeded
        L_0x0034:
            java.lang.String r8 = ">"
            r9 = 4
            if (r6 >= r7) goto L_0x010d
            org.eclipse.jetty.io.Buffer r4 = r1._buffer
            r4.compact()
            org.eclipse.jetty.io.Buffer r4 = r1._buffer
            int r4 = r4.space()
            if (r4 != 0) goto L_0x00e1
            boolean r4 = r1._fragmentFrames
            if (r4 == 0) goto L_0x00a9
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r4 = r1._state
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.DATA
            if (r4 != r7) goto L_0x00a9
            org.eclipse.jetty.io.Buffer r0 = r1._buffer
            int r4 = r6 / 4
            int r4 = r4 * 4
            org.eclipse.jetty.io.Buffer r0 = r0.get(r4)
            org.eclipse.jetty.io.Buffer r4 = r1._buffer
            r4.compact()
            boolean r4 = r1._masked
            if (r4 == 0) goto L_0x0091
            byte[] r4 = r0.array()
            if (r4 != 0) goto L_0x006f
            org.eclipse.jetty.io.Buffer r0 = r1._buffer
            org.eclipse.jetty.io.Buffer r0 = r0.asMutableBuffer()
        L_0x006f:
            byte[] r4 = r0.array()
            int r7 = r0.putIndex()
            int r10 = r0.getIndex()
        L_0x007b:
            if (r10 >= r7) goto L_0x0091
            byte r11 = r4[r10]
            byte[] r12 = r1._mask
            int r13 = r1.f4124_m
            int r14 = r13 + 1
            r1.f4124_m = r14
            int r13 = r13 % r9
            byte r12 = r12[r13]
            r11 = r11 ^ r12
            byte r11 = (byte) r11
            r4[r10] = r11
            int r10 = r10 + 1
            goto L_0x007b
        L_0x0091:
            int r4 = r1._bytesNeeded
            int r7 = r0.length()
            int r4 = r4 - r7
            r1._bytesNeeded = r4
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r4 = r1._handler
            byte r7 = r1._flags
            r7 = r7 & 247(0xf7, float:3.46E-43)
            byte r7 = (byte) r7
            byte r10 = r1._opcode
            r4.onFrame(r7, r10, r0)
            r1._opcode = r3
            r0 = 1
        L_0x00a9:
            org.eclipse.jetty.io.Buffer r4 = r1._buffer
            int r4 = r4.space()
            if (r4 == 0) goto L_0x00b2
            goto L_0x00e1
        L_0x00b2:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "FULL: "
            r2.append(r3)
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r3 = r1._state
            r2.append(r3)
            java.lang.String r3 = " "
            r2.append(r3)
            int r3 = r1._bytesNeeded
            r2.append(r3)
            r2.append(r8)
            org.eclipse.jetty.io.Buffer r3 = r1._buffer
            int r3 = r3.capacity()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x00e1:
            r4 = r0
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ IOException -> 0x0105 }
            boolean r0 = r0.isInputShutdown()     // Catch:{ IOException -> 0x0105 }
            if (r0 == 0) goto L_0x00ec
            r0 = -1
            goto L_0x00f4
        L_0x00ec:
            org.eclipse.jetty.io.EndPoint r0 = r1._endp     // Catch:{ IOException -> 0x0105 }
            org.eclipse.jetty.io.Buffer r7 = r1._buffer     // Catch:{ IOException -> 0x0105 }
            int r0 = r0.fill(r7)     // Catch:{ IOException -> 0x0105 }
        L_0x00f4:
            org.eclipse.jetty.io.Buffer r7 = r1._buffer     // Catch:{ IOException -> 0x0105 }
            int r6 = r7.length()     // Catch:{ IOException -> 0x0105 }
            if (r0 > 0) goto L_0x0100
            r15 = r4
            r4 = r0
            r0 = r15
            goto L_0x010d
        L_0x0100:
            r15 = r4
            r4 = r0
            r0 = r15
            goto L_0x002a
        L_0x0105:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r7 = LOG
            r7.debug(r0)
            r0 = r4
            r4 = -1
        L_0x010d:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = r1._state
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r10 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.SKIP
            if (r7 != r10) goto L_0x0115
            r7 = 1
            goto L_0x0117
        L_0x0115:
            int r7 = r1._bytesNeeded
        L_0x0117:
            if (r6 >= r7) goto L_0x011b
            goto L_0x0388
        L_0x011b:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = r1._state
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r10 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.DATA
            r11 = 1002(0x3ea, float:1.404E-42)
            if (r7 == r10) goto L_0x0318
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = r1._state
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r10 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.SKIP
            if (r7 != r10) goto L_0x012b
            r7 = 1
            goto L_0x012d
        L_0x012b:
            int r7 = r1._bytesNeeded
        L_0x012d:
            if (r6 < r7) goto L_0x0318
            int[] r7 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.C24751.f4125xe4d7c314
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r10 = r1._state
            int r10 = r10.ordinal()
            r7 = r7[r10]
            java.lang.String r10 = "frame size "
            r12 = 1008(0x3f0, float:1.413E-42)
            r13 = 256(0x100, double:1.265E-321)
            switch(r7) {
                case 1: goto L_0x02fc;
                case 2: goto L_0x02a6;
                case 3: goto L_0x0261;
                case 4: goto L_0x01fc;
                case 5: goto L_0x0194;
                case 6: goto L_0x017b;
                case 7: goto L_0x0169;
                case 8: goto L_0x0314;
                case 9: goto L_0x014d;
                case 10: goto L_0x0144;
                default: goto L_0x0142;
            }
        L_0x0142:
            goto L_0x0314
        L_0x0144:
            org.eclipse.jetty.io.Buffer r0 = r1._buffer
            r0.skip(r6)
            r0 = 1
            r6 = 0
            goto L_0x0314
        L_0x014d:
            int r0 = r1._bytesNeeded
            int r0 = java.lang.Math.min(r6, r0)
            org.eclipse.jetty.io.Buffer r7 = r1._buffer
            r7.skip(r0)
            int r6 = r6 - r0
            int r7 = r1._bytesNeeded
            int r7 = r7 - r0
            r1._bytesNeeded = r7
            int r0 = r1._bytesNeeded
            if (r0 != 0) goto L_0x0166
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r0 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.START
            r1._state = r0
        L_0x0166:
            r0 = 1
            goto L_0x0314
        L_0x0169:
            long r10 = r1._length
            int r7 = (int) r10
            r1._bytesNeeded = r7
            boolean r7 = r1._skip
            if (r7 == 0) goto L_0x0175
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.SKIP
            goto L_0x0177
        L_0x0175:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.DATA
        L_0x0177:
            r1._state = r7
            goto L_0x0314
        L_0x017b:
            org.eclipse.jetty.io.Buffer r7 = r1._buffer
            byte[] r10 = r1._mask
            r7.get(r10, r3, r9)
            r1.f4124_m = r3
            int r6 = r6 + -4
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.PAYLOAD
            r1._state = r7
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = r1._state
            int r7 = r7.getNeeds()
            r1._bytesNeeded = r7
            goto L_0x0314
        L_0x0194:
            org.eclipse.jetty.io.Buffer r7 = r1._buffer
            byte r7 = r7.get()
            int r6 = r6 + -1
            long r2 = r1._length
            long r2 = r2 * r13
            r7 = r7 & 255(0xff, float:3.57E-43)
            long r13 = (long) r7
            long r2 = r2 + r13
            r1._length = r2
            int r2 = r1._bytesNeeded
            int r2 = r2 - r5
            r1._bytesNeeded = r2
            if (r2 != 0) goto L_0x01f9
            long r2 = r1._length
            int r7 = (int) r2
            r1._bytesNeeded = r7
            org.eclipse.jetty.io.Buffer r7 = r1._buffer
            int r7 = r7.capacity()
            long r13 = (long) r7
            int r7 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            if (r7 < 0) goto L_0x01e6
            boolean r2 = r1._fragmentFrames
            if (r2 != 0) goto L_0x01e6
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r0 = r1._handler
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r10)
            long r10 = r1._length
            r2.append(r10)
            r2.append(r8)
            org.eclipse.jetty.io.Buffer r3 = r1._buffer
            int r3 = r3.capacity()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.close(r12, r2)
            r1._skip = r5
            r0 = 1
        L_0x01e6:
            boolean r2 = r1._masked
            if (r2 == 0) goto L_0x01ed
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.MASK
            goto L_0x01ef
        L_0x01ed:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.PAYLOAD
        L_0x01ef:
            r1._state = r2
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = r1._state
            int r2 = r2.getNeeds()
            r1._bytesNeeded = r2
        L_0x01f9:
            r3 = 0
            goto L_0x011b
        L_0x01fc:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer
            byte r2 = r2.get()
            int r6 = r6 + -1
            r7 = r10
            long r9 = r1._length
            long r9 = r9 * r13
            r2 = r2 & 255(0xff, float:3.57E-43)
            long r13 = (long) r2
            long r9 = r9 + r13
            r1._length = r9
            int r2 = r1._bytesNeeded
            int r2 = r2 - r5
            r1._bytesNeeded = r2
            if (r2 != 0) goto L_0x0314
            long r9 = r1._length
            org.eclipse.jetty.io.Buffer r2 = r1._buffer
            int r2 = r2.capacity()
            long r13 = (long) r2
            int r2 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
            if (r2 <= 0) goto L_0x024c
            boolean r2 = r1._fragmentFrames
            if (r2 != 0) goto L_0x024c
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r0 = r1._handler
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r7)
            long r9 = r1._length
            r2.append(r9)
            r2.append(r8)
            org.eclipse.jetty.io.Buffer r7 = r1._buffer
            int r7 = r7.capacity()
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            r0.close(r12, r2)
            r1._skip = r5
            r0 = 1
        L_0x024c:
            boolean r2 = r1._masked
            if (r2 == 0) goto L_0x0253
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.MASK
            goto L_0x0255
        L_0x0253:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.PAYLOAD
        L_0x0255:
            r1._state = r2
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = r1._state
            int r2 = r2.getNeeds()
            r1._bytesNeeded = r2
            goto L_0x0314
        L_0x0261:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer
            byte r2 = r2.get()
            int r6 = r6 + -1
            r7 = r2 & 128(0x80, float:1.794E-43)
            if (r7 == 0) goto L_0x026f
            r7 = 1
            goto L_0x0270
        L_0x026f:
            r7 = 0
        L_0x0270:
            r1._masked = r7
            r2 = r2 & 127(0x7f, float:1.78E-43)
            byte r2 = (byte) r2
            r7 = 126(0x7e, float:1.77E-43)
            r9 = 0
            if (r2 == r7) goto L_0x0297
            r7 = 127(0x7f, float:1.78E-43)
            if (r2 == r7) goto L_0x0290
            r2 = r2 & 127(0x7f, float:1.78E-43)
            long r9 = (long) r2
            r1._length = r9
            boolean r2 = r1._masked
            if (r2 == 0) goto L_0x028b
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.MASK
            goto L_0x028d
        L_0x028b:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.PAYLOAD
        L_0x028d:
            r1._state = r2
            goto L_0x029d
        L_0x0290:
            r1._length = r9
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.LENGTH_63
            r1._state = r2
            goto L_0x029d
        L_0x0297:
            r1._length = r9
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.LENGTH_16
            r1._state = r2
        L_0x029d:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = r1._state
            int r2 = r2.getNeeds()
            r1._bytesNeeded = r2
            goto L_0x0314
        L_0x02a6:
            org.eclipse.jetty.io.Buffer r2 = r1._buffer
            byte r2 = r2.get()
            int r6 = r6 + -1
            r7 = r2 & 15
            byte r7 = (byte) r7
            r1._opcode = r7
            r3 = 4
            int r2 = r2 >> r3
            r2 = r2 & 15
            byte r2 = (byte) r2
            r1._flags = r2
            byte r2 = r1._opcode
            boolean r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.isControlFrame(r2)
            if (r2 == 0) goto L_0x02ef
            byte r2 = r1._flags
            boolean r2 = org.eclipse.jetty.websocket.WebSocketConnectionRFC6455.isLastFrame(r2)
            if (r2 != 0) goto L_0x02ef
            org.eclipse.jetty.util.log.Logger r0 = LOG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r7 = "Fragmented Control from "
            r2.append(r7)
            org.eclipse.jetty.io.EndPoint r7 = r1._endp
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            r7 = 0
            java.lang.Object[] r9 = new java.lang.Object[r7]
            r0.warn((java.lang.String) r2, (java.lang.Object[]) r9)
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r0 = r1._handler
            java.lang.String r2 = "Fragmented control"
            r0.close(r11, r2)
            r1._skip = r5
            r0 = 1
        L_0x02ef:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.LENGTH_7
            r1._state = r2
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = r1._state
            int r2 = r2.getNeeds()
            r1._bytesNeeded = r2
            goto L_0x0314
        L_0x02fc:
            r2 = 0
            r1._skip = r2
            byte r2 = r1._opcode
            r7 = 8
            if (r2 != r7) goto L_0x0308
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.SEEK_EOF
            goto L_0x030a
        L_0x0308:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.OPCODE
        L_0x030a:
            r1._state = r2
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = r1._state
            int r2 = r2.getNeeds()
            r1._bytesNeeded = r2
        L_0x0314:
            r3 = 0
            r9 = 4
            goto L_0x011b
        L_0x0318:
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r2 = r1._state
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r7 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.DATA
            if (r2 != r7) goto L_0x0384
            int r2 = r1._bytesNeeded
            if (r6 < r2) goto L_0x0384
            boolean r0 = r1._masked
            boolean r6 = r1._shouldBeMasked
            if (r0 == r6) goto L_0x033a
            org.eclipse.jetty.io.Buffer r0 = r1._buffer
            r0.skip(r2)
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r0 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.START
            r1._state = r0
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r0 = r1._handler
            java.lang.String r2 = "Not masked"
            r0.close(r11, r2)
        L_0x0338:
            r0 = 1
            goto L_0x0388
        L_0x033a:
            org.eclipse.jetty.io.Buffer r0 = r1._buffer
            org.eclipse.jetty.io.Buffer r0 = r0.get(r2)
            boolean r2 = r1._masked
            if (r2 == 0) goto L_0x0373
            byte[] r2 = r0.array()
            if (r2 != 0) goto L_0x0350
            org.eclipse.jetty.io.Buffer r0 = r1._buffer
            org.eclipse.jetty.io.Buffer r0 = r0.asMutableBuffer()
        L_0x0350:
            byte[] r2 = r0.array()
            int r6 = r0.putIndex()
            int r7 = r0.getIndex()
        L_0x035c:
            if (r7 >= r6) goto L_0x0373
            byte r8 = r2[r7]
            byte[] r9 = r1._mask
            int r10 = r1.f4124_m
            int r11 = r10 + 1
            r1.f4124_m = r11
            r3 = 4
            int r10 = r10 % r3
            byte r9 = r9[r10]
            r8 = r8 ^ r9
            byte r8 = (byte) r8
            r2[r7] = r8
            int r7 = r7 + 1
            goto L_0x035c
        L_0x0373:
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r2 = r1._handler
            byte r3 = r1._flags
            byte r6 = r1._opcode
            r2.onFrame(r3, r6, r0)
            r2 = 0
            r1._bytesNeeded = r2
            org.eclipse.jetty.websocket.WebSocketParserRFC6455$State r0 = org.eclipse.jetty.websocket.WebSocketParserRFC6455.State.START
            r1._state = r0
            goto L_0x0338
        L_0x0384:
            r2 = 0
            r3 = 0
            goto L_0x0011
        L_0x0388:
            if (r0 == 0) goto L_0x038b
            r4 = 1
        L_0x038b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketParserRFC6455.parseNext():int");
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

    public void returnBuffer() {
        Buffer buffer = this._buffer;
        if (buffer != null && buffer.length() == 0) {
            this._buffers.returnBuffer(this._buffer);
            this._buffer = null;
        }
    }

    public String toString() {
        return String.format("%s@%x state=%s buffer=%s", new Object[]{getClass().getSimpleName(), Integer.valueOf(hashCode()), this._state, this._buffer});
    }
}
