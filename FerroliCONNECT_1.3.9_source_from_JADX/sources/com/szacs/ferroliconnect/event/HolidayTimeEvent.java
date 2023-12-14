package com.szacs.ferroliconnect.event;

public class HolidayTimeEvent extends BaseEvent {
    private long seconds;

    public HolidayTimeEvent(long j) {
        super(Event.HOLIDAY_TIME);
        this.seconds = j;
    }

    public long getSeconds() {
        return this.seconds;
    }

    public void setSeconds(long j) {
        this.seconds = j;
    }
}
