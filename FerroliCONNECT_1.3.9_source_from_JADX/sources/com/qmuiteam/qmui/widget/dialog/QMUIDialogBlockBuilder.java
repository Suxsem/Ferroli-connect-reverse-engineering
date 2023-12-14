package com.qmuiteam.qmui.widget.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIWrapContentScrollView;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;

public class QMUIDialogBlockBuilder extends QMUIDialogBuilder<QMUIDialogBlockBuilder> {
    private CharSequence mContent;

    public QMUIDialogBlockBuilder(Context context) {
        super(context);
        setActionDivider(1, C1614R.color.qmui_config_color_separator, 0, 0);
    }

    public QMUIDialogBlockBuilder setContent(CharSequence charSequence) {
        this.mContent = charSequence;
        return this;
    }

    public QMUIDialogBlockBuilder setContent(int i) {
        this.mContent = getBaseContext().getResources().getString(i);
        return this;
    }

    /* access modifiers changed from: protected */
    public void onConfigTitleView(TextView textView) {
        super.onConfigTitleView(textView);
        CharSequence charSequence = this.mContent;
        if (charSequence == null || charSequence.length() == 0) {
            TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes((AttributeSet) null, C1614R.styleable.QMUIDialogTitleTvCustomDef, C1614R.attr.qmui_dialog_title_style, 0);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == C1614R.styleable.QMUIDialogTitleTvCustomDef_qmui_paddingBottomWhenNotContent) {
                    textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), obtainStyledAttributes.getDimensionPixelSize(index, textView.getPaddingBottom()));
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
        CharSequence charSequence = this.mContent;
        if (charSequence != null && charSequence.length() > 0) {
            QMUISpanTouchFixTextView qMUISpanTouchFixTextView = new QMUISpanTouchFixTextView(context);
            QMUIResHelper.assignTextViewWithAttr(qMUISpanTouchFixTextView, C1614R.attr.qmui_dialog_message_content_style);
            if (!hasTitle()) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, C1614R.styleable.QMUIDialogMessageTvCustomDef, C1614R.attr.qmui_dialog_message_content_style, 0);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == C1614R.styleable.QMUIDialogMessageTvCustomDef_qmui_paddingTopWhenNotTitle) {
                        qMUISpanTouchFixTextView.setPadding(qMUISpanTouchFixTextView.getPaddingLeft(), obtainStyledAttributes.getDimensionPixelSize(index, qMUISpanTouchFixTextView.getPaddingTop()), qMUISpanTouchFixTextView.getPaddingRight(), qMUISpanTouchFixTextView.getPaddingBottom());
                    }
                }
                obtainStyledAttributes.recycle();
            }
            qMUISpanTouchFixTextView.setText(this.mContent);
            QMUIWrapContentScrollView qMUIWrapContentScrollView = new QMUIWrapContentScrollView(context);
            qMUIWrapContentScrollView.setMaxHeight(getContentAreaMaxHeight());
            qMUIWrapContentScrollView.addView(qMUISpanTouchFixTextView);
            viewGroup.addView(qMUIWrapContentScrollView);
        }
    }

    public QMUIDialog create(int i) {
        setActionContainerOrientation(1);
        return super.create(i);
    }
}
