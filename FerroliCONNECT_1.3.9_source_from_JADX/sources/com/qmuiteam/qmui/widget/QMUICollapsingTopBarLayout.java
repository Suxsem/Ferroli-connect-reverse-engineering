package com.qmuiteam.qmui.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.design.widget.AppBarLayout;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.OnApplyWindowInsetsListener;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.QMUIInterpolatorStaticHolder;
import com.qmuiteam.qmui.util.QMUICollapsingTextHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.util.QMUIViewOffsetHelper;

public class QMUICollapsingTopBarLayout extends FrameLayout implements IWindowInsetLayout {
    private static final int DEFAULT_SCRIM_ANIMATION_DURATION = 600;
    final QMUICollapsingTextHelper mCollapsingTextHelper;
    private boolean mCollapsingTitleEnabled;
    private Drawable mContentScrim;
    int mCurrentOffset;
    private int mExpandedMarginBottom;
    private int mExpandedMarginEnd;
    private int mExpandedMarginStart;
    private int mExpandedMarginTop;
    Rect mLastInsetRect;
    WindowInsetsCompat mLastInsets;
    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private boolean mRefreshToolbar;
    private int mScrimAlpha;
    private long mScrimAnimationDuration;
    private ValueAnimator mScrimAnimator;
    private ValueAnimator.AnimatorUpdateListener mScrimUpdateListener;
    private int mScrimVisibleHeightTrigger;
    private boolean mScrimsAreShown;
    Drawable mStatusBarScrim;
    private final Rect mTmpRect;
    private QMUITopBar mTopBar;
    private View mTopBarDirectChild;
    private int mTopBarId;

    public QMUICollapsingTopBarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUICollapsingTopBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMUICollapsingTopBarLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRefreshToolbar = true;
        this.mTmpRect = new Rect();
        this.mScrimVisibleHeightTrigger = -1;
        this.mCollapsingTextHelper = new QMUICollapsingTextHelper(this);
        this.mCollapsingTextHelper.setTextSizeInterpolator(QMUIInterpolatorStaticHolder.DECELERATE_INTERPOLATOR);
        QMUIViewHelper.checkAppCompatTheme(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUICollapsingTopBarLayout, i, 0);
        this.mCollapsingTextHelper.setExpandedTextGravity(obtainStyledAttributes.getInt(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleGravity, 81));
        this.mCollapsingTextHelper.setCollapsedTextGravity(obtainStyledAttributes.getInt(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_collapsedTitleGravity, 8388627));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMargin, 0);
        this.mExpandedMarginBottom = dimensionPixelSize;
        this.mExpandedMarginEnd = dimensionPixelSize;
        this.mExpandedMarginTop = dimensionPixelSize;
        this.mExpandedMarginStart = dimensionPixelSize;
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginStart)) {
            this.mExpandedMarginStart = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginStart, 0);
        }
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginEnd)) {
            this.mExpandedMarginEnd = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginEnd, 0);
        }
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginTop)) {
            this.mExpandedMarginTop = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginTop, 0);
        }
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginBottom)) {
            this.mExpandedMarginBottom = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleMarginBottom, 0);
        }
        this.mCollapsingTitleEnabled = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_titleEnabled, true);
        setTitle(obtainStyledAttributes.getText(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_title));
        this.mCollapsingTextHelper.setExpandedTextAppearance(C1614R.style.QMUI_CollapsingTopBarLayoutExpanded);
        this.mCollapsingTextHelper.setCollapsedTextAppearance(C1614R.style.QMUI_CollapsingTopBarLayoutCollapsed);
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setExpandedTextAppearance(obtainStyledAttributes.getResourceId(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_expandedTitleTextAppearance, 0));
        }
        if (obtainStyledAttributes.hasValue(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_collapsedTitleTextAppearance)) {
            this.mCollapsingTextHelper.setCollapsedTextAppearance(obtainStyledAttributes.getResourceId(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_collapsedTitleTextAppearance, 0));
        }
        this.mScrimVisibleHeightTrigger = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_scrimVisibleHeightTrigger, -1);
        this.mScrimAnimationDuration = (long) obtainStyledAttributes.getInt(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_scrimAnimationDuration, DEFAULT_SCRIM_ANIMATION_DURATION);
        setContentScrim(obtainStyledAttributes.getDrawable(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_contentScrim));
        setStatusBarScrim(obtainStyledAttributes.getDrawable(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_statusBarScrim));
        this.mTopBarId = obtainStyledAttributes.getResourceId(C1614R.styleable.QMUICollapsingTopBarLayout_qmui_topBarId, -1);
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return QMUICollapsingTopBarLayout.this.setWindowInsets(windowInsetsCompat);
            }
        });
    }

    /* access modifiers changed from: private */
    public WindowInsetsCompat setWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        return (Build.VERSION.SDK_INT < 21 || !applySystemWindowInsets21(windowInsetsCompat)) ? windowInsetsCompat : windowInsetsCompat.consumeSystemWindowInsets();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            ViewCompat.setFitsSystemWindows(this, ViewCompat.getFitsSystemWindows((View) parent));
            if (this.mOnOffsetChangedListener == null) {
                this.mOnOffsetChangedListener = new OffsetUpdateListener();
            }
            ((AppBarLayout) parent).addOnOffsetChangedListener(this.mOnOffsetChangedListener);
            ViewCompat.requestApplyInsets(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        ViewParent parent = getParent();
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = this.mOnOffsetChangedListener;
        if (onOffsetChangedListener != null && (parent instanceof AppBarLayout)) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener);
        }
        super.onDetachedFromWindow();
    }

    public void draw(Canvas canvas) {
        int windowInsetTop;
        Drawable drawable;
        super.draw(canvas);
        ensureToolbar();
        if (this.mTopBar == null && (drawable = this.mContentScrim) != null && this.mScrimAlpha > 0) {
            drawable.mutate().setAlpha(this.mScrimAlpha);
            this.mContentScrim.draw(canvas);
        }
        if (this.mCollapsingTitleEnabled) {
            this.mCollapsingTextHelper.draw(canvas);
        }
        if (this.mStatusBarScrim != null && this.mScrimAlpha > 0 && (windowInsetTop = getWindowInsetTop()) > 0) {
            this.mStatusBarScrim.setBounds(0, -this.mCurrentOffset, getWidth(), windowInsetTop - this.mCurrentOffset);
            this.mStatusBarScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mStatusBarScrim.draw(canvas);
        }
    }

    /* access modifiers changed from: private */
    public int getWindowInsetTop() {
        WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        Rect rect = this.mLastInsetRect;
        if (rect != null) {
            return rect.top;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        if (this.mContentScrim == null || this.mScrimAlpha <= 0 || !isToolbarChild(view)) {
            z = false;
        } else {
            this.mContentScrim.mutate().setAlpha(this.mScrimAlpha);
            this.mContentScrim.draw(canvas);
            z = true;
        }
        if (super.drawChild(canvas, view, j) || z) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Drawable drawable = this.mContentScrim;
        if (drawable != null) {
            drawable.setBounds(0, 0, i, i2);
        }
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void ensureToolbar() {
        /*
            r6 = this;
            boolean r0 = r6.mRefreshToolbar
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r6.mTopBar = r0
            r6.mTopBarDirectChild = r0
            int r1 = r6.mTopBarId
            r2 = -1
            if (r1 == r2) goto L_0x0021
            android.view.View r1 = r6.findViewById(r1)
            com.qmuiteam.qmui.widget.QMUITopBar r1 = (com.qmuiteam.qmui.widget.QMUITopBar) r1
            r6.mTopBar = r1
            com.qmuiteam.qmui.widget.QMUITopBar r1 = r6.mTopBar
            if (r1 == 0) goto L_0x0021
            android.view.View r1 = r6.findDirectChild(r1)
            r6.mTopBarDirectChild = r1
        L_0x0021:
            com.qmuiteam.qmui.widget.QMUITopBar r1 = r6.mTopBar
            r2 = 0
            if (r1 != 0) goto L_0x003e
            int r1 = r6.getChildCount()
            r3 = 0
        L_0x002b:
            if (r3 >= r1) goto L_0x003c
            android.view.View r4 = r6.getChildAt(r3)
            boolean r5 = r4 instanceof com.qmuiteam.qmui.widget.QMUITopBar
            if (r5 == 0) goto L_0x0039
            r0 = r4
            com.qmuiteam.qmui.widget.QMUITopBar r0 = (com.qmuiteam.qmui.widget.QMUITopBar) r0
            goto L_0x003c
        L_0x0039:
            int r3 = r3 + 1
            goto L_0x002b
        L_0x003c:
            r6.mTopBar = r0
        L_0x003e:
            r6.mRefreshToolbar = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout.ensureToolbar():void");
    }

    private boolean isToolbarChild(View view) {
        View view2 = this.mTopBarDirectChild;
        if (view2 == null || view2 == this) {
            if (view == this.mTopBar) {
                return true;
            }
        } else if (view == view2) {
            return true;
        }
        return false;
    }

    private View findDirectChild(View view) {
        ViewParent parent = view.getParent();
        while (parent != this && parent != null) {
            if (parent instanceof View) {
                view = (View) parent;
            }
            parent = parent.getParent();
        }
        return view;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        ensureToolbar();
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (!(this.mLastInsets == null && this.mLastInsetRect == null)) {
            int windowInsetTop = getWindowInsetTop();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                if (ViewCompat.getFitsSystemWindows(childAt) && childAt.getTop() < windowInsetTop) {
                    ViewCompat.offsetTopAndBottom(childAt, windowInsetTop);
                }
            }
        }
        int childCount2 = getChildCount();
        for (int i6 = 0; i6 < childCount2; i6++) {
            getViewOffsetHelper(getChildAt(i6)).onViewLayout();
        }
        if (this.mCollapsingTitleEnabled) {
            View view = this.mTopBarDirectChild;
            if (view == null) {
                view = this.mTopBar;
            }
            int maxOffsetForPinChild = getMaxOffsetForPinChild(view);
            QMUIViewHelper.getDescendantRect(this, this.mTopBar, this.mTmpRect);
            Rect titleContainerRect = this.mTopBar.getTitleContainerRect();
            int i7 = this.mTmpRect.top + maxOffsetForPinChild;
            this.mCollapsingTextHelper.setCollapsedBounds(this.mTmpRect.left + titleContainerRect.left, titleContainerRect.top + i7, this.mTmpRect.left + titleContainerRect.right, i7 + titleContainerRect.bottom);
            this.mCollapsingTextHelper.setExpandedBounds(this.mExpandedMarginStart, this.mTmpRect.top + this.mExpandedMarginTop, (i3 - i) - this.mExpandedMarginEnd, (i4 - i2) - this.mExpandedMarginBottom);
            this.mCollapsingTextHelper.recalculate();
        }
        if (this.mTopBar != null) {
            if (this.mCollapsingTitleEnabled && TextUtils.isEmpty(this.mCollapsingTextHelper.getText())) {
                this.mCollapsingTextHelper.setText(this.mTopBar.getTitle());
            }
            View view2 = this.mTopBarDirectChild;
            if (view2 == null || view2 == this) {
                setMinimumHeight(getHeightWithMargins(this.mTopBar));
            } else {
                setMinimumHeight(getHeightWithMargins(view2));
            }
        }
        updateScrimVisibility();
    }

    private static int getHeightWithMargins(@NonNull View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return view.getHeight();
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return view.getHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    static QMUIViewOffsetHelper getViewOffsetHelper(View view) {
        QMUIViewOffsetHelper qMUIViewOffsetHelper = (QMUIViewOffsetHelper) view.getTag(C1614R.C1616id.qmui_view_offset_helper);
        if (qMUIViewOffsetHelper != null) {
            return qMUIViewOffsetHelper;
        }
        QMUIViewOffsetHelper qMUIViewOffsetHelper2 = new QMUIViewOffsetHelper(view);
        view.setTag(C1614R.C1616id.qmui_view_offset_helper, qMUIViewOffsetHelper2);
        return qMUIViewOffsetHelper2;
    }

    public void setTitle(@Nullable CharSequence charSequence) {
        this.mCollapsingTextHelper.setText(charSequence);
    }

    @Nullable
    public CharSequence getTitle() {
        if (this.mCollapsingTitleEnabled) {
            return this.mCollapsingTextHelper.getText();
        }
        return null;
    }

    public void setTitleEnabled(boolean z) {
        if (z != this.mCollapsingTitleEnabled) {
            this.mCollapsingTitleEnabled = z;
            requestLayout();
        }
    }

    public boolean isTitleEnabled() {
        return this.mCollapsingTitleEnabled;
    }

    public void setScrimsShown(boolean z) {
        setScrimsShown(z, ViewCompat.isLaidOut(this) && !isInEditMode());
    }

    public void setScrimsShown(boolean z, boolean z2) {
        if (this.mScrimsAreShown != z) {
            int i = 255;
            if (z2) {
                if (!z) {
                    i = 0;
                }
                animateScrim(i);
            } else {
                if (!z) {
                    i = 0;
                }
                setScrimAlpha(i);
            }
            this.mScrimsAreShown = z;
        }
    }

    public void setScrimUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener2 = this.mScrimUpdateListener;
        if (animatorUpdateListener2 != animatorUpdateListener) {
            ValueAnimator valueAnimator = this.mScrimAnimator;
            if (valueAnimator == null) {
                this.mScrimUpdateListener = animatorUpdateListener;
                return;
            }
            if (animatorUpdateListener2 != null) {
                valueAnimator.removeUpdateListener(animatorUpdateListener2);
            }
            this.mScrimUpdateListener = animatorUpdateListener;
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener3 = this.mScrimUpdateListener;
            if (animatorUpdateListener3 != null) {
                this.mScrimAnimator.addUpdateListener(animatorUpdateListener3);
            }
        }
    }

    private void animateScrim(int i) {
        ensureToolbar();
        ValueAnimator valueAnimator = this.mScrimAnimator;
        if (valueAnimator == null) {
            this.mScrimAnimator = new ValueAnimator();
            this.mScrimAnimator.setDuration(this.mScrimAnimationDuration);
            this.mScrimAnimator.setInterpolator(i > this.mScrimAlpha ? QMUIInterpolatorStaticHolder.FAST_OUT_LINEAR_IN_INTERPOLATOR : QMUIInterpolatorStaticHolder.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
            this.mScrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    QMUICollapsingTopBarLayout.this.setScrimAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = this.mScrimUpdateListener;
            if (animatorUpdateListener != null) {
                this.mScrimAnimator.addUpdateListener(animatorUpdateListener);
            }
        } else if (valueAnimator.isRunning()) {
            this.mScrimAnimator.cancel();
        }
        this.mScrimAnimator.setIntValues(new int[]{this.mScrimAlpha, i});
        this.mScrimAnimator.start();
    }

    /* access modifiers changed from: package-private */
    public void setScrimAlpha(int i) {
        QMUITopBar qMUITopBar;
        if (i != this.mScrimAlpha) {
            if (!(this.mContentScrim == null || (qMUITopBar = this.mTopBar) == null)) {
                ViewCompat.postInvalidateOnAnimation(qMUITopBar);
            }
            this.mScrimAlpha = i;
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: package-private */
    public int getScrimAlpha() {
        return this.mScrimAlpha;
    }

    public void setContentScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = this.mContentScrim;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback((Drawable.Callback) null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.mContentScrim = drawable3;
            Drawable drawable4 = this.mContentScrim;
            if (drawable4 != null) {
                drawable4.setBounds(0, 0, getWidth(), getHeight());
                this.mContentScrim.setCallback(this);
                this.mContentScrim.setAlpha(this.mScrimAlpha);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setContentScrimColor(@ColorInt int i) {
        setContentScrim(new ColorDrawable(i));
    }

    public void setContentScrimResource(@DrawableRes int i) {
        setContentScrim(ContextCompat.getDrawable(getContext(), i));
    }

    @Nullable
    public Drawable getContentScrim() {
        return this.mContentScrim;
    }

    public void setStatusBarScrim(@Nullable Drawable drawable) {
        Drawable drawable2 = this.mStatusBarScrim;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback((Drawable.Callback) null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.mStatusBarScrim = drawable3;
            Drawable drawable4 = this.mStatusBarScrim;
            if (drawable4 != null) {
                if (drawable4.isStateful()) {
                    this.mStatusBarScrim.setState(getDrawableState());
                }
                DrawableCompat.setLayoutDirection(this.mStatusBarScrim, ViewCompat.getLayoutDirection(this));
                this.mStatusBarScrim.setVisible(getVisibility() == 0, false);
                this.mStatusBarScrim.setCallback(this);
                this.mStatusBarScrim.setAlpha(this.mScrimAlpha);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mStatusBarScrim;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.mContentScrim;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        QMUICollapsingTextHelper qMUICollapsingTextHelper = this.mCollapsingTextHelper;
        if (qMUICollapsingTextHelper != null) {
            z |= qMUICollapsingTextHelper.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(@NonNull Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mContentScrim || drawable == this.mStatusBarScrim;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.mStatusBarScrim;
        if (!(drawable == null || drawable.isVisible() == z)) {
            this.mStatusBarScrim.setVisible(z, false);
        }
        Drawable drawable2 = this.mContentScrim;
        if (drawable2 != null && drawable2.isVisible() != z) {
            this.mContentScrim.setVisible(z, false);
        }
    }

    public void setStatusBarScrimColor(@ColorInt int i) {
        setStatusBarScrim(new ColorDrawable(i));
    }

    public void setStatusBarScrimResource(@DrawableRes int i) {
        setStatusBarScrim(ContextCompat.getDrawable(getContext(), i));
    }

    @Nullable
    public Drawable getStatusBarScrim() {
        return this.mStatusBarScrim;
    }

    public void setCollapsedTitleTextAppearance(@StyleRes int i) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(i);
    }

    public void setCollapsedTitleTextColor(@ColorInt int i) {
        setCollapsedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setCollapsedTitleTextColor(@NonNull ColorStateList colorStateList) {
        this.mCollapsingTextHelper.setCollapsedTextColor(colorStateList);
    }

    public void setCollapsedTitleGravity(int i) {
        this.mCollapsingTextHelper.setCollapsedTextGravity(i);
    }

    public int getCollapsedTitleGravity() {
        return this.mCollapsingTextHelper.getCollapsedTextGravity();
    }

    public void setExpandedTitleTextAppearance(@StyleRes int i) {
        this.mCollapsingTextHelper.setExpandedTextAppearance(i);
    }

    public void setExpandedTitleColor(@ColorInt int i) {
        setExpandedTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setExpandedTitleTextColor(@NonNull ColorStateList colorStateList) {
        this.mCollapsingTextHelper.setExpandedTextColor(colorStateList);
    }

    public void setExpandedTitleGravity(int i) {
        this.mCollapsingTextHelper.setExpandedTextGravity(i);
    }

    public int getExpandedTitleGravity() {
        return this.mCollapsingTextHelper.getExpandedTextGravity();
    }

    public void setCollapsedTitleTypeface(@Nullable Typeface typeface) {
        this.mCollapsingTextHelper.setCollapsedTypeface(typeface);
    }

    @NonNull
    public Typeface getCollapsedTitleTypeface() {
        return this.mCollapsingTextHelper.getCollapsedTypeface();
    }

    public void setExpandedTitleTypeface(@Nullable Typeface typeface) {
        this.mCollapsingTextHelper.setExpandedTypeface(typeface);
    }

    @NonNull
    public Typeface getExpandedTitleTypeface() {
        return this.mCollapsingTextHelper.getExpandedTypeface();
    }

    public void setExpandedTitleMargin(int i, int i2, int i3, int i4) {
        this.mExpandedMarginStart = i;
        this.mExpandedMarginTop = i2;
        this.mExpandedMarginEnd = i3;
        this.mExpandedMarginBottom = i4;
        requestLayout();
    }

    public int getExpandedTitleMarginStart() {
        return this.mExpandedMarginStart;
    }

    public void setExpandedTitleMarginStart(int i) {
        this.mExpandedMarginStart = i;
        requestLayout();
    }

    public int getExpandedTitleMarginTop() {
        return this.mExpandedMarginTop;
    }

    public void setExpandedTitleMarginTop(int i) {
        this.mExpandedMarginTop = i;
        requestLayout();
    }

    public int getExpandedTitleMarginEnd() {
        return this.mExpandedMarginEnd;
    }

    public void setExpandedTitleMarginEnd(int i) {
        this.mExpandedMarginEnd = i;
        requestLayout();
    }

    public int getExpandedTitleMarginBottom() {
        return this.mExpandedMarginBottom;
    }

    public void setExpandedTitleMarginBottom(int i) {
        this.mExpandedMarginBottom = i;
        requestLayout();
    }

    public void setScrimVisibleHeightTrigger(@IntRange(from = 0) int i) {
        if (this.mScrimVisibleHeightTrigger != i) {
            this.mScrimVisibleHeightTrigger = i;
            updateScrimVisibility();
        }
    }

    public int getScrimVisibleHeightTrigger() {
        int i = this.mScrimVisibleHeightTrigger;
        if (i >= 0) {
            return i;
        }
        int windowInsetTop = getWindowInsetTop();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight > 0) {
            return Math.min((minimumHeight * 2) + windowInsetTop, getHeight());
        }
        return getHeight() / 3;
    }

    public void setScrimAnimationDuration(@IntRange(from = 0) long j) {
        this.mScrimAnimationDuration = j;
    }

    public long getScrimAnimationDuration() {
        return this.mScrimAnimationDuration;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        return applySystemWindowInsets19(rect);
    }

    public boolean applySystemWindowInsets19(Rect rect) {
        if (!ViewCompat.getFitsSystemWindows(this)) {
            rect = null;
        }
        if (QMUILangHelper.objectEquals(this.mLastInsets, rect)) {
            return true;
        }
        this.mLastInsetRect = rect;
        requestLayout();
        return true;
    }

    public boolean applySystemWindowInsets21(WindowInsetsCompat windowInsetsCompat) {
        if (!ViewCompat.getFitsSystemWindows(this)) {
            windowInsetsCompat = null;
        }
        if (QMUILangHelper.objectEquals(this.mLastInsets, windowInsetsCompat)) {
            return true;
        }
        this.mLastInsets = windowInsetsCompat;
        requestLayout();
        return true;
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public static final int COLLAPSE_MODE_OFF = 0;
        public static final int COLLAPSE_MODE_PARALLAX = 2;
        public static final int COLLAPSE_MODE_PIN = 1;
        private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5f;
        int mCollapseMode = 0;
        float mParallaxMult = DEFAULT_PARALLAX_MULTIPLIER;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUICollapsingTopBarLayout_Layout);
            this.mCollapseMode = obtainStyledAttributes.getInt(C1614R.styleable.QMUICollapsingTopBarLayout_Layout_qmui_layout_collapseMode, 0);
            setParallaxMultiplier(obtainStyledAttributes.getFloat(C1614R.styleable.f3094x69da7123, DEFAULT_PARALLAX_MULTIPLIER));
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2, i3);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @TargetApi(19)
        @RequiresApi(19)
        public LayoutParams(FrameLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public void setCollapseMode(int i) {
            this.mCollapseMode = i;
        }

        public int getCollapseMode() {
            return this.mCollapseMode;
        }

        public void setParallaxMultiplier(float f) {
            this.mParallaxMult = f;
        }

        public float getParallaxMultiplier() {
            return this.mParallaxMult;
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateScrimVisibility() {
        if (this.mContentScrim != null || this.mStatusBarScrim != null) {
            setScrimsShown(getHeight() + this.mCurrentOffset < getScrimVisibleHeightTrigger());
        }
    }

    /* access modifiers changed from: package-private */
    public final int getMaxOffsetForPinChild(View view) {
        return ((getHeight() - getViewOffsetHelper(view).getLayoutTop()) - view.getHeight()) - ((LayoutParams) view.getLayoutParams()).bottomMargin;
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        OffsetUpdateListener() {
        }

        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            QMUICollapsingTopBarLayout qMUICollapsingTopBarLayout = QMUICollapsingTopBarLayout.this;
            qMUICollapsingTopBarLayout.mCurrentOffset = i;
            int access$100 = qMUICollapsingTopBarLayout.getWindowInsetTop();
            int childCount = QMUICollapsingTopBarLayout.this.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = QMUICollapsingTopBarLayout.this.getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                QMUIViewOffsetHelper viewOffsetHelper = QMUICollapsingTopBarLayout.getViewOffsetHelper(childAt);
                int i3 = layoutParams.mCollapseMode;
                if (i3 == 1) {
                    viewOffsetHelper.setTopAndBottomOffset(QMUILangHelper.constrain(-i, 0, QMUICollapsingTopBarLayout.this.getMaxOffsetForPinChild(childAt)));
                } else if (i3 == 2) {
                    viewOffsetHelper.setTopAndBottomOffset(Math.round(((float) (-i)) * layoutParams.mParallaxMult));
                }
            }
            QMUICollapsingTopBarLayout.this.updateScrimVisibility();
            if (QMUICollapsingTopBarLayout.this.mStatusBarScrim != null && access$100 > 0) {
                ViewCompat.postInvalidateOnAnimation(QMUICollapsingTopBarLayout.this);
            }
            QMUICollapsingTopBarLayout.this.mCollapsingTextHelper.setExpansionFraction(((float) Math.abs(i)) / ((float) ((QMUICollapsingTopBarLayout.this.getHeight() - ViewCompat.getMinimumHeight(QMUICollapsingTopBarLayout.this)) - access$100)));
        }
    }
}
