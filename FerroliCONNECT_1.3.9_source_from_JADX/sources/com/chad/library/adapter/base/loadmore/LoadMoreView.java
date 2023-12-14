package com.chad.library.adapter.base.loadmore;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import com.chad.library.adapter.base.BaseViewHolder;

public abstract class LoadMoreView {
    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_END = 4;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_LOADING = 2;
    private boolean mLoadMoreEndGone = false;
    private int mLoadMoreStatus = 1;

    @LayoutRes
    public abstract int getLayoutId();

    /* access modifiers changed from: protected */
    @IdRes
    public abstract int getLoadEndViewId();

    /* access modifiers changed from: protected */
    @IdRes
    public abstract int getLoadFailViewId();

    /* access modifiers changed from: protected */
    @IdRes
    public abstract int getLoadingViewId();

    public void setLoadMoreStatus(int i) {
        this.mLoadMoreStatus = i;
    }

    public int getLoadMoreStatus() {
        return this.mLoadMoreStatus;
    }

    public void convert(BaseViewHolder baseViewHolder) {
        int i = this.mLoadMoreStatus;
        if (i == 1) {
            visibleLoading(baseViewHolder, false);
            visibleLoadFail(baseViewHolder, false);
            visibleLoadEnd(baseViewHolder, false);
        } else if (i == 2) {
            visibleLoading(baseViewHolder, true);
            visibleLoadFail(baseViewHolder, false);
            visibleLoadEnd(baseViewHolder, false);
        } else if (i == 3) {
            visibleLoading(baseViewHolder, false);
            visibleLoadFail(baseViewHolder, true);
            visibleLoadEnd(baseViewHolder, false);
        } else if (i == 4) {
            visibleLoading(baseViewHolder, false);
            visibleLoadFail(baseViewHolder, false);
            visibleLoadEnd(baseViewHolder, true);
        }
    }

    private void visibleLoading(BaseViewHolder baseViewHolder, boolean z) {
        baseViewHolder.setVisible(getLoadingViewId(), z);
    }

    private void visibleLoadFail(BaseViewHolder baseViewHolder, boolean z) {
        baseViewHolder.setVisible(getLoadFailViewId(), z);
    }

    private void visibleLoadEnd(BaseViewHolder baseViewHolder, boolean z) {
        int loadEndViewId = getLoadEndViewId();
        if (loadEndViewId != 0) {
            baseViewHolder.setVisible(loadEndViewId, z);
        }
    }

    public final void setLoadMoreEndGone(boolean z) {
        this.mLoadMoreEndGone = z;
    }

    public final boolean isLoadEndMoreGone() {
        if (getLoadEndViewId() == 0) {
            return true;
        }
        return this.mLoadMoreEndGone;
    }

    @Deprecated
    public boolean isLoadEndGone() {
        return this.mLoadMoreEndGone;
    }
}
