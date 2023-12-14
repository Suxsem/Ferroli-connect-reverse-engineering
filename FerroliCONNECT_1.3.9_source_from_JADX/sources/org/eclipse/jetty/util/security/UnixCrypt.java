package org.eclipse.jetty.util.security;

import java.io.PrintStream;
import java.lang.reflect.Array;
import kotlin.UByte;
import org.eclipse.jetty.http.HttpTokens;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public class UnixCrypt {
    private static final byte[] A64TOI = new byte[128];
    private static final long[][] CF6464 = ((long[][]) Array.newInstance(long.class, new int[]{16, 16}));
    private static final byte[] CIFP = {1, 2, 3, 4, 17, 18, 19, 20, 5, 6, 7, 8, 21, 22, 23, 24, 9, 10, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 25, 26, 27, 28, 13, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 15, 16, 29, 30, 31, HttpTokens.SPACE, 33, 34, 35, 36, 49, 50, 51, 52, 37, 38, 39, 40, 53, 54, 55, 56, 41, 42, 43, 44, 57, HttpTokens.COLON, HttpTokens.SEMI_COLON, 60, 45, 46, 47, 48, 61, 62, 63, 64};
    private static final byte[] ExpandTr = {HttpTokens.SPACE, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 13, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 13, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, HttpTokens.SPACE, 1};
    private static final long[][] IE3264 = ((long[][]) Array.newInstance(long.class, new int[]{8, 16}));

    /* renamed from: IP */
    private static final byte[] f4117IP = {HttpTokens.COLON, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4, 62, 54, 46, 38, 30, 22, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 6, 64, 56, 48, 40, HttpTokens.SPACE, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, HttpTokens.SEMI_COLON, 51, 43, 35, 27, 19, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
    private static final byte[] ITOA64 = {46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] P32Tr = {16, 7, 20, 21, 29, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, HttpTokens.SPACE, 27, 3, 9, 19, 13, 30, 6, 22, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 4, 25};
    private static final byte[] PC1 = {57, 49, 41, 33, 25, 17, 9, 1, HttpTokens.COLON, 50, 42, 34, 26, 18, 10, 2, HttpTokens.SEMI_COLON, 51, 43, 35, 27, 19, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4};
    private static final long[][] PC1ROT = ((long[][]) Array.newInstance(long.class, new int[]{16, 16}));
    private static final byte[] PC2 = {9, 18, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 17, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 24, 1, 5, 22, 25, 3, 28, 15, 6, 21, 10, 35, 38, 23, 19, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4, 26, 8, 43, 54, 16, 7, 27, 20, 13, 2, 0, 0, 41, 52, 31, 37, 47, 55, 0, 0, 30, 40, 51, 45, 33, 48, 0, 0, 44, 49, 39, 56, 34, 53, 0, 0, 46, 42, 50, 36, 29, HttpTokens.SPACE};
    private static final long[][][] PC2ROT = ((long[][][]) Array.newInstance(long.class, new int[]{2, 16, 16}));
    private static final byte[] Rotates = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    /* renamed from: S */
    private static final byte[][] f4118S = {new byte[]{MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 4, 13, 1, 2, 15, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 8, 3, 10, 6, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 5, 9, 0, 7, 0, 15, 7, 4, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 2, 13, 1, 10, 6, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 9, 5, 3, 8, 4, 1, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 8, 13, 6, 2, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 15, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 9, 7, 3, 10, 5, 0, 15, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 8, 2, 4, 9, 1, 7, 5, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 3, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 10, 0, 6, 13}, new byte[]{15, 1, 8, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 6, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 3, 4, 9, 7, 2, 13, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 0, 1, 10, 6, 9, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 5, 0, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 7, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 10, 4, 13, 1, 5, 8, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 6, 7, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 0, 5, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 9}, new byte[]{10, 0, 9, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 6, 3, 15, 5, 1, 13, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 7, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 15, 1, 13, 6, 4, 9, 8, 15, 3, 0, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 1, 2, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 5, 10, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 3, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 5, 2, MqttWireMessage.MESSAGE_TYPE_PINGREQ}, new byte[]{7, 13, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 3, 0, 6, 9, 10, 1, 2, 8, 5, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4, 15, 13, 8, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 5, 6, 15, 0, 3, 4, 7, 2, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 1, 10, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 9, 10, 6, 9, 0, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 7, 13, 15, 1, 3, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 7, 2, MqttWireMessage.MESSAGE_TYPE_DISCONNECT}, new byte[]{2, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4, 1, 7, 10, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 6, 8, 5, 3, 15, 13, 0, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 9, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 2, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 4, 2, 1, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 10, 13, 7, 8, 15, 9, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 5, 6, 3, 0, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 8, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 7, 1, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}, new byte[]{MqttWireMessage.MESSAGE_TYPE_PINGREQ, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 7, 5, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 10, 15, 4, 2, 7, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 9, 5, 6, 1, 13, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 0, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 3, 8, 9, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 15, 5, 2, 8, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 3, 7, 0, 4, 10, 1, 13, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 6, 4, 3, 2, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 9, 5, 15, 10, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 1, 7, 6, 0, 8, 13}, new byte[]{4, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 2, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 15, 0, 8, 13, 3, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 9, 7, 5, 10, 6, 1, 13, 0, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 7, 4, 9, 1, 10, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 3, 5, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 2, 15, 8, 6, 1, 4, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 13, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 3, 7, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 10, 15, 6, 8, 0, 5, 9, 2, 6, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 2, 3, MqttWireMessage.MESSAGE_TYPE_PINGREQ}, new byte[]{13, 2, 8, 4, 6, 15, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 1, 10, 9, 3, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 5, 0, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 7, 1, 15, 13, 8, 10, 3, 7, 4, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 5, 6, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 0, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 9, 2, 7, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 4, 1, 9, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, MqttWireMessage.MESSAGE_TYPE_DISCONNECT, 7, 4, 10, 8, 13, 15, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 9, 0, 3, 5, 6, MqttWireMessage.MESSAGE_TYPE_UNSUBACK}};
    private static final long[][] SPE = ((long[][]) Array.newInstance(long.class, new int[]{8, 64}));

    private static int to_six_bit(int i) {
        return ((i >> 16) & 252) | ((i << 26) & -67108864) | ((i << 12) & 16515072) | ((i >> 2) & 64512);
    }

    private static long to_six_bit(long j) {
        return ((j >> 16) & 1082331758844L) | ((j << 26) & -288230371923853312L) | ((j << 12) & 70931694147600384L) | ((j >> 2) & 277076930264064L);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v16, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v42, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v44, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v45, resolved type: byte} */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0182, code lost:
        if (r14 <= 0) goto L_0x0186;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0184, code lost:
        r14 = r14 - 1;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 64
            byte[] r1 = new byte[r0]
            r1 = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7} // fill-array
            f4117IP = r1
            r1 = 48
            byte[] r2 = new byte[r1]
            r2 = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1} // fill-array
            ExpandTr = r2
            r2 = 56
            byte[] r2 = new byte[r2]
            r2 = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4} // fill-array
            PC1 = r2
            r2 = 16
            byte[] r3 = new byte[r2]
            r3 = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1} // fill-array
            Rotates = r3
            byte[] r3 = new byte[r0]
            r3 = {9, 18, 14, 17, 11, 24, 1, 5, 22, 25, 3, 28, 15, 6, 21, 10, 35, 38, 23, 19, 12, 4, 26, 8, 43, 54, 16, 7, 27, 20, 13, 2, 0, 0, 41, 52, 31, 37, 47, 55, 0, 0, 30, 40, 51, 45, 33, 48, 0, 0, 44, 49, 39, 56, 34, 53, 0, 0, 46, 42, 50, 36, 29, 32} // fill-array
            PC2 = r3
            r3 = 8
            byte[][] r4 = new byte[r3][]
            byte[] r5 = new byte[r0]
            r5 = {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13} // fill-array
            r6 = 0
            r4[r6] = r5
            byte[] r5 = new byte[r0]
            r5 = {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9} // fill-array
            r7 = 1
            r4[r7] = r5
            byte[] r5 = new byte[r0]
            r5 = {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12} // fill-array
            r8 = 2
            r4[r8] = r5
            byte[] r5 = new byte[r0]
            r5 = {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14} // fill-array
            r9 = 3
            r4[r9] = r5
            byte[] r5 = new byte[r0]
            r5 = {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3} // fill-array
            r10 = 4
            r4[r10] = r5
            byte[] r5 = new byte[r0]
            r5 = {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13} // fill-array
            r11 = 5
            r4[r11] = r5
            byte[] r5 = new byte[r0]
            r5 = {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12} // fill-array
            r12 = 6
            r4[r12] = r5
            byte[] r5 = new byte[r0]
            r5 = {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11} // fill-array
            r12 = 7
            r4[r12] = r5
            f4118S = r4
            r4 = 32
            byte[] r5 = new byte[r4]
            r5 = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25} // fill-array
            P32Tr = r5
            byte[] r5 = new byte[r0]
            r5 = {1, 2, 3, 4, 17, 18, 19, 20, 5, 6, 7, 8, 21, 22, 23, 24, 9, 10, 11, 12, 25, 26, 27, 28, 13, 14, 15, 16, 29, 30, 31, 32, 33, 34, 35, 36, 49, 50, 51, 52, 37, 38, 39, 40, 53, 54, 55, 56, 41, 42, 43, 44, 57, 58, 59, 60, 45, 46, 47, 48, 61, 62, 63, 64} // fill-array
            CIFP = r5
            byte[] r5 = new byte[r0]
            r5 = {46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122} // fill-array
            ITOA64 = r5
            r5 = 128(0x80, float:1.794E-43)
            byte[] r5 = new byte[r5]
            A64TOI = r5
            int[] r5 = new int[]{r2, r2}
            java.lang.Class<long> r13 = long.class
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r13, r5)
            long[][] r5 = (long[][]) r5
            PC1ROT = r5
            int[] r5 = new int[]{r8, r2, r2}
            java.lang.Class<long> r13 = long.class
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r13, r5)
            long[][][] r5 = (long[][][]) r5
            PC2ROT = r5
            int[] r5 = new int[]{r3, r2}
            java.lang.Class<long> r13 = long.class
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r13, r5)
            long[][] r5 = (long[][]) r5
            IE3264 = r5
            int[] r5 = new int[]{r3, r0}
            java.lang.Class<long> r13 = long.class
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r13, r5)
            long[][] r5 = (long[][]) r5
            SPE = r5
            int[] r2 = new int[]{r2, r2}
            java.lang.Class<long> r5 = long.class
            java.lang.Object r2 = java.lang.reflect.Array.newInstance(r5, r2)
            long[][] r2 = (long[][]) r2
            CF6464 = r2
            byte[] r2 = new byte[r0]
            byte[] r5 = new byte[r0]
            r13 = 0
        L_0x00d9:
            if (r13 >= r0) goto L_0x00e8
            byte[] r14 = A64TOI
            byte[] r15 = ITOA64
            byte r15 = r15[r13]
            byte r10 = (byte) r13
            r14[r15] = r10
            int r13 = r13 + 1
            r10 = 4
            goto L_0x00d9
        L_0x00e8:
            r10 = 0
        L_0x00e9:
            if (r10 >= r0) goto L_0x00f0
            r2[r10] = r6
            int r10 = r10 + 1
            goto L_0x00e9
        L_0x00f0:
            r10 = 0
        L_0x00f1:
            if (r10 >= r0) goto L_0x011c
            byte[] r13 = PC2
            byte r13 = r13[r10]
            if (r13 != 0) goto L_0x00fa
            goto L_0x0119
        L_0x00fa:
            byte[] r14 = Rotates
            byte r15 = r14[r6]
            int r15 = r15 - r7
            int r13 = r13 + r15
            int r15 = r13 % 28
            byte r14 = r14[r6]
            if (r15 >= r14) goto L_0x0108
            int r13 = r13 + -28
        L_0x0108:
            byte[] r14 = PC1
            byte r13 = r14[r13]
            if (r13 <= 0) goto L_0x0116
            int r13 = r13 + -1
            r14 = r13 | 7
            r13 = r13 & r12
            int r14 = r14 - r13
            int r13 = r14 + 1
        L_0x0116:
            byte r13 = (byte) r13
            r2[r10] = r13
        L_0x0119:
            int r10 = r10 + 1
            goto L_0x00f1
        L_0x011c:
            long[][] r10 = PC1ROT
            init_perm(r10, r2, r3)
            r10 = 0
        L_0x0122:
            if (r10 >= r8) goto L_0x0164
            r13 = 0
        L_0x0125:
            if (r13 >= r0) goto L_0x012e
            r5[r13] = r6
            r2[r13] = r6
            int r13 = r13 + 1
            goto L_0x0125
        L_0x012e:
            r13 = 0
        L_0x012f:
            if (r13 >= r0) goto L_0x0142
            byte[] r14 = PC2
            byte r14 = r14[r13]
            if (r14 != 0) goto L_0x0138
            goto L_0x013f
        L_0x0138:
            int r14 = r14 + -1
            int r15 = r13 + 1
            byte r15 = (byte) r15
            r5[r14] = r15
        L_0x013f:
            int r13 = r13 + 1
            goto L_0x012f
        L_0x0142:
            r13 = 0
        L_0x0143:
            if (r13 >= r0) goto L_0x015a
            byte[] r14 = PC2
            byte r14 = r14[r13]
            if (r14 != 0) goto L_0x014c
            goto L_0x0157
        L_0x014c:
            int r14 = r14 + r10
            int r15 = r14 % 28
            if (r15 > r10) goto L_0x0153
            int r14 = r14 + -28
        L_0x0153:
            byte r14 = r5[r14]
            r2[r13] = r14
        L_0x0157:
            int r13 = r13 + 1
            goto L_0x0143
        L_0x015a:
            long[][][] r13 = PC2ROT
            r13 = r13[r10]
            init_perm(r13, r2, r3)
            int r10 = r10 + 1
            goto L_0x0122
        L_0x0164:
            r10 = 0
        L_0x0165:
            if (r10 >= r3) goto L_0x019c
            r13 = 0
        L_0x0168:
            if (r13 >= r3) goto L_0x0199
            if (r13 >= r8) goto L_0x016e
            r14 = 0
            goto L_0x017d
        L_0x016e:
            byte[] r14 = f4117IP
            byte[] r15 = ExpandTr
            int r17 = r10 * 6
            int r17 = r17 + r13
            int r17 = r17 + -2
            byte r15 = r15[r17]
            int r15 = r15 - r7
            byte r14 = r14[r15]
        L_0x017d:
            if (r14 <= r4) goto L_0x0182
            int r14 = r14 + -32
            goto L_0x0186
        L_0x0182:
            if (r14 <= 0) goto L_0x0186
            int r14 = r14 + -1
        L_0x0186:
            if (r14 <= 0) goto L_0x0190
            int r14 = r14 + -1
            r15 = r14 | 7
            r14 = r14 & r12
            int r15 = r15 - r14
            int r14 = r15 + 1
        L_0x0190:
            int r15 = r10 * 8
            int r15 = r15 + r13
            byte r14 = (byte) r14
            r2[r15] = r14
            int r13 = r13 + 1
            goto L_0x0168
        L_0x0199:
            int r10 = r10 + 1
            goto L_0x0165
        L_0x019c:
            long[][] r10 = IE3264
            init_perm(r10, r2, r3)
            r10 = 0
        L_0x01a2:
            if (r10 >= r0) goto L_0x01be
            byte[] r13 = f4117IP
            byte[] r14 = CIFP
            byte r14 = r14[r10]
            int r14 = r14 - r7
            byte r13 = r13[r14]
            if (r13 <= 0) goto L_0x01b7
            int r13 = r13 + -1
            r14 = r13 | 7
            r13 = r13 & r12
            int r14 = r14 - r13
            int r13 = r14 + 1
        L_0x01b7:
            int r13 = r13 - r7
            int r10 = r10 + 1
            byte r14 = (byte) r10
            r2[r13] = r14
            goto L_0x01a2
        L_0x01be:
            long[][] r10 = CF6464
            init_perm(r10, r2, r3)
            r10 = 0
        L_0x01c4:
            if (r10 >= r1) goto L_0x01d4
            byte[] r12 = P32Tr
            byte[] r13 = ExpandTr
            byte r13 = r13[r10]
            int r13 = r13 - r7
            byte r12 = r12[r13]
            r2[r10] = r12
            int r10 = r10 + 1
            goto L_0x01c4
        L_0x01d4:
            r1 = 0
        L_0x01d5:
            if (r1 >= r3) goto L_0x0263
            r10 = 0
        L_0x01d8:
            if (r10 >= r0) goto L_0x025a
            int r12 = r10 >> 0
            r12 = r12 & r7
            int r12 = r12 << r11
            int r13 = r10 >> 1
            r13 = r13 & r7
            int r13 = r13 << r9
            r12 = r12 | r13
            int r13 = r10 >> 2
            r13 = r13 & r7
            int r13 = r13 << r8
            r12 = r12 | r13
            int r13 = r10 >> 3
            r13 = r13 & r7
            int r13 = r13 << r7
            r12 = r12 | r13
            int r13 = r10 >> 4
            r13 = r13 & r7
            int r13 = r13 << r6
            r12 = r12 | r13
            int r13 = r10 >> 5
            r13 = r13 & r7
            r14 = 4
            int r13 = r13 << r14
            r12 = r12 | r13
            byte[][] r13 = f4118S
            r13 = r13[r1]
            byte r12 = r13[r12]
            int r13 = r12 >> 3
            r13 = r13 & r7
            int r13 = r13 << r6
            int r14 = r12 >> 2
            r14 = r14 & r7
            int r14 = r14 << r7
            r13 = r13 | r14
            int r14 = r12 >> 1
            r14 = r14 & r7
            int r14 = r14 << r8
            r13 = r13 | r14
            int r12 = r12 >> r6
            r12 = r12 & r7
            int r12 = r12 << r9
            r12 = r12 | r13
            r13 = 0
        L_0x0211:
            if (r13 >= r4) goto L_0x0218
            r5[r13] = r6
            int r13 = r13 + 1
            goto L_0x0211
        L_0x0218:
            r13 = 0
            r14 = 4
        L_0x021a:
            if (r13 >= r14) goto L_0x022b
            int r15 = r1 * 4
            int r15 = r15 + r13
            int r16 = r12 >> r13
            r0 = r16 & 1
            byte r0 = (byte) r0
            r5[r15] = r0
            int r13 = r13 + 1
            r0 = 64
            goto L_0x021a
        L_0x022b:
            r12 = 0
            r0 = 24
        L_0x022f:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x0248
            long r12 = r12 << r7
            byte r15 = r2[r0]
            int r15 = r15 - r7
            byte r15 = r5[r15]
            long r8 = (long) r15
            long r8 = r8 << r4
            long r8 = r8 | r12
            int r12 = r0 + 24
            byte r12 = r2[r12]
            int r12 = r12 - r7
            byte r12 = r5[r12]
            long r12 = (long) r12
            long r12 = r12 | r8
            r8 = 2
            r9 = 3
            goto L_0x022f
        L_0x0248:
            long[][] r0 = SPE
            r0 = r0[r1]
            long r8 = to_six_bit((long) r12)
            r0[r10] = r8
            int r10 = r10 + 1
            r0 = 64
            r8 = 2
            r9 = 3
            goto L_0x01d8
        L_0x025a:
            r14 = 4
            int r1 = r1 + 1
            r0 = 64
            r8 = 2
            r9 = 3
            goto L_0x01d5
        L_0x0263:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.security.UnixCrypt.<clinit>():void");
    }

    private UnixCrypt() {
    }

    private static long perm6464(long j, long[][] jArr) {
        long j2 = 0;
        long j3 = j;
        int i = 8;
        while (true) {
            i--;
            if (i < 0) {
                return j2;
            }
            int i2 = (int) (255 & j3);
            j3 >>= 8;
            int i3 = i << 1;
            j2 = j2 | jArr[i3][i2 & 15] | jArr[i3 + 1][i2 >> 4];
        }
    }

    private static long perm3264(int i, long[][] jArr) {
        long j = 0;
        int i2 = i;
        int i3 = 4;
        while (true) {
            i3--;
            if (i3 < 0) {
                return j;
            }
            int i4 = i2 & 255;
            i2 >>= 8;
            int i5 = i3 << 1;
            j = j | jArr[i5][i4 & 15] | jArr[i5 + 1][i4 >> 4];
        }
    }

    private static long[] des_setkey(long j) {
        long perm6464 = perm6464(j, PC1ROT);
        long[] jArr = new long[16];
        jArr[0] = perm6464 & -217020518463700993L;
        long j2 = perm6464;
        for (int i = 1; i < 16; i++) {
            jArr[i] = j2;
            j2 = perm6464(j2, PC2ROT[Rotates[i] - 1]);
            jArr[i] = j2 & -217020518463700993L;
        }
        return jArr;
    }

    private static long des_cipher(long j, int i, int i2, long[] jArr) {
        int i3 = to_six_bit(i);
        long j2 = j & 6148914691236517205L;
        int i4 = 1;
        long j3 = (j & -6148914694099828736L) | ((j >> 1) & 1431655765);
        char c = ' ';
        long j4 = 4294967295L;
        long perm3264 = perm3264((int) (((((j2 << 32) | (j2 << 1)) & -4294967296L) | ((j3 | (j3 >> 32)) & 4294967295L)) >> 32), IE3264);
        long perm32642 = perm3264((int) (perm3264 & -1), IE3264);
        long j5 = perm3264;
        int i5 = i2;
        while (true) {
            i5--;
            if (i5 < 0) {
                return perm6464(((((j5 >> 35) & 252645135) | (((j5 & -1) << 1) & 4042322160L)) << 32) | (252645135 & (perm32642 >> 35)) | (((-1 & perm32642) << 1) & 4042322160L), CF6464);
            }
            char c2 = 0;
            long j6 = perm32642;
            long j7 = j5;
            int i6 = 0;
            while (i6 < 8) {
                int i7 = i6 << 1;
                long j8 = (long) i3;
                long j9 = ((j6 >> c) ^ j6) & j8 & j4;
                long j10 = ((j9 | (j9 << c)) ^ j6) ^ jArr[i7];
                long[][] jArr2 = SPE;
                j7 ^= ((((((jArr2[i4][(int) ((j10 >> 50) & 63)] ^ jArr2[c2][(int) ((j10 >> 58) & 63)]) ^ jArr2[2][(int) ((j10 >> 42) & 63)]) ^ jArr2[3][(int) ((j10 >> 34) & 63)]) ^ jArr2[4][(int) ((j10 >> 26) & 63)]) ^ jArr2[5][(int) ((j10 >> 18) & 63)]) ^ jArr2[6][(int) ((j10 >> 10) & 63)]) ^ jArr2[7][(int) ((j10 >> 2) & 63)];
                long j11 = ((j7 >> 32) ^ j7) & j8 & 4294967295L;
                long j12 = jArr[i7 + i4] ^ ((j11 | (j11 << 32)) ^ j7);
                j6 ^= jArr2[7][(int) ((j12 >> 2) & 63)] ^ ((((((jArr2[i4][(int) ((j12 >> 50) & 63)] ^ jArr2[0][(int) ((j12 >> 58) & 63)]) ^ jArr2[2][(int) ((j12 >> 42) & 63)]) ^ jArr2[3][(int) ((j12 >> 34) & 63)]) ^ jArr2[4][(int) ((j12 >> 26) & 63)]) ^ jArr2[5][(int) ((j12 >> 18) & 63)]) ^ jArr2[6][(int) ((j12 >> 10) & 63)]);
                i6++;
                c2 = 0;
                i4 = 1;
                j4 = 4294967295L;
                c = ' ';
            }
            long j13 = j7 ^ j6;
            long j14 = j6 ^ j13;
            j5 = j13 ^ j14;
            perm32642 = j14;
            i4 = 1;
            j4 = 4294967295L;
            c = ' ';
        }
    }

    private static void init_perm(long[][] jArr, byte[] bArr, int i) {
        for (int i2 = 0; i2 < i * 8; i2++) {
            int i3 = bArr[i2] - 1;
            if (i3 >= 0) {
                int i4 = i3 >> 2;
                int i5 = 1 << (i3 & 3);
                for (int i6 = 0; i6 < 16; i6++) {
                    int i7 = (i2 & 7) + ((7 - (i2 >> 3)) << 3);
                    if ((i6 & i5) != 0) {
                        long[] jArr2 = jArr[i4];
                        jArr2[i6] = jArr2[i6] | (1 << i7);
                    }
                }
            }
        }
    }

    public static String crypt(String str, String str2) {
        byte[] bArr = new byte[13];
        if (str == null || str2 == null) {
            return Constraint.ANY_ROLE;
        }
        int length = str.length();
        long j = 0;
        int i = 0;
        while (i < 8) {
            j = (j << 8) | ((long) (i < length ? str.charAt(i) * 2 : 0));
            i++;
        }
        long[] des_setkey = des_setkey(j);
        int i2 = 2;
        byte b = 0;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            char charAt = i2 < str2.length() ? str2.charAt(i2) : '.';
            bArr[i2] = (byte) charAt;
            b = (b << 6) | (A64TOI[charAt] & UByte.MAX_VALUE);
        }
        long des_cipher = des_cipher(0, b, 25, des_setkey);
        int i3 = 12;
        bArr[12] = ITOA64[(((int) des_cipher) << 2) & 63];
        char c = 4;
        while (true) {
            des_cipher >>= c;
            i3--;
            if (i3 < 2) {
                return new String(bArr, 0, 13);
            }
            bArr[i3] = ITOA64[((int) des_cipher) & 63];
            c = 6;
        }
    }

    public static void main(String[] strArr) {
        if (strArr.length != 2) {
            System.err.println("Usage - java org.eclipse.util.UnixCrypt <key> <salt>");
            System.exit(1);
        }
        PrintStream printStream = System.err;
        printStream.println("Crypt=" + crypt(strArr[0], strArr[1]));
    }
}
