package com.chad.library.adapter.base.loadmore;

import com.chad.library.C0934R;

public final class SimpleLoadMoreView extends LoadMoreView {
    public int getLayoutId() {
        return C0934R.C0937layout.quick_view_load_more;
    }

    /* access modifiers changed from: protected */
    public int getLoadingViewId() {
        return C0934R.C0936id.load_more_loading_view;
    }

    /* access modifiers changed from: protected */
    public int getLoadFailViewId() {
        return C0934R.C0936id.load_more_load_fail_view;
    }

    /* access modifiers changed from: protected */
    public int getLoadEndViewId() {
        return C0934R.C0936id.load_more_load_end_view;
    }
}
