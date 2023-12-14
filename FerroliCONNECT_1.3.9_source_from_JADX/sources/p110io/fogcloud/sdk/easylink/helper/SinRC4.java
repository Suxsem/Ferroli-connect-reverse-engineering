package p110io.fogcloud.sdk.easylink.helper;

import kotlin.UByte;

/* renamed from: io.fogcloud.sdk.easylink.helper.SinRC4 */
public class SinRC4 {
    public static String decry_RC4(byte[] bArr, String str) {
        if (bArr == null || str == null) {
            return null;
        }
        return asString(RC4Base(bArr, str));
    }

    public static String decry_RC4(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        return new String(RC4Base(HexString2Bytes(str), str2));
    }

    public static byte[] encry_RC4_byte(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        return RC4Base(str.getBytes(), str2);
    }

    public static byte[] encry_RC4_byte(byte[] bArr, String str) {
        if (bArr == null || str == null) {
            return null;
        }
        return RC4Base(bArr, str);
    }

    public static byte[] intToBytes2(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static String encry_RC4_string(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        return toHexString(asString(encry_RC4_byte(str, str2)));
    }

    private static String asString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length);
        for (byte b : bArr) {
            stringBuffer.append((char) b);
        }
        return stringBuffer.toString();
    }

    private static byte[] initKey(String str) {
        byte[] bytes = str.getBytes();
        byte[] bArr = new byte[256];
        for (int i = 0; i < 256; i++) {
            bArr[i] = (byte) i;
        }
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            i3 = ((bytes[i2] & UByte.MAX_VALUE) + (bArr[i4] & UByte.MAX_VALUE) + i3) & 255;
            byte b = bArr[i4];
            bArr[i4] = bArr[i3];
            bArr[i3] = b;
            i2 = (i2 + 1) % bytes.length;
        }
        return bArr;
    }

    private static String toHexString(String str) {
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            String hexString = Integer.toHexString(str.charAt(i) & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            str2 = str2 + hexString;
        }
        return str2;
    }

    private static byte[] HexString2Bytes(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        byte[] bytes = str.getBytes();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = uniteBytes(bytes[i2], bytes[i2 + 1]);
        }
        return bArr;
    }

    private static byte uniteBytes(byte b, byte b2) {
        return (byte) (((char) (((char) Byte.decode("0x" + new String(new byte[]{b})).byteValue()) << 4)) ^ ((char) Byte.decode("0x" + new String(new byte[]{b2})).byteValue()));
    }

    private static byte[] RC4Base(byte[] bArr, String str) {
        byte[] initKey = initKey(str);
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        byte b = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            i = (i + 1) & 255;
            b = ((initKey[i] & UByte.MAX_VALUE) + b) & UByte.MAX_VALUE;
            byte b2 = initKey[i];
            initKey[i] = initKey[b];
            initKey[b] = b2;
            bArr2[i2] = (byte) (initKey[((initKey[i] & UByte.MAX_VALUE) + (initKey[b] & UByte.MAX_VALUE)) & 255] ^ bArr[i2]);
        }
        return bArr2;
    }
}
