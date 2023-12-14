package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import java.util.ArrayList;
import java.util.List;

public class QMUIObservableScrollView extends ScrollView {
    private List<OnScrollChangedListener> mOnScrollChangedListeners;

    public interface OnScrollChangedListener {
        void onScrollChanged(QMUIObservableScrollView qMUIObservableScrollView, int i, int i2, int i3, int i4);
    }

    public QMUIObservableScrollView(Context context) {
        super(context);
    }

    public QMUIObservableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QMUIObservableScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        if (this.mOnScrollChangedListeners == null) {
            this.mOnScrollChangedListeners = new ArrayList();
        }
        if (!this.mOnScrollChangedListeners.contains(onScrollChangedListener)) {
            this.mOnScrollChangedListeners.add(onScrollChangedListener);
        }
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        List<OnScrollChangedListener> list = this.mOnScrollChangedListeners;
        if (list != null) {
            list.remove(onScrollChangedListener);
        }
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        List<OnScrollChangedListener> list = this.mOnScrollChangedListeners;
        if (list != null && !list.isEmpty()) {
            for (OnScrollChangedListener onScrollChanged : this.mOnScrollChangedListeners) {
                onScrollChanged.onScrollChanged(this, i, i2, i3, i4);
            }
        }
    }
}
