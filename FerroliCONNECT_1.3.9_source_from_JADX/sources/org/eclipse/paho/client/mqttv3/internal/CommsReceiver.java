package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsReceiver implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsReceiver";
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private ClientComms clientComms = null;
    private ClientState clientState = null;

    /* renamed from: in */
    private MqttInputStream f4130in;
    private Object lifecycle = new Object();
    private Thread recThread = null;
    private Future receiverFuture;
    private volatile boolean receiving;
    private boolean running = false;
    private final Semaphore runningSemaphore = new Semaphore(1);
    private String threadName;
    private CommsTokenStore tokenStore = null;

    public CommsReceiver(ClientComms clientComms2, ClientState clientState2, CommsTokenStore commsTokenStore, InputStream inputStream) {
        this.f4130in = new MqttInputStream(clientState2, inputStream);
        this.clientComms = clientComms2;
        this.clientState = clientState2;
        this.tokenStore = commsTokenStore;
        log.setResourceName(clientComms2.getClient().getClientId());
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            if (!this.running) {
                this.running = true;
                this.receiverFuture = executorService.submit(this);
            }
        }
    }

    public void stop() {
        Semaphore semaphore;
        synchronized (this.lifecycle) {
            if (this.receiverFuture != null) {
                this.receiverFuture.cancel(true);
            }
            log.fine(CLASS_NAME, "stop", "850");
            if (this.running) {
                this.running = false;
                this.receiving = false;
                if (!Thread.currentThread().equals(this.recThread)) {
                    try {
                        this.runningSemaphore.acquire();
                        semaphore = this.runningSemaphore;
                    } catch (InterruptedException unused) {
                        semaphore = this.runningSemaphore;
                    } catch (Throwable th) {
                        this.runningSemaphore.release();
                        throw th;
                    }
                    semaphore.release();
                }
            }
        }
        this.recThread = null;
        log.fine(CLASS_NAME, "stop", "851");
    }

    public void run() {
        this.recThread = Thread.currentThread();
        this.recThread.setName(this.threadName);
        try {
            this.runningSemaphore.acquire();
            MqttToken mqttToken = null;
            while (this.running && this.f4130in != null) {
                try {
                    log.fine(CLASS_NAME, "run", "852");
                    this.receiving = this.f4130in.available() > 0;
                    MqttWireMessage readMqttWireMessage = this.f4130in.readMqttWireMessage();
                    this.receiving = false;
                    if (readMqttWireMessage instanceof MqttAck) {
                        mqttToken = this.tokenStore.getToken(readMqttWireMessage);
                        if (mqttToken != null) {
                            synchronized (mqttToken) {
                                this.clientState.notifyReceivedAck((MqttAck) readMqttWireMessage);
                            }
                        } else {
                            if (!(readMqttWireMessage instanceof MqttPubRec) && !(readMqttWireMessage instanceof MqttPubComp)) {
                                if (!(readMqttWireMessage instanceof MqttPubAck)) {
                                    throw new MqttException(6);
                                }
                            }
                            log.fine(CLASS_NAME, "run", "857");
                        }
                    } else if (readMqttWireMessage != null) {
                        this.clientState.notifyReceivedMsg(readMqttWireMessage);
                    }
                } catch (MqttException e) {
                    log.fine(CLASS_NAME, "run", "856", (Object[]) null, e);
                    this.running = false;
                    this.clientComms.shutdownConnection(mqttToken, e);
                } catch (IOException e2) {
                    try {
                        log.fine(CLASS_NAME, "run", "853");
                        this.running = false;
                        if (!this.clientComms.isDisconnecting()) {
                            this.clientComms.shutdownConnection(mqttToken, new MqttException(32109, e2));
                        }
                    } catch (Throwable th) {
                        this.receiving = false;
                        this.runningSemaphore.release();
                        throw th;
                    }
                }
                this.receiving = false;
                this.runningSemaphore.release();
            }
            log.fine(CLASS_NAME, "run", "854");
        } catch (InterruptedException unused) {
            this.running = false;
        }
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean isReceiving() {
        return this.receiving;
    }
}
