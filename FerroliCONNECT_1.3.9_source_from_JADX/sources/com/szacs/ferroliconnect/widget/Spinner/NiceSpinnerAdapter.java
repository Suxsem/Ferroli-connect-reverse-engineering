package com.szacs.ferroliconnect.widget.Spinner;

import android.content.Context;
import java.util.List;

public class NiceSpinnerAdapter<T> extends NiceSpinnerBaseAdapter {
    private final List<T> mItems;

    public NiceSpinnerAdapter(Context context, List<T> list, int i, int i2) {
        super(context, i, i2);
        this.mItems = list;
    }

    public int getCount() {
        return this.mItems.size() - 1;
    }

    public T getItem(int i) {
        if (i >= this.mSelectedIndex) {
            return this.mItems.get(i + 1);
        }
        return this.mItems.get(i);
    }

    public T getItemInDataset(int i) {
        return this.mItems.get(i);
    }
}
