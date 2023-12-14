package p110io.fogcloud.sdk.easylink.helper;

import android.util.Log;

/* renamed from: io.fogcloud.sdk.easylink.helper.aws_broadcast */
public class aws_broadcast {
    /* access modifiers changed from: private */
    public static int GROUP_FRAME = 992;
    /* access modifiers changed from: private */
    public static int START_FRAME = 1248;
    /* access modifiers changed from: private */
    public int aws_interval = 20;
    /* access modifiers changed from: private */
    public int d_port = 50000;
    /* access modifiers changed from: private */
    public boolean stop_broadcast = false;

    static /* synthetic */ int access$308(aws_broadcast aws_broadcast) {
        int i = aws_broadcast.d_port;
        aws_broadcast.d_port = i + 1;
        return i;
    }

    public void set_stop_broad_flag(boolean z) {
        this.stop_broadcast = z;
    }

    /* access modifiers changed from: private */
    public boolean isContainChinese(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= 19968 && charArray[i] <= 40891) {
                return true;
            }
        }
        return false;
    }

    private static char encode_chinese(char[] cArr, int i, char[] cArr2) {
        char[] cArr3 = new char[264];
        int i2 = ((i * 8) + 5) / 6;
        for (char c = 0; c < i; c = (char) (c + 1)) {
            for (char c2 = 0; c2 < 8; c2 = (char) (c2 + 1)) {
                cArr3[(c * 8) + c2] = (char) ((cArr[c] >> c2) & 1);
            }
        }
        cArr2[i2] = 0;
        for (char c3 = 0; c3 < i2; c3 = (char) (c3 + 1)) {
            cArr2[c3] = 0;
            for (char c4 = 0; c4 < 6; c4 = (char) (c4 + 1)) {
                cArr2[c3] = (char) (cArr2[c3] | (cArr3[(c3 * 6) + c4] << c4));
            }
        }
        return (char) i2;
    }

    /* access modifiers changed from: private */
    public static char zconfig_checksum(char[] cArr, int i) {
        int i2 = 0;
        for (char c = 0; c < i; c = (char) (c + 1)) {
            i2 += cArr[c];
        }
        return (char) ((i2 & 127) | ((i2 & 16256) << 1));
    }

    public void aws_send(final String str, final String str2) {
        new Thread(new Runnable() {
            public void run() {
                char c;
                int i;
                char c2;
                UdpSend udpSend = new UdpSend();
                char[] cArr = new char[50];
                char[] cArr2 = new char[100];
                char[] charArray = str.toCharArray();
                char[] charArray2 = str2.toCharArray();
                boolean z = !aws_broadcast.this.isContainChinese(str);
                if (z) {
                    c = (char) charArray.length;
                    for (int i2 = 0; i2 < c; i2++) {
                        cArr[i2] = (char) (charArray[i2] - ' ');
                    }
                } else {
                    c = 0;
                }
                char length = (char) charArray2.length;
                if (z) {
                    c2 = (char) (c + 4 + length + 2);
                    cArr2[0] = c2;
                    cArr2[1] = 1;
                    cArr2[2] = c;
                    i = 3;
                } else {
                    c2 = (char) (c + 3 + length + 2);
                    cArr2[0] = c2;
                    cArr2[1] = 0;
                    i = 2;
                }
                int i3 = i + 1;
                cArr2[i] = length;
                int i4 = 0;
                while (i4 < c) {
                    cArr2[i3] = cArr[i4];
                    i4++;
                    i3++;
                }
                int i5 = 0;
                while (i5 < length) {
                    cArr2[i3] = (char) (charArray2[i5] - ' ');
                    i5++;
                    i3++;
                }
                char access$100 = aws_broadcast.zconfig_checksum(cArr2, c2 - 2);
                cArr2[i3] = (char) ((65280 & access$100) >> 8);
                cArr2[i3 + 1] = (char) (access$100 & 127);
                for (int i6 = 0; i6 < c2; i6++) {
                    cArr2[i6] = (char) (cArr2[i6] + ((char) (((i6 % 8) + 2) << 7)));
                }
                while (!aws_broadcast.this.stop_broadcast) {
                    Log.w("yyy", "broadcasting");
                    int unused = aws_broadcast.this.d_port = 50000;
                    try {
                        udpSend.sendConfigData(aws_broadcast.START_FRAME, aws_broadcast.access$308(aws_broadcast.this));
                        Thread.sleep((long) aws_broadcast.this.aws_interval);
                        udpSend.sendConfigData(aws_broadcast.START_FRAME, aws_broadcast.access$308(aws_broadcast.this));
                        Thread.sleep((long) aws_broadcast.this.aws_interval);
                        udpSend.sendConfigData(aws_broadcast.START_FRAME, aws_broadcast.access$308(aws_broadcast.this));
                        Thread.sleep((long) aws_broadcast.this.aws_interval);
                        int i7 = 1;
                        for (int i8 = 0; i8 < c2; i8++) {
                            udpSend.sendConfigData(cArr2[i8], aws_broadcast.access$308(aws_broadcast.this));
                            Thread.sleep((long) aws_broadcast.this.aws_interval);
                            if (i8 % 8 == 7) {
                                udpSend.sendConfigData(aws_broadcast.GROUP_FRAME + i7, aws_broadcast.access$308(aws_broadcast.this));
                                Thread.sleep((long) aws_broadcast.this.aws_interval);
                                udpSend.sendConfigData(aws_broadcast.GROUP_FRAME + i7, aws_broadcast.access$308(aws_broadcast.this));
                                Thread.sleep((long) aws_broadcast.this.aws_interval);
                                i7++;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                udpSend.get_socket().close();
            }
        }).start();
    }
}
