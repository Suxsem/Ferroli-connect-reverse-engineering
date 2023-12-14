package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class WebSocketReceiver implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketReceiver";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private InputStream input;
    private Object lifecycle = new Object();
    private PipedOutputStream pipedOutputStream;
    private Thread receiverThread = null;
    private volatile boolean receiving;
    private boolean running = false;
    private boolean stopping = false;

    public WebSocketReceiver(InputStream inputStream, PipedInputStream pipedInputStream) throws IOException {
        this.input = inputStream;
        this.pipedOutputStream = new PipedOutputStream();
        pipedInputStream.connect(this.pipedOutputStream);
    }

    public void start(String str) {
        log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            if (!this.running) {
                this.running = true;
                this.receiverThread = new Thread(this, str);
                this.receiverThread.start();
            }
        }
    }

    public void stop() {
        boolean z = true;
        this.stopping = true;
        synchronized (this.lifecycle) {
            log.fine(CLASS_NAME, "stop", "850");
            if (this.running) {
                this.running = false;
                this.receiving = false;
                closeOutputStream();
            } else {
                z = false;
            }
        }
        if (z && !Thread.currentThread().equals(this.receiverThread)) {
            try {
                this.receiverThread.join();
            } catch (InterruptedException unused) {
            }
        }
        this.receiverThread = null;
        log.fine(CLASS_NAME, "stop", "851");
    }

    public void run() {
        while (this.running && this.input != null) {
            try {
                log.fine(CLASS_NAME, "run", "852");
                this.receiving = this.input.available() > 0;
                WebSocketFrame webSocketFrame = new WebSocketFrame(this.input);
                if (!webSocketFrame.isCloseFlag()) {
                    for (byte write : webSocketFrame.getPayload()) {
                        this.pipedOutputStream.write(write);
                    }
                    this.pipedOutputStream.flush();
                } else if (!this.stopping) {
                    throw new IOException("Server sent a WebSocket Frame with the Stop OpCode");
                }
                this.receiving = false;
            } catch (IOException unused) {
                stop();
            }
        }
    }

    private void closeOutputStream() {
        try {
            this.pipedOutputStream.close();
        } catch (IOException unused) {
        }
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean isReceiving() {
        return this.receiving;
    }
}
