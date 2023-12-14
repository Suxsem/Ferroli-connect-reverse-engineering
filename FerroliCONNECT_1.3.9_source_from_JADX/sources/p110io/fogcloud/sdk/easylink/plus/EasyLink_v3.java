package p110io.fogcloud.sdk.easylink.plus;

import com.google.android.gms.common.ConnectionResult;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import kotlin.UByte;
import p110io.fogcloud.sdk.easylink.helper.ComHelper;
import p110io.fogcloud.sdk.easylink.helper.SinRC4;

/* renamed from: io.fogcloud.sdk.easylink.plus.EasyLink_v3 */
public class EasyLink_v3 {
    private static int START_FLAG1 = 1450;
    private static int START_FLAG2 = 1451;
    private static int START_FLAG3 = 1452;
    private static int UDP_START_PORT = 50000;
    private static byte[] buffer = new byte[ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED];

    /* renamed from: e3 */
    private static EasyLink_v3 f3629e3;
    private static int len;
    private static byte[] send_data = new byte[128];
    /* access modifiers changed from: private */
    public static boolean stopSending;
    private InetAddress address = null;
    private boolean issendip = false;
    private byte[] key = new byte[65];
    private int port = 0;
    private DatagramPacket send_packet = null;
    private boolean small_mtu;
    private byte[] ssid = new byte[65];
    private byte[] user_info = new byte[65];

    private EasyLink_v3() {
        stopSending = false;
    }

    public static EasyLink_v3 getInstence() {
        if (f3629e3 == null) {
            f3629e3 = new EasyLink_v3();
        }
        return f3629e3;
    }

    public void transmitSettings(byte[] bArr, byte[] bArr2, byte[] bArr3, String str, final int i) {
        try {
            this.address = InetAddress.getByName("255.255.255.255");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ComHelper.checkPara(str)) {
            this.ssid = SinRC4.encry_RC4_byte(bArr, str);
            this.key = SinRC4.encry_RC4_byte(bArr2, str);
            if (bArr3 != null) {
                this.issendip = true;
                this.user_info = SinRC4.encry_RC4_byte(bArr3, str);
            }
        } else {
            this.ssid = bArr;
            this.key = bArr2;
            if (bArr3 != null) {
                this.issendip = true;
                this.user_info = bArr3;
            }
        }
        int i2 = 3;
        send_data[0] = (byte) (this.ssid.length + 3 + this.key.length + (this.issendip ? this.user_info.length : 0) + 2);
        byte[] bArr4 = send_data;
        bArr4[1] = (byte) this.ssid.length;
        bArr4[2] = (byte) this.key.length;
        int i3 = 0;
        while (true) {
            byte[] bArr5 = this.ssid;
            if (i3 >= bArr5.length) {
                break;
            }
            send_data[i2] = bArr5[i3];
            i3++;
            i2++;
        }
        int i4 = 0;
        while (true) {
            byte[] bArr6 = this.key;
            if (i4 >= bArr6.length) {
                break;
            }
            send_data[i2] = bArr6[i4];
            i4++;
            i2++;
        }
        if (this.issendip) {
            int i5 = 0;
            while (true) {
                byte[] bArr7 = this.user_info;
                if (i5 >= bArr7.length) {
                    break;
                }
                send_data[i2] = bArr7[i5];
                i5++;
                i2++;
            }
        }
        short s = 0;
        for (int i6 = 0; i6 < i2; i6++) {
            s = (short) (s + (send_data[i6] & UByte.MAX_VALUE));
        }
        byte[] bArr8 = send_data;
        bArr8[i2] = (byte) ((65535 & s) >> 8);
        bArr8[i2 + 1] = (byte) (s & 255);
        new Thread(new Runnable() {
            public void run() {
                boolean unused = EasyLink_v3.stopSending = false;
                EasyLink_v3.this.send(i);
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void send(int i) {
        while (!stopSending) {
            try {
                this.port = UDP_START_PORT;
                UDP_SEND(START_FLAG1, i);
                UDP_SEND(START_FLAG2, i);
                UDP_SEND(START_FLAG3, i);
                int i2 = 1;
                int i3 = 0;
                for (int i4 = 0; i4 < send_data[0]; i4++) {
                    len = (i2 * 256) + (send_data[i4] & UByte.MAX_VALUE);
                    UDP_SEND(len, i);
                    if (i4 % 4 == 3) {
                        i3++;
                        len = i3 + 1280;
                        UDP_SEND(len, i);
                    }
                    i2++;
                    if (i2 == 5) {
                        i2 = 1;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SetSmallMTU(boolean z) {
        this.small_mtu = z;
    }

    private void UDP_SEND(int i, int i2) {
        try {
            Thread.sleep((long) i2);
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.setBroadcast(true);
            if (this.small_mtu) {
                if (i > 1280) {
                    i -= 1280;
                }
                if (i < 64) {
                    i += 176;
                }
            }
            this.send_packet = new DatagramPacket(buffer, i, this.address, this.port);
            datagramSocket.send(this.send_packet);
            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopTransmitting() {
        stopSending = true;
    }
}
