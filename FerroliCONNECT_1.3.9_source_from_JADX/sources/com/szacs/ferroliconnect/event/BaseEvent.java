package com.szacs.ferroliconnect.event;

public class BaseEvent {
    private Event event;

    public BaseEvent(Event event2) {
        this.event = event2;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event2) {
        this.event = event2;
    }
}
