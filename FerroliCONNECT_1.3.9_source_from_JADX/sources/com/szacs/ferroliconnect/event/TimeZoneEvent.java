package com.szacs.ferroliconnect.event;

public class TimeZoneEvent extends BaseEvent {
    private boolean autoDaylight;
    private int seconds;

    public TimeZoneEvent(int i, boolean z) {
        super(Event.EVENT_CHANGE_TIMEZONE);
        this.seconds = i;
        this.autoDaylight = z;
    }

    public TimeZoneEvent() {
        super(Event.EVENT_CHANGE_TIMEZONE);
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int i) {
        this.seconds = i;
    }

    public boolean isAutoDaylight() {
        return this.autoDaylight;
    }

    public void setAutoDaylight(boolean z) {
        this.autoDaylight = z;
    }
}
