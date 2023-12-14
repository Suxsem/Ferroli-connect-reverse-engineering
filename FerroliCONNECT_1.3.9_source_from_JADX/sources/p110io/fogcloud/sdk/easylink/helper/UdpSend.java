package p110io.fogcloud.sdk.easylink.helper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/* renamed from: io.fogcloud.sdk.easylink.helper.UdpSend */
public class UdpSend {

    /* renamed from: ds */
    private DatagramSocket f3626ds;

    public UdpSend() {
        try {
            this.f3626ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public DatagramSocket get_socket() {
        return this.f3626ds;
    }

    public UdpSend(int i) {
        try {
            this.f3626ds = new DatagramSocket(i);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setReuseAddr() {
        try {
            this.f3626ds.setReuseAddress(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendConfigData(int i, int i2) {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[i], i, InetAddress.getByName("255.255.255.255"), i2);
            if (!this.f3626ds.isClosed()) {
                this.f3626ds.send(datagramPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.f3626ds.close();
        }
    }

    public void sendUdpunicast(byte[] bArr, int i, int i2, InetAddress inetAddress) throws SocketException, UnknownHostException {
        DatagramPacket datagramPacket = new DatagramPacket(bArr, i, inetAddress, i2);
        try {
            if (!this.f3626ds.isClosed()) {
                this.f3626ds.send(datagramPacket);
            }
        } catch (IOException e) {
            this.f3626ds.close();
            e.printStackTrace();
        }
    }

    public void recvPackage(DatagramPacket datagramPacket) {
        try {
            this.f3626ds.setSoTimeout(3000);
            this.f3626ds.receive(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
