package com.szacs.ferroliconnect.adapter;

import com.szacs.ferroliconnect.widget.wheel.WheelAdapter;
import java.util.ArrayList;
import java.util.List;

public class ArrayWheelAdapter implements WheelAdapter {
    private List<String> items;

    public int getMaximumLength() {
        return 3;
    }

    public ArrayWheelAdapter(List<String> list) {
        if (list != null) {
            this.items = list;
        } else {
            this.items = new ArrayList();
        }
    }

    public List<String> getStrContents() {
        return this.items;
    }

    public void setStrContents(List<String> list) {
        this.items = list;
    }

    public String getItem(int i) {
        if (i < 0 || i >= getItemsCount()) {
            return null;
        }
        return this.items.get(i);
    }

    public int getItemsCount() {
        return this.items.size();
    }
}
