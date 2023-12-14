package com.szacs.ferroliconnect.widget.Spinner;

import android.content.Context;
import android.widget.ListAdapter;

public class NiceSpinnerAdapterWrapper extends NiceSpinnerBaseAdapter {
    private final ListAdapter mBaseAdapter;

    public NiceSpinnerAdapterWrapper(Context context, ListAdapter listAdapter, int i, int i2) {
        super(context, i, i2);
        this.mBaseAdapter = listAdapter;
    }

    public int getCount() {
        return this.mBaseAdapter.getCount() - 1;
    }

    public Object getItem(int i) {
        if (i >= this.mSelectedIndex) {
            return this.mBaseAdapter.getItem(i + 1);
        }
        return this.mBaseAdapter.getItem(i);
    }

    public Object getItemInDataset(int i) {
        return this.mBaseAdapter.getItem(i);
    }
}
