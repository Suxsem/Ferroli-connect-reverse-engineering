package com.szacs.ferroliconnect.event;

public class ApplyEvent extends BaseEvent {
    private boolean[] days;

    public ApplyEvent() {
        super(Event.EVENT_APPLY_PROGRAM);
    }

    public boolean[] getDays() {
        return this.days;
    }

    public void setDays(boolean[] zArr) {
        this.days = zArr;
    }
}
