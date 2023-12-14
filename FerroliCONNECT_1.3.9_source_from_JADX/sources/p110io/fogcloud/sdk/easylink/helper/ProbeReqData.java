package p110io.fogcloud.sdk.easylink.helper;

import java.io.UnsupportedEncodingException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* renamed from: io.fogcloud.sdk.easylink.helper.ProbeReqData */
public class ProbeReqData {
    private static final String ARC4_KEY = "mxchip_easylink_minus";
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final int version = 1;

    public String[] bgProtocol(String str, String str2, int i) throws UnsupportedEncodingException {
        int i2;
        String str3 = str;
        String str4 = str2;
        int i3 = i;
        byte[] bArr = new byte[2];
        bArr[0] = 1;
        byte[] bArr2 = new byte[(str3.getBytes("UTF-8").length + 5 + str4.getBytes("UTF-8").length)];
        bArr2[0] = (byte) (i3 & 255);
        bArr2[1] = (byte) ((i3 >> 8) & 255);
        bArr2[2] = (byte) ((i3 >> 16) & 255);
        bArr2[3] = (byte) ((i3 >> 24) & 255);
        bArr2[4] = (byte) str3.getBytes("UTF-8").length;
        byte[] bytes = str3.getBytes("UTF-8");
        int length = bytes.length;
        int i4 = 0;
        int i5 = 5;
        while (i4 < length) {
            bArr2[i5] = bytes[i4];
            i4++;
            i5++;
        }
        byte[] bytes2 = str4.getBytes("UTF-8");
        int length2 = bytes2.length;
        int i6 = 0;
        while (i6 < length2) {
            bArr2[i5] = bytes2[i6];
            i6++;
            i5++;
        }
        byte[] transfer = transfer(bArr2);
        byte[] transfer2 = transfer(new RC4(ARC4_KEY.getBytes("UTF-8")).encrypt(bArr2));
        int i7 = 29;
        int length3 = transfer2.length % 29 == 0 ? transfer2.length / 29 : (transfer2.length / 29) + 1;
        String[] strArr = new String[(length3 + 1)];
        strArr[0] = new String(transfer);
        if (1 == length3) {
            byte[] bArr3 = new byte[(transfer2.length + 3)];
            int i8 = length3 & 15;
            byte b = (byte) (((byte) (i8 << 4)) + ((byte) i8));
            byte[] bArr4 = new byte[(transfer2.length + 1)];
            bArr4[0] = b;
            int i9 = 0;
            while (i9 < transfer2.length) {
                int i10 = i9 + 1;
                bArr4[i10] = transfer2[i9];
                i9 = i10;
            }
            bArr[1] = 16;
            bArr[1] = (byte) ((Crc8Code.calcCrc8(bArr4) & 15) | bArr[1]);
            bArr3[0] = bArr[0];
            bArr3[1] = bArr[1];
            bArr3[2] = b;
            for (int i11 = 0; i11 < transfer2.length; i11++) {
                bArr3[i11 + 3] = transfer2[i11];
            }
            strArr[1] = new String(bArr3, "UTF-8");
        } else {
            int i12 = 0;
            while (i12 < length3) {
                int i13 = i12 + 1;
                if (i13 < length3) {
                    i2 = 29;
                } else {
                    i2 = transfer2.length % i7;
                }
                byte[] bArr5 = new byte[(i2 + 3)];
                byte[] bArr6 = new byte[(i2 + 1)];
                byte b2 = (byte) (((byte) ((length3 & 15) << 4)) + ((byte) (i13 & 15)));
                bArr6[0] = b2;
                int i14 = 0;
                while (i14 < i2) {
                    int i15 = i14 + (i12 * 29);
                    bArr5[i14 + 3] = transfer2[i15];
                    i14++;
                    bArr6[i14] = transfer2[i15];
                }
                bArr[1] = 0;
                bArr[1] = 16;
                bArr[1] = (byte) ((Crc8Code.calcCrc8(bArr6) & 15) | bArr[1]);
                bArr5[0] = bArr[0];
                bArr5[1] = bArr[1];
                bArr5[2] = b2;
                strArr[i13] = new String(bArr5, "UTF-8");
                i12 = i13;
                i7 = 29;
            }
        }
        return strArr;
    }

    /* access modifiers changed from: package-private */
    public byte[] transfer(byte[] bArr) {
        int i;
        int length = bArr.length;
        byte[] bArr2 = new byte[(length * 2)];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            byte b = (byte) (bArr[i3] & ByteCompanionObject.MAX_VALUE);
            if (b == 126) {
                int i4 = i2 + 1;
                bArr2[i2] = 126;
                i2 = i4 + 1;
                bArr2[i4] = 1;
            } else if (b == 0) {
                int i5 = i2 + 1;
                bArr2[i2] = 126;
                i2 = i5 + 1;
                bArr2[i5] = 2;
            } else {
                bArr2[i2] = b;
                i2++;
            }
            if (i3 % 7 == 6) {
                int i6 = i3 - 6;
                byte b2 = 0;
                for (int i7 = 0; i7 < 7; i7++) {
                    b2 = (byte) (b2 + ((bArr[i6 + i7] & 128) >> (7 - i7)));
                }
                if (b2 == 126) {
                    int i8 = i2 + 1;
                    bArr2[i2] = 126;
                    i2 = i8 + 1;
                    bArr2[i8] = 1;
                } else if (b2 == 0) {
                    int i9 = i2 + 1;
                    bArr2[i2] = 126;
                    i2 = i9 + 1;
                    bArr2[i9] = 2;
                } else {
                    i = i2 + 1;
                    bArr2[i2] = b2;
                }
            } else {
                if (i3 == length - 1) {
                    int i10 = length % 7;
                    byte b3 = 0;
                    for (int i11 = 0; i11 < i10; i11++) {
                        b3 = (byte) (b3 + ((bArr[(length - i10) + i11] & 128) >> (7 - i11)));
                    }
                    if (b3 == 126) {
                        int i12 = i2 + 1;
                        bArr2[i2] = 126;
                        i2 = i12 + 1;
                        bArr2[i12] = 1;
                    } else if (b3 == 0) {
                        int i13 = i2 + 1;
                        bArr2[i2] = 126;
                        i2 = i13 + 1;
                        bArr2[i13] = 2;
                    } else {
                        i = i2 + 1;
                        bArr2[i2] = b3;
                    }
                }
            }
            i2 = i;
        }
        byte[] bArr3 = new byte[i2];
        for (int i14 = 0; i14 < i2; i14++) {
            bArr3[i14] = bArr2[i14];
        }
        return bArr3;
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        int length = str.length() / 2;
        char[] charArray = str.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (charToByte(charArray[i2 + 1]) | (charToByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i] & UByte.MAX_VALUE;
            int i2 = i * 2;
            char[] cArr2 = hexArray;
            cArr[i2] = cArr2[b >>> 4];
            cArr[i2 + 1] = cArr2[b & 15];
        }
        return new String(cArr);
    }
}
