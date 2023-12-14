package com.szacs.ferroliconnect.event;

public class TargetSettingOptionEvent extends BaseEvent {
    private int option;

    public TargetSettingOptionEvent(int i) {
        super(Event.EVENT_CHANGE_TARGET_SETTING_OPTION);
        this.option = i;
    }

    public int getOption() {
        return this.option;
    }

    public void setOption(int i) {
        this.option = i;
    }
}
