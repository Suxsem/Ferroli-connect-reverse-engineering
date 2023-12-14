package com.szacs.ferroliconnect.event;

public class BoilerHeatTargetTempSetOptions extends BaseEvent {
    private int option;

    public BoilerHeatTargetTempSetOptions(int i) {
        super(Event.EVENT_TARGET_TEMP_SET_OPTION);
        this.option = i;
    }

    public int getOption() {
        return this.option;
    }

    public void setOption(int i) {
        this.option = i;
    }
}
