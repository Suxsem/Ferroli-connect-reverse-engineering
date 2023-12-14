package com.szacs.ferroliconnect.widget.wheel;

public class StrericWheelAdapter implements WheelAdapter {
    private String[] strContents;

    public int getMaximumLength() {
        return 5;
    }

    public StrericWheelAdapter(String[] strArr) {
        this.strContents = strArr;
    }

    public String[] getStrContents() {
        return this.strContents;
    }

    public void setStrContents(String[] strArr) {
        this.strContents = strArr;
    }

    public String getItem(int i) {
        if (i < 0 || i >= getItemsCount()) {
            return null;
        }
        return this.strContents[i];
    }

    public int getItemsCount() {
        return this.strContents.length;
    }
}
