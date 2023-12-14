package p110io.fogcloud.sdk.easylink.helper;

import kotlin.UByte;

/* renamed from: io.fogcloud.sdk.easylink.helper.Helper */
public class Helper {
    public static String castHexKeyboard(String str) {
        String upperCase = str.toUpperCase();
        char[] charArray = upperCase.toCharArray();
        String str2 = "";
        for (int i = 0; i < upperCase.length(); i++) {
            if (!(charArray[i] == '0' || charArray[i] == '1' || charArray[i] == '2' || charArray[i] == '3' || charArray[i] == '4' || charArray[i] == '5' || charArray[i] == '6' || charArray[i] == '7' || charArray[i] == '8' || charArray[i] == '9' || charArray[i] == 'A' || charArray[i] == 'B' || charArray[i] == 'C' || charArray[i] == 'D' || charArray[i] == 'E')) {
                charArray[i] = 'F';
            }
            str2 = str2 + charArray[i];
        }
        return str2;
    }

    public static boolean checkDataHexa(String str) {
        String upperCase = str.toUpperCase();
        char[] charArray = upperCase.toCharArray();
        boolean z = true;
        for (int i = 0; i < upperCase.length(); i++) {
            if (!(charArray[i] == '0' || charArray[i] == '1' || charArray[i] == '2' || charArray[i] == '3' || charArray[i] == '4' || charArray[i] == '5' || charArray[i] == '6' || charArray[i] == '7' || charArray[i] == '8' || charArray[i] == '9' || charArray[i] == 'A' || charArray[i] == 'B' || charArray[i] == 'C' || charArray[i] == 'D' || charArray[i] == 'E' || charArray[i] == 'F')) {
                z = false;
            }
        }
        return z;
    }

    public static String checkAndChangeDataHexa(String str) {
        String upperCase = str.toUpperCase();
        char[] charArray = upperCase.toCharArray();
        String str2 = "";
        for (int i = 0; i < upperCase.length(); i++) {
            if (charArray[i] == '0' || charArray[i] == '1' || charArray[i] == '2' || charArray[i] == '3' || charArray[i] == '4' || charArray[i] == '5' || charArray[i] == '6' || charArray[i] == '7' || charArray[i] == '8' || charArray[i] == '9' || charArray[i] == 'A' || charArray[i] == 'B' || charArray[i] == 'C' || charArray[i] == 'D' || charArray[i] == 'E' || charArray[i] == 'F') {
                str2 = str2 + charArray[i];
            }
        }
        return str2;
    }

    public static boolean checkFileName(String str) {
        char[] charArray = str.toCharArray();
        boolean z = true;
        for (int i = 0; i < str.length(); i++) {
            if (!(charArray[i] == '0' || charArray[i] == '1' || charArray[i] == '2' || charArray[i] == '3' || charArray[i] == '4' || charArray[i] == '5' || charArray[i] == '6' || charArray[i] == '7' || charArray[i] == '8' || charArray[i] == '9' || charArray[i] == 'a' || charArray[i] == 'b' || charArray[i] == 'c' || charArray[i] == 'd' || charArray[i] == 'e' || charArray[i] == 'f' || charArray[i] == 'g' || charArray[i] == 'h' || charArray[i] == 'i' || charArray[i] == 'j' || charArray[i] == 'k' || charArray[i] == 'l' || charArray[i] == 'm' || charArray[i] == 'n' || charArray[i] == 'o' || charArray[i] == 'p' || charArray[i] == 'q' || charArray[i] == 'r' || charArray[i] == 's' || charArray[i] == 't' || charArray[i] == 'u' || charArray[i] == 'v' || charArray[i] == 'w' || charArray[i] == 'x' || charArray[i] == 'y' || charArray[i] == 'z' || charArray[i] == 'A' || charArray[i] == 'B' || charArray[i] == 'C' || charArray[i] == 'D' || charArray[i] == 'E' || charArray[i] == 'F' || charArray[i] == 'G' || charArray[i] == 'H' || charArray[i] == 'I' || charArray[i] == 'J' || charArray[i] == 'K' || charArray[i] == 'L' || charArray[i] == 'M' || charArray[i] == 'N' || charArray[i] == 'O' || charArray[i] == 'P' || charArray[i] == 'Q' || charArray[i] == 'R' || charArray[i] == 'S' || charArray[i] == 'T' || charArray[i] == 'U' || charArray[i] == 'V' || charArray[i] == 'W' || charArray[i] == 'X' || charArray[i] == 'Y' || charArray[i] == 'Z' || charArray[i] == '.' || charArray[i] == '_')) {
                z = false;
            }
        }
        return z;
    }

    public static String checkAndChangeFileName(String str) {
        char[] charArray = str.toCharArray();
        String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            if (charArray[i] == '0' || charArray[i] == '1' || charArray[i] == '2' || charArray[i] == '3' || charArray[i] == '4' || charArray[i] == '5' || charArray[i] == '6' || charArray[i] == '7' || charArray[i] == '8' || charArray[i] == '9' || charArray[i] == 'a' || charArray[i] == 'b' || charArray[i] == 'c' || charArray[i] == 'd' || charArray[i] == 'e' || charArray[i] == 'f' || charArray[i] == 'g' || charArray[i] == 'h' || charArray[i] == 'i' || charArray[i] == 'j' || charArray[i] == 'k' || charArray[i] == 'l' || charArray[i] == 'm' || charArray[i] == 'n' || charArray[i] == 'o' || charArray[i] == 'p' || charArray[i] == 'q' || charArray[i] == 'r' || charArray[i] == 's' || charArray[i] == 't' || charArray[i] == 'u' || charArray[i] == 'v' || charArray[i] == 'w' || charArray[i] == 'x' || charArray[i] == 'y' || charArray[i] == 'z' || charArray[i] == 'A' || charArray[i] == 'B' || charArray[i] == 'C' || charArray[i] == 'D' || charArray[i] == 'E' || charArray[i] == 'F' || charArray[i] == 'G' || charArray[i] == 'H' || charArray[i] == 'I' || charArray[i] == 'J' || charArray[i] == 'K' || charArray[i] == 'L' || charArray[i] == 'M' || charArray[i] == 'N' || charArray[i] == 'O' || charArray[i] == 'P' || charArray[i] == 'Q' || charArray[i] == 'R' || charArray[i] == 'S' || charArray[i] == 'T' || charArray[i] == 'U' || charArray[i] == 'V' || charArray[i] == 'W' || charArray[i] == 'X' || charArray[i] == 'Y' || charArray[i] == 'Z' || charArray[i] == '.' || charArray[i] == '_') {
                str2 = str2 + charArray[i];
            }
        }
        return str2;
    }

    public static String StringForceDigit(String str, int i) {
        String replaceAll = str.replaceAll(" ", "");
        if (replaceAll.length() == 4) {
            return replaceAll;
        }
        if (replaceAll.length() < i) {
            while (replaceAll.length() != i) {
                replaceAll = "0".concat(replaceAll);
            }
        }
        return replaceAll;
    }

    public static String ConvertHexByteToString(byte b) {
        if (b < 0) {
            return "" + Integer.toString(b + UByte.MIN_VALUE, 16) + " ";
        } else if (b <= 15) {
            return "" + "0" + Integer.toString(b, 16) + " ";
        } else {
            return "" + Integer.toString(b, 16) + " ";
        }
    }

    public static String ConvertHexByteArrayToString(byte[] bArr) {
        String str = "";
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] < 0) {
                str = str + Integer.toString(bArr[i] + UByte.MIN_VALUE, 16) + " ";
            } else if (bArr[i] <= 15) {
                str = str + "0" + Integer.toString(bArr[i], 16) + " ";
            } else {
                str = str + Integer.toString(bArr[i], 16) + " ";
            }
        }
        return str;
    }

    public static String FormatValueByteWrite(String str) {
        return castHexKeyboard(StringForceDigit(str, 2)).toUpperCase();
    }

    public static String ConvertIntToHexFormatString(int i) {
        return ConvertHexByteArrayToString(ConvertIntTo2bytesHexaFormat(i)).replaceAll(" ", "");
    }

    public static byte[] ConvertStringToHexBytes(String str) {
        char[] charArray = str.toCharArray();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] bArr = {0, 0};
        int i = 0;
        int i2 = 0;
        while (i <= 1) {
            int i3 = i2;
            int i4 = 0;
            while (i4 <= 15) {
                if (charArray[i] == cArr[i4]) {
                    if (i != 1) {
                        if (i == 0) {
                            i4 *= 16;
                        }
                    }
                    i3 += i4;
                    i4 = 15;
                }
                i4++;
            }
            i++;
            i2 = i3;
        }
        bArr[0] = (byte) i2;
        int i5 = 2;
        int i6 = 0;
        while (i5 <= 3) {
            int i7 = i6;
            int i8 = 0;
            while (i8 <= 15) {
                if (charArray[i5] == cArr[i8]) {
                    if (i5 != 3) {
                        if (i5 == 2) {
                            i8 *= 16;
                        }
                    }
                    i7 += i8;
                    i8 = 15;
                }
                i8++;
            }
            i5++;
            i6 = i7;
        }
        bArr[1] = (byte) i6;
        return bArr;
    }

    public static byte[] ConvertStringToHexBytesArray(String str) {
        String replaceAll = str.toUpperCase().replaceAll(" ", "");
        char[] charArray = replaceAll.toCharArray();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] bArr = new byte[(replaceAll.length() / 2)];
        int length = replaceAll.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i;
            int i4 = 0;
            while (true) {
                int i5 = 15;
                if (i4 > 15) {
                    break;
                }
                if (charArray[i2] == cArr[i4]) {
                    int i6 = i2 % 2;
                    if (i6 != 1) {
                        if (i6 == 0) {
                            i4 *= 16;
                        }
                    }
                    i3 += i4;
                    i4 = i5 + 1;
                }
                i5 = i4;
                i4 = i5 + 1;
            }
            if (i2 % 2 == 1) {
                bArr[i2 / 2] = (byte) i3;
                i = 0;
            } else {
                i = i3;
            }
        }
        return bArr;
    }

    public static byte ConvertStringToHexByte(String str) {
        char[] charArray = str.toUpperCase().toCharArray();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int i = 0;
        int i2 = 0;
        while (i <= 1) {
            int i3 = i2;
            int i4 = 0;
            while (true) {
                int i5 = 15;
                if (i4 > 15) {
                    break;
                }
                if (charArray[i] == cArr[i4]) {
                    if (i != 1) {
                        if (i == 0) {
                            i4 *= 16;
                        }
                    }
                    i3 += i4;
                    i4 = i5 + 1;
                }
                i5 = i4;
                i4 = i5 + 1;
            }
            i++;
            i2 = i3;
        }
        return (byte) i2;
    }

    public static byte[] ConvertIntTo2bytesHexaFormat(int i) {
        byte[] bArr = new byte[4];
        int i2 = i / 256;
        bArr[0] = (byte) i2;
        int i3 = i - (i2 * 256);
        bArr[1] = (byte) i3;
        int i4 = i - (i3 * 256);
        bArr[2] = (byte) i4;
        bArr[2] = (byte) (i - (i4 * 256));
        return bArr;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int Convert2bytesHexaFormatToInt(byte[] r4) {
        /*
            r0 = 1
            byte r1 = r4[r0]
            r2 = -1
            r3 = 0
            if (r1 > r2) goto L_0x000c
            byte r0 = r4[r0]
            int r0 = r0 + 256
            goto L_0x000e
        L_0x000c:
            byte r0 = r4[r0]
        L_0x000e:
            int r0 = r0 + r3
            byte r1 = r4[r3]
            if (r1 > r2) goto L_0x001a
            byte r4 = r4[r3]
            int r4 = r4 * 256
            int r4 = r4 + 256
            goto L_0x001e
        L_0x001a:
            byte r4 = r4[r3]
            int r4 = r4 * 256
        L_0x001e:
            int r0 = r0 + r4
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p110io.fogcloud.sdk.easylink.helper.Helper.Convert2bytesHexaFormatToInt(byte[]):int");
    }

    public static int ConvertStringToInt(String str) {
        if (str.length() <= 2) {
            return Integer.parseInt(str.substring(0, 2), 16);
        }
        return Integer.parseInt(str.substring(2, 4), 16) + (Integer.parseInt(str.substring(0, 2), 16) * 256);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] buildArrayBlocks(byte[] r5, int r6) {
        /*
            java.lang.String[] r0 = new java.lang.String[r6]
            r1 = 1
            byte r2 = r5[r1]
            byte r3 = r5[r1]
            if (r3 >= 0) goto L_0x000d
            byte r2 = r5[r1]
            int r2 = r2 + 256
        L_0x000d:
            r3 = 0
            byte r4 = r5[r3]
            if (r4 >= 0) goto L_0x0017
            byte r5 = r5[r3]
            int r5 = r5 + 256
            goto L_0x0019
        L_0x0017:
            byte r5 = r5[r3]
        L_0x0019:
            int r5 = r5 * 256
            int r2 = r2 + r5
        L_0x001c:
            if (r3 >= r6) goto L_0x0044
            r5 = 14
            if (r3 != r5) goto L_0x0023
            goto L_0x0024
        L_0x0023:
            r5 = r3
        L_0x0024:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Block  "
            r3.append(r4)
            int r4 = r5 + r2
            java.lang.String r4 = ConvertIntToHexFormatString(r4)
            java.lang.String r4 = r4.toUpperCase()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0[r5] = r3
            int r3 = r5 + 1
            goto L_0x001c
        L_0x0044:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p110io.fogcloud.sdk.easylink.helper.Helper.buildArrayBlocks(byte[], int):java.lang.String[]");
    }

    public static String[] buildArrayValueBlocks(byte[] bArr, int i) {
        String[] strArr = new String[i];
        int i2 = 1;
        for (int i3 = 0; i3 < i; i3++) {
            strArr[i3] = "";
            strArr[i3] = strArr[i3] + ConvertHexByteToString(bArr[i2]).toUpperCase();
            strArr[i3] = strArr[i3] + " ";
            strArr[i3] = strArr[i3] + ConvertHexByteToString(bArr[i2 + 1]).toUpperCase();
            strArr[i3] = strArr[i3] + " ";
            strArr[i3] = strArr[i3] + ConvertHexByteToString(bArr[i2 + 2]).toUpperCase();
            strArr[i3] = strArr[i3] + " ";
            strArr[i3] = strArr[i3] + ConvertHexByteToString(bArr[i2 + 3]).toUpperCase();
            i2 += 4;
        }
        return strArr;
    }

    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
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

    public static byte[] fillbyte(int i, byte[] bArr) {
        byte[] bArr2 = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 < bArr.length) {
                bArr2[i2] = bArr[i2];
            } else {
                bArr2[i2] = 0;
            }
        }
        return bArr2;
    }

    public static byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }
}
