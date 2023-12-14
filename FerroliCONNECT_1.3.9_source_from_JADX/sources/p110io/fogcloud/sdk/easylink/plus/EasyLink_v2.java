package p110io.fogcloud.sdk.easylink.plus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;
import kotlin.UByte;
import p110io.fogcloud.sdk.easylink.helper.Helper;

/* renamed from: io.fogcloud.sdk.easylink.plus.EasyLink_v2 */
public class EasyLink_v2 {

    /* renamed from: e2 */
    private static EasyLink_v2 f3627e2 = null;
    private static String head = "239.118.0.0";

    /* renamed from: ip */
    private static String f3628ip = null;
    /* access modifiers changed from: private */
    public static boolean stopSending = false;
    private static String syncHString = "abcdefghijklmnopqrstuvw";
    private byte[] key = new byte[65];
    private byte[] ssid = new byte[65];
    private byte[] user_info = new byte[65];

    private EasyLink_v2() {
        stopSending = false;
    }

    public static EasyLink_v2 getInstence() {
        if (f3627e2 == null) {
            f3627e2 = new EasyLink_v2();
        }
        return f3627e2;
    }

    public void transmitSettings(byte[] bArr, byte[] bArr2, byte[] bArr3, final int i) {
        this.ssid = bArr;
        this.key = bArr2;
        this.user_info = bArr3;
        new Thread(new Runnable() {
            public void run() {
                boolean unused = EasyLink_v2.stopSending = false;
                EasyLink_v2.this.send(i);
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void send(int i) {
        while (!stopSending) {
            try {
                sendSync(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSync(int i) throws InterruptedException, IOException {
        byte[] bArr;
        byte[] bytes = syncHString.getBytes();
        byte[] bArr2 = new byte[2];
        int length = this.user_info.length;
        int i2 = 0;
        if (length == 0) {
            length++;
            this.user_info = new byte[1];
            this.user_info[0] = 0;
        }
        byte[] bArr3 = this.ssid;
        bArr2[0] = (byte) bArr3.length;
        byte[] bArr4 = this.key;
        bArr2[1] = (byte) bArr4.length;
        byte[] byteMerger = Helper.byteMerger(bArr2, Helper.byteMerger(bArr3, bArr4));
        for (int i3 = 0; i3 < 5; i3++) {
            sendData(new DatagramPacket(bytes, 20, new InetSocketAddress(InetAddress.getByName(head), getRandomNumber())), head);
            Thread.sleep((long) i);
        }
        if (length == 0) {
            while (i2 < byteMerger.length) {
                int i4 = i2 + 1;
                if (i4 < byteMerger.length) {
                    f3628ip = "239.126." + (byteMerger[i2] & UByte.MAX_VALUE) + "." + (byteMerger[i4] & UByte.MAX_VALUE);
                } else {
                    f3628ip = "239.126." + (byteMerger[i2] & UByte.MAX_VALUE) + ".0";
                }
                int i5 = (i2 / 2) + 20;
                sendData(new DatagramPacket(new byte[i5], i5, new InetSocketAddress(InetAddress.getByName(f3628ip), getRandomNumber())), f3628ip);
                Thread.sleep((long) i);
                i2 += 2;
            }
            return;
        }
        if (byteMerger.length % 2 != 0) {
            bArr = Helper.byteMerger(byteMerger, new byte[]{0, (byte) length, 0});
        } else if (this.user_info.length == 0) {
            bArr = Helper.byteMerger(byteMerger, new byte[]{(byte) length, 0, 0});
        } else {
            bArr = Helper.byteMerger(byteMerger, new byte[]{(byte) length, 0});
        }
        byte[] byteMerger2 = Helper.byteMerger(bArr, this.user_info);
        while (i2 < byteMerger2.length) {
            int i6 = i2 + 1;
            if (i6 < byteMerger2.length) {
                f3628ip = "239.126." + (byteMerger2[i2] & UByte.MAX_VALUE) + "." + (byteMerger2[i6] & UByte.MAX_VALUE);
            } else {
                f3628ip = "239.126." + (byteMerger2[i2] & UByte.MAX_VALUE) + ".0";
            }
            int i7 = (i2 / 2) + 20;
            sendData(new DatagramPacket(new byte[i7], i7, new InetSocketAddress(InetAddress.getByName(f3628ip), getRandomNumber())), f3628ip);
            Thread.sleep((long) i);
            i2 += 2;
        }
    }

    protected static void sendData(DatagramPacket datagramPacket, String str) throws IOException {
        MulticastSocket multicastSocket = new MulticastSocket(54064);
        multicastSocket.setReuseAddress(true);
        multicastSocket.setNetworkInterface(getWlanEth());
        multicastSocket.send(datagramPacket);
        multicastSocket.close();
    }

    public static NetworkInterface getWlanEth() {
        Enumeration<NetworkInterface> enumeration;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            enumeration = null;
        }
        StringBuilder sb = new StringBuilder();
        while (enumeration.hasMoreElements()) {
            NetworkInterface nextElement = enumeration.nextElement();
            sb.append(nextElement.getName() + " ");
            if (nextElement.getName().equals("wlan0")) {
                return nextElement;
            }
        }
        return null;
    }

    public void stopTransmitting() {
        stopSending = true;
    }

    private static int getRandomNumber() {
        int nextInt = new Random().nextInt(65536);
        if (nextInt < 10000) {
            return 65523;
        }
        return nextInt;
    }
}
