package p110io.fogcloud.sdk.easylink.helper;

/* renamed from: io.fogcloud.sdk.easylink.helper.CRC16 */
public class CRC16 {
    public static char zconfig_checksum_v2(char[] cArr, int i) {
        int i2 = 0;
        for (char c = 0; c < i; c = (char) (c + 1)) {
            i2 += cArr[c];
        }
        int i3 = (i2 & 127) | ((i2 & 16256) << 1);
        if ((i3 & 255) == 0) {
            i3 |= 1;
        }
        if ((65280 & i3) == 0) {
            i3 |= 256;
        }
        return (char) i3;
    }
}
