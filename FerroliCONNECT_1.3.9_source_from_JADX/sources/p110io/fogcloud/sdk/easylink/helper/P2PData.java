package p110io.fogcloud.sdk.easylink.helper;

import java.io.UnsupportedEncodingException;
import kotlin.text.Typography;

/* renamed from: io.fogcloud.sdk.easylink.helper.P2PData */
public class P2PData {
    private char[] decode_table = {0, 'U', '!', 30, 'x', 9, 'a', 'V', 12, 'q', 'g', '/', '=', 'K', 1, '+', 'l', 28, '-', '8', 'P', 'X', 'Z', '@', '_', 'F', 27, 'p', 'b', '}', 'h', 'k', '^', '?', 'O', Typography.amp, '\'', 13, '(', ']', 11, '[', 6, 'e', 'N', 15, 'A', Typography.greater, '\\', 23, 'T', '|', Typography.dollar, '1', 24, 19, 's', 'z', 3, 'I', '2', 'L', '%', 'c', 'w', 'v', 'Q', 26, 5, ':', 'i', Typography.less, '4', 'C', '7', 'D', 'f', 8, 127, 'Y', ' ', 'G', 'r', 22, '.', '5', 'n', '3', 20, 31, 'R', ')', 'E', 'j', ',', 14, 21, '0', 'B', '6', 16, '`', 't', 'M', 'd', 'H', 'y', 10, 2, 4, 'm', 7, ';', Typography.quote, 29, '*', '~', '9', 17, '{', 18, 25, 'o', 'W', '#', 'u', 'J', 'S', 156, 175, 145, 164, Typography.paragraph, 168, Typography.section, 151, 152, 132, Typography.half, 185, 130, Typography.plusMinus, Typography.pound, 190, Typography.cent, 137, 149, 154, 148, 142, Typography.registered, 186, Typography.copyright, 159, 166, Typography.middleDot, 173, 180, 135, 165, Typography.leftGuillemete, 136, 133, 147, 170, 144, 129, Typography.rightGuillemete, 143, 134, 146, Typography.nbsp, 172, 139, 181, 128, Typography.degree, 153, 179, 188, 184, 157, 191, 141, 150, 138, 158, 178, 155, 131, 161, 140, 193, 216, 213, 214, 197, 192, 211, 205, 194, Typography.times, 217, 201, 204, 202, 220, 210, 222, 198, 199, 221, 223, 203, 218, 195, 219, 200, 212, 206, 208, 209, 196, 207, 239, 234, 227, 230, 235, 225, 228, 233, 226, 224, 236, 229, 231, 232, 238, 237, 241, 247, 244, 245, 242, 243, 240, 246, 251, 249, 250, 248, 252, 253, 254, 255};

    public String bgProtocol(aws_broadcast aws_broadcast, String str, String str2) throws UnsupportedEncodingException {
        int i;
        boolean z;
        boolean z2;
        String str3 = str;
        String str4 = str2;
        char[] cArr = new char[(str3.getBytes("UTF-8").length + 3 + str4.getBytes("UTF-8").length)];
        int length = str3.getBytes("UTF-8").length + str4.getBytes("UTF-8").length + 3;
        aws_broadcast.aws_send(str, str2);
        int i2 = 1;
        if (length > 32) {
            int i3 = length - 32;
            if (i3 <= str3.getBytes("UTF-8").length) {
                byte[] bytes = str.getBytes();
                int length2 = bytes.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length2) {
                        z2 = false;
                        break;
                    } else if (bytes[i4] < 0) {
                        z2 = true;
                        break;
                    } else {
                        i4++;
                    }
                }
                if (!z2) {
                    cArr[0] = (char) (str3.getBytes("UTF-8").length - i3);
                    byte[] bytes2 = str3.getBytes("UTF-8");
                    int length3 = bytes2.length;
                    int i5 = 0;
                    int i6 = 1;
                    int i7 = 1;
                    while (i5 < length3) {
                        byte b = bytes2[i5];
                        i6 += i2;
                        if (i6 > i3) {
                            cArr[i7] = this.decode_table[b];
                            i7++;
                        }
                        i5++;
                        i2 = 1;
                    }
                    byte[] bytes3 = str4.getBytes("UTF-8");
                    int length4 = bytes3.length;
                    int i8 = 0;
                    while (i8 < length4) {
                        cArr[i7] = this.decode_table[bytes3[i8]];
                        i8++;
                        i7++;
                    }
                    char zconfig_checksum_v2 = CRC16.zconfig_checksum_v2(cArr, cArr.length);
                    cArr[i7] = (char) ((zconfig_checksum_v2 & 65280) >> 8);
                    cArr[i7 + 1] = (char) (zconfig_checksum_v2 & 255);
                } else {
                    cArr[0] = 0;
                    byte[] bytes4 = str4.getBytes("UTF-8");
                    int length5 = bytes4.length;
                    int i9 = 0;
                    int i10 = 1;
                    i = 1;
                    while (i9 < length5) {
                        cArr[i10] = this.decode_table[bytes4[i9]];
                        i++;
                        i9++;
                        i10++;
                    }
                    char zconfig_checksum_v22 = CRC16.zconfig_checksum_v2(cArr, cArr.length);
                    cArr[i10] = (char) ((zconfig_checksum_v22 & 65280) >> 8);
                    cArr[i10 + 1] = (char) (zconfig_checksum_v22 & 255);
                }
            }
            return new String(cArr, 0, length);
        }
        byte[] bytes5 = str.getBytes();
        int length6 = bytes5.length;
        int i11 = 0;
        while (true) {
            if (i11 >= length6) {
                z = false;
                break;
            } else if (bytes5[i11] < 0) {
                z = true;
                break;
            } else {
                i11++;
            }
        }
        if (z) {
            cArr[0] = 0;
            byte[] bytes6 = str4.getBytes("UTF-8");
            int length7 = bytes6.length;
            int i12 = 0;
            int i13 = 1;
            int i14 = 1;
            while (i12 < length7) {
                cArr[i13] = this.decode_table[bytes6[i12]];
                i14 = i + 1;
                i12++;
                i13++;
            }
            char zconfig_checksum_v23 = CRC16.zconfig_checksum_v2(cArr, cArr.length);
            cArr[i13] = (char) ((zconfig_checksum_v23 & 65280) >> 8);
            cArr[i13 + 1] = (char) (zconfig_checksum_v23 & 255);
        } else {
            cArr[0] = (char) str3.getBytes("UTF-8").length;
            byte[] bytes7 = str.getBytes();
            int length8 = bytes7.length;
            int i15 = 0;
            int i16 = 1;
            while (i15 < length8) {
                cArr[i16] = this.decode_table[bytes7[i15]];
                i15++;
                i16++;
            }
            byte[] bytes8 = str4.getBytes("UTF-8");
            int length9 = bytes8.length;
            int i17 = 0;
            while (i17 < length9) {
                cArr[i16] = this.decode_table[bytes8[i17]];
                i17++;
                i16++;
            }
            char zconfig_checksum_v24 = CRC16.zconfig_checksum_v2(cArr, cArr.length);
            cArr[i16] = (char) ((zconfig_checksum_v24 & 65280) >> 8);
            cArr[i16 + 1] = (char) (zconfig_checksum_v24 & 255);
            return new String(cArr, 0, length);
        }
        length = i + 2;
        return new String(cArr, 0, length);
    }
}
