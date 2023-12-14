package com.qmuiteam.qmui.widget;

import android.support.p000v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

public abstract class QMUIPagerAdapter extends PagerAdapter {
    private SparseArray<Object> mScrapItems = new SparseArray<>();

    /* access modifiers changed from: protected */
    public abstract void destroy(ViewGroup viewGroup, int i, Object obj);

    /* access modifiers changed from: protected */
    public abstract Object hydrate(ViewGroup viewGroup, int i);

    /* access modifiers changed from: protected */
    public abstract void populate(ViewGroup viewGroup, Object obj, int i);

    public final Object instantiateItem(ViewGroup viewGroup, int i) {
        Object obj = this.mScrapItems.get(i);
        if (obj == null) {
            obj = hydrate(viewGroup, i);
        } else {
            this.mScrapItems.remove(i);
        }
        populate(viewGroup, obj, i);
        return obj;
    }

    public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        destroy(viewGroup, i, obj);
        this.mScrapItems.put(i, obj);
    }
}
