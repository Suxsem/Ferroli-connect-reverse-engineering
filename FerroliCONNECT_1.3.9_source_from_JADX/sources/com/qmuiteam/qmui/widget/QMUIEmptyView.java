package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;

public class QMUIEmptyView extends FrameLayout {
    protected Button mButton;
    private TextView mDetailTextView;
    private QMUILoadingView mLoadingView;
    private TextView mTitleTextView;

    public QMUIEmptyView(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIEmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMUIEmptyView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIEmptyView);
        Boolean valueOf = Boolean.valueOf(obtainStyledAttributes.getBoolean(C1614R.styleable.QMUIEmptyView_qmui_show_loading, false));
        String string = obtainStyledAttributes.getString(C1614R.styleable.QMUIEmptyView_qmui_title_text);
        String string2 = obtainStyledAttributes.getString(C1614R.styleable.QMUIEmptyView_qmui_detail_text);
        String string3 = obtainStyledAttributes.getString(C1614R.styleable.QMUIEmptyView_qmui_btn_text);
        obtainStyledAttributes.recycle();
        show(valueOf.booleanValue(), string, string2, string3, (View.OnClickListener) null);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(C1614R.C1617layout.qmui_empty_view, this, true);
        this.mLoadingView = (QMUILoadingView) findViewById(C1614R.C1616id.empty_view_loading);
        this.mTitleTextView = (TextView) findViewById(C1614R.C1616id.empty_view_title);
        this.mDetailTextView = (TextView) findViewById(C1614R.C1616id.empty_view_detail);
        this.mButton = (Button) findViewById(C1614R.C1616id.empty_view_button);
    }

    public void show(boolean z, String str, String str2, String str3, View.OnClickListener onClickListener) {
        setLoadingShowing(z);
        setTitleText(str);
        setDetailText(str2);
        setButton(str3, onClickListener);
        show();
    }

    public void show(boolean z) {
        setLoadingShowing(z);
        setTitleText((String) null);
        setDetailText((String) null);
        setButton((String) null, (View.OnClickListener) null);
        show();
    }

    public void show(String str, String str2) {
        setLoadingShowing(false);
        setTitleText(str);
        setDetailText(str2);
        setButton((String) null, (View.OnClickListener) null);
        show();
    }

    public void show() {
        setVisibility(0);
    }

    public void hide() {
        setVisibility(8);
        setLoadingShowing(false);
        setTitleText((String) null);
        setDetailText((String) null);
        setButton((String) null, (View.OnClickListener) null);
    }

    public boolean isShowing() {
        return getVisibility() == 0;
    }

    public boolean isLoading() {
        return this.mLoadingView.getVisibility() == 0;
    }

    public void setLoadingShowing(boolean z) {
        this.mLoadingView.setVisibility(z ? 0 : 8);
    }

    public void setTitleText(String str) {
        this.mTitleTextView.setText(str);
        this.mTitleTextView.setVisibility(str != null ? 0 : 8);
    }

    public void setDetailText(String str) {
        this.mDetailTextView.setText(str);
        this.mDetailTextView.setVisibility(str != null ? 0 : 8);
    }

    public void setTitleColor(int i) {
        this.mTitleTextView.setTextColor(i);
    }

    public void setDetailColor(int i) {
        this.mDetailTextView.setTextColor(i);
    }

    public void setButton(String str, View.OnClickListener onClickListener) {
        this.mButton.setText(str);
        this.mButton.setVisibility(str != null ? 0 : 8);
        this.mButton.setOnClickListener(onClickListener);
    }
}
