package com.qmuiteam.qmui.widget.grouplist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUILangHelper;

public class QMUIGroupListSectionHeaderFooterView extends LinearLayout {
    private TextView mTextView;

    public QMUIGroupListSectionHeaderFooterView(Context context) {
        this(context, (AttributeSet) null, C1614R.attr.QMUIGroupListSectionViewStyle);
    }

    public QMUIGroupListSectionHeaderFooterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUIGroupListSectionViewStyle);
    }

    public QMUIGroupListSectionHeaderFooterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public QMUIGroupListSectionHeaderFooterView(Context context, CharSequence charSequence) {
        this(context);
        setText(charSequence);
    }

    public QMUIGroupListSectionHeaderFooterView(Context context, CharSequence charSequence, boolean z) {
        this(context);
        if (z) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0);
        }
        setText(charSequence);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(C1614R.C1617layout.qmui_group_list_section_layout, this, true);
        setGravity(80);
        this.mTextView = (TextView) findViewById(C1614R.C1616id.group_list_section_header_textView);
    }

    public void setText(CharSequence charSequence) {
        if (QMUILangHelper.isNullOrEmpty(charSequence)) {
            this.mTextView.setVisibility(8);
        } else {
            this.mTextView.setVisibility(0);
        }
        this.mTextView.setText(charSequence);
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    public void setTextGravity(int i) {
        this.mTextView.setGravity(i);
    }
}
