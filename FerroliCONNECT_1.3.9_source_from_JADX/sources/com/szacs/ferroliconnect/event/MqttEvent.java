package com.szacs.ferroliconnect.event;

public class MqttEvent extends BaseEvent {
    public static final int MQTT_CONNECT_FAIL = 3;
    public static final int MQTT_CONNECT_LOST = 2;
    public static final int MQTT_CONNECT_SUCCESS = 1;
    private int state;

    public MqttEvent(int i) {
        super(Event.EVENT_MQTT_SATE_CHANGE);
        if (i == 1) {
            this.state = 1;
        } else if (i == 2) {
            this.state = 2;
        } else if (i != 3) {
            this.state = 2;
        } else {
            this.state = 3;
        }
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        if (i == 1) {
            this.state = 1;
        } else if (i == 2) {
            this.state = 2;
        } else if (i != 3) {
            this.state = 2;
        } else {
            this.state = 3;
        }
    }
}
