package org.eclipse.paho.client.mqttv3;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class TimerPingSender implements MqttPingSender {
    /* access modifiers changed from: private */
    public static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.TimerPingSender";
    /* access modifiers changed from: private */
    public static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    /* access modifiers changed from: private */
    public ClientComms comms;
    private Timer timer;

    public void init(ClientComms clientComms) {
        if (clientComms != null) {
            this.comms = clientComms;
            return;
        }
        throw new IllegalArgumentException("ClientComms cannot be null.");
    }

    public void start() {
        String clientId = this.comms.getClient().getClientId();
        log.fine(CLASS_NAME, "start", "659", new Object[]{clientId});
        this.timer = new Timer("MQTT Ping: " + clientId);
        this.timer.schedule(new PingTask(this, (PingTask) null), this.comms.getKeepAlive());
    }

    public void stop() {
        log.fine(CLASS_NAME, "stop", "661", (Object[]) null);
        Timer timer2 = this.timer;
        if (timer2 != null) {
            timer2.cancel();
        }
    }

    public void schedule(long j) {
        this.timer.schedule(new PingTask(this, (PingTask) null), j);
    }

    private class PingTask extends TimerTask {
        private static final String methodName = "PingTask.run";

        private PingTask() {
        }

        /* synthetic */ PingTask(TimerPingSender timerPingSender, PingTask pingTask) {
            this();
        }

        public void run() {
            TimerPingSender.log.fine(TimerPingSender.CLASS_NAME, methodName, "660", new Object[]{new Long(System.currentTimeMillis())});
            TimerPingSender.this.comms.checkForActivity();
        }
    }
}
