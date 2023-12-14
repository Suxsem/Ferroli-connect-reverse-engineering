package com.qmuiteam.qmui.widget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.p000v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class QMUIDialogBuilder<T extends QMUIDialogBuilder> {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected QMUILinearLayout mActionContainer;
    private int mActionContainerOrientation = 0;
    private int mActionDividerColorRes = C1614R.color.qmui_config_color_separator;
    private int mActionDividerInsetEnd = 0;
    private int mActionDividerInsetStart = 0;
    private int mActionDividerThickness = 0;
    protected List<QMUIDialogAction> mActions = new ArrayList();
    protected View mAnchorBottomView;
    protected View mAnchorTopView;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private boolean mChangeAlphaForPressOrDisable = true;
    private int mContentAreaMaxHeight = -1;
    /* access modifiers changed from: private */
    public Context mContext;
    protected QMUIDialog mDialog;
    protected QMUIDialogView mDialogView;
    private QMUIDialogView.OnDecorationListener mOnDecorationListener;
    protected LinearLayout mRootView;
    protected String mTitle;
    protected TextView mTitleView;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    /* access modifiers changed from: protected */
    public void onConfigTitleView(TextView textView) {
    }

    /* access modifiers changed from: protected */
    public abstract void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context);

    public QMUIDialogBuilder(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public int getContentAreaMaxHeight() {
        int i = this.mContentAreaMaxHeight;
        if (i != -1) {
            return i;
        }
        double screenHeight = (double) QMUIDisplayHelper.getScreenHeight(this.mContext);
        Double.isNaN(screenHeight);
        return ((int) (screenHeight * 0.85d)) - QMUIDisplayHelper.dp2px(this.mContext, 100);
    }

    public Context getBaseContext() {
        return this.mContext;
    }

    public T setContentAreaMaxHeight(int i) {
        this.mContentAreaMaxHeight = i;
        return this;
    }

    public T setTitle(String str) {
        if (str != null && str.length() > 0) {
            this.mTitle = str + this.mContext.getString(C1614R.string.qmui_tool_fixellipsize);
        }
        return this;
    }

    public T setTitle(int i) {
        return setTitle(this.mContext.getResources().getString(i));
    }

    public T setCancelable(boolean z) {
        this.mCancelable = z;
        return this;
    }

    public T setCanceledOnTouchOutside(boolean z) {
        this.mCanceledOnTouchOutside = z;
        return this;
    }

    public T setOnDecorationListener(QMUIDialogView.OnDecorationListener onDecorationListener) {
        this.mOnDecorationListener = onDecorationListener;
        return this;
    }

    public T setActionContainerOrientation(int i) {
        this.mActionContainerOrientation = i;
        return this;
    }

    public T setChangeAlphaForPressOrDisable(boolean z) {
        this.mChangeAlphaForPressOrDisable = z;
        return this;
    }

    public T setActionDivider(int i, int i2, int i3, int i4) {
        this.mActionDividerThickness = i;
        this.mActionDividerColorRes = i2;
        this.mActionDividerInsetStart = i3;
        this.mActionDividerInsetEnd = i4;
        return this;
    }

    public T addAction(@Nullable QMUIDialogAction qMUIDialogAction) {
        if (qMUIDialogAction != null) {
            this.mActions.add(qMUIDialogAction);
        }
        return this;
    }

    public T addAction(int i, QMUIDialogAction.ActionListener actionListener) {
        return addAction(0, i, actionListener);
    }

    public T addAction(CharSequence charSequence, QMUIDialogAction.ActionListener actionListener) {
        return addAction(0, charSequence, 1, actionListener);
    }

    public T addAction(int i, int i2, QMUIDialogAction.ActionListener actionListener) {
        return addAction(i, i2, 1, actionListener);
    }

    public T addAction(int i, CharSequence charSequence, QMUIDialogAction.ActionListener actionListener) {
        return addAction(i, charSequence, 1, actionListener);
    }

    public T addAction(int i, int i2, int i3, QMUIDialogAction.ActionListener actionListener) {
        return addAction(i, (CharSequence) this.mContext.getResources().getString(i2), i3, actionListener);
    }

    public T addAction(int i, CharSequence charSequence, int i2, QMUIDialogAction.ActionListener actionListener) {
        this.mActions.add(new QMUIDialogAction(this.mContext, i, charSequence, i2, actionListener));
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean hasTitle() {
        String str = this.mTitle;
        return (str == null || str.length() == 0) ? false : true;
    }

    public QMUIDialog show() {
        QMUIDialog create = create();
        create.show();
        return create;
    }

    public QMUIDialog create() {
        return create(C1614R.style.QMUI_Dialog);
    }

    @SuppressLint({"InflateParams"})
    public QMUIDialog create(@StyleRes int i) {
        this.mDialog = new QMUIDialog(this.mContext, i);
        Context context = this.mDialog.getContext();
        this.mRootView = (LinearLayout) LayoutInflater.from(context).inflate(C1614R.C1617layout.qmui_dialog_layout, (ViewGroup) null);
        this.mDialogView = (QMUIDialogView) this.mRootView.findViewById(C1614R.C1616id.dialog);
        this.mDialogView.setOnDecorationListener(this.mOnDecorationListener);
        this.mAnchorTopView = this.mRootView.findViewById(C1614R.C1616id.anchor_top);
        this.mAnchorBottomView = this.mRootView.findViewById(C1614R.C1616id.anchor_bottom);
        onCreateTitle(this.mDialog, this.mDialogView, context);
        onCreateContent(this.mDialog, this.mDialogView, context);
        onCreateHandlerBar(this.mDialog, this.mDialogView, context);
        this.mDialog.addContentView(this.mRootView, new ViewGroup.LayoutParams(-1, -2));
        this.mDialog.setCancelable(this.mCancelable);
        this.mDialog.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
        onAfter(this.mDialog, this.mRootView, context);
        return this.mDialog;
    }

    /* access modifiers changed from: protected */
    public void onCreateTitle(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
        if (hasTitle()) {
            this.mTitleView = new TextView(context);
            this.mTitleView.setText(this.mTitle);
            QMUIResHelper.assignTextViewWithAttr(this.mTitleView, C1614R.attr.qmui_dialog_title_style);
            onConfigTitleView(this.mTitleView);
            this.mTitleView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            viewGroup.addView(this.mTitleView);
        }
    }

    public TextView getTitleView() {
        return this.mTitleView;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
        if (r10 == 3) goto L_0x0061;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0104  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreateHandlerBar(com.qmuiteam.qmui.widget.dialog.QMUIDialog r17, android.view.ViewGroup r18, android.content.Context r19) {
        /*
            r16 = this;
            r0 = r16
            r1 = r19
            java.util.List<com.qmuiteam.qmui.widget.dialog.QMUIDialogAction> r2 = r0.mActions
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x0115
            int[] r3 = com.qmuiteam.qmui.C1614R.styleable.QMUIDialogActionContainerCustomDef
            int r4 = com.qmuiteam.qmui.C1614R.attr.qmui_dialog_action_container_style
            r5 = 0
            r6 = 0
            android.content.res.TypedArray r3 = r1.obtainStyledAttributes(r5, r3, r4, r6)
            int r4 = r3.getIndexCount()
            r7 = -1
            r8 = 1
            r9 = 0
            r10 = 1
            r11 = 0
            r12 = -1
            r13 = 0
        L_0x0021:
            if (r9 >= r4) goto L_0x004d
            int r14 = r3.getIndex(r9)
            int r15 = com.qmuiteam.qmui.C1614R.styleable.f3096xb857e9e9
            if (r14 != r15) goto L_0x0030
            int r10 = r3.getInteger(r14, r10)
            goto L_0x004a
        L_0x0030:
            int r15 = com.qmuiteam.qmui.C1614R.styleable.f3095x3a88cc0c
            if (r14 != r15) goto L_0x0039
            int r11 = r3.getInteger(r14, r6)
            goto L_0x004a
        L_0x0039:
            int r15 = com.qmuiteam.qmui.C1614R.styleable.QMUIDialogActionContainerCustomDef_qmui_dialog_action_space
            if (r14 != r15) goto L_0x0042
            int r13 = r3.getDimensionPixelSize(r14, r6)
            goto L_0x004a
        L_0x0042:
            int r15 = com.qmuiteam.qmui.C1614R.styleable.QMUIDialogActionContainerCustomDef_qmui_dialog_action_height
            if (r14 != r15) goto L_0x004a
            int r12 = r3.getDimensionPixelSize(r14, r6)
        L_0x004a:
            int r9 = r9 + 1
            goto L_0x0021
        L_0x004d:
            r3.recycle()
            int r3 = r0.mActionContainerOrientation
            if (r3 != r8) goto L_0x0056
        L_0x0054:
            r11 = -1
            goto L_0x0061
        L_0x0056:
            if (r10 != 0) goto L_0x005a
            r11 = r2
            goto L_0x0061
        L_0x005a:
            if (r10 != r8) goto L_0x005e
            r11 = 0
            goto L_0x0061
        L_0x005e:
            r3 = 3
            if (r10 != r3) goto L_0x0054
        L_0x0061:
            com.qmuiteam.qmui.layout.QMUILinearLayout r3 = new com.qmuiteam.qmui.layout.QMUILinearLayout
            int r4 = com.qmuiteam.qmui.C1614R.attr.qmui_dialog_action_container_style
            r3.<init>(r1, r5, r4)
            r0.mActionContainer = r3
            com.qmuiteam.qmui.layout.QMUILinearLayout r3 = r0.mActionContainer
            int r4 = r0.mActionContainerOrientation
            if (r4 != r8) goto L_0x0072
            r4 = 1
            goto L_0x0073
        L_0x0072:
            r4 = 0
        L_0x0073:
            r3.setOrientation(r4)
            com.qmuiteam.qmui.layout.QMUILinearLayout r3 = r0.mActionContainer
            android.view.ViewGroup$LayoutParams r4 = new android.view.ViewGroup$LayoutParams
            r5 = -2
            r4.<init>(r7, r5)
            r3.setLayoutParams(r4)
        L_0x0081:
            if (r6 >= r2) goto L_0x00f5
            if (r11 != r6) goto L_0x008e
            com.qmuiteam.qmui.layout.QMUILinearLayout r3 = r0.mActionContainer
            android.view.View r4 = r0.createActionContainerSpace(r1)
            r3.addView(r4)
        L_0x008e:
            java.util.List<com.qmuiteam.qmui.widget.dialog.QMUIDialogAction> r3 = r0.mActions
            java.lang.Object r3 = r3.get(r6)
            com.qmuiteam.qmui.widget.dialog.QMUIDialogAction r3 = (com.qmuiteam.qmui.widget.dialog.QMUIDialogAction) r3
            int r4 = r0.mActionContainerOrientation
            if (r4 != r8) goto L_0x00a0
            android.widget.LinearLayout$LayoutParams r4 = new android.widget.LinearLayout$LayoutParams
            r4.<init>(r7, r12)
            goto L_0x00b5
        L_0x00a0:
            android.widget.LinearLayout$LayoutParams r4 = new android.widget.LinearLayout$LayoutParams
            r4.<init>(r5, r12)
            if (r11 < 0) goto L_0x00ae
            if (r6 < r11) goto L_0x00ac
            r4.leftMargin = r13
            goto L_0x00ae
        L_0x00ac:
            r4.rightMargin = r13
        L_0x00ae:
            r9 = 2
            if (r10 != r9) goto L_0x00b5
            r9 = 1065353216(0x3f800000, float:1.0)
            r4.weight = r9
        L_0x00b5:
            com.qmuiteam.qmui.widget.dialog.QMUIDialog r9 = r0.mDialog
            com.qmuiteam.qmui.layout.QMUIButton r3 = r3.buildActionView(r9, r6)
            int r9 = r0.mActionDividerThickness
            if (r9 <= 0) goto L_0x00e2
            if (r6 <= 0) goto L_0x00e2
            if (r11 == r6) goto L_0x00e2
            int r14 = r0.mActionContainerOrientation
            if (r14 != r8) goto L_0x00d5
            int r14 = r0.mActionDividerInsetStart
            int r15 = r0.mActionDividerInsetEnd
            int r5 = r0.mActionDividerColorRes
            int r5 = android.support.p000v4.content.ContextCompat.getColor(r1, r5)
            r3.onlyShowTopDivider(r14, r15, r9, r5)
            goto L_0x00e2
        L_0x00d5:
            int r5 = r0.mActionDividerInsetStart
            int r14 = r0.mActionDividerInsetEnd
            int r15 = r0.mActionDividerColorRes
            int r15 = android.support.p000v4.content.ContextCompat.getColor(r1, r15)
            r3.onlyShowLeftDivider(r5, r14, r9, r15)
        L_0x00e2:
            boolean r5 = r0.mChangeAlphaForPressOrDisable
            r3.setChangeAlphaWhenDisable(r5)
            boolean r5 = r0.mChangeAlphaForPressOrDisable
            r3.setChangeAlphaWhenPress(r5)
            com.qmuiteam.qmui.layout.QMUILinearLayout r5 = r0.mActionContainer
            r5.addView(r3, r4)
            int r6 = r6 + 1
            r5 = -2
            goto L_0x0081
        L_0x00f5:
            if (r11 != r2) goto L_0x0100
            com.qmuiteam.qmui.layout.QMUILinearLayout r2 = r0.mActionContainer
            android.view.View r1 = r0.createActionContainerSpace(r1)
            r2.addView(r1)
        L_0x0100:
            int r1 = r0.mActionContainerOrientation
            if (r1 != 0) goto L_0x010e
            com.qmuiteam.qmui.layout.QMUILinearLayout r1 = r0.mActionContainer
            com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder$1 r2 = new com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder$1
            r2.<init>()
            r1.addOnLayoutChangeListener(r2)
        L_0x010e:
            com.qmuiteam.qmui.layout.QMUILinearLayout r1 = r0.mActionContainer
            r2 = r18
            r2.addView(r1)
        L_0x0115:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder.onCreateHandlerBar(com.qmuiteam.qmui.widget.dialog.QMUIDialog, android.view.ViewGroup, android.content.Context):void");
    }

    private View createActionContainerSpace(Context context) {
        Space space = new Space(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
        layoutParams.weight = 1.0f;
        space.setLayoutParams(layoutParams);
        return space;
    }

    /* access modifiers changed from: protected */
    public void onAfter(QMUIDialog qMUIDialog, LinearLayout linearLayout, Context context) {
        C16702 r1 = new View.OnClickListener() {
            public void onClick(View view) {
                QMUIDialogBuilder.this.mDialog.cancelOutSide();
            }
        };
        this.mAnchorBottomView.setOnClickListener(r1);
        this.mAnchorTopView.setOnClickListener(r1);
        this.mRootView.setOnClickListener(r1);
    }

    public View getAnchorTopView() {
        return this.mAnchorTopView;
    }

    public View getAnchorBottomView() {
        return this.mAnchorBottomView;
    }

    public List<QMUIDialogAction> getPositiveAction() {
        ArrayList arrayList = new ArrayList();
        for (QMUIDialogAction next : this.mActions) {
            if (next.getActionProp() == 0) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
