package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.p000v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import java.util.ArrayList;
import java.util.List;

public class QMUITopBar extends RelativeLayout {
    private static final int DEFAULT_VIEW_ID = -1;
    private View mCenterView;
    private int mLeftBackDrawableRes;
    private int mLeftLastViewId;
    private List<View> mLeftViewList;
    private int mRightLastViewId;
    private List<View> mRightViewList;
    private int mSubTitleTextColor;
    private int mSubTitleTextSize;
    private TextView mSubTitleView;
    private int mTitleContainerPaddingHor;
    private Rect mTitleContainerRect;
    private LinearLayout mTitleContainerView;
    private int mTitleGravity;
    private int mTitleMarginHorWhenNoBtnAside;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private int mTitleTextSizeWithSubTitle;
    private TextView mTitleView;
    private int mTopBarBgColor;
    private Drawable mTopBarBgWithSeparatorDrawableCache;
    private int mTopBarImageBtnHeight;
    private int mTopBarImageBtnWidth;
    private int mTopBarSeparatorColor;
    private int mTopBarSeparatorHeight;
    private int mTopBarTextBtnPaddingHor;
    private ColorStateList mTopBarTextBtnTextColor;
    private int mTopBarTextBtnTextSize;
    private int mTopbarHeight;

    public QMUITopBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTopbarHeight = -1;
        initVar();
        init(context, attributeSet, i);
    }

    public QMUITopBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUITopBarStyle);
    }

    public QMUITopBar(Context context) {
        this(context, (AttributeSet) null);
    }

    QMUITopBar(Context context, boolean z) {
        super(context);
        this.mTopbarHeight = -1;
        initVar();
        if (z) {
            int color = ContextCompat.getColor(context, C1614R.color.qmui_config_color_transparent);
            this.mTopBarSeparatorColor = color;
            this.mTopBarSeparatorHeight = 0;
            this.mTopBarBgColor = color;
            return;
        }
        init(context, (AttributeSet) null, C1614R.attr.QMUITopBarStyle);
    }

    private void initVar() {
        this.mLeftLastViewId = -1;
        this.mRightLastViewId = -1;
        this.mLeftViewList = new ArrayList();
        this.mRightViewList = new ArrayList();
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1614R.styleable.QMUITopBar, i, 0);
        this.mTopBarSeparatorColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUITopBar_qmui_topbar_separator_color, ContextCompat.getColor(context, C1614R.color.qmui_config_color_separator));
        this.mTopBarSeparatorHeight = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_separator_height, 1);
        this.mTopBarBgColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUITopBar_qmui_topbar_bg_color, -1);
        getCommonFieldFormTypedArray(context, obtainStyledAttributes);
        boolean z = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUITopBar_qmui_topbar_need_separator, true);
        obtainStyledAttributes.recycle();
        setBackgroundDividerEnabled(z);
    }

    /* access modifiers changed from: package-private */
    public void getCommonFieldFormTypedArray(Context context, TypedArray typedArray) {
        this.mLeftBackDrawableRes = typedArray.getResourceId(C1614R.styleable.QMUITopBar_qmui_topbar_left_back_drawable_id, C1614R.C1616id.qmui_topbar_item_left_back);
        this.mTitleGravity = typedArray.getInt(C1614R.styleable.QMUITopBar_qmui_topbar_title_gravity, 17);
        this.mTitleTextSize = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_title_text_size, QMUIDisplayHelper.sp2px(context, 17));
        this.mTitleTextSizeWithSubTitle = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_title_text_size, QMUIDisplayHelper.sp2px(context, 16));
        this.mSubTitleTextSize = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_subtitle_text_size, QMUIDisplayHelper.sp2px(context, 11));
        this.mTitleTextColor = typedArray.getColor(C1614R.styleable.QMUITopBar_qmui_topbar_title_color, QMUIResHelper.getAttrColor(context, C1614R.attr.qmui_config_color_gray_1));
        this.mSubTitleTextColor = typedArray.getColor(C1614R.styleable.QMUITopBar_qmui_topbar_subtitle_color, QMUIResHelper.getAttrColor(context, C1614R.attr.qmui_config_color_gray_4));
        this.mTitleMarginHorWhenNoBtnAside = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_title_margin_horizontal_when_no_btn_aside, 0);
        this.mTitleContainerPaddingHor = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_title_container_padding_horizontal, 0);
        this.mTopBarImageBtnWidth = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_image_btn_width, QMUIDisplayHelper.dp2px(context, 48));
        this.mTopBarImageBtnHeight = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_image_btn_height, QMUIDisplayHelper.dp2px(context, 48));
        this.mTopBarTextBtnPaddingHor = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_text_btn_padding_horizontal, QMUIDisplayHelper.dp2px(context, 12));
        this.mTopBarTextBtnTextColor = typedArray.getColorStateList(C1614R.styleable.QMUITopBar_qmui_topbar_text_btn_color_state_list);
        this.mTopBarTextBtnTextSize = typedArray.getDimensionPixelSize(C1614R.styleable.QMUITopBar_qmui_topbar_text_btn_text_size, QMUIDisplayHelper.sp2px(context, 16));
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        while (parent != null && (parent instanceof View)) {
            if (parent instanceof QMUICollapsingTopBarLayout) {
                makeSureTitleContainerView();
                return;
            }
            parent = parent.getParent();
        }
    }

    public void setCenterView(View view) {
        View view2 = this.mCenterView;
        if (view2 != view) {
            if (view2 != null) {
                removeView(view2);
            }
            this.mCenterView = view;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mCenterView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            }
            layoutParams.addRule(13);
            addView(view, layoutParams);
        }
    }

    public TextView setTitle(int i) {
        return setTitle(getContext().getString(i));
    }

    public TextView setTitle(String str) {
        TextView titleView = getTitleView(false);
        titleView.setText(str);
        if (QMUILangHelper.isNullOrEmpty(str)) {
            titleView.setVisibility(8);
        } else {
            titleView.setVisibility(0);
        }
        return titleView;
    }

    public CharSequence getTitle() {
        TextView textView = this.mTitleView;
        if (textView == null) {
            return null;
        }
        return textView.getText();
    }

    public TextView setEmojiTitle(String str) {
        TextView titleView = getTitleView(true);
        titleView.setText(str);
        if (QMUILangHelper.isNullOrEmpty(str)) {
            titleView.setVisibility(8);
        } else {
            titleView.setVisibility(0);
        }
        return titleView;
    }

    public void showTitleView(boolean z) {
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setVisibility(z ? 0 : 8);
        }
    }

    private TextView getTitleView(boolean z) {
        if (this.mTitleView == null) {
            this.mTitleView = new TextView(getContext());
            this.mTitleView.setGravity(17);
            this.mTitleView.setSingleLine(true);
            this.mTitleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            this.mTitleView.setTextColor(this.mTitleTextColor);
            updateTitleViewStyle();
            makeSureTitleContainerView().addView(this.mTitleView, generateTitleViewAndSubTitleViewLp());
        }
        return this.mTitleView;
    }

    private void updateTitleViewStyle() {
        if (this.mTitleView != null) {
            TextView textView = this.mSubTitleView;
            if (textView == null || QMUILangHelper.isNullOrEmpty(textView.getText())) {
                this.mTitleView.setTextSize(0, (float) this.mTitleTextSize);
            } else {
                this.mTitleView.setTextSize(0, (float) this.mTitleTextSizeWithSubTitle);
            }
        }
    }

    public void setSubTitle(String str) {
        TextView subTitleView = getSubTitleView();
        subTitleView.setText(str);
        if (QMUILangHelper.isNullOrEmpty(str)) {
            subTitleView.setVisibility(8);
        } else {
            subTitleView.setVisibility(0);
        }
        updateTitleViewStyle();
    }

    public void setSubTitle(int i) {
        setSubTitle(getResources().getString(i));
    }

    private TextView getSubTitleView() {
        if (this.mSubTitleView == null) {
            this.mSubTitleView = new TextView(getContext());
            this.mSubTitleView.setGravity(17);
            this.mSubTitleView.setSingleLine(true);
            this.mSubTitleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            this.mSubTitleView.setTextSize(0, (float) this.mSubTitleTextSize);
            this.mSubTitleView.setTextColor(this.mSubTitleTextColor);
            LinearLayout.LayoutParams generateTitleViewAndSubTitleViewLp = generateTitleViewAndSubTitleViewLp();
            generateTitleViewAndSubTitleViewLp.topMargin = QMUIDisplayHelper.dp2px(getContext(), 1);
            makeSureTitleContainerView().addView(this.mSubTitleView, generateTitleViewAndSubTitleViewLp);
        }
        return this.mSubTitleView;
    }

    public void setTitleGravity(int i) {
        this.mTitleGravity = i;
        TextView textView = this.mTitleView;
        if (textView != null) {
            ((LinearLayout.LayoutParams) textView.getLayoutParams()).gravity = i;
            if (i == 17 || i == 1) {
                this.mTitleView.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), getPaddingBottom());
            }
        }
        TextView textView2 = this.mSubTitleView;
        if (textView2 != null) {
            ((LinearLayout.LayoutParams) textView2.getLayoutParams()).gravity = i;
        }
        requestLayout();
    }

    public Rect getTitleContainerRect() {
        if (this.mTitleContainerRect == null) {
            this.mTitleContainerRect = new Rect();
        }
        LinearLayout linearLayout = this.mTitleContainerView;
        if (linearLayout == null) {
            this.mTitleContainerRect.set(0, 0, 0, 0);
        } else {
            QMUIViewHelper.getDescendantRect(this, linearLayout, this.mTitleContainerRect);
        }
        return this.mTitleContainerRect;
    }

    private LinearLayout makeSureTitleContainerView() {
        if (this.mTitleContainerView == null) {
            this.mTitleContainerView = new LinearLayout(getContext());
            this.mTitleContainerView.setOrientation(1);
            this.mTitleContainerView.setGravity(17);
            LinearLayout linearLayout = this.mTitleContainerView;
            int i = this.mTitleContainerPaddingHor;
            linearLayout.setPadding(i, 0, i, 0);
            addView(this.mTitleContainerView, generateTitleContainerViewLp());
        }
        return this.mTitleContainerView;
    }

    private RelativeLayout.LayoutParams generateTitleContainerViewLp() {
        return new RelativeLayout.LayoutParams(-1, QMUIResHelper.getAttrDimen(getContext(), C1614R.attr.qmui_topbar_height));
    }

    private LinearLayout.LayoutParams generateTitleViewAndSubTitleViewLp() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = this.mTitleGravity;
        return layoutParams;
    }

    public void addLeftView(View view, int i) {
        RelativeLayout.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 == null || !(layoutParams2 instanceof RelativeLayout.LayoutParams)) {
            layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        } else {
            layoutParams = (RelativeLayout.LayoutParams) layoutParams2;
        }
        addLeftView(view, i, layoutParams);
    }

    public void addLeftView(View view, int i, RelativeLayout.LayoutParams layoutParams) {
        int i2 = this.mLeftLastViewId;
        if (i2 == -1) {
            layoutParams.addRule(9);
        } else {
            layoutParams.addRule(1, i2);
        }
        layoutParams.alignWithParent = true;
        this.mLeftLastViewId = i;
        view.setId(i);
        this.mLeftViewList.add(view);
        addView(view, layoutParams);
    }

    public void addRightView(View view, int i) {
        RelativeLayout.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
        if (layoutParams2 == null || !(layoutParams2 instanceof RelativeLayout.LayoutParams)) {
            layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        } else {
            layoutParams = (RelativeLayout.LayoutParams) layoutParams2;
        }
        addRightView(view, i, layoutParams);
    }

    public void addRightView(View view, int i, RelativeLayout.LayoutParams layoutParams) {
        int i2 = this.mRightLastViewId;
        if (i2 == -1) {
            layoutParams.addRule(11);
        } else {
            layoutParams.addRule(0, i2);
        }
        layoutParams.alignWithParent = true;
        this.mRightLastViewId = i;
        view.setId(i);
        this.mRightViewList.add(view);
        addView(view, layoutParams);
    }

    public RelativeLayout.LayoutParams generateTopBarImageButtonLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.mTopBarImageBtnWidth, this.mTopBarImageBtnHeight);
        layoutParams.topMargin = Math.max(0, (getTopBarHeight() - this.mTopBarImageBtnHeight) / 2);
        return layoutParams;
    }

    public QMUIAlphaImageButton addRightImageButton(int i, int i2) {
        QMUIAlphaImageButton generateTopBarImageButton = generateTopBarImageButton(i);
        addRightView(generateTopBarImageButton, i2, generateTopBarImageButtonLayoutParams());
        return generateTopBarImageButton;
    }

    public QMUIAlphaImageButton addLeftImageButton(int i, int i2) {
        QMUIAlphaImageButton generateTopBarImageButton = generateTopBarImageButton(i);
        addLeftView(generateTopBarImageButton, i2, generateTopBarImageButtonLayoutParams());
        return generateTopBarImageButton;
    }

    public RelativeLayout.LayoutParams generateTopBarTextButtonLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, this.mTopBarImageBtnHeight);
        layoutParams.topMargin = Math.max(0, (getTopBarHeight() - this.mTopBarImageBtnHeight) / 2);
        return layoutParams;
    }

    public Button addLeftTextButton(int i, int i2) {
        return addLeftTextButton(getResources().getString(i), i2);
    }

    public Button addLeftTextButton(String str, int i) {
        Button generateTopBarTextButton = generateTopBarTextButton(str);
        addLeftView(generateTopBarTextButton, i, generateTopBarTextButtonLayoutParams());
        return generateTopBarTextButton;
    }

    public Button addRightTextButton(int i, int i2) {
        return addRightTextButton(getResources().getString(i), i2);
    }

    public Button addRightTextButton(String str, int i) {
        Button generateTopBarTextButton = generateTopBarTextButton(str);
        addRightView(generateTopBarTextButton, i, generateTopBarTextButtonLayoutParams());
        return generateTopBarTextButton;
    }

    private Button generateTopBarTextButton(String str) {
        Button button = new Button(getContext());
        button.setBackgroundResource(0);
        button.setMinWidth(0);
        button.setMinHeight(0);
        button.setMinimumWidth(0);
        button.setMinimumHeight(0);
        int i = this.mTopBarTextBtnPaddingHor;
        button.setPadding(i, 0, i, 0);
        button.setTextColor(this.mTopBarTextBtnTextColor);
        button.setTextSize(0, (float) this.mTopBarTextBtnTextSize);
        button.setGravity(17);
        button.setText(str);
        return button;
    }

    private QMUIAlphaImageButton generateTopBarImageButton(int i) {
        QMUIAlphaImageButton qMUIAlphaImageButton = new QMUIAlphaImageButton(getContext());
        qMUIAlphaImageButton.setBackgroundColor(0);
        qMUIAlphaImageButton.setImageResource(i);
        return qMUIAlphaImageButton;
    }

    public QMUIAlphaImageButton addLeftBackImageButton() {
        return addLeftImageButton(this.mLeftBackDrawableRes, C1614R.C1616id.qmui_topbar_item_left_back);
    }

    public void removeAllLeftViews() {
        for (View removeView : this.mLeftViewList) {
            removeView(removeView);
        }
        this.mLeftLastViewId = -1;
        this.mLeftViewList.clear();
    }

    public void removeAllRightViews() {
        for (View removeView : this.mRightViewList) {
            removeView(removeView);
        }
        this.mRightLastViewId = -1;
        this.mRightViewList.clear();
    }

    public void removeCenterViewAndTitleView() {
        View view = this.mCenterView;
        if (view != null) {
            if (view.getParent() == this) {
                removeView(this.mCenterView);
            }
            this.mCenterView = null;
        }
        TextView textView = this.mTitleView;
        if (textView != null) {
            if (textView.getParent() == this) {
                removeView(this.mTitleView);
            }
            this.mTitleView = null;
        }
    }

    private int getTopBarHeight() {
        if (this.mTopbarHeight == -1) {
            this.mTopbarHeight = QMUIResHelper.getAttrDimen(getContext(), C1614R.attr.qmui_topbar_height);
        }
        return this.mTopbarHeight;
    }

    public void setBackgroundAlpha(int i) {
        getBackground().setAlpha(i);
    }

    public int computeAndSetBackgroundAlpha(int i, int i2, int i3) {
        int max = (int) (Math.max(0.0d, Math.min((double) (((float) (i - i2)) / ((float) (i3 - i2))), 1.0d)) * 255.0d);
        setBackgroundAlpha(max);
        return max;
    }

    public void setBackgroundDividerEnabled(boolean z) {
        if (z) {
            if (this.mTopBarBgWithSeparatorDrawableCache == null) {
                this.mTopBarBgWithSeparatorDrawableCache = QMUIDrawableHelper.createItemSeparatorBg(this.mTopBarSeparatorColor, this.mTopBarBgColor, this.mTopBarSeparatorHeight, false);
            }
            QMUIViewHelper.setBackgroundKeepingPadding((View) this, this.mTopBarBgWithSeparatorDrawableCache);
            return;
        }
        QMUIViewHelper.setBackgroundColorKeepPadding(this, this.mTopBarBgColor);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size;
        int paddingRight;
        super.onMeasure(i, i2);
        if (this.mTitleContainerView != null) {
            int i3 = 0;
            for (int i4 = 0; i4 < this.mLeftViewList.size(); i4++) {
                View view = this.mLeftViewList.get(i4);
                if (view.getVisibility() != 8) {
                    i3 += view.getMeasuredWidth();
                }
            }
            int i5 = 0;
            for (int i6 = 0; i6 < this.mRightViewList.size(); i6++) {
                View view2 = this.mRightViewList.get(i6);
                if (view2.getVisibility() != 8) {
                    i5 += view2.getMeasuredWidth();
                }
            }
            if ((this.mTitleGravity & 7) == 1) {
                if (i3 == 0 && i5 == 0) {
                    int i7 = this.mTitleMarginHorWhenNoBtnAside;
                    i3 += i7;
                    i5 += i7;
                }
                size = (View.MeasureSpec.getSize(i) - (Math.max(i3, i5) * 2)) - getPaddingLeft();
                paddingRight = getPaddingRight();
            } else {
                if (i3 == 0) {
                    i3 += this.mTitleMarginHorWhenNoBtnAside;
                }
                if (i5 == 0) {
                    i5 += this.mTitleMarginHorWhenNoBtnAside;
                }
                size = ((View.MeasureSpec.getSize(i) - i3) - i5) - getPaddingLeft();
                paddingRight = getPaddingRight();
            }
            this.mTitleContainerView.measure(View.MeasureSpec.makeMeasureSpec(size - paddingRight, 1073741824), i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        super.onLayout(z, i, i2, i3, i4);
        LinearLayout linearLayout = this.mTitleContainerView;
        if (linearLayout != null) {
            int measuredWidth = linearLayout.getMeasuredWidth();
            int measuredHeight = this.mTitleContainerView.getMeasuredHeight();
            int measuredHeight2 = ((i4 - i2) - this.mTitleContainerView.getMeasuredHeight()) / 2;
            int paddingLeft = getPaddingLeft();
            if ((this.mTitleGravity & 7) == 1) {
                i5 = ((i3 - i) - this.mTitleContainerView.getMeasuredWidth()) / 2;
            } else {
                i5 = paddingLeft;
                for (int i6 = 0; i6 < this.mLeftViewList.size(); i6++) {
                    View view = this.mLeftViewList.get(i6);
                    if (view.getVisibility() != 8) {
                        i5 += view.getMeasuredWidth();
                    }
                }
                if (this.mLeftViewList.isEmpty()) {
                    i5 += QMUIResHelper.getAttrDimen(getContext(), C1614R.attr.qmui_topbar_title_margin_horizontal_when_no_btn_aside);
                }
            }
            this.mTitleContainerView.layout(i5, measuredHeight2, measuredWidth + i5, measuredHeight + measuredHeight2);
        }
    }
}
