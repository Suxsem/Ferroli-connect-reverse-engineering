package com.qmuiteam.qmui.widget.dialog;

import android.content.Context;
import android.support.p003v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.alpha.QMUIAlphaLinearLayout;

public class QMUIBottomSheetItemView extends QMUIAlphaLinearLayout {
    private AppCompatImageView mAppCompatImageView;
    private ViewStub mSubScript;
    private TextView mTextView;

    public QMUIBottomSheetItemView(Context context) {
        super(context);
    }

    public QMUIBottomSheetItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public QMUIBottomSheetItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mAppCompatImageView = (AppCompatImageView) findViewById(C1614R.C1616id.grid_item_image);
        this.mSubScript = (ViewStub) findViewById(C1614R.C1616id.grid_item_subscript);
        this.mTextView = (TextView) findViewById(C1614R.C1616id.grid_item_title);
    }

    public AppCompatImageView getAppCompatImageView() {
        return this.mAppCompatImageView;
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    public ViewStub getSubScript() {
        return this.mSubScript;
    }
}
