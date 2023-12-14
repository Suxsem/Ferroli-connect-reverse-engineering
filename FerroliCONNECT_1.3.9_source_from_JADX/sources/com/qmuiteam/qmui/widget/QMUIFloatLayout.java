package com.qmuiteam.qmui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.qmuiteam.qmui.C1614R;

public class QMUIFloatLayout extends ViewGroup {
    private static final int LINES = 0;
    private static final int NUMBER = 1;
    private int mChildHorizontalSpacing;
    private int mChildVerticalSpacing;
    private int mGravity;
    private int[] mItemNumberInEachLine;
    private int mLineCount;
    private int mMaxMode;
    private int mMaximum;
    private OnLineCountChangeListener mOnLineCountChangeListener;
    private int[] mWidthSumInEachLine;
    private int measuredChildCount;

    public interface OnLineCountChangeListener {
        void onChange(int i, int i2);
    }

    public QMUIFloatLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIFloatLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QMUIFloatLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxMode = 0;
        this.mMaximum = Integer.MAX_VALUE;
        this.mLineCount = 0;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIFloatLayout);
        this.mChildHorizontalSpacing = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIFloatLayout_qmui_childHorizontalSpacing, 0);
        this.mChildVerticalSpacing = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIFloatLayout_qmui_childVerticalSpacing, 0);
        this.mGravity = obtainStyledAttributes.getInteger(C1614R.styleable.QMUIFloatLayout_android_gravity, 3);
        int i = obtainStyledAttributes.getInt(C1614R.styleable.QMUIFloatLayout_android_maxLines, -1);
        if (i >= 0) {
            setMaxLines(i);
        }
        int i2 = obtainStyledAttributes.getInt(C1614R.styleable.QMUIFloatLayout_qmui_maxNumber, -1);
        if (i2 >= 0) {
            setMaxNumber(i2);
        }
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"DrawAllocation"})
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = i;
        int i8 = i2;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        int childCount = getChildCount();
        this.mItemNumberInEachLine = new int[childCount];
        this.mWidthSumInEachLine = new int[childCount];
        int i9 = 8;
        int i10 = 0;
        int i11 = 1;
        if (mode == 1073741824) {
            this.measuredChildCount = 0;
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = size - getPaddingRight();
            int i12 = paddingTop;
            int i13 = 0;
            int i14 = paddingLeft;
            i4 = 0;
            while (true) {
                if (i10 >= childCount || ((this.mMaxMode == i11 && this.measuredChildCount >= this.mMaximum) || (this.mMaxMode == 0 && i4 >= this.mMaximum))) {
                    i6 = size;
                } else {
                    View childAt = getChildAt(i10);
                    if (childAt.getVisibility() == i9) {
                        i6 = size;
                    } else {
                        ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                        i6 = size;
                        childAt.measure(getChildMeasureSpec(i7, getPaddingLeft() + getPaddingRight(), layoutParams.width), getChildMeasureSpec(i8, getPaddingTop() + getPaddingBottom(), layoutParams.height));
                        int measuredWidth = childAt.getMeasuredWidth();
                        i13 = Math.max(i13, childAt.getMeasuredHeight());
                        if (i14 + measuredWidth > paddingRight) {
                            if (this.mMaxMode == 0 && i4 + 1 >= this.mMaximum) {
                                break;
                            }
                            int[] iArr = this.mWidthSumInEachLine;
                            iArr[i4] = iArr[i4] - this.mChildHorizontalSpacing;
                            i4++;
                            i14 = getPaddingLeft();
                            i12 += this.mChildVerticalSpacing + i13;
                        }
                        int[] iArr2 = this.mItemNumberInEachLine;
                        iArr2[i4] = iArr2[i4] + 1;
                        int[] iArr3 = this.mWidthSumInEachLine;
                        int i15 = iArr3[i4];
                        int i16 = this.mChildHorizontalSpacing;
                        iArr3[i4] = i15 + measuredWidth + i16;
                        i14 += measuredWidth + i16;
                        this.measuredChildCount++;
                    }
                    i10++;
                    size = i6;
                    i9 = 8;
                    i11 = 1;
                }
            }
            i6 = size;
            int[] iArr4 = this.mWidthSumInEachLine;
            if (iArr4.length > 0 && iArr4[i4] > 0) {
                iArr4[i4] = iArr4[i4] - this.mChildHorizontalSpacing;
            }
            if (mode2 == 0) {
                size2 = i12 + i13 + getPaddingBottom();
            } else if (mode2 == Integer.MIN_VALUE) {
                size2 = Math.min(i12 + i13 + getPaddingBottom(), size2);
            }
            i3 = i6;
        } else {
            this.measuredChildCount = 0;
            i3 = getPaddingLeft() + getPaddingRight();
            int i17 = 0;
            int i18 = 0;
            while (true) {
                if (i17 >= childCount) {
                    i5 = 1;
                    break;
                }
                int i19 = this.mMaxMode;
                i5 = 1;
                if (i19 != 1) {
                    if (i19 == 0 && 1 > this.mMaximum) {
                        break;
                    }
                } else if (this.measuredChildCount > this.mMaximum) {
                    break;
                }
                View childAt2 = getChildAt(i17);
                if (childAt2.getVisibility() != 8) {
                    ViewGroup.LayoutParams layoutParams2 = childAt2.getLayoutParams();
                    childAt2.measure(getChildMeasureSpec(i7, getPaddingLeft() + getPaddingRight(), layoutParams2.width), getChildMeasureSpec(i8, getPaddingTop() + getPaddingBottom(), layoutParams2.height));
                    i3 += childAt2.getMeasuredWidth();
                    i18 = Math.max(i18, childAt2.getMeasuredHeight());
                    this.measuredChildCount++;
                }
                i17++;
            }
            int i20 = this.measuredChildCount;
            if (i20 > 0) {
                i3 += this.mChildHorizontalSpacing * (i20 - i5);
            }
            size2 = i18 + getPaddingTop() + getPaddingBottom();
            int[] iArr5 = this.mItemNumberInEachLine;
            if (iArr5.length > 0) {
                iArr5[0] = childCount;
            }
            int[] iArr6 = this.mWidthSumInEachLine;
            if (iArr6.length > 0) {
                iArr6[0] = i3;
            }
            i4 = 0;
        }
        setMeasuredDimension(i3, size2);
        int i21 = i4 + 1;
        int i22 = this.mLineCount;
        if (i22 != i21) {
            OnLineCountChangeListener onLineCountChangeListener = this.mOnLineCountChangeListener;
            if (onLineCountChangeListener != null) {
                onLineCountChangeListener.onChange(i22, i21);
            }
            this.mLineCount = i21;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = this.mGravity & 7;
        if (i6 == 1) {
            layoutWithGravityCenterHorizontal(i5);
        } else if (i6 == 3) {
            layoutWithGravityLeft(i5);
        } else if (i6 != 5) {
            layoutWithGravityLeft(i5);
        } else {
            layoutWithGravityRight(i5);
        }
    }

    private void layoutWithGravityCenterHorizontal(int i) {
        int[] iArr;
        int paddingTop = getPaddingTop();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr2 = this.mItemNumberInEachLine;
            if (i2 >= iArr2.length || iArr2[i2] == 0 || i3 > this.measuredChildCount - 1) {
                int childCount = getChildCount();
                int i4 = this.measuredChildCount;
            } else {
                int paddingLeft = ((((i - getPaddingLeft()) - getPaddingRight()) - this.mWidthSumInEachLine[i2]) / 2) + getPaddingLeft();
                int i5 = 0;
                int i6 = i3;
                while (true) {
                    iArr = this.mItemNumberInEachLine;
                    if (i6 >= iArr[i2] + i3) {
                        break;
                    }
                    View childAt = getChildAt(i6);
                    if (childAt.getVisibility() != 8) {
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight = childAt.getMeasuredHeight();
                        childAt.layout(paddingLeft, paddingTop, paddingLeft + measuredWidth, paddingTop + measuredHeight);
                        i5 = Math.max(i5, measuredHeight);
                        paddingLeft += measuredWidth + this.mChildHorizontalSpacing;
                    }
                    i6++;
                }
                paddingTop += i5 + this.mChildVerticalSpacing;
                i3 += iArr[i2];
                i2++;
            }
        }
        int childCount2 = getChildCount();
        int i42 = this.measuredChildCount;
        if (i42 < childCount2) {
            while (i42 < childCount2) {
                View childAt2 = getChildAt(i42);
                if (childAt2.getVisibility() != 8) {
                    childAt2.layout(0, 0, 0, 0);
                }
                i42++;
            }
        }
    }

    private void layoutWithGravityLeft(int i) {
        int paddingRight = i - getPaddingRight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        int min = Math.min(childCount, this.measuredChildCount);
        int i2 = paddingTop;
        int i3 = 0;
        int i4 = paddingLeft;
        for (int i5 = 0; i5 < min; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (i4 + measuredWidth > paddingRight) {
                    i4 = getPaddingLeft();
                    i2 += i3 + this.mChildVerticalSpacing;
                    i3 = 0;
                }
                childAt.layout(i4, i2, i4 + measuredWidth, i2 + measuredHeight);
                i4 += measuredWidth + this.mChildHorizontalSpacing;
                i3 = Math.max(i3, measuredHeight);
            }
        }
        int i6 = this.measuredChildCount;
        if (i6 < childCount) {
            while (i6 < childCount) {
                View childAt2 = getChildAt(i6);
                if (childAt2.getVisibility() != 8) {
                    childAt2.layout(0, 0, 0, 0);
                }
                i6++;
            }
        }
    }

    private void layoutWithGravityRight(int i) {
        int[] iArr;
        int paddingTop = getPaddingTop();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr2 = this.mItemNumberInEachLine;
            if (i2 >= iArr2.length || iArr2[i2] == 0 || i3 > this.measuredChildCount - 1) {
                int childCount = getChildCount();
                int i4 = this.measuredChildCount;
            } else {
                int paddingRight = (i - getPaddingRight()) - this.mWidthSumInEachLine[i2];
                int i5 = 0;
                int i6 = i3;
                while (true) {
                    iArr = this.mItemNumberInEachLine;
                    if (i6 >= iArr[i2] + i3) {
                        break;
                    }
                    View childAt = getChildAt(i6);
                    if (childAt.getVisibility() != 8) {
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight = childAt.getMeasuredHeight();
                        childAt.layout(paddingRight, paddingTop, paddingRight + measuredWidth, paddingTop + measuredHeight);
                        i5 = Math.max(i5, measuredHeight);
                        paddingRight += measuredWidth + this.mChildHorizontalSpacing;
                    }
                    i6++;
                }
                paddingTop += i5 + this.mChildVerticalSpacing;
                i3 += iArr[i2];
                i2++;
            }
        }
        int childCount2 = getChildCount();
        int i42 = this.measuredChildCount;
        if (i42 < childCount2) {
            while (i42 < childCount2) {
                View childAt2 = getChildAt(i42);
                if (childAt2.getVisibility() != 8) {
                    childAt2.layout(0, 0, 0, 0);
                }
                i42++;
            }
        }
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setMaxNumber(int i) {
        this.mMaximum = i;
        this.mMaxMode = 1;
        requestLayout();
    }

    public int getMaxNumber() {
        if (this.mMaxMode == 1) {
            return this.mMaximum;
        }
        return -1;
    }

    public void setMaxLines(int i) {
        this.mMaximum = i;
        this.mMaxMode = 0;
        requestLayout();
    }

    public void setOnLineCountChangeListener(OnLineCountChangeListener onLineCountChangeListener) {
        this.mOnLineCountChangeListener = onLineCountChangeListener;
    }

    public int getLineCount() {
        return this.mLineCount;
    }

    public int getMaxLines() {
        if (this.mMaxMode == 0) {
            return this.mMaximum;
        }
        return -1;
    }

    public void setChildHorizontalSpacing(int i) {
        this.mChildHorizontalSpacing = i;
        invalidate();
    }

    public void setChildVerticalSpacing(int i) {
        this.mChildVerticalSpacing = i;
        invalidate();
    }
}
