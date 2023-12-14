package org.eclipse.jetty.websocket;

import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.WebSocketParser;

public class WebSocketParserD06 implements WebSocketParser {
    private static final Logger LOG = Log.getLogger((Class<?>) WebSocketParserD06.class);
    private Buffer _buffer;
    private final WebSocketBuffers _buffers;
    private int _bytesNeeded;
    private final EndPoint _endp;
    private byte _flags;
    private final WebSocketParser.FrameHandler _handler;
    private long _length;

    /* renamed from: _m */
    private int f4122_m;
    private final byte[] _mask = new byte[4];
    private final boolean _masked;
    private byte _opcode;
    private State _state;

    public enum State {
        START(0),
        MASK(4),
        OPCODE(1),
        LENGTH_7(1),
        LENGTH_16(2),
        LENGTH_63(8),
        DATA(0),
        SKIP(1);
        
        int _needs;

        private State(int i) {
            this._needs = i;
        }

        /* access modifiers changed from: package-private */
        public int getNeeds() {
            return this._needs;
        }
    }

    public WebSocketParserD06(WebSocketBuffers webSocketBuffers, EndPoint endPoint, WebSocketParser.FrameHandler frameHandler, boolean z) {
        this._buffers = webSocketBuffers;
        this._endp = endPoint;
        this._handler = frameHandler;
        this._masked = z;
        this._state = State.START;
    }

    public boolean isBufferEmpty() {
        Buffer buffer = this._buffer;
        return buffer == null || buffer.length() == 0;
    }

    public Buffer getBuffer() {
        return this._buffer;
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x000f A[LOOP:0: B:4:0x000f->B:104:0x000f, LOOP_END, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0283 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int parseNext() {
        /*
            r15 = this;
            org.eclipse.jetty.io.Buffer r0 = r15._buffer
            if (r0 != 0) goto L_0x000c
            org.eclipse.jetty.websocket.WebSocketBuffers r0 = r15._buffers
            org.eclipse.jetty.io.Buffer r0 = r0.getBuffer()
            r15._buffer = r0
        L_0x000c:
            r0 = 0
            r1 = 0
            r2 = 0
        L_0x000f:
            org.eclipse.jetty.io.Buffer r3 = r15._buffer
            int r3 = r3.length()
        L_0x0015:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            org.eclipse.jetty.websocket.WebSocketParserD06$State r5 = org.eclipse.jetty.websocket.WebSocketParserD06.State.SKIP
            r6 = 1
            if (r4 != r5) goto L_0x001e
            r4 = 1
            goto L_0x0020
        L_0x001e:
            int r4 = r15._bytesNeeded
        L_0x0020:
            java.lang.String r5 = ">"
            if (r3 >= r4) goto L_0x0091
            org.eclipse.jetty.io.Buffer r3 = r15._buffer
            r3.compact()
            org.eclipse.jetty.io.Buffer r3 = r15._buffer
            int r3 = r3.space()
            if (r3 == 0) goto L_0x0062
            r3 = -1
            org.eclipse.jetty.io.EndPoint r4 = r15._endp     // Catch:{ IOException -> 0x0055 }
            boolean r4 = r4.isOpen()     // Catch:{ IOException -> 0x0055 }
            if (r4 == 0) goto L_0x0043
            org.eclipse.jetty.io.EndPoint r4 = r15._endp     // Catch:{ IOException -> 0x0055 }
            org.eclipse.jetty.io.Buffer r5 = r15._buffer     // Catch:{ IOException -> 0x0055 }
            int r4 = r4.fill(r5)     // Catch:{ IOException -> 0x0055 }
            goto L_0x0044
        L_0x0043:
            r4 = -1
        L_0x0044:
            if (r4 > 0) goto L_0x004d
            int r0 = r1 + r2
            if (r0 <= 0) goto L_0x004b
            goto L_0x004c
        L_0x004b:
            r0 = r4
        L_0x004c:
            return r0
        L_0x004d:
            int r1 = r1 + r4
            org.eclipse.jetty.io.Buffer r4 = r15._buffer     // Catch:{ IOException -> 0x0055 }
            int r3 = r4.length()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0015
        L_0x0055:
            r0 = move-exception
            org.eclipse.jetty.util.log.Logger r4 = LOG
            r4.debug(r0)
            int r0 = r1 + r2
            if (r0 <= 0) goto L_0x0060
            goto L_0x0061
        L_0x0060:
            r0 = -1
        L_0x0061:
            return r0
        L_0x0062:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "FULL: "
            r1.append(r2)
            org.eclipse.jetty.websocket.WebSocketParserD06$State r2 = r15._state
            r1.append(r2)
            java.lang.String r2 = " "
            r1.append(r2)
            int r2 = r15._bytesNeeded
            r1.append(r2)
            r1.append(r5)
            org.eclipse.jetty.io.Buffer r2 = r15._buffer
            int r2 = r2.capacity()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0091:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            org.eclipse.jetty.websocket.WebSocketParserD06$State r7 = org.eclipse.jetty.websocket.WebSocketParserD06.State.DATA
            r8 = 4
            if (r4 == r7) goto L_0x0279
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            org.eclipse.jetty.websocket.WebSocketParserD06$State r7 = org.eclipse.jetty.websocket.WebSocketParserD06.State.SKIP
            if (r4 != r7) goto L_0x00a0
            r4 = 1
            goto L_0x00a2
        L_0x00a0:
            int r4 = r15._bytesNeeded
        L_0x00a2:
            if (r3 < r4) goto L_0x0279
            int[] r4 = org.eclipse.jetty.websocket.WebSocketParserD06.C24731.$SwitchMap$org$eclipse$jetty$websocket$WebSocketParserD06$State
            org.eclipse.jetty.websocket.WebSocketParserD06$State r7 = r15._state
            int r7 = r7.ordinal()
            r4 = r4[r7]
            java.lang.String r7 = "frame size "
            r9 = 1004(0x3ec, float:1.407E-42)
            r10 = 256(0x100, double:1.265E-321)
            switch(r4) {
                case 1: goto L_0x0264;
                case 2: goto L_0x024b;
                case 3: goto L_0x01f9;
                case 4: goto L_0x01a6;
                case 5: goto L_0x013c;
                case 6: goto L_0x00d2;
                case 7: goto L_0x00b8;
                default: goto L_0x00b7;
            }
        L_0x00b7:
            goto L_0x0091
        L_0x00b8:
            int r4 = r15._bytesNeeded
            int r4 = java.lang.Math.min(r3, r4)
            org.eclipse.jetty.io.Buffer r7 = r15._buffer
            r7.skip(r4)
            int r3 = r3 - r4
            int r7 = r15._bytesNeeded
            int r7 = r7 - r4
            r15._bytesNeeded = r7
            int r4 = r15._bytesNeeded
            if (r4 != 0) goto L_0x0091
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.START
            r15._state = r4
            goto L_0x0091
        L_0x00d2:
            org.eclipse.jetty.io.Buffer r4 = r15._buffer
            byte r4 = r4.get()
            int r3 = r3 + -1
            boolean r12 = r15._masked
            if (r12 == 0) goto L_0x00eb
            byte[] r12 = r15._mask
            int r13 = r15.f4122_m
            int r14 = r13 + 1
            r15.f4122_m = r14
            int r13 = r13 % r8
            byte r8 = r12[r13]
            r4 = r4 ^ r8
            byte r4 = (byte) r4
        L_0x00eb:
            long r12 = r15._length
            long r12 = r12 * r10
            r4 = r4 & 255(0xff, float:3.57E-43)
            long r10 = (long) r4
            long r12 = r12 + r10
            r15._length = r12
            int r4 = r15._bytesNeeded
            int r4 = r4 - r6
            r15._bytesNeeded = r4
            if (r4 != 0) goto L_0x0091
            long r10 = r15._length
            int r4 = (int) r10
            r15._bytesNeeded = r4
            org.eclipse.jetty.io.Buffer r4 = r15._buffer
            int r4 = r4.capacity()
            long r12 = (long) r4
            int r4 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r4 < 0) goto L_0x0136
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.SKIP
            r15._state = r4
            int r2 = r2 + 1
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r4 = r15._handler
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            long r10 = r15._length
            r8.append(r10)
            r8.append(r5)
            org.eclipse.jetty.io.Buffer r7 = r15._buffer
            int r7 = r7.capacity()
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            r4.close(r9, r7)
            goto L_0x0091
        L_0x0136:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.DATA
            r15._state = r4
            goto L_0x0091
        L_0x013c:
            org.eclipse.jetty.io.Buffer r4 = r15._buffer
            byte r4 = r4.get()
            int r3 = r3 + -1
            boolean r12 = r15._masked
            if (r12 == 0) goto L_0x0155
            byte[] r12 = r15._mask
            int r13 = r15.f4122_m
            int r14 = r13 + 1
            r15.f4122_m = r14
            int r13 = r13 % r8
            byte r8 = r12[r13]
            r4 = r4 ^ r8
            byte r4 = (byte) r4
        L_0x0155:
            long r12 = r15._length
            long r12 = r12 * r10
            r4 = r4 & 255(0xff, float:3.57E-43)
            long r10 = (long) r4
            long r12 = r12 + r10
            r15._length = r12
            int r4 = r15._bytesNeeded
            int r4 = r4 - r6
            r15._bytesNeeded = r4
            if (r4 != 0) goto L_0x0091
            long r10 = r15._length
            int r4 = (int) r10
            r15._bytesNeeded = r4
            org.eclipse.jetty.io.Buffer r4 = r15._buffer
            int r4 = r4.capacity()
            long r12 = (long) r4
            int r4 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r4 <= 0) goto L_0x01a0
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.SKIP
            r15._state = r4
            int r2 = r2 + 1
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r4 = r15._handler
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            long r10 = r15._length
            r8.append(r10)
            r8.append(r5)
            org.eclipse.jetty.io.Buffer r7 = r15._buffer
            int r7 = r7.capacity()
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            r4.close(r9, r7)
            goto L_0x0091
        L_0x01a0:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.DATA
            r15._state = r4
            goto L_0x0091
        L_0x01a6:
            org.eclipse.jetty.io.Buffer r4 = r15._buffer
            byte r4 = r4.get()
            int r3 = r3 + -1
            boolean r7 = r15._masked
            if (r7 == 0) goto L_0x01bf
            byte[] r7 = r15._mask
            int r9 = r15.f4122_m
            int r10 = r9 + 1
            r15.f4122_m = r10
            int r9 = r9 % r8
            byte r7 = r7[r9]
            r4 = r4 ^ r7
            byte r4 = (byte) r4
        L_0x01bf:
            r7 = 126(0x7e, float:1.77E-43)
            r8 = 0
            if (r4 == r7) goto L_0x01e9
            r7 = 127(0x7f, float:1.78E-43)
            if (r4 == r7) goto L_0x01d9
            r4 = r4 & 127(0x7f, float:1.78E-43)
            long r7 = (long) r4
            r15._length = r7
            long r7 = r15._length
            int r4 = (int) r7
            r15._bytesNeeded = r4
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.DATA
            r15._state = r4
            goto L_0x0091
        L_0x01d9:
            r15._length = r8
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.LENGTH_63
            r15._state = r4
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            int r4 = r4.getNeeds()
            r15._bytesNeeded = r4
            goto L_0x0091
        L_0x01e9:
            r15._length = r8
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.LENGTH_16
            r15._state = r4
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            int r4 = r4.getNeeds()
            r15._bytesNeeded = r4
            goto L_0x0091
        L_0x01f9:
            org.eclipse.jetty.io.Buffer r4 = r15._buffer
            byte r4 = r4.get()
            int r3 = r3 + -1
            boolean r7 = r15._masked
            if (r7 == 0) goto L_0x0212
            byte[] r7 = r15._mask
            int r9 = r15.f4122_m
            int r10 = r9 + 1
            r15.f4122_m = r10
            int r9 = r9 % r8
            byte r7 = r7[r9]
            r4 = r4 ^ r7
            byte r4 = (byte) r4
        L_0x0212:
            r7 = r4 & 15
            byte r7 = (byte) r7
            r15._opcode = r7
            int r4 = r4 >> r8
            r4 = r4 & 15
            byte r4 = (byte) r4
            r15._flags = r4
            byte r4 = r15._opcode
            boolean r4 = org.eclipse.jetty.websocket.WebSocketConnectionD06.isControlFrame(r4)
            if (r4 == 0) goto L_0x023d
            byte r4 = r15._flags
            boolean r4 = org.eclipse.jetty.websocket.WebSocketConnectionD06.isLastFrame(r4)
            if (r4 != 0) goto L_0x023d
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.SKIP
            r15._state = r4
            int r2 = r2 + 1
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r4 = r15._handler
            r7 = 1002(0x3ea, float:1.404E-42)
            java.lang.String r8 = "fragmented control"
            r4.close(r7, r8)
            goto L_0x0241
        L_0x023d:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.LENGTH_7
            r15._state = r4
        L_0x0241:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            int r4 = r4.getNeeds()
            r15._bytesNeeded = r4
            goto L_0x0091
        L_0x024b:
            org.eclipse.jetty.io.Buffer r4 = r15._buffer
            byte[] r7 = r15._mask
            r4.get(r7, r0, r8)
            int r3 = r3 + -4
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.OPCODE
            r15._state = r4
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            int r4 = r4.getNeeds()
            r15._bytesNeeded = r4
            r15.f4122_m = r0
            goto L_0x0091
        L_0x0264:
            boolean r4 = r15._masked
            if (r4 == 0) goto L_0x026b
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.MASK
            goto L_0x026d
        L_0x026b:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = org.eclipse.jetty.websocket.WebSocketParserD06.State.OPCODE
        L_0x026d:
            r15._state = r4
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            int r4 = r4.getNeeds()
            r15._bytesNeeded = r4
            goto L_0x0091
        L_0x0279:
            org.eclipse.jetty.websocket.WebSocketParserD06$State r4 = r15._state
            org.eclipse.jetty.websocket.WebSocketParserD06$State r5 = org.eclipse.jetty.websocket.WebSocketParserD06.State.DATA
            if (r4 != r5) goto L_0x000f
            int r4 = r15._bytesNeeded
            if (r3 < r4) goto L_0x000f
            org.eclipse.jetty.io.Buffer r3 = r15._buffer
            org.eclipse.jetty.io.Buffer r3 = r3.get(r4)
            boolean r4 = r15._masked
            if (r4 == 0) goto L_0x02bb
            byte[] r4 = r3.array()
            if (r4 != 0) goto L_0x0299
            org.eclipse.jetty.io.Buffer r3 = r15._buffer
            org.eclipse.jetty.io.Buffer r3 = r3.asMutableBuffer()
        L_0x0299:
            byte[] r4 = r3.array()
            int r5 = r3.putIndex()
            int r7 = r3.getIndex()
        L_0x02a5:
            if (r7 >= r5) goto L_0x02bb
            byte r9 = r4[r7]
            byte[] r10 = r15._mask
            int r11 = r15.f4122_m
            int r12 = r11 + 1
            r15.f4122_m = r12
            int r11 = r11 % r8
            byte r10 = r10[r11]
            r9 = r9 ^ r10
            byte r9 = (byte) r9
            r4[r7] = r9
            int r7 = r7 + 1
            goto L_0x02a5
        L_0x02bb:
            int r2 = r2 + r6
            org.eclipse.jetty.websocket.WebSocketParser$FrameHandler r4 = r15._handler
            byte r5 = r15._flags
            byte r6 = r15._opcode
            r4.onFrame(r5, r6, r3)
            r15._bytesNeeded = r0
            org.eclipse.jetty.websocket.WebSocketParserD06$State r0 = org.eclipse.jetty.websocket.WebSocketParserD06.State.START
            r15._state = r0
            org.eclipse.jetty.io.Buffer r0 = r15._buffer
            int r0 = r0.length()
            if (r0 != 0) goto L_0x02dd
            org.eclipse.jetty.websocket.WebSocketBuffers r0 = r15._buffers
            org.eclipse.jetty.io.Buffer r3 = r15._buffer
            r0.returnBuffer(r3)
            r0 = 0
            r15._buffer = r0
        L_0x02dd:
            int r1 = r1 + r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.websocket.WebSocketParserD06.parseNext():int");
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
