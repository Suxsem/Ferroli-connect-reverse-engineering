package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class MqttInputStream extends InputStream {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private ByteArrayOutputStream bais;
    private ClientState clientState = null;

    /* renamed from: in */
    private DataInputStream f4132in;
    private byte[] packet;
    private long packetLen;
    private long remLen;

    public MqttInputStream(ClientState clientState2, InputStream inputStream) {
        this.clientState = clientState2;
        this.f4132in = new DataInputStream(inputStream);
        this.bais = new ByteArrayOutputStream();
        this.remLen = -1;
    }

    public int read() throws IOException {
        return this.f4132in.read();
    }

    public int available() throws IOException {
        return this.f4132in.available();
    }

    public void close() throws IOException {
        this.f4132in.close();
    }

    public MqttWireMessage readMqttWireMessage() throws IOException, MqttException {
        try {
            if (this.remLen < 0) {
                this.bais.reset();
                byte readByte = this.f4132in.readByte();
                this.clientState.notifyReceivedBytes(1);
                byte b = (byte) ((readByte >>> 4) & 15);
                if (b < 1 || b > 14) {
                    throw ExceptionHelper.createMqttException(32108);
                }
                this.remLen = MqttWireMessage.readMBI(this.f4132in).getValue();
                this.bais.write(readByte);
                this.bais.write(MqttWireMessage.encodeMBI(this.remLen));
                this.packet = new byte[((int) (((long) this.bais.size()) + this.remLen))];
                this.packetLen = 0;
            }
            if (this.remLen < 0) {
                return null;
            }
            readFully();
            this.remLen = -1;
            byte[] byteArray = this.bais.toByteArray();
            System.arraycopy(byteArray, 0, this.packet, 0, byteArray.length);
            MqttWireMessage createWireMessage = MqttWireMessage.createWireMessage(this.packet);
            log.fine(CLASS_NAME, "readMqttWireMessage", "501", new Object[]{createWireMessage});
            return createWireMessage;
        } catch (SocketTimeoutException unused) {
            return null;
        }
    }

    private void readFully() throws IOException {
        int size = this.bais.size();
        long j = this.packetLen;
        int i = size + ((int) j);
        int i2 = (int) (this.remLen - j);
        if (i2 >= 0) {
            int i3 = 0;
            while (i3 < i2) {
                try {
                    int read = this.f4132in.read(this.packet, i + i3, i2 - i3);
                    this.clientState.notifyReceivedBytes(read);
                    if (read >= 0) {
                        i3 += read;
                    } else {
                        throw new EOFException();
                    }
                } catch (SocketTimeoutException e) {
                    this.packetLen += (long) i3;
                    throw e;
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }
}
