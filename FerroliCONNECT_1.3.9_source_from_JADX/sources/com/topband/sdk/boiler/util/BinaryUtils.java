package com.topband.sdk.boiler.util;

import android.support.p000v4.internal.view.SupportMenu;
import android.util.Log;
import java.nio.ByteBuffer;
import kotlin.UByte;

public class BinaryUtils {
    static final int SHORT_LOW_BYTE_MASK = 255;

    public static int byte2int(byte b) {
        return b & UByte.MAX_VALUE;
    }

    public static byte[] bytes2array(byte... bArr) {
        return bArr;
    }

    public static byte int2byte(int i) {
        return (byte) (i & 255);
    }

    public static short int2short(int i) {
        return (short) (i & SupportMenu.USER_MASK);
    }

    public static String byte2HexString(byte b) {
        return Integer.toHexString(byte2int(b));
    }

    public static String byte2String(byte b) {
        return Integer.toString(byte2int(b));
    }

    public static byte float2byte(float f, int i) {
        return int2byte((int) (f * ((float) i)));
    }

    public static float byte2float(byte b, int i) {
        return (float) (byte2int(b) / i);
    }

    public static long bytes2long(byte[] bArr) {
        long j = 0;
        if (bArr == null) {
            return 0;
        }
        for (byte b : bArr) {
            j = (j << 8) | ((long) b);
        }
        return j;
    }

    public static long bytesToLong(byte[] bArr) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.put(bArr, 0, bArr.length);
        allocate.flip();
        return allocate.getLong();
    }

    public static byte[] long2bytes(long j) {
        return new byte[]{(byte) ((int) (j >> 56)), (byte) ((int) (j >> 48)), (byte) ((int) (j >> 40)), (byte) ((int) (j >> 32)), (byte) ((int) (j >> 24)), (byte) ((int) (j >> 16)), (byte) ((int) (j >> 8)), (byte) ((int) j)};
    }

    public static String bytes2String(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte byte2int : bArr) {
            sb.append(Integer.toHexString(byte2int(byte2int)));
        }
        return sb.toString();
    }

    public static short bytes2shortHtL(byte b, byte b2) {
        return int2short((byte2int(b) << 8) | byte2int(b2));
    }

    public static byte[] short2BytesHtL(short s) {
        return new byte[]{(byte) (s >> 8), (byte) (s & 255)};
    }

    public static void writeBytes(byte[] bArr, byte[] bArr2, int i) {
        System.arraycopy(bArr, 0, bArr2, i, bArr.length);
    }

    public static void printBytes(String str, byte[] bArr) {
        StringBuilder sb = new StringBuilder("[" + str + "] : ");
        for (byte byte2int : bArr) {
            sb.append(Integer.toHexString(byte2int(byte2int)));
        }
        Log.d(str, ": " + sb.toString());
    }
}
