package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class WebSocketSecureNetworkModule extends SSLNetworkModule {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private String host;
    private ByteArrayOutputStream outputStream = new ExtendedByteArrayOutputStream(this);
    private PipedInputStream pipedInputStream;
    private int port;
    ByteBuffer recievedPayload;
    private String uri;
    private WebSocketReceiver webSocketReceiver;

    public WebSocketSecureNetworkModule(SSLSocketFactory sSLSocketFactory, String str, String str2, int i, String str3) {
        super(sSLSocketFactory, str2, i, str3);
        this.uri = str;
        this.host = str2;
        this.port = i;
        this.pipedInputStream = new PipedInputStream();
        log.setResourceName(str3);
    }

    public void start() throws IOException, MqttException {
        super.start();
        new WebSocketHandshake(super.getInputStream(), super.getOutputStream(), this.uri, this.host, this.port).execute();
        this.webSocketReceiver = new WebSocketReceiver(getSocketInputStream(), this.pipedInputStream);
        this.webSocketReceiver.start("WssSocketReceiver");
    }

    /* access modifiers changed from: package-private */
    public OutputStream getSocketOutputStream() throws IOException {
        return super.getOutputStream();
    }

    /* access modifiers changed from: package-private */
    public InputStream getSocketInputStream() throws IOException {
        return super.getInputStream();
    }

    public InputStream getInputStream() throws IOException {
        return this.pipedInputStream;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.outputStream;
    }

    public void stop() throws IOException {
        getSocketOutputStream().write(new WebSocketFrame((byte) 8, true, "1000".getBytes()).encodeFrame());
        getSocketOutputStream().flush();
        WebSocketReceiver webSocketReceiver2 = this.webSocketReceiver;
        if (webSocketReceiver2 != null) {
            webSocketReceiver2.stop();
        }
        super.stop();
    }

    public String getServerURI() {
        return "wss://" + this.host + ":" + this.port;
    }
}
