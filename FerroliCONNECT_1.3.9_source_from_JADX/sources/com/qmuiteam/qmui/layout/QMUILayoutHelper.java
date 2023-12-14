package com.qmuiteam.qmui.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.p000v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIResHelper;
import java.lang.ref.WeakReference;

public class QMUILayoutHelper implements IQMUILayout {
    private int mBorderColor = 0;
    private RectF mBorderRect;
    private int mBorderWidth = 1;
    private int mBottomDividerAlpha = 255;
    private int mBottomDividerColor;
    private int mBottomDividerHeight = 0;
    private int mBottomDividerInsetLeft = 0;
    private int mBottomDividerInsetRight = 0;
    private Paint mClipPaint;
    private Context mContext;
    private Paint mDividerPaint;
    private int mHeightLimit = 0;
    private int mHeightMini = 0;
    /* access modifiers changed from: private */
    public int mHideRadiusSide = 0;
    /* access modifiers changed from: private */
    public boolean mIsOutlineExcludePadding = false;
    private boolean mIsShowBorderOnlyBeforeL = true;
    private int mLeftDividerAlpha = 255;
    private int mLeftDividerColor;
    private int mLeftDividerInsetBottom = 0;
    private int mLeftDividerInsetTop = 0;
    private int mLeftDividerWidth = 0;
    private PorterDuffXfermode mMode;
    private int mOuterNormalColor = 0;
    /* access modifiers changed from: private */
    public int mOutlineInsetBottom = 0;
    /* access modifiers changed from: private */
    public int mOutlineInsetLeft = 0;
    /* access modifiers changed from: private */
    public int mOutlineInsetRight = 0;
    /* access modifiers changed from: private */
    public int mOutlineInsetTop = 0;
    private WeakReference<View> mOwner;
    private Path mPath = new Path();
    /* access modifiers changed from: private */
    public int mRadius;
    private float[] mRadiusArray;
    private int mRightDividerAlpha = 255;
    private int mRightDividerColor;
    private int mRightDividerInsetBottom = 0;
    private int mRightDividerInsetTop = 0;
    private int mRightDividerWidth = 0;
    /* access modifiers changed from: private */
    public float mShadowAlpha = 0.0f;
    private int mShadowElevation = 0;
    private int mTopDividerAlpha = 255;
    private int mTopDividerColor;
    private int mTopDividerHeight = 0;
    private int mTopDividerInsetLeft = 0;
    private int mTopDividerInsetRight = 0;
    private int mWidthLimit = 0;
    private int mWidthMini = 0;

    public QMUILayoutHelper(Context context, AttributeSet attributeSet, int i, View view) {
        int i2;
        boolean z;
        int i3 = 0;
        this.mContext = context;
        this.mOwner = new WeakReference<>(view);
        int color = ContextCompat.getColor(context, C1614R.color.qmui_config_color_separator);
        this.mTopDividerColor = color;
        this.mBottomDividerColor = color;
        this.mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.mClipPaint = new Paint();
        this.mClipPaint.setAntiAlias(true);
        this.mShadowAlpha = QMUIResHelper.getAttrFloatValue(context, C1614R.attr.qmui_general_shadow_alpha);
        this.mBorderRect = new RectF();
        if (attributeSet == null && i == 0) {
            z = false;
            i2 = 0;
        } else {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUILayout, i, 0);
            int indexCount = obtainStyledAttributes.getIndexCount();
            int i4 = 0;
            z = false;
            i2 = 0;
            for (int i5 = 0; i5 < indexCount; i5++) {
                int index = obtainStyledAttributes.getIndex(i5);
                if (index == C1614R.styleable.QMUILayout_android_maxWidth) {
                    this.mWidthLimit = obtainStyledAttributes.getDimensionPixelSize(index, this.mWidthLimit);
                } else if (index == C1614R.styleable.QMUILayout_android_maxHeight) {
                    this.mHeightLimit = obtainStyledAttributes.getDimensionPixelSize(index, this.mHeightLimit);
                } else if (index == C1614R.styleable.QMUILayout_android_minWidth) {
                    this.mWidthMini = obtainStyledAttributes.getDimensionPixelSize(index, this.mWidthMini);
                } else if (index == C1614R.styleable.QMUILayout_android_minHeight) {
                    this.mHeightMini = obtainStyledAttributes.getDimensionPixelSize(index, this.mHeightMini);
                } else if (index == C1614R.styleable.QMUILayout_qmui_topDividerColor) {
                    this.mTopDividerColor = obtainStyledAttributes.getColor(index, this.mTopDividerColor);
                } else if (index == C1614R.styleable.QMUILayout_qmui_topDividerHeight) {
                    this.mTopDividerHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.mTopDividerHeight);
                } else if (index == C1614R.styleable.QMUILayout_qmui_topDividerInsetLeft) {
                    this.mTopDividerInsetLeft = obtainStyledAttributes.getDimensionPixelSize(index, this.mTopDividerInsetLeft);
                } else if (index == C1614R.styleable.QMUILayout_qmui_topDividerInsetRight) {
                    this.mTopDividerInsetRight = obtainStyledAttributes.getDimensionPixelSize(index, this.mTopDividerInsetRight);
                } else if (index == C1614R.styleable.QMUILayout_qmui_bottomDividerColor) {
                    this.mBottomDividerColor = obtainStyledAttributes.getColor(index, this.mBottomDividerColor);
                } else if (index == C1614R.styleable.QMUILayout_qmui_bottomDividerHeight) {
                    this.mBottomDividerHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.mBottomDividerHeight);
                } else if (index == C1614R.styleable.QMUILayout_qmui_bottomDividerInsetLeft) {
                    this.mBottomDividerInsetLeft = obtainStyledAttributes.getDimensionPixelSize(index, this.mBottomDividerInsetLeft);
                } else if (index == C1614R.styleable.QMUILayout_qmui_bottomDividerInsetRight) {
                    this.mBottomDividerInsetRight = obtainStyledAttributes.getDimensionPixelSize(index, this.mBottomDividerInsetRight);
                } else if (index == C1614R.styleable.QMUILayout_qmui_leftDividerColor) {
                    this.mLeftDividerColor = obtainStyledAttributes.getColor(index, this.mLeftDividerColor);
                } else if (index == C1614R.styleable.QMUILayout_qmui_leftDividerWidth) {
                    this.mLeftDividerWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.mBottomDividerHeight);
                } else if (index == C1614R.styleable.QMUILayout_qmui_leftDividerInsetTop) {
                    this.mLeftDividerInsetTop = obtainStyledAttributes.getDimensionPixelSize(index, this.mLeftDividerInsetTop);
                } else if (index == C1614R.styleable.QMUILayout_qmui_leftDividerInsetBottom) {
                    this.mLeftDividerInsetBottom = obtainStyledAttributes.getDimensionPixelSize(index, this.mLeftDividerInsetBottom);
                } else if (index == C1614R.styleable.QMUILayout_qmui_rightDividerColor) {
                    this.mRightDividerColor = obtainStyledAttributes.getColor(index, this.mRightDividerColor);
                } else if (index == C1614R.styleable.QMUILayout_qmui_rightDividerWidth) {
                    this.mRightDividerWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.mRightDividerWidth);
                } else if (index == C1614R.styleable.QMUILayout_qmui_rightDividerInsetTop) {
                    this.mRightDividerInsetTop = obtainStyledAttributes.getDimensionPixelSize(index, this.mRightDividerInsetTop);
                } else if (index == C1614R.styleable.QMUILayout_qmui_rightDividerInsetBottom) {
                    this.mRightDividerInsetBottom = obtainStyledAttributes.getDimensionPixelSize(index, this.mRightDividerInsetBottom);
                } else if (index == C1614R.styleable.QMUILayout_qmui_borderColor) {
                    this.mBorderColor = obtainStyledAttributes.getColor(index, this.mBorderColor);
                } else if (index == C1614R.styleable.QMUILayout_qmui_borderWidth) {
                    this.mBorderWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.mBorderWidth);
                } else if (index == C1614R.styleable.QMUILayout_qmui_radius) {
                    i2 = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == C1614R.styleable.QMUILayout_qmui_outerNormalColor) {
                    this.mOuterNormalColor = obtainStyledAttributes.getColor(index, this.mOuterNormalColor);
                } else if (index == C1614R.styleable.QMUILayout_qmui_hideRadiusSide) {
                    this.mHideRadiusSide = obtainStyledAttributes.getColor(index, this.mHideRadiusSide);
                } else if (index == C1614R.styleable.QMUILayout_qmui_showBorderOnlyBeforeL) {
                    this.mIsShowBorderOnlyBeforeL = obtainStyledAttributes.getBoolean(index, this.mIsShowBorderOnlyBeforeL);
                } else if (index == C1614R.styleable.QMUILayout_qmui_shadowElevation) {
                    i4 = obtainStyledAttributes.getDimensionPixelSize(index, i4);
                } else if (index == C1614R.styleable.QMUILayout_qmui_shadowAlpha) {
                    this.mShadowAlpha = obtainStyledAttributes.getFloat(index, this.mShadowAlpha);
                } else if (index == C1614R.styleable.QMUILayout_qmui_useThemeGeneralShadowElevation) {
                    z = obtainStyledAttributes.getBoolean(index, false);
                } else if (index == C1614R.styleable.QMUILayout_qmui_outlineInsetLeft) {
                    this.mOutlineInsetLeft = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == C1614R.styleable.QMUILayout_qmui_outlineInsetRight) {
                    this.mOutlineInsetRight = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == C1614R.styleable.QMUILayout_qmui_outlineInsetTop) {
                    this.mOutlineInsetTop = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == C1614R.styleable.QMUILayout_qmui_outlineInsetBottom) {
                    this.mOutlineInsetBottom = obtainStyledAttributes.getDimensionPixelSize(index, 0);
                } else if (index == C1614R.styleable.QMUILayout_qmui_outlineExcludePadding) {
                    this.mIsOutlineExcludePadding = obtainStyledAttributes.getBoolean(index, false);
                }
            }
            obtainStyledAttributes.recycle();
            i3 = i4;
        }
        if (i3 == 0 && z) {
            i3 = QMUIResHelper.getAttrDimen(context, C1614R.attr.qmui_general_shadow_elevation);
        }
        setRadiusAndShadow(i2, this.mHideRadiusSide, i3, this.mShadowAlpha);
    }

    public void setUseThemeGeneralShadowElevation() {
        this.mShadowElevation = QMUIResHelper.getAttrDimen(this.mContext, C1614R.attr.qmui_general_shadow_elevation);
        setRadiusAndShadow(this.mRadius, this.mHideRadiusSide, this.mShadowElevation, this.mShadowAlpha);
    }

    public void setOutlineExcludePadding(boolean z) {
        View view;
        if (useFeature() && (view = (View) this.mOwner.get()) != null) {
            this.mIsOutlineExcludePadding = z;
            view.invalidateOutline();
        }
    }

    public boolean setWidthLimit(int i) {
        if (this.mWidthLimit == i) {
            return false;
        }
        this.mWidthLimit = i;
        return true;
    }

    public boolean setHeightLimit(int i) {
        if (this.mHeightLimit == i) {
            return false;
        }
        this.mHeightLimit = i;
        return true;
    }

    public int getShadowElevation() {
        return this.mShadowElevation;
    }

    public float getShadowAlpha() {
        return this.mShadowAlpha;
    }

    public void setOutlineInset(int i, int i2, int i3, int i4) {
        View view;
        if (useFeature() && (view = (View) this.mOwner.get()) != null) {
            this.mOutlineInsetLeft = i;
            this.mOutlineInsetRight = i3;
            this.mOutlineInsetTop = i2;
            this.mOutlineInsetBottom = i4;
            view.invalidateOutline();
        }
    }

    public void setShowBorderOnlyBeforeL(boolean z) {
        this.mIsShowBorderOnlyBeforeL = z;
        invalidate();
    }

    public void setShadowElevation(int i) {
        if (this.mShadowElevation != i) {
            this.mShadowElevation = i;
            invalidate();
        }
    }

    public void setShadowAlpha(float f) {
        if (this.mShadowAlpha != f) {
            this.mShadowAlpha = f;
            invalidate();
        }
    }

    private void invalidate() {
        View view;
        if (useFeature() && (view = (View) this.mOwner.get()) != null) {
            int i = this.mShadowElevation;
            if (i == 0) {
                view.setElevation(0.0f);
            } else {
                view.setElevation((float) i);
            }
            view.invalidateOutline();
        }
    }

    public void setHideRadiusSide(int i) {
        if (this.mHideRadiusSide != i) {
            setRadiusAndShadow(this.mRadius, i, this.mShadowElevation, this.mShadowAlpha);
        }
    }

    public int getHideRadiusSide() {
        return this.mHideRadiusSide;
    }

    public void setRadius(int i) {
        if (this.mRadius != i) {
            setRadiusAndShadow(i, this.mShadowElevation, this.mShadowAlpha);
        }
    }

    public void setRadius(int i, int i2) {
        if (this.mRadius != i || i2 != this.mHideRadiusSide) {
            setRadiusAndShadow(i, i2, this.mShadowElevation, this.mShadowAlpha);
        }
    }

    public int getRadius() {
        return this.mRadius;
    }

    public void setRadiusAndShadow(int i, int i2, float f) {
        setRadiusAndShadow(i, this.mHideRadiusSide, i2, f);
    }

    public void setRadiusAndShadow(int i, int i2, int i3, float f) {
        View view = (View) this.mOwner.get();
        if (view != null) {
            this.mRadius = i;
            this.mHideRadiusSide = i2;
            int i4 = this.mRadius;
            boolean z = false;
            if (i4 > 0) {
                if (i2 == 1) {
                    this.mRadiusArray = new float[]{0.0f, 0.0f, 0.0f, 0.0f, (float) i4, (float) i4, (float) i4, (float) i4};
                } else if (i2 == 2) {
                    this.mRadiusArray = new float[]{(float) i4, (float) i4, 0.0f, 0.0f, 0.0f, 0.0f, (float) i4, (float) i4};
                } else if (i2 == 3) {
                    this.mRadiusArray = new float[]{(float) i4, (float) i4, (float) i4, (float) i4, 0.0f, 0.0f, 0.0f, 0.0f};
                } else if (i2 == 4) {
                    this.mRadiusArray = new float[]{0.0f, 0.0f, (float) i4, (float) i4, (float) i4, (float) i4, 0.0f, 0.0f};
                } else {
                    this.mRadiusArray = null;
                }
            }
            this.mShadowElevation = i3;
            this.mShadowAlpha = f;
            if (useFeature()) {
                if (this.mShadowElevation == 0 || isRadiusWithSideHidden()) {
                    view.setElevation(0.0f);
                } else {
                    view.setElevation((float) this.mShadowElevation);
                }
                view.setOutlineProvider(new ViewOutlineProvider() {
                    @TargetApi(21)
                    public void getOutline(View view, Outline outline) {
                        int i;
                        int i2;
                        int i3;
                        int i4;
                        int width = view.getWidth();
                        int height = view.getHeight();
                        if (width != 0 && height != 0) {
                            if (QMUILayoutHelper.this.isRadiusWithSideHidden()) {
                                if (QMUILayoutHelper.this.mHideRadiusSide == 4) {
                                    i4 = 0 - QMUILayoutHelper.this.mRadius;
                                    i2 = width;
                                    i = height;
                                } else if (QMUILayoutHelper.this.mHideRadiusSide == 1) {
                                    i3 = 0 - QMUILayoutHelper.this.mRadius;
                                    i2 = width;
                                    i = height;
                                    i4 = 0;
                                    outline.setRoundRect(i4, i3, i2, i, (float) QMUILayoutHelper.this.mRadius);
                                    return;
                                } else {
                                    if (QMUILayoutHelper.this.mHideRadiusSide == 2) {
                                        width += QMUILayoutHelper.this.mRadius;
                                    } else if (QMUILayoutHelper.this.mHideRadiusSide == 3) {
                                        height += QMUILayoutHelper.this.mRadius;
                                    }
                                    i2 = width;
                                    i = height;
                                    i4 = 0;
                                }
                                i3 = 0;
                                outline.setRoundRect(i4, i3, i2, i, (float) QMUILayoutHelper.this.mRadius);
                                return;
                            }
                            int access$200 = QMUILayoutHelper.this.mOutlineInsetTop;
                            int max = Math.max(access$200 + 1, height - QMUILayoutHelper.this.mOutlineInsetBottom);
                            int access$400 = QMUILayoutHelper.this.mOutlineInsetLeft;
                            int access$500 = width - QMUILayoutHelper.this.mOutlineInsetRight;
                            if (QMUILayoutHelper.this.mIsOutlineExcludePadding) {
                                access$400 += view.getPaddingLeft();
                                access$200 += view.getPaddingTop();
                                access$500 = Math.max(access$400 + 1, access$500 - view.getPaddingRight());
                                max = Math.max(access$200 + 1, max - view.getPaddingBottom());
                            }
                            int i5 = access$500;
                            int i6 = max;
                            int i7 = access$200;
                            int i8 = access$400;
                            outline.setAlpha(QMUILayoutHelper.this.mShadowAlpha);
                            if (QMUILayoutHelper.this.mRadius <= 0) {
                                outline.setRect(i8, i7, i5, i6);
                                return;
                            }
                            outline.setRoundRect(i8, i7, i5, i6, (float) QMUILayoutHelper.this.mRadius);
                        }
                    }
                });
                if (this.mRadius > 0) {
                    z = true;
                }
                view.setClipToOutline(z);
            }
            view.invalidate();
        }
    }

    public boolean isRadiusWithSideHidden() {
        return this.mRadius > 0 && this.mHideRadiusSide != 0;
    }

    public void updateTopDivider(int i, int i2, int i3, int i4) {
        this.mTopDividerInsetLeft = i;
        this.mTopDividerInsetRight = i2;
        this.mTopDividerHeight = i3;
        this.mTopDividerColor = i4;
    }

    public void updateBottomDivider(int i, int i2, int i3, int i4) {
        this.mBottomDividerInsetLeft = i;
        this.mBottomDividerInsetRight = i2;
        this.mBottomDividerColor = i4;
        this.mBottomDividerHeight = i3;
    }

    public void updateLeftDivider(int i, int i2, int i3, int i4) {
        this.mLeftDividerInsetTop = i;
        this.mLeftDividerInsetBottom = i2;
        this.mLeftDividerWidth = i3;
        this.mLeftDividerColor = i4;
    }

    public void updateRightDivider(int i, int i2, int i3, int i4) {
        this.mRightDividerInsetTop = i;
        this.mRightDividerInsetBottom = i2;
        this.mRightDividerWidth = i3;
        this.mRightDividerColor = i4;
    }

    public void onlyShowTopDivider(int i, int i2, int i3, int i4) {
        updateTopDivider(i, i2, i3, i4);
        this.mLeftDividerWidth = 0;
        this.mRightDividerWidth = 0;
        this.mBottomDividerHeight = 0;
    }

    public void onlyShowBottomDivider(int i, int i2, int i3, int i4) {
        updateBottomDivider(i, i2, i3, i4);
        this.mLeftDividerWidth = 0;
        this.mRightDividerWidth = 0;
        this.mTopDividerHeight = 0;
    }

    public void onlyShowLeftDivider(int i, int i2, int i3, int i4) {
        updateLeftDivider(i, i2, i3, i4);
        this.mRightDividerWidth = 0;
        this.mTopDividerHeight = 0;
        this.mBottomDividerHeight = 0;
    }

    public void onlyShowRightDivider(int i, int i2, int i3, int i4) {
        updateRightDivider(i, i2, i3, i4);
        this.mLeftDividerWidth = 0;
        this.mTopDividerHeight = 0;
        this.mBottomDividerHeight = 0;
    }

    public void setTopDividerAlpha(int i) {
        this.mTopDividerAlpha = i;
    }

    public void setBottomDividerAlpha(int i) {
        this.mBottomDividerAlpha = i;
    }

    public void setLeftDividerAlpha(int i) {
        this.mLeftDividerAlpha = i;
    }

    public void setRightDividerAlpha(int i) {
        this.mRightDividerAlpha = i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r0 = r2.mWidthMini;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int handleMiniWidth(int r3, int r4) {
        /*
            r2 = this;
            int r0 = android.view.View.MeasureSpec.getMode(r3)
            r1 = 1073741824(0x40000000, float:2.0)
            if (r0 == r1) goto L_0x0010
            int r0 = r2.mWidthMini
            if (r4 >= r0) goto L_0x0010
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
        L_0x0010:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.layout.QMUILayoutHelper.handleMiniWidth(int, int):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r0 = r2.mHeightMini;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int handleMiniHeight(int r3, int r4) {
        /*
            r2 = this;
            int r0 = android.view.View.MeasureSpec.getMode(r3)
            r1 = 1073741824(0x40000000, float:2.0)
            if (r0 == r1) goto L_0x0010
            int r0 = r2.mHeightMini
            if (r4 >= r0) goto L_0x0010
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
        L_0x0010:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.layout.QMUILayoutHelper.handleMiniHeight(int, int):int");
    }

    public int getMeasuredWidthSpec(int i) {
        if (this.mWidthLimit <= 0 || View.MeasureSpec.getSize(i) <= this.mWidthLimit) {
            return i;
        }
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            return View.MeasureSpec.makeMeasureSpec(this.mWidthLimit, Integer.MIN_VALUE);
        }
        return View.MeasureSpec.makeMeasureSpec(this.mWidthLimit, 1073741824);
    }

    public int getMeasuredHeightSpec(int i) {
        if (this.mHeightLimit <= 0 || View.MeasureSpec.getSize(i) <= this.mHeightLimit) {
            return i;
        }
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            return View.MeasureSpec.makeMeasureSpec(this.mWidthLimit, Integer.MIN_VALUE);
        }
        return View.MeasureSpec.makeMeasureSpec(this.mWidthLimit, 1073741824);
    }

    public void setBorderColor(@ColorInt int i) {
        this.mBorderColor = i;
    }

    public void setBorderWidth(int i) {
        this.mBorderWidth = i;
    }

    public void drawDividers(Canvas canvas, int i, int i2) {
        if (this.mDividerPaint == null && (this.mTopDividerHeight > 0 || this.mBottomDividerHeight > 0 || this.mLeftDividerWidth > 0 || this.mRightDividerWidth > 0)) {
            this.mDividerPaint = new Paint();
        }
        int i3 = this.mTopDividerHeight;
        if (i3 > 0) {
            this.mDividerPaint.setStrokeWidth((float) i3);
            this.mDividerPaint.setColor(this.mTopDividerColor);
            this.mDividerPaint.setAlpha(this.mTopDividerAlpha);
            float f = (((float) this.mTopDividerHeight) * 1.0f) / 2.0f;
            canvas.drawLine((float) this.mTopDividerInsetLeft, f, (float) (i - this.mTopDividerInsetRight), f, this.mDividerPaint);
        }
        int i4 = this.mBottomDividerHeight;
        if (i4 > 0) {
            this.mDividerPaint.setStrokeWidth((float) i4);
            this.mDividerPaint.setColor(this.mBottomDividerColor);
            this.mDividerPaint.setAlpha(this.mBottomDividerAlpha);
            float floor = (float) Math.floor((double) (((float) i2) - ((((float) this.mBottomDividerHeight) * 1.0f) / 2.0f)));
            canvas.drawLine((float) this.mBottomDividerInsetLeft, floor, (float) (i - this.mBottomDividerInsetRight), floor, this.mDividerPaint);
        }
        int i5 = this.mLeftDividerWidth;
        if (i5 > 0) {
            this.mDividerPaint.setStrokeWidth((float) i5);
            this.mDividerPaint.setColor(this.mLeftDividerColor);
            this.mDividerPaint.setAlpha(this.mLeftDividerAlpha);
            canvas.drawLine(0.0f, (float) this.mLeftDividerInsetTop, 0.0f, (float) (i2 - this.mLeftDividerInsetBottom), this.mDividerPaint);
        }
        int i6 = this.mRightDividerWidth;
        if (i6 > 0) {
            this.mDividerPaint.setStrokeWidth((float) i6);
            this.mDividerPaint.setColor(this.mRightDividerColor);
            this.mDividerPaint.setAlpha(this.mRightDividerAlpha);
            float f2 = (float) i;
            canvas.drawLine(f2, (float) this.mRightDividerInsetTop, f2, (float) (i2 - this.mRightDividerInsetBottom), this.mDividerPaint);
        }
    }

    public void dispatchRoundBorderDraw(Canvas canvas) {
        View view = (View) this.mOwner.get();
        if (view != null) {
            if (this.mBorderColor != 0 || (this.mRadius != 0 && this.mOuterNormalColor != 0)) {
                if (!this.mIsShowBorderOnlyBeforeL || !useFeature() || this.mShadowElevation == 0) {
                    int width = canvas.getWidth();
                    int height = canvas.getHeight();
                    if (this.mIsOutlineExcludePadding) {
                        this.mBorderRect.set((float) (view.getPaddingLeft() + 1), (float) (view.getPaddingTop() + 1), (float) ((width - 1) - view.getPaddingRight()), (float) ((height - 1) - view.getPaddingBottom()));
                    } else {
                        this.mBorderRect.set(1.0f, 1.0f, (float) (width - 1), (float) (height - 1));
                    }
                    if (this.mRadius == 0 || (!useFeature() && this.mOuterNormalColor == 0)) {
                        this.mClipPaint.setStyle(Paint.Style.STROKE);
                        canvas.drawRect(this.mBorderRect, this.mClipPaint);
                        return;
                    }
                    if (!useFeature()) {
                        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) width, (float) height, (Paint) null, 31);
                        canvas.drawColor(this.mOuterNormalColor);
                        this.mClipPaint.setColor(this.mOuterNormalColor);
                        this.mClipPaint.setStyle(Paint.Style.FILL);
                        this.mClipPaint.setXfermode(this.mMode);
                        float[] fArr = this.mRadiusArray;
                        if (fArr == null) {
                            RectF rectF = this.mBorderRect;
                            int i = this.mRadius;
                            canvas.drawRoundRect(rectF, (float) i, (float) i, this.mClipPaint);
                        } else {
                            drawRoundRect(canvas, this.mBorderRect, fArr, this.mClipPaint);
                        }
                        this.mClipPaint.setXfermode((Xfermode) null);
                        canvas.restoreToCount(saveLayer);
                    }
                    this.mClipPaint.setColor(this.mBorderColor);
                    this.mClipPaint.setStrokeWidth((float) this.mBorderWidth);
                    this.mClipPaint.setStyle(Paint.Style.STROKE);
                    float[] fArr2 = this.mRadiusArray;
                    if (fArr2 == null) {
                        RectF rectF2 = this.mBorderRect;
                        int i2 = this.mRadius;
                        canvas.drawRoundRect(rectF2, (float) i2, (float) i2, this.mClipPaint);
                        return;
                    }
                    drawRoundRect(canvas, this.mBorderRect, fArr2, this.mClipPaint);
                }
            }
        }
    }

    private void drawRoundRect(Canvas canvas, RectF rectF, float[] fArr, Paint paint) {
        this.mPath.reset();
        this.mPath.addRoundRect(rectF, fArr, Path.Direction.CW);
        canvas.drawPath(this.mPath, paint);
    }

    public static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }
}
